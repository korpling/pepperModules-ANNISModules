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

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STimelineRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class STimelineRelation2ANNISMapper extends SRelation2ANNISMapper {

  private final static Logger log = LoggerFactory.getLogger(STimelineRelation2ANNISMapper.class);

  public STimelineRelation2ANNISMapper(IdManager idManager, SDocumentGraph documentGraph, Map<SToken, Long> token2Index, TupleWriter nodeTabWriter, TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter, TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter, Salt2ANNISMapper parentMapper) {
    super(idManager, documentGraph, token2Index, nodeTabWriter, nodeAnnoTabWriter, rankTabWriter, edgeAnnoTabWriter, componentTabWriter, parentMapper);
  }

  /**
   * This method maps the given {@link STimelineRelation} timelineRelation with
   * use of the minimal timeline relations.
   *
   * @param timelineRelation The {@link STimelineRelation} to map
   * @param minimalTimelineRelations The set of {@link STimelineRelation}
   * objects indexed by their start Point of Time
   * @param timelineRel2TokenIndex A map from a minimal timeline relation to it's token index
   * @param minimal States whether the given {@link STimelineRelation} is
   * minimal, i.e. there is no other timeline which is contained in the given
   * one.
   */
  private void mapSTimeline(STimelineRelation timelineRelation, 
          Map<Integer, STimelineRelation> minimalTimelineRelations, 
          Map<STimelineRelation, Long> timelineRel2TokenIndex, boolean minimal) {
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
    EList<Long> virtualTokenIds = new BasicEList<>();
    if (minimal) {
      String virtualTokenName = tok.getSName() + "_virtualToken";
      VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);
      virtualTokenIds.add(virtualTokenId.getNodeID());
      Long tokenIndex = timelineRel2TokenIndex.get(timelineRelation);
      token_left = tokenIndex;
      token_right = tokenIndex;
      if (virtualTokenId.isFresh()) {
        super.writeNodeTabEntry(virtualTokenId.getNodeID(), 0L, corpus_ref, SRelation2ANNISMapper.DEFAULT_LAYER, virtualTokenName, tokenIndex, tokenIndex, tokenIndex, token_left, token_right, null, null, " ", false);
      }
    } else {
      List<STimelineRelation> overlappedTimelines = new ArrayList<>();
      for (Integer i = timelineRelation.getSStart(); i < timelineRelation.getSEnd(); i++) {
        STimelineRelation rel = minimalTimelineRelations.get(i);
        if(rel != null) {
          overlappedTimelines.add(rel);
        } else {
          log.warn("No minimal timeline entry found for index {} when processing token {} (span {})", 
                  i, timelineRelation.getSToken().getSId());
        }
      }
      {
        token_left = timelineRel2TokenIndex.get(overlappedTimelines.get(0));
        token_right = timelineRel2TokenIndex.get(overlappedTimelines.get(overlappedTimelines.size() - 1));
      }
      for (STimelineRelation overlappedRelation : overlappedTimelines) {
        SToken overlappedToken = overlappedRelation.getSToken();
        String virtualTokenName = overlappedToken.getSName() + "_virtualToken";
        VirtualNodeID virtualTokenId = idManager.getVirtualNodeId(virtualTokenName);
        virtualTokenIds.add(virtualTokenId.getNodeID());
      }
    }
    idManager.registerTokenVirtMapping(tok.getSId(), virtualSpanId.getNodeID(), virtualTokenIds);
    if (virtualSpanId.isFresh()) {
      Long segId = null;
      String segName = null;
      String span = null;
      SegmentationInfo segmentInfo = idManager.getSegmentInformation(tok.getSId());
      if (segmentInfo != null) {
        segId = segmentInfo.getANNISId();
        segName = segmentInfo.getSegmentationName();
        span = segmentInfo.getSpan();
      }
      writeNodeTabEntry(virtualSpanId.getNodeID(), 0L, corpus_ref, SRelation2ANNISMapper.DEFAULT_LAYER, virtualSpanName, token_left, token_right, null, token_left, token_right, segId, segName, span, isRoot(tok));
      mapSNodeAnnotation(virtualSpanId.getNodeID(), SRelation2ANNISMapper.DEFAULT_LAYER + "_virtual", segName, span);
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
        for (Long tokId : virtualTokenIds) {
          getNewPPOrder();
          getNewPPOrder();
        }
      }
      {
        EList<String> rankEntry = new BasicEList<>();
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
    EList<STimelineRelation> timelineRelations = documentGraph.getSTimelineRelations();
    LinkedHashSet<STimelineRelation> nonMinimalTimelineRelations = new LinkedHashSet<>();
    
    TreeMap<Integer, STimelineRelation> minimalTimelineRelations = new TreeMap<>();
    
    if (timelineRelations != null && !timelineRelations.isEmpty()) {
      {
        for (STimelineRelation timelineRel1 : timelineRelations) {
          
          if(timelineRel1.getSToken() != null) {

            boolean relationIsMinimal = true;
            for (STimelineRelation timelineRel2 : timelineRelations) {
              if (!timelineRel1.equals(timelineRel2)) {
                if ((timelineRel2.getSStart() >= timelineRel1.getSStart() && timelineRel2.getSEnd() < timelineRel1.getSEnd()) || (timelineRel2.getSStart() > timelineRel1.getSStart() && timelineRel2.getSEnd() <= timelineRel1.getSEnd())) {
                  relationIsMinimal = false;
                  break;
                }
              }
            }
            if (relationIsMinimal) {
              if(minimalTimelineRelations.containsKey(timelineRel1.getSStart())) {
                // there is already a minimal virtual token for this start, mark this token as "non-minimal"
                // "end" is not considered since a minimal token will distinctively definied by the start value
                nonMinimalTimelineRelations.add(timelineRel1);
              } else {
                minimalTimelineRelations.put(timelineRel1.getSStart(), timelineRel1);
              }
            } else {
              nonMinimalTimelineRelations.add(timelineRel1);
            }
          }
        } // end for each timeline relation
      }
      
      // since we a are using a TreeMap the values are already sorted
      LinkedHashMap<STimelineRelation, Long> timelineRel2TokenIndex
              = new LinkedHashMap<>();
      long i=0;
      for(STimelineRelation rel : minimalTimelineRelations.values()) {
        timelineRel2TokenIndex.put(rel, i++);
      }
      
      for (STimelineRelation t : minimalTimelineRelations.values()) {
        this.mapSTimeline(t, minimalTimelineRelations, timelineRel2TokenIndex, true);
      }
      for (STimelineRelation t : nonMinimalTimelineRelations) {
        this.mapSTimeline(t, minimalTimelineRelations, timelineRel2TokenIndex, false);
      }
      {

        
        EList<Long> sortedMinimalIdList = new BasicEList<>();
        for (STimelineRelation tr : minimalTimelineRelations.values()) {
          EList<Long> tr_IdS = idManager.getVirtualisedTokenId(tr.getSToken().getSId());
          if (tr_IdS.size() > 1) {
            log.warn("minimal timeline relation has more than one virtual token id");
          } else if (tr_IdS.isEmpty()) {
            log.warn("token {} is not mapped to virtual one", tr.getSToken().getSId(), tr_IdS);
          } else {
            sortedMinimalIdList.add(tr_IdS.get(0));
          }
        }
        idManager.registerMininmalVirtToken(sortedMinimalIdList);
      }
    }
  }

  @Override
  public void mapSRelations2ANNIS(EList<? extends SNode> sRelationRoots, STYPE_NAME relationTypeName, Salt2ANNISMapper.TRAVERSION_TYPE traversionType) {
    // we don't actually a timeline relation, but we create a  virtual tokenization if necessary
  }

  @Override
  public void run() {
    if (documentGraph.getSTimelineRelations() != null) {
      if (documentGraph.getSTimelineRelations().size() > 0) {
        beginTransaction();

        createVirtualTokenization();

        commitTransaction();
      }
    }
  }

}
