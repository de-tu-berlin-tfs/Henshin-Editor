/**
 * <copyright>
 * </copyright>
 *
 * $Id$
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
 *   <li>{@link target.FKey#getRefernces <em>Refernces</em>}</li>
 * </ul>
 * </p>
 *
 * @see target.TargetPackage#getFKey()
 * @model
 * @generated
 */
public interface FKey extends EObject {
	/**
	 * Returns the value of the '<em><b>Fcols</b></em>' containment reference list.
	 * The list contents are of type {@link target.Column}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fcols</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fcols</em>' containment reference list.
	 * @see target.TargetPackage#getFKey_Fcols()
	 * @model containment="true"
	 * @generated
	 */
	EList<Column> getFcols();

	/**
	 * Returns the value of the '<em><b>Refernces</b></em>' containment reference list.
	 * The list contents are of type {@link target.Table}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refernces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refernces</em>' containment reference list.
	 * @see target.TargetPackage#getFKey_Refernces()
	 * @model containment="true"
	 * @generated
	 */
	EList<Table> getRefernces();

} // FKey
