/**
 */
package TGG_correspondence;

import lu.uni.snt.secan.ttc_java.tTC_Java.AbstractTarget;
import lu.uni.snt.secan.ttc_xml.tTC_XML.AbstractCorr;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CORR</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link TGG_correspondence.CORR#getTgt <em>Tgt</em>}</li>
 * </ul>
 * </p>
 *
 * @see TGG_correspondence.TGG_correspondencePackage#getCORR()
 * @model
 * @generated
 */
public interface CORR extends AbstractCorr {
	/**
	 * Returns the value of the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt</em>' reference.
	 * @see #setTgt(AbstractTarget)
	 * @see TGG_correspondence.TGG_correspondencePackage#getCORR_Tgt()
	 * @model
	 * @generated
	 */
	AbstractTarget getTgt();

	/**
	 * Sets the value of the '{@link TGG_correspondence.CORR#getTgt <em>Tgt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgt</em>' reference.
	 * @see #getTgt()
	 * @generated
	 */
	void setTgt(AbstractTarget value);

} // CORR
