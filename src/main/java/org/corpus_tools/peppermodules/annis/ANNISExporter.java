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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentMap;

import org.corpus_tools.pepper.impl.PepperExporterImpl;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleException;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleNotReadyException;
import org.corpus_tools.peppermodules.annis.ResolverEntry.Vis;
import org.corpus_tools.peppermodules.annis.resolver.DomStatistics;
import org.corpus_tools.peppermodules.annis.resolver.PointingStatistics;
import org.corpus_tools.peppermodules.annis.resolver.QName;
import org.corpus_tools.peppermodules.annis.resolver.SpanStatistics;
import org.corpus_tools.peppermodules.annis.resolver.VirtualTokenStatistics;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SCorpusGraph;
import org.corpus_tools.salt.common.SStructure;
import org.corpus_tools.salt.core.SMetaAnnotation;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;

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

	private boolean mergeTextsWithTimeline = true;

	// ------------------------- IdManager
	/**
	 * object to manage ANNIS ids*
	 */
	private GlobalIdManager globalIdManager;

	private DomStatistics domStats;
	private SpanStatistics spanStats;
	private PointingStatistics pointingStats;
	private VirtualTokenStatistics virtualTokenStatistics;

	// =================================================== mandatory
	// ===================================================
	public ANNISExporter() {
		super();
		setProperties(new ANNISExporterProperties());
		this.setName("ANNISExporter");
		setSupplierContact(URI.createURI("saltnpepper@lists.hu-berlin.de"));
		// setSupplierHomepage(URI.createURI("https://github.com/korpling/pepperModules-ANNISModules"));
		setDesc("This exporter transforms a Salt model into the annis format. ");
		this.addSupportedFormat("relANNIS", "3.3", null);
		this.addSupportedFormat("annis", "3.3", null);
	}

	/**
	 * Creates a {@link Salt2ANNISMapper} object and passes this object, so that
	 * all {@link Salt2ANNISMapper} object can access the {@link IdManager}
	 * etc..
	 */
	@Override
	public PepperMapper createPepperMapper(Identifier sElementId) {
		Salt2ANNISMapper mapper = new Salt2ANNISMapper();
		mapper.setIdManager(new IdManager(globalIdManager));
		mapper.setGlobalDomStats(domStats);
		mapper.setGlobalPointingStats(pointingStats);
		mapper.setGlobalSpanStats(spanStats);
		mapper.setGlobalVirtualTokenStats(virtualTokenStatistics);
		mapper.setMergeTextsWithTimeline(mergeTextsWithTimeline);
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

		// a fix: it seems to be a bug, that ScorpusGraph is not set
		// automatically for mapper
		if (sElementId.getIdentifiableElement() != null) {
			if (sElementId.getIdentifiableElement() instanceof SCorpus) {
				mapper.setCorpusGraph(((SCorpus) sElementId.getIdentifiableElement()).getGraph());
			}
		}

		return mapper;

	}

	// =================================================== optional
	// ===================================================
	private synchronized TupleWriter createTupleWriter(File outFile) {
		return createTupleWriter(outFile, this.escapeCharacters, this.characterEscapeTable);
	}

	/**
	 * Creates a {@link TupleWriter} responsible for the given file.
	 *
	 * @param outFile
	 * @param escapeCharacters
	 *            states whether characters should be escaped
	 * @param characterEscapeTable
	 *            contains the character-escape-mapping
	 * @return
	 */
	public static synchronized TupleWriter createTupleWriter(File outFile, boolean escapeCharacters,
			ConcurrentMap<Character, String> characterEscapeTable) {
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		if (!outFile.exists()) {
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				throw new PepperModuleException("Could not create the corpus tab file " + outFile.getAbsolutePath()
						+ " Exception:" + e.getMessage());
			}
		}
		TupleWriter tWriter = new TupleWriter();

		tWriter.setEscaping(escapeCharacters);
		if (characterEscapeTable != null) {
			tWriter.setEscapeTable(characterEscapeTable);
		}

		tWriter.setFile(outFile);
		return (tWriter);
	}

	private ANNISExporterProperties getProps() {
		return ((ANNISExporterProperties) this.getProperties());
	}

	/**
	 * Initializes all {@link TupleWriter} objects.
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException {
		if (this.getProperties() != null) {
			overwriteResolverVisMap = getProps().getClobberResolverVisMap();
			overwriteCorpusAnnotations = getProps().getClobberCorpusAnnotations();
			String individualCorpusName_tmp = getProps().getIndividualCorpusName();

			// remove leading and trailing whitespaces of the individual corpus
			// name, if it is set.
			if (individualCorpusName_tmp != null) {
				this.individualCorpusName = individualCorpusName_tmp.trim();
			}

			this.escapeCharacters = getProps().getEscapeCharacters();
			if (this.escapeCharacters) {
				this.characterEscapeTable = getProps().getEscapeCharactersSet();
			}

			this.mergeTextsWithTimeline = getProps().getMergeTextsWithTimeline();
		}
		this.globalIdManager = new GlobalIdManager();
		this.domStats = new DomStatistics();
		this.spanStats = new SpanStatistics();
		this.pointingStats = new PointingStatistics();
		this.virtualTokenStatistics = new VirtualTokenStatistics();

		return (super.isReadyToStart());
	}

	private void writeVersionFiles() {
		final File versionFile = new File(getCorpusDesc().getCorpusPath().toFileString(), ANNIS.FILE_VERSION);
		try {
			Files.write(ANNIS.ANNIS_VERSION, versionFile, Charsets.UTF_8);
		} catch (IOException ex) {
			log.error("Can't write {} file", ex, ANNIS.FILE_VERSION);
		}
	}

	private void createTupleWriters() {
		final String corpPath = getCorpusDesc().getCorpusPath().toFileString()
				+ ((getCorpusDesc().getCorpusPath().toFileString().endsWith("/")) ? "" : "/");
		tw_text = createTupleWriter(new File(corpPath + FILE_TEXT));
		tw_node = createTupleWriter(new File(corpPath + FILE_NODE));
		tw_nodeAnno = createTupleWriter(new File(corpPath + FILE_NODE_ANNO));
		tw_rank = createTupleWriter(new File(corpPath + FILE_RANK));
		tw_edgeAnno = createTupleWriter(new File(corpPath + FILE_EDGE_ANNO));
		tw_component = createTupleWriter(new File(corpPath + FILE_COMPONENT));
		tw_corpus = createTupleWriter(new File(corpPath + FILE_CORPUS));

		// set the visualisation tuple writer
		final File resolverVisFile = new File(corpPath + FILE_VISUALIZATION);
		if (!resolverVisFile.exists()) {
			tw_visualization = createTupleWriter(resolverVisFile);
		} else if (overwriteResolverVisMap) {
			tw_visualization = createTupleWriter(resolverVisFile);
		}

		// set the corpus meta annotation tuple writer
		final File corpusAnnotationFile = new File(corpPath + FILE_CORPUS_META);
		if (!corpusAnnotationFile.exists()) {
			tw_corpusMeta = createTupleWriter(corpusAnnotationFile);
		} else if (overwriteCorpusAnnotations) {
			tw_corpusMeta = createTupleWriter(corpusAnnotationFile);
		}
	}

	@Override
	public void start() throws PepperModuleException {
		createTupleWriters();
		writeVersionFiles();
		super.start();
	}

	/**
	 * Creates resolver entries for:
	 * <ul>
	 * <li>all virtual tokens (artificial tokens in case there are several
	 * segmentations)</li>
	 * <li>all layers in {@link #spanStats}</li>
	 * <li>all layers in {@link #domStats}</li>
	 * <li>all layers in {@link #pointingStats}</li>
	 * <li>media data</li>
	 * </ul>
	 */
	@Override
	public void end() throws PepperModuleException {
		super.end();

		createVirtualTokenizationResolverEntries();

		for (String l : spanStats.getLayers()) {
			createSpanResolverEntry(l);
		}

		for (String l : domStats.getLayers()) {
			createDominanceResolverEntry(l);
		}

		for (QName l : pointingStats.getLayers()) {
			createPointingResolverEntry(l);
		}

		createMediaResolverEntries();

		for (SCorpusGraph corpusGraph : getSaltProject().getCorpusGraphs()) {
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

		Set<QName> annosForLayer = spanStats.getNodeAnnotations(layer);

		Set<QName> virtualAnnoNames = virtualTokenStatistics.getVirtualAnnoNames();
		if (!virtualAnnoNames.isEmpty()) {
			// don't show the empty token but the virtual token annos instead
			entry.getMappings().put("hide_tok", "true");
			LinkedHashSet<String> annoNameFilter = new LinkedHashSet<>();

			for (QName a : annosForLayer) {
				String ns = a.getNs();
				if (QName.NULL.equals(ns)) {
					ns = SRelation2ANNISMapper.DEFAULT_NS;
				}
				annoNameFilter.add("/" + ns + "::" + a.getName() + "/");
			}
			for (QName virtualAnno : virtualAnnoNames) {
				String ns = virtualAnno.hasNs() ? virtualAnno.getNs() : SRelation2ANNISMapper.DEFAULT_NS;
				annoNameFilter.add("/" + ns + "::" + virtualAnno.getName() + "/");
				// also add the new annotation name to the overall list in case
				// there are
				// duplicate annotation names in different namespaces
				annosForLayer.add(virtualAnno);
			}

			entry.getMappings().put("annos", Joiner.on(",").join(annoNameFilter));

		}

		// check for entries that only differ in their namespace but have the
		// same name
		boolean showNamespace = false;
		HashSet<String> annoNames = new HashSet<>();
		for (QName n : annosForLayer) {
			if (annoNames.contains(n.getName())) {
				showNamespace = true;
				break;
			} else {
				annoNames.add(n.getName());
			}
		}

		entry.setVis(Vis.grid);

		entry.setDisplay(displayName);
		if (layer != null) {
			entry.setElement(ResolverEntry.Element.node);
			if (virtualAnnoNames.isEmpty()) {
				entry.setLayerName(layer);
			}
		}
		if (showNamespace) {
			entry.getMappings().put("show_ns", "true");
		}

		globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
	}

	/**
	 * Creates a resolver entry for the passed layer. The passed layer must be a
	 * layer containing {@link SStructure} nodes. The visualizer is chosen
	 * following this heuristic:
	 * <table border="1">
	 * <tr>
	 * <td>rstdoc</td>
	 * <td>layerName starts with 'rhet' or is equal to 'rst'</td>
	 * </tr>
	 * <tr>
	 * <td>tree</td>
	 * <td>otherwise</td>
	 * </tr>
	 * </table>
	 *
	 * @param layerName
	 */
	private void createDominanceResolverEntry(String layerName) {

		ResolverEntry entry = new ResolverEntry();

		if (layerName != null && (layerName.equalsIgnoreCase("rst") || layerName.startsWith("rhet"))) {

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

			if (terminalAnnos.size() >= 1) {
				QName qname = terminalAnnos.iterator().next();
				entry.getMappings().put("terminal_name", globalIdManager.getEscapedIdentifier(qname.getName()));
				if (!QName.NULL.equals(qname.getNs())) {
					entry.getMappings().put("terminal_ns", globalIdManager.getEscapedIdentifier(qname.getNs()));
				}
			}

			SortedMap<Integer, QName> nodeAnnos = domStats.getNodeAnnoCounter().getBySize(layerName);

			if (nodeAnnos.size() >= 1) {
				QName qname = nodeAnnos.get(nodeAnnos.lastKey());
				entry.getMappings().put("node_key", globalIdManager.getEscapedIdentifier(qname.getName()));

				String nodeNS = qname.getNs();
				if (nodeNS == null || QName.NULL.equals(nodeNS)) {
					nodeNS = SRelation2ANNISMapper.DEFAULT_NS;
				}
				// always output the anno namespace if it is not the same as the
				// layer
				// this visualization is triggered with
				if (!nodeNS.equals(layerName)) {
					entry.getMappings().put("node_anno_ns", globalIdManager.getEscapedIdentifier(nodeNS));
				}
			}

			Set<QName> edgeAnnos = domStats.getEdgeAnno().get(layerName);

			if (edgeAnnos.size() >= 1) {
				QName qname = edgeAnnos.iterator().next();
				entry.getMappings().put("edge_key", globalIdManager.getEscapedIdentifier(qname.getName()));
				if (!QName.NULL.equals(qname.getNs())) {
					entry.getMappings().put("edge_anno_ns", globalIdManager.getEscapedIdentifier(qname.getNs()));
				}
			}

			SortedMap<Integer, String> etypes = domStats.getEdgeTypeCounter().getBySize(layerName);
			if (etypes.isEmpty()
					|| (((ANNISExporterProperties) getProperties()).getExcludeSingleDomType() && etypes.size() <= 1)) {
				entry.getMappings().put("edge_type", globalIdManager.getEscapedIdentifier("null"));
			} else {
				// the primary edge type always has the greatest number of
				// entries
				String primaryType = etypes.get(etypes.lastKey());
				String secondaryType = etypes.size() >= 2 ? etypes.get(etypes.firstKey()) : null;

				// check if the terminal nodes are reachable by the original
				// types
				// use the special "null" type if not
				Set<String> terminalRelationTypes = domStats.getTerminalEdgeType().get(layerName);
				if (secondaryType == null) {
					if (!terminalRelationTypes.contains(primaryType)) {
						primaryType = "null";
					}
				} else {
					if (!terminalRelationTypes.contains(primaryType)
							&& !terminalRelationTypes.contains(secondaryType)) {
						primaryType = "null";
					}
				}

				entry.getMappings().put("edge_type", globalIdManager.getEscapedIdentifier(primaryType));
				if (secondaryType != null) {
					entry.getMappings().put("secedge_type", globalIdManager.getEscapedIdentifier(secondaryType));
				}
			}
		}
		globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);

	}

	private void createPointingResolverEntry(QName layerName) {

		ResolverEntry entry = new ResolverEntry();
		Set<QName> terminalAnnos = pointingStats.getTerminalAnno().get(layerName);

		if (terminalAnnos.size() <= 1) {
			// use arch_dependency visualizer
			entry.setVis(Vis.arch_dependency);

			if (terminalAnnos.size() == 1) {
				QName qname = terminalAnnos.iterator().next();
				entry.getMappings().put("node_key", globalIdManager.getEscapedIdentifier(qname.getName()));
			}
		} else {

			// use coref visualizer for "large" documents and discourse
			// (fulltext) otherwise
			if (pointingStats.getMaxNodeCount() <= MAX_NUM_OF_NODES_FOR_DISCOURSE) {
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

		if (globalIdManager.isAudioFound()) {
			ResolverEntry entry = new ResolverEntry();
			entry.setDisplay("audio");
			entry.setVis(Vis.audio);
			entry.setVisibility(ResolverEntry.Visibility.preloaded);
			globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
		}

		if (globalIdManager.isVideoFound()) {
			ResolverEntry entry = new ResolverEntry();
			entry.setDisplay("video");
			entry.setVis(Vis.video);
			entry.setVisibility(ResolverEntry.Visibility.preloaded);
			globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
		}

		if (globalIdManager.isPDFFound()) {
			ResolverEntry entry = new ResolverEntry();
			entry.setDisplay("pdf");
			entry.setVis(Vis.pdf);
			entry.setVisibility(ResolverEntry.Visibility.preloaded);
			globalIdManager.getResolverEntryByDisplay().putIfAbsent(entry.getDisplay(), entry);
		}
	}

	private void createVirtualTokenizationResolverEntries() {

		Set<QName> virtualAnnoNames = virtualTokenStatistics.getVirtualAnnoNames();

		/*
		 * Display a "dialog" view containing the virtual anno names (which are
		 * the union of all known segmentation names and text names that have
		 * been the source for the virtual token generation). This resolver
		 * entry is only created if we know at least one of the anno names and
		 * if either a virtual tokenization was created or the original minimal
		 * tokenization was only containing empty token.
		 */
		if (!virtualAnnoNames.isEmpty()
				&& (globalIdManager.containsVirtualTokens() || !virtualTokenStatistics.getHasRealToken())) {
			// deactivate the original kwic view
			ResolverEntry entryHideKWIC = new ResolverEntry();
			entryHideKWIC.setDisplay("kwic");
			entryHideKWIC.setVisibility(ResolverEntry.Visibility.removed);
			entryHideKWIC.setVis(Vis.kwic);
			globalIdManager.getResolverEntryByDisplay().putIfAbsent(entryHideKWIC.getDisplay(), entryHideKWIC);

			// create a permanent grid which displays all order relations
			ResolverEntry entryGrid = new ResolverEntry();
			entryGrid.setDisplay("");
			entryGrid.setVis(Vis.grid);
			entryGrid.setElement(ResolverEntry.Element.node);
			entryGrid.setVisibility(ResolverEntry.Visibility.permanent);
			entryGrid.getMappings().put("hide_tok", "true");

			Set<String> escapedNames = new LinkedHashSet<>();

			for (QName origName : virtualAnnoNames) {
				escapedNames.add(globalIdManager.getEscapedIdentifier(origName.getName()));
			}

			entryGrid.getMappings().put("annos", Joiner.on(",").join(escapedNames));
			globalIdManager.getResolverEntryByDisplay().putIfAbsent(entryGrid.getDisplay(), entryGrid);
		}
	}

	/**
	 * This method prints the resolver_vis_map.annis file
	 *
	 * @param corpusGraph
	 *            the corpus graph
	 */
	private void printResolverVisMap(SCorpusGraph corpusGraph) {

		Long transactionId = tw_visualization.beginTA();

		try {

			String corpusName = "NULL";
			String corpusVersion = "NULL";
			// get the version of the corpus but initialise the default NULL
			if (corpusGraph.getRoots() != null) {
				if (corpusGraph.getRoots().size() > 0) {
					SCorpus rootCorpus = (SCorpus) corpusGraph.getRoots().get(0);
					// set corpus name
					if (this.individualCorpusName != null) {
						corpusName = this.individualCorpusName;
					} else {
						corpusName = rootCorpus.getName();
					}

					// set corpus version
					SMetaAnnotation version = rootCorpus.getMetaAnnotation("version");
					if (version != null) {
						if (version.getValue_STEXT() != null) {
							corpusVersion = version.getValue_STEXT();
						}
					}
				}
			}

			List<ResolverEntry> entries = new ArrayList<>(getGlobalIdManager().getResolverEntryByDisplay().values());

			// sort the entries
			Collections.sort(entries, new ResolverComparator());

			int order = 1;
			for (ResolverEntry e : entries) {
				e.setOrder(order);

				List<String> resolverTuple = new ArrayList<>();

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
			throw new PepperModuleException(this, "Could not write to the file "
					+ tw_visualization.getFile().getAbsolutePath() + ". Reason: " + e.getMessage(), e);
		}
	}

	/**
	 * returns singleton object to manage ANNIS ids
	 *
	 *
	 * @return
	 */
	public GlobalIdManager getGlobalIdManager() {
		return this.globalIdManager;
	}

	public static class ResolverComparator implements Comparator<ResolverEntry> {

		@Override
		public int compare(ResolverEntry o1, ResolverEntry o2) {
			int byVis = o1.getVis().compareTo(o2.getVis());
			if (byVis != 0) {
				return byVis;
			}

			return o1.getDisplay().compareTo(o2.getDisplay());
		}

	}

}
