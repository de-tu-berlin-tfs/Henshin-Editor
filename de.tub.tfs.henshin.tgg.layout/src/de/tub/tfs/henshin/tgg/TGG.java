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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getSrcroot <em>Srcroot</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getTarroot <em>Tarroot</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getSource <em>Source</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getCorresp <em>Corresp</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getTarget <em>Target</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getNodelayouts <em>Nodelayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getEdgelayouts <em>Edgelayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getGraphlayouts <em>Graphlayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getTRules <em>TRules</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getCritPairs <em>Crit Pairs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getSourcePkgs <em>Source Pkgs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getCorrespondencePkgs <em>Correspondence Pkgs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getTargetPkgs <em>Target Pkgs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGG#getImportedPkgs <em>Imported Pkgs</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG()
 * @model
 * @generated
 */
public interface TGG extends Module {
	/**
	 * Returns the value of the '<em><b>Srcroot</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Srcroot</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srcroot</em>' reference.
	 * @see #setSrcroot(EObject)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Srcroot()
	 * @model
	 * @deprecated
	 */
	EObject getSrcroot();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGG#getSrcroot <em>Srcroot</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srcroot</em>' reference.
	 * @see #getSrcroot()
	 * @deprecated
	 */
	void setSrcroot(EObject value);

	/**
	 * Returns the value of the '<em><b>Tarroot</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tarroot</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tarroot</em>' reference.
	 * @see #setTarroot(EObject)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Tarroot()
	 * @model
	 * @deprecated
	 */
	EObject getTarroot();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGG#getTarroot <em>Tarroot</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tarroot</em>' reference.
	 * @see #getTarroot()
	 * @deprecated
	 */
	void setTarroot(EObject value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EPackage)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Source()
	 * @model
	 * @deprecated
	 */
	EPackage getSource();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGG#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @deprecated
	 */
	void setSource(EPackage value);

	/**
	 * Returns the value of the '<em><b>Corresp</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Corresp</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corresp</em>' reference.
	 * @see #setCorresp(EPackage)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Corresp()
	 * @model
	 * @deprecated
	 */
	EPackage getCorresp();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGG#getCorresp <em>Corresp</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corresp</em>' reference.
	 * @see #getCorresp()
	 * @deprecated
	 */
	void setCorresp(EPackage value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EPackage)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Target()
	 * @model
	 * @deprecated
	 */
	EPackage getTarget();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGG#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @deprecated
	 */
	void setTarget(EPackage value);

	/**
	 * Returns the value of the '<em><b>Nodelayouts</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.NodeLayout}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodelayouts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodelayouts</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Nodelayouts()
	 * @model containment="true"
	 * @generated
	 */
	EList<NodeLayout> getNodelayouts();

	/**
	 * Returns the value of the '<em><b>Edgelayouts</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.EdgeLayout}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edgelayouts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edgelayouts</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Edgelayouts()
	 * @model containment="true"
	 * @generated
	 */
	EList<EdgeLayout> getEdgelayouts();

	/**
	 * Returns the value of the '<em><b>Graphlayouts</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.GraphLayout}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graphlayouts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphlayouts</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_Graphlayouts()
	 * @model containment="true"
	 * @generated
	 */
	EList<GraphLayout> getGraphlayouts();

	/**
	 * Returns the value of the '<em><b>TRules</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.TRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>TRules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>TRules</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_TRules()
	 * @model containment="true"
	 * @generated
	 */
	EList<TRule> getTRules();

	/**
	 * Returns the value of the '<em><b>Crit Pairs</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.CritPair}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Crit Pairs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Crit Pairs</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_CritPairs()
	 * @model containment="true"
	 * @generated
	 */
	EList<CritPair> getCritPairs();

	/**
	 * Returns the value of the '<em><b>Source Pkgs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Pkgs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Pkgs</em>' reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_SourcePkgs()
	 * @model
	 * @deprecated
	 */
	EList<EPackage> getSourcePkgs();

	/**
	 * Returns the value of the '<em><b>Correspondence Pkgs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correspondence Pkgs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondence Pkgs</em>' reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_CorrespondencePkgs()
	 * @model
	 * @deprecated
	 */
	EList<EPackage> getCorrespondencePkgs();

	/**
	 * Returns the value of the '<em><b>Target Pkgs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Pkgs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Pkgs</em>' reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_TargetPkgs()
	 * @model
	 * @deprecated
	 */
	EList<EPackage> getTargetPkgs();

	/**
	 * Returns the value of the '<em><b>Imported Pkgs</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.tgg.ImportedPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imported Pkgs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imported Pkgs</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGG_ImportedPkgs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ImportedPackage> getImportedPkgs();

} // TGG
