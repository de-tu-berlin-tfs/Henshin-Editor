/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.analysis.impl;

import de.tub.tfs.henshin.analysis.AnalysisPackage;
import de.tub.tfs.henshin.analysis.CriticalPair;
import de.tub.tfs.henshin.analysis.CriticalPairType;

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
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Critical Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getRule1 <em>Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getRule2 <em>Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getSourceUnit <em>Source Unit</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getTargetUnit <em>Target Unit</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getOverlapping <em>Overlapping</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getMappingsOverlappingToRule1 <em>Mappings Overlapping To Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getMappingsOverlappingToRule2 <em>Mappings Overlapping To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl#getCriticalObjects <em>Critical Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CriticalPairImpl extends EObjectImpl implements CriticalPair {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final CriticalPairType TYPE_EDEFAULT = CriticalPairType.DELETE_USE_CONFLICT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected CriticalPairType type = TYPE_EDEFAULT;

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
	 * The cached value of the '{@link #getSourceUnit() <em>Source Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit sourceUnit;

	/**
	 * The cached value of the '{@link #getTargetUnit() <em>Target Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit targetUnit;

	/**
	 * The cached value of the '{@link #getOverlapping() <em>Overlapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverlapping()
	 * @generated
	 * @ordered
	 */
	protected Graph overlapping;

	/**
	 * The cached value of the '{@link #getMappingsOverlappingToRule1() <em>Mappings Overlapping To Rule1</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingsOverlappingToRule1()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappingsOverlappingToRule1;

	/**
	 * The cached value of the '{@link #getMappingsOverlappingToRule2() <em>Mappings Overlapping To Rule2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingsOverlappingToRule2()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappingsOverlappingToRule2;

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
	 * The cached value of the '{@link #getCriticalObjects() <em>Critical Objects</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCriticalObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> criticalObjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CriticalPairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnalysisPackage.Literals.CRITICAL_PAIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CriticalPairType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(CriticalPairType newType) {
		CriticalPairType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__TYPE, oldType, type));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnalysisPackage.CRITICAL_PAIR__RULE1, oldRule1, rule1));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__RULE1, oldRule1, rule1));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnalysisPackage.CRITICAL_PAIR__RULE2, oldRule2, rule2));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__RULE2, oldRule2, rule2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getSourceUnit() {
		if (sourceUnit != null && sourceUnit.eIsProxy()) {
			InternalEObject oldSourceUnit = (InternalEObject)sourceUnit;
			sourceUnit = (TransformationUnit)eResolveProxy(oldSourceUnit);
			if (sourceUnit != oldSourceUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnalysisPackage.CRITICAL_PAIR__SOURCE_UNIT, oldSourceUnit, sourceUnit));
			}
		}
		return sourceUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetSourceUnit() {
		return sourceUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceUnit(TransformationUnit newSourceUnit) {
		TransformationUnit oldSourceUnit = sourceUnit;
		sourceUnit = newSourceUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__SOURCE_UNIT, oldSourceUnit, sourceUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getTargetUnit() {
		if (targetUnit != null && targetUnit.eIsProxy()) {
			InternalEObject oldTargetUnit = (InternalEObject)targetUnit;
			targetUnit = (TransformationUnit)eResolveProxy(oldTargetUnit);
			if (targetUnit != oldTargetUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnalysisPackage.CRITICAL_PAIR__TARGET_UNIT, oldTargetUnit, targetUnit));
			}
		}
		return targetUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetTargetUnit() {
		return targetUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetUnit(TransformationUnit newTargetUnit) {
		TransformationUnit oldTargetUnit = targetUnit;
		targetUnit = newTargetUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__TARGET_UNIT, oldTargetUnit, targetUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getOverlapping() {
		return overlapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOverlapping(Graph newOverlapping, NotificationChain msgs) {
		Graph oldOverlapping = overlapping;
		overlapping = newOverlapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__OVERLAPPING, oldOverlapping, newOverlapping);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverlapping(Graph newOverlapping) {
		if (newOverlapping != overlapping) {
			NotificationChain msgs = null;
			if (overlapping != null)
				msgs = ((InternalEObject)overlapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AnalysisPackage.CRITICAL_PAIR__OVERLAPPING, null, msgs);
			if (newOverlapping != null)
				msgs = ((InternalEObject)newOverlapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AnalysisPackage.CRITICAL_PAIR__OVERLAPPING, null, msgs);
			msgs = basicSetOverlapping(newOverlapping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysisPackage.CRITICAL_PAIR__OVERLAPPING, newOverlapping, newOverlapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappingsOverlappingToRule1() {
		if (mappingsOverlappingToRule1 == null) {
			mappingsOverlappingToRule1 = new EObjectContainmentEList<Mapping>(Mapping.class, this, AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1);
		}
		return mappingsOverlappingToRule1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappingsOverlappingToRule2() {
		if (mappingsOverlappingToRule2 == null) {
			mappingsOverlappingToRule2 = new EObjectContainmentEList<Mapping>(Mapping.class, this, AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2);
		}
		return mappingsOverlappingToRule2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappingsRule1ToRule2() {
		if (mappingsRule1ToRule2 == null) {
			mappingsRule1ToRule2 = new EObjectContainmentEList<Mapping>(Mapping.class, this, AnalysisPackage.CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2);
		}
		return mappingsRule1ToRule2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getCriticalObjects() {
		if (criticalObjects == null) {
			criticalObjects = new EObjectResolvingEList<EObject>(EObject.class, this, AnalysisPackage.CRITICAL_PAIR__CRITICAL_OBJECTS);
		}
		return criticalObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnalysisPackage.CRITICAL_PAIR__OVERLAPPING:
				return basicSetOverlapping(null, msgs);
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1:
				return ((InternalEList<?>)getMappingsOverlappingToRule1()).basicRemove(otherEnd, msgs);
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2:
				return ((InternalEList<?>)getMappingsOverlappingToRule2()).basicRemove(otherEnd, msgs);
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2:
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
			case AnalysisPackage.CRITICAL_PAIR__TYPE:
				return getType();
			case AnalysisPackage.CRITICAL_PAIR__RULE1:
				if (resolve) return getRule1();
				return basicGetRule1();
			case AnalysisPackage.CRITICAL_PAIR__RULE2:
				if (resolve) return getRule2();
				return basicGetRule2();
			case AnalysisPackage.CRITICAL_PAIR__SOURCE_UNIT:
				if (resolve) return getSourceUnit();
				return basicGetSourceUnit();
			case AnalysisPackage.CRITICAL_PAIR__TARGET_UNIT:
				if (resolve) return getTargetUnit();
				return basicGetTargetUnit();
			case AnalysisPackage.CRITICAL_PAIR__OVERLAPPING:
				return getOverlapping();
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1:
				return getMappingsOverlappingToRule1();
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2:
				return getMappingsOverlappingToRule2();
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2:
				return getMappingsRule1ToRule2();
			case AnalysisPackage.CRITICAL_PAIR__CRITICAL_OBJECTS:
				return getCriticalObjects();
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
			case AnalysisPackage.CRITICAL_PAIR__TYPE:
				setType((CriticalPairType)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__RULE1:
				setRule1((Rule)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__RULE2:
				setRule2((Rule)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__SOURCE_UNIT:
				setSourceUnit((TransformationUnit)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__TARGET_UNIT:
				setTargetUnit((TransformationUnit)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__OVERLAPPING:
				setOverlapping((Graph)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1:
				getMappingsOverlappingToRule1().clear();
				getMappingsOverlappingToRule1().addAll((Collection<? extends Mapping>)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2:
				getMappingsOverlappingToRule2().clear();
				getMappingsOverlappingToRule2().addAll((Collection<? extends Mapping>)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2:
				getMappingsRule1ToRule2().clear();
				getMappingsRule1ToRule2().addAll((Collection<? extends Mapping>)newValue);
				return;
			case AnalysisPackage.CRITICAL_PAIR__CRITICAL_OBJECTS:
				getCriticalObjects().clear();
				getCriticalObjects().addAll((Collection<? extends EObject>)newValue);
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
			case AnalysisPackage.CRITICAL_PAIR__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AnalysisPackage.CRITICAL_PAIR__RULE1:
				setRule1((Rule)null);
				return;
			case AnalysisPackage.CRITICAL_PAIR__RULE2:
				setRule2((Rule)null);
				return;
			case AnalysisPackage.CRITICAL_PAIR__SOURCE_UNIT:
				setSourceUnit((TransformationUnit)null);
				return;
			case AnalysisPackage.CRITICAL_PAIR__TARGET_UNIT:
				setTargetUnit((TransformationUnit)null);
				return;
			case AnalysisPackage.CRITICAL_PAIR__OVERLAPPING:
				setOverlapping((Graph)null);
				return;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1:
				getMappingsOverlappingToRule1().clear();
				return;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2:
				getMappingsOverlappingToRule2().clear();
				return;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2:
				getMappingsRule1ToRule2().clear();
				return;
			case AnalysisPackage.CRITICAL_PAIR__CRITICAL_OBJECTS:
				getCriticalObjects().clear();
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
			case AnalysisPackage.CRITICAL_PAIR__TYPE:
				return type != TYPE_EDEFAULT;
			case AnalysisPackage.CRITICAL_PAIR__RULE1:
				return rule1 != null;
			case AnalysisPackage.CRITICAL_PAIR__RULE2:
				return rule2 != null;
			case AnalysisPackage.CRITICAL_PAIR__SOURCE_UNIT:
				return sourceUnit != null;
			case AnalysisPackage.CRITICAL_PAIR__TARGET_UNIT:
				return targetUnit != null;
			case AnalysisPackage.CRITICAL_PAIR__OVERLAPPING:
				return overlapping != null;
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1:
				return mappingsOverlappingToRule1 != null && !mappingsOverlappingToRule1.isEmpty();
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2:
				return mappingsOverlappingToRule2 != null && !mappingsOverlappingToRule2.isEmpty();
			case AnalysisPackage.CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2:
				return mappingsRule1ToRule2 != null && !mappingsRule1ToRule2.isEmpty();
			case AnalysisPackage.CRITICAL_PAIR__CRITICAL_OBJECTS:
				return criticalObjects != null && !criticalObjects.isEmpty();
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
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //CriticalPairImpl
