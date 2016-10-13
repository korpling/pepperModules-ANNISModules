/**
 * Copyright 2015 Humboldt-Universität zu Berlin
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
package org.corpus_tools.peppermodules.annis;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.tuple.Pair;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.peppermodules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import org.corpus_tools.peppermodules.annis.resolver.DomStatistics;
import org.corpus_tools.peppermodules.annis.resolver.PointingStatistics;
import org.corpus_tools.peppermodules.annis.resolver.SpanStatistics;
import org.corpus_tools.peppermodules.annis.resolver.VirtualTokenStatistics;
import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.SDATATYPE;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SDominanceRelation;
import org.corpus_tools.salt.common.SPointingRelation;
import org.corpus_tools.salt.common.SSpan;
import org.corpus_tools.salt.common.SSpanningRelation;
import org.corpus_tools.salt.common.SStructure;
import org.corpus_tools.salt.common.STextualRelation;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.GraphTraverseHandler;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SLayer;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.graph.Relation;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import java.util.Arrays;
import java.util.LinkedList;
import org.corpus_tools.salt.common.STextualDS;

public abstract class SRelation2ANNISMapper implements Runnable, GraphTraverseHandler {

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

  Collection<? extends SNode> sRelationRoots = null;
  SALT_TYPE edgeTypeName = null;
  
  
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
          String traversalId, SNode currNode, SRelation<SNode, SNode> sRelation,
          SNode fromNode, long order) {
		// A node was reached.
    // We've got the rank
    // fromNode -> relation -> currNode

    Long rankId;
    List<Long> virtualTokenIds = this.idManager.getVirtualisedTokenId(currNode.getId());

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

        List<String> rankEntry = new ArrayList<>();
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
          if (sRelation.getAnnotations() != null) {
            for (SAnnotation sAnnotation : sRelation.getAnnotations()) {
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
        currentNodeID = idManager.getVirtualisedSpanId(currNode.getId());
      } else {
        // map the target node
        currentNodeID = this.mapSNode(currNode);
      }
      
      if (currentNodeID != null) {
        // set the rank id
        rankId = idManager.getGlobal().getNewRankId();
        // It does not have a pre-order. Set it.
        this.preorderTable.put(currentNodeID, this.getNewPPOrder());

        this.rankTable.put(currentNodeID, rankId);

        if (sRelation != null) {
          if (sRelation.getAnnotations() != null) {
            for (SAnnotation sAnnotation : sRelation.getAnnotations()) {
              this.mapSAnnotation2ANNIS(rankId, sAnnotation);
            }
          }
        }
      } // end if currentNodeID not null
    }

    this.rankLevel += 1;
  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
          SNode currNode, SRelation relation, SNode fromNode, long order) {

		// We've got the rank
    // fromNode -> relation -> currNode
    Long currNodeID;
    if (virtualNodes.contains(currNode)) {
      currNodeID = idManager.getVirtualisedSpanId(currNode.getId());
    } else {
      currNodeID = idManager.getNodeId(currNode);
    }

    Long parentRank = null;
    if (fromNode != null) {
      Long fromNodeID;
      if (virtualNodes.contains(fromNode)) {
        fromNodeID = idManager.getVirtualisedSpanId(fromNode.getId());
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

      this.mapRank2ANNIS(relation, currNodeID, rankId, pre, post, parentRank, rankLevel);
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
          String traversalId, SRelation relation, SNode currNode, long order) {
    boolean returnVal = false;

    return returnVal;
  }

// =============================== Mapping of SRelations ======================
  public abstract void mapSRelations2ANNIS(Collection<? extends SNode> sRelationRoots, SALT_TYPE edgeTypeName, TRAVERSION_TYPE traversionType);

  /**
   * This method maps the currently processed component to the ANNIS
   * component tab file.
   */
  protected void mapComponent2ANNIS() {
		// id		type	layer					name
    // unique	c/d/p	SLayer/default_layer	"NULL"/SType
    List<String> componentEntry = new ArrayList<>();
    componentEntry.add(currentComponentId.toString());
    componentEntry.add(currentComponentType);
    componentEntry.add(idManager.getEscapedIdentifier(currentComponentLayer));
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
     * level Thus, we create an List of SRelations and the index is the rankId
     *
     * @TODO: do we REALLY need this?
     */
    List<String> rankEntry = new ArrayList<>();

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

    List<String> edgeAnnotationEntry = new ArrayList<>();
    edgeAnnotationEntry.add(rankId.toString());
    String ns;
    if (sAnnotation.getNamespace() != null) {
      ns = sAnnotation.getNamespace();
    } else {
      ns = "default_ns";
    }
    edgeAnnotationEntry.add(idManager.getEscapedIdentifier(ns));
    edgeAnnotationEntry.add(idManager.getEscapedIdentifier(sAnnotation.getName()));
    edgeAnnotationEntry.add(sAnnotation.getValue_STEXT());

    addTuple(OutputTable.EDGE_ANNO, edgeAnnotationEntry);

  }

// =============================== Data for Node Mapping ======================
  protected final Map<SToken, Long> token2Index;

// ================================= Mapping of SNodes ========================
  public Long mapSNode(SNode sNode) {
    SegmentationInfo segInfo = this.idManager.getSegmentInformation(sNode.getId());
    if (segInfo != null) {
      return this.mapSNode(sNode, segInfo.getANNISId(), segInfo.getSegmentationName(), segInfo.getSpan());
    } else {
      return this.mapSNode(sNode, null, null, null);
    }
  }

  private Long mapSNode(SNode node, Long seg_index, String seg_name, String span) {
    /// get the Identifier of the node since we will need it many times
    String nodeIdentifier = node.getId();

    /// if the node already has a nodeId, it was already mapped
    Pair<Long, Boolean> idPair = idManager.getNewNodeId(nodeIdentifier);
    if (idPair.getRight() == false) {
      // the node is not new
      return idPair.getLeft();
    }
    Long virtualSpanID = idManager.getVirtualisedSpanId(nodeIdentifier);
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
    Long corpus_ref = this.idManager.getNewCorpusTabId(this.documentGraph.getDocument().getId());
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
      if (node.getLayers() != null) {
        if (!node.getLayers().isEmpty()) {
          layer = node.getLayers().iterator().next().getName();
        }
      }
    }// set the layer to the actual value

    if (node instanceof SToken) {      
      // set the token index
      token_index = this.token2Index.get((SToken) node);
      left_token = token_index;
      right_token = token_index;

      // set the left and right value and the text_ref
      List<SRelation<SNode, SNode>> outRelations = documentGraph.getOutRelations(node.getId());
      if (outRelations == null) {
        throw new PepperModuleException("The token " + node.getId() + " has no outgoing relations!");
      }
      /// find the STextualRelation
      for (Relation relation : outRelations) {
        // get the relation which is of the type STextual relation
        if (relation instanceof STextualRelation) {
          STextualRelation sTextualRelation = ((STextualRelation) relation);
          // set the left value
          left = new Long(sTextualRelation.getStart());
          // set the right value
          /* 
          Note to future me: 
          While "left" is inclusive "right" is exclusive. This does not only
          hold for Salt but also for (rel)ANNIS. The implementation always
          assumed this at certain points (e.g. when extracting spans for matrix queries)
          but the documentation did state otherwise for a certain time.
          Also the legacy ANNIS exporter used this semantics.
          Using an exclusive end is important because otherwise the empty string can't be represented.
          */
          right = new Long(sTextualRelation.getEnd());
          // set the reference to the text
          text_ref = idManager.getNewTextId(sTextualRelation.getTarget().getId());
          // set the overlapped text
          span = sTextualRelation.getTarget().getText().substring(left.intValue(), sTextualRelation.getEnd());
          
          getVirtualTokenStats().checkRealToken(span);
          break;
        }
      }
    } else {
      if (node instanceof SSpan || node instanceof SStructure) {
        // get the overlapping token
        List<SToken> overlappedToken;
        if (node instanceof SStructure) {
          overlappedToken = this.documentGraph.getOverlappedTokens(node, SALT_TYPE.SSPANNING_RELATION, 
                  SALT_TYPE.SDOMINANCE_RELATION);
        } else {
          overlappedToken = this.documentGraph.getOverlappedTokens(node, SALT_TYPE.SSPANNING_RELATION);

        }
        if (overlappedToken.isEmpty()){
          log.warn("Node {} is not connected to any token. This is invalid for ANNIS and the node will be excluded.", node.getId());
          return null;
        }
        
        if(this.idManager.hasVirtualTokenization()) {
          
          List<Long> overlappedVirtualTokenIDs = new LinkedList<>();
          for(SToken tok : overlappedToken) {
            List<Long> tmp = this.idManager.getVirtualisedTokenId(tok.getId());
            if(tmp != null) {
              overlappedVirtualTokenIDs.addAll(tmp);
            }
          }
          if(overlappedVirtualTokenIDs.isEmpty()) {
            log.warn("Node {} is not connected to any virtual token. This is invalid for ANNIS and the node will be excluded.", node.getId());
            return null;
          }
          
          Long[] overlappedTokenIndexes = this.idManager.getMinimalVirtTokenIndex(
                  overlappedVirtualTokenIDs.toArray(new Long[overlappedVirtualTokenIDs.size()]));
          Arrays.sort(overlappedTokenIndexes);
          
          left_token = overlappedTokenIndexes[0];
          right_token = overlappedTokenIndexes[overlappedTokenIndexes.length-1];

          // the token index is the same as the character for the virtual 
          // tokenization since each virtual token has exactly one character (space)
          left = left_token;
          right = right_token;

          span = null;
          // always the same for virtual tokenization
          text_ref = 0l;

        } else {
        
          // get the textual datasource the node is connected to and check there is only one
          STextualDS textualDataSource = null;
          for(SToken t : overlappedToken) {
            for(SRelation rel : t.getOutRelations()) {
              if(rel instanceof STextualRelation) {
                STextualRelation textRel = (STextualRelation) rel;
                if(textualDataSource == null) {
                  textualDataSource = textRel.getTarget();
                } else if(textualDataSource != textRel.getTarget()) {
                  log.warn("Node {} is connected to more than one textual data source. This is invalid for ANNIS and the node will be excluded.", 
                          node.getId());
                  return null;
                }
              }
            }
          }
          if(textualDataSource == null) {
            log.warn("Node {} is connected to no textual data source. This is invalid for ANNIS and the node will be excluded.", 
                          node.getId());
            return null;
          }
          text_ref = idManager.getNewTextId(textualDataSource.getId());

          // sort the token by left
          List<SToken> sortedOverlappedToken = this.documentGraph.getSortedTokenByText(overlappedToken);

          SToken firstOverlappedToken = sortedOverlappedToken.get(0);
          SToken lastOverlappedToken = sortedOverlappedToken.get(sortedOverlappedToken.size() - 1);

          // set left_token
          left_token = (long) this.token2Index.get(firstOverlappedToken);
          // set right_token
          right_token = (long) this.token2Index.get(lastOverlappedToken);

          // get first and last overlapped character
          List<SRelation<SNode, SNode>> firstTokenOutRelations = documentGraph.getOutRelations(firstOverlappedToken.getId());
          if (firstTokenOutRelations == null) {
            log.warn("The token {} has no outgoing relations. Node {} will be excluded.!", firstOverlappedToken.getId(),
                    node.getId());
            return null;
          }

          /// find the STextualRelation
          for (Relation relation : firstTokenOutRelations) {
            // get the relation which is of the type STextual relation
            if (relation instanceof STextualRelation) {
              STextualRelation sTextualRelation = ((STextualRelation) relation);
              // set the left value
              left = new Long(sTextualRelation.getStart());
              break;
            }
          }

          List<SRelation<SNode, SNode>> lastTokenOutRelations = documentGraph.getOutRelations(lastOverlappedToken.getId());
          if (lastTokenOutRelations == null) {
            throw new PepperModuleException("The token " + lastOverlappedToken.getId() + " has no outgoing relations!");
          }

          /// find the STextualRelation
          for (Relation relation : lastTokenOutRelations) {
            // get the relation which is of the type STextual relation
            if (relation instanceof STextualRelation) {
              STextualRelation sTextualRelation = ((STextualRelation) relation);
              // set the left value
              right = new Long(sTextualRelation.getEnd());
              break;
            }
          }

        } // end if no virtual tokenization
      } else {
        // IGNORE CASE
      }
      // the node is a span or structure
    }
    

    this.writeNodeTabEntry(id, text_ref, corpus_ref, layer, name, left, right,
            token_index, left_token, right_token, seg_index, seg_name, span,
            isRoot(node));

    if (node.getAnnotations() != null) {
      mapSNodeAnnotations(node, id, node.getAnnotations());
    }

    // report progress
    parentMapper.notifiyNewNodeMapped();

    return id;
  }
  
  private String getUniqueNodeName(SNode node) {
    URI nodeID = node.getPath();
    return nodeID.fragment();
  }

  protected void writeNodeTabEntry(
          Long id, Long text_ref, Long corpus_ref,
          String layer, String name,
          Long left, Long right, Long token_index,
          Long left_token, Long right_token,
          Long seg_index, String seg_name, String span,
          boolean isRoot) {

    Preconditions.checkArgument(left_token <= right_token, "Left-most covered token index (" 
            + left_token + ") must be less or equal to the right-most covered token index (" 
            + right_token + "), node " + name + " corpus " + corpus_ref);
    Preconditions.checkArgument(left <= right, "Left-most covered character index (" 
            + left + ") must be less or equal to the right-most covered character index (" 
            + right + "), node " +  name + " corpus " + corpus_ref );
    
    List<String> tableEntry = new ArrayList<>();
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
  protected void mapSNodeAnnotations(SNode node, Long node_ref, Set<SAnnotation> annotations) {
    for (SAnnotation annotation : annotations) {
      this.mapSNodeAnnotation(node, node_ref, annotation);
    }
  }

  protected void mapSNodeAnnotation(Long node_ref, String namespace, String name, String value) {

    List<String> tableEntry = new ArrayList<>();
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

    if (annotation.getValueType() == SDATATYPE.SURI) {
      // copy the linked file
      copyLinkedFile(annotation.getValue_SURI());
    }
    String name = annotation.getName();
    String value = annotation.getValue_STEXT();

    mapSNodeAnnotation(node_ref, namespace, name, value);

  }

  protected boolean isRoot(SNode n) {

    if (n == null) {
      return false;
    }

    List<SRelation<SNode, SNode>> inRelations = documentGraph.getInRelations(n.getId());
    if (inRelations != null) {
      for (Relation e : inRelations) {
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
        File docDir = new File(extData, documentGraph.getDocument().getName());
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
    Set<SLayer> nodeLayer = node.getLayers();
    if (nodeLayer != null) {
      // get layer name which comes lexically first
      TreeMap<String, SLayer> layers = new TreeMap<>();
      for (SLayer l : nodeLayer) {
        layers.put(l.getName(), l);
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
