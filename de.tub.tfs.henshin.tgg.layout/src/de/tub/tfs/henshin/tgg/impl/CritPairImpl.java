/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Crit Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getOverlapping <em>Overlapping</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getRule1 <em>Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getRule2 <em>Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getMappingsOverToRule1 <em>Mappings Over To Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getMappingsOverToRule2 <em>Mappings Over To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.CritPairImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CritPairImpl extends EObjectImpl implements CritPair {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CritPairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.CRIT_PAIR;
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
	public Graph getOverlapping() {
		return (Graph)eGet(TggPackage.Literals.CRIT_PAIR__OVERLAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverlapping(Graph newOverlapping) {
		eSet(TggPackage.Literals.CRIT_PAIR__OVERLAPPING, newOverlapping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule1() {
		return (Rule)eGet(TggPackage.Literals.CRIT_PAIR__RULE1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule1(Rule newRule1) {
		eSet(TggPackage.Literals.CRIT_PAIR__RULE1, newRule1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule2() {
		return (Rule)eGet(TggPackage.Literals.CRIT_PAIR__RULE2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule2(Rule newRule2) {
		eSet(TggPackage.Literals.CRIT_PAIR__RULE2, newRule2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Mapping> getMappingsOverToRule1() {
		return (EList<Mapping>)eGet(TggPackage.Literals.CRIT_PAIR__MAPPINGS_OVER_TO_RULE1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Mapping> getMappingsOverToRule2() {
		return (EList<Mapping>)eGet(TggPackage.Literals.CRIT_PAIR__MAPPINGS_OVER_TO_RULE2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Mapping> getMappingsRule1ToRule2() {
		return (EList<Mapping>)eGet(TggPackage.Literals.CRIT_PAIR__MAPPINGS_RULE1_TO_RULE2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(TggPackage.Literals.CRIT_PAIR__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(TggPackage.Literals.CRIT_PAIR__NAME, newName);
	}

} //CritPairImpl
