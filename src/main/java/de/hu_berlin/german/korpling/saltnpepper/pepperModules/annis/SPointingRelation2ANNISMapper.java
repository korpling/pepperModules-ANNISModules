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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SPointingRelation;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.util.SALT_TYPE;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.QName;

public class SPointingRelation2ANNISMapper extends SRelation2ANNISMapper {

  private SNode lastEnteredNode;
  
  public SPointingRelation2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, 
          Map<SToken, Long> token2Index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter relationAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    super(idManager,
            documentGraph,
            token2Index,
            nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, relationAnnoTabWriter, componentTabWriter,
            parentMapper);

  }

  @Override
  public void run() {

    beginTransaction();
    if (sRelationRoots != null && sRelationRoots.size() != 0) {
      for (SNode node : sRelationRoots) {

        String componentLayer = DEFAULT_NS;
        /*
         List<SLayer> nodeLayer = node.getLayers();
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
        List<SNode> singleRootList = new ArrayList<>();
        singleRootList.add(node);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, traversionType.toString(), this);

        // map the component
        this.mapComponent2ANNIS();

      }
    }
    
    commitTransaction();
    
    getPointingStats().setNodeCount(this.documentGraph.getNodes().size());
  }

  @Override
  public void mapSRelations2ANNIS(Collection<? extends SNode> sRelationRoots,
          SALT_TYPE relationTypeName, TRAVERSION_TYPE traversionType) {
    this.traversionType = traversionType;
    this.relationTypeName = relationTypeName;
    this.sRelationRoots = sRelationRoots;

  }

// ========================= Graph Traversion =================================
  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SNode currNode, SRelation relation,
          SNode fromNode, long order) {

    // this method behaves exactly as the one in the super class
    super.nodeReached(traversalType, traversalId, currNode, relation, fromNode, order);
    
    lastEnteredNode = currNode;

    if (relation != null && relation instanceof SPointingRelation) {
	  if (relation.getType() != null && !relation.getType().isEmpty()) {
          this.currentComponentName = relation.getType();
      } else {
        this.currentComponentName = "salt::NULL";
      }
    }

  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
          SNode currNode, SRelation relation, SNode fromNode, long order) {

    // this method behaves exactly as the one in the super class
    super.nodeLeft(traversalType, traversalId, currNode, relation, fromNode, order);
    
    if (lastEnteredNode == currNode) {
      // we left a leaf node
      QName layer = new QName(currentComponentLayer, currentTraversionSType);
      getPointingStats().addLayer(layer);
      
      if (!(currNode instanceof SToken)) {
        Set<SAnnotation> annos = currNode.getAnnotations();
        if (annos != null) {
          for (SAnnotation anno : annos) {
            getPointingStats().getTerminalAnno().add(layer,
                    new QName(anno.getNamespace(), anno.getName()));
          }
        }
      }
    }

  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SRelation relation, SNode currNode, long order) {
    boolean returnVal = false;

		// only traverse on, if the current relation is null (top rank) or instance of SPointingRelation
    // only traverse on, if the current relation is null or a dominance relation
    if (relation == null) {
      returnVal = true;
    } else {
      if (relation instanceof SPointingRelation) {
        if (this.currentTraversionSType == null) {
          // traversing super component
          returnVal = true;
        } else {
          // traversing sub component. Only accept, if the SType is correct
        	if (relation.getType() != null && !relation.getType().isEmpty()) {
              if (relation.getType().equals(this.currentTraversionSType)) {
                returnVal = true;
              }
          } else {
            // the current SType is NULL, i.e. SALT::NULL
            if (this.currentTraversionSType.equals("salt::NULL")) {
              returnVal = true;
            }

          }
        }
      }
    }

    return returnVal;
  }

}
