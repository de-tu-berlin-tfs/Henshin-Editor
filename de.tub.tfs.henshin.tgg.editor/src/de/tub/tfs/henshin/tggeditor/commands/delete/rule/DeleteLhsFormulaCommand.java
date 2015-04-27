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
package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteNACCommand deletes a NAC.
 * When executed it makes all the needed changes in the tree structure of 
 * the Formula in the lhs of the rule.
 */
public class DeleteLhsFormulaCommand extends CompoundCommand {
	/**
	 * nested condition nc
	 */
	private Formula nc;
	
	/**
	 * Constructor
	 * @param nc
	 */
	public DeleteLhsFormulaCommand(Formula nc) {
		
		// TODO: handle further types of NACs and undo
		this.nc = nc;
		if (nc instanceof Not) {
		Graph nac = ((NestedCondition) ((Not)nc).getChild() ).getConclusion();
		add(new DeleteNACCommand(nac));
		add(new SimpleDeleteEObjectCommand(nc));
		}
		if (nc instanceof And) {
			Formula fLeft = ((And)nc).getLeft();
			Formula fRight = ((And)nc).getRight(); 
			if( (fLeft instanceof Not) && (fRight instanceof Not) )
			{
				Graph nacLeft = ((NestedCondition) ((Not)fLeft).getChild()).getConclusion();
				Graph nacRight = ((NestedCondition) ((Not)fRight).getChild()).getConclusion();
				add(new DeleteNACCommand(nacLeft));
				add(new DeleteNACCommand(nacRight));
				add(new SimpleDeleteEObjectCommand(nc));
			}
		}
	}

	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (nc != null);
	}

//	/**
//	 * 
//	 */
//	@Override
//	public void execute() {
//		super.execute();
//	}

//	@Override
//	public boolean canUndo() {
//		return super.canUndo();
//	}

//	/**
//	 * @see org.eclipse.gef.commands.Command#redo()
//	 */
//	@Override
//	public void redo() {
//		super.redo();
//	}

//	/**
//	 * @see org.eclipse.gef.commands.Command#undo()
//	 */
//	@Override
//	public void undo() {
//		super.undo();
//	}
	

	
	
}