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

import org.corpus_tools.salt.core.SNode;

/**
 * Contains general ANNIS format information
 *
 * @author florian
 *
 */
public interface ANNIS {

	/**
	 * Name of the file, to store the corpus-structure. *
	 */
	public static final String FILE_ENDING = ".annis";

	/**
	 * Name of the file, to store the corpus-structure. *
	 */
	public static final String FILE_CORPUS = "corpus" + FILE_ENDING;
	/**
	 * Name of the file, to store the meta-data of the corpus-structure. *
	 */
	public static final String FILE_CORPUS_META = "corpus_annotation" + FILE_ENDING;
	/**
	 * Name of the file, to store the primary text data. *
	 */
	public static final String FILE_TEXT = "text" + FILE_ENDING;
	/**
	 * Name of the file, to store all {@link SNode}s. *
	 */
	public static final String FILE_NODE = "node" + FILE_ENDING;
	/**
	 * Name of the file, to store the annotations odf the nodes. *
	 */
	public static final String FILE_NODE_ANNO = "node_annotation" + FILE_ENDING;
	/**
	 * Name of the file, to store the edges. *
	 */
	public static final String FILE_RANK = "rank" + FILE_ENDING;
	/**
	 * Name of the file, to store the annotations of the edges. *
	 */
	public static final String FILE_EDGE_ANNO = "edge_annotation" + FILE_ENDING;
	/**
	 * Name of the file, to store the components of the edges. *
	 */
	public static final String FILE_COMPONENT = "component" + FILE_ENDING;
	/**
	 * Name of the file, to store the visualization configuration. *
	 */
	public static final String FILE_VISUALIZATION = "resolver_vis_map" + FILE_ENDING;

	/**
	 * Name of the file, to store the version information. *
	 */
	public static final String FILE_VERSION = "annis.version";

	public static final String ANNIS_VERSION = "3.3";
	/**
	 * A list of all ANNIS format file names.*
	 */
	public static final String[] FILE_ANNIS_FILES = { FILE_CORPUS, FILE_CORPUS_META, FILE_TEXT, FILE_NODE,
			FILE_NODE_ANNO, FILE_RANK, FILE_EDGE_ANNO, FILE_COMPONENT, FILE_VISUALIZATION };

}
