/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.analysis;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Critical Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getType <em>Type</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getRule1 <em>Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getRule2 <em>Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getSourceUnit <em>Source Unit</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getTargetUnit <em>Target Unit</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getOverlapping <em>Overlapping</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getMappingsOverlappingToRule1 <em>Mappings Overlapping To Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getMappingsOverlappingToRule2 <em>Mappings Overlapping To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.analysis.CriticalPair#getCriticalObjects <em>Critical Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair()
 * @model
 * @generated
 */
public interface CriticalPair extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * The literals are from the enumeration {@link de.tub.tfs.henshin.analysis.CriticalPairType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see de.tub.tfs.henshin.analysis.CriticalPairType
	 * @see #setType(CriticalPairType)
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_Type()
	 * @model default="0"
	 * @generated
	 */
	CriticalPairType getType();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.analysis.CriticalPair#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see de.tub.tfs.henshin.analysis.CriticalPairType
	 * @see #getType()
	 * @generated
	 */
	void setType(CriticalPairType value);

	/**
	 * Returns the value of the '<em><b>Rule1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule1</em>' reference.
	 * @see #setRule1(Rule)
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_Rule1()
	 * @model
	 * @generated
	 */
	Rule getRule1();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.analysis.CriticalPair#getRule1 <em>Rule1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule1</em>' reference.
	 * @see #getRule1()
	 * @generated
	 */
	void setRule1(Rule value);

	/**
	 * Returns the value of the '<em><b>Rule2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule2</em>' reference.
	 * @see #setRule2(Rule)
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_Rule2()
	 * @model
	 * @generated
	 */
	Rule getRule2();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.analysis.CriticalPair#getRule2 <em>Rule2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule2</em>' reference.
	 * @see #getRule2()
	 * @generated
	 */
	void setRule2(Rule value);

	/**
	 * Returns the value of the '<em><b>Source Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Unit</em>' reference.
	 * @see #setSourceUnit(Unit)
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_SourceUnit()
	 * @model
	 * @generated
	 */
	Unit getSourceUnit();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.analysis.CriticalPair#getSourceUnit <em>Source Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Unit</em>' reference.
	 * @see #getSourceUnit()
	 * @generated
	 */
	void setSourceUnit(Unit value);

	/**
	 * Returns the value of the '<em><b>Target Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Unit</em>' reference.
	 * @see #setTargetUnit(Unit)
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_TargetUnit()
	 * @model
	 * @generated
	 */
	Unit getTargetUnit();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.analysis.CriticalPair#getTargetUnit <em>Target Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Unit</em>' reference.
	 * @see #getTargetUnit()
	 * @generated
	 */
	void setTargetUnit(Unit value);

	/**
	 * Returns the value of the '<em><b>Overlapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overlapping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overlapping</em>' containment reference.
	 * @see #setOverlapping(Graph)
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_Overlapping()
	 * @model containment="true"
	 * @generated
	 */
	Graph getOverlapping();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.analysis.CriticalPair#getOverlapping <em>Overlapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overlapping</em>' containment reference.
	 * @see #getOverlapping()
	 * @generated
	 */
	void setOverlapping(Graph value);

	/**
	 * Returns the value of the '<em><b>Mappings Overlapping To Rule1</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings Overlapping To Rule1</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings Overlapping To Rule1</em>' containment reference list.
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_MappingsOverlappingToRule1()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappingsOverlappingToRule1();

	/**
	 * Returns the value of the '<em><b>Mappings Overlapping To Rule2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings Overlapping To Rule2</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings Overlapping To Rule2</em>' containment reference list.
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_MappingsOverlappingToRule2()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappingsOverlappingToRule2();

	/**
	 * Returns the value of the '<em><b>Mappings Rule1 To Rule2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings Rule1 To Rule2</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings Rule1 To Rule2</em>' containment reference list.
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_MappingsRule1ToRule2()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappingsRule1ToRule2();

	/**
	 * Returns the value of the '<em><b>Critical Objects</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Critical Objects</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Critical Objects</em>' reference list.
	 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPair_CriticalObjects()
	 * @model
	 * @generated
	 */
	EList<EObject> getCriticalObjects();

} // CriticalPair
