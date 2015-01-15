/**
 */
package de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigFactory
 * @model kind="package"
 * @generated
 */
public interface TggInterpreterConfigPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "tggInterpreterConfig";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://tgg.tu-berlin.de/TggInterpreterConfig";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "tggInterpreterConfig";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  TggInterpreterConfigPackage eINSTANCE = de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigPackageImpl.init();

  /**
   * The meta object id for the '{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigImpl <em>Tgg Interpreter Config</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigImpl
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigPackageImpl#getTggInterpreterConfig()
   * @generated
   */
  int TGG_INTERPRETER_CONFIG = 0;

  /**
   * The feature id for the '<em><b>Options</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TGG_INTERPRETER_CONFIG__OPTIONS = 0;

  /**
   * The number of structural features of the '<em>Tgg Interpreter Config</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TGG_INTERPRETER_CONFIG_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.ProcessingMapEntryImpl <em>Processing Map Entry</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.ProcessingMapEntryImpl
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigPackageImpl#getProcessingMapEntry()
   * @generated
   */
  int PROCESSING_MAP_ENTRY = 1;

  /**
   * The feature id for the '<em><b>Key</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROCESSING_MAP_ENTRY__KEY = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROCESSING_MAP_ENTRY__VALUE = 1;

  /**
   * The number of structural features of the '<em>Processing Map Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROCESSING_MAP_ENTRY_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig <em>Tgg Interpreter Config</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Tgg Interpreter Config</em>'.
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig
   * @generated
   */
  EClass getTggInterpreterConfig();

  /**
   * Returns the meta object for the map '{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig#getOptions <em>Options</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>Options</em>'.
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig#getOptions()
   * @see #getTggInterpreterConfig()
   * @generated
   */
  EReference getTggInterpreterConfig_Options();

  /**
   * Returns the meta object for class '{@link java.util.Map.Entry <em>Processing Map Entry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Processing Map Entry</em>'.
   * @see java.util.Map.Entry
   * @model keyDataType="org.eclipse.emf.ecore.EString"
   *        valueDataType="org.eclipse.emf.ecore.EString"
   * @generated
   */
  EClass getProcessingMapEntry();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Key</em>'.
   * @see java.util.Map.Entry
   * @see #getProcessingMapEntry()
   * @generated
   */
  EAttribute getProcessingMapEntry_Key();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see java.util.Map.Entry
   * @see #getProcessingMapEntry()
   * @generated
   */
  EAttribute getProcessingMapEntry_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  TggInterpreterConfigFactory getTggInterpreterConfigFactory();

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
  interface Literals
  {
    /**
     * The meta object literal for the '{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigImpl <em>Tgg Interpreter Config</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigImpl
     * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigPackageImpl#getTggInterpreterConfig()
     * @generated
     */
    EClass TGG_INTERPRETER_CONFIG = eINSTANCE.getTggInterpreterConfig();

    /**
     * The meta object literal for the '<em><b>Options</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TGG_INTERPRETER_CONFIG__OPTIONS = eINSTANCE.getTggInterpreterConfig_Options();

    /**
     * The meta object literal for the '{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.ProcessingMapEntryImpl <em>Processing Map Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.ProcessingMapEntryImpl
     * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigPackageImpl#getProcessingMapEntry()
     * @generated
     */
    EClass PROCESSING_MAP_ENTRY = eINSTANCE.getProcessingMapEntry();

    /**
     * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROCESSING_MAP_ENTRY__KEY = eINSTANCE.getProcessingMapEntry_Key();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROCESSING_MAP_ENTRY__VALUE = eINSTANCE.getProcessingMapEntry_Value();

  }

} //TggInterpreterConfigPackage
