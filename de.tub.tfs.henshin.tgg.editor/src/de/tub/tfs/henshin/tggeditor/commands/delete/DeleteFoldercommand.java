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
package de.tub.tfs.henshin.tggeditor.commands.delete;

import java.util.List;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.gef.commands.Command;

public class DeleteFoldercommand extends Command {

	private IndependentUnit model;

	public DeleteFoldercommand(IndependentUnit model) {
		this.model = model;
	}
	
	
	
	@Override
	public void execute() {
		model.getSubUnits().clear();
		((List)model.eContainer().eGet(model.eContainingFeature())).remove(model);		
		
	}

}
