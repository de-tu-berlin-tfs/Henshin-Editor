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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage
 * @generated
 */
public class REPEATAdapterFactory extends AdapterFactoryImpl
{
  /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static REPEATPackage modelPackage;

  /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public REPEATAdapterFactory()
  {
		if (modelPackage == null) {
			modelPackage = REPEATPackage.eINSTANCE;
		}
	}

  /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
  @Override
  public boolean isFactoryForType(Object object)
  {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

  /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected REPEATSwitch<Adapter> modelSwitch =
    new REPEATSwitch<Adapter>() {
			@Override
			public Adapter caseRProgram(RProgram object) {
				return createRProgramAdapter();
			}
			@Override
			public Adapter caseStmnt_LST_Elem(Stmnt_LST_Elem object) {
				return createStmnt_LST_ElemAdapter();
			}
			@Override
			public Adapter caseRepeat(Repeat object) {
				return createRepeatAdapter();
			}
			@Override
			public Adapter caseAsg(Asg object) {
				return createAsgAdapter();
			}
			@Override
			public Adapter caseRead(Read object) {
				return createReadAdapter();
			}
			@Override
			public Adapter caseComment(Comment object) {
				return createCommentAdapter();
			}
			@Override
			public Adapter caseLog_Expr(Log_Expr object) {
				return createLog_ExprAdapter();
			}
			@Override
			public Adapter caseLog_Expr_T(Log_Expr_T object) {
				return createLog_Expr_TAdapter();
			}
			@Override
			public Adapter caseLog_Expr_Unary(Log_Expr_Unary object) {
				return createLog_Expr_UnaryAdapter();
			}
			@Override
			public Adapter caseLog_Expr_Binary(Log_Expr_Binary object) {
				return createLog_Expr_BinaryAdapter();
			}
			@Override
			public Adapter caseLog_Neg(Log_Neg object) {
				return createLog_NegAdapter();
			}
			@Override
			public Adapter caseSym(Sym object) {
				return createSymAdapter();
			}
			@Override
			public Adapter caseSource(Source object) {
				return createSourceAdapter();
			}
			@Override
			public Adapter caseAbstractSource(AbstractSource object) {
				return createAbstractSourceAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

  /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
  @Override
  public Adapter createAdapter(Notifier target)
  {
		return modelSwitch.doSwitch((EObject)target);
	}


  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.RProgram <em>RProgram</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.RProgram
	 * @generated
	 */
  public Adapter createRProgramAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem <em>Stmnt LST Elem</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem
	 * @generated
	 */
  public Adapter createStmnt_LST_ElemAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Repeat <em>Repeat</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Repeat
	 * @generated
	 */
  public Adapter createRepeatAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Asg <em>Asg</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Asg
	 * @generated
	 */
  public Adapter createAsgAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Read <em>Read</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Read
	 * @generated
	 */
  public Adapter createReadAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Comment
	 * @generated
	 */
  public Adapter createCommentAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr <em>Log Expr</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr
	 * @generated
	 */
  public Adapter createLog_ExprAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_T <em>Log Expr T</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_T
	 * @generated
	 */
  public Adapter createLog_Expr_TAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Unary <em>Log Expr Unary</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Unary
	 * @generated
	 */
  public Adapter createLog_Expr_UnaryAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary <em>Log Expr Binary</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary
	 * @generated
	 */
  public Adapter createLog_Expr_BinaryAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Log_Neg <em>Log Neg</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Neg
	 * @generated
	 */
  public Adapter createLog_NegAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Sym <em>Sym</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Sym
	 * @generated
	 */
  public Adapter createSymAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.repeat.rEPEAT.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.repeat.rEPEAT.Source
	 * @generated
	 */
  public Adapter createSourceAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link TGG_correspondence.AbstractSource <em>Abstract Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see TGG_correspondence.AbstractSource
	 * @generated
	 */
	public Adapter createAbstractSourceAdapter() {
		return null;
	}

		/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
  public Adapter createEObjectAdapter()
  {
		return null;
	}

} //REPEATAdapterFactory
