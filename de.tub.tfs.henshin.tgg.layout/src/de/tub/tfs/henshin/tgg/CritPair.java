/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Crit Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getOverlapping <em>Overlapping</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getRule1 <em>Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getRule2 <em>Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getMappingsOverToRule1 <em>Mappings Over To Rule1</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getMappingsOverToRule2 <em>Mappings Over To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getMappingsRule1ToRule2 <em>Mappings Rule1 To Rule2</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.CritPair#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CritPair extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Overlapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overlapping</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overlapping</em>' reference.
	 * @see #setOverlapping(Graph)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_Overlapping()
	 * @model
	 * @generated
	 */
	Graph getOverlapping();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.CritPair#getOverlapping <em>Overlapping</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overlapping</em>' reference.
	 * @see #getOverlapping()
	 * @generated
	 */
	void setOverlapping(Graph value);

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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_Rule1()
	 * @model
	 * @generated
	 */
	Rule getRule1();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.CritPair#getRule1 <em>Rule1</em>}' reference.
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_Rule2()
	 * @model
	 * @generated
	 */
	Rule getRule2();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.CritPair#getRule2 <em>Rule2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule2</em>' reference.
	 * @see #getRule2()
	 * @generated
	 */
	void setRule2(Rule value);

	/**
	 * Returns the value of the '<em><b>Mappings Over To Rule1</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings Over To Rule1</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings Over To Rule1</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_MappingsOverToRule1()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappingsOverToRule1();

	/**
	 * Returns the value of the '<em><b>Mappings Over To Rule2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings Over To Rule2</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings Over To Rule2</em>' containment reference list.
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_MappingsOverToRule2()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappingsOverToRule2();

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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_MappingsRule1ToRule2()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappingsRule1ToRule2();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"name"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getCritPair_Name()
	 * @model default="name"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.CritPair#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // CritPair
