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


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.corpus_tools.pepper.common.CorpusDesc;
import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.testFramework.PepperExporterTest;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SDominanceRelation;
import org.corpus_tools.salt.common.SOrderRelation;
import org.corpus_tools.salt.common.SSpan;
import org.corpus_tools.salt.common.SStructure;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.common.SaltProject;
import org.corpus_tools.salt.core.SLayer;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNIS;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporter; 
 
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
    
    
    SaltProject saltProject= SaltFactory.createSaltProject();
		saltProject.setName("sampleSaltProject");
		SCorpusGraph sCorpGraph = SaltFactory.createSCorpusGraph();
    saltProject.addCorpusGraph(sCorpGraph);
		
    
    SCorpus rootCorpus = sCorpGraph.createCorpus(null, "rootCorpus");
    SDocument d1 = sCorpGraph.createDocument(rootCorpus, "doc1");
    SDocument d2 = sCorpGraph.createDocument(rootCorpus, "doc2");
    
    doc1 = SaltFactory.createSDocumentGraph();
    doc2 = SaltFactory.createSDocumentGraph();
    
    d1.setDocumentGraph(doc1);
    d2.setDocumentGraph(doc2);

    getFixture().setSaltProject(saltProject);
    
    CorpusDesc corpusDesc = new CorpusDesc();
    corpusDesc.setCorpusPath(URI.createFileURI(outputDir.getAbsolutePath()));
    getFixture().setCorpusDesc(corpusDesc);
    
    
	}
  
	@Test
	public void testGridHeuristics() throws IOException
	{
    
    SampleGenerator.createInformationStructureSpan(doc1.getDocument());
    SampleGenerator.createInformationStructureAnnotations(doc1.getDocument());
    
    SampleGenerator.createTokens(doc2.getDocument());
    
    SLayer abcLayer = SaltFactory.createSLayer();
    abcLayer.setName("abc");
    doc2.addLayer(abcLayer);
    SSpan abcSpan = doc2.createSpan(doc2.getTokens().get(0));
    abcLayer.getNodes().add(abcSpan);
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testHeuristicsEscape() throws IOException
	{
    
    SampleGenerator.createSyntaxStructure(doc1.getDocument());
    SampleGenerator.createSyntaxAnnotations(doc1.getDocument());

    SampleGenerator.createTokens(doc2.getDocument());

    SLayer abcLayer = SaltFactory.createSLayer();
    abcLayer.setName("abc");
    doc2.addLayer(abcLayer);

    SStructure abcStruct = doc2.createSStructure(doc2.getTokens().get(0));
    abcStruct.createAnnotation("?ns", "überschlag?", "ABC");
    abcLayer.getNodes().add(abcStruct);

    
    
    start();

    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/"
            + ANNIS.FILE_VISUALIZATION,
            outputDir.getAbsolutePath() + "/" + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testSimpleTreeHeuristics() throws IOException
	{
    
    SampleGenerator.createSyntaxStructure(doc1.getDocument());
    SampleGenerator.createSyntaxAnnotations(doc1.getDocument());
    
    SampleGenerator.createTokens(doc2.getDocument());
    
    // make sure the created syntactic relations have a type
    for(SDominanceRelation domRel : doc1.getDominanceRelations()) {
      domRel.setType("relation");
    }
    
    SLayer abcLayer = SaltFactory.createSLayer();
    abcLayer.setName("abc");
    doc2.addLayer(abcLayer);
    
    SStructure abcStruct = doc2.createSStructure(doc2.getTokens().get(0));
    abcStruct.createAnnotation(null, "const", "ABC");
    abcLayer.getNodes().add(abcStruct);
    
    start();
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testTreeWithoutEdgeType() throws IOException
	{
    
    SampleGenerator.createSyntaxStructure(doc1.getDocument());
    SampleGenerator.createSyntaxAnnotations(doc1.getDocument());
    
    for(SStructure struct : doc1.getStructures()) {
      for(SLayer layer : struct.getLayers()) {
        layer.removeNode(struct);
      }
    }
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" + ANNIS.FILE_VISUALIZATION);
	}
  
  @Test
	public void testArtificialTokenHeuristics() throws IOException
	{
    SampleGenerator.createDialogue(doc1.getDocument());
    
    STextualDS text = doc2.createTextualDS("1 2 3");
    SToken tok1 = doc2.createToken(text, 0, 1);
    SToken tok2 = doc2.createToken(text, 2, 3);
    SToken tok3 = doc2.createToken(text, 4, 5);
    
    SSpan spk1_1 = doc2.createSpan(new ArrayList<>(Arrays.asList(tok1)));
    SSpan spk1_2 = doc2.createSpan(new ArrayList<>(Arrays.asList(tok3)));
    SSpan spk2_1 = doc2.createSpan(new ArrayList<>(Arrays.asList(tok1, tok2)));
    SSpan spk2_2 = doc2.createSpan(new ArrayList<>(Arrays.asList(tok3)));
    
    spk1_1.createAnnotation(null, "spk1_v", "Hi");
    spk1_2.createAnnotation(null, "spk1_v", "Ho");
    spk2_1.createAnnotation(null, "spk2_v", "Oh");
    spk2_2.createAnnotation(null, "spk2_v", "sorry");
    
    SOrderRelation order1 = SaltFactory.createSOrderRelation();
    order1.setSource(spk1_1);
    order1.setTarget(spk1_2);
    order1.setType("spk1_v");
    doc2.addRelation(order1);
    
    SOrderRelation order2 = SaltFactory.createSOrderRelation();
    order2.setSource(spk2_1);
    order2.setTarget(spk2_2);
    order2.setType("spk2_v");
    doc2.addRelation(order2);
    
    
    start();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION, 
      outputDir.getAbsolutePath() + "/" 
            + ANNIS.FILE_VISUALIZATION);
	}
	
}
