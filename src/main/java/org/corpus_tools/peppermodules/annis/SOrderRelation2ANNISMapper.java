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
package org.corpus_tools.peppermodules.annis;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.corpus_tools.peppermodules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SOrderRelation;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOrderRelation2ANNISMapper extends SRelation2ANNISMapper {

  private static final Logger log = LoggerFactory.getLogger(SOrderRelation2ANNISMapper.class);

  public SOrderRelation2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, 
          Map<SToken, Long> token2index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter relationAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {

    super(idManager, documentGraph, token2index, nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, relationAnnoTabWriter, componentTabWriter, parentMapper);

  }

  private boolean appendIndex = false;
  private int segPathCounter = 0;

  private long seg_index = 0l;


  @Override
  public void run() {
    // this mapper does nothing in parallel, thus all work is done in mapSRelations2ANNIS
  }

  @Override
  public void mapSRelations2ANNIS(Collection<? extends SNode> sRelationRoots,
          SALT_TYPE relationTypeName, TRAVERSION_TYPE traversionType) {

    beginTransaction();

    if (sRelationRoots != null && !sRelationRoots.isEmpty()) {

      appendIndex = sRelationRoots.size() > 1;

      // Step 1: traverse the SOrderRelations
      for (SNode node : sRelationRoots) {
        List<SNode> singleRootList = new ArrayList<>();
        singleRootList.add(node);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "", this);
        this.segPathCounter = this.segPathCounter + 1;
        this.seg_index = 0l;
      }
    }

    commitTransaction();

  }

// ========================= Graph Traversion =================================
  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SNode currNode, SRelation<SNode, SNode> sRelation,
          SNode fromNode, long order) {

		//if (sRelation != null & sRelation instanceof SOrderRelation){
    //System.out.println("found relation "+ fromNode.getName() +" ->["+sRelation.getId()+"] "+currNode.getName());
    //System.out.println("Traversal id is: "+traversalId);
    //if (sRelation.getSTypes() != null){
    //if (sRelation.getSTypes().size() > 0){
    // set the segName, segIndex and segSpan
    String name = this.currentTraversionSType;
    if(name == null) {
      name = "default_seg";
    }
    if (appendIndex) {
      name = name + segPathCounter;
    } 
    
    Long segIndex = this.seg_index;
    this.seg_index = this.seg_index + 1;
    String segSpan = "NULL";
    String namespace = null;
    if (currNode instanceof SToken) {
      SDocumentGraph g = ((SToken) currNode).getGraph();
      if (g != null) {
        segSpan = g.getText((SToken) currNode);
      }
    } else {
      // find the annotation value with the same name as the segmentation chain
      Set<SAnnotation> annos = currNode.getAnnotations();
      if (annos != null) {
        for (SAnnotation a : annos) {
          if (name.equals(a.getName())) {
            segSpan = a.getValue_STEXT();
            namespace = a.getNamespace();
            break;
          }
        }
      }
    }
    this.idManager.addSegmentInformation(currNode.getId(), segIndex, name, segSpan);
    getVirtualTokenStats().addVirtualAnnoName(namespace, name);
  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
          SNode currNode, SRelation relation, SNode fromNode, long order) {

		// do nothing
  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
          String traversalId, SRelation relation, SNode currNode, long order) {
    
    // only traverse on, if the current relation is null (top rank) or instance of SOrderRelation
    if (relation == null) {
      return true;
    } else {
      if (relation instanceof SOrderRelation) {
        if(this.currentTraversionSType == null) {
          return true;
        } else {
          // check the type of the edge es well since node might be connected 
          // to different order relation chainss
          if(this.currentTraversionSType.equals(relation.getType())) {
            return true;
          }
        }
      }
    }

    return false;
  }

}
