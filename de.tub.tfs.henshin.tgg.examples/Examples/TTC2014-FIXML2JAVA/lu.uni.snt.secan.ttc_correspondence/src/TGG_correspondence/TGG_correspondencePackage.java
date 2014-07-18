/**
 */
package TGG_correspondence;

import lu.uni.snt.secan.ttc_xml.tTC_XML.TTC_XMLPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see TGG_correspondence.TGG_correspondenceFactory
 * @model kind="package"
 * @generated
 */
public interface TGG_correspondencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "TGG_correspondence";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://snt.uni.lu/TGGcorrespondence";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "TGGcorrespondence";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TGG_correspondencePackage eINSTANCE = TGG_correspondence.impl.TGG_correspondencePackageImpl.init();

	/**
	 * The meta object id for the '{@link TGG_correspondence.impl.CORRImpl <em>CORR</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGG_correspondence.impl.CORRImpl
	 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getCORR()
	 * @generated
	 */
	int CORR = 0;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR__SRC = TTC_XMLPackage.ABSTRACT_CORR__SRC;

	/**
	 * The feature id for the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR__TGT = TTC_XMLPackage.ABSTRACT_CORR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>CORR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR_FEATURE_COUNT = TTC_XMLPackage.ABSTRACT_CORR_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link TGG_correspondence.CORR <em>CORR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CORR</em>'.
	 * @see TGG_correspondence.CORR
	 * @generated
	 */
	EClass getCORR();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TGG_correspondenceFactory getTGG_correspondenceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link TGG_correspondence.impl.CORRImpl <em>CORR</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGG_correspondence.impl.CORRImpl
		 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getCORR()
		 * @generated
		 */
		EClass CORR = eINSTANCE.getCORR();

	}

} //TGG_correspondencePackage
