/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see correspondece.CorrespondecePackage
 * @generated
 */
public interface CorrespondeceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CorrespondeceFactory eINSTANCE = correspondece.impl.CorrespondeceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>CT</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CT</em>'.
	 * @generated
	 */
	CT createCT();

	/**
	 * Returns a new object of class '<em>AFK</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>AFK</em>'.
	 * @generated
	 */
	AFK createAFK();

	/**
	 * Returns a new object of class '<em>AC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>AC</em>'.
	 * @generated
	 */
	AC createAC();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorrespondecePackage getCorrespondecePackage();

} //CorrespondeceFactory
