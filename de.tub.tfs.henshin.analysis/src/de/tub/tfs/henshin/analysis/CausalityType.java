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
 * A representation of the literals of the enumeration '<em><b>Causality Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.analysis.AnalysisPackage#getCausalityType()
 * @model
 * @generated
 */
public enum CausalityType implements Enumerator {
	/**
	 * The '<em><b>Initialization</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INITIALIZATION_VALUE
	 * @generated
	 * @ordered
	 */
	INITIALIZATION(0, "Initialization", "Initialization"),

	/**
	 * The '<em><b>Conflict</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	CONFLICT(1, "Conflict", "Conflict"),

	/**
	 * The '<em><b>Dependency Along Controlflow</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEPENDENCY_ALONG_CONTROLFLOW_VALUE
	 * @generated
	 * @ordered
	 */
	DEPENDENCY_ALONG_CONTROLFLOW(2, "DependencyAlongControlflow", "DependencyAlongControlflow"),

	/**
	 * The '<em><b>Dependency Against Controlflow</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEPENDENCY_AGAINST_CONTROLFLOW_VALUE
	 * @generated
	 * @ordered
	 */
	DEPENDENCY_AGAINST_CONTROLFLOW(3, "DependencyAgainstControlflow", "DependencyAgainstControlflow");

	/**
	 * The '<em><b>Initialization</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Initialization</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INITIALIZATION
	 * @model name="Initialization"
	 * @generated
	 * @ordered
	 */
	public static final int INITIALIZATION_VALUE = 0;

	/**
	 * The '<em><b>Conflict</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Conflict</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONFLICT
	 * @model name="Conflict"
	 * @generated
	 * @ordered
	 */
	public static final int CONFLICT_VALUE = 1;

	/**
	 * The '<em><b>Dependency Along Controlflow</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dependency Along Controlflow</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEPENDENCY_ALONG_CONTROLFLOW
	 * @model name="DependencyAlongControlflow"
	 * @generated
	 * @ordered
	 */
	public static final int DEPENDENCY_ALONG_CONTROLFLOW_VALUE = 2;

	/**
	 * The '<em><b>Dependency Against Controlflow</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dependency Against Controlflow</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEPENDENCY_AGAINST_CONTROLFLOW
	 * @model name="DependencyAgainstControlflow"
	 * @generated
	 * @ordered
	 */
	public static final int DEPENDENCY_AGAINST_CONTROLFLOW_VALUE = 3;

	/**
	 * An array of all the '<em><b>Causality Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CausalityType[] VALUES_ARRAY =
		new CausalityType[] {
			INITIALIZATION,
			CONFLICT,
			DEPENDENCY_ALONG_CONTROLFLOW,
			DEPENDENCY_AGAINST_CONTROLFLOW,
		};

	/**
	 * A public read-only list of all the '<em><b>Causality Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CausalityType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Causality Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CausalityType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CausalityType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Causality Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CausalityType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CausalityType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Causality Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CausalityType get(int value) {
		switch (value) {
			case INITIALIZATION_VALUE: return INITIALIZATION;
			case CONFLICT_VALUE: return CONFLICT;
			case DEPENDENCY_ALONG_CONTROLFLOW_VALUE: return DEPENDENCY_ALONG_CONTROLFLOW;
			case DEPENDENCY_AGAINST_CONTROLFLOW_VALUE: return DEPENDENCY_AGAINST_CONTROLFLOW;
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
	private CausalityType(int value, String name, String literal) {
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
	
} //CausalityType
