/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.util.SendNotify;


/**
 * The class CreateNACCommand creates tgg NAC.
 * NAC is created as Not of NestedCondition under the lhs of the rule.
 * When there are more than one NAC, it will make a tree structure.  
 * The tree structure of the Formula consists of And and Not. 
 * And can further consist of two Not or one Not and one And.
 */
public class CreateNACCommand extends Command{	
	/**
	 * The rule to which the NAC is applied. 
	 */
	private Rule rule;
	
	/**
	 * The NAC model.
	 */
	private NestedCondition nac; //NAC is NestedCondition with NOT as top.
	
	/**
	 * The graph which represents the NAC model.
	 */
	private TripleGraph nacGraph;
	
	/**
	 * The name of the NAC.
	 */
	private String name;
	
	Formula oldF;
	Not newNot;
	
	/**
	 * Constructor for a CreateNACCommand.
	 * @param rule
	 * @param name
	 */
	public CreateNACCommand(Rule rule, String name) {
		this.rule = rule;		
		this.name = name;
		this.nacGraph = TggFactory.eINSTANCE.createTripleGraph();
		this.nac = HenshinFactory.eINSTANCE.createNestedCondition();		
		this.nacGraph.setName(name);
	}

	/**
	 *  @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {		
		return name != null && rule != null ;
	}

	/**
	 * Create a NAC. 
	 * NAC is created as Not of NestedCondition under the lhs of the rule.
	 * When there are already one or more than one NAC, it will make a tree structure.  
	 * The tree structure of the formula consists of And and Not. 
	 * And can further consist of two Not or one Not and one And.
	 * The new NAC is set on the right side under the highest And.
	 * The pre-existing NACs are set on the left side under the highest And.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		nac.setConclusion(nacGraph);
		newNot = HenshinFactory.eINSTANCE.createNot();
		newNot.setChild(nac);
		//If there is already a NAC, a new Formula (AND) is created from the old NAC and the new NAC. 
		if(rule.getLhs().getFormula() != null){	
			oldF = rule.getLhs().getFormula();
			rule.getLhs().setFormula(null);
			And newF = HenshinFactory.eINSTANCE.createAnd();
			newF.setLeft(oldF);
			newF.setRight(newNot);
			rule.getLhs().setFormula(newF);
			SendNotify.sendAddFormulaNotify(rule, (EObject)newF);
		}
		else{
			rule.getLhs().setFormula(newNot);
			SendNotify.sendAddFormulaNotify(rule, (EObject)newNot);
		}			
	}

	/** 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {		
		if(oldF != null){
			rule.getLhs().setFormula(oldF);
			SendNotify.sendRemoveFormulaNotify(rule, (EObject)newNot);
		}else{
			rule.getLhs().setFormula(null);
			SendNotify.sendRemoveFormulaNotify(rule, (EObject)newNot);
		}
	}
	
}
