/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.interpreter.RuleApplication;

/**
 * The Class RuleApplicationEObject.
 */
public class RuleApplicationEObject extends EObjectImpl {

	/** The rule application. */
	private RuleApplication ruleApplication;

	/** The executed. */
	private boolean executed = true;

	/**
	 * Gets the rule application.
	 * 
	 * @return the rule application
	 */
	public synchronized RuleApplication getRuleApplication() {
		return ruleApplication;
	}

	/**
	 * Instantiates a new rule application e object.
	 * 
	 * @param unit
	 *            the unit
	 */
	public RuleApplicationEObject(RuleApplication unit) {
		super();
		this.ruleApplication = unit;
	}

	/**
	 * Checks if is executed.
	 * 
	 * @return true, if is executed
	 */
	public synchronized boolean isExecuted() {
		return executed;
	}

	/**
	 * Sets the executed.
	 * 
	 * @param executed
	 *            the new executed
	 */
	public synchronized void setExecuted(boolean executed) {
		this.executed = executed;
	}

}
