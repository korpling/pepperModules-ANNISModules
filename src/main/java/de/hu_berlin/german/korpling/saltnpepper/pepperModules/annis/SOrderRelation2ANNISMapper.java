/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import java.util.HashSet;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STimelineRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import java.util.HashMap;
import java.util.Map;

public class SOrderRelation2ANNISMapper extends SRelation2ANNISMapper {

  private static final Logger log = LoggerFactory.getLogger(SOrderRelation2ANNISMapper.class);

  public SOrderRelation2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, 
          Map<SToken, Long> token2index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    super(idManager, documentGraph, token2index, nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, edgeAnnoTabWriter, componentTabWriter, parentMapper);

  }

  private boolean appendIndex = false;
  private int segPathCounter = 0;

  private long seg_index = 0l;

  /**
   * This method maps the given {@link STimelineRelation} timelineRelation with
   * use of the minimal timeline relations.
   *
   * @param timelineRelation The {@link STimelineRelation} to map
   * @param minimalTimelineRelations The set of {@link STimelineRelation}
   * objects indexed by their start Point of Time
   * @param minimalTimelineRelationList A list of minimal timeline relations
   * @param minimal States whether the given {@link STimelineRelation} is
   * minimal, i.e. there is no other timeline which is contained in the given
   * one.
   */
  private void mapSTimeline(STimelineRelation timelineRelation, Map<String, STimelineRelation> minimalTimelineRelations, EList<STimelineRelation> minimalTimelineRelationList, boolean minimal) {

    // define a new component
    this.currentComponentId = idManager.getGlobal().getNewComponentId();
    this.currentComponentType = "c";
    this.currentComponentLayer = "VIRTUAL";
    this.currentComponentName = "timelineRelationMapping";
    this.mapComponent2ANNIS();

    // set the corpus reference
    Long corpus_ref = this.idManager.getNewCorpusTabId(this.documentGraph.getSDocument().getSId());
    Long token_left;
    Long token_right;

    SToken tok = timelineRelation.getSToken();
    String virtualSpanSId = tok.getSId();
    virtualSpanSId = virtualSpanSId + "_virtualSpan";
    String virtualSpanName = tok.getSName() + "_virtualSpan";

    VirtualNodeID virtualSpanId = idManager.getVirtualNodeId(virtualSpanSId);
    EList<Long> virtualTokenIds = new BasicEList<>();

    if (minimal) { // map a timeline which has only one virtual token
      String virtualTokenName = tok.getSName() + "_virtualToken";

      VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);

      virtualTokenIds.add(virtualTokenId.getNodeID());
      Long tokenIndex = (long) minimalTimelineRelationList.indexOf(timelineRelation);
      token_left = tokenIndex;
      token_right = tokenIndex;

      if (virtualTokenId.isFresh()) { // this is a new virtual token
        // map the virtual token
        super.writeNodeTabEntry(virtualTokenId.getNodeID(), 0l,
                corpus_ref, DEFAULT_LAYER, virtualTokenName, tokenIndex, tokenIndex,
                tokenIndex, token_left, token_right, null, null, " ",
                false);
      } // this is a new virtual token

    } // map a timeline which has only one virtual token
    else { // map a timeline which has more than one virtual token
			/*
       System.out.println("Count of minimal timelines: "+ minimalTimelineRelationList.size());
       System.out.println("Mapping timeline for token "+ timelineRelation.getSToken().getSName());
       System.out.println("Start POT "+ timelineRelation.getSStart());
       System.out.println("End POT "+ timelineRelation.getSEnd());
       */
      EList<STimelineRelation> overlappedTimelines = new BasicEList<>();

      //System.out.println(minimalTimelineRelations.keySet());
      for (Integer i = timelineRelation.getSStart(); i < timelineRelation.getSEnd(); i++) { // get all timeline-overlapped timelines
        for (String key : minimalTimelineRelations.keySet()) {
          if (key.equals(i.toString())) {
						//System.out.println("Found key");
            //System.out.println("Found POT "+i);
            overlappedTimelines.add(minimalTimelineRelations.get(key));
          }
        }
      }// get all timeline-overlapped timelines
      {// set token_left and token_right
        token_left = (long) minimalTimelineRelationList.indexOf(overlappedTimelines.get(0));
        token_right = (long) minimalTimelineRelationList.indexOf(overlappedTimelines.get(overlappedTimelines.size() - 1));
      }// set token_left and token_right
      //int virtualTokenPostfix = 0;
      for (STimelineRelation overlappedRelation : overlappedTimelines) {// create the list of virtual token ids
        SToken overlappedToken = overlappedRelation.getSToken();
        String virtualTokenName = overlappedToken.getSName() + "_virtualToken";//+ virtualTokenPostfix;
        //virtualTokenPostfix = virtualTokenPostfix + 1;

        VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);
        virtualTokenIds.add(virtualTokenId.getNodeID());
      } // create the list of virtual token ids
    } // map a timeline which has more than one virtual token

    // register the mapping
    this.idManager.registerTokenVirtMapping(tok.getSId(), virtualSpanId.getNodeID(), virtualTokenIds);

    if (virtualSpanId.isFresh()) { // map the virtual span and the Token annotations
      // get the segmentation information
      Long segId = null;
      String segName = null;
      String span = null;
      SegmentationInfo segmentInfo = this.idManager.getSegmentInformation(tok.getSId());
      if (segmentInfo != null) {
        segId = segmentInfo.getANNISId();
        segName = segmentInfo.getSegmentationName();
        span = segmentInfo.getSpan();
      }
      // map the span
      super.writeNodeTabEntry(virtualSpanId.getNodeID(), 0l,
              corpus_ref, DEFAULT_LAYER, virtualSpanName, token_left, token_right,
              null, token_left, token_right, segId, segName, span, isRoot(tok));
      // map the virtual anno
      this.mapSNodeAnnotation(virtualSpanId.getNodeID(), DEFAULT_LAYER + "_virtual", segName, span);
      // map the token annotations
      if (tok.getSAnnotations() != null) {
        for (SAnnotation anno : tok.getSAnnotations()) {
          this.mapSNodeAnnotation(null, virtualSpanId.getNodeID(), anno);
        }
      }
    } // map the virtual span and the Token annotations

    {// now, we will create the rank entries
      /**
       * for the timeline: create: NULL -> virtSpan virtSpan -> virtTok1 ...
       * virtSpan -> virtTokn
       */
      this.prePostOrder = (long) 1;
      Long parentRank = idManager.getGlobal().getNewRankId();

      {// Step 1: map all virtSpan -> virtTok_i
        for (Long tokId : virtualTokenIds) {
					// mapping of rank entry from virtSpan -> virtTok_i is omitted
          // thus we don't output the rankEntry but only
          // update the pre/post order values

          // pre
          this.getNewPPOrder();
          // post
          this.getNewPPOrder();

        }
      }

      {// map NULL -> virtSpan
        EList<String> rankEntry = new BasicEList<>();

        rankEntry.add(parentRank.toString());
        rankEntry.add("0");
        rankEntry.add(this.getNewPPOrder().toString());
        rankEntry.add("" + virtualSpanId.getNodeID());
        rankEntry.add(this.currentComponentId.toString());
        rankEntry.add("NULL");
        rankEntry.add("0");

        addTuple(OutputTable.RANK, rankEntry);
      }
    }
  }

  /**
   * This method sorts the {@link STimelineRelation} objects given as parameter
   * by their start Point of Time
   *
   * @param sTimelineRelations the {@link STimelineRelation} objects to sort
   * @return The sorted {@link STimelineRelation} objects
   */
  private Map<String, STimelineRelation> sortTimelineRelationsByStart(EList<STimelineRelation> sTimelineRelations) {
    HashMap<String, STimelineRelation> retVal = new HashMap<>();
    for (STimelineRelation t : sTimelineRelations) {
      String startTime = t.getSStart().toString();
      if (retVal.containsKey(startTime)) {
        log.warn("sortTimelineRelationsByStart: Both the timeline for Token " + t.getSToken().getSId() + " and "
                + retVal.get(startTime).getSToken().getSId() + " is " + t.getSStart());
      }
      retVal.put(startTime, t);
    }
    return retVal;
  }

  /**
   * This method creates a virtual tokenization for all {@link SToken} which are
   * overlapped by a {@link STimelineRelation}
   */
  public void createVirtualTokenization() {
    EList<STimelineRelation> timelineRelations = this.documentGraph.getSTimelineRelations();

    EList<String> pointsOfTime = this.documentGraph.getSTimeline().getSPointsOfTime();
    /*
     for (String pot : pointsOfTime){
     System.out.println("POT: "+pot);
     }*/
		//System.out.println("SOrderRelation2RelANNOSMapper : Points of time are " + pointsOfTime);

    HashSet<STimelineRelation> nonMinimalTimelineRelations = new HashSet<>();
    HashSet<STimelineRelation> minimalTimelineRelations = new HashSet<>();
    EList<STimelineRelation> minimalTimelineRelationList = new BasicEList<>();

    if (timelineRelations != null && !timelineRelations.isEmpty()) {
      {// get minimal and non-minimal timelines
        for (STimelineRelation timelineRel1 : timelineRelations) {
          boolean relationIsMinimal = true;
          for (STimelineRelation timelineRel2 : timelineRelations) { // for all other timeline relations
            if (!timelineRel1.equals(timelineRel2)) {
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
              if ((timelineRel2.getSStart() >= timelineRel1.getSStart() && timelineRel2.getSEnd() < timelineRel1.getSEnd())
                      || (timelineRel2.getSStart() > timelineRel1.getSStart() && timelineRel2.getSEnd() <= timelineRel1.getSEnd())) { // a timeline relation is non-minimal, if there is another relation which is shorter
                relationIsMinimal = false;
                break;
              }
            }
          } // for all other timeline relations
          if (relationIsMinimal) {
            int interval = (timelineRel1.getSEnd() - timelineRel1.getSStart());
            //System.out.println("Found minimal STimelineRelation with interval "+interval+" and POT "+timelineRel1.getSStart()+" for Token "+ timelineRel1.getSToken().getSName());
            minimalTimelineRelations.add(timelineRel1);
            minimalTimelineRelationList.add(timelineRel1);
          } else {
            nonMinimalTimelineRelations.add(timelineRel1);
          }
        }
      }// get minimal and non-minimal timelines
      Map<String, STimelineRelation> minimalTimelineRelationsSortedByStart = this.sortTimelineRelationsByStart(minimalTimelineRelationList);

      for (STimelineRelation t : minimalTimelineRelations) {// map the token to a virtual tokenization
        this.mapSTimeline(t, minimalTimelineRelationsSortedByStart, minimalTimelineRelationList, true);
      }
      for (STimelineRelation t : nonMinimalTimelineRelations) {// map the non-minimal token
        this.mapSTimeline(t, minimalTimelineRelationsSortedByStart, minimalTimelineRelationList, false);
      }

      { // create the sorted virtual token id list
        EList<STimelineRelation> sortedMinimalTimelineRelationList = new BasicEList<>();
        EList<Long> sortedMinimalIdList = new BasicEList<>();
        for (String pot : pointsOfTime) {
          for (String key : minimalTimelineRelationsSortedByStart.keySet()) {
            if (key.equals(pot)) {
              //System.out.println("Found minimal key");
              sortedMinimalTimelineRelationList.add(minimalTimelineRelationsSortedByStart.get(key));
            }
          }
        }
        for (STimelineRelation tr : sortedMinimalTimelineRelationList) {
          EList<Long> tr_IdS = this.idManager.getVirtualisedTokenId(tr.getSToken().getSId());
          if (tr_IdS.size() > 1) {
            log.warn("minimal timeline relation has more than one virtual token id");
          } else if (tr_IdS.isEmpty()) {
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
  public void run() {
    // this mapper does nothing in parallel, thus all work is done in mapSRelations2ANNIS
  }

  @Override
  public void mapSRelations2ANNIS(EList<? extends SNode> sRelationRoots,
          STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType) {

    beginTransaction();

    if (sRelationRoots != null && sRelationRoots.size() != 0) {

      appendIndex = sRelationRoots.size() > 1;

      // Step 1: traverse the SOrderRelations
      for (SNode node : sRelationRoots) {
        EList<SNode> singleRootList = new BasicEList<>();
        singleRootList.add(node);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "", this);
        this.segPathCounter = this.segPathCounter + 1;
        this.seg_index = 0l;
      }

      // Step 2: map the timeline relations, if existent
      if (this.documentGraph.getSTimelineRelations() != null) {
        if (this.documentGraph.getSTimelineRelations().size() > 0) {
          this.createVirtualTokenization();
        }
      }

    }

    commitTransaction();

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
    String name;
    if (appendIndex) {
      name = this.currentTraversionSType + segPathCounter;
    } else {
      name = this.currentTraversionSType;
    }
    
    Long segIndex = this.seg_index;
    this.seg_index = this.seg_index + 1;
    String segSpan = "NULL";
    if (currNode instanceof SToken) {
      SDocumentGraph g = ((SToken) currNode).getSDocumentGraph();
      if (g != null) {
        segSpan = g.getSText((SToken) currNode);
      }
    } else {
      // find the annotation value with the same name as the segmentation chain
      EList<SAnnotation> annos = currNode.getSAnnotations();
      if (annos != null) {
        for (SAnnotation a : annos) {
          if (name.equals(a.getSName())) {
            segSpan = a.getSValueSTEXT();
            break;
          }
        }
      }
    }
    this.idManager.addSegmentInformation(currNode.getSId(), segIndex, name, segSpan);
    getOrderStats().addOrderRelation(name);
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
    if (edge == null) {
      returnVal = true;
    } else {
      if (edge instanceof SOrderRelation) {
        returnVal = true;
      }
    }

    return returnVal;
  }

}
