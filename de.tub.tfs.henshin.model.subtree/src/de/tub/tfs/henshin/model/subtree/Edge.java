/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.subtree;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Edge#getSource <em>Source</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Edge#getTarget <em>Target</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Edge#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Edge#getTargetNode <em>Target Node</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.subtree.Edge#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.tub.tfs.henshin.model.subtree.Subtree#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Subtree)
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getEdge_Source()
	 * @see de.tub.tfs.henshin.model.subtree.Subtree#getOutgoing
	 * @model opposite="outgoing"
	 * @generated
	 */
	Subtree getSource();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.subtree.Edge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Subtree value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.tub.tfs.henshin.model.subtree.Subtree#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Subtree)
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getEdge_Target()
	 * @see de.tub.tfs.henshin.model.subtree.Subtree#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	Subtree getTarget();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.subtree.Edge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Subtree value);

	/**
	 * Returns the value of the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Node</em>' reference.
	 * @see #setSourceNode(Node)
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getEdge_SourceNode()
	 * @model
	 * @generated
	 */
	Node getSourceNode();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.subtree.Edge#getSourceNode <em>Source Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Node</em>' reference.
	 * @see #getSourceNode()
	 * @generated
	 */
	void setSourceNode(Node value);

	/**
	 * Returns the value of the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Node</em>' reference.
	 * @see #setTargetNode(Node)
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getEdge_TargetNode()
	 * @model
	 * @generated
	 */
	Node getTargetNode();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.subtree.Edge#getTargetNode <em>Target Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Node</em>' reference.
	 * @see #getTargetNode()
	 * @generated
	 */
	void setTargetNode(Node value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see de.tub.tfs.henshin.model.subtree.SubtreePackage#getEdge_Type()
	 * @model required="true"
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.subtree.Edge#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

} // Edge
