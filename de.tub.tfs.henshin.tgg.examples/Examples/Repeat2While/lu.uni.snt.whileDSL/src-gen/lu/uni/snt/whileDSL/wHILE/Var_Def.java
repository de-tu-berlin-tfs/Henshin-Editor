/**
 */
package lu.uni.snt.whileDSL.wHILE;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Var Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Var_Def#getLeft <em>Left</em>}</li>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Var_Def#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getVar_Def()
 * @model
 * @generated
 */
public interface Var_Def extends Fgmnt_LST_Elem
{
  /**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(Var)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getVar_Def_Left()
	 * @model containment="true"
	 * @generated
	 */
  Var getLeft();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Var_Def#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
  void setLeft(Var value);

  /**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(Expr)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getVar_Def_Right()
	 * @model containment="true"
	 * @generated
	 */
  Expr getRight();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Var_Def#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
  void setRight(Expr value);

} // Var_Def
