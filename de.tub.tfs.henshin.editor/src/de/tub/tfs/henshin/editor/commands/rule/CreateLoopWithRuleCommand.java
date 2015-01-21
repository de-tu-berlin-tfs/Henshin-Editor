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
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

public class CreateLoopWithRuleCommand extends CompoundCommand {



	private Rule rule;


	/**
	 * @param rule
	 */
	public CreateLoopWithRuleCommand(Rule rule) {
		super("Create Loop with Rule");
		this.rule = rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return rule != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		getCommands().clear();

		Module module = rule.getModule();
		
		
		LoopUnit loopUnit = HenshinFactory.eINSTANCE.createLoopUnit();
		loopUnit.setSubUnit(rule);
		loopUnit.setName(rule.getName()+"_alap");
		
		module.getUnits().add(loopUnit);

	}

}
