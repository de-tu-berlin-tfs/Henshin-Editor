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
package de.tub.tfs.henshin.model.subtree;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Subtree#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Subtree#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Subtree#getRoot <em>Root</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getSubtree()
 * @model
 * @generated
 */
public interface Subtree extends EObject {
	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.subtree.Edge}.
	 * It is bidirectional and its opposite is '{@link de.tub.tfs.henshin.model.subtree.Edge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getSubtree_Incoming()
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Edge> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.subtree.Edge}.
	 * It is bidirectional and its opposite is '{@link de.tub.tfs.henshin.model.subtree.Edge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getSubtree_Outgoing()
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Edge> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Root</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' reference.
	 * @see #setRoot(Node)
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getSubtree_Root()
	 * @model required="true"
	 * @generated
	 */
	Node getRoot();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.subtree.Subtree#getRoot <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(Node value);

} // Subtree
