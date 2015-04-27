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
package correspondece;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see correspondece.CorrespondecePackage
 * @generated
 */
public interface CorrespondeceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CorrespondeceFactory eINSTANCE = correspondece.impl.CorrespondeceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>CT</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CT</em>'.
	 * @generated
	 */
	CT createCT();

	/**
	 * Returns a new object of class '<em>AFK</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>AFK</em>'.
	 * @generated
	 */
	AFK createAFK();

	/**
	 * Returns a new object of class '<em>AC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>AC</em>'.
	 * @generated
	 */
	AC createAC();

	/**
	 * Returns a new object of class '<em>CD2DB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CD2DB</em>'.
	 * @generated
	 */
	CD2DB createCD2DB();

	/**
	 * Returns a new object of class '<em>A2T</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>A2T</em>'.
	 * @generated
	 */
	A2T createA2T();

	/**
	 * Returns a new object of class '<em>Generic Corr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Corr</em>'.
	 * @generated
	 */
	GenericCorr createGenericCorr();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorrespondecePackage getCorrespondecePackage();

} //CorrespondeceFactory
