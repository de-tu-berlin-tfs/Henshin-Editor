/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.henshin.model.Graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.GraphLayout#getDividerX <em>Divider X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.GraphLayout#getMaxY <em>Max Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.GraphLayout#getGraph <em>Graph</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.GraphLayout#isIsSC <em>Is SC</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getGraphLayout()
 * @model
 * @generated
 */
public interface GraphLayout extends EObject {
	/**
	 * Returns the value of the '<em><b>Divider X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Divider X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Divider X</em>' attribute.
	 * @see #setDividerX(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getGraphLayout_DividerX()
	 * @model
	 * @generated
	 */
	int getDividerX();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.GraphLayout#getDividerX <em>Divider X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Divider X</em>' attribute.
	 * @see #getDividerX()
	 * @generated
	 */
	void setDividerX(int value);

	/**
	 * Returns the value of the '<em><b>Max Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Y</em>' attribute.
	 * @see #setMaxY(int)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getGraphLayout_MaxY()
	 * @model
	 * @generated
	 */
	int getMaxY();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.GraphLayout#getMaxY <em>Max Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Y</em>' attribute.
	 * @see #getMaxY()
	 * @generated
	 */
	void setMaxY(int value);

	/**
	 * Returns the value of the '<em><b>Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graph</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graph</em>' reference.
	 * @see #setGraph(Graph)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getGraphLayout_Graph()
	 * @model
	 * @generated
	 */
	Graph getGraph();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.GraphLayout#getGraph <em>Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graph</em>' reference.
	 * @see #getGraph()
	 * @generated
	 */
	void setGraph(Graph value);

	/**
	 * Returns the value of the '<em><b>Is SC</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is SC</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is SC</em>' attribute.
	 * @see #setIsSC(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getGraphLayout_IsSC()
	 * @model
	 * @generated
	 */
	boolean isIsSC();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.GraphLayout#isIsSC <em>Is SC</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is SC</em>' attribute.
	 * @see #isIsSC()
	 * @generated
	 */
	void setIsSC(boolean value);

} // GraphLayout
