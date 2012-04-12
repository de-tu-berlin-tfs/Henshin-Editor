/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package target.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import target.Column;
import target.FKey;
import target.Table;
import target.TargetPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>FKey</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link target.impl.FKeyImpl#getFcols <em>Fcols</em>}</li>
 *   <li>{@link target.impl.FKeyImpl#getRefernces <em>Refernces</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FKeyImpl extends EObjectImpl implements FKey {
	/**
	 * The cached value of the '{@link #getFcols() <em>Fcols</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFcols()
	 * @generated
	 * @ordered
	 */
	protected EList<Column> fcols;

	/**
	 * The cached value of the '{@link #getRefernces() <em>Refernces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefernces()
	 * @generated
	 * @ordered
	 */
	protected EList<Table> refernces;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FKeyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TargetPackage.Literals.FKEY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Column> getFcols() {
		if (fcols == null) {
			fcols = new EObjectContainmentEList<Column>(Column.class, this, TargetPackage.FKEY__FCOLS);
		}
		return fcols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Table> getRefernces() {
		if (refernces == null) {
			refernces = new EObjectContainmentEList<Table>(Table.class, this, TargetPackage.FKEY__REFERNCES);
		}
		return refernces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TargetPackage.FKEY__FCOLS:
				return ((InternalEList<?>)getFcols()).basicRemove(otherEnd, msgs);
			case TargetPackage.FKEY__REFERNCES:
				return ((InternalEList<?>)getRefernces()).basicRemove(otherEnd, msgs);
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
			case TargetPackage.FKEY__FCOLS:
				return getFcols();
			case TargetPackage.FKEY__REFERNCES:
				return getRefernces();
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
			case TargetPackage.FKEY__FCOLS:
				getFcols().clear();
				getFcols().addAll((Collection<? extends Column>)newValue);
				return;
			case TargetPackage.FKEY__REFERNCES:
				getRefernces().clear();
				getRefernces().addAll((Collection<? extends Table>)newValue);
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
			case TargetPackage.FKEY__FCOLS:
				getFcols().clear();
				return;
			case TargetPackage.FKEY__REFERNCES:
				getRefernces().clear();
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
			case TargetPackage.FKEY__FCOLS:
				return fcols != null && !fcols.isEmpty();
			case TargetPackage.FKEY__REFERNCES:
				return refernces != null && !refernces.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FKeyImpl
