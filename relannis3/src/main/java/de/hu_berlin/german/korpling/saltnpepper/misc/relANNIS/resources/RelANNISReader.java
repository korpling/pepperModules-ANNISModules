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

import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.misc.tupleconnector.TupleReader;


public class RelANNISReader implements Runnable
{
// ================================== start TupleReader
// --------------------------- corpus	
	/**
	 * TupleReader for corpus.tab
	 */
	private TupleReader corpusTReader= null;

	/**
	 * Sets tuple reader for corpus.tab.
	 * @param corpusTReader
	 */
	public void setCorpusTReader(TupleReader corpusTReader) {
		this.corpusTReader = corpusTReader;
	}

	/**
	 * Returns tuple reader for corpus.tab.
	 * @return corpusTReader reader for corpus.tab
	 */
	public TupleReader getCorpusTReader() {
		return corpusTReader;
	}
// --------------------------- corpusAnnotation	
	/**
	 * TupleReader for corpus_annotation.tab
	 */
	private TupleReader corpusAnnotationTReader= null;

	/**
	 * Sets tuple reader for corpus_annotation.tab.
	 * @param corpusAnnotationTReader
	 */
	public void setcorpusAnnotationTReader(TupleReader corpusAnnotationTReader) {
		this.corpusAnnotationTReader = corpusAnnotationTReader;
	}

	/**
	 * Returns tuple reader for corpus_annotation.tab.
	 * @return corpusAnnotationTReader reader for corpusAnnotation.tab
	 */
	public TupleReader getcorpusAnnotationTReader() {
		return corpusAnnotationTReader;
	}

// --------------------------- text
	/**
	 * TupleReader for text.tab
	 */
	private TupleReader textTReader= null;
	public void setTextTReader(TupleReader textTReader) {
		this.textTReader = textTReader;
	}

	public TupleReader getTextTReader() {
		return textTReader;
	}
	
// --------------------------- nodes
	/**
	 * TupleReader for node.tab
	 */
	private TupleReader nodeTReader= null;
	public void setNodeTReader(TupleReader nodeTReader) {
		this.nodeTReader = nodeTReader;
	}

	public TupleReader getNodeTReader() {
		return nodeTReader;
	}

// --------------------------- node annotation	
	private TupleReader nodeAnnotationTReader= null;
	public void setNodeAnnotationTReader(TupleReader nodeAnnotationTReader) {
		this.nodeAnnotationTReader = nodeAnnotationTReader;
	}

	public TupleReader getNodeAnnotationTReader() {
		return nodeAnnotationTReader;
	}

// --------------------------- rank	
	private TupleReader rankTReader= null;
	public void setRankTReader(TupleReader rankTReader) {
		this.rankTReader = rankTReader;
	}

	public TupleReader getRankTReader() {
		return rankTReader;
	}

// --------------------------- edge annotation	
	private TupleReader edgeAnnotationTReader= null;
	public void setEdgeAnnotationTReader(TupleReader edgeAnnotationTReader) {
		this.edgeAnnotationTReader = edgeAnnotationTReader;
	}

	public TupleReader getEdgeAnnotationTReader() {
		return edgeAnnotationTReader;
	}
// --------------------------- components
	private TupleReader componentTReader= null;

	public void setComponentTReader(TupleReader componentTReader) {
		this.componentTReader = componentTReader;
	}

	public TupleReader getComponentTReader() {
		return componentTReader;
	}
	
// ================================== end TupleReader
	
	private RACorpusGraph raCorpusGraph= null;
	
	public void setRaCorpusGraph(RACorpusGraph raCorpusGraph) {
		this.raCorpusGraph = raCorpusGraph;
	}

	public RACorpusGraph getRaCorpusGraph() {
		return raCorpusGraph;
	}
	
	private RADocumentGraph raDocumentGraph= null;
	

	public void setRaDocumentGraph(RADocumentGraph raDocumentGraph) 
	{
		if (raDocumentGraph== null)
			throw new RelANNISException("Cannot add an empty raDocumentGraph-object.");
//		if (raDocumentGraph.getRaCorpus()== null)
//			throw new RelANNISException("Cannot add a raDocumentGraph-object, if its raDocument-object isn�t set.");
		this.raDocumentGraph = raDocumentGraph;
	}

	public RADocumentGraph getRaDocumentGraph() {
		return raDocumentGraph;
	}
	

// ================================= start loading corpus structure	
	/**
	 * if mode= CORPUS_STRUCTURE, corpus structure will be load, if mode= "DOCUMENT", a document will be load
	 */
	private String LOADING_MODE= null;
	
	/**
	 * Stores the pre value related to its corpus. Pre is the key
	 */
	private Hashtable<Long, RACorpus> preCorpusTable= null;
	
	private void basicLoadCorpusStructure()
	{
		this.loadCorpus();
		this.loadCorpusAnnotations();
	}
	
	private void loadCorpus()
	{
		if (this.getRaCorpusGraph()== null)
			 throw new RelANNISException("Cannot start reading corpusstructure, because no corpus-graph-object is given.");
		
		this.preCorpusTable= new Hashtable<Long, RACorpus>();
		Collection<String> preTuple= null;
		try {
			while ((preTuple= this.getCorpusTReader().getTuple())!= null)
			{//mapping the tuple to corpus
				EList<String> tuple= new BasicEList<String>(preTuple);
				RACorpus corpus= relANNISFactory.eINSTANCE.createRACorpus();
				corpus.setRaId(new Long(tuple.get(0)));
				corpus.setRaName(tuple.get(1));
				if (tuple.get(2).equalsIgnoreCase(RA_CORPUS_TYPE.DOCUMENT.toString()))
					corpus.setRaType(RA_CORPUS_TYPE.DOCUMENT);
				else corpus.setRaType(RA_CORPUS_TYPE.CORPUS);
				corpus.setRaVersion(tuple.get(3));
				corpus.setRaPre(new Long(tuple.get(4)));
				corpus.setRaPost(new Long(tuple.get(5)));
				this.getRaCorpusGraph().addSNode(corpus);
				this.preCorpusTable.put(corpus.getRaPre(), corpus);
		
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading corpusstructure, because the file '"+this.getCorpusTReader().getFile()+"' cannot be read correctly. ", e);
		}
		{//organize corpus in corpus graph, create reltaions between them according to pp-order
			for (RACorpus corpus: this.getRaCorpusGraph().getRaCorpora())
			{
				if (corpus.getRaPost()- corpus.getRaPre()> 1)
				{//for non terminal node do
					Long maxSubTreePost= 0l;
					Long currPre= corpus.getRaPre();
					while (maxSubTreePost != corpus.getRaPost()-1)
					{// do until 
						currPre++;
						RACorpus subCorpus= this.preCorpusTable.get(currPre);
						RACorpusRelation corpRel= relANNISFactory.eINSTANCE.createRACorpusRelation();
						corpRel.setRaSuperCorpus(corpus);
						corpRel.setRaSubCorpus(subCorpus);
						this.getRaCorpusGraph().addSRelation(corpRel);
						maxSubTreePost= subCorpus.getRaPost();
						currPre= maxSubTreePost;
					}
				}
			}
		}
	}
	
	/**
	 * Loads all corpus annotations into RACorpusAnnotation-objects.
	 */
	private void loadCorpusAnnotations()
	{
		if (this.getcorpusAnnotationTReader()== null)
			throw new RelANNISException("Cannot load corpus annotations, because no tuple reader is given for loading corpus annotations.");
		
		this.preCorpusTable= new Hashtable<Long, RACorpus>();
		Collection<String> preTuple= null;
		try {
			while ((preTuple= this.getcorpusAnnotationTReader().getTuple())!= null)
			{//mapping the tuple to corpus
				EList<String> annoTuple= new BasicEList<String>(preTuple);
				RACorpusAnnotation corpAnno= relANNISFactory.eINSTANCE.createRACorpusAnnotation();
				corpAnno.setRaNamespace((annoTuple.get(1).equalsIgnoreCase(KW_NULL_VALUE))? null: (annoTuple.get(1)));
				corpAnno.setRaName((annoTuple.get(2).equalsIgnoreCase(KW_NULL_VALUE))? null: (annoTuple.get(2)));
				corpAnno.setRaValue((annoTuple.get(3).equalsIgnoreCase(KW_NULL_VALUE))? null: (annoTuple.get(3)));
				RACorpus corpus= (RACorpus) this.getRaCorpusGraph().getSNode(annoTuple.get(0));
				if (corpus== null)
					throw new RelANNISException("Cannot load the following tuple as corpus annotation, because it has no corresponding corpus object. Tuple: "+ annoTuple);
				corpus.addSAnnotation(corpAnno);
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading corpusstructure, because the file '"+this.getCorpusTReader().getFile()+"' cannot be read correctly. ", e);
		}
	}
// ================================= end loading corpus structure
// ================================= start loading document
	private void basicLoadDocument()
	{
		if (this.getRaDocumentGraph()== null)
			throw new RelANNISException("Cannot load document, because no document graph is given.");
		if (this.getTextTReader()== null)
			throw new RelANNISException("Cannot load document, because no tuple reader for text is given.");
		if (this.getNodeTReader()== null)
			throw new RelANNISException("Cannot load document, because no tuple reader for Node is given.");
		if (this.getNodeAnnotationTReader()== null)
			throw new RelANNISException("Cannot load document, because no tuple reader for NodeAnnotation is given.");
		if (this.getRankTReader()== null)
			throw new RelANNISException("Cannot load document, because no tuple reader for Rank is given.");
		if (this.getEdgeAnnotationTReader()== null)
			throw new RelANNISException("Cannot load document, because no tuple reader for edge annotation is given.");
		if (this.getComponentTReader()== null)
			throw new RelANNISException("Cannot load document, because no tuple reader for component is given.");
//		Long millis= System.currentTimeMillis();
		this.loadRANode();
//		System.out.println("time to load nodes: "+ (System.currentTimeMillis()- millis));
//		millis= System.currentTimeMillis();
		this.loadRAText(textIds);
//		System.out.println("time to load texts: "+ (System.currentTimeMillis()- millis));
//		millis= System.currentTimeMillis();
		this.raComponents= new Hashtable<Long, RAComponent>();
		this.loadRARanks();
//		System.out.println("time to load ranks: "+ (System.currentTimeMillis()- millis));
		{//loading components
//			millis= System.currentTimeMillis();
			this.loadRAComponents(this.raComponents);
//			System.out.println("time to load components: "+ (System.currentTimeMillis()- millis));
//			millis= System.currentTimeMillis();
		}
		{//loading node annotations
//			millis= System.currentTimeMillis();
			this.loadRANodeAnnotations();
//			System.out.println("time to load node annotations: "+ (System.currentTimeMillis()- millis));
//			millis= System.currentTimeMillis();
		}
		{//loading edge annotations
//			millis= System.currentTimeMillis();
			this.loadRAEdgeAnnotations();
//			System.out.println("time to load edge annotations: "+ (System.currentTimeMillis()- millis));
//			millis= System.currentTimeMillis();
		}
	}
	private String KW_NULL_VALUE= "NULL";
	
	protected void loadRAText(EList<Long> textIds)
	{
		this.getTextTReader().restart();
		Collection<String> preTuple= null;
		try {
			while ((preTuple= this.getTextTReader().getTuple())!= null)
			{
				EList<String> tuple= new BasicEList<String>(preTuple);
				if (textIds.contains(new Long(tuple.get(0))))
				{
					RAText raText= this.getRaDocumentGraph().getRaText(new Long(tuple.get(0)));
					//if raText does not already exists
					if (raText== null)
					{
						raText= relANNISFactory.eINSTANCE.createRAText();
						raText.setRaId(new Long(tuple.get(0)));
						this.getRaDocumentGraph().addSNode(raText);
					}
					if (!tuple.get(1).equalsIgnoreCase(KW_NULL_VALUE))
						raText.setRaName(tuple.get(1));
					raText.setRaText(tuple.get(2));
				}
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading corpusstructure, because the file '"+this.getCorpusTReader().getFile()+"' cannot be read correctly. ", e);
		}	
	}
	
	protected EList<Long> textIds= null;
	
	protected void loadRANode()
	{
		this.getNodeTReader().restart();
		Collection<String> preTuple= null;
		this.textIds= new BasicEList<Long>();
		try {
			while ((preTuple= this.getNodeTReader().getTuple())!= null)
			{
				EList<String> tuple= new BasicEList<String>(preTuple);
				if (tuple.get(2).equals(this.docNumber.toString()))
				{//if document id is the searched one
					RANode raNode= relANNISFactory.eINSTANCE.createRANode();
					raNode.setRaId(new Long(tuple.get(0)));
					{//text_ref
						RAText raText= null;
						if (!this.textIds.contains(new Long(tuple.get(1))))
						{
							this.textIds.add(new Long(tuple.get(1)));
							raText= relANNISFactory.eINSTANCE.createRAText();
							raText.setRaId(new Long(tuple.get(1)));
							this.getRaDocumentGraph().addSNode(raText);
						}
						else
						{
							raText= this.getRaDocumentGraph().getRaText(new Long(tuple.get(1)));
						}
						raNode.setRaText(raText);
					}
//					if (	(tuple.get(2)!= null) &&
//							(!tuple.get(2).equalsIgnoreCase("NULL")))
//						raNode.setRaCorpus_ref(new Long(tuple.get(2)));
//					else raNode.setRaToken_Index(null);
					//raNamespace
					raNode.setRaNamespace(tuple.get(3));
					//raName
					raNode.setRaName(tuple.get(4));
					//raLeft
					raNode.setRaLeft(new Long(tuple.get(5)));
					//raRight
					raNode.setRaRight(new Long(tuple.get(6)));
					//raTokenIndex
					if (	(tuple.get(7)!= null) &&
							(!tuple.get(7).equalsIgnoreCase(KW_NULL_VALUE)))
						raNode.setRaToken_Index(new Long(tuple.get(7)));
					else raNode.setRaToken_Index(null);
					//raContinuous
					raNode.setRaContinuous(new Boolean(tuple.get(8)));
					this.getRaDocumentGraph().addSNode(raNode);
				}
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading nodes, because the file '"+this.getCorpusTReader().getFile()+"' cannot be read correctly. ", e);
		}
	}
	
	protected void loadRARanks()
	{
		Long millisLoad= 0l;
		Long millis= 0l;
		Long milliRank= 0l;
		Long milliParent= 0l;
		
		Long currMillis= System.currentTimeMillis();
		this.getRankTReader().restart();
		millisLoad= millisLoad + (System.currentTimeMillis()- currMillis);
		
		Collection<String> preTuple= null;
		
		//contains all ranks, which doesn't still have a parent node object
		EList<RARank> raRanksWithoutRaParentNode= new BasicEList<RARank>();
		try 
		{
			Long allInall= System.currentTimeMillis();
			while ((preTuple= this.getRankTReader().getTuple())!= null)
			{
				currMillis= System.currentTimeMillis();
				EList<String> tuple= new BasicEList<String>(preTuple);
				millis= millis + (System.currentTimeMillis()- currMillis);
				if (this.getRaDocumentGraph().getRaNode(new Long(tuple.get(2)))!= null)
				{//check if tuple has to be inserted, this is the case, if node_ref corresponds to an existing node in graph
					currMillis= System.currentTimeMillis();
					RARank raRank= this.getRaDocumentGraph().getRaRank(new Long(tuple.get(0)));
					//if rank not already exists
					if (raRank== null)
					{	
						raRank= relANNISFactory.eINSTANCE.createRARank();
						raRank.setRaPre(new Long(tuple.get(0)));
						//pre
						raRank.setRaPre(new Long(tuple.get(0)));
						this.getRaDocumentGraph().addSRelation(raRank);
					}
					milliRank= milliRank + (System.currentTimeMillis()- currMillis);
					//post
					raRank.setRaPost(new Long(tuple.get(1)));
					//node ref
					RANode raNode= this.getRaDocumentGraph().getRaNode(new Long(tuple.get(2)));
					if (raNode== null)
						throw new RelANNISException("Error in document "+this.getRaDocumentGraph().getSId()+":Cannot create a rank for the following tuple, because its node '"+tuple.get(2)+"' does not exist. Tuple: "+ tuple);
					raRank.setRaNode(raNode);
					{//component_ref
						RAComponent raComponent= null;
						raComponent= this.raComponents.get(new Long(tuple.get(3)));
						if (raComponent== null)
						{
							raComponent= relANNISFactory.eINSTANCE.createRAComponent();
							raComponent.setRaId(new Long(tuple.get(3)));
							this.raComponents.put(new Long(tuple.get(3)), raComponent);
							this.getRaDocumentGraph().getRaComponents().add(raComponent);
						}
						raRank.setRaComponent(raComponent);
					}
					currMillis= System.currentTimeMillis();
					{//parent rank
						if (!tuple.get(4).equalsIgnoreCase(KW_NULL_VALUE))
						{//if rank has a parent	
							RARank parentRank= this.getRaDocumentGraph().getRaRank(new Long(tuple.get(4)));
							if (parentRank== null)
							{//if parent rank doesn�t exists, create it
								parentRank= relANNISFactory.eINSTANCE.createRARank();
								parentRank.setRaPre(new Long(tuple.get(4)));
								this.getRaDocumentGraph().addSRelation(parentRank);
							}
							raRank.setRaParentRank(parentRank);
							
							{//set raParentNode
								if (parentRank.getRaNode()!= null)
								{
									raRank.setRaParentNode(parentRank.getRaNode());
								}
								else raRanksWithoutRaParentNode.add(raRank);
							}		
						}
					}
					milliParent= milliParent + (System.currentTimeMillis()- currMillis);
				}	
			}
			Long timeRanksWithoutRaParentNode= System.currentTimeMillis();
			{//run through all raRank-objects without parent node
				for (RARank raRank: raRanksWithoutRaParentNode)
				{
					raRank.setRaParentNode(raRank.getRaParentRank().getRaNode());
				}
			}
			allInall= System.currentTimeMillis()- allInall;
			timeRanksWithoutRaParentNode= System.currentTimeMillis()-timeRanksWithoutRaParentNode;
			System.out.println("time to get allInall : "+ allInall);
			System.out.println("time to get RanksWithoutRaParentNode: "+ timeRanksWithoutRaParentNode);
			System.out.println("time to get if rank shall be inserted: "+ millis);
			System.out.println("time to to load file: "+ millisLoad);
			System.out.println("time to check if rank still exists: "+ milliRank);
			System.out.println("time to get parent: "+ milliParent);
			System.out.println("time to get parent: "+ milliParent);
		} 
		catch (IOException e) {
			throw new RelANNISException("Cannot start reading rank, because the file '"+this.getCorpusTReader().getFile()+"' cannot be read correctly. ", e);
		}
	}
	
	/**
	 * Loads all node Annotations from tuple reader nodeAnnotation. This method puts every annotation
	 * to its corresponding node object in document-graph. Only annotations belonging to already inserted 
	 * nodes will be noticed. 
	 */
	protected void loadRANodeAnnotations()
	{
		this.getNodeAnnotationTReader().restart();
		Collection<String> preTuple= null;
		try {
			EList<String> tuple= null;
			while ((preTuple= this.getNodeAnnotationTReader().getTuple())!= null)
			{
				tuple= new BasicEList<String>(preTuple);
				RANode raNode= null;
				raNode= this.getRaDocumentGraph().getRaNode(new Long(tuple.get(0)));
				if (raNode!= null)
				{//check if node_annotation belongs to current document
					RANodeAnnotation raNodeAnnotation= relANNISFactory.eINSTANCE.createRANodeAnnotation();
					{//raNamespace
						if (	(!tuple.get(1).equalsIgnoreCase(KW_NULL_VALUE)) &&
								(!tuple.get(1).equals("")))
						raNodeAnnotation.setRaNamespace(tuple.get(1));
					}
					{//raNam
						if (	(tuple.get(2).equalsIgnoreCase(KW_NULL_VALUE)) ||
								(tuple.get(2).equals("")))
							throw new RelANNISException("Cannot load document '"+this.getRaDocumentGraph().getSId()+"', beacause there is a node annotation, whose name is empty. Tuple:  "+ tuple+ ".");
						else raNodeAnnotation.setRaName(tuple.get(2));
					}
					{//raValue
						if (	(!tuple.get(3).equalsIgnoreCase(KW_NULL_VALUE)) &&
								(!tuple.get(3).equals("")))
						raNodeAnnotation.setRaValue(tuple.get(3));
					}
					raNode.addSAnnotation(raNodeAnnotation);
				}	
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading node annotation, because the file '"+this.getNodeAnnotationTReader().getFile()+"' cannot be read correctly. ", e);
		}	
	}
	
	/**
	 * Loads all edge Annotations from tuple reader egdeAnnotation. This method puts every annotation
	 * to its corresponding rank object in document-graph. Only annotations belonging to already inserted 
	 * ranks will be noticed. 
	 */
	protected void loadRAEdgeAnnotations()
	{
		this.getEdgeAnnotationTReader().restart();
		Collection<String> preTuple= null;
		try {
			EList<String> tuple= null;
			while ((preTuple= this.getEdgeAnnotationTReader().getTuple())!= null)
			{
				tuple= new BasicEList<String>(preTuple);
				RARank raRank= null;
				raRank= this.getRaDocumentGraph().getRaRank(new Long(tuple.get(0)));
				if (raRank!= null)
				{//check if node_annotation belongs to current document
					RAEdgeAnnotation raEdgeAnnotation= relANNISFactory.eINSTANCE.createRAEdgeAnnotation();
					{//raNamespace
						if (	(!tuple.get(1).equalsIgnoreCase(KW_NULL_VALUE)) &&
								(!tuple.get(1).equals("")))
						raEdgeAnnotation.setRaNamespace(tuple.get(1));
					}
					{//raNam
						if (	(tuple.get(2).equalsIgnoreCase(KW_NULL_VALUE)) ||
								(tuple.get(2).equals("")))
							throw new RelANNISException("Cannot load document '"+this.getRaDocumentGraph().getSId()+"', beacause there is a edge annotation, whose name is empty. Tuple:  "+ tuple+ ".");
						else raEdgeAnnotation.setRaName(tuple.get(2));
					}
					{//raValue
						if (	(!tuple.get(3).equalsIgnoreCase(KW_NULL_VALUE)) &&
								(!tuple.get(3).equals("")))
							raEdgeAnnotation.setRaValue(tuple.get(3));
					}
					raRank.addSAnnotation(raEdgeAnnotation);
				}	
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading rank annotations, because the file '"+this.getEdgeAnnotationTReader()+"' cannot be read correctly. ", e);
		}	
	}
	
	protected Map<Long, RAComponent> raComponents= null;
	/**
	 * Loads components from ComponentTReader, corresponing to rank-objects belonging to current
	 * document graph. It only puts content to already existing component objects
	 */
	protected void loadRAComponents(Map<Long, RAComponent> raComponents)
	{
		this.getComponentTReader().restart();
		try {
			this.getComponentTReader().readFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Collection<String> preTuple= null;
		try {
			EList<String> tuple= null;
			while ((preTuple= this.getComponentTReader().getTuple())!= null)
			{
				tuple= new BasicEList<String>(preTuple);
				RAComponent raComponent= raComponents.get(new Long(tuple.get(0)));
				
				if (raComponent!= null)
				{//load only components, belonging to the current document
					{//raValue
						if (	(!tuple.get(1).equalsIgnoreCase(KW_NULL_VALUE)) &&
								(!tuple.get(1).equals("")))
						{	
							raComponent.setRaType(RA_COMPONENT_TYPE.get(tuple.get(1)));
						}
					}
					{//raNamespace
						if (	(!tuple.get(2).equalsIgnoreCase(KW_NULL_VALUE)) &&
								(!tuple.get(2).equals("")))
						raComponent.setRaNamespace(tuple.get(2));
					}
					{//raName
						if (	!(tuple.get(3).equalsIgnoreCase(KW_NULL_VALUE)) &&
								!(tuple.get(3).equals("")))
							raComponent.setRaName(tuple.get(3));
					}
				}	
			}
		} catch (IOException e) {
			throw new RelANNISException("Cannot start reading rank annotations, because the file '"+this.getEdgeAnnotationTReader()+"' cannot be read correctly. ", e);
		}
	}
// ================================= end loading document
	/**
	 * loads the corpus structure into given model.
	 */
	public synchronized void loadCorpusStructure()
	{
		this.LOADING_MODE= "CORPUS_STRUCTURE";
		//TODO to be threaded, problem, when reading takes time, resource and other objects does not wait
//		Thread corpusLoadingThread= new Thread(this);
//		corpusLoadingThread.start();
		this.run();
	}
	
	private Long docNumber= null;
	/**
	 * loads the corpus structure into given model.
	 */
	public synchronized void loadDocument(Long docNumber)
	{
		this.LOADING_MODE= "DOCUMENT";
		this.docNumber= docNumber;
		//TODO to be threaded, problem, when reading takes time, resource and other objects does not wait
//		Thread corpusLoadingThread= new Thread(this);
//		corpusLoadingThread.start();
		this.run();
	}
	
	@Override
	public void run() 
	{
		if (LOADING_MODE.equalsIgnoreCase("CORPUS_STRUCTURE"))
		{
			this.basicLoadCorpusStructure();
		}
		else if (LOADING_MODE.equalsIgnoreCase("DOCUMENT"))
		{
			this.basicLoadDocument();
		}
		
	}
}
