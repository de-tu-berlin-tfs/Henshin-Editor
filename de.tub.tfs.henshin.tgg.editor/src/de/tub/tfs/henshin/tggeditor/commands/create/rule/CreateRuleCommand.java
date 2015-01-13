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
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;


/**
 * The class CreateRuleCommand creates a new rule for a transformation system.
 */
public class CreateRuleCommand extends Command {
	
	/** transformation system in which a rule is created */
	private Module module;
	/** rule */
	private Rule rule;
	/** name of a rule to create */
//	private String name;
	/**
	 * the lhs graph
	 */
	private Graph lhs;
	/**
	 * the rhs graph
	 */
	private TripleGraph rhs;
	private IndependentUnit unit;
	
	//NEW
	public TripleGraph getRhsGraph(){
		return rhs;
	}
	
	public Graph getLhsGraph(){
		return lhs;
	}
	
	public Rule getRule(){
		return rule;
	}
	

	public CreateRuleCommand(Module module, String name,IndependentUnit unit) {
		this(module,name);
		this.unit = unit;
	}
		
	/**
	 * the constructor
	 * @param module the transformationsystem
	 * @param name the name for the rule
	 */
	public CreateRuleCommand(Module module, String name) {
		this.module = module;

		this.rule = TggFactory.eINSTANCE.createTGGRule();
//		this.name = name;
//		this.rule.setActivated(true);
		this.rule.setName(name);
		this.lhs  = TggFactory.eINSTANCE.createTripleGraph();
		this.rhs = TggFactory.eINSTANCE.createTripleGraph();
		lhs.setName("lhs");
		rhs.setName("rhs");
		rule.setLhs(lhs);
		rule.setRhs(rhs);
		// mark as original rule from the tgg
		((TGGRule) rule).setMarkerType(RuleUtil.TGG_RULE);
		((TGGRule) rule).setIsMarked(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {		
		return module != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (unit != null){
			//unit.getSubUnits().add(rule);
			EList<Unit> subUnits = unit.getSubUnits();
			int index = 0;
			for (Unit sunit :  unit.getSubUnits()){
				if (sunit instanceof Rule){
					Rule oldRule = ((Rule)sunit);
					String oldRuleName = oldRule.getName();
					if (rule.getName().contains(oldRuleName)){
						index = subUnits.indexOf(oldRule);
						break;
					}
				}
			}
			unit.getSubUnits().add(index, rule);
		}
		//module.getUnits().add(rule);
		//NEW
		EList<Unit> units = module.getUnits();
		int index = 0;
		for (Unit unit :  module.getUnits()){
			if (unit instanceof Rule){
				Rule oldRule = ((Rule)unit);
				String oldRuleName = oldRule.getName();
				if (rule.getName().contains(oldRuleName)){
					index = units.indexOf(oldRule);
					break;
				}
			}
		}
		module.getUnits().add(index, rule);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		EList<Unit> units = module.getUnits();
		int index = units.indexOf(rule);
		units.remove(index);
		if (unit != null)
			unit.getSubUnits().remove(rule);
		super.undo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return module != null && rule != null;
	}

}
