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
package de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.*;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAbstractAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class relANNISFactoryImpl extends EFactoryImpl implements relANNISFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public static relANNISFactory init() {
		try {
			relANNISFactory therelANNISFactory = (relANNISFactory)EPackage.Registry.INSTANCE.getEFactory("relANNIS"); 
			if (therelANNISFactory != null) {
				return therelANNISFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new relANNISFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public relANNISFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case relANNISPackage.RA_CORPUS: return createRACorpus();
			case relANNISPackage.RA_CORPUS_ANNOTATION: return createRACorpusAnnotation();
			case relANNISPackage.RA_CORPUS_GRAPH: return createRACorpusGraph();
			case relANNISPackage.RA_CORPUS_RELATION: return createRACorpusRelation();
			case relANNISPackage.RA_DOCUMENT_GRAPH: return createRADocumentGraph();
			case relANNISPackage.RA_NODE: return createRANode();
			case relANNISPackage.RA_TEXT: return createRAText();
			case relANNISPackage.RA_NODE_ANNOTATION: return createRANodeAnnotation();
			case relANNISPackage.RA_RANK: return createRARank();
			case relANNISPackage.RA_EDGE_ANNOTATION: return createRAEdgeAnnotation();
			case relANNISPackage.RA_COMPONENT: return createRAComponent();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case relANNISPackage.RA_CORPUS_TYPE:
				return createRA_CORPUS_TYPEFromString(eDataType, initialValue);
			case relANNISPackage.RA_COMPONENT_TYPE:
				return createRA_COMPONENT_TYPEFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case relANNISPackage.RA_CORPUS_TYPE:
				return convertRA_CORPUS_TYPEToString(eDataType, instanceValue);
			case relANNISPackage.RA_COMPONENT_TYPE:
				return convertRA_COMPONENT_TYPEToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RACorpus createRACorpus() {
		RACorpusImpl raCorpus = new RACorpusImpl();
		return raCorpus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpusAnnotation createRACorpusAnnotation() {
		RACorpusAnnotationImpl raCorpusAnnotation = new RACorpusAnnotationImpl();
		return raCorpusAnnotation;
	}

	/**
	 * {@inheritDoc relANNISFactory#createRANodeAnnotation()}
	 */
	public RACorpusAnnotation createRACorpusAnnotation(SAbstractAnnotation sAbstractAnnotation) {
		RACorpusAnnotationImpl raCorpusAnnotation = new RACorpusAnnotationImpl(sAbstractAnnotation);
		return raCorpusAnnotation;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpusGraph createRACorpusGraph() {
		RACorpusGraphImpl raCorpusGraph = new RACorpusGraphImpl();
		return raCorpusGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpusRelation createRACorpusRelation() {
		RACorpusRelationImpl raCorpusRelation = new RACorpusRelationImpl();
		return raCorpusRelation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RADocumentGraph createRADocumentGraph() {
		RADocumentGraphImpl raDocumentGraph = new RADocumentGraphImpl();
		return raDocumentGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RANode createRANode() {
		RANodeImpl raNode = new RANodeImpl();
		return raNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	
	 * @generated
	 */
	public RAText createRAText() {
		RATextImpl raText = new RATextImpl();
		return raText;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RANodeAnnotation createRANodeAnnotation() {
		RANodeAnnotationImpl raNodeAnnotation = new RANodeAnnotationImpl();
		return raNodeAnnotation;
	}

	/**
	 * {@inheritDoc relANNISFactory#createRANodeAnnotation()}
	 */
	public RANodeAnnotation createRANodeAnnotation(SAbstractAnnotation sAbstractAnnotation) {
		RANodeAnnotationImpl raNodeAnnotation = new RANodeAnnotationImpl(sAbstractAnnotation);
		return raNodeAnnotation;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RARank createRARank() {
		RARankImpl raRank = new RARankImpl();
		return raRank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAEdgeAnnotation createRAEdgeAnnotation() {
		RAEdgeAnnotationImpl raEdgeAnnotation = new RAEdgeAnnotationImpl();
		return raEdgeAnnotation;
	}
	
	@Override
	public RAEdgeAnnotation createRAEdgeAnnotation(
			SAbstractAnnotation sAbstractAnnotation) 
	{
		RAEdgeAnnotationImpl raEdgeAnnotation= new RAEdgeAnnotationImpl(sAbstractAnnotation);
		return(raEdgeAnnotation);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAComponent createRAComponent() {
		RAComponentImpl raComponent = new RAComponentImpl();
		return raComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RA_CORPUS_TYPE createRA_CORPUS_TYPEFromString(EDataType eDataType, String initialValue) {
		RA_CORPUS_TYPE result = RA_CORPUS_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRA_CORPUS_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RA_COMPONENT_TYPE createRA_COMPONENT_TYPEFromString(EDataType eDataType, String initialValue) {
		RA_COMPONENT_TYPE result = RA_COMPONENT_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRA_COMPONENT_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public relANNISPackage getrelANNISPackage() {
		return (relANNISPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static relANNISPackage getPackage() {
		return relANNISPackage.eINSTANCE;
	}
} //relANNISFactoryImpl
