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
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.henshin.model.Attribute;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.AttributeLayoutImpl#isNew <em>New</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.AttributeLayoutImpl#getLhsattribute <em>Lhsattribute</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.AttributeLayoutImpl#getRhsattribute <em>Rhsattribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeLayoutImpl extends EObjectImpl implements AttributeLayout {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeLayoutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.ATTRIBUTE_LAYOUT;
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
	public boolean isNew() {
		return (Boolean)eGet(TggPackage.Literals.ATTRIBUTE_LAYOUT__NEW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNew(boolean newNew) {
		eSet(TggPackage.Literals.ATTRIBUTE_LAYOUT__NEW, newNew);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getLhsattribute() {
		return (Attribute)eGet(TggPackage.Literals.ATTRIBUTE_LAYOUT__LHSATTRIBUTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsattribute(Attribute newLhsattribute) {
		eSet(TggPackage.Literals.ATTRIBUTE_LAYOUT__LHSATTRIBUTE, newLhsattribute);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getRhsattribute() {
		return (Attribute)eGet(TggPackage.Literals.ATTRIBUTE_LAYOUT__RHSATTRIBUTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsattribute(Attribute newRhsattribute) {
		eSet(TggPackage.Literals.ATTRIBUTE_LAYOUT__RHSATTRIBUTE, newRhsattribute);
	}

} //AttributeLayoutImpl
