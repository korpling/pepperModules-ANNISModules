/*
 * Copyright 2015 Humboldt Univerity of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.resolver;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Statistics used for creating resolver entries for pointing relation components.
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class PointingStatistics {

  private final Multimap<QName, QName> terminalAnnoByLayer
          = HashMultimap.create();
  
  private final Set<QName> layers = Collections.synchronizedSet(new HashSet<QName>());

  public void addTerminalAnno(QName layer, String annoNS, String annoName) {
    synchronized(terminalAnnoByLayer) {
      terminalAnnoByLayer.put(layer, new QName(annoNS, annoName));
    }
    layers.add(layer);
  }
  
  public void addLayer(QName layer) {
    layers.add(layer);
  }
  
  public Set<QName> getTerminalAnno(QName layer) {
    Set<QName> result = new HashSet<QName>();
    synchronized(terminalAnnoByLayer) {
      result.addAll(terminalAnnoByLayer.get(layer));
    }
    return result;
  }
  
  public Set<QName> getLayers() {
    return new HashSet<QName>(layers);
  }
}
