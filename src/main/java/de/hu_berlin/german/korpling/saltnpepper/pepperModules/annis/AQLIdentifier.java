/*
 * Copyright 2015 Humboldt-Universität zu Berlin.
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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import java.util.Objects;

/**
 * A structure holding information about different types of identifiers in AQL.
 * Some parts of Salt will be mapped to units that are queryable by AQL.
 * The identifiers of these units are described by this class.
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class AQLIdentifier {
  public enum Type {
    NODE_ANNO("node annotation"), 
    EDGE_ANNO("edge annotation"), 
    EDGE_TYPE("edge name/type"),
    SEGMENTATION("segmentation"),
    META_ANNO("meta annotation");
    
    private final String name;

    private Type(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
    
  }
  
  private final Type type;
  private final String ns;
  private final String name;
 

  public AQLIdentifier(Type type, String ns, String name) {
    this.type = type;
    this.ns = ns;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  /**
   * Get the namespace.
   * @return 
   */
  public String getNs() {
    return ns;
  }
  
  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + Objects.hashCode(this.type);
    hash = 29 * hash + Objects.hashCode(this.ns);
    hash = 29 * hash + Objects.hashCode(this.name);
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
    final AQLIdentifier other = (AQLIdentifier) obj;
    if (this.type != other.type) {
      return false;
    }
    if (!Objects.equals(this.ns, other.ns)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AQLIdentifier{" + "type=" + type + ", ns=" + ns + ", name=" + name + '}';
  }
  
  
}
