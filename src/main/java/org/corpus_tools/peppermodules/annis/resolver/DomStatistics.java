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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.corpus_tools.salt.core.SAnnotation;

/**
 * Statistics used for creating resolver entries for dominance components.
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class DomStatistics {
  
  
  private final Set<String> layers = Collections.synchronizedSet(new HashSet<String>());
  
  private final StatTableCounter<String> edgeTypeCounter
          = new StatTableCounter<>(layers);
  
  private final StatMultiMap<String, QName> terminalAnno
          = new StatMultiMap<>(layers);
  
  private final StatMultiMap<String, String> terminalEdgeType
          = new StatMultiMap<>(layers);
  
  private final StatMultiMap<String, QName> edgeAnno
          = new StatMultiMap<>(layers);
  
  private final StatTableCounter<QName> nodeAnnoCounter
          = new StatTableCounter<>(layers);
  
  ///////////////
  // edge type //
  ///////////////
  
  public void addRelationType(String layer, String type) {
    if(type != null) {
  	  edgeTypeCounter.add(layer, type, 1);
    }
  }
  
  public void addRelationAnno(String layer,  Set<SAnnotation> annos) {
    if (annos != null) {
      for(SAnnotation a : annos) {
        QName qname = new QName(a.getNamespace(), a.getName());
        edgeAnno.add(layer, qname);
      }
    }
  }
  
  public void addNodeAnno(String layer, Set<SAnnotation> annos) {
    if (annos != null) {
      for (SAnnotation a : annos) {
        QName qname = new QName(a.getNamespace(), a.getName());
        nodeAnnoCounter.add(layer, qname, 1);
      }
    }
  }
  
  public void addTerminalEdgeType(String layer, String type) {
	  terminalEdgeType.add(layer, type);
  }
  
  /**
   * Merges the information from the other statistics object.
   * 
   * The other object is not allowed to be modified while
   * executing this functions since no explicit locking will occur. This object
   * will be locked and suppports concurrent calls to this function.
   * @param other 
   */
  public void merge(DomStatistics other) {
    edgeAnno.merge(other.edgeAnno);
    edgeTypeCounter.merge(other.edgeTypeCounter);
    nodeAnnoCounter.merge(other.nodeAnnoCounter);
    terminalAnno.merge(other.terminalAnno);
    terminalEdgeType.merge(other.terminalEdgeType);
  }
  
  public Set<String> getLayers() {
    return new HashSet<>(layers);
  }

  public StatMultiMap<String, QName> getTerminalAnno() {
    return terminalAnno;
  }

  public StatMultiMap<String, String> getTerminalEdgeType() {
    return terminalEdgeType;
  }

  public StatMultiMap<String, QName> getEdgeAnno() {
    return edgeAnno;
  }

  public StatTableCounter<String> getEdgeTypeCounter() {
    return edgeTypeCounter;
  }

  public StatTableCounter<QName> getNodeAnnoCounter() {
    return nodeAnnoCounter;
  }
  
  
}
