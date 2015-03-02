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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.util;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.*;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAComponent;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAEdgeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RARank;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Edge;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Graph;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.IdentifiableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Label;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.LabelableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotatableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SFeaturableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SGraph;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SIdentifiableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SMetaAnnotatableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNamedElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SProcessingAnnotatableElement;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SRelation;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage
 * @generated
 */
public class relANNISSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static relANNISPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public relANNISSwitch() {
		if (modelPackage == null) {
			modelPackage = relANNISPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case relANNISPackage.RA_CORPUS: {
				RACorpus raCorpus = (RACorpus)theEObject;
				T result = caseRACorpus(raCorpus);
				if (result == null) result = caseSNode(raCorpus);
				if (result == null) result = caseNode(raCorpus);
				if (result == null) result = caseSAnnotatableElement(raCorpus);
				if (result == null) result = caseSNamedElement(raCorpus);
				if (result == null) result = caseSIdentifiableElement(raCorpus);
				if (result == null) result = caseSProcessingAnnotatableElement(raCorpus);
				if (result == null) result = caseSMetaAnnotatableElement(raCorpus);
				if (result == null) result = caseIdentifiableElement(raCorpus);
				if (result == null) result = caseSFeaturableElement(raCorpus);
				if (result == null) result = caseLabelableElement(raCorpus);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_CORPUS_ANNOTATION: {
				RACorpusAnnotation raCorpusAnnotation = (RACorpusAnnotation)theEObject;
				T result = caseRACorpusAnnotation(raCorpusAnnotation);
				if (result == null) result = caseSAnnotation(raCorpusAnnotation);
				if (result == null) result = caseSAbstractAnnotation(raCorpusAnnotation);
				if (result == null) result = caseLabel(raCorpusAnnotation);
				if (result == null) result = caseLabelableElement(raCorpusAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_CORPUS_GRAPH: {
				RACorpusGraph raCorpusGraph = (RACorpusGraph)theEObject;
				T result = caseRACorpusGraph(raCorpusGraph);
				if (result == null) result = caseSGraph(raCorpusGraph);
				if (result == null) result = caseGraph(raCorpusGraph);
				if (result == null) result = caseSNamedElement(raCorpusGraph);
				if (result == null) result = caseSAnnotatableElement(raCorpusGraph);
				if (result == null) result = caseSIdentifiableElement(raCorpusGraph);
				if (result == null) result = caseSProcessingAnnotatableElement(raCorpusGraph);
				if (result == null) result = caseSMetaAnnotatableElement(raCorpusGraph);
				if (result == null) result = caseIdentifiableElement(raCorpusGraph);
				if (result == null) result = caseSFeaturableElement(raCorpusGraph);
				if (result == null) result = caseLabelableElement(raCorpusGraph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_CORPUS_RELATION: {
				RACorpusRelation raCorpusRelation = (RACorpusRelation)theEObject;
				T result = caseRACorpusRelation(raCorpusRelation);
				if (result == null) result = caseSRelation(raCorpusRelation);
				if (result == null) result = caseEdge(raCorpusRelation);
				if (result == null) result = caseSAnnotatableElement(raCorpusRelation);
				if (result == null) result = caseSNamedElement(raCorpusRelation);
				if (result == null) result = caseSIdentifiableElement(raCorpusRelation);
				if (result == null) result = caseSProcessingAnnotatableElement(raCorpusRelation);
				if (result == null) result = caseSMetaAnnotatableElement(raCorpusRelation);
				if (result == null) result = caseIdentifiableElement(raCorpusRelation);
				if (result == null) result = caseSFeaturableElement(raCorpusRelation);
				if (result == null) result = caseLabelableElement(raCorpusRelation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_DOCUMENT_GRAPH: {
				RADocumentGraph raDocumentGraph = (RADocumentGraph)theEObject;
				T result = caseRADocumentGraph(raDocumentGraph);
				if (result == null) result = caseSGraph(raDocumentGraph);
				if (result == null) result = caseGraph(raDocumentGraph);
				if (result == null) result = caseSNamedElement(raDocumentGraph);
				if (result == null) result = caseSAnnotatableElement(raDocumentGraph);
				if (result == null) result = caseSIdentifiableElement(raDocumentGraph);
				if (result == null) result = caseSProcessingAnnotatableElement(raDocumentGraph);
				if (result == null) result = caseSMetaAnnotatableElement(raDocumentGraph);
				if (result == null) result = caseIdentifiableElement(raDocumentGraph);
				if (result == null) result = caseSFeaturableElement(raDocumentGraph);
				if (result == null) result = caseLabelableElement(raDocumentGraph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_NODE: {
				RANode raNode = (RANode)theEObject;
				T result = caseRANode(raNode);
				if (result == null) result = caseSNode(raNode);
				if (result == null) result = caseNode(raNode);
				if (result == null) result = caseSAnnotatableElement(raNode);
				if (result == null) result = caseSNamedElement(raNode);
				if (result == null) result = caseSIdentifiableElement(raNode);
				if (result == null) result = caseSProcessingAnnotatableElement(raNode);
				if (result == null) result = caseSMetaAnnotatableElement(raNode);
				if (result == null) result = caseIdentifiableElement(raNode);
				if (result == null) result = caseSFeaturableElement(raNode);
				if (result == null) result = caseLabelableElement(raNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_TEXT: {
				RAText raText = (RAText)theEObject;
				T result = caseRAText(raText);
				if (result == null) result = caseSNode(raText);
				if (result == null) result = caseNode(raText);
				if (result == null) result = caseSAnnotatableElement(raText);
				if (result == null) result = caseSNamedElement(raText);
				if (result == null) result = caseSIdentifiableElement(raText);
				if (result == null) result = caseSProcessingAnnotatableElement(raText);
				if (result == null) result = caseSMetaAnnotatableElement(raText);
				if (result == null) result = caseIdentifiableElement(raText);
				if (result == null) result = caseSFeaturableElement(raText);
				if (result == null) result = caseLabelableElement(raText);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_NODE_ANNOTATION: {
				RANodeAnnotation raNodeAnnotation = (RANodeAnnotation)theEObject;
				T result = caseRANodeAnnotation(raNodeAnnotation);
				if (result == null) result = caseSAnnotation(raNodeAnnotation);
				if (result == null) result = caseSAbstractAnnotation(raNodeAnnotation);
				if (result == null) result = caseLabel(raNodeAnnotation);
				if (result == null) result = caseLabelableElement(raNodeAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_RANK: {
				RARank raRank = (RARank)theEObject;
				T result = caseRARank(raRank);
				if (result == null) result = caseSRelation(raRank);
				if (result == null) result = caseEdge(raRank);
				if (result == null) result = caseSAnnotatableElement(raRank);
				if (result == null) result = caseSNamedElement(raRank);
				if (result == null) result = caseSIdentifiableElement(raRank);
				if (result == null) result = caseSProcessingAnnotatableElement(raRank);
				if (result == null) result = caseSMetaAnnotatableElement(raRank);
				if (result == null) result = caseIdentifiableElement(raRank);
				if (result == null) result = caseSFeaturableElement(raRank);
				if (result == null) result = caseLabelableElement(raRank);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_EDGE_ANNOTATION: {
				RAEdgeAnnotation raEdgeAnnotation = (RAEdgeAnnotation)theEObject;
				T result = caseRAEdgeAnnotation(raEdgeAnnotation);
				if (result == null) result = caseSAnnotation(raEdgeAnnotation);
				if (result == null) result = caseSAbstractAnnotation(raEdgeAnnotation);
				if (result == null) result = caseLabel(raEdgeAnnotation);
				if (result == null) result = caseLabelableElement(raEdgeAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case relANNISPackage.RA_COMPONENT: {
				RAComponent raComponent = (RAComponent)theEObject;
				T result = caseRAComponent(raComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Corpus</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Corpus</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRACorpus(RACorpus object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Corpus Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Corpus Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRACorpusAnnotation(RACorpusAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Corpus Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Corpus Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRACorpusGraph(RACorpusGraph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Corpus Relation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Corpus Relation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRACorpusRelation(RACorpusRelation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Document Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Document Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRADocumentGraph(RADocumentGraph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRANode(RANode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Text</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Text</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRAText(RAText object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Node Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Node Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRANodeAnnotation(RANodeAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Rank</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Rank</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRARank(RARank object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Edge Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Edge Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRAEdgeAnnotation(RAEdgeAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RA Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RA Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRAComponent(RAComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Labelable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Labelable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLabelableElement(LabelableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifiableElement(IdentifiableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SAnnotatable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SAnnotatable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSAnnotatableElement(SAnnotatableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SNamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SNamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSNamedElement(SNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SIdentifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SIdentifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSIdentifiableElement(SIdentifiableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SProcessing Annotatable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SProcessing Annotatable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSProcessingAnnotatableElement(SProcessingAnnotatableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SFeaturable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SFeaturable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSFeaturableElement(SFeaturableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SMeta Annotatable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SMeta Annotatable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSMetaAnnotatableElement(SMetaAnnotatableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SNode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SNode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSNode(SNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Label</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Label</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLabel(Label object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SAbstract Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SAbstract Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSAbstractAnnotation(SAbstractAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SAnnotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SAnnotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSAnnotation(SAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGraph(Graph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SGraph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SGraph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSGraph(SGraph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEdge(Edge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SRelation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SRelation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSRelation(SRelation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //relANNISSwitch
