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
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.editor.editparts.flow_diagram.FlowDiagramParametersContainerEditpart;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.compound_activity.CompoundActivityEditPart;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.flowcontrol.Transition;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * @author nam
 * 
 */
public class FlowDiagramEditpartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof FlowDiagram) {
			return new FlowDiagramEditpart((FlowDiagram) model);
		}

		else if (model instanceof Rule) {
			return new ActivityRuleEditPart((Rule) model);
		}

		else if (model instanceof ConditionalActivity) {
			return new ConditionalActivityEditpart((ConditionalActivity) model);
		}

		else if (model instanceof CompoundActivity) {
			return new CompoundActivityEditPart((CompoundActivity) model);
		}

		else if (model instanceof Activity) {
			return new ActivityEditPart<Activity>((Activity) model);
		}

		else if (model instanceof Start) {
			return new StartEditpart((Start) model);
		}

		else if (model instanceof End) {
			return new StopEditpart((End) model);
		}

		else if (model instanceof Transition) {
			return new TransitionEditpart((Transition) model);
		}

		else if (model instanceof Parameter) {
			return new ParameterEditPart((Parameter) model);
		}

		else if (model instanceof EContainerDescriptor) {
			return new FlowDiagramParametersContainerEditpart(
					(FlowDiagram) ((EContainerDescriptor) model).getContainer());
		}

		else if (model instanceof ParameterMapping) {
			return new ParameterMappingEditpart((ParameterMapping) model);
		}

		Assert.isTrue(model == null,
				"Could not create editpart for the given model: " + model);

		return null;
	}
}
