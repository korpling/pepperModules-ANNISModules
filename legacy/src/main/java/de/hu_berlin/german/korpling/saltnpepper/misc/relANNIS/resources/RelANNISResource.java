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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleConnectorFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleReader;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;

public class RelANNISResource extends ResourceImpl
{	
	/**
	 * Default name for namespaces if they cannot be computed by the layers name.
	 */
	public final static String DEFAULT_NS= "default_ns";
	/**
	 * Prefix for token-namespaces, for example if token has namespace xyz, the namespace
	 * token_xyz will be created. 
	 */
	public final static String TOKEN_NS_PREFIXNS= "token";
	
	public static final String COLUMN_SEPARATOR="\t";
	public static final String ESCAPING_LINE_SEPARATOR_1="\n";
	public static final String ESCAPING_LINE_SEPARATOR_2="\r";
	public static final String ESCAPING_BACKSLASH="\\";
	/**
	 * Contains the types of resources.
	 * @author Administrator
	 *
	 */
	enum RESOURCE_TYPE {CORPUS, CORPUS_ANNOTATION, TEXT, NODE, NODE_ANNOTATION, RANK, EDGE_ANNOTATION, COMPONENT, RESOLVER_VIS_MAP};
	
	private static Long currId= 0l;
	private static synchronized Long createResourceId()
	{
		Long retVal= null;
		retVal= currId;
		currId++;
		return(retVal);
	}
	
	/**
	 * represents the unique id of current resource.
	 */
	private Long resourceId= null;
	
	public Long getResourceId() {
		return resourceId;
	}
	
	public RelANNISResource()
	{
		super();
		this.resourceId= createResourceId();
	}
// ---------------- start: Defining file names
	/**
	 * Ending of all relANNIS files
	 */
	public static final String FILE_ENDING="tab";
	
	/**
	 * Names of relANNIS files
	 */
	public static final String[] FILE_NAMES= {	"component"+ "."+ FILE_ENDING,
													"corpus"+ "."+ FILE_ENDING,
													"corpus_annotation"+ "."+ FILE_ENDING,
													"edge_annotation"+ "."+ FILE_ENDING,
													"node"+ "."+ FILE_ENDING,
													"node_annotation"+ "."+ FILE_ENDING,
													"rank"+ "."+ FILE_ENDING,
													"text"+ "."+ FILE_ENDING};
	/**
	 * Name of file to store corpora and document objects.
	 */
	public static final String FILE_NAME_CORPUS= FILE_NAMES[1];
	/**
	 * Name of file to store corpora and document objects annotations.
	 */
	public static final String FILE_NAME_CORPUS_ANNOTATION= FILE_NAMES[2];
	/**
	 * Name of file to store primary texts.
	 */
	public static final String FILE_NAME_TEXT= FILE_NAMES[7];
	/**
	 * Name of file to store nodes.
	 */
	public static final String FILE_NAME_NODE= FILE_NAMES[4];
	/**
	 * Name of file to store annotations of nodes.
	 */
	public static final String FILE_NAME_NODE_ANNOTATION= FILE_NAMES[5];
	/**
	 * Name of file to store ranks of nodes (similar to edges netween nodes).
	 */
	public static final String FILE_NAME_RANK= FILE_NAMES[6];
	/**
	 * Name of file to store annotations on ranks.
	 */
	public static final String FILE_NAME_EDGE_ANNOTATION= FILE_NAMES[3];
	/**
	 * Name of file to store components of ranks.
	 */
	public static final String FILE_NAME_COMPONENT= FILE_NAMES[0];
	/**
	 * Name of file to store resolver visualisations.
	 */
	public static final String FILE_NAME_RESOLVER_VIS_MAP= "resolver_vis_map"+"."+FILE_ENDING;
//----------------- end: Defining file names 

// ==================================== start saving	
	/**
	 * Contains all resource types and their corresponding tupleReader. 
	 * The key is the resource type.
	*/
	private Map<RESOURCE_TYPE, TupleWriter> resourceTypeWriterTable= null;
	
	/**
	 * Stores the id for the resolver tuple reader (the same over all corpora).
	 */
	private volatile Long resolverTAId= null;
	
	/**
	 * Stores a a treetagger-model into tab-separated file.
	 */
	public void save(java.util.Map<?,?> options) throws java.io.IOException
	{
		if (this.getContents().size()>1)
		{
			StringBuffer resourcesStr= new StringBuffer();
			resourcesStr.append("[");
			for (int i= 0; i< this.getContents().size(); i++)
			{
				if (i> 0)
					resourcesStr.append(", " + this.getContents().get(i));
				else 
					resourcesStr.append(this.getContents().get(i));
			}
			resourcesStr.append("]");
			throw new RelANNISException("Cannot save more than one content objects (RACorpusGraph-objects) per resource. In this case '"+this.getContents().size()+"' resources: "+ resourcesStr+".");
		}
		if (!options.containsKey("SAVING_TYPE"))
			throw new RelANNISException("Cannot load model, because an important option ('SAVING_TYPE') isn't given. ");
		this.initSaving();
		if (options.get("SAVING_TYPE").equals("CORPUS_STRUCTURE"))
		{//loading corpus structure
			RelANNISWriter writer= new RelANNISWriter(this.getResourceId());
			writer.setOptions(options);
			//start: setting relANNIS version
				if (options.containsKey(RelANNISResourceFactory.RELANNIS_VERSION))
					writer.setRelannisVersion(options.get(RelANNISResourceFactory.RELANNIS_VERSION).toString());
			//start: setting relANNIS version
			{//setting corpus graph
				RACorpusGraph corpGraph= (RACorpusGraph) this.getContents().get(0);
				writer.setRaCorpusGraph(corpGraph);
				writer.setCorpusTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.CORPUS));
				writer.setCorpusAnnotationTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.CORPUS_ANNOTATION));
				writer.setResolverVisMapTWRiter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.RESOLVER_VIS_MAP));
				{//for transaction id of resolver entries
					if (this.resolverTAId!= null)
						writer.setResolverTaId(this.resolverTAId);
				}//for transaction id of resolver entries
			}
			writer.saveCorpusStructure();
			{//for transaction id of resolver entries
				this.resolverTAId= writer.getResolverTaId();
			}//for transaction id of resolver entries
		}
		else if (options.get("SAVING_TYPE").equals("DOCUMENT"))
		{//loading corpus document
			String docNumberStr= options.get("SAVING_DOCUMENT_NO").toString();
			if (docNumberStr== null)
				throw new RelANNISException("Cannot load document, because the number of document to load isn't given. Please set resource option 'LOADING_DOCUMENT_NO'.");
			Long docNumber= new Long(docNumberStr);
			RACorpus document= null;
			{//searching for document to store
				if (!(this.getContents().get(0) instanceof RACorpusGraph))
					throw new RelANNISException("Cannot save the given resource, because the first content entry is not of type RACorpusGraph. The following object is given instead: "+ this.getContents().get(0)+".");
				RACorpusGraph corpGraph= (RACorpusGraph) this.getContents().get(0);
				for (RACorpus raCorpus: corpGraph.getRaCorpora())
				{
					if (raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
					{	
						if (raCorpus.getRaId().equals(docNumber))
						{
							document= raCorpus;
							break;
						}
					}
				}
			}
			RelANNISWriter writer= new RelANNISWriter(this.getResourceId());
			writer.setOptions(options);
			//start: setting relANNIS version
				if (options.containsKey(RelANNISResourceFactory.RELANNIS_VERSION))
					writer.setRelannisVersion(options.get(RelANNISResourceFactory.RELANNIS_VERSION).toString());
			//start: setting relANNIS version
			//start: setting document graph
				if (document== null)
					throw new RelANNISException("Cannot save the document with id '"+docNumber+"', because it doesn't exists in corpus structure.");
				RADocumentGraph docGraph= document.getRaDocumentGraph();
				writer.setRaDocumentGraph(docGraph);
			//end: setting document graph
			//start: setting TupleWriter
				writer.setNodeTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.NODE));
				writer.setNodeAnnotationTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.NODE_ANNOTATION));
				writer.setRankTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.RANK));
				writer.setEdgeAnnotationTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.EDGE_ANNOTATION));
				writer.setTextTWriter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.TEXT));
				writer.setComponentTWRiter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.COMPONENT));
				writer.setResolverVisMapTWRiter(this.resourceTypeWriterTable.get(RESOURCE_TYPE.RESOLVER_VIS_MAP));
				{//for transaction id of resolver entries
					if (this.resolverTAId!= null)
						writer.setResolverTaId(this.resolverTAId);
				}//for transaction id of resolver entries
			//end: setting TupleWriter
			//save document
			writer.saveDocument();
			{//for transaction id of resolver entries
				this.resolverTAId= writer.getResolverTaId();
			}//for transaction id of resolver entries
		}
	}
		
		/**
		 * True, if saving is already initialized.
		 */
		private Boolean isInitSaving= false;
		
		/**
		 * Initializes the loading, creating all tupleWriter-objects.
		 */
		private void initSaving()
		{
			if (!isInitSaving)
			{	
				if (this.getURI()== null)
					throw new RelANNISException("Cannot load model to an empty uri.");
				File outputPath= new File(this.getURI().toFileString());
				if (!outputPath.exists())
					outputPath.mkdirs();
				if (!outputPath.isDirectory())
					throw new RelANNISException("Cannot load model to the given uri, because uri is not a directory: "+ this.getURI());
				
				File corpusFile= null;
				File corpusAnnotationFile= null;
				File textFile= null;
				File nodeFile= null;
				File nodeAnnotationFile= null;
				File rankFile= null;
				File edgeAnnotationFile= null;
				File componentFile= null;
				File resolverVisMapFile= null;
				{//create files for saving
					//start: corpus.tab
						corpusFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_CORPUS);
						if (!corpusFile.exists())
						{	try {
								corpusFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ corpusFile, e);
							}
						}
					//end: corpus.tab
					//start: corpus_annotation.tab
						corpusAnnotationFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_CORPUS_ANNOTATION);
						if (!corpusAnnotationFile.exists())
						{	try {
								corpusAnnotationFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ corpusAnnotationFile, e);
							}
						}
					//end: corpus_annotation.tab
					//start: text.tab
						textFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_TEXT);
						if (!textFile.exists())
						{	try {
								textFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ textFile, e);
							}
						}
					//end: text.tab
					//start: node.tab
						nodeFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_NODE);
						if (!nodeFile.exists())
						{	try {
								nodeFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ nodeFile, e);
							}
						}
					//end: node.tab
					//start: node_annotation.tab
						nodeAnnotationFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_NODE_ANNOTATION);
						if (!nodeAnnotationFile.exists())
						{	try {
								nodeAnnotationFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ nodeAnnotationFile, e);
							}
						}
					//end: node_annotation.tab
					//start: rank.tab
						rankFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_RANK);
						if (!rankFile.exists())
						{	try {
								rankFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ rankFile, e);
							}
						}
					//end: rank.tab
					//start: edge_annotation.tab
						edgeAnnotationFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_EDGE_ANNOTATION);
						if (!edgeAnnotationFile.exists())
						{	try {
								edgeAnnotationFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ edgeAnnotationFile, e);
							}
						}
					//end: edge_annotation.tab
					//start: component.tab
						componentFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_COMPONENT);
						if (!componentFile.exists())
						{	try {
								componentFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ componentFile, e);
							}
						}
					//end: component.tab
					//start: resolver_vis_map.tab
						resolverVisMapFile= new File(this.getURI().toFileString() + "/"+FILE_NAME_RESOLVER_VIS_MAP);
						if (!resolverVisMapFile.exists())
						{	try {
							resolverVisMapFile.createNewFile();
							} catch (IOException e) 
							{
								throw new RelANNISException("Cannot save resource, because of problems with file: "+ resolverVisMapFile, e);
							}
						}
					//end: resolver_vis_map.tab
				}
				{//creating tupleWriters for files
					this.resourceTypeWriterTable= new Hashtable<RESOURCE_TYPE, TupleWriter>();
					TupleWriter tupleWriter= null;
					Collection<String> attNames= null;
					
					//Corpus tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(corpusFile);
					attNames= new BasicEList<String>();
					attNames.add("id");
					attNames.add("name");
					attNames.add("type");
					attNames.add("version");
					attNames.add("pre");
					attNames.add("post");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.CORPUS, tupleWriter);
					
					//CorpusAnnotation tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(corpusAnnotationFile);
					attNames= new BasicEList<String>();
					attNames.add("namespace");
					attNames.add("name");
					attNames.add("value");
					attNames.add("corpus_ref");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.CORPUS_ANNOTATION, tupleWriter);
					
					//text tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(textFile);
					attNames= new BasicEList<String>();
					attNames.add("id");
					attNames.add("name");
					attNames.add("text");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.TEXT, tupleWriter);
					
					//Node tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(nodeFile);
					attNames= new BasicEList<String>();
					attNames.add("raId");
					attNames.add("raText_ref");
					attNames.add("raCorpus_ref");
					attNames.add("raNamespace");
					attNames.add("raName");
					attNames.add("raLeft");
					attNames.add("raRight");
					attNames.add("raToken_Index");
					attNames.add("raContinuoius");
					attNames.add("raSpan");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.NODE, tupleWriter);
					
					//NodeAnnotation tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(nodeAnnotationFile);
					attNames= new BasicEList<String>();
					attNames.add("raNode_ref");
					attNames.add("raNamespace");
					attNames.add("raName");
					attNames.add("raValue");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.NODE_ANNOTATION, tupleWriter);
					
					//rank tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(rankFile);
					attNames= new BasicEList<String>();
					attNames.add("raPre");
					attNames.add("raPost");
					attNames.add("raNode_ref");
					attNames.add("raComponent_ref");
					attNames.add("raParent_ref");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.RANK, tupleWriter);
					
					//edge Annotation tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(edgeAnnotationFile);
					attNames= new BasicEList<String>();
					attNames.add("raRank_ref");
					attNames.add("raNamespace");
					attNames.add("raName");
					attNames.add("raValue");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.EDGE_ANNOTATION, tupleWriter);
					
					//component tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(componentFile);
					attNames= new BasicEList<String>();
					attNames.add("raId");
					attNames.add("raType");
					attNames.add("raNamespace");
					attNames.add("raName");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.COMPONENT, tupleWriter);
					
					//resolver_vis_map tupleWriter
					tupleWriter= TupleConnectorFactory.fINSTANCE.createTupleWriter();
					tupleWriter.setFile(resolverVisMapFile);
					//sets that the same tuples cannot be stored two times
					tupleWriter.setDistinct(true);
					attNames= new BasicEList<String>();
					attNames.add("corpus");
					attNames.add("version");
					attNames.add("namespace");
					attNames.add("element");
					attNames.add("vis_type");
					attNames.add("display_name");
					attNames.add("order");
					attNames.add("mappings");
					tupleWriter.setAttNames(attNames);
					tupleWriter.setSeperator(COLUMN_SEPARATOR);
					this.resourceTypeWriterTable.put(RESOURCE_TYPE.RESOLVER_VIS_MAP, tupleWriter);
				}
			}
			this.isInitSaving= true;
		}

// ==================================== end saving
// ==================================== start loading
	/**
	 * Contains all resource types and their corresponding tupleReader. 
	 * The key is the resource type.
	*/
	private Map<RESOURCE_TYPE, TupleReader> resourceTypeReaderTable= null;
		
	/**
	 * Loads a resource into treetagger-model from tab-seperated file.
	 * Needs to have the option 'LOADING_TYPE' with possible values: 'CORPUS_STRUCTURE', 'DOCUMENT'
	 * @param options options, which influences loading
	 */
	public void load(java.util.Map<?,?> options) throws IOException
	{
		if (!options.containsKey("LOADING_TYPE"))
			throw new RelANNISException("Cannot load model, because an important option ('LOADING_TYPE') isn't given. ");
		this.initLoading();
		if (options.get("LOADING_TYPE").equals("CORPUS_STRUCTURE"))
		{//loading corpus structure
			RelANNISReader reader= new RelANNISReader();
			{//setting corpus graph
				RACorpusGraph corpGraph= relANNISFactory.eINSTANCE.createRACorpusGraph();
				this.getContents().add(corpGraph);
				reader.setRaCorpusGraph(corpGraph);
				reader.setCorpusTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.CORPUS));
				reader.setcorpusAnnotationTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.CORPUS_ANNOTATION));
			}
			
			reader.loadCorpusStructure();
		}
		else if (options.get("LOADING_TYPE").equals("DOCUMENT"))
		{// loading corpus document
			String docNumberStr= options.get("LOADING_DOCUMENT_NO").toString();
			if (docNumberStr== null)
				throw new RelANNISException("Cannot load document, because the number of document to load isn't given. Please set resource option 'LOADING_DOCUMENT_NO'.");
			Long docNumber= new Long(docNumberStr);
			RelANNISReader reader= new RelANNISReader();
			RADocumentGraph docGraph= null;
			//start: setting document graph
				docGraph= relANNISFactory.eINSTANCE.createRADocumentGraph();
				docGraph.setSId(docNumberStr);
				reader.setRaDocumentGraph(docGraph);
			//end: setting document graph
			//start: setting TupleReader
				reader.setNodeTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.NODE));
				reader.setNodeAnnotationTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.NODE_ANNOTATION));
				reader.setRankTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.RANK));
				reader.setEdgeAnnotationTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.EDGE_ANNOTATION));
				reader.setTextTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.TEXT));
				reader.setComponentTReader(this.resourceTypeReaderTable.get(RESOURCE_TYPE.COMPONENT));
			//end: setting TupleReader
			//load document
			reader.loadDocument(docNumber);
			if (this.getContents().size()< 1)
				throw new RelANNISException("Cannot load a resource, because target model (content) is empty.");
			RACorpusGraph raCorpGraph= (RACorpusGraph) this.getContents().get(0);
			//start: putting document into corpus graph
				for (RACorpus raCorpus: raCorpGraph.getRaCorpora())
				{
					if (raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
					{	
						if (raCorpus.getRaId().equals(docNumber))
						{
							raCorpus.setRaDocumentGraph(docGraph);
							break;
						}
					}
				}
			//end: putting document into corpus graph
		}
		else throw new RelANNISException("Cannot load model, because the value of option 'LOADING_TYPE' is unknown: "+ options.get("LOADING_TYPE")+ ". You only can use 'CORPUS_STRUCTURE' and 'DOCUMENT'.");
	}
	
	/**
	 * True, if loading is already initialized.
	 */
	private Boolean isInitLoading= false;
	
	/**
	 * Initializes the loading, creating all tupleReader-objects.
	 */
	private void initLoading()
	{
		if (!isInitLoading)
		{	
			if (this.getURI()== null)
				throw new RelANNISException("Cannot load model to an empty uri.");
			File outputPath= new File(this.getURI().toFileString());
			if (!outputPath.exists())
				outputPath.mkdirs();
			if (!outputPath.isDirectory())
				throw new RelANNISException("Cannot load model to the given uri, because uri is not a directory: "+ this.getURI());
			
			File corpusFile= null;
			File corpusAnnotationFile= null;
			File textFile= null;
			File nodeFile= null;
			File nodeAnnotationFile= null;
			File rankFile= null;
			File edgeAnnotationFile= null;
			File componentFile= null;
			{//checking if all necessary files exists in directory
				{//corpus file
					corpusFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_CORPUS);
					if (!corpusFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_CORPUS+"'.");
				}
				{//corpus annotation file
					corpusAnnotationFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_CORPUS_ANNOTATION);
					if (!corpusAnnotationFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_CORPUS_ANNOTATION+"'.");
				}
				{//text file
					textFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_TEXT);
					if (!textFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_TEXT+"'.");
				}
				{//node file
					nodeFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_NODE);
					if (!nodeFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_NODE+"'.");
				}
				{//node annotation file
					nodeAnnotationFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_NODE_ANNOTATION);
					if (!nodeAnnotationFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_NODE_ANNOTATION+"'.");
				}
				{//rank file
					rankFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_RANK);
					if (!rankFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_RANK+"'.");
				}
				{//egde annotation file
					edgeAnnotationFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_EDGE_ANNOTATION);
					if (!edgeAnnotationFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_EDGE_ANNOTATION+"'.");
				}
				{//component file
					componentFile= new File(this.getURI().toFileString() + "/"+ FILE_NAME_COMPONENT);
					if (!componentFile.exists()) 
						throw new RelANNISException("Cannot load model, because the given directory '"+this.getURI()+"' doesn't contain the file '"+FILE_NAME_COMPONENT+"'.");
				}
			}
			{//creating tuplereaders for files
				this.resourceTypeReaderTable= new Hashtable<RESOURCE_TYPE, TupleReader>();
				TupleReader tupleReader= null;
				
				//Corpus tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(corpusFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.CORPUS, tupleReader);
				
				//CorpusAnnotation tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(corpusAnnotationFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.CORPUS_ANNOTATION, tupleReader);

				//text tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(textFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.TEXT, tupleReader);
				
				//Node tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(nodeFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.NODE, tupleReader);
				
				//NodeAnnotation tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(nodeAnnotationFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.NODE_ANNOTATION, tupleReader);
				
				//rank tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(rankFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.RANK, tupleReader);
				
				//edge Annotation tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(edgeAnnotationFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.EDGE_ANNOTATION, tupleReader);
				
				//component tupleReader
				tupleReader= TupleConnectorFactory.fINSTANCE.createTupleReader();
				tupleReader.setFile(componentFile);
				tupleReader.setSeperator("\t");
				this.resourceTypeReaderTable.put(RESOURCE_TYPE.COMPONENT, tupleReader);
			}
		}
		this.isInitLoading= true;
	}
// ==================================== end loading
}
