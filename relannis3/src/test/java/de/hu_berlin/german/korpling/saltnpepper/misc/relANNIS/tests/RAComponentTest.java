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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Component</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RAComponentTest extends TestCase {

	/**
	 * The fixture for this RA Component test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RAComponent fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RAComponentTest.class);
	}

	/**
	 * Constructs a new RA Component test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAComponentTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Component test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RAComponent fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Component test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RAComponent getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRAComponent());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#getRaId() <em>Ra Id</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#getRaId()
	 */
	public void testGetRaId() 
	{
		assertNotNull(this.getFixture().getRaId());
		Long id= 0l;
		this.getFixture().setRaId(id);
		assertEquals(id, this.getFixture().getRaId());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#setRaId(java.lang.Long) <em>Ra Id</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#setRaId(java.lang.Long)
	 */
	public void testSetRaId() 
	{
		this.testGetRaId();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#getRaType() <em>Ra Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#getRaType()
	 */
	public void testGetRaType() 
	{
		RA_COMPONENT_TYPE raType= RA_COMPONENT_TYPE.P;
		this.getFixture().setRaType(raType);
		assertEquals(raType, this.getFixture().getRaType());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#setRaType(de.hub.corpling.relANNIS.RA_COMPONENT_TYPE) <em>Ra Type</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#setRaType(de.hub.corpling.relANNIS.RA_COMPONENT_TYPE)
	 */
	public void testSetRaType() 
	{
		this.testGetRaType();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#getRaName() <em>Ra Name</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#getRaName()
	 */
	public void testGetRaName() 
	{
		assertNull(this.getFixture().getRaName());
		String raName= "name";
		this.getFixture().setRaName(raName);
		assertEquals(raName, this.getFixture().getRaName());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#setRaName(java.lang.String) <em>Ra Name</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#setRaName(java.lang.String)
	 */
	public void testSetRaName() 
	{
		this.testGetRaName();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#getRaNamespace() <em>Ra Namespace</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#getRaNamespace()
	 */
	public void testGetRaNamespace() 
	{
		assertNull(this.getFixture().getRaNamespace());
		String raNamespace= "name";
		this.getFixture().setRaNamespace(raNamespace);
		assertEquals(raNamespace, this.getFixture().getRaNamespace());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RAComponent#setRaNamespace(java.lang.String) <em>Ra Namespace</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RAComponent#setRaNamespace(java.lang.String)
	 */
	public void testSetRaNamespace() 
	{
		this.testGetRaNamespace();
	}

} //RAComponentTest
