/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getX <em>X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getY <em>Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#isHide <em>Hide</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getNode <em>Node</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getLhsnode <em>Lhsnode</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getAttributeLayouts <em>Attribute Layouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#isNew <em>New</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getRhsTranslated <em>Rhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#getLhsTranslated <em>Lhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.NodeLayout#isCritical <em>Critical</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout()
 * @model
 * @deprecated
 */
public interface NodeLayout extends EObject {
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_X()
	 * @model
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#getX <em>X</em>}' attribute.
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_Y()
	 * @model
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Returns the value of the '<em><b>Hide</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hide</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hide</em>' attribute.
	 * @see #setHide(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_Hide()
	 * @model
	 * @generated
	 */
	boolean isHide();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#isHide <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hide</em>' attribute.
	 * @see #isHide()
	 * @generated
	 */
	void setHide(boolean value);

	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(Node)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_Node()
	 * @model
	 * @generated
	 */
	Node getNode();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(Node value);

	/**
	 * Returns the value of the '<em><b>Lhsnode</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhsnode</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhsnode</em>' reference.
	 * @see #setLhsnode(Node)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_Lhsnode()
	 * @model
	 * @generated
	 */
	Node getLhsnode();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#getLhsnode <em>Lhsnode</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhsnode</em>' reference.
	 * @see #getLhsnode()
	 * @generated
	 */
	void setLhsnode(Node value);

	/**
	 * Returns the value of the '<em><b>Attribute Layouts</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.AttributeLayout}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Layouts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Layouts</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_AttributeLayouts()
	 * @model containment="true"
	 * @generated
	 */
	EList<AttributeLayout> getAttributeLayouts();

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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_New()
	 * @model
	 * @generated
	 */
	boolean isNew();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#isNew <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New</em>' attribute.
	 * @see #isNew()
	 * @generated
	 */
	void setNew(boolean value);

	/**
	 * Returns the value of the '<em><b>Rhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs Translated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs Translated</em>' attribute.
	 * @see #setRhsTranslated(Boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_RhsTranslated()
	 * @model
	 * @generated
	 */
	Boolean getRhsTranslated();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#getRhsTranslated <em>Rhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhs Translated</em>' attribute.
	 * @see #getRhsTranslated()
	 * @generated
	 */
	void setRhsTranslated(Boolean value);

	/**
	 * Returns the value of the '<em><b>Lhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs Translated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs Translated</em>' attribute.
	 * @see #setLhsTranslated(Boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_LhsTranslated()
	 * @model
	 * @generated
	 */
	Boolean getLhsTranslated();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#getLhsTranslated <em>Lhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhs Translated</em>' attribute.
	 * @see #getLhsTranslated()
	 * @generated
	 */
	void setLhsTranslated(Boolean value);

	/**
	 * Returns the value of the '<em><b>Critical</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Critical</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Critical</em>' attribute.
	 * @see #setCritical(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getNodeLayout_Critical()
	 * @model
	 * @generated
	 */
	boolean isCritical();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.NodeLayout#isCritical <em>Critical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Critical</em>' attribute.
	 * @see #isCritical()
	 * @generated
	 */
	void setCritical(boolean value);

} // NodeLayout
