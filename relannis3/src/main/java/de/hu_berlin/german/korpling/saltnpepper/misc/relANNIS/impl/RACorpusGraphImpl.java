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
import org.eclipse.emf.ecore.util.InternalEList;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusGraph;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.exceptions.RelANNISException;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusGraphTraverser.RA_TRAVERSAL_TYPE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.Node;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverser.GRAPH_TRAVERSE_MODE;
import de.hu_berlin.german.korpling.saltnpepper.salt.graph.modules.GraphTraverserObject;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SNode;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SGraphImpl;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.modules.SGraphAccessorModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Corpus Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusGraphImpl#getRaCorpora <em>Ra Corpora</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RACorpusGraphImpl extends SGraphImpl implements RACorpusGraph 
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RACorpusGraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_CORPUS_GRAPH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RACorpus> getRaCorpora() 
	{
		EList<RACorpus> retVal= (EList<RACorpus>)(EList<? extends SNode>)super.getSNodes();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public EList<RACorpus> getRARoots() 
	{
		EList<RACorpus> retVal= null;
		SGraphAccessorModule sGraphAccessor= new SGraphAccessorModule();
		sGraphAccessor.setSGraph(this);
		retVal= (EList<RACorpus>) (EList<? extends SNode>) sGraphAccessor.getSRoots();
		return(retVal);
	}

	/**
	 * Returns a list of roots, which are roots for the given RACorpus object.
	 * @param raCorpus the object to which roots shall be computed.
	 * @return the roots to the given corpus
	 */
	@Override
	public synchronized RACorpus getRARoots(RACorpus raCorpus) 
	{
		RACorpus retVal= null;
		GraphTraverser graphTraverser= new GraphTraverser();
		graphTraverser.setGraph(this);
		RACorpusGraphTraverser raCorpusTraverser= new RACorpusGraphTraverser();
		raCorpusTraverser.setRATraversalType(RA_TRAVERSAL_TYPE.RA_ROOTS);
		GraphTraverserObject travObj= graphTraverser.getTraverserObject(GRAPH_TRAVERSE_MODE.BOTTOM_UP, raCorpusTraverser);
		travObj.start(raCorpus);
		travObj.waitUntilFinished();
		for (Exception e:  travObj.getExceptions())
		{
			throw new RelANNISException("Some error occurs while traversing corpus structure graph.", e);
		}
		if (raCorpusTraverser.getResultObject() instanceof RACorpus)
			retVal= (RACorpus) raCorpusTraverser.getResultObject();
		return(retVal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@SuppressWarnings("unchecked")
	public synchronized void generateRAPPOrder() 
	{
		GraphTraverser graphTraverser= new GraphTraverser();
		graphTraverser.setGraph(this);
		RACorpusGraphTraverser raCorpusTraverser= new RACorpusGraphTraverser();
		raCorpusTraverser.setRATraversalType(RA_TRAVERSAL_TYPE.PP_ORDER);
		GraphTraverserObject travObj= graphTraverser.getTraverserObject(GRAPH_TRAVERSE_MODE.DEPTH_FIRST, raCorpusTraverser);
		//set traversion type to corpus structure
		travObj.start((EList<Node>) (EList<? extends Node>) this.getRARoots());
		travObj.waitUntilFinished();
		for (Exception e:  travObj.getExceptions())
		{
			throw new RelANNISException("Some error occurs while traversing corpus structure graph.", e);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS_GRAPH__RA_CORPORA:
				return ((InternalEList<?>)getRaCorpora()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS_GRAPH__RA_CORPORA:
				return getRaCorpora();
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
			case relANNISPackage.RA_CORPUS_GRAPH__RA_CORPORA:
				getRaCorpora().clear();
				getRaCorpora().addAll((Collection<? extends RACorpus>)newValue);
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
			case relANNISPackage.RA_CORPUS_GRAPH__RA_CORPORA:
				getRaCorpora().clear();
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
			case relANNISPackage.RA_CORPUS_GRAPH__RA_CORPORA:
				return !getRaCorpora().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RACorpusGraphImpl
