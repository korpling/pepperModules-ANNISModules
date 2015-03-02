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

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl#getRaType <em>Ra Type</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl#getRaNamespace <em>Ra Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RAComponentImpl extends EObjectImpl implements RAComponent {
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
	 * The cached value of the '{@link #getRaId() <em>Ra Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaId()
	 * @generated
	 * @ordered
	 */
	protected Long raId = RA_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getRaType() <em>Ra Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaType()
	 * @generated
	 * @ordered
	 */
	protected static final RA_COMPONENT_TYPE RA_TYPE_EDEFAULT = RA_COMPONENT_TYPE.D;

	/**
	 * The cached value of the '{@link #getRaType() <em>Ra Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaType()
	 * @generated
	 * @ordered
	 */
	protected RA_COMPONENT_TYPE raType = RA_TYPE_EDEFAULT;

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
	 * The cached value of the '{@link #getRaName() <em>Ra Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaName()
	 * @generated
	 * @ordered
	 */
	protected String raName = RA_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getRaNamespace() <em>Ra Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaNamespace()
	 * @generated
	 * @ordered
	 */
	protected String raNamespace = RA_NAMESPACE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RAComponentImpl() {
		super();
		this.setRaId(getNewId());
	}

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long getRaId() {
		return raId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRaId(Long newRaId) {
		Long oldRaId = raId;
		raId = newRaId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_COMPONENT__RA_ID, oldRaId, raId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RA_COMPONENT_TYPE getRaType() {
		return raType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaType(RA_COMPONENT_TYPE newRaType) {
		RA_COMPONENT_TYPE oldRaType = raType;
		raType = newRaType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_COMPONENT__RA_TYPE, oldRaType, raType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRaName() {
		return raName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRaName(String newRaName) {
		String oldRaName = raName;
		raName = newRaName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_COMPONENT__RA_NAME, oldRaName, raName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRaNamespace() {
		return raNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRaNamespace(String newRaNamespace) {
		String oldRaNamespace = raNamespace;
		raNamespace = newRaNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_COMPONENT__RA_NAMESPACE, oldRaNamespace, raNamespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_COMPONENT__RA_ID:
				return getRaId();
			case relANNISPackage.RA_COMPONENT__RA_TYPE:
				return getRaType();
			case relANNISPackage.RA_COMPONENT__RA_NAME:
				return getRaName();
			case relANNISPackage.RA_COMPONENT__RA_NAMESPACE:
				return getRaNamespace();
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
			case relANNISPackage.RA_COMPONENT__RA_ID:
				setRaId((Long)newValue);
				return;
			case relANNISPackage.RA_COMPONENT__RA_TYPE:
				setRaType((RA_COMPONENT_TYPE)newValue);
				return;
			case relANNISPackage.RA_COMPONENT__RA_NAME:
				setRaName((String)newValue);
				return;
			case relANNISPackage.RA_COMPONENT__RA_NAMESPACE:
				setRaNamespace((String)newValue);
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
			case relANNISPackage.RA_COMPONENT__RA_ID:
				setRaId(RA_ID_EDEFAULT);
				return;
			case relANNISPackage.RA_COMPONENT__RA_TYPE:
				setRaType(RA_TYPE_EDEFAULT);
				return;
			case relANNISPackage.RA_COMPONENT__RA_NAME:
				setRaName(RA_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_COMPONENT__RA_NAMESPACE:
				setRaNamespace(RA_NAMESPACE_EDEFAULT);
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
			case relANNISPackage.RA_COMPONENT__RA_ID:
				return RA_ID_EDEFAULT == null ? raId != null : !RA_ID_EDEFAULT.equals(raId);
			case relANNISPackage.RA_COMPONENT__RA_TYPE:
				return raType != RA_TYPE_EDEFAULT;
			case relANNISPackage.RA_COMPONENT__RA_NAME:
				return RA_NAME_EDEFAULT == null ? raName != null : !RA_NAME_EDEFAULT.equals(raName);
			case relANNISPackage.RA_COMPONENT__RA_NAMESPACE:
				return RA_NAMESPACE_EDEFAULT == null ? raNamespace != null : !RA_NAMESPACE_EDEFAULT.equals(raNamespace);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (raId: ");
		result.append(raId);
		result.append(", raType: ");
		result.append(raType);
		result.append(", raName: ");
		result.append(raName);
		result.append(", raNamespace: ");
		result.append(raNamespace);
		result.append(')');
		return result.toString();
	}

} //RAComponentImpl
