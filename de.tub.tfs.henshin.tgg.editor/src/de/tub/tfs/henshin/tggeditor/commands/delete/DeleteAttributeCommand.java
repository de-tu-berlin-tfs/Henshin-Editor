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
package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteAttributeCommand extends CompoundCommand {
	/**
	 * Adds an attribute to a DeleteCommand.
	 * 
	 * @param attribute the attribute
	 */
	public DeleteAttributeCommand(Attribute attribute) {
		add(new SimpleDeleteEObjectCommand (attribute));
	}
}
