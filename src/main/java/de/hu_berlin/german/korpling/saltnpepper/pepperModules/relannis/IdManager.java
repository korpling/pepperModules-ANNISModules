package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class IdManager {

	private ConcurrentHashMap<SElementId,Long> nodeIdMap;
	private ConcurrentHashMap<String,Long> virtualNodeIdMap;
	private ConcurrentHashMap<SElementId,Long> corpusTabIdMap;
	private ConcurrentHashMap<SElementId,Long> textIdMap;
	private ConcurrentHashMap<SElementId,EList<Long>> tokenVirtualisationMapping;
	private ConcurrentHashMap<SElementId,Long> spanVirtualisationMapping;
	
	private Long nodeId = 0l;
	
	private Long componentId = 0l;
	
	private Long rankId = 0l;
	
	private Long corpusId = 0l;
	private Long textId = 0l;
	
	private boolean containsVirtualTokens = false;
	
	
	public IdManager() {
		this.nodeId = 0l;
		this.nodeIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.corpusTabIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.textIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.virtualNodeIdMap = new ConcurrentHashMap<String, Long>();
		this.tokenVirtualisationMapping = new ConcurrentHashMap<SElementId, EList<Long>>();
		this.spanVirtualisationMapping = new ConcurrentHashMap<SElementId, Long>();
		
		this.componentId = 0l;
		this.rankId = 0l;
		this.corpusId = 0l;
		this.textId = 0l;
	}
	
	/**
	 * This method returns an unique node tab RelANNIS id for the 
	 * node with the specified SElementId. Also, the method returns a boolean
	 * which specifies whether the RelANNIS id is fresh.
	 * @param sElementId the SElementId of the node
	 * @return a pair <Long,Boolean> which is the RelANNIS node tab id and
	 * 		a boolean which specifies whether the id is fresh.
	 */
	public Pair<Long,Boolean> getNewNodeId(SElementId sElementId){
		boolean isNew = false;
		Long newId = this.nodeIdMap.get(sElementId);
		if (newId == null){
			synchronized (this) {
				if (newId == null){
					// no Id found. Create a new one
					isNew = true;
					newId = this.nodeId;
					this.nodeIdMap.put(sElementId, nodeId);
					this.nodeId += 1;

				}
			}
		}
		return new ImmutablePair<Long,Boolean>(newId,isNew);
	}
	
	public Pair<Long,Boolean> getVirtualNodeId(String sElementId){
		boolean isNew = false;
		Long newId = this.virtualNodeIdMap.get(sElementId);
		if (newId == null){
			synchronized (this) {
				if (newId == null){
					// no Id found. Create a new one
					isNew = true;
					newId = this.nodeId;
					this.virtualNodeIdMap.put(sElementId, nodeId);
					this.nodeId += 1;

				}
			}
		}
		return new ImmutablePair<Long,Boolean>(newId,isNew);
	}
	
	public void registerTokenVirtMapping(SElementId tokenId, Long virtualSpanId, EList<Long> virtualTokenIds){
		this.containsVirtualTokens = true;
		if (! this.tokenVirtualisationMapping.contains(tokenId)){
			this.tokenVirtualisationMapping.put(tokenId, virtualTokenIds);
		}
		if (! this.spanVirtualisationMapping.contains(virtualSpanId)){
			this.spanVirtualisationMapping.put(tokenId, virtualSpanId);
		}
	}
	
	public synchronized EList<Long> getVirtualisedTokenId(SElementId tokenId){
		return this.tokenVirtualisationMapping.get(tokenId);
	}
	
	public synchronized Long getVirtualisedSpanId(SElementId tokenId){
		return this.spanVirtualisationMapping.get(tokenId);
	}
	
	public Long getNewComponentId(){
		Long newId = null;
		synchronized (this) {
			newId = this.componentId;
			this.componentId += 1;
		}
		return newId;
	}
	
	public Long getNewRankId(){
		Long newId = null;
		synchronized (this) {
			newId = this.rankId;
			this.rankId += 1;
		}
		return newId;
	}
	
	public Long getNewCorpusTabId(SElementId sElementId){
		Long newId = this.corpusTabIdMap.get(sElementId);
		if (newId == null){
			synchronized (this) {
				if (newId == null){
					// no Id found. Create a new one
					newId = this.corpusId;
					//System.out.println("Added new Element "+sElementId.getValueString()+" with id "+newId.toString());
					this.corpusTabIdMap.put(sElementId, corpusId);
					this.corpusId += 1;

				}
			}
		}
		return newId;
	}
	
	public Long getNewTextId(SElementId sElementId){
		if (this.containsVirtualTokens)
		{
			return 0l;
		}
		else
		{
			Long newId = this.textIdMap.get(sElementId);
			if (newId == null){
				synchronized (this) {
					if (this.containsVirtualTokens){
						return 0l;
					}
					if (newId == null){
						// no Id found. Create a new one
						newId = this.textId;
						this.textIdMap.put(sElementId, textId);
						this.textId += 1;

					}
				}
			}
			return newId;
		}	
	}
	
}
