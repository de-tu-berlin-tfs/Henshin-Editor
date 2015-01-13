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
/**
 */
package TGG_correspondence;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see TGG_correspondence.TGG_correspondenceFactory
 * @model kind="package"
 * @generated
 */
public interface TGG_correspondencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "TGG_correspondence";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://snt.uni.lu/TGGcorrespondence";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "TGGcorrespondence";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TGG_correspondencePackage eINSTANCE = TGG_correspondence.impl.TGG_correspondencePackageImpl.init();

	/**
	 * The meta object id for the '{@link TGG_correspondence.impl.CORRImpl <em>CORR</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGG_correspondence.impl.CORRImpl
	 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getCORR()
	 * @generated
	 */
	int CORR = 0;

	/**
	 * The feature id for the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR__TGT = 0;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR__SRC = 1;

	/**
	 * The number of structural features of the '<em>CORR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>CORR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link TGG_correspondence.impl.AbstractSourceImpl <em>Abstract Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGG_correspondence.impl.AbstractSourceImpl
	 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getAbstractSource()
	 * @generated
	 */
	int ABSTRACT_SOURCE = 1;

	/**
	 * The feature id for the '<em><b>S2c</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOURCE__S2C = 0;

	/**
	 * The number of structural features of the '<em>Abstract Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOURCE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link TGG_correspondence.impl.AbstractTargetImpl <em>Abstract Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGG_correspondence.impl.AbstractTargetImpl
	 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getAbstractTarget()
	 * @generated
	 */
	int ABSTRACT_TARGET = 2;

	/**
	 * The feature id for the '<em><b>T2c</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TARGET__T2C = 0;

	/**
	 * The number of structural features of the '<em>Abstract Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TARGET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TARGET_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link TGG_correspondence.impl.GenCORRImpl <em>Gen CORR</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGG_correspondence.impl.GenCORRImpl
	 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getGenCORR()
	 * @generated
	 */
	int GEN_CORR = 3;

	/**
	 * The feature id for the '<em><b>Gen SRC</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_CORR__GEN_SRC = 0;

	/**
	 * The feature id for the '<em><b>Gen TGT</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_CORR__GEN_TGT = 1;

	/**
	 * The number of structural features of the '<em>Gen CORR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_CORR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Gen CORR</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GEN_CORR_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link TGG_correspondence.CORR <em>CORR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CORR</em>'.
	 * @see TGG_correspondence.CORR
	 * @generated
	 */
	EClass getCORR();

	/**
	 * Returns the meta object for the reference '{@link TGG_correspondence.CORR#getTgt <em>Tgt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tgt</em>'.
	 * @see TGG_correspondence.CORR#getTgt()
	 * @see #getCORR()
	 * @generated
	 */
	EReference getCORR_Tgt();

	/**
	 * Returns the meta object for the reference '{@link TGG_correspondence.CORR#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see TGG_correspondence.CORR#getSrc()
	 * @see #getCORR()
	 * @generated
	 */
	EReference getCORR_Src();

	/**
	 * Returns the meta object for class '{@link TGG_correspondence.AbstractSource <em>Abstract Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Source</em>'.
	 * @see TGG_correspondence.AbstractSource
	 * @generated
	 */
	EClass getAbstractSource();

	/**
	 * Returns the meta object for the reference list '{@link TGG_correspondence.AbstractSource#getS2c <em>S2c</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>S2c</em>'.
	 * @see TGG_correspondence.AbstractSource#getS2c()
	 * @see #getAbstractSource()
	 * @generated
	 */
	EReference getAbstractSource_S2c();

	/**
	 * Returns the meta object for class '{@link TGG_correspondence.AbstractTarget <em>Abstract Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Target</em>'.
	 * @see TGG_correspondence.AbstractTarget
	 * @generated
	 */
	EClass getAbstractTarget();

	/**
	 * Returns the meta object for the reference list '{@link TGG_correspondence.AbstractTarget#getT2c <em>T2c</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>T2c</em>'.
	 * @see TGG_correspondence.AbstractTarget#getT2c()
	 * @see #getAbstractTarget()
	 * @generated
	 */
	EReference getAbstractTarget_T2c();

	/**
	 * Returns the meta object for class '{@link TGG_correspondence.GenCORR <em>Gen CORR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gen CORR</em>'.
	 * @see TGG_correspondence.GenCORR
	 * @generated
	 */
	EClass getGenCORR();

	/**
	 * Returns the meta object for the reference '{@link TGG_correspondence.GenCORR#getGenSRC <em>Gen SRC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Gen SRC</em>'.
	 * @see TGG_correspondence.GenCORR#getGenSRC()
	 * @see #getGenCORR()
	 * @generated
	 */
	EReference getGenCORR_GenSRC();

	/**
	 * Returns the meta object for the reference '{@link TGG_correspondence.GenCORR#getGenTGT <em>Gen TGT</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Gen TGT</em>'.
	 * @see TGG_correspondence.GenCORR#getGenTGT()
	 * @see #getGenCORR()
	 * @generated
	 */
	EReference getGenCORR_GenTGT();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TGG_correspondenceFactory getTGG_correspondenceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link TGG_correspondence.impl.CORRImpl <em>CORR</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGG_correspondence.impl.CORRImpl
		 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getCORR()
		 * @generated
		 */
		EClass CORR = eINSTANCE.getCORR();

		/**
		 * The meta object literal for the '<em><b>Tgt</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORR__TGT = eINSTANCE.getCORR_Tgt();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORR__SRC = eINSTANCE.getCORR_Src();

		/**
		 * The meta object literal for the '{@link TGG_correspondence.impl.AbstractSourceImpl <em>Abstract Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGG_correspondence.impl.AbstractSourceImpl
		 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getAbstractSource()
		 * @generated
		 */
		EClass ABSTRACT_SOURCE = eINSTANCE.getAbstractSource();

		/**
		 * The meta object literal for the '<em><b>S2c</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_SOURCE__S2C = eINSTANCE.getAbstractSource_S2c();

		/**
		 * The meta object literal for the '{@link TGG_correspondence.impl.AbstractTargetImpl <em>Abstract Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGG_correspondence.impl.AbstractTargetImpl
		 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getAbstractTarget()
		 * @generated
		 */
		EClass ABSTRACT_TARGET = eINSTANCE.getAbstractTarget();

		/**
		 * The meta object literal for the '<em><b>T2c</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_TARGET__T2C = eINSTANCE.getAbstractTarget_T2c();

		/**
		 * The meta object literal for the '{@link TGG_correspondence.impl.GenCORRImpl <em>Gen CORR</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGG_correspondence.impl.GenCORRImpl
		 * @see TGG_correspondence.impl.TGG_correspondencePackageImpl#getGenCORR()
		 * @generated
		 */
		EClass GEN_CORR = eINSTANCE.getGenCORR();

		/**
		 * The meta object literal for the '<em><b>Gen SRC</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_CORR__GEN_SRC = eINSTANCE.getGenCORR_GenSRC();

		/**
		 * The meta object literal for the '<em><b>Gen TGT</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GEN_CORR__GEN_TGT = eINSTANCE.getGenCORR_GenTGT();

	}

} //TGG_correspondencePackage
