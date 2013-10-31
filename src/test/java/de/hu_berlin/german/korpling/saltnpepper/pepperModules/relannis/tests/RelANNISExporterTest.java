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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.CorpusDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.util.FileComparator;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltProject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltSample.SaltSample;
import junit.framework.TestCase;
import org.eclipse.emf.common.util.URI;
import org.xml.sax.InputSource;

 
 
public class RelANNISExporterTest extends TestCase implements FilenameFilter{
	private RelANNISExporter fixture = null;
	private SaltSample saltSample = null;
	
	//String resourcePath = "file://"+(new File("_TMP"+File.separator+"test"+File.separator).getAbsolutePath());
	String resourcePath = (new File("_TMP"+File.separator+"test"+File.separator).getAbsolutePath());
	
	String outputDirectory1 = resourcePath+File.separator+"SampleExport1"+File.separator;
	String outputDirectory2 = resourcePath+File.separator+"SampleExport2"+File.separator;
	
	public boolean accept( File f, String s )
	  {
	    return s.toLowerCase().endsWith( ".xml" ) 
	    	 & s.toLowerCase().indexOf("anno")==-1 ;
			
		
	  }

	
	public RelANNISExporter getFixture() {
		return this.fixture;
	}

	public void setFixture(RelANNISExporter fixture) {
		this.fixture = fixture;
	}
	
	public void setSaltSample(SaltSample saltSample){
		this.saltSample = saltSample;
	}
	
	@Override	
	public void setUp(){
		this.setFixture(new RelANNISExporter());
		
		
		if (! new File(resourcePath).exists()){
			new File(resourcePath).mkdirs();
			System.out.println("Creating Resource Path :"+ resourcePath);
		}
		if (! new File(outputDirectory1).exists()){
			new File(outputDirectory1).mkdirs();
			System.out.println("Creating output Path :"+ outputDirectory1);
		}
		
	}
	
	public void testMapSText(){
		saltSample = new SaltSample();
		this.fixture = new RelANNISExporter();
		//SaltSample.createSDocumentStructure(sDocument);
		try {
			//
			//this.getFixture().setSCorpusGraph(saltSample.getCorpus());
			
			SaltProject proj = SaltFactory.eINSTANCE.createSaltProject();
			//proj.getSCorpusGraphs().add(saltSample.getCorpus());
			SaltSample.createCorpusStructure(proj);
			for (SDocument document : proj.getSCorpusGraphs().get(0).getSDocuments()){
				document.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
				// create the primary text
				SaltSample.createPrimaryData(document);
			//	SaltSample.createTokens(document);
			//	SaltSample.createMorphologyAnnotations(document);
			}
			
			if (proj.getSCorpusGraphs() == null){
				System.out.println("ERROR: SCorpusGraph is NULL!");
			}
			
			this.fixture.setSaltProject(proj);
			CorpusDefinition corpDef= PepperModulesFactory.eINSTANCE.createCorpusDefinition();
			FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
			formatDef.setFormatName("relANNIS");
			formatDef.setFormatVersion("4.0");
			corpDef.setFormatDefinition(formatDef);
			corpDef.setCorpusPath(URI.createFileURI(resourcePath));
			System.out.println("ResourcePath should be "+ resourcePath);
			System.out.println("Set output path to "+ (new File(resourcePath)).getAbsolutePath());
			this.fixture.setCorpusDefinition(corpDef);
			this.fixture.setSpecialParams(null);
			
			this.getFixture().start(proj.getSCorpusGraphs().get(0).getSRootCorpus().get(0).getSElementId());
			for (SDocument document : proj.getSCorpusGraphs().get(0).getSDocuments()){
				//document.setSDocumentGraph(SaltFactory.eINSTANCE.createSDocumentGraph());
				// create the primary text
				//SaltSample.createPrimaryData(document);
				//SaltSample.createMorphologyAnnotations(document);
				this.getFixture().start(document.getSElementId());
			}
			
			
		} catch (RelANNISModuleException e){
			
		}	
		
	}

	private void cleanUp() {
		File resourceDir = new File(resourcePath).getParentFile();
		//System.out.println(resourceDir.toString());
		if (deleteDirectory(resourceDir))
			System.out.println("Deleted temporary directory "+resourceDir.getAbsolutePath());
		
		
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
	 * Method for compating xml-documents. <br/>
	 * This method checks whether input and output files 
	 * are identical which should <br/>
	 * be the case since the 
	 * test works with SaltSample. 
	 * 
	 * @param uri input path
	 * @param uri2 output path
	 */
	private void compareDocuments(URI uri, URI uri2) {
		File fileToCheck = null;
		InputSource gold = null;
		InputSource toCheck = null;
		FileComparator fileComparator = new FileComparator();
		for (File in : new File(uri.toFileString()).listFiles(this)){
			fileToCheck = new File(uri2.toFileString()+File.separator+in.getName());
			try {
				toCheck = new InputSource(new FileInputStream(fileToCheck));
				gold = new InputSource(new FileInputStream(in));
				
				
				if (! (fileComparator.compareFiles(in, fileToCheck))){
					System.out.println("WARNING: File "+in.getAbsolutePath()+" and "+ fileToCheck.getAbsolutePath()+" are not equal!");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
