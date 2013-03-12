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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGGPackage;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getX <em>X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getY <em>Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#isHide <em>Hide</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getNode <em>Node</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getLhsnode <em>Lhsnode</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getAttributeLayouts <em>Attribute Layouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#isNew <em>New</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getRhsTranslated <em>Rhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#getLhsTranslated <em>Lhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.NodeLayoutImpl#isCritical <em>Critical</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeLayoutImpl extends EObjectImpl implements NodeLayout {
	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * The default value of the '{@link #isHide() <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHide()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHide() <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHide()
	 * @generated
	 * @ordered
	 */
	protected boolean hide = HIDE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNode() <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected Node node;

	/**
	 * The cached value of the '{@link #getLhsnode() <em>Lhsnode</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsnode()
	 * @generated
	 * @ordered
	 */
	protected Node lhsnode;

	/**
	 * The cached value of the '{@link #getAttributeLayouts() <em>Attribute Layouts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeLayouts()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeLayout> attributeLayouts;

	/**
	 * The default value of the '{@link #isNew() <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNew()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEW_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNew() <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNew()
	 * @generated
	 * @ordered
	 */
	protected boolean new_ = NEW_EDEFAULT;

	/**
	 * The default value of the '{@link #getRhsTranslated() <em>Rhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean RHS_TRANSLATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRhsTranslated() <em>Rhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected Boolean rhsTranslated = RHS_TRANSLATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getLhsTranslated() <em>Lhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean LHS_TRANSLATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLhsTranslated() <em>Lhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected Boolean lhsTranslated = LHS_TRANSLATED_EDEFAULT;

	/**
	 * The default value of the '{@link #isCritical() <em>Critical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCritical()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CRITICAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCritical() <em>Critical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCritical()
	 * @generated
	 * @ordered
	 */
	protected boolean critical = CRITICAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeLayoutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGPackage.Literals.NODE_LAYOUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHide() {
		return hide;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHide(boolean newHide) {
		boolean oldHide = hide;
		hide = newHide;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__HIDE, oldHide, hide));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getNode() {
		if (node != null && node.eIsProxy()) {
			InternalEObject oldNode = (InternalEObject)node;
			node = (Node)eResolveProxy(oldNode);
			if (node != oldNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.NODE_LAYOUT__NODE, oldNode, node));
			}
		}
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetNode() {
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNode(Node newNode) {
		Node oldNode = node;
		node = newNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__NODE, oldNode, node));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getLhsnode() {
		if (lhsnode != null && lhsnode.eIsProxy()) {
			InternalEObject oldLhsnode = (InternalEObject)lhsnode;
			lhsnode = (Node)eResolveProxy(oldLhsnode);
			if (lhsnode != oldLhsnode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.NODE_LAYOUT__LHSNODE, oldLhsnode, lhsnode));
			}
		}
		return lhsnode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetLhsnode() {
		return lhsnode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsnode(Node newLhsnode) {
		Node oldLhsnode = lhsnode;
		lhsnode = newLhsnode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__LHSNODE, oldLhsnode, lhsnode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeLayout> getAttributeLayouts() {
		if (attributeLayouts == null) {
			attributeLayouts = new EObjectContainmentEList<AttributeLayout>(AttributeLayout.class, this, TGGPackage.NODE_LAYOUT__ATTRIBUTE_LAYOUTS);
		}
		return attributeLayouts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNew() {
		return new_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNew(boolean newNew) {
		boolean oldNew = new_;
		new_ = newNew;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__NEW, oldNew, new_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getRhsTranslated() {
		return rhsTranslated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsTranslated(Boolean newRhsTranslated) {
		Boolean oldRhsTranslated = rhsTranslated;
		rhsTranslated = newRhsTranslated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__RHS_TRANSLATED, oldRhsTranslated, rhsTranslated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getLhsTranslated() {
		return lhsTranslated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsTranslated(Boolean newLhsTranslated) {
		Boolean oldLhsTranslated = lhsTranslated;
		lhsTranslated = newLhsTranslated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__LHS_TRANSLATED, oldLhsTranslated, lhsTranslated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCritical() {
		return critical;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCritical(boolean newCritical) {
		boolean oldCritical = critical;
		critical = newCritical;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.NODE_LAYOUT__CRITICAL, oldCritical, critical));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TGGPackage.NODE_LAYOUT__ATTRIBUTE_LAYOUTS:
				return ((InternalEList<?>)getAttributeLayouts()).basicRemove(otherEnd, msgs);
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
			case TGGPackage.NODE_LAYOUT__X:
				return getX();
			case TGGPackage.NODE_LAYOUT__Y:
				return getY();
			case TGGPackage.NODE_LAYOUT__HIDE:
				return isHide();
			case TGGPackage.NODE_LAYOUT__NODE:
				if (resolve) return getNode();
				return basicGetNode();
			case TGGPackage.NODE_LAYOUT__LHSNODE:
				if (resolve) return getLhsnode();
				return basicGetLhsnode();
			case TGGPackage.NODE_LAYOUT__ATTRIBUTE_LAYOUTS:
				return getAttributeLayouts();
			case TGGPackage.NODE_LAYOUT__NEW:
				return isNew();
			case TGGPackage.NODE_LAYOUT__RHS_TRANSLATED:
				return getRhsTranslated();
			case TGGPackage.NODE_LAYOUT__LHS_TRANSLATED:
				return getLhsTranslated();
			case TGGPackage.NODE_LAYOUT__CRITICAL:
				return isCritical();
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
			case TGGPackage.NODE_LAYOUT__X:
				setX((Integer)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__Y:
				setY((Integer)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__HIDE:
				setHide((Boolean)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__NODE:
				setNode((Node)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__LHSNODE:
				setLhsnode((Node)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__ATTRIBUTE_LAYOUTS:
				getAttributeLayouts().clear();
				getAttributeLayouts().addAll((Collection<? extends AttributeLayout>)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__NEW:
				setNew((Boolean)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__RHS_TRANSLATED:
				setRhsTranslated((Boolean)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__LHS_TRANSLATED:
				setLhsTranslated((Boolean)newValue);
				return;
			case TGGPackage.NODE_LAYOUT__CRITICAL:
				setCritical((Boolean)newValue);
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
			case TGGPackage.NODE_LAYOUT__X:
				setX(X_EDEFAULT);
				return;
			case TGGPackage.NODE_LAYOUT__Y:
				setY(Y_EDEFAULT);
				return;
			case TGGPackage.NODE_LAYOUT__HIDE:
				setHide(HIDE_EDEFAULT);
				return;
			case TGGPackage.NODE_LAYOUT__NODE:
				setNode((Node)null);
				return;
			case TGGPackage.NODE_LAYOUT__LHSNODE:
				setLhsnode((Node)null);
				return;
			case TGGPackage.NODE_LAYOUT__ATTRIBUTE_LAYOUTS:
				getAttributeLayouts().clear();
				return;
			case TGGPackage.NODE_LAYOUT__NEW:
				setNew(NEW_EDEFAULT);
				return;
			case TGGPackage.NODE_LAYOUT__RHS_TRANSLATED:
				setRhsTranslated(RHS_TRANSLATED_EDEFAULT);
				return;
			case TGGPackage.NODE_LAYOUT__LHS_TRANSLATED:
				setLhsTranslated(LHS_TRANSLATED_EDEFAULT);
				return;
			case TGGPackage.NODE_LAYOUT__CRITICAL:
				setCritical(CRITICAL_EDEFAULT);
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
			case TGGPackage.NODE_LAYOUT__X:
				return x != X_EDEFAULT;
			case TGGPackage.NODE_LAYOUT__Y:
				return y != Y_EDEFAULT;
			case TGGPackage.NODE_LAYOUT__HIDE:
				return hide != HIDE_EDEFAULT;
			case TGGPackage.NODE_LAYOUT__NODE:
				return node != null;
			case TGGPackage.NODE_LAYOUT__LHSNODE:
				return lhsnode != null;
			case TGGPackage.NODE_LAYOUT__ATTRIBUTE_LAYOUTS:
				return attributeLayouts != null && !attributeLayouts.isEmpty();
			case TGGPackage.NODE_LAYOUT__NEW:
				return new_ != NEW_EDEFAULT;
			case TGGPackage.NODE_LAYOUT__RHS_TRANSLATED:
				return RHS_TRANSLATED_EDEFAULT == null ? rhsTranslated != null : !RHS_TRANSLATED_EDEFAULT.equals(rhsTranslated);
			case TGGPackage.NODE_LAYOUT__LHS_TRANSLATED:
				return LHS_TRANSLATED_EDEFAULT == null ? lhsTranslated != null : !LHS_TRANSLATED_EDEFAULT.equals(lhsTranslated);
			case TGGPackage.NODE_LAYOUT__CRITICAL:
				return critical != CRITICAL_EDEFAULT;
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
		result.append(" (x: ");
		result.append(x);
		result.append(", y: ");
		result.append(y);
		result.append(", hide: ");
		result.append(hide);
		result.append(", new: ");
		result.append(new_);
		result.append(", rhsTranslated: ");
		result.append(rhsTranslated);
		result.append(", lhsTranslated: ");
		result.append(lhsTranslated);
		result.append(", critical: ");
		result.append(critical);
		result.append(')');
		return result.toString();
	}

} //NodeLayoutImpl
