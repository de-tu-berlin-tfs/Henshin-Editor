/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.impl.NodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TNodeImpl#getX <em>X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TNodeImpl#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TNodeImpl extends NodeImpl implements TNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TNODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 10;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX() {
		return (Integer)eGet(TggPackage.Literals.TNODE__X, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(int newX) {
		eSet(TggPackage.Literals.TNODE__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY() {
		return (Integer)eGet(TggPackage.Literals.TNODE__Y, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(int newY) {
		eSet(TggPackage.Literals.TNODE__Y, newY);
	}

} //TNodeImpl
