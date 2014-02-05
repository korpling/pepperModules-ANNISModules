package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.Hashtable;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class SPointingRelation2RelANNISMapper extends SRelation2RelANNISMapper  {

	public SPointingRelation2RelANNISMapper(IdManager idManager,
			SDocumentGraph documentGraph, TupleWriter nodeTabWriter,
			TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
			TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter) {
		
		super(idManager, documentGraph, nodeTabWriter, nodeAnnoTabWriter,
				rankTabWriter, edgeAnnoTabWriter, componentTabWriter);
		
	}

	@Override
	public void mapSRelations2RelANNIS(EList<SNode> sRelationRoots,
			STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType) {
		this.traversionType = traversionType;
		if (sRelationRoots != null && sRelationRoots.size() != 0){
			for (SNode node : sRelationRoots){
				
				String componentLayer = DEFAULT_NS;
				/*
				EList<SLayer> nodeLayer = node.getSLayers();
				if (nodeLayer != null){
					if (nodeLayer.size() > 0){
						if (! "".equals(nodeLayer.get(0))){
							componentLayer = nodeLayer.get(0).toString();
						}
					}
				}*/
				
				if (this.currentTraversionSType== null){
					super.initialiseTraversion(new String("p"), componentLayer, new String("NULL"));
				} else {
					super.initialiseTraversion(new String("p"), componentLayer, this.currentTraversionSType);
					
				}
				//System.out.println("[DEBUG] Mapping pointingRelation with sType "+ this.currentTraversionSType);
				// create an EList for the current root
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				
				this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,traversionType.toString(), this);
				
				// map the component
				this.mapComponent2RelANNIS();
				
			}
		}
		
	}
	
// ========================= Graph Traversion =================================
	
	@Override
	public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SNode currNode, SRelation sRelation,
			SNode fromNode, long order) {
		
		// this method behaves exactly as the one in the super class
		super.nodeReached(traversalType, traversalId, currNode, sRelation, fromNode, order);
		
		if (sRelation != null & sRelation instanceof SPointingRelation){
			//System.out.println("found relation "+ fromNode.getSName() +" ->["+sRelation.getSId()+"] "+currNode.getSName());
			//System.out.println("Traversal id is: "+traversalId);
			if (sRelation.getSTypes() != null){
				if (sRelation.getSTypes().size() > 0){
					this.currentComponentName = sRelation.getSTypes().get(0);
					//System.out.println("SType is "+this.currentComponentName);
				}
			} else {
				this.currentComponentName = SaltFactory.DEFAULT_STYPE;
			}
		}
		
		
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
			SNode currNode, SRelation edge, SNode fromNode, long order) {
		
		// this method behaves exactly as the one in the super class
		super.nodeLeft(traversalType, traversalId, currNode, edge, fromNode, order);
		
	}

	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SRelation edge, SNode currNode, long order) {
		boolean returnVal = false;
		
		// only traverse on, if the current edge is null (top rank) or instance of SPointingRelation
		// only traverse on, if the current edge is null or a dominance relation
		if (edge == null){
			returnVal = true;
		} else {
			if (edge instanceof SPointingRelation){
				if (this.currentTraversionSType == null){
					// traversing super component
					returnVal = true;
				} else {
					// traversing sub component. Only accept, if the SType is correct
					EList<String> edgeSTypes = edge.getSTypes();
					if (edgeSTypes != null){
						if (edgeSTypes.size() > 0){
							if (edgeSTypes.get(0).equals(this.currentTraversionSType)){
								returnVal = true;
							}
						}
					} else {
						// the current SType is NULL, i.e. SALT::NULL
						if (this.currentTraversionSType.equals(SaltFactory.DEFAULT_STYPE)){
							returnVal = true;
						}
						
					}
				}
			} 
		}
		
		return returnVal;
	}

	
	
}
