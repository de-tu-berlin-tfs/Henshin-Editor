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
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.graph.DeleteNodeCommand;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * A {@link Command command} to delete {@link Node nodes} contained in a
 * {@link Rule rule}'s containment structure. (e.g LHS, RHS, NACs...)
 */
public class DeleteRuleNodeCommand extends CompoundCommand {

	/**
	 * The node to be deleted.
	 */
	private Node model;

	/**
	 * Instantiates a new delete rule node command.
	 * 
	 * @param model
	 *            the node
	 */
	public DeleteRuleNodeCommand(Node model) {
		this.model = model;

		final Graph parentModel = model.getGraph();

		if (parentModel != null) {
			if (parentModel.eContainer() instanceof NestedCondition) {
				final NestedCondition ac = (NestedCondition) parentModel
						.eContainer();
				for (Mapping m : ac.getMappings()) {
					if (m.getImage() == model) {
						add(new DeleteMappingCommand(m, false));
					}
				}

				for (Mapping m : ModelUtil
						.getMappings(parentModel.getFormula())) {
					if (m.getImage() == model || m.getOrigin() == model) {
						add(new DeleteMappingCommand(m, m.getOrigin() == model));
					}
				}
			}
			if (parentModel.eContainer() instanceof Rule) {
				for (Mapping m : ((Rule) parentModel.eContainer())
						.getMappings()) {
					if (m.getImage() == model || m.getOrigin() == model) {
						add(new DeleteMappingCommand(m, m.getOrigin() == model));
					}

				}

				if (((Rule) model.eContainer().eContainer()).getLhs() == model
						.eContainer()) {
					Graph lhs = (Graph) model.eContainer();
					if (lhs.getFormula() != null) {
						addDeleteMappingCommand(lhs.getFormula());
					}
				}
			}
		}

		add(new DeleteNodeCommand(model));
	}

	/**
	 * If an application condition of a formula has mapping(s), then adds a
	 * delete mapping command for each found mapping.
	 * 
	 * @param formula
	 *            a Formula to check, whether its application condition has
	 *            mapping(s) with {@code model} as {@code source} node or not.
	 */
	private void addDeleteMappingCommand(Formula formula) {
		if (formula instanceof NestedCondition) {
			for (Mapping m : ((NestedCondition) formula).getMappings()) {
				if (m.getOrigin() == model) {
					add(new DeleteMappingCommand(m, true));
				}
			}
		} else if (formula instanceof UnaryFormula) {
			addDeleteMappingCommand(((UnaryFormula) formula).getChild());
		} else if (formula instanceof BinaryFormula) {
			addDeleteMappingCommand(((BinaryFormula) formula).getLeft());
			addDeleteMappingCommand(((BinaryFormula) formula).getRight());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
	}

}
