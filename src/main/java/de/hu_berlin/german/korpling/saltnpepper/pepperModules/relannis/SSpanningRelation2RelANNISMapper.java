package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.helper.modules.SDataSourceAccessor;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDataSourceSequence;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSequentialDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import org.apache.commons.lang3.tuple.Pair;

public class SSpanningRelation2RelANNISMapper extends SRelation2RelANNISMapper  {

	public SSpanningRelation2RelANNISMapper(IdManager idManager,
			SDocumentGraph documentGraph, TupleWriter nodeTabWriter,
			TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
			TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter) {
		
		super(idManager, documentGraph, nodeTabWriter, nodeAnnoTabWriter,
				rankTabWriter, edgeAnnoTabWriter, componentTabWriter);
		
	}

	@Override
	public void run(){
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
					super.initialiseTraversion(new String("c"), componentLayer, new String("NULL"));
				} else {
					super.initialiseTraversion(new String("c"), componentLayer, this.currentTraversionSType);
				}
				
				// create an EList for the current root
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
				
				this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,traversionType.toString(), this);
				
				// map the component
				this.mapComponent2RelANNIS();
				
			}
		}
	}
	
	@Override
	public void mapSRelations2RelANNIS(EList<SNode> sRelationRoots,
			STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType) {
		this.traversionType = traversionType;
		this.relationTypeName = relationTypeName;
		this.sRelationRoots = sRelationRoots;
		
		
	}

  @Override
  protected void mapRank2RelANNIS(SRelation sRelation, SNode targetNode,
    Long rankId, Long preOrder, Long postOrder, Long parentRank, Long level)
  {
    boolean doMapping = true;
    if(sRelation != null && targetNode instanceof SToken)
    {
      
      if(sRelation.getSAnnotations() != null && !sRelation.getSAnnotations().isEmpty())
      {
        // always do mapping if the edge has annotations
        doMapping = true;
      }
      else
      {
        // otherwise check if the span is continuous
        
        SSpan span = (SSpan) sRelation.getSSource();

        BasicEList<STYPE_NAME> spanRel = new BasicEList<STYPE_NAME>();
        spanRel.add(STYPE_NAME.SSPANNING_RELATION);

        EList<SToken> overlappedToken = 
          documentGraph.getSortedSTokenByText(documentGraph.getOverlappedSTokens(span, spanRel));

        if(!overlappedToken.isEmpty())
        {
          Range rangeOfFirst = getRangeForToken(overlappedToken.get(0));
          Range rangeOfLast = getRangeForToken(overlappedToken.get(overlappedToken.size()-1));

          SDataSourceSequence seq = SaltFactory.eINSTANCE.createSDataSourceSequence();
          seq.setSSequentialDS(rangeOfFirst.textualDS);
          seq.setSStart(rangeOfFirst.start);
          seq.setSEnd(rangeOfLast.end);


          EList<SToken> allTokenInRange = documentGraph.getSTokensBySequence(seq);

          // only do the mapping if the span is not continuous
          if (allTokenInRange != null && allTokenInRange.size() <= overlappedToken.
            size())
          {
            doMapping = false;
          }
        }
      }
    } // end if non-null relation and target node is token
    if(doMapping)
    {
      super.mapRank2RelANNIS(sRelation, targetNode, rankId, preOrder,
        postOrder,
        parentRank, level);
    }
  }
  
  private Range getRangeForToken(SToken tok)
  {
    if(tok != null)
    {
      EList<SRelation> outRelsOfFirstToken = tok.getOutgoingSRelations();
      if (outRelsOfFirstToken != null)
      {
        for (SRelation rel : outRelsOfFirstToken)
        {
          if (rel instanceof STextualRelation)
          {
            Range result = new Range();
            result.start = ((STextualRelation) rel).getSStart();
            result.end = ((STextualRelation) rel).getSEnd();
            result.textualDS = ((STextualRelation) rel).getSTextualDS();
            return result;
          }
        }
      }
    }
    return null;
  }
  
	
// ========================= Graph Traversion =================================
	
	@Override
	public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SNode currNode, SRelation sRelation,
			SNode fromNode, long order) {
		
		// this method behaves exactly as the one in the super class
		super.nodeReached(traversalType, traversalId, currNode, sRelation, fromNode, order);
		
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
		
		// only traverse on, if the current node is a Span and edge is null
		if (currNode instanceof SSpan && edge == null) 
			returnVal = true;
		if ( currNode instanceof SToken && edge instanceof SSpanningRelation )
			returnVal = true;
		
		return returnVal;
	}

  
  private static class Range
  {
    public int start;
    public int end;
    public STextualDS textualDS;
  }
	
	
}
