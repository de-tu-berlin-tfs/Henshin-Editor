/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.tools;

import java.util.AbstractMap.SimpleEntry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.tggeditor.commands.create.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The AttributeCreationTool creates Attributes for a node.
 * @see de.tub.tfs.henshin.tggeditor.commands.create.CreateAttributeCommand
 */
public class AttributeCreationTool extends CreationTool {
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands.Command)
	 */
	@Override
	protected void executeCommand(final Command command) {
		if (command instanceof CreateAttributeCommand) {
			CreateAttributeCommand c = (CreateAttributeCommand) command;
			SimpleEntry<EAttribute, String> result = DialogUtil
					.runAttributeCreationDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().getActivePart().getSite().getShell(), c.getNode());
			if (result.getKey() != null && result.getValue() != null) {
				c.setLabel(result.getKey().getName()+":"+result.getValue());
				c.setAttributeType(result.getKey());
				c.setValue(result.getValue());
				super.executeCommand(c);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
	 */
	@Override
	protected void handleFinished() {
		reactivate();
//		super.handleFinished();
	}
}
