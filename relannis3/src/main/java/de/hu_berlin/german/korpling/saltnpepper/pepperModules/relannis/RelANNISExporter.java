/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISProperties;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISResourceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.SaltFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

@Component(name="RelANNISExporterComponent", factory="PepperExporterComponentFactory")
public class RelANNISExporter extends PepperExporterImpl implements PepperExporter
{	
	private static final String MODULE_NAME= "RelANNISExporter";
	private static final Logger logger= LoggerFactory.getLogger(MODULE_NAME);
	public RelANNISExporter()
	{
		super();

		//setting name of module
		setName(MODULE_NAME);
		//set list of formats supported by this module
		this.addSupportedFormat("relANNIS", "3.1", null);
		this.addSupportedFormat("relANNIS", "3.2", null);
		
		setProperties(new PepperModuleProperties());
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
				throw new PepperModuleException(this, "Cannot start exporting, because no salt project is given.");
			if (this.getSaltProject().getSCorpusGraphs()== null)
				throw new PepperModuleException(this, "Cannot start exporting, because there are no corpus graphs in salt project to export.");
			if (this.getSaltProject().getSCorpusGraphs().size() > 1){
				logger.warn("Cannot work with more than one corpus structure graphs. Only the first will be processed, the rest will be ignored. ");
			}
			sCorpGraph= (SCorpusGraph) this.getSaltProject().getSCorpusGraphs().get(0);
		}//emit correct sCorpus graph object
		this.isPreStarted= true;
		this.raCorpusGraph= relANNISFactory.eINSTANCE.createRACorpusGraph();
		Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
		mapper.mapSCorpusGraph2RACorpusGraph(sCorpGraph, this.raCorpusGraph);
		//sets table for relation between SElementId and raId
		this.sElementId2RaId= mapper.getsElementId2RaId();
		
	}
	
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
			throw new PepperModuleException(this, "Cannot export an element with empty element id.");
		if (sElementId.getSIdentifiableElement()== null)
			throw new PepperModuleException(this, "Cannot export an element with element id, which does not have an sIdentifiableElement settet.");
		if (this.getCorpusDesc().getCorpusPath()== null)
			throw new PepperModuleException(this, "Cannot export an the element '"+sElementId.getId()+"', because of no corpus path is set.");
		
		//start: pre start corpus structure, if it wasn't
			if (!isPreStarted)
				this.preStartCorpusStructure();
		//end: pre start corpus structure, if it wasn't
		if (sElementId.getSIdentifiableElement() instanceof SDocument)
		{//export documentStructure
			Long timeToExportDocument= System.nanoTime();
			logger.debug(this.getName()+" exporting document "+ sElementId.getSId());
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
							getSDocument2MapperTable().put(SaltFactory.eINSTANCE.getGlobalId(sElementId), mapper);
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
								getSDocument2MapperTable().remove(SaltFactory.eINSTANCE.getGlobalId(sElementId));
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
							Resource resource= resourceSet.createResource(this.getCorpusDesc().getCorpusPath());
							resource.getContents().add(raCorpusGraph);
							Map<String, String> optionMap= new Hashtable<String, String>();
							optionMap.put("SAVING_TYPE", "DOCUMENT");
							optionMap.put("SAVING_DOCUMENT_NO", docRaId.toString());
							if (getProperties()!= null)
							{//copy special params to optionMap
								for (Object key: getProperties().getProperties().keySet())
								{
									optionMap.put(key.toString(), getProperties().getProperties().getProperty(key.toString()));
								}
							}//copy special params to optionMap
							
							String relannisVersion= "3.1";
							if (	(this.getCorpusDesc()!= null)&&
									(this.getCorpusDesc().getFormatDesc()!= null)&&
									(this.getCorpusDesc().getFormatDesc().getFormatVersion()!= null)&&
									(!this.getCorpusDesc().getFormatDesc().getFormatVersion().isEmpty()))
							{
								relannisVersion= this.getCorpusDesc().getFormatDesc().getFormatVersion();
							}
							optionMap.put(RelANNISResourceFactory.RELANNIS_VERSION, relannisVersion);
							
							try {
								resource.save(optionMap);
							} catch (IOException e) 
							{
								throw new PepperModuleException(this, "Cannot save document structure of element '"+sElementId.getSId()+"' to resource.", e);
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
		}
		catch (Exception e) {
			if (e instanceof PepperModuleException){
				throw (PepperModuleException) e;
			}else{
				throw new PepperModuleException(this, "",e);
			}
		}
	}
	
	/**
	 * A Hashmap representing the correspondence between a {@link SDocument} object given by its {@link SElementId} object and 
	 * the {@link Salt2RelANNISMapper} object processing the {@link SDocument} object.
	 */
	private Map<String, Salt2RelANNISMapper> sDocument2Mapper= null;
	private Map<String, Salt2RelANNISMapper> getSDocument2MapperTable(){
		if (sDocument2Mapper== null){
			sDocument2Mapper= Collections.synchronizedMap(new HashMap<String, Salt2RelANNISMapper>()); 
		}
		return(sDocument2Mapper);
	}
	
	@Override
	public Double getProgress(String globalId) {
		Double retVal= null;
		Salt2RelANNISMapper mapper= getSDocument2MapperTable().get(globalId);
		if (mapper!= null)
			retVal= mapper.getProgress();
		return(retVal);
	}
	
	@Override
	public void end()
	{
		logger.debug("[{}] start export of corpus structure...",MODULE_NAME);
		super.end();
		
		Long timeToExportSCorpusStructure= System.nanoTime();
		
		//start: map the graphs
			SCorpusGraph sCorpGraph= null;
				if (this.getSaltProject().getSCorpusGraphs().size() > 1){
					logger.warn("Cannot work with more than one corpus structure graphs. Only the first will be processed, the rest will be ignored. ");
				}
				sCorpGraph= (SCorpusGraph) this.getSaltProject().getSCorpusGraphs().get(0);
			
			if (	(sCorpGraph!= null)&&
					(sCorpGraph.getSCorpora().size()>0))
			{//only, if corpus graph is not empty
				Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
				mapper.setpModuleController(this.getModuleController());
				mapper.mapFinalSCorpusGraph2RACorpusGraph(sCorpGraph, this.raCorpusGraph, this.sElementId2RaId);
			
			//start: save raCorpusGraph to resource
				// create resource set and resource
				ResourceSet resourceSet = new ResourceSetImpl();
	
				// Register XML resource factory
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
				Resource resource= resourceSet.createResource(this.getCorpusDesc().getCorpusPath());
				resource.getContents().add(raCorpusGraph);
				Map<String, String> optionMap= new Hashtable<String, String>();
				optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
				if (	(getProperties()!= null)&&
						(getProperties().getProperties()!= null))
				{//copy special params to optionMap
					for (Object key: getProperties().getProperties().keySet())
					{
						optionMap.put(key.toString(), getProperties().getProperties().getProperty(key.toString()));
					}
				}//copy special params to optionMap
				try {
					resource.save(optionMap);
				} catch (IOException e) 
				{
					throw new PepperModuleException(this, "Cannot save corpus structure to resource.", e);
				}
			//end: save raCorpusGraph to resource
			this.totalTimeToExportSCorpusStructure= this.totalTimeToExportSCorpusStructure +(System.nanoTime() - timeToExportSCorpusStructure);
			
			StringBuffer msg= new StringBuffer();
			msg.append("needed time of "+this.getName()+":\n");
			msg.append("\t time to export whole corpus-structure:\t\t\t\t"+ this.totalTimeToExportSCorpusStructure / 1000000+"\n");
			msg.append("\t total time to export whole document structure:\t\t"+ totalTimeToExportSDocumentStructure / 1000000+"\n");
			msg.append("\t total time to save all document-structures:\t\t"+ totalTimeToSaveSDocument / 1000000+"\n");
			msg.append("\t total time to map all document-structure from salt:\t"+ totalTimeToMapSDocument / 1000000+"\n");
			logger.debug(msg.toString());
		}
	}
}
