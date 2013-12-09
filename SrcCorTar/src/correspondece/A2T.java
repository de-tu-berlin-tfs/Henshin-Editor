/**
 */
package correspondece;

import org.eclipse.emf.ecore.EObject;

import source.Association;

import target.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>A2T</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link correspondece.A2T#getAss <em>Ass</em>}</li>
 *   <li>{@link correspondece.A2T#getTable <em>Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see correspondece.CorrespondecePackage#getA2T()
 * @model
 * @generated
 */
public interface A2T extends EObject {
	/**
	 * Returns the value of the '<em><b>Ass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ass</em>' reference.
	 * @see #setAss(Association)
	 * @see correspondece.CorrespondecePackage#getA2T_Ass()
	 * @model
	 * @generated
	 */
	Association getAss();

	/**
	 * Sets the value of the '{@link correspondece.A2T#getAss <em>Ass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ass</em>' reference.
	 * @see #getAss()
	 * @generated
	 */
	void setAss(Association value);

	/**
	 * Returns the value of the '<em><b>Table</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' reference.
	 * @see #setTable(Table)
	 * @see correspondece.CorrespondecePackage#getA2T_Table()
	 * @model
	 * @generated
	 */
	Table getTable();

	/**
	 * Sets the value of the '{@link correspondece.A2T#getTable <em>Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' reference.
	 * @see #getTable()
	 * @generated
	 */
	void setTable(Table value);

} // A2T
