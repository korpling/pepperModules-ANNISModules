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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperModuleProperty;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Defines the properties to be used for the {@link ANNISExporter}.
 *
 * @author Mario Frank
 *
 */
public class ANNISExporterProperties extends PepperModuleProperties {

  public static final String PREFIX_CLOBBER = "clobber.";

  /**
   * Name of property which sets the clobber mode for the resolver_vis_map tab.
   */
  public static final String PROP_VISUALISATION_CLOBBER =  PREFIX_CLOBBER + "visualisation";

  /**
   * Name of property which sets the clobber mode for the corpus_annotation tab.
   */
  public static final String PROP_CORPUS_ANNOTATION_CLOBBER = PREFIX_CLOBBER + "corpus_annotation";

  /**
   * Name of property which sets the top-level corpus name to a given one. The
   * default name for the corpus is overridden.
   */
  public static final String PROP_INDIVIDUAL_CORPUS_NAME = "corpusName";

  public static final String PROP_ESCAPE_CHARACTERS = "escapeCharacters";

  public static final String PROP_ESCAPE_CHARACTERS_LIST =  "escapeCharactersList";
  
  public static final String PROP_MERGE_TEXTS_WITH_TIMELINE =  "mergeTextsWithTimeline";

  public ANNISExporterProperties() {
    this.addProperty(new PepperModuleProperty<>(PROP_VISUALISATION_CLOBBER, Boolean.class, "This property defines whether the resolver_vis_map.tab is allowed to be overwritten if it is existent. By default, the table is overwritten(value:true)", Boolean.TRUE, false));
    this.addProperty(new PepperModuleProperty<>(PROP_CORPUS_ANNOTATION_CLOBBER, Boolean.class, "This property defines whether the corpus_annotation.tab is allowed to be overwritten if it is existent. By default, the table is overwritten(value:true)", Boolean.TRUE, false));
    this.addProperty(new PepperModuleProperty<>(PROP_INDIVIDUAL_CORPUS_NAME, String.class, "This property defines an individual name for the top-level corpus. By default, the top-level corpus gets a generic name by the salt meta model.", null, false));
    this.addProperty(new PepperModuleProperty<>(PROP_ESCAPE_CHARACTERS, Boolean.class, "This property defines whether special characters are escaped during export. By default, characters which are incompatible with databases are escaped.", Boolean.TRUE, false));
    this.addProperty(new PepperModuleProperty<>(PROP_ESCAPE_CHARACTERS_LIST, String.class, "This property defines a set of special characters with their escape characters.", null, Boolean.FALSE));
    this.addProperty(new PepperModuleProperty<>(PROP_MERGE_TEXTS_WITH_TIMELINE, Boolean.class, "If \"true\" and the document has a timeline merge texts of one document to one text and create an artificial tokenization based on the timeline.", Boolean.TRUE, false));
  }

  /**
   * Returns whether special characters should be escaped.
   *
   * @return
   */
  public boolean getEscapeCharacters() {
    return ((Boolean) this.getProperty(PROP_ESCAPE_CHARACTERS).getValue());
  }

  /**
   * Returns which characters should be replaced by which character.
   *
   * @return
   */
  public ConcurrentMap<Character, String> getEscapeCharactersSet() {
    ConcurrentMap<Character, String> characterEscapeTable = null;
    String escapeString = ((String) this.getProperty(PROP_ESCAPE_CHARACTERS_LIST).getValue());
    if (escapeString != null) {
      if (!escapeString.isEmpty()) {
        characterEscapeTable = new ConcurrentHashMap<>();
        // \(FIND_CHAR=REPLACE_CHAR\) (,\(FIND_CHAR=REPLACE_CHAR\))*
        Pattern pattern = Pattern.compile("(\\()(.*?=.*?)(\\))");
        Matcher matcher = pattern.matcher(escapeString);

        ArrayList<String> listMatches = new ArrayList<>();

        while (matcher.find()) {
          listMatches.add(matcher.group(2));
        }
        for (String escapePair : listMatches) {
          String[] valuePair = escapePair.split("=");
          if (valuePair.length == 2) {
            char key;
            if (valuePair[0].equals("\\t")) {
              key = '\t';
            } else if (valuePair[0].equals("\\n")) {
              key = '\n';
            } else if (valuePair[0].equals("\\r")) {
              key = '\r';
            } else if (valuePair[0].equals("\'")) {
              key = '\'';
            } else if (valuePair[0].equals("\"")) {
              key = '\"';
            } else if (valuePair[0].length() == 1) {
              key = valuePair[0].toCharArray()[0];
            } else {
              continue;
            }
            characterEscapeTable.put(key, valuePair[1]);
          }
        }

      }
    }
    return characterEscapeTable;
  }

  /**
   * Returns whether the resolver_vis_map.tab should be overwritten.
   *
   * @return
   */
  public boolean getClobberResolverVisMap() {
    return ((Boolean) this.getProperty(PROP_VISUALISATION_CLOBBER).getValue());
  }

  /**
   * Returns whether the corpus_annotation.tab should be overwritten.
   *
   * @return
   */
  public boolean getClobberCorpusAnnotations() {
    return ((Boolean) this.getProperty(PROP_CORPUS_ANNOTATION_CLOBBER).getValue());
  }

  /**
   * Returns the individual corpus name.
   *
   * @return null, if no individual name should be used and the individual name,
   * else.
   */
  public String getIndividualCorpusName() {
    return ((String) this.getProperty(PROP_INDIVIDUAL_CORPUS_NAME).getValue());
  }
  
  
  public boolean getMergeTextsWithTimeline() {
    return ((Boolean) this.getProperty(PROP_MERGE_TEXTS_WITH_TIMELINE).getValue());
  }

}
