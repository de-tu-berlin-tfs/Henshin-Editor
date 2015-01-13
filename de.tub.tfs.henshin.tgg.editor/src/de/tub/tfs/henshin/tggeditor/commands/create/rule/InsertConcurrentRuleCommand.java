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
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleComparator;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
/**
 * The InsertConcurrentRuleCommand inserts a concurrent rule in the rule folder.
 * @author Gérard Kirpach
 *
 */

public class InsertConcurrentRuleCommand extends Command {

	protected IndependentUnit parentFolderUnit;
	protected TGGRule newConRule;
	protected Module transSystem;
	protected ConcurrentRuleComparator ruleComparator;

	public InsertConcurrentRuleCommand(TGGRule newConRule, IndependentUnit parentFolderUnit,
				Module transSystem, ConcurrentRuleComparator ruleComparator) {
		super();
		this.parentFolderUnit = parentFolderUnit;
		this.newConRule = newConRule;
		this.transSystem = transSystem;
		this.ruleComparator = ruleComparator;
	}

// ...
@Override
public void execute() {
	if ((parentFolderUnit == null) || (transSystem == null)) {
		return;
	}
	// set rule marker to indicate that the new concurrent rule becomes a rule of the TGG
	if(newConRule instanceof TGGRule){
		TGGRule tggRule = (TGGRule) newConRule;
		tggRule.setMarkerType(RuleUtil.TGG_RULE);
	}

	EList<Unit> subUnits = parentFolderUnit.getSubUnits();
	int position = subUnits.size() - 1;
	int folderposition = -1;
	boolean pFolderHasRules = false;
	TGGRule subRule;
	IndependentUnit subFolder;
	Boolean prior = null;
	for (Unit subUnit : subUnits) {
		prior = null;
		if (subUnit instanceof TGGRule) {
			pFolderHasRules = true;
			subRule = (TGGRule) subUnit;
			prior = ruleComparator.prior(newConRule, subRule);
		} else if (subUnit instanceof IndependentUnit){
			subFolder = (IndependentUnit) subUnit;
			prior = prior(newConRule, subFolder);
		}
		if (prior != null && prior) { // if not equal and greater
			// get position of first strictly smaller rule
			position = subUnits.indexOf(subUnit);
			break;
		}
	}
	if (!pFolderHasRules){
		position = 0;
	}
	parentFolderUnit.getSubUnits().add(position, newConRule);
	transSystem.getUnits().add(position, newConRule);
} // ...

private Boolean prior(TGGRule rule, IndependentUnit unit){
	Boolean prior = null;
	if (unit.getSubUnits().isEmpty()) return null;
	for (Unit subunit : unit.getSubUnits()){
		if (subunit instanceof TGGRule){
			prior = ruleComparator.prior(rule, (TGGRule)subunit);
		} else if (subunit instanceof IndependentUnit){
			prior = prior(rule, (IndependentUnit)subunit);
		}
		if (prior!=null && prior) return true;
	}
	return false;
}
	
}
