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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hu_berlin.german.korpling.saltnpepper.pepper.common.FormatDesc;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testFramework.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ANNISExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.RelANNISExporterProperties;
import java.util.Map;
 
 
public class RelANNISExporterTest extends PepperExporterTest{ 
	
	
	@Before
	public void setUp()
	{
		setFixture(new ANNISExporter());
		File tmpFile= new File(System.getProperty("java.io.tmpdir")+File.separator+"relANNISExporter_test");
		tmpFile.mkdirs();
		File resFile= new File("./src/test/resources");
		resFile.mkdirs();
		setResourcesURI(URI.createFileURI(resFile.getAbsolutePath()));
		FormatDesc formatDef;
		formatDef= new FormatDesc();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("4.0");
		supportedFormatsCheck.add(formatDef);
	}
	@Test
	public void testSpecialParams1() throws IOException
	{
		File pepperParams= new File("./src/test/resources/testSpecialParams/test1.pepperParams");
		getFixture().getProperties().addProperties(URI.createFileURI(pepperParams.getAbsolutePath()));
		
		PepperModuleProperties props = getFixture().getProperties();
		boolean clobberVisMap = ((RelANNISExporterProperties) props).getClobberResolverVisMap();
		boolean clobberCorpusAnno = ((RelANNISExporterProperties) props).getClobberCorpusAnnotations();
		String individualCorpName = ((RelANNISExporterProperties) props).getIndividualCorpusName();
		boolean escapeCharacters = ((RelANNISExporterProperties) props).getEscapeCharacters();
		Map<Character, String> escapeCharactersList = ((RelANNISExporterProperties) props).getEscapeCharactersSet();
		
		
		assertTrue(clobberVisMap);
		assertTrue(clobberCorpusAnno);
		assertNull(individualCorpName);
		assertTrue(escapeCharacters);
		assertNull(escapeCharactersList);
		
		
		//fail();
	}
	@Test
	public void testSpecialParams2() throws IOException
	{
		File pepperParams= new File("./src/test/resources/testSpecialParams/test2.pepperParams");
		getFixture().getProperties().addProperties(URI.createFileURI(pepperParams.getAbsolutePath()));
		
		PepperModuleProperties props = getFixture().getProperties();
		boolean clobberVisMap = ((RelANNISExporterProperties) props).getClobberResolverVisMap();
		boolean clobberCorpusAnno = ((RelANNISExporterProperties) props).getClobberCorpusAnnotations();
		String individualCorpName = ((RelANNISExporterProperties) props).getIndividualCorpusName();
		boolean escapeCharacters = ((RelANNISExporterProperties) props).getEscapeCharacters();
		Map<Character, String> escapeCharactersList = ((RelANNISExporterProperties) props).getEscapeCharactersSet();
		
		
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
