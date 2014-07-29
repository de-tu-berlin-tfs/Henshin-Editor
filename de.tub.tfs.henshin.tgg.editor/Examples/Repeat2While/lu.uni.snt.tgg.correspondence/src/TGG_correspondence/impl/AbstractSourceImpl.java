/**
 */
package TGG_correspondence.impl;

import TGG_correspondence.AbstractSource;
import TGG_correspondence.CORR;
import TGG_correspondence.TGG_correspondencePackage;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link TGG_correspondence.impl.AbstractSourceImpl#getS2c <em>S2c</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AbstractSourceImpl extends CDOObjectImpl implements AbstractSource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGG_correspondencePackage.Literals.ABSTRACT_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CORR> getS2c() {
		return (EList<CORR>)eGet(TGG_correspondencePackage.Literals.ABSTRACT_SOURCE__S2C, true);
	}

} //AbstractSourceImpl
