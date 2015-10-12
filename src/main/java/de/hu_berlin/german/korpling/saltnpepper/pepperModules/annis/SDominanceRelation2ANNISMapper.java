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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SDominanceRelation;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SLayer;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;

import com.google.common.base.Preconditions;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper.TRAVERSION_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.QName;

public class SDominanceRelation2ANNISMapper extends SRelation2ANNISMapper {

	private SNode lastEnteredNode;

	public SDominanceRelation2ANNISMapper(IdManager idManager, SDocumentGraph documentGraph, Map<SToken, Long> token2index, TupleWriter nodeTabWriter, TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter, TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter, Salt2ANNISMapper parentMapper) {

		super(idManager, documentGraph, token2index, nodeTabWriter, nodeAnnoTabWriter, rankTabWriter, edgeAnnoTabWriter, componentTabWriter, parentMapper);

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
					componentLayerName = componentLayer.getName();
				}
				layers.add(componentLayerName);

				if (this.currentTraversionSType == null) {
					super.initialiseTraversion("d", componentLayerName, "NULL");
				} else {
					super.initialiseTraversion("d", componentLayerName, this.currentTraversionSType);
				}

				// create an EList for the current root
				List<SNode> singleRootList = new ArrayList<>();
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
	public void mapSRelations2ANNIS(Collection<? extends SNode> sRelationRoots, SALT_TYPE edgeTypeName, TRAVERSION_TYPE traversionType) {
		this.traversionType = traversionType;
		this.edgeTypeName = edgeTypeName;
		this.sRelationRoots = sRelationRoots;

	}

	// ========================= Graph Traversion
	// =================================
	@Override
	public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation sRelation, SNode fromNode, long order) {

		// this method behaves exactly as the one in the super class
		super.nodeReached(traversalType, traversalId, currNode, sRelation, fromNode, order);
		lastEnteredNode = currNode;

		if (sRelation != null) {
			getDomStats().addRelationType(currentComponentLayer, sRelation.getType());
			getDomStats().addRelationAnno(currentComponentLayer, sRelation.getAnnotations());
		}
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation relation, SNode fromNode, long order) {

		// this method behaves exactly as the one in the super class
		super.nodeLeft(traversalType, traversalId, currNode, relation, fromNode, order);

		if (currentTraversionSType == null) {
			if (lastEnteredNode == currNode) {
				// we left a leaf node

				if (relation != null) {
					getDomStats().addTerminalEdgeType(currentComponentLayer, relation.getType());
				}

				if (currNode instanceof SToken) {
					if (idManager.getVirtualisedSpanId(currNode.getId()) != null) {
						// there is a virtual token for this token, thus the
						// "real" token
						// will be mapped to a span in ANNIS
						SegmentationInfo segInfo = idManager.getSegmentInformation(currNode.getId());
						if (segInfo != null) {
							getDomStats().getTerminalAnno().add(currentComponentLayer, new QName(segInfo.getSegmentationName()));
						}
					}
				} else {
					// non-token must be specified
					Set<SAnnotation> annos = currNode.getAnnotations();
					if (annos != null && !annos.isEmpty()) {
						SAnnotation anno = annos.iterator().next();
						getDomStats().getTerminalAnno().add(currentComponentLayer, new QName(anno.getNamespace(), anno.getName()));
					}

				}
			} else {
				getDomStats().addNodeAnno(currentComponentLayer, currNode.getAnnotations());
			}
		}
	}

	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SRelation relation, SNode currNode, long order) {
		boolean returnVal = false;

		// only traverse on, if the current relation is null or a dominance
		// relation
		if (relation == null) {
			returnVal = true;
		} else {
			if (relation instanceof SDominanceRelation) {
				if (this.currentTraversionSType == null) {
					// traversing super component
					returnVal = true;
				} else {
					// traversing sub component. Only accept, if the SType is
					// correct
					if (relation.getType() != null && !relation.getType().isEmpty()) {
						if (relation.getType().equals(this.currentTraversionSType)) {
							returnVal = true;
						}
					}
				}
			}
		}
		return returnVal;
	}
}
