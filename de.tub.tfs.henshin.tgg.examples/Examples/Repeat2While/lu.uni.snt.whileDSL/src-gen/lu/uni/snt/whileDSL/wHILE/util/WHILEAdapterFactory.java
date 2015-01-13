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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage
 * @generated
 */
public class WHILEAdapterFactory extends AdapterFactoryImpl
{
  /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static WHILEPackage modelPackage;

  /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public WHILEAdapterFactory()
  {
		if (modelPackage == null) {
			modelPackage = WHILEPackage.eINSTANCE;
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
  protected WHILESwitch<Adapter> modelSwitch =
    new WHILESwitch<Adapter>() {
			@Override
			public Adapter caseWProgram(WProgram object) {
				return createWProgramAdapter();
			}
			@Override
			public Adapter caseFgmnt_LST_Elem(Fgmnt_LST_Elem object) {
				return createFgmnt_LST_ElemAdapter();
			}
			@Override
			public Adapter caseWhile(While object) {
				return createWhileAdapter();
			}
			@Override
			public Adapter caseVar_Def(Var_Def object) {
				return createVar_DefAdapter();
			}
			@Override
			public Adapter caseFn_Call(Fn_Call object) {
				return createFn_CallAdapter();
			}
			@Override
			public Adapter caseFn_Def(Fn_Def object) {
				return createFn_DefAdapter();
			}
			@Override
			public Adapter caseComment(Comment object) {
				return createCommentAdapter();
			}
			@Override
			public Adapter caseExpr(Expr object) {
				return createExprAdapter();
			}
			@Override
			public Adapter caseExpr_T(Expr_T object) {
				return createExpr_TAdapter();
			}
			@Override
			public Adapter caseUnary(Unary object) {
				return createUnaryAdapter();
			}
			@Override
			public Adapter caseBinary(Binary object) {
				return createBinaryAdapter();
			}
			@Override
			public Adapter caseNeg(Neg object) {
				return createNegAdapter();
			}
			@Override
			public Adapter caseVar(Var object) {
				return createVarAdapter();
			}
			@Override
			public Adapter caseInput(Input object) {
				return createInputAdapter();
			}
			@Override
			public Adapter caseTarget(Target object) {
				return createTargetAdapter();
			}
			@Override
			public Adapter caseAbstractTarget(AbstractTarget object) {
				return createAbstractTargetAdapter();
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
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.WProgram <em>WProgram</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.WProgram
	 * @generated
	 */
  public Adapter createWProgramAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem <em>Fgmnt LST Elem</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem
	 * @generated
	 */
  public Adapter createFgmnt_LST_ElemAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.While <em>While</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.While
	 * @generated
	 */
  public Adapter createWhileAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Var_Def <em>Var Def</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Var_Def
	 * @generated
	 */
  public Adapter createVar_DefAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Fn_Call <em>Fn Call</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Call
	 * @generated
	 */
  public Adapter createFn_CallAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Fn_Def <em>Fn Def</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Def
	 * @generated
	 */
  public Adapter createFn_DefAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Comment
	 * @generated
	 */
  public Adapter createCommentAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Expr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Expr
	 * @generated
	 */
  public Adapter createExprAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Expr_T <em>Expr T</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Expr_T
	 * @generated
	 */
  public Adapter createExpr_TAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Unary <em>Unary</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Unary
	 * @generated
	 */
  public Adapter createUnaryAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Binary <em>Binary</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Binary
	 * @generated
	 */
  public Adapter createBinaryAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Neg <em>Neg</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Neg
	 * @generated
	 */
  public Adapter createNegAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Var <em>Var</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Var
	 * @generated
	 */
  public Adapter createVarAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Input <em>Input</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Input
	 * @generated
	 */
  public Adapter createInputAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link lu.uni.snt.whileDSL.wHILE.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see lu.uni.snt.whileDSL.wHILE.Target
	 * @generated
	 */
  public Adapter createTargetAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link TGG_correspondence.AbstractTarget <em>Abstract Target</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see TGG_correspondence.AbstractTarget
	 * @generated
	 */
	public Adapter createAbstractTargetAdapter() {
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

} //WHILEAdapterFactory
