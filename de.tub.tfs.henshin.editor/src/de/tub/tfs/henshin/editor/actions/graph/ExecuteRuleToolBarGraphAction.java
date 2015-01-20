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
package de.tub.tfs.henshin.editor.actions.graph;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.actions.rule.ExecuteRuleAction;
import de.tub.tfs.henshin.editor.ui.graph.GraphPage;

/**
 * The Class ExecuteRuleToolBarGraphAction.
 */
public class ExecuteRuleToolBarGraphAction extends ExecuteRuleAction {

	/**
	 * Instantiates a new execute rule tool bar graph action.
	 * 
	 * @param part
	 *            the part
	 * @param graphPage
	 *            the graph page
	 */
	public ExecuteRuleToolBarGraphAction(IWorkbenchPart part,
			GraphPage graphPage) {
		super(part);
		graph = graphPage.getCastedModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.CreateNACAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.rule.ExecuteRuleAction#run()
	 */
	@Override
	public void run() {
		rule = null;
		super.run();
	}

}
