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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.tests.storing;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISResourceFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

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
 * @generated
 */
public class RACorpusGraphStoringTest extends TestCase {

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
		TestRunner.run(RACorpusGraphStoringTest.class);
	}

	/**
	 * Constructs a new RA Corpus Graph test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpusGraphStoringTest(String name) {
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
	 * Tests if corpus structure will be loaded and saved correctly. Compared with corpus.tab.
	 * @throws IOException 
	 * @see de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora()
	 */
	public void testStoring1() throws IOException 
	{
		URI outputURI= URI.createFileURI("./src/test/resources/sample1/Corpus1relANNIS/");
		URI outCorpusURI = URI.createFileURI(outputURI + "/corpus.tab");
		
		URI tmpURI= URI.createFileURI("./_TMP/sample1");
		URI tmpCorpusURI = URI.createFileURI(tmpURI + "/corpus.tab");
		
		// create resource set and resource 
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register XML resource factory
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());

		//save resource
		Resource resourceOut = resourceSet.createResource(outputURI);
		Map<String, String> optionMap= new Hashtable<String, String>();
		optionMap.put("LOADING_TYPE", "CORPUS_STRUCTURE");
		resourceOut.load(optionMap);
		RACorpusGraph corpGraph= (RACorpusGraph) resourceOut.getContents().get(0);
				
		Resource resourceIn= resourceSet.createResource(tmpURI);
		resourceIn.getContents().add(corpGraph);
		optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
		resourceIn.save(optionMap);
		
		assertTrue(FileComparer.compareFiles(outCorpusURI, tmpCorpusURI));
	}
	
	/**
	 * Tests if corpus structure will be loaded and saved correctly. Compared with corpus.tab.
	 * Also corpus metadata will be checked.
	 * @throws IOException 
	 * @see de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora()
	 */
	public void testStoring2() throws IOException 
	{
		URI outputURI= URI.createFileURI("./src/test/resources/sample2/Corpus1relANNIS/");
		URI outCorpusURI = URI.createFileURI(outputURI + "/corpus.tab");
		URI outCorpusAnnotationURI = URI.createFileURI(outputURI + "/corpus_annotation.tab");
		
		URI tmpURI= URI.createFileURI("./_TMP/sample2");
		URI tmpCorpusURI = URI.createFileURI(tmpURI + "/corpus.tab");
		URI tmpCorpusAnnotationURI = URI.createFileURI(tmpURI + "/corpus_annotation.tab");
		
		// create resource set and resource 
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register XML resource factory
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());

		//save resource
		Resource resourceOut = resourceSet.createResource(outputURI);
		Map<String, String> optionMap= new Hashtable<String, String>();
		optionMap.put("LOADING_TYPE", "CORPUS_STRUCTURE");
		resourceOut.load(optionMap);
		RACorpusGraph corpGraph= (RACorpusGraph) resourceOut.getContents().get(0);
				
		Resource resourceIn= resourceSet.createResource(tmpURI);
		resourceIn.getContents().add(corpGraph);
		optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
		resourceIn.save(optionMap);
		
		assertTrue("corpora are not the same, expected '"+outCorpusURI+"' is different from '"+tmpCorpusURI+"'.", FileComparer.compareFiles(outCorpusURI, tmpCorpusURI));
		assertTrue("corpora are not the same, expected '"+outCorpusAnnotationURI+"' is different from '"+tmpCorpusAnnotationURI+"'.",FileComparer.compareFiles(outCorpusAnnotationURI, tmpCorpusAnnotationURI));
	}
	
	/**
	 * Tests if corpus structure will be loaded and saved correctly. Compared with corpus.tab.
	 * Also corpus metadata will be checked. And nodes.
	 * @throws IOException 
	 * @see de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora()
	 */
//	public void testStoring3() throws IOException 
//	{
//		URI outputURI= URI.createFileURI("./src/test/resources/sample3/Corpus1relANNIS/");
//		URI outCorpusURI = URI.createFileURI(outputURI + "/corpus.tab");
//		URI outCorpusAnnotationURI = URI.createFileURI(outputURI + "/corpus_annotation.tab");
//		
//		//document graph
//		URI outNodeURI = URI.createFileURI(outputURI + "/node.tab");
//		URI outTextURI = URI.createFileURI(outputURI + "/text.tab");
//		URI outNodeAnnotationURI = URI.createFileURI(outputURI + "/node_annotation.tab");
//		URI outRankURI = URI.createFileURI(outputURI + "/rank.tab");
//		URI outEdgeAnnotationURI = URI.createFileURI(outputURI + "/edge_annotation.tab");
//		URI outComponentURI = URI.createFileURI(outputURI + "/component.tab");
//		
//		
//		URI tmpURI= URI.createFileURI("./_TMP/sample3");
//		URI tmpCorpusURI = URI.createFileURI(tmpURI + "/corpus.tab");
//		URI tmpCorpusAnnotationURI = URI.createFileURI(tmpURI + "/corpus_annotation.tab");
//		
//		//document graph
//		URI tmpNodeURI = URI.createFileURI(tmpURI + "/node.tab");
//		URI tmpTextURI = URI.createFileURI(tmpURI + "/text.tab");
//		URI tmpNodeAnnotationURI = URI.createFileURI(tmpURI + "/node_annotation.tab");
//		URI tmpRankURI = URI.createFileURI(tmpURI + "/rank.tab");
//		URI tmpEdgeAnnotationURI = URI.createFileURI(tmpURI + "/edge_annotation.tab");
//		URI tmpComponentURI = URI.createFileURI(tmpURI + "/component.tab");
//		
//		// create resource set and resource 
//		ResourceSet resourceSet = new ResourceSetImpl();
//
//		// Register XML resource factory
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
//
//		//save resource
//		Resource resourceOut = resourceSet.createResource(outputURI);
//		Map<String, String> optionMap= new Hashtable<String, String>();
//		optionMap.put("LOADING_TYPE", "CORPUS_STRUCTURE");
//		resourceOut.load(optionMap);
//		RACorpusGraph corpGraph= (RACorpusGraph) resourceOut.getContents().get(0);
//		
//		{//documents
////			Map<String, String> optionMapDoc= new Hashtable<String, String>();
//			optionMap.put("LOADING_TYPE", "DOCUMENT");
////			RADocumentGraph docGraph= null;
//			
//			for (SNode sNode: corpGraph.getSNodes())
//			{
//				RACorpus raCorpus= (RACorpus) sNode;
//				if (raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
//				{
//					Resource resourceOutDoc = resourceSet.createResource(outputURI);
//					optionMap.put("LOADING_DOCUMENT_NO", raCorpus.getRaId().toString());
//					resourceOutDoc.load(optionMap);
//				}
//			}
//		}
//		Resource resourceIn= null;
//		for (SNode sNode: corpGraph.getSNodes())
//		{
//			RACorpus raCorpus= (RACorpus) sNode;
//			if (raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
//			{
//				System.out.println("saving doc: "+raCorpus.getRaId());
//				//save document
//				resourceIn= resourceSet.createResource(tmpURI);
//				resourceIn.getContents().add(corpGraph);
//				optionMap.put("SAVING_TYPE", "DOCUMENT");
//				optionMap.put("SAVING_DOCUMENT_NO", raCorpus.getRaId().toString());
//				resourceIn.save(optionMap);
//			}
//		}
//		
//		
//		//save corpus structure
//		resourceIn= resourceSet.createResource(tmpURI);
//		resourceIn.getContents().add(corpGraph);
//		optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
//		resourceIn.save(optionMap);
//		
//		
//		//corpus.tab
//		assertTrue(FileComparer.compareFiles(outCorpusURI, tmpCorpusURI));
//		//corpus_annotation.tab
//		assertTrue(FileComparer.compareTabFiles(outCorpusAnnotationURI, tmpCorpusAnnotationURI));
//		//node.tab
//		assertTrue(FileComparer.compareTabFiles(outNodeURI, tmpNodeURI));
//		//text.tab
//		assertTrue(FileComparer.compareTabFiles(outTextURI, tmpTextURI));
//		//node_annotation.tab
//		assertTrue(FileComparer.compareTabFiles(outNodeAnnotationURI, tmpNodeAnnotationURI));
//		//edge_annotation
//		assertTrue(FileComparer.compareTabFiles(outEdgeAnnotationURI, tmpEdgeAnnotationURI));
//		//rank.tab
//		assertTrue(FileComparer.compareTabFiles(outRankURI, tmpRankURI));
//		//component
//		assertTrue(FileComparer.compareTabFiles(outComponentURI, tmpComponentURI));
//	}
//	
//	/**
//	 * Tests if corpus structure will be loaded and saved correctly. Compared with corpus.tab.
//	 * Also corpus metadata will be checked. And nodes.
//	 * @throws IOException 
//	 * @see de.hub.corpling.relANNIS.RACorpusGraph#getRaCorpora()
//	 */
//	public void testStoring4() throws IOException 
//	{
//		URI outputURI= URI.createFileURI("./src/test/resources/sample4/");
//		URI outCorpusURI = URI.createFileURI(outputURI + "/corpus.tab");
//		URI outCorpusAnnotationURI = URI.createFileURI(outputURI + "/corpus_annotation.tab");
//		
//		//document graph
//		URI outNodeURI = URI.createFileURI(outputURI + "/node.tab");
//		URI outTextURI = URI.createFileURI(outputURI + "/text.tab");
//		URI outNodeAnnotationURI = URI.createFileURI(outputURI + "/node_annotation.tab");
//		URI outRankURI = URI.createFileURI(outputURI + "/rank.tab");
//		URI outEdgeAnnotationURI = URI.createFileURI(outputURI + "/edge_annotation.tab");
//		URI outComponentURI = URI.createFileURI(outputURI + "/component.tab");
//		
//		
//		URI tmpURI= URI.createFileURI("./_TMP/sample4");
//		URI tmpCorpusURI = URI.createFileURI(tmpURI + "/corpus.tab");
//		URI tmpCorpusAnnotationURI = URI.createFileURI(tmpURI + "/corpus_annotation.tab");
//		
//		//document graph
//		URI tmpNodeURI = URI.createFileURI(tmpURI + "/node.tab");
//		URI tmpTextURI = URI.createFileURI(tmpURI + "/text.tab");
//		URI tmpNodeAnnotationURI = URI.createFileURI(tmpURI + "/node_annotation.tab");
//		URI tmpRankURI = URI.createFileURI(tmpURI + "/rank.tab");
//		URI tmpEdgeAnnotationURI = URI.createFileURI(tmpURI + "/edge_annotation.tab");
//		URI tmpComponentURI = URI.createFileURI(tmpURI + "/component.tab");
//		
//		// create resource set and resource 
//		ResourceSet resourceSet = new ResourceSetImpl();
//
//		// Register XML resource factory
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",new XMIResourceFactoryImpl());
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
//
//		//save resource
//		Resource resourceOut = resourceSet.createResource(outputURI);
//		Map<String, String> optionMap= new Hashtable<String, String>();
//		optionMap.put("LOADING_TYPE", "CORPUS_STRUCTURE");
//		resourceOut.load(optionMap);
//		RACorpusGraph corpGraph= (RACorpusGraph) resourceOut.getContents().get(0);
//		
//		{//documents
////			Map<String, String> optionMapDoc= new Hashtable<String, String>();
//			optionMap.put("LOADING_TYPE", "DOCUMENT");
////			RADocumentGraph docGraph= null;
//			
//			for (SNode sNode: corpGraph.getSNodes())
//			{
//				RACorpus raCorpus= (RACorpus) sNode;
//				if (raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
//				{
//					Resource resourceOutDoc = resourceSet.createResource(outputURI);
//					optionMap.put("LOADING_DOCUMENT_NO", raCorpus.getRaId().toString());
//					resourceOutDoc.load(optionMap);
//				}
//			}
//		}
//		Resource resourceIn= null;
//		for (SNode sNode: corpGraph.getSNodes())
//		{
//			RACorpus raCorpus= (RACorpus) sNode;
//			if (raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
//			{
//				System.out.println("saving doc: "+raCorpus.getRaId());
//				//save document
//				resourceIn= resourceSet.createResource(tmpURI);
//				resourceIn.getContents().add(corpGraph);
//				optionMap.put("SAVING_TYPE", "DOCUMENT");
//				optionMap.put("SAVING_DOCUMENT_NO", raCorpus.getRaId().toString());
//				resourceIn.save(optionMap);
//			}
//		}
//		
//		
//		//save corpus structure
//		resourceIn= resourceSet.createResource(tmpURI);
//		resourceIn.getContents().add(corpGraph);
//		optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
//		resourceIn.save(optionMap);
//		
//		
//		//corpus.tab
//		assertTrue(FileComparer.compareFiles(outCorpusURI, tmpCorpusURI));
//		//corpus_annotation.tab
//		assertTrue(FileComparer.compareFiles(outCorpusAnnotationURI, tmpCorpusAnnotationURI));
//		//node.tab
//		assertTrue(FileComparer.compareFiles(outNodeURI, tmpNodeURI));
//		//text.tab
//		assertTrue(FileComparer.compareFiles(outTextURI, tmpTextURI));
//		//node_annotation.tab
//		assertTrue(FileComparer.compareFiles(outNodeAnnotationURI, tmpNodeAnnotationURI));
//		//edge_annotation
//		assertTrue(FileComparer.compareFiles(outEdgeAnnotationURI, tmpEdgeAnnotationURI));
//		//rank.tab
//		assertTrue(FileComparer.compareFiles(outRankURI, tmpRankURI));
//		//component
//		assertTrue(FileComparer.compareFiles(outComponentURI, tmpComponentURI));
//	}

} //RACorpusGraphTest
