/**
 */
package TGG_correspondence.impl;

import TGG_correspondence.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TGG_correspondenceFactoryImpl extends EFactoryImpl implements TGG_correspondenceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TGG_correspondenceFactory init() {
		try {
			TGG_correspondenceFactory theTGG_correspondenceFactory = (TGG_correspondenceFactory)EPackage.Registry.INSTANCE.getEFactory(TGG_correspondencePackage.eNS_URI);
			if (theTGG_correspondenceFactory != null) {
				return theTGG_correspondenceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TGG_correspondenceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGG_correspondenceFactoryImpl() {
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
			case TGG_correspondencePackage.CORR: return (EObject)createCORR();
			case TGG_correspondencePackage.ABSTRACT_SOURCE: return (EObject)createAbstractSource();
			case TGG_correspondencePackage.ABSTRACT_TARGET: return (EObject)createAbstractTarget();
			case TGG_correspondencePackage.GEN_CORR: return (EObject)createGenCORR();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CORR createCORR() {
		CORRImpl corr = new CORRImpl();
		return corr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractSource createAbstractSource() {
		AbstractSourceImpl abstractSource = new AbstractSourceImpl();
		return abstractSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractTarget createAbstractTarget() {
		AbstractTargetImpl abstractTarget = new AbstractTargetImpl();
		return abstractTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenCORR createGenCORR() {
		GenCORRImpl genCORR = new GenCORRImpl();
		return genCORR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGG_correspondencePackage getTGG_correspondencePackage() {
		return (TGG_correspondencePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TGG_correspondencePackage getPackage() {
		return TGG_correspondencePackage.eINSTANCE;
	}

} //TGG_correspondenceFactoryImpl
