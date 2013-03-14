/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.henshin.model.Attribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.AttributeLayout#isNew <em>New</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.AttributeLayout#getLhsattribute <em>Lhsattribute</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.AttributeLayout#getRhsattribute <em>Rhsattribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getAttributeLayout()
 * @model
 * @generated
 */
public interface AttributeLayout extends EObject {
	/**
	 * Returns the value of the '<em><b>New</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New</em>' attribute.
	 * @see #setNew(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getAttributeLayout_New()
	 * @model
	 * @generated
	 */
	boolean isNew();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.AttributeLayout#isNew <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New</em>' attribute.
	 * @see #isNew()
	 * @generated
	 */
	void setNew(boolean value);

	/**
	 * Returns the value of the '<em><b>Lhsattribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhsattribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhsattribute</em>' reference.
	 * @see #setLhsattribute(Attribute)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getAttributeLayout_Lhsattribute()
	 * @model
	 * @generated
	 */
	Attribute getLhsattribute();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.AttributeLayout#getLhsattribute <em>Lhsattribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhsattribute</em>' reference.
	 * @see #getLhsattribute()
	 * @generated
	 */
	void setLhsattribute(Attribute value);

	/**
	 * Returns the value of the '<em><b>Rhsattribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhsattribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhsattribute</em>' reference.
	 * @see #setRhsattribute(Attribute)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getAttributeLayout_Rhsattribute()
	 * @model
	 * @generated
	 */
	Attribute getRhsattribute();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.AttributeLayout#getRhsattribute <em>Rhsattribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhsattribute</em>' reference.
	 * @see #getRhsattribute()
	 * @generated
	 */
	void setRhsattribute(Attribute value);

} // AttributeLayout
