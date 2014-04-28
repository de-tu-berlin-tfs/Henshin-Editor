/**
 */
package TGG_correspondence.impl;

import TGG_correspondence.TGG_correspondenceFactory;
import TGG_correspondence.TGG_correspondencePackage;

import lu.uni.snt.secan.ttc_java.tTC_Java.TTC_JavaPackage;

import lu.uni.snt.secan.ttc_xml.tTC_XML.TTC_XMLPackage;

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
public class TGG_correspondencePackageImpl extends EPackageImpl implements TGG_correspondencePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass corrEClass = null;

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
	 * @see TGG_correspondence.TGG_correspondencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TGG_correspondencePackageImpl() {
		super(eNS_URI, TGG_correspondenceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TGG_correspondencePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TGG_correspondencePackage init() {
		if (isInited) return (TGG_correspondencePackage)EPackage.Registry.INSTANCE.getEPackage(TGG_correspondencePackage.eNS_URI);

		// Obtain or create and register package
		TGG_correspondencePackageImpl theTGG_correspondencePackage = (TGG_correspondencePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TGG_correspondencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TGG_correspondencePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TTC_JavaPackage.eINSTANCE.eClass();
		TTC_XMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTGG_correspondencePackage.createPackageContents();

		// Initialize created meta-data
		theTGG_correspondencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTGG_correspondencePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TGG_correspondencePackage.eNS_URI, theTGG_correspondencePackage);
		return theTGG_correspondencePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCORR() {
		return corrEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCORR_Tgt() {
		return (EReference)corrEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGG_correspondenceFactory getTGG_correspondenceFactory() {
		return (TGG_correspondenceFactory)getEFactoryInstance();
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
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		corrEClass = createEClass(CORR);
		createEReference(corrEClass, CORR__TGT);
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
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TTC_XMLPackage theTTC_XMLPackage = (TTC_XMLPackage)EPackage.Registry.INSTANCE.getEPackage(TTC_XMLPackage.eNS_URI);
		TTC_JavaPackage theTTC_JavaPackage = (TTC_JavaPackage)EPackage.Registry.INSTANCE.getEPackage(TTC_JavaPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		corrEClass.getESuperTypes().add(theTTC_XMLPackage.getAbstractCorr());

		// Initialize classes and features; add operations and parameters
		initEClass(corrEClass, TGG_correspondence.CORR.class, "CORR", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCORR_Tgt(), theTTC_JavaPackage.getAbstractTarget(), null, "tgt", null, 0, 1, TGG_correspondence.CORR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TGG_correspondencePackageImpl
