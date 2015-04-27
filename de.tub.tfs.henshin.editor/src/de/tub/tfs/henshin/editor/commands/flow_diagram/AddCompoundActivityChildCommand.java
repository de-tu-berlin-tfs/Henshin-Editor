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
 * AddCompoundActivityChildCommand.java
 *
 * Created 27.12.2011 - 22:18:17
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.Transition;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * A {@link Command command} to add a child {@link Activity activity} into a
 * given {@link CompoundActivity compound activity}.
 * 
 * @author nam
 * 
 */
public class AddCompoundActivityChildCommand extends CompoundCommand {

	/**
	 * @param element
	 * @param model
	 */
	public AddCompoundActivityChildCommand(Activity element,
			CompoundActivity model) {
		super("Add Compound Activity Content");

		for (Transition in : element.getIn()) {
			add(new DeleteTransitionCommand(in));
		}

		Transition out = element.getOut();

		if (out != null) {
			add(new DeleteTransitionCommand(out));
		}

		if (element instanceof ConditionalElement) {
			out = ((ConditionalElement) element).getAltOut();

			if (out != null) {
				add(new DeleteTransitionCommand(out));
			}
		}

		add(new SimpleDeleteEObjectCommand(element));
		add(new SimpleAddEObjectCommand<EObject, EObject>(element,
				FlowControlPackage.Literals.COMPOUND_ACTIVITY__CHILDREN, model));
	}
}
