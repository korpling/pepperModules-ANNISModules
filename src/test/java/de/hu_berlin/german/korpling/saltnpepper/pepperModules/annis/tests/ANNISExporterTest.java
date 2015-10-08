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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporterProperties;
 
 
public class ANNISExporterTest extends PepperExporterTest{ 
	
	
	@Before
	public void setUp()
	{
		setFixture(new ANNISExporter());
		File tmpFile= new File(System.getProperty("java.io.tmpdir")+File.separator+"ANNISExporter_test");
		tmpFile.mkdirs();
		File resFile= new File("./src/test/resources");
		resFile.mkdirs();
		setResourcesURI(URI.createFileURI(resFile.getAbsolutePath()));
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
    
	}
	@Test
	public void testCustomizationProperties1() throws IOException
	{
		File pepperParams= new File("./src/test/resources/testSpecialParams/test1.pepperParams");
		getFixture().getProperties().addProperties(URI.createFileURI(pepperParams.getAbsolutePath()));
		
		PepperModuleProperties props = getFixture().getProperties();
		boolean clobberVisMap = ((ANNISExporterProperties) props).getClobberResolverVisMap();
		boolean clobberCorpusAnno = ((ANNISExporterProperties) props).getClobberCorpusAnnotations();
		String individualCorpName = ((ANNISExporterProperties) props).getIndividualCorpusName();
		boolean escapeCharacters = ((ANNISExporterProperties) props).getEscapeCharacters();
		Map<Character, String> escapeCharactersList = ((ANNISExporterProperties) props).getEscapeCharactersSet();
		
		
		assertTrue(clobberVisMap);
		assertTrue(clobberCorpusAnno);
		assertNull(individualCorpName);
		assertTrue(escapeCharacters);
		assertNull(escapeCharactersList);
		
		
		//fail();
	}
	@Test
	public void testCustomizationProperties2() throws IOException
	{
		File pepperParams= new File("./src/test/resources/testSpecialParams/test2.pepperParams");
		getFixture().getProperties().addProperties(URI.createFileURI(pepperParams.getAbsolutePath()));
		
		PepperModuleProperties props = getFixture().getProperties();
		boolean clobberVisMap = ((ANNISExporterProperties) props).getClobberResolverVisMap();
		boolean clobberCorpusAnno = ((ANNISExporterProperties) props).getClobberCorpusAnnotations();
		String individualCorpName = ((ANNISExporterProperties) props).getIndividualCorpusName();
		boolean escapeCharacters = ((ANNISExporterProperties) props).getEscapeCharacters();
		Map<Character, String> escapeCharactersList = ((ANNISExporterProperties) props).getEscapeCharactersSet();
		
		
		assertTrue(clobberVisMap);
		assertTrue(clobberCorpusAnno);
		assertNotNull(individualCorpName);
		assertEquals(individualCorpName,"TestCorpusName");
		assertFalse(escapeCharacters);
		assertNotNull(escapeCharactersList);
		
		// assert: (\=\\),(a=bcd),(\t= )
		
		//fail();
	}
	
}
