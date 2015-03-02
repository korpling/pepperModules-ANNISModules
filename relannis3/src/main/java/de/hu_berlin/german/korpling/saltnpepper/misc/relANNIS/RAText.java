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

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Text</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaText <em>Ra Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAText()
 * @model
 * @generated
 */
public interface RAText extends SNode {
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
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAText_RaId()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaId();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaId <em>Ra Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Id</em>' attribute.
	 * @see #getRaId()
	 * @generated
	 */
	void setRaId(Long value);

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
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAText_RaName()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaName <em>Ra Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Name</em>' attribute.
	 * @see #getRaName()
	 * @generated
	 */
	void setRaName(String value);

	/**
	 * Returns the value of the '<em><b>Ra Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Text</em>' attribute.
	 * @see #setRaText(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRAText_RaText()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaText();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaText <em>Ra Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Text</em>' attribute.
	 * @see #getRaText()
	 * @generated
	 */
	void setRaText(String value);

} // RAText
