/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece;

import org.eclipse.emf.ecore.EObject;

import source.ClassDiagram;

import target.Database;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CD2DB</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link correspondece.CD2DB#getDb2cd <em>Db2cd</em>}</li>
 *   <li>{@link correspondece.CD2DB#getCd2db <em>Cd2db</em>}</li>
 * </ul>
 * </p>
 *
 * @see correspondece.CorrespondecePackage#getCD2DB()
 * @model
 * @generated
 */
public interface CD2DB extends EObject {
	/**
	 * Returns the value of the '<em><b>Db2cd</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db2cd</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db2cd</em>' reference.
	 * @see #setDb2cd(ClassDiagram)
	 * @see correspondece.CorrespondecePackage#getCD2DB_Db2cd()
	 * @model
	 * @generated
	 */
	ClassDiagram getDb2cd();

	/**
	 * Sets the value of the '{@link correspondece.CD2DB#getDb2cd <em>Db2cd</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db2cd</em>' reference.
	 * @see #getDb2cd()
	 * @generated
	 */
	void setDb2cd(ClassDiagram value);

	/**
	 * Returns the value of the '<em><b>Cd2db</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cd2db</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cd2db</em>' reference.
	 * @see #setCd2db(Database)
	 * @see correspondece.CorrespondecePackage#getCD2DB_Cd2db()
	 * @model
	 * @generated
	 */
	Database getCd2db();

	/**
	 * Sets the value of the '{@link correspondece.CD2DB#getCd2db <em>Cd2db</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cd2db</em>' reference.
	 * @see #getCd2db()
	 * @generated
	 */
	void setCd2db(Database value);

} // CD2DB
