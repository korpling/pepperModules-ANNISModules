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

import com.google.common.base.Preconditions;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.QName;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SDominanceRelation2ANNISMapper extends SRelation2ANNISMapper {

  private SNode lastEnteredNode;

  public SDominanceRelation2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, 
          Map<SToken, Long> token2index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    super(idManager, documentGraph, token2index, nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, edgeAnnoTabWriter, componentTabWriter, parentMapper);

  }

  @Override
  public void run() {

    beginTransaction();
    Set<String> layers = new HashSet<>();

    if (sRelationRoots != null && sRelationRoots.size() != 0) {
      for (SNode node : sRelationRoots) {

        String componentLayerName = DEFAULT_NS;
        SLayer componentLayer = getFirstComponentLayer(node);
        if (componentLayer != null) {
          componentLayerName = componentLayer.getSName();
        }
        layers.add(componentLayerName);

        if (this.currentTraversionSType == null) {
          super.initialiseTraversion("d", componentLayerName, "NULL");
        } else {
          super.initialiseTraversion("d", componentLayerName, this.currentTraversionSType);
        }

        // create an EList for the current root
        EList<SNode> singleRootList = new BasicEList<>();
        singleRootList.add(node);
        
        Preconditions.checkNotNull(currentComponentLayer);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, traversionType.toString(), this);

        // map the component
        this.mapComponent2ANNIS();
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

// ========================= Graph Traversion =================================
  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SNode currNode, SRelation sRelation,
          SNode fromNode, long order) {

    // this method behaves exactly as the one in the super class
    super.nodeReached(traversalType, traversalId, currNode, sRelation, fromNode, order);
    lastEnteredNode = currNode;
    
    if (sRelation != null) {
      getDomStats().addEdgeType(currentComponentLayer, sRelation.getSTypes());
      getDomStats().addEdgeAnno(currentComponentLayer, sRelation.getSAnnotations());
    }
  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
          SNode currNode, SRelation edge, SNode fromNode, long order) {

    // this method behaves exactly as the one in the super class
    super.nodeLeft(traversalType, traversalId, currNode, edge, fromNode, order);

    if (currentTraversionSType == null) {
      if (lastEnteredNode == currNode) {
        // we left a leaf node

        if (edge != null) {
          getDomStats().addTerminalEdgeType(currentComponentLayer, edge.getSTypes());
        }
        
        if(currNode instanceof SToken) {
          if(idManager.getVirtualisedSpanId(currNode.getSId()) != null) {
            // there is a virtual token for this token, thus the "real" token
            // will be mapped to a span in ANNIS
            SegmentationInfo segInfo = idManager.getSegmentInformation(currNode.getSId());
            if(segInfo != null) {
             getDomStats().getTerminalAnno().add(currentComponentLayer, new QName(segInfo.getSegmentationName()));
            }
          }
        } else {
          // non-token must be specified
          EList<SAnnotation> annos = currNode.getSAnnotations();
          if (annos != null && !annos.isEmpty()) {
            SAnnotation anno = annos.get(0);
            getDomStats().getTerminalAnno().add(currentComponentLayer, new QName(anno.getSNS(), anno.getSName()));
          }

        }
      } else {
        getDomStats().addNodeAnno(currentComponentLayer, currNode.getSAnnotations());
      }
    }
  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SRelation edge, SNode currNode, long order) {
    boolean returnVal = false;

    // only traverse on, if the current edge is null or a dominance relation
    if (edge == null) {
      returnVal = true;
    } else {
      if (edge instanceof SDominanceRelation) {
        if (this.currentTraversionSType == null) {
          // traversing super component
          returnVal = true;
        } else {
          // traversing sub component. Only accept, if the SType is correct
          EList<String> edgeSTypes = edge.getSTypes();
          if (edgeSTypes != null) {
            if (edgeSTypes.size() > 0) {
              if (edgeSTypes.get(0).equals(this.currentTraversionSType)) {
                returnVal = true;
              }
            }
          }
        }

      }
    }

    return returnVal;
  }

}
