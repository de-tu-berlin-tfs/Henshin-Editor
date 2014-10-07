/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.analysis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Critical Pair Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCriticalPairType()
 * @model
 * @generated
 */
public enum CriticalPairType implements Enumerator {
	/**
	 * The '<em><b>DELETE USE CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_USE_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_USE_CONFLICT(0, "DELETE_USE_CONFLICT", "delete-use-conflict"),

	/**
	 * The '<em><b>DELETE NEED CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_NEED_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_NEED_CONFLICT(1, "DELETE_NEED_CONFLICT", "delete-need-conflict"),

	/**
	 * The '<em><b>PRODUCE FORBID CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_FORBID_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	PRODUCE_FORBID_CONFLICT(2, "PRODUCE_FORBID_CONFLICT", "produce-forbid-conflict"),

	/**
	 * The '<em><b>PRODUCE EDGE DELTE NODE CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_EDGE_DELTE_NODE_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	PRODUCE_EDGE_DELTE_NODE_CONFLICT(3, "PRODUCE_EDGE_DELTE_NODE_CONFLICT", "produceEdge-deleteNode-conflict"),

	/**
	 * The '<em><b>CHANGE USE ATTR CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE_ATTR_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_USE_ATTR_CONFLICT(4, "CHANGE_USE_ATTR_CONFLICT", "change-use-attr-conflict"),

	/**
	 * The '<em><b>CHANGE NEED ATTR CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_NEED_ATTR_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_NEED_ATTR_CONFLICT(5, "CHANGE_NEED_ATTR_CONFLICT", "change-need-attr-conflict"),

	/**
	 * The '<em><b>CHANGE FORBID ATTR CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID_ATTR_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_FORBID_ATTR_CONFLICT(6, "CHANGE_FORBID_ATTR_CONFLICT", "change-forbid-attr-conflict"),

	/**
	 * The '<em><b>DELETE FORBID DEPENDENCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_FORBID_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_FORBID_DEPENDENCY(7, "DELETE_FORBID_DEPENDENCY", "produce-forbid-dependency"),

	/**
	 * The '<em><b>PRODUCE USE DEPENDENCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_USE_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	PRODUCE_USE_DEPENDENCY(8, "PRODUCE_USE_DEPENDENCY", "produce-use-dependency"),

	/**
	 * The '<em><b>PRODUCE DELETE DEPENDENCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_DELETE_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	PRODUCE_DELETE_DEPENDENCY(9, "PRODUCE_DELETE_DEPENDENCY", "produce-delete-dependency"),

	/**
	 * The '<em><b>PRODUCE NEED DEPENDENCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_NEED_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	PRODUCE_NEED_DEPENDENCY(10, "PRODUCE_NEED_DEPENDENCY", "produce-need-dependency"),

	/**
	 * The '<em><b>CHANGE USE ATTR DEPENDENCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE_ATTR_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_USE_ATTR_DEPENDENCY(11, "CHANGE_USE_ATTR_DEPENDENCY", "change-use-attr-dependency"),

	/**
	 * The '<em><b>CHANGE FORBID ATTR DEPENDENCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID_ATTR_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_FORBID_ATTR_DEPENDENCY(12, "CHANGE_FORBID_ATTR_DEPENDENCY", "change-forbid-attr-dependency");

	/**
	 * The '<em><b>DELETE USE CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DELETE USE CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_USE_CONFLICT
	 * @model literal="delete-use-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_USE_CONFLICT_VALUE = 0;

	/**
	 * The '<em><b>DELETE NEED CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DELETE NEED CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_NEED_CONFLICT
	 * @model literal="delete-need-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_NEED_CONFLICT_VALUE = 1;

	/**
	 * The '<em><b>PRODUCE FORBID CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRODUCE FORBID CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_FORBID_CONFLICT
	 * @model literal="produce-forbid-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int PRODUCE_FORBID_CONFLICT_VALUE = 2;

	/**
	 * The '<em><b>PRODUCE EDGE DELTE NODE CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRODUCE EDGE DELTE NODE CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_EDGE_DELTE_NODE_CONFLICT
	 * @model literal="produceEdge-deleteNode-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int PRODUCE_EDGE_DELTE_NODE_CONFLICT_VALUE = 3;

	/**
	 * The '<em><b>CHANGE USE ATTR CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANGE USE ATTR CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE_ATTR_CONFLICT
	 * @model literal="change-use-attr-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_USE_ATTR_CONFLICT_VALUE = 4;

	/**
	 * The '<em><b>CHANGE NEED ATTR CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANGE NEED ATTR CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_NEED_ATTR_CONFLICT
	 * @model literal="change-need-attr-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_NEED_ATTR_CONFLICT_VALUE = 5;

	/**
	 * The '<em><b>CHANGE FORBID ATTR CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANGE FORBID ATTR CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID_ATTR_CONFLICT
	 * @model literal="change-forbid-attr-conflict"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_FORBID_ATTR_CONFLICT_VALUE = 6;

	/**
	 * The '<em><b>DELETE FORBID DEPENDENCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DELETE FORBID DEPENDENCY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_FORBID_DEPENDENCY
	 * @model literal="produce-forbid-dependency"
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_FORBID_DEPENDENCY_VALUE = 7;

	/**
	 * The '<em><b>PRODUCE USE DEPENDENCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRODUCE USE DEPENDENCY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_USE_DEPENDENCY
	 * @model literal="produce-use-dependency"
	 * @generated
	 * @ordered
	 */
	public static final int PRODUCE_USE_DEPENDENCY_VALUE = 8;

	/**
	 * The '<em><b>PRODUCE DELETE DEPENDENCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRODUCE DELETE DEPENDENCY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_DELETE_DEPENDENCY
	 * @model literal="produce-delete-dependency"
	 * @generated
	 * @ordered
	 */
	public static final int PRODUCE_DELETE_DEPENDENCY_VALUE = 9;

	/**
	 * The '<em><b>PRODUCE NEED DEPENDENCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRODUCE NEED DEPENDENCY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRODUCE_NEED_DEPENDENCY
	 * @model literal="produce-need-dependency"
	 * @generated
	 * @ordered
	 */
	public static final int PRODUCE_NEED_DEPENDENCY_VALUE = 10;

	/**
	 * The '<em><b>CHANGE USE ATTR DEPENDENCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANGE USE ATTR DEPENDENCY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE_ATTR_DEPENDENCY
	 * @model literal="change-use-attr-dependency"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_USE_ATTR_DEPENDENCY_VALUE = 11;

	/**
	 * The '<em><b>CHANGE FORBID ATTR DEPENDENCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANGE FORBID ATTR DEPENDENCY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID_ATTR_DEPENDENCY
	 * @model literal="change-forbid-attr-dependency"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_FORBID_ATTR_DEPENDENCY_VALUE = 12;

	/**
	 * An array of all the '<em><b>Critical Pair Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CriticalPairType[] VALUES_ARRAY =
		new CriticalPairType[] {
			DELETE_USE_CONFLICT,
			DELETE_NEED_CONFLICT,
			PRODUCE_FORBID_CONFLICT,
			PRODUCE_EDGE_DELTE_NODE_CONFLICT,
			CHANGE_USE_ATTR_CONFLICT,
			CHANGE_NEED_ATTR_CONFLICT,
			CHANGE_FORBID_ATTR_CONFLICT,
			DELETE_FORBID_DEPENDENCY,
			PRODUCE_USE_DEPENDENCY,
			PRODUCE_DELETE_DEPENDENCY,
			PRODUCE_NEED_DEPENDENCY,
			CHANGE_USE_ATTR_DEPENDENCY,
			CHANGE_FORBID_ATTR_DEPENDENCY,
		};

	/**
	 * A public read-only list of all the '<em><b>Critical Pair Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CriticalPairType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Critical Pair Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CriticalPairType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CriticalPairType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Critical Pair Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CriticalPairType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CriticalPairType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Critical Pair Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CriticalPairType get(int value) {
		switch (value) {
			case DELETE_USE_CONFLICT_VALUE: return DELETE_USE_CONFLICT;
			case DELETE_NEED_CONFLICT_VALUE: return DELETE_NEED_CONFLICT;
			case PRODUCE_FORBID_CONFLICT_VALUE: return PRODUCE_FORBID_CONFLICT;
			case PRODUCE_EDGE_DELTE_NODE_CONFLICT_VALUE: return PRODUCE_EDGE_DELTE_NODE_CONFLICT;
			case CHANGE_USE_ATTR_CONFLICT_VALUE: return CHANGE_USE_ATTR_CONFLICT;
			case CHANGE_NEED_ATTR_CONFLICT_VALUE: return CHANGE_NEED_ATTR_CONFLICT;
			case CHANGE_FORBID_ATTR_CONFLICT_VALUE: return CHANGE_FORBID_ATTR_CONFLICT;
			case DELETE_FORBID_DEPENDENCY_VALUE: return DELETE_FORBID_DEPENDENCY;
			case PRODUCE_USE_DEPENDENCY_VALUE: return PRODUCE_USE_DEPENDENCY;
			case PRODUCE_DELETE_DEPENDENCY_VALUE: return PRODUCE_DELETE_DEPENDENCY;
			case PRODUCE_NEED_DEPENDENCY_VALUE: return PRODUCE_NEED_DEPENDENCY;
			case CHANGE_USE_ATTR_DEPENDENCY_VALUE: return CHANGE_USE_ATTR_DEPENDENCY;
			case CHANGE_FORBID_ATTR_DEPENDENCY_VALUE: return CHANGE_FORBID_ATTR_DEPENDENCY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private CriticalPairType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //CriticalPairType
