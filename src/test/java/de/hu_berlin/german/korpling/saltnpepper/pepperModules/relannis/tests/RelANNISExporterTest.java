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

import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModulesFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.moduleTests.PepperExporterTest;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.RelANNISExporter;

 
 
public class RelANNISExporterTest extends PepperExporterTest{ 
	
	@Override
	public void setUp()
	{
		setFixture(new RelANNISExporter());
		File tmpFile= new File(System.getProperty("java.io.tmpdir")+File.separator+"relANNISExporter_test");
		tmpFile.mkdirs();
		setTemprorariesURI(URI.createFileURI(tmpFile.getAbsolutePath()));
		File resFile= new File("./src/test/resources");
		resFile.mkdirs();
		setResourcesURI(URI.createFileURI(resFile.getAbsolutePath()));
		FormatDefinition formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("3.1");
		supportedFormatsCheck.add(formatDef);
		formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("3.2");
		supportedFormatsCheck.add(formatDef);
		formatDef= PepperModulesFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("4.0");
		supportedFormatsCheck.add(formatDef);
	}
}
