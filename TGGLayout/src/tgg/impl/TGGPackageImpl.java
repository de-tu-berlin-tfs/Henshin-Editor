/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;

import tgg.AttributeLayout;
import tgg.CritPair;
import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGGFactory;
import tgg.TGGPackage;
import tgg.TRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TGGPackageImpl extends EPackageImpl implements TGGPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeLayoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeLayoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass edgeLayoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass graphLayoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass critPairEClass = null;

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
	 * @see tgg.TGGPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TGGPackageImpl() {
		super(eNS_URI, TGGFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TGGPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TGGPackage init() {
		if (isInited) return (TGGPackage)EPackage.Registry.INSTANCE.getEPackage(TGGPackage.eNS_URI);

		// Obtain or create and register package
		TGGPackageImpl theTGGPackage = (TGGPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TGGPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TGGPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTGGPackage.createPackageContents();

		// Initialize created meta-data
		theTGGPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTGGPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TGGPackage.eNS_URI, theTGGPackage);
		return theTGGPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGG() {
		return tggEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Srcroot() {
		return (EReference)tggEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Tarroot() {
		return (EReference)tggEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Source() {
		return (EReference)tggEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Corresp() {
		return (EReference)tggEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Target() {
		return (EReference)tggEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Nodelayouts() {
		return (EReference)tggEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Edgelayouts() {
		return (EReference)tggEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Graphlayouts() {
		return (EReference)tggEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_TRules() {
		return (EReference)tggEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_CritPairs() {
		return (EReference)tggEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeLayout() {
		return nodeLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeLayout_X() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeLayout_Y() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeLayout_Hide() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeLayout_Node() {
		return (EReference)nodeLayoutEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeLayout_Lhsnode() {
		return (EReference)nodeLayoutEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeLayout_AttributeLayouts() {
		return (EReference)nodeLayoutEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeLayout_New() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeLayout_RhsTranslated() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeLayout_LhsTranslated() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeLayout() {
		return attributeLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeLayout_New() {
		return (EAttribute)attributeLayoutEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeLayout_Lhsattribute() {
		return (EReference)attributeLayoutEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeLayout_Rhsattribute() {
		return (EReference)attributeLayoutEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEdgeLayout() {
		return edgeLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEdgeLayout_New() {
		return (EAttribute)edgeLayoutEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeLayout_Lhsedge() {
		return (EReference)edgeLayoutEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeLayout_Rhsedge() {
		return (EReference)edgeLayoutEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEdgeLayout_RhsTranslated() {
		return (EAttribute)edgeLayoutEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEdgeLayout_LhsTranslated() {
		return (EAttribute)edgeLayoutEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGraphLayout() {
		return graphLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphLayout_DividerX() {
		return (EAttribute)graphLayoutEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphLayout_MaxY() {
		return (EAttribute)graphLayoutEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraphLayout_Graph() {
		return (EReference)graphLayoutEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphLayout_IsSC() {
		return (EAttribute)graphLayoutEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTRule() {
		return tRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTRule_Rule() {
		return (EReference)tRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTRule_Type() {
		return (EAttribute)tRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCritPair() {
		return critPairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCritPair_Overlapping() {
		return (EReference)critPairEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCritPair_Rule1() {
		return (EReference)critPairEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCritPair_Rule2() {
		return (EReference)critPairEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCritPair_MappingsOverToRule1() {
		return (EReference)critPairEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCritPair_MappingsOverToRule2() {
		return (EReference)critPairEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCritPair_MappingsRule1ToRule2() {
		return (EReference)critPairEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCritPair_Name() {
		return (EAttribute)critPairEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGFactory getTGGFactory() {
		return (TGGFactory)getEFactoryInstance();
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
		tggEClass = createEClass(TGG);
		createEReference(tggEClass, TGG__SRCROOT);
		createEReference(tggEClass, TGG__TARROOT);
		createEReference(tggEClass, TGG__SOURCE);
		createEReference(tggEClass, TGG__CORRESP);
		createEReference(tggEClass, TGG__TARGET);
		createEReference(tggEClass, TGG__NODELAYOUTS);
		createEReference(tggEClass, TGG__EDGELAYOUTS);
		createEReference(tggEClass, TGG__GRAPHLAYOUTS);
		createEReference(tggEClass, TGG__TRULES);
		createEReference(tggEClass, TGG__CRIT_PAIRS);

		nodeLayoutEClass = createEClass(NODE_LAYOUT);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__X);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__Y);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__HIDE);
		createEReference(nodeLayoutEClass, NODE_LAYOUT__NODE);
		createEReference(nodeLayoutEClass, NODE_LAYOUT__LHSNODE);
		createEReference(nodeLayoutEClass, NODE_LAYOUT__ATTRIBUTE_LAYOUTS);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__NEW);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__RHS_TRANSLATED);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__LHS_TRANSLATED);

		attributeLayoutEClass = createEClass(ATTRIBUTE_LAYOUT);
		createEAttribute(attributeLayoutEClass, ATTRIBUTE_LAYOUT__NEW);
		createEReference(attributeLayoutEClass, ATTRIBUTE_LAYOUT__LHSATTRIBUTE);
		createEReference(attributeLayoutEClass, ATTRIBUTE_LAYOUT__RHSATTRIBUTE);

		edgeLayoutEClass = createEClass(EDGE_LAYOUT);
		createEAttribute(edgeLayoutEClass, EDGE_LAYOUT__NEW);
		createEReference(edgeLayoutEClass, EDGE_LAYOUT__LHSEDGE);
		createEReference(edgeLayoutEClass, EDGE_LAYOUT__RHSEDGE);
		createEAttribute(edgeLayoutEClass, EDGE_LAYOUT__RHS_TRANSLATED);
		createEAttribute(edgeLayoutEClass, EDGE_LAYOUT__LHS_TRANSLATED);

		graphLayoutEClass = createEClass(GRAPH_LAYOUT);
		createEAttribute(graphLayoutEClass, GRAPH_LAYOUT__DIVIDER_X);
		createEAttribute(graphLayoutEClass, GRAPH_LAYOUT__MAX_Y);
		createEReference(graphLayoutEClass, GRAPH_LAYOUT__GRAPH);
		createEAttribute(graphLayoutEClass, GRAPH_LAYOUT__IS_SC);

		tRuleEClass = createEClass(TRULE);
		createEReference(tRuleEClass, TRULE__RULE);
		createEAttribute(tRuleEClass, TRULE__TYPE);

		critPairEClass = createEClass(CRIT_PAIR);
		createEReference(critPairEClass, CRIT_PAIR__OVERLAPPING);
		createEReference(critPairEClass, CRIT_PAIR__RULE1);
		createEReference(critPairEClass, CRIT_PAIR__RULE2);
		createEReference(critPairEClass, CRIT_PAIR__MAPPINGS_OVER_TO_RULE1);
		createEReference(critPairEClass, CRIT_PAIR__MAPPINGS_OVER_TO_RULE2);
		createEReference(critPairEClass, CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2);
		createEAttribute(critPairEClass, CRIT_PAIR__NAME);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(tggEClass, tgg.TGG.class, "TGG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGG_Srcroot(), ecorePackage.getEObject(), null, "srcroot", null, 0, 1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Tarroot(), ecorePackage.getEObject(), null, "tarroot", null, 0, 1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Source(), theEcorePackage.getEPackage(), null, "source", null, 0, 1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Corresp(), theEcorePackage.getEPackage(), null, "corresp", null, 0, 1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Target(), theEcorePackage.getEPackage(), null, "target", null, 0, 1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Nodelayouts(), this.getNodeLayout(), null, "nodelayouts", null, 0, -1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Edgelayouts(), this.getEdgeLayout(), null, "edgelayouts", null, 0, -1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Graphlayouts(), this.getGraphLayout(), null, "graphlayouts", null, 0, -1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_TRules(), this.getTRule(), null, "tRules", null, 0, -1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_CritPairs(), this.getCritPair(), null, "critPairs", null, 0, -1, tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeLayoutEClass, NodeLayout.class, "NodeLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNodeLayout_X(), ecorePackage.getEInt(), "x", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_Y(), ecorePackage.getEInt(), "y", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_Hide(), ecorePackage.getEBoolean(), "hide", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeLayout_Node(), theHenshinPackage.getNode(), null, "node", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeLayout_Lhsnode(), theHenshinPackage.getNode(), null, "lhsnode", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeLayout_AttributeLayouts(), this.getAttributeLayout(), null, "attributeLayouts", null, 0, -1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_New(), ecorePackage.getEBoolean(), "new", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_RhsTranslated(), ecorePackage.getEBooleanObject(), "rhsTranslated", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_LhsTranslated(), ecorePackage.getEBooleanObject(), "lhsTranslated", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeLayoutEClass, AttributeLayout.class, "AttributeLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeLayout_New(), ecorePackage.getEBoolean(), "new", null, 0, 1, AttributeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeLayout_Lhsattribute(), theHenshinPackage.getAttribute(), null, "lhsattribute", null, 0, 1, AttributeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeLayout_Rhsattribute(), theHenshinPackage.getAttribute(), null, "rhsattribute", null, 0, 1, AttributeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeLayoutEClass, EdgeLayout.class, "EdgeLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEdgeLayout_New(), ecorePackage.getEBoolean(), "new", null, 0, 1, EdgeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeLayout_Lhsedge(), theHenshinPackage.getEdge(), null, "lhsedge", null, 0, 1, EdgeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeLayout_Rhsedge(), theHenshinPackage.getEdge(), null, "rhsedge", null, 0, 1, EdgeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeLayout_RhsTranslated(), theEcorePackage.getEBooleanObject(), "rhsTranslated", null, 0, 1, EdgeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeLayout_LhsTranslated(), theEcorePackage.getEBooleanObject(), "lhsTranslated", null, 0, 1, EdgeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(graphLayoutEClass, GraphLayout.class, "GraphLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGraphLayout_DividerX(), ecorePackage.getEInt(), "dividerX", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphLayout_MaxY(), ecorePackage.getEInt(), "maxY", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGraphLayout_Graph(), theHenshinPackage.getGraph(), null, "graph", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphLayout_IsSC(), theEcorePackage.getEBoolean(), "isSC", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tRuleEClass, TRule.class, "TRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTRule_Rule(), theHenshinPackage.getRule(), null, "rule", null, 0, 1, TRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTRule_Type(), ecorePackage.getEString(), "type", null, 0, 1, TRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(critPairEClass, CritPair.class, "CritPair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCritPair_Overlapping(), theHenshinPackage.getGraph(), null, "overlapping", null, 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_Rule1(), theHenshinPackage.getRule(), null, "rule1", null, 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_Rule2(), theHenshinPackage.getRule(), null, "rule2", null, 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_MappingsOverToRule1(), theHenshinPackage.getMapping(), null, "mappingsOverToRule1", null, 0, -1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_MappingsOverToRule2(), theHenshinPackage.getMapping(), null, "mappingsOverToRule2", null, 0, -1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_MappingsRule1ToRule2(), theHenshinPackage.getMapping(), null, "mappingsRule1ToRule2", null, 0, -1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCritPair_Name(), theEcorePackage.getEString(), "name", "name", 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TGGPackageImpl
