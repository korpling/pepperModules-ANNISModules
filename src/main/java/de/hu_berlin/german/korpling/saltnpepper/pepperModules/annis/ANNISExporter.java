/**
 * Copyright 2009 Humboldt University of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.DomStatistics;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.osgi.service.component.annotations.Component;

import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperExporter;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.PepperMapper;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.exceptions.PepperModuleNotReadyException;
import de.hu_berlin.german.korpling.saltnpepper.pepper.modules.impl.PepperExporterImpl;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.ResolverEntry.Vis;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.OrderStatistics;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.PointingStatistics;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.QName;
import de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis.resolver.SpanStatistics;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpus;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCommon.sCorpusStructure.SCorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SElementId;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "ANNISExporterComponent", factory = "PepperExporterComponentFactory")
public class ANNISExporter extends PepperExporterImpl implements PepperExporter, ANNIS {

  private static final Logger log = LoggerFactory.getLogger(ANNISExporter.class);
  
  public static final long MAX_NUM_OF_NODES_FOR_DISCOURSE = 5000l;

  /**
   * tuple writer to write {@link ANNIS#FILE_TEXT} *
   */
  public TupleWriter tw_text = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_NODE} *
   */
  public TupleWriter tw_node = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_NODE_ANNO} *
   */
  public TupleWriter tw_nodeAnno = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_RANK} *
   */
  public TupleWriter tw_rank = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_EDGE_ANNO} *
   */
  public TupleWriter tw_edgeAnno = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_COMPONENT} *
   */
  public TupleWriter tw_component = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_CORPUS} *
   */
  public TupleWriter tw_corpus = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_CORPUS_META} *
   */
  public TupleWriter tw_corpusMeta = null;
  /**
   * tuple writer to write {@link ANNIS#FILE_VISUALIZATION} *
   */
  public TupleWriter tw_visualization = null;

  boolean overwriteResolverVisMap = true;

  boolean overwriteCorpusAnnotations = true;

  public String individualCorpusName = null;

  private ConcurrentMap<Character, String> characterEscapeTable = null;
  private boolean escapeCharacters = true;

  // ------------------------- IdManager
  /**
   * object to manage ANNIS ids*
   */
  private GlobalIdManager globalIdManager;
  
  private DomStatistics domStats;
  private SpanStatistics spanStats;
  private PointingStatistics pointingStats;
  private OrderStatistics orderStatistics;

  // =================================================== mandatory ===================================================
  public ANNISExporter() {
    super();
    setProperties(new RelANNISExporterProperties());
    this.setName("ANNISExporter");
    this.addSupportedFormat("relANNIS", "3.3", null);
    this.addSupportedFormat("annis", "3.3", null);
  }

  /**
   * Creates a {@link Salt2RelANNISMapper} object and passes this object, so
   * that all {@link Salt2RelANNISMapper} object can access the
   * {@link IdManager} etc..
   */
  @Override
  public PepperMapper createPepperMapper(SElementId sElementId) {
    Salt2RelANNISMapper mapper = new Salt2RelANNISMapper();
    mapper.setIdManager(new IdManager(globalIdManager));
    mapper.setGlobalDomStats(domStats);
    mapper.setGlobalPointingStats(pointingStats);
    mapper.setGlobalSpanStats(spanStats);
    mapper.setGlobalOrderStats(orderStatistics);
    mapper.setOutputDir(new File(getCorpusDesc().getCorpusPath().toFileString()));
    mapper.tw_text = tw_text;
    mapper.tw_node = tw_node;
    mapper.tw_nodeAnno = tw_nodeAnno;
    mapper.tw_rank = tw_rank;
    mapper.tw_edgeAnno = tw_edgeAnno;
    mapper.tw_component = tw_component;
    mapper.tw_corpus = tw_corpus;
    mapper.tw_corpusMeta = tw_corpusMeta;
    mapper.tw_visualization = tw_visualization;
    mapper.individualCorpusName = this.individualCorpusName;
    
    mapper.mapRelationsInParallel(true);

    //a fix: it seems to be a bug, that ScorpusGraph is not set automatically for mapper
    if (sElementId.getSIdentifiableElement() != null) {
      if (sElementId.getSIdentifiableElement() instanceof SCorpus) {
        mapper.setSCorpusGraph(((SCorpus) sElementId.getSIdentifiableElement()).getSCorpusGraph());
      }
    }

    return mapper;

  }

  // =================================================== optional ===================================================	
  /**
   * Creates a {@link TupleWriter} responsible for the given file.
   *
   * @param outFile
   * @param escapeCharacters states whether characters should be escaped
   * @param characterEscapeTable contains the character-escape-mapping
   * @return
   */
  public static synchronized TupleWriter createTupleWriter(File outFile, boolean escapeCharacters, ConcurrentMap<Character, String> characterEscapeTable) {
    if (!outFile.getParentFile().exists()) {
      outFile.getParentFile().mkdirs();
    }
    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        throw new PepperModuleException("Could not create the corpus tab file " + outFile.getAbsolutePath() + " Exception:" + e.getMessage());
      }
    }
    TupleWriter tWriter = new TupleWriter();

    tWriter.setEscaping(escapeCharacters);
    if (characterEscapeTable != null) {
      tWriter.setEscapeTable(new Hashtable<>(characterEscapeTable));
    }

    tWriter.setFile(outFile);
    return (tWriter);
  }

  /**
   * Creates a {@link TupleWriter} responsible for the given file.
   *
   * @param outFile
   * @return
   */
  public static synchronized TupleWriter createTupleWRiter(File outFile) {
    if (!outFile.getParentFile().exists()) {
      outFile.getParentFile().mkdirs();
    }
    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        throw new PepperModuleException("Could not create the corpus tab file " + outFile.getAbsolutePath() + " Exception:" + e.getMessage());
      }
    }
    TupleWriter tWriter = new TupleWriter();
    tWriter.setFile(outFile);
    return (tWriter);
  }

  /**
   * Initializes all {@link TupleWriter} objects.
   */
  @Override
  public boolean isReadyToStart() throws PepperModuleNotReadyException {
    if (this.getProperties() != null) {
      overwriteResolverVisMap = ((RelANNISExporterProperties) this.getProperties()).getClobberResolverVisMap();
      overwriteCorpusAnnotations = ((RelANNISExporterProperties) this.getProperties()).getClobberCorpusAnnotations();
      String individualCorpusName_tmp = ((RelANNISExporterProperties) this.getProperties()).getIndividualCorpusName();

      // remove leading and trailing whitespaces of the individual corpus name, if it is set.
      if (individualCorpusName_tmp != null) {
        this.individualCorpusName = individualCorpusName_tmp.trim();
      }

      this.escapeCharacters = ((RelANNISExporterProperties) this.getProperties()).getEscapeCharacters();
      if (this.escapeCharacters) {
        this.characterEscapeTable = ((RelANNISExporterProperties) this.getProperties()).getEscapeCharactersSet();
      }
    }
    String corpPath = getCorpusDesc().getCorpusPath().toFileString() + ((getCorpusDesc().getCorpusPath().toFileString().endsWith("/")) ? "" : "/");
    tw_text = createTupleWriter(new File(corpPath + FILE_TEXT), this.escapeCharacters, this.characterEscapeTable);
    tw_node = createTupleWriter(new File(corpPath + FILE_NODE), this.escapeCharacters, this.characterEscapeTable);
    tw_nodeAnno = createTupleWriter(new File(corpPath + FILE_NODE_ANNO), this.escapeCharacters, this.characterEscapeTable);
    tw_rank = createTupleWriter(new File(corpPath + FILE_RANK), this.escapeCharacters, this.characterEscapeTable);
    tw_edgeAnno = createTupleWriter(new File(corpPath + FILE_EDGE_ANNO), this.escapeCharacters, this.characterEscapeTable);
    tw_component = createTupleWriter(new File(corpPath + FILE_COMPONENT), this.escapeCharacters, this.characterEscapeTable);
    tw_corpus = createTupleWriter(new File(corpPath + FILE_CORPUS), this.escapeCharacters, this.characterEscapeTable);

    // set the visualisation tuple writer
    File resolverVisFile = new File(corpPath + FILE_VISUALIZATION);
    if (!resolverVisFile.exists()) {
      tw_visualization = createTupleWriter(resolverVisFile, this.escapeCharacters, this.characterEscapeTable);
    } else {
      if (overwriteResolverVisMap) {
        tw_visualization = createTupleWriter(resolverVisFile, this.escapeCharacters, this.characterEscapeTable);
      }
    }

    // set the corpus meta annotation tuple writer
    File corpusAnnotationFile = new File(corpPath + FILE_CORPUS_META);
    if (!corpusAnnotationFile.exists()) {
      tw_corpusMeta = createTupleWriter(corpusAnnotationFile, this.escapeCharacters, this.characterEscapeTable);
    } else {
      if (overwriteCorpusAnnotations) {
        tw_corpusMeta = createTupleWriter(corpusAnnotationFile, this.escapeCharacters, this.characterEscapeTable);
      }
    }

    this.globalIdManager = new GlobalIdManager();
    this.domStats = new DomStatistics();
    this.spanStats = new SpanStatistics();
    this.pointingStats = new PointingStatistics();
    this.orderStatistics = new OrderStatistics();

    // write versions file
    File versionFile = new File(getCorpusDesc().getCorpusPath().toFileString(), "relannis.version");
    try {
      Files.write("4.0", versionFile, Charsets.UTF_8);
    } catch (IOException ex) {
      log.error("Can't write relannis.version file", ex);
    }

    return (super.isReadyToStart());
  }

  @Override
  public void end() throws PepperModuleException {
    super.end();
    
    createOrderResolverEntries();
    
    for(String l : spanStats.getLayers()) {
      createSpanResolverEntry(l);
    }

    for(String l : domStats.getLayers()) {
      createDominanceResolverEntry(l);
    }
    
    for(QName l : pointingStats.getLayers()) {
      createPointingResolverEntry(l);
    }
    
    createMediaResolverEntries();
    
    for (SCorpusGraph corpusGraph : getSaltProject().getSCorpusGraphs()) {
      if (tw_visualization != null) {
        printResolverVisMap(corpusGraph);
      }
    }
    
  }
  
  private void createSpanResolverEntry(String layer) {
    String displayName = "grid";
    if (layer != null) {
      displayName = displayName + " (" + layer + ")";
    }
    ResolverEntry entry = new ResolverEntry();
    entry.setDisplay(displayName);
    if (layer != null) {    
      entry.setElement(ResolverEntry.Element.node);
      entry.setLayerName(layer);
    }
    entry.setVis(Vis.grid);
    globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
  }
  
  private void createDominanceResolverEntry(String layerName) {
    
    
    ResolverEntry entry = new ResolverEntry();
    
    
    if(layerName != null && 
            (layerName.equalsIgnoreCase("rst") || layerName.startsWith("rhet"))) {
      
      entry.setVis(Vis.rstdoc);
      entry.setDisplay("rst (" + layerName + ")");
      entry.setLayerName(layerName);
      entry.setElement(ResolverEntry.Element.node);

    } else {
      String displayName = "tree";
      if (layerName != null) {
        displayName = displayName + " (" + layerName + ")";
      }
      entry.setDisplay(displayName);
      if (layerName != null) {    
        entry.setElement(ResolverEntry.Element.node);
        entry.setLayerName(layerName);
      }
      entry.setVis(Vis.tree);

      Set<QName> terminalAnnos = domStats.getTerminalAnno().get(layerName);

      if(terminalAnnos.size() >= 1) {
        QName qname = terminalAnnos.iterator().next();
        entry.getMappings().put("terminal_name", qname.getName());
        if(!QName.NULL.equals(qname.getNs())) {
          entry.getMappings().put("terminal_ns", qname.getNs());
        }
      }

      SortedMap<Integer, QName> nodeAnnos = domStats.getNodeAnnoCounter().getBySize(layerName);

      if(nodeAnnos.size() >= 1) {
        QName qname = nodeAnnos.get(nodeAnnos.lastKey());
        entry.getMappings().put("node_key", qname.getName());
        if(!QName.NULL.equals(qname.getNs())) {
          entry.getMappings().put("node_anno_ns", qname.getNs());
        }
      }

      Set<QName> edgeAnnos = domStats.getEdgeAnno().get(layerName);

      if(edgeAnnos.size() >= 1) {
        QName qname = edgeAnnos.iterator().next();
        entry.getMappings().put("edge_key", qname.getName());
        if(!QName.NULL.equals(qname.getNs())) {
          entry.getMappings().put("edge_anno_ns", qname.getNs());
        }
      }

      SortedMap<Integer, String> etypes = domStats.getEdgeTypeCounter().getBySize(layerName);
      if(etypes.size() >= 2) {
        // the primary edge type always has the greatest number of entries
        String primaryType = etypes.get(etypes.lastKey());
        String secondaryType = etypes.get(etypes.firstKey());

        // check if the terminal nodes are reachable by the original types
        // use the special "null" type if not
        Set<String> terminalEdgeTypes = domStats.getTerminalEdgeType().get(layerName);
        if(!terminalEdgeTypes.contains(primaryType) && !terminalEdgeTypes.contains(secondaryType)) {
          primaryType = "null";
        }

        entry.getMappings().put("edge_type", primaryType);
        entry.getMappings().put("secedge_type", secondaryType);
      }
    }
    globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);

  }
  
  private void createPointingResolverEntry(QName layerName) {

    ResolverEntry entry = new ResolverEntry();
    Set<QName> terminalAnnos = pointingStats.getTerminalAnno().get(layerName);
    
    if(terminalAnnos.size() <= 1) {
      // use arch_dependency visualizer
      entry.setVis(Vis.arch_dependency);
      
      if (terminalAnnos.size() == 1) {
        QName qname = terminalAnnos.iterator().next();
        entry.getMappings().put("node_key", qname.getName());
      }
    } else {
      
      // use coref visualizer for "large" documents and discourse (fulltext) otherwise
      if(pointingStats.getMaxNodeCount() <= MAX_NUM_OF_NODES_FOR_DISCOURSE) {
        entry.setVis(Vis.discourse);
      } else {
        entry.setVis(Vis.coref);
      }
    }

    String displayName = entry.getVis().name();
    if (layerName != null) {
      displayName = layerName.getName() + " (" + layerName.getNs() + ")";
    }
    entry.setDisplay(displayName);
    if (layerName != null) {    
      entry.setElement(ResolverEntry.Element.edge);
      entry.setLayerName(layerName.getNs());
    }

    globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);

  }
  
  private void createMediaResolverEntries() {
    
    if(globalIdManager.isAudioFound()) {
      ResolverEntry entry = new ResolverEntry();
      entry.setDisplay("audio");
      entry.setVis(Vis.audio);
      entry.setVisibility(ResolverEntry.Visibility.preloaded);
      globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
    }
    
    if(globalIdManager.isVideoFound()) {
      ResolverEntry entry = new ResolverEntry();
      entry.setDisplay("video");
      entry.setVis(Vis.video);
      entry.setVisibility(ResolverEntry.Visibility.preloaded);
      globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
    }
    
    if(globalIdManager.isPDFFound()) {
      ResolverEntry entry = new ResolverEntry();
      entry.setDisplay("pdf");
      entry.setVis(Vis.pdf);
      entry.setVisibility(ResolverEntry.Visibility.preloaded);
      globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
    }
  }
  
  private void createOrderResolverEntries() {
    
    Set<String> orderRels = orderStatistics.getOrderRelations();
    if((globalIdManager.containsVirtualTokens() || !orderStatistics.getHasRealToken()) 
            && !orderRels.isEmpty()) {
      // deactivate the original kwic view
      ResolverEntry entryHideKWIC = new ResolverEntry();
      entryHideKWIC.setDisplay("kwic");
      entryHideKWIC.setVisibility(ResolverEntry.Visibility.removed);
      entryHideKWIC.setVis(Vis.kwic);
      globalIdManager.getResolverEntryByDisplay().putIfAbsent(
              entryHideKWIC.getDisplay(), entryHideKWIC);
      
      // create a permanent grid which displays all order relations
      ResolverEntry entryGrid = new ResolverEntry();
      entryGrid.setDisplay("");
      entryGrid.setVis(Vis.grid);
      entryGrid.setVisibility(ResolverEntry.Visibility.permanent);
      entryGrid.getMappings().put("hide_tok", "true");
      entryGrid.getMappings().put("annos", Joiner.on(",").join(orderRels));
      globalIdManager.getResolverEntryByDisplay().putIfAbsent(entryGrid.getDisplay(), entryGrid);
    }
  }

  /**
   * This method prints the resolver_vis_map.relannis file
   *
   * @param corpusGraph the corpus graph
   */
  private void printResolverVisMap(SCorpusGraph corpusGraph) {

    Long transactionId = tw_visualization.beginTA();

    try {

      String corpusName = "NULL";
      String corpusVersion = "NULL";
      // get the version of the corpus but initialise the default NULL
      if (corpusGraph.getSRootCorpus() != null) {
        if (corpusGraph.getSRootCorpus().size() > 0) {
          SCorpus rootCorpus = corpusGraph.getSRootCorpus().get(0);
          // set corpus name
          corpusName = rootCorpus.getSName();
          // set corpus version
          SMetaAnnotation version = rootCorpus.getSMetaAnnotation("version");
          if (version != null) {
            if (version.getSValueSTEXT() != null) {
              corpusVersion = version.getSValueSTEXT();
            }
          }
        }
      }

      List<ResolverEntry> entries = new ArrayList<>(
              getGlobalIdManager().getResolverEntryByDisplay().values());
      
      // sort the entries
      Collections.sort(entries, new ResolverComparator());
      
      int order = 1;
      for (ResolverEntry e : entries) {
        e.setOrder(order);

        EList<String> resolverTuple = new BasicEList<>();

        resolverTuple.add(corpusName);
        resolverTuple.add(corpusVersion);
        resolverTuple.add(e.getLayerName() == null ? "NULL" : e.getLayerName());
        resolverTuple.add(e.getElement().name());
        resolverTuple.add(e.getVis().name());
        resolverTuple.add(e.getDisplay());
        resolverTuple.add(e.getVisibility().name());
        resolverTuple.add("" + e.getOrder());

        List<String> mappings = new LinkedList<>();
        for (Map.Entry<String, String> m : e.getMappings().entrySet()) {
          mappings.add(m.getKey() + ":" + m.getValue());
        }
        if (mappings.isEmpty()) {
          resolverTuple.add("NULL");
        } else {
          resolverTuple.add(Joiner.on(";").join(mappings));
        }
        tw_visualization.addTuple(resolverTuple);

        order++;
      }
      tw_visualization.commitTA(transactionId);
    } catch (FileNotFoundException e) {
      tw_visualization.abortTA(transactionId);
      throw new PepperModuleException(this, "Could not write to the file " + tw_visualization.getFile().getAbsolutePath() + ". Reason: " + e.getMessage(), e);
    }
  }

  /**
   * returns singleton object to manage relANNIS ids
   *
   *
   * @return
   */
  public GlobalIdManager getGlobalIdManager() {
    return this.globalIdManager;
  }
  
  public static class ResolverComparator implements Comparator<ResolverEntry>{

    @Override
    public int compare(ResolverEntry o1, ResolverEntry o2) {
      int byVis = o1.getVis().compareTo(o2.getVis());
      if(byVis != 0) {
        return byVis;
      }
      
      return o1.getDisplay().compareTo(o2.getDisplay());
    }
    
  }

}
