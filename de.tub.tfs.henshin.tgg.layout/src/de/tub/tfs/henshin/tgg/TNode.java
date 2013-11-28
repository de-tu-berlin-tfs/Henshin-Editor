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
 *   <li>{@link de.tub.tfs.henshin.tgg.TNode#getMarkerType <em>Marker Type</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TNode#getGuessedSide <em>Guessed Side</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode()
 * @model
 * @generated
 */
public interface TNode extends Node {

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
	 * Returns the value of the '<em><b>Marker Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Marker Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Marker Type</em>' attribute.
	 * @see #setMarkerType(String)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode_MarkerType()
	 * @model
	 * @generated
	 */
	String getMarkerType();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TNode#getMarkerType <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marker Type</em>' attribute.
	 * @see #getMarkerType()
	 * @generated
	 */
	void setMarkerType(String value);

	/**
	 * Returns the value of the '<em><b>Guessed Side</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guessed Side</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guessed Side</em>' attribute.
	 * @see #setGuessedSide(String)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTNode_GuessedSide()
	 * @model
	 * @generated
	 */
	String getGuessedSide();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TNode#getGuessedSide <em>Guessed Side</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guessed Side</em>' attribute.
	 * @see #getGuessedSide()
	 * @generated
	 */
	void setGuessedSide(String value);
} // TNode
