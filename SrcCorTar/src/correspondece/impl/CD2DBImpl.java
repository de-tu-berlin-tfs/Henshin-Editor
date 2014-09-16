/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece.impl;

import correspondece.CD2DB;
import correspondece.CorrespondecePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import source.ClassDiagram;

import target.Database;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CD2DB</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link correspondece.impl.CD2DBImpl#getDb2cd <em>Db2cd</em>}</li>
 *   <li>{@link correspondece.impl.CD2DBImpl#getCd2db <em>Cd2db</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CD2DBImpl extends EObjectImpl implements CD2DB {
	/**
	 * The cached value of the '{@link #getDb2cd() <em>Db2cd</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDb2cd()
	 * @generated
	 * @ordered
	 */
	protected ClassDiagram db2cd;

	/**
	 * The cached value of the '{@link #getCd2db() <em>Cd2db</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCd2db()
	 * @generated
	 * @ordered
	 */
	protected Database cd2db;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CD2DBImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondecePackage.Literals.CD2DB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassDiagram getDb2cd() {
		if (db2cd != null && db2cd.eIsProxy()) {
			InternalEObject oldDb2cd = (InternalEObject)db2cd;
			db2cd = (ClassDiagram)eResolveProxy(oldDb2cd);
			if (db2cd != oldDb2cd) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.CD2DB__DB2CD, oldDb2cd, db2cd));
			}
		}
		return db2cd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassDiagram basicGetDb2cd() {
		return db2cd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDb2cd(ClassDiagram newDb2cd) {
		ClassDiagram oldDb2cd = db2cd;
		db2cd = newDb2cd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.CD2DB__DB2CD, oldDb2cd, db2cd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Database getCd2db() {
		if (cd2db != null && cd2db.eIsProxy()) {
			InternalEObject oldCd2db = (InternalEObject)cd2db;
			cd2db = (Database)eResolveProxy(oldCd2db);
			if (cd2db != oldCd2db) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.CD2DB__CD2DB, oldCd2db, cd2db));
			}
		}
		return cd2db;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Database basicGetCd2db() {
		return cd2db;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCd2db(Database newCd2db) {
		Database oldCd2db = cd2db;
		cd2db = newCd2db;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.CD2DB__CD2DB, oldCd2db, cd2db));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondecePackage.CD2DB__DB2CD:
				if (resolve) return getDb2cd();
				return basicGetDb2cd();
			case CorrespondecePackage.CD2DB__CD2DB:
				if (resolve) return getCd2db();
				return basicGetCd2db();
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
			case CorrespondecePackage.CD2DB__DB2CD:
				setDb2cd((ClassDiagram)newValue);
				return;
			case CorrespondecePackage.CD2DB__CD2DB:
				setCd2db((Database)newValue);
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
			case CorrespondecePackage.CD2DB__DB2CD:
				setDb2cd((ClassDiagram)null);
				return;
			case CorrespondecePackage.CD2DB__CD2DB:
				setCd2db((Database)null);
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
			case CorrespondecePackage.CD2DB__DB2CD:
				return db2cd != null;
			case CorrespondecePackage.CD2DB__CD2DB:
				return cd2db != null;
		}
		return super.eIsSet(featureID);
	}

} //CD2DBImpl
