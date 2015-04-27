/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Conditional Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalElementImpl#getAltOut <em>Alt Out</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ConditionalElementImpl extends FlowElementImpl implements
		ConditionalElement {
	/**
	 * The cached value of the '{@link #getAltOut() <em>Alt Out</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAltOut()
	 * @generated
	 * @ordered
	 */
	protected Transition altOut;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FlowControlPackage.Literals.CONDITIONAL_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transition getAltOut() {
		if (altOut != null && altOut.eIsProxy()) {
			InternalEObject oldAltOut = (InternalEObject)altOut;
			altOut = (Transition)eResolveProxy(oldAltOut);
			if (altOut != oldAltOut) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT, oldAltOut, altOut));
			}
		}
		return altOut;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transition basicGetAltOut() {
		return altOut;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setAltOut(Transition newAltOut) {
		Transition oldAltOut = altOut;
		altOut = newAltOut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT, oldAltOut, altOut));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlternativeLabel() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT:
				if (resolve) return getAltOut();
				return basicGetAltOut();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT:
				setAltOut((Transition)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT:
				setAltOut((Transition)null);
				return;
		}
		super.eUnset(featureID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl#getOutGoings()
	 */
	@Override
	public EList<Transition> getOutGoings() {
		EList<Transition> outs = super.getOutGoings();

		if (getAltOut() != null) {
			outs.add(getAltOut());
		}

		return outs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FlowControlPackage.CONDITIONAL_ELEMENT__ALT_OUT:
				return altOut != null;
		}
		return super.eIsSet(featureID);
	}

} // ConditionalElementImpl
