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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Statistics used for creating resolver entries for pointing relation components.
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class PointingStatistics {
  
  private final Set<QName> layers = Collections.synchronizedSet(new HashSet<QName>());


  private final StatMultiMap<QName, QName> terminalAnno
          = new StatMultiMap<QName, QName>(layers);
  
  private final ReadWriteLock numberOfNodeLock = new ReentrantReadWriteLock();
  
  private long numberOfNodes = 0l;
  
  private long maxNumberOfNodes = 0l;
  
  public void addLayer(QName layer) {
    layers.add(layer);
  }
  
  
  public Set<QName> getLayers() {
    return new HashSet<QName>(layers);
  }
  
  public void setNodeCount(long newCount) {
    numberOfNodeLock.writeLock().lock();
    try {
      numberOfNodes = newCount;
      maxNumberOfNodes = Math.max(maxNumberOfNodes, numberOfNodes);
    } finally {
      numberOfNodeLock.writeLock().unlock();
    }
  }
  
  public long getNodeCount() {
    numberOfNodeLock.readLock().lock();
    try {
      return numberOfNodes;
    } finally {
      numberOfNodeLock.readLock().unlock();
    }
  }
  
  public long getMaxNodeCount() {
    numberOfNodeLock.readLock().lock();
    try {
      return maxNumberOfNodes;
    } finally {
      numberOfNodeLock.readLock().unlock();
    }
  }
  
  /**
   * Merges the information from the other statistics object.
   * 
   * The other object is not allowed to be modified while
   * executing this functions since no explicit locking will occur. This object
   * will be locked and suppports concurrent calls to this function.
   * 
   * Please note that the number of nodes will not be merged (only the max number).
   * @param other 
   */
  public void merge(PointingStatistics other) {
    layers.addAll(other.layers);
    terminalAnno.merge(other.terminalAnno);
    numberOfNodeLock.writeLock().lock();
    try {
      maxNumberOfNodes = Math.max(maxNumberOfNodes, other.numberOfNodes);
    } finally {
      numberOfNodeLock.writeLock().unlock();
    }
  }

  public StatMultiMap<QName, QName> getTerminalAnno() {
    return terminalAnno;
  }
  
}
