/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.DomStatistics;
import com.google.common.io.Files;
import java.io.FileNotFoundException;
import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.VirtualTokenStatistics;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.PointingStatistics;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.SpanStatistics;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SDATATYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SRelation2ANNISMapper implements Runnable, SGraphTraverseHandler {

// =============================== Globally used objects ======================
  private final static Logger log = LoggerFactory.getLogger(SRelation2ANNISMapper.class);

  protected IdManager idManager;
  private final Salt2ANNISMapper parentMapper;

  protected SDocumentGraph documentGraph;

  /**
   * Default name for namespaces if they cannot be computed by the layers name.
   */
  protected final static String DEFAULT_NS = "default_ns";
  protected final static String DEFAULT_LAYER = "default_layer";

  EList<? extends SNode> sRelationRoots = null;
  STYPE_NAME relationTypeName = null;
  
  
// =================================== Constructor ============================ 
  /**
   * @param idManager
   * @param documentGraph
   * @param token2Index
   * @param nodeTabWriter
   * @param nodeAnnoTabWriter
   * @param rankTabWriter
   * @param edgeAnnoTabWriter
   * @param componentTabWriter
   * @param parentMapper
   */
  public SRelation2ANNISMapper(IdManager idManager, SDocumentGraph documentGraph,
          Map<SToken, Long> token2Index,
          TupleWriter nodeTabWriter, TupleWriter nodeAnnoTabWriter,
          TupleWriter rankTabWriter, TupleWriter edgeAnnoTabWriter,
          TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    this.idManager = idManager;
    this.parentMapper = parentMapper;
    this.documentGraph = documentGraph;
    this.token2Index = token2Index;
    

    writers.clear();
    writers.put(OutputTable.NODE, nodeTabWriter);
    writers.put(OutputTable.NODE_ANNOTATION, nodeAnnoTabWriter);
    writers.put(OutputTable.RANK, rankTabWriter);
    writers.put(OutputTable.EDGE_ANNO, edgeAnnoTabWriter);
    writers.put(OutputTable.COMPONENT, componentTabWriter);
  }

  protected void setTraversionSType(String traversionSType) {
    this.currentTraversionSType = traversionSType;
  }

  protected void beginTransaction() {
    transactionIds.clear();
    for (Map.Entry<OutputTable, TupleWriter> e : writers.entrySet()) {
      transactionIds.put(e.getKey(), e.getValue().beginTA());
    }
  }

  protected void initialiseTraversion(String componentType, String componentLayer, String componentName) {

		// set the current component:
    // id		type	layer					name
    // unique	c/d/p	SLayer/default_layer	"NULL"/SType
    currentComponentId = idManager.getGlobal().getNewComponentId();
    currentComponentType = componentType;
    currentComponentLayer = componentLayer;
    currentComponentName = componentName;

    // initialise the traversion level
    rankLevel = 0l;

    // initialise the pre and post order table
    preorderTable = new ConcurrentHashMap<>();

    // initialise the preorder
    prePostOrder = 0l;

    this.virtualNodes = new HashSet<>();

    // initialise rank Hashtable
    this.rankTable = new ConcurrentHashMap<>();
  }

  protected void commitTransaction() {

    try {
      for (Map.Entry<OutputTable, TupleWriter> e : writers.entrySet()) {
        Long txId = transactionIds.get(e.getKey());
        if (txId != null) {
          e.getValue().commitTA(txId);
        }
      }

    } catch (FileNotFoundException ex) {
      log.error("Could not write output file", ex);
      abortTransaction();
    }
    transactionIds.clear();
  }

  protected void abortTransaction() {
    for (Map.Entry<OutputTable, TupleWriter> e : writers.entrySet()) {
      Long txId = transactionIds.get(e.getKey());
      if (txId != null) {
        e.getValue().abortTA(txId);
      }
    }
    transactionIds.clear();
  }

  public enum OutputTable {

    RANK, EDGE_ANNO, COMPONENT, NODE, NODE_ANNOTATION
  }

  protected final Map<OutputTable, TupleWriter> writers
          = new EnumMap<>(OutputTable.class);

  private final Map<OutputTable, Long> transactionIds
          = new EnumMap<>(OutputTable.class);

// ============================ Data for SRelation Mapping ====================
  protected TRAVERSION_TYPE traversionType;

  protected Long currentComponentId = null;
  protected String currentComponentType = null;
  protected String currentComponentLayer = null;
  protected String currentComponentName = null;

  protected Long rankLevel;

  protected String currentTraversionSType = null;

// =================================== Pre/Post order =========================
  /**
   * counter for pre and post order
   */
  protected Long prePostOrder = null;

  /**
   * These tables contain the internal node IDs as key and the preorder as
   * value.
   */
  private ConcurrentMap<Long, Long> preorderTable;

  /**
   * Every internal node iD is a target of a rank and the Long is the id of the
   * Rank
   */
  protected ConcurrentMap<Long, Long> rankTable;

  /**
   * returns a new and unique ppOrder
   *
   * @return
   */
  protected synchronized Long getNewPPOrder() {
    if (prePostOrder == null) {
      prePostOrder = 0l;
    }
    Long currPrePost = prePostOrder;
    prePostOrder++;
    return (currPrePost);
  }

// ================================= Graph Traversion   =======================
  HashSet<SNode> virtualNodes;

  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SNode currNode, SRelation sRelation,
          SNode fromNode, long order) {
		// A node was reached.
    // We've got the rank
    // fromNode -> edge -> currNode

    Long rankId;
    EList<Long> virtualTokenIds = this.idManager.getVirtualisedTokenId(currNode.getSId());

    if (virtualTokenIds != null) {
      virtualNodes.add(currNode);
    }

		// If there are virtual token IDs for a node and we are handling the 
    // mapping of spans, we need map each span relation to potentially several token.
    // Thus we need a special handling for this case. Good thing is, 
    // that since these are spans we already now the post-order and don't
    // have to make a very special handling in nodeLeft()
    if (virtualTokenIds != null && traversionType.equals(TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR)) {

      for (Long virtualTokenId : virtualTokenIds) {
        // get a rank id
        rankId = idManager.getGlobal().getNewRankId();

        Long parentRank = null;
        if (fromNode != null) {
          parentRank = this.rankTable.get(idManager.getNodeId(fromNode));
        }
        Long pre = this.getNewPPOrder();
        Long post = this.getNewPPOrder();

        EList<String> rankEntry = new BasicEList<>();
        rankEntry.add(rankId.toString());
        rankEntry.add(pre.toString());
        rankEntry.add(post.toString());
        rankEntry.add(virtualTokenId.toString());
        rankEntry.add(this.currentComponentId.toString());
        if (parentRank == null) {
          rankEntry.add("NULL");
        } else {
          rankEntry.add(parentRank.toString());
        }
        rankEntry.add(this.rankLevel.toString());

        // map the SAnnotations
        boolean hasAnnotations = false;
        if (sRelation != null) {
          if (sRelation.getSAnnotations() != null) {
            for (SAnnotation sAnnotation : sRelation.getSAnnotations()) {
              this.mapSAnnotation2ANNIS(rankId, sAnnotation);
              hasAnnotations = true;
            }
          }
        }
        if (hasAnnotations || fromNode == null) {
          addTuple(OutputTable.RANK, rankEntry);
        }
      }

    } else {
      // replace the current node with the virtual span if necessary
      Long currentNodeID;
      if (virtualTokenIds != null) {
        currentNodeID = idManager.getVirtualisedSpanId(currNode.getSId());
      } else {
        // map the target node
        currentNodeID = this.mapSNode(currNode);
      }
//			if (this.preorderTable.containsKey(currentNodeID)) {
//				// this should NOT happen
//				throw new RuntimeException("Impossible traversal state, ID " + currentNodeID + " traversed twice");
//
//			}
      // set the rank id
      rankId = idManager.getGlobal().getNewRankId();
      // It does not have a pre-order. Set it.
      this.preorderTable.put(currentNodeID, this.getNewPPOrder());

      this.rankTable.put(currentNodeID, rankId);

      if (sRelation != null) {
        if (sRelation.getSAnnotations() != null) {
          for (SAnnotation sAnnotation : sRelation.getSAnnotations()) {
            this.mapSAnnotation2ANNIS(rankId, sAnnotation);
          }
        }
      }

    }

    this.rankLevel += 1;
  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
          SNode currNode, SRelation edge, SNode fromNode, long order) {

		// We've got the rank
    // fromNode -> edge -> currNode
    Long currNodeID;
    if (virtualNodes.contains(currNode)) {
      currNodeID = idManager.getVirtualisedSpanId(currNode.getSId());
    } else {
      currNodeID = idManager.getNodeId(currNode);
    }

    Long parentRank = null;
    if (fromNode != null) {
      Long fromNodeID;
      if (virtualNodes.contains(fromNode)) {
        fromNodeID = idManager.getVirtualisedSpanId(fromNode.getSId());
      } else {
        fromNodeID = idManager.getNodeId(fromNode);
      }
      parentRank = this.rankTable.get(fromNodeID);
    }

    Long rankId = this.rankTable.get(currNodeID);
    Long pre = this.preorderTable.get(currNodeID);
    if (rankId != null && pre != null) {
      Long post = this.getNewPPOrder();

      // decrease the rank level
      this.rankLevel -= 1;

      this.mapRank2ANNIS(edge, currNodeID, rankId, pre, post, parentRank, rankLevel);
    } else {
      this.rankLevel -= 1;
    }

  }

  protected void addTuple(OutputTable table, Collection<String> tuple) {
    try {
      TupleWriter w = writers.get(table);
      if (w != null) {
        Long txId = transactionIds.get(table);
        if (txId == null) {
          w.addTuple(tuple);
        } else {
          w.addTuple(txId, tuple);
        }
      }
    } catch (FileNotFoundException e) {
      throw new PepperModuleException(
              "Could not write to the " + table.name() + " tab TupleWriter. Exception is: " + e.
              getMessage(), e);
    }
  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SRelation edge, SNode currNode, long order) {
    boolean returnVal = false;

    return returnVal;
  }

// =============================== Mapping of SRelations ======================
  public abstract void mapSRelations2ANNIS(EList<? extends SNode> sRelationRoots, STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType);

  /**
   * This method maps the currently processed component to the ANNIS
   * component tab file.
   */
  protected void mapComponent2ANNIS() {
		// id		type	layer					name
    // unique	c/d/p	SLayer/default_layer	"NULL"/SType
    EList<String> componentEntry = new BasicEList<>();
    componentEntry.add(currentComponentId.toString());
    componentEntry.add(currentComponentType);
    componentEntry.add(currentComponentLayer);
    componentEntry.add(idManager.getEscapedIdentifier(currentComponentName));
    // add the tuple
    addTuple(OutputTable.COMPONENT, componentEntry);

  }

  protected void mapRank2ANNIS(SRelation sRelation, Long targetNodeID, Long rankId, Long preOrder, Long postOrder, Long parentRank, Long level) {
    // check all needed params
    if (targetNodeID == null) {
      throw new PepperModuleException("The given target node for the rank is null");
    }
    if (rankId == null) {
      throw new PepperModuleException("The given rank id for the rank is null");
    }
    if (preOrder == null) {
      throw new PepperModuleException("The given pre order for the rank is null");
    }
    if (postOrder == null) {
      throw new PepperModuleException("The given post order for the rank is null");
    }
    if (level == null) {
      throw new PepperModuleException("The given level for the rank is null");
    }

    /**
     * For rank, we need: rankId	pre	post	node_ref	component_ref	parentRank
     * level Thus, we create an EList of SRelations and the index is the rankId
     *
     * @TODO: do we REALLY need this?
     */
    EList<String> rankEntry = new BasicEList<>();

    rankEntry.add(rankId.toString());
    rankEntry.add(this.preorderTable.get(targetNodeID).toString());
    rankEntry.add(postOrder.toString());
    rankEntry.add(Long.toString(targetNodeID));
    rankEntry.add(this.currentComponentId.toString());
    if (parentRank == null) {
      rankEntry.add("NULL");
    } else {
      rankEntry.add(parentRank.toString());
    }

    rankEntry.add(level.toString());

    addTuple(OutputTable.RANK, rankEntry);

  }

  protected void mapSAnnotation2ANNIS(Long rankId, SAnnotation sAnnotation) {
    if (rankId == null) {
      throw new PepperModuleException("The given rank id for the mapping of the SAnnotation is null");
    }
    if (sAnnotation == null) {
      throw new PepperModuleException("The given SAnnotation is null");
    }
    // rank_id 	namespace	name	value

    EList<String> edgeAnnotationEntry = new BasicEList<>();
    edgeAnnotationEntry.add(rankId.toString());
    String ns;
    if (sAnnotation.getSNS() != null) {
      ns = sAnnotation.getSNS();
    } else {
      ns = "default_ns";
    }
    edgeAnnotationEntry.add(idManager.getEscapedIdentifier(ns));
    edgeAnnotationEntry.add(idManager.getEscapedIdentifier(sAnnotation.getSName()));
    edgeAnnotationEntry.add(sAnnotation.getSValueSTEXT());

    addTuple(OutputTable.EDGE_ANNO, edgeAnnotationEntry);

  }

// =============================== Data for Node Mapping ======================
  protected final Map<SToken, Long> token2Index;

// ================================= Mapping of SNodes ========================
  public void mapSNodes(EList<SNode> nodes) {
    for (SNode node : nodes) {
      this.mapSNode(node);
    }
  }

  public Long mapSNode(SNode sNode) {
    SegmentationInfo segInfo = this.idManager.getSegmentInformation(sNode.getSId());
    if (segInfo != null) {
      return this.mapSNode(sNode, segInfo.getANNISId(), segInfo.getSegmentationName(), segInfo.getSpan());
    } else {
      return this.mapSNode(sNode, null, null, null);
    }
  }

  public Long mapSNode(SNode node, Long seg_index, String seg_name, String span) {
    /// get the SElementId of the node since we will need it many times
    String nodeSElementId = node.getSId();

    /// if the node already has a nodeId, it was already mapped
    Pair<Long, Boolean> idPair = idManager.getNewNodeId(nodeSElementId);
    if (idPair.getRight() == false) {
      // the node is not new
      return idPair.getLeft();
    }
    Long virtualSpanID = idManager.getVirtualisedSpanId(nodeSElementId);
    if (virtualSpanID != null) {
      // the node is not new
      return virtualSpanID;
    }
		// initialise all variables which will be used for the node.tab 
    // get the RAId
    Long id = idPair.getLeft();
    // get the text ref
    Long text_ref = null;
    // get the document ref
    Long corpus_ref = this.idManager.getNewCorpusTabId(this.documentGraph.getSDocument().getSId());
		// get the layer if there is one
    //@ TODO: Change this to DEFAULT_LAYER
    String layer = DEFAULT_NS;
    // initialise the name with the unique identifier
    String name = getUniqueNodeName(node);
    // get the first covered character
    Long left = null;
    // get the last covered character
    Long right = null;
    // get the token index. If the node is no Token, the tokenIndex is NULL
    Long token_index = null;
    // get the first covered token
    Long left_token = null;
    // get the last covered token
    Long right_token = null;
		// get the segment_index
    //Long seg_index = null;
    // initialise the segment name
    //String seg_name = null;
    // initialise the span
    //String span = null;

    {// set the layer to the actual value
      if (node.getSLayers() != null) {
        if (node.getSLayers().size() != 0) {
          layer = node.getSLayers().get(0).getSName();
        }
      }
    }// set the layer to the actual value

    if (node instanceof SToken) {      
      // set the token index
      token_index = this.token2Index.get((SToken) node);
      left_token = token_index;
      right_token = token_index;

      // set the left and right value and the text_ref
      EList<Edge> outEdges = documentGraph.getOutEdges(node.getSId());
      if (outEdges == null) {
        throw new PepperModuleException("The token " + node.getSId() + " has no outgoing edges!");
      }
      /// find the STextualRelation
      for (Edge edge : outEdges) {
        // get the edge which is of the type STextual relation
        if (edge instanceof STextualRelation) {
          STextualRelation sTextualRelation = ((STextualRelation) edge);
          // set the left value
          left = new Long(sTextualRelation.getSStart());
          // set the right value which is end -1 since SEnd points to the index of the last char +1
          right = new Long(sTextualRelation.getSEnd() - 1);
          // set the reference to the text
          text_ref = idManager.getNewTextId(sTextualRelation.getSTextualDS().getSId());
          // set the overlapped text
          span = sTextualRelation.getSTextualDS().getSText().substring(left.intValue(), sTextualRelation.getSEnd());
          
          getVirtualTokenStats().checkRealToken(span);
          break;
        }
      }
    } else {
      if (node instanceof SSpan || node instanceof SStructure) {
        EList<STYPE_NAME> overlappingTypes = new BasicEList<>();
        overlappingTypes.add(STYPE_NAME.SSPANNING_RELATION);
        if (node instanceof SStructure) {
          overlappingTypes.add(STYPE_NAME.SDOMINANCE_RELATION);
        }
        // get the overlapping token
        EList<SToken> overlappedToken = this.documentGraph.getOverlappedSTokens(node, overlappingTypes);
        // sort the token by left
        EList<SToken> sortedOverlappedToken = this.documentGraph.getSortedSTokenByText(overlappedToken);

        SToken firstOverlappedToken = sortedOverlappedToken.get(0);
        SToken lastOverlappedToken = sortedOverlappedToken.get(sortedOverlappedToken.size() - 1);

        // set left_token
        left_token = (long) this.token2Index.get(firstOverlappedToken);
        // set right_token
        right_token = (long) this.token2Index.get(lastOverlappedToken);
        // get first and last overlapped character
        EList<Edge> firstTokenOutEdges = documentGraph.getOutEdges(firstOverlappedToken.getSId());
        if (firstTokenOutEdges == null) {
          throw new PepperModuleException("The token " + firstOverlappedToken.getSId() + " has no outgoing edges!");
        }

        /// find the STextualRelation
        for (Edge edge : firstTokenOutEdges) {
          // get the edge which is of the type STextual relation
          if (edge instanceof STextualRelation) {
            STextualRelation sTextualRelation = ((STextualRelation) edge);
            // set the left value
            left = new Long(sTextualRelation.getSStart());

            text_ref = idManager.getNewTextId(sTextualRelation.getSTextualDS().getSId());
            //System.out.println("Setting text_ref to"+ text_ref.toString());
            break;
          }
        }

        EList<Edge> lastTokenOutEdges = documentGraph.getOutEdges(lastOverlappedToken.getSId());
        if (lastTokenOutEdges == null) {
          throw new PepperModuleException("The token " + lastOverlappedToken.getSId() + " has no outgoing edges!");
        }

        /// find the STextualRelation
        for (Edge edge : lastTokenOutEdges) {
          // get the edge which is of the type STextual relation
          if (edge instanceof STextualRelation) {
            STextualRelation sTextualRelation = ((STextualRelation) edge);
            // set the left value
            right = new Long(sTextualRelation.getSEnd() - 1);
            text_ref = idManager.getNewTextId(sTextualRelation.getSTextualDS().getSId());
            break;
          }
        }

        if (this.idManager.hasVirtualTokenization()) {
          EList<Long> virtTokenFirst = this.idManager.getVirtualisedTokenId(firstOverlappedToken.getSId());
          EList<Long> virtTokenLast = this.idManager.getVirtualisedTokenId(lastOverlappedToken.getSId());
          if (virtTokenFirst != null && virtTokenLast != null) {
            
            left_token = this.idManager.getMinimalVirtTokenIndex(virtTokenFirst.get(0))[0];
            right_token = this.idManager.getMinimalVirtTokenIndex(virtTokenLast.get(virtTokenLast.size()-1))[0];

            // the token index is the same as the character for the virtual 
            // tokenization since each virtual token has exactly one character (space)
            left = left_token;
            right = right_token;
            
            span = null;
          }
        }

      } else {
        // IGNORE CASE
      }
      // the node is a span or structure
    }
    

    this.writeNodeTabEntry(id, text_ref, corpus_ref, layer, name, left, right,
            token_index, left_token, right_token, seg_index, seg_name, span,
            isRoot(node));

    if (node.getSAnnotations() != null) {
      this.mapSNodeAnnotations(node, id, node.getSAnnotations());
    }

    // report progress
    parentMapper.notifiyNewNodeMapped();

    return id;
  }
  
  private String getUniqueNodeName(SNode node) {
    URI nodeID = node.getSElementPath();
    return nodeID.fragment();
  }

  protected void writeNodeTabEntry(
          Long id, Long text_ref, Long corpus_ref,
          String layer, String name,
          Long left, Long right, Long token_index,
          Long left_token, Long right_token,
          Long seg_index, String seg_name, String span,
          boolean isRoot) {

    EList<String> tableEntry = new BasicEList<>();
    tableEntry.add(id.toString());
    tableEntry.add(text_ref.toString());
    tableEntry.add(corpus_ref.toString());
    tableEntry.add(layer);
    tableEntry.add(name);
    tableEntry.add(left.toString());
    tableEntry.add(right.toString());
    if (token_index == null) {
      tableEntry.add("NULL");
    } else {
      tableEntry.add(token_index.toString());
    }
    tableEntry.add(left_token.toString());
    tableEntry.add(right_token.toString());
    if (seg_index == null) {
      tableEntry.add("NULL");
    } else {
      tableEntry.add(seg_index.toString());
    }
    if (seg_name == null) {
      tableEntry.add("NULL");
    } else {
      tableEntry.add(idManager.getEscapedIdentifier(seg_name));
    }
    if (span == null) {
      tableEntry.add("NULL");
    } else {
      tableEntry.add(span);
    }

    tableEntry.add(isRoot ? "TRUE" : "FALSE");

    addTuple(OutputTable.NODE, tableEntry);

  }

// ======================== Mapping of SNodeAnnotations =======================
  protected void mapSNodeAnnotations(SNode node, Long node_ref, EList<SAnnotation> annotations) {
    for (SAnnotation annotation : annotations) {
      this.mapSNodeAnnotation(node, node_ref, annotation);
    }
  }

  protected void mapSNodeAnnotation(Long node_ref, String namespace, String name, String value) {

    EList<String> tableEntry = new BasicEList<>();
    tableEntry.add(node_ref.toString());
    tableEntry.add(idManager.getEscapedIdentifier(namespace));
    tableEntry.add(idManager.getEscapedIdentifier(name));
    tableEntry.add(value);

    addTuple(OutputTable.NODE_ANNOTATION, tableEntry);
  }

  protected void mapSNodeAnnotation(SNode node, Long node_ref, SAnnotation annotation) {
    String namespace = annotation.getNamespace();
    if (namespace != null) {
      if (namespace.equals("null")) {
        namespace = DEFAULT_NS;
      }
    } else {
      namespace = DEFAULT_NS;
    }

    if (annotation.getSValueType() == SDATATYPE.SURI) {
      // copy the linked file
      copyLinkedFile(annotation.getSValueSURI());
    }
    String name = annotation.getSName();
    String value = annotation.getSValueSTEXT();

    mapSNodeAnnotation(node_ref, namespace, name, value);

  }

  protected boolean isRoot(SNode n) {

    if (n == null) {
      return false;
    }

    EList<Edge> inEdges = documentGraph.getInEdges(n.getSId());
    if (inEdges != null) {
      for (Edge e : inEdges) {
        if (e instanceof SDominanceRelation
                || e instanceof SPointingRelation
                || e instanceof SSpanningRelation) {
          return false;
        }
      }
    }

    return true;
  }

  protected void copyLinkedFile(URI uri) {

    if (uri != null && parentMapper != null && parentMapper.getOutputDir() != null) {
      File outputDir = parentMapper.getOutputDir();
      File sourceFile;
      if(uri.toFileString() != null) {
        sourceFile = new File(uri.toFileString());
      } else {
        sourceFile = new File(uri.toString());
      }
      if (sourceFile.isFile()) {
        
        try {
          String mimeType = java.nio.file.Files.probeContentType(sourceFile.toPath());
          if(mimeType != null) {
            if(mimeType.startsWith("video/")) {
              idManager.getGlobal().setVideoFound();
            } else if(mimeType.startsWith("audio/")) {
              idManager.getGlobal().setAudioFound();
            } else if(mimeType.startsWith("application/pdf")) {
              idManager.getGlobal().setPDFFound();
            }
          }
          
        } catch (IOException ex) {
          log.error("Could not get mime type for file " + sourceFile.getAbsolutePath(), ex);
        }


        File extData = new File(outputDir, "ExtData");
        File docDir = new File(extData, documentGraph.getSDocument().getSName());
        if (!docDir.isDirectory()) {
          if (!docDir.mkdirs()) {
            log.error("Could not create directory " + docDir.getAbsolutePath());
          }
        }

        File targetFile = new File(docDir, sourceFile.getName());
        try {
          Files.copy(sourceFile, targetFile);
        } catch (IOException ex) {
          log.error("Could not copy file " + sourceFile.getAbsolutePath(), ex);
        }
      }
    }
  }

  protected SLayer getFirstComponentLayer(SNode node) {
    SLayer componentLayer = null;
    EList<SLayer> nodeLayer = node.getSLayers();
    if (nodeLayer != null) {
      // get layer name which comes lexically first
      TreeMap<String, SLayer> layers = new TreeMap<>();
      for (SLayer l : nodeLayer) {
        layers.put(l.getSName(), l);
      }
      if (!layers.isEmpty()) {
        componentLayer = layers.firstEntry().getValue();
      }
    }
    return componentLayer;
  }
  
  public DomStatistics getDomStats() {
    return parentMapper.getLocalDomStats();
  }
  
  public SpanStatistics getSpanStats() {
    return parentMapper.getLocalSpanStats();
  }
  
  public PointingStatistics getPointingStats() {
    return parentMapper.getLocalPointingStats();
  }
  
  public VirtualTokenStatistics getVirtualTokenStats() {
    return parentMapper.getLocalVirtualTokenStats();
  }

}
