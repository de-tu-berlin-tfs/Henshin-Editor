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
package lu.uni.snt.whileDSL.wHILE.impl;

import lu.uni.snt.whileDSL.wHILE.*;

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
public class WHILEFactoryImpl extends EFactoryImpl implements WHILEFactory
{
  /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static WHILEFactory init()
  {
		try {
			WHILEFactory theWHILEFactory = (WHILEFactory)EPackage.Registry.INSTANCE.getEFactory(WHILEPackage.eNS_URI);
			if (theWHILEFactory != null) {
				return theWHILEFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WHILEFactoryImpl();
	}

  /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public WHILEFactoryImpl()
  {
		super();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public EObject create(EClass eClass)
  {
		switch (eClass.getClassifierID()) {
			case WHILEPackage.WPROGRAM: return createWProgram();
			case WHILEPackage.FGMNT_LST_ELEM: return createFgmnt_LST_Elem();
			case WHILEPackage.WHILE: return createWhile();
			case WHILEPackage.VAR_DEF: return createVar_Def();
			case WHILEPackage.FN_CALL: return createFn_Call();
			case WHILEPackage.FN_DEF: return createFn_Def();
			case WHILEPackage.COMMENT: return createComment();
			case WHILEPackage.EXPR: return createExpr();
			case WHILEPackage.EXPR_T: return createExpr_T();
			case WHILEPackage.UNARY: return createUnary();
			case WHILEPackage.BINARY: return createBinary();
			case WHILEPackage.NEG: return createNeg();
			case WHILEPackage.VAR: return createVar();
			case WHILEPackage.INPUT: return createInput();
			case WHILEPackage.TARGET: return createTarget();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public WProgram createWProgram()
  {
		WProgramImpl wProgram = new WProgramImpl();
		return wProgram;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Fgmnt_LST_Elem createFgmnt_LST_Elem()
  {
		Fgmnt_LST_ElemImpl fgmnt_LST_Elem = new Fgmnt_LST_ElemImpl();
		return fgmnt_LST_Elem;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public While createWhile()
  {
		WhileImpl while_ = new WhileImpl();
		return while_;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Var_Def createVar_Def()
  {
		Var_DefImpl var_Def = new Var_DefImpl();
		return var_Def;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Fn_Call createFn_Call()
  {
		Fn_CallImpl fn_Call = new Fn_CallImpl();
		return fn_Call;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Fn_Def createFn_Def()
  {
		Fn_DefImpl fn_Def = new Fn_DefImpl();
		return fn_Def;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Comment createComment()
  {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Expr createExpr()
  {
		ExprImpl expr = new ExprImpl();
		return expr;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Expr_T createExpr_T()
  {
		Expr_TImpl expr_T = new Expr_TImpl();
		return expr_T;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Unary createUnary()
  {
		UnaryImpl unary = new UnaryImpl();
		return unary;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Binary createBinary()
  {
		BinaryImpl binary = new BinaryImpl();
		return binary;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Neg createNeg()
  {
		NegImpl neg = new NegImpl();
		return neg;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Var createVar()
  {
		VarImpl var = new VarImpl();
		return var;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Input createInput()
  {
		InputImpl input = new InputImpl();
		return input;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Target createTarget()
  {
		TargetImpl target = new TargetImpl();
		return target;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public WHILEPackage getWHILEPackage()
  {
		return (WHILEPackage)getEPackage();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
  @Deprecated
  public static WHILEPackage getPackage()
  {
		return WHILEPackage.eINSTANCE;
	}

} //WHILEFactoryImpl
