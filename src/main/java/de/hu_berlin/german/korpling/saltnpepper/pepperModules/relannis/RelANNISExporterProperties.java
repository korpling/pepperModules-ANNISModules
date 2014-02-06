/**
 * Copyright 2013 Humboldt University of Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis;


import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperties;
import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperModules.PepperModuleProperty;

/**
 * Defines the properties to be used for the {@link RelANNISExporter}. 
 * @author Mario Frank
 *
 */
public class RelANNISExporterProperties extends PepperModuleProperties 
{
	public static final String PREFIX="relANNIS.exporter.";
	
	public static final String PREFIX_CLOBBER="clobber.";
	
	/**
	 * Name of property which sets the clobber mode for the resolver_vis_map tab.
	 */
	public static final String PROP_VISUALISATION_CLOBBER=PREFIX+PREFIX_CLOBBER+"visualisation";
	
	/**
	 * Name of property which sets the clobber mode for the corpus_annotation tab.
	 */
	public static final String PROP_CORPUS_ANNOTATION_CLOBBER=PREFIX+PREFIX_CLOBBER+"corpus_annotation";
	
	/**
	 * Name of property which sets the top-level corpus name to a given one.
	 * The default name for the corpus is overridden.
	 */
	public static final String PROP_INDIVIDUAL_CORPUS_NAME=PREFIX+"corpusName";
	
	public static final String PROP_ESCAPE_CHARACTERS= PREFIX+"escapeCharacters";
	
	public static final String PROP_ESCAPE_CHARACTERS_LIST= PREFIX+"escapeCharactersList";
	
	public RelANNISExporterProperties()
	{
		this.addProperty(new PepperModuleProperty<Boolean>(PROP_VISUALISATION_CLOBBER, Boolean.class, "This property defines whether the resolver_vis_map.tab is allowed to be overwritten if it is existent. By default, the table is overwritten(value:true)", false,Boolean.FALSE));
		this.addProperty(new PepperModuleProperty<Boolean>(PROP_CORPUS_ANNOTATION_CLOBBER, Boolean.class, "This property defines whether the corpus_annotation.tab is allowed to be overwritten if it is existent. By default, the table is overwritten(value:true)", false,Boolean.FALSE));
		this.addProperty(new PepperModuleProperty<String>(PROP_INDIVIDUAL_CORPUS_NAME, String.class, "This property defines an individual name for the top-level corpus. By default, the top-level corpus gets a generic name by the salt meta model.", null,Boolean.FALSE));
		this.addProperty(new PepperModuleProperty<Boolean>(PROP_ESCAPE_CHARACTERS, Boolean.class, "This property defines whether special characters are escaped during export. By default, characters which are incompatible with databases are escaped.",true, Boolean.FALSE));
		this.addProperty(new PepperModuleProperty<String>(PROP_ESCAPE_CHARACTERS_LIST, String.class, "This property defines a set of special characters with their escape characters.",null, Boolean.FALSE));
		
	}
	
	/**
	 * Returns whether special characters should be escaped.
	 * @return
	 */
	public boolean getEscapeCharacters()
	{
			return((Boolean)this.getProperty(PROP_ESCAPE_CHARACTERS).getValue());
	}
	
	/**
	 * Returns which characters should be replaced by which character.
	 * @return
	 */
	public Hashtable<Character,String> getEscapeCharactersSet(){
		Hashtable<Character,String> characterEscapeTable= null;
		String escapeString = ((String)this.getProperty(PROP_ESCAPE_CHARACTERS_LIST).getValue());
		if (escapeString != null){
			if (!escapeString.isEmpty()){
				characterEscapeTable = new Hashtable<Character, String>();
				// \(FIND_CHAR=REPLACE_CHAR\) (,\(FIND_CHAR=REPLACE_CHAR\))*
				Pattern pattern = Pattern.compile("(\\()(.*?=.*?)(\\))");
		        Matcher matcher = pattern.matcher(escapeString);

		        Vector<String> listMatches = new Vector<String>();

		        System.out.println("Count of groups: "+matcher.groupCount());
		        while(matcher.find())
		        {
		        	
		        	System.out.println("Matched: "+matcher.group());
		            listMatches.add(matcher.group(2));
		        }
		        System.out.println("Escape table configuration:");
		        for (String escapePair : listMatches){
					String[] valuePair = escapePair.split("=");
					if (valuePair.length == 2){
						System.out.println("Key:"+valuePair[0]+" , Value:"+valuePair[1]);
						char key = ' ';
						if (valuePair[0].equals("\\t")){
							key = '\t';
						} else if (valuePair[0].equals("\\n")){
							key = '\n';
						} else if (valuePair[0].equals("\\r")){
							key = '\r';
						} else if (valuePair[0].equals("\'")){
							key = '\'';
						} else if (valuePair[0].equals("\"")){
							key = '\"';
						} else if (valuePair[0].length()==1){
							key = valuePair[0].toCharArray()[0];
						} else {
							System.out.println("INFO: malformed character in escape values: "+valuePair[0]);
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
	 * @return
	 */
	public boolean getClobberResolverVisMap()
	{
			return((Boolean)this.getProperty(PROP_VISUALISATION_CLOBBER).getValue());
	}
	/**
	 * Returns whether the corpus_annotation.tab should be overwritten.
	 * @return
	 */
	public boolean getClobberCorpusAnnotations()
	{
			return((Boolean)this.getProperty(PROP_CORPUS_ANNOTATION_CLOBBER).getValue());
	}
	
	/**
	 * Returns the individual corpus name.
	 * @return null, if no individual name should be used and the individual name, else.
	 */
	public String getIndividualCorpusName()
	{
		return((String)this.getProperty(PROP_INDIVIDUAL_CORPUS_NAME).getValue());
	}
	
	
}
