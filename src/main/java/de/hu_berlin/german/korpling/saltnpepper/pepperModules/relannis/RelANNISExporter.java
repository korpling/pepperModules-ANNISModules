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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.log.LogService;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


@Component(name="RelANNISExporterComponent", factory="PepperExporterComponentFactory")
public class RelANNISExporter extends PepperExporterImpl implements PepperExporter
{
	// =================================================== mandatory ===================================================
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * A constructor for your module. Set the coordinates, with which your module shall be registered. 
		 * The coordinates (modules name, version and supported formats) are a kind of a fingerprint, 
		 * which should make your module unique.
		 */
		public RelANNISExporter()
		{
			super();
			this.name= "RelANNISExporter";
			//TODO change the version of your module, we recommend to synchronize this value with the maven version in your pom.xml
			this.setVersion("1.0.0");
			this.addSupportedFormat("RelANNIS", "4.0", null); 
			tupleWriterCorpus = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tupleWriterNode = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tupleWriterNodeAnnotation = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tupleWriterText = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			this.idManager = new IdManager();
		}
		
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * This method creates a customized {@link PepperMapper} object and returns it. You can here do some additional initialisations. 
		 * Things like setting the {@link SElementId} of the {@link SDocument} or {@link SCorpus} object and the {@link URI} resource is done
		 * by the framework (or more in detail in method {@link #start()}).  
		 * The parameter <code>sElementId</code>, if a {@link PepperMapper} object should be created in case of the object to map is either 
		 * an {@link SDocument} object or an {@link SCorpus} object of the mapper should be initialized differently. 
		 * <br/>
		 * 
		 * @param sElementId {@link SElementId} of the {@link SCorpus} or {@link SDocument} to be processed. 
		 * @return {@link PepperMapper} object to do the mapping task for object connected to given {@link SElementId}
		 */
		public PepperMapper createPepperMapper(SElementId sElementId)
		{
			Salt2RelANNISMapper mapper  = new Salt2RelANNISMapper();
			mapper.setRelANNISExporter(this);
			return mapper;
			
		}
		
	// =================================================== optional ===================================================	
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * This method is called by the pepper framework after initializing this object and directly before start processing. 
		 * Initializing means setting properties {@link PepperModuleProperties}, setting temporary files, resources etc. .
		 * returns false or throws an exception in case of {@link PepperModule} instance is not ready for any reason.
		 * @return false, {@link PepperModule} instance is not ready for any reason, true, else.
		 */
		@Override
		public boolean isReadyToStart() throws PepperModuleNotReadyException
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
			
			//this.raCorpusGraph= relANNISFactory.eINSTANCE.createRACorpusGraph();
			//Salt2RelANNISMapper mapper= new Salt2RelANNISMapper();
			//mapper.setLogService(this.getLogService());
			
			/**
			 * set the tuple writer output files
			 */
			String corpusOutputPath = this.getCorpusDefinition().getCorpusPath().toFileString();
			//System.out.println("Corpus output path is: "+ corpusOutputPath);
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
			
			String textTabFileName = corpusOutputPath+"/text.tab";
			File textTabFile = new File(textTabFileName);
			if (textTabFile.exists()){
				
			} else {
				try {
					textTabFile.createNewFile();
				} catch (IOException e) {
					throw new RelANNISModuleException("Could not create the corpus tab file "+ textTabFileName+ " Exception:"+e.getMessage());
				}
			}
			tupleWriterText.setFile(textTabFile);
			
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
			String nodeAnnotationFileName = corpusOutputPath+"/node_annotation.tab";
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
			
			this.idManager = new IdManager();
			
			return(super.isReadyToStart());
		}
		
		/**
		 * <strong>OVERRIDE THIS METHOD FOR CUSTOMIZATION</strong>
		 * 
		 * This method is called by {@link #start()} to export the corpus-structure. This method than can create the 
		 * folder-structure to store the document-structure into it, if necessary. 
		 * 
		 * @param corpusGraph {@link SCorpusGraph} object to be exported
		 */
		@Override
		public void exportCorpusStructure(SCorpusGraph corpusGraph) throws PepperModuleException
		{
			//TODO remove the following line of code for adoption
			super.exportCorpusStructure(sCorpusGraph);
		}

		/**
		 *       All data structures go below this comment!
		 **/
		// ------------------------- TupleConector
		private TupleWriter tupleWriterCorpus;
		private TupleWriter tupleWriterNode;
		private TupleWriter tupleWriterNodeAnnotation;
		private TupleWriter tupleWriterText;
		// ------------------------- IdManager
		private IdManager idManager;
		
		/**
		 *       All GETTERS go below this comment
		 */
		// ------------------------- TupleConector
		public TupleWriter getCorpusTabTupleWriter(){
			return this.tupleWriterCorpus;
		}
		
		public TupleWriter getNodeTabTupleWriter(){
			return this.tupleWriterNode;
		}
		
		public TupleWriter getNodeAnnotationTabTupleWriter(){
			return this.tupleWriterNodeAnnotation;
		}

		public TupleWriter getTextTabTupleWriter(){
			return this.tupleWriterText;
		}
		
		// ------------------------- IdManager
		
		public IdManager getIdManager(){
			return this.idManager;
		}

}
