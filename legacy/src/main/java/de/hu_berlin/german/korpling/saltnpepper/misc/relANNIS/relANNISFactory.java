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

import org.eclipse.emf.ecore.EFactory;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage
 * @generated
 */
public interface relANNISFactory extends EFactory {
	/**
	 * Name of the annis namespace, with which annis internals are handled
	 */
	public static final String ATT_NS_ANNIS="annis";
	/**
	 * The name of the annotyation containing time information for audio data
	 */
	public static final String ATT_NAME_TIME="time";
	
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	relANNISFactory eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>RA Corpus</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Corpus</em>'.
	 * @generated
	 */
	RACorpus createRACorpus();

	/**
	 * Returns a new object of class '<em>RA Corpus Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Corpus Annotation</em>'.
	 * @generated
	 */
	RACorpusAnnotation createRACorpusAnnotation();

	/**
	 * Creates a new object of the class {@link SAnnotation} and includes the given {@link SAnnotation} object to delegate
	 * some methods.
	 * @return a new object of class '<em>RA Node Annotation</em>'.
	 */
	RACorpusAnnotation createRACorpusAnnotation(SAbstractAnnotation sAbstractAnnotation);
	
	/**
	 * Returns a new object of class '<em>RA Corpus Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Corpus Graph</em>'.
	 * @generated
	 */
	RACorpusGraph createRACorpusGraph();

	/**
	 * Returns a new object of class '<em>RA Corpus Relation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Corpus Relation</em>'.
	 * @generated
	 */
	RACorpusRelation createRACorpusRelation();

	/**
	 * Returns a new object of class '<em>RA Document Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Document Graph</em>'.
	 * @generated
	 */
	RADocumentGraph createRADocumentGraph();

	/**
	 * Returns a new object of class '<em>RA Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Node</em>'.
	 * @generated
	 */
	RANode createRANode();

	/**
	 * Returns a new object of class '<em>RA Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Text</em>'.
	 * @generated
	 */
	RAText createRAText();

	/**
	 * Returns a new object of class '<em>RA Node Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Node Annotation</em>'.
	 * @generated
	 */
	RANodeAnnotation createRANodeAnnotation();
	
	/**
	 * Creates a new object of the class {@link SAnnotation} and includes the given {@link SAnnotation} object to delegate
	 * some methods.
	 * @return a new object of class '<em>RA Node Annotation</em>'.
	 */
	RANodeAnnotation createRANodeAnnotation(SAbstractAnnotation sAbstractAnnotation);

	/**
	 * Returns a new object of class '<em>RA Rank</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Rank</em>'.
	 * @generated
	 */
	RARank createRARank();

	/**
	 * Returns a new object of class '<em>RA Edge Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Edge Annotation</em>'.
	 * @generated
	 */
	RAEdgeAnnotation createRAEdgeAnnotation();

	/**
	 * Creates a new object of the class {@link SAnnotation} and includes the given {@link SAnnotation} object to delegate
	 * some methods.
	 * @return a new object of class '<em>RA Node Annotation</em>'.
	 */
	RAEdgeAnnotation createRAEdgeAnnotation(SAbstractAnnotation sAbstractAnnotation);
	
	/**
	 * Returns a new object of class '<em>RA Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RA Component</em>'.
	 * @generated
	 */
	RAComponent createRAComponent();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	relANNISPackage getrelANNISPackage();

} //relANNISFactory
