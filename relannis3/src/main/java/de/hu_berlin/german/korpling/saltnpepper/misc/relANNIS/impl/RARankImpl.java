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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SFeature;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SRelationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Rank</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaParentNode <em>Ra Parent Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaNode <em>Ra Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaAnnotations <em>Ra Annotations</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaPre <em>Ra Pre</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaPost <em>Ra Post</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaNode_ref <em>Ra Node ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaComponent_ref <em>Ra Component ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaParent_ref <em>Ra Parent ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaParentRank <em>Ra Parent Rank</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl#getRaComponent <em>Ra Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RARankImpl extends SRelationImpl implements RARank {
	/**
	 * The default value of the '{@link #getRaPre() <em>Ra Pre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaPre()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_PRE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaPost() <em>Ra Post</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaPost()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_POST_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaNode_ref() <em>Ra Node ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaNode_ref()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_NODE_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaComponent_ref() <em>Ra Component ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaComponent_ref()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_COMPONENT_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaParent_ref() <em>Ra Parent ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaParent_ref()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_PARENT_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRaParentRank() <em>Ra Parent Rank</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaParentRank()
	 * @generated
	 * @ordered
	 */
	protected RARank raParentRank;

	/**
	 * The cached value of the '{@link #getRaComponent() <em>Ra Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaComponent()
	 * @generated
	 * @ordered
	 */
	protected RAComponent raComponent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RARankImpl() {
		super();
		init();
	}

	private void init()
	{
		
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_RANK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RANode getRaParentNode() {
		RANode raParentNode = basicGetRaParentNode();
		return raParentNode != null && raParentNode.eIsProxy() ? (RANode)eResolveProxy((InternalEObject)raParentNode) : raParentNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RANode basicGetRaParentNode() 
	{
		RANode retVal= null;
		if (super.getSSource()!= null)
			retVal= (RANode)super.getSSource();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaParentNode(RANode newRaParentNode) 
	{
		super.setSSource(newRaParentNode);	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RANode getRaNode() {
		RANode raNode = basicGetRaNode();
		return raNode != null && raNode.eIsProxy() ? (RANode)eResolveProxy((InternalEObject)raNode) : raNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RANode basicGetRaNode() 
	{
		RANode retVal= null;
		if (super.getSTarget()!= null)
			retVal= (RANode)super.getSTarget();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaNode(RANode newRaNode) 
	{
		super.setSTarget(newRaNode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RAEdgeAnnotation> getRaAnnotations() 
	{
		EList<RAEdgeAnnotation> retVal= (EList<RAEdgeAnnotation>) (EList<? extends SAnnotation>) super.getSAnnotations();
		return(retVal);
	}

	private static String KW_NS= "relANNIS";
	
// ----------------------- start pre
	private static String PRE_PREFIX="rank";
	
	/**
	 * Returns a unique identifier for RADocumentGraph.
	 * @return
	 */
	public static String createPrefixedId(Long raPre)
	{
		String retVal= null;
		retVal= PRE_PREFIX+ raPre;
		return(retVal);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaPre() 
	{
		Long retVal= null;
		if (super.getSId()!= null)
		{
			String idStr= super.getSId();
			retVal= new Long(idStr.replace(PRE_PREFIX, ""));
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaPre(Long newRaPre) 
	{
		super.setSId(PRE_PREFIX + newRaPre.toString());
	}
// ----------------------- end post
// ----------------------- start post
	public static String KW_RAPOST= "post";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaPost() 
	{
		Long retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAPOST);
		if (sFeature!= null)
		{
			retVal= new Long(sFeature.getSValue().toString());
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaPost(Long newRaPost) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAPOST);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setNamespace(KW_NS);
			sFeature.setSName(KW_RAPOST);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaPost);
	}
// ----------------------- end post

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaNode_ref() 
	{
		Long retVal= null;
		if (this.getRaNode()!= null)
			retVal= this.getRaNode().getRaId();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaComponent_ref() 
	{
		Long retVal= null;
		if (this.getRaComponent()!= null)
		{
			retVal= this.getRaComponent().getRaId();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaParent_ref() 
	{
		Long retVal= null;
		if (this.getRaParentRank()!= null)
			retVal= this.getRaParentRank().getRaPre();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RARank getRaParentRank() {
		if (raParentRank != null && raParentRank.eIsProxy()) {
			InternalEObject oldRaParentRank = (InternalEObject)raParentRank;
			raParentRank = (RARank)eResolveProxy(oldRaParentRank);
			if (raParentRank != oldRaParentRank) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, relANNISPackage.RA_RANK__RA_PARENT_RANK, oldRaParentRank, raParentRank));
			}
		}
		return raParentRank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RARank basicGetRaParentRank() {
		return raParentRank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRaParentRank(RARank newRaParentRank) {
		RARank oldRaParentRank = raParentRank;
		raParentRank = newRaParentRank;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_RANK__RA_PARENT_RANK, oldRaParentRank, raParentRank));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAComponent getRaComponent() {
		if (raComponent != null && raComponent.eIsProxy()) {
			InternalEObject oldRaComponent = (InternalEObject)raComponent;
			raComponent = (RAComponent)eResolveProxy(oldRaComponent);
			if (raComponent != oldRaComponent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, relANNISPackage.RA_RANK__RA_COMPONENT, oldRaComponent, raComponent));
			}
		}
		return raComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAComponent basicGetRaComponent() {
		return raComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRaComponent(RAComponent newRaComponent) {
		RAComponent oldRaComponent = raComponent;
		raComponent = newRaComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_RANK__RA_COMPONENT, oldRaComponent, raComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_RANK__RA_PARENT_NODE:
				if (resolve) return getRaParentNode();
				return basicGetRaParentNode();
			case relANNISPackage.RA_RANK__RA_NODE:
				if (resolve) return getRaNode();
				return basicGetRaNode();
			case relANNISPackage.RA_RANK__RA_ANNOTATIONS:
				return getRaAnnotations();
			case relANNISPackage.RA_RANK__RA_PRE:
				return getRaPre();
			case relANNISPackage.RA_RANK__RA_POST:
				return getRaPost();
			case relANNISPackage.RA_RANK__RA_NODE_REF:
				return getRaNode_ref();
			case relANNISPackage.RA_RANK__RA_COMPONENT_REF:
				return getRaComponent_ref();
			case relANNISPackage.RA_RANK__RA_PARENT_REF:
				return getRaParent_ref();
			case relANNISPackage.RA_RANK__RA_PARENT_RANK:
				if (resolve) return getRaParentRank();
				return basicGetRaParentRank();
			case relANNISPackage.RA_RANK__RA_COMPONENT:
				if (resolve) return getRaComponent();
				return basicGetRaComponent();
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
			case relANNISPackage.RA_RANK__RA_PARENT_NODE:
				setRaParentNode((RANode)newValue);
				return;
			case relANNISPackage.RA_RANK__RA_NODE:
				setRaNode((RANode)newValue);
				return;
			case relANNISPackage.RA_RANK__RA_ANNOTATIONS:
				getRaAnnotations().clear();
				getRaAnnotations().addAll((Collection<? extends RAEdgeAnnotation>)newValue);
				return;
			case relANNISPackage.RA_RANK__RA_PRE:
				setRaPre((Long)newValue);
				return;
			case relANNISPackage.RA_RANK__RA_POST:
				setRaPost((Long)newValue);
				return;
			case relANNISPackage.RA_RANK__RA_PARENT_RANK:
				setRaParentRank((RARank)newValue);
				return;
			case relANNISPackage.RA_RANK__RA_COMPONENT:
				setRaComponent((RAComponent)newValue);
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
			case relANNISPackage.RA_RANK__RA_PARENT_NODE:
				setRaParentNode((RANode)null);
				return;
			case relANNISPackage.RA_RANK__RA_NODE:
				setRaNode((RANode)null);
				return;
			case relANNISPackage.RA_RANK__RA_ANNOTATIONS:
				getRaAnnotations().clear();
				return;
			case relANNISPackage.RA_RANK__RA_PRE:
				setRaPre(RA_PRE_EDEFAULT);
				return;
			case relANNISPackage.RA_RANK__RA_POST:
				setRaPost(RA_POST_EDEFAULT);
				return;
			case relANNISPackage.RA_RANK__RA_PARENT_RANK:
				setRaParentRank((RARank)null);
				return;
			case relANNISPackage.RA_RANK__RA_COMPONENT:
				setRaComponent((RAComponent)null);
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
			case relANNISPackage.RA_RANK__RA_PARENT_NODE:
				return basicGetRaParentNode() != null;
			case relANNISPackage.RA_RANK__RA_NODE:
				return basicGetRaNode() != null;
			case relANNISPackage.RA_RANK__RA_ANNOTATIONS:
				return !getRaAnnotations().isEmpty();
			case relANNISPackage.RA_RANK__RA_PRE:
				return RA_PRE_EDEFAULT == null ? getRaPre() != null : !RA_PRE_EDEFAULT.equals(getRaPre());
			case relANNISPackage.RA_RANK__RA_POST:
				return RA_POST_EDEFAULT == null ? getRaPost() != null : !RA_POST_EDEFAULT.equals(getRaPost());
			case relANNISPackage.RA_RANK__RA_NODE_REF:
				return RA_NODE_REF_EDEFAULT == null ? getRaNode_ref() != null : !RA_NODE_REF_EDEFAULT.equals(getRaNode_ref());
			case relANNISPackage.RA_RANK__RA_COMPONENT_REF:
				return RA_COMPONENT_REF_EDEFAULT == null ? getRaComponent_ref() != null : !RA_COMPONENT_REF_EDEFAULT.equals(getRaComponent_ref());
			case relANNISPackage.RA_RANK__RA_PARENT_REF:
				return RA_PARENT_REF_EDEFAULT == null ? getRaParent_ref() != null : !RA_PARENT_REF_EDEFAULT.equals(getRaParent_ref());
			case relANNISPackage.RA_RANK__RA_PARENT_RANK:
				return raParentRank != null;
			case relANNISPackage.RA_RANK__RA_COMPONENT:
				return raComponent != null;
		}
		return super.eIsSet(featureID);
	}

} //RARankImpl
