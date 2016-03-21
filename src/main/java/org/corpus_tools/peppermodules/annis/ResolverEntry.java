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
package org.corpus_tools.peppermodules.annis;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class ResolverEntry {

  public static final String GRID = "grid";
  public static final String TREE = "tree";
  public static final String ARCH_DEPENDENCY = "arch_dependency";
  public static final String DISCOURSE = "discourse";
  public static final String COREF = "coref";
  public static final String RSTDOC = "rstdoc";
  public static final String AUDIO = "audio";
  public static final String VIDEO = "video";
  public static final String PDF = "pdf";
  public static final String KWIC = "kwic";
  
  public enum Element {

    node, edge, NULL
  }

  public enum Visibility {

    hidden, permanent, visible, removed, preloaded
  }

  private String layerName = null;
  private Element element = Element.NULL;
  private String vis = GRID;
  private String display = "visualization";
  private Visibility visibility = Visibility.hidden;
  private int order = 0;

  private final Map<String, String> mappings = new LinkedHashMap<>();

  public String getLayerName() {
    return layerName;
  }

  public void setLayerName(String layerName) {
    this.layerName = layerName;
  }

  public Element getElement() {
    return element;
  }

  public void setElement(Element element) {
    this.element = element;
  }

  public String getVis() {
    return vis;
  }

  public void setVis(String vis) throws IllegalArgumentException {
    Preconditions.checkArgument(vis != null);
    Preconditions.checkArgument(!vis.isEmpty());
    this.vis = vis;
  }

  public String getDisplay() {
    return display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public Map<String, String> getMappings() {
    return mappings;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.layerName);
    hash = 71 * hash + Objects.hashCode(this.element);
    hash = 71 * hash + Objects.hashCode(this.vis);
    hash = 71 * hash + Objects.hashCode(this.display);
    hash = 71 * hash + Objects.hashCode(this.visibility);
    hash = 71 * hash + Objects.hashCode(this.mappings);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ResolverEntry other = (ResolverEntry) obj;
    if (!Objects.equals(this.layerName, other.layerName)) {
      return false;
    }
    if (!Objects.equals(this.vis, other.vis)) {
      return false;
    }
    if (!Objects.equals(this.display, other.display)) {
      return false;
    }
    if (this.element != other.element) {
      return false;
    }
    if (this.visibility != other.visibility) {
      return false;
    }
    if (!Objects.equals(this.mappings, other.mappings)) {
      return false;
    }
    return true;
  }

  
  
  /**
   * Parses a resolver_vis_map.annis line as defined in the "ANNIS import format version 3.3".
   * 
   * This will ignore the corpus name and version as they are not part of this data structure.
   * 
   * @param line
   * @return A parsed representation of the resolver entry.
   * @throws IllegalArgumentException Thrown when the input line could not be parsed
   */
  public static ResolverEntry parseLine(String line) throws IllegalArgumentException {
    ResolverEntry result = new ResolverEntry();
    
    List<String> cells = Splitter.on('\t').limit(9).splitToList(line);
    if(cells.size() != 9) {
      throw new IllegalArgumentException("Wrong number of columns if resolver line, expected 9 but got " + cells.size());
    }
    
    result.layerName = cells.get(2);
    String elementRaw = cells.get(3);
    if("edge".equals(elementRaw)) {
      result.element = Element.edge;
    } else if ("node".equals(elementRaw)) {
      result.element = Element.node;
    } else {
      result.element = Element.NULL;
    }
    result.vis = cells.get(4);
    result.display = cells.get(5);
    result.visibility = Visibility.valueOf(cells.get(6));
    try {
      result.order = Integer.parseInt(cells.get(7));
    } catch(NumberFormatException ex) {
      throw new IllegalArgumentException("\"order\" column does not contain a number but \"" + cells.get(7) + "\"", ex);
    }
    
    // the mappings have to be parsed as well
    String mappingsRaw = cells.get(8);
    for(String entry : Splitter.on(';').omitEmptyStrings().trimResults().split(mappingsRaw)) {
      // split into key and value
      List<String> keyValue = Splitter.on(':').limit(2).omitEmptyStrings().splitToList(entry);
      if(keyValue.size() == 2) {
        result.mappings.put(keyValue.get(0), keyValue.get(1));
      }
    }
    
    return result;
  }

}
