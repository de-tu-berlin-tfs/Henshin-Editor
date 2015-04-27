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
package lu.uni.snt.repeat.rEPEAT.util;

import TGG_correspondence.AbstractSource;
import lu.uni.snt.repeat.rEPEAT.*;

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
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage
 * @generated
 */
public class REPEATSwitch<T> extends Switch<T>
{
  /**
	 * The cached model package
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static REPEATPackage modelPackage;

  /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public REPEATSwitch()
  {
		if (modelPackage == null) {
			modelPackage = REPEATPackage.eINSTANCE;
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
			case REPEATPackage.RPROGRAM: {
				RProgram rProgram = (RProgram)theEObject;
				T result = caseRProgram(rProgram);
				if (result == null) result = caseSource(rProgram);
				if (result == null) result = caseAbstractSource(rProgram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.STMNT_LST_ELEM: {
				Stmnt_LST_Elem stmnt_LST_Elem = (Stmnt_LST_Elem)theEObject;
				T result = caseStmnt_LST_Elem(stmnt_LST_Elem);
				if (result == null) result = caseSource(stmnt_LST_Elem);
				if (result == null) result = caseAbstractSource(stmnt_LST_Elem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.REPEAT: {
				Repeat repeat = (Repeat)theEObject;
				T result = caseRepeat(repeat);
				if (result == null) result = caseStmnt_LST_Elem(repeat);
				if (result == null) result = caseSource(repeat);
				if (result == null) result = caseAbstractSource(repeat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.ASG: {
				Asg asg = (Asg)theEObject;
				T result = caseAsg(asg);
				if (result == null) result = caseStmnt_LST_Elem(asg);
				if (result == null) result = caseSource(asg);
				if (result == null) result = caseAbstractSource(asg);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.READ: {
				Read read = (Read)theEObject;
				T result = caseRead(read);
				if (result == null) result = caseStmnt_LST_Elem(read);
				if (result == null) result = caseSource(read);
				if (result == null) result = caseAbstractSource(read);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.COMMENT: {
				Comment comment = (Comment)theEObject;
				T result = caseComment(comment);
				if (result == null) result = caseStmnt_LST_Elem(comment);
				if (result == null) result = caseSource(comment);
				if (result == null) result = caseAbstractSource(comment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.LOG_EXPR: {
				Log_Expr log_Expr = (Log_Expr)theEObject;
				T result = caseLog_Expr(log_Expr);
				if (result == null) result = caseSource(log_Expr);
				if (result == null) result = caseAbstractSource(log_Expr);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.LOG_EXPR_T: {
				Log_Expr_T log_Expr_T = (Log_Expr_T)theEObject;
				T result = caseLog_Expr_T(log_Expr_T);
				if (result == null) result = caseSource(log_Expr_T);
				if (result == null) result = caseAbstractSource(log_Expr_T);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.LOG_EXPR_UNARY: {
				Log_Expr_Unary log_Expr_Unary = (Log_Expr_Unary)theEObject;
				T result = caseLog_Expr_Unary(log_Expr_Unary);
				if (result == null) result = caseLog_Expr_T(log_Expr_Unary);
				if (result == null) result = caseSource(log_Expr_Unary);
				if (result == null) result = caseAbstractSource(log_Expr_Unary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.LOG_EXPR_BINARY: {
				Log_Expr_Binary log_Expr_Binary = (Log_Expr_Binary)theEObject;
				T result = caseLog_Expr_Binary(log_Expr_Binary);
				if (result == null) result = caseLog_Expr_T(log_Expr_Binary);
				if (result == null) result = caseSource(log_Expr_Binary);
				if (result == null) result = caseAbstractSource(log_Expr_Binary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.LOG_NEG: {
				Log_Neg log_Neg = (Log_Neg)theEObject;
				T result = caseLog_Neg(log_Neg);
				if (result == null) result = caseLog_Expr_Unary(log_Neg);
				if (result == null) result = caseLog_Expr_T(log_Neg);
				if (result == null) result = caseSource(log_Neg);
				if (result == null) result = caseAbstractSource(log_Neg);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.SYM: {
				Sym sym = (Sym)theEObject;
				T result = caseSym(sym);
				if (result == null) result = caseLog_Expr_Unary(sym);
				if (result == null) result = caseLog_Expr_T(sym);
				if (result == null) result = caseSource(sym);
				if (result == null) result = caseAbstractSource(sym);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case REPEATPackage.SOURCE: {
				Source source = (Source)theEObject;
				T result = caseSource(source);
				if (result == null) result = caseAbstractSource(source);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>RProgram</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RProgram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseRProgram(RProgram object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Stmnt LST Elem</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stmnt LST Elem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseStmnt_LST_Elem(Stmnt_LST_Elem object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Repeat</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repeat</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseRepeat(Repeat object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Asg</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Asg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseAsg(Asg object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Read</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Read</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseRead(Read object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Log Expr</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Expr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseLog_Expr(Log_Expr object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Log Expr T</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Expr T</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseLog_Expr_T(Log_Expr_T object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Log Expr Unary</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Expr Unary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseLog_Expr_Unary(Log_Expr_Unary object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Log Expr Binary</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Expr Binary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseLog_Expr_Binary(Log_Expr_Binary object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Log Neg</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Neg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseLog_Neg(Log_Neg object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Sym</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sym</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseSym(Sym object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Source</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseSource(Source object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractSource(AbstractSource object) {
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

} //REPEATSwitch
