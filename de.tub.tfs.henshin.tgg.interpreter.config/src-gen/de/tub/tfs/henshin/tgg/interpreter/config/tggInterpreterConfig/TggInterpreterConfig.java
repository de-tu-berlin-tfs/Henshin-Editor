/**
 */
package de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tgg Interpreter Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig#getOptions <em>Options</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage#getTggInterpreterConfig()
 * @model
 * @generated
 */
public interface TggInterpreterConfig extends EObject
{
  /**
   * Returns the value of the '<em><b>Options</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Options</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Options</em>' map.
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage#getTggInterpreterConfig_Options()
   * @model mapType="de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.ProcessingMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
   * @generated
   */
  EMap<String, String> getOptions();

} // TggInterpreterConfig
