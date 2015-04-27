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

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteAttributeCommand.
 */
public class DeleteAttributeCommand extends CompoundCommand {

	private Attribute attribute;

	/**
	 * F�gt ein �bergebenes Attribute zu einem DeletetCommand hinzu.
	 * 
	 * @param attribute
	 *            the attribute
	 */
	public DeleteAttributeCommand(Attribute attribute) {
		this.attribute = attribute;
		add(new SimpleDeleteEObjectCommand(attribute));
	}

	@Override
	public boolean canExecute() {
		
		return super.canExecute() && !HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule((Node) attribute.eContainer());
	}
}
