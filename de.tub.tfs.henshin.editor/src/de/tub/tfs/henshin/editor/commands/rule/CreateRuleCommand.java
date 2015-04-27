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
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateRuleCommand.
 * 
 * @author Johann, Angeline
 */
public class CreateRuleCommand extends Command {

	/** The transformation system. */
	private Module transformationSystem;

	/** The rule to create. */
	private Rule rule;

	/**
	 * Instantiates a new creates the rule command.
	 * 
	 * @param transformationSystem
	 *            The transformation system.
	 * @param name
	 *            The rule name
	 */
	public CreateRuleCommand(Module transformationSystem,
			String name) {
		this.transformationSystem = transformationSystem;

		this.rule = HenshinFactory.eINSTANCE.createRule();
		this.rule.setActivated(true);
		rule.setName(name);
	}

	public CreateRuleCommand(final Module transformationSystem,
			final String name, final boolean isKernelRule) {
		this(transformationSystem, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		transformationSystem.getUnits().add(rule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationSystem.getUnits().remove(rule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationSystem != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return rule != null && transformationSystem != null;
	}

	/**
	 * @return the rule
	 */
	public synchronized Rule getRule() {
		return rule;
	}

}
