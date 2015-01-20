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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.subtree.SubtreeFactory
 * @model kind="package"
 * @generated
 */
public interface SubtreePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "subtree";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://subtree";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "subtree";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SubtreePackage eINSTANCE = de.tub.tfs.henshin.model.subtree.impl.SubtreePackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.subtree.impl.SubtreeImpl <em>Subtree</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.subtree.impl.SubtreeImpl
	 * @see de.tub.tfs.henshin.model.subtree.impl.SubtreePackageImpl#getSubtree()
	 * @generated
	 */
	int SUBTREE = 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__INCOMING = 0;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__OUTGOING = 1;

	/**
	 * The feature id for the '<em><b>Root</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE__ROOT = 2;

	/**
	 * The number of structural features of the '<em>Subtree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTREE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.subtree.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.subtree.impl.EdgeImpl
	 * @see de.tub.tfs.henshin.model.subtree.impl.SubtreePackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__SOURCE_NODE = 2;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TARGET_NODE = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.subtree.Subtree <em>Subtree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subtree</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Subtree
	 * @generated
	 */
	EClass getSubtree();

	/**
	 * Returns the meta object for the reference list '{@link de.tub.tfs.henshin.model.subtree.Subtree#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Subtree#getIncoming()
	 * @see #getSubtree()
	 * @generated
	 */
	EReference getSubtree_Incoming();

	/**
	 * Returns the meta object for the reference list '{@link de.tub.tfs.henshin.model.subtree.Subtree#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Subtree#getOutgoing()
	 * @see #getSubtree()
	 * @generated
	 */
	EReference getSubtree_Outgoing();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.subtree.Subtree#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Root</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Subtree#getRoot()
	 * @see #getSubtree()
	 * @generated
	 */
	EReference getSubtree_Root();

	/**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.subtree.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.subtree.Edge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getSource()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Source();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.subtree.Edge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getTarget()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Target();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.subtree.Edge#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getSourceNode()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.subtree.Edge#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getTargetNode()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_TargetNode();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.subtree.Edge#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see de.tub.tfs.henshin.model.subtree.Edge#getType()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SubtreeFactory getSubtreeFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.subtree.impl.SubtreeImpl <em>Subtree</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.subtree.impl.SubtreeImpl
		 * @see de.tub.tfs.henshin.model.subtree.impl.SubtreePackageImpl#getSubtree()
		 * @generated
		 */
		EClass SUBTREE = eINSTANCE.getSubtree();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBTREE__INCOMING = eINSTANCE.getSubtree_Incoming();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBTREE__OUTGOING = eINSTANCE.getSubtree_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBTREE__ROOT = eINSTANCE.getSubtree_Root();

		/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.subtree.impl.EdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.subtree.impl.EdgeImpl
		 * @see de.tub.tfs.henshin.model.subtree.impl.SubtreePackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__SOURCE = eINSTANCE.getEdge_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TARGET = eINSTANCE.getEdge_Target();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__SOURCE_NODE = eINSTANCE.getEdge_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TARGET_NODE = eINSTANCE.getEdge_TargetNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TYPE = eINSTANCE.getEdge_Type();

	}

} //SubtreePackage
