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
package lu.uni.snt.whileDSL.wHILE;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage
 * @generated
 */
public interface WHILEFactory extends EFactory
{
  /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  WHILEFactory eINSTANCE = lu.uni.snt.whileDSL.wHILE.impl.WHILEFactoryImpl.init();

  /**
	 * Returns a new object of class '<em>WProgram</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>WProgram</em>'.
	 * @generated
	 */
  WProgram createWProgram();

  /**
	 * Returns a new object of class '<em>Fgmnt LST Elem</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fgmnt LST Elem</em>'.
	 * @generated
	 */
  Fgmnt_LST_Elem createFgmnt_LST_Elem();

  /**
	 * Returns a new object of class '<em>While</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>While</em>'.
	 * @generated
	 */
  While createWhile();

  /**
	 * Returns a new object of class '<em>Var Def</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Var Def</em>'.
	 * @generated
	 */
  Var_Def createVar_Def();

  /**
	 * Returns a new object of class '<em>Fn Call</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fn Call</em>'.
	 * @generated
	 */
  Fn_Call createFn_Call();

  /**
	 * Returns a new object of class '<em>Fn Def</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fn Def</em>'.
	 * @generated
	 */
  Fn_Def createFn_Def();

  /**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
  Comment createComment();

  /**
	 * Returns a new object of class '<em>Expr</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr</em>'.
	 * @generated
	 */
  Expr createExpr();

  /**
	 * Returns a new object of class '<em>Expr T</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr T</em>'.
	 * @generated
	 */
  Expr_T createExpr_T();

  /**
	 * Returns a new object of class '<em>Unary</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unary</em>'.
	 * @generated
	 */
  Unary createUnary();

  /**
	 * Returns a new object of class '<em>Binary</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary</em>'.
	 * @generated
	 */
  Binary createBinary();

  /**
	 * Returns a new object of class '<em>Neg</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Neg</em>'.
	 * @generated
	 */
  Neg createNeg();

  /**
	 * Returns a new object of class '<em>Var</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Var</em>'.
	 * @generated
	 */
  Var createVar();

  /**
	 * Returns a new object of class '<em>Input</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Input</em>'.
	 * @generated
	 */
  Input createInput();

  /**
	 * Returns a new object of class '<em>Target</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Target</em>'.
	 * @generated
	 */
  Target createTarget();

  /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
  WHILEPackage getWHILEPackage();

} //WHILEFactory
