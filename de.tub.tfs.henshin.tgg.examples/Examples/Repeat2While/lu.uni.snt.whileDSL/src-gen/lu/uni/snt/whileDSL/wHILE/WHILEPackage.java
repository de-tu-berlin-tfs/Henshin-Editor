/**
 */
package lu.uni.snt.whileDSL.wHILE;

import TGG_correspondence.TGG_correspondencePackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see lu.uni.snt.whileDSL.wHILE.WHILEFactory
 * @model kind="package"
 * @generated
 */
public interface WHILEPackage extends EPackage
{
  /**
	 * The package name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNAME = "wHILE";

  /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNS_URI = "http://www.uni.lu/snt/whileDSL/WHILE";

  /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNS_PREFIX = "wHILE";

  /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  WHILEPackage eINSTANCE = lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl.init();

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.TargetImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getTarget()
	 * @generated
	 */
  int TARGET = 14;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__T2C = TGG_correspondencePackage.ABSTRACT_TARGET__T2C;

		/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int TARGET_FEATURE_COUNT = TGG_correspondencePackage.ABSTRACT_TARGET_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.WProgramImpl <em>WProgram</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WProgramImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getWProgram()
	 * @generated
	 */
  int WPROGRAM = 0;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WPROGRAM__T2C = TARGET__T2C;

		/**
	 * The feature id for the '<em><b>Fst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int WPROGRAM__FST = TARGET_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>WProgram</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int WPROGRAM_FEATURE_COUNT = TARGET_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Fgmnt_LST_ElemImpl <em>Fgmnt LST Elem</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.Fgmnt_LST_ElemImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getFgmnt_LST_Elem()
	 * @generated
	 */
  int FGMNT_LST_ELEM = 1;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FGMNT_LST_ELEM__T2C = TARGET__T2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FGMNT_LST_ELEM__NEXT = TARGET_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Fgmnt LST Elem</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FGMNT_LST_ELEM_FEATURE_COUNT = TARGET_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.WhileImpl <em>While</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WhileImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getWhile()
	 * @generated
	 */
  int WHILE = 2;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE__T2C = FGMNT_LST_ELEM__T2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int WHILE__NEXT = FGMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int WHILE__EXPR = FGMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Fgmnt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int WHILE__FGMNT = FGMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>While</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int WHILE_FEATURE_COUNT = FGMNT_LST_ELEM_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Var_DefImpl <em>Var Def</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.Var_DefImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getVar_Def()
	 * @generated
	 */
  int VAR_DEF = 3;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DEF__T2C = FGMNT_LST_ELEM__T2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int VAR_DEF__NEXT = FGMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int VAR_DEF__LEFT = FGMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int VAR_DEF__RIGHT = FGMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>Var Def</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int VAR_DEF_FEATURE_COUNT = FGMNT_LST_ELEM_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Fn_CallImpl <em>Fn Call</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.Fn_CallImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getFn_Call()
	 * @generated
	 */
  int FN_CALL = 4;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FN_CALL__T2C = FGMNT_LST_ELEM__T2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_CALL__NEXT = FGMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Name F</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_CALL__NAME_F = FGMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Fn Call</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_CALL_FEATURE_COUNT = FGMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Fn_DefImpl <em>Fn Def</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.Fn_DefImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getFn_Def()
	 * @generated
	 */
  int FN_DEF = 5;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FN_DEF__T2C = FGMNT_LST_ELEM__T2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_DEF__NEXT = FGMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Name F</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_DEF__NAME_F = FGMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_DEF__BODY = FGMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>Fn Def</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int FN_DEF_FEATURE_COUNT = FGMNT_LST_ELEM_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.CommentImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getComment()
	 * @generated
	 */
  int COMMENT = 6;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__T2C = FGMNT_LST_ELEM__T2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int COMMENT__NEXT = FGMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int COMMENT__COMMENT = FGMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int COMMENT_FEATURE_COUNT = FGMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.ExprImpl <em>Expr</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.ExprImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getExpr()
	 * @generated
	 */
  int EXPR = 7;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR__T2C = TARGET__T2C;

		/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int EXPR__TYPE = TARGET_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Expr</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int EXPR_FEATURE_COUNT = TARGET_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Expr_TImpl <em>Expr T</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.Expr_TImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getExpr_T()
	 * @generated
	 */
  int EXPR_T = 8;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_T__T2C = TARGET__T2C;

		/**
	 * The number of structural features of the '<em>Expr T</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int EXPR_T_FEATURE_COUNT = TARGET_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.UnaryImpl <em>Unary</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.UnaryImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getUnary()
	 * @generated
	 */
  int UNARY = 9;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY__T2C = EXPR_T__T2C;

		/**
	 * The number of structural features of the '<em>Unary</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int UNARY_FEATURE_COUNT = EXPR_T_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.BinaryImpl <em>Binary</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.BinaryImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getBinary()
	 * @generated
	 */
  int BINARY = 10;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY__T2C = EXPR_T__T2C;

		/**
	 * The feature id for the '<em><b>Fst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int BINARY__FST = EXPR_T_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int BINARY__OPERATOR = EXPR_T_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Snd</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int BINARY__SND = EXPR_T_FEATURE_COUNT + 2;

  /**
	 * The number of structural features of the '<em>Binary</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int BINARY_FEATURE_COUNT = EXPR_T_FEATURE_COUNT + 3;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.NegImpl <em>Neg</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.NegImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getNeg()
	 * @generated
	 */
  int NEG = 11;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEG__T2C = UNARY__T2C;

		/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int NEG__EXPR = UNARY_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Neg</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int NEG_FEATURE_COUNT = UNARY_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.VarImpl <em>Var</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.VarImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getVar()
	 * @generated
	 */
  int VAR = 12;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR__T2C = UNARY__T2C;

		/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int VAR__LABEL = UNARY_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Var</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int VAR_FEATURE_COUNT = UNARY_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.whileDSL.wHILE.impl.InputImpl <em>Input</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.whileDSL.wHILE.impl.InputImpl
	 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getInput()
	 * @generated
	 */
  int INPUT = 13;

  /**
	 * The feature id for the '<em><b>T2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT__T2C = UNARY__T2C;

		/**
	 * The number of structural features of the '<em>Input</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int INPUT_FEATURE_COUNT = UNARY_FEATURE_COUNT + 0;


  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.WProgram <em>WProgram</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>WProgram</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.WProgram
	 * @generated
	 */
  EClass getWProgram();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.WProgram#getFst <em>Fst</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fst</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.WProgram#getFst()
	 * @see #getWProgram()
	 * @generated
	 */
  EReference getWProgram_Fst();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem <em>Fgmnt LST Elem</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fgmnt LST Elem</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem
	 * @generated
	 */
  EClass getFgmnt_LST_Elem();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Next</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem#getNext()
	 * @see #getFgmnt_LST_Elem()
	 * @generated
	 */
  EReference getFgmnt_LST_Elem_Next();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.While <em>While</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>While</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.While
	 * @generated
	 */
  EClass getWhile();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.While#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.While#getExpr()
	 * @see #getWhile()
	 * @generated
	 */
  EReference getWhile_Expr();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.While#getFgmnt <em>Fgmnt</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fgmnt</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.While#getFgmnt()
	 * @see #getWhile()
	 * @generated
	 */
  EReference getWhile_Fgmnt();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Var_Def <em>Var Def</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var Def</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Var_Def
	 * @generated
	 */
  EClass getVar_Def();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Var_Def#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Var_Def#getLeft()
	 * @see #getVar_Def()
	 * @generated
	 */
  EReference getVar_Def_Left();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Var_Def#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Var_Def#getRight()
	 * @see #getVar_Def()
	 * @generated
	 */
  EReference getVar_Def_Right();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Fn_Call <em>Fn Call</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fn Call</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Call
	 * @generated
	 */
  EClass getFn_Call();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.whileDSL.wHILE.Fn_Call#getNameF <em>Name F</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name F</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Call#getNameF()
	 * @see #getFn_Call()
	 * @generated
	 */
  EAttribute getFn_Call_NameF();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Fn_Def <em>Fn Def</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fn Def</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Def
	 * @generated
	 */
  EClass getFn_Def();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.whileDSL.wHILE.Fn_Def#getNameF <em>Name F</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name F</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Def#getNameF()
	 * @see #getFn_Def()
	 * @generated
	 */
  EAttribute getFn_Def_NameF();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Fn_Def#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Fn_Def#getBody()
	 * @see #getFn_Def()
	 * @generated
	 */
  EReference getFn_Def_Body();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Comment
	 * @generated
	 */
  EClass getComment();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.whileDSL.wHILE.Comment#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Comment#getComment()
	 * @see #getComment()
	 * @generated
	 */
  EAttribute getComment_Comment();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Expr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Expr
	 * @generated
	 */
  EClass getExpr();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Expr#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Expr#getType()
	 * @see #getExpr()
	 * @generated
	 */
  EReference getExpr_Type();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Expr_T <em>Expr T</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr T</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Expr_T
	 * @generated
	 */
  EClass getExpr_T();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Unary <em>Unary</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Unary
	 * @generated
	 */
  EClass getUnary();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Binary <em>Binary</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Binary
	 * @generated
	 */
  EClass getBinary();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Binary#getFst <em>Fst</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fst</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Binary#getFst()
	 * @see #getBinary()
	 * @generated
	 */
  EReference getBinary_Fst();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.whileDSL.wHILE.Binary#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Binary#getOperator()
	 * @see #getBinary()
	 * @generated
	 */
  EAttribute getBinary_Operator();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Binary#getSnd <em>Snd</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Snd</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Binary#getSnd()
	 * @see #getBinary()
	 * @generated
	 */
  EReference getBinary_Snd();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Neg <em>Neg</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Neg</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Neg
	 * @generated
	 */
  EClass getNeg();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.whileDSL.wHILE.Neg#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Neg#getExpr()
	 * @see #getNeg()
	 * @generated
	 */
  EReference getNeg_Expr();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Var <em>Var</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Var
	 * @generated
	 */
  EClass getVar();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.whileDSL.wHILE.Var#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Var#getLabel()
	 * @see #getVar()
	 * @generated
	 */
  EAttribute getVar_Label();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Input <em>Input</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Input
	 * @generated
	 */
  EClass getInput();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.whileDSL.wHILE.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see lu.uni.snt.whileDSL.wHILE.Target
	 * @generated
	 */
  EClass getTarget();

  /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
  WHILEFactory getWHILEFactory();

  /**
	 * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
	 * @generated
	 */
  interface Literals
  {
    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.WProgramImpl <em>WProgram</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WProgramImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getWProgram()
		 * @generated
		 */
    EClass WPROGRAM = eINSTANCE.getWProgram();

    /**
		 * The meta object literal for the '<em><b>Fst</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference WPROGRAM__FST = eINSTANCE.getWProgram_Fst();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Fgmnt_LST_ElemImpl <em>Fgmnt LST Elem</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.Fgmnt_LST_ElemImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getFgmnt_LST_Elem()
		 * @generated
		 */
    EClass FGMNT_LST_ELEM = eINSTANCE.getFgmnt_LST_Elem();

    /**
		 * The meta object literal for the '<em><b>Next</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference FGMNT_LST_ELEM__NEXT = eINSTANCE.getFgmnt_LST_Elem_Next();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.WhileImpl <em>While</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WhileImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getWhile()
		 * @generated
		 */
    EClass WHILE = eINSTANCE.getWhile();

    /**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference WHILE__EXPR = eINSTANCE.getWhile_Expr();

    /**
		 * The meta object literal for the '<em><b>Fgmnt</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference WHILE__FGMNT = eINSTANCE.getWhile_Fgmnt();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Var_DefImpl <em>Var Def</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.Var_DefImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getVar_Def()
		 * @generated
		 */
    EClass VAR_DEF = eINSTANCE.getVar_Def();

    /**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference VAR_DEF__LEFT = eINSTANCE.getVar_Def_Left();

    /**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference VAR_DEF__RIGHT = eINSTANCE.getVar_Def_Right();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Fn_CallImpl <em>Fn Call</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.Fn_CallImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getFn_Call()
		 * @generated
		 */
    EClass FN_CALL = eINSTANCE.getFn_Call();

    /**
		 * The meta object literal for the '<em><b>Name F</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute FN_CALL__NAME_F = eINSTANCE.getFn_Call_NameF();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Fn_DefImpl <em>Fn Def</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.Fn_DefImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getFn_Def()
		 * @generated
		 */
    EClass FN_DEF = eINSTANCE.getFn_Def();

    /**
		 * The meta object literal for the '<em><b>Name F</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute FN_DEF__NAME_F = eINSTANCE.getFn_Def_NameF();

    /**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference FN_DEF__BODY = eINSTANCE.getFn_Def_Body();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.CommentImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getComment()
		 * @generated
		 */
    EClass COMMENT = eINSTANCE.getComment();

    /**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute COMMENT__COMMENT = eINSTANCE.getComment_Comment();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.ExprImpl <em>Expr</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.ExprImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getExpr()
		 * @generated
		 */
    EClass EXPR = eINSTANCE.getExpr();

    /**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference EXPR__TYPE = eINSTANCE.getExpr_Type();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.Expr_TImpl <em>Expr T</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.Expr_TImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getExpr_T()
		 * @generated
		 */
    EClass EXPR_T = eINSTANCE.getExpr_T();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.UnaryImpl <em>Unary</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.UnaryImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getUnary()
		 * @generated
		 */
    EClass UNARY = eINSTANCE.getUnary();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.BinaryImpl <em>Binary</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.BinaryImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getBinary()
		 * @generated
		 */
    EClass BINARY = eINSTANCE.getBinary();

    /**
		 * The meta object literal for the '<em><b>Fst</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference BINARY__FST = eINSTANCE.getBinary_Fst();

    /**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute BINARY__OPERATOR = eINSTANCE.getBinary_Operator();

    /**
		 * The meta object literal for the '<em><b>Snd</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference BINARY__SND = eINSTANCE.getBinary_Snd();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.NegImpl <em>Neg</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.NegImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getNeg()
		 * @generated
		 */
    EClass NEG = eINSTANCE.getNeg();

    /**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference NEG__EXPR = eINSTANCE.getNeg_Expr();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.VarImpl <em>Var</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.VarImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getVar()
		 * @generated
		 */
    EClass VAR = eINSTANCE.getVar();

    /**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute VAR__LABEL = eINSTANCE.getVar_Label();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.InputImpl <em>Input</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.InputImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getInput()
		 * @generated
		 */
    EClass INPUT = eINSTANCE.getInput();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.whileDSL.wHILE.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.whileDSL.wHILE.impl.TargetImpl
		 * @see lu.uni.snt.whileDSL.wHILE.impl.WHILEPackageImpl#getTarget()
		 * @generated
		 */
    EClass TARGET = eINSTANCE.getTarget();

  }

} //WHILEPackage
