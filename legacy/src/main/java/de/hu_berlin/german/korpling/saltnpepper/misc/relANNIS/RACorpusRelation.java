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

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RA Corpus Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSuperCorpus <em>Ra Super Corpus</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSubCorpus <em>Ra Sub Corpus</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpusRelation()
 * @model
 * @generated
 */
public interface RACorpusRelation extends SRelation {
	/**
	 * Returns the value of the '<em><b>Ra Super Corpus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Super Corpus</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Super Corpus</em>' reference.
	 * @see #setRaSuperCorpus(RACorpus)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpusRelation_RaSuperCorpus()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RACorpus getRaSuperCorpus();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSuperCorpus <em>Ra Super Corpus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Super Corpus</em>' reference.
	 * @see #getRaSuperCorpus()
	 * @generated
	 */
	void setRaSuperCorpus(RACorpus value);

	/**
	 * Returns the value of the '<em><b>Ra Sub Corpus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ra Sub Corpus</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ra Sub Corpus</em>' reference.
	 * @see #setRaSubCorpus(RACorpus)
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#getRACorpusRelation_RaSubCorpus()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RACorpus getRaSubCorpus();

	/**
	 * Sets the value of the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSubCorpus <em>Ra Sub Corpus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ra Sub Corpus</em>' reference.
	 * @see #getRaSubCorpus()
	 * @generated
	 */
	void setRaSubCorpus(RACorpus value);

} // RACorpusRelation
