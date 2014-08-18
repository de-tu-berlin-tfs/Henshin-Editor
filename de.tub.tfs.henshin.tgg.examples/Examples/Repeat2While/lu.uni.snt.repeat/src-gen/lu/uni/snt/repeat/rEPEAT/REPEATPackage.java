/**
 */
package lu.uni.snt.repeat.rEPEAT;

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
 * @see lu.uni.snt.repeat.rEPEAT.REPEATFactory
 * @model kind="package"
 * @generated
 */
public interface REPEATPackage extends EPackage
{
  /**
	 * The package name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNAME = "rEPEAT";

  /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNS_URI = "http://www.uni.lu/snt/repeat/REPEAT";

  /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String eNS_PREFIX = "rEPEAT";

  /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  REPEATPackage eINSTANCE = lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl.init();

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.SourceImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getSource()
	 * @generated
	 */
  int SOURCE = 12;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__S2C = TGG_correspondencePackage.ABSTRACT_SOURCE__S2C;

		/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int SOURCE_FEATURE_COUNT = TGG_correspondencePackage.ABSTRACT_SOURCE_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.RProgramImpl <em>RProgram</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.RProgramImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getRProgram()
	 * @generated
	 */
  int RPROGRAM = 0;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RPROGRAM__S2C = SOURCE__S2C;

		/**
	 * The feature id for the '<em><b>Fst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int RPROGRAM__FST = SOURCE_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>RProgram</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int RPROGRAM_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Stmnt_LST_ElemImpl <em>Stmnt LST Elem</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.Stmnt_LST_ElemImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getStmnt_LST_Elem()
	 * @generated
	 */
  int STMNT_LST_ELEM = 1;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STMNT_LST_ELEM__S2C = SOURCE__S2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int STMNT_LST_ELEM__NEXT = SOURCE_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Stmnt LST Elem</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int STMNT_LST_ELEM_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.RepeatImpl <em>Repeat</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.RepeatImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getRepeat()
	 * @generated
	 */
  int REPEAT = 2;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPEAT__S2C = STMNT_LST_ELEM__S2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int REPEAT__NEXT = STMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Stmnt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int REPEAT__STMNT = STMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int REPEAT__EXPR = STMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>Repeat</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int REPEAT_FEATURE_COUNT = STMNT_LST_ELEM_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.AsgImpl <em>Asg</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.AsgImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getAsg()
	 * @generated
	 */
  int ASG = 3;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASG__S2C = STMNT_LST_ELEM__S2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int ASG__NEXT = STMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int ASG__LEFT = STMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int ASG__RIGHT = STMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The number of structural features of the '<em>Asg</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int ASG_FEATURE_COUNT = STMNT_LST_ELEM_FEATURE_COUNT + 2;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.ReadImpl <em>Read</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.ReadImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getRead()
	 * @generated
	 */
  int READ = 4;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int READ__S2C = STMNT_LST_ELEM__S2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int READ__NEXT = STMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Param</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int READ__PARAM = STMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Read</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int READ_FEATURE_COUNT = STMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.CommentImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getComment()
	 * @generated
	 */
  int COMMENT = 5;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__S2C = STMNT_LST_ELEM__S2C;

		/**
	 * The feature id for the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int COMMENT__NEXT = STMNT_LST_ELEM__NEXT;

  /**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int COMMENT__COMMENT = STMNT_LST_ELEM_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int COMMENT_FEATURE_COUNT = STMNT_LST_ELEM_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_ExprImpl <em>Log Expr</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_ExprImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr()
	 * @generated
	 */
  int LOG_EXPR = 6;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_EXPR__S2C = SOURCE__S2C;

		/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR__TYPE = SOURCE_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Log Expr</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_TImpl <em>Log Expr T</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_TImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr_T()
	 * @generated
	 */
  int LOG_EXPR_T = 7;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_EXPR_T__S2C = SOURCE__S2C;

		/**
	 * The number of structural features of the '<em>Log Expr T</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_T_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_UnaryImpl <em>Log Expr Unary</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_UnaryImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr_Unary()
	 * @generated
	 */
  int LOG_EXPR_UNARY = 8;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_EXPR_UNARY__S2C = LOG_EXPR_T__S2C;

		/**
	 * The number of structural features of the '<em>Log Expr Unary</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_UNARY_FEATURE_COUNT = LOG_EXPR_T_FEATURE_COUNT + 0;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl <em>Log Expr Binary</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr_Binary()
	 * @generated
	 */
  int LOG_EXPR_BINARY = 9;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_EXPR_BINARY__S2C = LOG_EXPR_T__S2C;

		/**
	 * The feature id for the '<em><b>Fst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_BINARY__FST = LOG_EXPR_T_FEATURE_COUNT + 0;

  /**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_BINARY__OPERATOR = LOG_EXPR_T_FEATURE_COUNT + 1;

  /**
	 * The feature id for the '<em><b>Snd</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_BINARY__SND = LOG_EXPR_T_FEATURE_COUNT + 2;

  /**
	 * The number of structural features of the '<em>Log Expr Binary</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_EXPR_BINARY_FEATURE_COUNT = LOG_EXPR_T_FEATURE_COUNT + 3;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_NegImpl <em>Log Neg</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_NegImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Neg()
	 * @generated
	 */
  int LOG_NEG = 10;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NEG__S2C = LOG_EXPR_UNARY__S2C;

		/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_NEG__EXPR = LOG_EXPR_UNARY_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Log Neg</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int LOG_NEG_FEATURE_COUNT = LOG_EXPR_UNARY_FEATURE_COUNT + 1;

  /**
	 * The meta object id for the '{@link lu.uni.snt.repeat.rEPEAT.impl.SymImpl <em>Sym</em>}' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see lu.uni.snt.repeat.rEPEAT.impl.SymImpl
	 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getSym()
	 * @generated
	 */
  int SYM = 11;

  /**
	 * The feature id for the '<em><b>S2c</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYM__S2C = LOG_EXPR_UNARY__S2C;

		/**
	 * The feature id for the '<em><b>Sym</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int SYM__SYM = LOG_EXPR_UNARY_FEATURE_COUNT + 0;

  /**
	 * The number of structural features of the '<em>Sym</em>' class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
  int SYM_FEATURE_COUNT = LOG_EXPR_UNARY_FEATURE_COUNT + 1;


  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.RProgram <em>RProgram</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RProgram</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.RProgram
	 * @generated
	 */
  EClass getRProgram();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.RProgram#getFst <em>Fst</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fst</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.RProgram#getFst()
	 * @see #getRProgram()
	 * @generated
	 */
  EReference getRProgram_Fst();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem <em>Stmnt LST Elem</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stmnt LST Elem</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem
	 * @generated
	 */
  EClass getStmnt_LST_Elem();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Next</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem#getNext()
	 * @see #getStmnt_LST_Elem()
	 * @generated
	 */
  EReference getStmnt_LST_Elem_Next();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Repeat <em>Repeat</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repeat</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Repeat
	 * @generated
	 */
  EClass getRepeat();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Repeat#getStmnt <em>Stmnt</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stmnt</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Repeat#getStmnt()
	 * @see #getRepeat()
	 * @generated
	 */
  EReference getRepeat_Stmnt();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Repeat#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Repeat#getExpr()
	 * @see #getRepeat()
	 * @generated
	 */
  EReference getRepeat_Expr();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Asg <em>Asg</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asg</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Asg
	 * @generated
	 */
  EClass getAsg();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Asg#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Asg#getLeft()
	 * @see #getAsg()
	 * @generated
	 */
  EReference getAsg_Left();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Asg#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Asg#getRight()
	 * @see #getAsg()
	 * @generated
	 */
  EReference getAsg_Right();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Read <em>Read</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Read</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Read
	 * @generated
	 */
  EClass getRead();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Read#getParam <em>Param</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Param</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Read#getParam()
	 * @see #getRead()
	 * @generated
	 */
  EReference getRead_Param();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Comment
	 * @generated
	 */
  EClass getComment();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.repeat.rEPEAT.Comment#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Comment#getComment()
	 * @see #getComment()
	 * @generated
	 */
  EAttribute getComment_Comment();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr <em>Log Expr</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Expr</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr
	 * @generated
	 */
  EClass getLog_Expr();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr#getType()
	 * @see #getLog_Expr()
	 * @generated
	 */
  EReference getLog_Expr_Type();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_T <em>Log Expr T</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Expr T</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_T
	 * @generated
	 */
  EClass getLog_Expr_T();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Unary <em>Log Expr Unary</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Expr Unary</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Unary
	 * @generated
	 */
  EClass getLog_Expr_Unary();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary <em>Log Expr Binary</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Expr Binary</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary
	 * @generated
	 */
  EClass getLog_Expr_Binary();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getFst <em>Fst</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fst</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getFst()
	 * @see #getLog_Expr_Binary()
	 * @generated
	 */
  EReference getLog_Expr_Binary_Fst();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getOperator()
	 * @see #getLog_Expr_Binary()
	 * @generated
	 */
  EAttribute getLog_Expr_Binary_Operator();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getSnd <em>Snd</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Snd</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getSnd()
	 * @see #getLog_Expr_Binary()
	 * @generated
	 */
  EReference getLog_Expr_Binary_Snd();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Log_Neg <em>Log Neg</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Neg</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Neg
	 * @generated
	 */
  EClass getLog_Neg();

  /**
	 * Returns the meta object for the containment reference '{@link lu.uni.snt.repeat.rEPEAT.Log_Neg#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Log_Neg#getExpr()
	 * @see #getLog_Neg()
	 * @generated
	 */
  EReference getLog_Neg_Expr();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Sym <em>Sym</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sym</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Sym
	 * @generated
	 */
  EClass getSym();

  /**
	 * Returns the meta object for the attribute '{@link lu.uni.snt.repeat.rEPEAT.Sym#getSym <em>Sym</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sym</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Sym#getSym()
	 * @see #getSym()
	 * @generated
	 */
  EAttribute getSym_Sym();

  /**
	 * Returns the meta object for class '{@link lu.uni.snt.repeat.rEPEAT.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see lu.uni.snt.repeat.rEPEAT.Source
	 * @generated
	 */
  EClass getSource();

  /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
  REPEATFactory getREPEATFactory();

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
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.RProgramImpl <em>RProgram</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.RProgramImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getRProgram()
		 * @generated
		 */
    EClass RPROGRAM = eINSTANCE.getRProgram();

    /**
		 * The meta object literal for the '<em><b>Fst</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference RPROGRAM__FST = eINSTANCE.getRProgram_Fst();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Stmnt_LST_ElemImpl <em>Stmnt LST Elem</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.Stmnt_LST_ElemImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getStmnt_LST_Elem()
		 * @generated
		 */
    EClass STMNT_LST_ELEM = eINSTANCE.getStmnt_LST_Elem();

    /**
		 * The meta object literal for the '<em><b>Next</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference STMNT_LST_ELEM__NEXT = eINSTANCE.getStmnt_LST_Elem_Next();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.RepeatImpl <em>Repeat</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.RepeatImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getRepeat()
		 * @generated
		 */
    EClass REPEAT = eINSTANCE.getRepeat();

    /**
		 * The meta object literal for the '<em><b>Stmnt</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference REPEAT__STMNT = eINSTANCE.getRepeat_Stmnt();

    /**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference REPEAT__EXPR = eINSTANCE.getRepeat_Expr();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.AsgImpl <em>Asg</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.AsgImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getAsg()
		 * @generated
		 */
    EClass ASG = eINSTANCE.getAsg();

    /**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference ASG__LEFT = eINSTANCE.getAsg_Left();

    /**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference ASG__RIGHT = eINSTANCE.getAsg_Right();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.ReadImpl <em>Read</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.ReadImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getRead()
		 * @generated
		 */
    EClass READ = eINSTANCE.getRead();

    /**
		 * The meta object literal for the '<em><b>Param</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference READ__PARAM = eINSTANCE.getRead_Param();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.CommentImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getComment()
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
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_ExprImpl <em>Log Expr</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_ExprImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr()
		 * @generated
		 */
    EClass LOG_EXPR = eINSTANCE.getLog_Expr();

    /**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference LOG_EXPR__TYPE = eINSTANCE.getLog_Expr_Type();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_TImpl <em>Log Expr T</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_TImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr_T()
		 * @generated
		 */
    EClass LOG_EXPR_T = eINSTANCE.getLog_Expr_T();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_UnaryImpl <em>Log Expr Unary</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_UnaryImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr_Unary()
		 * @generated
		 */
    EClass LOG_EXPR_UNARY = eINSTANCE.getLog_Expr_Unary();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl <em>Log Expr Binary</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Expr_Binary()
		 * @generated
		 */
    EClass LOG_EXPR_BINARY = eINSTANCE.getLog_Expr_Binary();

    /**
		 * The meta object literal for the '<em><b>Fst</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference LOG_EXPR_BINARY__FST = eINSTANCE.getLog_Expr_Binary_Fst();

    /**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute LOG_EXPR_BINARY__OPERATOR = eINSTANCE.getLog_Expr_Binary_Operator();

    /**
		 * The meta object literal for the '<em><b>Snd</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference LOG_EXPR_BINARY__SND = eINSTANCE.getLog_Expr_Binary_Snd();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.Log_NegImpl <em>Log Neg</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.Log_NegImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getLog_Neg()
		 * @generated
		 */
    EClass LOG_NEG = eINSTANCE.getLog_Neg();

    /**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EReference LOG_NEG__EXPR = eINSTANCE.getLog_Neg_Expr();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.SymImpl <em>Sym</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.SymImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getSym()
		 * @generated
		 */
    EClass SYM = eINSTANCE.getSym();

    /**
		 * The meta object literal for the '<em><b>Sym</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @generated
		 */
    EAttribute SYM__SYM = eINSTANCE.getSym_Sym();

    /**
		 * The meta object literal for the '{@link lu.uni.snt.repeat.rEPEAT.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
		 * @see lu.uni.snt.repeat.rEPEAT.impl.SourceImpl
		 * @see lu.uni.snt.repeat.rEPEAT.impl.REPEATPackageImpl#getSource()
		 * @generated
		 */
    EClass SOURCE = eINSTANCE.getSource();

  }

} //REPEATPackage
