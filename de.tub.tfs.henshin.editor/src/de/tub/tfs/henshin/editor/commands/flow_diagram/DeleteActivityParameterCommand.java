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

 * DeleteParameterCommand.java
 *
 * Created 28.12.2011 - 12:58:31
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * A {@link Command command} to delete {@link Parameter flow control parameters}
 * .
 * 
 * @author nam
 * 
 */
public class DeleteActivityParameterCommand extends CompoundCommand {

	/**
	 * Constructs a {@link DeleteActivityParameterCommand} to delete a given
	 * {@link Parameter parameter} in a given {@link FlowDiagram flow diagram}.
	 * 
	 * @param model
	 *            a {@link Parameter parameter} to delete.
	 * @param diagram
	 *            the containing {@link FlowDiagram flow diagram} of
	 *            {@code model}.
	 */
	public DeleteActivityParameterCommand(final Parameter model,
			final FlowDiagram diagram) {
		if (model != null) {
			// deletes all mapping with model as source.
			List<ParameterMapping> allMappings = ModelUtil.getReferences(model,
					ParameterMapping.class, diagram,
					FlowControlPackage.Literals.PARAMETER_MAPPING__SRC);

			for (ParameterMapping m : allMappings) {
				add(new SimpleDeleteEObjectCommand(m));

				if (m.getTarget().getProvider() == diagram) {
					add(new DeleteFlowDiagramParameterCommand(m.getTarget()));
				}
			}

			// deletes all mapping with model as target.
			allMappings = ModelUtil.getReferences(model,
					ParameterMapping.class, diagram,
					FlowControlPackage.Literals.PARAMETER_MAPPING__TARGET);

			for (ParameterMapping m : allMappings) {
				add(new SimpleDeleteEObjectCommand(m));

				if (m.getSrc().getProvider() == diagram) {
					add(new DeleteFlowDiagramParameterCommand(m.getSrc()));
				}
			}

			add(new SimpleDeleteEObjectCommand(model));

			add(new UpdateActivity2ParameterMappingIdCommand(
					(Activity) model.getProvider()));
		}
	}

	/**
	 * @param parameter
	 */
	public DeleteActivityParameterCommand(final Parameter parameter) {
		this(parameter, ((Activity) parameter.getProvider()).getDiagram());
	}
}
