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

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.resources.RelANNISResource;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.ModuleController;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModule;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.exceptions.GraphInsertException;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SAudioDSRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SAudioDataSource;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDataSourceSequence;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructuredNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STYPE_NAME;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SDATATYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SProcessingAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;

public class Salt2RelANNISMapper implements SGraphTraverseHandler
{
	private static final Logger logger= LoggerFactory.getLogger(Salt2RelANNISMapper.class);
	
	public Salt2RelANNISMapper()
	{
		this.init();
	}
	
	private void init()
	{
		//initialize naming table
		alreadyExistingRANames= new Hashtable<String, String>();
	}
	
// -------------------------start: PepperModuleController	
	/**
	 * The controller handling the {@link PepperModule} object.
	 */
	private ModuleController pModuleController= null;
	
	public void setpModuleController(ModuleController pModuleController) {
		this.pModuleController = pModuleController;
	}

	public ModuleController getpModuleController() {
		return pModuleController;
	}
// -------------------------end: PepperModuleController
	
// -------------------------start: RACorpusGraph
	private RACorpusGraph raCorpusGraph= null;

	/**
	 * Sets the corpus graph to map to.
	 * @param raCorpusGraph corpus graph to map to
	 */
	public void setRaCorpusGraph(RACorpusGraph raCorpusGraph) {
		this.raCorpusGraph = raCorpusGraph;
	}

	/**
	 * Returns the corpus graph to map to.
	 * @return corpus graph to map to 
	 */
	public RACorpusGraph getRaCorpusGraph() {
		return raCorpusGraph;
	}
// -------------------------end: RACorpusGraph
// -------------------------start: SCorpusGraph 	
	private SCorpusGraph sCorpusGraph= null;
	
	/**
	 * Sets the corpus graph to map from.
	 * @param sCorpusGraph corpus graph to map from
	 */
	public void setSCorpusGraph(SCorpusGraph sCorpusGraph) {
		this.sCorpusGraph = sCorpusGraph;
	}

	/**
	 * Returns the corpus graph to map from.
	 * @return corpus graph to map from 
	 */
	public SCorpusGraph getSCorpusGraph() {
		return sCorpusGraph;
	}
// -------------------------end: SCorpusGraph
	enum TRAVERSION_TYPE {	CORPUS_STRUCTURE, 
							DOCUMENT_STRUCTURE_TOKEN,
							DOCUMENT_STRUCTURE_CR, 
							DOCUMENT_STRUCTURE_DR, 
							DOCUMENT_STRUCTURE_DR_SUB, 
							DOCUMENT_STRUCTURE_PR,
							DOCUMENT_STRUCTURE_PR_SUB,
							DOCUMENT_STRUCTURE_OR};
	/**
	 * stores the current type of traversion
	 */
	private TRAVERSION_TYPE currTraversionType= null;
	
	/**
	 * stores a table for sElement ids and corresponding RAIds for RACorpus-objects.
	 */
	private Map<SElementId, Long> sElementId2RaId= null;
	
	/**
	 * Returns a table, which will be filled with SElementId-objects corresponding
	 * to raIds.
	 * @return the sElementId2RaId
	 */
	public Map<SElementId, Long> getsElementId2RaId() {
		return sElementId2RaId;
	}	

	
// ================================================ start: mapping corpus structure	
	/**
	 * Starts the mapping of the RACorpusGraph-object to SCOrpusGraph object.
	 * @param raCorpusGraph corpus graph to map from
	 * @param sCorpusGraph corpus graph to map to
	 */
	public void mapSCorpusGraph2RACorpusGraph(SCorpusGraph sCorpusGraph, RACorpusGraph raCorpusGraph)
	{
		this.setRaCorpusGraph(raCorpusGraph);
		this.setSCorpusGraph(sCorpusGraph);
		if (this.getRaCorpusGraph()== null)
			throw new PepperModuleException("Cannot map sCorpusGraph to raCorpusGraph, because raCorpusGraph is null.");
		if (this.getSCorpusGraph()== null)
			throw new PepperModuleException("Cannot map sCorpusGraph to raCorpusGraph, because sCorpusGraph is null.");
		
		if (this.getsElementId2RaId()== null)
			this.sElementId2RaId= new Hashtable<SElementId, Long>();
		
		{//start traversion of corpus structure
			try
			{
				EList<SNode> roots= this.getSCorpusGraph().getSRoots();
				if (	(roots== null) ||
						(roots.size()== 0))
				{
					throw new PepperModuleException("Cannot traverse through corpus structure, because there is no raCOrpus-object as root.");
				}
				//set traversion type to corpus structure
				this.currTraversionType= TRAVERSION_TYPE.CORPUS_STRUCTURE;
				this.lastRACorpus= new Stack<RACorpus>();
				this.getSCorpusGraph().traverse(roots, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "compute_corpus_structure", this, true);
			}catch (Exception e) {
				throw new PepperModuleException("Some error occurs while traversing corpus structure graph.", e);
			}
		}//start traversion of corpus structure
	}
	
	/**
	 * Maps all Annotations on SDocument and SCorpus to corresponding RACorpus elements.
	 * @param raCorpusGraph corpus graph to map from
	 * @param sCorpusGraph corpus graph to map to
	 */
	public void mapFinalSCorpusGraph2RACorpusGraph(	SCorpusGraph sCorpusGraph, 
													RACorpusGraph raCorpusGraph,
													Map<SElementId, Long> sElementId2RaId)
	{
		this.sElementId2RaId= sElementId2RaId;
		this.setRaCorpusGraph(raCorpusGraph);
		this.setSCorpusGraph(sCorpusGraph);
		if (this.getRaCorpusGraph()== null)
			throw new PepperModuleException("Cannot final map sCorpusGraph to raCorpusGraph, because raCorpusGraph is null.");
		if (this.getSCorpusGraph()== null)
			throw new PepperModuleException("Cannot final map sCorpusGraph to raCorpusGraph, because sCorpusGraph is null.");
		
		if (this.getsElementId2RaId()== null)
			throw new PepperModuleException("Cannot map final sCorpusGraph to raCorpusGraph, because sElementId2RaId is null.");
		
		for (SCorpus sCorp: this.getSCorpusGraph().getSCorpora())
		{//map annotations of all corpora
			Long raId= this.sElementId2RaId.get(sCorp.getSElementId());
			if (raId== null)
				throw new PepperModuleException("Cannot map final sCorpusGraph to raCorpusGraph, because there is no raId registered to sElementId '"+sCorp.getSElementId().getSId()+"'.");
			RACorpus raCorpus= null;
			for (RACorpus raCorpus2: this.getRaCorpusGraph().getRaCorpora())
			{
				if (raCorpus2.getRaId().equals(raId))
					raCorpus= raCorpus2;
			}
			this.mapAnnotation2SRACorpusAnnotation(sCorp, raCorpus);
		}//map annotations of all corpora
		
		for (SDocument sCorp: this.getSCorpusGraph().getSDocuments())
		{//map annotations of all documents
			Long raId= this.sElementId2RaId.get(sCorp.getSElementId());
			if (raId== null)
				throw new PepperModuleException("Cannot map final sdocumentGraph to radocumentGraph, because there is no raId registered to sElementId '"+sCorp.getSElementId().getSId()+"'.");
			RACorpus raDocument= null;
			for (RACorpus raCorpus: this.getRaCorpusGraph().getRaCorpora())
			{
				if (raCorpus.getRaId().equals(raId))
					raDocument= raCorpus;
			}
			this.mapAnnotation2SRACorpusAnnotation(sCorp, raDocument);
		}//map annotations of all documents
	}
	
	/**
	 * Maps a SCorpus object to a RACorpus object
	 * @param sCorpus
	 * @param raCorpus
	 */
	protected void mapSCorpus2RACorpus(SCorpus sCorpus, RACorpus raCorpus)
	{
		raCorpus.setRaName(sCorpus.getSName());
		raCorpus.setRaType(RA_CORPUS_TYPE.CORPUS);
		String version= null; 
		if (sCorpus.getSAnnotation("version")!= null)
			{ version= sCorpus.getSAnnotation("version").getSValue().toString(); }
		else if (sCorpus.getSMetaAnnotation("version")!= null)
			{ version= sCorpus.getSMetaAnnotation("version").getSValue().toString(); }
		raCorpus.setRaVersion(version);
	}
	
	/**
	 * Maps a SDocument object to a RACorpus object
	 * @param sDocument
	 * @param raCorpus
	 */
	protected void mapSDocument2RACorpus(SDocument sDocument, RACorpus raCorpus)
	{
		raCorpus.setRaName(sDocument.getSName());
		raCorpus.setRaType(RA_CORPUS_TYPE.DOCUMENT);
		String version= null; 
		if (sDocument.getSAnnotation("version")!= null)
			{ version= sDocument.getSAnnotation("version").getSValue().toString(); }
		else if (sDocument.getSMetaAnnotation("version")!= null)
			{ version= sDocument.getSMetaAnnotation("version").getSValue().toString(); }
		raCorpus.setRaVersion(version);
	}
	
	/**
	 * Maps all annotations (SAnnotation objects and SMetaAnnotation-objects) of sNodeto RACorpusAnnotation-objects.
	 * All RACorpusAnnotation-objects will be added to the given raCorpus object.
	 * @param sNode object which possibly contains annotations
	 * @param raCorpus object where the annotations have to be mapped to
	 */
	protected void mapAnnotation2SRACorpusAnnotation(SNode sNode, RACorpus raCorpus)
	{
		RACorpusAnnotation raCorpusAnnotation= null;
		for (SAnnotation sAnno: sNode.getSAnnotations())
		{
			raCorpusAnnotation= relANNISFactory.eINSTANCE.createRACorpusAnnotation(sAnno);
			raCorpus.addRACorpusAnnotation(raCorpusAnnotation);
		}
		for (SMetaAnnotation sAnno: sNode.getSMetaAnnotations())
		{			
			raCorpusAnnotation= relANNISFactory.eINSTANCE.createRACorpusAnnotation(sAnno);
			raCorpus.addRACorpusAnnotation(raCorpusAnnotation);
		}
	}
	
// ================================================ end: mapping corpus structure
// ================================================ end: mapping document structure
	private SDocumentGraph sDocGraph = null;
	/**
	 * @param sDocGraph the sDocGraph to set
	 */
	private void setsDocGraph(SDocumentGraph sDocGraph) {
		this.sDocGraph = sDocGraph;
	}

	/**
	 * @return the sDocGraph
	 */
	public SDocumentGraph getsDocGraph() {
		return sDocGraph;
	}

	private RADocumentGraph raDocGraph = null; 
	/**
	 * @param raDocGraph the raDocGraph to set
	 */
	private void setRaDocGraph(RADocumentGraph raDocGraph) {
		this.raDocGraph = raDocGraph;
	}

	/**
	 * @return the raDocGraph
	 */
	public RADocumentGraph getRaDocGraph() {
		return raDocGraph;
	}
	
	/**
	 * The progress in percent of the current mapping.
	 */
	private double currentProgress= 0;
	
	/**
	 * returns the current progress.
	 * @return
	 */
	public double getProgress() {
		//TODO is because of an internal bug???
		if (currentProgress> 1)
			currentProgress= 0.999999999999999;
		return currentProgress;
	}

	/**
	 * Maps the given SDocumentGraph-object to the given RADocumentGraph-object.
	 * @param sDocGraph
	 * @param raDocGraph
	 */
	public void mapSDocumentGraph2RADocumentGraph(	SDocumentGraph sDocGraph,
													RADocumentGraph raDocGraph)
	{
		this.setsDocGraph(sDocGraph);
		this.setRaDocGraph(raDocGraph);
		if (this.getsDocGraph()== null)
			throw new PepperModuleException("Cannot map sDocumentGraph to raDocumentGraph, because sDocumentGraph is null.");
		if (this.getRaDocGraph()== null)
			throw new PepperModuleException("Cannot map sDocumentGraph to raDocumentGraph, because raDocumentGraph is null.");
			
		this.getRaDocGraph().setSId(this.getsDocGraph().getSId());
		
		//start: some inits
			this.sElementId2RANode= new Hashtable<SElementId, RANode>();
		//end: some inits
		
		//start: map all STextualDS-objects
			for (STextualDS sText: this.getsDocGraph().getSTextualDSs())
			{
				RAText raText= relANNISFactory.eINSTANCE.createRAText();
				this.mapSTextualDS2RAText(sText, raText);
				this.getRaDocGraph().addSNode(raText);
			}
		//end: map all STextualDS-objects
		
		Long timeToMapSRComponents= 0l;
		Long timeToMapDRComponents= 0l;
		Long timeToMapPRComponents= 0l;
		Long timeToMapLonlyComponents= 0l;
		//start: exporting all SStructure, SSpan and SToken elements connected with SSpanningRelation
			logger.debug("[RelANNISExporter] "+"[RelANNISExporter] "+getsDocGraph().getSElementId().getSId()+ ": relANNISExporter computing components for SSpanningRelation...");
			timeToMapSRComponents= System.nanoTime();
			this.traverseBySRelation(SSpanningRelation.class);
			timeToMapSRComponents= System.nanoTime() - timeToMapSRComponents;
		//end: exporting all SStructure, SSpan and SToken elements connected with SSpanningRelation
		
		//start: exporting all SStructure, SSpan and SToken elements connected with SDominanceRelation
			logger.debug("[RelANNISExporter] "+getsDocGraph().getSElementId().getSId()+ ": relANNISExporter computing components for SDominanceRelation...");
			timeToMapDRComponents= System.nanoTime();
			this.traverseBySRelation(SDominanceRelation.class);
			timeToMapDRComponents= System.nanoTime() - timeToMapDRComponents;
		//end: exporting all SStructure, SSpan and SToken elements connected with SDominanceRelation
		
		//start: Export all tokens who aren't connected by (SSPANNING_RELATION, SPOINTING_RELATION or SDOMINANCE_RELATION)
			timeToMapLonlyComponents= System.nanoTime();
			logger.debug("[RelANNISExporter] "+getsDocGraph().getSElementId().getSId()+ ": relANNISExporter computing components for SToken without relation.");
			EList<SToken> sTokens= this.getsDocGraph().getSTokens();
			if (sTokens!= null)
			{
				int alreadyProcessedTokens= 0;
				int percentage= 0;
				for (SToken sToken: sTokens)
				{
					alreadyProcessedTokens++;
					if (((alreadyProcessedTokens *100/ sTokens.size()) - percentage)>=percentageThreshold)
					{
						percentage= alreadyProcessedTokens *100/ sTokens.size();
						currentProgress= currentProgress+ percentage* 0.20;
					}

					//if element does not already have been stored
					if (this.sElementId2RANode.get(sToken.getSElementId())== null)
					{//token found, which has not already been visited
						this.currRaComponent= relANNISFactory.eINSTANCE.createRAComponent();
						
						this.currTravInfo= null;
						this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN;
						this.currNodeIsRoot= true;
						EList<SNode> startNodes= new BasicEList<SNode>();
						startNodes.add(sToken);
						//sets traversion to be not cycle safe
						try{
							this.getsDocGraph().traverse(startNodes, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "export_tokens", this, false);
						}catch (Exception e) {
							throw new PepperModuleException("Some error occurs while traversing corpus structure graph.", e);
						}
					}//token found, which has not already been visited
				}
			}
			timeToMapLonlyComponents= System.nanoTime() - timeToMapLonlyComponents;
			if (	(sDocGraph.getSAudioDSRelations()!= null)&&
					(sDocGraph.getSAudioDSRelations().size()>0))
			{//start: map SAudioDataSource
				
				for (SAudioDataSource audio: sDocGraph.getSAudioDataSources())
				{//create alibi annotation for audio data, so that relANNIS model can copy them
					RANodeAnnotation raNodeAnnotation= relANNISFactory.eINSTANCE.createRANodeAnnotation();
					raNodeAnnotation.setSName("audio");
					raNodeAnnotation.setSValue(audio.getSAudioReference());
					raNodeAnnotation.setSValueType(SDATATYPE.SURI);
					this.getRaDocGraph().getRaNodes().get(0).addSAnnotation(raNodeAnnotation);
				}//create alibi annotation for audio data, so that relANNIS model can copy them
				
				for (SAudioDSRelation audioRel: sDocGraph.getSAudioDSRelations())
				{
					if (audioRel.getSToken()!= null)
					{//create artificial annotation for audiosequence of SAudioDSRelation
						RANode raToken= sElementId2RANode.get(audioRel.getSToken().getSElementId());
						RANodeAnnotation raTokenAnno= relANNISFactory.eINSTANCE.createRANodeAnnotation();
						raTokenAnno.setRaNamespace(relANNISFactory.ATT_NS_ANNIS);
						raTokenAnno.setRaName(relANNISFactory.ATT_NAME_TIME);
						String value= "";
						if (audioRel.getSStart()!= null)
							value= audioRel.getSStart().toString();
						if (audioRel.getSEnd()!= null)
							value= value+ "-"+ audioRel.getSEnd().toString();
						raTokenAnno.setRaValue(value);
						raToken.addSAnnotation(raTokenAnno);
					}//create artificial annotation for audiosequence of SAudioDSRelation
				}
			}//end: map SAudioDataSource
			
			StringBuilder logStr= new StringBuilder();
			logStr.append("time to map document: "+this.getsDocGraph().getSDocument().getSName()+"\n");
			logStr.append("\ttime to map spanning-relation-components:\t"+(timeToMapSRComponents/ 1000000)+"\n");
			logStr.append("\ttime to map dominance-relation-components:\t"+(timeToMapDRComponents/ 1000000)+"\n");
			logStr.append("\ttime to map pointing-relation-components:\t"+(timeToMapPRComponents/ 1000000)+"\n");
			logStr.append("\ttime to map lonely-components:\t\t"+(timeToMapLonlyComponents/ 1000000)+"\n");
			logger.debug("[RelANNISExporter] "+logStr.toString());
		//end: Export all tokens who aren't connected by (SSPANNING_RELATION, SPOINTING_RELATION or SDOMINANCE_RELATION)
			
			//start: exporting all SStructuredNodes connected with SPointingRelation
			logger.debug("[RelANNISExporter] "+getsDocGraph().getSElementId().getSId()+ ": relANNISExporter computing components for SPointingRelation...");
			timeToMapPRComponents= System.nanoTime();
			this.traverseBySRelation(SPointingRelation.class);
			timeToMapPRComponents= System.nanoTime() - timeToMapPRComponents;
		//end: exporting all SStructuredNodes connected with SPointingRelation
			
		//start: exporting all SNodes connected with SOrderRelation
			logger.debug("[RelANNISExporter] "+getsDocGraph().getSElementId().getSId()+ ": relANNISExporter computing components for SOrderRelation...");
			if (sDocGraph.getSOrderRelations().size()> 0)
			{
				Map<String, EList<SNode>> roots= this.getsDocGraph().getRootsBySRelationSType(STYPE_NAME.SORDER_RELATION);
				if (roots!= null)
				{
					Set<String> segmentNames= roots.keySet();
					double percentage= 0;
					int alreadyProcessedRoots= 0;
					for (String segmentName: segmentNames)
					{//walk through every slot
						//sets traversion to be not cycle safe
						try{
							SOrderRelationTraverser traverser= new SOrderRelationTraverser();
							traverser.sElementId2RANode= this.sElementId2RANode;
							this.getsDocGraph().traverse(roots.get(segmentName), GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "sOrderRelation", traverser, true);
							alreadyProcessedRoots++;
							percentage= alreadyProcessedRoots/ roots.size();
							currentProgress= currentProgress+ percentage* 0.20;
						}catch (Exception e) {
							throw new PepperModuleException("Some error occurs while traversing corpus structure graph.", e);
						}
					}
				}			
			}
		//end: exporting all SNodes connected with SOrderRelation
	}
	
	/** determines the threshold of process, which invokes a notification about it **/
	private static final int percentageThreshold= 10;
	
	/**
	 * 
	 * @param roots
	 * @param factor for computing the progress, factor means percentage of the current run of this method.
	 */
	private void traverseBySRelation2(EList<SNode> roots, double factor)
	{
		if (roots!= null)
		{
			int alreadyProcessedRoots= 0;
			int percentage= 0;
			for (SNode subRoot: roots)
			{//walk through every root
				alreadyProcessedRoots++;
				if (((alreadyProcessedRoots *100/ roots.size()) - percentage)>=percentageThreshold)
				{//notify the pepper-framework about progress
					percentage= alreadyProcessedRoots * 100/ roots.size();
					currentProgress= currentProgress+ percentage* factor;
				}//notify the pepper-framework about progress
				
				this.currRaComponent= relANNISFactory.eINSTANCE.createRAComponent();
				if (	(roots== null) ||
						(roots.size()== 0))
					throw new PepperModuleException("Cannot traverse through document structure, because there is no SNode -object as root.");
				this.currNodeIsRoot= true;
				EList<SNode> startNodes= new BasicEList<SNode>();
				startNodes.add(subRoot);
				//sets traversion to be not cycle safe
				try{
					this.getsDocGraph().traverse(startNodes, GRAPH_TRAVERSE_TYPE.TOP_DOWN_DEPTH_FIRST, "export_tokens", this, false);
				}catch (Exception e) {
					throw new PepperModuleException("Some error occurs while traversing document structure.", e);
				}
			}
		}
	}
	
	/**
	 * Traverses the graph by using the given SRelation type. For SDominanceRelation, this methode traverses
	 * all super components and sub components. For SSpanning relation this method traverses all super 
	 * components. And for SPointing relation this method traverses all sub components.
	 */
	private void traverseBySRelation(Class<? extends SRelation> clazz)
	{
		if (clazz== null)
			throw new PepperModuleException("Cannot compute roots for given SRelation subtype, becuase it is null.");
		
		//maps the given class name to a Salt sType name.
//		STYPE_NAME sType= SaltFactory.eINSTANCE.convertClazzToSTypeName(clazz);
		STYPE_NAME sType= null;
		
		if (SPointingRelation.class.isAssignableFrom(clazz)){
			sType= STYPE_NAME.SPOINTING_RELATION;
		}else if (SSpanningRelation.class.isAssignableFrom(clazz)){
			sType= STYPE_NAME.SSPANNING_RELATION;
		}else if (SDominanceRelation.class.isAssignableFrom(clazz)){
			sType= STYPE_NAME.SDOMINANCE_RELATION;
		}
		
		if (clazz.equals(SSpanningRelation.class))
		{//exporting all SStructure, SSpan and SToken elements connected with SSpanningRelation
			EList<SNode> superRoots= null;
			//start: computing roots
				superRoots= this.getsDocGraph().getRootsBySRelation(sType);
			//end: computing roots
			//start: traverse for every super connected components root
				this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR;
				this.traverseBySRelation2(superRoots, 0.20);
			//end: traverse for every super connected components root
		}//exporting all SStructure, SSpan and SToken elements connected with SSpanningRelation
		else if (clazz.equals(SDominanceRelation.class))
		{//exporting all SStructure, SSpan and SToken elements connected with SDominanceRelation
			//start: traversing super components 
				EList<SNode> superRoots= null;
				//computing roots
					superRoots= this.getsDocGraph().getRootsBySRelation(sType);
				//computing roots
				//start:traverse for every super connected components root
					this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR;
					this.traverseBySRelation2(superRoots, 0.1);
				//end:traverse for every super connected components root
			//end:/traversing super components
			
			//start: traverse for every sub connected components root
				Map<String, EList<SNode>> rootsOfSubConnectedComponents= null;
				//computing roots
					rootsOfSubConnectedComponents= this.getsDocGraph().getRootsBySRelationSType(sType);
				//computing roots
				if (	(rootsOfSubConnectedComponents!= null) &&
						(rootsOfSubConnectedComponents.size() > 0))
				{	
					Set<String> slots= rootsOfSubConnectedComponents.keySet();
					for (String subComponentSlot: slots)
					{//walk through every slot
						this.currComponentId= subComponentSlot;
						this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB;
						this.traverseBySRelation2(rootsOfSubConnectedComponents.get(subComponentSlot), 0.1);
					}//walk through every slot
				}
			//end: traverse for every sub connected components root
		}//exporting all SStructure, SSpan and SToken elements connected with SDominanceRelation
		else if (clazz.equals(SPointingRelation.class))
		{//exporting all SStructuredNodes connected with SPointingRelation
			this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR;
			Map<String, EList<SNode>> roots= this.getsDocGraph().getRootsBySRelationSType(sType);
			if (roots!= null)
			{
				Set<String> slots= roots.keySet();
				for (String subComponentSlot: slots)
				{//walk through every slot
					
					this.currComponentId= subComponentSlot;
					this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB;
					this.traverseBySRelation2(roots.get(subComponentSlot), 0.20);
				}
			}			
		}//exporting all SStructuredNodes connected with SPointingRelation
	}
	
	/**
	 * stores sElementIds and corresponding RAText-objects.
	 */
	private Map<SElementId, RAText> sElementId2RAText= null;
	
	/**
	 * stores sElementIds and corresponding RANode-objects.
	 */
	private Map<SElementId, RANode> sElementId2RANode= null;
	/**
	 * Maps a STextualDS-object to a RAText-object.
	 * @param sText
	 * @param raText
	 */
	protected void mapSTextualDS2RAText(STextualDS sText, RAText raText)
	{
		if (sText== null)
			throw new PepperModuleException("Cannot map the STextualDS-object to the given RAText-object, because sText is empty.");
		if (raText== null)
			throw new PepperModuleException("Cannot map the STextualDS-object to the given RAText-object, because raText is empty.");
		raText.setRaText(sText.getSText());
		raText.setRaName(sText.getSName());
		if (sElementId2RAText== null)
			this.sElementId2RAText= new Hashtable<SElementId, RAText>();
		sElementId2RAText.put(sText.getSElementId(), raText);
	}

	/**
	 * Stores the sToken-objects ordered by the left token position 
	 */
	private EList<SToken> sTokenSortByLeft= null;

//	/**
//	 * Default name for namespaces if they cannot be computed by the layers name.
//	 */
//	private final static String DEFAULT_NS= "default_ns";
//	/**
//	 * Prefix for token-namespaces, for example if token has namespace xyz, the namespace
//	 * token_xyz will be created. 
//	 */
//	private final static String TOKEN_NS_PREFIXNS= "token";
	
	/**
	 * This table stores all names of raNodes which already has been used.
	 */
	private Hashtable<String, String> alreadyExistingRANames= null;
	
	/**
	 * Maps a SToken-object to a RANode-object. Also all annotations will be mapped.
	 * @param sToken
	 * @param raLeft
	 * @param raRight
	 * @param raText
	 * @param raToken
	 */
	protected void mapSToken2RANode(	SToken sToken,
										RAText raText,
										RANode raToken)
	{
		if (sToken== null)
			throw new PepperModuleException("Cannot map the SToken-object to the given RANode-object, because sToken is empty.");
		if (raToken== null)
			throw new PepperModuleException("Cannot map the SToken-object to the given RANode-object, because raToken is empty.");
		if (raText== null)
			throw new PepperModuleException("Cannot map the SToken-object to the given RANode-object, because the given raText is null. ");
			
		if (this.sTokenSortByLeft== null)
		{
			this.sTokenSortByLeft= this.getsDocGraph().getSortedSTokenByText();
		}
		{//namespace (because of syntax visualisation in ANNIS, token and corresponding syntactic nodes shall not have the same namespace)
			StringBuffer namespace= new StringBuffer();
			namespace.append(RelANNISResource.TOKEN_NS_PREFIXNS);
			if (	(sToken.getSLayers()!= null) &&
					(sToken.getSLayers().size()!= 0))
			{//a namespace can be taken from layers name
				if (sToken.getSLayers().get(0)!= null)
				{	
					namespace.append("_");
					namespace.append(sToken.getSLayers().get(0).getSName());
				}
			}//a namespace can be taken from layers name
			raToken.setRaNamespace(namespace.toString());
			//namespace is set to token layer, because of vizualizing of tree don't work else
		}//namespace
		
		EList<STYPE_NAME> sTypes= new BasicEList<STYPE_NAME>();
		sTypes.add(STYPE_NAME.STEXT_OVERLAPPING_RELATION);
		SDataSourceSequence sequence= this.getsDocGraph().getOverlappedDSSequences(sToken, sTypes).get(0);
		
		Long left= new Long(sequence.getSStart());
		Long right= new Long(sequence.getSEnd());
		
		if (left < 0)
			throw new PepperModuleException("Cannot map the given SToken-object '"+sToken.getSId()+"' to RAToken, because its left-value '"+left+"' is smaller than 0.");
		if (right > raText.getRaText().length())
		{
			//create text for error message, out of SText, which is at maximum 50 characters long
			String errorSText= (raText.getRaText()!= null)?((raText.getRaText().length()<50)?raText.getRaText().substring(0, raText.getRaText().length()):raText.getRaText().substring(0, 50)):"";
			throw new PepperModuleException("Cannot map the given SToken-object '"+sToken.getSId()+"' to RAToken, because its right-value '"+right+"' is bigger than the size of the text ("+((raText.getRaText()!=null)?raText.getRaText().length():0)+"): "+errorSText+"... .");
		}
			
		{//map name, name must be unique
			StringBuffer name= new StringBuffer();
			name.append(sToken.getSName());
			int i= 1;
			while(this.alreadyExistingRANames.containsKey(name.toString()))
			{
				name.delete(0, name.length());
				name.append(sToken.getSName()+"_"+i);
				i++;
			}
			raToken.setRaName(name.toString());
			this.alreadyExistingRANames.put(raToken.getRaName(), "");
		}//map name, name must be unique
		
		//map text 
		raToken.setRaText(raText);
		//map left
		raToken.setRaLeft(left);
		//map right
		raToken.setRaRight(right);
		//map continuous
		raToken.setRaContinuous(true);
		//map token_index
		raToken.setRaToken_Index(Long.valueOf(this.sTokenSortByLeft.indexOf(sToken)));
		
		for (SAnnotation sAnno: sToken.getSAnnotations())
		{//map annotations
			RANodeAnnotation raTokenAnno= this.mapSAnnotation2RANodeAnnotation(sAnno, raToken);
			raToken.addSAnnotation(raTokenAnno);
		}//map annotations
	}
	
	/**
	 * Maps a SStructuredNode-object to a RANode-object. Also all annotations will be mapped.
	 * @param sStructuredNode
	 * @param raText
	 * @param raStructuredNode
	 */
	@SuppressWarnings("unchecked")
	protected void mapSStructuredNode2RANode(	SStructuredNode sStructuredNode,
												Long raLeft,
												Long raRight,
												RAText raText,
												RANode raStructuredNode)
	{
		if (sStructuredNode== null)
			throw new PepperModuleException("Cannot map the SStructuredNode-object to the given RANode-object, because sStructuredNode is empty.");
		if (raStructuredNode== null)
			throw new PepperModuleException("Cannot map the SStructuredNode-object to the given RANode-object, because raStructuredNode is empty.");
		
		EList<STYPE_NAME> sTypes= new BasicEList<STYPE_NAME>();
		sTypes.add(STYPE_NAME.STEXT_OVERLAPPING_RELATION);
		EList<SDataSourceSequence> overlappedDSSequences= this.getsDocGraph().getOverlappedDSSequences(sStructuredNode, sTypes);
		if (	(overlappedDSSequences== null)||
				(overlappedDSSequences.size()==0))
		{
			logger.debug("[RelANNISExporter] "+"Cannot map SStructuredNode object '"+sStructuredNode.getSId()+"' to ra-node, because it does not overlap a text.");
		}
		else
		{
			SDataSourceSequence overlapedSequence= overlappedDSSequences.get(0);
			
			if (overlapedSequence.getSStart()== null)
				throw new PepperModuleException("Cannot map the given SStructuredNode-object '"+sStructuredNode.getSId()+"', because it doesn't have a left (start-value) border, pointing to the primary data.");
			if (overlapedSequence.getSEnd()== null)
				throw new PepperModuleException("Cannot map the given SStructuredNode-object '"+sStructuredNode.getSId()+"', because it doesn't have a right (end-value) border, pointing to the primary data.");
			Long left= new Long(overlapedSequence.getSStart());
			Long right= new Long(overlapedSequence.getSEnd());
			
			
			{//namespace
				String namespace= RelANNISResource.DEFAULT_NS;
				if (	(sStructuredNode.getSLayers()!= null) &&
						(sStructuredNode.getSLayers().size()!= 0))
				{//a namespace can be taken from layers name
					if (sStructuredNode.getSLayers().get(0)!= null)
					{	
						namespace= sStructuredNode.getSLayers().get(0).getSName();
					}
				}//a namespace can be taken from layers name
				raStructuredNode.setRaNamespace(namespace);
			}//namespace
			
			if (left < 0)
				throw new PepperModuleException("Cannot map the given SStructuredNode-object '"+sStructuredNode.getSId()+"' to RAStructuredNode, because its left-value '"+left+"' is smaller than 0.");
			if (right > raText.getRaText().length())
				throw new PepperModuleException("Cannot map the given SStructuredNode-object '"+sStructuredNode.getSId()+"' to RAStructuredNode, because its right-value '"+right+"' is bigger than the size of the text '"+raText.getRaText().length()+"'.");
			
			//start: map name, name must be unique
				StringBuffer name= new StringBuffer();
				name.append(sStructuredNode.getSName());
				int i= 1;
				while(this.alreadyExistingRANames.containsKey(name.toString()))
				{
					name.delete(0, name.length());
					name.append(sStructuredNode.getSName()+"_"+i);
					i++;
				}
				raStructuredNode.setRaName(name.toString());
				this.alreadyExistingRANames.put(raStructuredNode.getRaName(), "");
			//end: map name, name must be unique
		
			
			//map left
			raStructuredNode.setRaLeft(left);
			//map right
			raStructuredNode.setRaRight(right);
			
			if (this.sTokenSortByLeft== null)
			{//compute correct order of tokens and store it, because of performance do it also here 
				this.sTokenSortByLeft= this.getsDocGraph().getSortedSTokenByText();
			}//compute correct order of tokens and store it, because of performance do it also here	
			
			EList<SToken> sTokens= this.getsDocGraph().getSTokensBySequence(overlapedSequence);
			raStructuredNode.setRaContinuous(this.getsDocGraph().isContinuousByText((EList<SNode>) (EList<? extends SNode>)sTokens, (EList<SNode>) (EList<? extends SNode>)this.sTokenSortByLeft));
			
			//map text 
			raStructuredNode.setRaText(raText);
			for (SAnnotation sAnno: sStructuredNode.getSAnnotations())
			{//map annotations
				RANodeAnnotation raStructuredNodeAnno= this.mapSAnnotation2RANodeAnnotation(sAnno, raStructuredNode);
				try
				{
					raStructuredNode.addSAnnotation(raStructuredNodeAnno);
				}
				catch (GraphInsertException e) {
					logger.warn("An annotation of node '"+sStructuredNode.getSId()+"' could not be mapped to relANNIS. "+e);
				}
			}//map annotations
		}
	}
	
	/**
	 * Maps a {@link SAnnotation} object to a {@link RANodeAnnotation} object and returns the created {@link RANodeAnnotation} object.
	 * The created object will have the {@link SAnnotation} object as reference. 
	 * @param sAnno
	 * @param raNodeAnnotation
	 */
	protected RANodeAnnotation mapSAnnotation2RANodeAnnotation(	SAnnotation sAnno, 
													RANode raNode)
	{
		if (sAnno== null)
			throw new PepperModuleException("Cannot map the SAnnotation-object to the given RANodeAnnotation-object, because sAnnotation is empty.");
		RANodeAnnotation raNodeAnno= relANNISFactory.eINSTANCE.createRANodeAnnotation(sAnno);
		
//		{//compute namespace from layer
//			String namespace= null;
//			if (	(sAnno.getSAnnotatableElement() instanceof SNode) &&
//					(((SNode)sAnno.getSAnnotatableElement()).getSLayers()!= null) &&
//					(((SNode)sAnno.getSAnnotatableElement()).getSLayers().size()!= 0))
//			{//a namespace can be taken from layers name
//				if (((SNode)sAnno.getSAnnotatableElement()).getSLayers().get(0)!= null)
//				{	
//					namespace= ((SNode)sAnno.getSAnnotatableElement()).getSLayers().get(0).getSName();
//				}
//			}//a namespace can be taken from layers name
//			else namespace= DEFAULT_NS;
//			raNodeAnno.setRaNamespace(namespace);
//		}//compute namespace from layer
		
		return(raNodeAnno);
	}
		
	/**
	 * Maps a SAnnotation-object to a RAEdgeAnnotation-object.
	 * If no namespace is given by the layer of the corresponding SRelation object, it will be
	 * set to the default namespace.  
	 * @param sAnno
	 * @param raNodeAnnotation
	 */
	protected RAEdgeAnnotation mapSAnnotation2RAEdgeAnnotation(	SAnnotation sAnno)
	{
		if (sAnno== null)
			throw new PepperModuleException("Cannot map the SAnnotation-object to the given RANodeAnnotation-object, because sAnnotation is empty.");
		
		RAEdgeAnnotation raEdgeAnno= relANNISFactory.eINSTANCE.createRAEdgeAnnotation(sAnno);
		
		{//namespace
			String namespace= null;
			if (	(sAnno.getSAnnotatableElement() instanceof SRelation) &&
					(((SRelation)sAnno.getSAnnotatableElement()).getSLayers()!= null) &&
					(((SRelation)sAnno.getSAnnotatableElement()).getSLayers().size()!= 0))
			{//a namespace can be taken from layers name
				if (((SRelation)sAnno.getSAnnotatableElement()).getSLayers().get(0)!= null)
				{	
					namespace= ((SRelation)sAnno.getSAnnotatableElement()).getSLayers().get(0).getSName();
				}
			}//a namespace can be taken from layers name
			else namespace= RelANNISResource.DEFAULT_NS;
			raEdgeAnno.setRaNamespace(namespace);
		}//namespace
		
		return(raEdgeAnno);
	}
		
	/**
	 * Namespace for visualization type for grid viewable data.
	 */
	protected static String VIZ_TYPE_GRID="exmaralda";
	/**
	 * Namespace for visualization type for tree viewable data.
	 */
	protected static String VIZ_TYPE_TREE="tiger";
	/**
	 * Namespace for visualization type for ??? pr??? viewable data.
	 */
	protected static String VIZ_TYPE_PR="mmax";
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
	protected String getVizType(SNode owningSNode)
	{
		String vizType= null;
		if (owningSNode instanceof SToken)
			vizType= VIZ_TYPE_KWIC;
		else if (owningSNode instanceof SSpan)
			vizType= VIZ_TYPE_GRID;
		else if (owningSNode instanceof SStructure)
		{//compute mmax and tiger
			boolean hasSPointingRel= false;
			{//if node takes part in SPointingRelations chains
				for (Edge edge: this.getsDocGraph().getOutEdges(owningSNode.getSId()))
				{
					if (edge instanceof SPointingRelation)
					{
						hasSPointingRel= true;
						break;
					}
				}
				if (!hasSPointingRel)
				{//check only if node doesn't still contains pointing relations	
					for (Edge edge: this.getsDocGraph().getInEdges(owningSNode.getSId()))
					{
						if (edge instanceof SPointingRelation)
						{
							hasSPointingRel= true;
							break;
						}
					}
				}//check only if node does not still contains pointing relations
			}//if node takes part in SPointingRelations chains
			if (hasSPointingRel)
				vizType= VIZ_TYPE_PR;
			else vizType=VIZ_TYPE_TREE;
		}//compute mmax and tiger
		return(vizType);
	}
	/**
	 * This method is a simple heuristic to create a namespace for annotations out of the kind of
	 * owning node or owning edge.
	 * SSpanningNode --> "exmaralda"
	 * SSStructure --> "mmax" if node has an SPointingRelation as in or outgoing relation, "tiger" otherwise
	 *  
	 *  
	 * @param owningSNode the node which owns annotation
	 * @return returns a namespace for annis visualization
	 */
	protected String getVizType(SAnnotation sAnno)
	{
		String vizType= null;
		if (sAnno.getSAnnotatableElement()!= null)
		{//owning element is not null	
			if (sAnno.getSAnnotatableElement() instanceof SNode)
			{//sAnnotatableElement is SNode
				vizType= this.getVizType((SNode) sAnno.getSAnnotatableElement());
			}//sAnnotatableElement is SNode
			else if (sAnno.getSAnnotatableElement() instanceof SRelation)
			{
				SRelation owningSRel= (SRelation) sAnno.getSAnnotatableElement();
				if (owningSRel instanceof SSpanningRelation)
					vizType= VIZ_TYPE_GRID;
				else if (owningSRel instanceof SDominanceRelation)
					vizType= VIZ_TYPE_TREE;
				else if (owningSRel instanceof SPointingRelation)
					vizType= VIZ_TYPE_PR;
			}
		}//owning element isn' t null
		else throw new PepperModuleException("Cannot create a namespcae for an annotation, because it has no sAnnotatbaleElement.");
		return(vizType);
	}
	
	/**
	 * counter for pre and post order
	 */
	private static Long ppOrder= null;
	/**
	 * returns a new and unique ppOrder
	 * @return
	 */
	private static synchronized Long getPPOrder()
	{
		if (ppOrder== null)
			ppOrder= 0l;
		Long retVal= ppOrder;
		ppOrder++;
		return(retVal);
	}
	
	/**
	 * Maps the given SRelation-object to the given RARank-object. Also sets the RAComponent.
	 * @param sRel sRelation-object to map
	 * @param sNode the SNode object, which contains the pre value
	 * @param raComponent RAComponent-object, to which the mapped RARank-object belongs to
	 * @param raParentRank RARank object, which shall be parent of this object
	 * @param raNode raRank object, which is target of the created RARank object
	 * @param raRank RARank-object which shall be filled
	 */
	protected void mapSRelation2RARank(	SRelation sRel, 
										SNode sNode,
										RAComponent raComponent,
										RARank raParentRank,
										RANode raNode,
										RARank raRank)
	{
		if (raRank== null)
			throw new PepperModuleException("Cannot map the SRelation-object to the given RARank-object, because raRank is empty.");
		if (raComponent== null)
			throw new PepperModuleException("Cannot map the SRelation-object to the given RARank-object, because raComponent is empty.");
		if (raNode== null)
			throw new PepperModuleException("Cannot map the SRelation-object to the given RARank-object, because raNode is empty.");
	
		SProcessingAnnotation sProcAnnotation= (SProcessingAnnotation) sNode.getLabel(KW_NS, KW_NAME_PRE);
		if (sProcAnnotation== null)
			throw new PepperModuleException("Cannot map the SRelation-object to the given RARank-object, because there is no pre value found for this relation '"+sRel.getSId()+"'.");
		else 
		{
			raRank.setRaPre((Long)sProcAnnotation.getSValue());
		}
		raRank.setRaPost(getPPOrder());
		raRank.setRaComponent(raComponent);
		raRank.setRaNode(raNode);
		if (raParentRank!= null)
		{	
			raRank.setRaParentRank(raParentRank);
			raParentRank.setRaParentNode(raParentRank.getRaNode());
		}
		if (sRel!= null)
		{	
			for (SAnnotation sAnno: sRel.getSAnnotations())
			{//map all annotations
				RAEdgeAnnotation raEdgeAnno= this.mapSAnnotation2RAEdgeAnnotation(sAnno);
				raRank.addSAnnotation(raEdgeAnno);
			}//map all annotations
		}
	}
	/**
	 * Maps the given SRelation-object to the given raCompent
	 */
	protected void map2RAComponent(	TRAVERSION_TYPE traversionType, 
									SRelation sRel,
									RAComponent raComponent)
	{
		if (raComponent== null)
			throw new PepperModuleException("Cannot map the given RAComponent-object, because raComponent is empty.");
		if (sRel!= null)
		{	
			if (sRel instanceof SSpanningRelation)
				raComponent.setRaType(RA_COMPONENT_TYPE.C);
			else if (sRel instanceof SDominanceRelation)
				raComponent.setRaType(RA_COMPONENT_TYPE.D);
			else if (sRel instanceof SPointingRelation)
				raComponent.setRaType(RA_COMPONENT_TYPE.P);
			if (	(traversionType.equals(TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB)||
					(traversionType.equals(TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))))
			{//only map subtype, if it is a sub coherent component		
				if (	(sRel.getSTypes()!= null)&&
						(sRel.getSTypes().size()> 0))
				{	
					String sTypes= "";
					for (String sType: sRel.getSTypes())
					{	
						if ("".equals(sTypes))
							sTypes= sType;
						else sTypes= sTypes+ ":" + sType;
					}
					raComponent.setRaName(sTypes);
				}
				else if (	(TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB.equals(traversionType)&&
							(	(sRel.getSTypes()!= null)||
								(sRel.getSTypes().size()> 0))))
				{//set subtype to edge if traversion type is DR and no subtype is set
					raComponent.setRaName("edge");
				}//set subtype to edge if traversion type is DR and no subtype is set
			}//only map subtype, if it is a sub coherent component
		}
		else 
		{
			raComponent.setRaType(null);
		}
		{//map namespace
			if (sRel!= null)
			{
				if (	(sRel.getSLayers()!= null) &&
						(sRel.getSLayers().size()> 0))
				{//corresponding SRelation has a layer
					raComponent.setRaNamespace(sRel.getSLayers().get(0).getSName());
				}//corresponding SRelation has a layer
				else
				{//corresponding SRelation has no layer
					raComponent.setRaNamespace(RelANNISResource.DEFAULT_NS);
				}//corresponding SRelation has no layer
			}
		}//map namespace
	}
	
// ================================================ end: mapping document structure
	
	/**
	 * stores the last rarank.
	 */
	private Stack<RARank> lastRARank= null;
	/**
	 * stores the last SCorpus-object
	 */
	private Stack<RACorpus> lastRACorpus= null;
	
	/**
	 * Stores the current component id for current sub component
	 */
	private String currComponentId= null;
	
	@Override
	public boolean checkConstraint(	GRAPH_TRAVERSE_TYPE traversalType,
									String traversalId, 
									SRelation edge, 
									SNode currNode, 
									long order)	
	{
		{//just for debug
//			if (this.getLogService()!= null)
//			{
//				StringBuilder debug= new StringBuilder();
//				debug.append("check constraint ("+this.currTraversionType+"): "+ currNode.getId());
//				if (	(edge!= null) &&
//						(edge instanceof SRelation))
//				{
//					debug.append(", by edge: "+ edge.getSource().getId()+ " -->[ "+ edge.getClass().getName()+"|"+((SRelation)edge).getSTypes()+"] "+edge.getTarget().getId());
//				}
//				this.getLogService().log(LogService.LOG_DEBUG, debug.toString());
//			}
//			System.out.print("check constraint: "+ currNode.getId());
//			if (edge!= null)
//				System.out.print(", by edge: "+ edge.getSource().getId()+ " --> "+ edge.getTarget().getId());
//			System.out.println();
		}//just for debug
		
		Boolean retVal= false;
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (	(currNode instanceof SCorpus)||
					(currNode instanceof SDocument))
			{
				retVal= true;
			}
		}//traversing corpus structure
		{//traversing document structure
			if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
			{
				{//if relation is SDominanceRelation and has no type, set one 
					if (	(edge!= null)&&
							(edge instanceof SDominanceRelation))
					{
						if (	(((SRelation)edge).getSTypes()== null) ||
								(((SRelation)edge).getSTypes().size()==0))
						{
							((SRelation)edge).addSType("edge");
						}
					}
							
				}//if relation is SDominanceRelation and has no type, set one
				SRelation sRelation= (SRelation) edge;
				if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN)
				{//just traversing tokens
					if (	
							(	(edge== null)||
								(edge instanceof STextualRelation)))
						retVal= true;
				}//just traversing tokens
				else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR)
				{//traversing document structure with spanning relations
					if (currNode instanceof SNode)
					{
						if (edge== null)
							retVal= true;
						else if (	(edge instanceof SSpanningRelation) ||
								(edge instanceof STextualRelation))
							retVal= true;
					}
				}//traversing document structure with spanning relations
				else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR)
				{//traversing document structure with dominance relations	
					if (currNode instanceof SNode)
					{
						if (edge== null)
							retVal= true;
						else if (	(edge instanceof SDominanceRelation) ||
									(edge instanceof STextualRelation))
							retVal= true;
					}
				}//traversing document structure with dominance relations
				else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR)
				{//traversing document structure with pointing relations	
					if (currNode instanceof SNode)
					{
						if (sRelation== null)
							retVal= true;
						else if (	(sRelation instanceof SPointingRelation) ||
									(sRelation instanceof STextualRelation))
						{//only go on, if relation isn't visited already
							if (!hasVisited(sRelation))
								retVal= true;
							else retVal= false;
							this.markAsVisited(sRelation);
						}
					}
				}//traversing document structure with pointing relations
				else if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB)||
							(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
				{//traversion of sub components
					if (	(sRelation!= null) &&
							(sRelation.getSTypes()!= null) &&
							(sRelation.getSTypes().size()> 0))
					{//do only if relation has a sub type
						//if the current sRelation belongs to current component
						if (this.currComponentId.equals(this.computeConnectedComponentId(sRelation)))
							retVal= true;
						//if the current sRelation doesn't belongs to current component
						else retVal= false;
					}//do only if relation has a sub type
					else if (sRelation== null)
					{//first relation of sub component has to be null
						retVal= true;
					}//first relation of sub component has to be null
					else retVal= false;
				}//traversion of sub components
			}
		}//traversing document structure
		return(retVal);
	}

	/**
	 * Computes a String value for identifiying a sub component and returns it. Component id
	 * is created out of all sTypes of sRelation. 
	 * @param sRelation
	 * @return
	 */
	private String computeConnectedComponentId(SRelation sRelation)
	{
		String retVal= null;
		{//compute connected component id
			if (	(sRelation.getSTypes()!= null)&&
					(sRelation.getSTypes().size()> 0))
			{	
				StringBuffer sTypes= new StringBuffer();
				for (String sType: sRelation.getSTypes())
				{	
					if ("".equals(sTypes.toString()))
						sTypes.append(sType);
					else sTypes.append(sTypes.toString()+ ":" + sType);
				}
				retVal= sTypes.toString();
			}
		}//compute connected component id
		return(retVal);
	}
	
	/**
	 * stores the RAText-object, which is currently overlapped.
	 */
	private RAText currRaText= null;
	
	/**
	 * stores current traversion information (for Token).
	 */
	private TraversionInfos currTravInfo= null;
	
	/**
	 * stores the maximal right and minimal left position
	 */
	private TraversionInfos minMaxTravInfo= null;
	
	/**
	 * This is a helper class to store some informations, which has to be passed up through tranversion.
	 */
	private class TraversionInfos
	{
		public Integer start= null;
		public Integer end= null;
	}
	/**
	 * stores if the current node is a root for traversion
	 */
	private Boolean currNodeIsRoot= false;
	
	/**
	 * Stores the STokens, which belongs to current traversed sub tree
	 */
	private EList<SToken> currSTokens= null;
	/**
	 * stores the current ra component object
	 */
	private RAComponent currRaComponent= null;
	
	/**
	 * Stores a parent rank and corresponding child ranks to create edge.source entries 
	 */
	private Hashtable<RARank, EList<RARank>> parentRank2Rank= null;
	/**
	 * This list contains the raComponents,being contained by current raDocumentGraph. 
	 */
	//TODO remove this, when using HashEList
	private HashSet<RAComponent> containedRAComponent= new HashSet<RAComponent>();
	
	
	@Override
	public void nodeLeft(	GRAPH_TRAVERSE_TYPE traversalType, 
							String traversalId,
							SNode currNode, 
							SRelation edge, 
							SNode fromNode, 
							long order)
	{
//		{//just for debug
//			System.out.print("node left: "+ currNode.getId());
//			if (edge!= null)
//				System.out.print(", by edge: "+ edge.getSource().getId()+ " --> "+ edge.getTarget().getId());
//			System.out.println();
//		}//just for debug
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			this.lastRACorpus.pop();
		}//traversing corpus structure
		else if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
		{//traversing document structure
			if (currNode instanceof SNode)
			{	
				if (currNode instanceof SToken)
				{//adds current token to list of current tokens
					if (this.currSTokens== null)
						this.currSTokens= new BasicEList<SToken>();
					this.currSTokens.add((SToken)currNode);
				}//adds current token to list of current tokens
				SNode currSNode= (SNode)currNode;
				if (!this.hasVisited(currSNode))
				{//node has not been visited	
					if (currSNode instanceof STextualDS)
					{//current node is text	
						this.currRaText= this.sElementId2RAText.get(currSNode.getSElementId());
					}//current node is text
					else
					{//something to map to RANode
						RANode raNode= null;
						if (currNode instanceof SToken)
						{//current node is token
							raNode= relANNISFactory.eINSTANCE.createRANode();
							{//if there are more than just one text sources in document-graph
								EList<Edge> outEdges= this.getsDocGraph().getOutEdges(currSNode.getId());
								STextualDS currSTextDS= null;
								for (Edge outEdge: outEdges)
								{
									if (outEdge instanceof STextualRelation)
									{
										currSTextDS= ((STextualRelation)outEdge).getSTextualDS();
										break;
									}
								}
								if (this.sElementId2RAText== null)
									throw new PepperModuleException("No RAText object is given to corresponding to the STExtualDS '"+currSTextDS.getSElementId()+"'.");
								if (currSTextDS== null)
									throw new PepperModuleException("An exception occurs while traversing RAGraph, because the currSTextDS object is null for node '"+currSNode.getSElementId()+"'.");
								this.mapSToken2RANode((SToken)currSNode, this.sElementId2RAText.get(currSTextDS.getSElementId()) , raNode);
							}//if there are more than just one text sources in document-graph
							
							if (this.currTravInfo== null)
							{
								this.currTravInfo= new TraversionInfos();
								this.currTravInfo.start= raNode.getRaLeft().intValue();
								this.currTravInfo.end= raNode.getRaRight().intValue();
							}
							this.getRaDocGraph().addSNode(raNode);
							
						}//current node is token
						else if (currNode instanceof SSpan)
						{//current node is span
							raNode= relANNISFactory.eINSTANCE.createRANode();
							this.mapSStructuredNode2RANode((SSpan)currSNode, new Long(this.minMaxTravInfo.start), new Long(this.minMaxTravInfo.end), this.currRaText, raNode);
							this.getRaDocGraph().addSNode(raNode);
						}//current node is span
						else if (currNode instanceof SStructure)
						{//current node is structure
							raNode= relANNISFactory.eINSTANCE.createRANode();
							this.mapSStructuredNode2RANode((SStructuredNode)currSNode, Long.valueOf(this.minMaxTravInfo.start), new Long(this.minMaxTravInfo.end), this.currRaText, raNode);
							this.getRaDocGraph().addSNode(raNode);
						}//current node is structure
						this.sElementId2RANode.put(currSNode.getSElementId(), raNode);
					}//something to map to RANode
					//mark sNode as visited
					this.markAsVisited(currSNode);
				}//node has not been visited
				else 
				{//node has also been visited
					if (	(currSNode instanceof SToken)|| 
							(currSNode instanceof SSpan)||
							(currSNode instanceof SStructure))
					{
						if (this.sElementId2RANode== null)
							throw new PepperModuleException("Might be a bug, no sElementId2RANode table exists.");
						if (currSNode.getSElementId()== null)
							throw new PepperModuleException("Might be a bug, no SElementId exists for node: "+ currSNode);
						RANode raNode= this.sElementId2RANode.get(currSNode.getSElementId());
						if (raNode.getRaLeft()== null)
							throw new PepperModuleException("No raLeft value exists for node: "+ raNode+", corresponding sNode is: "+ currSNode);
						this.currTravInfo.start= raNode.getRaLeft().intValue();
						this.currTravInfo.end= raNode.getRaRight().intValue();
					}
				}//node has also been visited
				
				if (	(edge!= null)&&
						(edge instanceof STextualRelation))
				{//SRelation not null and is STextualRelation
					SRelation sRelation= (SRelation) edge;
					{//sRelation is of type TextualRelation
						if (!hasVisited(sRelation))
						{//sRelation has not been visited
							STextualRelation sTextRel= (STextualRelation) edge;
							this.currTravInfo= new TraversionInfos();
							this.currTravInfo.start= sTextRel.getSStart();
							this.currTravInfo.end= sTextRel.getSEnd();
							//mark sRelation as visited
							this.markAsVisited(sRelation);
						}//sRelation has not been visited
					}//sRelation is of type TextualRelation
					
					
				}//SRelation not null and is STextualRelation
				else if (	(edge== null) ||
							(	(edge instanceof SSpanningRelation)||
								(edge instanceof SDominanceRelation)||
								(edge instanceof SPointingRelation))) 
				{//sRelation is null or of type SSPanningRelation, SDominanceRelation or SPointingRelation
					SRelation sRelation= null;
					if (edge!= null)
						sRelation= (SRelation) edge;
					{//map RAComponent
						if (!this.containedRAComponent.contains(currRaComponent))
//						TODO: uncomment this, when using HashELIst
//						if (!this.getRaDocGraph().getRaComponents().contains(currRaComponent))
						{
							this.map2RAComponent(this.currTraversionType, sRelation, this.currRaComponent);
							this.raDocGraph.getRaComponents().add(this.currRaComponent);
//							TODO: delete line after next, when using HashEList
							this.containedRAComponent.add(this.currRaComponent);
						}
					}//map RAComponent
					
					{//map SRelation to RARank
						RANode raNode= this.sElementId2RANode.get(currSNode.getSElementId());
						if (this.lastRARank== null)
							throw new PepperModuleException("The stack lastRank is empty.");
						RARank raRank= this.lastRARank.pop();
						RARank currLastRARank= null;
						if (	(this.lastRARank!= null) &&
								(!this.lastRARank.isEmpty()))
						{	
							currLastRARank= this.lastRARank.peek();
						}
						this.mapSRelation2RARank(sRelation, currSNode, this.currRaComponent, currLastRARank, raNode, raRank);
						this.getRaDocGraph().addSRelation(raRank);
						
						{//necessary for setting edge.source to parentRank.raNode
							if (this.parentRank2Rank== null)
								this.parentRank2Rank= new Hashtable<RARank, EList<RARank>>();
							{//create an entry in list, if there is a parent rank
								if (currLastRARank!= null)
								{
									//search for slot
									EList<RARank> slot= this.parentRank2Rank.get(currLastRARank);
									if (slot== null)
									{
										slot= new BasicEList<RARank>();
										this.parentRank2Rank.put(currLastRARank, slot);
									}
									slot.add(raRank);
								}
							}//create an entry in list, if there is a parent rank
							{//check if current rank is a parent rank in parentRank2Rank table and set rank.source
								if (this.parentRank2Rank.containsKey(raRank))
								{
									for (RARank childRARank: this.parentRank2Rank.get(raRank))
										childRARank.setRaParentNode(raRank.getRaNode());
								}
							}//check if current rank is a parent rank in parentRank2Rank table and set rank.source
						}//necessary for setting edge.source to parentRank.raNode
					}//map SRelation to RARank
					
				}//sRelation is null or of type SSPanningRelation, SDominanceRelation or SPointingRelation
				{//set minMaxTravInfo
					if (this.currNodeIsRoot)
					{	
						this.minMaxTravInfo= new TraversionInfos();
					}
					if (	(	(this.minMaxTravInfo.start== null)||
								(this.currTravInfo.start < this.minMaxTravInfo.start)) &&
								(this.currTravInfo!= null))
						this.minMaxTravInfo.start= this.currTravInfo.start;
					if (	(	(this.minMaxTravInfo.end== null)||
								(this.currTravInfo.end > this.minMaxTravInfo.end)) &&
								(this.currTravInfo!= null))
						this.minMaxTravInfo.end= this.currTravInfo.end;
					this.currNodeIsRoot= false;
				}
			}
		}//traversing document structure
	}

	private String KW_NS= "s2ra";
	private String KW_NAME_PRE= "pre";

	@Override
	public void nodeReached(	GRAPH_TRAVERSE_TYPE traversalType,
								String traversalId, 
								SNode currNode, 
								SRelation edge, 
								SNode fromNode,
								long order)
	{
//		{//just for debug
//			System.out.print("node reached: "+ currNode.getId());
//			if (edge!= null)
//				System.out.print(", by edge: "+ edge.getSource().getId()+ " --> "+ edge.getTarget().getId());
//			System.out.println();
//		}//just for debug
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			RACorpus raCorpus= relANNISFactory.eINSTANCE.createRACorpus();
			this.getRaCorpusGraph().addSNode(raCorpus);
			if (currNode instanceof SCorpus)
			{
				SCorpus sCorpus= (SCorpus) currNode;
				this.mapSCorpus2RACorpus(sCorpus, raCorpus);
			}
			else if (currNode instanceof SDocument)
			{
				SDocument sDocument= (SDocument) currNode;
				this.mapSDocument2RACorpus(sDocument, raCorpus);
			}
			{//map relation
				if (this.lastRACorpus.size()> 0)
				{//if there is already a corpus read	
					RACorpusRelation raCorpRel= relANNISFactory.eINSTANCE.createRACorpusRelation();
					raCorpRel.setRaSuperCorpus(this.lastRACorpus.peek());
					raCorpRel.setRaSubCorpus(raCorpus);
					this.getRaCorpusGraph().addSRelation(raCorpRel);
				}
			}
			this.sElementId2RaId.put(((SNode) currNode).getSElementId(), raCorpus.getRaId());
			this.lastRACorpus.push(raCorpus);
		}//traversing corpus structure
		else if (	(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_TOKEN)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_CR) ||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_DR_SUB)||
					(this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_PR_SUB))
		{//traversing document structure
			{//resets list of current tokens, because  new sub tree is reached
				if (edge== null)
				{
					this.currSTokens= null;
				}
			}//resets list of current tokens, because  new sub tree is reached
			{//add pre value
				SNode currSNode= (SNode) currNode;
				if 	(	(currSNode instanceof SToken) ||
						(currSNode instanceof SSpan)||
						(currSNode instanceof SStructure))
				{	
					SProcessingAnnotation sProcAnno= null;
					sProcAnno= (SProcessingAnnotation) currSNode.getLabel(KW_NS, KW_NAME_PRE);
					if (sProcAnno== null)
					{	
						sProcAnno= SaltCoreFactory.eINSTANCE.createSProcessingAnnotation();
						sProcAnno.setSNS(KW_NS);
						sProcAnno.setSName(KW_NAME_PRE);
						currSNode.addSProcessingAnnotation(sProcAnno);
					}
					sProcAnno.setSValue(getPPOrder());
					if (this.lastRARank== null)
						this.lastRARank= new Stack<RARank>();
					this.lastRARank.push(relANNISFactory.eINSTANCE.createRARank());
				}
			}//add pre value
		}//traversing document structure
		else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE_OR)
		{//traversing SOrderRelation
			SOrderRelation sOrderRel= (SOrderRelation) edge;
//			System.out.println(fromNode.getId()+"--"+sOrderRel.getSTypes()+"-->"+currNode.getId());
		}//traversing SOrderRelation
		{//for testing
//			System.out.println("----------> node reached: "+currNode.getId());
		}//for testing			
	}	
	
// ========================================= start: handling to check if node or relation has been visited	
	private Map<SElementId, Boolean> visitedSNodes= null;
	/**
	 * Marks the given node as visited.
	 * @param raRank
	 */
	private void markAsVisited(SNode sNode)
	{
		if (this.visitedSNodes== null)
			this.visitedSNodes= new Hashtable<SElementId, Boolean>();
		this.visitedSNodes.put(sNode.getSElementId(), true);
	}
	
	/**
	 * Returns true, if raRank contains visited flag, false else.
	 * @param raRank
	 * @return
	 */
	private boolean hasVisited(SNode sNode)
	{
		if (this.visitedSNodes== null)
			this.visitedSNodes= new Hashtable<SElementId, Boolean>();
		if (visitedSNodes.containsKey(sNode.getSElementId()))
			return(true);
		else return(false);
	}
	
	private Map<SElementId, Boolean> visitedSRelations= null;
	/**
	 * Marks the given node as visited.
	 * @param raRank
	 */
	private void markAsVisited(SRelation SRelation)
	{
		if (this.visitedSRelations== null)
			this.visitedSRelations= new Hashtable<SElementId, Boolean>();
		this.visitedSRelations.put(SRelation.getSElementId(), true);
	}
	
	/**
	 * Returns true, if raRank contains visited flag, false else.
	 * @param raRank
	 * @return
	 */
	private boolean hasVisited(SRelation SRelation)
	{
		if (this.visitedSRelations== null)
			this.visitedSRelations= new Hashtable<SElementId, Boolean>();
		if (visitedSRelations.containsKey(SRelation.getSElementId()))
			return(true);
		else return(false);
	}
// ========================================= start: handling to check if node or relation has been visited
}
