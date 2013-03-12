/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.henshin.model.Graph;

import tgg.GraphLayout;
import tgg.TGGPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graph Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tgg.impl.GraphLayoutImpl#getDividerX <em>Divider X</em>}</li>
 *   <li>{@link tgg.impl.GraphLayoutImpl#getMaxY <em>Max Y</em>}</li>
 *   <li>{@link tgg.impl.GraphLayoutImpl#getGraph <em>Graph</em>}</li>
 *   <li>{@link tgg.impl.GraphLayoutImpl#isIsSC <em>Is SC</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GraphLayoutImpl extends EObjectImpl implements GraphLayout {
	/**
	 * The default value of the '{@link #getDividerX() <em>Divider X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDividerX()
	 * @generated
	 * @ordered
	 */
	protected static final int DIVIDER_X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDividerX() <em>Divider X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDividerX()
	 * @generated
	 * @ordered
	 */
	protected int dividerX = DIVIDER_X_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxY() <em>Max Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxY()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxY() <em>Max Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxY()
	 * @generated
	 * @ordered
	 */
	protected int maxY = MAX_Y_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGraph() <em>Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraph()
	 * @generated
	 * @ordered
	 */
	protected Graph graph;

	/**
	 * The default value of the '{@link #isIsSC() <em>Is SC</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSC()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsSC() <em>Is SC</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSC()
	 * @generated
	 * @ordered
	 */
	protected boolean isSC = IS_SC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GraphLayoutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGPackage.Literals.GRAPH_LAYOUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDividerX() {
		return dividerX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDividerX(int newDividerX) {
		int oldDividerX = dividerX;
		dividerX = newDividerX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.GRAPH_LAYOUT__DIVIDER_X, oldDividerX, dividerX));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxY(int newMaxY) {
		int oldMaxY = maxY;
		maxY = newMaxY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.GRAPH_LAYOUT__MAX_Y, oldMaxY, maxY));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getGraph() {
		if (graph != null && graph.eIsProxy()) {
			InternalEObject oldGraph = (InternalEObject)graph;
			graph = (Graph)eResolveProxy(oldGraph);
			if (graph != oldGraph) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.GRAPH_LAYOUT__GRAPH, oldGraph, graph));
			}
		}
		return graph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph basicGetGraph() {
		return graph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraph(Graph newGraph) {
		Graph oldGraph = graph;
		graph = newGraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.GRAPH_LAYOUT__GRAPH, oldGraph, graph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSC() {
		return isSC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSC(boolean newIsSC) {
		boolean oldIsSC = isSC;
		isSC = newIsSC;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.GRAPH_LAYOUT__IS_SC, oldIsSC, isSC));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TGGPackage.GRAPH_LAYOUT__DIVIDER_X:
				return getDividerX();
			case TGGPackage.GRAPH_LAYOUT__MAX_Y:
				return getMaxY();
			case TGGPackage.GRAPH_LAYOUT__GRAPH:
				if (resolve) return getGraph();
				return basicGetGraph();
			case TGGPackage.GRAPH_LAYOUT__IS_SC:
				return isIsSC();
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
			case TGGPackage.GRAPH_LAYOUT__DIVIDER_X:
				setDividerX((Integer)newValue);
				return;
			case TGGPackage.GRAPH_LAYOUT__MAX_Y:
				setMaxY((Integer)newValue);
				return;
			case TGGPackage.GRAPH_LAYOUT__GRAPH:
				setGraph((Graph)newValue);
				return;
			case TGGPackage.GRAPH_LAYOUT__IS_SC:
				setIsSC((Boolean)newValue);
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
			case TGGPackage.GRAPH_LAYOUT__DIVIDER_X:
				setDividerX(DIVIDER_X_EDEFAULT);
				return;
			case TGGPackage.GRAPH_LAYOUT__MAX_Y:
				setMaxY(MAX_Y_EDEFAULT);
				return;
			case TGGPackage.GRAPH_LAYOUT__GRAPH:
				setGraph((Graph)null);
				return;
			case TGGPackage.GRAPH_LAYOUT__IS_SC:
				setIsSC(IS_SC_EDEFAULT);
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
			case TGGPackage.GRAPH_LAYOUT__DIVIDER_X:
				return dividerX != DIVIDER_X_EDEFAULT;
			case TGGPackage.GRAPH_LAYOUT__MAX_Y:
				return maxY != MAX_Y_EDEFAULT;
			case TGGPackage.GRAPH_LAYOUT__GRAPH:
				return graph != null;
			case TGGPackage.GRAPH_LAYOUT__IS_SC:
				return isSC != IS_SC_EDEFAULT;
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
		result.append(" (dividerX: ");
		result.append(dividerX);
		result.append(", maxY: ");
		result.append(maxY);
		result.append(", isSC: ");
		result.append(isSC);
		result.append(')');
		return result.toString();
	}

} //GraphLayoutImpl
