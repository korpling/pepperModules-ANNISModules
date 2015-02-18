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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.emf.common.util.EList;

/**
 * Statistics used for creating resolver entries for dominance components.
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class DomStatistics {
  
  private final Table<String, String, AtomicInteger> edgeTypeCounter
          = HashBasedTable.create();
  
  private final Multimap<String, QName> terminalAnnoByLayer
          = HashMultimap.create();
  
  private final Multimap<String, String> terminalEdgeType
          = HashMultimap.create();
  
  private final Multimap<String, QName> edgeAnnoByLayer
          = HashMultimap.create();
  
  private final Table<String, QName, AtomicInteger> nodeAnnoCounter
          = HashBasedTable.create();
  
  private final Set<String> layers = Collections.synchronizedSet(new HashSet<String>());
  
  public void addEdgeType(String layer, EList<String> types) {
    
    if (types != null) {
      synchronized (edgeTypeCounter) {

        for (String edgeType : types) {
          if (!edgeTypeCounter.contains(layer, edgeType)) {
            edgeTypeCounter.put(layer, edgeType, new AtomicInteger(0));
          }
          edgeTypeCounter.get(layer, edgeType).incrementAndGet();
        }
      }

      layers.add(layer);
    }
  }
  
  public SortedMap<Integer, String> getEdgeTypesBySize(String layer) {
    TreeMap<Integer, String> result = new TreeMap<Integer, String>();
    
    synchronized (edgeTypeCounter) {

      for (Map.Entry<String, AtomicInteger> e : edgeTypeCounter.row(layer).entrySet()) {
        result.put(e.getValue().get(), e.getKey());
      }
    }
    return result;
  }
  
  public void addTerminalAnno(String layer, String annoNS, String annoName) {
    synchronized(terminalAnnoByLayer) {
      terminalAnnoByLayer.put(layer, new QName(annoNS, annoName));
    }
    layers.add(layer);
  }
  
  public Set<QName> getTerminalAnno(String layer) {
    Set<QName> result = new HashSet<QName>();
    synchronized(terminalAnnoByLayer) {
      result.addAll(terminalAnnoByLayer.get(layer));
    }
    return result;
  }
  
  public void addTerminalEdgeType(String layer, EList<String> sTypes) {
    if (sTypes != null) {
      synchronized (terminalEdgeType) {
        for(String edgeType : sTypes) {
          terminalEdgeType.put(layer, edgeType);
        }
      }      
      layers.add(layer);
    }
  }
  
  public Set<String> getTerminalEdgeType(String layer) {
    Set<String> result = new HashSet<String>();
    synchronized(terminalEdgeType) {
      result.addAll(terminalEdgeType.get(layer));
    }
    return result;
  }
  
  public void addEdgeAnno(String layer,  EList<SAnnotation> annos) {
    if (annos != null) {
      synchronized (edgeAnnoByLayer) {
        for(SAnnotation a : annos) {
          edgeAnnoByLayer.put(layer, new QName(a.getSNS(), a.getSName()));
        }
      }
      layers.add(layer);
    }
  }
  
  public Set<QName> getEdgeAnno(String layer) {
    Set<QName> result = new HashSet<QName>();
    synchronized(edgeAnnoByLayer) {
      result.addAll(edgeAnnoByLayer.get(layer));
    }
    return result;
  }
  
  public void addNodeAnno(String layer,  EList<SAnnotation> annos) {
    if (annos != null) {
      synchronized (nodeAnnoCounter) {
        for(SAnnotation a : annos) {
          QName qname = new QName(a.getSNS(), a.getSName());
          if(nodeAnnoCounter.get(layer, qname) == null) {
            nodeAnnoCounter.put(layer, qname, new AtomicInteger(0));
          }
          nodeAnnoCounter.get(layer, qname).incrementAndGet();
        }
      }
      layers.add(layer);
    }
  }
  
  public SortedMap<Integer, QName> getNodeAnnobySize(String layer) {
    TreeMap<Integer, QName> result = new TreeMap<Integer, QName>();
    
    synchronized (nodeAnnoCounter) {

      for (Map.Entry<QName, AtomicInteger> e : nodeAnnoCounter.row(layer).entrySet()) {
        result.put(e.getValue().get(), e.getKey());
      }
    }
    return result;
  }
  
  
  public Set<String> getLayers() {
    return new HashSet<String>(layers);
  }
  
  
}
