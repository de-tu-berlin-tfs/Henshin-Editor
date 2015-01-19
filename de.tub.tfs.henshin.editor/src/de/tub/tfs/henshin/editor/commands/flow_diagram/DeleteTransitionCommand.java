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
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.emf.ecore.EStructuralFeature;

import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class DeleteTransitionCommand extends SimpleDeleteEObjectCommand {

	/**
     * 
     */
	private FlowElement src;

	/**
     * 
     */
	private FlowElement target;

	/**
     * 
     */
	private Transition model;

	/**
     * 
     */
	private EStructuralFeature outGoingFeature;

	public DeleteTransitionCommand(Transition model,
			EStructuralFeature outGoingFeature) {
		super(model);

		this.model = model;
		this.src = model.getPrevous();
		this.target = model.getNext();
		this.outGoingFeature = outGoingFeature;
	}

	/**
	 * @param model
	 */
	public DeleteTransitionCommand(Transition model) {
		this(
				model,
				model.isAlternate() ? FlowControlPackage.Literals.CONDITIONAL_ELEMENT__ALT_OUT
						: FlowControlPackage.Literals.FLOW_ELEMENT__OUT);
	}

	/**
	 * @param outGoigFeature
	 *            the outGoigFeature to set
	 */
	public void setOutGoigFeature(EStructuralFeature outGoigFeature) {
		this.outGoingFeature = outGoigFeature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		model.setPrevous(null);
		model.setNext(null);

		src.eSet(outGoingFeature, null);
		target.getIn().remove(model);

		super.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		model.setPrevous(src);
		model.setNext(target);

		src.eSet(outGoingFeature, model);
		target.getIn().add(model);

		super.undo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return src != null && target != null && model != null
				&& super.canExecute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return src != null && target != null && model != null
				&& super.canUndo();
	}
}
