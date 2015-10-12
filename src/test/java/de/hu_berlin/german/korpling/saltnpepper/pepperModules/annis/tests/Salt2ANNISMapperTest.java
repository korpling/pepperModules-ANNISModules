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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.corpus_tools.salt.SALT_TYPE;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.SOrderRelation;
import org.corpus_tools.salt.common.SPointingRelation;
import org.corpus_tools.salt.common.SStructuredNode;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.common.STextualRelation;
import org.corpus_tools.salt.common.STimeline;
import org.corpus_tools.salt.common.STimelineRelation;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.core.SLayer;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.exceptions.SaltSampleException;
import org.corpus_tools.salt.graph.Relation;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNIS;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporterProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.GlobalIdManager;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.IdManager;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.Salt2ANNISMapper;

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
    getFixture().setProperties(new ANNISExporterProperties());

    if (!globalTmpPath.exists())
    {
      globalTmpPath.mkdirs();
    }

    SCorpusGraph sCorpGraph = SaltFactory.createSCorpusGraph();
    rootCorpus = SaltFactory.createSCorpus();
    rootCorpus.setName("mainCorp");
    sCorpGraph.addNode(rootCorpus);

    fixture.setCorpusGraph(sCorpGraph);

    sDocument = SaltFactory.createSDocument();
    sCorpGraph.addDocument(rootCorpus, sDocument);

    sDocument.setDocumentGraph(SaltFactory.createSDocumentGraph());
    getFixture().setDocument(sDocument);

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
	  getFixture().setCorpus(rootCorpus);
	  getFixture().mapSCorpus();
	  getFixture().setDocument(sDocument);
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
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
	SampleGenerator.createPrimaryData(getFixture().getDocument());
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
    STextualDS primaryData_DE = SampleGenerator.createPrimaryData(getFixture().
      getDocument(), SampleGenerator.LANG_DE);
    SampleGenerator.createTokens(getFixture().getDocument(), primaryData_DE);
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
    SampleGenerator.createParallelData(getFixture().getDocument());
	  
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
    SampleGenerator.createParallelData(getFixture().getDocument(), false);
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
    SampleGenerator.createMorphologyAnnotations(getFixture().getDocument());
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
    SampleGenerator.createInformationStructureSpan(getFixture().getDocument());
    SampleGenerator.
      createInformationStructureAnnotations(getFixture().getDocument());
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
    SampleGenerator.createSyntaxStructure(getFixture().getDocument());
    SampleGenerator.createSyntaxAnnotations(getFixture().getDocument());
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
    
    SDocumentGraph graph = SaltFactory.createSDocumentGraph();
    getFixture().getDocument().setDocumentGraph(graph);
    
    
    STextualDS textDS = graph.createTextualDS("a");
    
    SToken tok = graph.createToken(textDS, 0, 1);
    
    SStructuredNode struct4 = graph.createSStructure(tok);
    SStructuredNode struct3 = graph.createSStructure(struct4);
    SStructuredNode struct2 = graph.createSStructure(struct4);
    List<SStructuredNode> struct1Targets = new ArrayList<>();
    struct1Targets.add(struct2);
    struct1Targets.add(struct3);
    SStructuredNode struct1 = graph.createStructure(struct1Targets);

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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
    SampleGenerator.createAnaphoricAnnotations(getFixture().getDocument());
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
    SampleGenerator.createSDocumentStructure(getFixture().getDocument());
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
    SampleGenerator.createSDocumentStructure(getFixture().getDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));

    getFixture().setCorpus(rootCorpus);
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
      getFixture().setDocument(sDocument);
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
    SampleGenerator.createDialogue(getFixture().getDocument());
    
    SDocumentGraph g = getFixture().getDocument().getDocumentGraph();
    
    
    // change the "yes!" to be two token instead of one.
    SToken yesToken = g.getTokens().get(13);
    assertEquals("yes!", g.getText(yesToken));
    STextualDS ds = null;
    int oldEnd = -1;
    for(Relation e : g.getOutRelations(yesToken.getId())) {
      if(e instanceof STextualRelation) {
        STextualRelation textRelYes = (STextualRelation) e;
        oldEnd = textRelYes.getEnd();
        textRelYes.setEnd(oldEnd-1);
        ds = textRelYes.getTarget();
        break;
      }
    }
    Assert.assertNotNull(ds);
    SToken exclamationToken = g.createToken(ds, oldEnd-1, oldEnd);
    assertEquals("!", g.getText(exclamationToken));
    
    SOrderRelation orderRelExclamation = SaltFactory.createSOrderRelation();
    orderRelExclamation.setSource(yesToken);
    orderRelExclamation.setTarget(exclamationToken);
    g.addRelation(orderRelExclamation);
    STimelineRelation timeRelExclamation = SaltFactory.createSTimelineRelation();
		timeRelExclamation.setSource(exclamationToken);
		timeRelExclamation.setTarget(g.getTimeline());
		timeRelExclamation.setStart(11);
		timeRelExclamation.setEnd(12);
		sDocument.getDocumentGraph().addRelation(timeRelExclamation);
    
    // add a span which overlaps a token which gets a new token-index after the
    // artificial tokenization was created
    List<SToken> coveredBySpan = new ArrayList<>();
    coveredBySpan.add(g.getTokens().get(g.getTokens().size()-3));
    coveredBySpan.add(yesToken);
    assertEquals("oh", g.getText(coveredBySpan.get(0)));
    assertEquals("yes", g.getText(coveredBySpan.get(1)));
    g.createSpan(coveredBySpan);

    
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
    SampleGenerator.createDialogue(getFixture().getDocument());
    List<SToken> sDocumentTokens = getFixture().getDocument().
      getDocumentGraph().getTokens();
    SRelation pointing = getFixture().getDocument().getDocumentGraph().createSRelation(
      sDocumentTokens.get(0), sDocumentTokens.get(1),
      SALT_TYPE.SPOINTING_RELATION, "anno=test");
    pointing.setType("dep");
    getFixture().getDocument().getDocumentGraph().createSpan(sDocumentTokens.
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());

    // create a single SOrderRelation chain
    SToken previousToken = null;
    for (SToken t : getFixture().getDocument().getDocumentGraph().
      getSortedTokenByText())
    {
      if (previousToken != null)
      {
        SOrderRelation r = SaltFactory.createSOrderRelation();
        r.setType("order");
        r.setSource(previousToken);
        r.setTarget(t);
        getFixture().getDocument().getDocumentGraph().addRelation(r);
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
    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());

    // create a several SOrderRelation chains (pairwise)
    List<SToken> tokens = getFixture().getDocument().getDocumentGraph().
            getSortedTokenByText();
    int remainder = tokens.size() % 2;
    int numberOfChains = (tokens.size() - remainder) / 2;
    LinkedList<String> expectedNames = new LinkedList<>();
    for (int i = 0; i < numberOfChains; i++) {
      SOrderRelation r = SaltFactory.createSOrderRelation();
      r.setType("order");
      r.setSource(tokens.get(i * 2));
      r.setTarget(tokens.get((i * 2) + 1));
      getFixture().getDocument().getDocumentGraph().addRelation(r);

      if (i == numberOfChains - 1 && remainder == 1) {
        SOrderRelation additionalRel = SaltFactory.createSOrderRelation();
        additionalRel.setType("order");
        additionalRel.setSource(tokens.get((i * 2) + 1));
        additionalRel.setTarget(tokens.get((i * 2) + 2));
        getFixture().getDocument().getDocumentGraph().addRelation(additionalRel);
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
		if (sDocument.getDocumentGraph() == null) {
			sDocument.setDocumentGraph(SaltFactory.createSDocumentGraph());
		}
		STextualDS sTextualDS = null;
		// creating the primary text depending on the language
		sTextualDS = SaltFactory.createSTextualDS();
		sTextualDS.setText(sampleText);
		// adding the text to the document-graph
		sDocument.getDocumentGraph().addNode(sTextualDS);
    
    SToken sToken = SaltFactory.createSToken();
		sDocument.getDocumentGraph().addNode(sToken);
		
		STextualRelation sTextRel = SaltFactory.createSTextualRelation();
		sTextRel.setSource(sToken);
		sTextRel.setTarget(sTextualDS);
		sTextRel.setStart(0);
		sTextRel.setEnd(sampleText.length());
		sDocument.getDocumentGraph().addRelation(sTextRel);
    
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
		if (sDocument.getDocumentGraph() == null) {
			sDocument.setDocumentGraph(SaltFactory.createSDocumentGraph());
		}
		STextualDS sTextualDS = null;
		// creating the primary text depending on the language
		sTextualDS = SaltFactory.createSTextualDS();
		sTextualDS.setText(sampleText);
		// adding the text to the document-graph
		sDocument.getDocumentGraph().addNode(sTextualDS);
    
    SToken sToken = SaltFactory.createSToken();
		sDocument.getDocumentGraph().addNode(sToken);
		
		STextualRelation sTextRel = SaltFactory.createSTextualRelation();
		sTextRel.setSource(sToken);
		sTextRel.setTarget(sTextualDS);
		sTextRel.setStart(0);
		sTextRel.setEnd(sampleText.length());
		sDocument.getDocumentGraph().addRelation(sTextRel);
    
    doMapping();
    
    TabFileComparator.checkEqual(new File(testPath, ANNIS.FILE_NODE).getAbsolutePath(), 
            new File(tmpPath, ANNIS.FILE_NODE).getAbsolutePath());
    
  }
  
  @Test
  public void testIDEscape() throws IOException {

    SampleGenerator.createPrimaryData(getFixture().getDocument());
    SampleGenerator.createTokens(getFixture().getDocument());
    getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
    
    SToken tok1 = getFixture().getDocument().getDocumentGraph().getTokens().get(0);
    SToken tok2 = getFixture().getDocument().getDocumentGraph().getTokens().get(1);
    
    // invalid annotation
    tok1.createAnnotation("6>]`'[;+|]", "1\\.[`&[}²*", "value");
    
    // invalid relation name
    SLayer layer = SaltFactory.createSLayer();
		layer.setName("7#=+}}ä?ö;");
    getFixture().getDocument().getDocumentGraph().addLayer(layer);
    
    SPointingRelation dep = SaltFactory.createSPointingRelation();
    dep.setSource(tok1);
    dep.setTarget(tok2);
    dep.setType("-7#=+}}ä?ö;");
    layer.addRelation(dep);
    getFixture().getDocument().getDocumentGraph().addRelation(dep);
    
    // invalid relation annotation
    dep.createAnnotation("0>;;!]{§", "2ß{.:)²,¸", "value");
    
    // invalid segmentation name
    SOrderRelation order = SaltFactory.createSOrderRelation();
    order.setSource(tok1);
    order.setTarget(tok2);
    order.setType("-,$#³>ä~ß.");
    getFixture().getDocument().getDocumentGraph().addRelation(order);
    
    // invalid meta data (different annotation names with the same mapping)
    getFixture().getDocument().createMetaAnnotation(
            "ns",
            "%invalid%%name$", "value1");
    getFixture().getDocument().createMetaAnnotation(
            "ns",
            "invalid__name_", "value2");
    getFixture().getDocument().createMetaAnnotation(
            "ns",
            "invalid_§name_", "value3");
    
    doMapping();

    assertFalse("There was no file to be compared in folder '" + testPath.
      getAbsolutePath() + "' and folder '" + tmpPath.getAbsolutePath() + "'.",
      new Integer(0).equals(compareFiles(testPath, tmpPath)));
  }
  
  @Test
  public void testMapVirtualTokenWithMissing() throws IOException
  {
    SDocumentGraph g = getFixture().getDocument().getDocumentGraph();
    
    STimeline timeLine = g.createTimeline();
    for(int i=1; i <= 7; i++) {
    	timeLine.increasePointOfTime();
    }
    
    STextualDS text1 = g.createTextualDS("Hello?");
    STextualDS text2 = g.createTextualDS("World!");
    
    SToken tokHello = g.createToken(text1, 0, 5);
    SToken tokWorld = g.createToken(text2, 0, 5);
    SToken tokExclamation = g.createToken(text2, 5, 6);
    SToken tokQuestion = g.createToken(text1, 5, 6);
    
    STimelineRelation timeRelHello = SaltFactory.createSTimelineRelation();
    timeRelHello.setTarget(timeLine);
    timeRelHello.setSource(tokHello);
    timeRelHello.setStart(0);
    timeRelHello.setEnd(6);
    g.addRelation(timeRelHello);
    
    STimelineRelation timeRelWorld = SaltFactory.createSTimelineRelation();
    timeRelWorld.setTarget(timeLine);
    timeRelWorld.setSource(tokWorld);
    timeRelWorld.setStart(1);
    timeRelWorld.setEnd(2);
    g.addRelation(timeRelWorld);
    
    STimelineRelation timeRelExclamation = SaltFactory.createSTimelineRelation();
    timeRelExclamation.setTarget(timeLine);
    timeRelExclamation.setSource(tokExclamation);
    timeRelExclamation.setStart(3);
    timeRelExclamation.setEnd(4);
    g.addRelation(timeRelExclamation);
    
    STimelineRelation timeRelQuestion = SaltFactory.createSTimelineRelation();
    timeRelQuestion.setTarget(timeLine);
    timeRelQuestion.setSource(tokQuestion);
    timeRelQuestion.setStart(6);
    timeRelQuestion.setEnd(7);
    g.addRelation(timeRelQuestion);
    
    SOrderRelation orderRel1 = SaltFactory.createSOrderRelation();
    orderRel1.setSource(tokHello);
    orderRel1.setTarget(tokQuestion);
    orderRel1.setType("tok1");
    g.addRelation(orderRel1);
    
    SOrderRelation orderRel2 = SaltFactory.createSOrderRelation();
    orderRel2.setSource(tokWorld);
    orderRel2.setTarget(tokExclamation);
    orderRel2.setType("tok2");
    g.addRelation(orderRel2);
    
    doMapping();
    
    TabFileComparator.checkEqual(testPath.getAbsolutePath() + "/" + ANNIS.FILE_NODE, 
      tmpPath.getAbsolutePath() + "/" + ANNIS.FILE_NODE, 0);
    

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
          getAbsolutePath(), 0, 3);
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
