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
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;


@Component(name="RelANNISExporterComponent", factory="PepperExporterComponentFactory")
public class RelANNISExporter extends PepperExporterImpl implements PepperExporter, RelANNIS
{
	// =================================================== mandatory ===================================================
		public RelANNISExporter()
		{
			super();
			this.name= "RelANNISExporter";
			this.setVersion("1.0.0");
			this.addSupportedFormat("RelANNIS", "3.1", null);
			this.addSupportedFormat("RelANNIS", "3.2", null);
			this.addSupportedFormat("RelANNIS", "4.0", null); 
		}
		
		/**
		 * Creates a {@link Salt2RelANNISMapper} object and passes this object, so that all {@link Salt2RelANNISMapper} object
		 * can access the {@link IdManager} etc..
		 */
		public PepperMapper createPepperMapper(SElementId sElementId)
		{
			Salt2RelANNISMapper mapper  = new Salt2RelANNISMapper();
			mapper.setIdManager(getIdManager());
			mapper.tw_text= tw_text;
			mapper.tw_node= tw_node;
			mapper.tw_nodeAnno= tw_nodeAnno;
			mapper.tw_rank= tw_rank;
			mapper.tw_edgeAnno= tw_edgeAnno;
			mapper.tw_component= tw_component;
			mapper.tw_corpus= tw_corpus;
			mapper.tw_corpusMeta= tw_corpusMeta;
			
			return mapper;
			
		}
		
	// =================================================== optional ===================================================	
		/**
		 * Creates a {@link TupleWriter} responsible for the given file.
		 * @param outFile
		 * @return
		 */
		public static synchronized TupleWriter createTupleWRiter(File outFile)
		{
			if (!outFile.getParentFile().exists())
				outFile.getParentFile().mkdirs();
			if (!outFile.exists()){
				try {
					outFile.createNewFile();
				} catch (IOException e) {
					throw new RelANNISModuleException("Could not create the corpus tab file "+ outFile.getAbsolutePath()+ " Exception:"+e.getMessage());
				}
			}
			TupleWriter tWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
			tWriter.setFile(outFile);
			return(tWriter);
		}
		
		/**
		 * Initializes all {@link TupleWriter} objects.
		 */
		@Override
		public boolean isReadyToStart() throws PepperModuleNotReadyException
		{
			tw_text= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_CORPUS));
			tw_node= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_NODE));
			tw_nodeAnno= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_NODE_ANNO));
			tw_rank= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_RANK));
			tw_edgeAnno= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_EDGE_ANNO));
			tw_component= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_COMPONENT));
			tw_corpus= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_CORPUS));
			tw_corpusMeta= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_CORPUS_META));
			
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

		/** tuple writer to write {@link RelANNIS#FILE_TEXT} **/
		public TupleWriter tw_text= null;
		/** tuple writer to write {@link RelANNIS#FILE_NODE} **/
		public TupleWriter tw_node= null;
		/** tuple writer to write {@link RelANNIS#FILE_NODE_ANNO} **/
		public TupleWriter tw_nodeAnno= null;
		/** tuple writer to write {@link RelANNIS#FILE_RANK} **/
		public TupleWriter tw_rank= null;
		/** tuple writer to write {@link RelANNIS#FILE_EDGE_ANNO} **/
		public TupleWriter tw_edgeAnno= null;
		/** tuple writer to write {@link RelANNIS#FILE_COMPONENT} **/
		public TupleWriter tw_component= null;
		/** tuple writer to write {@link RelANNIS#FILE_CORPUS} **/
		public TupleWriter tw_corpus= null;
		/** tuple writer to write {@link RelANNIS#FILE_CORPUS_META} **/
		public TupleWriter tw_corpusMeta= null;
		// ------------------------- IdManager
		/** object to manage relANNIS ids**/
		private IdManager idManager;
		/** returns singleton object to manage relANNIS ids**/
		public IdManager getIdManager(){
			return this.idManager;
		}

}
