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
 * CreateFlowControlParameterCommand.java
 *
 * Created 28.12.2011 - 17:24:29
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.Layout;

/**
 * A {@link Command command} to create {@link Parameter flow control parameters}
 * for a given {@link org.eclipse.emf.henshin.model.Parameter henshin parameter}
 * .
 * 
 * @author nam
 * 
 */
public class CreateFlowControlParameterCommand extends CompoundCommand {

	private Parameter newParameter;

	public CreateFlowControlParameterCommand(ParameterProvider parent,
			Layout parentLayout,
			org.eclipse.emf.henshin.model.Parameter henshinParam) {
		super("Add Parameter");

		newParameter = FlowControlFactory.eINSTANCE.createParameter();

		newParameter.setName(henshinParam.getName());
		newParameter.setProvider(parent);
		newParameter.setHenshinParameter(henshinParam);

		add(new SimpleAddEObjectCommand<ParameterProvider, Parameter>(
				newParameter,
				FlowControlPackage.Literals.PARAMETER_PROVIDER__PARAMETERS,
				parent));

		if (parent instanceof Activity) {
			add(new UpdateActivity2ParameterMappingIdCommand((Activity) parent,
					(FlowElementLayout) parentLayout));
		}
	}

	public CreateFlowControlParameterCommand(FlowDiagram parent,
			org.eclipse.emf.henshin.model.Parameter henshinParam) {
		this(parent, null, henshinParam);
	}

	/**
	 * @param model
	 * @param containingFeature
	 * @param parent
	 */
	public CreateFlowControlParameterCommand(Activity parent,
			org.eclipse.emf.henshin.model.Parameter henshinParam) {
		this(parent, HenshinLayoutUtil.INSTANCE.getLayout(parent), henshinParam);
	}

	/**
	 * @return the newParameter
	 */
	public Parameter getNewParameter() {
		return newParameter;
	}
}
