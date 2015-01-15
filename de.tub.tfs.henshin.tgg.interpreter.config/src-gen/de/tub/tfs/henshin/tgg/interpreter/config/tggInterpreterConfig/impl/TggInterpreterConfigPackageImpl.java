/**
 */
package de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl;

import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig;
import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigFactory;
import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TggInterpreterConfigPackageImpl extends EPackageImpl implements TggInterpreterConfigPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass tggInterpreterConfigEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass processingMapEntryEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private TggInterpreterConfigPackageImpl()
  {
    super(eNS_URI, TggInterpreterConfigFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link TggInterpreterConfigPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static TggInterpreterConfigPackage init()
  {
    if (isInited) return (TggInterpreterConfigPackage)EPackage.Registry.INSTANCE.getEPackage(TggInterpreterConfigPackage.eNS_URI);

    // Obtain or create and register package
    TggInterpreterConfigPackageImpl theTggInterpreterConfigPackage = (TggInterpreterConfigPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TggInterpreterConfigPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TggInterpreterConfigPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theTggInterpreterConfigPackage.createPackageContents();

    // Initialize created meta-data
    theTggInterpreterConfigPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theTggInterpreterConfigPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(TggInterpreterConfigPackage.eNS_URI, theTggInterpreterConfigPackage);
    return theTggInterpreterConfigPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTggInterpreterConfig()
  {
    return tggInterpreterConfigEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTggInterpreterConfig_Options()
  {
    return (EReference)tggInterpreterConfigEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getProcessingMapEntry()
  {
    return processingMapEntryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProcessingMapEntry_Key()
  {
    return (EAttribute)processingMapEntryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProcessingMapEntry_Value()
  {
    return (EAttribute)processingMapEntryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TggInterpreterConfigFactory getTggInterpreterConfigFactory()
  {
    return (TggInterpreterConfigFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    tggInterpreterConfigEClass = createEClass(TGG_INTERPRETER_CONFIG);
    createEReference(tggInterpreterConfigEClass, TGG_INTERPRETER_CONFIG__OPTIONS);

    processingMapEntryEClass = createEClass(PROCESSING_MAP_ENTRY);
    createEAttribute(processingMapEntryEClass, PROCESSING_MAP_ENTRY__KEY);
    createEAttribute(processingMapEntryEClass, PROCESSING_MAP_ENTRY__VALUE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(tggInterpreterConfigEClass, TggInterpreterConfig.class, "TggInterpreterConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTggInterpreterConfig_Options(), this.getProcessingMapEntry(), null, "options", null, 0, -1, TggInterpreterConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(processingMapEntryEClass, Map.Entry.class, "ProcessingMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getProcessingMapEntry_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProcessingMapEntry_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //TggInterpreterConfigPackageImpl
