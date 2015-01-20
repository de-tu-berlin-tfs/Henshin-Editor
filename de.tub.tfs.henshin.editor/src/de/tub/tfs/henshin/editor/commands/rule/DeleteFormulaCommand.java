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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.FormulaTree;

/**
 * This command adds command(s) to delete all rule nodes and command to delete
 * an empty formula. An empty formula means that this formula has no application
 * condition.
 * 
 * @author Angeline Warning
 */
public class DeleteFormulaCommand extends CompoundCommand {

	/**
	 * Instantiates a new delete formula command.
	 * 
	 * @param formula
	 *            the formula
	 * @param parent
	 *            the parent
	 */
	public DeleteFormulaCommand(Formula formula, EObject parent) {
		List<NestedCondition> applConditions = FormulaTree
				.getApplConditions(formula);
		for (NestedCondition ac : applConditions) {
			Graph conclusion = ac.getConclusion();
			if (conclusion != null) {
				for (Node node : conclusion.getNodes()) {
					// Deletes all nodes contained in NC's conclusions
					add(new DeleteRuleNodeCommand(node));
				}
			}
		}

		add(new DeleteEmptyFormulaCommand(formula, parent));
	}

}
