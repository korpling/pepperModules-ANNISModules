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

import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Rank</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentNode <em>Ra Parent Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode <em>Ra Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaAnnotations <em>Ra Annotations</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPre <em>Ra Pre</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPost <em>Ra Post</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode_ref <em>Ra Node ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent_ref <em>Ra Component ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParent_ref <em>Ra Parent ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentRank <em>Ra Parent Rank</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent <em>Ra Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank()
 * @model
 * @generated
 */
public interface RARank extends SRelation {
	/**
	 * Returns the value of the '<em><b>Ra Parent Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Parent Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Parent Node</em>' reference.
	 * @see #setRaParentNode(RANode)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaParentNode()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RANode getRaParentNode();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentNode <em>Ra Parent Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Parent Node</em>' reference.
	 * @see #getRaParentNode()
	 * @generated
	 */
	void setRaParentNode(RANode value);

	/**
	 * Returns the value of the '<em><b>Ra Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Node</em>' reference.
	 * @see #setRaNode(RANode)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaNode()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RANode getRaNode();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode <em>Ra Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Node</em>' reference.
	 * @see #getRaNode()
	 * @generated
	 */
	void setRaNode(RANode value);

	/**
	 * Returns the value of the '<em><b>Ra Annotations</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank <em>Ra Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Annotations</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Annotations</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaAnnotations()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank
	 * @model opposite="raRank" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EList<RAEdgeAnnotation> getRaAnnotations();

	/**
	 * Returns the value of the '<em><b>Ra Pre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Pre</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Pre</em>' attribute.
	 * @see #setRaPre(Long)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaPre()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaPre();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPre <em>Ra Pre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Pre</em>' attribute.
	 * @see #getRaPre()
	 * @generated
	 */
	void setRaPre(Long value);

	/**
	 * Returns the value of the '<em><b>Ra Post</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Post</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Post</em>' attribute.
	 * @see #setRaPost(Long)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaPost()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaPost();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPost <em>Ra Post</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Post</em>' attribute.
	 * @see #getRaPost()
	 * @generated
	 */
	void setRaPost(Long value);

	/**
	 * Returns the value of the '<em><b>Ra Node ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Node ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Node ref</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaNode_ref()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaNode_ref();

	/**
	 * Returns the value of the '<em><b>Ra Component ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Component ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Component ref</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaComponent_ref()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaComponent_ref();

	/**
	 * Returns the value of the '<em><b>Ra Parent ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Parent ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Parent ref</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaParent_ref()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaParent_ref();

	/**
	 * Returns the value of the '<em><b>Ra Parent Rank</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Parent Rank</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Parent Rank</em>' reference.
	 * @see #setRaParentRank(RARank)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaParentRank()
	 * @model
	 * @generated
	 */
	RARank getRaParentRank();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentRank <em>Ra Parent Rank</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Parent Rank</em>' reference.
	 * @see #getRaParentRank()
	 * @generated
	 */
	void setRaParentRank(RARank value);

	/**
	 * Returns the value of the '<em><b>Ra Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Component</em>' reference.
	 * @see #setRaComponent(RAComponent)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRARank_RaComponent()
	 * @model
	 * @generated
	 */
	RAComponent getRaComponent();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent <em>Ra Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Component</em>' reference.
	 * @see #getRaComponent()
	 * @generated
	 */
	void setRaComponent(RAComponent value);

} // RARank
