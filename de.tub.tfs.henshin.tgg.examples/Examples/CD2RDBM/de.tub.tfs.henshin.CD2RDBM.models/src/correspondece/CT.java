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
package correspondece;

import org.eclipse.emf.ecore.EObject;

import target.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CT</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link correspondece.CT#getToClass <em>To Class</em>}</li>
 *   <li>{@link correspondece.CT#getToTable <em>To Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see correspondece.CorrespondecePackage#getCT()
 * @model
 * @generated
 */
public interface CT extends EObject {
	/**
	 * Returns the value of the '<em><b>To Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Class</em>' reference.
	 * @see #setToClass(source.Class)
	 * @see correspondece.CorrespondecePackage#getCT_ToClass()
	 * @model
	 * @generated
	 */
	source.Class getToClass();

	/**
	 * Sets the value of the '{@link correspondece.CT#getToClass <em>To Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Class</em>' reference.
	 * @see #getToClass()
	 * @generated
	 */
	void setToClass(source.Class value);

	/**
	 * Returns the value of the '<em><b>To Table</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To Table</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Table</em>' reference.
	 * @see #setToTable(Table)
	 * @see correspondece.CorrespondecePackage#getCT_ToTable()
	 * @model
	 * @generated
	 */
	Table getToTable();

	/**
	 * Sets the value of the '{@link correspondece.CT#getToTable <em>To Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Table</em>' reference.
	 * @see #getToTable()
	 * @generated
	 */
	void setToTable(Table value);

} // CT
