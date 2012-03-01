/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.layout.impl;

import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Layout System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.LayoutSystemImpl#getLayouts <em>Layouts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LayoutSystemImpl extends EObjectImpl implements LayoutSystem {
        /**
         * The cached value of the '{@link #getLayouts() <em>Layouts</em>}' containment reference list.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getLayouts()
         * @generated
         * @ordered
         */
        protected EList<Layout> layouts;

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        protected LayoutSystemImpl() {
                super();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        protected EClass eStaticClass() {
                return HenshinLayoutPackage.Literals.LAYOUT_SYSTEM;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public EList<Layout> getLayouts() {
                if (layouts == null) {
                        layouts = new EObjectContainmentEList<Layout>(Layout.class, this, HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS);
                }
                return layouts;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
                switch (featureID) {
                        case HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS:
                                return ((InternalEList<?>)getLayouts()).basicRemove(otherEnd, msgs);
                }
                return super.eInverseRemove(otherEnd, featureID, msgs);
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
                switch (featureID) {
                        case HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS:
                                return getLayouts();
                }
                return super.eGet(featureID, resolve, coreType);
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(int featureID, Object newValue) {
                switch (featureID) {
                        case HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS:
                                getLayouts().clear();
                                getLayouts().addAll((Collection<? extends Layout>)newValue);
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
                        case HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS:
                                getLayouts().clear();
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
                        case HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS:
                                return layouts != null && !layouts.isEmpty();
                }
                return super.eIsSet(featureID);
        }

} //LayoutSystemImpl
