package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper.TRAVERSION_TYPE;
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
import java.util.Set;

public class SDominanceRelation2RelANNISMapper extends SRelation2RelANNISMapper {

  private SNode lastEnteredNode;

  public SDominanceRelation2RelANNISMapper(IdManager idManager,
          SDocumentGraph documentGraph, TupleWriter nodeTabWriter,
          TupleWriter nodeAnnoTabWriter, TupleWriter rankTabWriter,
          TupleWriter edgeAnnoTabWriter, TupleWriter componentTabWriter,
          Salt2RelANNISMapper parentMapper) {

    super(idManager, documentGraph, nodeTabWriter, nodeAnnoTabWriter,
            rankTabWriter, edgeAnnoTabWriter, componentTabWriter, parentMapper);

  }

  @Override
  public void run() {

    beginTransaction();
    Set<String> layers = new HashSet<String>();

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
        EList<SNode> singleRootList = new BasicEList<SNode>();
        singleRootList.add(node);

        this.documentGraph.traverse(singleRootList, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, traversionType.toString(), this);

        // map the component
        this.mapComponent2RelANNIS();
      }
    }
    commitTransaction();
  }

  @Override
  public void mapSRelations2RelANNIS(EList<? extends SNode> sRelationRoots,
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

        if (!(currNode instanceof SToken)) {
          EList<SAnnotation> annos = currNode.getSAnnotations();
          if (annos != null && !annos.isEmpty()) {
            SAnnotation anno = annos.get(0);
            getDomStats().addTerminalAnno(currentComponentLayer, anno.getSNS(), anno.getSName());
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
