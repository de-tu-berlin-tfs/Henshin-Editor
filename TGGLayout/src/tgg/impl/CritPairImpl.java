/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;

import tgg.CritPair;
import tgg.TGGPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Crit Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tgg.impl.CritPairImpl#getOverlapping <em>Overlapping</em>}</li>
 *   <li>{@link tgg.impl.CritPairImpl#getRule1 <em>Rule1</em>}</li>
 *   <li>{@link tgg.impl.CritPairImpl#getRule2 <em>Rule2</em>}</li>
 *   <li>{@link tgg.impl.CritPairImpl#getMappingsOverToRule1 <em>Mappings Over To Rule1</em>}</li>
 *   <li>{@link tgg.impl.CritPairImpl#getMappingsOverToRule2 <em>Mappings Over To Rule2</em>}</li>
 *   <li>{@link tgg.impl.CritPairImpl#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}</li>
 *   <li>{@link tgg.impl.CritPairImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CritPairImpl extends EObjectImpl implements CritPair {
	/**
	 * The cached value of the '{@link #getOverlapping() <em>Overlapping</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverlapping()
	 * @generated
	 * @ordered
	 */
	protected Graph overlapping;

	/**
	 * The cached value of the '{@link #getRule1() <em>Rule1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule1()
	 * @generated
	 * @ordered
	 */
	protected Rule rule1;

	/**
	 * The cached value of the '{@link #getRule2() <em>Rule2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule2()
	 * @generated
	 * @ordered
	 */
	protected Rule rule2;

	/**
	 * The cached value of the '{@link #getMappingsOverToRule1() <em>Mappings Over To Rule1</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingsOverToRule1()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappingsOverToRule1;

	/**
	 * The cached value of the '{@link #getMappingsOverToRule2() <em>Mappings Over To Rule2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingsOverToRule2()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappingsOverToRule2;

	/**
	 * The cached value of the '{@link #getMappingsRule1ToRule2() <em>Mappings Rule1 To Rule2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingsRule1ToRule2()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappingsRule1ToRule2;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "name";

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CritPairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGPackage.Literals.CRIT_PAIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getOverlapping() {
		if (overlapping != null && overlapping.eIsProxy()) {
			InternalEObject oldOverlapping = (InternalEObject)overlapping;
			overlapping = (Graph)eResolveProxy(oldOverlapping);
			if (overlapping != oldOverlapping) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.CRIT_PAIR__OVERLAPPING, oldOverlapping, overlapping));
			}
		}
		return overlapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph basicGetOverlapping() {
		return overlapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverlapping(Graph newOverlapping) {
		Graph oldOverlapping = overlapping;
		overlapping = newOverlapping;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.CRIT_PAIR__OVERLAPPING, oldOverlapping, overlapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule1() {
		if (rule1 != null && rule1.eIsProxy()) {
			InternalEObject oldRule1 = (InternalEObject)rule1;
			rule1 = (Rule)eResolveProxy(oldRule1);
			if (rule1 != oldRule1) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.CRIT_PAIR__RULE1, oldRule1, rule1));
			}
		}
		return rule1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRule1() {
		return rule1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule1(Rule newRule1) {
		Rule oldRule1 = rule1;
		rule1 = newRule1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.CRIT_PAIR__RULE1, oldRule1, rule1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule2() {
		if (rule2 != null && rule2.eIsProxy()) {
			InternalEObject oldRule2 = (InternalEObject)rule2;
			rule2 = (Rule)eResolveProxy(oldRule2);
			if (rule2 != oldRule2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.CRIT_PAIR__RULE2, oldRule2, rule2));
			}
		}
		return rule2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRule2() {
		return rule2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule2(Rule newRule2) {
		Rule oldRule2 = rule2;
		rule2 = newRule2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.CRIT_PAIR__RULE2, oldRule2, rule2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappingsOverToRule1() {
		if (mappingsOverToRule1 == null) {
			mappingsOverToRule1 = new EObjectContainmentEList<Mapping>(Mapping.class, this, TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1);
		}
		return mappingsOverToRule1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappingsOverToRule2() {
		if (mappingsOverToRule2 == null) {
			mappingsOverToRule2 = new EObjectContainmentEList<Mapping>(Mapping.class, this, TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2);
		}
		return mappingsOverToRule2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappingsRule1ToRule2() {
		if (mappingsRule1ToRule2 == null) {
			mappingsRule1ToRule2 = new EObjectContainmentEList<Mapping>(Mapping.class, this, TGGPackage.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2);
		}
		return mappingsRule1ToRule2;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.CRIT_PAIR__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1:
				return ((InternalEList<?>)getMappingsOverToRule1()).basicRemove(otherEnd, msgs);
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2:
				return ((InternalEList<?>)getMappingsOverToRule2()).basicRemove(otherEnd, msgs);
			case TGGPackage.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2:
				return ((InternalEList<?>)getMappingsRule1ToRule2()).basicRemove(otherEnd, msgs);
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
			case TGGPackage.CRIT_PAIR__OVERLAPPING:
				if (resolve) return getOverlapping();
				return basicGetOverlapping();
			case TGGPackage.CRIT_PAIR__RULE1:
				if (resolve) return getRule1();
				return basicGetRule1();
			case TGGPackage.CRIT_PAIR__RULE2:
				if (resolve) return getRule2();
				return basicGetRule2();
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1:
				return getMappingsOverToRule1();
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2:
				return getMappingsOverToRule2();
			case TGGPackage.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2:
				return getMappingsRule1ToRule2();
			case TGGPackage.CRIT_PAIR__NAME:
				return getName();
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
			case TGGPackage.CRIT_PAIR__OVERLAPPING:
				setOverlapping((Graph)newValue);
				return;
			case TGGPackage.CRIT_PAIR__RULE1:
				setRule1((Rule)newValue);
				return;
			case TGGPackage.CRIT_PAIR__RULE2:
				setRule2((Rule)newValue);
				return;
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1:
				getMappingsOverToRule1().clear();
				getMappingsOverToRule1().addAll((Collection<? extends Mapping>)newValue);
				return;
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2:
				getMappingsOverToRule2().clear();
				getMappingsOverToRule2().addAll((Collection<? extends Mapping>)newValue);
				return;
			case TGGPackage.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2:
				getMappingsRule1ToRule2().clear();
				getMappingsRule1ToRule2().addAll((Collection<? extends Mapping>)newValue);
				return;
			case TGGPackage.CRIT_PAIR__NAME:
				setName((String)newValue);
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
			case TGGPackage.CRIT_PAIR__OVERLAPPING:
				setOverlapping((Graph)null);
				return;
			case TGGPackage.CRIT_PAIR__RULE1:
				setRule1((Rule)null);
				return;
			case TGGPackage.CRIT_PAIR__RULE2:
				setRule2((Rule)null);
				return;
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1:
				getMappingsOverToRule1().clear();
				return;
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2:
				getMappingsOverToRule2().clear();
				return;
			case TGGPackage.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2:
				getMappingsRule1ToRule2().clear();
				return;
			case TGGPackage.CRIT_PAIR__NAME:
				setName(NAME_EDEFAULT);
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
			case TGGPackage.CRIT_PAIR__OVERLAPPING:
				return overlapping != null;
			case TGGPackage.CRIT_PAIR__RULE1:
				return rule1 != null;
			case TGGPackage.CRIT_PAIR__RULE2:
				return rule2 != null;
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1:
				return mappingsOverToRule1 != null && !mappingsOverToRule1.isEmpty();
			case TGGPackage.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2:
				return mappingsOverToRule2 != null && !mappingsOverToRule2.isEmpty();
			case TGGPackage.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2:
				return mappingsRule1ToRule2 != null && !mappingsRule1ToRule2.isEmpty();
			case TGGPackage.CRIT_PAIR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(')');
		return result.toString();
	}

} //CritPairImpl
