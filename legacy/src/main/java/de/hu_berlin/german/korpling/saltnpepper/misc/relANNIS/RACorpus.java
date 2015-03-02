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

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Corpus</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaType <em>Ra Type</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaVersion <em>Ra Version</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPre <em>Ra Pre</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPost <em>Ra Post</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusAnnotations <em>Ra Corpus Annotations</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaDocumentGraph <em>Ra Document Graph</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusGraph <em>Ra Corpus Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus()
 * @model
 * @generated
 */
public interface RACorpus extends SNode {
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
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaId()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaId();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaId <em>Ra Id</em>}' attribute.
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
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaName()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaName();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaName <em>Ra Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Name</em>' attribute.
	 * @see #getRaName()
	 * @generated
	 */
	void setRaName(String value);

	/**
	 * Returns the value of the '<em><b>Ra Type</b></em>' attribute.
	 * The literals are from the enumeration {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Type</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE
	 * @see #setRaType(RA_CORPUS_TYPE)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaType()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RA_CORPUS_TYPE getRaType();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaType <em>Ra Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Type</em>' attribute.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE
	 * @see #getRaType()
	 * @generated
	 */
	void setRaType(RA_CORPUS_TYPE value);

	/**
	 * Returns the value of the '<em><b>Ra Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Version</em>' attribute.
	 * @see #setRaVersion(String)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaVersion()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getRaVersion();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaVersion <em>Ra Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Version</em>' attribute.
	 * @see #getRaVersion()
	 * @generated
	 */
	void setRaVersion(String value);

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
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaPre()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaPre();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPre <em>Ra Pre</em>}' attribute.
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
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaPost()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Long getRaPost();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPost <em>Ra Post</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Post</em>' attribute.
	 * @see #getRaPost()
	 * @generated
	 */
	void setRaPost(Long value);

	/**
	 * Returns the value of the '<em><b>Ra Corpus Annotations</b></em>' reference list.
	 * The list contents are of type {@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation}.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaCorpus <em>Ra Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Corpus Annotations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Corpus Annotations</em>' reference list.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaCorpusAnnotations()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaCorpus
	 * @model opposite="raCorpus" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EList<RACorpusAnnotation> getRaCorpusAnnotations();

	/**
	 * Returns the value of the '<em><b>Ra Document Graph</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaCorpus <em>Ra Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Document Graph</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Document Graph</em>' reference.
	 * @see #setRaDocumentGraph(RADocumentGraph)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaDocumentGraph()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaCorpus
	 * @model opposite="raCorpus" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RADocumentGraph getRaDocumentGraph();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaDocumentGraph <em>Ra Document Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Document Graph</em>' reference.
	 * @see #getRaDocumentGraph()
	 * @generated
	 */
	void setRaDocumentGraph(RADocumentGraph value);

	/**
	 * Returns the value of the '<em><b>Ra Corpus Graph</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph#getRaCorpora <em>Ra Corpora</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Corpus Graph</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Corpus Graph</em>' container reference.
	 * @see #setRaCorpusGraph(RACorpusGraph)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpus_RaCorpusGraph()
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph#getRaCorpora
	 * @model opposite="raCorpora" volatile="true" derived="true"
	 * @generated
	 */
	RACorpusGraph getRaCorpusGraph();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusGraph <em>Ra Corpus Graph</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Corpus Graph</em>' container reference.
	 * @see #getRaCorpusGraph()
	 * @generated
	 */
	void setRaCorpusGraph(RACorpusGraph value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addRACorpusAnnotation(RACorpusAnnotation raCorpusAnnotation);

} // RACorpus
