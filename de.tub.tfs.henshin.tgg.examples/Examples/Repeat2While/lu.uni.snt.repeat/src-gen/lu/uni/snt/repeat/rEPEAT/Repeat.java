/**
 */
package lu.uni.snt.repeat.rEPEAT;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repeat</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Repeat#getStmnt <em>Stmnt</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Repeat#getExpr <em>Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRepeat()
 * @model
 * @generated
 */
public interface Repeat extends Stmnt_LST_Elem
{
  /**
	 * Returns the value of the '<em><b>Stmnt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Stmnt</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Stmnt</em>' containment reference.
	 * @see #setStmnt(Stmnt_LST_Elem)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRepeat_Stmnt()
	 * @model containment="true"
	 * @generated
	 */
  Stmnt_LST_Elem getStmnt();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Repeat#getStmnt <em>Stmnt</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stmnt</em>' containment reference.
	 * @see #getStmnt()
	 * @generated
	 */
  void setStmnt(Stmnt_LST_Elem value);

  /**
	 * Returns the value of the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Expr</em>' containment reference.
	 * @see #setExpr(Log_Expr)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRepeat_Expr()
	 * @model containment="true"
	 * @generated
	 */
  Log_Expr getExpr();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Repeat#getExpr <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
  void setExpr(Log_Expr value);

} // Repeat
