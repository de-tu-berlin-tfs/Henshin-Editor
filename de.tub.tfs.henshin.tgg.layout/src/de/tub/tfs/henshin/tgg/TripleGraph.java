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

import org.eclipse.emf.henshin.model.Graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Triple Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerSC_X <em>Divider SC X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerCT_X <em>Divider CT X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerMaxY <em>Divider Max Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerYOffset <em>Divider YOffset</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTripleGraph()
 * @model
 * @generated
 */
public interface TripleGraph extends Graph {
	/**
	 * Returns the value of the '<em><b>Divider SC X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Divider SC X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Divider SC X</em>' attribute.
	 * @see #setDividerSC_X(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTripleGraph_DividerSC_X()
	 * @model
	 * @generated
	 */
	int getDividerSC_X();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerSC_X <em>Divider SC X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Divider SC X</em>' attribute.
	 * @see #getDividerSC_X()
	 * @generated
	 */
	void setDividerSC_X(int value);

	/**
	 * Returns the value of the '<em><b>Divider CT X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Divider CT X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Divider CT X</em>' attribute.
	 * @see #setDividerCT_X(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTripleGraph_DividerCT_X()
	 * @model
	 * @generated
	 */
	int getDividerCT_X();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerCT_X <em>Divider CT X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Divider CT X</em>' attribute.
	 * @see #getDividerCT_X()
	 * @generated
	 */
	void setDividerCT_X(int value);

	/**
	 * Returns the value of the '<em><b>Divider Max Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Divider Max Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Divider Max Y</em>' attribute.
	 * @see #setDividerMaxY(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTripleGraph_DividerMaxY()
	 * @model
	 * @generated
	 */
	int getDividerMaxY();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerMaxY <em>Divider Max Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Divider Max Y</em>' attribute.
	 * @see #getDividerMaxY()
	 * @generated
	 */
	void setDividerMaxY(int value);

	/**
	 * Returns the value of the '<em><b>Divider YOffset</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Divider YOffset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Divider YOffset</em>' attribute.
	 * @see #setDividerYOffset(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTripleGraph_DividerYOffset()
	 * @model default="0"
	 * @generated
	 */
	int getDividerYOffset();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TripleGraph#getDividerYOffset <em>Divider YOffset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Divider YOffset</em>' attribute.
	 * @see #getDividerYOffset()
	 * @generated
	 */
	void setDividerYOffset(int value);

} // TripleGraph
