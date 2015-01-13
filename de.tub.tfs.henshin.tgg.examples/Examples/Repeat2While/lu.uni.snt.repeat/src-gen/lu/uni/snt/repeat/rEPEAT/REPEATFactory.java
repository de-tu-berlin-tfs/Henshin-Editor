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
package lu.uni.snt.repeat.rEPEAT;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage
 * @generated
 */
public interface REPEATFactory extends EFactory
{
  /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  REPEATFactory eINSTANCE = lu.uni.snt.repeat.rEPEAT.impl.REPEATFactoryImpl.init();

  /**
	 * Returns a new object of class '<em>RProgram</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>RProgram</em>'.
	 * @generated
	 */
  RProgram createRProgram();

  /**
	 * Returns a new object of class '<em>Stmnt LST Elem</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stmnt LST Elem</em>'.
	 * @generated
	 */
  Stmnt_LST_Elem createStmnt_LST_Elem();

  /**
	 * Returns a new object of class '<em>Repeat</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repeat</em>'.
	 * @generated
	 */
  Repeat createRepeat();

  /**
	 * Returns a new object of class '<em>Asg</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Asg</em>'.
	 * @generated
	 */
  Asg createAsg();

  /**
	 * Returns a new object of class '<em>Read</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read</em>'.
	 * @generated
	 */
  Read createRead();

  /**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
  Comment createComment();

  /**
	 * Returns a new object of class '<em>Log Expr</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Log Expr</em>'.
	 * @generated
	 */
  Log_Expr createLog_Expr();

  /**
	 * Returns a new object of class '<em>Log Expr T</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Log Expr T</em>'.
	 * @generated
	 */
  Log_Expr_T createLog_Expr_T();

  /**
	 * Returns a new object of class '<em>Log Expr Unary</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Log Expr Unary</em>'.
	 * @generated
	 */
  Log_Expr_Unary createLog_Expr_Unary();

  /**
	 * Returns a new object of class '<em>Log Expr Binary</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Log Expr Binary</em>'.
	 * @generated
	 */
  Log_Expr_Binary createLog_Expr_Binary();

  /**
	 * Returns a new object of class '<em>Log Neg</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Log Neg</em>'.
	 * @generated
	 */
  Log_Neg createLog_Neg();

  /**
	 * Returns a new object of class '<em>Sym</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sym</em>'.
	 * @generated
	 */
  Sym createSym();

  /**
	 * Returns a new object of class '<em>Source</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source</em>'.
	 * @generated
	 */
  Source createSource();

  /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
  REPEATPackage getREPEATPackage();

} //REPEATFactory
