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
package org.corpus_tools.peppermodules.annis.resolver;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Statistics used for creating resolver entries for dominance components.
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class VirtualTokenStatistics {
  
  /**
   * Map from the name to the fully qualified name.
   * This ensures a name is only added once and there are no "duplicates".
   */
  private final ConcurrentMap<String, QName> virtualAnnoNames = 
          new ConcurrentHashMap<>();
  
  private final AtomicBoolean hasRealToken = new AtomicBoolean(false);

  private final Pattern patternNoToken = Pattern.compile("[0-9 ]*");

  public void addVirtualAnnoName(String ns, String name) {
    virtualAnnoNames.put(name, new QName(ns, name));
  }
  
  public Set<QName> getVirtualAnnoNames() {
    return new TreeSet<>(virtualAnnoNames.values());
  }
  
  
 
  public void checkRealToken(String span) {
    if(!patternNoToken.matcher(span).matches()) {
      hasRealToken.set(true);
    }
  }

  public boolean getHasRealToken() {
    return hasRealToken.get();
  }
  
  
  
  /**
   * Merges the information from the other statistics object.
   * 
   * The other object is not allowed to be modified while
   * executing this functions since no explicit locking will occur. This object
   * will be locked and suppports concurrent calls to this function.
   * @param other 
   */
  public void merge(VirtualTokenStatistics other) {
    virtualAnnoNames.putAll(other.virtualAnnoNames);
    hasRealToken.set(hasRealToken.get() || other.hasRealToken.get());
  }
}
