/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 */
package TGG_correspondence.impl;

import TGG_correspondence.GenCORR;
import TGG_correspondence.TGG_correspondencePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen CORR</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link TGG_correspondence.impl.GenCORRImpl#getGenSRC <em>Gen SRC</em>}</li>
 *   <li>{@link TGG_correspondence.impl.GenCORRImpl#getGenTGT <em>Gen TGT</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenCORRImpl extends CDOObjectImpl implements GenCORR {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenCORRImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGG_correspondencePackage.Literals.GEN_CORR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getGenSRC() {
		return (EObject)eGet(TGG_correspondencePackage.Literals.GEN_CORR__GEN_SRC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenSRC(EObject newGenSRC) {
		eSet(TGG_correspondencePackage.Literals.GEN_CORR__GEN_SRC, newGenSRC);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getGenTGT() {
		return (EObject)eGet(TGG_correspondencePackage.Literals.GEN_CORR__GEN_TGT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenTGT(EObject newGenTGT) {
		eSet(TGG_correspondencePackage.Literals.GEN_CORR__GEN_TGT, newGenTGT);
	}

} //GenCORRImpl
