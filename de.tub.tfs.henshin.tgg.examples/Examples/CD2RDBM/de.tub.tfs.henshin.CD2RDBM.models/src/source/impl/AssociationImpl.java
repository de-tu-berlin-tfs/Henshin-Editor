/**
 */
package source.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import source.Association;
import source.SourcePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link source.impl.AssociationImpl#getName <em>Name</em>}</li>
 *   <li>{@link source.impl.AssociationImpl#getSrc <em>Src</em>}</li>
 *   <li>{@link source.impl.AssociationImpl#getDest <em>Dest</em>}</li>
 *   <li>{@link source.impl.AssociationImpl#getLeftMultiplicity <em>Left Multiplicity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssociationImpl extends MinimalEObjectImpl.Container implements Association {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSrc() <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrc()
	 * @generated
	 * @ordered
	 */
	protected source.Class src;

	/**
	 * The cached value of the '{@link #getDest() <em>Dest</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDest()
	 * @generated
	 * @ordered
	 */
	protected source.Class dest;

	/**
	 * The default value of the '{@link #getLeftMultiplicity() <em>Left Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected static final int LEFT_MULTIPLICITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLeftMultiplicity() <em>Left Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected int leftMultiplicity = LEFT_MULTIPLICITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssociationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcePackage.Literals.ASSOCIATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ASSOCIATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class getSrc() {
		if (src != null && src.eIsProxy()) {
			InternalEObject oldSrc = (InternalEObject)src;
			src = (source.Class)eResolveProxy(oldSrc);
			if (src != oldSrc) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcePackage.ASSOCIATION__SRC, oldSrc, src));
			}
		}
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class basicGetSrc() {
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrc(source.Class newSrc) {
		source.Class oldSrc = src;
		src = newSrc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ASSOCIATION__SRC, oldSrc, src));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class getDest() {
		if (dest != null && dest.eIsProxy()) {
			InternalEObject oldDest = (InternalEObject)dest;
			dest = (source.Class)eResolveProxy(oldDest);
			if (dest != oldDest) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcePackage.ASSOCIATION__DEST, oldDest, dest));
			}
		}
		return dest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class basicGetDest() {
		return dest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDest(source.Class newDest) {
		source.Class oldDest = dest;
		dest = newDest;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ASSOCIATION__DEST, oldDest, dest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLeftMultiplicity() {
		return leftMultiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeftMultiplicity(int newLeftMultiplicity) {
		int oldLeftMultiplicity = leftMultiplicity;
		leftMultiplicity = newLeftMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ASSOCIATION__LEFT_MULTIPLICITY, oldLeftMultiplicity, leftMultiplicity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SourcePackage.ASSOCIATION__NAME:
				return getName();
			case SourcePackage.ASSOCIATION__SRC:
				if (resolve) return getSrc();
				return basicGetSrc();
			case SourcePackage.ASSOCIATION__DEST:
				if (resolve) return getDest();
				return basicGetDest();
			case SourcePackage.ASSOCIATION__LEFT_MULTIPLICITY:
				return getLeftMultiplicity();
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
			case SourcePackage.ASSOCIATION__NAME:
				setName((String)newValue);
				return;
			case SourcePackage.ASSOCIATION__SRC:
				setSrc((source.Class)newValue);
				return;
			case SourcePackage.ASSOCIATION__DEST:
				setDest((source.Class)newValue);
				return;
			case SourcePackage.ASSOCIATION__LEFT_MULTIPLICITY:
				setLeftMultiplicity((Integer)newValue);
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
			case SourcePackage.ASSOCIATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SourcePackage.ASSOCIATION__SRC:
				setSrc((source.Class)null);
				return;
			case SourcePackage.ASSOCIATION__DEST:
				setDest((source.Class)null);
				return;
			case SourcePackage.ASSOCIATION__LEFT_MULTIPLICITY:
				setLeftMultiplicity(LEFT_MULTIPLICITY_EDEFAULT);
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
			case SourcePackage.ASSOCIATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SourcePackage.ASSOCIATION__SRC:
				return src != null;
			case SourcePackage.ASSOCIATION__DEST:
				return dest != null;
			case SourcePackage.ASSOCIATION__LEFT_MULTIPLICITY:
				return leftMultiplicity != LEFT_MULTIPLICITY_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", leftMultiplicity: ");
		result.append(leftMultiplicity);
		result.append(')');
		return result.toString();
	}

} //AssociationImpl
