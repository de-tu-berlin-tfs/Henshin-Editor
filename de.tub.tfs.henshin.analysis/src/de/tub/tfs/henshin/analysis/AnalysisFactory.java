/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.analysis;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.analysis.AnalysisPackage
 * @generated
 */
public interface AnalysisFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnalysisFactory eINSTANCE = de.tub.tfs.henshin.analysis.impl.AnalysisFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Critical Pair</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Critical Pair</em>'.
	 * @generated
	 */
	CriticalPair createCriticalPair();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AnalysisPackage getAnalysisPackage();

} //AnalysisFactory
