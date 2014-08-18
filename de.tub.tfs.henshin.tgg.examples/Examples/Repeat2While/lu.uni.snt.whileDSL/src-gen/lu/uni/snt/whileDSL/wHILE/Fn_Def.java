/**
 */
package lu.uni.snt.whileDSL.wHILE;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fn Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Fn_Def#getNameF <em>Name F</em>}</li>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Fn_Def#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getFn_Def()
 * @model
 * @generated
 */
public interface Fn_Def extends Fgmnt_LST_Elem
{
  /**
	 * Returns the value of the '<em><b>Name F</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name F</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Name F</em>' attribute.
	 * @see #setNameF(String)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getFn_Def_NameF()
	 * @model
	 * @generated
	 */
  String getNameF();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Fn_Def#getNameF <em>Name F</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name F</em>' attribute.
	 * @see #getNameF()
	 * @generated
	 */
  void setNameF(String value);

  /**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Fgmnt_LST_Elem)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getFn_Def_Body()
	 * @model containment="true"
	 * @generated
	 */
  Fgmnt_LST_Elem getBody();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Fn_Def#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
  void setBody(Fgmnt_LST_Elem value);

} // Fn_Def
