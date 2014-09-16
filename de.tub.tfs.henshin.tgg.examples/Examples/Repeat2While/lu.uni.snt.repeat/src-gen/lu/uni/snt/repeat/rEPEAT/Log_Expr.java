/**
 */
package lu.uni.snt.repeat.rEPEAT;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Log Expr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Log_Expr#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Expr()
 * @model
 * @generated
 */
public interface Log_Expr extends Source
{
  /**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(Log_Expr_T)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Expr_Type()
	 * @model containment="true"
	 * @generated
	 */
  Log_Expr_T getType();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
  void setType(Log_Expr_T value);

} // Log_Expr
