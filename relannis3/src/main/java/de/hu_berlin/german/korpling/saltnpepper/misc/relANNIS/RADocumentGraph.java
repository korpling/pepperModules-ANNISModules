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

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Document Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaCorpus <em>Ra Corpus</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaNodes <em>Ra Nodes</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaComponents <em>Ra Components</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaTexts <em>Ra Texts</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaRanks <em>Ra Ranks</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRADocumentGraph()
 * @model
 * @generated
 */
public interface RADocumentGraph extends SGraph {
	/**
	 * Returns the value of the '<em><b>Ra Corpus</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaDocumentGraph <em>Ra Document Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Corpus</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Corpus</em>' reference.
	 * @see #setRaCorpus(RACorpus)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRADocumentGraph_RaCorpus()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaDocumentGraph
	 * @model opposite="raDocumentGraph" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RACorpus getRaCorpus();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaCorpus <em>Ra Corpus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Corpus</em>' reference.
	 * @see #getRaCorpus()
	 * @generated
	 */
	void setRaCorpus(RACorpus value);

	/**
	 * Returns the value of the '<em><b>Ra Nodes</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaDocumentGraph <em>Ra Document Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Nodes</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Nodes</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRADocumentGraph_RaNodes()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaDocumentGraph
	 * @model opposite="raDocumentGraph" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EList<RANode> getRaNodes();

	/**
	 * Returns the value of the '<em><b>Ra Components</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Components</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Components</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRADocumentGraph_RaComponents()
	 * @model
	 * @generated
	 */
	EList<RAComponent> getRaComponents();

	/**
	 * Returns the value of the '<em><b>Ra Texts</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Texts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Texts</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRADocumentGraph_RaTexts()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<RAText> getRaTexts();

	/**
	 * Returns the value of the '<em><b>Ra Ranks</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Ranks</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Ranks</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRADocumentGraph_RaRanks()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<RARank> getRaRanks();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Boolean isRaContinuous(RANode raNode);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getRaSpan(RANode raNode);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Boolean isTerminalRaNode(RANode raNode);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	RAText getRaText(Long raId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	RANode getRaNode(Long raId);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	RARank getRaRank(Long raPre);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<RANode> getRARoots();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<RANode> getRATerminals();

} // RADocumentGraph
