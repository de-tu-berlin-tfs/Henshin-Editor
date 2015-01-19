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
package de.tub.tfs.henshin.editor.commands.transformation_unit.parameter;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

/**
 * @author Johann
 * 
 */
public class CreateParameterAndRenameNodeCommand extends CompoundCommand {

	private final Unit transformationUnit;

	private final Node node;

	private final String name;

	/**
	 * @param node
	 * @param name
	 */
	public CreateParameterAndRenameNodeCommand(Unit transUnit,
			Node node, String name) {
		super();
		this.transformationUnit = transUnit;
		this.node = node;
		this.name = name;

		add(new CreateParameterCommand(transUnit, name));

		add(new SetEObjectFeatureValueCommand(node, name,
				HenshinPackage.NODE__NAME));
	}

	/**
	 * @return the node
	 */
	public synchronized Node getNode() {
		return node;
	}

	/**
	 * @return the transformationUnit
	 */
	public Unit getTransformationUnit() {
		return transformationUnit;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
