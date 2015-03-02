/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.index.ComplexIndex;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.index.Index;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.index.IndexFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SFeature;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SGraphImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Document Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl#getRaCorpus <em>Ra Corpus</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl#getRaNodes <em>Ra Nodes</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl#getRaComponents <em>Ra Components</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl#getRaTexts <em>Ra Texts</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl#getRaRanks <em>Ra Ranks</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RADocumentGraphImpl extends SGraphImpl implements RADocumentGraph 
{
	
	/**
	 * name of index for node-types
	 */
	protected static final String IDX_RA_NODETYPE=	"idx_raNodeType";
	
	/**
	 * name of index for edge-types
	 */
	protected static final String IDX_RA_EDGETYPE=	"idx_raEdgeType";
	
	/**
	 * The cached value of the '{@link #getRaComponents() <em>Ra Components</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<RAComponent> raComponents;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RADocumentGraphImpl() 
	{
		super();
		init();
	}

	private void init()
	{
		{//creating indexes
			Index index= null;
			
			{//creating node-type index
				index= IndexFactory.eINSTANCE.createComplexIndex();
				index.setId(IDX_RA_NODETYPE);
				this.getIndexMgr().addIndex(index);
			}
			
			{//creating relation-type index
				index= IndexFactory.eINSTANCE.createComplexIndex();
				index.setId(IDX_RA_EDGETYPE);
				this.getIndexMgr().addIndex(index);
			}
		}
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_DOCUMENT_GRAPH;
	}
	
// ============================ start: handling relations
	/**
	 * Calls the super method an puts the given relation into a relation type index.
	 * an exception will be thrown.
	 * @param edge to add
	 */
	protected void basicAddEdge(Edge edge)
	{
		if (!(edge instanceof SRelation))
			throw new RelANNISException("Cannot insert an edge, which is not a SRelation object: "+ edge);
		
		super.basicAddEdge(edge);
		
		String slotId= null;
		{//compute slot id
			if (edge instanceof RARank)
				slotId= RARank.class.getName();
			else
				slotId= (String) edge.getClass().getName();
		}
		
		this.getIndexMgr().getIndex(IDX_RA_EDGETYPE).addElement(slotId, edge);
	}
// ============================ end: handling relations
// ============================ start: handling nodes	
	/**
	 * Calls the super method an puts the given node into a node type index.
	 * an exception will be thrown.
	 * @param node to add
	 */
	protected void basicAddNode(Node node)
	{
		if (!(node instanceof SNode))
			throw new RelANNISException("Cannot insert a node, which is not a SNode object: "+ node);
		
		super.basicAddNode(node);
		
		String slotId= null;
		{//compute slot id
			if (node instanceof RAText)
			{
				slotId= RAText.class.getName();
			}
			else if (node instanceof RANode)
				slotId= RANode.class.getName();
			else
				slotId= (String) node.getClass().getName();
		}
		
		this.getIndexMgr().getIndex(IDX_RA_NODETYPE).addElement(slotId, node);
	}
	
// ============================ end: handling nodes		

// ============================= start raCorpus	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpus getRaCorpus() {
		RACorpus raCorpus = basicGetRaCorpus();
		return raCorpus != null && raCorpus.eIsProxy() ? (RACorpus)eResolveProxy((InternalEObject)raCorpus) : raCorpus;
	}

	
	public static String KW_RACORPUS= "RACORPUS";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RACorpus basicGetRaCorpus() 
	{
		RACorpus retVal= null;
		SFeature sFeature= super.getSFeature(KW_RACORPUS);
		if (sFeature!= null)
		{
			retVal= (RACorpus) sFeature.getSValue();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaCorpus(RACorpus newRaCorpus) 
	{
		SFeature sFeature= super.getSFeature(KW_RACORPUS);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSName(KW_RACORPUS);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaCorpus);
		if (newRaCorpus.getRaDocumentGraph()!= this)
			newRaCorpus.setRaDocumentGraph(this);
	}
//	============================= end raCorpus
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RAComponent> getRaComponents() {
		if (raComponents == null) {
			raComponents = new EObjectResolvingEList<RAComponent>(RAComponent.class, this, relANNISPackage.RA_DOCUMENT_GRAPH__RA_COMPONENTS);
		}
		return raComponents;
	}

// ============================ start: handling specific nodes
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RANode> getRaNodes() 
	{
		EList<RANode> retVal= null;
		EList<Node> nodes= (EList<Node>)(EList<? extends Object>)((ComplexIndex)this.getIndexMgr().getIndex(IDX_RA_NODETYPE)).getSlot(RANode.class.getName());
		if (nodes!= null)
			retVal= new EcoreEList.UnmodifiableEList(this,
					relANNISPackage.eINSTANCE.getRADocumentGraph_RaNodes(),
					nodes.size(), nodes.toArray());
		else retVal= new EcoreEList.UnmodifiableEList(this,
				relANNISPackage.eINSTANCE.getRADocumentGraph_RaNodes(), 0, (Object[]) null);
		
		return(retVal);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RAText> getRaTexts() 
	{
		EList<RAText> retVal= null;
		EList<Node> nodes= (EList<Node>)(EList<? extends Object>)((ComplexIndex)this.getIndexMgr().getIndex(IDX_RA_NODETYPE)).getSlot(RAText.class.getName());
		if (nodes!= null)
			retVal= new EcoreEList.UnmodifiableEList(this,
					relANNISPackage.eINSTANCE.getRADocumentGraph_RaTexts(),
					nodes.size(), nodes.toArray());
		else retVal= new EcoreEList.UnmodifiableEList(this,
				relANNISPackage.eINSTANCE.getRADocumentGraph_RaTexts(), 0, (Object[]) null);
		
		return(retVal);
	}	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RARank> getRaRanks() 
	{
		EList<RARank> retVal= null;
		EList<Edge> ranks= (EList<Edge>)(EList<? extends Object>)((ComplexIndex)this.getIndexMgr().getIndex(IDX_RA_EDGETYPE)).getSlot(RARank.class.getName());
		if (ranks!= null)
			retVal= new EcoreEList.UnmodifiableEList(this,
					relANNISPackage.eINSTANCE.getRADocumentGraph_RaRanks(),
					ranks.size(), ranks.toArray());
		else retVal= new EcoreEList.UnmodifiableEList(this,
				relANNISPackage.eINSTANCE.getRADocumentGraph_RaRanks(), 0, (Object[]) null);
		
		return(retVal);
	}

	/**
	 * Returns if this node is a terminal raNode, that means it has no raNode objects as childs.
	 * @param raNode node object to check
	 * @return true if there are no raNode Objects a schilds false else
	 */
	public Boolean isTerminalRaNode(RANode raNode)
	{
		RADocumentGraphTraverser traverser= new RADocumentGraphTraverser();
		traverser.setRaDocumentGraph(this);
		return(traverser.isTerminalRaNode(raNode));
	}
	
	/**
	 * Returns a raText-object corresponding to given raId.
	 */
	public RAText getRaText(Long raPre) 
	{
		RAText retVal= null;
		SNode sNode= super.getSNode(RATextImpl.createPrefixedId(raPre));
		if (sNode instanceof RAText)
			retVal= (RAText) sNode;
		return(retVal);
	}

	/**
	 * Returns a raNode-object corresponding to given raId.
	 */
	public RANode getRaNode(Long raId) 
	{
		RANode retVal= null;
		SNode sNode= super.getSNode(RANodeImpl.createPrefixedId(raId));
		if (sNode instanceof RANode)
			retVal= (RANode) sNode;
		return(retVal);
	}

	/**
	 * Returns a raRank-object corresponding to given raId.
	 */
	public RARank getRaRank(Long raId) 
	{
		RARank retVal= null;
		SRelation sRelation= super.getSRelation(RARankImpl.createPrefixedId(raId));
		if (sRelation instanceof RARank)
			retVal= (RARank) sRelation;
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public EList<RANode> getRARoots() 
	{
		EList<RANode> retVal= null;
		for (RARank raRank: this.getRaRanks())
		{
			// if rank has no parent rank, and if rank has a node 
			if (	(raRank.getRaParentRank()== null) &&
					(raRank.getRaNode()!= null))
				
			{
				if (retVal == null)
					retVal= new BasicEList<RANode>();
				retVal.add(raRank.getRaNode());
			}
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RANode> getRATerminals() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns if the given node has a continuous text span.
	 * @param raNode node to check
	 * @return True, if the overlapped text is continuous, false else.
	 */
	public Boolean isRaContinuous(RANode raNode) 
	{
		RADocumentGraphTraverser traverser= new RADocumentGraphTraverser();
		traverser.setRaDocumentGraph(this);
		return(traverser.isRaContinuous(raNode));
	}

	/**
	 * Returns the text which is overlapped by this RaNodeobject. 
	 * If raNode-object is not a terminal, null is returned
	 * @param raNode node object for which text has to be computed
	 * @return text which is overlapped by given raNode object, or null if raNode object isn't a terminal raNode object
	 */
	public String getRaSpan(RANode raNode) 
	{
		RADocumentGraphTraverser traverser= new RADocumentGraphTraverser();
		traverser.setRaDocumentGraph(this);
		return(traverser.getRaSpan(raNode));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_CORPUS:
				if (resolve) return getRaCorpus();
				return basicGetRaCorpus();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_NODES:
				return getRaNodes();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_COMPONENTS:
				return getRaComponents();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_TEXTS:
				return getRaTexts();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_RANKS:
				return getRaRanks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_CORPUS:
				setRaCorpus((RACorpus)newValue);
				return;
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_NODES:
				getRaNodes().clear();
				getRaNodes().addAll((Collection<? extends RANode>)newValue);
				return;
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_COMPONENTS:
				getRaComponents().clear();
				getRaComponents().addAll((Collection<? extends RAComponent>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_CORPUS:
				setRaCorpus((RACorpus)null);
				return;
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_NODES:
				getRaNodes().clear();
				return;
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_COMPONENTS:
				getRaComponents().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_CORPUS:
				return basicGetRaCorpus() != null;
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_NODES:
				return !getRaNodes().isEmpty();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_COMPONENTS:
				return raComponents != null && !raComponents.isEmpty();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_TEXTS:
				return !getRaTexts().isEmpty();
			case relANNISPackage.RA_DOCUMENT_GRAPH__RA_RANKS:
				return !getRaRanks().isEmpty();
		}
		return super.eIsSet(featureID);
	}
} //RADocumentGraphImpl
