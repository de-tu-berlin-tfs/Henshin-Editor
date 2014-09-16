/**
 */
package lu.uni.snt.whileDSL.wHILE;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Binary#getFst <em>Fst</em>}</li>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Binary#getOperator <em>Operator</em>}</li>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Binary#getSnd <em>Snd</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getBinary()
 * @model
 * @generated
 */
public interface Binary extends Expr_T
{
  /**
	 * Returns the value of the '<em><b>Fst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fst</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Fst</em>' containment reference.
	 * @see #setFst(Expr)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getBinary_Fst()
	 * @model containment="true"
	 * @generated
	 */
  Expr getFst();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Binary#getFst <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fst</em>' containment reference.
	 * @see #getFst()
	 * @generated
	 */
  void setFst(Expr value);

  /**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see #setOperator(String)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getBinary_Operator()
	 * @model
	 * @generated
	 */
  String getOperator();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Binary#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see #getOperator()
	 * @generated
	 */
  void setOperator(String value);

  /**
	 * Returns the value of the '<em><b>Snd</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Snd</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Snd</em>' containment reference.
	 * @see #setSnd(Expr)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getBinary_Snd()
	 * @model containment="true"
	 * @generated
	 */
  Expr getSnd();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Binary#getSnd <em>Snd</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Snd</em>' containment reference.
	 * @see #getSnd()
	 * @generated
	 */
  void setSnd(Expr value);

} // Binary
