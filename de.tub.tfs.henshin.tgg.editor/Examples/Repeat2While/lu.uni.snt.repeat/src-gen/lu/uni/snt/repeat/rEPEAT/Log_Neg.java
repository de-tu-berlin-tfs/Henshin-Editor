/**
 */
package lu.uni.snt.repeat.rEPEAT;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Log Neg</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Log_Neg#getExpr <em>Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Neg()
 * @model
 * @generated
 */
public interface Log_Neg extends Log_Expr_Unary
{
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
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Neg_Expr()
	 * @model containment="true"
	 * @generated
	 */
  Log_Expr getExpr();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Log_Neg#getExpr <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
  void setExpr(Log_Expr value);

} // Log_Neg
