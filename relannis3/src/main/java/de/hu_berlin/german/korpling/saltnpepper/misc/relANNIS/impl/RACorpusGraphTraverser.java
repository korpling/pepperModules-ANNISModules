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

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser.GRAPH_TRAVERSE_MODE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.TraversalObject;

/**
 * This class traverses a RACOrpusGraph object and sets pre and post order to its RACorpus-objects.
 * @author Florian Zipser
 *
 */
public class RACorpusGraphTraverser implements TraversalObject
{
	/**
	 * PP_ORDER will compute pre and post order. RA_ROOTS will a compute a root for the given start corpus.
	 * @author Administrator
	 *
	 */
	public enum RA_TRAVERSAL_TYPE {PP_ORDER, RA_ROOTS};
	
	private static Long ppValue= 0l;
	/**
	 * Returns a new unique Pre value
	 * @return a new pre value
	 */
	private synchronized static Long getNewPre()
	{
		Long retVal= ppValue;
		ppValue++;
		return(retVal);
	}

//============================= start: RA_TRAVERSAL_TYPE	
	private RA_TRAVERSAL_TYPE raTravType= RA_TRAVERSAL_TYPE.PP_ORDER;
	
	/**
	 * Sets the type for current traversion. Default is PP_ORDER
	 */
	public void setRATraversalType(RA_TRAVERSAL_TYPE raTravType)
	{
		this.raTravType= raTravType;
	}
	
	/**
	 * Returns the current traversal type.
	 * @return
	 */
	public RA_TRAVERSAL_TYPE getRATraversalType()
	{ return(this.raTravType);}
//============================= end: RA_TRAVERSAL_TYPE

// ============================ start: returned object
	/**
	 * The object containing the result respectively to the traversal type
	 */
	private Object resultObject= null;
	
	/**
	 * If one of the traversions produces a result. this can be found here.
	 * @return
	 */
	public Object getResultObject() 
	{
		return(this.resultObject);
	}
// ============================ end: returned object
	/**
	 * Returns a new unique post value
	 * @return a new post value
	 */
	private synchronized static Long getNewPost()
	{
		Long retVal= ppValue;
		ppValue++;
		return(retVal);
	}
	
	/**
	 * Resets the pre and post order value to 0.
	 */
	public synchronized static void resetPPOrder() 
	{
		ppValue= 0l;
	}
	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_MODE traversalMode,
			Long traversalId, Edge edge, Node currNode, long order) 
	{
		Boolean retVal= false;
		if (this.raTravType== RA_TRAVERSAL_TYPE.PP_ORDER)
		{
			if (currNode instanceof RACorpus)
			{//if node is of type RACorpus
				retVal= true;
			}
		}
		else if (this.raTravType== RA_TRAVERSAL_TYPE.RA_ROOTS)
		{
			if (	(currNode!= null) &&
					(currNode instanceof RACorpus))
			{//if node is of type RACorpus
				this.resultObject= currNode;
				retVal= true;
			}
		}
		return(retVal);
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_MODE traversalMode, Long traversalId,
			Node currNode, Edge edge, Node fromNode, long order) 
	{
		if (this.raTravType== RA_TRAVERSAL_TYPE.PP_ORDER)
		{
			if (currNode instanceof RACorpus)
			{//if node is of type RACorpus
				((RACorpus) currNode).setRaPost(getNewPost());
			}
		}
//		System.out.println("left "+ currNode+ ", post: "+ ((RACorpus) currNode).getRaPost());
	}

	@Override
	public void nodeReached(GRAPH_TRAVERSE_MODE traversalMode,
			Long traversalId, Node currNode, Edge edge, Node fromNode,
			long order) 
	{
		if (this.raTravType== RA_TRAVERSAL_TYPE.PP_ORDER)
		{	
			if (currNode instanceof RACorpus)
			{//if node is of type RACorpus
				((RACorpus) currNode).setRaPre(getNewPre());
			}
		}
//		System.out.println("reached "+ currNode+ ", pre: "+ ((RACorpus) currNode).getRaPre());
	}

}
