/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.analysis;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see de.tub.tfs.henshin.analysis.AnalysisFactory
 * @model kind="package"
 * @generated
 */
public interface AnalysisPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "analysis";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2011/Henshin/Analysis";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "analysis";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnalysisPackage eINSTANCE = de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl <em>Critical Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.analysis.impl.CriticalPairImpl
	 * @see de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl#getCriticalPair()
	 * @generated
	 */
	int CRITICAL_PAIR = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Rule1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__RULE1 = 1;

	/**
	 * The feature id for the '<em><b>Rule2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__RULE2 = 2;

	/**
	 * The feature id for the '<em><b>Source Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__SOURCE_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Target Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__TARGET_UNIT = 4;

	/**
	 * The feature id for the '<em><b>Overlapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__OVERLAPPING = 5;

	/**
	 * The feature id for the '<em><b>Mappings Overlapping To Rule1</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1 = 6;

	/**
	 * The feature id for the '<em><b>Mappings Overlapping To Rule2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2 = 7;

	/**
	 * The feature id for the '<em><b>Mappings Rule1 To Rule2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2 = 8;

	/**
	 * The feature id for the '<em><b>Critical Objects</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__CRITICAL_OBJECTS = 9;

	/**
	 * The number of structural features of the '<em>Critical Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link de.tub.tfs.henshin.analysis.CausalityType <em>Causality Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.analysis.CausalityType
	 * @see de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl#getCausalityType()
	 * @generated
	 */
	int CAUSALITY_TYPE = 1;

	/**
	 * The meta object id for the '{@link de.tub.tfs.henshin.analysis.CriticalPairType <em>Critical Pair Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.analysis.CriticalPairType
	 * @see de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl#getCriticalPairType()
	 * @generated
	 */
	int CRITICAL_PAIR_TYPE = 2;


	/**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.analysis.CriticalPair <em>Critical Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Critical Pair</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair
	 * @generated
	 */
	EClass getCriticalPair();

	/**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.analysis.CriticalPair#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getType()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EAttribute getCriticalPair_Type();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.analysis.CriticalPair#getRule1 <em>Rule1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule1</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getRule1()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_Rule1();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.analysis.CriticalPair#getRule2 <em>Rule2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule2</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getRule2()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_Rule2();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.analysis.CriticalPair#getSourceUnit <em>Source Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Unit</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getSourceUnit()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_SourceUnit();

	/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.analysis.CriticalPair#getTargetUnit <em>Target Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Unit</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getTargetUnit()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_TargetUnit();

	/**
	 * Returns the meta object for the containment reference '{@link de.tub.tfs.henshin.analysis.CriticalPair#getOverlapping <em>Overlapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Overlapping</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getOverlapping()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_Overlapping();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.analysis.CriticalPair#getMappingsOverlappingToRule1 <em>Mappings Overlapping To Rule1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings Overlapping To Rule1</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getMappingsOverlappingToRule1()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_MappingsOverlappingToRule1();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.analysis.CriticalPair#getMappingsOverlappingToRule2 <em>Mappings Overlapping To Rule2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings Overlapping To Rule2</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getMappingsOverlappingToRule2()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_MappingsOverlappingToRule2();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.analysis.CriticalPair#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings Rule1 To Rule2</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getMappingsRule1ToRule2()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_MappingsRule1ToRule2();

	/**
	 * Returns the meta object for the reference list '{@link de.tub.tfs.henshin.analysis.CriticalPair#getCriticalObjects <em>Critical Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Critical Objects</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPair#getCriticalObjects()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_CriticalObjects();

	/**
	 * Returns the meta object for enum '{@link de.tub.tfs.henshin.analysis.CausalityType <em>Causality Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Causality Type</em>'.
	 * @see de.tub.tfs.henshin.analysis.CausalityType
	 * @generated
	 */
	EEnum getCausalityType();

	/**
	 * Returns the meta object for enum '{@link de.tub.tfs.henshin.analysis.CriticalPairType <em>Critical Pair Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Critical Pair Type</em>'.
	 * @see de.tub.tfs.henshin.analysis.CriticalPairType
	 * @generated
	 */
	EEnum getCriticalPairType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AnalysisFactory getAnalysisFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.analysis.impl.CriticalPairImpl <em>Critical Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.analysis.impl.CriticalPairImpl
		 * @see de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl#getCriticalPair()
		 * @generated
		 */
		EClass CRITICAL_PAIR = eINSTANCE.getCriticalPair();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CRITICAL_PAIR__TYPE = eINSTANCE.getCriticalPair_Type();

		/**
		 * The meta object literal for the '<em><b>Rule1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__RULE1 = eINSTANCE.getCriticalPair_Rule1();

		/**
		 * The meta object literal for the '<em><b>Rule2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__RULE2 = eINSTANCE.getCriticalPair_Rule2();

		/**
		 * The meta object literal for the '<em><b>Source Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__SOURCE_UNIT = eINSTANCE.getCriticalPair_SourceUnit();

		/**
		 * The meta object literal for the '<em><b>Target Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__TARGET_UNIT = eINSTANCE.getCriticalPair_TargetUnit();

		/**
		 * The meta object literal for the '<em><b>Overlapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__OVERLAPPING = eINSTANCE.getCriticalPair_Overlapping();

		/**
		 * The meta object literal for the '<em><b>Mappings Overlapping To Rule1</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE1 = eINSTANCE.getCriticalPair_MappingsOverlappingToRule1();

		/**
		 * The meta object literal for the '<em><b>Mappings Overlapping To Rule2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__MAPPINGS_OVERLAPPING_TO_RULE2 = eINSTANCE.getCriticalPair_MappingsOverlappingToRule2();

		/**
		 * The meta object literal for the '<em><b>Mappings Rule1 To Rule2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__MAPPINGS_RULE1_TO_RULE2 = eINSTANCE.getCriticalPair_MappingsRule1ToRule2();

		/**
		 * The meta object literal for the '<em><b>Critical Objects</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__CRITICAL_OBJECTS = eINSTANCE.getCriticalPair_CriticalObjects();

		/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.analysis.CausalityType <em>Causality Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.analysis.CausalityType
		 * @see de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl#getCausalityType()
		 * @generated
		 */
		EEnum CAUSALITY_TYPE = eINSTANCE.getCausalityType();

		/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.analysis.CriticalPairType <em>Critical Pair Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.analysis.CriticalPairType
		 * @see de.tub.tfs.henshin.analysis.impl.AnalysisPackageImpl#getCriticalPairType()
		 * @generated
		 */
		EEnum CRITICAL_PAIR_TYPE = eINSTANCE.getCriticalPairType();

	}

} //AnalysisPackage
