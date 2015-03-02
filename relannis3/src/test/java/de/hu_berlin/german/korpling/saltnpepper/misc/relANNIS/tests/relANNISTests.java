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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>relANNIS</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class relANNISTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new relANNISTests("relANNIS Tests");
		suite.addTestSuite(RACorpusTest.class);
		suite.addTestSuite(RACorpusAnnotationTest.class);
		suite.addTestSuite(RACorpusGraphTest.class);
		suite.addTestSuite(RACorpusRelationTest.class);
		suite.addTestSuite(RADocumentGraphTest.class);
		suite.addTestSuite(RANodeTest.class);
		suite.addTestSuite(RATextTest.class);
		suite.addTestSuite(RANodeAnnotationTest.class);
		suite.addTestSuite(RARankTest.class);
		suite.addTestSuite(RAEdgeAnnotationTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public relANNISTests(String name) {
		super(name);
	}

} //relANNISTests
