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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Edge Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank() <em>Ra Rank</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank_ref() <em>Ra Rank ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaNamespace() <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaName() <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaValue() <em>Ra Value</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#useSAnnotation(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation) <em>Use SAnnotation</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RAEdgeAnnotationTest extends TestCase {

	/**
	 * The fixture for this RA Edge Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RAEdgeAnnotation fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RAEdgeAnnotationTest.class);
	}

	/**
	 * Constructs a new RA Edge Annotation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAEdgeAnnotationTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Edge Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RAEdgeAnnotation fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Edge Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RAEdgeAnnotation getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRAEdgeAnnotation());
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
	 * Tests the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#useSAnnotation(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation) <em>Use SAnnotation</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#useSAnnotation(de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation)
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RARankAnnotation#getRaRank() <em>Ra Rank</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARankAnnotation#getRaRank()
	 */
	public void testGetRaRank() 
	{
		assertNull(this.getFixture().getRaRank());
		RARank Rank= relANNISFactory.eINSTANCE.createRARank();
		this.getFixture().setRaRank(Rank);
		assertEquals(Rank, this.getFixture().getRaRank());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARankAnnotation#setRaRank(de.hub.corpling.relANNIS.RARank) <em>Ra Rank</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARankAnnotation#setRaRank(de.hub.corpling.relANNIS.RARank)
	 */
	public void testSetRaRank() 
	{
		this.testGetRaRank();
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARankAnnotation#getRaRank_ref() <em>Ra Rank ref</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARankAnnotation#getRaRank_ref()
	 */
	public void testGetRaRank_ref() 
	{
		assertNull(this.getFixture().getRaRank());
		assertNull(this.getFixture().getRaRank_ref());
		RARank Rank= relANNISFactory.eINSTANCE.createRARank();
		Long rankPre= 0l;
		Rank.setRaPre(rankPre);
		this.getFixture().setRaRank(Rank);
		assertEquals(Rank, this.getFixture().getRaRank());
		assertEquals(Rank.getRaPre(), this.getFixture().getRaRank_ref());
		
		Rank.setRaPre(rankPre);
		assertEquals(Rank.getRaPre(), this.getFixture().getRaRank_ref());
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RARankAnnotation#setRaRank_ref(java.lang.Long) <em>Ra Rank ref</em>}' feature setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RARankAnnotation#setRaRank_ref(java.lang.Long)
	 */
	public void testSetRaRank_ref() 
	{
		this.testGetRaRank_ref();
	}
} //RAEdgeAnnotationTest
