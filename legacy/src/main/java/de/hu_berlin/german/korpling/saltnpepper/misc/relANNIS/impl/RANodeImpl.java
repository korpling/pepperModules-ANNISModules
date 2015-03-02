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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RADocumentGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANode;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RANodeAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RAText;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SAnnotation;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SFeature;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SaltCoreFactory;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SNodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaId <em>Ra Id</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaText_ref <em>Ra Text ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaCorpus_ref <em>Ra Corpus ref</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaNamespace <em>Ra Namespace</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaName <em>Ra Name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaLeft <em>Ra Left</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaRight <em>Ra Right</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaToken_Index <em>Ra Token Index</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaContinuous <em>Ra Continuous</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaSpan <em>Ra Span</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaAnnotations <em>Ra Annotations</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaDocumentGraph <em>Ra Document Graph</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRaText <em>Ra Text</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getSegment_name <em>Segment name</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getLeftSegment <em>Left Segment</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RANodeImpl#getRightSegment <em>Right Segment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RANodeImpl extends SNodeImpl implements RANode {
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
	 * The default value of the '{@link #getRaText_ref() <em>Ra Text ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaText_ref()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_TEXT_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaCorpus_ref() <em>Ra Corpus ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaCorpus_ref()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_CORPUS_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaNamespace() <em>Ra Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_NAMESPACE_EDEFAULT = null;

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
	 * The default value of the '{@link #getRaLeft() <em>Ra Left</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaLeft()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_LEFT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaRight() <em>Ra Right</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaRight()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_RIGHT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaToken_Index() <em>Ra Token Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaToken_Index()
	 * @generated
	 * @ordered
	 */
	protected static final Long RA_TOKEN_INDEX_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaContinuous() <em>Ra Continuous</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaContinuous()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean RA_CONTINUOUS_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRaSpan() <em>Ra Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaSpan()
	 * @generated
	 * @ordered
	 */
	protected static final String RA_SPAN_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSegment_name() <em>Segment name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSegment_name()
	 * @generated
	 * @ordered
	 */
	protected static final String SEGMENT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSegment_name() <em>Segment name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSegment_name()
	 * @generated
	 * @ordered
	 */
	protected String segment_name = SEGMENT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLeftSegment() <em>Left Segment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftSegment()
	 * @generated
	 * @ordered
	 */
	protected static final Long LEFT_SEGMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLeftSegment() <em>Left Segment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftSegment()
	 * @generated
	 * @ordered
	 */
	protected Long leftSegment = LEFT_SEGMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getRightSegment() <em>Right Segment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightSegment()
	 * @generated
	 * @ordered
	 */
	protected static final Long RIGHT_SEGMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRightSegment() <em>Right Segment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightSegment()
	 * @generated
	 * @ordered
	 */
	protected Long rightSegment = RIGHT_SEGMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	protected RANodeImpl() 
	{
		super();
		init();
	}
	
	/**
	 * initializes this object
	 */
	protected void init()
	{
		//create a raId for this object
		this.setSId(ID_PREFIX+ getNewRAId());
	}

	/**
	 * Counter for current raId.
	 */
	protected static Long raId= 0l;
	
	/**
	 * Returns a new and unique raIs for this class.
	 * @return
	 */
	protected static Long getNewRAId()
	{
		Long retVal= raId;
		raId++;
		return(retVal);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_NODE;
	}

// ------------------------- start: handling ra id 	
	private static String ID_PREFIX="node";
	
	/**
	 * Returns a unique identifier for RADocumentGraph.
	 * @return
	 */
	public static String createPrefixedId(Long raId)
	{
		String retVal= null;
		retVal= ID_PREFIX+ raId;
		return(retVal);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaId() 
	{
		Long retVal= null;
		
		String idStr= super.getSId();
		retVal= new Long(idStr.replace(ID_PREFIX, ""));
		
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaId(Long newRaId) 
	{
		super.setSId(ID_PREFIX + newRaId.toString());
	}
// ------------------------- end: handling ra id
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaText_ref() 
	{
		Long retVal= null;
		if (this.getRaText()!= null)
			retVal= this.getRaText().getRaId();
		
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaCorpus_ref() 
	{
		Long retVal= null;
		if (this.getRaDocumentGraph()!= null)
			if (this.getRaDocumentGraph().getRaCorpus()!= null)
				retVal= this.getRaDocumentGraph().getRaCorpus().getRaId();
		
		return(retVal);
	}

	public static String KW_NS= "relANNIS";
//---------------------------- start Namespace	
	public static String KW_RANAMEPACE= "RANAMEPACE";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String getRaNamespace() 
	{
		String retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RANAMEPACE);
		if (sFeature!= null)
		{
			retVal= sFeature.getSValue().toString();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaNamespace(String newRaText) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RANAMEPACE);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RANAMEPACE);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaText);
	}
//---------------------------- end Namespace
	
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

// ---------------------------- start: raLeft	
	public static String KW_RALEFT= "RALEFT";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaLeft() 
	{
		Long retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RALEFT);
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
	public void setRaLeft(Long newRaLeft) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RALEFT);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RALEFT);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaLeft);
	}
// ---------------------------- end: raLeft
// ---------------------------- start: raRight
	public static String KW_RARIGHT= "RARIGHT";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaRight() 
	{
		Long retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RARIGHT);
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
	public void setRaRight(Long newRARIGHT) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RARIGHT);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RARIGHT);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRARIGHT);
	}

// ---------------------------- start: raRight
// ---------------------------- start: token index
	public static String KW_RATOKEN_INDEX= "RATOKEN_INDEX";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Long getRaToken_Index() 
	{
		Long retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RATOKEN_INDEX);
		if (sFeature!= null)
		{
			if (sFeature.getSValue()!= null)
				retVal= new Long(sFeature.getSValue().toString());
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaToken_Index(Long newRATOKEN_INDEX) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RATOKEN_INDEX);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RATOKEN_INDEX);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRATOKEN_INDEX);
	}
// ---------------------------- end: token index
	/**
	 * Stores if this node is continuous. 
	 */
	private Boolean isContinuous= null;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Boolean getRaContinuous() 
	{
		//TODO traversion for getting the information, if it isn�t set
		return(this.isContinuous);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaContinuous(Boolean newRaContinuous) 
	{
		this.isContinuous= newRaContinuous;
	}

// ---------------------------- start: span
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String getRaSpan() 
	{
		String retVal= null;
		if (this.getRaDocumentGraph()!= null)
		{
			retVal= this.getRaDocumentGraph().getRaSpan(this);
			if (retVal.length() >= 2000)
				retVal= retVal.substring(0, 2000);
		}
		return(retVal);
	}
// ---------------------------- end: span

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RANodeAnnotation> getRaAnnotations() 
	{
		EList<RANodeAnnotation> raAnnos= (EList<RANodeAnnotation>) (EList<? extends SAnnotation>) super.getSAnnotations();
		return(raAnnos);
	}
// ========================== start raDocumentGraph	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RADocumentGraph getRaDocumentGraph() {
		RADocumentGraph raDocumentGraph = basicGetRaDocumentGraph();
		return raDocumentGraph != null && raDocumentGraph.eIsProxy() ? (RADocumentGraph)eResolveProxy((InternalEObject)raDocumentGraph) : raDocumentGraph;
	}

		/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RADocumentGraph basicGetRaDocumentGraph() 
	{
		RADocumentGraph retVal= null;
		if (super.getSGraph() instanceof RADocumentGraph)
			retVal= (RADocumentGraph) super.getSGraph();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaDocumentGraph(RADocumentGraph newRaDocumentGraph) 
	{
		super.setSGraph(newRaDocumentGraph);
	}
// ========================== end raDocumentGraph

// ========================== start raText	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RAText getRaText() {
		RAText raText = basicGetRaText();
		return raText != null && raText.eIsProxy() ? (RAText)eResolveProxy((InternalEObject)raText) : raText;
	}

	protected String KW_RA_TEXT= "KW_RA_TEXT";
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RAText basicGetRaText() 
	{
		RAText retVal= null;
		SFeature sFeature= super.getSFeature(KW_NS, KW_RA_TEXT);
		if (sFeature!= null)
		{
			retVal= (RAText) sFeature.getSValue();
		}
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaText(RAText newRaText) 
	{
		SFeature sFeature= super.getSFeature(KW_NS, KW_RA_TEXT);
		if (sFeature== null)
		{
			sFeature= SaltCoreFactory.eINSTANCE.createSFeature();
			sFeature.setSNS(KW_NS);
			sFeature.setSName(KW_RA_TEXT);
			this.addSFeature(sFeature);
		}
		
		sFeature.setSValue(newRaText);
	}
/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSegment_name() {
		return segment_name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSegment_name(String newSegment_name) {
		String oldSegment_name = segment_name;
		segment_name = newSegment_name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_NODE__SEGMENT_NAME, oldSegment_name, segment_name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long getLeftSegment() {
		return leftSegment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeftSegment(Long newLeftSegment) {
		Long oldLeftSegment = leftSegment;
		leftSegment = newLeftSegment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_NODE__LEFT_SEGMENT, oldLeftSegment, leftSegment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long getRightSegment() {
		return rightSegment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRightSegment(Long newRightSegment) {
		Long oldRightSegment = rightSegment;
		rightSegment = newRightSegment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, relANNISPackage.RA_NODE__RIGHT_SEGMENT, oldRightSegment, rightSegment));
	}

	// ========================== end raText
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_NODE__RA_ID:
				return getRaId();
			case relANNISPackage.RA_NODE__RA_TEXT_REF:
				return getRaText_ref();
			case relANNISPackage.RA_NODE__RA_CORPUS_REF:
				return getRaCorpus_ref();
			case relANNISPackage.RA_NODE__RA_NAMESPACE:
				return getRaNamespace();
			case relANNISPackage.RA_NODE__RA_NAME:
				return getRaName();
			case relANNISPackage.RA_NODE__RA_LEFT:
				return getRaLeft();
			case relANNISPackage.RA_NODE__RA_RIGHT:
				return getRaRight();
			case relANNISPackage.RA_NODE__RA_TOKEN_INDEX:
				return getRaToken_Index();
			case relANNISPackage.RA_NODE__RA_CONTINUOUS:
				return getRaContinuous();
			case relANNISPackage.RA_NODE__RA_SPAN:
				return getRaSpan();
			case relANNISPackage.RA_NODE__RA_ANNOTATIONS:
				return getRaAnnotations();
			case relANNISPackage.RA_NODE__RA_DOCUMENT_GRAPH:
				if (resolve) return getRaDocumentGraph();
				return basicGetRaDocumentGraph();
			case relANNISPackage.RA_NODE__RA_TEXT:
				if (resolve) return getRaText();
				return basicGetRaText();
			case relANNISPackage.RA_NODE__SEGMENT_NAME:
				return getSegment_name();
			case relANNISPackage.RA_NODE__LEFT_SEGMENT:
				return getLeftSegment();
			case relANNISPackage.RA_NODE__RIGHT_SEGMENT:
				return getRightSegment();
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
			case relANNISPackage.RA_NODE__RA_ID:
				setRaId((Long)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_NAMESPACE:
				setRaNamespace((String)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_NAME:
				setRaName((String)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_LEFT:
				setRaLeft((Long)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_RIGHT:
				setRaRight((Long)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_TOKEN_INDEX:
				setRaToken_Index((Long)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_CONTINUOUS:
				setRaContinuous((Boolean)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_ANNOTATIONS:
				getRaAnnotations().clear();
				getRaAnnotations().addAll((Collection<? extends RANodeAnnotation>)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_DOCUMENT_GRAPH:
				setRaDocumentGraph((RADocumentGraph)newValue);
				return;
			case relANNISPackage.RA_NODE__RA_TEXT:
				setRaText((RAText)newValue);
				return;
			case relANNISPackage.RA_NODE__SEGMENT_NAME:
				setSegment_name((String)newValue);
				return;
			case relANNISPackage.RA_NODE__LEFT_SEGMENT:
				setLeftSegment((Long)newValue);
				return;
			case relANNISPackage.RA_NODE__RIGHT_SEGMENT:
				setRightSegment((Long)newValue);
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
			case relANNISPackage.RA_NODE__RA_ID:
				setRaId(RA_ID_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_NAMESPACE:
				setRaNamespace(RA_NAMESPACE_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_NAME:
				setRaName(RA_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_LEFT:
				setRaLeft(RA_LEFT_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_RIGHT:
				setRaRight(RA_RIGHT_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_TOKEN_INDEX:
				setRaToken_Index(RA_TOKEN_INDEX_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_CONTINUOUS:
				setRaContinuous(RA_CONTINUOUS_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RA_ANNOTATIONS:
				getRaAnnotations().clear();
				return;
			case relANNISPackage.RA_NODE__RA_DOCUMENT_GRAPH:
				setRaDocumentGraph((RADocumentGraph)null);
				return;
			case relANNISPackage.RA_NODE__RA_TEXT:
				setRaText((RAText)null);
				return;
			case relANNISPackage.RA_NODE__SEGMENT_NAME:
				setSegment_name(SEGMENT_NAME_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__LEFT_SEGMENT:
				setLeftSegment(LEFT_SEGMENT_EDEFAULT);
				return;
			case relANNISPackage.RA_NODE__RIGHT_SEGMENT:
				setRightSegment(RIGHT_SEGMENT_EDEFAULT);
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
			case relANNISPackage.RA_NODE__RA_ID:
				return RA_ID_EDEFAULT == null ? getRaId() != null : !RA_ID_EDEFAULT.equals(getRaId());
			case relANNISPackage.RA_NODE__RA_TEXT_REF:
				return RA_TEXT_REF_EDEFAULT == null ? getRaText_ref() != null : !RA_TEXT_REF_EDEFAULT.equals(getRaText_ref());
			case relANNISPackage.RA_NODE__RA_CORPUS_REF:
				return RA_CORPUS_REF_EDEFAULT == null ? getRaCorpus_ref() != null : !RA_CORPUS_REF_EDEFAULT.equals(getRaCorpus_ref());
			case relANNISPackage.RA_NODE__RA_NAMESPACE:
				return RA_NAMESPACE_EDEFAULT == null ? getRaNamespace() != null : !RA_NAMESPACE_EDEFAULT.equals(getRaNamespace());
			case relANNISPackage.RA_NODE__RA_NAME:
				return RA_NAME_EDEFAULT == null ? getRaName() != null : !RA_NAME_EDEFAULT.equals(getRaName());
			case relANNISPackage.RA_NODE__RA_LEFT:
				return RA_LEFT_EDEFAULT == null ? getRaLeft() != null : !RA_LEFT_EDEFAULT.equals(getRaLeft());
			case relANNISPackage.RA_NODE__RA_RIGHT:
				return RA_RIGHT_EDEFAULT == null ? getRaRight() != null : !RA_RIGHT_EDEFAULT.equals(getRaRight());
			case relANNISPackage.RA_NODE__RA_TOKEN_INDEX:
				return RA_TOKEN_INDEX_EDEFAULT == null ? getRaToken_Index() != null : !RA_TOKEN_INDEX_EDEFAULT.equals(getRaToken_Index());
			case relANNISPackage.RA_NODE__RA_CONTINUOUS:
				return RA_CONTINUOUS_EDEFAULT == null ? getRaContinuous() != null : !RA_CONTINUOUS_EDEFAULT.equals(getRaContinuous());
			case relANNISPackage.RA_NODE__RA_SPAN:
				return RA_SPAN_EDEFAULT == null ? getRaSpan() != null : !RA_SPAN_EDEFAULT.equals(getRaSpan());
			case relANNISPackage.RA_NODE__RA_ANNOTATIONS:
				return !getRaAnnotations().isEmpty();
			case relANNISPackage.RA_NODE__RA_DOCUMENT_GRAPH:
				return basicGetRaDocumentGraph() != null;
			case relANNISPackage.RA_NODE__RA_TEXT:
				return basicGetRaText() != null;
			case relANNISPackage.RA_NODE__SEGMENT_NAME:
				return SEGMENT_NAME_EDEFAULT == null ? segment_name != null : !SEGMENT_NAME_EDEFAULT.equals(segment_name);
			case relANNISPackage.RA_NODE__LEFT_SEGMENT:
				return LEFT_SEGMENT_EDEFAULT == null ? leftSegment != null : !LEFT_SEGMENT_EDEFAULT.equals(leftSegment);
			case relANNISPackage.RA_NODE__RIGHT_SEGMENT:
				return RIGHT_SEGMENT_EDEFAULT == null ? rightSegment != null : !RIGHT_SEGMENT_EDEFAULT.equals(rightSegment);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (segment_name: ");
		result.append(segment_name);
		result.append(", leftSegment: ");
		result.append(leftSegment);
		result.append(", rightSegment: ");
		result.append(rightSegment);
		result.append(')');
		return result.toString();
	}

} //RANodeImpl
