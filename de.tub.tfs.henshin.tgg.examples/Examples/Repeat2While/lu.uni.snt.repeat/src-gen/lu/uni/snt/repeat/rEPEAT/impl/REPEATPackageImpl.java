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

import TGG_correspondence.TGG_correspondencePackage;
import lu.uni.snt.repeat.rEPEAT.Asg;
import lu.uni.snt.repeat.rEPEAT.Comment;
import lu.uni.snt.repeat.rEPEAT.Log_Expr;
import lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary;
import lu.uni.snt.repeat.rEPEAT.Log_Expr_T;
import lu.uni.snt.repeat.rEPEAT.Log_Expr_Unary;
import lu.uni.snt.repeat.rEPEAT.Log_Neg;
import lu.uni.snt.repeat.rEPEAT.REPEATFactory;
import lu.uni.snt.repeat.rEPEAT.REPEATPackage;
import lu.uni.snt.repeat.rEPEAT.RProgram;
import lu.uni.snt.repeat.rEPEAT.Read;
import lu.uni.snt.repeat.rEPEAT.Repeat;
import lu.uni.snt.repeat.rEPEAT.Source;
import lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem;
import lu.uni.snt.repeat.rEPEAT.Sym;

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
public class REPEATPackageImpl extends EPackageImpl implements REPEATPackage
{
  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass rProgramEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass stmnt_LST_ElemEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass repeatEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass asgEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass readEClass = null;

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
  private EClass log_ExprEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass log_Expr_TEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass log_Expr_UnaryEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass log_Expr_BinaryEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass log_NegEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass symEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass sourceEClass = null;

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
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
  private REPEATPackageImpl()
  {
		super(eNS_URI, REPEATFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link REPEATPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
  public static REPEATPackage init()
  {
		if (isInited) return (REPEATPackage)EPackage.Registry.INSTANCE.getEPackage(REPEATPackage.eNS_URI);

		// Obtain or create and register package
		REPEATPackageImpl theREPEATPackage = (REPEATPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof REPEATPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new REPEATPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TGG_correspondencePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theREPEATPackage.createPackageContents();

		// Initialize created meta-data
		theREPEATPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theREPEATPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(REPEATPackage.eNS_URI, theREPEATPackage);
		return theREPEATPackage;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getRProgram()
  {
		return rProgramEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getRProgram_Fst()
  {
		return (EReference)rProgramEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getStmnt_LST_Elem()
  {
		return stmnt_LST_ElemEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getStmnt_LST_Elem_Next()
  {
		return (EReference)stmnt_LST_ElemEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getRepeat()
  {
		return repeatEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getRepeat_Stmnt()
  {
		return (EReference)repeatEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getRepeat_Expr()
  {
		return (EReference)repeatEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getAsg()
  {
		return asgEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getAsg_Left()
  {
		return (EReference)asgEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getAsg_Right()
  {
		return (EReference)asgEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getRead()
  {
		return readEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getRead_Param()
  {
		return (EReference)readEClass.getEStructuralFeatures().get(0);
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
  public EClass getLog_Expr()
  {
		return log_ExprEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getLog_Expr_Type()
  {
		return (EReference)log_ExprEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getLog_Expr_T()
  {
		return log_Expr_TEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getLog_Expr_Unary()
  {
		return log_Expr_UnaryEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getLog_Expr_Binary()
  {
		return log_Expr_BinaryEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getLog_Expr_Binary_Fst()
  {
		return (EReference)log_Expr_BinaryEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getLog_Expr_Binary_Operator()
  {
		return (EAttribute)log_Expr_BinaryEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getLog_Expr_Binary_Snd()
  {
		return (EReference)log_Expr_BinaryEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getLog_Neg()
  {
		return log_NegEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getLog_Neg_Expr()
  {
		return (EReference)log_NegEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getSym()
  {
		return symEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getSym_Sym()
  {
		return (EAttribute)symEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getSource()
  {
		return sourceEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public REPEATFactory getREPEATFactory()
  {
		return (REPEATFactory)getEFactoryInstance();
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
		rProgramEClass = createEClass(RPROGRAM);
		createEReference(rProgramEClass, RPROGRAM__FST);

		stmnt_LST_ElemEClass = createEClass(STMNT_LST_ELEM);
		createEReference(stmnt_LST_ElemEClass, STMNT_LST_ELEM__NEXT);

		repeatEClass = createEClass(REPEAT);
		createEReference(repeatEClass, REPEAT__STMNT);
		createEReference(repeatEClass, REPEAT__EXPR);

		asgEClass = createEClass(ASG);
		createEReference(asgEClass, ASG__LEFT);
		createEReference(asgEClass, ASG__RIGHT);

		readEClass = createEClass(READ);
		createEReference(readEClass, READ__PARAM);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__COMMENT);

		log_ExprEClass = createEClass(LOG_EXPR);
		createEReference(log_ExprEClass, LOG_EXPR__TYPE);

		log_Expr_TEClass = createEClass(LOG_EXPR_T);

		log_Expr_UnaryEClass = createEClass(LOG_EXPR_UNARY);

		log_Expr_BinaryEClass = createEClass(LOG_EXPR_BINARY);
		createEReference(log_Expr_BinaryEClass, LOG_EXPR_BINARY__FST);
		createEAttribute(log_Expr_BinaryEClass, LOG_EXPR_BINARY__OPERATOR);
		createEReference(log_Expr_BinaryEClass, LOG_EXPR_BINARY__SND);

		log_NegEClass = createEClass(LOG_NEG);
		createEReference(log_NegEClass, LOG_NEG__EXPR);

		symEClass = createEClass(SYM);
		createEAttribute(symEClass, SYM__SYM);

		sourceEClass = createEClass(SOURCE);
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
		rProgramEClass.getESuperTypes().add(this.getSource());
		stmnt_LST_ElemEClass.getESuperTypes().add(this.getSource());
		repeatEClass.getESuperTypes().add(this.getStmnt_LST_Elem());
		asgEClass.getESuperTypes().add(this.getStmnt_LST_Elem());
		readEClass.getESuperTypes().add(this.getStmnt_LST_Elem());
		commentEClass.getESuperTypes().add(this.getStmnt_LST_Elem());
		log_ExprEClass.getESuperTypes().add(this.getSource());
		log_Expr_TEClass.getESuperTypes().add(this.getSource());
		log_Expr_UnaryEClass.getESuperTypes().add(this.getLog_Expr_T());
		log_Expr_BinaryEClass.getESuperTypes().add(this.getLog_Expr_T());
		log_NegEClass.getESuperTypes().add(this.getLog_Expr_Unary());
		symEClass.getESuperTypes().add(this.getLog_Expr_Unary());
		sourceEClass.getESuperTypes().add(theTGG_correspondencePackage.getAbstractSource());

		// Initialize classes and features; add operations and parameters
		initEClass(rProgramEClass, RProgram.class, "RProgram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRProgram_Fst(), this.getStmnt_LST_Elem(), null, "fst", null, 0, 1, RProgram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stmnt_LST_ElemEClass, Stmnt_LST_Elem.class, "Stmnt_LST_Elem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStmnt_LST_Elem_Next(), this.getStmnt_LST_Elem(), null, "next", null, 0, 1, Stmnt_LST_Elem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repeatEClass, Repeat.class, "Repeat", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepeat_Stmnt(), this.getStmnt_LST_Elem(), null, "stmnt", null, 0, 1, Repeat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRepeat_Expr(), this.getLog_Expr(), null, "expr", null, 0, 1, Repeat.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(asgEClass, Asg.class, "Asg", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAsg_Left(), this.getSym(), null, "left", null, 0, 1, Asg.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAsg_Right(), this.getSym(), null, "right", null, 0, 1, Asg.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(readEClass, Read.class, "Read", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRead_Param(), this.getSym(), null, "param", null, 0, 1, Read.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComment_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(log_ExprEClass, Log_Expr.class, "Log_Expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLog_Expr_Type(), this.getLog_Expr_T(), null, "type", null, 0, 1, Log_Expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(log_Expr_TEClass, Log_Expr_T.class, "Log_Expr_T", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(log_Expr_UnaryEClass, Log_Expr_Unary.class, "Log_Expr_Unary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(log_Expr_BinaryEClass, Log_Expr_Binary.class, "Log_Expr_Binary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLog_Expr_Binary_Fst(), this.getLog_Expr(), null, "fst", null, 0, 1, Log_Expr_Binary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLog_Expr_Binary_Operator(), ecorePackage.getEString(), "operator", null, 0, 1, Log_Expr_Binary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLog_Expr_Binary_Snd(), this.getLog_Expr(), null, "snd", null, 0, 1, Log_Expr_Binary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(log_NegEClass, Log_Neg.class, "Log_Neg", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLog_Neg_Expr(), this.getLog_Expr(), null, "expr", null, 0, 1, Log_Neg.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symEClass, Sym.class, "Sym", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSym_Sym(), ecorePackage.getEString(), "sym", null, 0, 1, Sym.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourceEClass, Source.class, "Source", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //REPEATPackageImpl
