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

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
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
		this.addSupportedFormat("relANNIS", "4.0", null);
		
		tupleWriterCorpus = TupleConnectorFactory.fINSTANCE.createTupleWriter();
		tupleWriterNode = TupleConnectorFactory.fINSTANCE.createTupleWriter();
		tupleWriterNodeAnnotation = TupleConnectorFactory.fINSTANCE.createTupleWriter();
		
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
	//private RACorpusGraph raCorpusGraph= null;
	
	// ------------------------- TupleConector
	private TupleWriter tupleWriterCorpus;
	private TupleWriter tupleWriterNode;
	private TupleWriter tupleWriterNodeAnnotation;
	
	public TupleWriter getCorpusTabTupleWriter(){
		return this.tupleWriterCorpus;
	}
	
	public TupleWriter getNodeTabTupleWriter(){
		return this.tupleWriterNode;
	}
	
	public TupleWriter getNodeAnnotationTabTupleWriter(){
		return this.tupleWriterNodeAnnotation;
	}

	private IdManager idManager;
	
	public IdManager getIdManager(){
		return this.idManager;
	}
	
	/**
	 * This method sets all information which is needed while exporting the 
	 * SCorpus.
	 * For example, the tupleWriters need to be initialized.
	 */
	private void preStart()
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
		//this.raCorpusGraph= relANNISFactory.eINSTANCE.createRACorpusGraph();
		Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
		mapper.setLogService(this.getLogService());
		
		/**
		 * set the tuple writer output files
		 */
		String corpusOutputPath = this.getCorpusDefinition().getCorpusPath().toFileString();
		// create the corpus tab file
		String corpusTabFileName = corpusOutputPath+"/corpus.tab";
		File corpusTabFile = new File(corpusTabFileName);
		if (corpusTabFile.exists()){
			
		} else {
			try {
				corpusTabFile.createNewFile();
			} catch (IOException e) {
				throw new RelANNISModuleException("Could not create the corpus tab file "+ corpusTabFileName+ " Exception:"+e.getMessage());
			}
		}
		tupleWriterCorpus.setFile(corpusTabFile);
		
		// create the node tab file
		String nodeTabFileName = corpusOutputPath+"/node.tab";
		File nodeTabFile = new File(nodeTabFileName);
		if (nodeTabFile.exists()){
			
		} else {
			try {
				nodeTabFile.createNewFile();
			} catch (IOException e) {
				throw new RelANNISModuleException("Could not create the node tab file "+ nodeTabFileName+ " Exception:"+e.getMessage());
			}
		}
		tupleWriterNode.setFile(nodeTabFile);
		
		// create the node annotation file
		String nodeAnnotationFileName = corpusOutputPath+"node_annotation.tab";
		File nodeAnnotationFile = new File(nodeAnnotationFileName);
		if (nodeAnnotationFile.exists()){
			
		} else {
			try {
				nodeAnnotationFile.createNewFile();
			} catch (IOException e) {
				throw new RelANNISModuleException("Could not create the node_annotation tab file "+ nodeAnnotationFileName+ " Exception:"+e.getMessage());
			}
		}
		tupleWriterNodeAnnotation.setFile(nodeAnnotationFile);
		
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
		
		//start: pre start, if it wasn't
			if (!isPreStarted)
				this.preStart();
		//end: pre start, if it wasn't
			
		/**
		 * if the given node is a corpus,
		 * export it with a new mapper.
		 */
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
				/**
				 * create a new mapper instance and start the corpus 
				 * export.
				 */
				Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
				mapper.setpModuleController(this.getPepperModuleController());
				mapper.setLogService(this.getLogService());
				
				mapper.mapSCorpusGraph(sCorpGraph);
			//end: map the graphs
			this.totalTimeToExportSCorpusStructure= this.totalTimeToExportSCorpusStructure +(System.nanoTime() - timeToExportSCorpusStructure);
		}//export corpusStructure
		/**
		 * The given node is a document.
		 * Create a new mapper instance for it and export the SDocument.
		 */
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
						SDocumentGraph sDocGraph= null;
						//start: set both graphs
							sDocGraph= ((SDocument)sElementId.getSIdentifiableElement()).getSDocumentGraph();
						//end: set both graphs
						//start: map the graphs
							Long timeToMapSDocument= System.nanoTime();
							Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
							if (sDocument2Mapper== null)
								sDocument2Mapper= Collections.synchronizedMap(new HashMap<SElementId, Salt2RelANNISMapper>());
							sDocument2Mapper.put(sElementId, mapper);
							mapper.setLogService(this.getLogService());
							
							mapper.mapSDocumentGraph(sDocGraph);
							//mapper.mapSDocumentGraph2RADocumentGraph(sDocGraph, raDocGraph);
							//start: cleaning up
								this.sDocument2Mapper.remove(sElementId);
								mapper= null;
							//end: cleaning up	
							this.totalTimeToMapSDocument= this.totalTimeToMapSDocument + (System.nanoTime() - timeToMapSDocument);
						//end: map the graphs
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
