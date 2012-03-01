/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.layout.impl;

import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow Element Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.FlowElementLayoutImpl#getMapId <em>Map Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FlowElementLayoutImpl extends LayoutImpl implements FlowElementLayout {
        /**
         * The default value of the '{@link #getMapId() <em>Map Id</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getMapId()
         * @generated
         * @ordered
         */
        protected static final int MAP_ID_EDEFAULT = -1;

        /**
         * The cached value of the '{@link #getMapId() <em>Map Id</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getMapId()
         * @generated
         * @ordered
         */
        protected int mapId = MAP_ID_EDEFAULT;

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        protected FlowElementLayoutImpl() {
                super();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        protected EClass eStaticClass() {
                return HenshinLayoutPackage.Literals.FLOW_ELEMENT_LAYOUT;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public int getMapId() {
                return mapId;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public void setMapId(int newMapId) {
                int oldMapId = mapId;
                mapId = newMapId;
                if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__MAP_ID, oldMapId, mapId));
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
                switch (featureID) {
                        case HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__MAP_ID:
                                return getMapId();
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
                        case HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__MAP_ID:
                                setMapId((Integer)newValue);
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
                        case HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__MAP_ID:
                                setMapId(MAP_ID_EDEFAULT);
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
                        case HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__MAP_ID:
                                return mapId != MAP_ID_EDEFAULT;
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
                result.append(" (mapId: ");
                result.append(mapId);
                result.append(')');
                return result.toString();
        }

} //FlowElementLayoutImpl
