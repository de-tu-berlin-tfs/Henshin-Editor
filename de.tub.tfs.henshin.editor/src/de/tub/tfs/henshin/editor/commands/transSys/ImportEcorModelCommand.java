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
package de.tub.tfs.henshin.editor.commands.transSys;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * The Class ImportEcorModelCommand.
 * 
 * @author Johann
 */
public class ImportEcorModelCommand extends CompoundCommand {

	/**
	 * Fügt dem Transformationssystem den Menüpunkt zum Hinzufügen eines Ecore
	 * Modells hinzu.
	 * 
	 * @param transformationSystem
	 *            aktuelles Transformationssystem
	 * @param ePackages
	 *            Liste über alle Packages im Ordner
	 */
	public ImportEcorModelCommand(Module transformationSystem,
			List<EPackage> ePackages) {
		for (EPackage ePackage : ePackages) {
			add(new ImportEcorModellCommand(transformationSystem, ePackage));
		}
	}

}
