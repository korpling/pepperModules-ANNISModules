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

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Node Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode <em>Ra Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode_ref <em>Ra Node ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNamespace <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaValue <em>Ra Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRANodeAnnotation()
 * @model
 * @generated
 */
public interface RANodeAnnotation extends SAnnotation {
	/**
	 * Returns the value of the '<em><b>Ra Node</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaAnnotations <em>Ra Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Node</em>' reference.
	 * @see #setRaNode(RANode)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRANodeAnnotation_RaNode()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaAnnotations
	 * @model opposite="raAnnotations" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RANode getRaNode();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode <em>Ra Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Node</em>' reference.
	 * @see #getRaNode()
	 * @generated
	 */
	void setRaNode(RANode value);

	/**
	 * Returns the value of the '<em><b>Ra Node ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Node ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Node ref</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRANodeAnnotation_RaNode_ref()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaNode_ref();

	/**
	 * Returns the value of the '<em><b>Ra Namespace</b></em>' attribute.
	 * This can either be the value of the object being contained in this object, or be delegated to 
	 * the {@link SAnnotation} object, if set. See {@link #useSAnnotation(SAnnotation)}. The value of the {@link SAnnotation}
	 * object only will be used, when the internal value is null, means {@link #setRaNamespace(String)} has never been invoked
	 * or its parameter was null.
	 * 
	 * @return the value of the '<em>Ra Namespace</em>' attribute.
	 * @see #setRaNamespace(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRANodeAnnotation_RaNamespace()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaNamespace();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNamespace <em>Ra Namespace</em>}' attribute.
	 * If the given parameter is not null, the internal value will be set to the parameter, and the corresponding getter will not
	 * use the internal {@link SAnnotation} object, if one is set.
	 * @param value the new value of the '<em>Ra Namespace</em>' attribute.
	 * @see #getRaNamespace()
	 * @generated
	 */
	void setRaNamespace(String value);

	/**
	 * Returns the value of the '<em><b>Ra Name</b></em>' attribute.
	 * This can either be the value of the object being contained in this object, or be delegated to 
	 * the {@link SAnnotation} object, if set. See {@link #useSAnnotation(SAnnotation)}. The value of the {@link SAnnotation}
	 * object only will be used, when the internal value is null, means {@link #setRaName(String)} has never been invoked
	 * or its parameter was null.
	 * 
	 * @return the value of the '<em>Ra Name</em>' attribute.
	 * @see #setRaName(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRANodeAnnotation_RaName()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaName <em>Ra Name</em>}' attribute.
	 * If the given parameter is not null, the internal value will be set to the parameter, and the corresponding getter will not
	 * use the internal {@link SAnnotation} object, if one is set.
	 * @param value the new value of the '<em>Ra Name</em>' attribute.
	 * @see #getRaName()
	 * @generated
	 */
	void setRaName(String value);

	/**
	 * Returns the value of the '<em><b>Ra Value</b></em>' attribute.
	 * This can either be the value of the object being contained in this object, or be delegated to 
	 * the {@link SAnnotation} object, if set. See {@link #useSAnnotation(SAnnotation)}. The value of the {@link SAnnotation}
	 * object only will be used, when the internal value is null, means {@link #setRaValue(String)} has never been invoked
	 * or its parameter was null. 
	 * 
	 * @return the value of the '<em>Ra Value</em>' attribute.
	 * @see #setRaValue(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRANodeAnnotation_RaValue()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaValue();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaValue <em>Ra Value</em>}' attribute.
	 * If the given parameter is not null, the internal value will be set to the parameter, and the corresponding getter will not
	 * use the internal {@link SAnnotation} object, if one is set.
	 * @param value the new value of the '<em>Ra Value</em>' attribute.
	 * @see #getRaValue()
	 * @generated
	 */
	void setRaValue(String value);

	/**
	 * Sets a passed {@link SAbstractAnnotation} to be used as delegator for methods {@link #getRaNamespace()}, {@link #getRaName()}
	 * and {@link #getRaValue()}. If one of these values is set by calling the corresponding setters, the values of the 
	 * {@link SAbstractAnnotation} object will not be changed.
	 * @model
	 */
	void useSAnnotation(SAbstractAnnotation sAnnotation);

} // RANodeAnnotation
