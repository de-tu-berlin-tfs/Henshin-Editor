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
 * UnNestActivityCommand.java
 *
 * Created 30.12.2011 - 17:46:42
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class UnNestActivityCommand extends CompoundCommand {

	/**
	 * @param model
	 */
	public UnNestActivityCommand(Activity model) {
		super("Detach Activity");

		if (model != null) {
			if (model.eContainer() instanceof CompoundActivity) {
				EObject newContainer = model.eContainer().eContainer();

				add(new SimpleDeleteEObjectCommand(model));

				if (newContainer instanceof FlowDiagram) {
					add(new SimpleAddEObjectCommand<EObject, EObject>(model,
							FlowControlPackage.Literals.FLOW_DIAGRAM__ELEMENTS,
							newContainer));
				} else {
					add(new SimpleAddEObjectCommand<EObject, EObject>(
							model,
							FlowControlPackage.Literals.COMPOUND_ACTIVITY__CHILDREN,
							newContainer));
				}
			}
		}
	}
}
