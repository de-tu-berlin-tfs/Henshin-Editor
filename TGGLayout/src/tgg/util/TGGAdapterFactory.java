/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import tgg.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tgg.TGGPackage
 * @generated
 */
public class TGGAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TGGPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TGGPackage.eINSTANCE;
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
	protected TGGSwitch<Adapter> modelSwitch =
		new TGGSwitch<Adapter>() {
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
	 * Creates a new adapter for an object of class '{@link tgg.TGG <em>TGG</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.TGG
	 * @generated
	 */
	public Adapter createTGGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.NodeLayout <em>Node Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.NodeLayout
	 * @generated
	 */
	public Adapter createNodeLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.AttributeLayout <em>Attribute Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.AttributeLayout
	 * @generated
	 */
	public Adapter createAttributeLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.EdgeLayout <em>Edge Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.EdgeLayout
	 * @generated
	 */
	public Adapter createEdgeLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.GraphLayout <em>Graph Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.GraphLayout
	 * @generated
	 */
	public Adapter createGraphLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.TRule <em>TRule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.TRule
	 * @generated
	 */
	public Adapter createTRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.CritPair <em>Crit Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.CritPair
	 * @generated
	 */
	public Adapter createCritPairAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tgg.ImportedPackage <em>Imported Package</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tgg.ImportedPackage
	 * @generated
	 */
	public Adapter createImportedPackageAdapter() {
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

} //TGGAdapterFactory
