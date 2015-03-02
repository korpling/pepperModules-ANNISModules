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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl;

import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser.GRAPH_TRAVERSE_MODE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverserObject;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.TraversalObject;

public class RADocumentGraphTraverser implements TraversalObject
{
	enum TRAVERSAL_MODE {IS_CONTINUOUS};
	/**
	 * Stores current mode of traversing
	 */
	protected TRAVERSAL_MODE currMode= null;
	
	private RADocumentGraph raDocumentGraph= null;
	public void setRaDocumentGraph(RADocumentGraph raDocumentGraph) {
		this.raDocumentGraph = raDocumentGraph;
	}

	public RADocumentGraph getRaDocumentGraph() {
		return raDocumentGraph;
	}
	
	/**
	 * Returns if the given node has a continuous text span.
	 * @param raNode node to check
	 * @return True, if the overlapped text is continuous, false else.
	 */
	public Boolean isRaContinuous(RANode raNode) 
	{
		Boolean retVal= null;
		
		GraphTraverser traverser= new GraphTraverser();
		traverser.setGraph(this.getRaDocumentGraph());
		this.currMode= TRAVERSAL_MODE.IS_CONTINUOUS;
		GraphTraverserObject travObj= traverser.getTraverserObject(GRAPH_TRAVERSE_MODE.DEPTH_FIRST, this);
		travObj.start(raNode);
		travObj.waitUntilFinished();
		return(retVal);
	}

	/**
	 * Returns if this node is a terminal raNode, that means it has no raNode objects as childs.
	 * @param raNode node object to check
	 * @return true if there are no raNode objects as child false else
	 */
	public Boolean isTerminalRaNode(RANode raNode)
	{
		Boolean retVal= null;
		
		EList<Edge> outEdges= this.getRaDocumentGraph().getOutEdges(raNode.getId().toString());
		if (	(outEdges== null) ||
				(outEdges.size()< 1))
		{
			retVal= true;
		}
		else
		{
			for (Edge edge: outEdges)
			{
				if (edge instanceof RARank)
				{
					if (((RARank) edge).getRaComponent()!= null)
					{
						if (	(((RARank) edge).getRaComponent().getRaType().equals(RA_COMPONENT_TYPE.D))||
								(((RARank) edge).getRaComponent().getRaType().equals(RA_COMPONENT_TYPE.C)))
						{	
							retVal= false;
							break;
						}
						else retVal= true;
					}
					else retVal= true;
				}
				else retVal= true;
			}
		}
		return(retVal);
	}
	
	/**
	 * Returns the text which is overlapped by this RaNodeobject. 
	 * If raNode-object is not a terminal, null is returned
	 * @param raNode node object for which text has to be computed
	 * @return text which is overlapped by given raNode object, or null if raNode object isn�t a terminal raNode object
	 */
	public String getRaSpan(RANode raNode) 
	{
		String retVal= null;
		if (this.isTerminalRaNode(raNode))
		{
			if (raNode== null)
				throw new RelANNISException("Cannot return span for empty raNode object.");
			if (raNode.getRaText()== null)
				throw new RelANNISException("Cannot return span for a RANode object, which does not have a raText object. The RANode-object has id '"+raNode.getRaId()+"'.");
			if (raNode.getRaText().getRaText()!= null)
			{	
				if (raNode.getRaRight()== null)
					throw new RelANNISException("Cannot return span for a RANode object, whose RARight-value isn�t set. The RANode-object has id '"+raNode.getRaId()+"'.");
				if (raNode.getRaLeft()== null)
					throw new RelANNISException("Cannot return span for a RANode object, whose RALeft-value isn�t set. The RANode-object has id '"+raNode.getRaId()+"'.");
				if (raNode.getRaText().getRaText().length() < raNode.getRaRight().intValue())
					throw new RelANNISException("Cannot return span for the given raNode object with id  '"+raNode.getRaId()+"', because the right index ('"+raNode.getRaRight()+"') is larger then text length ('"+raNode.getRaText().getRaText().length()+"').");
				retVal= raNode.getRaText().getRaText().substring(raNode.getRaLeft().intValue(), raNode.getRaRight().intValue());
			}
		}
		else retVal= null;
		return(retVal);
	}
	
	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_MODE traversalMode,
			Long traversalId, Edge edge, Node currNode, long order) 
	{
		boolean retVal= false;
		if (currMode== TRAVERSAL_MODE.IS_CONTINUOUS)
		{
			if (	(((RARank)edge).getRaComponent()!= null) &&
					(	(((RARank)edge).getRaComponent().getRaType()== RA_COMPONENT_TYPE.D) ||
						(((RARank)edge).getRaComponent().getRaType()== RA_COMPONENT_TYPE.C)))
			{
				retVal= true;
			}
		}
		
		return(retVal);
	}
	
	@Override
	public void nodeLeft(GRAPH_TRAVERSE_MODE traversalMode, Long traversalId,
			Node currNode, Edge edge, Node fromNode, long order) 
	{
		RANode raNode= null;
		if (	(currNode!= null)&&
				(currNode instanceof RANode))
			raNode= (RANode) raNode;
		
	}

	@Override
	public void nodeReached(GRAPH_TRAVERSE_MODE traversalMode,
			Long traversalId, Node currNode, Edge edge, Node fromNode,
			long order) {
		// TODO Auto-generated method stub
		
	}
}
