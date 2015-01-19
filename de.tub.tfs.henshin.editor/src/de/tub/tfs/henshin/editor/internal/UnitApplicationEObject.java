/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
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
