package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.SToken;
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
	
	// states whether virtual tokens and spans are managed
	private boolean containsVirtualTokens = false;
	// the virtual tokens which are sorted by their point of time
	private EList<Long> virtualTokenIdList = null;
	
	public IdManager() {
		this.nodeId = 0l;
		this.nodeIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.corpusTabIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.textIdMap = new ConcurrentHashMap<SElementId, Long>();
		this.virtualNodeIdMap = new ConcurrentHashMap<String, Long>();
		this.tokenVirtualisationMapping = new ConcurrentHashMap<SElementId, EList<Long>>();
		this.spanVirtualisationMapping = new ConcurrentHashMap<SElementId, Long>();
		
		this.virtualTokenIdList = new BasicEList<Long>();
		
		this.componentId = 0l;
		this.rankId = 0l;
		this.corpusId = 0l;
		this.textId = 0l;
	}
	
	/**
	 * This method returns whether this manager manages virtual tokens
	 * @return true, if virtual tokens are existent and false, else
	 */
	public synchronized boolean hasVirtualTokenization(){
		return this.containsVirtualTokens;
	}
	
	/**
	 * This method returns the token id of the first and last covered virtual token.
	 * @param leftTokenRAId The RelANNIS id of the first covered token
	 * @param rightTokenRAId The RelANNIS id of the last covered token
	 * @return A pair of token ids
	 */
	public synchronized Pair<Long,Long> getLeftRightVirtualToken(Long leftTokenRAId, Long rightTokenRAId){
		Pair<Long,Long> returnVal = null;
		returnVal = new ImmutablePair<Long, Long>((long)virtualTokenIdList.indexOf(leftTokenRAId), (long)virtualTokenIdList.indexOf(rightTokenRAId));
		return returnVal;
	}
	
	/**
	 * This method returns an unique node tab RelANNIS id for the 
	 * node with the specified {@link SElementId}. Also, the method returns a boolean
	 * which specifies whether the RelANNIS id is fresh.
	 * @param sElementId the {@link SElementId} of the node
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
	
	/**
	 * This method returns a RelANNIS id for a virtual node specified by the given string parameter.
	 * @param sElementId The element id
	 * @return a pair <Long,Boolean> which is the RelANNIS node tab id and
	 * 		a boolean which specifies whether the id is fresh.
	 */
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
	
	/**
	 * This method is used to set the list of virtual tokens which is sorted by the Point Of Time of the respective vitrual tokens.
	 * @param virtTokIdList the sorted token id list
	 */
	public synchronized void registerVirtualTokenIdList(EList<Long> virtTokIdList){
		this.virtualTokenIdList = virtTokIdList;
	}
	
	/**
	 * This method is used to register the mapping of a real token (specified by tokenId) to a
	 * virtual span (specified by virtualSpanId) and a set of virtual tokens (specified by virtualTokenIds)
	 * which are overlapped by the virtual span.
	 * @param tokenId The {@link SElementId} of the real token
	 * @param virtualSpanId The RelANNIS id of the virtual span
	 * @param virtualTokenIds The RelANNIS ids of the virtual tokens
	 */
	public synchronized void registerTokenVirtMapping(SElementId tokenId, Long virtualSpanId, EList<Long> virtualTokenIds){
		this.containsVirtualTokens = true;
		if (! this.tokenVirtualisationMapping.contains(tokenId)){
			this.tokenVirtualisationMapping.put(tokenId, virtualTokenIds);
		}
		if (! this.spanVirtualisationMapping.contains(virtualSpanId)){
			this.spanVirtualisationMapping.put(tokenId, virtualSpanId);
		}
	}
	
	/**
	 * This method returns the RelANNIS ids of the virtual tokens which represent the token specified by tokenId.
	 * @param tokenId The {@link SElementId} of the node
	 * @return The list of RelANNIS ids of the virtual tokens or null, if the token was not virtualised.
	 */
	public synchronized EList<Long> getVirtualisedTokenId(SElementId tokenId){
		return this.tokenVirtualisationMapping.get(tokenId);
	}
	
	/**
	 * This method returns the RelANNIS id of the virtual span which represents the token specified by tokenId.
	 * @param tokenId The {@link SElementId} of the node
	 * @return The RelANNIS id of the virtual Span or null, if the token was not virtualised.
	 */
	public synchronized Long getVirtualisedSpanId(SElementId tokenId){
		return this.spanVirtualisationMapping.get(tokenId);
	}
	
	/**
	 * This method returns a new component id.
	 * @return The component id
	 */
	public Long getNewComponentId(){
		Long newId = null;
		synchronized (this) {
			newId = this.componentId;
			this.componentId += 1;
		}
		return newId;
	}
	
	/**
	 * This method returns a new rank id.
	 * @return The new rank id
	 */
	public Long getNewRankId(){
		Long newId = null;
		synchronized (this) {
			newId = this.rankId;
			this.rankId += 1;
		}
		return newId;
	}
	
	/**
	 * This method returns the corpus tab id of the {@link SCorpus} or {@link SDocument} specified by the {@link SElementId} sElementId
	 * @param sElementId The {@link SElementId} of the {@link SCorpus} or {@link SDocument}
	 * @return The corpus tab id.
	 */
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
	
	/**
	 * This method returns the text tab id of the {@link STextualDS} specified by the {@link SElementId} sElementId
	 * @param sElementId The {@link SElementId} of the {@link STextualDS}
	 * @return The text tab id which is 0, if this class currently manages virtual tokens.
	 */
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
