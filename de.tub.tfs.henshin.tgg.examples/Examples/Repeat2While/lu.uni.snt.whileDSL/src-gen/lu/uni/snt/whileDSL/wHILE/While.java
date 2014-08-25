/**
 */
package lu.uni.snt.whileDSL.wHILE;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>While</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.While#getExpr <em>Expr</em>}</li>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.While#getFgmnt <em>Fgmnt</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getWhile()
 * @model
 * @generated
 */
public interface While extends Fgmnt_LST_Elem
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
	 * @see #setExpr(Expr)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getWhile_Expr()
	 * @model containment="true"
	 * @generated
	 */
  Expr getExpr();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.While#getExpr <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
  void setExpr(Expr value);

  /**
	 * Returns the value of the '<em><b>Fgmnt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fgmnt</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Fgmnt</em>' containment reference.
	 * @see #setFgmnt(Fgmnt_LST_Elem)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getWhile_Fgmnt()
	 * @model containment="true"
	 * @generated
	 */
  Fgmnt_LST_Elem getFgmnt();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.While#getFgmnt <em>Fgmnt</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fgmnt</em>' containment reference.
	 * @see #getFgmnt()
	 * @generated
	 */
  void setFgmnt(Fgmnt_LST_Elem value);

} // While
