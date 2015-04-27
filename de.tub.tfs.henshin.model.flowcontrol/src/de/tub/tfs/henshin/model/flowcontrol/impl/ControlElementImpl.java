/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import org.eclipse.emf.ecore.EClass;

import de.tub.tfs.henshin.model.flowcontrol.ControlElement;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Control Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class ControlElementImpl extends FlowElementImpl implements ControlElement {
        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected ControlElementImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        protected EClass eStaticClass() {
		return FlowControlPackage.Literals.CONTROL_ELEMENT;
	}

} //ControlElementImpl
