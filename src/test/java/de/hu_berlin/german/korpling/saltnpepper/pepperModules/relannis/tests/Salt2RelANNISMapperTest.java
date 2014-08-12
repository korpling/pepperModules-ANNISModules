package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.tests;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleTestException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.IdManager;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNIS;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltSample.SaltSample;

public class Salt2RelANNISMapperTest extends TestCase 
{
	private Salt2RelANNISMapper fixture= null;

	public Salt2RelANNISMapper getFixture() {
		return fixture;
	}
	public void setFixture(Salt2RelANNISMapper fixture) {
		this.fixture = fixture;
	}
	
	private static final File globalTmpPath = new File(System.getProperty("java.io.tmpdir")+File.separator+"relANNISModules_test" +File.separator+"Salt2relannisMapper"+File.separator);
	
	@Override	
	public void setUp(){
		setFixture(new Salt2RelANNISMapper());
		
		getFixture().mapRelationsInParallel(false);
		
		if (! globalTmpPath.exists()){
			globalTmpPath.mkdirs();
		}
		
		SCorpusGraph sCorpGraph= SaltFactory.eINSTANCE.createSCorpusGraph();
		SCorpus sCorpus1= SaltFactory.eINSTANCE.createSCorpus();
		sCorpus1.setSName("mainCorp");
		sCorpGraph.addSNode(sCorpus1);
		
		
		fixture.setSCorpusGraph(sCorpGraph);
		
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sCorpGraph.addSDocument(sCorpus1, sDocument);
		
		sDocument.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		getFixture().setSDocument(sDocument);
		
		getFixture().isTestMode = true;
		
		IdManager idManager= new IdManager();
		getFixture().setIdManager(idManager);
	}
	
	private void createTupleWriters(File path)
	{
		getFixture().tw_text= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath()+File.separator+ RelANNIS.FILE_TEXT));
		getFixture().tw_node= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_NODE));
		getFixture().tw_nodeAnno= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_NODE_ANNO));
		getFixture().tw_rank= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_RANK));
		getFixture().tw_edgeAnno= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_EDGE_ANNO));
		getFixture().tw_component= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_COMPONENT));
		getFixture().tw_corpus= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_CORPUS));
		getFixture().tw_corpusMeta= RelANNISExporter.createTupleWRiter(new File(path.getAbsolutePath() +File.separator+ RelANNIS.FILE_CORPUS_META));
	}
	
	@After
	public void tearDown() throws IOException{
//		if (!deleteDirectory(tmpPath))
//			throw new IOException();
	}

	
	/** returns path of test resources **/
	private String getTestPath()
	{
		return("./src/test/resources/");
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSText() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapIndividualCorpusName() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().individualCorpusName="NewTopLevelCorpus";
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}

	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSToken() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		SaltSample.createTokens(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text in english</li>
	 * <li> a primary text in german</li>
	 *  <li>tokens for english</li>
	 *  <li>tokens for german</li>
	 *  
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSeveralTokenizations() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		SaltSample.createTokens(getFixture().getSDocument());
		STextualDS primaryData_DE= SaltSample.createPrimaryData(getFixture().getSDocument(), SaltSample.LANG_DE);
		SaltSample.createTokens(getFixture().getSDocument(), primaryData_DE);
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text in english</li>
	 * <li> a primary text in german</li>
	 *  <li>tokens for english</li>
	 *  <li>tokens for german</li>
	 *  <li>alignment relations</li>
	 *  
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapParallelData() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createParallelData(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text in english</li>
	 * <li> a primary text in german</li>
	 *  <li>tokens for english</li>
	 *  <li>tokens for german</li>
	 *  <li>alignment relations</li>
	 *  <li>one pointingRelation which has no SType</li>
	 *  
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapUntypedPointingRelation() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createParallelData(getFixture().getSDocument(),false);
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>annotations on tokens</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSTokenAnnotation() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		SaltSample.createTokens(getFixture().getSDocument());
		SaltSample.createMorphologyAnnotations(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
	
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>spans</li>
	 *  <li>annotations on spans</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSSpans() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		SaltSample.createTokens(getFixture().getSDocument());
		SaltSample.createInformationStructureSpan(getFixture().getSDocument());
		SaltSample.createInformationStructureAnnotations(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>syntax tree</li>
	 *  <li>annotation on syntax tree</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSSyntax() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		SaltSample.createTokens(getFixture().getSDocument());
		SaltSample.createSyntaxStructure(getFixture().getSDocument());
		SaltSample.createSyntaxAnnotations(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>anaphoric annotations</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapAnaphoric() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createPrimaryData(getFixture().getSDocument());
		SaltSample.createTokens(getFixture().getSDocument());
		SaltSample.createAnaphoricAnnotations(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>annotations on token</li>
	 *  <li>spans</li>
	 *  <li>information structure annotation on spans</li>
	 *  <li>syntax tree</li>
	 *  <li>annotations on syntax tree</li>
	 *  <li>annotation on syntax tree</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapFullGraph() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createSDocumentStructure(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	@Test
	public void testMultiThreadingSpeed(){
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createSDocumentStructure(getFixture().getSDocument());
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		getFixture().mapSCorpus();
		
		long startTime = 0l;
		long stopTime = 0l;
		long elapsedTime = 0l;
		
		long averageTime = 0l;
		System.out.println("INFO: Starting single-threaded mapping (average of 100 times) of FullGraph");
		for (int i = 0; i < 100 ; i++){
			startTime = System.currentTimeMillis();
			getFixture().mapSDocument();
		    stopTime = System.currentTimeMillis();
		    elapsedTime = stopTime - startTime;
		    averageTime += elapsedTime;
		}
	    System.out.println("INFO: Single-threaded mapping (average of 100 times) of FullGraph needed "+(averageTime/100)+" ms");
	    
	    averageTime = 0l;
	    
	    System.out.println("INFO: Starting multi-threaded mapping (average of 100 times) of FullGraph");
		for (int i = 0; i < 100 ; i++){
			startTime = System.currentTimeMillis();
			getFixture().mapRelationsInParallel(true);
			getFixture().mapSDocument();
		    stopTime = System.currentTimeMillis();
		    elapsedTime = stopTime - startTime;
		    averageTime += elapsedTime;
		}
	    System.out.println("INFO: Multi-threaded mapping (average of 100 times) of FullGraph needed "+(averageTime/100)+" ms");
		
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>annotations on token</li>
	 *  <li>spans</li>
	 *  <li>information structure annotation on spans</li>
	 *  <li>syntax tree</li>
	 *  <li>annotations on syntax tree</li>
	 *  <li>annotation on syntax tree</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testMapSOrderRelation() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createDialogue(getFixture().getSDocument());
		
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		File dotPath = new File(globalTmpPath.getAbsoluteFile()+testName+File.separator+"newSOrderRelationTest.dot");	
		
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Creates an {@link SDocumentGraph} containing:
	 * <ul>
	 * 	<li> a primary text</li>
	 *  <li>tokens</li>
	 *  <li>annotations on token</li>
	 *  <li>spans</li>
	 *  <li>information structure annotation on spans</li>
	 *  <li>syntax tree</li>
	 *  <li>annotations on syntax tree</li>
	 *  <li>annotation on syntax tree</li>
	 * </ul>
	 * @throws IOException
	 */
	@Test
	public void testComplexMapSOrderRelation() throws IOException
	{
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String testName= ste[1].getMethodName();
		
		File tmpPath= new File(globalTmpPath.getAbsoluteFile()+ File.separator+testName);
		File testPath= new File(getTestPath()+testName);
		createTupleWriters(tmpPath);
		
		// create the primary text
		SaltSample.createDialogue(getFixture().getSDocument());
		EList<SToken> sDocumentTokens = getFixture().getSDocument().getSDocumentGraph().getSTokens();
		getFixture().getSDocument().getSDocumentGraph().createSRelation(sDocumentTokens.get(0), sDocumentTokens.get(1), STYPE_NAME.SPOINTING_RELATION, "anno=test");
		getFixture().getSDocument().getSDocumentGraph().createSSpan(sDocumentTokens.get(0));
		
		getFixture().setResourceURI(URI.createFileURI(tmpPath.getAbsolutePath()));
		
		File dotPath = new File(globalTmpPath.getAbsoluteFile()+testName+File.separator+"newSOrderRelationTest.dot");	
		
		getFixture().mapSCorpus();
		getFixture().mapSDocument();
		
		
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	
	/**
	 * Deletes the directory with all contained directories/files
	 * @param fileToDelete
	 */
	private boolean deleteDirectory(File fileToDelete) {
		if (fileToDelete.isDirectory()) {
	        String[] directoryContent = fileToDelete.list();
	        for (int i=0; i < directoryContent.length; i++) {
	            if (! (deleteDirectory(new File(fileToDelete, directoryContent[i])))) {
	                return false;
	            }
	        }
	    }

	    return fileToDelete.delete();
	}

	/**
	 * Compares all relANNIS files given by {@link RelANNIS#FILE_RELANNIS_FILES} if they exist in gold path with the createdPath.
	 * If they do not exist, the method throws an exception. 
	 * @param goldPath
	 * @param createdPath
	 * @return number of successfully compared files
	 * @throws IOException 
	 */
	private int compareFiles(File goldPath, File createdPath) throws IOException
	{
		if (!goldPath.exists())
			throw new FileNotFoundException("Cannot run test, because goldPath '"+goldPath+"' does not exist (Path, where the correct files are located).");
		
		Vector<File> filesToCompare= new Vector<File>();
		for (String raFileName: RelANNIS.FILE_RELANNIS_FILES)
		{
			File raFile= new File(goldPath.getAbsolutePath()+File.separator+raFileName);
			if (raFile.exists())
				filesToCompare.add(raFile);
		}
		
		if (filesToCompare.size()== 0)
			return(0);
		
		boolean oneComparisonWasUnsuccessful = false;
		
		for (File goldFile: filesToCompare)
		{
			File createdFile= new File(createdPath.getAbsolutePath()+File.separator+goldFile.getName());
			if (! createdFile.exists())
				throw new FileNotFoundException("Missing file '"+goldFile.getName()+"' in relANNIS path '"+createdPath.getAbsolutePath()+"'.");
			TabFileComparator.checkEqual(goldFile.getAbsolutePath(), createdFile.getAbsolutePath(), 0);
 	    
		}
		if (oneComparisonWasUnsuccessful){
			return(0);
		}
		
		return(filesToCompare.size());
	}
}
