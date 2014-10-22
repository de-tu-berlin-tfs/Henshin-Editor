/**
 */
package correspondece.impl;

import correspondece.CT;
import correspondece.CorrespondecePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import target.Table;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CT</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link correspondece.impl.CTImpl#getToClass <em>To Class</em>}</li>
 *   <li>{@link correspondece.impl.CTImpl#getToTable <em>To Table</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CTImpl extends MinimalEObjectImpl.Container implements CT {
	/**
	 * The cached value of the '{@link #getToClass() <em>To Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToClass()
	 * @generated
	 * @ordered
	 */
	protected source.Class toClass;

	/**
	 * The cached value of the '{@link #getToTable() <em>To Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToTable()
	 * @generated
	 * @ordered
	 */
	protected Table toTable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CTImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondecePackage.Literals.CT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class getToClass() {
		if (toClass != null && toClass.eIsProxy()) {
			InternalEObject oldToClass = (InternalEObject)toClass;
			toClass = (source.Class)eResolveProxy(oldToClass);
			if (toClass != oldToClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.CT__TO_CLASS, oldToClass, toClass));
			}
		}
		return toClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class basicGetToClass() {
		return toClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToClass(source.Class newToClass) {
		source.Class oldToClass = toClass;
		toClass = newToClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.CT__TO_CLASS, oldToClass, toClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Table getToTable() {
		if (toTable != null && toTable.eIsProxy()) {
			InternalEObject oldToTable = (InternalEObject)toTable;
			toTable = (Table)eResolveProxy(oldToTable);
			if (toTable != oldToTable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.CT__TO_TABLE, oldToTable, toTable));
			}
		}
		return toTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Table basicGetToTable() {
		return toTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToTable(Table newToTable) {
		Table oldToTable = toTable;
		toTable = newToTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.CT__TO_TABLE, oldToTable, toTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondecePackage.CT__TO_CLASS:
				if (resolve) return getToClass();
				return basicGetToClass();
			case CorrespondecePackage.CT__TO_TABLE:
				if (resolve) return getToTable();
				return basicGetToTable();
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
			case CorrespondecePackage.CT__TO_CLASS:
				setToClass((source.Class)newValue);
				return;
			case CorrespondecePackage.CT__TO_TABLE:
				setToTable((Table)newValue);
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
			case CorrespondecePackage.CT__TO_CLASS:
				setToClass((source.Class)null);
				return;
			case CorrespondecePackage.CT__TO_TABLE:
				setToTable((Table)null);
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
			case CorrespondecePackage.CT__TO_CLASS:
				return toClass != null;
			case CorrespondecePackage.CT__TO_TABLE:
				return toTable != null;
		}
		return super.eIsSet(featureID);
	}

} //CTImpl
