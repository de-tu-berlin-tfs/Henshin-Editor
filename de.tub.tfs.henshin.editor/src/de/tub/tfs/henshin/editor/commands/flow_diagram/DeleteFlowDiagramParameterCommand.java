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
 * DeleteFlowDiagramParameterCommand.java
 *
 * Created 25.01.2012 - 17:40:14
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class DeleteFlowDiagramParameterCommand extends CompoundCommand {
	/**
     * 
     */
	public DeleteFlowDiagramParameterCommand(final Parameter model) {
		FlowDiagram diagram = (FlowDiagram) model.getProvider();

		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getSrc() == model || m.getTarget() == model) {
				add(new SimpleDeleteEObjectCommand(m));
			}
		}

		FlowControlSystem root = FlowControlUtil.INSTANCE
				.getFlowControlSystem(diagram);
		TreeIterator<EObject> it = root.eAllContents();

		while (it.hasNext()) {
			EObject next = it.next();

			if (next instanceof Activity) {
				for (ParameterMapping m : ((Activity) next)
						.getParameterMappings()) {
					if (m.getSrc() == model || m.getTarget() == model) {
						add(new SimpleDeleteEObjectCommand(m));

						add(new DeleteActivityParameterCommand(
								m.getSrc() == model ? m.getTarget()
										: m.getSrc()));
					}
				}
			}
		}

		add(new SimpleDeleteEObjectCommand(model));
	}
}
