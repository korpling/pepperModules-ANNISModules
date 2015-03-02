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

import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.EList;

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
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser.GRAPH_TRAVERSE_MODE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverserObject;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.TraversalObject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.SaltCommonFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SDominanceRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SPointingRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpan;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SSpanningRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SStructure;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SProcessingAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class RelANNIS2SaltMapper implements TraversalObject
{
// -------------------------start: RACorpusGraph
	private RACorpusGraph raCorpusGraph= null;

	/**
	 * Sets the corpus graph to map from.
	 * @param raCorpusGraph corpus graph to map from
	 */
	public void setRaCorpusGraph(RACorpusGraph raCorpusGraph) {
		this.raCorpusGraph = raCorpusGraph;
	}

	/**
	 * Returns the corpus graph to map from.
	 * @return the corpus graph to map from
	 */
	public RACorpusGraph getRaCorpusGraph() {
		return raCorpusGraph;
	}
// -------------------------end: RACorpusGraph
// -------------------------start: SCorpusGraph 	
	private SCorpusGraph sCorpusGraph= null;
	
	/**
	 * Sets the corpus graph to map to.
	 * @param raCorpusGraph corpus graph to map to
	 */
	public void setSCorpusGraph(SCorpusGraph sCorpusGraph) {
		this.sCorpusGraph = sCorpusGraph;
	}

	/**
	 * Returns the corpus graph to map to.
	 * @return the corpus graph to map to
	 */
	public SCorpusGraph getSCorpusGraph() {
		return sCorpusGraph;
	}
// -------------------------end: SCorpusGraph
// -------------------------start: sElementId2RaId
	private Map<SElementId, Long> sElementId2RaId= null;
	
	/**
	 * Sets a table, which will be filled with SELementId-objects corresponding
	 * to raIds.
	 * @param sElementId2RaId the sElementId2RaId to set
	 */
	public void setsElementId2RaId(Map<SElementId, Long> sElementId2RaId) {
		this.sElementId2RaId = sElementId2RaId;
	}

	/**
	 * Returns a table, which will be filled with SELementId-objects corresponding
	 * to raIds.
	 * @return the sElementId2RaId
	 */
	public Map<SElementId, Long> getsElementId2RaId() {
		return sElementId2RaId;
	}
// -------------------------end: sElementId2RaId
	
	enum TRAVERSION_TYPE {CORPUS_STRUCTURE, DOCUMENT_STRUCTURE};
	/**
	 * stores the current type of traversion
	 */
	private TRAVERSION_TYPE currTraversionType= null;

// ================================================ start: mapping corpus structure	
	/**
	 * Starts the mapping of the RACorpusGraph-object to SCOrpusGraph object.
	 * @param raCorpusGraph corpus graph to map from
	 * @param sCorpusGraph corpus graph to map to
	 */
	@SuppressWarnings("unchecked")
	public void mapRACorpusGraph2SCorpusGraph(RACorpusGraph raCorpusGraph, SCorpusGraph sCorpusGraph)
	{
		this.setRaCorpusGraph(raCorpusGraph);
		this.setSCorpusGraph(sCorpusGraph);
		if (this.getRaCorpusGraph()== null)
			throw new PepperModuleException("Cannot map raCorpusGraph to sCorpusGraph, because raCorpusGraph is null.");
		if (this.getSCorpusGraph()== null)
			throw new PepperModuleException("Cannot map raCorpusGraph to sCorpusGraph, because sCorpusGraph is null.");
		
		if (this.getsElementId2RaId()== null)
			this.setsElementId2RaId(new Hashtable<SElementId, Long>());
		GraphTraverser graphTraverser= new GraphTraverser();
		graphTraverser.setGraph(this.getRaCorpusGraph());
		GraphTraverserObject travObj= graphTraverser.getTraverserObject(GRAPH_TRAVERSE_MODE.DEPTH_FIRST, this);
		EList<RACorpus> roots= this.getRaCorpusGraph().getRARoots();
		if (	(roots== null) ||
				(roots.size()== 0))
			throw new PepperModuleException("Cannot traverse through corpus structure, because there is no raCOrpus-object as root.");
		//set traversion type to corpus structure
		this.currTraversionType= TRAVERSION_TYPE.CORPUS_STRUCTURE;
		this.lastCorpus= new Stack<SCorpus>();
		travObj.start((EList<Node>) (EList<? extends Node>) roots);
		travObj.waitUntilFinished();
		//look for exceptions while traversion
		for (Exception e:  travObj.getExceptions())
		{
			throw new PepperModuleException("Some error occurs while traversing corpus structure graph.", e);
		}
	}
	
	/**
	 * Maps the given raCorpus object to an SCorpus-object or an SDocumentObject.
	 * It also calls mapRACorpus2SMetaAnnotation.
	 * @param raCorpus
	 * @return returns the created object as SNode object
	 */
	protected SNode mapRACorpus2SCorpusSDocument(RACorpus raCorpus)
	{
		SNode corpDoc= null;
		if (raCorpus.getRaType()== RA_CORPUS_TYPE.CORPUS)
		{//raCorpus is of type corpus
			corpDoc= SaltCommonFactory.eINSTANCE.createSCorpus();
		}
		else if(raCorpus.getRaType()== RA_CORPUS_TYPE.DOCUMENT)
		{//raCorpus is of type document
			corpDoc= SaltCommonFactory.eINSTANCE.createSDocument();
		}
		corpDoc.setSName(raCorpus.getRaName());
		this.getSCorpusGraph().addSNode(corpDoc);
		{//if there is a version put it to SMetaAnnotation
			if (	(raCorpus.getRaVersion()!= null)&&
					(!raCorpus.getRaVersion().equals("")))
			{
				SMetaAnnotation sMetaAnno= SaltCommonFactory.eINSTANCE.createSMetaAnnotation();
				sMetaAnno.setSName("version");
				sMetaAnno.setSValue(raCorpus.getRaVersion());
				corpDoc.addSMetaAnnotation(sMetaAnno);
			}
		}
		{//map all RACOrpus_annotations
			if (	(raCorpus.getRaCorpusAnnotations()!= null) &&
					(raCorpus.getRaCorpusAnnotations().size() > 0))
			{
				this.mapRACorpus2SMetaAnnotation(raCorpus, corpDoc);
			}
			
		}
		this.getsElementId2RaId().put(corpDoc.getSElementId(), raCorpus.getRaId());
		return(corpDoc);
	}
	
	/**
	 * Maps the all raCorpusAnnotations of raCorpus object to SMetaAnnotationObjects. The
	 * created SMetaAnnotation objects will be added to the given sNode. 
	 * @param raCorpus 
	 * @param sNode SNode-object where SMetaANnotations shall be added
	 */
	protected void mapRACorpus2SMetaAnnotation(RACorpus raCorpus, SNode sNode)
	{
		for (RACorpusAnnotation raCorpAnno: raCorpus.getRaCorpusAnnotations())
		{
			SMetaAnnotation sMetAnno= SaltCommonFactory.eINSTANCE.createSMetaAnnotation();
			sMetAnno.setSNS(raCorpAnno.getRaNamespace());
			sMetAnno.setSName(raCorpAnno.getRaName());
			sMetAnno.setSValue(raCorpAnno.getRaValue());
			sNode.addSMetaAnnotation(sMetAnno);
		}
	}
	
	/**
	 * Maps the given raRelation to either a SCorpRelation or a SCorpDocRelation. It depends in type
	 * of destination of raCorpusRelation. 
	 * @param raCorpusRelation relation to map
	 * @return either a SCorpRelation or a SCorpDocRelation
	 */
	protected SRelation mapRACorpusRelation2SRelation(RACorpusRelation raCorpusRelation)
	{
		SRelation retVal= null;
		if (raCorpusRelation.getRaSubCorpus().getRaType()== RA_CORPUS_TYPE.CORPUS)
		{//destination is corpus
			retVal= SaltCommonFactory.eINSTANCE.createSCorpusRelation();
		}
		else if (raCorpusRelation.getRaSubCorpus().getRaType()== RA_CORPUS_TYPE.DOCUMENT)
		{//destination is document
			retVal= SaltCommonFactory.eINSTANCE.createSCorpusDocumentRelation();
		}
		this.getSCorpusGraph().addSRelation(retVal);
		return(retVal);
	}
// ================================================ end: mapping corpus structure
	private SDocumentGraph sDocGraph= null;
	/**
	 * Set a document graph, which shall be created.
	 * @param sDocGraph the sDocGraph to set
	 */
	public void setSDocGraph(SDocumentGraph sDocGraph) {
		this.sDocGraph = sDocGraph;
	}

	/**
	 * Returns a document graph, which shall be created. 
	 * @return the sDocGraph
	 */
	public SDocumentGraph getSDocGraph() {
		return sDocGraph;
	}
	
	
	private RADocumentGraph raDocumentGraph= null;
	
	/**
	 * Sets RADocument graph, to map from.
	 * @param raDocumentGraph the raDocumentGraph to set
	 */
	public void setRaDocumentGraph(RADocumentGraph raDocumentGraph) {
		this.raDocumentGraph = raDocumentGraph;
	}

	/**
	 * Returns RADocument graph, to map from.
	 * @return the raDocumentGraph
	 */
	public RADocumentGraph getRaDocumentGraph() {
		return raDocumentGraph;
	}
// ================================================ start: mapping document structure
	/**
	 * Stores raId and mapped STextualDS-object.
	 */
	private Map<Long, STextualDS> raId2STextualDS= null;
	
	/**
	 * Stores all raId's of RANode objects and their corresponding SNode objects.
	 */
	private Map<Long, SNode> raId2SNode= null;
	/**
	 * Maps a document structure containing in given RACorpus-object to document structure of
	 * given SDocument-object.
	 * @param raCorpus model from which shall be mapped
	 * @param sDocument model to which shall be mapped
	 */
	@SuppressWarnings("unchecked")
	public void mapRACorpus2SDocument(RACorpus raCorpus, SDocument sDocument)
	{
		if (raCorpus== null)
			throw new PepperModuleException("Cannot map the given RACorpus to SDocument, because RACorpus-object is null.");
		if (raCorpus.getRaDocumentGraph()== null)
			throw new PepperModuleException("Cannot map the given RACorpus to SDocument, because RACorpus-object '"+raCorpus.getRaId()+"' has no RADocumentGraph-object..");
		if (sDocument== null)
			throw new PepperModuleException("Cannot map the given RACorpus to SDocument, because SDocument-object is null.");
		this.setRaDocumentGraph(raCorpus.getRaDocumentGraph());
		sDocument.setSDocumentGraph(SaltCommonFactory.eINSTANCE.createSDocumentGraph());
		sDocument.getSDocumentGraph().setSName(sDocument.getSName());
		this.setSDocGraph(sDocument.getSDocumentGraph());
		{//map all RAText-objects
			for (RAText raText: this.getRaDocumentGraph().getRaTexts())
			{
				STextualDS sText= SaltCommonFactory.eINSTANCE.createSTextualDS();
				this.mapRAText2STextualDS(raText, sText);
				this.getSDocGraph().addSNode(sText);
				//store mapping between raId and STextualDS-object
				if (raId2STextualDS== null)
					raId2STextualDS= new Hashtable<Long, STextualDS>();
				raId2STextualDS.put(raText.getRaId(), sText);
			}
		}
		{//traversion of document structure			
			GraphTraverser graphTraverser= new GraphTraverser();
			graphTraverser.setGraph(this.getRaDocumentGraph());
			GraphTraverserObject travObj= graphTraverser.getTraverserObject(GRAPH_TRAVERSE_MODE.DEPTH_FIRST, this);
			EList<RANode> roots= this.getRaDocumentGraph().getRARoots();
			if (	(roots== null) ||
					(roots.size()== 0))
				throw new PepperModuleException("Cannot traverse through document structure, because there is no raCorpus-object as root.");
			//set traversion type to corpus structure
			this.currTraversionType= TRAVERSION_TYPE.DOCUMENT_STRUCTURE;
			this.raId2SNode= new Hashtable<Long, SNode>();
			travObj.start((EList<Node>) (EList<? extends Node>) roots);
			travObj.waitUntilFinished();
			for (Exception e:  travObj.getExceptions())
			{
				throw new PepperModuleException("Some error occurs while traversing corpus structure graph.", e);
			}
		}//traversion of document structure
	}
	
	/**
	 * Maps a raText to a STextualDS-object.
	 * @param raText
	 * @param sText
	 */
	protected void mapRAText2STextualDS(RAText raText, STextualDS sText)
	{
		sText.setSText(raText.getRaText());
		sText.setSName(raText.getRaName());
	}
	
	/**
	 * Maps the given RANode-object to the given SToken-object. all RAAnnotations will also be mapped 
	 * by calling mapRANodeAnnotation2SAnnotation.
	 * @param raNode
	 * @param sToken
	 */
	protected void mapRANode2SToken(RANode raNode, SToken sToken)
	{
		if (raNode== null)
			throw new PepperModuleException("Cannot map an empty RANode-object.");
		if (sToken== null)
			throw new PepperModuleException("Cannot map an empty SToken-object.");
		sToken.setSName(raNode.getRaName());
		for (RANodeAnnotation raNodeAnno: raNode.getRaAnnotations())
		{	
			SAnnotation sAnno= SaltCommonFactory.eINSTANCE.createSAnnotation();
			this.mapRANodeAnnotation2SAnnotation(raNodeAnno, sAnno);
			sToken.addSAnnotation(sAnno);
		}
	}
	
	/**
	 * Maps a RANode-object to a STextualRelation-object.
	 * @param raNode RANode-object to map
	 * @param sTextRel STextualRelation-object to map to
	 * @param sToken SToken-object, from where the relation starts
	 * @param sText STextualDS-object, to where the relation goes
	 */
	protected void mapRANode2STextualRelation(	RANode raNode, 
												STextualRelation sTextRel,
												SToken sToken,
												STextualDS sText)
	{
		if (raNode== null)
			throw new PepperModuleException("Cannot map an empty RANode.");
		if (sTextRel== null)
			throw new PepperModuleException("Cannot map an empty STextualRelation-object.");
		if (sText== null)
			throw new PepperModuleException("Cannot map an empty STextualDS-object.");
		if (sToken== null)
			throw new PepperModuleException("Cannot map an empty SToken-object.");
		sTextRel.setSStart(raNode.getRaLeft().intValue());
		sTextRel.setSEnd(raNode.getRaRight().intValue());
		sTextRel.setSToken(sToken);
		sTextRel.setSTextualDS(sText);
	}
	
	/**
	 * Maps the given RANode-object to the given SSpan-object. all RAAnnotations will also be mapped 
	 * by calling mapRANodeAnnotation2SAnnotation.
	 * @param raNode
	 * @param sSpan
	 */
	protected void mapRANode2SSpan(RANode raNode, SSpan sSpan)
	{
		if (raNode== null)
			throw new PepperModuleException("Cannot map an empty RANode-object.");
		if (sSpan== null)
			throw new PepperModuleException("Cannot map an empty SSpan-object.");
		sSpan.setSName(raNode.getRaName());
		for (RANodeAnnotation raNodeAnno: raNode.getRaAnnotations())
		{	
			SAnnotation sAnno= SaltCommonFactory.eINSTANCE.createSAnnotation();
			this.mapRANodeAnnotation2SAnnotation(raNodeAnno, sAnno);
			sSpan.addSAnnotation(sAnno);
		}
	}
	
	/**
	 * Maps the given RANode-object to the given SStructure-object. All RAAnnotations will also be mapped 
	 * by calling mapRANodeAnnotation2SAnnotation.
	 * @param raNode
	 * @param sStructure
	 */
	protected void mapRANode2SStructure(RANode raNode, SStructure sStructure)
	{
		if (raNode== null)
			throw new PepperModuleException("Cannot map a RANode-object to SStructure-object with an empty RANode-object.");
		if (sStructure== null)
			throw new PepperModuleException("Cannot map a RANode-object to SStructure-object with an empty SStructure-object.");
		sStructure.setSName(raNode.getRaName());
		for (RANodeAnnotation raNodeAnno: raNode.getRaAnnotations())
		{	
			SAnnotation sAnno= SaltCommonFactory.eINSTANCE.createSAnnotation();
			this.mapRANodeAnnotation2SAnnotation(raNodeAnno, sAnno);
			sStructure.addSAnnotation(sAnno);
		}
	}
	
	/**
	 * Maps a RARank-object to a SRelation-object. All RAAnnotations will also be mapped 
	 * by calling mapRAEdgeAnnotation2SAnnotation.
	 * @param raRank
	 * @param sRel
	 * @param sSource
	 * @param sTarget
	 */
	protected void mapRARank2SRelation(		RARank raRank, 
											SRelation sRel,
											SNode sSource,
											SNode sTarget)
	{
		if (raRank== null)
			throw new PepperModuleException("Cannot map a RARank-object to SRelation-object with an empty RARAnk-object.");
		if (sRel== null)
			throw new PepperModuleException("Cannot map a RARank-object to SRelation-object with an empty SRelation-object.");
		if (sSource== null)
			throw new PepperModuleException("Cannot map a RARank-object to SRelation-object with an empty SNode-object as source.");
		if (sTarget== null)
			throw new PepperModuleException("Cannot map a RARank-object to SRelation-object with an empty SNode-object as target.");
		if (sRel.getSSource()== null)
			sRel.setSSource(sSource);
		if (sRel.getSTarget()== null)
			sRel.setSTarget(sTarget);
		if (	(sRel.getSTypes()== null) ||
				(!sRel.getSTypes().contains(raRank.getRaComponent().getRaName())))
		{
			sRel.addSType(raRank.getRaComponent().getRaName());
		}
			
		for (RAEdgeAnnotation raEdgeAnno: raRank.getRaAnnotations())
		{
			SAnnotation sAnno= SaltCommonFactory.eINSTANCE.createSAnnotation();
			this.mapRAEdgeAnnotation2SAnnotation(raEdgeAnno, sAnno);
			sRel.addSAnnotation(sAnno);
		}
	}
	
	/**
	 * Maps a given RANodeAnnotation-object to the given SAnnotation-object.
	 * @param raNodeAnno
	 * @param sAnno
	 */
	protected void mapRANodeAnnotation2SAnnotation(RANodeAnnotation raNodeAnno, SAnnotation sAnno)
	{
		if (raNodeAnno== null)
			throw new PepperModuleException("Cannot map an empty RANodeAnnotation-object.");
		if (sAnno== null)
			throw new PepperModuleException("Cannot map an empty SAnnotation-object.");
		sAnno.setSNS(raNodeAnno.getRaNamespace());
		sAnno.setSName(raNodeAnno.getRaName());
		sAnno.setSValue(raNodeAnno.getRaValue());
	}
	
	/**
	 * Maps a given RAEdgeAnnotation-object to the given SAnnotation-object.
	 * @param raEdgeAnno
	 * @param sAnno
	 */
	protected void mapRAEdgeAnnotation2SAnnotation(RAEdgeAnnotation raEdgeAnno, SAnnotation sAnno)
	{
		if (raEdgeAnno== null)
			throw new PepperModuleException("Cannot map an empty RAEdgeAnnotation-object.");
		if (sAnno== null)
			throw new PepperModuleException("Cannot map an empty SAnnotation-object.");
		sAnno.setSNS(raEdgeAnno.getRaNamespace());
		sAnno.setSName(raEdgeAnno.getRaName());
		sAnno.setSValue(raEdgeAnno.getRaValue());
	}
// ================================================ end: mapping document structure
	
	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_MODE traversalMode,
			Long traversalId, Edge edge, Node currNode, long order) 
	{
		Boolean retVal= false;
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (currNode instanceof RACorpus)
			{
				retVal= true;
			}
		}//traversing corpus structure
		else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE)
		{//traversing document structure 
			if (	(edge != null) &&
					(edge instanceof RARank) &&
					(hasVisited((RARank) edge)))
			{
				retVal= false;
			}
			else
			{	
				if (currNode instanceof RANode)
				{
					retVal= true;
				}
			}
		}//traversing document structure
		return(retVal);
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_MODE traversalMode, Long traversalId,
			Node currNode, Edge edge, Node fromNode, long order) 
	{
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (((RACorpus)currNode).getRaType().equals(RA_CORPUS_TYPE.CORPUS))
			{	
				this.lastCorpus.pop();
			}
		}//traversing corpus structure
		else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE)
		{//traversing document structure 
		}//traversing document structure
	}

	/**
	 * Stores the last seen corpus-object temprorary.
	 */
	private Stack<SCorpus> lastCorpus= null;
	@Override
	public void nodeReached(GRAPH_TRAVERSE_MODE traversalMode,
			Long traversalId, Node currNode, Edge edge, Node fromNode,
			long order) 
	{
		if (this.currTraversionType== TRAVERSION_TYPE.CORPUS_STRUCTURE)
		{//traversing corpus structure	
			if (currNode instanceof RACorpus)
			{//mapping raCorpus-object to SCorpus or SDocument
				SNode sNode= this.mapRACorpus2SCorpusSDocument((RACorpus) currNode);
				if (	(edge!= null) &&
						(fromNode!= null))
				{//if there is an edge and a fromNode, map it
					if (edge instanceof RACorpusRelation)
					{
							SRelation sRelation= this.mapRACorpusRelation2SRelation((RACorpusRelation) edge);
							sRelation.setSSource(this.lastCorpus.peek());
							sRelation.setSTarget(sNode);
					}
				}
				//setting lastCorpus-object to current last corpus object
				if (sNode instanceof SCorpus)
					this.lastCorpus.push((SCorpus) sNode);
			}
		}//traversing corpus structure
		else if (this.currTraversionType== TRAVERSION_TYPE.DOCUMENT_STRUCTURE)
		{//traversing document structure 
			if (currNode instanceof RANode)
			{//instance of RANode	
				RANode currRaNode= (RANode) currNode; 
				RARank raRank= (RARank) edge;
				if (!hasVisited(currRaNode))
				{	
					{//map nodes
						if (this.getRaDocumentGraph().isTerminalRaNode(currRaNode))
						{//node is terminal token
							//only map, if Token doesn't still exists
							//create token and map
							SToken sToken= SaltCommonFactory.eINSTANCE.createSToken();
							this.mapRANode2SToken(currRaNode, sToken);
							this.getSDocGraph().addSNode(sToken);
							
							//crate textRel and map
							STextualRelation sTextRel= SaltCommonFactory.eINSTANCE.createSTextualRelation();
							STextualDS sText= this.raId2STextualDS.get(currRaNode.getRaText_ref());
							this.mapRANode2STextualRelation(currRaNode, sTextRel, sToken, sText);
							this.getSDocGraph().addSRelation(sTextRel);
							this.raId2SNode.put(currRaNode.getRaId(), sToken);
						}
						else 
						{//node is non terminal
							if (this.isSpan(currRaNode))
							{//if raNode is span
								SSpan sSpan= SaltCommonFactory.eINSTANCE.createSSpan();
								this.mapRANode2SSpan(currRaNode, sSpan);
								this.getSDocGraph().addSNode(sSpan);
								this.raId2SNode.put(currRaNode.getRaId(), sSpan);
							}//if raNode is span
							else
							{//node is struct
								SStructure sStruct= SaltCommonFactory.eINSTANCE.createSStructure();
								this.mapRANode2SStructure(currRaNode, sStruct);
								this.getSDocGraph().addSNode(sStruct);
								this.raId2SNode.put(currRaNode.getRaId(), sStruct);
							}//node is struct
						}
						{//mark currRANode as visited
							this.markAsVisited(currRaNode);
						}//mark currRANode as visited
					}//map nodes
				}
				if (	(raRank!= null) &&
						(!this.hasVisited(raRank)))
				{//map relations raRank is not visited and not null
					if (raRank.getRaComponent()== null)
						throw new PepperModuleException("Cannot map the given rank with pre '"+raRank.getRaPre()+"', because it has no corresponding component.");
					if (raRank.getRaComponent().getRaType().equals(RA_COMPONENT_TYPE.C))
					{//coverage relations
						SToken sToken= (SToken)this.raId2SNode.get(raRank.getRaNode().getRaId());
						SSpan sSpan= (SSpan) this.raId2SNode.get(raRank.getRaParentNode().getRaId());
						SSpanningRelation sSpanRel= SaltCommonFactory.eINSTANCE.createSSpanningRelation();
						this.mapRARank2SRelation(raRank, sSpanRel, sSpan, sToken);
						this.getSDocGraph().addSRelation(sSpanRel);
					}//coverage relations
					else if (raRank.getRaComponent().getRaType().equals(RA_COMPONENT_TYPE.D))
					{//dominance relations
						SNode sTarget= (SNode)this.raId2SNode.get(raRank.getRaNode().getRaId());
						SStructure sStructure= (SStructure)this.raId2SNode.get(raRank.getRaParentNode().getRaId());
						
						Edge existingEdge= null;
						for (Edge outEdge : this.getSDocGraph().getOutEdges(sStructure.getSId()))
						{//check if a dominance relation between given nodes already exists	
							if(outEdge.getTarget().getId().equals(sTarget.getId()))
							{
								SRelation sRelation= (SRelation) outEdge;
								if (sRelation.getSTypes()== null)
								{
									existingEdge= outEdge;
									break;
								}
							}
						}//check if a dominance relation between given nodes already exists
						if (existingEdge==  null)
						{//if no edge exists	
							SDominanceRelation sDomRel= SaltCommonFactory.eINSTANCE.createSDominanceRelation();
							this.mapRARank2SRelation(raRank, sDomRel, sStructure, sTarget);
							this.getSDocGraph().addSRelation(sDomRel);
						}//if no edge exists
						else 
						{//extend existing edge with type if necessary
							if (raRank.getRaComponent().getRaName()!= null)
								((SRelation)existingEdge).addSType(raRank.getRaComponent().getRaName());
						}//extend existing edge with type if necessary
					}//dominance relations
					else if (raRank.getRaComponent().getRaType().equals(RA_COMPONENT_TYPE.P))
					{//pointing relations
						SNode sTarget= this.raId2SNode.get(raRank.getRaNode().getRaId());
						SNode sSource= this.raId2SNode.get(raRank.getRaParentNode().getRaId());
						SPointingRelation sPointRel= SaltCommonFactory.eINSTANCE.createSPointingRelation();
						this.mapRARank2SRelation(raRank, sPointRel, sSource, sTarget);
						this.getSDocGraph().addSRelation(sPointRel);
					}//pointing relations
					{//mark currRANode as visited
						this.markAsVisited(raRank);
					}//mark currRANode as visited
				}//map relations raRank is not visited and not null	
			}//instance of RANode
		}//traversing document structure
	}
	
	/**
	 * Returns true, if raNode is span.
	 * @param raNode
	 * @return
	 */
	private Boolean isSpan(RANode raNode)
	{
		Boolean retVal= false;
		for (Edge edge: this.getRaDocumentGraph().getOutEdges(raNode.getSId()))
		{
			if (((RARank)edge).getRaComponent().getRaType().equals(RA_COMPONENT_TYPE.C))
			{
				retVal= true;
				break;
			}
		}
		return(retVal);
	}
	
	/**
	 * Marks the given node as visited.
	 * @param raRank
	 */
	private void markAsVisited(RARank raRank)
	{
		if (raRank== null)
			throw new PepperModuleException("Cannot mark an empty RARank-object as vistited.");
		if (raRank.getSProcessingAnnotation("ra2saltMapper::visited")== null)
		{
			SProcessingAnnotation sProcAnno= SaltCommonFactory.eINSTANCE.createSProcessingAnnotation();
			sProcAnno.setSNS("ra2saltMapper");
			sProcAnno.setSName("visited");
			sProcAnno.setSValue(true);
			raRank.addSProcessingAnnotation(sProcAnno);
		}
	}
	
	/**
	 * Returns true, if raRank contains visited flag, false else.
	 * @param raRank
	 * @return
	 */
	private boolean hasVisited(RARank raRank)
	{
		if (raRank== null)
			throw new PepperModuleException("Cannot check if RARank-object has been visited, because it is empty.");
		Boolean retVal= false;
		if (raRank.getSProcessingAnnotation("ra2Saltmapper::visited")!= null)
		{
			retVal= true;
		}
		
		return(retVal);
	}
	
	/**
	 * Marks the given node as visited.
	 * @param raNode
	 */
	private void markAsVisited(RANode raNode)
	{
		if (raNode== null)
			throw new PepperModuleException("Cannot mark an empty RANode-object as vistited.");
		if (raNode.getSProcessingAnnotation("ra2saltMapper::visited")== null)
		{
			SProcessingAnnotation sProcAnno= SaltCommonFactory.eINSTANCE.createSProcessingAnnotation();
			sProcAnno.setSNS("ra2saltMapper");
			sProcAnno.setSName("visited");
			sProcAnno.setSValue(true);
			raNode.addSProcessingAnnotation(sProcAnno);
		}
	}
	
	/**
	 * Returns true, if raNode contains visited flag, false else.
	 * @param raNode
	 * @return
	 */
	private boolean hasVisited(RANode raNode)
	{
		if (raNode== null)
			throw new PepperModuleException("Cannot check if RANode-object has been visited, because it is empty.");
		Boolean retVal= false;
		if (raNode.getSProcessingAnnotation("ra2Saltmapper::visited")!= null)
		{
			retVal= true;
		}
		
		return(retVal);
	}
}
