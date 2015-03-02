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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpus;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.RACorpusRelation;
import de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.relANNISPackage;
import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.impl.SRelationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RA Corpus Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusRelationImpl#getRaSuperCorpus <em>Ra Super Corpus</em>}</li>
 *   <li>{@link de.hu_berlin.german.korpling.saltnpepper.misc.relANNIS.impl.RACorpusRelationImpl#getRaSubCorpus <em>Ra Sub Corpus</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RACorpusRelationImpl extends SRelationImpl implements RACorpusRelation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RACorpusRelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return relANNISPackage.Literals.RA_CORPUS_RELATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpus getRaSuperCorpus() {
		RACorpus raSuperCorpus = basicGetRaSuperCorpus();
		return raSuperCorpus != null && raSuperCorpus.eIsProxy() ? (RACorpus)eResolveProxy((InternalEObject)raSuperCorpus) : raSuperCorpus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RACorpus basicGetRaSuperCorpus() 
	{
		return((RACorpus)super.getSSource());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaSuperCorpus(RACorpus newRaSuperCorpus) 
	{
		super.setSource(newRaSuperCorpus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RACorpus getRaSubCorpus() {
		RACorpus raSubCorpus = basicGetRaSubCorpus();
		return raSubCorpus != null && raSubCorpus.eIsProxy() ? (RACorpus)eResolveProxy((InternalEObject)raSubCorpus) : raSubCorpus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public RACorpus basicGetRaSubCorpus() 
	{
		return((RACorpus)super.getSTarget());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setRaSubCorpus(RACorpus newRaSubCorpus) 
	{
		super.setSTarget(newRaSubCorpus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUPER_CORPUS:
				if (resolve) return getRaSuperCorpus();
				return basicGetRaSuperCorpus();
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUB_CORPUS:
				if (resolve) return getRaSubCorpus();
				return basicGetRaSubCorpus();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUPER_CORPUS:
				setRaSuperCorpus((RACorpus)newValue);
				return;
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUB_CORPUS:
				setRaSubCorpus((RACorpus)newValue);
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
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUPER_CORPUS:
				setRaSuperCorpus((RACorpus)null);
				return;
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUB_CORPUS:
				setRaSubCorpus((RACorpus)null);
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
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUPER_CORPUS:
				return basicGetRaSuperCorpus() != null;
			case relANNISPackage.RA_CORPUS_RELATION__RA_SUB_CORPUS:
				return basicGetRaSubCorpus() != null;
		}
		return super.eIsSet(featureID);
	}

} //RACorpusRelationImpl
