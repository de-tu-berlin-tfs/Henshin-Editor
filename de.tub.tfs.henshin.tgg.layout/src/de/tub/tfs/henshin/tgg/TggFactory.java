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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.tgg.TggPackage
 * @generated
 */
public interface TggFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TggFactory eINSTANCE = de.tub.tfs.henshin.tgg.impl.TggFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG</em>'.
	 * @generated
	 */
	TGG createTGG();

	/**
	 * Returns a new object of class '<em>Node Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Layout</em>'.
	 * @generated
	 */
	NodeLayout createNodeLayout();

	/**
	 * Returns a new object of class '<em>Attribute Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Layout</em>'.
	 * @generated
	 */
	AttributeLayout createAttributeLayout();

	/**
	 * Returns a new object of class '<em>Edge Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edge Layout</em>'.
	 * @generated
	 */
	EdgeLayout createEdgeLayout();

	/**
	 * Returns a new object of class '<em>Graph Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graph Layout</em>'.
	 * @generated
	 */
	GraphLayout createGraphLayout();

	/**
	 * Returns a new object of class '<em>TRule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TRule</em>'.
	 * @generated
	 */
	TRule createTRule();

	/**
	 * Returns a new object of class '<em>Crit Pair</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Crit Pair</em>'.
	 * @generated
	 */
	CritPair createCritPair();

	/**
	 * Returns a new object of class '<em>Imported Package</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Imported Package</em>'.
	 * @generated
	 */
	ImportedPackage createImportedPackage();

	/**
	 * Returns a new object of class '<em>Triple Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Triple Graph</em>'.
	 * @generated
	 */
	TripleGraph createTripleGraph();

	/**
	 * Returns a new object of class '<em>TNode</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TNode</em>'.
	 * @generated
	 */
	TNode createTNode();

	/**
	 * Returns a new object of class '<em>TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule</em>'.
	 * @generated
	 */
	TGGRule createTGGRule();

	/**
	 * Returns a new object of class '<em>TEdge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TEdge</em>'.
	 * @generated
	 */
	TEdge createTEdge();

	/**
	 * Returns a new object of class '<em>TAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TAttribute</em>'.
	 * @generated
	 */
	TAttribute createTAttribute();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TggPackage getTggPackage();

} //TggFactory
