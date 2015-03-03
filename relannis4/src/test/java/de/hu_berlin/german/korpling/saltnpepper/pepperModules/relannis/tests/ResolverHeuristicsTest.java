/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.tests;


import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;
import org.junit.Rule;
import org.junit.rules.TestName;
 
 
public class ResolverHeuristicsTest extends PepperExporterTest{ 
	
  @Rule
  public TestName name = new TestName();
  
  private SDocument doc1;
  private SDocument doc2;
  
  private File outputDir;
  private File testPath;
	
	@Before
	public void setUp()
	{
		setFixture(new RelANNISExporter());
    
		outputDir = getTempPath(ResolverHeuristicsTest.class.getSimpleName()+ "/" +name.getMethodName());
		outputDir.mkdirs();
		testPath = new File(getTestResources() + name.getMethodName());
		testPath.mkdirs();
		setResourcesURI(URI.createFileURI(testPath.getAbsolutePath()));
		FormatDesc formatDef;
		formatDef= new FormatDesc();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("4.0");
		supportedFormatsCheck.add(formatDef);
    
    
    SaltProject saltProject= SaltFactory.eINSTANCE.createSaltProject();
		saltProject.setSName("sampleSaltProject");
		SCorpusGraph sCorpGraph = SaltFactory.eINSTANCE.createSCorpusGraph();
    saltProject.getSCorpusGraphs().add(sCorpGraph);
		
    
    SCorpus rootCorpus = sCorpGraph.createSCorpus(null, "rootCorpus");
    doc1 = sCorpGraph.createSDocument(rootCorpus, "doc1");
    doc2 = sCorpGraph.createSDocument(rootCorpus, "doc2");
    
    
    doc2.setSDocumentGraph(SaltFactory.eINSTANCE
            .createSDocumentGraph());

    getFixture().setSaltProject(saltProject);
    
    CorpusDesc corpusDesc = new CorpusDesc();
    corpusDesc.setCorpusPath(URI.createFileURI(outputDir.getAbsolutePath()));
    getFixture().setCorpusDesc(corpusDesc);
    
    
	}
  
	@Test
	public void testGridHeuristics() throws IOException
	{
    
    SampleGenerator.createInformationStructureSpan(doc1);
    SampleGenerator.createInformationStructureAnnotations(doc1);
    
    SampleGenerator.createTokens(doc2);
    
    SLayer abcLayer = SaltFactory.eINSTANCE.createSLayer();
    abcLayer.setSName("abc");
    doc2.getSDocumentGraph().addSLayer(abcLayer);
    SSpan abcSpan = doc2.getSDocumentGraph().createSSpan(doc2.getSDocumentGraph().getSTokens().get(0));
    abcLayer.getSNodes().add(abcSpan);
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/resolver_vis_map.relannis", 
      outputDir.getAbsolutePath() + "/resolver_vis_map.relannis");
	}
	
}
