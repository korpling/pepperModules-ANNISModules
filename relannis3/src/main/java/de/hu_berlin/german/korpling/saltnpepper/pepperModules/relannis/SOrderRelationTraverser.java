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

import java.util.Map;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.GRAPH_TRAVERSE_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SOrderRelation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraphTraverseHandler;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

public class SOrderRelationTraverser implements SGraphTraverseHandler
{
	/**
	 * determining order for current node  
	 */
	private long orderCounter= 0;
	
	/**
	 * stores {@link SElementId} objects and corresponding {@link RANode}-objects.
	 */
	public Map<SElementId, RANode> sElementId2RANode= null;
	
	/**
	 * The initial {@link RANode}  object, which must be root of segment path. In this case, it can not get a name that easy,
	 * it must get it after the first {@link SOrderRelation} object has been traversed.
	 */
	private RANode initialNode= null;
	
	@Override
	public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SNode currNode, SRelation sRelation,
			SNode fromNode, long order) 
	{
		RANode raNode= sElementId2RANode.get(currNode.getSElementId());
		if (raNode== null)
		{
			throw new RelANNISException("Cannot create order raNode corresponding to SNode '"+currNode.getSId()+"', because internal mapping table has no entry for given SNode. ");
		}
		if (sRelation!= null)
		{
			String types= null;
			if (sRelation.getSTypes().size()== 0){
				types="default";
			}else{
				types= sRelation.getSTypes().get(0);
			}
			if (sRelation.getSTypes().size()>1)
			{
				for (int i=1; i< sRelation.getSTypes().size(); i++)
				{
					types= types+ ":"+ sRelation.getSTypes().get(i);
				}
				
			}
			raNode.setSegment_name(types);
			if (this.initialNode!= null)
				this.initialNode.setSegment_name(types);
		}
		else
			this.initialNode= raNode;
		
		raNode.setLeftSegment(orderCounter);
		raNode.setRightSegment(orderCounter);
		orderCounter++;
	}

	@Override
	public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId,
			SNode currNode, SRelation edge, SNode fromNode, long order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType,
			String traversalId, SRelation edge, SNode currNode, long order) {
		{
			if (edge== null)
				return(true);
			else
			{
				if (edge instanceof SOrderRelation)
					return(true);
				else return(false);
			}
		}
	}

}
