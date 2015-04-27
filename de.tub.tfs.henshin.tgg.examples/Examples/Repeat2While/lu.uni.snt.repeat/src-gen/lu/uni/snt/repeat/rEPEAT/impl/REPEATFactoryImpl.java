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
package lu.uni.snt.repeat.rEPEAT.impl;

import lu.uni.snt.repeat.rEPEAT.*;

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
public class REPEATFactoryImpl extends EFactoryImpl implements REPEATFactory
{
  /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static REPEATFactory init()
  {
		try {
			REPEATFactory theREPEATFactory = (REPEATFactory)EPackage.Registry.INSTANCE.getEFactory(REPEATPackage.eNS_URI);
			if (theREPEATFactory != null) {
				return theREPEATFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new REPEATFactoryImpl();
	}

  /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public REPEATFactoryImpl()
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
			case REPEATPackage.RPROGRAM: return createRProgram();
			case REPEATPackage.STMNT_LST_ELEM: return createStmnt_LST_Elem();
			case REPEATPackage.REPEAT: return createRepeat();
			case REPEATPackage.ASG: return createAsg();
			case REPEATPackage.READ: return createRead();
			case REPEATPackage.COMMENT: return createComment();
			case REPEATPackage.LOG_EXPR: return createLog_Expr();
			case REPEATPackage.LOG_EXPR_T: return createLog_Expr_T();
			case REPEATPackage.LOG_EXPR_UNARY: return createLog_Expr_Unary();
			case REPEATPackage.LOG_EXPR_BINARY: return createLog_Expr_Binary();
			case REPEATPackage.LOG_NEG: return createLog_Neg();
			case REPEATPackage.SYM: return createSym();
			case REPEATPackage.SOURCE: return createSource();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public RProgram createRProgram()
  {
		RProgramImpl rProgram = new RProgramImpl();
		return rProgram;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Stmnt_LST_Elem createStmnt_LST_Elem()
  {
		Stmnt_LST_ElemImpl stmnt_LST_Elem = new Stmnt_LST_ElemImpl();
		return stmnt_LST_Elem;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Repeat createRepeat()
  {
		RepeatImpl repeat = new RepeatImpl();
		return repeat;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Asg createAsg()
  {
		AsgImpl asg = new AsgImpl();
		return asg;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Read createRead()
  {
		ReadImpl read = new ReadImpl();
		return read;
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
  public Log_Expr createLog_Expr()
  {
		Log_ExprImpl log_Expr = new Log_ExprImpl();
		return log_Expr;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Expr_T createLog_Expr_T()
  {
		Log_Expr_TImpl log_Expr_T = new Log_Expr_TImpl();
		return log_Expr_T;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Expr_Unary createLog_Expr_Unary()
  {
		Log_Expr_UnaryImpl log_Expr_Unary = new Log_Expr_UnaryImpl();
		return log_Expr_Unary;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Expr_Binary createLog_Expr_Binary()
  {
		Log_Expr_BinaryImpl log_Expr_Binary = new Log_Expr_BinaryImpl();
		return log_Expr_Binary;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Neg createLog_Neg()
  {
		Log_NegImpl log_Neg = new Log_NegImpl();
		return log_Neg;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Sym createSym()
  {
		SymImpl sym = new SymImpl();
		return sym;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Source createSource()
  {
		SourceImpl source = new SourceImpl();
		return source;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public REPEATPackage getREPEATPackage()
  {
		return (REPEATPackage)getEPackage();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
  @Deprecated
  public static REPEATPackage getPackage()
  {
		return REPEATPackage.eINSTANCE;
	}

} //REPEATFactoryImpl
