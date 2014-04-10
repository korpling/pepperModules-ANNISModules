/**
 * Copyright 2012 Humboldt University of Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.common.DOCUMENT_STATUS;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SProcessingAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class Salt2RelANNISMapper extends PepperMapperImpl implements SGraphTraverseHandler
{
	public Salt2RelANNISMapper()
	{
		this.init();
	}
	
	private void init()
	{
		
	}

	private IdManager idManager= null;
	public IdManager getIdManager() {
		return idManager;
	}

	public void setIdManager(IdManager idManager) {
		this.idManager = idManager;
	}
	/** tuple writer to write {@link RelANNIS#FILE_TEXT} **/
	public TupleWriter tw_text= null;
	/** tuple writer to write {@link RelANNIS#FILE_NODE} **/
	public TupleWriter tw_node= null;
	/** tuple writer to write {@link RelANNIS#FILE_NODE_ANNO} **/
	public TupleWriter tw_nodeAnno= null;
	/** tuple writer to write {@link RelANNIS#FILE_RANK} **/
	public TupleWriter tw_rank= null;
	/** tuple writer to write {@link RelANNIS#FILE_EDGE_ANNO} **/
	public TupleWriter tw_edgeAnno= null;
	/** tuple writer to write {@link RelANNIS#FILE_COMPONENT} **/
	public TupleWriter tw_component= null;
	/** tuple writer to write {@link RelANNIS#FILE_CORPUS} **/
	public TupleWriter tw_corpus= null;
	/** tuple writer to write {@link RelANNIS#FILE_CORPUS_META} **/
	public TupleWriter tw_corpusMeta= null;
	
	/** the individual name for the top-level corpus **/
	public String individualCorpusName= null;
	private Pair<String,String> individualCorpusNameReplacement=null;
	
	public boolean isTestMode = false;
	
// -------------------------start: SCorpusGraph 	
	private SCorpusGraph sCorpusGraph= null;
	
	/**
	 * Sets the corpus graph to map from.
	 * @param sCorpusGraph corpus graph to map from
	 */
	public void setSCorpusGraph(SCorpusGraph sCorpusGraph) {
		this.sCorpusGraph = sCorpusGraph;
	}

	/**
	 * Returns the corpus graph to map from.
	 * @return corpus graph to map from 
	 */
	public SCorpusGraph getSCorpusGraph() {
		return sCorpusGraph;
	}
// -------------------------end: SCorpusGraph
	enum TRAVERSION_TYPE {	CORPUS_STRUCTURE, 
							DOCUMENT_STRUCTURE_TOKEN,
							DOCUMENT_STRUCTURE_CR, 
							DOCUMENT_STRUCTURE_DR, 
							DOCUMENT_STRUCTURE_DR_SUB, 
							DOCUMENT_STRUCTURE_PR,
							DOCUMENT_STRUCTURE_PR_SUB};
	/**
	 * stores the current type of traversion
	 */
	private TRAVERSION_TYPE currTraversionType= null;
	
// ================================================ start: mapping corpus structure	
	
	
	@Override
	public DOCUMENT_STATUS mapSCorpus()
	{   
		
		//this.setSCorpusGraph(sCorpusGraph);
		if (this.getSCorpusGraph() == null)
			throw new PepperModuleException(this, "Cannot map sCorpusGraph, because sCorpusGraph is null.");
		
		{//start traversion of corpus structure
			/// initialize preOrder and postOrder:
			this.preorderTable = new Hashtable<SNode,Long>();
			this.postorderTable = new Hashtable<SNode, Long>();
			prePostOrder = 0l;
			
			try
			{
				EList<SNode> roots= this.getSCorpusGraph().getSRoots();
				if (	(roots== null) || (roots.size()== 0))
				{
					throw new PepperModuleException(this, "Cannot traverse through corpus structure, because there is no Corpus-object as root.");
				}
				
				// set the SName of the corpus graph root to the individual one
				if (this.individualCorpusName != null){
					this.individualCorpusNameReplacement = new ImmutablePair<String, String>(roots.get(0).getSName(), this.individualCorpusName);
					//roots.get(0).setSName(this.individualCorpusName);
				}
				//set traversion type to corpus structure
				this.currTraversionType= TRAVERSION_TYPE.CORPUS_STRUCTURE;
				sCorpusGraph.traverse(roots, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "compute_corpus_structure", this);
			}catch (Exception e) {
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
	private Long prePostOrder= null;
	private boolean mapRelationsInParallel = true;
	
	public void mapRelationsInParallel(boolean parallel){
		this.mapRelationsInParallel = parallel;
	}
	
	/**
	 * returns a new and unique ppOrder
	 * @return
	 */
	private synchronized Long getNewPPOrder()
	{
		if (prePostOrder== null){
			prePostOrder=0l;
		}
		Long currPrePost= prePostOrder;
		prePostOrder++;
		return(currPrePost);
	}
	
	@Override
	public DOCUMENT_STATUS mapSDocument(){

		this.preorderTable = new Hashtable<SNode, Long>();
		this.postorderTable = new Hashtable<SNode, Long>();
		prePostOrder = 0l;
		
		if (this.getSDocument() == null)
			throw new PepperModuleException(this, "Cannot map sDocumentGraph, because sDocumentGraph is null.");
		
		{//start traversion of documentStructure
			
			
			try
			{
				
				/**
				 * traverse by 
				 * SpanningRelations: DOCUMENT_STRUCTURE_CR
				 * DominanceRelations: DOCUMENT_STRUCTURE_DR
				 * PointingRelations: DOCUMENT_STRUCTURE_PR
				 * 
				 * DominanceRelations Subcomponents: DOCUMENT_STRUCTURE_DR_SUB
				 * PointingRelations Subcomponents: DOCUMENT_STRUCTURE_PR_SUB
				 * 
				 * Dominance relations may consist of different subcomponents
				 * since there are "edge" and "secedge" types
				 * 
				 * Since every root node has it's own component,
				 * the pre and post order needs to be 0 for the root
				 * node. You need to handle this.
				 */
				EList<SNode> sRelationRoots = null;
				Map<String,EList<SNode>> subComponentRoots = null;
				
				// START Step 1: map SOrderRelation
				subComponentRoots = this.getSDocument().getSDocumentGraph().getRootsBySRelationSType(STYPE_NAME.SORDER_RELATION);
				if (subComponentRoots != null){
					if (subComponentRoots.size() > 0){
						for (String key : subComponentRoots.keySet()){
							//System.out.println("Count of SOrderRelation roots for key "+key+" : "+subComponentRoots.get(key).size());
							//System.out.println("Mapping SOrderRelations subcomponents with sType: "+key);
							SRelation2RelANNISMapper sOrderRelationMapper = new SOrderRelation2RelANNISMapper(getIdManager(), getSDocument().getSDocumentGraph(), tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component);
							sOrderRelationMapper.setTraversionSType(key);
							sOrderRelationMapper.mapSRelations2RelANNIS(subComponentRoots.get(key), STYPE_NAME.SORDER_RELATION, null);
							
						}
					}
				}
				
				// END Step 1: map SOrderRelation
				
				// START Step 2: map SText
				if (this.getSDocument().getSDocumentGraph().getSTimelineRelations() == null){
					this.mapSText();
				} else {
					if (this.getSDocument().getSDocumentGraph().getSTimelineRelations().size() == 0){
						this.mapSText();
					} else {
						Long sDocID = null;
						Long textId = 0l;
						SElementId sDocumentElementId = this.getSDocument().getSElementId();
							
						if (sDocumentElementId == null){
							throw new PepperModuleException(this, "SElement Id of the document '"+this.getSDocument().getSName()+"' is NULL!");
						}
						sDocID = this.idManager.getNewCorpusTabId(sDocumentElementId);
						String textName = "sText0";
						String textContent = "";
						Vector<String> tuple = new Vector<String>();
						tuple.add(sDocID.toString());
						tuple.add(textId.toString());
						tuple.add(textName);
						tuple.add(textContent);
							
						long transactionId = tw_text.beginTA();
						try {
							tw_text.addTuple(transactionId,tuple);
							tw_text.commitTA(transactionId);
							
						} catch (FileNotFoundException e) {
							tw_text.abortTA(transactionId);
							throw new PepperModuleException(this, "Could not write to the node.tab, exception was"+e.getMessage());
						}
					}
				}
				
				Vector<SRelation2RelANNISMapper> threads = new Vector<SRelation2RelANNISMapper>();
				
				subComponentRoots = getSDocument().getSDocumentGraph().getRootsBySRelationSType(STYPE_NAME.SPOINTING_RELATION);
				if (subComponentRoots != null){
					//System.out.println("The Pointing relation graphs have "+ subComponentRoots.size() + " STypes.");
					if (subComponentRoots.size() > 0){
						
						SProcessingAnnotation proc = this.getSCorpusGraph().getSProcessingAnnotation("someDocumentContainsPointingRelations");
						if (proc == null){
							this.getSCorpusGraph().createSProcessingAnnotation(null, "someDocumentContainsPointingRelations", "true");
						}
						
						for (String key : subComponentRoots.keySet()){
							//System.out.println("Count of PR roots for key "+key+" : "+subComponentRoots.get(key).size());
							//System.out.println("Mapping PointingRelation subcomponents with sType: "+key);
							SRelation2RelANNISMapper sPointingSubRelationMapper = new SPointingRelation2RelANNISMapper(getIdManager(), getSDocument().getSDocumentGraph(), tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component);
							sPointingSubRelationMapper.mapSRelations2RelANNIS(subComponentRoots.get(key), STYPE_NAME.SPOINTING_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR);
							sPointingSubRelationMapper.setTraversionSType(key);
							if (this.mapRelationsInParallel ){
								threads.add(sPointingSubRelationMapper);
								sPointingSubRelationMapper.start();
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
				if (sRelationRoots != null){
					if (sRelationRoots.size() > 0){
						SRelation2RelANNISMapper sDominanceRelationMapper = new SDominanceRelation2RelANNISMapper(getIdManager(), getSDocument().getSDocumentGraph(), tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component);
						sDominanceRelationMapper.mapSRelations2RelANNIS(sRelationRoots, STYPE_NAME.SDOMINANCE_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR);
						if (this.mapRelationsInParallel){
							threads.add(sDominanceRelationMapper);
							sDominanceRelationMapper.start();
						} else {
							sDominanceRelationMapper.run();
						}
					}
				}
				// END Step 3: map SDominanceRelations
				
				// START Step 3.1 : map the subComponents of the SDominanceRelations
				subComponentRoots = getSDocument().getSDocumentGraph().getRootsBySRelationSType(STYPE_NAME.SDOMINANCE_RELATION);
				if (subComponentRoots != null){
					//System.out.println("The Dominance relation graphs have "+ subComponentRoots.size() + " STypes.");
					if (subComponentRoots.size() > 0){
						for (String key : subComponentRoots.keySet()){
							//System.out.println("Mapping DominanceRelation subcomponents with sType: "+key);
							
							SRelation2RelANNISMapper sDominanceSubRelationMapper = new SDominanceRelation2RelANNISMapper(getIdManager(), getSDocument().getSDocumentGraph(), tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component);
							sDominanceSubRelationMapper.setTraversionSType(key);
							sDominanceSubRelationMapper.mapSRelations2RelANNIS(subComponentRoots.get(key), STYPE_NAME.SDOMINANCE_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR);
							if (this.mapRelationsInParallel){
								threads.add(sDominanceSubRelationMapper);
								sDominanceSubRelationMapper.start();
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
				if (sRelationRoots != null){
					if (sRelationRoots.size() > 0){
						SRelation2RelANNISMapper spanningRelationMapper = new SSpanningRelation2RelANNISMapper(getIdManager(), getSDocument().getSDocumentGraph(), tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component);
						spanningRelationMapper.mapSRelations2RelANNIS(sRelationRoots, STYPE_NAME.SSPANNING_RELATION, TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR);
						if (this.mapRelationsInParallel){
							threads.add(spanningRelationMapper);
							spanningRelationMapper.start();
						} else {
							spanningRelationMapper.run();
						}
					}
				}
				// END Step 4: map SSpanningrelations
				
				if (this.mapRelationsInParallel){
					for (SRelation2RelANNISMapper mapper : threads){
						mapper.join();
					}
				}
				// START Step 5: map all SToken which were not mapped, yet
				SRelation2RelANNISMapper mapper = new SSpanningRelation2RelANNISMapper(getIdManager(), getSDocument().getSDocumentGraph(), tw_node, tw_nodeAnno, tw_rank, tw_edgeAnno, tw_component);
				for (SNode node : getSDocument().getSDocumentGraph().getSTokens()){
					if (this.idManager.getVirtualisedSpanId(node.getSElementId()) == null){
						mapper.mapSNode(node);
					}
					
				}
				// END Step 5: map all SToken which were not mapped, yet
			}catch (Exception e) {
				throw new PepperModuleException(this, "Some error occurs while traversing document structure graph.", e);
			}
		}//start traversion of corpus structure
		
		return DOCUMENT_STATUS.COMPLETED;
	}
	
	
	/**
	 * <pre
	 * corpus_ref 	integer 		X 	foreign key to corpus.id
	 * id 			integer 		X 	restart from 0 for every corpus_ref
     * name 		text 			name of the text
	 * text 		text 			content of the text
	 * </pre>
	 */
	protected void mapSText(){
		//System.out.println("MAPPING TEXT ****************************************");
		SDocumentGraph sDoc = this.getSDocument().getSDocumentGraph();
		Long sDocID = null;
		Long textId = 0l;
		//System.out.println("Count of textual DS: "+sDoc.getSTextualDSs().size());
		for (STextualDS text : sDoc.getSTextualDSs()){
			SElementId sDocumentElementId = this.getSDocument().getSElementId();
			//System.out.println("Found text \""+text.getSText()+"\"");
			//System.out.println("The text has a length of "+text.getSText().length());
			//for (STextualRelation sTextRel : this.getSDocument().getSDocumentGraph().getSTextualRelations()){
			//	System.out.println("TextRelation: start "+sTextRel.getSStart()+" , end "+sTextRel.getSEnd());
			//}
			
			if (sDocumentElementId == null){
				throw new PepperModuleException(this, "SElement Id of the document '"+sDoc.getSName()+"' is NULL!");
			}
			IdManager manager = getIdManager();
			if (manager == null){
				throw new PepperModuleException(this, "No IdManager was found, this might be a bug.!");
			}
			sDocID = manager.getNewCorpusTabId(sDocumentElementId);
			String textName = text.getSName();
			String textContent = text.getSText();
			Vector<String> tuple = new Vector<String>();
			tuple.add(sDocID.toString());
			tuple.add(manager.getNewTextId(text.getSElementId()).toString());
			tuple.add(textName);
			tuple.add(textContent);
			
			long transactionId = tw_text.beginTA();
			try {
				tw_text.addTuple(transactionId,tuple);
				tw_text.commitTA(transactionId);
				
			} catch (FileNotFoundException e) {
				tw_text.abortTA(transactionId);
				throw new PepperModuleException(this, "Could not write to the node.tab, exception was"+e.getMessage());
			}
			textId++;
		}
	}
	
// ================================================ end: mapping document structure
	
	
	/**
	 * These tables contain the SNodes as key and the preorder/postorder as value.
	 */
	private Hashtable<SNode,Long> preorderTable;
	private Hashtable<SNode, Long> postorderTable;
	
	
	/**
	 * This method maps a SDocument or SCorpus to a corpus.tab entry and all meta annotations to
	 * the corpus_annotations.tab.
	 * 
	 * id 	integer 	X 	X 	primary key
	 * name 	text 	X 	X 	unique name (per corpus)
	 * type 	text 		X 	CORPUS, DOCUMENT
	 * version 	text 			version number (not used)
	 * pre 	integer 		X 	pre order of the corpus tree
	 * post 	integer 		X 	post order of the corpus tree
	 * @param sNode
	 * @param id
	 * @param preOrder
	 * @param postOrder
	 */
	private void mapToCorpusTab(SNode sNode,Long id, Long preOrder, Long postOrder ){
		TupleWriter corpusTabWriter = tw_corpus;
		String idString = id.toString();
		
		String name = sNode.getSName();
		if (this.individualCorpusNameReplacement != null){
			if (name.equals(this.individualCorpusNameReplacement.getLeft())){
				name = this.individualCorpusNameReplacement.getRight();
			}
		}
		
		
		String type = null;
		String version = "NULL";
		String pre = preOrder.toString();
		String post = postOrder.toString();
		if (sNode instanceof SDocument){
			type = "DOCUMENT";
		} else {
			if (sNode instanceof SCorpus){
				type = "CORPUS";
				
			}
		}
		Vector<String> tuple = new Vector<String>();
		tuple.add(idString);
		tuple.add(name);
		tuple.add(type);
		tuple.add(version);
		tuple.add(pre);
		tuple.add(post);
		
		long transactionId = corpusTabWriter.beginTA();
		try {
			corpusTabWriter.addTuple(transactionId,tuple);
			corpusTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			corpusTabWriter.abortTA(transactionId);
			throw new PepperModuleException(this, "Could not write to the corpus.tab, exception was"+e.getMessage());
		}
		
		// TODO: map to corpus_annotations.tab
		
	}
	
	// ==================================================== Traversion ===============================================
	
	@Override
	public boolean checkConstraint(	GRAPH_TRAVERSE_TYPE traversalType,
									String traversalId, 
									SRelation edge, 
									SNode currNode, 
									long order)	
	{
		Boolean returnValue= false;
		if (this.currTraversionType == TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (	(currNode instanceof SCorpus)||
					(currNode instanceof SDocument))
			{
				returnValue= true;
			}
		}//traversing corpus structure
		
		return returnValue;				
	}
	
	@Override
	public void nodeReached(	GRAPH_TRAVERSE_TYPE traversalType,
								String traversalId, 
								SNode currNode, 
								SRelation edge, 
								SNode fromNode,
								long order)
	{
		
		/**
		 * First part: 
		 * This traversal concerns the Corpus structure
		 * Save the pre-order in the CorpusStructure table.
		 */
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (currNode instanceof SCorpus || currNode instanceof SDocument){
				
				this.idManager.getNewCorpusTabId(currNode.getSElementId());
				
				/**
				 * Initialize a new entry for this node in the preorder table,
				 * if it was not reached, yet.
				 */
				if (!this.preorderTable.containsKey(currNode)){
					this.preorderTable.put(currNode, getNewPPOrder());
				} 
			}
		}//traversing corpus structure		
	}	
	
	@Override
	public void nodeLeft(	GRAPH_TRAVERSE_TYPE traversalType, 
							String traversalId,
							SNode currNode, 
							SRelation edge, 
							SNode fromNode, 
							long order)
	{
		
		/**
		 * We just left a node we had reached.
		 */

		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			
			/**
			 * This node is a leaf.
			 * Set the postorder for it.
			 **/
			if (currNode instanceof SDocument || currNode instanceof SCorpus){
				if (this.preorderTable.containsKey(currNode)){
					if (! this.postorderTable.containsKey(currNode)){
						this.postorderTable.put(currNode, getNewPPOrder());
						Long iD = null;
						if (currNode instanceof SDocument){
							SDocument sd = (SDocument)currNode;
							iD = getIdManager().getNewCorpusTabId(sd.getSElementId());
						}
						if (currNode instanceof SCorpus){
							SCorpus sc = (SCorpus)currNode;
							iD = getIdManager().getNewCorpusTabId(sc.getSElementId());
						}
						
						// map the the node to the corpus tab
						this.mapToCorpusTab(currNode, iD,this.preorderTable.get(currNode),this.postorderTable.get(currNode));
					}
				}
			}
		}//traversing corpus structure
		
	}

	
	
}
