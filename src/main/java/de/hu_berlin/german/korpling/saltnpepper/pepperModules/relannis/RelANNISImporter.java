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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISResourceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperImporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperImporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

@Component(name="RelANNISImporterComponent", factory="PepperImporterComponentFactory")
@Service(value=PepperImporter.class)
public class RelANNISImporter extends PepperImporterImpl implements PepperImporter
{	
	public RelANNISImporter()
	{
		super();

		//setting name of module
		this.name= "RelANNISImporter";
		//set list of formats supported by this module
		this.addSupportedFormat("relANNIS", "3.1", null);
	}

	/**
	 * Stores relation between SElementId and raId. 
	 */
	private Map<SElementId, Long> sElementId2RaId= null;
	
	/**
	 * Corpus graph model of relANNIS model.
	 */
	private RACorpusGraph raCorpusGraph= null;
	
	@Override
	public void importCorpusStructure(SCorpusGraph corpusGraph)
			throws PepperModuleException 
	{
		this.setSCorpusGraph(corpusGraph);
		if (this.getSCorpusGraph()== null)
			throw new RelANNISModuleException(this.name+": Cannot start with importing corpus, because salt graph isn�t set.");
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
		
		if (this.getCorpusDefinition().getCorpusPath()== null)
			throw new RelANNISModuleException("Cannot load corpus structure, because no corpus path is given.");
		//load resource 
		Resource resource = resourceSet.createResource(this.getCorpusDefinition().getCorpusPath());
		if (resource== null)
			throw new RelANNISModuleException("Cannot load corpus structure, because the given resource is null.");
		
		Map<String, String> optionMap= new Hashtable<String, String>();
		optionMap.put("LOADING_TYPE", "CORPUS_STRUCTURE");
		try {
			resource.load(optionMap);
		} catch (IOException e) 
		{
			throw new RelANNISModuleException("Cannot load corpus structure.", e);
		}
		RACorpusGraph raCorpusGraph= (RACorpusGraph) resource.getContents().get(0);
		this.raCorpusGraph= raCorpusGraph;
		RelANNIS2SaltMapper mapper= new RelANNIS2SaltMapper();
		mapper.mapRACorpusGraph2SCorpusGraph(raCorpusGraph, this.getSCorpusGraph());
		//sets table for relation between SElementId and raId
		this.sElementId2RaId= mapper.getsElementId2RaId();
	}
	
	
	
	@Override
	public void start(SElementId sElementId) throws PepperModuleException 
	{
		{//checking special parameter
//			if (this.getSpecialParams()== null)
//				throw new PepperModuleException("Cannot start converting, because no special parameters are set.");
//			File specialParamFile= new File(this.getSpecialParams().toFileString());
//			if (!specialParamFile.exists())
//				throw new PepperModuleException("Cannot start converting, because the file for special parameters does not exists: "+ specialParamFile);
//			if (!specialParamFile.isFile())
//				throw new PepperModuleException("Cannot start converting, because the file for special parameters is not a file: "+ specialParamFile);
		}
		
		if (sElementId== null)
			throw new RelANNISModuleException("Cannot not import, because the given SElmentId-object is empty.");
		if (sElementId.getSIdentifiableElement()== null)
			throw new RelANNISModuleException("Cannot not import, because the given SElmentId-object has no SIdentifiableElement-object like a SCorpus- or SDocument object. It is null.");
		//if elementId belongs to SDocument
		if(!	(	(sElementId.getSIdentifiableElement() instanceof SDocument) ||
					(sElementId.getSIdentifiableElement() instanceof SCorpus)))
			throw new PepperModuleException("Cannot import data to given sElementID "+sElementId.getSId()+", because the corresponding element is not of kind SDocument or SCorpus. It is of kind: "+ sElementId.getSIdentifiableElement().getClass().getName());
		if (sElementId.getSIdentifiableElement() instanceof SDocument)
		{//sElementId belongs to SDOcument-object
			SDocument sDocument= (SDocument) sElementId.getSIdentifiableElement();
			//getting ra id
			Long docRaId= this.sElementId2RaId.get(sElementId);
			if (docRaId!= null)
			{
				{//load resource 
					ResourceSet resourceSet = new ResourceSetImpl();
					resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
					
					if (this.getCorpusDefinition().getCorpusPath()== null)
						throw new RelANNISModuleException("Cannot load corpus structure, because no corpus path is given.");
					
					Resource resource = resourceSet.createResource(this.getCorpusDefinition().getCorpusPath());
					if (resource== null)
						throw new RelANNISModuleException("Cannot load corpus structure, because the given resource is null.");
					
					Map<String, String> optionMap= new Hashtable<String, String>();
					
					optionMap.put("LOADING_TYPE", "DOCUMENT");
					optionMap.put("LOADING_DOCUMENT_NO", docRaId.toString());
					resource.getContents().add(this.raCorpusGraph);
					try {
						resource.load(optionMap);
					} catch (IOException e) 
					{
						throw new RelANNISModuleException("Cannot load corpus structure.", e);
					}
					RACorpus raDocument= null;
											
					{//searching for correct document
						for (RACorpus raCorpus: raCorpusGraph.getRaCorpora())
						{
							if (raCorpus.getRaId().equals(docRaId))
								raDocument= raCorpus;
						}
					}
					if (raDocument== null)
						throw new RelANNISModuleException("Cannot import the given document '"+docRaId+"', because it does not exists in relANNIS Model.");
					if (raDocument.getRaDocumentGraph()==null)
						throw new RelANNISModuleException("Cannot import the given document '"+docRaId+"', because no content has been load.");
					RelANNIS2SaltMapper mapper= new RelANNIS2SaltMapper();
					mapper.mapRACorpus2SDocument(raDocument, sDocument);
				}//load resource
			}
		}//sElementId belongs to SDOcument-object
	}
}
