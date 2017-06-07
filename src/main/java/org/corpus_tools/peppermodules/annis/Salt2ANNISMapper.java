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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.peppermodules.annis.resolver.DomStatistics;
import org.corpus_tools.peppermodules.annis.resolver.PointingStatistics;
import org.corpus_tools.peppermodules.annis.resolver.SpanStatistics;
import org.corpus_tools.peppermodules.annis.resolver.VirtualTokenStatistics;
import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.GraphTraverseHandler;
import org.corpus_tools.salt.core.SAnnotationContainer;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SMetaAnnotation;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Multimap;

import org.corpus_tools.salt.util.SaltUtil;

public class Salt2ANNISMapper extends PepperMapperImpl implements GraphTraverseHandler {

  private final AtomicInteger numberOfMappedNodes = new AtomicInteger();
  private int numberOfDocumentNodes;
  private File outputDir;
  private final DomStatistics localDomStats = new DomStatistics();
  private final SpanStatistics localSpanStats = new SpanStatistics();
  private final PointingStatistics localPointingStats = new PointingStatistics();
  private final VirtualTokenStatistics localVirtualTokenStats = new VirtualTokenStatistics();
  
  private DomStatistics globalDomStats;
  private SpanStatistics globalSpanStats;
  private PointingStatistics globalPointingStats;
  private VirtualTokenStatistics globalVirtualTokenStats;

  public Salt2ANNISMapper() {
    this.init();
   
  }

  private void init() {
  }

  private final static Logger logger = LoggerFactory.getLogger(Salt2ANNISMapper.class);

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
   * tuple writer to write {@link ANNIS#FILE_TEXT} *
   */
  public TupleWriter tw_text = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_NODE} *
   */
  public TupleWriter tw_node = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_NODE_ANNO} *
   */
  public TupleWriter tw_nodeAnno = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_RANK} *
   */
  public TupleWriter tw_rank = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_EDGE_ANNO} *
   */
  public TupleWriter tw_edgeAnno = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_COMPONENT} *
   */
  public TupleWriter tw_component = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_CORPUS} *
   */
  public TupleWriter tw_corpus = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_CORPUS_META} *
   */
  public TupleWriter tw_corpusMeta = null;

  /**
   * tuple writer to write {@link ANNIS#FILE_VISUALIZATION} *
   */
  public TupleWriter tw_visualization = null;

  /**
   * the individual name for the top-level corpus *
   */
  public String individualCorpusName = null;
  private Pair<String, String> individualCorpusNameReplacement = null;
  
  public boolean isTestMode = false;
  
  private boolean mergeTextsWithTimeline = true;

// -------------------------start: SCorpusGraph 	
  private SCorpusGraph sCorpusGraph = null;

  /**
   * Sets the corpus graph to map from.
   *
   * @param sCorpusGraph corpus graph to map from
   */
  public void setCorpusGraph(SCorpusGraph sCorpusGraph) {
    this.sCorpusGraph = sCorpusGraph;
  }

  /**
   * Returns the corpus graph to map from.
   *
   * @return corpus graph to map from
   */
  public SCorpusGraph getCorpusGraph() {
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
    //this.setCorpusGraph(sCorpusGraph);
    if (this.getCorpusGraph() == null) {
      throw new PepperModuleException(this, "Cannot map sCorpusGraph, because sCorpusGraph is null.");
    }

    {
			//start traversion of corpus structure
      /// initialize preOrder and postOrder:
      this.preorderTable = new ConcurrentHashMap<>();
      this.postorderTable = new ConcurrentHashMap<>();
      prePostOrder = 0l;

      try {
        List<SNode> roots = this.getCorpusGraph().getRoots();
        if ((roots == null) || (roots.size() == 0)) {
          throw new PepperModuleException(this, "Cannot traverse through corpus structure, because there is no Corpus-object as root.");
        }

        if (roots.size() > 1) {
//          throw new PepperModuleException(this, "Only one root corpus is allowed for ANNIS export");
        	logger.warn("Only one root corpus is allowed for ANNIS export");
        }

        // check if we are called for the root corpus
        if (getCorpus() != null && roots.contains(getCorpus())) {
          // set the SName of the corpus graph root to the individual one
          if (this.individualCorpusName != null) {
            this.individualCorpusNameReplacement = new ImmutablePair<>(roots.get(0).getName(), this.individualCorpusName);
            //roots.get(0).setName(this.individualCorpusName);
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

    if (this.getDocument() == null || this.getDocument().getDocumentGraph() == null) {
      throw new PepperModuleException(this, "Cannot map sDocumentGraph, because sDocumentGraph is null.");
    }

    {//start traversion of documentStructure

      try {

        if (this.getDocument().getDocumentGraph().getNodes() != null) {
          this.numberOfDocumentNodes = this.getDocument().getDocumentGraph().getNodes().size();
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
        List<? extends SNode> sRelationRoots;
        Multimap<String, SNode>subComponentRoots;
//        Map<String, List<SNode>> subComponentRoots;

        Map<SToken, Long> token2Index = calculateToken2Index(getDocument().getDocumentGraph());
        
        // START Step 1: map SOrderRelation
        subComponentRoots = this.getDocument().getDocumentGraph().getRootsByRelationType(SALT_TYPE.SORDER_RELATION);
        if (subComponentRoots != null) {
          if (subComponentRoots.size() > 0) {
        	  for (Entry<String, SNode> entry : subComponentRoots.entries()) {
              SRelation2ANNISMapper sOrderRelationMapper
                      = new SOrderRelation2ANNISMapper(getIdManager(),
                              getDocument().getDocumentGraph(), token2Index,
                              tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                              this);
              
              String traversionType = entry.getKey();
              if(SaltUtil.SALT_NULL_VALUE.equals(traversionType)) {
                sOrderRelationMapper.setTraversionSType(null);
              } else {
                sOrderRelationMapper.setTraversionSType(traversionType);
              }
              
              sOrderRelationMapper.mapSRelations2ANNIS(subComponentRoots.get(entry.getKey()), SALT_TYPE.SORDER_RELATION, null);

            }
          }
        }
				// END Step 1: map SOrderRelation
        
        // also map the timeline (by creating a virtual tokenization if necessary)
        STimelineRelation2ANNISMapper timelineMapper
                    = new STimelineRelation2ANNISMapper(getIdManager(),
                            getDocument().getDocumentGraph(), token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                            this, mergeTextsWithTimeline);
        timelineMapper.run();

        // START Step 2: map SText
        if (idManager.hasVirtualTokenization()) {
          Long sDocID;
          Long textId = 0l;
          String sDocumentElementId = this.getDocument().getId();

          if (sDocumentElementId == null) {
            throw new PepperModuleException(this,
                    "SId Id of the document '" + this.getDocument().getName()
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

        subComponentRoots = getDocument().getDocumentGraph().getRootsByRelationType(SALT_TYPE.SPOINTING_RELATION);
        if (subComponentRoots != null) {
          //System.out.println("The Pointing relation graphs have "+ subComponentRoots.size() + " STypes.");
          if (subComponentRoots.size() > 0) {

            for (String key : subComponentRoots.keySet()) {
					//System.out.println("Count of PR roots for key "+key+" : "+subComponentRoots.get(key).size());
              //System.out.println("Mapping PointingRelation subcomponents with sType: "+key);
              SRelation2ANNISMapper sPointingSubRelationMapper
                      = new SPointingRelation2ANNISMapper(getIdManager(),
                              getDocument().getDocumentGraph(), token2Index,
                              tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                              this
                      );
              sPointingSubRelationMapper.mapSRelations2ANNIS(subComponentRoots.get(key), SALT_TYPE.SPOINTING_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR);
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
        sRelationRoots = this.getDocument().getDocumentGraph().getRootsByRelation(SALT_TYPE.SDOMINANCE_RELATION);
        if (sRelationRoots != null) {
          if (sRelationRoots.size() > 0) {
            SRelation2ANNISMapper sDominanceRelationMapper
                    = new SDominanceRelation2ANNISMapper(getIdManager(),
                            getDocument().getDocumentGraph(), token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component,
                            this
                    );
            sDominanceRelationMapper.mapSRelations2ANNIS(sRelationRoots, SALT_TYPE.SDOMINANCE_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR);
            if (exec != null) {
              exec.execute(sDominanceRelationMapper);
            } else {
              sDominanceRelationMapper.run();
            }
          }
        }
		// END Step 3: map SDominanceRelations

        // START Step 3.1 : map the subComponents of the SDominanceRelations
        subComponentRoots = getDocument().getDocumentGraph().getRootsByRelationType(SALT_TYPE.SDOMINANCE_RELATION);
        if (subComponentRoots != null) {
          //System.out.println("The Dominance relation graphs have "+ subComponentRoots.size() + " STypes.");
          if (subComponentRoots.size() > 0) {
            
            Set<String> domComponentTypeNames = subComponentRoots.keySet();
        
            // only output the named relation types if there the user has not choosen
            // to include them or if there are more than 1 named types
            if(!((ANNISExporterProperties) this.getProperties()).getExcludeSingleDomType()
                    || domComponentTypeNames.size() >= 2)
            {
              for (String key : domComponentTypeNames) {
                
                if(!SaltUtil.SALT_NULL_VALUE.equals(key)) {
                
                  SRelation2ANNISMapper sDominanceSubRelationMapper
                          = new SDominanceRelation2ANNISMapper(getIdManager(),
                                  getDocument().getDocumentGraph(), token2Index,
                                  tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
                  sDominanceSubRelationMapper.setTraversionSType(key);
                  sDominanceSubRelationMapper.mapSRelations2ANNIS(subComponentRoots.get(key), SALT_TYPE.SDOMINANCE_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR);
                  if (exec != null) {
                    exec.execute(sDominanceSubRelationMapper);
                  } else {
                    sDominanceSubRelationMapper.run();
                  }
                }
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
        sRelationRoots = this.getDocument().getDocumentGraph().getRootsByRelation(SALT_TYPE.SSPANNING_RELATION);
        if (sRelationRoots != null) {
          if (sRelationRoots.size() > 0) {
            SRelation2ANNISMapper spanningRelationMapper
                    = new SSpanningRelation2ANNISMapper(getIdManager(),
                            getDocument().getDocumentGraph(), token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
            spanningRelationMapper.mapSRelations2ANNIS(sRelationRoots, SALT_TYPE.SSPANNING_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR);
            if (exec != null) {
              exec.execute(spanningRelationMapper);
            } else {
              spanningRelationMapper.run();
            }
          }
        }
				// END Step 4: map SSpanningrelations

        // START Step 5: map SMedialRelations
        sRelationRoots = this.getDocument().getDocumentGraph().getTokens();
        if (sRelationRoots != null) {
          if (sRelationRoots.size() > 0) {
            SRelation2ANNISMapper audioRelationMapper
                    = new Audio2ANNISMapper(getIdManager(),
                            getDocument().getDocumentGraph(),
                            token2Index,
                            tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
            audioRelationMapper.mapSRelations2ANNIS(sRelationRoots,
                    SALT_TYPE.STIME_OVERLAPPING_RELATION,
                    TRAVERSION_TYPE.DOCUMENT_STRUCTURE_AUDIO);
            if (exec != null) {
              exec.execute(audioRelationMapper);
            } else {
              audioRelationMapper.run();
            }
          }
        }
        // END Step 5: map SMedialRelations

        if (exec != null) {
          exec.shutdown();
          while (!exec.awaitTermination(60, TimeUnit.SECONDS)) {
            // wait to finish
          }
        }

        // START Step 6: map all SToken which were not mapped, yet
        SRelation2ANNISMapper mapper
                = new SSpanningRelation2ANNISMapper(getIdManager(),
                        getDocument().getDocumentGraph(), token2Index,
                        tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component, this);
        mapper.beginTransaction();
        for (SNode node : getDocument().getDocumentGraph().getTokens()) {
          if (this.idManager.getVirtualisedSpanId(node.getId()) == null) {
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

    List<SToken> tokenSortedByLeft = documentGraph.getSortedTokenByText();
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
    if(globalVirtualTokenStats != null) {
      globalVirtualTokenStats.merge(localVirtualTokenStats);
    }
  }

  /**
   * <pre> corpus_ref integer X foreign key to corpus.id id integer X restart
   * from 0 for every corpus_ref name text name of the text text text content
   * of the text </pre>
   */
  protected void mapSText() {
    //System.out.println("MAPPING TEXT ****************************************");
    SDocumentGraph sDoc = this.getDocument().getDocumentGraph();
    Long sDocID;
    Long textId = 0l;
    //System.out.println("Count of textual DS: "+sDoc.getTextualDSs().size());
    for (STextualDS text : sDoc.getTextualDSs()) {
      String sDocumentElementId = this.getDocument().getId();
			//System.out.println("Found text \""+text.getText()+"\"");
      //System.out.println("The text has a length of "+text.getText().length());
      //for (STextualRelation sTextRel : this.getDocument().getDocumentGraph().getTextualRelations()){
      //	System.out.println("TextRelation: start "+sTextRel.getStart()+" , end "+sTextRel.getEnd());
      //}

      if (sDocumentElementId == null) {
        throw new PepperModuleException(this, "SElement Id of the document '" + sDoc.getName() + "' is NULL!");
      }
      IdManager manager = getIdManager();
      if (manager == null) {
        throw new PepperModuleException(this, "No IdManager was found, this might be a bug.!");
      }
      sDocID = manager.getNewCorpusTabId(sDocumentElementId);
      String textName = text.getName();
      String textContent = text.getText();
      ArrayList<String> tuple = new ArrayList<>();
      tuple.add(sDocID.toString());
      tuple.add(manager.getNewTextId(text.getId()).toString());
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

    CorpusType corpusType = CorpusType.createFromNode(sNode);
    
    String name = sNode.getName();
    if(name == null) {
      name = "untitled";
    }
    if (corpusType == CorpusType.DOCUMENT) {
      // make sure the document name is globally unique (not just for a sub-corpus)
      name = idManager.getUniqueDocumentName(name);
    } else if (corpusType== CorpusType.TOPLEVEL && this.individualCorpusNameReplacement != null) {
      if (name.equals(this.individualCorpusNameReplacement.getLeft())) {
        name = this.individualCorpusNameReplacement.getRight();
      }
    }

    String type = corpusType.getCorpusTabType();
    String version = "NULL";
    String pre = preOrder.toString();
    String post = postOrder.toString();
    String toplevel = corpusType == CorpusType.TOPLEVEL ? "TRUE" : "FALSE";

    // set corpus version
    SMetaAnnotation versionMetaAnno = sNode.getMetaAnnotation("version");
    if (versionMetaAnno != null) {
      if (versionMetaAnno.getValue_STEXT() != null) {
        version = versionMetaAnno.getValue_STEXT();
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
      throw new PepperModuleException(this, "Could not write to the " 
              + ANNIS.FILE_CORPUS + ", exception was" + e.getMessage());
    }

    if (tw_corpusMeta != null) {
      SAnnotationContainer metaOwner = (SAnnotationContainer) sNode;
      if (metaOwner.getMetaAnnotations() != null) {
        transactionId = tw_corpusMeta.beginTA();
        try {

          for (SMetaAnnotation meta : metaOwner.getMetaAnnotations()) {
            String namespace = meta.getNamespace();
            if (namespace == null) {
              namespace = "NULL";
            }
            tw_corpusMeta.addTuple(transactionId,
                    Arrays.asList(idString, 
                            idManager.getEscapedIdentifier(namespace), 
                            idManager.getEscapedIdentifier(meta.getName()), 
                            (meta.getValue()== null)?"NULL":meta.getValue_STEXT()));
          }
          tw_corpusMeta.commitTA(transactionId);
        } catch (FileNotFoundException ex) {
          tw_corpusMeta.abortTA(transactionId);
          throw new PepperModuleException(this,
                  "Could not write to the " + ANNIS.FILE_CORPUS_META + ", exception was" + ex.getMessage());
        }
      }
    }
  }

  // ==================================================== Traversion ===============================================
  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId,
          SRelation relation,
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
          SRelation relation,
          SNode fromNode,
          long order) {

    /**
     * First part: This traversal concerns the Corpus structure Save the
     * pre-order in the CorpusStructure table.
     */
    if (this.currTraversionType == TRAVERSION_TYPE.CORPUS_STRUCTURE) {//traversing corpus structure	
      if (currNode instanceof SCorpus || currNode instanceof SDocument) {

        this.idManager.getNewCorpusTabId(currNode.getId());

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
          SRelation relation,
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
              iD = getIdManager().getNewCorpusTabId(sd.getId());
            }
            if (currNode instanceof SCorpus) {
              SCorpus sc = (SCorpus) currNode;
              iD = getIdManager().getNewCorpusTabId(sc.getId());
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

  public VirtualTokenStatistics getLocalVirtualTokenStats() {
    return localVirtualTokenStats;
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

  public VirtualTokenStatistics getGlobalVirtualTokenStats() {
    return globalVirtualTokenStats;
  }

  public void setGlobalVirtualTokenStats(VirtualTokenStatistics globalVirtualTokenStats) {
    this.globalVirtualTokenStats = globalVirtualTokenStats;
  }

  public void setMergeTextsWithTimeline(boolean mergeTextsWithTimeline) {
    this.mergeTextsWithTimeline = mergeTextsWithTimeline;
  }
}
