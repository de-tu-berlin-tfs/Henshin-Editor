/**
 */
package target;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>FKey</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link target.FKey#getFcols <em>Fcols</em>}</li>
 *   <li>{@link target.FKey#getReferences <em>References</em>}</li>
 * </ul>
 * </p>
 *
 * @see target.TargetPackage#getFKey()
 * @model
 * @generated
 */
public interface FKey extends EObject {
	/**
	 * Returns the value of the '<em><b>Fcols</b></em>' reference list.
	 * The list contents are of type {@link target.Column}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fcols</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fcols</em>' reference list.
	 * @see target.TargetPackage#getFKey_Fcols()
	 * @model
	 * @generated
	 */
	EList<Column> getFcols();

	/**
	 * Returns the value of the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' reference.
	 * @see #setReferences(Table)
	 * @see target.TargetPackage#getFKey_References()
	 * @model
	 * @generated
	 */
	Table getReferences();

	/**
	 * Sets the value of the '{@link target.FKey#getReferences <em>References</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>References</em>' reference.
	 * @see #getReferences()
	 * @generated
	 */
	void setReferences(Table value);

} // FKey
