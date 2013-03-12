/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tgg.TGGPackage
 * @generated
 */
public interface TGGFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TGGFactory eINSTANCE = tgg.impl.TGGFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG</em>'.
	 * @generated
	 */
	TGG createTGG();

	/**
	 * Returns a new object of class '<em>Node Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Layout</em>'.
	 * @generated
	 */
	NodeLayout createNodeLayout();

	/**
	 * Returns a new object of class '<em>Attribute Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Layout</em>'.
	 * @generated
	 */
	AttributeLayout createAttributeLayout();

	/**
	 * Returns a new object of class '<em>Edge Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edge Layout</em>'.
	 * @generated
	 */
	EdgeLayout createEdgeLayout();

	/**
	 * Returns a new object of class '<em>Graph Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graph Layout</em>'.
	 * @generated
	 */
	GraphLayout createGraphLayout();

	/**
	 * Returns a new object of class '<em>TRule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TRule</em>'.
	 * @generated
	 */
	TRule createTRule();

	/**
	 * Returns a new object of class '<em>Crit Pair</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Crit Pair</em>'.
	 * @generated
	 */
	CritPair createCritPair();

	/**
	 * Returns a new object of class '<em>Imported Package</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Imported Package</em>'.
	 * @generated
	 */
	ImportedPackage createImportedPackage();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TGGPackage getTGGPackage();

} //TGGFactory
