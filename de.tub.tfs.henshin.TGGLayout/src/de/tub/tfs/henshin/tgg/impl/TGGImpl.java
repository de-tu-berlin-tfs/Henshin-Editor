/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.tgg.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGPackage;
import de.tub.tfs.henshin.tgg.TRule;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getSrcroot <em>Srcroot</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTarroot <em>Tarroot</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getSource <em>Source</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getCorresp <em>Corresp</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getNodelayouts <em>Nodelayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getEdgelayouts <em>Edgelayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getGraphlayouts <em>Graphlayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTRules <em>TRules</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getCritPairs <em>Crit Pairs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TGGImpl extends EObjectImpl implements TGG {
	/**
	 * The cached value of the '{@link #getSrcroot() <em>Srcroot</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcroot()
	 * @generated
	 * @ordered
	 */
	protected EObject srcroot;

	/**
	 * The cached value of the '{@link #getTarroot() <em>Tarroot</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarroot()
	 * @generated
	 * @ordered
	 */
	protected EObject tarroot;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EPackage source;

	/**
	 * The cached value of the '{@link #getCorresp() <em>Corresp</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorresp()
	 * @generated
	 * @ordered
	 */
	protected EPackage corresp;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EPackage target;

	/**
	 * The cached value of the '{@link #getNodelayouts() <em>Nodelayouts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodelayouts()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeLayout> nodelayouts;

	/**
	 * The cached value of the '{@link #getEdgelayouts() <em>Edgelayouts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgelayouts()
	 * @generated
	 * @ordered
	 */
	protected EList<EdgeLayout> edgelayouts;

	/**
	 * The cached value of the '{@link #getGraphlayouts() <em>Graphlayouts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphlayouts()
	 * @generated
	 * @ordered
	 */
	protected EList<GraphLayout> graphlayouts;

	/**
	 * The cached value of the '{@link #getTRules() <em>TRules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTRules()
	 * @generated
	 * @ordered
	 */
	protected EList<TRule> tRules;

	/**
	 * The cached value of the '{@link #getCritPairs() <em>Crit Pairs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCritPairs()
	 * @generated
	 * @ordered
	 */
	protected EList<CritPair> critPairs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGPackage.Literals.TGG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSrcroot() {
		if (srcroot != null && srcroot.eIsProxy()) {
			InternalEObject oldSrcroot = (InternalEObject)srcroot;
			srcroot = eResolveProxy(oldSrcroot);
			if (srcroot != oldSrcroot) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.TGG__SRCROOT, oldSrcroot, srcroot));
			}
		}
		return srcroot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSrcroot() {
		return srcroot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrcroot(EObject newSrcroot) {
		EObject oldSrcroot = srcroot;
		srcroot = newSrcroot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.TGG__SRCROOT, oldSrcroot, srcroot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTarroot() {
		if (tarroot != null && tarroot.eIsProxy()) {
			InternalEObject oldTarroot = (InternalEObject)tarroot;
			tarroot = eResolveProxy(oldTarroot);
			if (tarroot != oldTarroot) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.TGG__TARROOT, oldTarroot, tarroot));
			}
		}
		return tarroot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetTarroot() {
		return tarroot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarroot(EObject newTarroot) {
		EObject oldTarroot = tarroot;
		tarroot = newTarroot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.TGG__TARROOT, oldTarroot, tarroot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (EPackage)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.TGG__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(EPackage newSource) {
		EPackage oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.TGG__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getCorresp() {
		if (corresp != null && corresp.eIsProxy()) {
			InternalEObject oldCorresp = (InternalEObject)corresp;
			corresp = (EPackage)eResolveProxy(oldCorresp);
			if (corresp != oldCorresp) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.TGG__CORRESP, oldCorresp, corresp));
			}
		}
		return corresp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetCorresp() {
		return corresp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorresp(EPackage newCorresp) {
		EPackage oldCorresp = corresp;
		corresp = newCorresp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.TGG__CORRESP, oldCorresp, corresp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (EPackage)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.TGG__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(EPackage newTarget) {
		EPackage oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.TGG__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NodeLayout> getNodelayouts() {
		if (nodelayouts == null) {
			nodelayouts = new EObjectContainmentEList<NodeLayout>(NodeLayout.class, this, TGGPackage.TGG__NODELAYOUTS);
		}
		return nodelayouts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EdgeLayout> getEdgelayouts() {
		if (edgelayouts == null) {
			edgelayouts = new EObjectContainmentEList<EdgeLayout>(EdgeLayout.class, this, TGGPackage.TGG__EDGELAYOUTS);
		}
		return edgelayouts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GraphLayout> getGraphlayouts() {
		if (graphlayouts == null) {
			graphlayouts = new EObjectContainmentEList<GraphLayout>(GraphLayout.class, this, TGGPackage.TGG__GRAPHLAYOUTS);
		}
		return graphlayouts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TRule> getTRules() {
		if (tRules == null) {
			tRules = new EObjectContainmentEList<TRule>(TRule.class, this, TGGPackage.TGG__TRULES);
		}
		return tRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CritPair> getCritPairs() {
		if (critPairs == null) {
			critPairs = new EObjectContainmentEList<CritPair>(CritPair.class, this, TGGPackage.TGG__CRIT_PAIRS);
		}
		return critPairs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TGGPackage.TGG__NODELAYOUTS:
				return ((InternalEList<?>)getNodelayouts()).basicRemove(otherEnd, msgs);
			case TGGPackage.TGG__EDGELAYOUTS:
				return ((InternalEList<?>)getEdgelayouts()).basicRemove(otherEnd, msgs);
			case TGGPackage.TGG__GRAPHLAYOUTS:
				return ((InternalEList<?>)getGraphlayouts()).basicRemove(otherEnd, msgs);
			case TGGPackage.TGG__TRULES:
				return ((InternalEList<?>)getTRules()).basicRemove(otherEnd, msgs);
			case TGGPackage.TGG__CRIT_PAIRS:
				return ((InternalEList<?>)getCritPairs()).basicRemove(otherEnd, msgs);
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
			case TGGPackage.TGG__SRCROOT:
				if (resolve) return getSrcroot();
				return basicGetSrcroot();
			case TGGPackage.TGG__TARROOT:
				if (resolve) return getTarroot();
				return basicGetTarroot();
			case TGGPackage.TGG__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case TGGPackage.TGG__CORRESP:
				if (resolve) return getCorresp();
				return basicGetCorresp();
			case TGGPackage.TGG__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case TGGPackage.TGG__NODELAYOUTS:
				return getNodelayouts();
			case TGGPackage.TGG__EDGELAYOUTS:
				return getEdgelayouts();
			case TGGPackage.TGG__GRAPHLAYOUTS:
				return getGraphlayouts();
			case TGGPackage.TGG__TRULES:
				return getTRules();
			case TGGPackage.TGG__CRIT_PAIRS:
				return getCritPairs();
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
			case TGGPackage.TGG__SRCROOT:
				setSrcroot((EObject)newValue);
				return;
			case TGGPackage.TGG__TARROOT:
				setTarroot((EObject)newValue);
				return;
			case TGGPackage.TGG__SOURCE:
				setSource((EPackage)newValue);
				return;
			case TGGPackage.TGG__CORRESP:
				setCorresp((EPackage)newValue);
				return;
			case TGGPackage.TGG__TARGET:
				setTarget((EPackage)newValue);
				return;
			case TGGPackage.TGG__NODELAYOUTS:
				getNodelayouts().clear();
				getNodelayouts().addAll((Collection<? extends NodeLayout>)newValue);
				return;
			case TGGPackage.TGG__EDGELAYOUTS:
				getEdgelayouts().clear();
				getEdgelayouts().addAll((Collection<? extends EdgeLayout>)newValue);
				return;
			case TGGPackage.TGG__GRAPHLAYOUTS:
				getGraphlayouts().clear();
				getGraphlayouts().addAll((Collection<? extends GraphLayout>)newValue);
				return;
			case TGGPackage.TGG__TRULES:
				getTRules().clear();
				getTRules().addAll((Collection<? extends TRule>)newValue);
				return;
			case TGGPackage.TGG__CRIT_PAIRS:
				getCritPairs().clear();
				getCritPairs().addAll((Collection<? extends CritPair>)newValue);
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
			case TGGPackage.TGG__SRCROOT:
				setSrcroot((EObject)null);
				return;
			case TGGPackage.TGG__TARROOT:
				setTarroot((EObject)null);
				return;
			case TGGPackage.TGG__SOURCE:
				setSource((EPackage)null);
				return;
			case TGGPackage.TGG__CORRESP:
				setCorresp((EPackage)null);
				return;
			case TGGPackage.TGG__TARGET:
				setTarget((EPackage)null);
				return;
			case TGGPackage.TGG__NODELAYOUTS:
				getNodelayouts().clear();
				return;
			case TGGPackage.TGG__EDGELAYOUTS:
				getEdgelayouts().clear();
				return;
			case TGGPackage.TGG__GRAPHLAYOUTS:
				getGraphlayouts().clear();
				return;
			case TGGPackage.TGG__TRULES:
				getTRules().clear();
				return;
			case TGGPackage.TGG__CRIT_PAIRS:
				getCritPairs().clear();
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
			case TGGPackage.TGG__SRCROOT:
				return srcroot != null;
			case TGGPackage.TGG__TARROOT:
				return tarroot != null;
			case TGGPackage.TGG__SOURCE:
				return source != null;
			case TGGPackage.TGG__CORRESP:
				return corresp != null;
			case TGGPackage.TGG__TARGET:
				return target != null;
			case TGGPackage.TGG__NODELAYOUTS:
				return nodelayouts != null && !nodelayouts.isEmpty();
			case TGGPackage.TGG__EDGELAYOUTS:
				return edgelayouts != null && !edgelayouts.isEmpty();
			case TGGPackage.TGG__GRAPHLAYOUTS:
				return graphlayouts != null && !graphlayouts.isEmpty();
			case TGGPackage.TGG__TRULES:
				return tRules != null && !tRules.isEmpty();
			case TGGPackage.TGG__CRIT_PAIRS:
				return critPairs != null && !critPairs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGImpl
