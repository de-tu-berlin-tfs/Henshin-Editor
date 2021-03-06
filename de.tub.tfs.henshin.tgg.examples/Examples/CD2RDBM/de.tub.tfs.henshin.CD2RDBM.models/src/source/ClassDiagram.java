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
/**
 */
package source;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link source.ClassDiagram#getClass_ <em>Class</em>}</li>
 *   <li>{@link source.ClassDiagram#getAss <em>Ass</em>}</li>
 *   <li>{@link source.ClassDiagram#getPtypes <em>Ptypes</em>}</li>
 * </ul>
 * </p>
 *
 * @see source.SourcePackage#getClassDiagram()
 * @model
 * @generated
 */
public interface ClassDiagram extends EObject {
	/**
	 * Returns the value of the '<em><b>Class</b></em>' containment reference list.
	 * The list contents are of type {@link source.Class}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' containment reference list.
	 * @see source.SourcePackage#getClassDiagram_Class()
	 * @model containment="true"
	 * @generated
	 */
	EList<source.Class> getClass_();

	/**
	 * Returns the value of the '<em><b>Ass</b></em>' containment reference list.
	 * The list contents are of type {@link source.Association}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ass</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ass</em>' containment reference list.
	 * @see source.SourcePackage#getClassDiagram_Ass()
	 * @model containment="true"
	 * @generated
	 */
	EList<Association> getAss();

	/**
	 * Returns the value of the '<em><b>Ptypes</b></em>' containment reference list.
	 * The list contents are of type {@link source.PrimitiveDataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ptypes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ptypes</em>' containment reference list.
	 * @see source.SourcePackage#getClassDiagram_Ptypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<PrimitiveDataType> getPtypes();

} // ClassDiagram
