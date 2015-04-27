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
package de.tub.tfs.henshin.editor.commands.transformation_unit.parameter;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowControlParameterCommand;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;

/**
 * A {@link Command command} to create {@link Parameter henshin parameters}.
 * 
 * <p>
 * For all {@link Activity activities} in the flow control system, a
 * {@link de.tub.tfs.henshin.model.flowcontrol.Parameter flow control parameter}
 * will also be created and added to the proper parameter list, if the content
 * of the appropriate {@link Activity activity} is the same as the containing
 * {@link Rule rule} of the given {@link Parameter parameter}.
 * </p>
 * 
 * @author Johann, nam
 */
public class CreateParameterCommand extends CompoundCommand {

	/**
	 * The containing {@link Unit transformation unit} of the new
	 * {@link Parameter parameter}.
	 */
	private Unit transformationUnit;

	/**
	 * A new {@link Parameter} to be created.
	 */
	protected Parameter parameter;

	/**
	 * @param parameter
	 * @param transformationUnit
	 * @param name
	 */
	public CreateParameterCommand(Parameter parameter,
			final Unit transformationUnit, final String name) {
		super("Create Parameter");

		this.parameter = parameter;
		this.transformationUnit = transformationUnit;

		parameter.setName(name);

		FlowControlSystem flowSystem = FlowControlUtil.INSTANCE
				.getFlowControlSystem(transformationUnit);

		if (flowSystem != null) {
			TreeIterator<EObject> it = flowSystem.eAllContents();

			while (it.hasNext()) {
				EObject eObject = (EObject) it.next();

				if (eObject instanceof Activity) {
					Activity a = (Activity) eObject;

					if (a.getContent() == transformationUnit) {
						add(new CreateFlowControlParameterCommand(a, parameter));
					}
				}
			}
		}

		add(new SimpleAddEObjectCommand<Unit, Parameter>(
				parameter,
				HenshinPackage.Literals.UNIT__PARAMETERS,
				transformationUnit));
	}

	/**
	 * Instantiates a new creates the parameter command.
	 * 
	 * @param transformationUnit
	 *            the transformation unit
	 * @param name
	 *            the name
	 */
	public CreateParameterCommand(final Unit transformationUnit,
			final String name) {
		this(HenshinFactory.eINSTANCE.createParameter(), transformationUnit,
				name);
	}

	/**
	 * Constructs a {@link CreateParameterCommand}.
	 * 
	 * @param transformationUnit
	 *            the transformation unit
	 * @param parameter
	 *            the parameter
	 */
	public CreateParameterCommand(final Unit transformationUnit,
			Parameter parameter) {
		this(parameter, transformationUnit, "");
	}

	/**
	 * @return the parameter
	 */
	public Parameter getParameter() {
		return parameter;
	}

	/**
	 * @return the transformationUnit
	 */
	public Unit getTransformationUnit() {
		return transformationUnit;
	}

}
