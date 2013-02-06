package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class IdManager {

	private ConcurrentHashMap<SElementId,Long> rAIdMap;
	
	private Long rAIdCounter = 0l;
	
	public IdManager() {
		this.rAIdCounter = 0l;
		this.rAIdMap.clear();
	}
	
	synchronized Long getNewRAId(SElementId sElementId){
		Long rAId = 0l;
		if (this.rAIdMap.contains(sElementId)){
			rAId = this.rAIdMap.get(sElementId);
		} else {
			this.rAIdCounter += 1;
			rAId = this.rAIdCounter;
			this.rAIdMap.put(sElementId, rAId);
		}
		return rAId;
	}
}
