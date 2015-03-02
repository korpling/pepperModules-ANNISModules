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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaType <em>Ra Type</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaNamespace <em>Ra Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAComponent()
 * @model
 * @generated
 */
public interface RAComponent extends EObject {
	/**
	 * Returns the value of the '<em><b>Ra Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Id</em>' attribute.
	 * @see #setRaId(Long)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAComponent_RaId()
	 * @model
	 * @generated
	 */
	Long getRaId();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaId <em>Ra Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Id</em>' attribute.
	 * @see #getRaId()
	 * @generated
	 */
	void setRaId(Long value);

	/**
	 * Returns the value of the '<em><b>Ra Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Type</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE
	 * @see #setRaType(RA_COMPONENT_TYPE)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAComponent_RaType()
	 * @model default=""
	 * @generated
	 */
	RA_COMPONENT_TYPE getRaType();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaType <em>Ra Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Type</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE
	 * @see #getRaType()
	 * @generated
	 */
	void setRaType(RA_COMPONENT_TYPE value);

	/**
	 * Returns the value of the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Name</em>' attribute.
	 * @see #setRaName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAComponent_RaName()
	 * @model
	 * @generated
	 */
	String getRaName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaName <em>Ra Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Name</em>' attribute.
	 * @see #getRaName()
	 * @generated
	 */
	void setRaName(String value);

	/**
	 * Returns the value of the '<em><b>Ra Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Namespace</em>' attribute.
	 * @see #setRaNamespace(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAComponent_RaNamespace()
	 * @model
	 * @generated
	 */
	String getRaNamespace();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaNamespace <em>Ra Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Namespace</em>' attribute.
	 * @see #getRaNamespace()
	 * @generated
	 */
	void setRaNamespace(String value);

} // RAComponent
