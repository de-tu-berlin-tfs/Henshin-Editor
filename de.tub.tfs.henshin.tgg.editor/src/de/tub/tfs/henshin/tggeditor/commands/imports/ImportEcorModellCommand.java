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
package de.tub.tfs.henshin.tggeditor.commands.imports;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;

/**
 * The Class ImportEcorModellCommand.
 *
 * @author Johann
 */
public class ImportEcorModellCommand extends Command {

	/** The EPackage. */
	private EPackage ePackage;
	
	/** The transformation system. */
	private Module transformationSystem;
	
	/**
	 * Instantiates a new import ecor model command.
	 *
	 * @param transformationSystem the transformation system
	 * @param epackge the epackge
	 */
	public ImportEcorModellCommand(Module transformationSystem, EPackage epackge) {
		super();
		this.ePackage = epackge;
		this.transformationSystem = transformationSystem;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationSystem!=null && ePackage!=null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		((EObjectResolvingEList<EPackage>)transformationSystem.getImports()).addUnique(ePackage);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationSystem.getImports().remove(ePackage);
	}
	
	
}
