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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;




public class DeleteImportedModellCommand extends Command {
/**
 * Instantiates a new delete imported model command
 * 
 */
	
	private TGG tgg;
	private EPackage ePackage;
	private int model;
	private Module module;
	private ImportedPackage impPackage;
	
	public DeleteImportedModellCommand(ImportedPackage impPackage, TGG tgg, Module module) {
		this.tgg = tgg;
		this.ePackage = impPackage.getPackage();
		this.impPackage = impPackage;
		this.module = module;
	}
	
	@Override
	public boolean canExecute() {
			return true;
	}
	
	@Override
	public void execute() {
		
		tgg.getImportedPkgs().remove(impPackage);
		
		module.getImports().remove(ePackage);
		
	}
	
	@Override
	public void undo() {

		tgg.getImportedPkgs().add(impPackage);
		module.getImports().add(ePackage);
		super.undo();
	}

}
