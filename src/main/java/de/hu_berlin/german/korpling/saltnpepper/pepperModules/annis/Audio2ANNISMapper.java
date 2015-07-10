/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import com.google.common.collect.Range;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SAudioDSRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

/**
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class Audio2ANNISMapper extends SRelation2ANNISMapper {

  /**
   * A map from a SDocument ID to an URI
   */
  private final Set<URI> mappedFiles;

  /**
   * A map that maps from a virtual token node ID to a time range.
   */
  private final Map<Long, Range<Double>> virtTokenTimes = new TreeMap<>();

  public Audio2ANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph,
          Map<SToken, Long> token2Index,
          TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2ANNISMapper parentMapper) {
    super(idManager, documentGraph,
            token2Index,
            nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, edgeAnnoTabWriter, componentTabWriter, parentMapper);
    mappedFiles = Collections.synchronizedSet(new HashSet<URI>());
  }

  @Override
  public void mapSRelations2ANNIS(List<? extends SNode> sRelationRoots, STYPE_NAME relationTypeName,
          Salt2ANNISMapper.TRAVERSION_TYPE traversionType) {
    this.traversionType = traversionType;
    this.relationTypeName = relationTypeName;
    this.sRelationRoots = sRelationRoots;
  }

  @Override
  public void run() {
    beginTransaction();
    if (sRelationRoots != null && sRelationRoots.size() != 0) {
      for (SNode node : sRelationRoots) {

        super.initialiseTraversion(null, null, null);

        // create an EList for the current root
        EList<SNode> singleRootList = new BasicEList<>();
        singleRootList.add(node);

        documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, traversionType.toString(), this);
      } // end for each root
      
      outputVirtualTokenAnnotations();
    }
    commitTransaction();
  }

  @Override
  public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SRelation edge, SNode currNode, long order) {

    if (traversionType == Salt2ANNISMapper.TRAVERSION_TYPE.DOCUMENT_STRUCTURE_AUDIO) {
      return currNode instanceof SToken || edge instanceof SAudioDSRelation;
    }

    return false;
  }

  @Override
  public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
          SNode currNode, SRelation sRelation, SNode fromNode, long order) {

    if (sRelation instanceof SAudioDSRelation
            && traversionType == Salt2ANNISMapper.TRAVERSION_TYPE.DOCUMENT_STRUCTURE_AUDIO) {
      SAudioDSRelation dsRel = (SAudioDSRelation) sRelation;
      Double start = dsRel.getSStart();
      Double end = dsRel.getSEnd();

      String val;
      if (start != null && end != null) {
        val = "" + start + "-" + end;
      } else if (start != null) {
        val = "" + start;
      } else if (end != null) {
        val = "-" + end;
      } else {
        val = "";
      }

      SToken tok = dsRel.getSToken();
      List<Long> virtualToken = idManager.getVirtualisedTokenId(tok.getSId());
      if (virtualToken == null) {

        tok.createSAnnotation("annis", "time", val);
        mapSNode(dsRel.getSToken());
      } else if (!virtualToken.isEmpty()) {
        // there is already a virtual span written for this token,
        // add the time information to the overlapped virtual token instead

        if (virtualToken.size() == 1) {

          Range<Double> newRange = Range.all();
          if (start != null && end != null) {
            newRange = Range.closed(start, end);
          } else if (start != null) {
            newRange = Range.atLeast(start);
          } else if (end != null) {
            newRange = Range.atMost(end);
          }

          addVirtualRange(virtualToken.get(0), newRange);
        } else {
          Long firstTokenID = virtualToken.get(0);
          Long lastTokenID = virtualToken.get(virtualToken.size() - 1);

          if (start != null) {
            addVirtualRange(firstTokenID, Range.atLeast(start));
          }
          if (end != null) {
            addVirtualRange(lastTokenID, Range.atMost(end));
          }

        }

      }

      URI linkedFile = dsRel.getSAudioDS().getSAudioReference();
      if (linkedFile != null) {
        if (mappedFiles.add(linkedFile)) {
          copyLinkedFile(linkedFile);
        }
      }

    }

  }

  @Override
  public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation edge, SNode fromNode, long order) {

  }
  
  /**
   * Adds a new range to the {@code virtTokenTimes} map. When a range already
   * exists the intersection of the old and new range is used.
   *
   * @param tokenID
   * @param newRange
   */
  private void addVirtualRange(Long tokenID, Range newRange) {
    Range existingRange = virtTokenTimes.get(tokenID);
    if (existingRange == null) {
      virtTokenTimes.put(tokenID, newRange);
    } else {
      virtTokenTimes.put(tokenID, existingRange.intersection(newRange));
    }
  }

  private void outputVirtualTokenAnnotations() {
    for(Map.Entry<Long, Range<Double>> e : virtTokenTimes.entrySet()) {
      Range<Double> r = e.getValue();
      String val = null;
      
      if(r.hasLowerBound() && r.hasUpperBound()) {
        val = r.lowerEndpoint() + "-" + r.upperEndpoint();
      } else if(r.hasLowerBound()) {
        val = "" + r.lowerEndpoint();
      } else if(r.hasUpperBound()) {
        val = "-" + r.upperEndpoint();
      }
      if(val != null) {
        mapSNodeAnnotation(e.getKey(), "annis", "time", val);
      }
    }
  }
  
  
}
