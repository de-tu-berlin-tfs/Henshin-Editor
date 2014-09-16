/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.layout;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.Layout#getX <em>X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.Layout#getY <em>Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.Layout#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getLayout()
 * @model
 * @generated
 */
public interface Layout extends EObject {
        /**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>X</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getLayout_X()
	 * @model
	 * @generated
	 */
        int getX();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.Layout#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
        void setX(int value);

        /**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Y</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getLayout_Y()
	 * @model
	 * @generated
	 */
        int getY();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.Layout#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
        void setY(int value);

        /**
	 * Returns the value of the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Model</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' reference.
	 * @see #setModel(EObject)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getLayout_Model()
	 * @model required="true"
	 * @generated
	 */
        EObject getModel();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.Layout#getModel <em>Model</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' reference.
	 * @see #getModel()
	 * @generated
	 */
        void setModel(EObject value);

} // Layout
