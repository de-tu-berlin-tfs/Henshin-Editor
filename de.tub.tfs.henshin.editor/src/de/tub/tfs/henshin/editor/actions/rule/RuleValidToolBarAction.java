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

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.rule.RulePage;

/**
 * The Class RuleValidToolBarAction.
 */
public class RuleValidToolBarAction extends ValidateRuleAction {

	/**
	 * Instantiates a new rule valid tool bar action.
	 * 
	 * @param part
	 *            the part
	 * @param rulePage
	 *            the rule page
	 */
	public RuleValidToolBarAction(IWorkbenchPart part, RulePage rulePage) {
		super(part);
		this.rule = rulePage.getCastedModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.CreateNACAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return rule != null;
	}

}
