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
package correspondece.impl;

import correspondece.*;

import org.eclipse.emf.ecore.EClass;
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
public class CorrespondeceFactoryImpl extends EFactoryImpl implements CorrespondeceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CorrespondeceFactory init() {
		try {
			CorrespondeceFactory theCorrespondeceFactory = (CorrespondeceFactory)EPackage.Registry.INSTANCE.getEFactory(CorrespondecePackage.eNS_URI);
			if (theCorrespondeceFactory != null) {
				return theCorrespondeceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CorrespondeceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondeceFactoryImpl() {
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
			case CorrespondecePackage.CT: return createCT();
			case CorrespondecePackage.AFK: return createAFK();
			case CorrespondecePackage.AC: return createAC();
			case CorrespondecePackage.CD2DB: return createCD2DB();
			case CorrespondecePackage.A2T: return createA2T();
			case CorrespondecePackage.GENERIC_CORR: return createGenericCorr();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CT createCT() {
		CTImpl ct = new CTImpl();
		return ct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AFK createAFK() {
		AFKImpl afk = new AFKImpl();
		return afk;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AC createAC() {
		ACImpl ac = new ACImpl();
		return ac;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CD2DB createCD2DB() {
		CD2DBImpl cd2DB = new CD2DBImpl();
		return cd2DB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public A2T createA2T() {
		A2TImpl a2T = new A2TImpl();
		return a2T;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericCorr createGenericCorr() {
		GenericCorrImpl genericCorr = new GenericCorrImpl();
		return genericCorr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondecePackage getCorrespondecePackage() {
		return (CorrespondecePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CorrespondecePackage getPackage() {
		return CorrespondecePackage.eINSTANCE;
	}

} //CorrespondeceFactoryImpl
