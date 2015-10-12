/**
 * Copyright 2015 Humboldt-Universität zu Berlin
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
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.common.STextualRelation;
import org.corpus_tools.salt.common.STimelineRelation;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.graph.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    Long corpus_ref = idManager.getNewCorpusTabId(documentGraph.getDocument().getId());

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
    Long corpus_ref = idManager.getNewCorpusTabId(documentGraph.getDocument().getId());
    Long token_left;
    Long token_right;
    SToken tok = timelineRelation.getSource();
    if (tok == null) {
      // We can't map timeline relation that don't have a token connected.
      return;
    }
    
    String virtualSpanSId = tok.getId();
    virtualSpanSId = virtualSpanSId + "_virtualSpan";
    String virtualSpanName = tok.getName() + "_virtualSpan";
    VirtualNodeID virtualSpanId = idManager.getVirtualNodeId(virtualSpanSId);
    List<Long> overlappedVirtualTokenIds = new ArrayList<>();
   
    
    token_left = (long) timelineRelation.getStart();
    token_right = (long) timelineRelation.getEnd()-1;

    for(long i = token_left; i <= token_right; i++) {
       String virtualTokenName = "virtualToken" + i;
      VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);
      overlappedVirtualTokenIds.add(virtualTokenId.getNodeID());
    }
    

    idManager.registerTokenVirtMapping(tok.getId(), virtualSpanId.getNodeID(), overlappedVirtualTokenIds);
    if (virtualSpanId.isFresh()) {
      Long segId = null;
      String segName = null;
      String textName = null;
      String span;
      SegmentationInfo segmentInfo = idManager.getSegmentInformation(tok.getId());
      if(segmentInfo == null) {
        span = documentGraph.getText(tok);
     
        // find any connected texts of the original token for the statistics
        for (Relation e : documentGraph.getOutRelations(tok.getId())) {
          if (e instanceof STextualRelation) {
            STextualDS text = ((STextualRelation) e).getTarget();
            textName = text.getName();
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

        getVirtualTokenStats().addVirtualAnnoName(SRelation2ANNISMapper.DEFAULT_LAYER + "_virtual", segName);
      }  else if(textName != null) {        
        mapSNodeAnnotation(virtualSpanId.getNodeID(), SRelation2ANNISMapper.DEFAULT_LAYER + "_virtual", textName, span);
        getVirtualTokenStats().addVirtualAnnoName( SRelation2ANNISMapper.DEFAULT_LAYER + "_virtual", textName);
      }
      if (tok.getAnnotations() != null) {
        for (SAnnotation anno : tok.getAnnotations()) {
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
//    List<String> timelineItems = documentGraph.getTimeline().getEnd();
    
    // create a set of token indexes
    BitSet virtualCovered = new BitSet(documentGraph.getTimeline().getEnd());
    
    List<STimelineRelation> timelineRelations = documentGraph.getTimelineRelations();
    LinkedHashSet<STimelineRelation> timlineRelationsSet = new LinkedHashSet<>();
    
    if (timelineRelations != null && !timelineRelations.isEmpty()) {
      for (STimelineRelation timelineRel1 : timelineRelations) {

        if (timelineRel1.getTarget() != null) {

          timlineRelationsSet.add(timelineRel1);
          virtualCovered.set(timelineRel1.getStart(), timelineRel1.getEnd());
        }
      } // end for each timeline relation
    }
    
    List<Long> virtualTokenNodeIDs = new ArrayList<>(documentGraph.getTimeline().getEnd());
    
    // create a virtual token for each point of time
    int tokenIndex=0;
    for(int i=0; i <= documentGraph.getTimeline().getEnd();i++) {
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
  public void mapSRelations2ANNIS(Collection<? extends SNode> sRelationRoots, SALT_TYPE edgeTypeName, Salt2ANNISMapper.TRAVERSION_TYPE traversionType) {
    // we don't actually a timeline relation, but we create a  virtual tokenization if necessary
  }

  @Override
  public void run() {
    List<STimelineRelation> timelineRelations = documentGraph.getTimelineRelations();
    if (timelineRelations != null && !timelineRelations.isEmpty()
            && mergeTextsWithTimeline) {
      beginTransaction();

      createVirtualTokenization();

      commitTransaction();
    }
  }
}
