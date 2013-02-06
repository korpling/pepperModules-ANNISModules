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
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
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
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SProcessingAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;

public class Salt2RelANNISMapper implements SGraphTraverseHandler
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

	private RelANNISExporter exporter = null;
	
	public void setRelANNISExporter(RelANNISExporter exp){
		this.exporter = exp;
	}
	
// -------------------------start: PepperModuleController	
	/**
	 * The controller handling the {@link PepperModule} object.
	 */
	private PepperModuleController pModuleController= null;
	
	public void setpModuleController(PepperModuleController pModuleController) {
		this.pModuleController = pModuleController;
	}

	public PepperModuleController getpModuleController() {
		return pModuleController;
	}
// -------------------------end: PepperModuleController
	

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
	
	private boolean PROCESS_DOMINANCE_RELATIONS_MULTITHREADED = true;
	private boolean PROCESS_POINTING_RELATIONS_MULTITHREADED = true;
	

// ================================================ start: LogService	
	private LogService logService;

	public void setLogService(LogService logService) 
	{
		this.logService = logService;
	}
	
	public LogService getLogService() 
	{
		return(this.logService);
	}
	
	public void unsetLogService(LogService logService) {
		this.logService= null;
	}
// ================================================ end: LogService
	
// ================================================ start: mapping corpus structure	
	
	/**
	 * Maps the Corpus structure to the corpus.relannis file.
	 * @param sCorpusGraph corpus graph to map
	 */
	public void mapSCorpusGraph(SCorpusGraph sCorpusGraph)
	{
		this.setSCorpusGraph(sCorpusGraph);
		if (this.getSCorpusGraph()== null)
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
				this.getSCorpusGraph().traverse(roots, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "compute_corpus_structure", this);
			}catch (Exception e) {
				throw new RelANNISModuleException("Some error occurs while traversing corpus structure graph.", e);
			}
		}//start traversion of corpus structure
	}

	public void mapSDocumentGraph(SDocumentGraph sDocumentGraph){
		this.setsDocGraph(sDocumentGraph);
		if (this.getsDocGraph() == null)
			throw new RelANNISModuleException("Cannot map sDocumentGraph, because sDocumentGraph is null.");
		
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
				this.traverseSpanningRelations();
				this.traverseDominanceRelations();
				this.traversePointingRelations();
				
			}catch (Exception e) {
				throw new RelANNISModuleException("Some error occurs while traversing document structure graph.", e);
			}
		}//start traversion of corpus structure
		
	}
	
	private void traverseSpanningRelations(){
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR;
		EList<SNode> spanningRelationRoots = this.getsDocGraph().getRootsBySRelation(STYPE_NAME.SSPANNING_RELATION);
		if (spanningRelationRoots != null && spanningRelationRoots.size() != 0){
			for (SNode node : spanningRelationRoots){
				this.preorderTable = new Hashtable<SNode,Long>();
				this.postorderTable = new Hashtable<SNode, Long>();
				prePostOrder = 0l;
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				this.getsDocGraph().traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
			}
		}
	}
	
	private void traverseDominanceRelations(){
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR;
		EList<SNode> dominanceRelations = this.getsDocGraph().getRootsBySRelation(STYPE_NAME.SDOMINANCE_RELATION);
		if (dominanceRelations != null && dominanceRelations.size() != 0){
			for (SNode node : dominanceRelations){
				this.preorderTable = new Hashtable<SNode,Long>();
				this.postorderTable = new Hashtable<SNode, Long>();
				prePostOrder = 0l;
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				this.getsDocGraph().traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
			}
		}
	}
	
	private void traversePointingRelations(){
		this.currTraversionType = TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR;
		EList<SNode> pointingRelations = this.getsDocGraph().getRootsBySRelation(STYPE_NAME.SPOINTING_RELATION);
		if (pointingRelations != null && pointingRelations.size() != 0){
			for (SNode node : pointingRelations){
				this.preorderTable = new Hashtable<SNode,Long>();
				this.postorderTable = new Hashtable<SNode, Long>();
				prePostOrder = 0l;
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				this.getsDocGraph().traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"export_tokens", this);
			}
		}
	}
	
// ================================================ end: mapping corpus structure
// ================================================ end: mapping document structure
	private SDocumentGraph sDocGraph = null;
	/**
	 * @param sDocGraph the sDocGraph to set
	 */
	private void setsDocGraph(SDocumentGraph sDocGraph) {
		this.sDocGraph = sDocGraph;
	}

	/**
	 * @return the sDocGraph
	 */
	public SDocumentGraph getsDocGraph() {
		return sDocGraph;
	}

	/**
	 * The progress in percent of the current mapping.
	 */
	private double currentProgress= 0;
	
	/**
	 * returns the current progress.
	 * @return
	 */
	public double getProgress() {
		return currentProgress;
	}

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
			if (this.preorderTable.containsKey(currNode)){
				if (! this.postorderTable.containsKey(currNode)){
					this.postorderTable.put(currNode, getNewPPOrder());
				}
			}
		}//traversing corpus structure
		else if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
		{//traversing document structure
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
							EList<Edge> outEdges= this.getsDocGraph().getOutEdges(currNode.getId());
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
						}
					
					this.markAsVisited(currNode);
				}
				
			}
		}//traversing document structure
	}

	private void mapSToken(SToken sToken, STextualDS currSTextDS) {
		if (sToken == null)
			throw new RelANNISModuleException("Cannot map the SToken-object to a RA node entry, because sToken is empty.");
		
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
		 * Integer leftToken : @NOTE: this value is not clear for me
		 * Integer rightToken : @NOTE: this value is not clear for me
		 * Integer segmentIndex
		 * String segmentName
		 * String span : for token, this is the substring of the covered text
		 */
		Long id = this.exporter.getIdManager().getNewRAId(sToken.getSElementId());
		Long textId = this.exporter.getIdManager().getNewRAId(currSTextDS.getSElementId());
		Long corpusId = this.exporter.getIdManager().getNewRAId(this.getsDocGraph().getSElementId());
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
			this.sTokenSortByLeft= this.getsDocGraph().getSortedSTokenByText();
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
		SDataSourceSequence sequence= this.getsDocGraph().getOverlappedDSSequences(sToken, sTypes).get(0);
		
		String sText = currSTextDS.getSText();
		
		left= new Long(sequence.getSStart());
		right= new Long(sequence.getSEnd());
		if (left < 0)
			throw new RelANNISModuleException("Cannot map the given SToken-object '"+sToken.getSId()+"' to RAToken, because its left-value '"+left+"' is smaller than 0.");
		if (right > sText.length())
			throw new RelANNISModuleException("Cannot map the given SToken-object '"+sToken.getSId()+"' to RAToken, because its right-value '"+right+"' is bigger than the size of the text ("+sText.length()+"): "+sText.substring(0, 50)+"... .");
		
		span = sText.substring(left.intValue(),right.intValue());
		
		/**
		 * @TODO: assert that "right " is not bigger then the overlapped texts size
		 */
		
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
		 * The order is the number of the edge in fromNode
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
				if (currNode instanceof SDocument){
					exporter.getIdManager().getNewRAId(currNode.getSElementId());
				}
				/**
				 * Initialize a new entry for this node in the preorder table,
				 * if it was not reached, yet.
				 * If the node has a preorder, set the postorder.
				 */
				if (!this.preorderTable.containsKey(currNode)){
					this.preorderTable.put(currNode, getNewPPOrder());
				} else {
					if (!this.postorderTable.containsKey(currNode)){
						this.postorderTable.put(currNode, getNewPPOrder());
						EList<String> tuple = new BasicEList<String>();
						tuple.add(currNode.getSId());
						tuple.add(this.preorderTable.get(currNode).toString());
						tuple.add(this.postorderTable.get(currNode).toString());
						try {
							this.exporter.getCorpusTabTupleWriter().addTuple(tuple);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
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
