/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.analysis.impl;

import de.tub.tfs.henshin.analysis.AnalysisFactory;
import de.tub.tfs.henshin.analysis.AnalysisPackage;
import de.tub.tfs.henshin.analysis.CausalityType;
import de.tub.tfs.henshin.analysis.CriticalPair;
import de.tub.tfs.henshin.analysis.CriticalPairType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;

import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnalysisPackageImpl extends EPackageImpl implements AnalysisPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass criticalPairEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum causalityTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum criticalPairTypeEEnum = null;

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
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AnalysisPackageImpl() {
		super(eNS_URI, AnalysisFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AnalysisPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AnalysisPackage init() {
		if (isInited) return (AnalysisPackage)EPackage.Registry.INSTANCE.getEPackage(AnalysisPackage.eNS_URI);

		// Obtain or create and register package
		AnalysisPackageImpl theAnalysisPackage = (AnalysisPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AnalysisPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AnalysisPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		HenshinPackageImpl theHenshinPackage = (HenshinPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI) instanceof HenshinPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI) : HenshinPackage.eINSTANCE);

		// Create package meta-data objects
		theAnalysisPackage.createPackageContents();
		theHenshinPackage.createPackageContents();

		// Initialize created meta-data
		theAnalysisPackage.initializePackageContents();
		theHenshinPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAnalysisPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AnalysisPackage.eNS_URI, theAnalysisPackage);
		return theAnalysisPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCriticalPair() {
		return criticalPairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCriticalPair_Type() {
		return (EAttribute)criticalPairEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_Rule1() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_Rule2() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_SourceUnit() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_TargetUnit() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_Overlapping() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_MappingsOverlappingToRule1() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_MappingsOverlappingToRule2() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_MappingsRule1ToRule2() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCriticalPair_CriticalObjects() {
		return (EReference)criticalPairEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCausalityType() {
		return causalityTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCriticalPairType() {
		return criticalPairTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnalysisFactory getAnalysisFactory() {
		return (AnalysisFactory)getEFactoryInstance();
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
		criticalPairEClass = createEClass(CRITICAL_PAIR);
		createEAttribute(criticalPairEClass, CRITICAL_PAIR__TYPE);
		createEReference(criticalPairEClass, CRITICAL_PAIR__RULE1);
		createEReference(criticalPairEClass, CRITICAL_PAIR__RULE2);
		createEReference(criticalPairEClass, CRITICAL_PAIR__SOURCE_UNIT);
		createEReference(criticalPairEClass, CRITICAL_PAIR__TARGET_UNIT);
		createEReference(criticalPairEClass, CRITICAL_PAIR__OVERLAPPING);
		createEReference(criticalPairEClass, CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1);
		createEReference(criticalPairEClass, CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2);
		createEReference(criticalPairEClass, CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2);
		createEReference(criticalPairEClass, CRITICAL_PAIR__CRITICAL_OBJECTS);

		// Create enums
		causalityTypeEEnum = createEEnum(CAUSALITY_TYPE);
		criticalPairTypeEEnum = createEEnum(CRITICAL_PAIR_TYPE);
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
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(criticalPairEClass, CriticalPair.class, "CriticalPair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCriticalPair_Type(), this.getCriticalPairType(), "type", "0", 0, 1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_Rule1(), theHenshinPackage.getRule(), null, "rule1", null, 0, 1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_Rule2(), theHenshinPackage.getRule(), null, "rule2", null, 0, 1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_SourceUnit(), theHenshinPackage.getUnit(), null, "sourceUnit", null, 0, 1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_TargetUnit(), theHenshinPackage.getUnit(), null, "targetUnit", null, 0, 1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_Overlapping(), theHenshinPackage.getGraph(), null, "overlapping", null, 0, 1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_MappingsOverlappingToRule1(), theHenshinPackage.getMapping(), null, "mappingsOverlappingToRule1", null, 0, -1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_MappingsOverlappingToRule2(), theHenshinPackage.getMapping(), null, "mappingsOverlappingToRule2", null, 0, -1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_MappingsRule1ToRule2(), theHenshinPackage.getMapping(), null, "mappingsRule1ToRule2", null, 0, -1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCriticalPair_CriticalObjects(), ecorePackage.getEObject(), null, "criticalObjects", null, 0, -1, CriticalPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(causalityTypeEEnum, CausalityType.class, "CausalityType");
		addEEnumLiteral(causalityTypeEEnum, CausalityType.INITIALIZATION);
		addEEnumLiteral(causalityTypeEEnum, CausalityType.CONFLICT);
		addEEnumLiteral(causalityTypeEEnum, CausalityType.DEPENDENCY_ALONG_CONTROLFLOW);
		addEEnumLiteral(causalityTypeEEnum, CausalityType.DEPENDENCY_AGAINST_CONTROLFLOW);

		initEEnum(criticalPairTypeEEnum, CriticalPairType.class, "CriticalPairType");
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.DELETE_USE_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.DELETE_NEED_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.PRODUCE_FORBID_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.PRODUCE_EDGE_DELTE_NODE_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.CHANGE_USE_ATTR_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.CHANGE_NEED_ATTR_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.CHANGE_FORBID_ATTR_CONFLICT);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.DELETE_FORBID_DEPENDENCY);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.PRODUCE_USE_DEPENDENCY);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.PRODUCE_DELETE_DEPENDENCY);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.PRODUCE_NEED_DEPENDENCY);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.CHANGE_USE_ATTR_DEPENDENCY);
		addEEnumLiteral(criticalPairTypeEEnum, CriticalPairType.CHANGE_FORBID_ATTR_DEPENDENCY);

		// Create resource
		createResource(eNS_URI);
	}

} //AnalysisPackageImpl
