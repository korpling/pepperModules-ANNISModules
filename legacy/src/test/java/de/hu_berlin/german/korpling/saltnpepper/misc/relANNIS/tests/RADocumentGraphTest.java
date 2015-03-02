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

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Document Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaCorpus() <em>Ra Corpus</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaNodes() <em>Ra Nodes</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaTexts() <em>Ra Texts</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaRanks() <em>Ra Ranks</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#isRaContinuous(de.hub.corpling.relANNIS.RANode) <em>Is Ra Continuous</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaSpan(de.hub.corpling.relANNIS.RANode) <em>Get Ra Span</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#isTerminalRaNode(de.hub.corpling.relANNIS.RANode) <em>Is Terminal Ra Node</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaText(java.lang.Long) <em>Get Ra Text</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaNode(java.lang.Long) <em>Get Ra Node</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaRank(java.lang.Long) <em>Get Ra Rank</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRARoots() <em>Get RA Roots</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RADocumentGraph#getRATerminals() <em>Get RA Terminals</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RADocumentGraphTest extends TestCase {

	/**
	 * The fixture for this RA Document Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RADocumentGraph fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RADocumentGraphTest.class);
	}

	/**
	 * Constructs a new RA Document Graph test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RADocumentGraphTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Document Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RADocumentGraph fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Document Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RADocumentGraph getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRADocumentGraph());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaCorpus() <em>Ra Corpus</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaCorpus()
	 */
	public void testGetRaCorpus() 
	{
		assertNull(this.getFixture().getRaCorpus());
		RACorpus raCorpus= relANNISFactory.eINSTANCE.createRACorpus();
		this.getFixture().setRaCorpus(raCorpus);
		assertEquals(raCorpus, this.getFixture().getRaCorpus());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#setRaCorpus(de.hub.corpling.relANNIS.RACorpus) <em>Ra Corpus</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#setRaCorpus(de.hub.corpling.relANNIS.RACorpus)
	 */
	public void testSetRaCorpus() 
	{
		this.testGetRaCorpus();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaNodes() <em>Ra Nodes</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaNodes()
	 */
	public void testGetRaNodes() 
	{
		EList<RANode> raNodes= new BasicEList<RANode>();
		for (int i= 0; i < 10; i++)
		{
			RANode RANode= relANNISFactory.eINSTANCE.createRANode();
			raNodes.add(RANode);
			this.getFixture().addSNode(RANode);
		}
		assertTrue(raNodes.containsAll(this.getFixture().getRaNodes()));
		assertTrue(this.getFixture().getRaNodes().containsAll(raNodes));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaTexts() <em>Ra Texts</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaTexts()
	 */
	public void testGetRaTexts() 
	{
		EList<RAText> raTexts= new BasicEList<RAText>();
		for (int i= 0; i < 10; i++)
		{
			RAText raText= relANNISFactory.eINSTANCE.createRAText();
			raTexts.add(raText);
			this.getFixture().addSNode(raText);
		}
		assertTrue(raTexts.containsAll(this.getFixture().getRaTexts()));
		assertTrue(this.getFixture().getRaTexts().containsAll(raTexts));
		
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaRanks() <em>Ra Ranks</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaRanks()
	 */
	public void testGetRaRanks() 
	{
		EList<RARank> raRanks= new BasicEList<RARank>();
		for (int i= 0; i < 10; i++)
		{
			RARank raRank= relANNISFactory.eINSTANCE.createRARank();
			raRanks.add(raRank);
			this.getFixture().addSRelation(raRank);
		}
		assertTrue(raRanks.containsAll(this.getFixture().getRaRanks()));
		assertTrue(this.getFixture().getRaRanks().containsAll(raRanks));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#isContinuous(de.hub.corpling.relANNIS.RANode) <em>Is Continuous</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#isContinuous(de.hub.corpling.relANNIS.RANode)
	 */
	public void testIsRaContinuous__RANode() 
	{
//		RAText raText= relANNISFactory.eINSTANCE.createRAText();
//		raText.setRaText("This is a sample text.");
//		this.getFixture().addSNode(raText);
//		RANode raNode1= relANNISFactory.eINSTANCE.createRANode();
//		raNode1.setRaText(raText);
//		raNode1.setRaLeft(0l);
//		raNode1.setRaRight(4l);
//		this.getFixture().addSNode(raNode1);
//		assertTrue(this.getFixture().isRaContinuous(raNode1));
//		
//		RANode raNode2= relANNISFactory.eINSTANCE.createRANode();
//		raNode2.setRaText(raText);
//		raNode2.setRaLeft(8l);
//		raNode2.setRaRight(9l);
//		this.getFixture().addSNode(raNode2);
//		assertTrue(this.getFixture().isRaContinuous(raNode2));
//		
//		RANode raNode3= relANNISFactory.eINSTANCE.createRANode();
//		raNode3.setRaText(raText);
//		raNode3.setRaLeft(0l);
//		raNode3.setRaRight(9l);
//		this.getFixture().addSNode(raNode3);
//		
//		RARank raRank1= relANNISFactory.eINSTANCE.createRARank();
//		raRank1.setRaParentNode(raNode3);
//		raRank1.setRaNode(raNode1);
//		this.getFixture().addSRelation(raRank1);
//		
//		RARank raRank2= relANNISFactory.eINSTANCE.createRARank();
//		raRank2.setRaParentNode(raNode3);
//		raRank2.setRaNode(raNode2);
//		this.getFixture().addSRelation(raRank2);
//		assertFalse(this.getFixture().isRaContinuous(raNode3));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaSpan(de.hub.corpling.relANNIS.RANode) <em>Get Ra Span</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaSpan(de.hub.corpling.relANNIS.RANode)
	 */
	public void testGetRaSpan__RANode() 
	{
		RAText raText= relANNISFactory.eINSTANCE.createRAText();
		raText.setRaText("This is a sample text.");
		this.getFixture().addSNode(raText);
		RANode raNode1= relANNISFactory.eINSTANCE.createRANode();
		raNode1.setRaText(raText);
		raNode1.setRaLeft(0l);
		raNode1.setRaRight(4l);
		this.getFixture().addSNode(raNode1);
		assertEquals("This", this.getFixture().getRaSpan(raNode1));
		
		RANode raNode2= relANNISFactory.eINSTANCE.createRANode();
		raNode2.setRaText(raText);
		raNode2.setRaLeft(8l);
		raNode2.setRaRight(9l);
		this.getFixture().addSNode(raNode2);
		assertEquals("a", this.getFixture().getRaSpan(raNode2));
		
		RANode raNode3= relANNISFactory.eINSTANCE.createRANode();
		raNode3.setRaText(raText);
		raNode3.setRaLeft(0l);
		raNode3.setRaRight(9l);
		this.getFixture().addSNode(raNode3);
		
		RARank raRank1= relANNISFactory.eINSTANCE.createRARank();
		raRank1.setRaParentNode(raNode3);
		raRank1.setRaNode(raNode1);
		this.getFixture().addSRelation(raRank1);
		
		RARank raRank2= relANNISFactory.eINSTANCE.createRARank();
		raRank2.setRaParentNode(raNode3);
		raRank2.setRaNode(raNode2);
		this.getFixture().addSRelation(raRank2);
		assertEquals("This is a", this.getFixture().getRaSpan(raNode3));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#isTerminalRaNode(de.hub.corpling.relANNIS.RANode) <em>Is Terminal Ra Node</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#isTerminalRaNode(de.hub.corpling.relANNIS.RANode)
	 */
	public void testIsTerminalRaNode__RANode() 
	{
		RAText raText= relANNISFactory.eINSTANCE.createRAText();
		raText.setRaText("This is a sample text.");
		this.getFixture().addSNode(raText);
		RANode raNode1= relANNISFactory.eINSTANCE.createRANode();
		raNode1.setRaText(raText);
		raNode1.setRaLeft(0l);
		raNode1.setRaRight(4l);
		this.getFixture().addSNode(raNode1);
		assertNotNull(this.getFixture().isTerminalRaNode(raNode1));
		assertTrue(this.getFixture().isTerminalRaNode(raNode1));
		
		RANode raNode2= relANNISFactory.eINSTANCE.createRANode();
		raNode2.setRaText(raText);
		raNode2.setRaLeft(8l);
		raNode2.setRaRight(9l);
		this.getFixture().addSNode(raNode2);
		assertTrue(this.getFixture().isTerminalRaNode(raNode2));
		
		RANode raNode3= relANNISFactory.eINSTANCE.createRANode();
		raNode3.setRaText(raText);
		raNode3.setRaLeft(0l);
		raNode3.setRaRight(9l);
		this.getFixture().addSNode(raNode3);
		
		RARank raRank1= relANNISFactory.eINSTANCE.createRARank();
		raRank1.setRaParentNode(raNode3);
		raRank1.setRaNode(raNode1);
		this.getFixture().addSRelation(raRank1);
		
		RARank raRank2= relANNISFactory.eINSTANCE.createRARank();
		raRank2.setRaParentNode(raNode3);
		raRank2.setRaNode(raNode2);
		this.getFixture().addSRelation(raRank2);
		assertTrue(this.getFixture().isTerminalRaNode(raNode3));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaText(java.lang.Long) <em>Get Ra Text</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaText(java.lang.Long)
	 */
	public void testGetRaText__Long() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaNode(java.lang.Long) <em>Get Ra Node</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaNode(java.lang.Long)
	 */
	public void testGetRaNode__Long() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRaRank(java.lang.Long) <em>Get Ra Rank</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRaRank(java.lang.Long)
	 */
	public void testGetRaRank__Long() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRARoots() <em>Get RA Roots</em>}' operation.
	 * 		node1
	 * 		/	\
	 * node2	node3	node4
	 * roots:	node1, node4
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRARoots()
	 */
	public void testGetRARoots() 
	{
		EList<RANode> roots= new BasicEList<RANode>();
		RANode node1= null;
		RANode node2= null;
		RANode node3= null;
		RANode node4= null;
		RARank rank1= null;
		RARank rank2= null;
		RARank rank3= null;
		RARank rank4= null;
		
		node1= relANNISFactory.eINSTANCE.createRANode();
		node1.setRaName("node1");
		this.getFixture().addSNode(node1);
		roots.add(node1);
		
		node2= relANNISFactory.eINSTANCE.createRANode();
		node2.setRaName("node2");
		this.getFixture().addSNode(node2);
		
		node3= relANNISFactory.eINSTANCE.createRANode();
		node3.setRaName("node3");
		this.getFixture().addSNode(node3);
		
		node4= relANNISFactory.eINSTANCE.createRANode();
		node4.setRaName("node4");
		this.getFixture().addSNode(node4);
		roots.add(node4);
		
		rank1= relANNISFactory.eINSTANCE.createRARank();
		rank1.setRaNode(node1);
		this.getFixture().addSRelation(rank1);
		
		rank2= relANNISFactory.eINSTANCE.createRARank();
		rank2.setRaParentNode(node2);
		rank2.setRaParentRank(rank1);
		rank2.setRaNode(node2);
		this.getFixture().addSRelation(rank2);
		
		rank3= relANNISFactory.eINSTANCE.createRARank();
		rank3.setRaParentNode(node3);
		rank3.setRaParentRank(rank1);
		rank3.setRaNode(node3);
		this.getFixture().addSRelation(rank3);
		
		
		rank4= relANNISFactory.eINSTANCE.createRARank();
		rank4.setRaNode(node4);
		this.getFixture().addSRelation(rank4);
		
		assertTrue(roots.containsAll(this.getFixture().getRARoots()));
		assertTrue(this.getFixture().getRARoots().containsAll(roots));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RADocumentGraph#getRATerminals() <em>Get RA Terminals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RADocumentGraph#getRATerminals()
	 */
	public void testGetRATerminals() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
	}
} //RADocumentGraphTest
