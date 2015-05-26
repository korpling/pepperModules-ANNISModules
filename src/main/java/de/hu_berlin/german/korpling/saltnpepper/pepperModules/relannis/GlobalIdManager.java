package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class GlobalIdManager {

  private final AtomicLong componentId = new AtomicLong(0l);
  private final AtomicLong rankId = new AtomicLong(0l);

  private final AtomicLong corpusId = new AtomicLong(0l);

  private final AtomicLong nodeId = new AtomicLong(0l);

  private final ConcurrentHashMap<String, Long> corpusTabIdMap = new ConcurrentHashMap<>();

  // states whether virtual tokens and spans are managed
  private final AtomicBoolean containsVirtualTokens = new AtomicBoolean(false);

  private final ConcurrentMap<String, ResolverEntry> resolverEntryByDisplay
          = new ConcurrentHashMap<>();
  
  private final AtomicBoolean audioFound = new AtomicBoolean(false);
  private final AtomicBoolean videoFound = new AtomicBoolean(false);
  private final AtomicBoolean pdfFound = new AtomicBoolean(false);
  
  private final ConcurrentMap<String, String> documentNames
          = new ConcurrentHashMap<>();

  public GlobalIdManager() {

  }

  /**
   * This method returns a new rank id.
   *
   * @return The new rank id
   */
  public long getNewRankId() {
    return rankId.getAndIncrement();
  }

  /**
   * This method returns a new component id.
   *
   * @return The component id
   */
  public Long getNewComponentId() {
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

  public ConcurrentHashMap<String, Long> getCorpusTabIdMap() {
    return corpusTabIdMap;
  }

  public ConcurrentMap<String, ResolverEntry> getResolverEntryByDisplay() {
    return resolverEntryByDisplay;
  }

  public ConcurrentMap<String, String> getDocumentNames() {
    return documentNames;
  }
  
  public void setAudioFound() {
    audioFound.set(true);
  }
  
  public void setVideoFound() {
    videoFound.set(true);
  }
  
  public void setPDFFound() {
    pdfFound.set(true);
  }

  public boolean isAudioFound() {
    return audioFound.get();
  }

  public boolean isVideoFound() {
    return videoFound.get();
  }
  
  public boolean isPDFFound() {
    return pdfFound.get();
  }
  
  
}
