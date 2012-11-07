/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg;

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
 * @see tgg.TGGFactory
 * @model kind="package"
 * @generated
 */
public interface TGGPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "tgg";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tgg.tu-berlin.de";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tgg";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TGGPackage eINSTANCE = tgg.impl.TGGPackageImpl.init();

	/**
	 * The meta object id for the '{@link tgg.impl.TGGImpl <em>TGG</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.TGGImpl
	 * @see tgg.impl.TGGPackageImpl#getTGG()
	 * @generated
	 */
	int TGG = 0;

	/**
	 * The feature id for the '<em><b>Srcroot</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__SRCROOT = 0;

	/**
	 * The feature id for the '<em><b>Tarroot</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__TARROOT = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Corresp</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__CORRESP = 3;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__TARGET = 4;

	/**
	 * The feature id for the '<em><b>Nodelayouts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__NODELAYOUTS = 5;

	/**
	 * The feature id for the '<em><b>Edgelayouts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__EDGELAYOUTS = 6;

	/**
	 * The feature id for the '<em><b>Graphlayouts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__GRAPHLAYOUTS = 7;

	/**
	 * The feature id for the '<em><b>TRules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__TRULES = 8;

	/**
	 * The feature id for the '<em><b>Crit Pairs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__CRIT_PAIRS = 9;

	/**
	 * The number of structural features of the '<em>TGG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link tgg.impl.NodeLayoutImpl <em>Node Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.NodeLayoutImpl
	 * @see tgg.impl.TGGPackageImpl#getNodeLayout()
	 * @generated
	 */
	int NODE_LAYOUT = 1;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__Y = 1;

	/**
	 * The feature id for the '<em><b>Hide</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__HIDE = 2;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__NODE = 3;

	/**
	 * The feature id for the '<em><b>Lhsnode</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__LHSNODE = 4;

	/**
	 * The feature id for the '<em><b>Attribute Layouts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__ATTRIBUTE_LAYOUTS = 5;

	/**
	 * The feature id for the '<em><b>New</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__NEW = 6;

	/**
	 * The feature id for the '<em><b>Rhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__RHS_TRANSLATED = 7;

	/**
	 * The feature id for the '<em><b>Lhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT__LHS_TRANSLATED = 8;

	/**
	 * The number of structural features of the '<em>Node Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_LAYOUT_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link tgg.impl.AttributeLayoutImpl <em>Attribute Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.AttributeLayoutImpl
	 * @see tgg.impl.TGGPackageImpl#getAttributeLayout()
	 * @generated
	 */
	int ATTRIBUTE_LAYOUT = 2;

	/**
	 * The feature id for the '<em><b>New</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_LAYOUT__NEW = 0;

	/**
	 * The feature id for the '<em><b>Lhsattribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_LAYOUT__LHSATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Rhsattribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_LAYOUT__RHSATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Attribute Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_LAYOUT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link tgg.impl.EdgeLayoutImpl <em>Edge Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.EdgeLayoutImpl
	 * @see tgg.impl.TGGPackageImpl#getEdgeLayout()
	 * @generated
	 */
	int EDGE_LAYOUT = 3;

	/**
	 * The feature id for the '<em><b>New</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_LAYOUT__NEW = 0;

	/**
	 * The feature id for the '<em><b>Lhsedge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_LAYOUT__LHSEDGE = 1;

	/**
	 * The feature id for the '<em><b>Rhsedge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_LAYOUT__RHSEDGE = 2;

	/**
	 * The feature id for the '<em><b>Rhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_LAYOUT__RHS_TRANSLATED = 3;

	/**
	 * The feature id for the '<em><b>Lhs Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_LAYOUT__LHS_TRANSLATED = 4;

	/**
	 * The number of structural features of the '<em>Edge Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_LAYOUT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link tgg.impl.GraphLayoutImpl <em>Graph Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.GraphLayoutImpl
	 * @see tgg.impl.TGGPackageImpl#getGraphLayout()
	 * @generated
	 */
	int GRAPH_LAYOUT = 4;

	/**
	 * The feature id for the '<em><b>Divider X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_LAYOUT__DIVIDER_X = 0;

	/**
	 * The feature id for the '<em><b>Max Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_LAYOUT__MAX_Y = 1;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_LAYOUT__GRAPH = 2;

	/**
	 * The feature id for the '<em><b>Is SC</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_LAYOUT__IS_SC = 3;

	/**
	 * The number of structural features of the '<em>Graph Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_LAYOUT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link tgg.impl.TRuleImpl <em>TRule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.TRuleImpl
	 * @see tgg.impl.TGGPackageImpl#getTRule()
	 * @generated
	 */
	int TRULE = 5;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRULE__RULE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRULE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>TRule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRULE_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link tgg.impl.CritPairImpl <em>Crit Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tgg.impl.CritPairImpl
	 * @see tgg.impl.TGGPackageImpl#getCritPair()
	 * @generated
	 */
	int CRIT_PAIR = 6;

	/**
	 * The feature id for the '<em><b>Overlapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__OVERLAPPING = 0;

	/**
	 * The feature id for the '<em><b>Rule1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__RULE1 = 1;

	/**
	 * The feature id for the '<em><b>Rule2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__RULE2 = 2;

	/**
	 * The feature id for the '<em><b>Mappings Over To Rule1</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__MAPPINGS_OVER_TO_RULE1 = 3;

	/**
	 * The feature id for the '<em><b>Mappings Over To Rule2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__MAPPINGS_OVER_TO_RULE2 = 4;

	/**
	 * The feature id for the '<em><b>Mappings Rule1 To Rule2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2 = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR__NAME = 6;

	/**
	 * The number of structural features of the '<em>Crit Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRIT_PAIR_FEATURE_COUNT = 7;


	/**
	 * Returns the meta object for class '{@link tgg.TGG <em>TGG</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG</em>'.
	 * @see tgg.TGG
	 * @generated
	 */
	EClass getTGG();

	/**
	 * Returns the meta object for the reference '{@link tgg.TGG#getSrcroot <em>Srcroot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Srcroot</em>'.
	 * @see tgg.TGG#getSrcroot()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Srcroot();

	/**
	 * Returns the meta object for the reference '{@link tgg.TGG#getTarroot <em>Tarroot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tarroot</em>'.
	 * @see tgg.TGG#getTarroot()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Tarroot();

	/**
	 * Returns the meta object for the reference '{@link tgg.TGG#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see tgg.TGG#getSource()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Source();

	/**
	 * Returns the meta object for the reference '{@link tgg.TGG#getCorresp <em>Corresp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Corresp</em>'.
	 * @see tgg.TGG#getCorresp()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Corresp();

	/**
	 * Returns the meta object for the reference '{@link tgg.TGG#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see tgg.TGG#getTarget()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Target();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.TGG#getNodelayouts <em>Nodelayouts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodelayouts</em>'.
	 * @see tgg.TGG#getNodelayouts()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Nodelayouts();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.TGG#getEdgelayouts <em>Edgelayouts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edgelayouts</em>'.
	 * @see tgg.TGG#getEdgelayouts()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Edgelayouts();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.TGG#getGraphlayouts <em>Graphlayouts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Graphlayouts</em>'.
	 * @see tgg.TGG#getGraphlayouts()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Graphlayouts();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.TGG#getTRules <em>TRules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>TRules</em>'.
	 * @see tgg.TGG#getTRules()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_TRules();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.TGG#getCritPairs <em>Crit Pairs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Crit Pairs</em>'.
	 * @see tgg.TGG#getCritPairs()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_CritPairs();

	/**
	 * Returns the meta object for class '{@link tgg.NodeLayout <em>Node Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Layout</em>'.
	 * @see tgg.NodeLayout
	 * @generated
	 */
	EClass getNodeLayout();

	/**
	 * Returns the meta object for the attribute '{@link tgg.NodeLayout#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see tgg.NodeLayout#getX()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EAttribute getNodeLayout_X();

	/**
	 * Returns the meta object for the attribute '{@link tgg.NodeLayout#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see tgg.NodeLayout#getY()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EAttribute getNodeLayout_Y();

	/**
	 * Returns the meta object for the attribute '{@link tgg.NodeLayout#isHide <em>Hide</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hide</em>'.
	 * @see tgg.NodeLayout#isHide()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EAttribute getNodeLayout_Hide();

	/**
	 * Returns the meta object for the reference '{@link tgg.NodeLayout#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see tgg.NodeLayout#getNode()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EReference getNodeLayout_Node();

	/**
	 * Returns the meta object for the reference '{@link tgg.NodeLayout#getLhsnode <em>Lhsnode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Lhsnode</em>'.
	 * @see tgg.NodeLayout#getLhsnode()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EReference getNodeLayout_Lhsnode();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.NodeLayout#getAttributeLayouts <em>Attribute Layouts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Layouts</em>'.
	 * @see tgg.NodeLayout#getAttributeLayouts()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EReference getNodeLayout_AttributeLayouts();

	/**
	 * Returns the meta object for the attribute '{@link tgg.NodeLayout#isNew <em>New</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New</em>'.
	 * @see tgg.NodeLayout#isNew()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EAttribute getNodeLayout_New();

	/**
	 * Returns the meta object for the attribute '{@link tgg.NodeLayout#getRhsTranslated <em>Rhs Translated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rhs Translated</em>'.
	 * @see tgg.NodeLayout#getRhsTranslated()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EAttribute getNodeLayout_RhsTranslated();

	/**
	 * Returns the meta object for the attribute '{@link tgg.NodeLayout#getLhsTranslated <em>Lhs Translated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lhs Translated</em>'.
	 * @see tgg.NodeLayout#getLhsTranslated()
	 * @see #getNodeLayout()
	 * @generated
	 */
	EAttribute getNodeLayout_LhsTranslated();

	/**
	 * Returns the meta object for class '{@link tgg.AttributeLayout <em>Attribute Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Layout</em>'.
	 * @see tgg.AttributeLayout
	 * @generated
	 */
	EClass getAttributeLayout();

	/**
	 * Returns the meta object for the attribute '{@link tgg.AttributeLayout#isNew <em>New</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New</em>'.
	 * @see tgg.AttributeLayout#isNew()
	 * @see #getAttributeLayout()
	 * @generated
	 */
	EAttribute getAttributeLayout_New();

	/**
	 * Returns the meta object for the reference '{@link tgg.AttributeLayout#getLhsattribute <em>Lhsattribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Lhsattribute</em>'.
	 * @see tgg.AttributeLayout#getLhsattribute()
	 * @see #getAttributeLayout()
	 * @generated
	 */
	EReference getAttributeLayout_Lhsattribute();

	/**
	 * Returns the meta object for the reference '{@link tgg.AttributeLayout#getRhsattribute <em>Rhsattribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rhsattribute</em>'.
	 * @see tgg.AttributeLayout#getRhsattribute()
	 * @see #getAttributeLayout()
	 * @generated
	 */
	EReference getAttributeLayout_Rhsattribute();

	/**
	 * Returns the meta object for class '{@link tgg.EdgeLayout <em>Edge Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge Layout</em>'.
	 * @see tgg.EdgeLayout
	 * @generated
	 */
	EClass getEdgeLayout();

	/**
	 * Returns the meta object for the attribute '{@link tgg.EdgeLayout#isNew <em>New</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New</em>'.
	 * @see tgg.EdgeLayout#isNew()
	 * @see #getEdgeLayout()
	 * @generated
	 */
	EAttribute getEdgeLayout_New();

	/**
	 * Returns the meta object for the reference '{@link tgg.EdgeLayout#getLhsedge <em>Lhsedge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Lhsedge</em>'.
	 * @see tgg.EdgeLayout#getLhsedge()
	 * @see #getEdgeLayout()
	 * @generated
	 */
	EReference getEdgeLayout_Lhsedge();

	/**
	 * Returns the meta object for the reference '{@link tgg.EdgeLayout#getRhsedge <em>Rhsedge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rhsedge</em>'.
	 * @see tgg.EdgeLayout#getRhsedge()
	 * @see #getEdgeLayout()
	 * @generated
	 */
	EReference getEdgeLayout_Rhsedge();

	/**
	 * Returns the meta object for the attribute '{@link tgg.EdgeLayout#getRhsTranslated <em>Rhs Translated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rhs Translated</em>'.
	 * @see tgg.EdgeLayout#getRhsTranslated()
	 * @see #getEdgeLayout()
	 * @generated
	 */
	EAttribute getEdgeLayout_RhsTranslated();

	/**
	 * Returns the meta object for the attribute '{@link tgg.EdgeLayout#getLhsTranslated <em>Lhs Translated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lhs Translated</em>'.
	 * @see tgg.EdgeLayout#getLhsTranslated()
	 * @see #getEdgeLayout()
	 * @generated
	 */
	EAttribute getEdgeLayout_LhsTranslated();

	/**
	 * Returns the meta object for class '{@link tgg.GraphLayout <em>Graph Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph Layout</em>'.
	 * @see tgg.GraphLayout
	 * @generated
	 */
	EClass getGraphLayout();

	/**
	 * Returns the meta object for the attribute '{@link tgg.GraphLayout#getDividerX <em>Divider X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Divider X</em>'.
	 * @see tgg.GraphLayout#getDividerX()
	 * @see #getGraphLayout()
	 * @generated
	 */
	EAttribute getGraphLayout_DividerX();

	/**
	 * Returns the meta object for the attribute '{@link tgg.GraphLayout#getMaxY <em>Max Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Y</em>'.
	 * @see tgg.GraphLayout#getMaxY()
	 * @see #getGraphLayout()
	 * @generated
	 */
	EAttribute getGraphLayout_MaxY();

	/**
	 * Returns the meta object for the reference '{@link tgg.GraphLayout#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Graph</em>'.
	 * @see tgg.GraphLayout#getGraph()
	 * @see #getGraphLayout()
	 * @generated
	 */
	EReference getGraphLayout_Graph();

	/**
	 * Returns the meta object for the attribute '{@link tgg.GraphLayout#isIsSC <em>Is SC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is SC</em>'.
	 * @see tgg.GraphLayout#isIsSC()
	 * @see #getGraphLayout()
	 * @generated
	 */
	EAttribute getGraphLayout_IsSC();

	/**
	 * Returns the meta object for class '{@link tgg.TRule <em>TRule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TRule</em>'.
	 * @see tgg.TRule
	 * @generated
	 */
	EClass getTRule();

	/**
	 * Returns the meta object for the reference '{@link tgg.TRule#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see tgg.TRule#getRule()
	 * @see #getTRule()
	 * @generated
	 */
	EReference getTRule_Rule();

	/**
	 * Returns the meta object for the attribute '{@link tgg.TRule#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see tgg.TRule#getType()
	 * @see #getTRule()
	 * @generated
	 */
	EAttribute getTRule_Type();

	/**
	 * Returns the meta object for class '{@link tgg.CritPair <em>Crit Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Crit Pair</em>'.
	 * @see tgg.CritPair
	 * @generated
	 */
	EClass getCritPair();

	/**
	 * Returns the meta object for the reference '{@link tgg.CritPair#getOverlapping <em>Overlapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Overlapping</em>'.
	 * @see tgg.CritPair#getOverlapping()
	 * @see #getCritPair()
	 * @generated
	 */
	EReference getCritPair_Overlapping();

	/**
	 * Returns the meta object for the reference '{@link tgg.CritPair#getRule1 <em>Rule1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule1</em>'.
	 * @see tgg.CritPair#getRule1()
	 * @see #getCritPair()
	 * @generated
	 */
	EReference getCritPair_Rule1();

	/**
	 * Returns the meta object for the reference '{@link tgg.CritPair#getRule2 <em>Rule2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule2</em>'.
	 * @see tgg.CritPair#getRule2()
	 * @see #getCritPair()
	 * @generated
	 */
	EReference getCritPair_Rule2();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.CritPair#getMappingsOverToRule1 <em>Mappings Over To Rule1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings Over To Rule1</em>'.
	 * @see tgg.CritPair#getMappingsOverToRule1()
	 * @see #getCritPair()
	 * @generated
	 */
	EReference getCritPair_MappingsOverToRule1();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.CritPair#getMappingsOverToRule2 <em>Mappings Over To Rule2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings Over To Rule2</em>'.
	 * @see tgg.CritPair#getMappingsOverToRule2()
	 * @see #getCritPair()
	 * @generated
	 */
	EReference getCritPair_MappingsOverToRule2();

	/**
	 * Returns the meta object for the containment reference list '{@link tgg.CritPair#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings Rule1 To Rule2</em>'.
	 * @see tgg.CritPair#getMappingsRule1ToRule2()
	 * @see #getCritPair()
	 * @generated
	 */
	EReference getCritPair_MappingsRule1ToRule2();

	/**
	 * Returns the meta object for the attribute '{@link tgg.CritPair#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see tgg.CritPair#getName()
	 * @see #getCritPair()
	 * @generated
	 */
	EAttribute getCritPair_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TGGFactory getTGGFactory();

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
		 * The meta object literal for the '{@link tgg.impl.TGGImpl <em>TGG</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.TGGImpl
		 * @see tgg.impl.TGGPackageImpl#getTGG()
		 * @generated
		 */
		EClass TGG = eINSTANCE.getTGG();

		/**
		 * The meta object literal for the '<em><b>Srcroot</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__SRCROOT = eINSTANCE.getTGG_Srcroot();

		/**
		 * The meta object literal for the '<em><b>Tarroot</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__TARROOT = eINSTANCE.getTGG_Tarroot();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__SOURCE = eINSTANCE.getTGG_Source();

		/**
		 * The meta object literal for the '<em><b>Corresp</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__CORRESP = eINSTANCE.getTGG_Corresp();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__TARGET = eINSTANCE.getTGG_Target();

		/**
		 * The meta object literal for the '<em><b>Nodelayouts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__NODELAYOUTS = eINSTANCE.getTGG_Nodelayouts();

		/**
		 * The meta object literal for the '<em><b>Edgelayouts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__EDGELAYOUTS = eINSTANCE.getTGG_Edgelayouts();

		/**
		 * The meta object literal for the '<em><b>Graphlayouts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__GRAPHLAYOUTS = eINSTANCE.getTGG_Graphlayouts();

		/**
		 * The meta object literal for the '<em><b>TRules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__TRULES = eINSTANCE.getTGG_TRules();

		/**
		 * The meta object literal for the '<em><b>Crit Pairs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__CRIT_PAIRS = eINSTANCE.getTGG_CritPairs();

		/**
		 * The meta object literal for the '{@link tgg.impl.NodeLayoutImpl <em>Node Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.NodeLayoutImpl
		 * @see tgg.impl.TGGPackageImpl#getNodeLayout()
		 * @generated
		 */
		EClass NODE_LAYOUT = eINSTANCE.getNodeLayout();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_LAYOUT__X = eINSTANCE.getNodeLayout_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_LAYOUT__Y = eINSTANCE.getNodeLayout_Y();

		/**
		 * The meta object literal for the '<em><b>Hide</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_LAYOUT__HIDE = eINSTANCE.getNodeLayout_Hide();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_LAYOUT__NODE = eINSTANCE.getNodeLayout_Node();

		/**
		 * The meta object literal for the '<em><b>Lhsnode</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_LAYOUT__LHSNODE = eINSTANCE.getNodeLayout_Lhsnode();

		/**
		 * The meta object literal for the '<em><b>Attribute Layouts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_LAYOUT__ATTRIBUTE_LAYOUTS = eINSTANCE.getNodeLayout_AttributeLayouts();

		/**
		 * The meta object literal for the '<em><b>New</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_LAYOUT__NEW = eINSTANCE.getNodeLayout_New();

		/**
		 * The meta object literal for the '<em><b>Rhs Translated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_LAYOUT__RHS_TRANSLATED = eINSTANCE.getNodeLayout_RhsTranslated();

		/**
		 * The meta object literal for the '<em><b>Lhs Translated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_LAYOUT__LHS_TRANSLATED = eINSTANCE.getNodeLayout_LhsTranslated();

		/**
		 * The meta object literal for the '{@link tgg.impl.AttributeLayoutImpl <em>Attribute Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.AttributeLayoutImpl
		 * @see tgg.impl.TGGPackageImpl#getAttributeLayout()
		 * @generated
		 */
		EClass ATTRIBUTE_LAYOUT = eINSTANCE.getAttributeLayout();

		/**
		 * The meta object literal for the '<em><b>New</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_LAYOUT__NEW = eINSTANCE.getAttributeLayout_New();

		/**
		 * The meta object literal for the '<em><b>Lhsattribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_LAYOUT__LHSATTRIBUTE = eINSTANCE.getAttributeLayout_Lhsattribute();

		/**
		 * The meta object literal for the '<em><b>Rhsattribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_LAYOUT__RHSATTRIBUTE = eINSTANCE.getAttributeLayout_Rhsattribute();

		/**
		 * The meta object literal for the '{@link tgg.impl.EdgeLayoutImpl <em>Edge Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.EdgeLayoutImpl
		 * @see tgg.impl.TGGPackageImpl#getEdgeLayout()
		 * @generated
		 */
		EClass EDGE_LAYOUT = eINSTANCE.getEdgeLayout();

		/**
		 * The meta object literal for the '<em><b>New</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_LAYOUT__NEW = eINSTANCE.getEdgeLayout_New();

		/**
		 * The meta object literal for the '<em><b>Lhsedge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_LAYOUT__LHSEDGE = eINSTANCE.getEdgeLayout_Lhsedge();

		/**
		 * The meta object literal for the '<em><b>Rhsedge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_LAYOUT__RHSEDGE = eINSTANCE.getEdgeLayout_Rhsedge();

		/**
		 * The meta object literal for the '<em><b>Rhs Translated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_LAYOUT__RHS_TRANSLATED = eINSTANCE.getEdgeLayout_RhsTranslated();

		/**
		 * The meta object literal for the '<em><b>Lhs Translated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_LAYOUT__LHS_TRANSLATED = eINSTANCE.getEdgeLayout_LhsTranslated();

		/**
		 * The meta object literal for the '{@link tgg.impl.GraphLayoutImpl <em>Graph Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.GraphLayoutImpl
		 * @see tgg.impl.TGGPackageImpl#getGraphLayout()
		 * @generated
		 */
		EClass GRAPH_LAYOUT = eINSTANCE.getGraphLayout();

		/**
		 * The meta object literal for the '<em><b>Divider X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_LAYOUT__DIVIDER_X = eINSTANCE.getGraphLayout_DividerX();

		/**
		 * The meta object literal for the '<em><b>Max Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_LAYOUT__MAX_Y = eINSTANCE.getGraphLayout_MaxY();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_LAYOUT__GRAPH = eINSTANCE.getGraphLayout_Graph();

		/**
		 * The meta object literal for the '<em><b>Is SC</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPH_LAYOUT__IS_SC = eINSTANCE.getGraphLayout_IsSC();

		/**
		 * The meta object literal for the '{@link tgg.impl.TRuleImpl <em>TRule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.TRuleImpl
		 * @see tgg.impl.TGGPackageImpl#getTRule()
		 * @generated
		 */
		EClass TRULE = eINSTANCE.getTRule();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRULE__RULE = eINSTANCE.getTRule_Rule();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRULE__TYPE = eINSTANCE.getTRule_Type();

		/**
		 * The meta object literal for the '{@link tgg.impl.CritPairImpl <em>Crit Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tgg.impl.CritPairImpl
		 * @see tgg.impl.TGGPackageImpl#getCritPair()
		 * @generated
		 */
		EClass CRIT_PAIR = eINSTANCE.getCritPair();

		/**
		 * The meta object literal for the '<em><b>Overlapping</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRIT_PAIR__OVERLAPPING = eINSTANCE.getCritPair_Overlapping();

		/**
		 * The meta object literal for the '<em><b>Rule1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRIT_PAIR__RULE1 = eINSTANCE.getCritPair_Rule1();

		/**
		 * The meta object literal for the '<em><b>Rule2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRIT_PAIR__RULE2 = eINSTANCE.getCritPair_Rule2();

		/**
		 * The meta object literal for the '<em><b>Mappings Over To Rule1</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRIT_PAIR__MAPPINGS_OVER_TO_RULE1 = eINSTANCE.getCritPair_MappingsOverToRule1();

		/**
		 * The meta object literal for the '<em><b>Mappings Over To Rule2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRIT_PAIR__MAPPINGS_OVER_TO_RULE2 = eINSTANCE.getCritPair_MappingsOverToRule2();

		/**
		 * The meta object literal for the '<em><b>Mappings Rule1 To Rule2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2 = eINSTANCE.getCritPair_MappingsRule1ToRule2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CRIT_PAIR__NAME = eINSTANCE.getCritPair_Name();

	}

} //TGGPackage
