/**
 */
package lu.uni.snt.repeat.rEPEAT;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Read</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Read#getParam <em>Param</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRead()
 * @model
 * @generated
 */
public interface Read extends Stmnt_LST_Elem
{
  /**
	 * Returns the value of the '<em><b>Param</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Param</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Param</em>' containment reference.
	 * @see #setParam(Sym)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRead_Param()
	 * @model containment="true"
	 * @generated
	 */
  Sym getParam();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Read#getParam <em>Param</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Param</em>' containment reference.
	 * @see #getParam()
	 * @generated
	 */
  void setParam(Sym value);

} // Read
