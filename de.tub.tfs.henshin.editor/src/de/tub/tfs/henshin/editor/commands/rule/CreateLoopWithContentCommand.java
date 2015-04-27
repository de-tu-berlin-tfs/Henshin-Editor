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
 * RunRuleCommand.java
 *
 * Created 02.01.2012 - 12:51:11
 */
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

public class CreateLoopWithContentCommand extends CompoundCommand {



	private Unit unit;


	/**
	 * @param unit
	 */
	public CreateLoopWithContentCommand(Unit unit) {
		super("Create Loop with Rule");
		this.unit = unit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return unit != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		getCommands().clear();

		Module module = unit.getModule();
		
		
		LoopUnit loopUnit = HenshinFactory.eINSTANCE.createLoopUnit();
		loopUnit.setSubUnit(unit);
		// FIXME: Undo this - SUSANN likes this more:
		//loopUnit.setName(unit.getName()+"_alap");
		loopUnit.setName("alap "+unit.getName());
		
		module.getUnits().add(loopUnit);

	}

}
