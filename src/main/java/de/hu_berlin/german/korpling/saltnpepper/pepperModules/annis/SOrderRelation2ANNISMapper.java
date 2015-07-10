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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

import java.util.List;
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


  @Override
  public void run() {
    // this mapper does nothing in parallel, thus all work is done in mapSRelations2ANNIS
  }

  @Override
  public void mapSRelations2ANNIS(List<? extends SNode> sRelationRoots,
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
