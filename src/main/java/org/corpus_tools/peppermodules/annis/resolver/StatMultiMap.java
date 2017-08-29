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

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.LinkedHashSet;

/**
 * A wrapper for a multi-map implementation
 * @author Thomas Krause <krauseto@hu-berlin.de>
 * @param <LayerType>
 * @param <ValueType>
 */
public class StatMultiMap<LayerType, ValueType> {

  private final Multimap<LayerType, ValueType> valuesByLayer
          = HashMultimap.create();
  
  private final Set<LayerType> layers;

  /**
   * Constructor.
   * @param layers A *synchronized* set of layer names
   */
  public StatMultiMap(Set<LayerType> layers) {
    this.layers = layers;
  }
  
  public void add(LayerType layer, ValueType val) {
    synchronized(valuesByLayer) {
      valuesByLayer.put(layer, val);
    }
    layers.add(layer);
  }
  
  /**
   * Merges the information from the other statistics object.
   * 
   * The other object is not allowed to be modified while
   * executing this functions since no explicit locking will occur. This object
   * will be locked and suppports concurrent calls to this function.
   * @param other 
   */
  public void merge(StatMultiMap<LayerType, ? extends ValueType> other) {
    final Set<LayerType> otherLayerTypes = new LinkedHashSet<>();
    synchronized (valuesByLayer) {
      valuesByLayer.putAll(other.valuesByLayer);
      otherLayerTypes.addAll(valuesByLayer.keySet());
    } // end synchronized
    
    // update know layer types
    for(LayerType l : otherLayerTypes) {
      layers.add(l);
    }
  }
  
  public Set<ValueType> get(LayerType layer) {
    Set<ValueType> result = new HashSet<>();
    synchronized(valuesByLayer) {
      result.addAll(valuesByLayer.get(layer));
    }
    return result;
  }
  
  
}
