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
package de.tub.tfs.henshin.tggeditor.editpolicies;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteImportedModellCommand;


public class ImportedModellEditPolicy extends ComponentEditPolicy {

	private TGG tgg;
	
	public ImportedModellEditPolicy(TGG tgg) {
		this.tgg = tgg;
	}
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Module module = (Module) getHost().getParent().getParent().getModel();
		return new DeleteImportedModellCommand(((ImportedPackage) getHost().getModel()), tgg, module);
		
	}

	
}
