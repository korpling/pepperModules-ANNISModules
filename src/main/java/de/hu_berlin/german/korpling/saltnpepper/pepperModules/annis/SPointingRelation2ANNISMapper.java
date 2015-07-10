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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.QName;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

import java.util.List;
import java.util.Map;

public class SPointingRelation2ANNISMapper extends SRelation2ANNISMapper {

  private SNode lastEnteredNode;
  
  public SPointingRelation2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, 
          Map<SToken, Long> token2Index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    super(idManager,
            documentGraph,
            token2Index,
            nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, edgeAnnoTabWriter, componentTabWriter,
            parentMapper);

  }

  @Override
  public void run() {

    beginTransaction();
    if (sRelationRoots != null && sRelationRoots.size() != 0) {
      for (SNode node : sRelationRoots) {

        String componentLayer = DEFAULT_NS;
        /*
         List<SLayer> nodeLayer = node.getSLayers();
         if (nodeLayer != null){
         if (nodeLayer.size() > 0){
         if (! "".equals(nodeLayer.get(0))){
         componentLayer = nodeLayer.get(0).toString();
         }
         }
         }*/

        if (this.currentTraversionSType == null) {
          super.initialiseTraversion("p", componentLayer, "NULL");
        } else {
          super.initialiseTraversion("p", componentLayer, this.currentTraversionSType);

        }
				//System.out.println("[DEBUG] Mapping pointingRelation with sType "+ this.currentTraversionSType);
        // create an List for the current root
        EList<SNode> singleRootList = new BasicEList<>();
        singleRootList.add(node);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, traversionType.toString(), this);

        // map the component
        this.mapComponent2ANNIS();

      }
    }
    
    commitTransaction();
    
    getPointingStats().setNodeCount(this.documentGraph.getNumOfNodes());
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

    if (sRelation != null & sRelation instanceof SPointingRelation) {
			//System.out.println("found relation "+ fromNode.getSName() +" ->["+sRelation.getSId()+"] "+currNode.getSName());
      //System.out.println("Traversal id is: "+traversalId);
      if (sRelation.getSTypes() != null) {
        if (sRelation.getSTypes().size() > 0) {
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
    
    if (lastEnteredNode == currNode) {
      // we left a leaf node
      QName layer = new QName(currentComponentLayer, currentTraversionSType);
      getPointingStats().addLayer(layer);
      
      if (!(currNode instanceof SToken)) {
        EList<SAnnotation> annos = currNode.getSAnnotations();
        if (annos != null) {
          for (SAnnotation anno : annos) {
            getPointingStats().getTerminalAnno().add(layer,
                    new QName(anno.getSNS(), anno.getSName()));
          }
        }
      }
    }

  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SRelation edge, SNode currNode, long order) {
    boolean returnVal = false;

		// only traverse on, if the current edge is null (top rank) or instance of SPointingRelation
    // only traverse on, if the current edge is null or a dominance relation
    if (edge == null) {
      returnVal = true;
    } else {
      if (edge instanceof SPointingRelation) {
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
          } else {
            // the current SType is NULL, i.e. SALT::NULL
            if (this.currentTraversionSType.equals(SaltFactory.DEFAULT_STYPE)) {
              returnVal = true;
            }

          }
        }
      }
    }

    return returnVal;
  }

}
