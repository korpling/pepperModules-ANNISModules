package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;

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
   * Name of the file, to store the relations. *
   */
  public static final String FILE_RANK = "rank" + FILE_ENDING;
  /**
   * Name of the file, to store the annotations of the relations. *
   */
  public static final String FILE_EDGE_ANNO = "edge_annotation" + FILE_ENDING;
  /**
   * Name of the file, to store the components of the relations. *
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
  /**
   * A list of all ANNIS format file names.*
   */
  public static final String[] FILE_ANNIS_FILES = {FILE_CORPUS, FILE_CORPUS_META, FILE_TEXT, FILE_NODE, FILE_NODE_ANNO, FILE_RANK, FILE_EDGE_ANNO, FILE_COMPONENT, FILE_VISUALIZATION};

}
