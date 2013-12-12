/**
 */
package lu.uni.snt.whileDSL.wHILE;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>WProgram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.WProgram#getFst <em>Fst</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getWProgram()
 * @model
 * @generated
 */
public interface WProgram extends Target
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
	 * @see #setFst(Fgmnt_LST_Elem)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getWProgram_Fst()
	 * @model containment="true"
	 * @generated
	 */
  Fgmnt_LST_Elem getFst();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.WProgram#getFst <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fst</em>' containment reference.
	 * @see #getFst()
	 * @generated
	 */
  void setFst(Fgmnt_LST_Elem value);

} // WProgram
