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
 * SetActivityContentCommand.java
 * 
 * Created 18.12.2011 - 13:34:05
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;

/**
 * A {@link Command command} to set content of an {@link Activity activity}.
 * 
 * <p>
 * All the parameters of the new content will also be passed to the
 * {@link Activity activity}, if there are some.
 * </p>
 * 
 * @author nam
 * 
 */
public class SetActivityContentCommand extends CompoundCommand {

	/**
	 * Constructs a {@link SetActivityContentCommand} to set the content of a
	 * given {@link Activity activity}.
	 * 
	 * @param model
	 *            an {@link Activity activity} to set the new content.
	 * @param content
	 *            a new content for {@code model}.
	 */
	public SetActivityContentCommand(Activity model, NamedElement content) {
		this(model, HenshinLayoutUtil.INSTANCE.getLayout(model), content);
	}

	/**
	 * @param model
	 * @param layoutModel
	 * @param content
	 */
	public SetActivityContentCommand(Activity model,
			FlowElementLayout layoutModel, NamedElement content) {
		super("Set Activity Content");

		if (model != null) {
			FlowDiagram diagram = model.getDiagram();

			/*
			 * removes parameters of old content.
			 */
			for (Parameter param : model.getParameters()) {
				add(new DeleteActivityParameterCommand(param, diagram));
			}

			/*
			 * adds parameters of the new content.
			 */
			if (content instanceof Rule) {
				for (org.eclipse.emf.henshin.model.Parameter p : ((Rule) content)
						.getParameters()) {
					add(new CreateFlowControlParameterCommand(model,
							layoutModel, p));
				}
			} else if (content instanceof FlowDiagram) {
				if (layoutModel != null) {
					FlowDiagram d = (FlowDiagram) content;

					for (Parameter p : d.getParameters()) {
						ParameterMapping newMapping = FlowControlFactory.eINSTANCE
								.createParameterMapping();
						Parameter newActivityParameter = FlowControlFactory.eINSTANCE
								.createParameter();

						newActivityParameter.setHenshinParameter(p
								.getHenshinParameter());
						newActivityParameter.setName(p.getName());
						newActivityParameter.setProvider(model);

						if (p.isInput()) {
							newMapping.setSrc(newActivityParameter);
							newMapping.setTarget(p);
						} else {
							newMapping.setSrc(p);
							newMapping.setTarget(newActivityParameter);
						}

						add(new SimpleAddEObjectCommand<EObject, EObject>(
								newActivityParameter,
								FlowControlPackage.Literals.PARAMETER_PROVIDER__PARAMETERS,
								model));

						add(new SimpleAddEObjectCommand<EObject, EObject>(
								newMapping,
								FlowControlPackage.Literals.ACTIVITY__PARAMETER_MAPPINGS,
								model));
					}

				}
			}
		}

		add(new UpdateActivity2ParameterMappingIdCommand(model, layoutModel));

		add(new SimpleSetEFeatureCommand<Activity, NamedElement>(model,
				content, FlowControlPackage.Literals.ACTIVITY__CONTENT));
	}
}
