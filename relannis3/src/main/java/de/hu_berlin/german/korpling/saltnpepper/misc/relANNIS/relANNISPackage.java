/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCorePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory
 * @model kind="package"
 * @generated
 */
public interface relANNISPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "relANNIS";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "relANNIS";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "relANNIS";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	relANNISPackage eINSTANCE = de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl <em>RA Corpus</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpus()
	 * @generated
	 */
	int RA_CORPUS = 0;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__LABELS = SaltCorePackage.SNODE__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__ID = SaltCorePackage.SNODE__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__IDENTIFIER = SaltCorePackage.SNODE__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__GRAPH = SaltCorePackage.SNODE__GRAPH;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__LAYERS = SaltCorePackage.SNODE__LAYERS;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SANNOTATIONS = SaltCorePackage.SNODE__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SFEATURES = SaltCorePackage.SNODE__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SNAME = SaltCorePackage.SNODE__SNAME;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SELEMENT_ID = SaltCorePackage.SNODE__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SID = SaltCorePackage.SNODE__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SELEMENT_PATH = SaltCorePackage.SNODE__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SPROCESSING_ANNOTATIONS = SaltCorePackage.SNODE__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SMETA_ANNOTATIONS = SaltCorePackage.SNODE__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SGRAPH = SaltCorePackage.SNODE__SGRAPH;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__SLAYERS = SaltCorePackage.SNODE__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_ID = SaltCorePackage.SNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_NAME = SaltCorePackage.SNODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_TYPE = SaltCorePackage.SNODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_VERSION = SaltCorePackage.SNODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Pre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_PRE = SaltCorePackage.SNODE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Ra Post</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_POST = SaltCorePackage.SNODE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Ra Corpus Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_CORPUS_ANNOTATIONS = SaltCorePackage.SNODE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Ra Document Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_DOCUMENT_GRAPH = SaltCorePackage.SNODE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Ra Corpus Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS__RA_CORPUS_GRAPH = SaltCorePackage.SNODE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>RA Corpus</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_FEATURE_COUNT = SaltCorePackage.SNODE_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusAnnotationImpl <em>RA Corpus Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusAnnotationImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpusAnnotation()
	 * @generated
	 */
	int RA_CORPUS_ANNOTATION = 1;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__LABELS = SaltCorePackage.SANNOTATION__LABELS;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__NAMESPACE = SaltCorePackage.SANNOTATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__NAME = SaltCorePackage.SANNOTATION__NAME;

	/**
	 * The feature id for the '<em><b>QName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__QNAME = SaltCorePackage.SANNOTATION__QNAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__VALUE = SaltCorePackage.SANNOTATION__VALUE;

	/**
	 * The feature id for the '<em><b>Labelable Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__LABELABLE_ELEMENT = SaltCorePackage.SANNOTATION__LABELABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__VALUE_STRING = SaltCorePackage.SANNOTATION__VALUE;

	/**
	 * The feature id for the '<em><b>SNS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__SNS = SaltCorePackage.SANNOTATION__SNS;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__SNAME = SaltCorePackage.SANNOTATION__SNAME;

	/**
	 * The feature id for the '<em><b>SValue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__SVALUE = SaltCorePackage.SANNOTATION__SVALUE;

	/**
	 * The feature id for the '<em><b>SValue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__SVALUE_TYPE = SaltCorePackage.SANNOTATION__SVALUE_TYPE;

	/**
	 * The feature id for the '<em><b>SAnnotatable Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__SANNOTATABLE_ELEMENT = SaltCorePackage.SANNOTATION__SANNOTATABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Ra Corpus ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__RA_CORPUS_REF = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__RA_NAMESPACE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__RA_NAME = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__RA_VALUE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Corpus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION__RA_CORPUS = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>RA Corpus Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_ANNOTATION_FEATURE_COUNT = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusGraphImpl <em>RA Corpus Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusGraphImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpusGraph()
	 * @generated
	 */
	int RA_CORPUS_GRAPH = 2;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__LABELS = SaltCorePackage.SGRAPH__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__ID = SaltCorePackage.SGRAPH__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__IDENTIFIER = SaltCorePackage.SGRAPH__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Index Mgr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__INDEX_MGR = SaltCorePackage.SGRAPH__INDEX_MGR;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__NODES = SaltCorePackage.SGRAPH__NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__EDGES = SaltCorePackage.SGRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Num Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__NUM_OF_NODES = SaltCorePackage.SGRAPH__NUM_OF_NODES;

	/**
	 * The feature id for the '<em><b>Num Of Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__NUM_OF_EDGES = SaltCorePackage.SGRAPH__NUM_OF_EDGES;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__LAYERS = SaltCorePackage.SGRAPH__LAYERS;

	/**
	 * The feature id for the '<em><b>Num Of Layers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__NUM_OF_LAYERS = SaltCorePackage.SGRAPH__NUM_OF_LAYERS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SFEATURES = SaltCorePackage.SGRAPH__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SNAME = SaltCorePackage.SGRAPH__SNAME;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SANNOTATIONS = SaltCorePackage.SGRAPH__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SELEMENT_ID = SaltCorePackage.SGRAPH__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SID = SaltCorePackage.SGRAPH__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SELEMENT_PATH = SaltCorePackage.SGRAPH__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SPROCESSING_ANNOTATIONS = SaltCorePackage.SGRAPH__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SMETA_ANNOTATIONS = SaltCorePackage.SGRAPH__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SRelations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SRELATIONS = SaltCorePackage.SGRAPH__SRELATIONS;

	/**
	 * The feature id for the '<em><b>SNodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SNODES = SaltCorePackage.SGRAPH__SNODES;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__SLAYERS = SaltCorePackage.SGRAPH__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Corpora</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH__RA_CORPORA = SaltCorePackage.SGRAPH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>RA Corpus Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_GRAPH_FEATURE_COUNT = SaltCorePackage.SGRAPH_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusRelationImpl <em>RA Corpus Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusRelationImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpusRelation()
	 * @generated
	 */
	int RA_CORPUS_RELATION = 3;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__LABELS = SaltCorePackage.SRELATION__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__ID = SaltCorePackage.SRELATION__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__IDENTIFIER = SaltCorePackage.SRELATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__GRAPH = SaltCorePackage.SRELATION__GRAPH;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SOURCE = SaltCorePackage.SRELATION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__TARGET = SaltCorePackage.SRELATION__TARGET;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__LAYERS = SaltCorePackage.SRELATION__LAYERS;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SANNOTATIONS = SaltCorePackage.SRELATION__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SFEATURES = SaltCorePackage.SRELATION__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SNAME = SaltCorePackage.SRELATION__SNAME;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SELEMENT_ID = SaltCorePackage.SRELATION__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SID = SaltCorePackage.SRELATION__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SELEMENT_PATH = SaltCorePackage.SRELATION__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SPROCESSING_ANNOTATIONS = SaltCorePackage.SRELATION__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SMETA_ANNOTATIONS = SaltCorePackage.SRELATION__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SSource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SSOURCE = SaltCorePackage.SRELATION__SSOURCE;

	/**
	 * The feature id for the '<em><b>STarget</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__STARGET = SaltCorePackage.SRELATION__STARGET;

	/**
	 * The feature id for the '<em><b>SGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SGRAPH = SaltCorePackage.SRELATION__SGRAPH;

	/**
	 * The feature id for the '<em><b>STypes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__STYPES = SaltCorePackage.SRELATION__STYPES;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__SLAYERS = SaltCorePackage.SRELATION__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Super Corpus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__RA_SUPER_CORPUS = SaltCorePackage.SRELATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Sub Corpus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION__RA_SUB_CORPUS = SaltCorePackage.SRELATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>RA Corpus Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_CORPUS_RELATION_FEATURE_COUNT = SaltCorePackage.SRELATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl <em>RA Document Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRADocumentGraph()
	 * @generated
	 */
	int RA_DOCUMENT_GRAPH = 4;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__LABELS = SaltCorePackage.SGRAPH__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__ID = SaltCorePackage.SGRAPH__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__IDENTIFIER = SaltCorePackage.SGRAPH__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Index Mgr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__INDEX_MGR = SaltCorePackage.SGRAPH__INDEX_MGR;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__NODES = SaltCorePackage.SGRAPH__NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__EDGES = SaltCorePackage.SGRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Num Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__NUM_OF_NODES = SaltCorePackage.SGRAPH__NUM_OF_NODES;

	/**
	 * The feature id for the '<em><b>Num Of Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__NUM_OF_EDGES = SaltCorePackage.SGRAPH__NUM_OF_EDGES;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__LAYERS = SaltCorePackage.SGRAPH__LAYERS;

	/**
	 * The feature id for the '<em><b>Num Of Layers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__NUM_OF_LAYERS = SaltCorePackage.SGRAPH__NUM_OF_LAYERS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SFEATURES = SaltCorePackage.SGRAPH__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SNAME = SaltCorePackage.SGRAPH__SNAME;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SANNOTATIONS = SaltCorePackage.SGRAPH__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SELEMENT_ID = SaltCorePackage.SGRAPH__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SID = SaltCorePackage.SGRAPH__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SELEMENT_PATH = SaltCorePackage.SGRAPH__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SPROCESSING_ANNOTATIONS = SaltCorePackage.SGRAPH__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SMETA_ANNOTATIONS = SaltCorePackage.SGRAPH__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SRelations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SRELATIONS = SaltCorePackage.SGRAPH__SRELATIONS;

	/**
	 * The feature id for the '<em><b>SNodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SNODES = SaltCorePackage.SGRAPH__SNODES;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__SLAYERS = SaltCorePackage.SGRAPH__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Corpus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__RA_CORPUS = SaltCorePackage.SGRAPH_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__RA_NODES = SaltCorePackage.SGRAPH_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Components</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__RA_COMPONENTS = SaltCorePackage.SGRAPH_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Texts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__RA_TEXTS = SaltCorePackage.SGRAPH_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Ranks</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH__RA_RANKS = SaltCorePackage.SGRAPH_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>RA Document Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_DOCUMENT_GRAPH_FEATURE_COUNT = SaltCorePackage.SGRAPH_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl <em>RA Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRANode()
	 * @generated
	 */
	int RA_NODE = 5;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__LABELS = SaltCorePackage.SNODE__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__ID = SaltCorePackage.SNODE__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__IDENTIFIER = SaltCorePackage.SNODE__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__GRAPH = SaltCorePackage.SNODE__GRAPH;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__LAYERS = SaltCorePackage.SNODE__LAYERS;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SANNOTATIONS = SaltCorePackage.SNODE__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SFEATURES = SaltCorePackage.SNODE__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SNAME = SaltCorePackage.SNODE__SNAME;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SELEMENT_ID = SaltCorePackage.SNODE__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SID = SaltCorePackage.SNODE__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SELEMENT_PATH = SaltCorePackage.SNODE__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SPROCESSING_ANNOTATIONS = SaltCorePackage.SNODE__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SMETA_ANNOTATIONS = SaltCorePackage.SNODE__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SGRAPH = SaltCorePackage.SNODE__SGRAPH;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SLAYERS = SaltCorePackage.SNODE__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_ID = SaltCorePackage.SNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Text ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_TEXT_REF = SaltCorePackage.SNODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Corpus ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_CORPUS_REF = SaltCorePackage.SNODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_NAMESPACE = SaltCorePackage.SNODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_NAME = SaltCorePackage.SNODE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Ra Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_LEFT = SaltCorePackage.SNODE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Ra Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_RIGHT = SaltCorePackage.SNODE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Ra Token Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_TOKEN_INDEX = SaltCorePackage.SNODE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Ra Continuous</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_CONTINUOUS = SaltCorePackage.SNODE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Ra Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_SPAN = SaltCorePackage.SNODE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Ra Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_ANNOTATIONS = SaltCorePackage.SNODE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Ra Document Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_DOCUMENT_GRAPH = SaltCorePackage.SNODE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Ra Text</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RA_TEXT = SaltCorePackage.SNODE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Segment name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__SEGMENT_NAME = SaltCorePackage.SNODE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Left Segment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__LEFT_SEGMENT = SaltCorePackage.SNODE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Right Segment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE__RIGHT_SEGMENT = SaltCorePackage.SNODE_FEATURE_COUNT + 15;

	/**
	 * The number of structural features of the '<em>RA Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_FEATURE_COUNT = SaltCorePackage.SNODE_FEATURE_COUNT + 16;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl <em>RA Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRAText()
	 * @generated
	 */
	int RA_TEXT = 6;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__LABELS = SaltCorePackage.SNODE__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__ID = SaltCorePackage.SNODE__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__IDENTIFIER = SaltCorePackage.SNODE__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__GRAPH = SaltCorePackage.SNODE__GRAPH;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__LAYERS = SaltCorePackage.SNODE__LAYERS;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SANNOTATIONS = SaltCorePackage.SNODE__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SFEATURES = SaltCorePackage.SNODE__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SNAME = SaltCorePackage.SNODE__SNAME;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SELEMENT_ID = SaltCorePackage.SNODE__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SID = SaltCorePackage.SNODE__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SELEMENT_PATH = SaltCorePackage.SNODE__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SPROCESSING_ANNOTATIONS = SaltCorePackage.SNODE__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SMETA_ANNOTATIONS = SaltCorePackage.SNODE__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SGRAPH = SaltCorePackage.SNODE__SGRAPH;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__SLAYERS = SaltCorePackage.SNODE__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__RA_ID = SaltCorePackage.SNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__RA_NAME = SaltCorePackage.SNODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT__RA_TEXT = SaltCorePackage.SNODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>RA Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_TEXT_FEATURE_COUNT = SaltCorePackage.SNODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl <em>RA Node Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRANodeAnnotation()
	 * @generated
	 */
	int RA_NODE_ANNOTATION = 7;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__LABELS = SaltCorePackage.SANNOTATION__LABELS;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__NAMESPACE = SaltCorePackage.SANNOTATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__NAME = SaltCorePackage.SANNOTATION__NAME;

	/**
	 * The feature id for the '<em><b>QName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__QNAME = SaltCorePackage.SANNOTATION__QNAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__VALUE = SaltCorePackage.SANNOTATION__VALUE;

	/**
	 * The feature id for the '<em><b>Labelable Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__LABELABLE_ELEMENT = SaltCorePackage.SANNOTATION__LABELABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__VALUE_STRING = SaltCorePackage.SANNOTATION__VALUE;

	/**
	 * The feature id for the '<em><b>SNS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__SNS = SaltCorePackage.SANNOTATION__SNS;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__SNAME = SaltCorePackage.SANNOTATION__SNAME;

	/**
	 * The feature id for the '<em><b>SValue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__SVALUE = SaltCorePackage.SANNOTATION__SVALUE;

	/**
	 * The feature id for the '<em><b>SValue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__SVALUE_TYPE = SaltCorePackage.SANNOTATION__SVALUE_TYPE;

	/**
	 * The feature id for the '<em><b>SAnnotatable Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__SANNOTATABLE_ELEMENT = SaltCorePackage.SANNOTATION__SANNOTATABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Ra Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__RA_NODE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Node ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__RA_NODE_REF = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__RA_NAMESPACE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__RA_NAME = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION__RA_VALUE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>RA Node Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_NODE_ANNOTATION_FEATURE_COUNT = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl <em>RA Rank</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRARank()
	 * @generated
	 */
	int RA_RANK = 8;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__LABELS = SaltCorePackage.SRELATION__LABELS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__ID = SaltCorePackage.SRELATION__ID;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__IDENTIFIER = SaltCorePackage.SRELATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__GRAPH = SaltCorePackage.SRELATION__GRAPH;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SOURCE = SaltCorePackage.SRELATION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__TARGET = SaltCorePackage.SRELATION__TARGET;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__LAYERS = SaltCorePackage.SRELATION__LAYERS;

	/**
	 * The feature id for the '<em><b>SAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SANNOTATIONS = SaltCorePackage.SRELATION__SANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SFeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SFEATURES = SaltCorePackage.SRELATION__SFEATURES;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SNAME = SaltCorePackage.SRELATION__SNAME;

	/**
	 * The feature id for the '<em><b>SElement Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SELEMENT_ID = SaltCorePackage.SRELATION__SELEMENT_ID;

	/**
	 * The feature id for the '<em><b>SId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SID = SaltCorePackage.SRELATION__SID;

	/**
	 * The feature id for the '<em><b>SElement Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SELEMENT_PATH = SaltCorePackage.SRELATION__SELEMENT_PATH;

	/**
	 * The feature id for the '<em><b>SProcessing Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SPROCESSING_ANNOTATIONS = SaltCorePackage.SRELATION__SPROCESSING_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SMeta Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SMETA_ANNOTATIONS = SaltCorePackage.SRELATION__SMETA_ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>SSource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SSOURCE = SaltCorePackage.SRELATION__SSOURCE;

	/**
	 * The feature id for the '<em><b>STarget</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__STARGET = SaltCorePackage.SRELATION__STARGET;

	/**
	 * The feature id for the '<em><b>SGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SGRAPH = SaltCorePackage.SRELATION__SGRAPH;

	/**
	 * The feature id for the '<em><b>STypes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__STYPES = SaltCorePackage.SRELATION__STYPES;

	/**
	 * The feature id for the '<em><b>SLayers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__SLAYERS = SaltCorePackage.SRELATION__SLAYERS;

	/**
	 * The feature id for the '<em><b>Ra Parent Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_PARENT_NODE = SaltCorePackage.SRELATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_NODE = SaltCorePackage.SRELATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_ANNOTATIONS = SaltCorePackage.SRELATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Pre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_PRE = SaltCorePackage.SRELATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Post</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_POST = SaltCorePackage.SRELATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Ra Node ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_NODE_REF = SaltCorePackage.SRELATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Ra Component ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_COMPONENT_REF = SaltCorePackage.SRELATION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Ra Parent ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_PARENT_REF = SaltCorePackage.SRELATION_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Ra Parent Rank</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_PARENT_RANK = SaltCorePackage.SRELATION_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Ra Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK__RA_COMPONENT = SaltCorePackage.SRELATION_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>RA Rank</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_RANK_FEATURE_COUNT = SaltCorePackage.SRELATION_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl <em>RA Edge Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRAEdgeAnnotation()
	 * @generated
	 */
	int RA_EDGE_ANNOTATION = 9;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__LABELS = SaltCorePackage.SANNOTATION__LABELS;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__NAMESPACE = SaltCorePackage.SANNOTATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__NAME = SaltCorePackage.SANNOTATION__NAME;

	/**
	 * The feature id for the '<em><b>QName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__QNAME = SaltCorePackage.SANNOTATION__QNAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__VALUE = SaltCorePackage.SANNOTATION__VALUE;

	/**
	 * The feature id for the '<em><b>Labelable Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__LABELABLE_ELEMENT = SaltCorePackage.SANNOTATION__LABELABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__VALUE_STRING = SaltCorePackage.SANNOTATION__VALUE;

	/**
	 * The feature id for the '<em><b>SNS</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__SNS = SaltCorePackage.SANNOTATION__SNS;

	/**
	 * The feature id for the '<em><b>SName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__SNAME = SaltCorePackage.SANNOTATION__SNAME;

	/**
	 * The feature id for the '<em><b>SValue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__SVALUE = SaltCorePackage.SANNOTATION__SVALUE;

	/**
	 * The feature id for the '<em><b>SValue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__SVALUE_TYPE = SaltCorePackage.SANNOTATION__SVALUE_TYPE;

	/**
	 * The feature id for the '<em><b>SAnnotatable Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__SANNOTATABLE_ELEMENT = SaltCorePackage.SANNOTATION__SANNOTATABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Ra Rank</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__RA_RANK = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ra Rank ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__RA_RANK_REF = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ra Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__RA_NAMESPACE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__RA_NAME = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ra Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION__RA_VALUE = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>RA Edge Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_EDGE_ANNOTATION_FEATURE_COUNT = SaltCorePackage.SANNOTATION_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl <em>RA Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRAComponent()
	 * @generated
	 */
	int RA_COMPONENT = 10;

	/**
	 * The feature id for the '<em><b>Ra Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_COMPONENT__RA_ID = 0;

	/**
	 * The feature id for the '<em><b>Ra Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_COMPONENT__RA_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Ra Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_COMPONENT__RA_NAME = 2;

	/**
	 * The feature id for the '<em><b>Ra Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_COMPONENT__RA_NAMESPACE = 3;

	/**
	 * The number of structural features of the '<em>RA Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RA_COMPONENT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE <em>RA CORPUS TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRA_CORPUS_TYPE()
	 * @generated
	 */
	int RA_CORPUS_TYPE = 11;


	/**
	 * The meta object id for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE <em>RA COMPONENT TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRA_COMPONENT_TYPE()
	 * @generated
	 */
	int RA_COMPONENT_TYPE = 12;


	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus <em>RA Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Corpus</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus
	 * @generated
	 */
	EClass getRACorpus();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaId <em>Ra Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaId()
	 * @see #getRACorpus()
	 * @generated
	 */
	EAttribute getRACorpus_RaId();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaName()
	 * @see #getRACorpus()
	 * @generated
	 */
	EAttribute getRACorpus_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaType <em>Ra Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Type</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaType()
	 * @see #getRACorpus()
	 * @generated
	 */
	EAttribute getRACorpus_RaType();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaVersion <em>Ra Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Version</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaVersion()
	 * @see #getRACorpus()
	 * @generated
	 */
	EAttribute getRACorpus_RaVersion();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPre <em>Ra Pre</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Pre</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPre()
	 * @see #getRACorpus()
	 * @generated
	 */
	EAttribute getRACorpus_RaPre();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPost <em>Ra Post</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Post</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaPost()
	 * @see #getRACorpus()
	 * @generated
	 */
	EAttribute getRACorpus_RaPost();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusAnnotations <em>Ra Corpus Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Corpus Annotations</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusAnnotations()
	 * @see #getRACorpus()
	 * @generated
	 */
	EReference getRACorpus_RaCorpusAnnotations();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaDocumentGraph <em>Ra Document Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Document Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaDocumentGraph()
	 * @see #getRACorpus()
	 * @generated
	 */
	EReference getRACorpus_RaDocumentGraph();

	/**
	 * Returns the meta object for the container reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusGraph <em>Ra Corpus Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Ra Corpus Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus#getRaCorpusGraph()
	 * @see #getRACorpus()
	 * @generated
	 */
	EReference getRACorpus_RaCorpusGraph();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation <em>RA Corpus Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Corpus Annotation</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation
	 * @generated
	 */
	EClass getRACorpusAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaNamespace <em>Ra Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Namespace</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaNamespace()
	 * @see #getRACorpusAnnotation()
	 * @generated
	 */
	EAttribute getRACorpusAnnotation_RaNamespace();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaName()
	 * @see #getRACorpusAnnotation()
	 * @generated
	 */
	EAttribute getRACorpusAnnotation_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaValue <em>Ra Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Value</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaValue()
	 * @see #getRACorpusAnnotation()
	 * @generated
	 */
	EAttribute getRACorpusAnnotation_RaValue();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaCorpus <em>Ra Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Corpus</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaCorpus()
	 * @see #getRACorpusAnnotation()
	 * @generated
	 */
	EReference getRACorpusAnnotation_RaCorpus();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaCorpus_ref <em>Ra Corpus ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Corpus ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation#getRaCorpus_ref()
	 * @see #getRACorpusAnnotation()
	 * @generated
	 */
	EAttribute getRACorpusAnnotation_RaCorpus_ref();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph <em>RA Corpus Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Corpus Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph
	 * @generated
	 */
	EClass getRACorpusGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph#getRaCorpora <em>Ra Corpora</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ra Corpora</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph#getRaCorpora()
	 * @see #getRACorpusGraph()
	 * @generated
	 */
	EReference getRACorpusGraph_RaCorpora();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation <em>RA Corpus Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Corpus Relation</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation
	 * @generated
	 */
	EClass getRACorpusRelation();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSuperCorpus <em>Ra Super Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Super Corpus</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSuperCorpus()
	 * @see #getRACorpusRelation()
	 * @generated
	 */
	EReference getRACorpusRelation_RaSuperCorpus();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSubCorpus <em>Ra Sub Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Sub Corpus</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation#getRaSubCorpus()
	 * @see #getRACorpusRelation()
	 * @generated
	 */
	EReference getRACorpusRelation_RaSubCorpus();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph <em>RA Document Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Document Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph
	 * @generated
	 */
	EClass getRADocumentGraph();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaCorpus <em>Ra Corpus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Corpus</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaCorpus()
	 * @see #getRADocumentGraph()
	 * @generated
	 */
	EReference getRADocumentGraph_RaCorpus();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaNodes <em>Ra Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Nodes</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaNodes()
	 * @see #getRADocumentGraph()
	 * @generated
	 */
	EReference getRADocumentGraph_RaNodes();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaComponents <em>Ra Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Components</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaComponents()
	 * @see #getRADocumentGraph()
	 * @generated
	 */
	EReference getRADocumentGraph_RaComponents();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaTexts <em>Ra Texts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Texts</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaTexts()
	 * @see #getRADocumentGraph()
	 * @generated
	 */
	EReference getRADocumentGraph_RaTexts();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaRanks <em>Ra Ranks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Ranks</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph#getRaRanks()
	 * @see #getRADocumentGraph()
	 * @generated
	 */
	EReference getRADocumentGraph_RaRanks();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode <em>RA Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Node</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode
	 * @generated
	 */
	EClass getRANode();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaId <em>Ra Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaId()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaId();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaText_ref <em>Ra Text ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Text ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaText_ref()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaText_ref();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaCorpus_ref <em>Ra Corpus ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Corpus ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaCorpus_ref()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaCorpus_ref();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaNamespace <em>Ra Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Namespace</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaNamespace()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaNamespace();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaName()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaLeft <em>Ra Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Left</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaLeft()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaLeft();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaRight <em>Ra Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Right</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaRight()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaRight();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaToken_Index <em>Ra Token Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Token Index</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaToken_Index()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaToken_Index();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaContinuous <em>Ra Continuous</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Continuous</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaContinuous()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaContinuous();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaSpan <em>Ra Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Span</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaSpan()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RaSpan();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaAnnotations <em>Ra Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Annotations</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaAnnotations()
	 * @see #getRANode()
	 * @generated
	 */
	EReference getRANode_RaAnnotations();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaDocumentGraph <em>Ra Document Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Document Graph</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaDocumentGraph()
	 * @see #getRANode()
	 * @generated
	 */
	EReference getRANode_RaDocumentGraph();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaText <em>Ra Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Text</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRaText()
	 * @see #getRANode()
	 * @generated
	 */
	EReference getRANode_RaText();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getSegment_name <em>Segment name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Segment name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getSegment_name()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_Segment_name();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getLeftSegment <em>Left Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Left Segment</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getLeftSegment()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_LeftSegment();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRightSegment <em>Right Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Right Segment</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode#getRightSegment()
	 * @see #getRANode()
	 * @generated
	 */
	EAttribute getRANode_RightSegment();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText <em>RA Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Text</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText
	 * @generated
	 */
	EClass getRAText();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaId <em>Ra Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaId()
	 * @see #getRAText()
	 * @generated
	 */
	EAttribute getRAText_RaId();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaName()
	 * @see #getRAText()
	 * @generated
	 */
	EAttribute getRAText_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaText <em>Ra Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Text</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText#getRaText()
	 * @see #getRAText()
	 * @generated
	 */
	EAttribute getRAText_RaText();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation <em>RA Node Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Node Annotation</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation
	 * @generated
	 */
	EClass getRANodeAnnotation();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode <em>Ra Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Node</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode()
	 * @see #getRANodeAnnotation()
	 * @generated
	 */
	EReference getRANodeAnnotation_RaNode();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode_ref <em>Ra Node ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Node ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNode_ref()
	 * @see #getRANodeAnnotation()
	 * @generated
	 */
	EAttribute getRANodeAnnotation_RaNode_ref();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNamespace <em>Ra Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Namespace</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaNamespace()
	 * @see #getRANodeAnnotation()
	 * @generated
	 */
	EAttribute getRANodeAnnotation_RaNamespace();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaName()
	 * @see #getRANodeAnnotation()
	 * @generated
	 */
	EAttribute getRANodeAnnotation_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaValue <em>Ra Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Value</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation#getRaValue()
	 * @see #getRANodeAnnotation()
	 * @generated
	 */
	EAttribute getRANodeAnnotation_RaValue();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank <em>RA Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Rank</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank
	 * @generated
	 */
	EClass getRARank();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentNode <em>Ra Parent Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Parent Node</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentNode()
	 * @see #getRARank()
	 * @generated
	 */
	EReference getRARank_RaParentNode();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode <em>Ra Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Node</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode()
	 * @see #getRARank()
	 * @generated
	 */
	EReference getRARank_RaNode();

	/**
	 * Returns the meta object for the reference list '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaAnnotations <em>Ra Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ra Annotations</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaAnnotations()
	 * @see #getRARank()
	 * @generated
	 */
	EReference getRARank_RaAnnotations();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPre <em>Ra Pre</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Pre</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPre()
	 * @see #getRARank()
	 * @generated
	 */
	EAttribute getRARank_RaPre();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPost <em>Ra Post</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Post</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaPost()
	 * @see #getRARank()
	 * @generated
	 */
	EAttribute getRARank_RaPost();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode_ref <em>Ra Node ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Node ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaNode_ref()
	 * @see #getRARank()
	 * @generated
	 */
	EAttribute getRARank_RaNode_ref();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent_ref <em>Ra Component ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Component ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent_ref()
	 * @see #getRARank()
	 * @generated
	 */
	EAttribute getRARank_RaComponent_ref();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParent_ref <em>Ra Parent ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Parent ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParent_ref()
	 * @see #getRARank()
	 * @generated
	 */
	EAttribute getRARank_RaParent_ref();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentRank <em>Ra Parent Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Parent Rank</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaParentRank()
	 * @see #getRARank()
	 * @generated
	 */
	EReference getRARank_RaParentRank();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent <em>Ra Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Component</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank#getRaComponent()
	 * @see #getRARank()
	 * @generated
	 */
	EReference getRARank_RaComponent();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation <em>RA Edge Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Edge Annotation</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation
	 * @generated
	 */
	EClass getRAEdgeAnnotation();

	/**
	 * Returns the meta object for the reference '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank <em>Ra Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ra Rank</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank()
	 * @see #getRAEdgeAnnotation()
	 * @generated
	 */
	EReference getRAEdgeAnnotation_RaRank();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank_ref <em>Ra Rank ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Rank ref</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaRank_ref()
	 * @see #getRAEdgeAnnotation()
	 * @generated
	 */
	EAttribute getRAEdgeAnnotation_RaRank_ref();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaNamespace <em>Ra Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Namespace</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaNamespace()
	 * @see #getRAEdgeAnnotation()
	 * @generated
	 */
	EAttribute getRAEdgeAnnotation_RaNamespace();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaName()
	 * @see #getRAEdgeAnnotation()
	 * @generated
	 */
	EAttribute getRAEdgeAnnotation_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaValue <em>Ra Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Value</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation#getRaValue()
	 * @see #getRAEdgeAnnotation()
	 * @generated
	 */
	EAttribute getRAEdgeAnnotation_RaValue();

	/**
	 * Returns the meta object for class '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent <em>RA Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RA Component</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent
	 * @generated
	 */
	EClass getRAComponent();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaId <em>Ra Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Id</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaId()
	 * @see #getRAComponent()
	 * @generated
	 */
	EAttribute getRAComponent_RaId();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaType <em>Ra Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Type</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaType()
	 * @see #getRAComponent()
	 * @generated
	 */
	EAttribute getRAComponent_RaType();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaName <em>Ra Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Name</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaName()
	 * @see #getRAComponent()
	 * @generated
	 */
	EAttribute getRAComponent_RaName();

	/**
	 * Returns the meta object for the attribute '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaNamespace <em>Ra Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ra Namespace</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent#getRaNamespace()
	 * @see #getRAComponent()
	 * @generated
	 */
	EAttribute getRAComponent_RaNamespace();

	/**
	 * Returns the meta object for enum '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE <em>RA CORPUS TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>RA CORPUS TYPE</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE
	 * @generated
	 */
	EEnum getRA_CORPUS_TYPE();

	/**
	 * Returns the meta object for enum '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE <em>RA COMPONENT TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>RA COMPONENT TYPE</em>'.
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE
	 * @generated
	 */
	EEnum getRA_COMPONENT_TYPE();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	relANNISFactory getrelANNISFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl <em>RA Corpus</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpus()
		 * @generated
		 */
		EClass RA_CORPUS = eINSTANCE.getRACorpus();

		/**
		 * The meta object literal for the '<em><b>Ra Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS__RA_ID = eINSTANCE.getRACorpus_RaId();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS__RA_NAME = eINSTANCE.getRACorpus_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS__RA_TYPE = eINSTANCE.getRACorpus_RaType();

		/**
		 * The meta object literal for the '<em><b>Ra Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS__RA_VERSION = eINSTANCE.getRACorpus_RaVersion();

		/**
		 * The meta object literal for the '<em><b>Ra Pre</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS__RA_PRE = eINSTANCE.getRACorpus_RaPre();

		/**
		 * The meta object literal for the '<em><b>Ra Post</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS__RA_POST = eINSTANCE.getRACorpus_RaPost();

		/**
		 * The meta object literal for the '<em><b>Ra Corpus Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS__RA_CORPUS_ANNOTATIONS = eINSTANCE.getRACorpus_RaCorpusAnnotations();

		/**
		 * The meta object literal for the '<em><b>Ra Document Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS__RA_DOCUMENT_GRAPH = eINSTANCE.getRACorpus_RaDocumentGraph();

		/**
		 * The meta object literal for the '<em><b>Ra Corpus Graph</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS__RA_CORPUS_GRAPH = eINSTANCE.getRACorpus_RaCorpusGraph();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusAnnotationImpl <em>RA Corpus Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusAnnotationImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpusAnnotation()
		 * @generated
		 */
		EClass RA_CORPUS_ANNOTATION = eINSTANCE.getRACorpusAnnotation();

		/**
		 * The meta object literal for the '<em><b>Ra Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS_ANNOTATION__RA_NAMESPACE = eINSTANCE.getRACorpusAnnotation_RaNamespace();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS_ANNOTATION__RA_NAME = eINSTANCE.getRACorpusAnnotation_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS_ANNOTATION__RA_VALUE = eINSTANCE.getRACorpusAnnotation_RaValue();

		/**
		 * The meta object literal for the '<em><b>Ra Corpus</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS_ANNOTATION__RA_CORPUS = eINSTANCE.getRACorpusAnnotation_RaCorpus();

		/**
		 * The meta object literal for the '<em><b>Ra Corpus ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_CORPUS_ANNOTATION__RA_CORPUS_REF = eINSTANCE.getRACorpusAnnotation_RaCorpus_ref();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusGraphImpl <em>RA Corpus Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusGraphImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpusGraph()
		 * @generated
		 */
		EClass RA_CORPUS_GRAPH = eINSTANCE.getRACorpusGraph();

		/**
		 * The meta object literal for the '<em><b>Ra Corpora</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS_GRAPH__RA_CORPORA = eINSTANCE.getRACorpusGraph_RaCorpora();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusRelationImpl <em>RA Corpus Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusRelationImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRACorpusRelation()
		 * @generated
		 */
		EClass RA_CORPUS_RELATION = eINSTANCE.getRACorpusRelation();

		/**
		 * The meta object literal for the '<em><b>Ra Super Corpus</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS_RELATION__RA_SUPER_CORPUS = eINSTANCE.getRACorpusRelation_RaSuperCorpus();

		/**
		 * The meta object literal for the '<em><b>Ra Sub Corpus</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_CORPUS_RELATION__RA_SUB_CORPUS = eINSTANCE.getRACorpusRelation_RaSubCorpus();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl <em>RA Document Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RADocumentGraphImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRADocumentGraph()
		 * @generated
		 */
		EClass RA_DOCUMENT_GRAPH = eINSTANCE.getRADocumentGraph();

		/**
		 * The meta object literal for the '<em><b>Ra Corpus</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_DOCUMENT_GRAPH__RA_CORPUS = eINSTANCE.getRADocumentGraph_RaCorpus();

		/**
		 * The meta object literal for the '<em><b>Ra Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_DOCUMENT_GRAPH__RA_NODES = eINSTANCE.getRADocumentGraph_RaNodes();

		/**
		 * The meta object literal for the '<em><b>Ra Components</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_DOCUMENT_GRAPH__RA_COMPONENTS = eINSTANCE.getRADocumentGraph_RaComponents();

		/**
		 * The meta object literal for the '<em><b>Ra Texts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_DOCUMENT_GRAPH__RA_TEXTS = eINSTANCE.getRADocumentGraph_RaTexts();

		/**
		 * The meta object literal for the '<em><b>Ra Ranks</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_DOCUMENT_GRAPH__RA_RANKS = eINSTANCE.getRADocumentGraph_RaRanks();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl <em>RA Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRANode()
		 * @generated
		 */
		EClass RA_NODE = eINSTANCE.getRANode();

		/**
		 * The meta object literal for the '<em><b>Ra Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_ID = eINSTANCE.getRANode_RaId();

		/**
		 * The meta object literal for the '<em><b>Ra Text ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_TEXT_REF = eINSTANCE.getRANode_RaText_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Corpus ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_CORPUS_REF = eINSTANCE.getRANode_RaCorpus_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_NAMESPACE = eINSTANCE.getRANode_RaNamespace();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_NAME = eINSTANCE.getRANode_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Left</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_LEFT = eINSTANCE.getRANode_RaLeft();

		/**
		 * The meta object literal for the '<em><b>Ra Right</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_RIGHT = eINSTANCE.getRANode_RaRight();

		/**
		 * The meta object literal for the '<em><b>Ra Token Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_TOKEN_INDEX = eINSTANCE.getRANode_RaToken_Index();

		/**
		 * The meta object literal for the '<em><b>Ra Continuous</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_CONTINUOUS = eINSTANCE.getRANode_RaContinuous();

		/**
		 * The meta object literal for the '<em><b>Ra Span</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RA_SPAN = eINSTANCE.getRANode_RaSpan();

		/**
		 * The meta object literal for the '<em><b>Ra Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_NODE__RA_ANNOTATIONS = eINSTANCE.getRANode_RaAnnotations();

		/**
		 * The meta object literal for the '<em><b>Ra Document Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_NODE__RA_DOCUMENT_GRAPH = eINSTANCE.getRANode_RaDocumentGraph();

		/**
		 * The meta object literal for the '<em><b>Ra Text</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_NODE__RA_TEXT = eINSTANCE.getRANode_RaText();

		/**
		 * The meta object literal for the '<em><b>Segment name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__SEGMENT_NAME = eINSTANCE.getRANode_Segment_name();

		/**
		 * The meta object literal for the '<em><b>Left Segment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__LEFT_SEGMENT = eINSTANCE.getRANode_LeftSegment();

		/**
		 * The meta object literal for the '<em><b>Right Segment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE__RIGHT_SEGMENT = eINSTANCE.getRANode_RightSegment();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl <em>RA Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RATextImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRAText()
		 * @generated
		 */
		EClass RA_TEXT = eINSTANCE.getRAText();

		/**
		 * The meta object literal for the '<em><b>Ra Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_TEXT__RA_ID = eINSTANCE.getRAText_RaId();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_TEXT__RA_NAME = eINSTANCE.getRAText_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_TEXT__RA_TEXT = eINSTANCE.getRAText_RaText();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl <em>RA Node Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeAnnotationImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRANodeAnnotation()
		 * @generated
		 */
		EClass RA_NODE_ANNOTATION = eINSTANCE.getRANodeAnnotation();

		/**
		 * The meta object literal for the '<em><b>Ra Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_NODE_ANNOTATION__RA_NODE = eINSTANCE.getRANodeAnnotation_RaNode();

		/**
		 * The meta object literal for the '<em><b>Ra Node ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE_ANNOTATION__RA_NODE_REF = eINSTANCE.getRANodeAnnotation_RaNode_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE_ANNOTATION__RA_NAMESPACE = eINSTANCE.getRANodeAnnotation_RaNamespace();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE_ANNOTATION__RA_NAME = eINSTANCE.getRANodeAnnotation_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_NODE_ANNOTATION__RA_VALUE = eINSTANCE.getRANodeAnnotation_RaValue();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl <em>RA Rank</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RARankImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRARank()
		 * @generated
		 */
		EClass RA_RANK = eINSTANCE.getRARank();

		/**
		 * The meta object literal for the '<em><b>Ra Parent Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_RANK__RA_PARENT_NODE = eINSTANCE.getRARank_RaParentNode();

		/**
		 * The meta object literal for the '<em><b>Ra Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_RANK__RA_NODE = eINSTANCE.getRARank_RaNode();

		/**
		 * The meta object literal for the '<em><b>Ra Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_RANK__RA_ANNOTATIONS = eINSTANCE.getRARank_RaAnnotations();

		/**
		 * The meta object literal for the '<em><b>Ra Pre</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_RANK__RA_PRE = eINSTANCE.getRARank_RaPre();

		/**
		 * The meta object literal for the '<em><b>Ra Post</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_RANK__RA_POST = eINSTANCE.getRARank_RaPost();

		/**
		 * The meta object literal for the '<em><b>Ra Node ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_RANK__RA_NODE_REF = eINSTANCE.getRARank_RaNode_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Component ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_RANK__RA_COMPONENT_REF = eINSTANCE.getRARank_RaComponent_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Parent ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_RANK__RA_PARENT_REF = eINSTANCE.getRARank_RaParent_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Parent Rank</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_RANK__RA_PARENT_RANK = eINSTANCE.getRARank_RaParentRank();

		/**
		 * The meta object literal for the '<em><b>Ra Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_RANK__RA_COMPONENT = eINSTANCE.getRARank_RaComponent();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl <em>RA Edge Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAEdgeAnnotationImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRAEdgeAnnotation()
		 * @generated
		 */
		EClass RA_EDGE_ANNOTATION = eINSTANCE.getRAEdgeAnnotation();

		/**
		 * The meta object literal for the '<em><b>Ra Rank</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RA_EDGE_ANNOTATION__RA_RANK = eINSTANCE.getRAEdgeAnnotation_RaRank();

		/**
		 * The meta object literal for the '<em><b>Ra Rank ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_EDGE_ANNOTATION__RA_RANK_REF = eINSTANCE.getRAEdgeAnnotation_RaRank_ref();

		/**
		 * The meta object literal for the '<em><b>Ra Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_EDGE_ANNOTATION__RA_NAMESPACE = eINSTANCE.getRAEdgeAnnotation_RaNamespace();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_EDGE_ANNOTATION__RA_NAME = eINSTANCE.getRAEdgeAnnotation_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_EDGE_ANNOTATION__RA_VALUE = eINSTANCE.getRAEdgeAnnotation_RaValue();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl <em>RA Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RAComponentImpl
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRAComponent()
		 * @generated
		 */
		EClass RA_COMPONENT = eINSTANCE.getRAComponent();

		/**
		 * The meta object literal for the '<em><b>Ra Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_COMPONENT__RA_ID = eINSTANCE.getRAComponent_RaId();

		/**
		 * The meta object literal for the '<em><b>Ra Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_COMPONENT__RA_TYPE = eINSTANCE.getRAComponent_RaType();

		/**
		 * The meta object literal for the '<em><b>Ra Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_COMPONENT__RA_NAME = eINSTANCE.getRAComponent_RaName();

		/**
		 * The meta object literal for the '<em><b>Ra Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RA_COMPONENT__RA_NAMESPACE = eINSTANCE.getRAComponent_RaNamespace();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE <em>RA CORPUS TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRA_CORPUS_TYPE()
		 * @generated
		 */
		EEnum RA_CORPUS_TYPE = eINSTANCE.getRA_CORPUS_TYPE();

		/**
		 * The meta object literal for the '{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE <em>RA COMPONENT TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE
		 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.relANNISPackageImpl#getRA_COMPONENT_TYPE()
		 * @generated
		 */
		EEnum RA_COMPONENT_TYPE = eINSTANCE.getRA_COMPONENT_TYPE();

	}

} //relANNISPackage
