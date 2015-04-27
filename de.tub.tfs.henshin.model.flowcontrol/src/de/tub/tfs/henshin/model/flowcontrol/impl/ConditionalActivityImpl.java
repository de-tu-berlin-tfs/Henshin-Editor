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

import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalActivityImpl#getAltOut <em>Alt Out</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConditionalActivityImpl extends ActivityImpl implements ConditionalActivity {
        /**
	 * The cached value of the '{@link #getAltOut() <em>Alt Out</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getAltOut()
	 * @generated
	 * @ordered
	 */
        protected Transition altOut;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected ConditionalActivityImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        protected EClass eStaticClass() {
		return FlowControlPackage.Literals.CONDITIONAL_ACTIVITY;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Transition getAltOut() {
		if (altOut != null && altOut.eIsProxy()) {
			InternalEObject oldAltOut = (InternalEObject)altOut;
			altOut = (Transition)eResolveProxy(oldAltOut);
			if (altOut != oldAltOut) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT, oldAltOut, altOut));
			}
		}
		return altOut;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Transition basicGetAltOut() {
		return altOut;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setAltOut(Transition newAltOut) {
		Transition oldAltOut = altOut;
		altOut = newAltOut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT, oldAltOut, altOut));
	}

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated NOT		
         */
        public String getAlternativeLabel() {
                // TODO: implement this method
                // Ensure that you remove @generated or mark it @generated NOT
            return "false";
        }

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT:
				if (resolve) return getAltOut();
				return basicGetAltOut();
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
			case FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT:
				setAltOut((Transition)newValue);
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
			case FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT:
				setAltOut((Transition)null);
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
			case FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT:
				return altOut != null;
		}
		return super.eIsSet(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ConditionalElement.class) {
			switch (derivedFeatureID) {
				case FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT: return FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ConditionalElement.class) {
			switch (baseFeatureID) {
				case FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT: return FlowControlPackage.CONDITIONAL_ACTIVITY__ALT_OUT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ConditionalActivityImpl
