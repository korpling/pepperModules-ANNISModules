package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.util.FileComparator;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.IdManager;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNIS;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltSample.SaltSample;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.resources.dot.Salt2DOT;

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
		
		
		if (! globalTmpPath.exists()){
			globalTmpPath.mkdirs();
		}
		
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sDocument.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		getFixture().setSDocument(sDocument);
		
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
	
	@Override
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
		
		Salt2DOT salt2dot= new Salt2DOT();
		salt2dot.salt2Dot(getFixture().getSDocument().getSDocumentGraph(), URI.createFileURI(testPath.getAbsolutePath()+ File.separator+testName+".dot"));
		
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
		
		getFixture().mapSDocument();
		
		assertFalse("There was no file to be compared in folder '"+testPath.getAbsolutePath()+"' and folder '"+tmpPath.getAbsolutePath()+"'.", new Integer(0).equals(compareFiles(testPath, tmpPath)));
	}
	
	/**
	 * Deletes the directory with all contained directories/files
	 * @param fileToDelete
	 */
	private boolean deleteDirectory(File fileToDelete) {
		//System.out.println("Deleting "+fileToDelete.getAbsolutePath());
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
	 * @return number of compared files
	 * @throws IOException 
	 */
	private int compareFiles(File goldPath, File createdPath) throws IOException
	{
		if (!goldPath.exists())
			throw new FileNotFoundException("Cannot run test, because goldPath '"+goldPath+"' does not exist (Path, where the correct files are located).");
		
		Vector<File> filesToCompare= new Vector<File>();
		for (String raFileName: RelANNIS.FILE_RELANNIS_FILES)
		{
			File raFile= new File(goldPath.getAbsolutePath()+raFileName);
			if (raFile.exists())
				filesToCompare.add(raFile);
		}
		
		if (filesToCompare.size()== 0)
			return(0);
		
		for (File goldFile: filesToCompare)
		{
			File createdFile= new File(createdPath.getAbsolutePath()+goldFile.getName());
			if (createdFile.exists())
				throw new FileNotFoundException("Missing file '"+goldFile.getName()+"' in relANNIS path '"+createdPath.getAbsolutePath()+"'.");
			FileComparator comparator= new FileComparator();
			comparator.compareFiles(goldFile, createdFile);
		}
		return(filesToCompare.size());
	}
}
