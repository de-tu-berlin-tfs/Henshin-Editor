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
 * DeleteFlowDiagramInputParameterCommand.java
 *
 * Created 25.01.2012 - 17:09:44
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class DeleteFlowDiagramInputParameterCommand extends CompoundCommand {

	public DeleteFlowDiagramInputParameterCommand(final Parameter p) {

		FlowDiagram diagram = (FlowDiagram) p.getProvider();

		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getSrc() == p) {
				add(new SimpleDeleteEObjectCommand(m));
			}
		}

		for (ParameterMapping m : ModelUtil.getReferences(p,
				ParameterMapping.class,
				FlowControlUtil.INSTANCE.getFlowControlSystem(diagram),
				FlowControlPackage.Literals.PARAMETER_MAPPING__TARGET)) {

			add(new DeleteActivityParameterCommand(m.getSrc(), diagram));
		}

		add(new SimpleDeleteEObjectCommand(p));
	}
}
