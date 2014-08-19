/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece;

import org.eclipse.emf.ecore.EObject;

import source.Attribute;

import target.Column;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AC</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link correspondece.AC#getAtr <em>Atr</em>}</li>
 *   <li>{@link correspondece.AC#getCol <em>Col</em>}</li>
 * </ul>
 * </p>
 *
 * @see correspondece.CorrespondecePackage#getAC()
 * @model
 * @generated
 */
public interface AC extends EObject {
	/**
	 * Returns the value of the '<em><b>Atr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Atr</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Atr</em>' reference.
	 * @see #setAtr(Attribute)
	 * @see correspondece.CorrespondecePackage#getAC_Atr()
	 * @model
	 * @generated
	 */
	Attribute getAtr();

	/**
	 * Sets the value of the '{@link correspondece.AC#getAtr <em>Atr</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Atr</em>' reference.
	 * @see #getAtr()
	 * @generated
	 */
	void setAtr(Attribute value);

	/**
	 * Returns the value of the '<em><b>Col</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Col</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Col</em>' reference.
	 * @see #setCol(Column)
	 * @see correspondece.CorrespondecePackage#getAC_Col()
	 * @model
	 * @generated
	 */
	Column getCol();

	/**
	 * Sets the value of the '{@link correspondece.AC#getCol <em>Col</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Col</em>' reference.
	 * @see #getCol()
	 * @generated
	 */
	void setCol(Column value);

} // AC
