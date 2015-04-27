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
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * The Class CreateAttributesCommand.
 */
public class CreateAttributesCommand extends CompoundCommand {

	/** The node. */
	private Node node;

	/**
	 * Instantiates a new creates the attributes command.
	 * 
	 * @param node
	 *            the node
	 */
	public CreateAttributesCommand(Node node) {
		super();
		this.node = node;
	}

	/**
	 * Gets the node.
	 * 
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Adds the create attribute command.
	 * 
	 * @param attributeType
	 *            A type of attribute to create.
	 * @param value
	 *            Attribute value to set.
	 */
	public void addCreateAttributeCommand(EAttribute attributeType, String value) {
		add(new CreateAttributeCommand(node, value, attributeType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return super.canExecute() || getCommands().isEmpty();
	}

}
