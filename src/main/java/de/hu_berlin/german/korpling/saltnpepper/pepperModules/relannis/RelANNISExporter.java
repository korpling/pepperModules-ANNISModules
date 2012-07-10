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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISResourceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

@Component(name="RelANNISExporterComponent", factory="PepperExporterComponentFactory")
@Service(value=PepperExporter.class)
public class RelANNISExporter extends PepperExporterImpl implements PepperExporter
{	
	public RelANNISExporter()
	{
		super();

		//setting name of module
		this.name= "RelANNISExporter";
		//set list of formats supported by this module
		this.addSupportedFormat("relANNIS", "3.1", null);
		this.addSupportedFormat("relANNIS", "3.2", null);
	}
	
	/**
	 * Measured total time which is needed to export the whole corpus structure. 
	 */
	private Long totalTimeToExportSCorpusStructure= 0l;
	/**
	 * Measured total time which is needed to import the document corpus structure. 
	 */
	private Long totalTimeToExportSDocumentStructure= 0l;
	/**
	 * Measured time which is needed to save all documents into exmaralda model.. 
	 */
	private Long totalTimeToSaveSDocument= 0l;
	/**
	 * Measured time which is needed to map all documents to salt. 
	 */
	/**
	 * Measured time which is needed to map all documents to salt. 
	 */
	private Long totalTimeToMapSDocument= 0l;
	
	private boolean isPreStarted= false;
	/**
	 * stores the raCorpusGraph-object.
	 */
	private RACorpusGraph raCorpusGraph= null;
	
	private void preStartCorpusStructure()
	{
		SCorpusGraph sCorpGraph= null;
		{//emit correct sCorpus graph object
			if (this.getSaltProject()== null)
				throw new RelANNISModuleException("Cannot start exporting, because no salt project is given.");
			if (this.getSaltProject().getSCorpusGraphs()== null)
				throw new RelANNISModuleException("Cannot start exporting, because there are no corpus graphs in salt project to export.");
			if (this.getSaltProject().getSCorpusGraphs().size() > 1)
				throw new RelANNISModuleException("Cannot work with more than one corpus structure graphs.");
			sCorpGraph= (SCorpusGraph) this.getSaltProject().getSCorpusGraphs().get(0);
		}//emit correct sCorpus graph object
		this.isPreStarted= true;
		this.raCorpusGraph= relANNISFactory.eINSTANCE.createRACorpusGraph();
		Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
		mapper.setLogService(this.getLogService());
		mapper.mapSCorpusGraph2RACorpusGraph(sCorpGraph, this.raCorpusGraph);
		//sets table for relation between SElementId and raId
		this.sElementId2RaId= mapper.getsElementId2RaId();
		
	}
	
	/**
	 * Special params as properties.
	 */
	private Properties options= null;
	
	/**
	 * Stores relation between SElementId and raId. 
	 */
	private Map<SElementId, Long> sElementId2RaId= null;
	
	@Override
	public void start(SElementId sElementId) throws PepperModuleException 
	{
		try
		{
		if (sElementId== null)
			throw new RelANNISModuleException("Cannot export an element with empty element id.");
		if (sElementId.getSIdentifiableElement()== null)
			throw new RelANNISModuleException("Cannot export an element with element id, which does not have an sIdentifiableElement settet.");
		if (this.getCorpusDefinition().getCorpusPath()== null)
			throw new RelANNISModuleException("Cannot export an the element '"+sElementId.getId()+"', because of no corpus path is set.");
		
		if (this.getSpecialParams()!= null)
		{//init options
			File optionsFile= new File(this.getSpecialParams().toFileString());
			if (!optionsFile.exists())
				this.getLogService().log(LogService.LOG_WARNING, "Cannot load special param file at location '"+optionsFile.getAbsolutePath()+"', because it does not exist.");
			else
			{
				this.options= new Properties();
				this.options.load(new FileInputStream(optionsFile));
			}
		}//init options
		
		//start: pre start corpus structure, if it wasn't
			if (!isPreStarted)
				this.preStartCorpusStructure();
		//end: pre start corpus structure, if it wasn't
		if (sElementId.getSIdentifiableElement() instanceof SCorpus)
		{//export corpusStructure
			Long timeToExportSCorpusStructure= System.nanoTime();
			if (this.getLogService()!= null)
				this.getLogService().log(LogService.LOG_DEBUG,this.getName()+" exporting corpus "+ sElementId.getSId());
			
			//start: map the graphs
				SCorpusGraph sCorpGraph= null;
				{//emit correct sCorpus graph object
					if (this.getSaltProject().getSCorpusGraphs().size() > 1)
						throw new RelANNISModuleException("Cannot work with more than one corpus structure graphs.");
					sCorpGraph= (SCorpusGraph) this.getSaltProject().getSCorpusGraphs().get(0);
				}//emit correct sCorpus graph object
				Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
				mapper.setpModuleController(this.getPepperModuleController());
				mapper.setLogService(this.getLogService());
				mapper.mapFinalSCorpusGraph2RACorpusGraph(sCorpGraph, this.raCorpusGraph, this.sElementId2RaId);
			//end: map the graphs
			
			//start: save raCorpusGraph to resource
				// create resource set and resource
				ResourceSet resourceSet = new ResourceSetImpl();
	
				// Register XML resource factory
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
				Resource resource= resourceSet.createResource(this.getCorpusDefinition().getCorpusPath());
				resource.getContents().add(raCorpusGraph);
				Map<String, String> optionMap= new Hashtable<String, String>();
				optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
				if (options!= null)
				{//copy special params to optionMap
					for (Object key: options.keySet())
					{
						optionMap.put(key.toString(), options.getProperty(key.toString()));
					}
				}//copy special params to optionMap
				try {
					resource.save(optionMap);
				} catch (IOException e) 
				{
					throw new RelANNISModuleException("Cannot save corpus structure of element '"+sElementId.getSId()+"' to resource.", e);
				}
			//end: save raCorpusGraph to resource
			this.totalTimeToExportSCorpusStructure= this.totalTimeToExportSCorpusStructure +(System.nanoTime() - timeToExportSCorpusStructure);
		}//export corpusStructure
		else if (sElementId.getSIdentifiableElement() instanceof SDocument)
		{//export documentStructure
			Long timeToExportDocument= System.nanoTime();
			if (this.getLogService()!= null)
				this.getLogService().log(LogService.LOG_DEBUG,this.getName()+" exporting document "+ sElementId.getSId());
			if (((SDocument)sElementId.getSIdentifiableElement()).getSDocumentGraph()!= null)
			{//only export if document graph isn't null	
				if (this.sElementId2RaId!= null)
				{
					//getting ra id
					Long docRaId= this.sElementId2RaId.get(sElementId);
					if (docRaId!= null)	
					{	
						RADocumentGraph raDocGraph= null;
						SDocumentGraph sDocGraph= null;
						//start: set both graphs
							sDocGraph= ((SDocument)sElementId.getSIdentifiableElement()).getSDocumentGraph();
							raDocGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
						//end: set both graphs
						//start: map the graphs
							Long timeToMapSDocument= System.nanoTime();
							Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
							if (sDocument2Mapper== null)
								sDocument2Mapper= Collections.synchronizedMap(new HashMap<SElementId, Salt2RelANNISMapper>());
							sDocument2Mapper.put(sElementId, mapper);
							mapper.setLogService(this.getLogService());
							mapper.mapSDocumentGraph2RADocumentGraph(sDocGraph, raDocGraph);
							//start: adding documentgraph to document
								RACorpus raDocument= null;
								for (RACorpus raCorpus: this.raCorpusGraph.getRaCorpora())
								{
									if (raCorpus.getRaId().equals(docRaId))
										raDocument= raCorpus;
								}
								raDocument.setRaDocumentGraph(raDocGraph);
							//end: adding document graph to docuement
							//start: cleaning up
								this.sDocument2Mapper.remove(sElementId);
								mapper= null;
							//end: cleaning up	
							this.totalTimeToMapSDocument= this.totalTimeToMapSDocument + (System.nanoTime() - timeToMapSDocument);
						//end: map the graphs
						//start: save document graph to resource
							Long timeToSaveSDocument= System.nanoTime();
							// create resource set and resource
							ResourceSet resourceSet = new ResourceSetImpl();
				
							// Register XML resource factory
							resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
							Resource resource= resourceSet.createResource(this.getCorpusDefinition().getCorpusPath());
							resource.getContents().add(raCorpusGraph);
							Map<String, String> optionMap= new Hashtable<String, String>();
							optionMap.put("SAVING_TYPE", "DOCUMENT");
							optionMap.put("SAVING_DOCUMENT_NO", docRaId.toString());
							if (options!= null)
							{//copy special params to optionMap
								for (Object key: options.keySet())
								{
									optionMap.put(key.toString(), options.getProperty(key.toString()));
								}
							}//copy special params to optionMap
							
							String relannisVersion= "3.1";
							if (	(this.getCorpusDefinition()!= null)&&
									(this.getCorpusDefinition().getFormatDefinition()!= null)&&
									(this.getCorpusDefinition().getFormatDefinition().getFormatVersion()!= null)&&
									(!this.getCorpusDefinition().getFormatDefinition().getFormatVersion().isEmpty()))
							{
								relannisVersion= this.getCorpusDefinition().getFormatDefinition().getFormatVersion();
							}
							optionMap.put(RelANNISResourceFactory.RELANNIS_VERSION, relannisVersion);
							
							try {
								resource.save(optionMap);
							} catch (IOException e) 
							{
								throw new RelANNISModuleException("Cannot save document structure of element '"+sElementId.getSId()+"' to resource.", e);
							}
							finally
							{
								this.totalTimeToSaveSDocument= this.totalTimeToSaveSDocument + (System.nanoTime() - timeToSaveSDocument); 
							}
						//end: save document graph to resource
						//start: remove the raDocumentgraph
							raDocGraph.getRaCorpus().setRaDocumentGraph(null);
						//end: remove the raDocumentgraph
					}
				}
			}//only export if document graph isn't null
			this.totalTimeToExportSDocumentStructure= this.totalTimeToExportSDocumentStructure + (System.nanoTime() - timeToExportDocument);
		}//export documentStructure
		else 
		{
			throw new RelANNISModuleException("Cannot export the following elment with id '"+sElementId.getSId()+"', because it isn't of Type SCorpus or SDocument: "+ sElementId.getSIdentifiableElement());
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RelANNISModuleException("",e);
		}
	}
	
	/**
	 * A Hashmap representing the correspondence between a {@link SDocument} object given by its {@link SElementId} object and 
	 * the {@link Salt2RelANNISMapper} object processing the {@link SDocument} object.
	 */
	private Map<SElementId, Salt2RelANNISMapper> sDocument2Mapper= null;
	
	/**
	 * Returns the progress of the {@link Salt2RelANNISMapper} processing the {@link SDocument} represented by the given 
	 * {@link SElementId} object.
	 * @param sDocumentId identifier for the {@link SDocument}. 
	 */
	@Override
	public Double getProgress(SElementId sDocumentId)
	{
		Double retVal= null;
		Salt2RelANNISMapper mapper= this.sDocument2Mapper.get(sDocumentId);
		if (mapper!= null)
			retVal= mapper.getProgress();
		return(retVal);
	}
	
	@Override
	public void end()
	{
		super.end();
		if (this.getLogService()!= null)
		{	
			StringBuffer msg= new StringBuffer();
			msg.append("needed time of "+this.getName()+":\n");
			msg.append("\t time to export whole corpus-structure:\t\t\t\t"+ this.totalTimeToExportSCorpusStructure / 1000000+"\n");
			msg.append("\t total time to export whole document structure:\t\t"+ totalTimeToExportSDocumentStructure / 1000000+"\n");
			msg.append("\t total time to save all document-structures:\t\t"+ totalTimeToSaveSDocument / 1000000+"\n");
			msg.append("\t total time to map all document-structure from salt:\t"+ totalTimeToMapSDocument / 1000000+"\n");
			this.getLogService().log(LogService.LOG_DEBUG, msg.toString());
		}
	}
}
