/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggEngineImpl;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The Class ExecuteRuleCommand executes the given rule on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteRuleCommand extends Command {

	/** The graph on which the rule is executed. */
	private Graph graph;

	/** The rule which will be executed. */
	private Rule rule;

	/** The assignments for the execution. */
	private Map<String, Object> assignments;

	/** The rule application providing the method apply() for execution. */
	private RuleApplication ruleApplication;

	/** The henshin graph needed from ruleApplication. */
	private TggHenshinEGraph henshinGraph;

	/** The layout system. */
	@SuppressWarnings("unused")
	private TGG layoutSystem;


	/**
	 * Instantiates a new execute rule command.
	 * 
	 * @param graph
	 *            the graph
	 * @param rule
	 *            the rule
	 * @param assignments
	 *            the assignments
	 */
	public ExecuteRuleCommand(Graph graph, Rule rule,
			Map<String, Object> assignments) {
		super();
		this.graph = graph;
		this.rule = rule;
		this.assignments = assignments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return graph != null && rule != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		henshinGraph = new TggHenshinEGraph(graph);
		EngineImpl emfEngine = new TggEngineImpl(henshinGraph);
		ruleApplication = new RuleApplicationImpl(emfEngine,henshinGraph, rule,null);
		if (assignments != null) {
			for (Entry<String, Object> entry : assignments.entrySet()) {
				ruleApplication.setParameterValue(entry.getKey(),entry.getValue());
			}
			
			
		}
		try {
			if (!ruleApplication.execute(null)) {
				MessageDialog.openError(null, "Execute Failure", 
						"The rule ["+ rule.getName() + "] couldn't be applied.");
			} else {
				layoutSystem = GraphicalNodeUtil.getLayoutSystem(graph);
				ExecuteFTRulesCommand.createNodePositions(ruleApplication, henshinGraph, 0);
				//createNodeLayouts(ruleApplication, henshinGraph, 0);
				//createEdgeLayouts();
			}
		} catch (RuntimeException ex){
			ex.printStackTrace();
			ErrorDialog.openError(Display.getDefault().getActiveShell(), "Execute Failure", "The rule ["+ rule.getName() + "] couldn't be applied.", new Status(IStatus.ERROR,MuvitorActivator.PLUGIN_ID,ex.getMessage(),ex.getCause()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ruleApplication.undo(null);
		// undoDeleteEdges();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		ruleApplication.redo(null);
		// deleteAllEdges(edges);
	}




}
