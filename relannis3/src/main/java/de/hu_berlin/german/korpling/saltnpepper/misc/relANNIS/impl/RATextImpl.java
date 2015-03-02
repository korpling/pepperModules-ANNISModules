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

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SFeature;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SNodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Text</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl#getRaText <em>Ra Text</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RATextImpl extends SNodeImpl implements RAText {
	
	/**
	 * Contains a new unused id value.
	 */
	private static Long currId= 0l;
	
	/**
	 * Returns a new unique and unused id value for texts.
	 * @return
	 */
	public static synchronized Long getNewId()
	{
		Long retVal= null;
		retVal= currId;
		currId++;
		return(retVal);
	}
	
	/**
	 * The default value of the '{@link #getRaId() <em>Ra Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaId()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_ID_EDEFAULT = null;

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
	 * The default value of the '{@link #getRaText() <em>Ra Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaText()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_TEXT_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RATextImpl() {
		super();
		this.setRaId(getNewId());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_TEXT;
	}

	public static String KW_NS= "relANNIS";
// ------------------------- start: handling ra id 	
	private static String ID_PREFIX="text";
	
	/**
	 * Returns a unique identifier for RADocumentGraph.
	 * @return
	 */
	public static String createPrefixedId(Long raId)
	{
		String retVal= null;
		retVal= ID_PREFIX+ raId;
		return(retVal);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaId() 
	{
		Long retVal= null;
		if (	(super.getSId()!= null) && 
				(!super.getSId().equals("")))
		{
			String idStr= super.getSId();
			retVal= new Long(idStr.replace(ID_PREFIX, ""));
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaId(Long newRaId) 
	{
		super.setSId(ID_PREFIX + newRaId.toString());
	}
// ------------------------- end: handling ra id
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String getRaName() 
	{
		return(super.getSName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaName(String newRaName) 
	{
		super.setSName(newRaName);
	}

	public static String KW_RATEXT= "RATEXT";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String getRaText() 
	{
		String retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RATEXT);
		if (sFeature!= null)
		{
			retVal= (sFeature.getSValue()!= null)?sFeature.getSValue().toString():"";
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaText(String newRaText) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RATEXT);
		if (	(newRaText!= null)&&
				(!newRaText.isEmpty()))
			newRaText= newRaText.replace("\n", " ").replace("\r", " ");
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RATEXT);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaText);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_TEXT__RA_ID:
				return getRaId();
			case relANNISPackage.RA_TEXT__RA_NAME:
				return getRaName();
			case relANNISPackage.RA_TEXT__RA_TEXT:
				return getRaText();
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
			case relANNISPackage.RA_TEXT__RA_ID:
				setRaId((Long)newValue);
				return;
			case relANNISPackage.RA_TEXT__RA_NAME:
				setRaName((String)newValue);
				return;
			case relANNISPackage.RA_TEXT__RA_TEXT:
				setRaText((String)newValue);
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
			case relANNISPackage.RA_TEXT__RA_ID:
				setRaId(RA_ID_EDEFAULT);
				return;
			case relANNISPackage.RA_TEXT__RA_NAME:
				setRaName(RA_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_TEXT__RA_TEXT:
				setRaText(RA_TEXT_EDEFAULT);
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
			case relANNISPackage.RA_TEXT__RA_ID:
				return RA_ID_EDEFAULT == null ? getRaId() != null : !RA_ID_EDEFAULT.equals(getRaId());
			case relANNISPackage.RA_TEXT__RA_NAME:
				return RA_NAME_EDEFAULT == null ? getRaName() != null : !RA_NAME_EDEFAULT.equals(getRaName());
			case relANNISPackage.RA_TEXT__RA_TEXT:
				return RA_TEXT_EDEFAULT == null ? getRaText() != null : !RA_TEXT_EDEFAULT.equals(getRaText());
		}
		return super.eIsSet(featureID);
	}

} //RATextImpl
