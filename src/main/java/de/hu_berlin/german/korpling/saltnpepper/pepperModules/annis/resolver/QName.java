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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver;

import com.google.common.collect.ComparisonChain;
import java.io.Serializable;

/**
 * A qualified name which consists of a name+":"+namespace
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class QName implements Serializable, Comparable<QName>
{
  public static final String NULL = "null";
  
  private final String ns;
  private final String name;

  public QName(String ns, String name) {
    this.ns = ns == null ? NULL : ns;
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
  
  public boolean hasNs() {
    return !NULL.equals(ns);
  }

  @Override
  public String toString() {
    return ns + ":" + name;
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

  @Override
  public int compareTo(QName o) {
    return ComparisonChain.start().compare(name, o.name)
            .compare(ns, o.ns)
            .result();
  }

  
  
}
