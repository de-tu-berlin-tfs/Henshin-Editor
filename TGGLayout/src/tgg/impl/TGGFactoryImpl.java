/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tgg.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TGGFactoryImpl extends EFactoryImpl implements TGGFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TGGFactory init() {
		try {
			TGGFactory theTGGFactory = (TGGFactory)EPackage.Registry.INSTANCE.getEFactory("http://tgg.tu-berlin.de"); 
			if (theTGGFactory != null) {
				return theTGGFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TGGFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TGGPackage.TGG: return createTGG();
			case TGGPackage.NODE_LAYOUT: return createNodeLayout();
			case TGGPackage.ATTRIBUTE_LAYOUT: return createAttributeLayout();
			case TGGPackage.EDGE_LAYOUT: return createEdgeLayout();
			case TGGPackage.GRAPH_LAYOUT: return createGraphLayout();
			case TGGPackage.TRULE: return createTRule();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGG createTGG() {
		TGGImpl tgg = new TGGImpl();
		return tgg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeLayout createNodeLayout() {
		NodeLayoutImpl nodeLayout = new NodeLayoutImpl();
		return nodeLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeLayout createAttributeLayout() {
		AttributeLayoutImpl attributeLayout = new AttributeLayoutImpl();
		return attributeLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeLayout createEdgeLayout() {
		EdgeLayoutImpl edgeLayout = new EdgeLayoutImpl();
		return edgeLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GraphLayout createGraphLayout() {
		GraphLayoutImpl graphLayout = new GraphLayoutImpl();
		return graphLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TRule createTRule() {
		TRuleImpl tRule = new TRuleImpl();
		return tRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGPackage getTGGPackage() {
		return (TGGPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TGGPackage getPackage() {
		return TGGPackage.eINSTANCE;
	}

} //TGGFactoryImpl
