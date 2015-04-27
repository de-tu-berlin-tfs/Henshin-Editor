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
package de.tub.tfs.henshin.model.layout;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.NodeLayout#isHide <em>Hide</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.NodeLayout#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.NodeLayout#getColor <em>Color</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.NodeLayout#isMulti <em>Multi</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getNodeLayout()
 * @model
 * @generated
 */
public interface NodeLayout extends Layout {
        /**
	 * Returns the value of the '<em><b>Hide</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Hide</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Hide</em>' attribute.
	 * @see #setHide(boolean)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getNodeLayout_Hide()
	 * @model default="true"
	 * @generated
	 */
        boolean isHide();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.NodeLayout#isHide <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hide</em>' attribute.
	 * @see #isHide()
	 * @generated
	 */
        void setHide(boolean value);

        /**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getNodeLayout_Enabled()
	 * @model default="true"
	 * @generated
	 */
        boolean isEnabled();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.NodeLayout#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
        void setEnabled(boolean value);

        /**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Color</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(int)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getNodeLayout_Color()
	 * @model
	 * @generated
	 */
        int getColor();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.NodeLayout#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
        void setColor(int value);

        /**
	 * Returns the value of the '<em><b>Multi</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Multi</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi</em>' attribute.
	 * @see #setMulti(boolean)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getNodeLayout_Multi()
	 * @model default="false"
	 * @generated
	 */
        boolean isMulti();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.NodeLayout#isMulti <em>Multi</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multi</em>' attribute.
	 * @see #isMulti()
	 * @generated
	 */
        void setMulti(boolean value);

} // NodeLayout
