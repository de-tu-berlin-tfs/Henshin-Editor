/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TggPackageImpl extends EPackageImpl implements TggPackage {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importedPackageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tripleGraphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum tripleComponentEEnum = null;

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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TggPackageImpl() {
		super(eNS_URI, TggFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TggPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TggPackage init() {
		if (isInited) return (TggPackage)EPackage.Registry.INSTANCE.getEPackage(TggPackage.eNS_URI);

		// Obtain or create and register package
		TggPackageImpl theTggPackage = (TggPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TggPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TggPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTggPackage.createPackageContents();

		// Initialize created meta-data
		theTggPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTggPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TggPackage.eNS_URI, theTggPackage);
		return theTggPackage;
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
	public EReference getTGG_SourcePkgs() {
		return (EReference)tggEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_CorrespondencePkgs() {
		return (EReference)tggEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_TargetPkgs() {
		return (EReference)tggEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_ImportedPkgs() {
		return (EReference)tggEClass.getEStructuralFeatures().get(13);
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
	public EAttribute getNodeLayout_Critical() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(9);
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
	public EAttribute getEdgeLayout_Critical() {
		return (EAttribute)edgeLayoutEClass.getEStructuralFeatures().get(5);
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
	public EClass getImportedPackage() {
		return importedPackageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportedPackage_LoadWithDefaultValues() {
		return (EAttribute)importedPackageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImportedPackage_Package() {
		return (EReference)importedPackageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportedPackage_Component() {
		return (EAttribute)importedPackageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTripleGraph() {
		return tripleGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTripleGraph_DividerSC_X() {
		return (EAttribute)tripleGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTripleGraph_DividerCT_X() {
		return (EAttribute)tripleGraphEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTripleGraph_DividerMaxY() {
		return (EAttribute)tripleGraphEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTripleGraph_DividerYOffset() {
		return (EAttribute)tripleGraphEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTNode() {
		return tNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTNode_X() {
		return (EAttribute)tNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTNode_Y() {
		return (EAttribute)tNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTNode_Component() {
		return (EAttribute)tNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTNode_MarkerType() {
		return (EAttribute)tNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRule() {
		return tggRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_IsMarked() {
		return (EAttribute)tggRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_ManualMatchingOrder() {
		return (EAttribute)tggRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_MarkerType() {
		return (EAttribute)tggRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTEdge() {
		return tEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTEdge_MarkerType() {
		return (EAttribute)tEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTAttribute() {
		return tAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTAttribute_MarkerType() {
		return (EAttribute)tAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTripleComponent() {
		return tripleComponentEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TggFactory getTggFactory() {
		return (TggFactory)getEFactoryInstance();
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
		createEReference(tggEClass, TGG__SOURCE_PKGS);
		createEReference(tggEClass, TGG__CORRESPONDENCE_PKGS);
		createEReference(tggEClass, TGG__TARGET_PKGS);
		createEReference(tggEClass, TGG__IMPORTED_PKGS);

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
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__CRITICAL);

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
		createEAttribute(edgeLayoutEClass, EDGE_LAYOUT__CRITICAL);

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

		importedPackageEClass = createEClass(IMPORTED_PACKAGE);
		createEAttribute(importedPackageEClass, IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES);
		createEReference(importedPackageEClass, IMPORTED_PACKAGE__PACKAGE);
		createEAttribute(importedPackageEClass, IMPORTED_PACKAGE__COMPONENT);

		tripleGraphEClass = createEClass(TRIPLE_GRAPH);
		createEAttribute(tripleGraphEClass, TRIPLE_GRAPH__DIVIDER_SC_X);
		createEAttribute(tripleGraphEClass, TRIPLE_GRAPH__DIVIDER_CT_X);
		createEAttribute(tripleGraphEClass, TRIPLE_GRAPH__DIVIDER_MAX_Y);
		createEAttribute(tripleGraphEClass, TRIPLE_GRAPH__DIVIDER_YOFFSET);

		tNodeEClass = createEClass(TNODE);
		createEAttribute(tNodeEClass, TNODE__X);
		createEAttribute(tNodeEClass, TNODE__Y);
		createEAttribute(tNodeEClass, TNODE__COMPONENT);
		createEAttribute(tNodeEClass, TNODE__MARKER_TYPE);

		tggRuleEClass = createEClass(TGG_RULE);
		createEAttribute(tggRuleEClass, TGG_RULE__IS_MARKED);
		createEAttribute(tggRuleEClass, TGG_RULE__MANUAL_MATCHING_ORDER);
		createEAttribute(tggRuleEClass, TGG_RULE__MARKER_TYPE);

		tEdgeEClass = createEClass(TEDGE);
		createEAttribute(tEdgeEClass, TEDGE__MARKER_TYPE);

		tAttributeEClass = createEClass(TATTRIBUTE);
		createEAttribute(tAttributeEClass, TATTRIBUTE__MARKER_TYPE);

		// Create enums
		tripleComponentEEnum = createEEnum(TRIPLE_COMPONENT);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggEClass.getESuperTypes().add(theHenshinPackage.getModule());
		tripleGraphEClass.getESuperTypes().add(theHenshinPackage.getGraph());
		tNodeEClass.getESuperTypes().add(theHenshinPackage.getNode());
		tggRuleEClass.getESuperTypes().add(theHenshinPackage.getRule());
		tEdgeEClass.getESuperTypes().add(theHenshinPackage.getEdge());
		tAttributeEClass.getESuperTypes().add(theHenshinPackage.getAttribute());

		// Initialize classes and features; add operations and parameters
		initEClass(tggEClass, de.tub.tfs.henshin.tgg.TGG.class, "TGG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGG_Srcroot(), ecorePackage.getEObject(), null, "srcroot", null, 0, 1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Tarroot(), ecorePackage.getEObject(), null, "tarroot", null, 0, 1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Source(), theEcorePackage.getEPackage(), null, "source", null, 0, 1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Corresp(), theEcorePackage.getEPackage(), null, "corresp", null, 0, 1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Target(), theEcorePackage.getEPackage(), null, "target", null, 0, 1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Nodelayouts(), this.getNodeLayout(), null, "nodelayouts", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Edgelayouts(), this.getEdgeLayout(), null, "edgelayouts", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Graphlayouts(), this.getGraphLayout(), null, "graphlayouts", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_TRules(), this.getTRule(), null, "tRules", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_CritPairs(), this.getCritPair(), null, "critPairs", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_SourcePkgs(), theEcorePackage.getEPackage(), null, "sourcePkgs", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_CorrespondencePkgs(), theEcorePackage.getEPackage(), null, "correspondencePkgs", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_TargetPkgs(), theEcorePackage.getEPackage(), null, "targetPkgs", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_ImportedPkgs(), this.getImportedPackage(), null, "importedPkgs", null, 0, -1, de.tub.tfs.henshin.tgg.TGG.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		initEAttribute(getNodeLayout_Critical(), theEcorePackage.getEBoolean(), "critical", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		initEAttribute(getEdgeLayout_Critical(), theEcorePackage.getEBoolean(), "critical", null, 0, 1, EdgeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(graphLayoutEClass, GraphLayout.class, "GraphLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGraphLayout_DividerX(), ecorePackage.getEInt(), "dividerX", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphLayout_MaxY(), ecorePackage.getEInt(), "maxY", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGraphLayout_Graph(), theHenshinPackage.getGraph(), null, "graph", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphLayout_IsSC(), theEcorePackage.getEBoolean(), "isSC", null, 0, 1, GraphLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tRuleEClass, TRule.class, "TRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTRule_Rule(), theHenshinPackage.getRule(), null, "rule", null, 0, 1, TRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTRule_Type(), ecorePackage.getEString(), "type", null, 0, 1, TRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(critPairEClass, CritPair.class, "CritPair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCritPair_Overlapping(), this.getTripleGraph(), null, "overlapping", null, 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_Rule1(), theHenshinPackage.getRule(), null, "rule1", null, 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_Rule2(), theHenshinPackage.getRule(), null, "rule2", null, 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_MappingsOverToRule1(), theHenshinPackage.getMapping(), null, "mappingsOverToRule1", null, 0, -1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_MappingsOverToRule2(), theHenshinPackage.getMapping(), null, "mappingsOverToRule2", null, 0, -1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCritPair_MappingsRule1ToRule2(), theHenshinPackage.getMapping(), null, "mappingsRule1ToRule2", null, 0, -1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCritPair_Name(), theEcorePackage.getEString(), "name", "name", 0, 1, CritPair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importedPackageEClass, ImportedPackage.class, "ImportedPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportedPackage_LoadWithDefaultValues(), theEcorePackage.getEBoolean(), "loadWithDefaultValues", null, 0, 1, ImportedPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImportedPackage_Package(), theEcorePackage.getEPackage(), null, "package", null, 0, 1, ImportedPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportedPackage_Component(), this.getTripleComponent(), "component", null, 0, 1, ImportedPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tripleGraphEClass, TripleGraph.class, "TripleGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTripleGraph_DividerSC_X(), theEcorePackage.getEInt(), "dividerSC_X", null, 0, 1, TripleGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTripleGraph_DividerCT_X(), theEcorePackage.getEInt(), "dividerCT_X", null, 0, 1, TripleGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTripleGraph_DividerMaxY(), theEcorePackage.getEInt(), "dividerMaxY", null, 0, 1, TripleGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTripleGraph_DividerYOffset(), theEcorePackage.getEInt(), "dividerYOffset", "0", 0, 1, TripleGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tNodeEClass, TNode.class, "TNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTNode_X(), theEcorePackage.getEInt(), "x", "0", 0, 1, TNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTNode_Y(), theEcorePackage.getEInt(), "y", "0", 0, 1, TNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTNode_Component(), this.getTripleComponent(), "component", null, 0, 1, TNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTNode_MarkerType(), theEcorePackage.getEString(), "markerType", null, 0, 1, TNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleEClass, TGGRule.class, "TGGRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGRule_IsMarked(), ecorePackage.getEBooleanObject(), "isMarked", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_ManualMatchingOrder(), theEcorePackage.getEBoolean(), "manualMatchingOrder", "false", 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_MarkerType(), theEcorePackage.getEString(), "markerType", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tEdgeEClass, TEdge.class, "TEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTEdge_MarkerType(), theEcorePackage.getEString(), "markerType", null, 0, 1, TEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tAttributeEClass, TAttribute.class, "TAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTAttribute_MarkerType(), theEcorePackage.getEString(), "markerType", null, 0, 1, TAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(tripleComponentEEnum, TripleComponent.class, "TripleComponent");
		addEEnumLiteral(tripleComponentEEnum, TripleComponent.SOURCE);
		addEEnumLiteral(tripleComponentEEnum, TripleComponent.CORRESPONDENCE);
		addEEnumLiteral(tripleComponentEEnum, TripleComponent.TARGET);

		// Create resource
		createResource(eNS_URI);
	}

} //TggPackageImpl
