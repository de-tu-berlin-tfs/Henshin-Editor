/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.henshin.model.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.EdgeLayout#isNew <em>New</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.EdgeLayout#getLhsedge <em>Lhsedge</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.EdgeLayout#getRhsedge <em>Rhsedge</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.EdgeLayout#getRhsTranslated <em>Rhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.EdgeLayout#getLhsTranslated <em>Lhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.EdgeLayout#isCritical <em>Critical</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout()
 * @model
 * @generated
 */
public interface EdgeLayout extends EObject {
	/**
	 * Returns the value of the '<em><b>New</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New</em>' attribute.
	 * @see #setNew(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout_New()
	 * @model
	 * @generated
	 */
	boolean isNew();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.EdgeLayout#isNew <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New</em>' attribute.
	 * @see #isNew()
	 * @generated
	 */
	void setNew(boolean value);

	/**
	 * Returns the value of the '<em><b>Lhsedge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhsedge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhsedge</em>' reference.
	 * @see #setLhsedge(Edge)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout_Lhsedge()
	 * @model
	 * @generated
	 */
	Edge getLhsedge();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.EdgeLayout#getLhsedge <em>Lhsedge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhsedge</em>' reference.
	 * @see #getLhsedge()
	 * @generated
	 */
	void setLhsedge(Edge value);

	/**
	 * Returns the value of the '<em><b>Rhsedge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhsedge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhsedge</em>' reference.
	 * @see #setRhsedge(Edge)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout_Rhsedge()
	 * @model
	 * @generated
	 */
	Edge getRhsedge();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.EdgeLayout#getRhsedge <em>Rhsedge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhsedge</em>' reference.
	 * @see #getRhsedge()
	 * @generated
	 */
	void setRhsedge(Edge value);

	/**
	 * Returns the value of the '<em><b>Rhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs Translated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs Translated</em>' attribute.
	 * @see #setRhsTranslated(Boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout_RhsTranslated()
	 * @model
	 * @generated
	 */
	Boolean getRhsTranslated();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.EdgeLayout#getRhsTranslated <em>Rhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhs Translated</em>' attribute.
	 * @see #getRhsTranslated()
	 * @generated
	 */
	void setRhsTranslated(Boolean value);

	/**
	 * Returns the value of the '<em><b>Lhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs Translated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs Translated</em>' attribute.
	 * @see #setLhsTranslated(Boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout_LhsTranslated()
	 * @model
	 * @generated
	 */
	Boolean getLhsTranslated();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.EdgeLayout#getLhsTranslated <em>Lhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhs Translated</em>' attribute.
	 * @see #getLhsTranslated()
	 * @generated
	 */
	void setLhsTranslated(Boolean value);

	/**
	 * Returns the value of the '<em><b>Critical</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Critical</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Critical</em>' attribute.
	 * @see #setCritical(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getEdgeLayout_Critical()
	 * @model
	 * @generated
	 */
	boolean isCritical();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.EdgeLayout#isCritical <em>Critical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Critical</em>' attribute.
	 * @see #isCritical()
	 * @generated
	 */
	void setCritical(boolean value);

} // EdgeLayout
