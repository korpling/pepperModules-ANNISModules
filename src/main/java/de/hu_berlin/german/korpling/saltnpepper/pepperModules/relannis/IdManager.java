package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;

public class IdManager {

	private ConcurrentHashMap<SElementId,Long> rAIdMap;
	
	private Long rAIdCounter = 0l;
	
	public IdManager() {
		this.rAIdCounter = 0l;
		this.rAIdMap = new ConcurrentHashMap<SElementId, Long>();
		//this.rAIdMap.clear();
	}
	
	synchronized Long getNewRAId(SElementId sElementId){
		Long newId = this.rAIdMap.get(sElementId);
		if (newId == null){
			// no Id found. Create a new one
			newId = this.rAIdCounter;
			this.rAIdMap.put(sElementId, rAIdCounter);
			this.rAIdCounter += 1;
		}
		return newId;
		
	}
}
