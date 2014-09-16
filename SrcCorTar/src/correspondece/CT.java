/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece;

import org.eclipse.emf.ecore.EObject;

import target.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CT</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link correspondece.CT#getClass_ <em>Class</em>}</li>
 *   <li>{@link correspondece.CT#getTable <em>Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see correspondece.CorrespondecePackage#getCT()
 * @model
 * @generated
 */
public interface CT extends EObject {
	/**
	 * Returns the value of the '<em><b>Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' reference.
	 * @see #setClass(source.Class)
	 * @see correspondece.CorrespondecePackage#getCT_Class()
	 * @model
	 * @generated
	 */
	source.Class getClass_();

	/**
	 * Sets the value of the '{@link correspondece.CT#getClass_ <em>Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' reference.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(source.Class value);

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
	 * @see correspondece.CorrespondecePackage#getCT_Table()
	 * @model
	 * @generated
	 */
	Table getTable();

	/**
	 * Sets the value of the '{@link correspondece.CT#getTable <em>Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' reference.
	 * @see #getTable()
	 * @generated
	 */
	void setTable(Table value);

} // CT
