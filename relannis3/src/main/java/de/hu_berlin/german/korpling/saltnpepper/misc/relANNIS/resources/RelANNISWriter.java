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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleWriter;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SDATATYPE;

public class RelANNISWriter implements Runnable
{
// ================================== start TupleWriter
	public static final String[] supported_versions= {"3.1", "3.2"};
	
	/**
	 * Options to customize the export mapping.
	 */
	private java.util.Map<?,?> options=null;
	
	/**
	 * Sets the options to customize the export mapping. If options contain property {@value #PROP_ESCAPE_LINEBREAK} or
	 * {@value #PROP_ESCAPE_TAB} or {@value #PROP_ESCAPE_BACKSLASH}, an internal flag will be set to replace tab and line break characters with given character sequences.
	 * @param options
	 */
	public void setOptions(java.util.Map<?,?> options) {
		this.options = options;
		if (options!= null)
		{
			if (	(options.containsKey(RelANNISProperties.PROP_ESCAPE_LINEBREAK))||
					(options.containsKey(RelANNISProperties.PROP_ESCAPE_TAB))||
					(options.containsKey(RelANNISProperties.PROP_ESCAPE_BACKSLASH)))
			{
				if (options.containsKey(RelANNISProperties.PROP_ESCAPE_LINEBREAK))
					lineBreaksEscaping= options.get(RelANNISProperties.PROP_ESCAPE_LINEBREAK).toString();
				if (options.containsKey(RelANNISProperties.PROP_ESCAPE_TAB))
					tabEscaping= options.get(RelANNISProperties.PROP_ESCAPE_TAB).toString();
				if (options.containsKey(RelANNISProperties.PROP_ESCAPE_BACKSLASH))
					backslashEscaping= options.get(RelANNISProperties.PROP_ESCAPE_BACKSLASH).toString();
			}
		}
	}

	/**
	 * Returns the options to customize the export mapping.
	 * @return 
	 */
	public java.util.Map<?,?> getOptions() {
		return options;
	}
	
	/**
	 * Escaping for line breaks, given in options. Automatically computed
	 * when calling {@link #setOptions(Properties)}.
	 */
	private String lineBreaksEscaping= null;
	/**
	 * Escaping for tab, given in options. Automatically computed
	 * when calling {@link #setOptions(Properties)}.
	 */
	private String tabEscaping= null;
	/**
	 * Escaping for backslash, given in options. Automatically computed
	 * when calling {@link #setOptions(Properties)}.
	 */
	private String backslashEscaping= null;
	/**
	 * Determines the relANNIS version in which the model shall be stored.
	 */
	private String relannisVersion= null;
	/**
	 * Sets the relANNIS version in which the model shall be stored.
	 * @param relannisVersion version of relANNIS format
	 */
	public void setRelannisVersion(String relannisVersion) {
		boolean acceptableVersion= false;
		String supportedVersions="";
		for (String version: supported_versions)
		{	
			supportedVersions= supportedVersions+ " " +version;
			if (version.equals(relannisVersion))
			{
				acceptableVersion= true;
				break;
			}
		}
		if (!acceptableVersion)
			throw new RelANNISException("The given version '"+relannisVersion+"' is not one of the supported '"+supportedVersions+"'.");
		this.relannisVersion = relannisVersion;
	}

	/**
	 * Returns the relANNIS version in which the model shall be stored.
	 * @return
	 */
	public String getRelannisVersion() {
		return relannisVersion;
	}
	
	/**
	 * Initializes an object and sets its session-id to the given one.
	 */
	public RelANNISWriter(Long sessionId)
	{
		init(sessionId);
		this.setRelannisVersion(supported_versions[0]);
	}
	
	/**
	 * Initializes this object for example by setting all objects corresponding to the current session. 
	 */
	private void init(Long sessionId)
	{
		this.setSessionId(sessionId);
		addSession(sessionId);
	}
	
	/**
	 * Table storing all sessions, and all corresponding objects.
	 */
	private static volatile Hashtable<Long, Hashtable<String, Object>> sessionObjects= null;
	
	/**
	 * This value stores a string which identifies an attribute identifying a tuple. If this String 
	 * is stored in this table, it is already stored. (Separator #)
	 */ 
	private static final String SESSION_STORED_TUPLES="storedTuples";
	
	/**
	 * Creates a new table and all objects which are necessary.
	 * @param SessionId
	 */
	private static synchronized void addSession(Long sessionId)
	{
		if (sessionObjects== null)
			sessionObjects= new Hashtable<Long, Hashtable<String,Object>>();
		
		if (!sessionObjects.containsKey(sessionId))
		{
			Hashtable<String, Object> objects= new Hashtable<String, Object>();
			sessionObjects.put(sessionId, objects);
			objects.put(SESSION_STORED_TUPLES, new Hashtable<String, Vector<String>>());
		}
	}
	
	/**
	 * Returns all objects corresponding to the given session id.
	 * @param sessionId
	 * @return
	 */
	protected static synchronized Hashtable<String, Object> getSessionObjects(Long sessionId)
	{
		Hashtable<String, Object> retVal= null;
		if (sessionId== null)
			throw new RelANNISException("The given sessionId is null.");
		if (sessionObjects== null)
			throw new RelANNISException("No sessionObjectTable is given.");
		retVal= sessionObjects.get(sessionId);
		return(retVal);
	}
	
	/**
	 * Returns an object corresponding to the given object name and the given session id.
	 * @param sessionId
	 * @param objectName
	 * @return
	 */
	protected static synchronized Object getSessionObject(Long sessionId, String objectName)
	{
		Object retVal= null;
		if (sessionId== null)
			throw new RelANNISException("The given sessionId is null.");
		if (objectName== null)
			throw new RelANNISException("The given objectName is null.");
		if (sessionObjects== null)
			throw new RelANNISException("No sessionObjectTable is given.");
		retVal= sessionObjects.get(sessionId).get(objectName);
		return(retVal);
	}
// --------------------------- sessionId
	/**
	 * Unique session-identifier, several objects of this type can access common 
	 * objects via the given session-Identifier.
	 */
	private Long sessionId= null;

	/**
	 * Sets unique session-identifier, several objects of this type can access common 
	 * objects via the given session-Identifier.
	 * @param sessionId
	 */
	public void setSessionId(Long sessionId) 
	{
		this.sessionId= sessionId;
	}

	/**
	 * Returns unique session-identifier, several objects of this type can access common 
	 * objects via the given session-Identifier.
	 * @param sessionId
	 */
	public Long getSessionId() 
	{
		return(this.sessionId);
	}
// --------------------------- sessionId
// --------------------------- corpus	
	/**
	 * tuple writer for corpus.tab
	 */
	private TupleWriter corpusTWriter= null;

	/**
	 * Sets tuple writer for corpus.tab.
	 * @param corpusTWriter
	 */
	public void setCorpusTWriter(TupleWriter corpusTWriter) {
		this.corpusTWriter = corpusTWriter;
	}

	/**
	 * Returns tuple writer for corpus.tab.
	 * @param corpusTWriter
	 */
	public TupleWriter getCorpusTWriter() {
		return corpusTWriter;
	}

// --------------------------- corpusAnnotation	
	/**
	 * tuple writer for corpus_annotation.tab
	 */
	private TupleWriter corpusAnnotationTWriter= null;

	/**
	 * Sets tuple writer for corpus_annotation.tab.
	 * @param corpusAnnotationTWriter
	 */
	public void setCorpusAnnotationTWriter(TupleWriter corpusAnnotationTWriter) {
		this.corpusAnnotationTWriter = corpusAnnotationTWriter;
	}

	/**
	 * Returns tuple writer for corpusAnnotation.tab.
	 * @param corpus_annotationTWriter
	 */
	public TupleWriter getCorpusAnnotationTWriter() {
		return corpusAnnotationTWriter;
	}
	
// --------------------------- text
	/**
	 * TupleWriter for text.tab
	 */
	private TupleWriter textTWriter= null;
	public void setTextTWriter(TupleWriter textTWriter) {
		this.textTWriter = textTWriter;
	}

	public TupleWriter getTextTWriter() {
		return textTWriter;
	}
	
// --------------------------- nodes
	/**
	 * TupleWriter for node.tab
	 */
	private TupleWriter nodeTWriter= null;
	public void setNodeTWriter(TupleWriter nodeTWriter) {
		this.nodeTWriter = nodeTWriter;
	}

	public TupleWriter getNodeTWriter() {
		return nodeTWriter;
	}

// --------------------------- node annotation	
	private TupleWriter nodeAnnotationTWriter= null;
	public void setNodeAnnotationTWriter(TupleWriter nodeAnnotationTWriter) {
		this.nodeAnnotationTWriter = nodeAnnotationTWriter;
	}

	public TupleWriter getNodeAnnotationTWriter() {
		return nodeAnnotationTWriter;
	}

// --------------------------- rank	
	private TupleWriter rankTWriter= null;
	public void setRankTWriter(TupleWriter rankTWriter) {
		this.rankTWriter = rankTWriter;
	}

	public TupleWriter getRankTWriter() {
		return rankTWriter;
	}

// --------------------------- edge annotation	
	private TupleWriter edgeAnnotationTWriter= null;
	public void setEdgeAnnotationTWriter(TupleWriter edgeAnnotationTWriter) {
		this.edgeAnnotationTWriter = edgeAnnotationTWriter;
	}

	public TupleWriter getEdgeAnnotationTWriter() {
		return edgeAnnotationTWriter;
	}

//---------------------------- component
	private TupleWriter componentTWRiter= null;
	
	public void setComponentTWRiter(TupleWriter componentTWRiter) {
		this.componentTWRiter = componentTWRiter;
	}

	public TupleWriter getComponentTWRiter() {
		return componentTWRiter;
	}
	
//---------------------------- component
	private TupleWriter resolverVisMapTWRiter= null;
	
	public void setResolverVisMapTWRiter(TupleWriter componentTWRiter) {
		this.resolverVisMapTWRiter = componentTWRiter;
	}

	public TupleWriter getResolverVisMapTWRiter() {
		return resolverVisMapTWRiter;
	}
// ================================== end TupleWriter
	
	/**
	 * Stores corpus structure to save
	 */
	private RACorpusGraph raCorpusGraph= null;
	
	public void setRaCorpusGraph(RACorpusGraph raCorpusGraph) {
		this.raCorpusGraph = raCorpusGraph;
	}

	public RACorpusGraph getRaCorpusGraph() {
		return raCorpusGraph;
	}	
	
	/**
	 * Stores document graph to save
	 */
	private RADocumentGraph raDocumentGraph= null;
	
	/**
	 * Sets document to save
	 * @param raDocumentGraph
	 */
	public void setRaDocumentGraph(RADocumentGraph raDocumentGraph) {
		this.raDocumentGraph = raDocumentGraph;
	}

	/**
	 * Returns document to save.
	 * @return
	 */
	public RADocumentGraph getRaDocumentGraph() {
		return raDocumentGraph;
	}

	/**
	 * If {@link #tabEscaping} or {@link #lineBreaksEscaping} or {@link #backslashEscaping} is set, then they will be replaced by the given values. 
	 * Also removes colons 'colons'. 
	 * @param input
	 * @param cleanColons determins if colons has to be removed
	 * @return
	 */
	protected String cleanString(final String input, Boolean cleanColons)
	{
		String retVal= input;
		if (input!= null)
		{
			if (cleanColons)
				retVal= retVal.replace(":", "");
			if (	(lineBreaksEscaping!= null)||
					(tabEscaping!= null)||
					(backslashEscaping!= null))
			{
				if (tabEscaping!= null)
				{
					retVal= retVal.replace(RelANNISResource.COLUMN_SEPARATOR, tabEscaping);
				}
				if (lineBreaksEscaping!= null)
				{
					retVal= retVal.replace(RelANNISResource.ESCAPING_LINE_SEPARATOR_1, lineBreaksEscaping);
					retVal= retVal.replace(RelANNISResource.ESCAPING_LINE_SEPARATOR_2, lineBreaksEscaping);
				}
				if (backslashEscaping!= null)
				{
					retVal= retVal.replace(RelANNISResource.ESCAPING_BACKSLASH, backslashEscaping);
				}
				return(retVal);
			}
			else return(retVal);
		}
		else return(retVal);
	}
	
// ================================= start saving corpus structure	
	/**
	 * if mode= CORPUS_STRUCTURE, corpus structure will be save, if mode= "DOCUMENT", a document will be save
	 */
	private String SAVING_MODE= null;
	
	protected void basicSaveCorpusStructure()
	{	
		if (this.getRaCorpusGraph()== null)
			 throw new RelANNISException("Cannot start saving corpusstructure, because no corpus-graph-object is given.");
		if (this.getCorpusTWriter()== null)
			throw new RelANNISException("Cannot start saving corpusstructure, because no tuple writer is given for corpus.");
		if (this.getCorpusAnnotationTWriter()== null)
			throw new RelANNISException("Cannot start saving corpusstructure, because no tuple writer is given for corpus annotations.");
		// get a new ta id for corpus 
		Long currCorpusTa= this.getCorpusTWriter().beginTA();
		//get a new ta id for corpus annotation
		Long currCorpusAnnoTa= this.getCorpusAnnotationTWriter().beginTA();
		
		this.saveCorpus(currCorpusTa);
		
		//commit current ta
		try {
			this.getCorpusTWriter().commitTA(currCorpusTa);
		} catch (FileNotFoundException e) 
		{
			throw new RelANNISException("Cannot save corpus structure to file: "+ this.getCorpusTWriter().getFile(), e);
		}
		try {
			this.getCorpusAnnotationTWriter().commitTA(currCorpusAnnoTa);
		} catch (FileNotFoundException e) 
		{
			throw new RelANNISException("Cannot save corpus structure to file: "+ this.getCorpusAnnotationTWriter(), e);
		}
	}
	
	private String KW_NULL_VALUE= "NULL";
	@SuppressWarnings("unchecked")
	protected void saveCorpus(Long TAId)
	{
		if (	(this.getRaCorpusGraph()== null)||
				(this.getRaCorpusGraph().getRaCorpora()== null) ||	
				(this.getRaCorpusGraph().getRaCorpora().size()==0))
		{//do not save, because there is nothing to save
			//TODO log WARNING
		}//do not save, because there is nothing to save
		else
		{//save graph
			EList<String> tuple= null;
			//start: if pre and post isn't set already compute them, therefore check first and last corpus
				if (	(this.getRaCorpusGraph().getRaCorpora().get(0).getRaPre()== null) ||
						(this.getRaCorpusGraph().getRaCorpora().get(0).getRaPost()== null) ||
						(this.getRaCorpusGraph().getRaCorpora().get(this.getRaCorpusGraph().getRaCorpora().size()-1).getRaPre()== null) ||
						(this.getRaCorpusGraph().getRaCorpora().get(this.getRaCorpusGraph().getRaCorpora().size()-1).getRaPost()== null))
				{
					this.getRaCorpusGraph().generateRAPPOrder();
				}
			//end: if pre and post is not set already compute them
			for (RACorpus corpus: this.getRaCorpusGraph().getRaCorpora())
			{
				tuple= new BasicEList<String>();
				tuple.add(corpus.getRaId().toString());
				if (corpus.getRaName()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(corpus.getRaName());
				if (corpus.getRaType()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(corpus.getRaType().toString());
				if (corpus.getRaVersion()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(corpus.getRaVersion());
				if (corpus.getRaPre()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(corpus.getRaPre().toString());
				if (corpus.getRaPost()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(corpus.getRaPost().toString());
				try {
					this.getCorpusTWriter().addTuple(TAId, tuple);
				} catch (FileNotFoundException e) 
				{
					throw new RelANNISException("Cannot save corpus structure to file: "+ this.getCorpusTWriter().getFile(), e);
				}
				
				this.saveCorpusAnnotation((EList<RACorpusAnnotation>)(EList<? extends SAnnotation>)corpus.getSAnnotations(), TAId);
			}
		}//save graph
	}
	
	protected void saveCorpusAnnotation(	EList<RACorpusAnnotation> raCorpusAnnotations,
											Long TAId)
	{
		if (raCorpusAnnotations!= null)
		{	
			for (RACorpusAnnotation raCorpusAnnotation: raCorpusAnnotations)
			{
				Collection<String> tuple= new BasicEList<String>();
				if (raCorpusAnnotation.getRaCorpus_ref()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(raCorpusAnnotation.getRaCorpus_ref().toString());
				if (raCorpusAnnotation.getRaNamespace()== null) tuple.add(RelANNISResource.DEFAULT_NS);
				else tuple.add(raCorpusAnnotation.getRaNamespace());
				if (raCorpusAnnotation.getRaName()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(cleanString(raCorpusAnnotation.getRaName(), true));
				if (raCorpusAnnotation.getRaValue()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(cleanString(raCorpusAnnotation.getRaValue(), true));
				try {
					this.getCorpusAnnotationTWriter().addTuple(TAId, tuple);
				} catch (FileNotFoundException e) 
				{
					throw new RelANNISException("Cannot write corpus annotation '"+raCorpusAnnotation+"' object to tuple writer.");
				}
			}
		}
	}
// ================================= end saving corpus structure
// ================================= start saving document
	private void basicSaveDocument()
	{	
		if (this.getRaDocumentGraph()== null)
			 throw new RelANNISException("Cannot start save document, because no document-graph-object is given.");
		if (this.getNodeTWriter()== null)
			throw new RelANNISException("Cannot start save document, because no tuple writer is given for node.");
		if (this.getTextTWriter()== null)
			throw new RelANNISException("Cannot start save document, because no tuple writer is given for text.");
		if (this.getNodeAnnotationTWriter()== null)
			throw new RelANNISException("Cannot start save document, because no tuple writer is given for node annotation.");
		if (this.getRankTWriter()== null)
			throw new RelANNISException("Cannot start save document, because no tuple writer is given for rank.");
		if (this.getEdgeAnnotationTWriter()== null)
			throw new RelANNISException("Cannot start save document, because no tuple writer is given for edge annotation.");
		if (this.getComponentTWRiter()== null)
			throw new RelANNISException("Cannot start save document, because no tuple writer is given for component.");
		// get a new ta id for node
		Long currNodeTa= this.getNodeTWriter().beginTA();
		//get a new ta id for text
		Long currTextTa= this.getTextTWriter().beginTA();
		//get a new ta id for rank
		Long currRankTa= this.getRankTWriter().beginTA();
		//get a new ta id for node annotation
		Long currNodeAnnotationTa= this.getNodeAnnotationTWriter().beginTA();
		//get a new ta id for edge annotation
		Long currEdgeAnnotationTa= this.getEdgeAnnotationTWriter().beginTA();
		//get a new ta id for component
		Long currComponentTa= this.getComponentTWRiter().beginTA();
		
		String objectToSave= "";
		try 
		{
			objectToSave= "text";
			this.saveTexts(currTextTa);
			objectToSave= "node";
			this.saveNodes(currNodeTa);
			objectToSave= "rank";
			this.saveRanks(currRankTa);
			objectToSave= "component";
			this.saveRAComponents(currComponentTa);
			
			//commit all ta's 
			objectToSave= "node";
			this.getNodeTWriter().commitTA(currNodeTa);
			objectToSave= "text";
			this.getTextTWriter().commitTA(currTextTa);
			objectToSave= "rank";
			this.getRankTWriter().commitTA(currRankTa);
			objectToSave= "node annotation";
			this.getNodeAnnotationTWriter().commitTA(currNodeAnnotationTa);
			objectToSave= "egde annotation";
			this.getEdgeAnnotationTWriter().commitTA(currEdgeAnnotationTa);
			objectToSave= "component";
			this.getComponentTWRiter().commitTA(currComponentTa);
		} catch (Exception e) 
		{
			this.getNodeTWriter().abortTA(currNodeTa);
			this.getTextTWriter().abortTA(currTextTa);
			this.getRankTWriter().abortTA(currTextTa);
			this.getNodeAnnotationTWriter().abortTA(currNodeAnnotationTa);
			this.getEdgeAnnotationTWriter().abortTA(currEdgeAnnotationTa);
			this.getComponentTWRiter().abortTA(currComponentTa);
			
			String id= this.getRaDocumentGraph().getRaCorpus().getRaId().toString();
			String name= this.getRaDocumentGraph().getRaCorpus().getRaName();
			e.printStackTrace();
			throw new RelANNISException("Cannot save '"+objectToSave+"'-objects of the document '"+name+"' with id '"+id+"'.", e);
		}
	}
	
	/**
	 * Saves RAText objects in document graph with text-tuple writer.
	 * @param TAId id of transaction in which all tuples shall be written
	 */
	protected void saveTexts(Long TAId)
	{
		for (RAText raText: this.getRaDocumentGraph().getRaTexts())
		{
			Collection<String> tuple= new BasicEList<String>();
			tuple= new BasicEList<String>();
			if (raText.getRaId()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raText.getRaId().toString());
			if (raText.getRaName()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(cleanString(raText.getRaName(), false));
			if (raText.getRaText()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(cleanString(raText.getRaText(), false));
			try {
				this.getTextTWriter().addTuple(TAId, tuple);
			} catch (FileNotFoundException e) 
			{
				throw new RelANNISException("Cannot save texts in document to file: "+ this.getTextTWriter().getFile(), e);
			}
		}
	}
//----------------------------------- resolverTA	
	//TODO the resolver TA must be included into session handling
	/**
	 * Stores the id for the resolver tuple reader (the same over all corpora).
	 */
	private volatile Long resolverTAId= null;
	/**
	 * Sets the transaction id which shall be used for storing all resolver entries.
	 * @param TAId transaction id 
	 */
	public void setResolverTaId(Long TAId)
	{
		this.resolverTAId= TAId;
	}
	
	/**
	 * Returns the transaction id which is used for storing all resolver entries.
	 * @return transaction id
	 */
	public Long getResolverTaId()
	{
		return(this.resolverTAId);
	}
//----------------------------------- resolverTA
	
	private RACorpus raSuperRoot= null;
	/**
	 * Returns the super root RACorpus-object. 
	 * @return
	 */
	private RACorpus getRASuperRoot()
	{
		if (this.raSuperRoot== null)
		{	
			if (	(this.getRaDocumentGraph()!= null) &&
					(this.getRaDocumentGraph().getRaCorpus()!= null) &&
					(this.getRaDocumentGraph().getRaCorpus().getRaCorpusGraph()!= null))
			{	
					this.raSuperRoot= this.getRaDocumentGraph().getRaCorpus().getRaCorpusGraph().getRARoots(this.getRaDocumentGraph().getRaCorpus());
			}
		}
		return(raSuperRoot);
	}
	
	/**
	 * Saves raNode-objects. They all will be put into tuple writer. Also all nodeAnnotations
	 * will be saved.
	 * @param TAId id of transaction in which all tuples shall be written
	 */
	protected void saveNodes(Long TAId)
	{
		for (RANode raNode: this.getRaDocumentGraph().getRaNodes())
		{
			Collection<String> tuple= new BasicEList<String>();
			tuple= new BasicEList<String>();
			Boolean continuous= raNode.getRaContinuous();
			String span= null;
			if (raNode.getRaToken_Index()!= null)
				span= raNode.getRaSpan();			
			//raId
			if (raNode.getRaId()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaId().toString());
			//raText_ref 	
			if (raNode.getRaText_ref()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaText_ref().toString());
			//raCorpus_ref
			if (raNode.getRaCorpus_ref()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaCorpus_ref().toString());
			//raNamespace
			if (raNode.getRaNamespace()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaNamespace());
			//raName
			if (raNode.getRaName()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaName());
			//raLeft
			if (raNode.getRaLeft()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaLeft().toString());
			//raRight
			if (raNode.getRaRight()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaRight().toString());
			//token_index
			if (raNode.getRaToken_Index()== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(raNode.getRaToken_Index().toString());
			
			if (!supported_versions[0].equals(this.getRelannisVersion()))
			{//not available in version 3.1
				//segment_name
				if (raNode.getSegment_name()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(raNode.getSegment_name());
				//left segment
				if (raNode.getLeftSegment()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(raNode.getLeftSegment().toString());
				//right segment
				if (raNode.getRightSegment()== null) tuple.add(KW_NULL_VALUE);
				else tuple.add(raNode.getRightSegment().toString());
			}//not available in version 3.1
			//raContinuous
			if (continuous== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(continuous.toString());
			//raSpan save only span if it is not null, and node is not a terminal node
			if (span== null) tuple.add(KW_NULL_VALUE);
			else tuple.add(cleanString(span, false));
			
			try {
				this.getNodeTWriter().addTuple(TAId, tuple);
			} catch (FileNotFoundException e) 
			{
				throw new RelANNISException("Cannot save nodes in document to file: "+ this.getNodeTWriter().getFile(), e);
			}
			//save node annotations
			this.saveRANodeAnnotation(raNode, TAId);
			
			//start: create a resolver entry
				if (raNode.getRaToken_Index()!= null)
					this.createResolverVisMap(raNode, null);				
			//end: create a resolver entry
		}
	}
	 
	/**
	 * Namespace for not to visualize, an entry shall not get a resolver entry.
	 */
	protected static String VIZ_TYPE_NON="NON";
	/**
	 * Namespace for visualization type for grid viewable data.
	 */
	protected static String VIZ_TYPE_GRID="grid";
	/**
	 * Namespace for visualization type for urml data (old_grid).
	 */
	protected static String VIZ_TYPE_GRID_OLD="old_grid";
	/**
	 * Namespace for visualization type for tree viewable data.
	 */
	protected static String VIZ_TYPE_TREE="tree";
	/**
	 * Namespace for visualization type for discourse viewable data.
	 */
	protected static String VIZ_TYPE_DISCOURSE="discourse";
	/**
	 * Namespace for visualization type for kwic viewable data.
	 */
	protected static String VIZ_TYPE_KWIC="kwic";
	/**
	 * This method is a simple heuristic to create a namespace for nodes out of the kind of.
	 * SSpanningNode --> "exmaralda"
	 * SSStructure --> "mmax" if node has an SPointingRelation as in or outgoing relation, "tiger" otherwise
	 *  
	 *  
	 * @param owningSNode the node which owns annotation
	 * @return returns a namespcase for annis visualisation
	 */
	public String getVisType(RARank raRank)
	{
		String retVal= VIZ_TYPE_GRID;
		
		if (raRank== null)
		{
			retVal= VIZ_TYPE_NON;
		}
		else if (raRank.getRaNode().getRaToken_Index()!= null)
		{// not to visualize in case of token
			retVal= VIZ_TYPE_NON;
		}// not to visualize in case of token
		else if (	(raRank.getRaNode().getRaNamespace()!= null)&&
					(raRank.getRaNode().getRaNamespace().equalsIgnoreCase("urml")))
		{
			retVal= VIZ_TYPE_GRID_OLD;
		}
		else if (raRank.getRaComponent().getRaType()== RA_COMPONENT_TYPE.C)
		{//if component belongs to coverage (c)
			retVal= VIZ_TYPE_GRID;
		}//if component belongs to coverage (c)
		else if (raRank.getRaComponent().getRaType()== RA_COMPONENT_TYPE.D)
		{//if component belongs to coverage (d)
			retVal= VIZ_TYPE_TREE;
		}//if component belongs to coverage (d)
		else if (raRank.getRaComponent().getRaType()== RA_COMPONENT_TYPE.P)
		{//if component belongs to pointing relation (p)
			retVal= VIZ_TYPE_DISCOURSE;
		}//if component belongs to pointing relation (p)
		
		return(retVal);
	}
	
	/**
	 * Saves raRank-objects. They all will be put into tuple writer. Also all edgeAnnotations
	 * will be saved.
	 * @param TAId id of transaction in which all tuples shall be written
	 */
	protected void saveRanks(Long TAId)
	{
		for (RARank raRank: this.getRaDocumentGraph().getRaRanks())
		{
			Collection<String> tuple= new BasicEList<String>();
			tuple= new BasicEList<String>();
			//pre
			tuple.add((raRank.getRaPre()!=null) ? raRank.getRaPre().toString() : KW_NULL_VALUE); 
			//post
			tuple.add((raRank.getRaPost()!=null) ? raRank.getRaPost().toString() : KW_NULL_VALUE);
			//node_ref
			tuple.add((raRank.getRaNode_ref()!=null) ? raRank.getRaNode_ref().toString() : KW_NULL_VALUE);
			//component_ref
			tuple.add((raRank.getRaComponent_ref()!=null) ? raRank.getRaComponent_ref().toString() : KW_NULL_VALUE);
			//parent_ref
			tuple.add((raRank.getRaParent_ref()!=null) ? raRank.getRaParent_ref().toString() : KW_NULL_VALUE);
			try {
				this.getRankTWriter().addTuple(TAId, tuple);
			} catch (FileNotFoundException e) 
			{
				throw new RelANNISException("Cannot save ranks in document to file: "+ this.getRankTWriter().getFile(), e);
			}
			//save edge annotations
			this.saveRAEdgeAnnotation(raRank, TAId);
			
			{//create a resolver entry
				this.createResolverVisMap(raRank.getRaNode(), raRank);				
			}//create a resolver entry
		}
	}	
	
	/**
	 * Writes all visualization entries, stored in storedTuples to TupleWriter. 
	 */
	@SuppressWarnings("unchecked")
	protected void saveResolverVisMap()
	{
		Hashtable<String, Vector<String>> storedTuples= ((Hashtable<String, Vector<String>>)getSessionObject(getSessionId(), SESSION_STORED_TUPLES)); 
		if (storedTuples!= null)
		{
			if (resolverTAId== null)
				resolverTAId= this.getResolverVisMapTWRiter().beginTA();
			Enumeration<Vector<String>>tuples= storedTuples.elements();
			while (tuples.hasMoreElements())
			{
				Vector<String> resolverTuple= tuples.nextElement();
				try {
					this.getResolverVisMapTWRiter().addTuple(this.resolverTAId, resolverTuple);
				} catch (FileNotFoundException e) {
					throw new RelANNISException("Cannot store an entry for resolver_vis_map. ",e);
				}
			}
			try {
				this.getResolverVisMapTWRiter().commitTA(resolverTAId);
			} catch (FileNotFoundException e) 
			{ throw new RelANNISException("Cannot flush the entries for the resolver visualization.");}
		}
	}
	
	/**
	 * Creates a deisplay name computed out of the given namespace and visualisation_type.
	 * @param namespace
	 * @return
	 */
	private String createDisplayName(String namespace, String visType)
	{
		StringBuffer retVal= new StringBuffer();
		if (namespace== null)
			retVal.append("default_display_name");
		else
		{	
			retVal.append(namespace);
			retVal.append(" ");
			retVal.append("(");
			retVal.append(visType);
			retVal.append(")");
		}
		return(retVal.toString());
	}
	
	/**
	 * 
	 * @param raNode
	 * @param raRank
	 */
	@SuppressWarnings("unchecked")
	protected void createResolverVisMap(RANode raNode, RARank raRank)
	{	
		if (resolverTAId== null)
			resolverTAId= this.getResolverVisMapTWRiter().beginTA();
		
		String corpusName= null;
		String corpusVersion= null;
		if (this.getRASuperRoot()!= null)
		{
			corpusName= this.getRASuperRoot().getRaName();
			corpusVersion= this.getRASuperRoot().getRaVersion();
		}
		String vis_type= null;
		vis_type= this.getVisType(raRank);
		
		{//create entry for node: corpus_name, corpus_version, namespace, type, visType, vis_name, order, mapping
			if (	(vis_type== VIZ_TYPE_DISCOURSE) ||
					(vis_type== VIZ_TYPE_NON))
			{//do not store an entry for nodes, if vis_type is discourse, or non
				;
			}//do not store an entry for nodes, if vis_type is discourse, or non
			else
			{	
				Vector<String> resolverTuple= new Vector<String>();
			    //corpus (name of supercorpus),
				if (corpusName== null)
					resolverTuple.add(KW_NULL_VALUE);
				else resolverTuple.add(corpusName);
				{//version,
					if (corpusVersion== null)
						resolverTuple.add(KW_NULL_VALUE);
					else resolverTuple.add(corpusVersion);
				}//version
				{//namespace (element_namespace),
					if (raNode.getRaNamespace()== null)
					{	
						resolverTuple.add(KW_NULL_VALUE);
					}
					else
					{
						resolverTuple.add(raNode.getRaNamespace());
					}
				}//namespace (element_namespace),
				{//element (node | edge)
					resolverTuple.add("node");
				}//element (node | edge)
				{//vis_type(default= created by heuristic)
					if (vis_type== null)
					{	
						resolverTuple.add(KW_NULL_VALUE);
					}
					else 
					{
						resolverTuple.add(vis_type);
					}
				}//vis_type(default= created by heuristic)
				//display name (default= namespace)
				resolverTuple.add(this.createDisplayName(raNode.getRaNamespace(), vis_type));
			    //order (default= 0) 
				resolverTuple.add("0");
				//mappings (default= NULL)
				resolverTuple.add(KW_NULL_VALUE);
				{//storing tuple identifier in storedTuples-table
					StringBuffer tupleIdentifier= new StringBuffer();
					tupleIdentifier.append(resolverTuple.get(2));
					tupleIdentifier.append("#");
					tupleIdentifier.append(resolverTuple.get(3));
					tupleIdentifier.append("#");
					tupleIdentifier.append(resolverTuple.get(4));
					Hashtable<String, Vector<String>> storedTuples= (Hashtable<String, Vector<String>>)getSessionObject(getSessionId(), SESSION_STORED_TUPLES); 
					if (!storedTuples.containsKey(tupleIdentifier.toString()))
					{
						storedTuples.put(tupleIdentifier.toString(), resolverTuple);
					}
					{//delete edge entry if exist
						tupleIdentifier= new StringBuffer();
						tupleIdentifier.append(resolverTuple.get(2));
						tupleIdentifier.append("#");
						tupleIdentifier.append("edge");
						tupleIdentifier.append("#");
						tupleIdentifier.append(resolverTuple.get(4));
						
						if (storedTuples.containsKey(tupleIdentifier.toString()))
						{
							storedTuples.remove(tupleIdentifier.toString());
						}
					}//delete edge entry if exist
				}//storing tuple identifier in storedTuples-table
			}
		}//create entry for node
		if (raRank!= null)
		{//create entry for edge
			if (raRank.getRaParentRank()!= null)
			{//only create entry, if rank has a parent, else it is no real edge
				Boolean storeEntry= true;
				if (	(vis_type== VIZ_TYPE_GRID) ||
						(vis_type== VIZ_TYPE_NON))
				{//do not store an entry for edges, if vis_type is grid, or non
					storeEntry= false;
				}//do not store an entry for edges, if vis_type is grid, or non
				else if (vis_type== VIZ_TYPE_DISCOURSE)
				{
					if (	(raRank.getRaNode()!= null) &&
							(raRank.getRaParentNode()!= null))
					{	
						if (!raRank.getRaNode().getRaText_ref().equals(raRank.getRaParentNode().getRaText_ref()))
						{//do not create a vis-Map entry for discourse, if nodes corresponds to different texts (parallel corpora)
							storeEntry= false;
						}//do not create a vis-Map entry for discourse, if nodes corresponds to different texts (parallel corpora)
					}
				}
				if (storeEntry)
				{
					String namespace= null;
					{//namespace (element_namespace)
						if (raRank.getRaComponent().getRaNamespace()== null)
							namespace= KW_NULL_VALUE;
						else namespace= raRank.getRaComponent().getRaNamespace();
					}//namespace (element_namespace)
					String type= null;
					{//element (node | edge)
						type= "edge";
					}//element (node | edge)
					String visType= null;
					{//vis_type(default= created by heuristic)
						if (vis_type== null)
							visType= KW_NULL_VALUE;
						else visType= vis_type;
					}//vis_type(default= created by heuristic)
					StringBuffer tupleIdentifier= new StringBuffer();
					{//storing tuple identifier in storedTuples-table
						tupleIdentifier.append(namespace);
						tupleIdentifier.append("#");
						tupleIdentifier.append("node");
						tupleIdentifier.append("#");
						tupleIdentifier.append(visType);	
					}//storing tuple identifier in storedTuples-table
					Hashtable<String, Vector<String>> storedTuples= (Hashtable<String, Vector<String>>)getSessionObject(getSessionId(), SESSION_STORED_TUPLES);
					if (	(storedTuples!= null) &&
							(storedTuples.containsKey(tupleIdentifier.toString())))
					{//do nothing, if there is already an entry for node with same namespace and visualisation type
						;
					}//do nothing, if there is already an entry for node with same namespace and visualisation type
					else
					{	
						Vector<String> resolverTuple= new Vector<String>();
					    //corpus (name of supercorpus),
						if (corpusName== null)
							resolverTuple.add(KW_NULL_VALUE);
						else resolverTuple.add(corpusName);
						//version,
						if (corpusVersion== null)
							resolverTuple.add(KW_NULL_VALUE);
						else resolverTuple.add(corpusVersion);
						
					    //namespace (element_namespace),
						resolverTuple.add(namespace);
						//element (node | edge),
						resolverTuple.add(type);
						//vis_type(default= created by heuristic),
						resolverTuple.add(visType);
							
						//display name (default= namespace)
						resolverTuple.add(this.createDisplayName(raNode.getRaNamespace(), vis_type));
						
					    //order (default= 0) 
						resolverTuple.add("0");
						//mappings (default= NULL)
						resolverTuple.add(KW_NULL_VALUE);
						{//storing tuple identifier in storedTuples-table
							tupleIdentifier= new StringBuffer();
							tupleIdentifier.append(resolverTuple.get(2));
							tupleIdentifier.append("#");
							tupleIdentifier.append(resolverTuple.get(3));
							tupleIdentifier.append("#");
							tupleIdentifier.append(resolverTuple.get(4));
						}//storing tuple identifier in storedTuples-table
						if (!storedTuples.containsKey(tupleIdentifier.toString()))
						{
							storedTuples.put(tupleIdentifier.toString(), resolverTuple);
						}
					}
				}
			}//only create entry, if rank has a parent, else it is no real edge
		}//create entry for edge

	}
	
	
	private void createResolverVisMapEntry(RANodeAnnotation raNodeAnno)
	{
		String corpusName= null;
		String corpusVersion= null;
		if (this.getRASuperRoot()!= null)
		{
			corpusName= this.getRASuperRoot().getRaName();
			corpusVersion= this.getRASuperRoot().getRaVersion();
		}
		
		Vector<String> resolverTuple= new Vector<String>();
	    //corpus (name of supercorpus),
		if (corpusName== null)
			resolverTuple.add(KW_NULL_VALUE);
		else resolverTuple.add(corpusName);
		//version,
		if (corpusVersion== null)
			resolverTuple.add(KW_NULL_VALUE);
		else resolverTuple.add(corpusVersion);
		
	    //namespace (element_namespace),
		resolverTuple.add(KW_NULL_VALUE);
		//element (node | edge),
		resolverTuple.add(KW_NULL_VALUE);
		//vis_type(default= created by heuristic),
		URI uri= (URI) raNodeAnno.getSValue();
		String visType;
		if (	(uri.fileExtension()!= null)&&
				(	("ogg".equalsIgnoreCase(uri.fileExtension()))||
					("wav".equalsIgnoreCase(uri.fileExtension()))))
			visType= "audio";
		else
			visType= "video";
		resolverTuple.add(visType);
			
		//display name (default= namespace)
		resolverTuple.add(visType);
		
	    //order (default= 0) 
		resolverTuple.add("0");
		//mappings (default= NULL)
		resolverTuple.add(KW_NULL_VALUE);
		
		//storing tuple identifier in storedTuples-table
			StringBuffer tupleIdentifier= new StringBuffer();
			tupleIdentifier.append(resolverTuple.get(2));
			tupleIdentifier.append("#");
			tupleIdentifier.append(resolverTuple.get(3));
			tupleIdentifier.append("#");
			tupleIdentifier.append(resolverTuple.get(4));
		//storing tuple identifier in storedTuples-table
		Hashtable<String, Vector<String>> storedTuples= (Hashtable<String, Vector<String>>)getSessionObject(getSessionId(), SESSION_STORED_TUPLES);
		if (!storedTuples.containsKey(tupleIdentifier.toString()))
		{
			storedTuples.put(tupleIdentifier.toString(), resolverTuple);
		}
	}
	
	/**
	 * Saves all node-annotations of the given raNode . Annotations will be saved
	 * to NodeAnnotationReader.
	 * @param raNode node, whose annotations shall be saved.
	 * @param TAId id of transaction in which all tuples shall be written
	 */
	protected void saveRANodeAnnotation(RANode raNode, Long TAId)
	{
		for (RANodeAnnotation raNodeAnnotation: raNode.getRaAnnotations())
		{
			if (	(raNodeAnnotation.getRaName()!= null)&&
					(!raNodeAnnotation.getRaName().isEmpty()))
			{
				Collection<String> tuple= new BasicEList<String>();
				tuple= new BasicEList<String>();
				//raNode_ref
				tuple.add(raNode.getRaId().toString());
				//raNamespace
				String raNamespace= (raNodeAnnotation.getRaNamespace()== null) ? RelANNISResource.DEFAULT_NS : raNodeAnnotation.getRaNamespace();
				tuple.add(raNamespace);
				//raName
				String raName= (raNodeAnnotation.getRaName()== null) ? KW_NULL_VALUE : cleanString(raNodeAnnotation.getRaName(), true);
				tuple.add(raName);
				//raValue
					String raValue= null;
					if (	(raNodeAnnotation.getSValue()!= null)&&
							(raNodeAnnotation.getSValue()instanceof URI))
					{//special treating of external reference-annotations 
						URI uri= (URI)raNodeAnnotation.getSValue();
						if (uri== null){
							throw new RelANNISException("Cannot save raNodeAnnotation '"+raNodeAnnotation+"', because uri-value was empty '"+uri+"'.");
						}
						String uriStr= uri.toFileString();
						if (uriStr== null){
							uriStr= uri.toString();
						}
						File externalFile= new File(uriStr);
						raValue= this.createExternalFileAnnotation(externalFile);
						createResolverVisMapEntry(raNodeAnnotation);
					}//special treating of reference-annotations
					else raValue= (raNodeAnnotation.getRaValue()== null) ? KW_NULL_VALUE : cleanString(raNodeAnnotation.getRaValue(), false);
					tuple.add(raValue);
				//raValue
				try {
					this.getNodeAnnotationTWriter().addTuple(TAId, tuple);
				} catch (FileNotFoundException e) 
				{
					throw new RelANNISException("Cannot save node annotation in document to file: "+ this.getNodeAnnotationTWriter().getFile(), e);
				}
			}
		}
	}
	
	/**
	 * Stores the external path, in which the external files will be stored
	 */
	private File extFilePath= null; 
	/**
	 * Stores the path of the current document starting at the root.
	 */
//	private String currentDocumentPath= null;
	/**
	 * Copies an external file and creates an external file-annotation.
	 * @return
	 */
	private String createExternalFileAnnotation(File externalFile)
	{
		String retVal= null;
		if (externalFile!= null)
		{//if externalFile is not empty	
			//extract export corpus-path
				if (this.extFilePath== null)
				{
					File corpusPath= this.getNodeAnnotationTWriter().getFile().getParentFile();
					this.extFilePath= new File(corpusPath.getAbsolutePath() + "/"+ "ExtData");
					if (!this.extFilePath.exists())
						this.extFilePath.mkdirs();
				}
			//extract export corpus-path
			String externalFileName= externalFile.getName();
			String externalFileEndingPath= this.getRaDocumentGraph().getRaCorpus().getRaName() +"/" +externalFileName;
			File exportFile= new File(this.extFilePath +"/"+ externalFileEndingPath);
			if (!exportFile.getParentFile().exists())
				exportFile.getParentFile().mkdirs();
			try
			{
				this.copyFile(externalFile, exportFile);
			}
			catch (IOException e) 
			{
				throw new RelANNISException("Cannot copy external file from '"+externalFile.getAbsolutePath()+"' to '"+exportFile.getAbsolutePath()+"'.", e);
			}
			retVal= "[ExtFile]" + externalFileEndingPath;
		}//if externalFile is not empty
		return(retVal);
	}
	
	private void copyFile(File src, File dest) throws IOException
	{
		  //static void copy( InputStream in, OutputStream out ) throws IOException 
		 	//static void copyFile( String src, String dest ) 
		FileInputStream  fis = null; 
		FileOutputStream fos = null; 
		
		
		fis = new FileInputStream(src ); 
		fos = new FileOutputStream(dest); 
		 
		byte[] buffer = new byte[ 0xFFFF ]; 
		for ( int len; (len = fis.read(buffer)) != -1; ) 
			fos.write( buffer, 0, len ); 
		
		fis.close();
		fos.close();
	}

	
	/**
	 * Saves all edge-annotations of the given raRank . Annotations will be saved
	 * to EdgeAnnotationReader.
	 * @param raRank rank, whose annotations shall be saved.
	 * @param TAId id of transaction in which all tuples shall be written
	 */
	protected void saveRAEdgeAnnotation(RARank raRank, Long TAId)
	{
		for (RAEdgeAnnotation raEdgeAnnotation: raRank.getRaAnnotations())
		{
			Collection<String> tuple= new BasicEList<String>();
			tuple= new BasicEList<String>();
			//raRank_ref
			tuple.add(raRank.getRaPre().toString());
			//raNamespace
			String raNamespace= (raEdgeAnnotation.getRaNamespace()== null) ? RelANNISResource.DEFAULT_NS : raEdgeAnnotation.getRaNamespace();
			tuple.add(raNamespace);
			//raName
			String raName= (raEdgeAnnotation.getRaName()== null) ? KW_NULL_VALUE : cleanString(raEdgeAnnotation.getRaName(), true);
			tuple.add(raName);
			//raValue
			String raValue= (raEdgeAnnotation.getRaValue()== null) ? KW_NULL_VALUE : cleanString(raEdgeAnnotation.getRaValue(), false);
			tuple.add(raValue);
			try {
				this.getEdgeAnnotationTWriter().addTuple(TAId, tuple);
			} catch (FileNotFoundException e) 
			{
				throw new RelANNISException("Cannot save rank annotation in document to file: "+ this.getEdgeAnnotationTWriter().getFile(), e);
			}
		}
	}
	
	/**
	 * Saves all components belonging to current document graph. Components will bes stored via
	 * ComponentTWRiter.
	 * @param TAId id of transaction in which all tuples shall be written
	 */
	protected void saveRAComponents(Long TAId)
	{
		for(RAComponent raComponent: this.getRaDocumentGraph().getRaComponents())
		{
			Collection<String> tuple= new BasicEList<String>();
			tuple= new BasicEList<String>();
			//raId
			tuple.add(raComponent.getRaId().toString());
			//raType
			String raType= (raComponent.getRaType()== null) ? KW_NULL_VALUE : raComponent.getRaType().toString();
			tuple.add(raType);
			//raNamespace
			String raNamespace= (raComponent.getRaNamespace()== null) ? KW_NULL_VALUE : raComponent.getRaNamespace();
			tuple.add(raNamespace);
			//raName
			String raName= (raComponent.getRaName()== null) ? KW_NULL_VALUE : raComponent.getRaName();
			tuple.add(raName);
			try {
				this.getComponentTWRiter().addTuple(TAId, tuple);
			} catch (FileNotFoundException e) 
			{
				throw new RelANNISException("Cannot save rank annotation in document to file: "+ this.getEdgeAnnotationTWriter().getFile(), e);
			}
		}
	}
// ================================= end saving document
	
	/**
	 * Saves the corpus structure into given model.
	 */
	public synchronized void saveCorpusStructure()
	{
		this.SAVING_MODE= "CORPUS_STRUCTURE";
		this.run();
	}
	
	/**
	 * Saves the corpus structure into given model.
	 */
	public synchronized void saveDocument()
	{
		this.SAVING_MODE= "DOCUMENT";
		this.run();
	}
	
	@Override
	public void run() 
	{
		if (SAVING_MODE.equalsIgnoreCase("CORPUS_STRUCTURE"))
		{
			this.basicSaveCorpusStructure();
			
			this.saveResolverVisMap();
		}
		else if (SAVING_MODE.equalsIgnoreCase("DOCUMENT"))
		{
			this.basicSaveDocument();
		}
		
	}
}
