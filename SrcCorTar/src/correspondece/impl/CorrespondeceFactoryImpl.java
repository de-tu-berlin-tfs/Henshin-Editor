/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece.impl;

import correspondece.*;

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
public class CorrespondeceFactoryImpl extends EFactoryImpl implements CorrespondeceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CorrespondeceFactory init() {
		try {
			CorrespondeceFactory theCorrespondeceFactory = (CorrespondeceFactory)EPackage.Registry.INSTANCE.getEFactory("http://cor"); 
			if (theCorrespondeceFactory != null) {
				return theCorrespondeceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CorrespondeceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondeceFactoryImpl() {
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
			case CorrespondecePackage.CT: return createCT();
			case CorrespondecePackage.AFK: return createAFK();
			case CorrespondecePackage.AC: return createAC();
			case CorrespondecePackage.CD2DB: return createCD2DB();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CT createCT() {
		CTImpl ct = new CTImpl();
		return ct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AFK createAFK() {
		AFKImpl afk = new AFKImpl();
		return afk;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AC createAC() {
		ACImpl ac = new ACImpl();
		return ac;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CD2DB createCD2DB() {
		CD2DBImpl cd2DB = new CD2DBImpl();
		return cd2DB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondecePackage getCorrespondecePackage() {
		return (CorrespondecePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CorrespondecePackage getPackage() {
		return CorrespondecePackage.eINSTANCE;
	}

} //CorrespondeceFactoryImpl
