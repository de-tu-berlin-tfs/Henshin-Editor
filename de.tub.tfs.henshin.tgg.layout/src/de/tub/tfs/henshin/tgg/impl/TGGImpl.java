/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TggPackage;

import de.tub.tfs.henshin.tgg.TripleGraph;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.impl.ModuleImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getSrcroot <em>Srcroot</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTarroot <em>Tarroot</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getSource <em>Source</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getCorresp <em>Corresp</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getNodelayouts <em>Nodelayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getEdgelayouts <em>Edgelayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getGraphlayouts <em>Graphlayouts</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTRules <em>TRules</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getCritPairs <em>Crit Pairs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getSourcePkgs <em>Source Pkgs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getCorrespondencePkgs <em>Correspondence Pkgs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getTargetPkgs <em>Target Pkgs</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGImpl#getImportedPkgs <em>Imported Pkgs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TGGImpl extends ModuleImpl implements TGG {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TGG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 9;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSrcroot() {
		return (EObject)eGet(TggPackage.Literals.TGG__SRCROOT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrcroot(EObject newSrcroot) {
		eSet(TggPackage.Literals.TGG__SRCROOT, newSrcroot);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTarroot() {
		return (EObject)eGet(TggPackage.Literals.TGG__TARROOT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarroot(EObject newTarroot) {
		eSet(TggPackage.Literals.TGG__TARROOT, newTarroot);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getSource() {
		return (EPackage)eGet(TggPackage.Literals.TGG__SOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(EPackage newSource) {
		eSet(TggPackage.Literals.TGG__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getCorresp() {
		return (EPackage)eGet(TggPackage.Literals.TGG__CORRESP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorresp(EPackage newCorresp) {
		eSet(TggPackage.Literals.TGG__CORRESP, newCorresp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getTarget() {
		return (EPackage)eGet(TggPackage.Literals.TGG__TARGET, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(EPackage newTarget) {
		eSet(TggPackage.Literals.TGG__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<NodeLayout> getNodelayouts() {
		return (EList<NodeLayout>)eGet(TggPackage.Literals.TGG__NODELAYOUTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EdgeLayout> getEdgelayouts() {
		return (EList<EdgeLayout>)eGet(TggPackage.Literals.TGG__EDGELAYOUTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<GraphLayout> getGraphlayouts() {
		return (EList<GraphLayout>)eGet(TggPackage.Literals.TGG__GRAPHLAYOUTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<TRule> getTRules() {
		return (EList<TRule>)eGet(TggPackage.Literals.TGG__TRULES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CritPair> getCritPairs() {
		return (EList<CritPair>)eGet(TggPackage.Literals.TGG__CRIT_PAIRS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EPackage> getSourcePkgs() {
		return (EList<EPackage>)eGet(TggPackage.Literals.TGG__SOURCE_PKGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EPackage> getCorrespondencePkgs() {
		return (EList<EPackage>)eGet(TggPackage.Literals.TGG__CORRESPONDENCE_PKGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EPackage> getTargetPkgs() {
		return (EList<EPackage>)eGet(TggPackage.Literals.TGG__TARGET_PKGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ImportedPackage> getImportedPkgs() {
		return (EList<ImportedPackage>)eGet(TggPackage.Literals.TGG__IMPORTED_PKGS, true);
	}

} //TGGImpl
