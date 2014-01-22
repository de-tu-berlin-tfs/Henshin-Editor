/**
 */
package TGG_correspondence;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see TGG_correspondence.TGG_correspondencePackage
 * @generated
 */
public interface TGG_correspondenceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TGG_correspondenceFactory eINSTANCE = TGG_correspondence.impl.TGG_correspondenceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>CORR</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CORR</em>'.
	 * @generated
	 */
	CORR createCORR();

	/**
	 * Returns a new object of class '<em>Abstract Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstract Source</em>'.
	 * @generated
	 */
	AbstractSource createAbstractSource();

	/**
	 * Returns a new object of class '<em>Abstract Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstract Target</em>'.
	 * @generated
	 */
	AbstractTarget createAbstractTarget();

	/**
	 * Returns a new object of class '<em>Gen CORR</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Gen CORR</em>'.
	 * @generated
	 */
	GenCORR createGenCORR();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TGG_correspondencePackage getTGG_correspondencePackage();

} //TGG_correspondenceFactory
