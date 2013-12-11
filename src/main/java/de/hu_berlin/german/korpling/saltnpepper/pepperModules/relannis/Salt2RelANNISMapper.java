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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.MAPPING_RESULT;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperMapperImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDataSourceSequence;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructuredNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class Salt2RelANNISMapper extends PepperMapperImpl implements SGraphTraverseHandler
{
	public Salt2RelANNISMapper()
	{
		this.init();
	}
	
	private void init()
	{
		//initialize naming table
		alreadyExistingRANames= new Hashtable<String, String>();
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
	public MAPPING_RESULT mapSCorpus()
	{
		//this.setSCorpusGraph(sCorpusGraph);
		if (this.getSCorpusGraph() == null)
			throw new RelANNISModuleException("Cannot map sCorpusGraph, because sCorpusGraph is null.");
		
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
					throw new RelANNISModuleException("Cannot traverse through corpus structure, because there is no Corpus-object as root.");
				}
				//set traversion type to corpus structure
				this.currTraversionType= TRAVERSION_TYPE.CORPUS_STRUCTURE;
				this.currentRelANNISCorpus= new Stack<SNode>();
				sCorpusGraph.traverse(roots, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "compute_corpus_structure", this);
			}catch (Exception e) {
				throw new RelANNISModuleException("Some error occurs while traversing corpus structure graph.", e);
			}
		}//start traversion of corpus structure
		return MAPPING_RESULT.FINISHED;
	}

	private void traverseToken() {
		SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN;
		if (this.sTokenSortByLeft != null){
			
		} else {
			this.sTokenSortByLeft = graph.getSortedSTokenByText();
		}
		this.preorderTable = new Hashtable<SNode,Long>();
		this.postorderTable = new Hashtable<SNode, Long>();
		prePostOrder = 0l;
		for (SNode node : this.sTokenSortByLeft){
			EList<SNode> singleRootList = new BasicEList<SNode>();
			singleRootList.add(node);
			
			graph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
		}
	}
	
	private void traverseSpanningRelations(){
		SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR;
		EList<SNode> spanningRelationRoots = graph.getRootsBySRelation(STYPE_NAME.SSPANNING_RELATION);
		if (spanningRelationRoots != null && spanningRelationRoots.size() != 0){
			for (SNode node : spanningRelationRoots){
				this.preorderTable = new Hashtable<SNode,Long>();
				this.postorderTable = new Hashtable<SNode, Long>();
				prePostOrder = 0l;
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				
				graph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
			}
		}
	}
	
	private void traverseDominanceRelations(){
		SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR;
		EList<SNode> dominanceRelations = graph.getRootsBySRelation(STYPE_NAME.SDOMINANCE_RELATION);
		if (dominanceRelations != null && dominanceRelations.size() != 0){
			for (SNode node : dominanceRelations){
				this.preorderTable = new Hashtable<SNode,Long>();
				this.postorderTable = new Hashtable<SNode, Long>();
				prePostOrder = 0l;
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				graph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
			}
		}
	}
	
	private void traversePointingRelations(){
		SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR;
		EList<SNode> pointingRelations = graph.getRootsBySRelation(STYPE_NAME.SPOINTING_RELATION);
		if (pointingRelations != null && pointingRelations.size() != 0){
			for (SNode node : pointingRelations){
				this.preorderTable = new Hashtable<SNode,Long>();
				this.postorderTable = new Hashtable<SNode, Long>();
				prePostOrder = 0l;
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				graph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
			}
		}
	}
	
// ================================================ end: mapping corpus structure
// ================================================ end: mapping document structure
	
	/**
	 * Stores the sToken-objects ordered by the left token position 
	 */
	private EList<SToken> sTokenSortByLeft= null;

	/**
	 * Default name for namespaces if they cannot be computed by the layers name.
	 */
	private final static String DEFAULT_NS= "default_ns";
	/**
	 * Prefix for token-namespaces, for example if token has namespace xyz, the namespace
	 * token_xyz will be created. 
	 */
	private final static String TOKEN_NS_PREFIXNS= "token";
	
	/**
	 * This table stores all names of raNodes which already has been used.
	 */
	private Hashtable<String, String> alreadyExistingRANames= null;
	
	
	/**
	 * counter for pre and post order
	 */
	private Long prePostOrder= null;
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
	public MAPPING_RESULT mapSDocument(){

		this.preorderTable = new Hashtable<SNode,Long>();
		this.postorderTable = new Hashtable<SNode, Long>();
		prePostOrder = 0l;
		
		if (this.getSDocument() == null)
			throw new RelANNISModuleException("Cannot map sDocumentGraph, because sDocumentGraph is null.");
		
		{//start traversion of documentStructure
			
			
			try
			{
				this.mapSText();
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
				//this.traverseToken();
				//this.traverseSpanningRelations();
				
				
				//this.traverseDominanceRelations();
				//this.traversePointingRelations();
				
			}catch (Exception e) {
				throw new RelANNISModuleException("Some error occurs while traversing document structure graph.", e);
			}
		}//start traversion of corpus structure
		
		return MAPPING_RESULT.FINISHED;
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
		SDocumentGraph sDoc = this.getSDocument().getSDocumentGraph();
		Long sDocID = null;
		Long textId = 0l;
		System.out.println("Count of textual DS: "+sDoc.getSTextualDSs().size());
		for (STextualDS text : sDoc.getSTextualDSs()){
			SElementId sDocumentElementId = sDoc.getSElementId();
			if (sDocumentElementId == null){
				throw new RelANNISModuleException("SElement Id of the document '"+sDoc.getSName()+"' is NULL!");
			}
			IdManager manager = getIdManager();
			if (manager == null){
				throw new RelANNISModuleException("No IdManager was found, this might be a bug.!");
			}
			sDocID = manager.getNewDocumentId(sDocumentElementId);
			String textName = text.getSName();
			String textContent = text.getSText();
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
				throw new RelANNISModuleException("Could not write to the node.tab, exception was"+e.getMessage());
			}
			textId++;
		}
	}
	
	/**
	 * Maps the given SRelation-object 
	 * @param sRel sRelation-object to map
	 * @param sNode the SNode object, which contains the pre value
	 */
	protected void mapSRelation2Rank(	SRelation sRel, 
										SNode sNode)
	{
		
	}
	
	/**
	 * Maps the given SRelation-object
	 */
	protected void map2Component(	TRAVERSION_TYPE traversionType, 
									SRelation sRel)
	{
		
	}
	
// ================================================ end: mapping document structure
	
	
	/**
	 * stores the last SCorpus-object which is a SDocument or a SCorpus
	 * In RelANNIS, both are seen as Corpus
	 */
	private Stack<SNode> currentRelANNISCorpus= null;
	
	/**
	 * Stores the current component id for current sub component
	 */
	private String currComponentId= null;
	
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
		{//traversing document structure
			if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
			{
				if (edge != null){
					/**
					 * If the current edge is a dominance relation and has no SType,
					 * set a generic one.
					 */
					if (edge instanceof SDominanceRelation){
						if (	(((SRelation)edge).getSTypes()== null) ||
								(((SRelation)edge).getSTypes().size()==0))
						{
							((SRelation)edge).addSType("edge");
						}
					}
					
				}
				
				boolean currentNodeIsSNode = false;
				if (currNode instanceof SNode){
					currentNodeIsSNode = true;
				}
				
				boolean edgeIsNull = false;
				boolean edgeIsTextualRelation = false;
				if (edge == null){
					edgeIsNull = true;
				} else {
					if (edge instanceof STextualRelation){
						edgeIsTextualRelation = true;
					}
				}
				
				
				
				/**
				 * Token traversion should always be done
				 */
				if (this.currTraversionType == TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN){
					if (edgeIsTextualRelation){
						returnValue = true;
					}
				}  
				/**
				 * If the spanning relations are traversed
				 */
				else if (this.currTraversionType == TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR){
					if (currentNodeIsSNode){
						if (edgeIsNull){
							returnValue = true;
						} else 
							if ((edge instanceof SSpanningRelation) || edgeIsTextualRelation ){
								returnValue = true;
							}
					}
				}
				/**
				 * if the dominance relations are traversed
				 */
				else if (this.currTraversionType == TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR){
					if (currentNodeIsSNode){
						if (edgeIsNull){
							returnValue = null;
						} else 				
							if ((edge instanceof SDominanceRelation) || edgeIsTextualRelation ){
								returnValue = true;
							}
					}
				}
				/**
				 * If the pointing relations are traversed
				 */
				else if (this.currTraversionType == TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR){
					if ((edge instanceof SPointingRelation) || edgeIsTextualRelation){
						/**
						 * visit pointing relations only once
						 */
						if (this.hasVisited(edge)){
							returnValue = false;
						} else {
							returnValue = true;
							this.markAsVisited(edge);
						}
					}
				}
				/**
				 * If the current traversion is the traversion of subcomponents of 
				 * pointing relations or dominance relations
				 */
				else if (( this.currTraversionType == TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB ) || 
						 ( this.currTraversionType == TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB )){
					if (edgeIsNull){
						returnValue = true;
					} else {
						/**
						 * the SRelation has at least one SType
						 */
						if ((edge.getSTypes()!= null) && (edge.getSTypes().size()> 0)){
							if (this.currComponentId.equals(this.computeConnectedComponentId(edge))){
								returnValue = true;
							} else {
								returnValue = false;
							}
						} else { // The STypes are not set. Then, you should not traverse
							returnValue = false;
						}
					}
				}
			}
		}
		return returnValue;				
	}

	/**
	 * Computes a String value for identifiying a sub component and returns it. Component id
	 * is created out of all sTypes of sRelation. 
	 * @param sRelation
	 * @return
	 */
	private String computeConnectedComponentId(SRelation sRelation)
	{
		String retVal= null;
		{//compute connected component id
			if (	(sRelation.getSTypes()!= null)&&
					(sRelation.getSTypes().size()> 0))
			{	
				StringBuffer sTypes= new StringBuffer();
				for (String sType: sRelation.getSTypes())
				{	
					if ("".equals(sTypes.toString()))
						sTypes.append(sType);
					else sTypes.append(sTypes.toString()+ ":" + sType);
				}
				retVal= sTypes.toString();
			}
		}//compute connected component id
		return(retVal);
	}
		
	/**
	 * stores current traversion information (for Token).
	 */
	private TraversionInfos currTravInfo= null;
	
	/**
	 * stores the maximal right and minimal left position
	 */
	private TraversionInfos minMaxTravInfo= null;
	
	/**
	 * This is a helper class to store some informations, which has to be passed up through tranversion.
	 */
	private class TraversionInfos
	{
		public Integer start= null;
		public Integer end= null;
	}
	/**
	 * stores if the current node is a root for traversion
	 */
	private Boolean currNodeIsRoot= false;
	
	/**
	 * Stores the STokens, which belongs to current traversed sub tree
	 */
	private EList<SToken> currSTokens= null;
	
	/**
	 * These tables contain the SNodes as key and the preorder/postorder as value.
	 */
	private Hashtable<SNode,Long> preorderTable;
	private Hashtable<SNode, Long> postorderTable;
	
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
//		{//just for debug
//			System.out.print("node left: "+ currNode.getId());
//			if (edge!= null)
//				System.out.print(", by edge: "+ edge.getSource().getId()+ " --> "+ edge.getTarget().getId());
//			System.out.println();
//		}//just for debug
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			/**
			 * If there is still one node left (i.e., subcorpora and documents), remove the top "Corpus" from the stack
			 */
			this.currentRelANNISCorpus.pop();
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
							iD = getIdManager().getNewDocumentId(sd.getSElementId());
						}
						if (currNode instanceof SCorpus){
							SCorpus sc = (SCorpus)currNode;
							iD = getIdManager().getNewDocumentId(sc.getSElementId());
						}
						this.mapToCorpusTab(currNode, iD,this.preorderTable.get(currNode),this.postorderTable.get(currNode));
					}
				}
			}
		}//traversing corpus structure
		else if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
		{//traversing document structure
			/**
			 * @TODO: catch NullPointerException
			 */
			SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
			
			if (currNode instanceof SNode){
				// the current node is a token
				if (currNode instanceof SToken){
					if (this.currSTokens == null){
						this.currSTokens = new BasicEList<SToken>();
					}
					this.currSTokens.add((SToken)currNode);
				}
				/*
				 *  The node was not yet visited.
				 *  if it is a textual data source, save it.
				 */
				if (! this.hasVisited(currNode)){
					
					// the current node is a textual data source
					if (currNode instanceof STextualDS){
						
					} 
					else 
						if (currNode instanceof SToken){
							EList<Edge> outEdges= graph.getOutEdges(currNode.getId());
							STextualDS currSTextDS= null;
							for (Edge outEdge: outEdges)
							{
								if (outEdge instanceof STextualRelation)
								{
									currSTextDS= ((STextualRelation)outEdge).getSTextualDS();
									break;
								}
							}
							if (currSTextDS== null)
								throw new RelANNISModuleException("An exception occurs while traversing the document graph, because the currSTextDS object is null for node '"+currNode.getSElementId()+"'.");
							/**
							 * map SToken to RA Node
							 */
							
							this.mapSToken((SToken)currNode,currSTextDS);
						} else 
							if (currNode instanceof SSpan || currNode instanceof SStructure){
								/*
								 *  if the current node is a SSpan or SStructure, get the overlapped
								 *  token and get the STextualDS from one of this token
								 */
								
								this.mapSStructuredNode((SStructuredNode)currNode);
							}
					
					this.markAsVisited(currNode);
				}
				
			}
		}//traversing document structure
	}
	
	private void mapSStructuredNode(SStructuredNode sNode){
		if (sNode == null)
			throw new RelANNISModuleException("Cannot map the structured node, because sNode is NULL.");
		
		STextualDS currSTextDS = null;
		
		SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
		
		EList<STYPE_NAME> sTypes= new BasicEList<STYPE_NAME>();
		sTypes.add(STYPE_NAME.STEXT_OVERLAPPING_RELATION);
		// get all DSSequences which are overlapped by the SSpan/SStructure
		EList<SDataSourceSequence> overlappedDSSequences= graph.getOverlappedDSSequences(sNode, sTypes);
		
		if (	(overlappedDSSequences== null)||
				(overlappedDSSequences.size()==0))
		{ 
			if (this.getLogService()!= null)
				this.getLogService().log(LogService.LOG_WARNING, "Cannot map structured node object '"+sNode.getSId()+"' to, because it does not overlap a text.");
		} else {
			// throw a warning if more than one DSSequence is overlapped
			if (overlappedDSSequences.size() > 1){
				if (this.getLogService()!= null)
					this.getLogService().log(LogService.LOG_WARNING, "Node '"+sNode.getSId()+"' overlaps more than one DSSequence.");
			}
			// get the first overlapped DSSequence
			SDataSourceSequence overlappedSequence= overlappedDSSequences.get(0);
			// get the token which overlap the DSSequence
			EList<SToken> tokenList = null;
			tokenList = graph.getSTokensBySequence(overlappedSequence);
			
			EList<SToken> sortedToken = null;
			// get the STextualDS by querying one of the token
			if (tokenList != null && !tokenList.isEmpty()){
				EList<Edge> outEdges= graph.getOutEdges(tokenList.get(0).getSId());
				for (Edge outEdge: outEdges)
				{
					if (outEdge instanceof STextualRelation)
					{
						currSTextDS= ((STextualRelation)outEdge).getSTextualDS();
						break;
					}
				}
			} else {
				throw new RelANNISModuleException("The DSSequence "+overlappedSequence.getSSequentialDS().getSId()+" is not overlapped by any SToken");
			}
						
			if (currSTextDS== null)
				throw new RelANNISModuleException("An exception occurs while traversing the document graph, because the currSTextDS object is null for node '"+sNode.getSElementId()+"'.");
			// sort the overlapping token by SStart and SEnd
			sortedToken = graph.getSortedSTokenByText(tokenList);
			
			if (overlappedSequence.getSStart()== null)
				throw new RelANNISModuleException("Cannot map the given structured node object '"+sNode.getSId()+"', because it doesn't have a left (start-value) border, pointing to the primary data.");
			if (overlappedSequence.getSEnd()== null)
				throw new RelANNISModuleException("Cannot map the given structured node object '"+sNode.getSId()+"', because it doesn't have a right (end-value) border, pointing to the primary data.");
			
			Long id = getIdManager().getNewNodeId(sNode.getSElementId());
			Long textId = getIdManager().getNewTextId(currSTextDS.getSElementId());
			Long corpusId = getIdManager().getNewDocumentId(graph.getSElementId());
			
			String namespace = null;
			String name = null;
			
			Long left = new Long(overlappedSequence.getSStart());
			Long right = new Long(overlappedSequence.getSEnd());
			// token index MUST be null for SSpan/SStructure
			Long tokenIndex = null;
			// leftToken and rightToken will be set
			Long leftToken = null;
			Long rightToken = null;
			if (this.sTokenSortByLeft == null){
				this.sTokenSortByLeft = graph.getSortedSTokenByText();
			}
			// set leftToken to the index of the leftmost token of the sorted sequence in the complete token list
			leftToken = new Long(this.sTokenSortByLeft.indexOf(sortedToken.get(0)));
			// set rightToken to the index of the rightmost token of the sorted sequence in the complete token list
			rightToken = new Long(this.sTokenSortByLeft.indexOf(sortedToken.get(sortedToken.size()-1)));
			/*
			EList<SOrderRelation> sOrderRelations = this.getsDocGraph().getSOrderRelations();
			if (sOrderRelations == null || sOrderRelations.isEmpty()){
				
			} else {
				for (SOrderRelation order : sOrderRelations){
				
				}
			}*/
			// those two can be existent due to SOrderRelation
			Long segmentIndex = null;
			String segmentName = null;
			//
			String span = null;
			
			{//namespace
				namespace= DEFAULT_NS;
				if (	(sNode.getSLayers()!= null) &&
						(sNode.getSLayers().size()!= 0))
				{//a namespace can be taken from layers name
					if (sNode.getSLayers().get(0)!= null)
					{	
						namespace= sNode.getSLayers().get(0).getSName();
					}
				}//a namespace can be taken from layers name
			}//namespace
			
			String sText = currSTextDS.getSText();
			
			if (left < 0)
				throw new RelANNISModuleException("Cannot map the given structured node object '"+sNode.getSId()+"' , because its left-value '"+left+"' is smaller than 0.");
			if (right > sText.length())
				throw new RelANNISModuleException("Cannot map the given structured node object '"+sNode.getSId()+"' , because its right-value '"+right+"' is bigger than the size of the text '"+sText.length()+"'.");
			
			{//start: map name, name must be unique
				StringBuffer nameBuffer= new StringBuffer();
				nameBuffer.append(sNode.getSName());
				int i= 1;
				while(this.alreadyExistingRANames.containsKey(nameBuffer.toString()))
				{
					nameBuffer.delete(0, nameBuffer.length());
					nameBuffer.append(sNode.getSName()+"_"+i);
					i++;
				}
				name = nameBuffer.toString();
				this.alreadyExistingRANames.put(name, "");
			}//end: map name, name must be unique
			
			
				
			if (this.sTokenSortByLeft== null)
			{//compute correct order of tokens and store it, because of performance do it also here 
				this.sTokenSortByLeft= graph.getSortedSTokenByText();
			}//compute correct order of tokens and store it, because of performance do it also here	
			
			{//start: calculate leftToken and rightToken
				EList<SToken> sTokens= graph.getSTokensBySequence(overlappedSequence);
				leftToken = (long) this.sTokenSortByLeft.indexOf(sTokens.get(0));
				rightToken = (long) this.sTokenSortByLeft.indexOf(sTokens.get(sTokens.size()-1));
			}//end: calculate leftToken and rightToken
			
			{// if there is a segmentation, the overlapped text should be set
				if (segmentIndex == null){
					span = overlappedSequence.toString();
				}
			}//
			
			this.mapToNodeTab(sNode, id, textId, corpusId, namespace, name, left, right, tokenIndex, leftToken, rightToken, segmentIndex, segmentName, span);
			this.mapNodeAnnotationsToNodeAnnotationTab(sNode, id);
			
		}
		
	
	}

	private void mapSToken(SToken sToken, STextualDS currSTextDS) {
		if (sToken == null)
			throw new RelANNISModuleException("Cannot map the SToken to a RA node entry, because sToken is empty.");
		if (currSTextDS == null)
			throw new RelANNISModuleException("Cannot map the SToken, because the current STexualDS is NULL.");
		
		SDocumentGraph graph = this.getSDocument().getSDocumentGraph();
		
		/**
		 * needed Attributes are:
		 * Long id : unique RelANNIS id of this token
		 * Integer textId : reference to the RelANNIS id of the SText
		 * Integer corpusId : the RelANNIS id of the containing document
		 * String tokenNamespace
		 * String name : the SName of the token
		 * Integer left : the first overlapped character
		 * Integer right : the last overlapped character
		 * Integer tokenIndex : the index of the token in the token sequence
		 * Integer leftToken : for SToken, this is equivalent to left
		 * Integer rightToken : for SToken, this is equivalent to right
		 * Integer segmentIndex
		 * String segmentName
		 * String span : for token, this is the substring of the covered text
		 */
		Long id = getIdManager().getNewNodeId(sToken.getSElementId());
		Long textId = getIdManager().getNewTextId(currSTextDS.getSElementId());
		Long corpusId = getIdManager().getNewDocumentId(graph.getSElementId());
		String tokenNamespace = null;
		String name = null;
		Long left = null;
		Long right = null;
		Long tokenIndex = null;
		Long leftToken = null;
		Long rightToken = null;
		Long segmentIndex = null;
		String segmentName = null;
		String span = null;
		/**
		 * get a sorted token sequence
		 */
		if (this.sTokenSortByLeft== null)
		{
			this.sTokenSortByLeft= graph.getSortedSTokenByText();
		}
		
		tokenIndex = Long.valueOf(this.sTokenSortByLeft.indexOf(sToken));
		
		/**
		 * create the token namespace
		 */
		StringBuffer namespace= new StringBuffer();
		namespace.append(TOKEN_NS_PREFIXNS);
		if (	(sToken.getSLayers()!= null) &&
				(sToken.getSLayers().size()!= 0))
		{//a namespace can be taken from layers name
			if (sToken.getSLayers().get(0)!= null)
			{	
				namespace.append("_");
				namespace.append(sToken.getSLayers().get(0).getSName());
			}
		}//a namespace can be taken from layers name
		tokenNamespace = namespace.toString();
		
		/**
		 *  create the left and right attribute (first and last overlapped character)
		 */
		EList<STYPE_NAME> sTypes= new BasicEList<STYPE_NAME>();
		sTypes.add(STYPE_NAME.STEXT_OVERLAPPING_RELATION);
		SDataSourceSequence sequence= graph.getOverlappedDSSequences(sToken, sTypes).get(0);
		
		String sText = currSTextDS.getSText();
		
		left= new Long(sequence.getSStart());
		right= new Long(sequence.getSEnd());
		
		leftToken = left;
		rightToken = right;
		
		if (left < 0)
			throw new RelANNISModuleException("Cannot map the given SToken-object '"+sToken.getSId()+"' to RAToken, because its left-value '"+left+"' is smaller than 0.");
		if (right > sText.length())
			throw new RelANNISModuleException("Cannot map the given SToken-object '"+sToken.getSId()+"' to RAToken, because its right-value '"+right+"' is bigger than the size of the text ("+sText.length()+"): "+sText.substring(0, 50)+"... .");
		
		span = sText.substring(left.intValue(),right.intValue());
		
		{//map name, name must be unique
			StringBuffer nameBuffer= new StringBuffer();
			nameBuffer.append(sToken.getSName());
			int i= 1;
			while(this.alreadyExistingRANames.containsKey(nameBuffer.toString()))
			{
				nameBuffer.delete(0, nameBuffer.length());
				nameBuffer.append(sToken.getSName()+"_"+i);
				i++;
			}
			name = nameBuffer.toString();
			this.alreadyExistingRANames.put(name, "");
		}//map name, name must be unique
		
	
		this.mapToNodeTab(sToken,id, textId, corpusId, tokenNamespace, name, left, right, tokenIndex, leftToken, rightToken, segmentIndex, segmentName, span);
		
	}

	/**
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
			throw new RelANNISModuleException("Could not write to the corpus.tab, exception was"+e.getMessage());
		}
	}
	
	/**
	 * This method maps a node to a RelANNIS node tab entry
	 * @param id (not NULL), the unique RelANNIS id of the node
	 * @param textId (not NULL), the reference to the RelANNIS id of the SText
	 * @param corpusId (not NULL), the reference to the RelANNIS id of the containing SDocument
	 * @param tokenNamespace
	 * @param name : the SName of the token
	 * @param left (not NULL): the first overlapped character
	 * @param right (not NULL): the last overlapped character
	 * @param tokenIndex : the index of the token in the token sequence
	 * @param leftToken (not NULL): 
	 * @param rightToken (not NULL): 
	 * @param segmentIndex
	 * @param segmentName
	 * @param span : for token, this is the substring of the covered text
	 */
	private void mapToNodeTab(SNode sNode,Long id, Long textId,Long corpusId,String namespace, String name,
			Long left, Long right, Long tokenIndex, Long leftToken, Long rightToken,
			Long segmentIndex, String segmentName, String span){
		
		if (id == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since the id is NULL");
		if (textId == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since the text id is NULL");
		if (corpusId == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since the corpus id is NULL");
		if (left == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since left is NULL");
		if (right == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since right is NULL");
		if (leftToken == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since the leftToken is NULL");
		if (rightToken == null)
			throw new RelANNISModuleException("Cannot map node to node.tab since the rightToken is NULL");
		String idString = id.toString();
		String textIdString = textId.toString();
		String corpusIdString = corpusId.toString();
		String namespaceString = null;
		if (namespace == null || namespace.equals("")){
			namespaceString = DEFAULT_NS;
		} else {
			namespaceString = namespace;
		}
		String nameString = null;
		if (name == null){
			nameString = "";
		} else {
			nameString = name;
		}
		String leftString = left.toString();
		String rightString = right.toString();
		String tokenIndexString = null;
		if (tokenIndex == null){
			tokenIndexString = "";
		} else {
			tokenIndexString = tokenIndex.toString();
		}
		String leftTokenString = leftToken.toString();
		String rightTokenString = rightToken.toString();
		String segmentIndexString = null;
		if (segmentIndex == null){
			segmentIndexString = "";
		} else {
			segmentIndexString = segmentIndex.toString();
		}
		String segmentNameString = null;
		if (segmentName == null){
			segmentNameString = "";
		} else {
			segmentNameString = segmentName;
		}
		String spanString = null;
		if (span == null){
			spanString = "";
		} else {
			spanString = span;
		}
		TupleWriter nodeTabWriter = tw_node;
		
		Vector<String> tuple = new Vector<String>();
		tuple.add(idString);
		tuple.add(textIdString);
		tuple.add(corpusIdString);
		tuple.add(namespaceString);
		tuple.add(nameString);
		tuple.add(leftString);
		tuple.add(rightString);
		tuple.add(tokenIndexString);
		tuple.add(leftTokenString);
		tuple.add(rightTokenString);
		tuple.add(segmentIndexString);
		tuple.add(segmentNameString);
		tuple.add(spanString);
		
		long transactionId = nodeTabWriter.beginTA();
		try {
			nodeTabWriter.addTuple(transactionId,tuple);
			nodeTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			nodeTabWriter.abortTA(transactionId);
			throw new RelANNISModuleException("Could not write to the node.tab, exception was"+e.getMessage());
		}
		
		// map the annotations of this node
		this.mapNodeAnnotationsToNodeAnnotationTab(sNode, id);
	}
	
	/**
	 * Maps the given annotation of the node with the given reference to the
	 * RelANNIS node_annotation.tab
	 * 
	 * @param node The node for which the annotations should be mapped
	 * @param nodeRef (not NULL) foreign key to _node.id 			
	 * 
	 */
	private void mapNodeAnnotationsToNodeAnnotationTab(SNode sNode, Long nodeRef){
		if (sNode == null)
			throw new RelANNISModuleException("Cannot map node to node_annotation.tab since the sNode is NULL");
		if (nodeRef == null)
			throw new RelANNISModuleException("Cannot map node to node_annotation.tab since the node id is NULL");
		/**
		 * map node annotations:
		 * node_ref 	bigint 	not NULL 	foreign key to _node.id
		 * namespace 	text 			
		 * name 	    text 	not NULL 	
		 * value 	    text
		 */
		for (SAnnotation sAnno: sNode.getSAnnotations())
		{//map annotations
			String nodeRefString = nodeRef.toString();
			String namespaceString = sAnno.getSNS();
			if (namespaceString == null || namespaceString.equals("")){
				namespaceString = DEFAULT_NS;
			}
			String nameString = sAnno.getSName();
			String valueString = sAnno.getSValueSTEXT();
			
			TupleWriter nodeAnnoTabWriter = tw_node;
			
			Vector<String> tuple = new Vector<String>();
			tuple.add(nodeRefString);
			tuple.add(namespaceString);
			tuple.add(nameString);
			tuple.add(valueString);
			
			long transactionId = nodeAnnoTabWriter.beginTA();
			try {
				nodeAnnoTabWriter.addTuple(transactionId,tuple);
				nodeAnnoTabWriter.commitTA(transactionId);
			} catch (FileNotFoundException e) {
				nodeAnnoTabWriter.abortTA(transactionId);
				throw new RelANNISModuleException("Could not write to the node_annotation.tab, exception was"+e.getMessage());
			}
			
		}//map annotations
	}
	
	private String KW_NS= "s2ra";
	private String KW_NAME_PRE= "pre";

	@Override
	public void nodeReached(	GRAPH_TRAVERSE_TYPE traversalType,
								String traversalId, 
								SNode currNode, 
								SRelation edge, 
								SNode fromNode,
								long order)
	{
		/**
		 * This method means that the traversion to a node was successful and that this node
		 * was reached.
		 * The reached node is currNode
		 * The prior node(source) is fromNode
		 * The connection between the nodes is the edge.
		 * The order is the number of the edges in fromNode
		 */
		
//		{//just for debug
//			System.out.print("node reached: "+ currNode.getId());
//			if (edge!= null)
//				System.out.print(", by edge: "+ edge.getSource().getId()+ " --> "+ edge.getTarget().getId());
//			System.out.println();
//		}//just for debug
		/**
		 * First part: 
		 * This traversal concerns the Corpus structure
		 * Save the pre-order in the CorpusStructure table.
		 */
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (currNode instanceof SCorpus || currNode instanceof SDocument){
				
				/**
				 * if this node is a document, get a RAId for it.
				 */
				//if (currNode instanceof SDocument){
					
				//}
				
				//Long corpusRAId = exporter.getIdManager().getNewRAId(currNode.getSElementId());
				/**
				 * Initialize a new entry for this node in the preorder table,
				 * if it was not reached, yet.
				 */
				if (!this.preorderTable.containsKey(currNode)){
					this.preorderTable.put(currNode, getNewPPOrder());
				} 
			}
			
			{//map relation
				if (this.currentRelANNISCorpus.size()> 0)
				{//if there is already a corpus read	
					/**
					 * If this RelANNIS Corpus has a parent,
					 * the relation between the parent and this node needs to be saved.
					 */
				}
			}
			/**
			 * Set the RelANNIS id for this Node.
			 */
			
			//this.sElementId2RaId.put(((SNode) currNode).getSElementId(), raCorpus.getRaId());
			this.currentRelANNISCorpus.push(currNode);
		}//traversing corpus structure
		else if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
		{//traversing document structure
			
			//if (!this.preorderTable.containsKey(currNode)){
			//	this.preorderTable.put(currNode, getNewPPOrder());
			//} 
					}//traversing document structure
		{//for testing
//			System.out.println("----------> node reached: "+currNode.getId());
		}//for testing			
	}	
	
// ========================================= start: handling to check if node or relation has been visited	
	private HashSet<SElementId> visitedSNodes = null;
	//private Map<SElementId, Boolean> visitedSNodes= null;
	/**
	 * Marks the given node as visited.
	 * @param raRank
	 */
	private void markAsVisited(SNode sNode)
	{
		if (this.visitedSNodes== null)
			this.visitedSNodes= new HashSet<SElementId>();
		this.visitedSNodes.add(sNode.getSElementId());
	}
	
	/**
	 * Returns true, if sNode was visited and false, else
	 * This is a lookup in a HashSet.
	 * @param sNode
	 * @return true, if visited and false, else
	 */
	private boolean hasVisited(SNode sNode)
	{	if (this.visitedSNodes== null){
			this.visitedSNodes= new HashSet<SElementId>();
			return false;
		}
		return visitedSNodes.contains(sNode.getSElementId());
	}
	
	private Map<SElementId, Boolean> visitedSRelations= null;
	/**
	 * Marks the given node as visited.
	 * @param raRank
	 */
	private void markAsVisited(SRelation SRelation)
	{
		if (this.visitedSRelations== null)
			this.visitedSRelations= new Hashtable<SElementId, Boolean>();
		this.visitedSRelations.put(SRelation.getSElementId(), true);
	}
	
	/**
	 * Returns true, if raRank contains visited flag, false else.
	 * @param raRank
	 * @return
	 */
	private boolean hasVisited(SRelation SRelation)
	{
		if (this.visitedSRelations== null)
			this.visitedSRelations= new Hashtable<SElementId, Boolean>();
		if (visitedSRelations.containsKey(SRelation.getSElementId()))
			return(true);
		else return(false);
	}
// ========================================= start: handling to check if node or relation has been visited
	
}
