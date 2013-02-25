/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.MarkedElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.actions.ActionElementFinder;
import org.eclipse.emf.henshin.model.actions.EdgeActionHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getAction <em>Action</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getIsMarked <em>Is Marked</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getMarkerType <em>Marker Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.EdgeImpl#getGraph <em>Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EdgeImpl extends EObjectImpl implements Edge {
	
	/**
	 * The default value of the '{@link #getAction() <em>Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected static final Action ACTION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getIsMarked() <em>Is Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsMarked()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_MARKED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsMarked() <em>Is Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsMarked()
	 * @generated
	 * @ordered
	 */
	protected Boolean isMarked = IS_MARKED_EDEFAULT;

	/**
	 * The default value of the '{@link #getMarkerType() <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkerType()
	 * @generated
	 * @ordered
	 */
	protected static final String MARKER_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMarkerType() <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkerType()
	 * @generated
	 * @ordered
	 */
	protected String markerType = MARKER_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Node source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Node target;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EReference type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Action getAction() {
		return EdgeActionHelper.INSTANCE.getAction(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setAction(Action action) {
		EdgeActionHelper.INSTANCE.setAction(this, action);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getIsMarked() {
		return isMarked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsMarked(Boolean newIsMarked) {
		Boolean oldIsMarked = isMarked;
		isMarked = newIsMarked;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__IS_MARKED, oldIsMarked, isMarked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMarkerType() {
		return markerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMarkerType(String newMarkerType) {
		String oldMarkerType = markerType;
		markerType = newMarkerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__MARKER_TYPE, oldMarkerType, markerType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (Node)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.EDGE__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Node newSource, NotificationChain msgs) {
		Node oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Node newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, HenshinPackage.NODE__OUTGOING, Node.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, HenshinPackage.NODE__OUTGOING, Node.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Node)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.EDGE__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Node newTarget, NotificationChain msgs) {
		Node oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Node newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, HenshinPackage.NODE__INCOMING, Node.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, HenshinPackage.NODE__INCOMING, Node.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EReference)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.EDGE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EReference newType) {
		EReference oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getGraph() {
		if (eContainerFeatureID() != HenshinPackage.EDGE__GRAPH) return null;
		return (Graph)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraph(Graph newGraph, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGraph, HenshinPackage.EDGE__GRAPH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraph(Graph newGraph) {
		if (newGraph != eInternalContainer() || (eContainerFeatureID() != HenshinPackage.EDGE__GRAPH && newGraph != null)) {
			if (EcoreUtil.isAncestor(this, newGraph))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newGraph != null)
				msgs = ((InternalEObject)newGraph).eInverseAdd(this, HenshinPackage.GRAPH__EDGES, Graph.class, msgs);
			msgs = basicSetGraph(newGraph, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.EDGE__GRAPH, newGraph, newGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Edge getActionEdge() {
		return ActionElementFinder.getActionElement(this, EdgeActionHelper.INSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.EDGE__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, HenshinPackage.NODE__OUTGOING, Node.class, msgs);
				return basicSetSource((Node)otherEnd, msgs);
			case HenshinPackage.EDGE__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, HenshinPackage.NODE__INCOMING, Node.class, msgs);
				return basicSetTarget((Node)otherEnd, msgs);
			case HenshinPackage.EDGE__GRAPH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetGraph((Graph)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.EDGE__SOURCE:
				return basicSetSource(null, msgs);
			case HenshinPackage.EDGE__TARGET:
				return basicSetTarget(null, msgs);
			case HenshinPackage.EDGE__GRAPH:
				return basicSetGraph(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case HenshinPackage.EDGE__GRAPH:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.GRAPH__EDGES, Graph.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.EDGE__ACTION:
				return getAction();
			case HenshinPackage.EDGE__IS_MARKED:
				return getIsMarked();
			case HenshinPackage.EDGE__MARKER_TYPE:
				return getMarkerType();
			case HenshinPackage.EDGE__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case HenshinPackage.EDGE__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case HenshinPackage.EDGE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case HenshinPackage.EDGE__GRAPH:
				return getGraph();
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
			case HenshinPackage.EDGE__ACTION:
				setAction((Action)newValue);
				return;
			case HenshinPackage.EDGE__IS_MARKED:
				setIsMarked((Boolean)newValue);
				return;
			case HenshinPackage.EDGE__MARKER_TYPE:
				setMarkerType((String)newValue);
				return;
			case HenshinPackage.EDGE__SOURCE:
				setSource((Node)newValue);
				return;
			case HenshinPackage.EDGE__TARGET:
				setTarget((Node)newValue);
				return;
			case HenshinPackage.EDGE__TYPE:
				setType((EReference)newValue);
				return;
			case HenshinPackage.EDGE__GRAPH:
				setGraph((Graph)newValue);
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
			case HenshinPackage.EDGE__ACTION:
				setAction(ACTION_EDEFAULT);
				return;
			case HenshinPackage.EDGE__IS_MARKED:
				setIsMarked(IS_MARKED_EDEFAULT);
				return;
			case HenshinPackage.EDGE__MARKER_TYPE:
				setMarkerType(MARKER_TYPE_EDEFAULT);
				return;
			case HenshinPackage.EDGE__SOURCE:
				setSource((Node)null);
				return;
			case HenshinPackage.EDGE__TARGET:
				setTarget((Node)null);
				return;
			case HenshinPackage.EDGE__TYPE:
				setType((EReference)null);
				return;
			case HenshinPackage.EDGE__GRAPH:
				setGraph((Graph)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(int featureID) {
		switch (featureID) {
			case HenshinPackage.EDGE__ACTION:
				return ACTION_EDEFAULT == null ? getAction() != null : !ACTION_EDEFAULT.equals(getAction());
			case HenshinPackage.EDGE__IS_MARKED:
				return IS_MARKED_EDEFAULT == null ? isMarked != null : !IS_MARKED_EDEFAULT.equals(isMarked);
			case HenshinPackage.EDGE__MARKER_TYPE:
				return MARKER_TYPE_EDEFAULT == null ? markerType != null : !MARKER_TYPE_EDEFAULT.equals(markerType);
			case HenshinPackage.EDGE__SOURCE:
				return source != null;
			case HenshinPackage.EDGE__TARGET:
				return target != null;
			case HenshinPackage.EDGE__TYPE:
				return type != null;
			case HenshinPackage.EDGE__GRAPH:
				return getGraph() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean eIsSet(int featureID) {
		if (featureID==HenshinPackage.EDGE__ACTION) {
			return false;
		}
		return eIsSetGen(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MarkedElement.class) {
			switch (derivedFeatureID) {
				case HenshinPackage.EDGE__IS_MARKED: return HenshinPackage.MARKED_ELEMENT__IS_MARKED;
				case HenshinPackage.EDGE__MARKER_TYPE: return HenshinPackage.MARKED_ELEMENT__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MarkedElement.class) {
			switch (baseFeatureID) {
				case HenshinPackage.MARKED_ELEMENT__IS_MARKED: return HenshinPackage.EDGE__IS_MARKED;
				case HenshinPackage.MARKED_ELEMENT__MARKER_TYPE: return HenshinPackage.EDGE__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		if (type!=null && type.getName()==null) {
			EcoreUtil.resolveAll(this);
		}
		String srcName = (source!=null) ? ((source.getName()!=null) ? source.getName() : "_") : "?";
		String trgName = (target!=null) ? ((target.getName()!=null) ? target.getName() : "_") : "?";
		String edgeType = ("(" + ((type!=null) ? type.getName() : "?") + ")");
		return "Edge " + edgeType + " " + srcName + " -> " + trgName;
	}
	
} //EdgeImpl
