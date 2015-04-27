/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.TransitionImpl#getNext <em>Next</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.TransitionImpl#getPrevous <em>Prevous</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends EObjectImpl implements Transition {
        /**
	 * The cached value of the '{@link #getNext() <em>Next</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getNext()
	 * @generated
	 * @ordered
	 */
        protected FlowElement next;

        /**
	 * The cached value of the '{@link #getPrevous() <em>Prevous</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getPrevous()
	 * @generated
	 * @ordered
	 */
        protected FlowElement prevous;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected TransitionImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        protected EClass eStaticClass() {
		return FlowControlPackage.Literals.TRANSITION;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowElement getNext() {
		if (next != null && next.eIsProxy()) {
			InternalEObject oldNext = (InternalEObject)next;
			next = (FlowElement)eResolveProxy(oldNext);
			if (next != oldNext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.TRANSITION__NEXT, oldNext, next));
			}
		}
		return next;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowElement basicGetNext() {
		return next;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setNext(FlowElement newNext) {
		FlowElement oldNext = next;
		next = newNext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.TRANSITION__NEXT, oldNext, next));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowElement getPrevous() {
		if (prevous != null && prevous.eIsProxy()) {
			InternalEObject oldPrevous = (InternalEObject)prevous;
			prevous = (FlowElement)eResolveProxy(oldPrevous);
			if (prevous != oldPrevous) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.TRANSITION__PREVOUS, oldPrevous, prevous));
			}
		}
		return prevous;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowElement basicGetPrevous() {
		return prevous;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setPrevous(FlowElement newPrevous) {
		FlowElement oldPrevous = prevous;
		prevous = newPrevous;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.TRANSITION__PREVOUS, oldPrevous, prevous));
	}

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated NOT
         */
        public boolean isAlternate() {
                // TODO: implement this method
                // Ensure that you remove @generated or mark it @generated NOT
                return getPrevous() instanceof ConditionalElement && getPrevous().getOut() != this;
        }

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.TRANSITION__NEXT:
				if (resolve) return getNext();
				return basicGetNext();
			case FlowControlPackage.TRANSITION__PREVOUS:
				if (resolve) return getPrevous();
				return basicGetPrevous();
		}
		return super.eGet(featureID, resolve, coreType);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FlowControlPackage.TRANSITION__NEXT:
				setNext((FlowElement)newValue);
				return;
			case FlowControlPackage.TRANSITION__PREVOUS:
				setPrevous((FlowElement)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public void eUnset(int featureID) {
		switch (featureID) {
			case FlowControlPackage.TRANSITION__NEXT:
				setNext((FlowElement)null);
				return;
			case FlowControlPackage.TRANSITION__PREVOUS:
				setPrevous((FlowElement)null);
				return;
		}
		super.eUnset(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FlowControlPackage.TRANSITION__NEXT:
				return next != null;
			case FlowControlPackage.TRANSITION__PREVOUS:
				return prevous != null;
		}
		return super.eIsSet(featureID);
	}

} //TransitionImpl
