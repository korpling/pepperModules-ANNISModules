package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class GlobalIdManager {
	
	private final AtomicLong componentId = new AtomicLong(0l);
	private final AtomicLong rankId = new AtomicLong(0l);
	
	private final AtomicLong corpusId = new AtomicLong(0l);
	
	private final AtomicLong nodeId = new AtomicLong(0l);
	
	// states whether virtual tokens and spans are managed
	private final AtomicBoolean containsVirtualTokens = new AtomicBoolean(false);
	
	public GlobalIdManager() {
		
	}
	
	
	/**
	 * This method returns a new rank id.
	 * @return The new rank id
	 */
	public long getNewRankId(){
		return rankId.getAndIncrement();
	}
	
	/**
	 * This method returns a new component id.
	 * @return The component id
	 */
	public Long getNewComponentId(){
		return componentId.getAndIncrement();
	}
	
	public long getNewCorpusId() {
		return corpusId.getAndIncrement();
	}
	
	public long getNewNodeId() {
		return nodeId.getAndIncrement();
	}

	public boolean containsVirtualTokens() {
		return containsVirtualTokens.get();
	}
	
	public void setContainsVirtualTokens(boolean val) {
		containsVirtualTokens.set(val);
	}
	
}
