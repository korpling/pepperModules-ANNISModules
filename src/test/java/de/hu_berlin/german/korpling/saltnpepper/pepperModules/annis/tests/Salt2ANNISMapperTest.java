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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.tests;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.GlobalIdManager;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.IdManager;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNIS;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporterProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructuredNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.SampleGenerator;
import de.hu_berlin.german.korpling.saltnpepper.salt.samples.exceptions.SaltSampleException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class Salt2ANNISMapperTest
{

  @Rule
  public TestName name = new TestName();

  private Salt2ANNISMapper fixture = null;
  
  private SCorpus rootCorpus = null;
  private SDocument sDocument = null;
  
  private ANNISExporterProperties props = new ANNISExporterProperties();

  public Salt2ANNISMapper getFixture()
  {
    return fixture;
  }

  public void setFixture(Salt2ANNISMapper fixture)
  {
    this.fixture = fixture;
  }

  private File tmpPath;
  private File testPath;

  private static final File globalTmpPath = new File(System.getProperty(
    "java.io.tmpdir") + File.separator + "ANNISModules_test" + File.separator
    + "Salt2ANNISMapper" + File.separator);

  @Before
  public void setUp()
  {
    setFixture(new Salt2ANNISMapper());

    getFixture().mapRelationsInParallel(false);

    if (!globalTmpPath.exists())
    {
      globalTmpPath.mkdirs();
    }

    SCorpusGraph sCorpGraph = SaltFactory.eINSTANCE.createSCorpusGraph();
    rootCorpus = SaltFactory.eINSTANCE.createSCorpus();
    rootCorpus.setSName("mainCorp");
    sCorpGraph.addSNode(rootCorpus);

    fixture.setSCorpusGraph(sCorpGraph);

    sDocument = SaltFactory.eINSTANCE.createSDocument();
    sCorpGraph.addSDocument(rootCorpus, sDocument);

    sDocument.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
    getFixture().setSDocument(sDocument);

    getFixture().isTestMode = true;

    GlobalIdManager globalIdManager = new GlobalIdManager();
    IdManager idManager = new IdManager(globalIdManager);
    getFixture().setIdManager(idManager);

    tmpPath = new File(globalTmpPath.getAbsoluteFile() + File.separator + name.
            getMethodName());
    testPath = new File(getTestPath() + name.getMethodName());
    createTupleWriters(tmpPath);
  }
  
  private void doMapping()
  {
	  getFixture().setSCorpus(rootCorpus);
	  getFixture().mapSCorpus();
	  getFixture().setSDocument(sDocument);
	  getFixture().mapSDocument();
  }

  private void createTupleWriters(File path)
  {
    getFixture().tw_text = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_TEXT),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_node = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_NODE),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_nodeAnno = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_NODE_ANNO),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_rank = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_RANK),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_edgeAnno = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_EDGE_ANNO),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_component = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_COMPONENT),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_corpus = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_CORPUS),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
    getFixture().tw_corpusMeta = ANNISExporter.createTupleWriter(
            new File(path.getAbsolutePath() + File.separator + ANNIS.FILE_CORPUS_META),
            props.getEscapeCharacters(), props.getEscapeCharactersSet());
  }

  @After
  public void tearDown() throws IOException
  {
//		if (!deleteDirectory(tmpPath))
//			throw new IOException();
  }

  /**
   * returns path of test resources *
   */
  private String getTestPath()
  {
    return ("./src/test/resources/");
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSText() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();
	
    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapIndividualCorpusName() throws IOException
  {
    // create the primary text
	SampleGenerator.createPrimaryData(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    getFixture().individualCorpusName = "NewTopLevelCorpus";
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSToken() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text in english</li>
   * <li> a primary text in german</li>
   * <li>tokens for english</li>
   * <li>tokens for german</li>
   *
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSeveralTokenizations() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    STextualDS primaryData_DE = SampleGenerator.createPrimaryData(getFixture().
      getSDocument(), SampleGenerator.LANG_DE);
    SampleGenerator.createTokens(getFixture().getSDocument(), primaryData_DE);
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text in english</li>
   * <li> a primary text in german</li>
   * <li>tokens for english</li>
   * <li>tokens for german</li>
   * <li>alignment relations</li>
   *
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapParallelData() throws IOException
  {
    // create the primary text
    SampleGenerator.createParallelData(getFixture().getSDocument());
	  
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text in english</li>
   * <li> a primary text in german</li>
   * <li>tokens for english</li>
   * <li>tokens for german</li>
   * <li>alignment relations</li>
   * <li>one pointingRelation which has no SType</li>
   *
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapUntypedPointingRelation() throws IOException
  {
    // create the primary text
    SampleGenerator.createParallelData(getFixture().getSDocument(), false);
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>annotations on tokens</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSTokenAnnotation() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    SampleGenerator.createMorphologyAnnotations(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>spans</li>
   * <li>annotations on spans</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSSpans() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    SampleGenerator.createInformationStructureSpan(getFixture().getSDocument());
    SampleGenerator.
      createInformationStructureAnnotations(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>syntax tree</li>
   * <li>annotation on syntax tree</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSSyntax() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    SampleGenerator.createSyntaxStructure(getFixture().getSDocument());
    SampleGenerator.createSyntaxAnnotations(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }
  
  @Test
  public void testMapDAGSimple() throws IOException 
  {
    
    /*
      create the following graph
           1
         /   \
        v     v
        2     3
         \   /
          v v
           4
           |
           v
           a
    */
    
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    SDocumentGraph graph = SaltFactory.eINSTANCE.createSDocumentGraph();
    getFixture().getSDocument().setSDocumentGraph(graph);
    
    
    STextualDS textDS = graph.createSTextualDS("a");
    
    SToken tok = graph.createSToken(textDS, 0, 1);
    
    SStructuredNode struct4 = graph.createSStructure(tok);
    SStructuredNode struct3 = graph.createSStructure(struct4);
    SStructuredNode struct2 = graph.createSStructure(struct4);
    EList<SStructuredNode> struct1Targets = new BasicEList<>();
    struct1Targets.add(struct2);
    struct1Targets.add(struct3);
    SStructuredNode struct1 = graph.createSStructure(struct1Targets);

    doMapping();
    
    // check the pre/post-order and level
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" + ANNIS.FILE_RANK, 
      tmpPath.getAbsolutePath() + "/" + ANNIS.FILE_RANK, 0, 3, 4, 5);
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>anaphoric annotations</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapAnaphoric() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    SampleGenerator.createAnaphoricAnnotations(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>annotations on token</li>
   * <li>spans</li>
   * <li>information structure annotation on spans</li>
   * <li>syntax tree</li>
   * <li>annotations on syntax tree</li>
   * <li>annotation on syntax tree</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapFullGraph() throws IOException
  {
    // create the primary text
    SampleGenerator.createSDocumentStructure(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  @Test
  public void testMultiThreadingSpeed()
  {
    // create the primary text
    SampleGenerator.createSDocumentStructure(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

	getFixture().setSCorpus(rootCorpus);
    getFixture().mapSCorpus();

    long startTime = 0l;
    long stopTime = 0l;
    long elapsedTime = 0l;

    long averageTime = 0l;
    System.out.println(
      "INFO: Starting single-threaded mapping (average of 100 times) of FullGraph");
    for (int i = 0; i < 100; i++)
    {
      startTime = System.currentTimeMillis();
	  getFixture().setSDocument(sDocument);
      getFixture().mapSDocument();
      stopTime = System.currentTimeMillis();
      elapsedTime = stopTime - startTime;
      averageTime += elapsedTime;
    }
    System.out.println(
      "INFO: Single-threaded mapping (average of 100 times) of FullGraph needed "
      + (averageTime / 100) + " ms");

    averageTime = 0l;

    System.out.println(
      "INFO: Starting multi-threaded mapping (average of 100 times) of FullGraph");
    for (int i = 0; i < 100; i++)
    {
      startTime = System.currentTimeMillis();
      getFixture().mapRelationsInParallel(true);
      getFixture().mapSDocument();
      stopTime = System.currentTimeMillis();
      elapsedTime = stopTime - startTime;
      averageTime += elapsedTime;
    }
    System.out.println(
      "INFO: Multi-threaded mapping (average of 100 times) of FullGraph needed "
      + (averageTime / 100) + " ms");

  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>annotations on token</li>
   * <li>spans</li>
   * <li>information structure annotation on spans</li>
   * <li>syntax tree</li>
   * <li>annotations on syntax tree</li>
   * <li>annotation on syntax tree</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testMapSOrderRelation() throws IOException
  {
    // create the primary text
    SampleGenerator.createDialogue(getFixture().getSDocument());
    
    // add a span which overlaps a token which gets a new token-index after the
    // artificial tokenization was created
    SDocumentGraph g = getFixture().getSDocument().getSDocumentGraph();
    EList<SToken> coveredBySpan = new BasicEList<>();
    coveredBySpan.add(g.getSTokens().get(g.getSTokens().size()-2));
    coveredBySpan.add(g.getSTokens().get(g.getSTokens().size()-1));
    assertEquals("oh", g.getSText(coveredBySpan.get(0)));
    assertEquals("yes!", g.getSText(coveredBySpan.get(1)));
    g.createSSpan(coveredBySpan);

    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  /**
   * Creates an {@link SDocumentGraph} containing:
   * <ul>
   * <li> a primary text</li>
   * <li>tokens</li>
   * <li>annotations on token</li>
   * <li>spans</li>
   * <li>information structure annotation on spans</li>
   * <li>syntax tree</li>
   * <li>annotations on syntax tree</li>
   * <li>annotation on syntax tree</li>
   * </ul>
   *
   * @throws IOException
   */
  @Test
  public void testComplexMapSOrderRelation() throws IOException
  {
    // create the primary text
    SampleGenerator.createDialogue(getFixture().getSDocument());
    EList<SToken> sDocumentTokens = getFixture().getSDocument().
      getSDocumentGraph().getSTokens();
    SRelation pointing = getFixture().getSDocument().getSDocumentGraph().createSRelation(
      sDocumentTokens.get(0), sDocumentTokens.get(1),
      STYPE_NAME.SPOINTING_RELATION, "anno=test");
    pointing.addSType("dep");
    getFixture().getSDocument().getSDocumentGraph().createSSpan(sDocumentTokens.
      get(0));
    
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }

  @Test
  public void testReuseExistingSOrderName() throws IOException
  {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());

    // create a single SOrderRelation chain
    SToken previousToken = null;
    for (SToken t : getFixture().getSDocument().getSDocumentGraph().
      getSortedSTokenByText())
    {
      if (previousToken != null)
      {
        SOrderRelation r = SaltFactory.eINSTANCE.createSOrderRelation();
        r.addSType("order");
        r.setSSource(previousToken);
        r.setSTarget(t);
        getFixture().getSDocument().getSDocumentGraph().addSRelation(r);
      }

      previousToken = t;
    }

    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    doMapping();

    // check output files that only "order" is used as segmentation name
    Set<String> segNames = Files.readLines(new File(tmpPath, ANNIS.FILE_NODE),
      Charsets.UTF_8, new LineProcessor<Set<String>>()
      {
        private final Set<String> result = new TreeSet<>();

        @Override
        public boolean processLine(String line) throws IOException
        {
          String[] split = line.split("\t");
          result.add(split[11]);
          return true;
        }

        @Override
        public Set<String> getResult()
        {
          return result;
        }
      });

    assertEquals(Arrays.asList("order"), new LinkedList(segNames));
  }

  @Test
  public void testAppendIndexForSOrderWithMultipleRoots() throws IOException {
    // create the primary text
    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());

    // create a several SOrderRelation chains (pairwise)
    EList<SToken> tokens = getFixture().getSDocument().getSDocumentGraph().
            getSortedSTokenByText();
    int remainder = tokens.size() % 2;
    int numberOfChains = (tokens.size() - remainder) / 2;
    LinkedList<String> expectedNames = new LinkedList<>();
    for (int i = 0; i < numberOfChains; i++) {
      SOrderRelation r = SaltFactory.eINSTANCE.createSOrderRelation();
      r.addSType("order");
      r.setSSource(tokens.get(i * 2));
      r.setSTarget(tokens.get((i * 2) + 1));
      getFixture().getSDocument().getSDocumentGraph().addSRelation(r);

      if (i == numberOfChains - 1 && remainder == 1) {
        SOrderRelation additionalRel = SaltFactory.eINSTANCE.createSOrderRelation();
        additionalRel.addSType("order");
        additionalRel.setSSource(tokens.get((i * 2) + 1));
        additionalRel.setSTarget(tokens.get((i * 2) + 2));
        getFixture().getSDocument().getSDocumentGraph().addSRelation(additionalRel);
      }

      expectedNames.add("order" + i);
    }

    doMapping();

    // check output files that only "order" is used as segmentation name
    Set<String> segNames = Files.readLines(new File(tmpPath, ANNIS.FILE_NODE),
            Charsets.UTF_8, new LineProcessor<Set<String>>() {
              private final Set<String> result = new TreeSet<>();

              @Override
              public boolean processLine(String line) throws IOException {
                String[] split = line.split("\t");
                result.add(split[11]);
                return true;
              }

              @Override
              public Set<String> getResult() {
                return result;
              }
            });

    assertEquals(expectedNames, new LinkedList(segNames));
  }
  
  @Test
  public void testEscapeDefault() throws IOException {

    // the last character the unicode character "GOTHIC LETTER AHSA" which is not part of the BMP
    // and must be represented as surrugate pair in Java
    String sampleText = "a\\b\\\\c\n\r\n\t\uD800\uDF30";
    
    if (sDocument == null) {
			throw new SaltSampleException("Cannot create example, because the given sDocument is empty.");
		}
		if (sDocument.getSDocumentGraph() == null) {
			sDocument.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		}
		STextualDS sTextualDS = null;
		// creating the primary text depending on the language
		sTextualDS = SaltFactory.eINSTANCE.createSTextualDS();
		sTextualDS.setSText(sampleText);
		// adding the text to the document-graph
		sDocument.getSDocumentGraph().addSNode(sTextualDS);
    
    SToken sToken = SaltFactory.eINSTANCE.createSToken();
		sDocument.getSDocumentGraph().addSNode(sToken);
		
		STextualRelation sTextRel = SaltFactory.eINSTANCE.createSTextualRelation();
		sTextRel.setSToken(sToken);
		sTextRel.setSTextualDS(sTextualDS);
		sTextRel.setSStart(0);
		sTextRel.setSEnd(sampleText.length());
		sDocument.getSDocumentGraph().addSRelation(sTextRel);
    
    doMapping();
    
    TabFileComparator.checkEqual(new File(testPath, ANNIS.FILE_NODE).getAbsolutePath(), 
            new File(tmpPath, ANNIS.FILE_NODE).getAbsolutePath());
    
  }
  
  @Test
  public void testEscapeCustom() throws IOException {

    // the last character is the unicode character "GOTHIC LETTER AHSA" which is not part of the BMP
    // and must be represented as surrugate pair in Java
    String sampleText = "a\\b\\\\c\n\r\n\t\uD800\uDF30";
    
    String customEscape = "(c=X),(\\n=N),(\\t=T),(\\=S),(\\r=R)";
    props.setPropertyValue(ANNISExporterProperties.PROP_ESCAPE_CHARACTERS_LIST, customEscape);
    
    // re-init tuple writers
    createTupleWriters(tmpPath);
    
    if (sDocument == null) {
			throw new SaltSampleException("Cannot create example, because the given sDocument is empty.");
		}
		if (sDocument.getSDocumentGraph() == null) {
			sDocument.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		}
		STextualDS sTextualDS = null;
		// creating the primary text depending on the language
		sTextualDS = SaltFactory.eINSTANCE.createSTextualDS();
		sTextualDS.setSText(sampleText);
		// adding the text to the document-graph
		sDocument.getSDocumentGraph().addSNode(sTextualDS);
    
    SToken sToken = SaltFactory.eINSTANCE.createSToken();
		sDocument.getSDocumentGraph().addSNode(sToken);
		
		STextualRelation sTextRel = SaltFactory.eINSTANCE.createSTextualRelation();
		sTextRel.setSToken(sToken);
		sTextRel.setSTextualDS(sTextualDS);
		sTextRel.setSStart(0);
		sTextRel.setSEnd(sampleText.length());
		sDocument.getSDocumentGraph().addSRelation(sTextRel);
    
    doMapping();
    
    TabFileComparator.checkEqual(new File(testPath, ANNIS.FILE_NODE).getAbsolutePath(), 
            new File(tmpPath, ANNIS.FILE_NODE).getAbsolutePath());
    
  }
  
  @Test
  public void testIDEscape() throws IOException {

    SampleGenerator.createPrimaryData(getFixture().getSDocument());
    SampleGenerator.createTokens(getFixture().getSDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    SToken tok1 = getFixture().getSDocument().getSDocumentGraph().getSTokens().get(0);
    SToken tok2 = getFixture().getSDocument().getSDocumentGraph().getSTokens().get(1);
    
    // invalid annotation
    tok1.createSAnnotation("6>]`'[;+|]", "1\\.[`&[}²*", "value");
    
    // invalid edge name
    SLayer layer = SaltFactory.eINSTANCE.createSLayer();
		layer.setSName("7#=+}}ä?ö;");
    getFixture().getSDocument().getSDocumentGraph().addSLayer(layer);
    
    SPointingRelation dep = SaltFactory.eINSTANCE.createSPointingRelation();
    dep.setSSource(tok1);
    dep.setSTarget(tok2);
    dep.addSType("-7#=+}}ä?ö;");
    layer.getEdges().add(dep);
    getFixture().getSDocument().getSDocumentGraph().addEdge(dep);
    
    // invalid edge annotation
    dep.createSAnnotation("0>;;!]{§", "2ß{.:)²,¸", "value");
    
    // invalid segmentation name
    SOrderRelation order = SaltFactory.eINSTANCE.createSOrderRelation();
    order.setSSource(tok1);
    order.setSTarget(tok2);
    order.addSType("-,$#³>ä~ß.");
    getFixture().getSDocument().getSDocumentGraph().addEdge(order);
    
    // invalid meta data (different annotation names with the same mapping)
    getFixture().getSDocument().createSMetaAnnotation(
            "ns",
            "%invalid%%name$", "value1");
    getFixture().getSDocument().createSMetaAnnotation(
            "ns",
            "invalid__name_", "value2");
    getFixture().getSDocument().createSMetaAnnotation(
            "ns",
            "invalid_§name_", "value3");
    
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }
  
  /**
   * Deletes the directory with all contained directories/files
   *
   * @param fileToDelete
   */
  private boolean deleteDirectory(File fileToDelete)
  {
    if (fileToDelete.isDirectory())
    {
      String[] directoryContent = fileToDelete.list();
      for (int i = 0; i < directoryContent.length; i++)
      {
        if (!(deleteDirectory(new File(fileToDelete, directoryContent[i]))))
        {
          return false;
        }
      }
    }

    return fileToDelete.delete();
  }

  /**
   * Compares all ANNIS files given by {@link ANNIS#FILE_ANNIS_FILES}
   * if they exist in gold path with the createdPath. If they do not exist, the
   * method throws an exception.
   *
   * @param goldPath
   * @param createdPath
   * @return number of successfully compared files
   * @throws IOException
   */
  private int compareFiles(File goldPath, File createdPath) throws IOException
  {
    if (!goldPath.exists())
    {
      throw new FileNotFoundException("Cannot run test, because goldPath '"
        + goldPath
        + "' does not exist (Path, where the correct files are located).");
    }

    Vector<File> filesToCompare = new Vector<>();
    for (String raFileName : ANNIS.FILE_ANNIS_FILES)
    {
      File raFile = new File(goldPath.getAbsolutePath() + File.separator
        + raFileName);
      if (raFile.exists())
      {
        filesToCompare.add(raFile);
      }
    }

    if (filesToCompare.size() == 0)
    {
      return (0);
    }

    boolean oneComparisonWasUnsuccessful = false;

    for (File goldFile : filesToCompare)
    {
      File createdFile = new File(createdPath.getAbsolutePath() + File.separator
        + goldFile.getName());
      if (!createdFile.exists())
      {
        throw new FileNotFoundException("Missing file '" + goldFile.getName()
          + "' in ANNIS path '" + createdPath.getAbsolutePath() + "'.");
      }

      if ("node.annis".equalsIgnoreCase(goldFile.getName())
        || "node_annotation.annis".equalsIgnoreCase(goldFile.getName()))
      {
        // we can ignore the ID column as long as the other columns are the same
        TabFileComparator.checkEqual(goldFile.getAbsolutePath(), createdFile.
          getAbsolutePath(), 0);
      }
      else if ("rank.annis".equalsIgnoreCase(goldFile.getName()))
      {
        // we can ignore the node_ref column as long as the other columns are the same
        TabFileComparator.checkEqual(goldFile.getAbsolutePath(), createdFile.
          getAbsolutePath(), 3);
      }
      else
      {
        TabFileComparator.checkEqual(goldFile.getAbsolutePath(), createdFile.
          getAbsolutePath());
      }
    }
    if (oneComparisonWasUnsuccessful)
    {
      return (0);
    }

    return (filesToCompare.size());
  }
}
