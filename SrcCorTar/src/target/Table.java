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
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link target.Table#getName <em>Name</em>}</li>
 *   <li>{@link target.Table#getFkeys <em>Fkeys</em>}</li>
 *   <li>{@link target.Table#getPkey <em>Pkey</em>}</li>
 *   <li>{@link target.Table#getCols <em>Cols</em>}</li>
 * </ul>
 * </p>
 *
 * @see target.TargetPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see target.TargetPackage#getTable_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link target.Table#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Fkeys</b></em>' containment reference list.
	 * The list contents are of type {@link target.FKey}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fkeys</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fkeys</em>' containment reference list.
	 * @see target.TargetPackage#getTable_Fkeys()
	 * @model containment="true"
	 * @generated
	 */
	EList<FKey> getFkeys();

	/**
	 * Returns the value of the '<em><b>Pkey</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pkey</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pkey</em>' reference.
	 * @see #setPkey(Column)
	 * @see target.TargetPackage#getTable_Pkey()
	 * @model
	 * @generated
	 */
	Column getPkey();

	/**
	 * Sets the value of the '{@link target.Table#getPkey <em>Pkey</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pkey</em>' reference.
	 * @see #getPkey()
	 * @generated
	 */
	void setPkey(Column value);

	/**
	 * Returns the value of the '<em><b>Cols</b></em>' containment reference list.
	 * The list contents are of type {@link target.Column}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cols</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cols</em>' containment reference list.
	 * @see target.TargetPackage#getTable_Cols()
	 * @model containment="true"
	 * @generated
	 */
	EList<Column> getCols();

} // Table
