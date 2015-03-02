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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GraphFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Label;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SDATATYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SAnnotationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Node Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl#getRaNode <em>Ra Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl#getRaNode_ref <em>Ra Node ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl#getRaNamespace <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl#getRaValue <em>Ra Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RANodeAnnotationImpl extends SAnnotationImpl implements RANodeAnnotation {
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
	 * The default value of the '{@link #getRaNamespace() <em>Ra Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_NAMESPACE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaName() <em>Ra Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaName()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaValue() <em>Ra Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaValue()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_VALUE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RANodeAnnotationImpl() {
		super();
	}
	
	/**
	 * Constructs a new object of this class and includes the given {@link SAbstractAnnotation} object to delegate
	 * some methods.
	 */
	protected RANodeAnnotationImpl(SAbstractAnnotation sAbstractAnnotation) {
		super();
		this.useSAnnotation(sAbstractAnnotation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_NODE_ANNOTATION;
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
	 * {@inheritDoc RANodeAnnotation#getRaNamespace()}
	 */
	public String getRaNamespace() 
	{
		return(this.getNamespace());
	}

	@Override
	public String getNamespace()
	{
		if (super.getSNS()!= null)
			return(super.getSNS());
		else if (this.getSAnnotation()!= null) 
			return(this.getSAnnotation().getSNS());
		else return(null);
	}
	
	/**
	 * {@inheritDoc RANodeAnnotation#setRaNamespace(String)}
	 */
	public void setRaNamespace(String newRaNamespace) 
	{
		super.setSNS(newRaNamespace);
	}

	/**
	 * {@inheritDoc RANodeAnnotation#getRaName()}
	 */
	public String getRaName() 
	{
		return(this.getName());
	}
	
	@Override
	public String getName()
	{
		if (super.getSName()!= null)
			return(super.getSName());
		else if (this.getSAnnotation()!= null) 
			return(this.getSAnnotation().getSName());
		else return(null);
	}
	
	/**
	 * {@inheritDoc RANodeAnnotation#setRaName(String)}
	 */
	public void setRaName(String newRaName) 
	{
		super.setSName(newRaName);
	}

	/**
	 * {@inheritDoc RANodeAnnotation#getRaValue()}
	 */
	public String getRaValue() 
	{
		return((String) this.getValue());
	}
	
	@Override
	public Object getValue()
	{
		if (super.getSValue()!= null)
			return(super.getSValueSTEXT());
		else if (this.getSAnnotation()!= null) 
			return(this.getSAnnotation().getSValueSTEXT());
		else return(null);
	}
	
	/**
	 * {@inheritDoc RANodeAnnotation#setRaValue(String)}
	 */
	public void setRaValue(String newRaValue) 
	{
		newRaValue= newRaValue.replace("\n", "").replace("\r", "");
		super.setSValue(newRaValue);
	}

	/**
	 * {@link SAnnotation} object to which the methods of this object will be delegated to.
	 */
	private SAbstractAnnotation sAbstractAnnotation= null;
	
	/**
	 * Returns the {@link SAnnotation} object to which the methods of this object will be delegated to.
	 */
	private SAbstractAnnotation getSAnnotation() {
		return sAbstractAnnotation;
	}

	/**
	 * Sets the {@link SAnnotation} object to which the methods of this object will be delegated to.
	 */
	private void setSAnnotation(SAbstractAnnotation sAbstractAnnotation) {
		this.sAbstractAnnotation = sAbstractAnnotation;
	}

	/**
	 * {@inheritDoc RANodeAnnotation#useSAnnotation(SAnnotation)}
	 */
	public void useSAnnotation(SAbstractAnnotation sAbstractAnnotation) 
	{
		this.setSAnnotation(sAbstractAnnotation);
	}

	/**
	 * Returns value type of contained {@link SAbstractAnnotation} object if exist, {@link SAnnotation#getSValueType()}
	 */
	public SDATATYPE getSValueType() 
	{
		if (this.getSAnnotation()!=  null)
			return(this.getSAnnotation().getSValueType());
		else
			return(super.getSValueType());
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RANode basicGetRaNode() 
	{
		return((RANode)super.getSAnnotatableElement());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaNode(RANode newRaNode) 
	{
		super.setSAnnotatableElement(newRaNode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaNode_ref() 
	{
		Long corp_ref= null;
		if (	(super.getSAnnotatableElement()!= null)&&
				(!(super.getSAnnotatableElement() instanceof RANode)))
			throw new RelANNISException("Cannot return the Node reference of corrpus annotation object, because corrresponding object is not of type RANode.");
		if (super.getSAnnotatableElement()!= null)
		{
			corp_ref= new Long(((RANode)super.getSAnnotatableElement()).getRaId());
		}
		return(corp_ref);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NODE:
				if (resolve) return getRaNode();
				return basicGetRaNode();
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NODE_REF:
				return getRaNode_ref();
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAMESPACE:
				return getRaNamespace();
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAME:
				return getRaName();
			case relANNISPackage.RA_NODE_ANNOTATION__RA_VALUE:
				return getRaValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NODE:
				setRaNode((RANode)newValue);
				return;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAMESPACE:
				setRaNamespace((String)newValue);
				return;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAME:
				setRaName((String)newValue);
				return;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_VALUE:
				setRaValue((String)newValue);
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
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NODE:
				setRaNode((RANode)null);
				return;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAMESPACE:
				setRaNamespace(RA_NAMESPACE_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAME:
				setRaName(RA_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_VALUE:
				setRaValue(RA_VALUE_EDEFAULT);
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
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NODE:
				return basicGetRaNode() != null;
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NODE_REF:
				return RA_NODE_REF_EDEFAULT == null ? getRaNode_ref() != null : !RA_NODE_REF_EDEFAULT.equals(getRaNode_ref());
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAMESPACE:
				return RA_NAMESPACE_EDEFAULT == null ? getRaNamespace() != null : !RA_NAMESPACE_EDEFAULT.equals(getRaNamespace());
			case relANNISPackage.RA_NODE_ANNOTATION__RA_NAME:
				return RA_NAME_EDEFAULT == null ? getRaName() != null : !RA_NAME_EDEFAULT.equals(getRaName());
			case relANNISPackage.RA_NODE_ANNOTATION__RA_VALUE:
				return RA_VALUE_EDEFAULT == null ? getRaValue() != null : !RA_VALUE_EDEFAULT.equals(getRaValue());
		}
		return super.eIsSet(featureID);
	}

} //RANodeAnnotationImpl
