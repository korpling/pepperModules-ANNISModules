package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalIdManager {
  
  private final static Logger log = LoggerFactory.getLogger(GlobalIdManager.class);

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
  
  private final ConcurrentMap<String, String> stringIDMapping
          = new ConcurrentHashMap<>();

  private static final Pattern validIdPattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_-]*");
  private static final Pattern invalidIdCharPattern = Pattern.compile("[^a-zA-Z0-9_-]");
  
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

  /**
   * When string identifiers are escaped it could happen that two different IDs are
   * escaped to the same string. In order to avoid this the {@link IdManager}
   * provides this function which maps any original ID to a unique escaped ID.
   * @param orig The original string ID
   * @return The replacement or null if the argument was null.
   */
  public String getEscapedIdentifier(String orig) {
    if (orig == null) {
      return null;
    }
    String escaped = getValidIDString(orig);
    String key = escaped;
    int appendix = 2;
    boolean tryNextAppendix;
    boolean firstReplacement = false;
    do {
      String oldVal = stringIDMapping.putIfAbsent(key, orig);
      if (oldVal == null && !orig.equals(escaped)) {
        firstReplacement = true;
      }
      tryNextAppendix = oldVal != null && !oldVal.equals(orig);
      if (tryNextAppendix) {
        key = escaped + appendix++;
      }
    } while (tryNextAppendix);
    if (firstReplacement) {
      log.warn("replaced invalid ANNIS identifier {} with {}", orig, key);
    }
    return key;
  }

  /**
   * Returns a string that is valid as ANNIS import format identifier.
   * @param orig
   * @return The valid string.
   */
  private String getValidIDString(String orig) {
    String result = orig;
    if (orig != null && !orig.isEmpty() && !validIdPattern.matcher(orig).matches()) {
      char firstChar = orig.charAt(0);
      if (!(firstChar == '_' || (firstChar >= 'A' && firstChar <= 'Z') || (firstChar >= 'a' && firstChar <= 'z'))) {
        firstChar = '_';
      }
      result = firstChar + invalidIdCharPattern.matcher(orig.substring(1)).replaceAll("_");
    }
    return result;
  }
  
  
}
