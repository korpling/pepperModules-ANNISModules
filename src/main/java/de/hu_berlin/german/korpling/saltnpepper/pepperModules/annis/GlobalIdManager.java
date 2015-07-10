/**
 * Copyright 2015 Humboldt-Universität zu Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.google.common.net.PercentEscaper;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
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
  
  private static final PercentEscaper percentEscaper = new PercentEscaper("_-", false);
  private static final Escaper firstCharEscaper = Escapers.builder()
          .addEscape('0', "%30")
          .addEscape('1', "%31")
          .addEscape('2', "%32")
          .addEscape('3', "%33")
          .addEscape('4', "%34")
          .addEscape('5', "%35")
          .addEscape('6', "%36")
          .addEscape('7', "%37")
          .addEscape('8', "%38")
          .addEscape('9', "%39")
          .addEscape('-', "%2D")
          .build();
  
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
   * Percent escapes a string if it not a valid string identifier.
   *
   * @param orig The original string ID
   * @return The replacement or null if the argument was null.
   */
  public String getEscapedIdentifier(String orig) {
    if (orig == null) {
      return null;
    }
    String escaped = getValidIDString(orig);
    
    if(!orig.equals(escaped)) {
      String oldEscape = stringIDMapping.putIfAbsent(orig, escaped);
      if(oldEscape == null) {
        // warn the first time a new identifier was escaped
        log.warn("replaced invalid ANNIS identifier {} with {}", orig, escaped);
      }
    }
    
    return escaped;
  }

  /**
   * Returns a string that is valid as ANNIS import format identifier.
   * @param orig
   * @return The valid string.
   */
  private String getValidIDString(String orig) {
    String result = orig;
    if (result != null && !result.isEmpty()) {
      result = percentEscaper.escape(result);
      // Check additional constraints on first character.
      // Since everything outside [a-zA-Z0-9] will be already encoded, the only
      // remaining invalid character range for the first character is [0-9-].
      char firstChar = result.charAt(0);
      if((firstChar >= 48 && firstChar <= 57) || firstChar == '-') {
        // first character is a digit or "-"
        result = firstCharEscaper.escape(String.valueOf(firstChar)) + result.substring(1);
      }
      
    }
    return result;
  }
  
  
}
