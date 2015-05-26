/**
 * Copyright 2012 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.resolver.DomStatistics;
import com.google.common.base.Strings;
import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.resolver.OrderStatistics;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.resolver.PointingStatistics;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.resolver.SpanStatistics;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotatableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Salt2RelANNISMapper extends PepperMapperImpl implements SGraphTraverseHandler {

  private final AtomicInteger numberOfMappedNodes = new AtomicInteger();
  private int numberOfDocumentNodes;
  private File outputDir;
  private final DomStatistics localDomStats = new DomStatistics();
  private final SpanStatistics localSpanStats = new SpanStatistics();
  private final PointingStatistics localPointingStats = new PointingStatistics();
  private final OrderStatistics localOrderStats = new OrderStatistics();
  
  private DomStatistics globalDomStats;
  private SpanStatistics globalSpanStats;
  private PointingStatistics globalPointingStats;
  private OrderStatistics globalOrderStats;

  public Salt2RelANNISMapper() {
    this.init();
  }

  private void init() {
  }

  private final static Logger log = LoggerFactory.getLogger(Salt2RelANNISMapper.class);

  private IdManager idManager = null;

  public IdManager getIdManager() {
    return idManager;
  }

  public void setIdManager(IdManager idManager) {
    this.idManager = idManager;
  }

  public File getOutputDir() {
    return outputDir;
  }

  public void setOutputDir(File outputDir) {
    this.outputDir = outputDir;
  }

  /**
   * tuple writer to write {@link RelANNIS#FILE_TEXT} *
   */
  public TupleWriter tw_text = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_NODE} *
   */
  public TupleWriter tw_node = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_NODE_ANNO} *
   */
  public TupleWriter tw_nodeAnno = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_RANK} *
   */
  public TupleWriter tw_rank = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_EDGE_ANNO} *
   */
  public TupleWriter tw_edgeAnno = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_COMPONENT} *
   */
  public TupleWriter tw_component = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_CORPUS} *
   */
  public TupleWriter tw_corpus = null;
  /**
   * tuple writer to write {@link RelANNIS#FILE_CORPUS_META} *
   */
  public TupleWriter tw_corpusMeta = null;

  /**
   * tuple writer to write {@link RelANNIS#FILE_VISUALIZATION} *
   */
  public TupleWriter tw_visualization = null;

  /**
   * the individual name for the top-level corpus *
   */
  public String individualCorpusName = null;
  private Pair<String, String> individualCorpusNameReplacement = null;

  public boolean isTestMode = false;

// -------------------------start: SCorpusGraph 	
  private SCorpusGraph sCorpusGraph = null;

  /**
   * Sets the corpus graph to map from.
   *
   * @param sCorpusGraph corpus graph to map from
   */
  public void setSCorpusGraph(SCorpusGraph sCorpusGraph) {
    this.sCorpusGraph = sCorpusGraph;
  }

  /**
   * Returns the corpus graph to map from.
   *
   * @return corpus graph to map from
   */
  public SCorpusGraph getSCorpusGraph() {
    return sCorpusGraph;
  }
// -------------------------end: SCorpusGraph

  public enum TRAVERSION_TYPE {

    CORPUS_STRUCTURE,
    DOCUMENT_STRUCTURE_TOKEN,
    DOCUMENT_STRUCTURE_CR,
    DOCUMENT_STRUCTURE_DR,
    DOCUMENT_STRUCTURE_DR_SUB,
    DOCUMENT_STRUCTURE_PR,
    DOCUMENT_STRUCTURE_PR_SUB,
    DOCUMENT_STRUCTURE_AUDIO
  };
  /**
   * stores the current type of traversion
   */
  private TRAVERSION_TYPE currTraversionType = null;

// ================================================ start: mapping corpus structure	
  @Override
  public DOCUMENT_STATUS mapSCorpus() {
    //this.setSCorpusGraph(sCorpusGraph);
    if (this.getSCorpusGraph() == null) {
      throw new PepperModuleException(this, "Cannot map sCorpusGraph, because sCorpusGraph is null.");
    }

    {
			//start traversion of corpus structure
      /// initialize preOrder and postOrder:
      this.preorderTable = new ConcurrentHashMap<>();
      this.postorderTable = new ConcurrentHashMap<>();
      prePostOrder = 0l;

      try {
        EList<SNode> roots = this.getSCorpusGraph().getSRoots();
        if ((roots == null) || (roots.size() == 0)) {
          throw new PepperModuleException(this, "Cannot traverse through corpus structure, because there is no Corpus-object as root.");
        }

        if (roots.size() > 1) {
          throw new PepperModuleException(this, "Only one root corpus is allowed for relANNIS export");
        }

        // check if we are called for the root corpus
        if (getSCorpus() != null && roots.contains(getSCorpus())) {
          // set the SName of the corpus graph root to the individual one
          if (this.individualCorpusName != null) {
            this.individualCorpusNameReplacement = new ImmutablePair<>(roots.get(0).getSName(), this.individualCorpusName);
            //roots.get(0).setSName(this.individualCorpusName);
          }
          //set traversion type to corpus structure
          this.currTraversionType = TRAVERSION_TYPE.CORPUS_STRUCTURE;
          sCorpusGraph.traverse(roots, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "compute_corpus_structure", this);
        }
      } catch (Exception e) {
        throw new PepperModuleException(this, "Some error occurs while traversing corpus structure graph.", e);
      }
    }//start traversion of corpus structure
    return DOCUMENT_STATUS.COMPLETED;
  }

// ================================================ end: mapping corpus structure
// ================================================ end: mapping document structure
  /**
   * counter for pre and post order
   */
  private Long prePostOrder = null;
  private boolean mapRelationsInParallel = true;

  public void mapRelationsInParallel(boolean parallel) {
    this.mapRelationsInParallel = parallel;
  }

  /**
   * returns a new and unique ppOrder
   *
   * @return
   */
  private synchronized Long getNewPPOrder() {
    if (prePostOrder == null) {
      prePostOrder = 0l;
    }
    Long currPrePost = prePostOrder;
    prePostOrder++;
    return (currPrePost);
  }

  @Override
  public DOCUMENT_STATUS mapSDocument() {

    this.preorderTable = new ConcurrentHashMap<>();
    this.postorderTable = new ConcurrentHashMap<>();
    prePostOrder = 0l;

    numberOfMappedNodes.set(0);

    if (this.getSDocument() == null || this.getSDocument().getSDocumentGraph() == null) {
      throw new PepperModuleException(this, "Cannot map sDocumentGraph, because sDocumentGraph is null.");
    }

    {//start traversion of documentStructure

      try {

        if (this.getSDocument().getSDocumentGraph().getSNodes() != null) {
          this.numberOfDocumentNodes = this.getSDocument().getSDocumentGraph().getSNodes().size();
        }

        /**
         * traverse by SpanningRelations: DOCUMENT_STRUCTURE_CR
         * DominanceRelations: DOCUMENT_STRUCTURE_DR PointingRelations:
         * DOCUMENT_STRUCTURE_PR
         *
         * DominanceRelations Subcomponents: DOCUMENT_STRUCTURE_DR_SUB
         * PointingRelations Subcomponents: DOCUMENT_STRUCTURE_PR_SUB
         *
         * Dominance relations may consist of different subcomponents since
         * there are "edge" and "secedge" types
         *
         * Since every root node has it's own component, the pre and post order
         * needs to be 0 for the root node. You need to handle this.
         */
        EList<? extends SNode> sRelationRoots;
        Map<String, EList<SNode>> subComponentRoots;

        Map<SToken, Long> token2Index = calculateToken2Index(getSDocument().getSDocumentGraph());
        
        // START Step 1: map SOrderRelation
        subComponentRoots = this.getSDocument().getSDocumentGraph().getRootsBySRelationSType(STYPE_NAME.SORDER_RELATION);
        if (subComponentRoots != null) {
          if (subComponentRoots.size() > 0) {
            for (Map.Entry<String, EList<SNode>> entry : subComponentRoots.entrySet()) {
							//System.out.println("Count of SOrderRelation roots for key "+key+" : "+subComponentRoots.get(key).size());
              //System.out.println("Mapping SOrderRelations subcomponents with sType: "+key);
              SRelation2RelANNISMapper sOrderRelationMapper
                      = new SOrderRelation2RelANNISMapper(getIdManager(),
                              getSDocument().getSDocumentGraph(), token2Index,
                              tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                              this);

              sOrderRelationMapper.setTraversionSType(entry.getKey());
              sOrderRelationMapper.mapSRelations2RelANNIS(subComponentRoots.get(entry.getKey()), STYPE_NAME.SORDER_RELATION, null);

            }
          }
        }
				// END Step 1: map SOrderRelation

        // START Step 2: map SText
        if (idManager.hasVirtualTokenization()) {
          Long sDocID;
          Long textId = 0l;
          String sDocumentElementId = this.getSDocument().getSId();

          if (sDocumentElementId == null) {
            throw new PepperModuleException(this,
                    "SId Id of the document '" + this.getSDocument().getSName()
                    + "' is NULL!");
          }
          sDocID = this.idManager.getNewCorpusTabId(sDocumentElementId);
          String textName = "sText0";
          String textContent = Strings.repeat(" ", idManager.getNumberOfVirtualToken());
          ArrayList<String> tuple = new ArrayList<>();
          tuple.add(sDocID.toString());
          tuple.add(textId.toString());
          tuple.add(textName);
          tuple.add(textContent);

          long transactionId = tw_text.beginTA();
          try {
            tw_text.addTuple(transactionId, tuple);
            tw_text.commitTA(transactionId);

          } catch (FileNotFoundException e) {
            tw_text.abortTA(transactionId);
            throw new PepperModuleException(this,
                    "Could not write to the node.tab, exception was" + e.
                    getMessage());
          }
        } else {
          this.mapSText();
        }

        ExecutorService exec = null;
        if (mapRelationsInParallel) {
          exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }

        subComponentRoots = getSDocument().getSDocumentGraph().getRootsBySRelationSType(STYPE_NAME.SPOINTING_RELATION);
        if (subComponentRoots != null) {
          //System.out.println("The Pointing relation graphs have "+ subComponentRoots.size() + " STypes.");
          if (subComponentRoots.size() > 0) {

            for (String key : subComponentRoots.keySet()) {
					//System.out.println("Count of PR roots for key "+key+" : "+subComponentRoots.get(key).size());
              //System.out.println("Mapping PointingRelation subcomponents with sType: "+key);
              SRelation2RelANNISMapper sPointingSubRelationMapper
                      = new SPointingRelation2RelANNISMapper(getIdManager(),
                              getSDocument().getSDocumentGraph(), token2Index,
                              tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                              this
                      );
              sPointingSubRelationMapper.mapSRelations2RelANNIS(subComponentRoots.get(key), STYPE_NAME.SPOINTING_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR);
              sPointingSubRelationMapper.setTraversionSType(key);
              if (exec != null) {
                exec.execute(sPointingSubRelationMapper);
              } else {
                sPointingSubRelationMapper.run();
              }
            }
          } else {
            //System.out.println("No PointingRelation components found (null map)");
          }
        } else {
          //System.out.println("No PointingRelation components found (empty map)");
        }
		// END Step 2: map SPointingRelations

        // START Step 3: map SDominanceRelations
        sRelationRoots = this.getSDocument().getSDocumentGraph().getRootsBySRelation(STYPE_NAME.SDOMINANCE_RELATION);
        if (sRelationRoots != null) {
          if (sRelationRoots.size() > 0) {
            SRelation2RelANNISMapper sDominanceRelationMapper
                    = new SDominanceRelation2RelANNISMapper(getIdManager(),
                            getSDocument().getSDocumentGraph(), token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                            this
                    );
            sDominanceRelationMapper.mapSRelations2RelANNIS(sRelationRoots, STYPE_NAME.SDOMINANCE_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR);
            if (exec != null) {
              exec.execute(sDominanceRelationMapper);
            } else {
              sDominanceRelationMapper.run();
            }
          }
        }
		// END Step 3: map SDominanceRelations

        // START Step 3.1 : map the subComponents of the SDominanceRelations
        subComponentRoots = getSDocument().getSDocumentGraph().getRootsBySRelationSType(STYPE_NAME.SDOMINANCE_RELATION);
        if (subComponentRoots != null) {
          //System.out.println("The Dominance relation graphs have "+ subComponentRoots.size() + " STypes.");
          if (subComponentRoots.size() > 0) {
            for (String key : subComponentRoots.keySet()) {
              //System.out.println("Mapping DominanceRelation subcomponents with sType: "+key);

              SRelation2RelANNISMapper sDominanceSubRelationMapper
                      = new SDominanceRelation2RelANNISMapper(getIdManager(),
                              getSDocument().getSDocumentGraph(), token2Index,
                              tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
              sDominanceSubRelationMapper.setTraversionSType(key);
              sDominanceSubRelationMapper.mapSRelations2RelANNIS(subComponentRoots.get(key), STYPE_NAME.SDOMINANCE_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR);
              if (exec != null) {
                exec.execute(sDominanceSubRelationMapper);
              } else {
                sDominanceSubRelationMapper.run();
              }
            }
          } else {
            //System.out.println("No DominanceRelation subcomponents found (null map)");
          }
        } else {
          //System.out.println("No DominanceRelation subcomponents found (empty map)");
        }
		// END Step 3.1 : map the subComponents of the SDominanceRelations

        // START Step 4: map SSpanningrelations
        sRelationRoots = this.getSDocument().getSDocumentGraph().getRootsBySRelation(STYPE_NAME.SSPANNING_RELATION);
        if (sRelationRoots != null) {
          if (sRelationRoots.size() > 0) {
            SRelation2RelANNISMapper spanningRelationMapper
                    = new SSpanningRelation2RelANNISMapper(getIdManager(),
                            getSDocument().getSDocumentGraph(), token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
            spanningRelationMapper.mapSRelations2RelANNIS(sRelationRoots, STYPE_NAME.SSPANNING_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR);
            if (exec != null) {
              exec.execute(spanningRelationMapper);
            } else {
              spanningRelationMapper.run();
            }
          }
        }
				// END Step 4: map SSpanningrelations

        // START Step 5: map SAudioDSRelations
        sRelationRoots = this.getSDocument().getSDocumentGraph().getSTokens();
        if (sRelationRoots != null) {
          if (sRelationRoots.size() > 0) {
            SRelation2RelANNISMapper audioRelationMapper
                    = new Audio2RelANNISMapper(getIdManager(),
                            getSDocument().getSDocumentGraph(),
                            token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
            audioRelationMapper.mapSRelations2RelANNIS(sRelationRoots,
                    STYPE_NAME.STIME_OVERLAPPING_RELATION,
                    TRAVERSION_TYPE.DOCUMENT_STRUCTURE_AUDIO);
            if (exec != null) {
              exec.execute(audioRelationMapper);
            } else {
              audioRelationMapper.run();
            }
          }
        }
        // END Step 5: map SAudioDSRelations

        if (exec != null) {
          exec.shutdown();
          while (!exec.awaitTermination(60, TimeUnit.SECONDS)) {
            // wait to finish
          }
        }

        // START Step 6: map all SToken which were not mapped, yet
        SRelation2RelANNISMapper mapper
                = new SSpanningRelation2RelANNISMapper(getIdManager(),
                        getSDocument().getSDocumentGraph(), token2Index,
                        tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
        mapper.beginTransaction();
        for (SNode node : getSDocument().getSDocumentGraph().getSTokens()) {
          if (this.idManager.getVirtualisedSpanId(node.getSId()) == null) {
            mapper.mapSNode(node);
          }
        }
        mapper.commitTransaction();
				// END Step 6: map all SToken which were not mapped, yet

      } catch (PepperModuleException e) {
        throw new PepperModuleException(this, "Some error occurs while traversing document structure graph.", e);
      } catch (InterruptedException e) {
        throw new PepperModuleException(this, "Some error occurs while traversing document structure graph.", e);
      }
    }//start traversion of corpus structure

    mergeLocalStatsIntoGlobal();
    
    setProgress(1.0);
    return DOCUMENT_STATUS.COMPLETED;
  }
  
  private Map<SToken, Long> calculateToken2Index(SDocumentGraph documentGraph) {

    List<SToken> tokenSortedByLeft = documentGraph.getSortedSTokenByText();
    // calculate the index of each token
    Map<SToken, Long> token2Index = new HashMap<>();
    if (tokenSortedByLeft != null) {
      long i = 0;
      for (SToken tok : tokenSortedByLeft) {
        token2Index.put(tok, i);
        i++;
      }
    }
    return Collections.unmodifiableMap(token2Index);
  }
  
  private void mergeLocalStatsIntoGlobal() {
    if(globalDomStats != null) {
      globalDomStats.merge(localDomStats);
    }
    if(globalPointingStats != null) {
      globalPointingStats.merge(localPointingStats);
    }
    if(globalSpanStats != null) {
      globalSpanStats.merge(localSpanStats);
    }
    if(globalOrderStats != null) {
      globalOrderStats.merge(localOrderStats);
    }
  }

  /**
   * <pre> corpus_ref integer X foreign key to corpus.id id integer X restart
   * from 0 for every corpus_ref name text name of the text text text content
   * of the text </pre>
   */
  protected void mapSText() {
    //System.out.println("MAPPING TEXT ****************************************");
    SDocumentGraph sDoc = this.getSDocument().getSDocumentGraph();
    Long sDocID;
    Long textId = 0l;
    //System.out.println("Count of textual DS: "+sDoc.getSTextualDSs().size());
    for (STextualDS text : sDoc.getSTextualDSs()) {
      String sDocumentElementId = this.getSDocument().getSId();
			//System.out.println("Found text \""+text.getSText()+"\"");
      //System.out.println("The text has a length of "+text.getSText().length());
      //for (STextualRelation sTextRel : this.getSDocument().getSDocumentGraph().getSTextualRelations()){
      //	System.out.println("TextRelation: start "+sTextRel.getSStart()+" , end "+sTextRel.getSEnd());
      //}

      if (sDocumentElementId == null) {
        throw new PepperModuleException(this, "SElement Id of the document '" + sDoc.getSName() + "' is NULL!");
      }
      IdManager manager = getIdManager();
      if (manager == null) {
        throw new PepperModuleException(this, "No IdManager was found, this might be a bug.!");
      }
      sDocID = manager.getNewCorpusTabId(sDocumentElementId);
      String textName = text.getSName();
      String textContent = text.getSText();
      ArrayList<String> tuple = new ArrayList<>();
      tuple.add(sDocID.toString());
      tuple.add(manager.getNewTextId(text.getSId()).toString());
      tuple.add(textName);
      tuple.add(textContent);

      long transactionId = tw_text.beginTA();
      try {
        tw_text.addTuple(transactionId, tuple);
        tw_text.commitTA(transactionId);

      } catch (FileNotFoundException e) {
        tw_text.abortTA(transactionId);
        throw new PepperModuleException(this, "Could not write to the node.tab, exception was" + e.getMessage());
      }
      textId++;
    }
  }

// ================================================ end: mapping document structure
  /**
   * These tables contain the SNodes as key and the preorder/postorder as value.
   */
  private ConcurrentMap<SNode, Long> preorderTable;
  private ConcurrentMap<SNode, Long> postorderTable;

  /**
   * This method maps a SDocument or SCorpus to a corpus.tab entry and all meta
   * annotations to the corpus_annotations.tab.
   *
   * id integer X X primary key name text X X unique name (per corpus) type text
   * X CORPUS, DOCUMENT version text version number (not used) pre integer X pre
   * order of the corpus tree post integer X post order of the corpus tree
   *
   * @param sNode
   * @param id
   * @param preOrder
   * @param postOrder
   */
  private void mapToCorpusTab(SNode sNode, Long id, Long preOrder, Long postOrder) {
    TupleWriter corpusTabWriter = tw_corpus;
    String idString = id.toString();

    String name = sNode.getSName();
    if (preOrder == 0 && this.individualCorpusNameReplacement != null) {
      if (name.equals(this.individualCorpusNameReplacement.getLeft())) {
        name = this.individualCorpusNameReplacement.getRight();
      }
    }

    String type = null;
    String version = "NULL";
    String pre = preOrder.toString();
    String post = postOrder.toString();
    String toplevel = preOrder == 0 ? "TRUE" : "FALSE";
    if (sNode instanceof SDocument) {
      type = "DOCUMENT";
    } else {
      if (sNode instanceof SCorpus) {
        type = "CORPUS";
      }
    }
    // set corpus version
    SMetaAnnotation versionMetaAnno = sNode.getSMetaAnnotation("version");
    if (versionMetaAnno != null) {
      if (versionMetaAnno.getSValueSTEXT() != null) {
        version = versionMetaAnno.getSValueSTEXT();
      }
    }
    
    ArrayList<String> tuple = new ArrayList<>();
    tuple.add(idString);
    tuple.add(name);
    tuple.add(type);
    tuple.add(version);
    tuple.add(pre);
    tuple.add(post);
    tuple.add(toplevel);

    long transactionId = corpusTabWriter.beginTA();
    try {
      corpusTabWriter.addTuple(transactionId, tuple);
      corpusTabWriter.commitTA(transactionId);
    } catch (FileNotFoundException e) {
      corpusTabWriter.abortTA(transactionId);
      throw new PepperModuleException(this, "Could not write to the corpus.relannis, exception was" + e.getMessage());
    }

    if (tw_corpusMeta != null && sNode instanceof SMetaAnnotatableElement) {
      SMetaAnnotatableElement metaOwner = (SMetaAnnotatableElement) sNode;
      if (metaOwner.getSMetaAnnotations() != null) {
        transactionId = tw_corpusMeta.beginTA();
        try {

          for (SMetaAnnotation meta : metaOwner.getSMetaAnnotations()) {
            String namespace = meta.getSNS();
            if (namespace == null) {
              namespace = "NULL";
            }
            tw_corpusMeta.addTuple(transactionId,
                    Arrays.asList(idString, namespace, meta.getSName(), meta.getSValue().toString()));
          }
          tw_corpusMeta.commitTA(transactionId);
        } catch (FileNotFoundException ex) {
          tw_corpusMeta.abortTA(transactionId);
          throw new PepperModuleException(this,
                  "Could not write to the corpus_annotation.relannis, exception was" + ex.getMessage());
        }
      }
    }
  }

  // ==================================================== Traversion ===============================================
  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId,
          SRelation edge,
          SNode currNode,
          long order) {
    Boolean returnValue = false;
    if (this.currTraversionType == TRAVERSION_TYPE.CORPUS_STRUCTURE) {//traversing corpus structure	
      if ((currNode instanceof SCorpus)
              || (currNode instanceof SDocument)) {
        returnValue = true;
      }
    }//traversing corpus structure

    return returnValue;
  }

  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId,
          SNode currNode,
          SRelation edge,
          SNode fromNode,
          long order) {

    /**
     * First part: This traversal concerns the Corpus structure Save the
     * pre-order in the CorpusStructure table.
     */
    if (this.currTraversionType == TRAVERSION_TYPE.CORPUS_STRUCTURE) {//traversing corpus structure	
      if (currNode instanceof SCorpus || currNode instanceof SDocument) {

        this.idManager.getNewCorpusTabId(currNode.getSId());

        /**
         * Initialize a new entry for this node in the preorder table, if it was
         * not reached, yet.
         */
        if (!this.preorderTable.containsKey(currNode)) {
          this.preorderTable.put(currNode, getNewPPOrder());
        }
      }
    }//traversing corpus structure		
  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId,
          SNode currNode,
          SRelation edge,
          SNode fromNode,
          long order) {

    /**
     * We just left a node we had reached.
     */
    if (this.currTraversionType == TRAVERSION_TYPE.CORPUS_STRUCTURE) {//traversing corpus structure	

      /**
       * This node is a leaf. Set the postorder for it.
       *
       */
      if (currNode instanceof SDocument || currNode instanceof SCorpus) {
        if (this.preorderTable.containsKey(currNode)) {
          if (!this.postorderTable.containsKey(currNode)) {
            this.postorderTable.put(currNode, getNewPPOrder());
            Long iD = null;
            if (currNode instanceof SDocument) {
              SDocument sd = (SDocument) currNode;
              iD = getIdManager().getNewCorpusTabId(sd.getSId());
            }
            if (currNode instanceof SCorpus) {
              SCorpus sc = (SCorpus) currNode;
              iD = getIdManager().getNewCorpusTabId(sc.getSId());
            }

            // map the the node to the corpus tab
            this.mapToCorpusTab(currNode, iD, this.preorderTable.get(currNode), this.postorderTable.get(currNode));
          }
        }
      }
    }//traversing corpus structure

  }

  public void notifiyNewNodeMapped() {
    int newValue = numberOfMappedNodes.incrementAndGet();
    // only set status for each 100th node
    if (newValue % 100 == 0) {
      double percent = (double) (newValue) / (double) numberOfDocumentNodes;
      // stretch the value so that we report 90% when all nodes are done
      setProgress(percent * 0.9);
    }
  }

  public DomStatistics getLocalDomStats() {
    return localDomStats;
  }

  public SpanStatistics getLocalSpanStats() {
    return localSpanStats;
  }

  public PointingStatistics getLocalPointingStats() {
    return localPointingStats;
  }  

  public OrderStatistics getLocalOrderStats() {
    return localOrderStats;
  }
  
  public void setGlobalDomStats(DomStatistics globalDomStats) {
    this.globalDomStats = globalDomStats;
  }

  public void setGlobalSpanStats(SpanStatistics globalSpanStats) {
    this.globalSpanStats = globalSpanStats;
  }

  public void setGlobalPointingStats(PointingStatistics globalPointingStats) {
    this.globalPointingStats = globalPointingStats;
  }

  public OrderStatistics getGlobalOrderStats() {
    return globalOrderStats;
  }

  public void setGlobalOrderStats(OrderStatistics globalOrderStats) {
    this.globalOrderStats = globalOrderStats;
  }
  
  
  
}
