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
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.rule.RunRuleCommand;
import de.tub.tfs.henshin.editor.ui.dialog.ParemetersValueDialog;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.FormulaTree;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.validator.ExpressionValidator;
import de.tub.tfs.henshin.editor.util.validator.TypeEditorValidator;

/**
 * The Class ExecuteRuleAction.
 */
public class ExecuteRuleAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.ExecuteRuleAction"; //$NON-NLS-1$

	/** The Constant DESC. */
	static private final String DESC = "Execute Rule";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Execute Rule";

	/** The rule. */
	protected Rule rule;

	/** The graph. */
	protected Graph graph;

	/** The status. */
	private Status status = new Status(IStatus.ERROR, "My Plug-in ID", 0, "",
			null);

	/**
	 * Instantiates a new execute rule action.
	 * 
	 * @param part
	 *            the part
	 */
	public ExecuteRuleAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		rule = null;
		graph = null;
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			Object model = ((EditPart) selectedObject).getModel();

			if (model instanceof Rule) {
				rule = (Rule) model;
				setText("Execute");
			}

			if (model instanceof Graph) {
				if (((Graph) model).eContainer() instanceof Module) {
					graph = (Graph) model;
					setText(DESC);
				}
			}
		}

		return rule != null || graph != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		
		if (rule == null) {
			rule = getRule();
		} 
		if (rule.getKernelRule() != null)
			rule = rule.getKernelRule();
		if (graph == null) {
			graph = getGraph();
		}
		

		if (graph != null && rule != null) {
			Map<String, List<ExpressionValidator>> variable2ExpressionValidators = getParameter2ExpressionValidators();

			if (variable2ExpressionValidators != null) {
				Map<String, Object> assignments = new HashMap<String, Object>();

				if (variable2ExpressionValidators.size() > 0) {
					ParemetersValueDialog vD = new ParemetersValueDialog(
							getWorkbenchPart().getSite().getShell(), SWT.NULL,
							variable2ExpressionValidators);

					vD.open();

					if (!vD.isCancel()) {
						assignments = vD.getAssigment();
					} else {
						return;
					}
				}
				// ExecuteRuleCommand command = new ExecuteRuleCommand(graph,
				// rule, assignments);
				// execute(command);
				execute(new RunRuleCommand(rule, graph, assignments));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("play16.png");
	}

	/**
	 * Gets the graph.
	 * 
	 * @return the graph
	 */
	private Graph getGraph() {
		return DialogUtil.runGraphChoiceDialog(getWorkbenchPart().getSite()
				.getShell(), ((Module) rule.eContainer())
				.getInstances());
	}


	
	/**
	 * Gets the rule.
	 * 
	 * @return the rule
	 */
	private Rule getRule() {
		return DialogUtil.runRuleChoiceDialog(getWorkbenchPart().getSite()
				.getShell(),HenshinUtil.getRules((Module) graph.eContainer())
				);
	}

	/**
	 * Gets the parameter2 expression validators.
	 * 
	 * @return the parameter2 expression validators
	 */
	private Map<String, List<ExpressionValidator>> getParameter2ExpressionValidators() {
		Map<String, List<ExpressionValidator>> parameter2Expression = new HashMap<String, List<ExpressionValidator>>();
		if (rule.getLhs().getFormula() != null) {
			for (Graph nesteConditional : FormulaTree.getFormulaGraph(rule
					.getLhs().getFormula())) {
				for (Node node : nesteConditional.getNodes()) {
					for (Attribute attr : node.getAttributes()) {
						TypeEditorValidator typeEditorValidator = new TypeEditorValidator(
								attr);
						String fehlermeldung = typeEditorValidator.isValid(attr
								.getValue());
						if (fehlermeldung != null) {
							ErrorDialog dialogF = new ErrorDialog(
									getWorkbenchPart().getSite().getShell(),
									"Fehler", fehlermeldung, status,
									IStatus.ERROR);
							dialogF.open();
							return null;
						}
						for (String par : typeEditorValidator
								.getUsedParameters()) {
							if (!parameter2Expression.containsKey(par)) {
								List<ExpressionValidator> newList = new Vector<ExpressionValidator>();
								parameter2Expression.put(par, newList);
							}
							parameter2Expression.get(par).add(
									new ExpressionValidator(attr));
						}
					}
				}
			}
		}
		for (Node node : rule.getRhs().getNodes()) {
			// Abstract type in RHS nur mit Mapping
			// if (node.getType().isAbstract()) {
			// boolean hasMapping = false;
			// for (Mapping m : rule.getMappings()) {
			// if (m.getImage() == node) {
			// hasMapping = true;
			// break;
			// }
			// }
			// if (!hasMapping) {
			// ErrorDialog dialogF = new ErrorDialog(getWorkbenchPart()
			// .getSite().getShell(), "Fehler", "Abstract node "
			// + node.getName() + " has not mapping!", status,
			// IStatus.ERROR);
			// dialogF.open();
			// return null;
			// }
			// }
			// ------------------------------------

			for (Attribute attr : node.getAttributes()) {
				ExpressionValidator expressionValidator = new ExpressionValidator(
						attr);
				for (String var : expressionValidator.getUsedParameters(attr
						.getValue())) {
					if (!parameter2Expression.containsKey(var)) {
						List<ExpressionValidator> newList = new Vector<ExpressionValidator>();
						parameter2Expression.put(var, newList);
					}
					parameter2Expression.get(var).add(expressionValidator);
				}
			}
		}

		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				TypeEditorValidator typeEditorValidator = new TypeEditorValidator(
						attr);
				String fehlermeldung = typeEditorValidator.isValid(attr
						.getValue());
				if (fehlermeldung != null) {
					ErrorDialog dialogF = new ErrorDialog(getWorkbenchPart()
							.getSite().getShell(), "Fehler", fehlermeldung,
							status, IStatus.ERROR);
					dialogF.open();
					return null;
				}
				for (String par : typeEditorValidator.getUsedParameters()) {
					if (!parameter2Expression.containsKey(par)) {
						List<ExpressionValidator> newList = new Vector<ExpressionValidator>();
						parameter2Expression.put(par, newList);
					}
					parameter2Expression.get(par).add(
							new ExpressionValidator(attr));
				}
			}
		}

		return parameter2Expression;
	}
}
