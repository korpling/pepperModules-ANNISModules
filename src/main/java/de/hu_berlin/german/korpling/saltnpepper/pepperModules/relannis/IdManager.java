package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class IdManager {

	private ConcurrentHashMap<SElementId,Long> nodeIdMap;
	private ConcurrentHashMap<SElementId,Long> documentIdMap;
	private ConcurrentHashMap<SElementId,Long> textIdMap;
	
	private Long nodeId = 0l;
	
	private Long componentId = 0l;
	
	private Long rankId = 0l;
	
	private Long documentId = 0l;
	private Long textId = 0l;
	
	
	public IdManager() {
		this.nodeId = 0l;
		this.nodeIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.documentIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.textIdMap = new ConcurrentHashMap<SElementId, Long>();
		
		this.componentId = 0l;
		this.rankId = 0l;
		this.documentId = 0l;
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
	
	public Long getNewDocumentId(SElementId sElementId){
		Long newId = this.documentIdMap.get(sElementId);
		if (newId == null){
			synchronized (this) {
				if (newId == null){
					// no Id found. Create a new one
					newId = this.documentId;
					this.documentIdMap.put(sElementId, documentId);
					this.documentId += 1;

				}
			}
		}
		return newId;
	}
	
	public Long getNewTextId(SElementId sElementId){
		Long newId = this.textIdMap.get(sElementId);
		if (newId == null){
			synchronized (this) {
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
