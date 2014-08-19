/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleComponent;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Imported Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.ImportedPackageImpl#isLoadWithDefaultValues <em>Load With Default Values</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.ImportedPackageImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.ImportedPackageImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImportedPackageImpl extends EObjectImpl implements ImportedPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportedPackageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.IMPORTED_PACKAGE;
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
	public boolean isLoadWithDefaultValues() {
		return (Boolean)eGet(TggPackage.Literals.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadWithDefaultValues(boolean newLoadWithDefaultValues) {
		eSet(TggPackage.Literals.IMPORTED_PACKAGE__LOAD_WITH_DEFAULT_VALUES, newLoadWithDefaultValues);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getPackage() {
		return (EPackage)eGet(TggPackage.Literals.IMPORTED_PACKAGE__PACKAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(EPackage newPackage) {
		eSet(TggPackage.Literals.IMPORTED_PACKAGE__PACKAGE, newPackage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TripleComponent getComponent() {
		return (TripleComponent)eGet(TggPackage.Literals.IMPORTED_PACKAGE__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(TripleComponent newComponent) {
		eSet(TggPackage.Literals.IMPORTED_PACKAGE__COMPONENT, newComponent);
	}

} //ImportedPackageImpl
