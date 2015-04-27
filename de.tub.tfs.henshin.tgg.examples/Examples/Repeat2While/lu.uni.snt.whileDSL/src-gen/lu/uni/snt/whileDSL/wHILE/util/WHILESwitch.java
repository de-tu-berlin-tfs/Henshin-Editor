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
package lu.uni.snt.whileDSL.wHILE.util;

import TGG_correspondence.AbstractTarget;
import lu.uni.snt.whileDSL.wHILE.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage
 * @generated
 */
public class WHILESwitch<T> extends Switch<T>
{
  /**
	 * The cached model package
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static WHILEPackage modelPackage;

  /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public WHILESwitch()
  {
		if (modelPackage == null) {
			modelPackage = WHILEPackage.eINSTANCE;
		}
	}

  /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
		return ePackage == modelPackage;
	}

  /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
		switch (classifierID) {
			case WHILEPackage.WPROGRAM: {
				WProgram wProgram = (WProgram)theEObject;
				T result = caseWProgram(wProgram);
				if (result == null) result = caseTarget(wProgram);
				if (result == null) result = caseAbstractTarget(wProgram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.FGMNT_LST_ELEM: {
				Fgmnt_LST_Elem fgmnt_LST_Elem = (Fgmnt_LST_Elem)theEObject;
				T result = caseFgmnt_LST_Elem(fgmnt_LST_Elem);
				if (result == null) result = caseTarget(fgmnt_LST_Elem);
				if (result == null) result = caseAbstractTarget(fgmnt_LST_Elem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.WHILE: {
				While while_ = (While)theEObject;
				T result = caseWhile(while_);
				if (result == null) result = caseFgmnt_LST_Elem(while_);
				if (result == null) result = caseTarget(while_);
				if (result == null) result = caseAbstractTarget(while_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.VAR_DEF: {
				Var_Def var_Def = (Var_Def)theEObject;
				T result = caseVar_Def(var_Def);
				if (result == null) result = caseFgmnt_LST_Elem(var_Def);
				if (result == null) result = caseTarget(var_Def);
				if (result == null) result = caseAbstractTarget(var_Def);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.FN_CALL: {
				Fn_Call fn_Call = (Fn_Call)theEObject;
				T result = caseFn_Call(fn_Call);
				if (result == null) result = caseFgmnt_LST_Elem(fn_Call);
				if (result == null) result = caseTarget(fn_Call);
				if (result == null) result = caseAbstractTarget(fn_Call);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.FN_DEF: {
				Fn_Def fn_Def = (Fn_Def)theEObject;
				T result = caseFn_Def(fn_Def);
				if (result == null) result = caseFgmnt_LST_Elem(fn_Def);
				if (result == null) result = caseTarget(fn_Def);
				if (result == null) result = caseAbstractTarget(fn_Def);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.COMMENT: {
				Comment comment = (Comment)theEObject;
				T result = caseComment(comment);
				if (result == null) result = caseFgmnt_LST_Elem(comment);
				if (result == null) result = caseTarget(comment);
				if (result == null) result = caseAbstractTarget(comment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.EXPR: {
				Expr expr = (Expr)theEObject;
				T result = caseExpr(expr);
				if (result == null) result = caseTarget(expr);
				if (result == null) result = caseAbstractTarget(expr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.EXPR_T: {
				Expr_T expr_T = (Expr_T)theEObject;
				T result = caseExpr_T(expr_T);
				if (result == null) result = caseTarget(expr_T);
				if (result == null) result = caseAbstractTarget(expr_T);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.UNARY: {
				Unary unary = (Unary)theEObject;
				T result = caseUnary(unary);
				if (result == null) result = caseExpr_T(unary);
				if (result == null) result = caseTarget(unary);
				if (result == null) result = caseAbstractTarget(unary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.BINARY: {
				Binary binary = (Binary)theEObject;
				T result = caseBinary(binary);
				if (result == null) result = caseExpr_T(binary);
				if (result == null) result = caseTarget(binary);
				if (result == null) result = caseAbstractTarget(binary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.NEG: {
				Neg neg = (Neg)theEObject;
				T result = caseNeg(neg);
				if (result == null) result = caseUnary(neg);
				if (result == null) result = caseExpr_T(neg);
				if (result == null) result = caseTarget(neg);
				if (result == null) result = caseAbstractTarget(neg);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.VAR: {
				Var var = (Var)theEObject;
				T result = caseVar(var);
				if (result == null) result = caseUnary(var);
				if (result == null) result = caseExpr_T(var);
				if (result == null) result = caseTarget(var);
				if (result == null) result = caseAbstractTarget(var);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.INPUT: {
				Input input = (Input)theEObject;
				T result = caseInput(input);
				if (result == null) result = caseUnary(input);
				if (result == null) result = caseExpr_T(input);
				if (result == null) result = caseTarget(input);
				if (result == null) result = caseAbstractTarget(input);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case WHILEPackage.TARGET: {
				Target target = (Target)theEObject;
				T result = caseTarget(target);
				if (result == null) result = caseAbstractTarget(target);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>WProgram</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>WProgram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseWProgram(WProgram object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Fgmnt LST Elem</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fgmnt LST Elem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseFgmnt_LST_Elem(Fgmnt_LST_Elem object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>While</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>While</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseWhile(While object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Var Def</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseVar_Def(Var_Def object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Fn Call</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fn Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseFn_Call(Fn_Call object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Fn Def</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fn Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseFn_Def(Fn_Def object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseComment(Comment object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Expr</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseExpr(Expr object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Expr T</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr T</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseExpr_T(Expr_T object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Unary</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseUnary(Unary object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Binary</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseBinary(Binary object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Neg</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Neg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseNeg(Neg object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Var</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseVar(Var object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Input</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseInput(Input object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Target</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseTarget(Target object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractTarget(AbstractTarget object) {
		return null;
	}

		/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
  @Override
  public T defaultCase(EObject object)
  {
		return null;
	}

} //WHILESwitch
