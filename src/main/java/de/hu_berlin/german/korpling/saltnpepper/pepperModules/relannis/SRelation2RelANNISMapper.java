package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Hashtable;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public abstract class SRelation2RelANNISMapper extends Thread implements SGraphTraverseHandler{

// =============================== Globally used objects ======================
	
	protected IdManager idManager;
	
	protected SDocumentGraph documentGraph;
	
	/**
	 * Default name for namespaces if they cannot be computed by the layers name.
	 */
	protected final static String DEFAULT_NS= "default_ns";
	protected final static String DEFAULT_LAYER= "default_layer";
	
	EList<SNode> sRelationRoots= null;
	STYPE_NAME relationTypeName= null; 
	
	
	
// =================================== Constructor ============================ 
	
	
	/**
	 * @param idManager
	 * @param documentGraph
	 * @param nodeTabWriter
	 * @param nodeAnnoTabWriter
	 * @param rankTabWriter
	 * @param edgeAnnoTabWriter
	 * @param componentTabWriter
	 */
	public SRelation2RelANNISMapper(IdManager idManager,SDocumentGraph documentGraph, 
			TupleWriter nodeTabWriter, TupleWriter nodeAnnoTabWriter,
			TupleWriter rankTabWriter, TupleWriter edgeAnnoTabWriter,
			TupleWriter componentTabWriter) {
		
		this.idManager = idManager;
		
		this.documentGraph = documentGraph;
		
		this.tokenSortedByLeft = documentGraph.getSortedSTokenByText();
		
		this.nodeTabWriter = nodeTabWriter;
		this.nodeAnnoTabWriter = nodeAnnoTabWriter;
		
		this.rankTabWriter = rankTabWriter;
		this.edgeAnnoTabWriter = edgeAnnoTabWriter;
		this.componentTabWriter = componentTabWriter;
	}

	protected void setTraversionSType(String traversionSType){
		this.currentTraversionSType = traversionSType;
	}
	
	protected void initialiseTraversion(String componentType,String componentLayer, String componentName){
		// set the current component:
		// id		type	layer					name
		// unique	c/d/p	SLayer/default_layer	"NULL"/SType
		currentComponentId = this.idManager.getNewComponentId();
		currentComponentType = componentType;
		currentComponentLayer = componentLayer;
		currentComponentName = componentName;
		
		// initialise the traversion level
		rankLevel = 0l;
		
		// initialise the pre and post order table
		preorderTable = new Hashtable<SNode,Long>();
		
		// initialise the preorder
		prePostOrder = 0l;
		
		this.virtualNodes = new HashSet<SNode>();
		
		// initialise rank Hashtable
		this.rankTable = new Hashtable<SNode, Long>();
	}
	
// ============================ Data for SRelation Mapping ====================
	
	protected TupleWriter rankTabWriter;
	protected TupleWriter edgeAnnoTabWriter;
	protected TupleWriter componentTabWriter;
	
	protected TRAVERSION_TYPE traversionType;
	
	protected Long currentComponentId= null;
	protected String currentComponentType= null;
	protected String currentComponentLayer= null;
	protected String currentComponentName= null;
	
	protected Long rankLevel;
	
	protected String currentTraversionSType= null;
	
// =================================== Pre/Post order =========================
	
	/**
	 * counter for pre and post order
	 */
	protected Long prePostOrder= null;
	
	/**
	 * These tables contain the SNodes as key and the preorder as value.
	 */
	protected Hashtable<SNode, Long> preorderTable;
	
	/**
	 * Every SNode is a target of a rank
	 * and the Long is the id of the Rank
	 */
	protected Hashtable<SNode, Long> rankTable;
	
	/**
	 * returns a new and unique ppOrder
	 * @return
	 */
	protected synchronized Long getNewPPOrder()
	{
		if (prePostOrder== null){
			prePostOrder=0l;
		}
		Long currPrePost= prePostOrder;
		prePostOrder++;
		return(currPrePost);
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

		//if (this.traversionType.equals(TRAVERSION_TYPE.valueOf(traversalId))){
		// the traversion type is correct

		if (this.preorderTable.contains(currNode)){
			// this should NOT happen
		} else {
			Long rankId = null;
			EList<Long> virtualTokenIds = this.idManager.getVirtualisedTokenId(currNode.getSElementId());
			// if there are virtual token ids, the virtual tokens were already mapped into the node.tab
			// We have to create n ranks where n is the count of virtual token ids.
			// all ranks have the same annotations
			if (virtualTokenIds != null)
			{ // THERE ARE VIRTUAL TOKEN IDS
				if (this.traversionType.equals(TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR))
				{ // HANDLE POINTING RELATIONS
					// They are redirected to the virtual spans
					virtualTokenIds = new BasicEList<Long>();
					virtualTokenIds.add(this.idManager.getVirtualisedSpanId(currNode.getSElementId()));
					//System.out.println("Mapping pointing relations concerning a virtual token/span");
				} // HANDLE POINTING RELATIONS
				// set virtual token rank mapping
				this.virtualNodes.add(currNode);

				for (Long virtualTokenId : virtualTokenIds){
					// get a rank id
					rankId = this.idManager.getNewRankId();
					
					Long parentRank = null;
					if (fromNode != null){
						parentRank = this.rankTable.get(fromNode);
					}
					Long pre = this.getNewPPOrder();
					Long post = this.getNewPPOrder();
					
					if (this.traversionType.equals(TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR)){
						//System.out.println("Mapping PointingRelation rank to node with Id "+virtualTokenId);
						this.preorderTable.put(currNode, pre);
						this.rankTable.put(currNode, rankId);
					}
					
					{ // map the rank
						EList<String> rankEntry = new BasicEList<String>();
						rankEntry.add(rankId.toString());
						rankEntry.add(pre.toString());
						rankEntry.add(post.toString());
						rankEntry.add(virtualTokenId.toString());
						rankEntry.add(this.currentComponentId.toString());
						if (parentRank == null){
							rankEntry.add(new String("NULL"));
						} else {
							rankEntry.add(parentRank.toString());
						}
						rankEntry.add(this.rankLevel.toString());
						Long transactionId = this.rankTabWriter.beginTA();
						try {
							this.rankTabWriter.addTuple(transactionId, rankEntry);
							this.rankTabWriter.commitTA(transactionId);
						} catch (FileNotFoundException e) {
							throw new RelANNISModuleException("Could not write to the rank tab TupleWriter. Exception is: "+e.getMessage(),e);
						}
					} // map the rank
					if (sRelation != null)
					{ // MAP THE SAnnotations
						if (sRelation.getSAnnotations() != null)
						{
							for (SAnnotation sAnnotation : sRelation.getSAnnotations())
							{
								this.mapSAnnotation2RelANNIS(rankId, sAnnotation);
							}
						}
					} // MAP THE SAnnotations
				} // MAP VIRTUAL RANK 
			} // THERE ARE NO VIRTUAL TOKEN IDs	
			else 
			{ // MAP NORMAL RANK
				// set the rank id
				rankId = this.idManager.getNewRankId();
				// It does not have a pre-order. Set it.
				this.preorderTable.put(currNode, this.getNewPPOrder());
				//System.out.println("Reached node: "+currNode.getSName()+ " with pre "+this.preorderTable.get(currNode));
				// map the target node
				this.mapSNode(currNode);
				this.rankTable.put(currNode, rankId);
				// map the SAnnotations
				if (sRelation != null){
					if (sRelation.getSAnnotations() != null){
						for (SAnnotation sAnnotation : sRelation.getSAnnotations()){
							this.mapSAnnotation2RelANNIS(rankId, sAnnotation);
						}
					}
				}
			}// MAP NORMAL RANK
			

			this.rankLevel += 1;
		}
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
			SNode currNode, SRelation edge, SNode fromNode, long order) {
		
		// We've got the rank
		// fromNode -> edge -> currNode
		
		// only do something if we are not mapping a rank to a virtual token
		if (! this.virtualNodes.contains(currNode)){
				Long parentRank = null;
				if (fromNode != null){
					parentRank = this.rankTable.get(fromNode);
				}
				Long rankId = this.rankTable.get(currNode);
				Long pre = this.preorderTable.get(currNode);
				Long post = this.getNewPPOrder();
				//System.out.println("Leaving node: "+currNode.getSName()+ " with post "+post);
				
				// decrease the rank level
				this.rankLevel -= 1;
				
				this.mapRank2RelANNIS(edge, currNode, rankId, pre, post, parentRank, rankLevel);
		} else {
			// decrease the rank level
			this.rankLevel -= 1;	
		}		
				
				
			//}
			
			
		//}
		
		
	}

	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SRelation edge, SNode currNode, long order) {
		boolean returnVal = false;
		
		return returnVal;
	}
	
	
	
// =============================== Mapping of SRelations ======================
	
	
	public abstract void mapSRelations2RelANNIS(EList<SNode> sRelationRoots, STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType);
	
	/**
	 * This method maps the currently processed component to the relANNIS component tab file.
	 */
	protected void mapComponent2RelANNIS(){
		// id		type	layer					name
		// unique	c/d/p	SLayer/default_layer	"NULL"/SType
		EList<String> componentEntry = new BasicEList<String>();
		componentEntry.add(currentComponentId.toString());
		componentEntry.add(currentComponentType);
		componentEntry.add(currentComponentLayer);
		componentEntry.add(currentComponentName);
		// add the tuple
		Long transactionId = this.componentTabWriter.beginTA();
		try {
			this.componentTabWriter.addTuple(componentEntry);
			this.componentTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			throw new RelANNISModuleException("Could not write to the component tab TupleWriter. Exception is: "+e.getMessage(),e);
		}
		
	}
	
	protected void mapRank2RelANNIS(SRelation sRelation, SNode targetNode,Long rankId, Long preOrder, Long postOrder, Long parentRank, Long level){
		// check all needed params
		if (targetNode == null)
			throw new RelANNISModuleException("The given target node for the rank is null");
		if (rankId == null)
			throw new RelANNISModuleException("The given rank id for the rank is null");
		if (preOrder == null)
			throw new RelANNISModuleException("The given pre order for the rank is null");
		if (postOrder == null)
			throw new RelANNISModuleException("The given post order for the rank is null");
		if (level == null)
			throw new RelANNISModuleException("The given level for the rank is null");
		
		
		
		/**
		 * For rank, we need:
		 * rankId	pre	post	node_ref	component_ref	parentRank	level
		 * Thus, we create an EList of SRelations and the index is the rankId
		 * @TODO: do we REALLY need this?
		 */
		EList<String> rankEntry = new BasicEList<String>();

		rankEntry.add(rankId.toString());
		rankEntry.add(this.preorderTable.get(targetNode).toString());
		rankEntry.add(postOrder.toString());
		rankEntry.add(this.idManager.getNewNodeId(targetNode.getSElementId()).getLeft().toString());
		rankEntry.add(this.currentComponentId.toString());
		if (parentRank == null){
			rankEntry.add(new String("NULL"));
		} else {
			rankEntry.add(parentRank.toString());
		}
		
		rankEntry.add(level.toString());
		Long transactionId = this.rankTabWriter.beginTA();
		
		try {
			this.rankTabWriter.addTuple(transactionId, rankEntry);
			this.rankTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			throw new RelANNISModuleException("Could not write to the rank tab TupleWriter. Exception is: "+e.getMessage(),e);
		}
	}
	
	protected void mapSAnnotation2RelANNIS(Long rankId, SAnnotation sAnnotation){
		if (rankId == null)
			throw new RelANNISModuleException("The given rank id for the mapping of the SAnnotation is null");
		if (sAnnotation == null)
			throw new RelANNISModuleException("The given SAnnotation is null");
		// rank_id 	namespace	name	value
		
		EList<String> edgeAnnotationEntry = new BasicEList<String>();
		edgeAnnotationEntry.add(rankId.toString());
		if (sAnnotation.getSNS() != null){
			edgeAnnotationEntry.add(sAnnotation.getSNS());
		} else {
			edgeAnnotationEntry.add(new String("default_ns"));
		}
		edgeAnnotationEntry.add(sAnnotation.getSName());
		edgeAnnotationEntry.add(sAnnotation.getSValueSTEXT());
		
		Long transactionId = this.edgeAnnoTabWriter.beginTA();
		
		try {
			this.edgeAnnoTabWriter.addTuple(transactionId, edgeAnnotationEntry);
			this.edgeAnnoTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			throw new RelANNISModuleException("Could not write to the edge annotation TupleWriter. Exception is: "+e.getMessage(),e);
		}
		
	}
		
// =============================== Data for Node Mapping ======================
	
	private TupleWriter nodeTabWriter;
	private TupleWriter nodeAnnoTabWriter;
	
	private EList<SToken> tokenSortedByLeft;
	
// ================================= Mapping of SNodes ========================
	
	public void mapSNodes(EList<SNode> nodes){
		for (SNode node : nodes){
			this.mapSNode(node);
		}
	}
	
	public void mapSNode(SNode sNode){
		Pair<Long,Pair<String,String>> segInfo = this.idManager.getSegmentInformation(sNode.getSElementId());
		if (segInfo != null){
			this.mapSNode(sNode, segInfo.getLeft(), segInfo.getRight().getLeft(), segInfo.getRight().getRight());
		} else {
			this.mapSNode(sNode, null, null, null);
		}
		
	}
	
	public void mapSNode(SNode node, Long seg_index, String seg_name, String span){
		/// get the SElementId of the node since we will need it many times
		SElementId nodeSElementId = node.getSElementId();
		
		/// if the node already has a nodeId, it was already mapped
		Pair<Long,Boolean> idPair = this.idManager.getNewNodeId(nodeSElementId);
		if (idPair.getRight().booleanValue() == false){
			// the node is not new
			return;
		}
		if (this.idManager.getVirtualisedSpanId(nodeSElementId) != null){
			// the node is not new
			return;
		}
		// initialise all variables which will be used for the node.tab 
		// get the RAId
		Long id = idPair.getLeft();
		// get the text ref
		Long text_ref = null;
		// get the document ref
		Long corpus_ref = this.idManager.getNewCorpusTabId(this.documentGraph.getSDocument().getSElementId());
		// get the layer if there is one
		//@ TODO: Change this to DEFAULT_LAYER
		String layer = DEFAULT_NS;
		// initialise the name
		String name = node.getSName();
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
			if (node.getSLayers() != null){
				if (node.getSLayers().size() != 0){
					layer = node.getSLayers().get(0).getSName();
				}
			}
		}// set the layer to the actual value
		
		
		if (node instanceof SToken){
			// set the token index
			token_index = new Long(this.tokenSortedByLeft.indexOf(node));
			left_token = token_index;
			right_token = token_index;
			
			// set the left and right value and the text_ref
			EList<Edge> outEdges = documentGraph.getOutEdges(node.getSId());
			if (outEdges == null)
				throw new RelANNISModuleException("The token "+node.getSId()+ " has no outgoing edges!");
			/// find the STextualRelation
			for (Edge edge : outEdges){
				// get the edge which is of the type STextual relation
				if (edge instanceof STextualRelation){
					STextualRelation sTextualRelation = ((STextualRelation)edge);
					// set the left value
					left = new Long(sTextualRelation.getSStart());
					// set the right value which is end -1 since SEnd points to the index of the last char +1
					right = new Long(sTextualRelation.getSEnd()-1);
					// set the reference to the text
					text_ref = new Long(idManager.getNewTextId(sTextualRelation.getSTextualDS().getSElementId()));
					// set the overlapped text
					span = sTextualRelation.getSTextualDS().getSText().substring(left.intValue(),sTextualRelation.getSEnd());
					break;
				}
			}
		} else {
			if (node instanceof SSpan || node instanceof SStructure){
				EList<STYPE_NAME> overlappingTypes = new BasicEList<STYPE_NAME>();
				overlappingTypes.add(STYPE_NAME.SSPANNING_RELATION);
				if (node instanceof SStructure){
					overlappingTypes.add(STYPE_NAME.SDOMINANCE_RELATION);
				}
				// get the overlapping token
				EList<SToken> overlappedToken = this.documentGraph.getOverlappedSTokens(node, overlappingTypes);
				// sort the token by left
				EList<SToken> sortedOverlappedToken = this.documentGraph.getSortedSTokenByText(overlappedToken);
				
				SToken firstOverlappedToken = sortedOverlappedToken.get(0);
				SToken lastOverlappedToken = sortedOverlappedToken.get(sortedOverlappedToken.size()-1);
				// set left_token
				left_token = (long) this.tokenSortedByLeft.indexOf(firstOverlappedToken);
				// set right_token
				right_token = (long) this.tokenSortedByLeft.indexOf(lastOverlappedToken);
				// get first and last overlapped character
				EList<Edge> firstTokenOutEdges = documentGraph.getOutEdges(firstOverlappedToken.getSId());
				if (firstTokenOutEdges == null)
					throw new RelANNISModuleException("The token "+firstOverlappedToken.getSId()+ " has no outgoing edges!");
				
				/// find the STextualRelation
				for (Edge edge : firstTokenOutEdges){
					// get the edge which is of the type STextual relation
					if (edge instanceof STextualRelation){
						STextualRelation sTextualRelation = ((STextualRelation)edge);
						// set the left value
						left = new Long(sTextualRelation.getSStart());
						
						text_ref = new Long(idManager.getNewTextId(sTextualRelation.getSTextualDS().getSElementId()));
						//System.out.println("Setting text_ref to"+ text_ref.toString());
						break;
					}
				}
				
				EList<Edge> lastTokenOutEdges = documentGraph.getOutEdges(lastOverlappedToken.getSId());
				if (lastTokenOutEdges == null)
					throw new RelANNISModuleException("The token "+lastOverlappedToken.getSId()+ " has no outgoing edges!");
				
				/// find the STextualRelation
				for (Edge edge : lastTokenOutEdges){
					// get the edge which is of the type STextual relation
					if (edge instanceof STextualRelation){
						STextualRelation sTextualRelation = ((STextualRelation)edge);
						// set the left value
						right = new Long(sTextualRelation.getSEnd()-1);
						text_ref = new Long(idManager.getNewTextId(sTextualRelation.getSTextualDS().getSElementId()));
						break;
					}
				}
				
				
			} else {
				// IGNORE CASE
			}
			// the node is a span or structure
		}
		if (this.idManager.hasVirtualTokenization()){
			EList<Long> virtualisedTokenIds = this.idManager.getVirtualisedTokenId(node.getSElementId());
			if (virtualisedTokenIds != null){
				left = 0l;
				right = 0l;
				Pair<Long,Long> leftRight = this.idManager.getLeftRightVirtualToken(virtualisedTokenIds.get(0), virtualisedTokenIds.get(virtualisedTokenIds.size()-1));
				left_token = leftRight.getLeft(); // get this
				right_token = leftRight.getRight(); // get this
				span = null;
			}
		}
		
		this.writeNodeTabEntry(id, text_ref, corpus_ref, layer, name, left, right, token_index, left_token, right_token, seg_index, seg_name, span);
		
		if (node.getSAnnotations() != null){
			this.mapSNodeAnnotations(node, id, node.getSAnnotations());
		}
	}
	
	protected void writeNodeTabEntry(
			Long id, Long text_ref, Long corpus_ref,
			String layer, String name,
			Long left, Long right, Long token_index,
			Long left_token, Long right_token,
			Long seg_index, String seg_name, String span){
		
		Long transactionId = this.nodeTabWriter.beginTA();
		EList<String> tableEntry = new BasicEList<String>();
		tableEntry.add(id.toString());
		tableEntry.add(text_ref.toString());
		tableEntry.add(corpus_ref.toString());
		tableEntry.add(layer);
		tableEntry.add(name);
		tableEntry.add(left.toString());
		tableEntry.add(right.toString());
		if (token_index == null){
			tableEntry.add("NULL");
		} else {
			tableEntry.add(token_index.toString());
		}
		tableEntry.add(left_token.toString());
		tableEntry.add(right_token.toString());
		if (seg_index == null){
			tableEntry.add("NULL");
		} else {
			tableEntry.add(seg_index.toString());
		}
		if (seg_name == null){
			tableEntry.add("NULL");
		} else {
			tableEntry.add(seg_name);
		}
		if (span == null){
			tableEntry.add("NULL");
		} else {
			tableEntry.add(span);
		}
		try {
			this.nodeTabWriter.addTuple(transactionId, tableEntry);
			this.nodeTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			throw new RelANNISModuleException("Could not write to the node tab TupleWriter. Exception is: "+e.getMessage(),e);
		}
		
	}
	
// ======================== Mapping of SNodeAnnotations =======================
	
	protected void mapSNodeAnnotations(SNode node, Long node_ref, EList<SAnnotation> annotations){
		for (SAnnotation annotation : annotations){
			this.mapSNodeAnnotation(node, node_ref, annotation);
		}
	}
	
	protected void mapSNodeAnnotation(Long node_ref, String namespace, String name, String value){
		Long transactionId = this.nodeAnnoTabWriter.beginTA();
		EList<String> tableEntry = new BasicEList<String>();
		tableEntry.add(node_ref.toString());
		tableEntry.add(namespace);
		tableEntry.add(name);
		tableEntry.add(value);
		try {
			this.nodeAnnoTabWriter.addTuple(transactionId, tableEntry);
			this.nodeAnnoTabWriter.commitTA(transactionId);
		} catch (FileNotFoundException e) {
			throw new RelANNISModuleException("Could not write to the node annotation tab TupleWriter. Exception is: "+e.getMessage(),e);
		}
	}
	
	protected void mapSNodeAnnotation(SNode node, Long node_ref, SAnnotation annotation){
		String namespace = annotation.getNamespace();
		if (namespace != null){
			if (namespace.equals("null")){
				namespace = DEFAULT_NS;
			}
		} else {
			namespace = DEFAULT_NS;
		}
		
		String name = annotation.getSName();
		String value = annotation.getSValueSTEXT();
		mapSNodeAnnotation(node_ref,namespace,name,value);
		
	}

	
	
	
	
	
}
