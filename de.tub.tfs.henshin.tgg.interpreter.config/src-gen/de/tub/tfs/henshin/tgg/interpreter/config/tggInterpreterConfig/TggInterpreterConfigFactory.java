/**
 */
package de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage
 * @generated
 */
public interface TggInterpreterConfigFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TggInterpreterConfigFactory eINSTANCE = de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Tgg Interpreter Config</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Tgg Interpreter Config</em>'.
   * @generated
   */
  TggInterpreterConfig createTggInterpreterConfig();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  TggInterpreterConfigPackage getTggInterpreterConfigPackage();

} //TggInterpreterConfigFactory
