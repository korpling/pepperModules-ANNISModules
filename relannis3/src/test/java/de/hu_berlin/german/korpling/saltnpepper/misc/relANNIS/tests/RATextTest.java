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

import junit.framework.TestCase;
import junit.textui.TestRunner;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Text</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RAText#getRaId() <em>Ra Id</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RAText#getRaName() <em>Ra Name</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RAText#getRaText() <em>Ra Text</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RATextTest extends TestCase {

	/**
	 * The fixture for this RA Text test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RAText fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RATextTest.class);
	}

	/**
	 * Constructs a new RA Text test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RATextTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Text test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RAText fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Text test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RAText getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(relANNISFactory.eINSTANCE.createRAText());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAText#getRaId() <em>Ra Id</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAText#getRaId()
	 */
	public void testGetRaId() 
	{
		assertNotNull(this.getFixture().getRaId());
		Long id= 0l;
		this.getFixture().setRaId(id);
		assertEquals(id, this.getFixture().getRaId());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAText#setRaId(java.lang.Long) <em>Ra Id</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAText#setRaId(java.lang.Long)
	 */
	public void testSetRaId() 
	{
		this.testGetRaId();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAText#getRaName() <em>Ra Name</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAText#getRaName()
	 */
	public void testGetRaName() 
	{
		assertNull(this.getFixture().getRaName());
		String name = "text1";
		this.getFixture().setRaName(name);
		assertEquals(name, this.getFixture().getRaName());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAText#setRaName(java.lang.String) <em>Ra Name</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAText#setRaName(java.lang.String)
	 */
	public void testSetRaName() 
	{
		this.testGetRaName();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAText#getRaText() <em>Ra Text</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAText#getRaText()
	 */
	public void testGetRaText() 
	{
		assertNull(this.getFixture().getRaText());
		String text = "This is a sample text";
		this.getFixture().setRaText(text);
		assertEquals(text, this.getFixture().getRaText());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAText#setRaText(java.lang.String) <em>Ra Text</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAText#setRaText(java.lang.String)
	 */
	public void testSetRaText() 
	{
		this.testGetRaText();
	}

} //RATextTest
