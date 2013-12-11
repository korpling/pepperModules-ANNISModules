package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

public class SNode2RelANNISMapper {

	private IdManager idManager;
	
	private ConcurrentHashMap<SElementId,Boolean> nodeWasMapped;
	
	private TupleWriter nodeTabWriter;
	private TupleWriter nodeAnnoTabWriter;
	
	private EList<SToken> tokenSortedByLeft;
	
	private SDocumentGraph documentGraph;
	
	SNode2RelANNISMapper(IdManager manager,SDocumentGraph documentGraph,  TupleWriter nodeTabWriter, TupleWriter nodeAnnoTabWriter){
		this.idManager = manager;
		this.nodeWasMapped = new ConcurrentHashMap<SElementId, Boolean>();
		this.documentGraph = documentGraph;
		
		this.tokenSortedByLeft = documentGraph.getSortedSTokenByText();
		
		this.nodeTabWriter = nodeTabWriter;
		this.nodeAnnoTabWriter = nodeAnnoTabWriter;
	}
	
	private void writeNodeTabEntry(
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
	
	void mapSNode(SNode node){
		/// get the SElementId of the node since we will need it many times
		SElementId nodeSElementId = node.getSElementId();
		
		/// if the node already has a nodeId, it was already mapped
		Pair<Long,Boolean> idPair = this.idManager.getNewNodeId(nodeSElementId);
		if (idPair.getRight().booleanValue() == false){
			// the node is not new
			return;
		}
		// initialise all variables which will be used for the node.tab 
		// get the RAId
		Long id = idPair.getLeft();
		// get the text ref
		Long text_ref = null;
		// get the document ref
		Long corpus_ref = this.idManager.getNewDocumentId(this.documentGraph.getSDocument().getSElementId());
		// get the layer if there is one
		String layer = "default_ns";
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
		Long seg_index = null;
		// initialise the segment name
		String seg_name = null;
		// initialise the span
		String span = null;
		
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
					right = new Long(sTextualRelation.getSEnd() -1);
					// set the reference to the text
					text_ref = new Long(idManager.getNewTextId(sTextualRelation.getSTextualDS().getSElementId()));
					// set the overlapped text
					span = sTextualRelation.getSTextualDS().getSText().substring(left.intValue(),right.intValue());
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
						right = new Long(sTextualRelation.getSEnd() -1 );
						text_ref = new Long(idManager.getNewTextId(sTextualRelation.getSTextualDS().getSElementId()));
						break;
					}
				}
				
				
			} else {
				// IGNORE CASE
			}
			// the node is a span or structure
		}
		
		this.writeNodeTabEntry(id, text_ref, corpus_ref, layer, name, left, right, token_index, left_token, right_token, seg_index, seg_name, span);
		
		if (node.getSAnnotations() != null){
			this.mapSNodeAnnotations(node, id, node.getSAnnotations());
		}
	}
	
	void mapSNodes(EList<SNode> nodes){
		for (SNode node : nodes){
			this.mapSNode(node);
		}
	}
	
	private void mapSNodeAnnotation(SNode node, Long node_ref, SAnnotation annotation){
		String namespace = annotation.getNamespace();
		if (namespace.equals("null")){
			namespace = "default_ns";
		}
		String name = annotation.getSName();
		String value = annotation.getSValueSTEXT();
		
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
	
	private void mapSNodeAnnotations(SNode node, Long node_ref, EList<SAnnotation> annotations){
		for (SAnnotation annotation : annotations){
			this.mapSNodeAnnotation(node, node_ref, annotation);
		}
	}
	
	
}
