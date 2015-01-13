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
package de.tub.tfs.henshin.tggeditor.commands.move;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;

/**
 * The Class MoveTransformationUnitCommand.
 *
 * @author Johann
 */
public class MoveEObjectCommand extends Command {

	/** The list. */
	private EList<EObject> list;
	
	private EList<EObject> oldList;
	
	/** The index. */
	private int index;
	
	/** The new index. */
	private int newIndex;

	private EObject elem;
	
	
	/**
	 * Instantiates a new move transformation unit command.
	 *
	 * @param list the list
	 * @param oldIndex the old index
	 * @param newIndex the new index
	 */
	public MoveEObjectCommand(EList<EObject> list,EList<EObject> oldList,
			EObject elem, int newIndex) {
		super();
		this.list = list;
		this.index = oldList.indexOf(elem);
		this.newIndex=newIndex;
		this.oldList = oldList;
		this.elem = elem;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (elem instanceof IndependentUnit && (((IndependentUnit) elem).getName().equals("FTRuleFolder") 
											 || ((IndependentUnit) elem).getName().equals("RuleFolder")
											 || ((IndependentUnit) elem).getName().equals("BTRuleFolder")
										  	 || ((IndependentUnit) elem).getName().equals("CCRuleFolder")
									         || ((IndependentUnit) elem).getName().equals("ITRuleFolder")))
				return false; 
		
		return true;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (oldList == list){
			list.move(newIndex, index);
		} else {
			
			if (newIndex >= list.size() || newIndex < 0)
				list.add(elem);
			else
				list.add(newIndex, elem);
			
			if (oldList != elem.eContainer().eGet(elem.eContainmentFeature()))
				oldList.remove(elem);
			
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (oldList == list){
			list.move(index, newIndex);
		} else {
			if (oldList != elem.eContainer().eGet(elem.eContainmentFeature()))
				list.remove(elem);
			oldList.add(elem);
		}
	}
	
	
	
	
}
