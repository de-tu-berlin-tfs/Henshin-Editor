/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.interpreter.UnitApplication;

import de.tub.tfs.henshin.editor.commands.transformation_unit.ExecuteTransformationUnitCommand;

/**
 * The Class UnitApplicationEObject.
 */
public class UnitApplicationEObject extends EObjectImpl {

	/** The unit application. */
	private ExecuteTransformationUnitCommand command;

	/**
	 * Gets the unit application.
	 * 
	 * @return the unit application
	 */
	public synchronized UnitApplication getUnitApplication() {
		return command.getUnitApplication();
	}

	/**
	 * Instantiates a new unit application e object.
	 * 
	 * @param unit
	 *            the unit
	 */
	public UnitApplicationEObject(ExecuteTransformationUnitCommand command) {
		super();
		this.command = command;
	}

	// public void refreshEdges(){
	// command.refreshEdges();
	// }

}
