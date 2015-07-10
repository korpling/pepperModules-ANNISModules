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


import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class SSpanningRelation2ANNISMapper extends SRelation2ANNISMapper {

  private final Map<SSpan, Boolean> spanIsContinous;

  public SSpanningRelation2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, 
          Map<SToken, Long> token2Index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    super(idManager, documentGraph, 
            token2Index,
            nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, edgeAnnoTabWriter, componentTabWriter,
            parentMapper);

    spanIsContinous = Collections.synchronizedMap(new HashMap<SSpan, Boolean>());
  }

  @Override
  public void run() {

    beginTransaction();
    if (sRelationRoots != null && sRelationRoots.size() != 0) {
      for (SNode node : sRelationRoots) {

        String componentLayerName = DEFAULT_NS;
        SLayer componentLayer = getFirstComponentLayer(node);
        if (componentLayer != null) {
          componentLayerName = componentLayer.getSName();
        }

        if (this.currentTraversionSType == null) {
          super.initialiseTraversion("c", componentLayerName, "NULL");
        } else {
          super.initialiseTraversion("c", componentLayerName, this.currentTraversionSType);
        }

        // create an EList for the current root
        EList<SNode> singleRootList = new BasicEList<>();
        singleRootList.add(node);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, traversionType.toString(), this);

        // map the component
        this.mapComponent2ANNIS();
        this.getSpanStats().addLayer(componentLayerName);
      }
    }

    commitTransaction();
  }

  @Override
  public void mapSRelations2ANNIS(List<? extends SNode> sRelationRoots,
          STYPE_NAME relationTypeName, TRAVERSION_TYPE traversionType) {
    this.traversionType = traversionType;
    this.relationTypeName = relationTypeName;
    this.sRelationRoots = sRelationRoots;

  }

  @Override
  protected void mapRank2ANNIS(SRelation sRelation, Long targetNodeID,
          Long rankId, Long preOrder, Long postOrder, Long parentRank, Long level) {
    boolean doMapping = true;
    if (sRelation instanceof SSpanningRelation) {
      if (sRelation.getSAnnotations() != null && !sRelation.getSAnnotations().isEmpty()) {
        // always do mapping if the edge has annotations
        doMapping = true;
      } else {
        // otherwise check if the span is continuous
        SSpan span = (SSpan) sRelation.getSSource();
        doMapping = !isContinuous(span);

      }
    } // end if non-null relation and target node is token
    if (doMapping) {
      super.mapRank2ANNIS(sRelation, targetNodeID, rankId, preOrder,
              postOrder,
              parentRank, level);
    }
  }

  private boolean isContinuous(SSpan span) {
    boolean continuous = false;
    // look it up in the cache
    Boolean cachedValue = spanIsContinous.get(span);
    if (cachedValue != null) {
      continuous = cachedValue;
    } else {
      // we need to compute wether the span is continuous
      EList<STYPE_NAME> spanRel = new BasicEList<>();
      spanRel.add(STYPE_NAME.SSPANNING_RELATION);

      List<SToken> overlappedToken
              = documentGraph.getOverlappedSTokens(span, spanRel);

      if (overlappedToken != null && !overlappedToken.isEmpty()) {
        long minIndex = Integer.MAX_VALUE;
        long maxIndex = Integer.MIN_VALUE;
        for (SToken tok : overlappedToken) {
          Long idx = token2Index.get(tok);
          if (idx != null) {
            minIndex = Math.min(minIndex, idx);
            maxIndex = Math.max(maxIndex, idx);
          }
        }
        long rangeSize = maxIndex - minIndex + 1;
        continuous = (rangeSize == overlappedToken.size());
        spanIsContinous.put(span, continuous);
      }

    }
    return continuous;
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
    
    getSpanStats().addNodeAnno(currentComponentLayer, currNode.getSAnnotations());
  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SRelation edge, SNode currNode, long order) {
    boolean returnVal = false;

    // only traverse on, if the current node is a Span and edge is null
    if (currNode instanceof SSpan && edge == null) {
      returnVal = true;
    }
    if (currNode instanceof SToken && edge instanceof SSpanningRelation) {
      returnVal = true;
    }

    return returnVal;
  }

}
