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
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;


/**
 * The class CreatePriorotyRuleCommand creates a new rule folder.
 * 
 * It is copied from CreateRuleFolderCommand - the difference is: 
 * CreateRuleFolderCommand creates an Independent Unit as folder,
 * CreatePriorityRuleFolderCommand creates a Priority Unit as
 * folder.
 */
public class CreatePriorityRuleFolderCommand extends Command {
	
	/** transformation system */
	private Module module;
	/** The folder to create */
	private PriorityUnit folder;
	/** Parent Unit */
	private MultiUnit unit;
	

	public CreatePriorityRuleFolderCommand(Module module, String name, MultiUnit unit) {
		this(module,name);
		this.unit = unit;
	}
		
	/**
	 * the constructor
	 * @param module the transformationsystem
	 * @param name the name for the new folder
	 */
	public CreatePriorityRuleFolderCommand(Module module, String name) {
		this.module = module;
		folder = HenshinFactory.eINSTANCE.createPriorityUnit();

		folder.setName(name);

		folder.setDescription("priorityRuleFolder.png");
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {		
		return module != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (unit != null)
			unit.getSubUnits().add(folder);
		module.getUnits().add(folder);
		
		
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		EList<Unit> units = module.getUnits();
		int index = units.indexOf(folder);
		units.remove(index);
		if (unit != null)
			unit.getSubUnits().remove(folder);
		super.undo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return module != null && folder != null;
	}

}
