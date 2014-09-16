/**
 */
package TGG_correspondence.impl;

import TGG_correspondence.AbstractSource;
import TGG_correspondence.AbstractTarget;
import TGG_correspondence.CORR;
import TGG_correspondence.TGG_correspondencePackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CORR</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link TGG_correspondence.impl.CORRImpl#getTgt <em>Tgt</em>}</li>
 *   <li>{@link TGG_correspondence.impl.CORRImpl#getSrc <em>Src</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CORRImpl extends CDOObjectImpl implements CORR {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CORRImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGG_correspondencePackage.Literals.CORR;
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
	public AbstractTarget getTgt() {
		return (AbstractTarget)eGet(TGG_correspondencePackage.Literals.CORR__TGT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTgt(AbstractTarget newTgt) {
		eSet(TGG_correspondencePackage.Literals.CORR__TGT, newTgt);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractSource getSrc() {
		return (AbstractSource)eGet(TGG_correspondencePackage.Literals.CORR__SRC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrc(AbstractSource newSrc) {
		eSet(TGG_correspondencePackage.Literals.CORR__SRC, newSrc);
	}

} //CORRImpl
