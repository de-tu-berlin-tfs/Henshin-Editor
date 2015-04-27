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
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;


/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateFTRulesAction extends GenerateOpRulesAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateFTRulesAction";

	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateFTRulesAction(IWorkbenchPart part) {
		super(part);
		DESC = "Generate All FT_Rules";
		TOOLTIP = "Generates Forward Translation Rules for all TGG Rules";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		opRuleTypeUpperCase = "FT";
		opRuleType=RuleUtil.TGG_FT_RULE;
	}
	
	@Override
	//protected void setCommand(Rule rule, IndependentUnit container) {
	// NEW SUSANN
	protected void setCommand(Rule rule, MultiUnit container) {
		command = new GenerateFTRuleCommand((Rule)rule,container);		
	}
	
}
