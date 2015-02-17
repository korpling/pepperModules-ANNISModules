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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.emf.common.util.EList;

/**
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class Statistics {
  
  private final Table<String, String, AtomicInteger> edgeTypeCounter
          = HashBasedTable.create();
  
  private final Multimap<String, QName> terminalAnnoByLayer
          = HashMultimap.create();
  
  private final Multimap<String, String> terminalEdgeType
          = HashMultimap.create();
  
  private final Multimap<String, QName> edgeAnnoByLayer
          = HashMultimap.create();
  
  private final Set<String> layers = Collections.synchronizedSet(new HashSet<String>());
  
  public void addEdgeType(String layer, String edgeType) {
    
    synchronized (edgeTypeCounter) {

      if (!edgeTypeCounter.contains(layer, edgeType)) {
        edgeTypeCounter.put(layer, edgeType, new AtomicInteger(0));
      }
      edgeTypeCounter.get(layer, edgeType).incrementAndGet();
    }
    
    layers.add(layer);
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
  
  public void addEdgeAnno(String layer, String annoNS, String annoName) {
    synchronized(edgeAnnoByLayer) {
      edgeAnnoByLayer.put(layer, new QName(annoNS, annoName));
    }
    layers.add(layer);
  }
  
  public Set<QName> getEdgeAnno(String layer) {
    Set<QName> result = new HashSet<QName>();
    synchronized(edgeAnnoByLayer) {
      result.addAll(edgeAnnoByLayer.get(layer));
    }
    return result;
  }
  
  public Set<String> getLayers() {
    return new HashSet<String>(layers);
  }
  
  public static class QName {
    private final String ns;
    private final String name;

    public QName(String ns, String name) {
      this.ns = ns == null ? "null" : ns;
      this.name = name;
    }

    public QName(String name) {
      this(null, name);
    }

    public String getNs() {
      return ns;
    }

    public String getName() {
      return name;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 89 * hash + (this.ns != null ? this.ns.hashCode() : 0);
      hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final QName other = (QName) obj;
      if ((this.ns == null) ? (other.ns != null) : !this.ns.equals(other.ns)) {
        return false;
      }
      if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
        return false;
      }
      return true;
    }
  }
  
}
