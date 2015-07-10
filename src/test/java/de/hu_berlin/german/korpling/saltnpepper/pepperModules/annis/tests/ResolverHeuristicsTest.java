/**
 * Copyright 2015 Humboldt-Universität zu Berlin
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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.tests;


import de.hu_berlin.german.korpling.saltnpepper.pepper.common.CorpusDesc;
import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNIS;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;
import java.util.Arrays;
import org.eclipse.emf.common.util.BasicEList;
import org.junit.Rule;
import org.junit.rules.TestName;
 
 
public class ResolverHeuristicsTest extends PepperExporterTest{ 
	
  @Rule
  public TestName name = new TestName();
  
  private SDocumentGraph doc1;
  private SDocumentGraph doc2;
  
  private File outputDir;
  private File testPath;
	
	@Before
	public void setUp()
	{
		setFixture(new ANNISExporter());
    
		outputDir = getTempPath(ResolverHeuristicsTest.class.getSimpleName()+ "/" +name.getMethodName());
		outputDir.mkdirs();
		testPath = new File(getTestResources() + name.getMethodName());
		testPath.mkdirs();
		setResourcesURI(URI.createFileURI(testPath.getAbsolutePath()));
		FormatDesc formatDef;
		formatDef= new FormatDesc();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("3.3");
		supportedFormatsCheck.add(formatDef);
    
    FormatDesc formatDef2;
		formatDef2= new FormatDesc();
		formatDef2.setFormatName("annis");
		formatDef2.setFormatVersion("3.3");
		supportedFormatsCheck.add(formatDef2);
    
    
    SaltProject saltProject= SaltFactory.eINSTANCE.createSaltProject();
		saltProject.setSName("sampleSaltProject");
		SCorpusGraph sCorpGraph = SaltFactory.eINSTANCE.createSCorpusGraph();
    saltProject.getSCorpusGraphs().add(sCorpGraph);
		
    
    SCorpus rootCorpus = sCorpGraph.createSCorpus(null, "rootCorpus");
    SDocument d1 = sCorpGraph.createSDocument(rootCorpus, "doc1");
    SDocument d2 = sCorpGraph.createSDocument(rootCorpus, "doc2");
    
    doc1 = SaltFactory.eINSTANCE.createSDocumentGraph();
    doc2 = SaltFactory.eINSTANCE.createSDocumentGraph();
    
    d1.setSDocumentGraph(doc1);
    d2.setSDocumentGraph(doc2);

    getFixture().setSaltProject(saltProject);
    
    CorpusDesc corpusDesc = new CorpusDesc();
    corpusDesc.setCorpusPath(URI.createFileURI(outputDir.getAbsolutePath()));
    getFixture().setCorpusDesc(corpusDesc);
    
    
	}
  
	@Test
	public void testGridHeuristics() throws IOException
	{
    
    SampleGenerator.createInformationStructureSpan(doc1.getSDocument());
    SampleGenerator.createInformationStructureAnnotations(doc1.getSDocument());
    
    SampleGenerator.createTokens(doc2.getSDocument());
    
    SLayer abcLayer = SaltFactory.eINSTANCE.createSLayer();
    abcLayer.setSName("abc");
    doc2.addSLayer(abcLayer);
    SSpan abcSpan = doc2.createSSpan(doc2.getSTokens().get(0));
    abcLayer.getSNodes().add(abcSpan);
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testHeuristicsEscape() throws IOException
	{
    
    SampleGenerator.createSyntaxStructure(doc1.getSDocument());
    SampleGenerator.createSyntaxAnnotations(doc1.getSDocument());

    SampleGenerator.createTokens(doc2.getSDocument());

    SLayer abcLayer = SaltFactory.eINSTANCE.createSLayer();
    abcLayer.setSName("abc");
    doc2.addSLayer(abcLayer);

    SStructure abcStruct = doc2.createSStructure(doc2.getSTokens().get(0));
    abcStruct.createSAnnotation("?ns", "überschlag?", "ABC");
    abcLayer.getSNodes().add(abcStruct);

    start();

    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/"
            + ANNIS.FILE_VISUALIZATION,
            outputDir.getAbsolutePath() + "/" + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testSimpleTreeHeuristics() throws IOException
	{
    
    SampleGenerator.createSyntaxStructure(doc1.getSDocument());
    SampleGenerator.createSyntaxAnnotations(doc1.getSDocument());
    
    SampleGenerator.createTokens(doc2.getSDocument());
    
    // make sure the created syntactic relations have a type
    for(SDominanceRelation domRel : doc1.getSDominanceRelations()) {
      domRel.addSType("edge");
    }
    
    SLayer abcLayer = SaltFactory.eINSTANCE.createSLayer();
    abcLayer.setSName("abc");
    doc2.addSLayer(abcLayer);
    
    SStructure abcStruct = doc2.createSStructure(doc2.getSTokens().get(0));
    abcStruct.createSAnnotation(null, "const", "ABC");
    abcLayer.getSNodes().add(abcStruct);
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testTreeWithoutEdgeType() throws IOException
	{
    
    SampleGenerator.createSyntaxStructure(doc1.getSDocument());
    SampleGenerator.createSyntaxAnnotations(doc1.getSDocument());
    
    for(SStructure struct : doc1.getSStructures()) {
      struct.getSLayers().clear();
    }
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testArtificialTokenHeuristics() throws IOException
	{
    SampleGenerator.createDialogue(doc1.getSDocument());
    
    STextualDS text = doc2.createSTextualDS("1 2 3");
    SToken tok1 = doc2.createSToken(text, 0, 1);
    SToken tok2 = doc2.createSToken(text, 2, 3);
    SToken tok3 = doc2.createSToken(text, 4, 5);
    
    SSpan spk1_1 = doc2.createSSpan(new BasicEList<>(Arrays.asList(tok1)));
    SSpan spk1_2 = doc2.createSSpan(new BasicEList<>(Arrays.asList(tok3)));
    SSpan spk2_1 = doc2.createSSpan(new BasicEList<>(Arrays.asList(tok1, tok2)));
    SSpan spk2_2 = doc2.createSSpan(new BasicEList<>(Arrays.asList(tok3)));
    
    spk1_1.createSAnnotation(null, "spk1_v", "Hi");
    spk1_2.createSAnnotation(null, "spk1_v", "Ho");
    spk2_1.createSAnnotation(null, "spk2_v", "Oh");
    spk2_2.createSAnnotation(null, "spk2_v", "sorry");
    
    SOrderRelation order1 = SaltFactory.eINSTANCE.createSOrderRelation();
    order1.setSSource(spk1_1);
    order1.setSTarget(spk1_2);
    order1.addSType("spk1_v");
    doc2.addSRelation(order1);
    
    SOrderRelation order2 = SaltFactory.eINSTANCE.createSOrderRelation();
    order2.setSSource(spk2_1);
    order2.setSTarget(spk2_2);
    order2.addSType("spk2_v");
    doc2.addSRelation(order2);
    
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION);
	}
	
}
