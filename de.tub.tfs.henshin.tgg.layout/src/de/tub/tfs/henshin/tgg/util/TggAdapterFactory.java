/**
 */
package de.tub.tfs.henshin.tgg.util;

import de.tub.tfs.henshin.tgg.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.LayoutElement;
import org.eclipse.emf.henshin.model.MarkedElement;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.tgg.TggPackage
 * @generated
 */
public class TggAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TggPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TggAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TggPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TggSwitch<Adapter> modelSwitch =
		new TggSwitch<Adapter>() {
			@Override
			public Adapter caseTGG(TGG object) {
				return createTGGAdapter();
			}
			@Override
			public Adapter caseNodeLayout(NodeLayout object) {
				return createNodeLayoutAdapter();
			}
			@Override
			public Adapter caseAttributeLayout(AttributeLayout object) {
				return createAttributeLayoutAdapter();
			}
			@Override
			public Adapter caseEdgeLayout(EdgeLayout object) {
				return createEdgeLayoutAdapter();
			}
			@Override
			public Adapter caseGraphLayout(GraphLayout object) {
				return createGraphLayoutAdapter();
			}
			@Override
			public Adapter caseTRule(TRule object) {
				return createTRuleAdapter();
			}
			@Override
			public Adapter caseCritPair(CritPair object) {
				return createCritPairAdapter();
			}
			@Override
			public Adapter caseImportedPackage(ImportedPackage object) {
				return createImportedPackageAdapter();
			}
			@Override
			public Adapter caseTripleGraph(TripleGraph object) {
				return createTripleGraphAdapter();
			}
			@Override
			public Adapter caseTNode(TNode object) {
				return createTNodeAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseGraph(Graph object) {
				return createGraphAdapter();
			}
			@Override
			public Adapter caseGraphElement(GraphElement object) {
				return createGraphElementAdapter();
			}
			@Override
			public Adapter caseMarkedElement(MarkedElement object) {
				return createMarkedElementAdapter();
			}
			@Override
			public Adapter caseLayoutElement(LayoutElement object) {
				return createLayoutElementAdapter();
			}
			@Override
			public Adapter caseNode(Node object) {
				return createNodeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.TGG <em>TGG</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.TGG
	 * @generated
	 */
	public Adapter createTGGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.NodeLayout <em>Node Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.NodeLayout
	 * @generated
	 */
	public Adapter createNodeLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.AttributeLayout <em>Attribute Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.AttributeLayout
	 * @generated
	 */
	public Adapter createAttributeLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.EdgeLayout <em>Edge Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.EdgeLayout
	 * @generated
	 */
	public Adapter createEdgeLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.GraphLayout <em>Graph Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.GraphLayout
	 * @generated
	 */
	public Adapter createGraphLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.TRule <em>TRule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.TRule
	 * @generated
	 */
	public Adapter createTRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.CritPair <em>Crit Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.CritPair
	 * @generated
	 */
	public Adapter createCritPairAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.ImportedPackage <em>Imported Package</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.ImportedPackage
	 * @generated
	 */
	public Adapter createImportedPackageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.TripleGraph <em>Triple Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.TripleGraph
	 * @generated
	 */
	public Adapter createTripleGraphAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.tgg.TNode <em>TNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.tgg.TNode
	 * @generated
	 */
	public Adapter createTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.Graph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.Graph
	 * @generated
	 */
	public Adapter createGraphAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.GraphElement <em>Graph Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.GraphElement
	 * @generated
	 */
	public Adapter createGraphElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.MarkedElement <em>Marked Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.MarkedElement
	 * @generated
	 */
	public Adapter createMarkedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.LayoutElement <em>Layout Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.LayoutElement
	 * @generated
	 */
	public Adapter createLayoutElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TggAdapterFactory
