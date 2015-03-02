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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Rank</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaParentNode() <em>Ra Parent Node</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaNode() <em>Ra Node</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaAnnotations() <em>Ra Annotations</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaPre() <em>Ra Pre</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaPost() <em>Ra Post</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaNode_ref() <em>Ra Node ref</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaComponent_ref() <em>Ra Component ref</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RARank#getRaParent_ref() <em>Ra Parent ref</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RARankTest extends TestCase {

	/**
	 * The fixture for this RA Rank test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RARank fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RARankTest.class);
	}

	/**
	 * Constructs a new RA Rank test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RARankTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Rank test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RARank fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Rank test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RARank getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRARank());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaParentNode() <em>Ra Parent Node</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaParentNode()
	 */
	public void testGetRaParentNode() 
	{
		assertNull(this.getFixture().getRaParentNode());
		RANode node= relANNISFactory.eINSTANCE.createRANode();
		this.getFixture().setRaParentNode(node);
		assertEquals(node, this.getFixture().getRaParentNode());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaParentNode(de.hub.corpling.relANNIS.RANode) <em>Ra Parent Node</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaParentNode(de.hub.corpling.relANNIS.RANode)
	 */
	public void testSetRaParentNode() 
	{
		this.testGetRaParentNode();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaNode() <em>Ra Node</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaNode()
	 */
	public void testGetRaNode() 
	{
		assertNull(this.getFixture().getRaNode());
		RANode node= relANNISFactory.eINSTANCE.createRANode();
		this.getFixture().setRaNode(node);
		assertEquals(node, this.getFixture().getRaNode());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaNode(de.hub.corpling.relANNIS.RANode) <em>Ra Node</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaNode(de.hub.corpling.relANNIS.RANode)
	 */
	public void testSetRaNode() 
	{
		this.testGetRaNode();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaAnnotations() <em>Ra Annotations</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaAnnotations()
	 */
	public void testGetRaAnnotations() 
	{
		EList<RAEdgeAnnotation> raEdgeAnnoList= new BasicEList<RAEdgeAnnotation>();
		for (int i= 0; i< 10; i++)
		{
			RAEdgeAnnotation raEdgeAnnotation= relANNISFactory.eINSTANCE.createRAEdgeAnnotation();
			raEdgeAnnotation.setRaName("name"+ i);
			raEdgeAnnotation.setRaValue("value"+i);
			raEdgeAnnoList.add(raEdgeAnnotation);
			this.getFixture().addSAnnotation(raEdgeAnnotation);
		}
		assertTrue(raEdgeAnnoList.containsAll(this.getFixture().getRaAnnotations()));
		assertTrue(this.getFixture().getRaAnnotations().containsAll(raEdgeAnnoList));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaAnnotations(de.hub.corpling.relANNIS.RAEdgeAnnotation) <em>Ra Annotations</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaAnnotations(de.hub.corpling.relANNIS.RAEdgeAnnotation)
	 */
	public void testSetRaAnnotations() 
	{
		this.testGetRaAnnotations();	
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaPre() <em>Ra Pre</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaPre()
	 */
	public void testGetRaPre() 
	{
		assertNull(this.getFixture().getRaPre());
		Long pre= 0l;
		this.getFixture().setRaPre(pre);
		assertEquals(pre, this.getFixture().getRaPre());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaPre(java.lang.Long) <em>Ra Pre</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaPre(java.lang.Long)
	 */
	public void testSetRaPre() 
	{
		this.testGetRaPre();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaPost() <em>Ra Post</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaPost()
	 */
	public void testGetRaPost() 
	{
		assertNull(this.getFixture().getRaPost());
		Long Post= 0l;
		this.getFixture().setRaPost(Post);
		assertEquals(Post, this.getFixture().getRaPost());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaPost(java.lang.Long) <em>Ra Post</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaPost(java.lang.Long)
	 */
	public void testSetRaPost() 
	{
		this.testGetRaPost();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaNode_ref() <em>Ra Node ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaNode_ref()
	 */
	public void testGetRaNode_ref() 
	{
		assertNull(this.getFixture().getRaNode_ref());
		RANode node= relANNISFactory.eINSTANCE.createRANode();
		Long id= 0l;
		node.setRaId(id);
		this.getFixture().setRaNode(node);
		assertEquals(id, this.getFixture().getRaNode_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaNode_ref(java.lang.Long) <em>Ra Node ref</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaNode_ref(java.lang.Long)
	 */
	public void testSetRaNode_ref() 
	{
		this.testGetRaNode_ref();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaComponent_ref() <em>Ra Component ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaComponent_ref()
	 */
	public void testGetRaComponent_ref() 
	{
		RAComponent raComponent= relANNISFactory.eINSTANCE.createRAComponent();
		Long raCompId= 1l;
		raComponent.setRaId(raCompId);
		this.getFixture().setRaComponent(raComponent);
		assertEquals(raCompId, this.getFixture().getRaComponent_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#getRaParent_ref() <em>Ra Parent ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#getRaParent_ref()
	 */
	public void testGetRaParent_ref() 
	{
		assertNull(this.getFixture().getRaParent_ref());
		RARank rank= relANNISFactory.eINSTANCE.createRARank();
		Long pre= 0l;
		rank.setRaPre(pre);
		this.getFixture().setRaParentRank(rank);
		assertEquals(pre, this.getFixture().getRaParent_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARank#setRaParent_ref(java.lang.Long) <em>Ra Parent ref</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARank#setRaParent_ref(java.lang.Long)
	 */
	public void testSetRaParent_ref() 
	{
		this.testGetRaParent_ref();
	}

} //RARankTest
