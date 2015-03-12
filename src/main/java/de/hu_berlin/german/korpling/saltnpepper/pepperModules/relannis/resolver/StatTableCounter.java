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
import com.google.common.collect.Table;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 * @param <ValueType>
 */
public class StatTableCounter<ValueType> {
  
  private final Table<String, ValueType, AtomicInteger> values
          = HashBasedTable.create();
  
  private final Set<String> layers;

  /**
   * Constructor.
   * @param layers A *synchronized* set of layer names
   */
  public StatTableCounter(Set<String> layers) {
    this.layers = layers;
  }
  
  public void add(String layer, ValueType col, int  delta) {
    
    if(layer == null) {
      return;
    }
    
    synchronized(values) {
      if(values.get(layer, col) == null) {
        values.put(layer, col, new AtomicInteger(0));
      }
      values.get(layer, col).addAndGet(delta);
    }
    layers.add(layer);
  }
  
  /**
   * Merges the edge type information from the other statistics object.
   * 
   * The other object is not allowed to be modified while
   * executing this functions since no explicit locking will occur. This object
   * will be locked and suppports concurrent calls to this function.
   * @param other 
   */
  public void merge(StatTableCounter<? extends ValueType> other) {
    synchronized (values) {

      for (Table.Cell<String, ? extends ValueType, AtomicInteger> c : other.values.cellSet()) {
        AtomicInteger i = values.get(c.getRowKey(), c.getColumnKey());
        if (i == null) {
          values.put(c.getRowKey(), c.getColumnKey(), c.getValue());
        } else {
          i.addAndGet(c.getValue().get());
        }
        layers.add(c.getRowKey());
      }
    } // end synchronized
  }
  
  public SortedMap<Integer, ValueType> getBySize(String layer) {
    TreeMap<Integer, ValueType> result = new TreeMap<>();
    
    if(layer == null)
    {
      return result;
    }
    
    synchronized (values) {

      for (Map.Entry<ValueType, AtomicInteger> e : values.row(layer).entrySet()) {
        result.put(e.getValue().get(), e.getKey());
      }
    }
    return result;
  }
}
