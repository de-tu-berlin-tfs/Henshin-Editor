/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.layout;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage
 * @generated
 */
public interface HenshinLayoutFactory extends EFactory {
        /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        HenshinLayoutFactory eINSTANCE = de.tub.tfs.henshin.model.layout.impl.HenshinLayoutFactoryImpl.init();

        /**
	 * Returns a new object of class '<em>Layout System</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Layout System</em>'.
	 * @generated
	 */
        LayoutSystem createLayoutSystem();

        /**
	 * Returns a new object of class '<em>Node Layout</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Layout</em>'.
	 * @generated
	 */
        NodeLayout createNodeLayout();

        /**
	 * Returns a new object of class '<em>Flow Element Layout</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Flow Element Layout</em>'.
	 * @generated
	 */
        FlowElementLayout createFlowElementLayout();

        /**
	 * Returns a new object of class '<em>EContainer Descriptor</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>EContainer Descriptor</em>'.
	 * @generated
	 */
        EContainerDescriptor createEContainerDescriptor();

        /**
	 * Returns a new object of class '<em>Layout</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Layout</em>'.
	 * @generated
	 */
        Layout createLayout();

        /**
	 * Returns a new object of class '<em>Sub Unit Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sub Unit Layout</em>'.
	 * @generated
	 */
	SubUnitLayout createSubUnitLayout();

								/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
        HenshinLayoutPackage getHenshinLayoutPackage();

} //HenshinLayoutFactory
