/**
 */
package de.tub.tfs.henshin.tgg.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TggPackage;

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
public class NodeLayoutImpl extends CDOObjectImpl implements NodeLayout {
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
		return TggPackage.Literals.NODE_LAYOUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX() {
		return (Integer)eGet(TggPackage.Literals.NODE_LAYOUT__X, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(int newX) {
		eSet(TggPackage.Literals.NODE_LAYOUT__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY() {
		return (Integer)eGet(TggPackage.Literals.NODE_LAYOUT__Y, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(int newY) {
		eSet(TggPackage.Literals.NODE_LAYOUT__Y, newY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHide() {
		return (Boolean)eGet(TggPackage.Literals.NODE_LAYOUT__HIDE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHide(boolean newHide) {
		eSet(TggPackage.Literals.NODE_LAYOUT__HIDE, newHide);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getNode() {
		return (Node)eGet(TggPackage.Literals.NODE_LAYOUT__NODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNode(Node newNode) {
		eSet(TggPackage.Literals.NODE_LAYOUT__NODE, newNode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getLhsnode() {
		return (Node)eGet(TggPackage.Literals.NODE_LAYOUT__LHSNODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsnode(Node newLhsnode) {
		eSet(TggPackage.Literals.NODE_LAYOUT__LHSNODE, newLhsnode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<AttributeLayout> getAttributeLayouts() {
		return (EList<AttributeLayout>)eGet(TggPackage.Literals.NODE_LAYOUT__ATTRIBUTE_LAYOUTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNew() {
		return (Boolean)eGet(TggPackage.Literals.NODE_LAYOUT__NEW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNew(boolean newNew) {
		eSet(TggPackage.Literals.NODE_LAYOUT__NEW, newNew);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getRhsTranslated() {
		return (Boolean)eGet(TggPackage.Literals.NODE_LAYOUT__RHS_TRANSLATED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsTranslated(Boolean newRhsTranslated) {
		eSet(TggPackage.Literals.NODE_LAYOUT__RHS_TRANSLATED, newRhsTranslated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getLhsTranslated() {
		return (Boolean)eGet(TggPackage.Literals.NODE_LAYOUT__LHS_TRANSLATED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsTranslated(Boolean newLhsTranslated) {
		eSet(TggPackage.Literals.NODE_LAYOUT__LHS_TRANSLATED, newLhsTranslated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCritical() {
		return (Boolean)eGet(TggPackage.Literals.NODE_LAYOUT__CRITICAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCritical(boolean newCritical) {
		eSet(TggPackage.Literals.NODE_LAYOUT__CRITICAL, newCritical);
	}

} //NodeLayoutImpl
