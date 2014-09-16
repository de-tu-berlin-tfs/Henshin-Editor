/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.layout.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl#isHide <em>Hide</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl#getColor <em>Color</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl#isMulti <em>Multi</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeLayoutImpl extends LayoutImpl implements NodeLayout {
        /**
	 * The default value of the '{@link #isHide() <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #isHide()
	 * @generated
	 * @ordered
	 */
        protected static final boolean HIDE_EDEFAULT = true;

        /**
	 * The cached value of the '{@link #isHide() <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #isHide()
	 * @generated
	 * @ordered
	 */
        protected boolean hide = HIDE_EDEFAULT;

        /**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
        protected static final boolean ENABLED_EDEFAULT = true;

        /**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
        protected boolean enabled = ENABLED_EDEFAULT;

        /**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
        protected static final int COLOR_EDEFAULT = 0;

        /**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
        protected int color = COLOR_EDEFAULT;

        /**
	 * The default value of the '{@link #isMulti() <em>Multi</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #isMulti()
	 * @generated
	 * @ordered
	 */
        protected static final boolean MULTI_EDEFAULT = false;

        /**
	 * The cached value of the '{@link #isMulti() <em>Multi</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #isMulti()
	 * @generated
	 * @ordered
	 */
        protected boolean multi = MULTI_EDEFAULT;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected NodeLayoutImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        protected EClass eStaticClass() {
		return HenshinLayoutPackage.Literals.NODE_LAYOUT;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public boolean isHide() {
		return hide;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setHide(boolean newHide) {
		boolean oldHide = hide;
		hide = newHide;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.NODE_LAYOUT__HIDE, oldHide, hide));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public boolean isEnabled() {
		return enabled;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.NODE_LAYOUT__ENABLED, oldEnabled, enabled));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public int getColor() {
		return color;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setColor(int newColor) {
		int oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.NODE_LAYOUT__COLOR, oldColor, color));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public boolean isMulti() {
		return multi;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setMulti(boolean newMulti) {
		boolean oldMulti = multi;
		multi = newMulti;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.NODE_LAYOUT__MULTI, oldMulti, multi));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinLayoutPackage.NODE_LAYOUT__HIDE:
				return isHide();
			case HenshinLayoutPackage.NODE_LAYOUT__ENABLED:
				return isEnabled();
			case HenshinLayoutPackage.NODE_LAYOUT__COLOR:
				return getColor();
			case HenshinLayoutPackage.NODE_LAYOUT__MULTI:
				return isMulti();
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
			case HenshinLayoutPackage.NODE_LAYOUT__HIDE:
				setHide((Boolean)newValue);
				return;
			case HenshinLayoutPackage.NODE_LAYOUT__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case HenshinLayoutPackage.NODE_LAYOUT__COLOR:
				setColor((Integer)newValue);
				return;
			case HenshinLayoutPackage.NODE_LAYOUT__MULTI:
				setMulti((Boolean)newValue);
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
			case HenshinLayoutPackage.NODE_LAYOUT__HIDE:
				setHide(HIDE_EDEFAULT);
				return;
			case HenshinLayoutPackage.NODE_LAYOUT__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case HenshinLayoutPackage.NODE_LAYOUT__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case HenshinLayoutPackage.NODE_LAYOUT__MULTI:
				setMulti(MULTI_EDEFAULT);
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
			case HenshinLayoutPackage.NODE_LAYOUT__HIDE:
				return hide != HIDE_EDEFAULT;
			case HenshinLayoutPackage.NODE_LAYOUT__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case HenshinLayoutPackage.NODE_LAYOUT__COLOR:
				return color != COLOR_EDEFAULT;
			case HenshinLayoutPackage.NODE_LAYOUT__MULTI:
				return multi != MULTI_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (hide: ");
		result.append(hide);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", color: ");
		result.append(color);
		result.append(", multi: ");
		result.append(multi);
		result.append(')');
		return result.toString();
	}

} //NodeLayoutImpl
