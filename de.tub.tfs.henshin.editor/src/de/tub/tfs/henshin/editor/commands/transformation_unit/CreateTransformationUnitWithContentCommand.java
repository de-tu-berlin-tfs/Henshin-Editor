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
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import java.util.List;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.model.ModelCreationFactory;

/**
 * The Class CreateTransformationUnitWithContentCommand.
 * 
 * @param <T>
 *            the generic type
 * @author Johann
 */
public class CreateTransformationUnitWithContentCommand<T extends Unit>
		extends CompoundCommand {

	/**
	 * Instantiates a new creates the transformation unit with content command.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 * @param parentObject
	 *            the parent object
	 * @param clazz
	 *            the clazz
	 * @param contents
	 *            the contents
	 * @param name
	 *            the name
	 */
	@SuppressWarnings({ "unchecked" })
	public CreateTransformationUnitWithContentCommand(
			Module transformationSystem, Object parentObject,
			Class<?> clazz, List<Unit> contents, String name) {
		super();
		ModelCreationFactory m = new ModelCreationFactory(clazz);
		T unit = (T) m.getNewObject();
		add(new CreateTransformationUnitCommand<T>(transformationSystem, unit,
				name));
		add(new AddContentInNewUnitAndReplaceCommand(parentObject, unit,
				contents));
	}

}
