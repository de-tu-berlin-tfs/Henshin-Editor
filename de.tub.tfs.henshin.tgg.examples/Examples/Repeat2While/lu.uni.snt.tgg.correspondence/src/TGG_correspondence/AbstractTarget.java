/**
 */
package TGG_correspondence;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link TGG_correspondence.AbstractTarget#getT2c <em>T2c</em>}</li>
 * </ul>
 * </p>
 *
 * @see TGG_correspondence.TGG_correspondencePackage#getAbstractTarget()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface AbstractTarget extends CDOObject {
	/**
	 * Returns the value of the '<em><b>T2c</b></em>' reference list.
	 * The list contents are of type {@link TGG_correspondence.CORR}.
	 * It is bidirectional and its opposite is '{@link TGG_correspondence.CORR#getTgt <em>Tgt</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>T2c</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>T2c</em>' reference list.
	 * @see TGG_correspondence.TGG_correspondencePackage#getAbstractTarget_T2c()
	 * @see TGG_correspondence.CORR#getTgt
	 * @model opposite="tgt"
	 * @generated
	 */
	EList<CORR> getT2c();

} // AbstractTarget
