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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperExceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperty;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.exceptions.RelANNISModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;


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
			mapper.individualCorpusName= this.individualCorpusName;
			
			return mapper;
			
		}
		
	// =================================================== optional ===================================================	
		/**
		 * Creates a {@link TupleWriter} responsible for the given file.
		 * @param outFile
		 * @param escapeCharacters states whether characters should be escaped
		 * @param characterEscapeTable contains the character-escape-mapping
		 * @return
		 */
		public static synchronized TupleWriter createTupleWRiter(File outFile,boolean escapeCharacters,Hashtable<Character,String> characterEscapeTable)
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
			
			tWriter.setEscaping(escapeCharacters);
			if (characterEscapeTable != null){
				tWriter.setEscapeTable(characterEscapeTable);
			}
			
			tWriter.setFile(outFile);
			return(tWriter);
		}
		
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
			if (this.getProperties() != null){
				overwriteResolverVisMap = ((RelANNISExporterProperties)this.getProperties()).getClobberResolverVisMap();
				overwriteCorpusAnnotations = ((RelANNISExporterProperties)this.getProperties()).getClobberCorpusAnnotations();
				String individualCorpusName_tmp = ((RelANNISExporterProperties)this.getProperties()).getIndividualCorpusName();
				
				// remove leading and trailing whitespaces of the individual corpus name, if it is set.
				if (individualCorpusName_tmp != null){
					this.individualCorpusName = individualCorpusName_tmp.trim();
				}
				
				this.escapeCharacters = ((RelANNISExporterProperties)this.getProperties()).getEscapeCharacters();
				if (this.escapeCharacters){
					this.characterEscapeTable = ((RelANNISExporterProperties)this.getProperties()).getEscapeCharactersSet();
				}
			}
			
			tw_text= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_CORPUS),this.escapeCharacters,this.characterEscapeTable);
			tw_node= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_NODE),this.escapeCharacters,this.characterEscapeTable);
			tw_nodeAnno= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_NODE_ANNO),this.escapeCharacters,this.characterEscapeTable);
			tw_rank= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_RANK),this.escapeCharacters,this.characterEscapeTable);
			tw_edgeAnno= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_EDGE_ANNO),this.escapeCharacters,this.characterEscapeTable);
			tw_component= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_COMPONENT),this.escapeCharacters,this.characterEscapeTable);
			tw_corpus= createTupleWRiter(new File(getCorpusDefinition().getCorpusPath().toFileString() + FILE_CORPUS),this.escapeCharacters,this.characterEscapeTable);
			
			
			
			// set the visualisation tuple writer
			File resolverVisFile = new File(getCorpusDefinition().getCorpusPath().toFileString()+FILE_VISUALIZATION);
			if (! resolverVisFile.exists()){
				tw_visualization= createTupleWRiter(resolverVisFile,this.escapeCharacters,this.characterEscapeTable);
			} else {
				if (overwriteResolverVisMap){
					tw_visualization= createTupleWRiter(resolverVisFile,this.escapeCharacters,this.characterEscapeTable);
				}
			}
			
			// set the corpus meta annotation tuple writer
			File corpusAnnotationFile = new File(getCorpusDefinition().getCorpusPath().toFileString()+FILE_CORPUS_META);
			if (! corpusAnnotationFile.exists()){
				tw_corpusMeta= createTupleWRiter(corpusAnnotationFile,this.escapeCharacters,this.characterEscapeTable);
			} else {
				if (overwriteCorpusAnnotations){
					tw_corpusMeta= createTupleWRiter(corpusAnnotationFile,this.escapeCharacters,this.characterEscapeTable);
				}
			}
			
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
			
			// print the resolver visualisation tab
			if (tw_visualization != null){
				printResolverVisMap(corpusGraph);
			}
		}

		/**
		 * This method prints the resolver_vis_map.tab file with the default values.
		 * @param corpusGraph the corpus graph
		 */
		private void printResolverVisMap(SCorpusGraph corpusGraph) {
			
			// print the resolver_vis_map:
			// corpus 	text 			the name of the supercorpus
			//version 	text 			the version of the corpus
			//namespace 	text 			the several layers of the corpus
			//element 	text 			the type of the entry: "node" or "edge"
			//vis_type 	text 		X 	the abstract type of visualization: "tree", "discourse", "grid", ...
			//display_name 	text 		X 	the name of the layer which shall be shown for display
			//visibility 	text 			either "permanent", "visible", "hidden", "removed" or "preloaded", default is "hidden"
			//order 	bigint 			the order of the layers, in which they shall be shown
			//mappings 	text
			
			EList<String> resolverTuple= new BasicEList<String>();
			String corpusName = "NULL";
			String corpusVersion = "NULL";
			String corpusNamespace = "default_ns";
			String elementEntry = "node";
			String vis_type = "grid"; // nut NULL
			String display_name = "default_ns"; // not NULL
			String visibility = "hidden";
			String order = "NULL";
			String mappings = "NULL";
			
			// get the version of the corpus but initialise the default NULL
			if (corpusGraph.getSRootCorpus() != null){
				if (corpusGraph.getSRootCorpus().size() > 0){
					SCorpus rootCorpus = corpusGraph.getSRootCorpus().get(0);
					// set corpus name
					corpusName = rootCorpus.getSName();
					// set corpus version
					SMetaAnnotation version= rootCorpus.getSMetaAnnotation("version");
					if (version != null){
						if ( version.getValueString() != null){
							corpusVersion= version.getValueString();
						}
					}
				}
			}
			resolverTuple.add(corpusName);
			resolverTuple.add(corpusVersion);
			resolverTuple.add(corpusNamespace);
			resolverTuple.add(elementEntry);
			resolverTuple.add(vis_type);
			resolverTuple.add(display_name);
			resolverTuple.add(visibility);
			resolverTuple.add(order);
			resolverTuple.add(mappings);
			
			Long transactionId = tw_visualization.beginTA();
			try {
				tw_visualization.addTuple(resolverTuple);
				tw_visualization.commitTA(transactionId);
			} catch (FileNotFoundException e) {
				throw new RelANNISModuleException("Could not write to the file "+tw_visualization.getFile().getAbsolutePath()+". Reason: "+e.getMessage(), e);
			}
			
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
		/** tuple writer to write {@link RelANNIS#FILE_VISUALIZATION} **/
		public TupleWriter tw_visualization= null;
		
		boolean overwriteResolverVisMap = true;
		
		boolean overwriteCorpusAnnotations = true;
		
		public String individualCorpusName= null;
		
		private Hashtable<Character,String> characterEscapeTable= null;
		private boolean escapeCharacters=true;
		
		// ------------------------- IdManager
		/** object to manage relANNIS ids**/
		private IdManager idManager;
		/** returns singleton object to manage relANNIS ids**/
		public IdManager getIdManager(){
			return this.idManager;
		}

}
