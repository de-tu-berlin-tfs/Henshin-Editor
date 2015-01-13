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

import TGG_correspondence.TGG_correspondencePackage;
import lu.uni.snt.whileDSL.wHILE.Binary;
import lu.uni.snt.whileDSL.wHILE.Comment;
import lu.uni.snt.whileDSL.wHILE.Expr;
import lu.uni.snt.whileDSL.wHILE.Expr_T;
import lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem;
import lu.uni.snt.whileDSL.wHILE.Fn_Call;
import lu.uni.snt.whileDSL.wHILE.Fn_Def;
import lu.uni.snt.whileDSL.wHILE.Input;
import lu.uni.snt.whileDSL.wHILE.Neg;
import lu.uni.snt.whileDSL.wHILE.Target;
import lu.uni.snt.whileDSL.wHILE.Unary;
import lu.uni.snt.whileDSL.wHILE.Var;
import lu.uni.snt.whileDSL.wHILE.Var_Def;
import lu.uni.snt.whileDSL.wHILE.WHILEFactory;
import lu.uni.snt.whileDSL.wHILE.WHILEPackage;
import lu.uni.snt.whileDSL.wHILE.WProgram;
import lu.uni.snt.whileDSL.wHILE.While;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WHILEPackageImpl extends EPackageImpl implements WHILEPackage
{
  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass wProgramEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass fgmnt_LST_ElemEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass whileEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass var_DefEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass fn_CallEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass fn_DefEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass commentEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass exprEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass expr_TEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass unaryEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass binaryEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass negEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass varEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass inputEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass targetEClass = null;

  /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
  private WHILEPackageImpl()
  {
		super(eNS_URI, WHILEFactory.eINSTANCE);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private static boolean isInited = false;

  /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link WHILEPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
  public static WHILEPackage init()
  {
		if (isInited) return (WHILEPackage)EPackage.Registry.INSTANCE.getEPackage(WHILEPackage.eNS_URI);

		// Obtain or create and register package
		WHILEPackageImpl theWHILEPackage = (WHILEPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof WHILEPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new WHILEPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TGG_correspondencePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theWHILEPackage.createPackageContents();

		// Initialize created meta-data
		theWHILEPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theWHILEPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(WHILEPackage.eNS_URI, theWHILEPackage);
		return theWHILEPackage;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getWProgram()
  {
		return wProgramEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getWProgram_Fst()
  {
		return (EReference)wProgramEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getFgmnt_LST_Elem()
  {
		return fgmnt_LST_ElemEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getFgmnt_LST_Elem_Next()
  {
		return (EReference)fgmnt_LST_ElemEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getWhile()
  {
		return whileEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getWhile_Expr()
  {
		return (EReference)whileEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getWhile_Fgmnt()
  {
		return (EReference)whileEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getVar_Def()
  {
		return var_DefEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getVar_Def_Left()
  {
		return (EReference)var_DefEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getVar_Def_Right()
  {
		return (EReference)var_DefEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getFn_Call()
  {
		return fn_CallEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getFn_Call_NameF()
  {
		return (EAttribute)fn_CallEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getFn_Def()
  {
		return fn_DefEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getFn_Def_NameF()
  {
		return (EAttribute)fn_DefEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getFn_Def_Body()
  {
		return (EReference)fn_DefEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getComment()
  {
		return commentEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getComment_Comment()
  {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getExpr()
  {
		return exprEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getExpr_Type()
  {
		return (EReference)exprEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getExpr_T()
  {
		return expr_TEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getUnary()
  {
		return unaryEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getBinary()
  {
		return binaryEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getBinary_Fst()
  {
		return (EReference)binaryEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getBinary_Operator()
  {
		return (EAttribute)binaryEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getBinary_Snd()
  {
		return (EReference)binaryEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getNeg()
  {
		return negEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getNeg_Expr()
  {
		return (EReference)negEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getVar()
  {
		return varEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getVar_Label()
  {
		return (EAttribute)varEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getInput()
  {
		return inputEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getTarget()
  {
		return targetEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public WHILEFactory getWHILEFactory()
  {
		return (WHILEFactory)getEFactoryInstance();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private boolean isCreated = false;

  /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void createPackageContents()
  {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		wProgramEClass = createEClass(WPROGRAM);
		createEReference(wProgramEClass, WPROGRAM__FST);

		fgmnt_LST_ElemEClass = createEClass(FGMNT_LST_ELEM);
		createEReference(fgmnt_LST_ElemEClass, FGMNT_LST_ELEM__NEXT);

		whileEClass = createEClass(WHILE);
		createEReference(whileEClass, WHILE__EXPR);
		createEReference(whileEClass, WHILE__FGMNT);

		var_DefEClass = createEClass(VAR_DEF);
		createEReference(var_DefEClass, VAR_DEF__LEFT);
		createEReference(var_DefEClass, VAR_DEF__RIGHT);

		fn_CallEClass = createEClass(FN_CALL);
		createEAttribute(fn_CallEClass, FN_CALL__NAME_F);

		fn_DefEClass = createEClass(FN_DEF);
		createEAttribute(fn_DefEClass, FN_DEF__NAME_F);
		createEReference(fn_DefEClass, FN_DEF__BODY);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__COMMENT);

		exprEClass = createEClass(EXPR);
		createEReference(exprEClass, EXPR__TYPE);

		expr_TEClass = createEClass(EXPR_T);

		unaryEClass = createEClass(UNARY);

		binaryEClass = createEClass(BINARY);
		createEReference(binaryEClass, BINARY__FST);
		createEAttribute(binaryEClass, BINARY__OPERATOR);
		createEReference(binaryEClass, BINARY__SND);

		negEClass = createEClass(NEG);
		createEReference(negEClass, NEG__EXPR);

		varEClass = createEClass(VAR);
		createEAttribute(varEClass, VAR__LABEL);

		inputEClass = createEClass(INPUT);

		targetEClass = createEClass(TARGET);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private boolean isInitialized = false;

  /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void initializePackageContents()
  {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TGG_correspondencePackage theTGG_correspondencePackage = (TGG_correspondencePackage)EPackage.Registry.INSTANCE.getEPackage(TGG_correspondencePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		wProgramEClass.getESuperTypes().add(this.getTarget());
		fgmnt_LST_ElemEClass.getESuperTypes().add(this.getTarget());
		whileEClass.getESuperTypes().add(this.getFgmnt_LST_Elem());
		var_DefEClass.getESuperTypes().add(this.getFgmnt_LST_Elem());
		fn_CallEClass.getESuperTypes().add(this.getFgmnt_LST_Elem());
		fn_DefEClass.getESuperTypes().add(this.getFgmnt_LST_Elem());
		commentEClass.getESuperTypes().add(this.getFgmnt_LST_Elem());
		exprEClass.getESuperTypes().add(this.getTarget());
		expr_TEClass.getESuperTypes().add(this.getTarget());
		unaryEClass.getESuperTypes().add(this.getExpr_T());
		binaryEClass.getESuperTypes().add(this.getExpr_T());
		negEClass.getESuperTypes().add(this.getUnary());
		varEClass.getESuperTypes().add(this.getUnary());
		inputEClass.getESuperTypes().add(this.getUnary());
		targetEClass.getESuperTypes().add(theTGG_correspondencePackage.getAbstractTarget());

		// Initialize classes and features; add operations and parameters
		initEClass(wProgramEClass, WProgram.class, "WProgram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWProgram_Fst(), this.getFgmnt_LST_Elem(), null, "fst", null, 0, 1, WProgram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fgmnt_LST_ElemEClass, Fgmnt_LST_Elem.class, "Fgmnt_LST_Elem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFgmnt_LST_Elem_Next(), this.getFgmnt_LST_Elem(), null, "next", null, 0, 1, Fgmnt_LST_Elem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(whileEClass, While.class, "While", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWhile_Expr(), this.getExpr(), null, "expr", null, 0, 1, While.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWhile_Fgmnt(), this.getFgmnt_LST_Elem(), null, "fgmnt", null, 0, 1, While.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(var_DefEClass, Var_Def.class, "Var_Def", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVar_Def_Left(), this.getVar(), null, "left", null, 0, 1, Var_Def.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Def_Right(), this.getExpr(), null, "right", null, 0, 1, Var_Def.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fn_CallEClass, Fn_Call.class, "Fn_Call", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFn_Call_NameF(), ecorePackage.getEString(), "nameF", null, 0, 1, Fn_Call.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fn_DefEClass, Fn_Def.class, "Fn_Def", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFn_Def_NameF(), ecorePackage.getEString(), "nameF", null, 0, 1, Fn_Def.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFn_Def_Body(), this.getFgmnt_LST_Elem(), null, "body", null, 0, 1, Fn_Def.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComment_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprEClass, Expr.class, "Expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpr_Type(), this.getExpr_T(), null, "type", null, 0, 1, Expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expr_TEClass, Expr_T.class, "Expr_T", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(unaryEClass, Unary.class, "Unary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(binaryEClass, Binary.class, "Binary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinary_Fst(), this.getExpr(), null, "fst", null, 0, 1, Binary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinary_Operator(), ecorePackage.getEString(), "operator", null, 0, 1, Binary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinary_Snd(), this.getExpr(), null, "snd", null, 0, 1, Binary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(negEClass, Neg.class, "Neg", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNeg_Expr(), this.getExpr(), null, "expr", null, 0, 1, Neg.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(varEClass, Var.class, "Var", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVar_Label(), ecorePackage.getEString(), "label", null, 0, 1, Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inputEClass, Input.class, "Input", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(targetEClass, Target.class, "Target", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //WHILEPackageImpl
