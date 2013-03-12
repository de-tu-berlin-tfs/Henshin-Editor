/**
 */
package tgg.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import tgg.ImportedPackage;
import tgg.TGGPackage;
import tgg.TripleComponent;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Imported Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tgg.impl.ImportedPackageImpl#isLoadWithDefaultValues <em>Load With Default Values</em>}</li>
 *   <li>{@link tgg.impl.ImportedPackageImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link tgg.impl.ImportedPackageImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImportedPackageImpl extends EObjectImpl implements ImportedPackage {
	/**
	 * The default value of the '{@link #isLoadWithDefaultValues() <em>Load With Default Values</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadWithDefaultValues()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOAD_WITH_DEFAULT_VALUES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoadWithDefaultValues() <em>Load With Default Values</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadWithDefaultValues()
	 * @generated
	 * @ordered
	 */
	protected boolean loadWithDefaultValues = LOAD_WITH_DEFAULT_VALUES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected EPackage package_;

	/**
	 * The default value of the '{@link #getComponent() <em>Component</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected static final TripleComponent COMPONENT_EDEFAULT = TripleComponent.SOURCE;

	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected TripleComponent component = COMPONENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportedPackageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGPackage.Literals.IMPORTED_PACKAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLoadWithDefaultValues() {
		return loadWithDefaultValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadWithDefaultValues(boolean newLoadWithDefaultValues) {
		boolean oldLoadWithDefaultValues = loadWithDefaultValues;
		loadWithDefaultValues = newLoadWithDefaultValues;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES, oldLoadWithDefaultValues, loadWithDefaultValues));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getPackage() {
		if (package_ != null && package_.eIsProxy()) {
			InternalEObject oldPackage = (InternalEObject)package_;
			package_ = (EPackage)eResolveProxy(oldPackage);
			if (package_ != oldPackage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.IMPORTED_PACKAGE__PACKAGE, oldPackage, package_));
			}
		}
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(EPackage newPackage) {
		EPackage oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.IMPORTED_PACKAGE__PACKAGE, oldPackage, package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TripleComponent getComponent() {
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(TripleComponent newComponent) {
		TripleComponent oldComponent = component;
		component = newComponent == null ? COMPONENT_EDEFAULT : newComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.IMPORTED_PACKAGE__COMPONENT, oldComponent, component));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TGGPackage.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES:
				return isLoadWithDefaultValues();
			case TGGPackage.IMPORTED_PACKAGE__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case TGGPackage.IMPORTED_PACKAGE__COMPONENT:
				return getComponent();
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
			case TGGPackage.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES:
				setLoadWithDefaultValues((Boolean)newValue);
				return;
			case TGGPackage.IMPORTED_PACKAGE__PACKAGE:
				setPackage((EPackage)newValue);
				return;
			case TGGPackage.IMPORTED_PACKAGE__COMPONENT:
				setComponent((TripleComponent)newValue);
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
			case TGGPackage.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES:
				setLoadWithDefaultValues(LOAD_WITH_DEFAULT_VALUES_EDEFAULT);
				return;
			case TGGPackage.IMPORTED_PACKAGE__PACKAGE:
				setPackage((EPackage)null);
				return;
			case TGGPackage.IMPORTED_PACKAGE__COMPONENT:
				setComponent(COMPONENT_EDEFAULT);
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
			case TGGPackage.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES:
				return loadWithDefaultValues != LOAD_WITH_DEFAULT_VALUES_EDEFAULT;
			case TGGPackage.IMPORTED_PACKAGE__PACKAGE:
				return package_ != null;
			case TGGPackage.IMPORTED_PACKAGE__COMPONENT:
				return component != COMPONENT_EDEFAULT;
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
		result.append(" (loadWithDefaultValues: ");
		result.append(loadWithDefaultValues);
		result.append(", component: ");
		result.append(component);
		result.append(')');
		return result.toString();
	}

} //ImportedPackageImpl
