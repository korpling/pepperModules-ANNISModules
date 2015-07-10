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

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STimelineRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

/**
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class STimelineRelation2ANNISMapper extends SRelation2ANNISMapper {

  private final static Logger log = LoggerFactory.getLogger(STimelineRelation2ANNISMapper.class);

  private final boolean mergeTextsWithTimeline;
  
  public STimelineRelation2ANNISMapper(IdManager idManager, 
          SDocumentGraph documentGraph, Map<SToken, Long> token2Index, 
          TupleWriter nodeTabWriter, TupleWriter nodeAnnoTabWriter, 
          TupleWriter rankTabWriter, TupleWriter edgeAnnoTabWriter, 
          TupleWriter componentTabWriter, Salt2ANNISMapper parentMapper, 
          boolean mergeTextsWithTimeline) {
    super(idManager, documentGraph, token2Index, nodeTabWriter, 
            nodeAnnoTabWriter, rankTabWriter, edgeAnnoTabWriter, componentTabWriter, 
            parentMapper);
    this.mergeTextsWithTimeline = mergeTextsWithTimeline;
  }
  
  private VirtualNodeID mapPointOfTime(long tokenIndex, boolean isRoot) {
    
    String virtualTokenName = "virtualToken" + tokenIndex;
    VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);
    Long corpus_ref = idManager.getNewCorpusTabId(documentGraph.getSDocument().getSId());

    long token_left = tokenIndex;
    long token_right = tokenIndex;
    if (virtualTokenId.isFresh()) {
      super.writeNodeTabEntry(virtualTokenId.getNodeID(),
              0L, corpus_ref, SRelation2ANNISMapper.DEFAULT_LAYER,
              virtualTokenName, tokenIndex, tokenIndex, tokenIndex,
              token_left, token_right, null, null, " ", isRoot);
    }
    
    return virtualTokenId;
  }

  /**
   * This method maps the given {@link STimelineRelation} timelineRelation with
   * use of the virtual tokens.
   *
   * @param timelineRelation The {@link STimelineRelation} to map
   * @param minimalTimelineRelations The set of {@link STimelineRelation}
   * objects indexed by their start Point of Time
   * @param timelineRel2TokenIndex A map from a minimal timeline relation to it's token index
   * @param minimal States whether the given {@link STimelineRelation} is
   * minimal, i.e. there is no other timeline which is contained in the given
   * one.
   */
  private void mapSTimeline(STimelineRelation timelineRelation) {
    currentComponentId = idManager.getGlobal().getNewComponentId();
    currentComponentType = "c";
    currentComponentLayer = "VIRTUAL";
    currentComponentName = "timelineRelationMapping";
    mapComponent2ANNIS();
    Long corpus_ref = idManager.getNewCorpusTabId(documentGraph.getSDocument().getSId());
    Long token_left;
    Long token_right;
    SToken tok = timelineRelation.getSToken();
    if (tok == null) {
      // We can't map timeline relation that don't have a token connected.
      return;
    }
    
    String virtualSpanSId = tok.getSId();
    virtualSpanSId = virtualSpanSId + "_virtualSpan";
    String virtualSpanName = tok.getSName() + "_virtualSpan";
    VirtualNodeID virtualSpanId = idManager.getVirtualNodeId(virtualSpanSId);
    List<Long> overlappedVirtualTokenIds = new ArrayList<>();
   
    
    token_left = (long) timelineRelation.getSStart();
    token_right = (long) timelineRelation.getSEnd()-1;

    for(long i = token_left; i <= token_right; i++) {
       String virtualTokenName = "virtualToken" + i;
      VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);
      overlappedVirtualTokenIds.add(virtualTokenId.getNodeID());
    }
    

    idManager.registerTokenVirtMapping(tok.getSId(), virtualSpanId.getNodeID(), overlappedVirtualTokenIds);
    if (virtualSpanId.isFresh()) {
      Long segId = null;
      String segName = null;
      String textName = null;
      String span;
      SegmentationInfo segmentInfo = idManager.getSegmentInformation(tok.getSId());
      if(segmentInfo == null) {
        span = documentGraph.getSText(tok);
     
        // find any connected texts of the original token for the statistics
        for (Edge e : documentGraph.getOutEdges(tok.getSId())) {
          if (e instanceof STextualRelation) {
            STextualDS text = ((STextualRelation) e).getSTextualDS();
            textName = text.getSName();
          }
        }
      } else {
        segId = segmentInfo.getANNISId();
        segName = segmentInfo.getSegmentationName();
        span = segmentInfo.getSpan();
      }
      writeNodeTabEntry(virtualSpanId.getNodeID(), 0L, corpus_ref, SRelation2ANNISMapper.DEFAULT_LAYER, virtualSpanName, token_left, token_right, null, token_left, token_right, segId, segName, span, isRoot(tok));
      
      if(segName != null) {
        mapSNodeAnnotation(virtualSpanId.getNodeID(), SRelation2ANNISMapper.DEFAULT_LAYER + "_virtual", segName, span);

        getVirtualTokenStats().addVirtualAnnoName(segName);
      }  else if(textName != null) {        
        getVirtualTokenStats().addVirtualAnnoName(textName);
        mapSNodeAnnotation(virtualSpanId.getNodeID(), SRelation2ANNISMapper.DEFAULT_LAYER + "_virtual", textName, span);
      }
      if (tok.getSAnnotations() != null) {
        for (SAnnotation anno : tok.getSAnnotations()) {
          mapSNodeAnnotation(null, virtualSpanId.getNodeID(), anno);
        }
      }
    }
    {
      prePostOrder = (long) 1;
      Long parentRank = idManager.getGlobal().getNewRankId();
      {
        for (Long tokId : overlappedVirtualTokenIds) {
          getNewPPOrder();
          getNewPPOrder();
        }
      }
      {
        List<String> rankEntry = new ArrayList<>();
        rankEntry.add(parentRank.toString());
        rankEntry.add("0");
        rankEntry.add(getNewPPOrder().toString());
        rankEntry.add("" + virtualSpanId.getNodeID());
        rankEntry.add(currentComponentId.toString());
        rankEntry.add("NULL");
        rankEntry.add("0");
        addTuple(OutputTable.RANK, rankEntry);
      }
    }
  }

  /**
   * This method creates a virtual tokenization for all {@link SToken} which are
   * overlapped by a {@link STimelineRelation}
   */
  private void createVirtualTokenization() {
    
    
    List<String> timelineItems = documentGraph.getSTimeline().getSPointsOfTime();
    // create a set of token indexes
    BitSet virtualCovered = new BitSet(timelineItems.size());
    
    List<STimelineRelation> timelineRelations = documentGraph.getSTimelineRelations();
    LinkedHashSet<STimelineRelation> timlineRelationsSet = new LinkedHashSet<>();
    
    if (timelineRelations != null && !timelineRelations.isEmpty()) {
      for (STimelineRelation timelineRel1 : timelineRelations) {

        if (timelineRel1.getSToken() != null) {

          timlineRelationsSet.add(timelineRel1);
          virtualCovered.set(timelineRel1.getSStart(), timelineRel1.getSEnd());
        }
      } // end for each timeline relation
    }
    
    List<Long> virtualTokenNodeIDs = new ArrayList<>(timelineItems.size());
    
    // create a virtual token for each point of time
    int tokenIndex=0;
    for(String pot : timelineItems) {
      boolean isCovered = virtualCovered.get(tokenIndex);
      VirtualNodeID tokID = mapPointOfTime(tokenIndex++, !isCovered);
      virtualTokenNodeIDs.add(tokID.getNodeID());
    }
    idManager.registerMininmalVirtToken(virtualTokenNodeIDs);

    for (STimelineRelation t : timlineRelationsSet) {
      this.mapSTimeline(t);
    }
  }

  @Override
  public void mapSRelations2ANNIS(List<? extends SNode> sRelationRoots, STYPE_NAME relationTypeName, Salt2ANNISMapper.TRAVERSION_TYPE traversionType) {
    // we don't actually a timeline relation, but we create a  virtual tokenization if necessary
  }

  @Override
  public void run() {
    List<STimelineRelation> timelineRelations = documentGraph.getSTimelineRelations();
    if (timelineRelations != null && !timelineRelations.isEmpty()
            && mergeTextsWithTimeline) {
      beginTransaction();

      createVirtualTokenization();

      commitTransaction();
    }
  }

}
