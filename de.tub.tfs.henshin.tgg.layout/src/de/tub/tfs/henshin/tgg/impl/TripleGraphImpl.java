/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleGraph;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.impl.GraphImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Triple Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TripleGraphImpl#getDividerSC_X <em>Divider SC X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TripleGraphImpl#getDividerCT_X <em>Divider CT X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TripleGraphImpl#getDividerMaxY <em>Divider Max Y</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TripleGraphImpl extends GraphImpl implements TripleGraph {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TripleGraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TRIPLE_GRAPH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 5;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDividerSC_X() {
		return (Integer)eGet(TggPackage.Literals.TRIPLE_GRAPH__DIVIDER_SC_X, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDividerSC_X(int newDividerSC_X) {
		eSet(TggPackage.Literals.TRIPLE_GRAPH__DIVIDER_SC_X, newDividerSC_X);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDividerCT_X() {
		return (Integer)eGet(TggPackage.Literals.TRIPLE_GRAPH__DIVIDER_CT_X, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDividerCT_X(int newDividerCT_X) {
		eSet(TggPackage.Literals.TRIPLE_GRAPH__DIVIDER_CT_X, newDividerCT_X);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDividerMaxY() {
		return (Integer)eGet(TggPackage.Literals.TRIPLE_GRAPH__DIVIDER_MAX_Y, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDividerMaxY(int newDividerMaxY) {
		eSet(TggPackage.Literals.TRIPLE_GRAPH__DIVIDER_MAX_Y, newDividerMaxY);
	}

} //TripleGraphImpl
