package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;

public class IdManager {

	private ConcurrentHashMap<String,Long> nodeIdMap;
	private ConcurrentHashMap<String,Long> virtualNodeIdMap;
	private ConcurrentHashMap<String,Long> corpusTabIdMap;
	private ConcurrentHashMap<String,Long> textIdMap;
	private ConcurrentHashMap<String,EList<Long>> tokenVirtualisationMapping;
	private ConcurrentHashMap<String,Long> spanVirtualisationMapping;
	
	private Long nodeId = 0l;
	private Long componentId = 0l;
	private Long rankId = 0l;
	private Long corpusId = 0l;
	private Long textId = 0l;
	
	// states whether virtual tokens and spans are managed
	private boolean containsVirtualTokens = false;
	// the virtual tokens which are sorted by their point of time
	private EList<Long> virtualTokenIdList = null;
	
	protected Hashtable<String,SegmentationInfo> segmentationInfoTable = null;
	
	public IdManager() {
		this.nodeId = 0l;
		this.nodeIdMap = new ConcurrentHashMap<String, Long>();
		this.corpusTabIdMap = new ConcurrentHashMap<String, Long>();
		this.textIdMap = new ConcurrentHashMap<String, Long>();
		this.virtualNodeIdMap = new ConcurrentHashMap<String, Long>();
		this.tokenVirtualisationMapping = new ConcurrentHashMap<String, EList<Long>>();
		this.spanVirtualisationMapping = new ConcurrentHashMap<String, Long>();
		
		this.virtualTokenIdList = new BasicEList<Long>();
		
		this.componentId = 0l;
		this.rankId = 0l;
		this.corpusId = 0l;
		this.textId = 0l;
		
		this.segmentationInfoTable = new Hashtable<String, SegmentationInfo>();
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
	public Pair<Long,Boolean> getNewNodeId(String sElementId){
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
	public VirtualNodeID getVirtualNodeId(String sElementId){
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
		return new VirtualNodeID(newId, isNew);
	}
	
	/**
	 * This method registers a segment index, segment name and segment span for the node specified by the {@link SElementId}
	 * @param node The {@link SElementId} of the node
	 * @param segIndex the segment index
	 * @param segName the segment name
	 * @param segSpan the segment span
	 */
	public synchronized void addSegmentInformation(String node, Long segIndex, String segName, String segSpan){
		this.segmentationInfoTable.put(node, new SegmentationInfo(segIndex, segName, segSpan));
	}
	
	/**
	 * This method returns the segment information for the node specified by the {@link SElementId}.
	 * @param node The {@link SElementId} of the node
	 * @return the segmentation info or null if the node has no segment property
	 */
	public synchronized SegmentationInfo getSegmentInformation(String node){
		SegmentationInfo returnVal = this.segmentationInfoTable.get(node);
		return returnVal;
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
	public synchronized void registerTokenVirtMapping(String tokenId, Long virtualSpanId, EList<Long> virtualTokenIds){
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
	public synchronized EList<Long> getVirtualisedTokenId(String tokenId){
		return this.tokenVirtualisationMapping.get(tokenId);
	}
  
  public synchronized int getNumberOfVirtualToken() {
    return virtualTokenIdList == null ? 0 : virtualTokenIdList.size();
  }
	
	/**
	 * This method returns the RelANNIS id of the virtual span which represents the token specified by tokenId.
	 * @param tokenId The {@link SElementId} of the node
	 * @return The RelANNIS id of the virtual Span or null, if the token was not virtualised.
	 */
	public synchronized Long getVirtualisedSpanId(String tokenId){
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
	public Long getNewCorpusTabId(String sElementId){
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
	public Long getNewTextId(String sElementId){
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
	
	
	public static class VirtualNodeID
	{
		private long nodeID;
		private boolean fresh;
		
		
		public VirtualNodeID(long nodeID, boolean fresh) {
			super();
			this.nodeID = nodeID;
			this.fresh = fresh;
		}
		public long getNodeID() {
			return nodeID;
		}
		public boolean isFresh() {
			return fresh;
		}
		
		
	}
}
