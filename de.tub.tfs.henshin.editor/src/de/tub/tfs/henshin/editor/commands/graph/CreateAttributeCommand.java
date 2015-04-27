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
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;

/**
 * The Class CreateAttributeCommand.
 * 
 * @author Johann Erstell ein Attribute f�r einen Knoten
 */
public class CreateAttributeCommand extends Command {

	/** The node. */
	Node node;

	/** The attribute. */
	Attribute attribute;

	/** The value. */
	String value;

	/** The type. */
	EAttribute type;

	/**
	 * Instantiates a new creates the attribute command.
	 * 
	 * @param node2
	 *            the node2
	 * @param value2
	 *            the value2
	 * @param eattribute
	 *            the eattribute
	 */
	public CreateAttributeCommand(Node node2, String value2,
			EAttribute eattribute) {
		this.node = node2;
		this.value = value2;
		this.attribute = HenshinFactory.eINSTANCE.createAttribute();
		this.type = eattribute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return node != null && attribute != null && !HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		attribute.setValue(value);
		attribute.setType(type);
		attribute.setNode(node);
		node.getAttributes().add(attribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		node.getAttributes().remove(attribute);
	}

	/**
	 * Gets the node.
	 * 
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

}
