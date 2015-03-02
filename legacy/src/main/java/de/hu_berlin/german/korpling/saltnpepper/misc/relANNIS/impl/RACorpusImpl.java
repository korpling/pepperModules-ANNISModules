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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RA_CORPUS_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SFeature;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SNodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Corpus</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaType <em>Ra Type</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaVersion <em>Ra Version</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaPre <em>Ra Pre</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaPost <em>Ra Post</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaCorpusAnnotations <em>Ra Corpus Annotations</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaDocumentGraph <em>Ra Document Graph</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusImpl#getRaCorpusGraph <em>Ra Corpus Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RACorpusImpl extends SNodeImpl implements RACorpus {
	/**
	 * The default value of the '{@link #getRaId() <em>Ra Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaId()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaName() <em>Ra Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaName()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaType() <em>Ra Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaType()
	 * @generated
	 * @ordered
	 */
	protected static final RA_CORPUS_TYPE RA_TYPE_EDEFAULT = RA_CORPUS_TYPE.CORPUS;

	/**
	 * The default value of the '{@link #getRaVersion() <em>Ra Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_VERSION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaPre() <em>Ra Pre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaPre()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_PRE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaPost() <em>Ra Post</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaPost()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_POST_EDEFAULT = null;

	/**
	 * Unique identifier for RACorpus. This will make sure, that an id value is used only one time
	 */
	private static Long corpusRaId= 0l;
	
	/**
	 * Returns  aunique identifier for RACorpus. This will make sure, that an 
	 * raId value is used only one time
	 * @return new unused raId value
	 */
	private static synchronized Long getNewRaId()
	{
		Long retVal= corpusRaId;
		corpusRaId++;
		return(retVal);
	}
	
	/**
	 * Sets raIds to 0.
	 */
	public static synchronized void resetRaId()
	{
		corpusRaId= 0l;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RACorpusImpl() 
	{
		super();
		this.setRaId(getNewRaId());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_CORPUS;
	}

	public static String KW_NS= "relANNIS";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaId() 
	{
		return(new Long(super.getSId()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaId(Long newRaId) 
	{
		super.setSId(newRaId.toString());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String getRaName() 
	{
		return(super.getSName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaName(String newRaName) 
	{
		super.setSName(newRaName);
	}

// ----------------------- start type
	public static String KW_RATYPE= "type";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RA_CORPUS_TYPE getRaType() 
	{
		RA_CORPUS_TYPE retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RATYPE);
		if (sFeature!= null)
		{
			if (RA_CORPUS_TYPE.CORPUS.toString().equalsIgnoreCase(sFeature.getSValue().toString()))
				retVal= RA_CORPUS_TYPE.CORPUS;
			else if (RA_CORPUS_TYPE.DOCUMENT.toString().equalsIgnoreCase(sFeature.getSValue().toString()))
				retVal= RA_CORPUS_TYPE.DOCUMENT;
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaType(RA_CORPUS_TYPE newRaType) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RATYPE);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RATYPE);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaType);
	}
// ----------------------- end type
// ----------------------- start version
	public static String KW_RAVERSION= "version";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String getRaVersion() 
	{
		String retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAVERSION);
		if (sFeature!= null)
		{
			if (sFeature.getSValue()!= null)
				retVal= sFeature.getSValue().toString();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaVersion(String newRaVersion) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAVERSION);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RAVERSION);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaVersion);
	}
// ----------------------- end version

// ----------------------- start pre	
	
	public static String KW_RAPRE= "pre";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaPre() 
	{
		Long retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAPRE);
		if (sFeature!= null)
		{
			retVal= new Long(sFeature.getSValue().toString());
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaPre(Long newRaPre) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAPRE);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RAPRE);
			this.addSFeature(sFeature);
		}
		sFeature.setSValue(newRaPre);
	}
// ----------------------- end pre
// ----------------------- start post
	public static String KW_RAPOST= "post";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaPost() 
	{
		Long retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAPOST);
		if (sFeature!= null)
		{
			retVal= new Long(sFeature.getSValue().toString());
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaPost(Long newRaPost) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RAPOST);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RAPOST);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaPost);
	}
// ----------------------- end post
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RACorpusAnnotation> getRaCorpusAnnotations() 
	{
		EList<RACorpusAnnotation> retVal= null;
		retVal= (EList<RACorpusAnnotation>) (EList<? extends SAnnotation>)super.getSAnnotations();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RADocumentGraph getRaDocumentGraph() {
		RADocumentGraph raDocumentGraph = basicGetRaDocumentGraph();
		return raDocumentGraph != null && raDocumentGraph.eIsProxy() ? (RADocumentGraph)eResolveProxy((InternalEObject)raDocumentGraph) : raDocumentGraph;
	}

	public static String KW_RADOCUMENT_GRAPH= "RADOCUMENT_GRAPH";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RADocumentGraph basicGetRaDocumentGraph() 
	{
		RADocumentGraph retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RADOCUMENT_GRAPH);
		if (sFeature!= null)
		{
			retVal= (RADocumentGraph)sFeature.getSValue();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaDocumentGraph(RADocumentGraph newRaDocumentGraph) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RADOCUMENT_GRAPH);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RADOCUMENT_GRAPH);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaDocumentGraph);
		if (	(newRaDocumentGraph!= null) && (
				newRaDocumentGraph.getRaCorpus()!= this))
			newRaDocumentGraph.setRaCorpus(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RACorpusGraph getRaCorpusGraph() 
	{
		RACorpusGraph retVal= null;
		if (super.getSGraph()!= null)
			retVal= (RACorpusGraph) super.getSGraph();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaCorpusGraph(RACorpusGraph newRaCorpusGraph) 
	{
		super.setSGraph(newRaCorpusGraph);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void addRACorpusAnnotation(RACorpusAnnotation raCorpusAnnotation) 
	{
		super.addSAnnotation(raCorpusAnnotation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case relANNISPackage.RA_CORPUS__RA_CORPUS_GRAPH:
				return eInternalContainer().eInverseRemove(this, relANNISPackage.RA_CORPUS_GRAPH__RA_CORPORA, RACorpusGraph.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS__RA_ID:
				return getRaId();
			case relANNISPackage.RA_CORPUS__RA_NAME:
				return getRaName();
			case relANNISPackage.RA_CORPUS__RA_TYPE:
				return getRaType();
			case relANNISPackage.RA_CORPUS__RA_VERSION:
				return getRaVersion();
			case relANNISPackage.RA_CORPUS__RA_PRE:
				return getRaPre();
			case relANNISPackage.RA_CORPUS__RA_POST:
				return getRaPost();
			case relANNISPackage.RA_CORPUS__RA_CORPUS_ANNOTATIONS:
				return getRaCorpusAnnotations();
			case relANNISPackage.RA_CORPUS__RA_DOCUMENT_GRAPH:
				if (resolve) return getRaDocumentGraph();
				return basicGetRaDocumentGraph();
			case relANNISPackage.RA_CORPUS__RA_CORPUS_GRAPH:
				return getRaCorpusGraph();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS__RA_ID:
				setRaId((Long)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_NAME:
				setRaName((String)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_TYPE:
				setRaType((RA_CORPUS_TYPE)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_VERSION:
				setRaVersion((String)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_PRE:
				setRaPre((Long)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_POST:
				setRaPost((Long)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_CORPUS_ANNOTATIONS:
				getRaCorpusAnnotations().clear();
				getRaCorpusAnnotations().addAll((Collection<? extends RACorpusAnnotation>)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_DOCUMENT_GRAPH:
				setRaDocumentGraph((RADocumentGraph)newValue);
				return;
			case relANNISPackage.RA_CORPUS__RA_CORPUS_GRAPH:
				setRaCorpusGraph((RACorpusGraph)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS__RA_ID:
				setRaId(RA_ID_EDEFAULT);
				return;
			case relANNISPackage.RA_CORPUS__RA_NAME:
				setRaName(RA_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_CORPUS__RA_TYPE:
				setRaType(RA_TYPE_EDEFAULT);
				return;
			case relANNISPackage.RA_CORPUS__RA_VERSION:
				setRaVersion(RA_VERSION_EDEFAULT);
				return;
			case relANNISPackage.RA_CORPUS__RA_PRE:
				setRaPre(RA_PRE_EDEFAULT);
				return;
			case relANNISPackage.RA_CORPUS__RA_POST:
				setRaPost(RA_POST_EDEFAULT);
				return;
			case relANNISPackage.RA_CORPUS__RA_CORPUS_ANNOTATIONS:
				getRaCorpusAnnotations().clear();
				return;
			case relANNISPackage.RA_CORPUS__RA_DOCUMENT_GRAPH:
				setRaDocumentGraph((RADocumentGraph)null);
				return;
			case relANNISPackage.RA_CORPUS__RA_CORPUS_GRAPH:
				setRaCorpusGraph((RACorpusGraph)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS__RA_ID:
				return RA_ID_EDEFAULT == null ? getRaId() != null : !RA_ID_EDEFAULT.equals(getRaId());
			case relANNISPackage.RA_CORPUS__RA_NAME:
				return RA_NAME_EDEFAULT == null ? getRaName() != null : !RA_NAME_EDEFAULT.equals(getRaName());
			case relANNISPackage.RA_CORPUS__RA_TYPE:
				return getRaType() != RA_TYPE_EDEFAULT;
			case relANNISPackage.RA_CORPUS__RA_VERSION:
				return RA_VERSION_EDEFAULT == null ? getRaVersion() != null : !RA_VERSION_EDEFAULT.equals(getRaVersion());
			case relANNISPackage.RA_CORPUS__RA_PRE:
				return RA_PRE_EDEFAULT == null ? getRaPre() != null : !RA_PRE_EDEFAULT.equals(getRaPre());
			case relANNISPackage.RA_CORPUS__RA_POST:
				return RA_POST_EDEFAULT == null ? getRaPost() != null : !RA_POST_EDEFAULT.equals(getRaPost());
			case relANNISPackage.RA_CORPUS__RA_CORPUS_ANNOTATIONS:
				return !getRaCorpusAnnotations().isEmpty();
			case relANNISPackage.RA_CORPUS__RA_DOCUMENT_GRAPH:
				return basicGetRaDocumentGraph() != null;
			case relANNISPackage.RA_CORPUS__RA_CORPUS_GRAPH:
				return getRaCorpusGraph() != null;
		}
		return super.eIsSet(featureID);
	}
	
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer();
		result.append(" (raName: ");
		result.append(this.getRaName());
		result.append(')');
		return result.toString();
	}

} //RACorpusImpl
