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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Corpus</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaId() <em>Ra Id</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaName() <em>Ra Name</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaType() <em>Ra Type</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaVersion() <em>Ra Version</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaPre() <em>Ra Pre</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaPost() <em>Ra Post</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaCorpusAnnotations() <em>Ra Corpus Annotations</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#getRaDocumentGraph() <em>Ra Document Graph</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpus#addRACorpusAnnotation(de.hub.corpling.relANNIS.RACorpusAnnotation) <em>Add RA Corpus Annotation</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RACorpusTest extends TestCase {

	/**
	 * The fixture for this RA Corpus test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RACorpus fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RACorpusTest.class);
	}

	/**
	 * Constructs a new RA Corpus test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpusTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Corpus test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RACorpus fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Corpus test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RACorpus getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRACorpus());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaId() <em>Ra Id</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaId()
	 */
	public void testGetRaId() 
	{
		Long id= 9l;
		this.getFixture().setRaId(id);
		assertEquals(id, this.getFixture().getRaId());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaId(java.lang.Long) <em>Ra Id</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaId(java.lang.Long)
	 */
	public void testSetRaId() 
	{
		this.testGetRaId();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaName() <em>Ra Name</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaName()
	 */
	public void testGetRaName() 
	{
		String name= "corpusName";
		this.getFixture().setRaName(name);
		assertEquals(name, this.getFixture().getRaName());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaName(java.lang.String) <em>Ra Name</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaName(java.lang.String)
	 */
	public void testSetRaName() 
	{
		this.testGetRaName();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaType() <em>Ra Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaType()
	 */
	public void testGetRaType() 
	{
		assertNull(this.getFixture().getRaType());
		RA_CORPUS_TYPE type= RA_CORPUS_TYPE.DOCUMENT;
		this.getFixture().setRaType(type);
		assertEquals(type, this.getFixture().getRaType());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaType(de.hub.corpling.relANNIS.RA_CORPUS_TYPE) <em>Ra Type</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaType(de.hub.corpling.relANNIS.RA_CORPUS_TYPE)
	 */
	public void testSetRaType() 
	{
		this.testGetRaType();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaVersion() <em>Ra Version</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaVersion()
	 */
	public void testGetRaVersion() 
	{
		assertNull(this.getFixture().getRaVersion());		
		String version= "v1.0";
		this.getFixture().setRaVersion(version);
		assertEquals(version, this.getFixture().getRaVersion());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaVersion(java.lang.String) <em>Ra Version</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaVersion(java.lang.String)
	 */
	public void testSetRaVersion() 
	{
		this.testGetRaVersion();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaPre() <em>Ra Pre</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaPre()
	 */
	public void testGetRaPre() 
	{
		for (Long i= 0l; i< 10; i++)
		{
			this.getFixture().setRaPre(i);
			assertEquals(i, this.getFixture().getRaPre());
		}
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaPre(java.lang.Long) <em>Ra Pre</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaPre(java.lang.Long)
	 */
	public void testSetRaPre() 
	{
		this.testGetRaPre();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaPost() <em>Ra Post</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaPost()
	 */
	public void testGetRaPost() 
	{
		for (Long i= 0l; i< 10; i++)
		{
			this.getFixture().setRaPost(i);
			assertEquals(i, this.getFixture().getRaPost());
		}
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaPost(java.lang.Long) <em>Ra Post</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaPost(java.lang.Long)
	 */
	public void testSetRaPost() 
	{
		this.testGetRaPost();
	}

	
	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaDocumentGraph() <em>Ra Document Graph</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaDocumentGraph()
	 */
	public void testGetRaDocumentGraph() 
	{
		RADocumentGraph docGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
		this.getFixture().setRaDocumentGraph(docGraph);
		assertEquals(docGraph, this.getFixture().getRaDocumentGraph());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#setRaDocumentGraph(de.hub.corpling.relANNIS.RADocumentGraph) <em>Ra Document Graph</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#setRaDocumentGraph(de.hub.corpling.relANNIS.RADocumentGraph)
	 */
	public void testSetRaDocumentGraph() 
	{
		this.testGetRaDocumentGraph();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#getRaCorpusAnnotations() <em>Ra Corpus Annotations</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#getRaCorpusAnnotations()
	 */
	public void testGetRaCorpusAnnotations() 
	{
		EList<RACorpusAnnotation> raCorpAnnos= new BasicEList<RACorpusAnnotation>();
		for (int i= 0; i< 10; i++)
		{
			RACorpusAnnotation raCorpAnno= relANNISFactory.eINSTANCE.createRACorpusAnnotation();
			raCorpAnno.setRaNamespace("ns"+ i);
			raCorpAnno.setRaName("name"+i);
			raCorpAnno.setRaValue("value"+ i);
			raCorpAnnos.add(raCorpAnno);
			this.getFixture().addRACorpusAnnotation(raCorpAnno);
		}
		assertTrue(raCorpAnnos.containsAll(this.getFixture().getRaCorpusAnnotations()));
		assertTrue(this.getFixture().getRaCorpusAnnotations().containsAll(raCorpAnnos));
	}
	
	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpus#addRACorpusAnnotation(de.hub.corpling.relANNIS.RACorpusAnnotation) <em>Add RA Corpus Annotation</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpus#addRACorpusAnnotation(de.hub.corpling.relANNIS.RACorpusAnnotation)
	 */
	public void testAddRACorpusAnnotation__RACorpusAnnotation() 
	{
		this.testGetRaCorpusAnnotations();
	}
} //RACorpusTest
