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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;

/**
 * A {@link Command} to create {@link Unit}s.
 * 
 * @param <T>
 *            the {@link Unit} type of the new object.
 * 
 * @author Johann
 */
public class CreateTransformationUnitCommand<T extends Unit>
		extends CompoundCommand {

	/** The transformation system. */
	protected Module transformationSystem;

	/** The name. */
	protected String name;

	/** The transformation unit. */
	protected T transformationUnit;

	/**
	 * @param transformationSystem
	 *            the transformation system
	 * @param unit
	 *            the unit
	 * @param name
	 *            the name
	 */
	public CreateTransformationUnitCommand(
			Module transformationSystem, T unit, String name) {
		this(
				transformationSystem,
				unit,
				name,
				HenshinPackage.Literals.MODULE__UNITS);
	}

	public CreateTransformationUnitCommand(
			Module transformationSystem, T unit, String name,
			EStructuralFeature containerFeature) {
		super("Create '" + name + "'");

		if (transformationSystem != null && unit != null && name != null) {

			unit.setName(name);
			unit.setDescription("");
			unit.setActivated(true);

			add(new SimpleAddEObjectCommand<Module, Unit>(
					unit, containerFeature, transformationSystem));
		}
	}

	/**
	 * Instantiates a new creates the transformation unit command.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 * @param clazz
	 *            the clazz
	 * @param name
	 *            the name
	 */
	@SuppressWarnings("unchecked")
	public CreateTransformationUnitCommand(
			Module transformationSystem, Class<?> clazz,
			String name) {
		this(transformationSystem, (T) new ModelCreationFactory(clazz)
				.getNewObject(), name);
	}
}