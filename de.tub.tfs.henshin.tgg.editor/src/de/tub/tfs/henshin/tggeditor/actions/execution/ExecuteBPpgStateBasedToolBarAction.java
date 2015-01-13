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
package de.tub.tfs.henshin.tggeditor.actions.execution;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tggeditor.commands.CheckOperationConsistencyCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteBDelCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteBTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteCCRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteFDelCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecutionInitCCCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecutionInitPpgDeltaBasedCCCommand;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;


/**
 * The class ExecuteCCRuleAction executes all CC Rules. The class is shown in the context menu of the
 * Tree Editor and enabled when the CC rule folder is selected and CC Rules are available. The 
 * ExecuteCCRuleCommand is used.
 * @see ExecuteCCRuleCommand
 */
public class ExecuteBPpgStateBasedToolBarAction extends ExecuteOpRulesAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.ExecuteFPpgAction";

	protected String name_CC_RULE_FOLDER;
	

	/**
	 * The list of CC {@link Rule}s in the henshin file.
	 */
	protected List<Rule> tRulesCC = new Vector<Rule>();

	/**
	 * The selected Model. Just needed to get all the graphs in the transformationsystem
	 */
	protected IndependentUnit modelCC;
	
	/**
	 * Instantiates a new execute rule tool bar rule action.
	 *
	 * @param part the part
	 * @param page the graph page
	 */
	public ExecuteBPpgStateBasedToolBarAction(MuvitorPageBookView part, GraphicalPage page) {
		super(part.getEditor());
		name_OP_RULE_FOLDER = "BTRuleFolder";
		name_CC_RULE_FOLDER = "CCRuleFolder";
		graph=page.getCastedModel();
		DESC = "[<=bPpg-S=]";
		TOOLTIP = "Propagate all changes from target to source";
		
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#run()
	 */
	@Override
	public void run() {
		EObject o =  EcoreUtil.getRootContainer( (EObject) graph);
		if (!(o instanceof Module))
			return;
		Module m = (Module) o;
		modelCC = (IndependentUnit) m.getUnit(name_CC_RULE_FOLDER);
		retrieveCCRules();
		super.run();
	}
	
	/**
	 * 
	 */
	protected void retrieveCCRules() {
		tRulesCC.clear();
		getAllRules(tRulesCC, modelCC);
	}	

	
	@Override
	protected CompoundCommand setCommand() {
		CompoundCommand cmd = new CompoundCommand();
		cmd.add(new ExecutionInitCCCommand(graph)); // initial marking
		ExecuteCCRulesCommand ccCmd =new ExecuteCCRulesCommand(graph, tRulesCC); //CC marking 
		cmd.add(ccCmd);
		cmd.add(new CheckOperationConsistencyCommand(ccCmd)); // check consistency
		cmd.add(new ExecuteBDelCommand(graph,tRulesCC)); // bDel		
		ExecuteBTRulesCommand btCmd =new ExecuteBTRulesCommand(graph, tRules); // bAdd 
		cmd.add(btCmd);
		cmd.add(new CheckOperationConsistencyCommand(btCmd)); // check consistency
		return cmd;
	}

	
}
