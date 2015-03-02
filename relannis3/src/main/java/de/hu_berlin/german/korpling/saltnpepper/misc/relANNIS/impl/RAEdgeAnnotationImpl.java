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

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SDATATYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SAnnotationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Edge Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl#getRaRank <em>Ra Rank</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl#getRaRank_ref <em>Ra Rank ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl#getRaNamespace <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl#getRaValue <em>Ra Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generatedO
 */
public class RAEdgeAnnotationImpl extends SAnnotationImpl implements RAEdgeAnnotation {
	/**
	 * The default value of the '{@link #getRaRank_ref() <em>Ra Rank ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaRank_ref()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_RANK_REF_EDEFAULT = null;

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
	protected RAEdgeAnnotationImpl() {
		super();
	}
	
	/**
	 * Constructs a new object of this class and includes the given {@link SAbstractAnnotation} object to delegate
	 * some methods.
	 */
	protected RAEdgeAnnotationImpl(SAbstractAnnotation sAbstractAnnotation) {
		super();
		this.useSAnnotation(sAbstractAnnotation);
	}
	
	/**
	 * Constructs a new object of this class and includes the given {@link SAnnotation} object to delegate
	 * some methods.
	 */
	protected RAEdgeAnnotationImpl(SAnnotation sAnnotation) {
		super();
		this.useSAnnotation(sAnnotation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_EDGE_ANNOTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RARank getRaRank() {
		RARank raRank = basicGetRaRank();
		return raRank != null && raRank.eIsProxy() ? (RARank)eResolveProxy((InternalEObject)raRank) : raRank;
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
	@Override
	public String getRaValue() 
	{
		return((String) this.getValue());
	}
	
	/**
	 * {@inheritDoc RANodeAnnotation#setRaValue(String)}
	 */
	@Override
	public void setRaValue(String newRaValue) 
	{
		newRaValue= newRaValue.replace("\n", "").replace("\r", "");
		super.setSValue(newRaValue);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RARank basicGetRaRank() 
	{
		return((RARank)super.getSAnnotatableElement());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaRank(RARank newRaRank) 
	{
		super.setSAnnotatableElement(newRaRank);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaRank_ref() 
	{
		Long corp_ref= null;
		if (	(super.getSAnnotatableElement()!= null)&&
				(!(super.getSAnnotatableElement() instanceof RARank)))
			throw new RelANNISException("Cannot return the Rank reference of corrpus annotation object, because corrresponding object is not of type RARank.");
		if (super.getSAnnotatableElement()!= null)
			corp_ref= new Long(((RARank)super.getSAnnotatableElement()).getRaPre());
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
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_RANK:
				if (resolve) return getRaRank();
				return basicGetRaRank();
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_RANK_REF:
				return getRaRank_ref();
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAMESPACE:
				return getRaNamespace();
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAME:
				return getRaName();
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_VALUE:
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
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_RANK:
				setRaRank((RARank)newValue);
				return;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAMESPACE:
				setRaNamespace((String)newValue);
				return;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAME:
				setRaName((String)newValue);
				return;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_VALUE:
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
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_RANK:
				setRaRank((RARank)null);
				return;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAMESPACE:
				setRaNamespace(RA_NAMESPACE_EDEFAULT);
				return;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAME:
				setRaName(RA_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_VALUE:
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
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_RANK:
				return basicGetRaRank() != null;
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_RANK_REF:
				return RA_RANK_REF_EDEFAULT == null ? getRaRank_ref() != null : !RA_RANK_REF_EDEFAULT.equals(getRaRank_ref());
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAMESPACE:
				return RA_NAMESPACE_EDEFAULT == null ? getRaNamespace() != null : !RA_NAMESPACE_EDEFAULT.equals(getRaNamespace());
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_NAME:
				return RA_NAME_EDEFAULT == null ? getRaName() != null : !RA_NAME_EDEFAULT.equals(getRaName());
			case relANNISPackage.RA_EDGE_ANNOTATION__RA_VALUE:
				return RA_VALUE_EDEFAULT == null ? getRaValue() != null : !RA_VALUE_EDEFAULT.equals(getRaValue());
		}
		return super.eIsSet(featureID);
	}

} //RAEdgeAnnotationImpl
