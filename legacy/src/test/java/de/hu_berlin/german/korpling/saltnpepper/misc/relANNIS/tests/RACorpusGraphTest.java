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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>RA Corpus Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora() <em>Ra Corpora</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpusGraph#getRARoots() <em>Get RA Roots</em>}</li>
 *   <li>{@link de.hub.corpling.relANNIS.RACorpusGraph#generateRAPPOrder() <em>Generate RAPP Order</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RACorpusGraphTest extends TestCase {

	/**
	 * The fixture for this RA Corpus Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RACorpusGraph fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RACorpusGraphTest.class);
	}

	/**
	 * Constructs a new RA Corpus Graph test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpusGraphTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this RA Corpus Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RACorpusGraph fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this RA Corpus Graph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RACorpusGraph getFixture() {
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
		setFixture(relANNISFactory.eINSTANCE.createRACorpusGraph());
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
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora() <em>Ra Corpora</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora()
	 */
	public void testGetRaCorpora() 
	{
		EList<RACorpus> raCorpora= new BasicEList<RACorpus>();
		for (int i= 0; i< 10; i++)
		{
			RACorpus raCorpus= relANNISFactory.eINSTANCE.createRACorpus();
			raCorpora.add(raCorpus);
			this.getFixture().addSNode(raCorpus);
		}
		assertTrue(raCorpora.containsAll(this.getFixture().getRaCorpora()));
		assertTrue(this.getFixture().getRaCorpora().containsAll(raCorpora));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusGraph#getRARoots() <em>Get RA Roots</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusGraph#getRARoots()
	 */
	public void testGetRARoots() 
	{
		EList<RACorpus> roots= new BasicEList<RACorpus>();
		RACorpus corpus1= null;
		RACorpus corpus2= null;
		RACorpusRelation corpRel= null;
		
		corpus1= relANNISFactory.eINSTANCE.createRACorpus();
		this.getFixture().addSNode(corpus1);
		roots.add(corpus1);
		
		corpus1= relANNISFactory.eINSTANCE.createRACorpus();
		this.getFixture().addSNode(corpus1);
		roots.add(corpus1);
		
		corpus2= relANNISFactory.eINSTANCE.createRACorpus();
		this.getFixture().addSNode(corpus2);
		
		corpRel= relANNISFactory.eINSTANCE.createRACorpusRelation();
		corpRel.setRaSuperCorpus(corpus1);
		corpRel.setRaSubCorpus(corpus2);
		this.getFixture().addSRelation(corpRel);
		
		corpus2= relANNISFactory.eINSTANCE.createRACorpus();
		this.getFixture().addSNode(corpus2);
		
		corpRel= relANNISFactory.eINSTANCE.createRACorpusRelation();
		corpRel.setRaSuperCorpus(corpus1);
		corpRel.setRaSubCorpus(corpus2);
		this.getFixture().addSRelation(corpRel);
		
		assertTrue(roots.containsAll(this.getFixture().getRARoots()));
		assertTrue(this.getFixture().getRARoots().containsAll(roots));
	}

	/**
	 * Tests the '{@link de.hub.corpling.relANNIS.RACorpusGraph#generateRAPPOrder() <em>Generate RAPP Order</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.corpling.relANNIS.RACorpusGraph#generateRAPPOrder()
	 */
	public void testGenerateRAPPOrder() 
	{
		RACorpusRelation corpRel= null;
		
		RACorpus corpus1= relANNISFactory.eINSTANCE.createRACorpus();
		corpus1.setRaName("corpus1");
		this.getFixture().addSNode(corpus1);
		
		RACorpus corpus2= relANNISFactory.eINSTANCE.createRACorpus();
		corpus2.setRaName("corpus2");
		this.getFixture().addSNode(corpus2);
		
		RACorpus corpus3= relANNISFactory.eINSTANCE.createRACorpus();
		corpus3.setRaName("corpus3");
		this.getFixture().addSNode(corpus3);
		
		corpRel= relANNISFactory.eINSTANCE.createRACorpusRelation();
		corpRel.setRaSuperCorpus(corpus1);
		corpRel.setRaSubCorpus(corpus2);
		this.getFixture().addSRelation(corpRel);
		
		
		corpRel= relANNISFactory.eINSTANCE.createRACorpusRelation();
		corpRel.setRaSuperCorpus(corpus1);
		corpRel.setRaSubCorpus(corpus3);
		this.getFixture().addSRelation(corpRel);
		
		RACorpus corpus4= relANNISFactory.eINSTANCE.createRACorpus();
		corpus4.setRaName("corpus4");
		this.getFixture().addSNode(corpus4);
		
		this.getFixture().generateRAPPOrder();
		
		assertEquals(new Long(0l), corpus1.getRaPre());
		assertEquals(new Long(5l), corpus1.getRaPost());
		
		assertEquals(new Long(1l), corpus2.getRaPre());
		assertEquals(new Long(2l), corpus2.getRaPost());
		
		assertEquals(new Long(3l), corpus3.getRaPre());
		assertEquals(new Long(4l), corpus3.getRaPost());
		
		assertEquals(new Long(6l), corpus4.getRaPre());
		assertEquals(new Long(7l), corpus4.getRaPost());
	}

} //RACorpusGraphTest
