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
package de.tub.tfs.henshin.model.layout.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage
 * @generated
 */
public class HenshinLayoutAdapterFactory extends AdapterFactoryImpl {
        /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected static HenshinLayoutPackage modelPackage;

        /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public HenshinLayoutAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = HenshinLayoutPackage.eINSTANCE;
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
        protected HenshinLayoutSwitch<Adapter> modelSwitch =
                new HenshinLayoutSwitch<Adapter>() {
			@Override
			public Adapter caseLayoutSystem(LayoutSystem object) {
				return createLayoutSystemAdapter();
			}
			@Override
			public Adapter caseNodeLayout(NodeLayout object) {
				return createNodeLayoutAdapter();
			}
			@Override
			public Adapter caseFlowElementLayout(FlowElementLayout object) {
				return createFlowElementLayoutAdapter();
			}
			@Override
			public Adapter caseEContainerDescriptor(EContainerDescriptor object) {
				return createEContainerDescriptorAdapter();
			}
			@Override
			public Adapter caseLayout(Layout object) {
				return createLayoutAdapter();
			}
			@Override
			public Adapter caseSubUnitLayout(SubUnitLayout object) {
				return createSubUnitLayoutAdapter();
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
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.layout.LayoutSystem <em>Layout System</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.layout.LayoutSystem
	 * @generated
	 */
        public Adapter createLayoutSystemAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.layout.NodeLayout <em>Node Layout</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.layout.NodeLayout
	 * @generated
	 */
        public Adapter createNodeLayoutAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.layout.FlowElementLayout <em>Flow Element Layout</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.layout.FlowElementLayout
	 * @generated
	 */
        public Adapter createFlowElementLayoutAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor <em>EContainer Descriptor</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.layout.EContainerDescriptor
	 * @generated
	 */
        public Adapter createEContainerDescriptorAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.layout.Layout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.layout.Layout
	 * @generated
	 */
        public Adapter createLayoutAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.layout.SubUnitLayout <em>Sub Unit Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.layout.SubUnitLayout
	 * @generated
	 */
	public Adapter createSubUnitLayoutAdapter() {
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

} //HenshinLayoutAdapterFactory
