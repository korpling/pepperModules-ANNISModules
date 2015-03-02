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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Node Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode() <em>Ra Node</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode_ref() <em>Ra Node ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNamespace() <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaName() <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaValue() <em>Ra Value</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#useSAnnotation(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation) <em>Use SAnnotation</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RANodeAnnotationTest extends TestCase {

	/**
	 * The fixture for this RA Node Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RANodeAnnotation fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RANodeAnnotationTest.class);
	}

	/**
	 * Constructs a new RA Node Annotation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RANodeAnnotationTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Node Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RANodeAnnotation fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Node Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RANodeAnnotation getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRANodeAnnotation());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusAnnotation#getRaNamespace() <em>Ra Namespace</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusAnnotation#getRaNamespace()
	 */
	public void testGetRaNamespace() 
	{
		assertNull(this.getFixture().getRaNamespace());
		String ns= "ns";
		this.getFixture().setRaNamespace(ns);
		assertEquals(ns, this.getFixture().getRaNamespace());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusAnnotation#setRaNamespace(java.lang.String) <em>Ra Namespace</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusAnnotation#setRaNamespace(java.lang.String)
	 */
	public void testSetRaNamespace() 
	{
		this.testGetRaNamespace();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusAnnotation#getRaName() <em>Ra Name</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusAnnotation#getRaName()
	 */
	public void testGetRaName() 
	{
		assertNull(this.getFixture().getRaName());
		String name= "annoName";
		this.getFixture().setRaName(name);
		assertEquals(name, this.getFixture().getRaName());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusAnnotation#setRaName(java.lang.String) <em>Ra Name</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusAnnotation#setRaName(java.lang.String)
	 */
	public void testSetRaName() 
	{
		this.testGetRaName();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusAnnotation#getRaValue() <em>Ra Value</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusAnnotation#getRaValue()
	 */
	public void testGetRaValue() 
	{
		assertNull(this.getFixture().getRaValue());
		String Value= "annoValue";
		this.getFixture().setRaValue(Value);
		assertEquals(Value, this.getFixture().getRaValue());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusAnnotation#setRaValue(java.lang.String) <em>Ra Value</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusAnnotation#setRaValue(java.lang.String)
	 */
	public void testSetRaValue() 
	{
		this.testGetRaValue();
	}
	
	/**
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#useSAnnotation(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation) <em>Use SAnnotation</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#useSAnnotation(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation)
	 */
	public void testUseSAnnotation__SAbstractAnnotation()
	{
		String sAnnoName= "annoName";
		String sAnnoNameSpace= "annoNameSpace";
		String sAnnoValue= "annoValue";
		
		SAnnotation sAnno= SaltFactory.eINSTANCE.createSAnnotation();
		sAnno.setSNS(sAnnoNameSpace);
		sAnno.setSName(sAnnoName);
		sAnno.setSValue(sAnnoValue);
		
		this.getFixture().useSAnnotation(sAnno);
		
		assertEquals(sAnnoNameSpace, this.getFixture().getRaNamespace());
		assertEquals(sAnnoName, this.getFixture().getRaName());
		assertEquals(sAnnoValue, this.getFixture().getRaValue());
		
		RANodeAnnotation raNodeAnno= relANNISFactory.eINSTANCE.createRANodeAnnotation(sAnno);
		
		assertEquals(sAnnoNameSpace, raNodeAnno.getRaNamespace());
		assertEquals(sAnnoName, raNodeAnno.getRaName());
		assertEquals(sAnnoValue, raNodeAnno.getRaValue());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANodeAnnotation#getRaNode() <em>Ra Node</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANodeAnnotation#getRaNode()
	 */
	public void testGetRaNode() 
	{
		assertNull(this.getFixture().getRaNode());
		RANode Node= relANNISFactory.eINSTANCE.createRANode();
		this.getFixture().setRaNode(Node);
		assertEquals(Node, this.getFixture().getRaNode());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANodeAnnotation#setRaNode(de.hub.corpling.relANNIS.RANode) <em>Ra Node</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANodeAnnotation#setRaNode(de.hub.corpling.relANNIS.RANode)
	 */
	public void testSetRaNode() 
	{
		this.testGetRaNode();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANodeAnnotation#getRaNode_ref() <em>Ra Node ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANodeAnnotation#getRaNode_ref()
	 */
	public void testGetRaNode_ref() 
	{
		assertNull(this.getFixture().getRaNode());
		assertNull(this.getFixture().getRaNode_ref());
		RANode Node= relANNISFactory.eINSTANCE.createRANode();
		Long NodeId= 0l;
		Node.setRaId(NodeId);
		this.getFixture().setRaNode(Node);
		assertEquals(Node, this.getFixture().getRaNode());
		assertEquals(Node.getRaId(), this.getFixture().getRaNode_ref());
		
		Node.setRaId(NodeId);
		assertEquals(Node.getRaId(), this.getFixture().getRaNode_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANodeAnnotation#setRaNode_ref(java.lang.Long) <em>Ra Node ref</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANodeAnnotation#setRaNode_ref(java.lang.Long)
	 */
	public void testSetRaNode_ref() 
	{
		this.testGetRaNode_ref();
	}
} //RANodeAnnotationTest
