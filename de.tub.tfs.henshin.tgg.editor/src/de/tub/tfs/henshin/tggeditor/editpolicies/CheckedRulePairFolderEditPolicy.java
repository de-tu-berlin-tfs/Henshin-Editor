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
package de.tub.tfs.henshin.tggeditor.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteCritPairCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CheckedRulePairFolder;


public class CheckedRulePairFolderEditPolicy extends ComponentEditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		
		if (getHost().getModel() instanceof CheckedRulePairFolder) {
			
			CompoundCommand command = new CompoundCommand();
			
			for (CritPair crit : ((CheckedRulePairFolder)getHost().getModel()).getCritPairs()) {
				command.add(new DeleteCritPairCommand(crit));
			}
			
			return command;
		}
		
		return null;
	}

}
