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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaId() <em>Ra Id</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaText_ref() <em>Ra Text ref</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaCorpus_ref() <em>Ra Corpus ref</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaNamespace() <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaName() <em>Ra Name</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaLeft() <em>Ra Left</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaRight() <em>Ra Right</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaToken_Index() <em>Ra Token Index</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaContinuous() <em>Ra Continuous</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaSpan() <em>Ra Span</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaAnnotations() <em>Ra Annotations</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaDocumentGraph() <em>Ra Document Graph</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RANode#getRaText() <em>Ra Text</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RANodeTest extends TestCase {

	/**
	 * The fixture for this RA Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RANode fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RANodeTest.class);
	}

	/**
	 * Constructs a new RA Node test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RANodeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RANode fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RANode getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRANode());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaId() <em>Ra Id</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaId()
	 */
	public void testGetRaId() 
	{
		assertNotNull(this.getFixture().getRaId());
		Long id= 0l;
		this.getFixture().setRaId(id);
		assertEquals(id, this.getFixture().getRaId());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaId(java.lang.Long) <em>Ra Id</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaId(java.lang.Long)
	 */
	public void testSetRaId() 
	{
		this.testGetRaId();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaText_ref() <em>Ra Text ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaText_ref()
	 */
	public void testGetRaText_ref() 
	{
		assertNull(this.getFixture().getRaText_ref());
		RAText raText= relANNISFactory.eINSTANCE.createRAText();
		Long id= 0l;
		raText.setRaId(id);
		this.getFixture().setRaText(raText);
		assertEquals(id, this.getFixture().getRaText_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaText_ref(java.lang.Long) <em>Ra Text ref</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaText_ref(java.lang.Long)
	 */
	public void testSetRaText_ref() 
	{
		this.testGetRaText_ref();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaCorpus_ref() <em>Ra Corpus ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaCorpus_ref()
	 */
	public void testGetRaCorpus_ref() 
	{
		assertNull(this.getFixture().getRaCorpus_ref());
		RACorpus raCorpus= relANNISFactory.eINSTANCE.createRACorpus();
		Long id= 0l;
		raCorpus.setRaId(id);
		RADocumentGraph docGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
		docGraph.setRaCorpus(raCorpus);
		this.getFixture().setRaDocumentGraph(docGraph);
		assertEquals(id, this.getFixture().getRaCorpus_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaCorpus_ref(java.lang.Long) <em>Ra Corpus ref</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaCorpus_ref(java.lang.Long)
	 */
	public void testSetRaCorpus_ref() 
	{
		this.testGetRaCorpus_ref();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaNamespace() <em>Ra Namespace</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaNamespace()
	 */
	public void testGetRaNamespace() 
	{
		assertNull(this.getFixture().getRaNamespace());
		String Namespace = "text1";
		this.getFixture().setRaNamespace(Namespace);
		assertEquals(Namespace, this.getFixture().getRaNamespace());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaNamespace(java.lang.String) <em>Ra Namespace</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaNamespace(java.lang.String)
	 */
	public void testSetRaNamespace() 
	{
		this.testGetRaNamespace();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaName() <em>Ra Name</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaName()
	 */
	public void testGetRaName() 
	{
		assertNull(this.getFixture().getRaName());
		String name = "text1";
		this.getFixture().setRaName(name);
		assertEquals(name, this.getFixture().getRaName());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaName(java.lang.String) <em>Ra Name</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaName(java.lang.String)
	 */
	public void testSetRaName() 
	{
		this.testGetRaName();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaLeft() <em>Ra Left</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaLeft()
	 */
	public void testGetRaLeft() 
	{
		assertNull(this.getFixture().getRaLeft());
		Long Left= 0l;
		this.getFixture().setRaLeft(Left);
		assertEquals(Left, this.getFixture().getRaLeft());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaLeft(java.lang.Long) <em>Ra Left</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaLeft(java.lang.Long)
	 */
	public void testSetRaLeft() 
	{
		this.testGetRaLeft();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaRight() <em>Ra Right</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaRight()
	 */
	public void testGetRaRight() 
	{
		assertNull(this.getFixture().getRaRight());
		Long Right= 0l;
		this.getFixture().setRaRight(Right);
		assertEquals(Right, this.getFixture().getRaRight());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaRight(java.lang.Long) <em>Ra Right</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaRight(java.lang.Long)
	 */
	public void testSetRaRight() 
	{
		this.testGetRaRight();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaToken_Index() <em>Ra Token Index</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaToken_Index()
	 */
	public void testGetRaToken_Index() 
	{
		assertNull(this.getFixture().getRaToken_Index());
		Long Token_index= 0l;
		this.getFixture().setRaToken_Index(Token_index);
		assertEquals(Token_index, this.getFixture().getRaToken_Index());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaToken_Index(java.lang.Long) <em>Ra Token Index</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaToken_Index(java.lang.Long)
	 */
	public void testSetRaToken_Index() 
	{
		this.testGetRaToken_Index();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaContinuous() <em>Ra Continuous</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaContinuous()
	 */
	public void testGetRaContinuous() 
	{
		assertNull(this.getFixture().getRaContinuous());
		Boolean continuous= true;
		this.getFixture().setRaContinuous(continuous);
		assertEquals(continuous, this.getFixture().getRaContinuous());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaContinuous(java.lang.Boolean) <em>Ra Continuous</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaContinuous(java.lang.Boolean)
	 */
	public void testSetRaContinuous() 
	{
		this.testGetRaContinuous();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaSpan() <em>Ra Span</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaSpan()
	 */
	public void testGetRaSpan() 
	{
		assertNull(this.getFixture().getRaSpan());
		RAText raText= relANNISFactory.eINSTANCE.createRAText();
		raText.setRaText("This is a sample text");
		RADocumentGraph raDocGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
		raDocGraph.addSNode(raText);
		this.getFixture().setRaText(raText);
		this.getFixture().setRaLeft(0l);
		this.getFixture().setRaRight(4l);
		raDocGraph.addSNode(this.getFixture());
		assertEquals("This", this.getFixture().getRaSpan());
//		assertNull(this.getFixture().getRaSpan());
//		String span = "The text which is overlapped by this node.";
//		this.getFixture().setRaSpan(span);
//		assertEquals(span, this.getFixture().getRaSpan());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaSpan(java.lang.String) <em>Ra Span</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaSpan(java.lang.String)
	 */
	public void testSetRaSpan() 
	{
		this.testGetRaSpan();
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaDocumentGraph() <em>Ra Document Graph</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaDocumentGraph()
	 */
	public void testGetRaDocumentGraph() 
	{
		assertNull(this.getFixture().getRaDocumentGraph());
		RADocumentGraph docGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
		this.getFixture().setRaDocumentGraph(docGraph);
		assertEquals(docGraph, this.getFixture().getRaDocumentGraph());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaDocumentGraph(de.hub.corpling.relANNIS.RADocumentGraph) <em>Ra Document Graph</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaDocumentGraph(de.hub.corpling.relANNIS.RADocumentGraph)
	 */
	public void testSetRaDocumentGraph() 
	{
		this.testGetRaDocumentGraph();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#getRaText() <em>Ra Text</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#getRaText()
	 */
	public void testGetRaText() 
	{
		assertNull(this.getFixture().getRaText());
		RAText raText= relANNISFactory.eINSTANCE.createRAText();
		this.getFixture().setRaText(raText);
		assertEquals(raText, this.getFixture().getRaText());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RANode#setRaText(de.hub.corpling.relANNIS.RAText) <em>Ra Text</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RANode#setRaText(de.hub.corpling.relANNIS.RAText)
	 */
	public void testSetRaText() 
	{
		this.testGetRaText();
	}

} //RANodeTest
