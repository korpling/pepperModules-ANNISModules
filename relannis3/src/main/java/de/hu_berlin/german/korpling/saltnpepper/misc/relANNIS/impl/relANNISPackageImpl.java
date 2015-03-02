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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISFactory;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCorePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class relANNISPackageImpl extends EPackageImpl implements relANNISPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raCorpusEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raCorpusAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raCorpusGraphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raCorpusRelationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raDocumentGraphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raTextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raNodeAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raRankEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raEdgeAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum rA_CORPUS_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum rA_COMPONENT_TYPEEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private relANNISPackageImpl() {
		super(eNS_URI, relANNISFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link relANNISPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static relANNISPackage init() {
		if (isInited) return (relANNISPackage)EPackage.Registry.INSTANCE.getEPackage(relANNISPackage.eNS_URI);

		// Obtain or create and register package
		relANNISPackageImpl therelANNISPackage = (relANNISPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof relANNISPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new relANNISPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SaltCorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		therelANNISPackage.createPackageContents();

		// Initialize created meta-data
		therelANNISPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		therelANNISPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(relANNISPackage.eNS_URI, therelANNISPackage);
		return therelANNISPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRACorpus() {
		return raCorpusEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpus_RaId() {
		return (EAttribute)raCorpusEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpus_RaName() {
		return (EAttribute)raCorpusEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpus_RaType() {
		return (EAttribute)raCorpusEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpus_RaVersion() {
		return (EAttribute)raCorpusEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpus_RaPre() {
		return (EAttribute)raCorpusEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpus_RaPost() {
		return (EAttribute)raCorpusEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpus_RaCorpusAnnotations() {
		return (EReference)raCorpusEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpus_RaDocumentGraph() {
		return (EReference)raCorpusEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpus_RaCorpusGraph() {
		return (EReference)raCorpusEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRACorpusAnnotation() {
		return raCorpusAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpusAnnotation_RaNamespace() {
		return (EAttribute)raCorpusAnnotationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpusAnnotation_RaName() {
		return (EAttribute)raCorpusAnnotationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpusAnnotation_RaValue() {
		return (EAttribute)raCorpusAnnotationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpusAnnotation_RaCorpus() {
		return (EReference)raCorpusAnnotationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRACorpusAnnotation_RaCorpus_ref() {
		return (EAttribute)raCorpusAnnotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRACorpusGraph() {
		return raCorpusGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpusGraph_RaCorpora() {
		return (EReference)raCorpusGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRACorpusRelation() {
		return raCorpusRelationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpusRelation_RaSuperCorpus() {
		return (EReference)raCorpusRelationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRACorpusRelation_RaSubCorpus() {
		return (EReference)raCorpusRelationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRADocumentGraph() {
		return raDocumentGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRADocumentGraph_RaCorpus() {
		return (EReference)raDocumentGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRADocumentGraph_RaNodes() {
		return (EReference)raDocumentGraphEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRADocumentGraph_RaComponents() {
		return (EReference)raDocumentGraphEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRADocumentGraph_RaTexts() {
		return (EReference)raDocumentGraphEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRADocumentGraph_RaRanks() {
		return (EReference)raDocumentGraphEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRANode() {
		return raNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaId() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaText_ref() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaCorpus_ref() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaNamespace() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaName() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaLeft() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaRight() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaToken_Index() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaContinuous() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RaSpan() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRANode_RaAnnotations() {
		return (EReference)raNodeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRANode_RaDocumentGraph() {
		return (EReference)raNodeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRANode_RaText() {
		return (EReference)raNodeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_Segment_name() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_LeftSegment() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANode_RightSegment() {
		return (EAttribute)raNodeEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRAText() {
		return raTextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAText_RaId() {
		return (EAttribute)raTextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAText_RaName() {
		return (EAttribute)raTextEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAText_RaText() {
		return (EAttribute)raTextEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRANodeAnnotation() {
		return raNodeAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRANodeAnnotation_RaNode() {
		return (EReference)raNodeAnnotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANodeAnnotation_RaNode_ref() {
		return (EAttribute)raNodeAnnotationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANodeAnnotation_RaNamespace() {
		return (EAttribute)raNodeAnnotationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANodeAnnotation_RaName() {
		return (EAttribute)raNodeAnnotationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRANodeAnnotation_RaValue() {
		return (EAttribute)raNodeAnnotationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRARank() {
		return raRankEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRARank_RaParentNode() {
		return (EReference)raRankEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRARank_RaNode() {
		return (EReference)raRankEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRARank_RaAnnotations() {
		return (EReference)raRankEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRARank_RaPre() {
		return (EAttribute)raRankEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRARank_RaPost() {
		return (EAttribute)raRankEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRARank_RaNode_ref() {
		return (EAttribute)raRankEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRARank_RaComponent_ref() {
		return (EAttribute)raRankEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRARank_RaParent_ref() {
		return (EAttribute)raRankEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRARank_RaParentRank() {
		return (EReference)raRankEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRARank_RaComponent() {
		return (EReference)raRankEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRAEdgeAnnotation() {
		return raEdgeAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRAEdgeAnnotation_RaRank() {
		return (EReference)raEdgeAnnotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAEdgeAnnotation_RaRank_ref() {
		return (EAttribute)raEdgeAnnotationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAEdgeAnnotation_RaNamespace() {
		return (EAttribute)raEdgeAnnotationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAEdgeAnnotation_RaName() {
		return (EAttribute)raEdgeAnnotationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAEdgeAnnotation_RaValue() {
		return (EAttribute)raEdgeAnnotationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRAComponent() {
		return raComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAComponent_RaId() {
		return (EAttribute)raComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAComponent_RaType() {
		return (EAttribute)raComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAComponent_RaName() {
		return (EAttribute)raComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRAComponent_RaNamespace() {
		return (EAttribute)raComponentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRA_CORPUS_TYPE() {
		return rA_CORPUS_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRA_COMPONENT_TYPE() {
		return rA_COMPONENT_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public relANNISFactory getrelANNISFactory() {
		return (relANNISFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		raCorpusEClass = createEClass(RA_CORPUS);
		createEAttribute(raCorpusEClass, RA_CORPUS__RA_ID);
		createEAttribute(raCorpusEClass, RA_CORPUS__RA_NAME);
		createEAttribute(raCorpusEClass, RA_CORPUS__RA_TYPE);
		createEAttribute(raCorpusEClass, RA_CORPUS__RA_VERSION);
		createEAttribute(raCorpusEClass, RA_CORPUS__RA_PRE);
		createEAttribute(raCorpusEClass, RA_CORPUS__RA_POST);
		createEReference(raCorpusEClass, RA_CORPUS__RA_CORPUS_ANNOTATIONS);
		createEReference(raCorpusEClass, RA_CORPUS__RA_DOCUMENT_GRAPH);
		createEReference(raCorpusEClass, RA_CORPUS__RA_CORPUS_GRAPH);

		raCorpusAnnotationEClass = createEClass(RA_CORPUS_ANNOTATION);
		createEAttribute(raCorpusAnnotationEClass, RA_CORPUS_ANNOTATION__RA_CORPUS_REF);
		createEAttribute(raCorpusAnnotationEClass, RA_CORPUS_ANNOTATION__RA_NAMESPACE);
		createEAttribute(raCorpusAnnotationEClass, RA_CORPUS_ANNOTATION__RA_NAME);
		createEAttribute(raCorpusAnnotationEClass, RA_CORPUS_ANNOTATION__RA_VALUE);
		createEReference(raCorpusAnnotationEClass, RA_CORPUS_ANNOTATION__RA_CORPUS);

		raCorpusGraphEClass = createEClass(RA_CORPUS_GRAPH);
		createEReference(raCorpusGraphEClass, RA_CORPUS_GRAPH__RA_CORPORA);

		raCorpusRelationEClass = createEClass(RA_CORPUS_RELATION);
		createEReference(raCorpusRelationEClass, RA_CORPUS_RELATION__RA_SUPER_CORPUS);
		createEReference(raCorpusRelationEClass, RA_CORPUS_RELATION__RA_SUB_CORPUS);

		raDocumentGraphEClass = createEClass(RA_DOCUMENT_GRAPH);
		createEReference(raDocumentGraphEClass, RA_DOCUMENT_GRAPH__RA_CORPUS);
		createEReference(raDocumentGraphEClass, RA_DOCUMENT_GRAPH__RA_NODES);
		createEReference(raDocumentGraphEClass, RA_DOCUMENT_GRAPH__RA_COMPONENTS);
		createEReference(raDocumentGraphEClass, RA_DOCUMENT_GRAPH__RA_TEXTS);
		createEReference(raDocumentGraphEClass, RA_DOCUMENT_GRAPH__RA_RANKS);

		raNodeEClass = createEClass(RA_NODE);
		createEAttribute(raNodeEClass, RA_NODE__RA_ID);
		createEAttribute(raNodeEClass, RA_NODE__RA_TEXT_REF);
		createEAttribute(raNodeEClass, RA_NODE__RA_CORPUS_REF);
		createEAttribute(raNodeEClass, RA_NODE__RA_NAMESPACE);
		createEAttribute(raNodeEClass, RA_NODE__RA_NAME);
		createEAttribute(raNodeEClass, RA_NODE__RA_LEFT);
		createEAttribute(raNodeEClass, RA_NODE__RA_RIGHT);
		createEAttribute(raNodeEClass, RA_NODE__RA_TOKEN_INDEX);
		createEAttribute(raNodeEClass, RA_NODE__RA_CONTINUOUS);
		createEAttribute(raNodeEClass, RA_NODE__RA_SPAN);
		createEReference(raNodeEClass, RA_NODE__RA_ANNOTATIONS);
		createEReference(raNodeEClass, RA_NODE__RA_DOCUMENT_GRAPH);
		createEReference(raNodeEClass, RA_NODE__RA_TEXT);
		createEAttribute(raNodeEClass, RA_NODE__SEGMENT_NAME);
		createEAttribute(raNodeEClass, RA_NODE__LEFT_SEGMENT);
		createEAttribute(raNodeEClass, RA_NODE__RIGHT_SEGMENT);

		raTextEClass = createEClass(RA_TEXT);
		createEAttribute(raTextEClass, RA_TEXT__RA_ID);
		createEAttribute(raTextEClass, RA_TEXT__RA_NAME);
		createEAttribute(raTextEClass, RA_TEXT__RA_TEXT);

		raNodeAnnotationEClass = createEClass(RA_NODE_ANNOTATION);
		createEReference(raNodeAnnotationEClass, RA_NODE_ANNOTATION__RA_NODE);
		createEAttribute(raNodeAnnotationEClass, RA_NODE_ANNOTATION__RA_NODE_REF);
		createEAttribute(raNodeAnnotationEClass, RA_NODE_ANNOTATION__RA_NAMESPACE);
		createEAttribute(raNodeAnnotationEClass, RA_NODE_ANNOTATION__RA_NAME);
		createEAttribute(raNodeAnnotationEClass, RA_NODE_ANNOTATION__RA_VALUE);

		raRankEClass = createEClass(RA_RANK);
		createEReference(raRankEClass, RA_RANK__RA_PARENT_NODE);
		createEReference(raRankEClass, RA_RANK__RA_NODE);
		createEReference(raRankEClass, RA_RANK__RA_ANNOTATIONS);
		createEAttribute(raRankEClass, RA_RANK__RA_PRE);
		createEAttribute(raRankEClass, RA_RANK__RA_POST);
		createEAttribute(raRankEClass, RA_RANK__RA_NODE_REF);
		createEAttribute(raRankEClass, RA_RANK__RA_COMPONENT_REF);
		createEAttribute(raRankEClass, RA_RANK__RA_PARENT_REF);
		createEReference(raRankEClass, RA_RANK__RA_PARENT_RANK);
		createEReference(raRankEClass, RA_RANK__RA_COMPONENT);

		raEdgeAnnotationEClass = createEClass(RA_EDGE_ANNOTATION);
		createEReference(raEdgeAnnotationEClass, RA_EDGE_ANNOTATION__RA_RANK);
		createEAttribute(raEdgeAnnotationEClass, RA_EDGE_ANNOTATION__RA_RANK_REF);
		createEAttribute(raEdgeAnnotationEClass, RA_EDGE_ANNOTATION__RA_NAMESPACE);
		createEAttribute(raEdgeAnnotationEClass, RA_EDGE_ANNOTATION__RA_NAME);
		createEAttribute(raEdgeAnnotationEClass, RA_EDGE_ANNOTATION__RA_VALUE);

		raComponentEClass = createEClass(RA_COMPONENT);
		createEAttribute(raComponentEClass, RA_COMPONENT__RA_ID);
		createEAttribute(raComponentEClass, RA_COMPONENT__RA_TYPE);
		createEAttribute(raComponentEClass, RA_COMPONENT__RA_NAME);
		createEAttribute(raComponentEClass, RA_COMPONENT__RA_NAMESPACE);

		// Create enums
		rA_CORPUS_TYPEEEnum = createEEnum(RA_CORPUS_TYPE);
		rA_COMPONENT_TYPEEEnum = createEEnum(RA_COMPONENT_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		SaltCorePackage theSaltCorePackage = (SaltCorePackage)EPackage.Registry.INSTANCE.getEPackage(SaltCorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		raCorpusEClass.getESuperTypes().add(theSaltCorePackage.getSNode());
		raCorpusAnnotationEClass.getESuperTypes().add(theSaltCorePackage.getSAnnotation());
		raCorpusGraphEClass.getESuperTypes().add(theSaltCorePackage.getSGraph());
		raCorpusRelationEClass.getESuperTypes().add(theSaltCorePackage.getSRelation());
		raDocumentGraphEClass.getESuperTypes().add(theSaltCorePackage.getSGraph());
		raNodeEClass.getESuperTypes().add(theSaltCorePackage.getSNode());
		raTextEClass.getESuperTypes().add(theSaltCorePackage.getSNode());
		raNodeAnnotationEClass.getESuperTypes().add(theSaltCorePackage.getSAnnotation());
		raRankEClass.getESuperTypes().add(theSaltCorePackage.getSRelation());
		raEdgeAnnotationEClass.getESuperTypes().add(theSaltCorePackage.getSAnnotation());

		// Initialize classes and features; add operations and parameters
		initEClass(raCorpusEClass, RACorpus.class, "RACorpus", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRACorpus_RaId(), ecorePackage.getELongObject(), "raId", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpus_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpus_RaType(), this.getRA_CORPUS_TYPE(), "raType", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpus_RaVersion(), ecorePackage.getEString(), "raVersion", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpus_RaPre(), ecorePackage.getELongObject(), "raPre", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpus_RaPost(), ecorePackage.getELongObject(), "raPost", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRACorpus_RaCorpusAnnotations(), this.getRACorpusAnnotation(), this.getRACorpusAnnotation_RaCorpus(), "raCorpusAnnotations", null, 0, -1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRACorpus_RaDocumentGraph(), this.getRADocumentGraph(), this.getRADocumentGraph_RaCorpus(), "raDocumentGraph", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRACorpus_RaCorpusGraph(), this.getRACorpusGraph(), this.getRACorpusGraph_RaCorpora(), "raCorpusGraph", null, 0, 1, RACorpus.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(raCorpusEClass, null, "addRACorpusAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRACorpusAnnotation(), "raCorpusAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(raCorpusAnnotationEClass, RACorpusAnnotation.class, "RACorpusAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRACorpusAnnotation_RaCorpus_ref(), ecorePackage.getELongObject(), "raCorpus_ref", null, 0, 1, RACorpusAnnotation.class, !IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpusAnnotation_RaNamespace(), ecorePackage.getEString(), "raNamespace", null, 0, 1, RACorpusAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpusAnnotation_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RACorpusAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRACorpusAnnotation_RaValue(), ecorePackage.getEString(), "raValue", null, 0, 1, RACorpusAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRACorpusAnnotation_RaCorpus(), this.getRACorpus(), this.getRACorpus_RaCorpusAnnotations(), "raCorpus", null, 0, 1, RACorpusAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = addEOperation(raCorpusAnnotationEClass, null, "useSAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSaltCorePackage.getSAbstractAnnotation(), "sAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(raCorpusGraphEClass, RACorpusGraph.class, "RACorpusGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRACorpusGraph_RaCorpora(), this.getRACorpus(), this.getRACorpus_RaCorpusGraph(), "raCorpora", null, 0, -1, RACorpusGraph.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		addEOperation(raCorpusGraphEClass, this.getRACorpus(), "getRARoots", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(raCorpusGraphEClass, this.getRACorpus(), "getRARoots", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRACorpus(), "raCorpus", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(raCorpusGraphEClass, null, "generateRAPPOrder", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(raCorpusRelationEClass, RACorpusRelation.class, "RACorpusRelation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRACorpusRelation_RaSuperCorpus(), this.getRACorpus(), null, "raSuperCorpus", null, 0, 1, RACorpusRelation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRACorpusRelation_RaSubCorpus(), this.getRACorpus(), null, "raSubCorpus", null, 0, 1, RACorpusRelation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(raDocumentGraphEClass, RADocumentGraph.class, "RADocumentGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRADocumentGraph_RaCorpus(), this.getRACorpus(), this.getRACorpus_RaDocumentGraph(), "raCorpus", null, 0, 1, RADocumentGraph.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRADocumentGraph_RaNodes(), this.getRANode(), this.getRANode_RaDocumentGraph(), "raNodes", null, 0, -1, RADocumentGraph.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRADocumentGraph_RaComponents(), this.getRAComponent(), null, "raComponents", null, 0, -1, RADocumentGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRADocumentGraph_RaTexts(), this.getRAText(), null, "raTexts", null, 0, -1, RADocumentGraph.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRADocumentGraph_RaRanks(), this.getRARank(), null, "raRanks", null, 0, -1, RADocumentGraph.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = addEOperation(raDocumentGraphEClass, ecorePackage.getEBooleanObject(), "isRaContinuous", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRANode(), "raNode", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(raDocumentGraphEClass, ecorePackage.getEString(), "getRaSpan", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRANode(), "raNode", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(raDocumentGraphEClass, ecorePackage.getEBooleanObject(), "isTerminalRaNode", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRANode(), "raNode", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(raDocumentGraphEClass, this.getRAText(), "getRaText", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getELongObject(), "raId", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(raDocumentGraphEClass, this.getRANode(), "getRaNode", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getELongObject(), "raId", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(raDocumentGraphEClass, this.getRARank(), "getRaRank", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getELongObject(), "raPre", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(raDocumentGraphEClass, this.getRANode(), "getRARoots", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(raDocumentGraphEClass, this.getRANode(), "getRATerminals", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(raNodeEClass, RANode.class, "RANode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRANode_RaId(), ecorePackage.getELongObject(), "raId", null, 0, 1, RANode.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaText_ref(), ecorePackage.getELongObject(), "raText_ref", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaCorpus_ref(), ecorePackage.getELongObject(), "raCorpus_ref", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaNamespace(), ecorePackage.getEString(), "raNamespace", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaLeft(), ecorePackage.getELongObject(), "raLeft", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaRight(), ecorePackage.getELongObject(), "raRight", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaToken_Index(), ecorePackage.getELongObject(), "raToken_Index", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaContinuous(), ecorePackage.getEBooleanObject(), "raContinuous", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RaSpan(), ecorePackage.getEString(), "raSpan", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRANode_RaAnnotations(), this.getRANodeAnnotation(), this.getRANodeAnnotation_RaNode(), "raAnnotations", null, 0, -1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRANode_RaDocumentGraph(), this.getRADocumentGraph(), this.getRADocumentGraph_RaNodes(), "raDocumentGraph", null, 0, 1, RANode.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRANode_RaText(), this.getRAText(), null, "raText", null, 0, 1, RANode.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_Segment_name(), ecorePackage.getEString(), "segment_name", null, 0, 1, RANode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_LeftSegment(), ecorePackage.getELongObject(), "leftSegment", null, 0, 1, RANode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANode_RightSegment(), ecorePackage.getELongObject(), "rightSegment", null, 0, 1, RANode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(raTextEClass, RAText.class, "RAText", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRAText_RaId(), ecorePackage.getELongObject(), "raId", null, 0, 1, RAText.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAText_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RAText.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAText_RaText(), ecorePackage.getEString(), "raText", null, 0, 1, RAText.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(raNodeAnnotationEClass, RANodeAnnotation.class, "RANodeAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRANodeAnnotation_RaNode(), this.getRANode(), this.getRANode_RaAnnotations(), "raNode", null, 0, 1, RANodeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANodeAnnotation_RaNode_ref(), ecorePackage.getELongObject(), "raNode_ref", null, 0, 1, RANodeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANodeAnnotation_RaNamespace(), ecorePackage.getEString(), "raNamespace", null, 0, 1, RANodeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANodeAnnotation_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RANodeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRANodeAnnotation_RaValue(), ecorePackage.getEString(), "raValue", null, 0, 1, RANodeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = addEOperation(raNodeAnnotationEClass, null, "useSAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSaltCorePackage.getSAbstractAnnotation(), "sAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(raRankEClass, RARank.class, "RARank", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRARank_RaParentNode(), this.getRANode(), null, "raParentNode", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRARank_RaNode(), this.getRANode(), null, "raNode", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRARank_RaAnnotations(), this.getRAEdgeAnnotation(), this.getRAEdgeAnnotation_RaRank(), "raAnnotations", null, 0, -1, RARank.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRARank_RaPre(), ecorePackage.getELongObject(), "raPre", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRARank_RaPost(), ecorePackage.getELongObject(), "raPost", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRARank_RaNode_ref(), ecorePackage.getELongObject(), "raNode_ref", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRARank_RaComponent_ref(), ecorePackage.getELongObject(), "raComponent_ref", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRARank_RaParent_ref(), ecorePackage.getELongObject(), "raParent_ref", null, 0, 1, RARank.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRARank_RaParentRank(), this.getRARank(), null, "raParentRank", null, 0, 1, RARank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRARank_RaComponent(), this.getRAComponent(), null, "raComponent", null, 0, 1, RARank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(raEdgeAnnotationEClass, RAEdgeAnnotation.class, "RAEdgeAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRAEdgeAnnotation_RaRank(), this.getRARank(), this.getRARank_RaAnnotations(), "raRank", null, 0, 1, RAEdgeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAEdgeAnnotation_RaRank_ref(), ecorePackage.getELongObject(), "raRank_ref", null, 0, 1, RAEdgeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAEdgeAnnotation_RaNamespace(), ecorePackage.getEString(), "raNamespace", null, 0, 1, RAEdgeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAEdgeAnnotation_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RAEdgeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAEdgeAnnotation_RaValue(), ecorePackage.getEString(), "raValue", null, 0, 1, RAEdgeAnnotation.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = addEOperation(raEdgeAnnotationEClass, null, "useSAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSaltCorePackage.getSAbstractAnnotation(), "sAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(raComponentEClass, RAComponent.class, "RAComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRAComponent_RaId(), ecorePackage.getELongObject(), "raId", null, 0, 1, RAComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAComponent_RaType(), this.getRA_COMPONENT_TYPE(), "raType", "", 0, 1, RAComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAComponent_RaName(), ecorePackage.getEString(), "raName", null, 0, 1, RAComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRAComponent_RaNamespace(), ecorePackage.getEString(), "raNamespace", null, 0, 1, RAComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(rA_CORPUS_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE.class, "RA_CORPUS_TYPE");
		addEEnumLiteral(rA_CORPUS_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE.CORPUS);
		addEEnumLiteral(rA_CORPUS_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE.DOCUMENT);

		initEEnum(rA_COMPONENT_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE.class, "RA_COMPONENT_TYPE");
		addEEnumLiteral(rA_COMPONENT_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE.D);
		addEEnumLiteral(rA_COMPONENT_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE.P);
		addEEnumLiteral(rA_COMPONENT_TYPEEEnum, de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_COMPONENT_TYPE.C);

		// Create resource
		createResource(eNS_URI);
	}

} //relANNISPackageImpl
