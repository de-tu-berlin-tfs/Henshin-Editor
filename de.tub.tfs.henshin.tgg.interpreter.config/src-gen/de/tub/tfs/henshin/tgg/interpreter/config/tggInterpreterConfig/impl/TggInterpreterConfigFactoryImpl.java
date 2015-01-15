/**
 */
package de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl;

import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TggInterpreterConfigFactoryImpl extends EFactoryImpl implements TggInterpreterConfigFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static TggInterpreterConfigFactory init()
  {
    try
    {
      TggInterpreterConfigFactory theTggInterpreterConfigFactory = (TggInterpreterConfigFactory)EPackage.Registry.INSTANCE.getEFactory(TggInterpreterConfigPackage.eNS_URI);
      if (theTggInterpreterConfigFactory != null)
      {
        return theTggInterpreterConfigFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new TggInterpreterConfigFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TggInterpreterConfigFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG: return createTggInterpreterConfig();
      case TggInterpreterConfigPackage.PROCESSING_MAP_ENTRY: return (EObject)createProcessingMapEntry();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TggInterpreterConfig createTggInterpreterConfig()
  {
    TggInterpreterConfigImpl tggInterpreterConfig = new TggInterpreterConfigImpl();
    return tggInterpreterConfig;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Map.Entry<String, String> createProcessingMapEntry()
  {
    ProcessingMapEntryImpl processingMapEntry = new ProcessingMapEntryImpl();
    return processingMapEntry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TggInterpreterConfigPackage getTggInterpreterConfigPackage()
  {
    return (TggInterpreterConfigPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static TggInterpreterConfigPackage getPackage()
  {
    return TggInterpreterConfigPackage.eINSTANCE;
  }

} //TggInterpreterConfigFactoryImpl
