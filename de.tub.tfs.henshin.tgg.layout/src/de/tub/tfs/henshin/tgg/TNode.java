/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TNode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.TNode#getX <em>X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TNode#getY <em>Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TNode#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode()
 * @model
 * @generated
 */
public interface TNode extends Node, TElem {

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode_X()
	 * @model default="0"
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TNode#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode_Y()
	 * @model default="0"
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TNode#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' attribute.
	 * The literals are from the enumeration {@link de.tub.tfs.henshin.tgg.TripleComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' attribute.
	 * @see de.tub.tfs.henshin.tgg.TripleComponent
	 * @see #setComponent(TripleComponent)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode_Component()
	 * @model
	 * @generated
	 */
	TripleComponent getComponent();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TNode#getComponent <em>Component</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' attribute.
	 * @see de.tub.tfs.henshin.tgg.TripleComponent
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(TripleComponent value);
} // TNode
