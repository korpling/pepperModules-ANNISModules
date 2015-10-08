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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.core.SNode;

/**
 * Describes the position of a corpus in the corpus hierarchy.
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public enum CorpusType {

  TOPLEVEL("CORPUS"),
  SUBCORPUS("CORPUS"),
  DOCUMENT("DOCUMENT"),
  UNKNOWN("NULL");

  private final String corpusTabType;

  private CorpusType(String corpusTabType) {
    this.corpusTabType = corpusTabType;
  }

  public String getCorpusTabType() {
    return corpusTabType;
  }

  public static CorpusType createFromNode(SNode n) {
    if (n instanceof SDocument) {
      return DOCUMENT;
    } else if(n instanceof SCorpus) {
      SCorpus c = (SCorpus) n;
      String[] path = c.getPath().segments();
      if(path == null || path.length <= 1) {
        return TOPLEVEL;
      } else {
        return SUBCORPUS;
      }
    }
    return UNKNOWN;
  }

}
