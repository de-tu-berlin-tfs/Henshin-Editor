/**
 */
package de.tub.tfs.henshin.tgg.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.TggPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graph Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.GraphLayoutImpl#getDividerX <em>Divider X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.GraphLayoutImpl#getMaxY <em>Max Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.GraphLayoutImpl#getGraph <em>Graph</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.GraphLayoutImpl#isIsSC <em>Is SC</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GraphLayoutImpl extends EObjectImpl implements GraphLayout {
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
		return TggPackage.Literals.GRAPH_LAYOUT;
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
	public int getDividerX() {
		return (Integer)eGet(TggPackage.Literals.GRAPH_LAYOUT__DIVIDER_X, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDividerX(int newDividerX) {
		eSet(TggPackage.Literals.GRAPH_LAYOUT__DIVIDER_X, newDividerX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxY() {
		return (Integer)eGet(TggPackage.Literals.GRAPH_LAYOUT__MAX_Y, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxY(int newMaxY) {
		eSet(TggPackage.Literals.GRAPH_LAYOUT__MAX_Y, newMaxY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getGraph() {
		return (Graph)eGet(TggPackage.Literals.GRAPH_LAYOUT__GRAPH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraph(Graph newGraph) {
		eSet(TggPackage.Literals.GRAPH_LAYOUT__GRAPH, newGraph);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSC() {
		return (Boolean)eGet(TggPackage.Literals.GRAPH_LAYOUT__IS_SC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSC(boolean newIsSC) {
		eSet(TggPackage.Literals.GRAPH_LAYOUT__IS_SC, newIsSC);
	}

} //GraphLayoutImpl
