package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SDocument;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sDocumentStructure.STextualDS;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IdManager {

	private final GlobalIdManager globalIdManager;
	
	private final ConcurrentMap<String,Long> textIdMap;
	private final ConcurrentMap<String,EList<Long>> tokenVirtualisationMapping;
	private final ConcurrentMap<String,Long> spanVirtualisationMapping;
	
	private final Lock lockNodeIdMap = new ReentrantLock();
	private final Map<String,Long> nodeIdMap = new HashMap<String, Long>();
	
	private final Lock lockVirtualNodeIdMap = new ReentrantLock();
	private final Map<String,Long> virtualNodeIdMap = new HashMap<String, Long>();
	
	private Long textId = 0l;
	
	
	// the virtual tokens which are sorted by their point of time
	private EList<Long> virtualTokenIdList = null;
	
	protected ConcurrentMap<String,SegmentationInfo> segmentationInfoTable = null;
  
	public IdManager(GlobalIdManager globalIdManager) {

		this.globalIdManager = globalIdManager;
		
		this.textIdMap = new ConcurrentHashMap<String, Long>();
		this.tokenVirtualisationMapping = new ConcurrentHashMap<String, EList<Long>>();
		this.spanVirtualisationMapping = new ConcurrentHashMap<String, Long>();
		
		this.virtualTokenIdList = new BasicEList<Long>();
		
		this.textId = 0l;
		
		this.segmentationInfoTable = new ConcurrentHashMap<String, SegmentationInfo>();
	}
	
	/**
	 * This method returns whether this manager manages virtual tokens
	 * @return true, if virtual tokens are existent and false, else
	 */
	public synchronized boolean hasVirtualTokenization(){
		return getGlobal().containsVirtualTokens();
	}
	
	/**
	 * This method returns the token id of the first and last covered virtual token.
	 * @param leftTokenRAId The RelANNIS id of the first covered token
	 * @param rightTokenRAId The RelANNIS id of the last covered token
	 * @return A pair of token ids
	 */
	public synchronized Pair<Long,Long> getLeftRightVirtualToken(Long leftTokenRAId, Long rightTokenRAId){
		Pair<Long,Long> returnVal;
		returnVal = new ImmutablePair<Long, Long>((long)virtualTokenIdList.indexOf(leftTokenRAId), (long)virtualTokenIdList.indexOf(rightTokenRAId));
		return returnVal;
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
		globalIdManager.setContainsVirtualTokens(true);
    tokenVirtualisationMapping.putIfAbsent(tokenId, virtualTokenIds);
    spanVirtualisationMapping.putIfAbsent(tokenId, virtualSpanId);
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
	
  public boolean insertResolverEntry(ResolverEntry entry) {
    ResolverEntry old = 
            globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
    return old == null;      
  }
  
  public List<ResolverEntry> getResolverEntries() {
    ArrayList<ResolverEntry> entries = new ArrayList<ResolverEntry>();
    entries.addAll(globalIdManager.getResolverEntryByDisplay().values());
    return entries;
  }
  
	
	/**
	 * This method returns the corpus tab id of the {@link SCorpus} or {@link SDocument} specified by the {@link SElementId} sElementId
	 * @param sElementId The {@link SElementId} of the {@link SCorpus} or {@link SDocument}
	 * @return The corpus tab id.
	 */
	public Long getNewCorpusTabId(String sElementId){
		Long newId = globalIdManager.getCorpusTabIdMap().get(sElementId);
		synchronized (this) {
			if (newId == null){
				// no Id found. Create a new one
				newId = getGlobal().getNewCorpusId();
				//System.out.println("Added new Element "+sElementId.getValueString()+" with id "+newId.toString());
				globalIdManager.getCorpusTabIdMap().put(sElementId, newId);
				
			}
		}
		return newId;
	}
	
	
	/**
	 * This method returns a RelANNIS id for a virtual node specified by the given string parameter.
	 * @param sElementId The element id
	 * @return a pair <Long,Boolean> which is the RelANNIS node tab id and
	 * 		a boolean which specifies whether the id is fresh.
	 */
	public VirtualNodeID getVirtualNodeId(String sElementId){
		boolean isNew = false;
		Long id = null;
		
		lockVirtualNodeIdMap.lock();
		try
		{
			id = this.virtualNodeIdMap.get(sElementId);
			if(id == null) {
				id = getGlobal().getNewNodeId();
				virtualNodeIdMap.put(sElementId, id);
				isNew = true;
			}
		}
		finally {
			lockVirtualNodeIdMap.unlock();
		}
		return new VirtualNodeID(id, isNew);
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
		Long id = null;
		
		lockNodeIdMap.lock();
		try
		{
			id = nodeIdMap.get(sElementId);
			if(id == null)
			{
				id = getGlobal().getNewNodeId();
				nodeIdMap.put(sElementId, id);
				isNew = true;
			}
		}
		finally {
			lockNodeIdMap.unlock();
		}
		return new ImmutablePair<Long,Boolean>(id,isNew);
	}
	
	/**
	 * This method returns the unique node tab RelANNIS id for the 
	 * node with the specified {@link SElementId}. 
	 * @param node the node
	 * @return the ID if an ID was already given, NULL otherwise
	 */
	public Long getNodeId(SNode node){
		
		if(node == null)
		{
			return null;
		}
		Long id = null;
		
		lockNodeIdMap.lock();
		try
		{
			id = nodeIdMap.get(node.getSId());
		}
		finally {
			lockNodeIdMap.unlock();
		}
		return id;
	}
	
	/**
	 * This method returns the text tab id of the {@link STextualDS} specified by the {@link SElementId} sElementId
	 * @param sElementId The {@link SElementId} of the {@link STextualDS}
	 * @return The text tab id which is 0, if this class currently manages virtual tokens.
	 */
	public Long getNewTextId(String sElementId){
		if (getGlobal().containsVirtualTokens())
		{
			return 0l;
		}
		else
		{
			Long newId = this.textIdMap.get(sElementId);
			synchronized (this) {
				if (getGlobal().containsVirtualTokens()){
					return 0l;
				}
				if (newId == null){
					// no Id found. Create a new one
					newId = this.textId;
					this.textIdMap.put(sElementId, textId);
					this.textId += 1;
					
				}
			}
			return newId;
		}	
	}
	
	public GlobalIdManager getGlobal() {
		return globalIdManager;
	}
	
}
