package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.Salt2RelANNISMapper;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
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
	
	private static final File resourcePath = new File(System.getProperty("java.io.tmpdir")+File.separator+"relANNISModules_test" +File.separator+"Salt2relannisMapper"+File.separator);
	
	private static final File outputDirectory1 = new File(resourcePath.getAbsolutePath()+File.separator+"SampleExport1"+File.separator);
//	private static final File outputDirectory2 = new File(resourcePath.getAbsolutePath()+File.separator+"SampleExport2"+File.separator);
	
	
	@Override	
	public void setUp(){
		setFixture(new Salt2RelANNISMapper());
		
		
		if (! resourcePath.exists()){
			resourcePath.mkdirs();
		}
		if (!outputDirectory1.exists()){
			outputDirectory1.mkdirs();
		}
		
	}
	@Override
	public void tearDown() throws IOException{
		File resourceDir = resourcePath.getParentFile();
		//System.out.println(resourceDir.toString());
		if (!deleteDirectory(resourceDir))
			throw new IOException();
	}
	
	public void testMapSText(){
		SDocument sDocument= SaltFactory.eINSTANCE.createSDocument();
		sDocument.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
		// create the primary text
		SaltSample.createPrimaryData(sDocument);
		getFixture().setResourceURI(URI.createFileURI(resourcePath.getAbsolutePath()));
		
		getFixture().mapSDocument();
		
		//TODO do the tests, you need
		fail();
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


//	/**
//	 * Method for compating xml-documents. <br/>
//	 * This method checks whether input and output files 
//	 * are identical which should <br/>
//	 * be the case since the 
//	 * test works with SaltSample. 
//	 * 
//	 * @param uri input path
//	 * @param uri2 output path
//	 */
//	private void compareDocuments(URI uri, URI uri2) {
//		File fileToCheck = null;
//		InputSource gold = null;
//		InputSource toCheck = null;
//		FileComparator fileComparator = new FileComparator();
//		for (File in : new File(uri.toFileString()).listFiles(this)){
//			fileToCheck = new File(uri2.toFileString()+File.separator+in.getName());
//			try {
//				toCheck = new InputSource(new FileInputStream(fileToCheck));
//				gold = new InputSource(new FileInputStream(in));
//				
//				
//				if (! (fileComparator.compareFiles(in, fileToCheck))){
//					System.out.println("WARNING: File "+in.getAbsolutePath()+" and "+ fileToCheck.getAbsolutePath()+" are not equal!");
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
}
