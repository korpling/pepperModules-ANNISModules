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
package de.hub.corpling.pepper.modules.relannis;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISResourceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.FormatDefinition;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperInterfaceFactory;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hub.corpling.pepper.modules.relannis.exceptions.RelANNISModuleException;

@Component(name="RelANNISExporterComponent", factory="PepperExporterComponentFactory")
@Service(value=PepperExporter.class)
public class RelANNISExporter extends PepperExporterImpl implements PepperExporter
{
	public RelANNISExporter()
	{
		super();
		this.name= "RelANNISExporter";
		//for testing the symbolic name has to be set without osgi
		if (	(this.getSymbolicName()==  null) ||
				(this.getSymbolicName().equalsIgnoreCase("")))
			this.setSymbolicName("de.hu_berlin.german.korpling.saltnpepper.pepperModules.RelANNISModules");
		this.init();
		if (this.getLogService()!= null)
			this.getLogService().log(LogService.LOG_DEBUG,this.getName()+" is created...");
	}

	protected void init()
	{
		this.supportedFormats= new BasicEList<FormatDefinition>();
		FormatDefinition formatDef= PepperInterfaceFactory.eINSTANCE.createFormatDefinition();
		formatDef.setFormatName("relANNIS");
		formatDef.setFormatVersion("3.1");
		this.supportedFormats.add(formatDef);
	}

	protected void activate(ComponentContext componentContext) 
	{
		this.setSymbolicName(componentContext.getBundleContext().getBundle().getSymbolicName());
		if (this.getLogService()!= null)
			this.getLogService().log(LogService.LOG_DEBUG,this.getName()+" is activated...");
	}

	/**
	 * Wird von der Service Component Runtime vor der Deaktivierung der Komponente
	 * aufgerufen und gibt noch eine Abschiedsbotschaft aus
	 * 
	 * @param componentContext
	 *          Der Kontext der Komponente
	 */
	protected void deactivate(ComponentContext componentContext) {
		if (this.getLogService()!= null)
			this.getLogService().log(LogService.LOG_DEBUG,this.getName()+" is deactivated...");

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
	 * Stores relation between SElementId and raId. 
	 */
	private Map<SElementId, Long> sElementId2RaId= null;
	
	@Override
	public void start(SElementId sElementId) throws PepperModuleException 
	{
		if (sElementId== null)
			throw new RelANNISModuleException("Cannot export an element with empty element id.");
		if (sElementId.getSIdentifiableElement()== null)
			throw new RelANNISModuleException("Cannot export an element with element id, which does not have an sIdentifiableElement settet.");
		if (this.getCorpusDefinition().getCorpusPath()== null)
			throw new RelANNISModuleException("Cannot export an the element '"+sElementId.getId()+"', because of no corpus path is set.");
		
		{//pre start corpus structure, if it wasn't
			if (!isPreStarted)
				this.preStartCorpusStructure();
		}//pre start corpus structure, if it wasn't
		if (sElementId.getSIdentifiableElement() instanceof SCorpus)
		{//export corpusStructure
			Long timeToExportSCorpusStructure= System.nanoTime();
			if (this.getLogService()!= null)
				this.getLogService().log(LogService.LOG_DEBUG,this.getName()+" exporting corpus "+ sElementId.getSId());
			
			{//map the graphs
				SCorpusGraph sCorpGraph= null;
				{//emit correct sCorpus graph object
					if (this.getSaltProject().getSCorpusGraphs().size() > 1)
						throw new RelANNISModuleException("Cannot work with more than one corpus structure graphs.");
					sCorpGraph= (SCorpusGraph) this.getSaltProject().getSCorpusGraphs().get(0);
				}//emit correct sCorpus graph object
				Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
				mapper.setLogService(this.getLogService());
				mapper.mapFinalSCorpusGraph2RACorpusGraph(sCorpGraph, this.raCorpusGraph, this.sElementId2RaId);
			}//map the graphs
			
			{//save raCorpusGraph to resource
				// create resource set and resource
				ResourceSet resourceSet = new ResourceSetImpl();
	
				// Register XML resource factory
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(null, new RelANNISResourceFactory());
				Resource resource= resourceSet.createResource(this.getCorpusDefinition().getCorpusPath());
				resource.getContents().add(raCorpusGraph);
				Map<String, String> optionMap= new Hashtable<String, String>();
				optionMap.put("SAVING_TYPE", "CORPUS_STRUCTURE");
				try {
					resource.save(optionMap);
				} catch (IOException e) 
				{
					throw new RelANNISModuleException("Cannot save corpus structure of element '"+sElementId.getSId()+"' to resource.", e);
				}
			}//save raCorpusGraph to resource
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
						{//set both graphs
							sDocGraph= ((SDocument)sElementId.getSIdentifiableElement()).getSDocumentGraph();
							raDocGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
						}//set both graphs
						{//map the graphs
							Long timeToMapSDocument= System.nanoTime();
							Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
							mapper.setLogService(this.getLogService());
							mapper.mapSDocumentGraph2RADocumentGraph(sDocGraph, raDocGraph);
							{//adding documentgraph to document
								RACorpus raDocument= null;
								for (RACorpus raCorpus: this.raCorpusGraph.getRaCorpora())
								{
									if (raCorpus.getRaId().equals(docRaId))
										raDocument= raCorpus;
								}
								raDocument.setRaDocumentGraph(raDocGraph);
							}//adding document graph to docuement
							//cleaning up
							mapper= null;
							this.totalTimeToMapSDocument= this.totalTimeToMapSDocument + (System.nanoTime() - timeToMapSDocument);
						}//map the graphs
						{//save document graph to resource
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
						}//save document graph to resource
						{//remove the raDocumentgraph
							raDocGraph.getRaCorpus().setRaDocumentGraph(null);
						}//remove the raDocumentgraph
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
