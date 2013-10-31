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
import java.io.IOException;

import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;


@Component(name="RelANNISExporterComponent", factory="PepperExporterComponentFactory")
public class RelANNISExporter extends PepperExporterImpl implements PepperExporter
{
	// =================================================== mandatory ===================================================
		public RelANNISExporter()
		{
			super();
			this.name= "RelANNISExporter";
			this.setVersion("1.0.0");
			this.addSupportedFormat("RelANNIS", "3.1", null);
			this.addSupportedFormat("RelANNIS", "4.0", null); 
			tupleWriterCorpus = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tupleWriterNode = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tupleWriterNodeAnnotation = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tupleWriterText = TupleConnectorFactory.fINSTANCE.createTupleWriter();
			this.idManager = new IdManager();
		}
		
		/**
		 * Creates a {@link Salt2RelANNISMapper} object and passes this object, so that all {@link Salt2RelANNISMapper} object
		 * can access the {@link IdManager} etc..
		 */
		public PepperMapper createPepperMapper(SElementId sElementId)
		{
			Salt2RelANNISMapper mapper  = new Salt2RelANNISMapper();
			mapper.setRelANNISExporter(this);
			return mapper;
			
		}
		
	// =================================================== optional ===================================================	
		/** Name of the file, to store the corpus-structure. **/
		public static final String FILE_CORPUS="corpus.tab";
		/** Name of the file, to store the meta-data of the corpus-structure. **/
		public static final String FILE_CORPUS_META="corpus_annotation.tab";
		/** Name of the file, to store the primary text data. **/
		public static final String FILE_TEXT="text.tab";
		/** Name of the file, to store all {@link SNode}s. **/
		public static final String FILE_NODE="node.tab";
		/** Name of the file, to store the annotations odf the nodes. **/
		public static final String FILE_NODE_ANNO="node_annotation.tab";
		/** Name of the file, to store the relations. **/
		public static final String FILE_RANK="rank.tab";
		/** Name of the file, to store the annotations of the relations. **/
		public static final String FILE_EDGE_ANNO="edge_annotation.tab";
		/** Name of the file, to store the components of the relations. **/
		public static final String FILE_COMPONENT="component.tab";
		/** Name of the file, to store the visualization configuration. **/
		public static final String FILE_VISUALIZATION="resolver_vis_map.tab";
		
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
			//set the tuple writer output files
			String corpusOutputPath = this.getCorpusDefinition().getCorpusPath().toFileString();
			//System.out.println("Corpus output path is: "+ corpusOutputPath);
			// create the corpus tab file
			String corpusTabFileName = corpusOutputPath+"/"+FILE_CORPUS;
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
			
			String textTabFileName = corpusOutputPath+"/"+FILE_TEXT;
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
			String nodeTabFileName = corpusOutputPath+"/"+FILE_NODE;
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
			String nodeAnnotationFileName = corpusOutputPath+"/"+FILE_NODE_ANNO;
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
