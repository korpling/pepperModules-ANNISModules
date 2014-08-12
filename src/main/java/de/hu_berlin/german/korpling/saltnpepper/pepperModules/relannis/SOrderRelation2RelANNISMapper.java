package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Hashtable;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STimelineRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class SOrderRelation2RelANNISMapper extends SRelation2RelANNISMapper  {
	
	private static final Logger log = LoggerFactory.getLogger(SOrderRelation2RelANNISMapper.class);
	
	public SOrderRelation2RelANNISMapper(IdManager idManager,
			SDocumentGraph documentGraph, TupleWriter nodeTabWriter,
			TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
			TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter) {
		
		super(idManager, documentGraph, nodeTabWriter, nodeAnnoTabWriter,
				rankTabWriter, edgeAnnoTabWriter, componentTabWriter);
		
	}
	private Long seg_index = 1l;
	
	
	
	/**
	 * This method maps the given {@link STimelineRelation} timelineRelation with use of the minimal timeline relations.
	 * @param timelineRelation The {@link STimelineRelation} to map
	 * @param minimalTimelineRelations The set of {@link STimelineRelation} objects indexed by their start Point of Time
	 * @param minimalTimelineRelationList A list of minimal timeline relations
	 * @param minimal States whether the given {@link STimelineRelation} is minimal, i.e. there is no other timeline which is contained in the given one.
	 */
	private void mapSTimeline(STimelineRelation timelineRelation, Hashtable<String,STimelineRelation> minimalTimelineRelations,EList<STimelineRelation> minimalTimelineRelationList,boolean minimal){
		
		// define a new component
		this.currentComponentId = idManager.getNewComponentId();
		this.currentComponentType = "c";
		this.currentComponentLayer = "VIRTUAL";
		this.currentComponentName = "timelineRelationMapping";
		this.mapComponent2RelANNIS();
		
		// set the corpus reference
		Long corpus_ref = this.idManager.getNewCorpusTabId(this.documentGraph.getSDocument().getSElementId());
		Long token_left = null;
		Long token_right = null;
		
		SToken tok = timelineRelation.getSToken();
		String virtualSpanSId = tok.getSId();
		virtualSpanSId = virtualSpanSId+"_virtualSpan";
		String virtualSpanName = tok.getSName()+"_virtualSpan";
		
		IdManager.VirtualNodeID virtualSpanId = this.idManager.getVirtualNodeId(virtualSpanSId);
		EList<Long> virtualTokenIds = new BasicEList<Long>();
		
		if (minimal)
		{ // map a timeline which has only one virtual token
			String virtualTokenName = tok.getSName() + "_virtualToken";
			
			IdManager.VirtualNodeID virtualTokenId = this.idManager.getVirtualNodeId(virtualTokenName);

			virtualTokenIds.add(virtualTokenId.getNodeID());
			
			if (virtualTokenId.isFresh())
			{ // this is a new virtual token
				Long tokenIndex = (long)minimalTimelineRelationList.indexOf(timelineRelation);
				token_left = tokenIndex;
				token_right = tokenIndex;
				
				
				
				// map the virtual token
				super.writeNodeTabEntry(virtualTokenId.getNodeID(), 0l, 
						corpus_ref, DEFAULT_LAYER, virtualTokenName, 0l, 0l, tokenIndex, token_left, token_right, null, null, "");
			} // this is a new virtual token
	
		} // map a timeline which has only one virtual token
		else
		{ // map a timeline which has more than one virtual token
			/*
			System.out.println("Count of minimal timelines: "+ minimalTimelineRelationList.size());
			System.out.println("Mapping timeline for token "+ timelineRelation.getSToken().getSName());
			System.out.println("Start POT "+ timelineRelation.getSStart());
			System.out.println("End POT "+ timelineRelation.getSEnd());
			*/
			EList<STimelineRelation> overlappedTimelines = new BasicEList<STimelineRelation>();
			
			//System.out.println(minimalTimelineRelations.keySet());
			for (Integer i = timelineRelation.getSStart() ; i < timelineRelation.getSEnd() ; i++)
			{ // get all timeline-overlapped timelines
				for (String key : minimalTimelineRelations.keySet()){
					if ( key.equals(i.toString())){
						//System.out.println("Found key");
						//System.out.println("Found POT "+i);
						overlappedTimelines.add(minimalTimelineRelations.get(key));
					}
				}
			}// get all timeline-overlapped timelines
			{// set token_left and token_right
				token_left = (long) minimalTimelineRelationList.indexOf(overlappedTimelines.get(0));
				token_right = (long) minimalTimelineRelationList.indexOf(overlappedTimelines.get(overlappedTimelines.size()-1));
			}// set token_left and token_right
			//int virtualTokenPostfix = 0;
			for (STimelineRelation overlappedRelation : overlappedTimelines)
			{// create the list of virtual token ids
				SToken overlappedToken = overlappedRelation.getSToken();
				String virtualTokenName = overlappedToken.getSName() + "_virtualToken";//+ virtualTokenPostfix;
				//virtualTokenPostfix = virtualTokenPostfix + 1;
				
				IdManager.VirtualNodeID virtualTokenId = this.idManager.getVirtualNodeId(virtualTokenName);
				virtualTokenIds.add(virtualTokenId.getNodeID());
			} // create the list of virtual token ids
		} // map a timeline which has more than one virtual token
		
		// register the mapping
		this.idManager.registerTokenVirtMapping(tok.getSElementId(), virtualSpanId.getNodeID(), virtualTokenIds);
		
		if (virtualSpanId.isFresh())
		{ // map the virtual span and the Token annotations
			// get the segmentation information
			Long segId = null;
			String segName = null;
			String span = null;
			SegmentationInfo segmentInfo = this.idManager.getSegmentInformation(tok.getSElementId());
			if (segmentInfo != null){
				segId = segmentInfo.getRelANNISId();
				segName = segmentInfo.getSegmentationName();
				span = segmentInfo.getSpan();
			}
			// map the span
			super.writeNodeTabEntry(virtualSpanId.getNodeID(), 0l, 
					corpus_ref, DEFAULT_LAYER, virtualSpanName, 0l, 0l, null, token_left, token_right, segId, segName, span);
			// map the virtual anno
			this.mapSNodeAnnotation(virtualSpanId.getNodeID(), DEFAULT_LAYER+"_virtual", "anno1", span);
			// map the token annotations
			if (tok.getSAnnotations() != null){
				for (SAnnotation anno : tok.getSAnnotations()){
					this.mapSNodeAnnotation(null, virtualSpanId.getNodeID(), anno);
				}
			}
		} // map the virtual span and the Token annotations
		
		{// now, we will create the rank entries
			/**
			 * for the timeline:
			 * create:
			 *	NULL -> virtSpan
			 *	virtSpan -> virtTok1
			 *   ...
			 *  virtSpan -> virtTokn
			 */
			this.prePostOrder = (long) 1;
			Long parentRank = this.idManager.getNewRankId();
			{// Step 1: map all virtSpan -> virtTok_i
				for (Long tokId : virtualTokenIds){
					EList<String> rankEntry = new BasicEList<String>();

					rankEntry.add(this.idManager.getNewRankId().toString());
					rankEntry.add(this.getNewPPOrder().toString());
					rankEntry.add(this.getNewPPOrder().toString());
					rankEntry.add(tokId.toString());
					rankEntry.add(this.currentComponentId.toString());
					rankEntry.add(parentRank.toString());
					rankEntry.add("1");
					Long transactionId = this.rankTabWriter.beginTA();
					try {
						this.rankTabWriter.addTuple(transactionId, rankEntry);
						this.rankTabWriter.commitTA(transactionId);
					} catch (FileNotFoundException e) {
						throw new PepperModuleException("Could not write to the rank tab TupleWriter. Exception is: "+e.getMessage(),e);
					}
				}
			}
			{// step 2: map NULL -> virtSpan
				EList<String> rankEntry = new BasicEList<String>();

				rankEntry.add(parentRank.toString());
				rankEntry.add("0");
				rankEntry.add(this.getNewPPOrder().toString());
				rankEntry.add("" + virtualSpanId.getNodeID());
				rankEntry.add(this.currentComponentId.toString());
				rankEntry.add(new String("NULL"));
				rankEntry.add("0");
				Long transactionId = this.rankTabWriter.beginTA();
				try {
					this.rankTabWriter.addTuple(transactionId, rankEntry);
					this.rankTabWriter.commitTA(transactionId);
				} catch (FileNotFoundException e) {
					throw new PepperModuleException("Could not write to the rank tab TupleWriter. Exception is: "+e.getMessage(),e);
				}
			}
		}
	}
	
	/**
	 * This method sorts the {@link STimelineRelation} objects given as parameter by their start Point of Time
	 * @param sTimelineRelations the {@link STimelineRelation} objects to sort
	 * @return The sorted {@link STimelineRelation} objects
	 */
	private Hashtable<String,STimelineRelation> sortTimelineRelationsByStart(EList<STimelineRelation> sTimelineRelations){
		Hashtable<String,STimelineRelation> retVal = new Hashtable<String, STimelineRelation>();
		for (STimelineRelation t : sTimelineRelations){
			if (retVal.contains(t.getSStart()))
			{
				log.warn("sortTimelineRelationsByStart: Both the timeline for Token "+t.getSToken().getSId()+ " and "+retVal.get(t).getSToken().getSId()+ " is "+t.getSStart());
			}
			retVal.put(t.getSStart().toString(), t);
		}
		return retVal;
	}
	
	/**
	 * This method creates a virtual tokenization for all {@link SToken} which are overlapped by a {@link STimelineRelation}
	 */
	public void createVirtualTokenization(){
		EList<STimelineRelation> timelineRelations = this.documentGraph.getSTimelineRelations();
		
		EList<String> pointsOfTime = this.documentGraph.getSTimeline().getSPointsOfTime();
		/*
		for (String pot : pointsOfTime){
			System.out.println("POT: "+pot);
		}*/
		//System.out.println("SOrderRelation2RelANNOSMapper : Points of time are " + pointsOfTime);
		
		HashSet<STimelineRelation> nonMinimalTimelineRelations = new HashSet<STimelineRelation>();
		HashSet<STimelineRelation> minimalTimelineRelations = new HashSet<STimelineRelation>();
		EList<STimelineRelation> minimalTimelineRelationList = new BasicEList<STimelineRelation>();
		
		if (timelineRelations != null && !timelineRelations.isEmpty()){
			{// get minimal and non-minimal timelines
				for (STimelineRelation timelineRel1 : timelineRelations)
				{
					boolean relationIsMinimal = true;
					for (STimelineRelation timelineRel2 : timelineRelations)
					{ // for all other timeline relations
						if (timelineRel1.equals(timelineRel2)){
							continue;
						} else {
							// ------------------ start(t2) >= start(t1) & end(t2) < end(t1)
							// t1 :      |        |
							// t2 :      |     |
							// ------------------ start(t2) > start(t1) & end(t2) <= end(t1)
							// t1 :      |        |
							// t2 :         |     |
							// ------------------ start(t2) > start(t1) & end(t2) < end(t1)
							// t1 :      |        |
							// t2 :        |     |
							//  t1.start < t2.start && t1.end
							if ( ( timelineRel2.getSStart() >= timelineRel1.getSStart() && timelineRel2.getSEnd() < timelineRel1.getSEnd() )
										||
								 ( timelineRel2.getSStart() > timelineRel1.getSStart() && timelineRel2.getSEnd() <= timelineRel1.getSEnd() )	
									)
							{ // a timeline relation is non-minimal, if there is another relation which is shorter
								relationIsMinimal = false;
								break;
							}
						}
					} // for all other timeline relations
					if (relationIsMinimal){
						int interval =  (timelineRel1.getSEnd() - timelineRel1.getSStart());
						//System.out.println("Found minimal STimelineRelation with interval "+interval+" and POT "+timelineRel1.getSStart()+" for Token "+ timelineRel1.getSToken().getSName());
						minimalTimelineRelations.add(timelineRel1);
						minimalTimelineRelationList.add(timelineRel1);
					} else {
						nonMinimalTimelineRelations.add(timelineRel1);
					}
				}
			}// get minimal and non-minimal timelines
			Hashtable<String,STimelineRelation> minimalTimelineRelationsSortedByStart = this.sortTimelineRelationsByStart(minimalTimelineRelationList);
			
			for (STimelineRelation t : minimalTimelineRelations)
			{// map the token to a virtual tokenization
				this.mapSTimeline(t,minimalTimelineRelationsSortedByStart,minimalTimelineRelationList,true);
			}
			for (STimelineRelation t : nonMinimalTimelineRelations)
			{// map the non-minimal token
				this.mapSTimeline(t,minimalTimelineRelationsSortedByStart,minimalTimelineRelationList,false);
			}
			
			{ // create the sorted virtual token id list
				EList<STimelineRelation> sortedMinimalTimelineRelationList = new BasicEList<STimelineRelation>();
				EList<Long> sortedMinimalIdList = new BasicEList<Long>();
				for (String pot : pointsOfTime)
				{ 
					for (String key : minimalTimelineRelationsSortedByStart.keySet()){
						if ( key.equals(pot)){
							//System.out.println("Found minimal key");
							sortedMinimalTimelineRelationList.add(minimalTimelineRelationsSortedByStart.get(key));
						}
					}
				}
				for (STimelineRelation tr : sortedMinimalTimelineRelationList){
					EList<Long> tr_IdS = this.idManager.getVirtualisedTokenId(tr.getSToken().getSElementId());
					if (tr_IdS.size() > 1){
						log.warn("minimal timeline relation has more than one virtual token id");
					}
					else if(tr_IdS.isEmpty()) {
						log.warn("token {} is not mapped to virtual one", tr.getSToken().getSId(), tr_IdS);
					} else {
						sortedMinimalIdList.add(tr_IdS.get(0));
					}
				}
				this.idManager.registerVirtualTokenIdList(sortedMinimalIdList);
			} // create the sorted virtual token id list
		}
		
	}

	@Override
	public void mapSRelations2RelANNIS(EList<SNode> sRelationRoots,
			STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType) {
		
		
		
		if (sRelationRoots != null && sRelationRoots.size() != 0){
			
			// Step 1: traverse the SOrderRelations
			for (SNode node : sRelationRoots){
				EList<SNode> singleRootList = new BasicEList<SNode>();
				singleRootList.add(node);
						
				this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST,"", this);
				this.seg_index = 0l;
			}
			
			// Step 2: map the timeline relations, if existent
			if (this.documentGraph.getSTimelineRelations() != null){
				if (this.documentGraph.getSTimelineRelations().size() > 0){
					this.createVirtualTokenization();
				}
			} 
			
		}
		
	}
	
// ========================= Graph Traversion =================================
	
	@Override
	public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SNode currNode, SRelation sRelation,
			SNode fromNode, long order) {
		
		
		//if (sRelation != null & sRelation instanceof SOrderRelation){
			//System.out.println("found relation "+ fromNode.getSName() +" ->["+sRelation.getSId()+"] "+currNode.getSName());
			//System.out.println("Traversal id is: "+traversalId);
			//if (sRelation.getSTypes() != null){
				//if (sRelation.getSTypes().size() > 0){
					// set the segName, segIndex and segSpan
					String name = null;
					if (sRelation == null){
						name = this.currentTraversionSType;
					} else if (sRelation.getSTypes() == null){
						name = this.currentTraversionSType;
					} else {
						name = sRelation.getSTypes().get(0);
					}
					Long segIndex = this.seg_index;
					this.seg_index = this.seg_index + 1;
					String segSpan = "NULL";
					if(currNode.getSGraph() instanceof SDocumentGraph)
					{
						SDocumentGraph g = (SDocumentGraph) currNode.getSGraph();
						segSpan = g.getSText(currNode);
					}
					this.idManager.addSegmentInformation(currNode.getSElementId(), segIndex, name, segSpan);
					
					//System.out.println("SType is "+this.currentComponentName);
				//}
			//}
		//}
		
		
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
			SNode currNode, SRelation edge, SNode fromNode, long order) {
		
		// do nothing
		
	}

	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SRelation edge, SNode currNode, long order) {
		boolean returnVal = false;
		
		// only traverse on, if the current edge is null (top rank) or instance of SOrderRelation
		if (edge == null){
			returnVal = true;
		} else {
			if (edge instanceof SOrderRelation){
				returnVal = true;
			} 
		}
		
		return returnVal;
	}

	
	
}
