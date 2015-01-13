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

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;


/**
 * The class GenerateBTRulesAction generates Backward-Translation-Rules from a TGG Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateBTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateBTRulesAction extends GenerateOpRulesAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRulesAction";
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateBTRulesAction(IWorkbenchPart part) {
		super(part);
		DESC = "Generate All BT_Rules";
		TOOLTIP = "Generates Backward Translation Rules for all TGG Rules";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		opRuleTypeUpperCase = "BT";
		opRuleType=RuleUtil.TGG_BT_RULE;
	}

	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		command = new GenerateBTRuleCommand((Rule)rule,container);		
	}
}
