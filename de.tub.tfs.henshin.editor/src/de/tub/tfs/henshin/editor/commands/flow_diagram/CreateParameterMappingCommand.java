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
 * CreateParameterMappingCommand.java
 *
 * Created 26.12.2011 - 12:17:23
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;

/**
 * A {@link Command command} to create {@link ParameterMapping mappings} between
 * two parameters in a {@link FlowDiagram flow diagram}.
 * 
 * @author nam
 * 
 */
public class CreateParameterMappingCommand extends CompoundCommand {
	private Parameter src;

	private Parameter target;

	private ParameterProvider srcProvider;

	private ParameterProvider targetProvider;

	private EObject container;

	private ParameterMapping newMapping;

	private void init() {
		if (src != null && target != null && newMapping != null
				&& container != null) {
			srcProvider = src.getProvider();
			targetProvider = target.getProvider();

			newMapping.setSrc(src);
			newMapping.setTarget(target);

			if (container instanceof FlowDiagram) {
				add(new SimpleAddEObjectCommand<FlowDiagram, ParameterMapping>(
						newMapping,
						FlowControlPackage.Literals.FLOW_DIAGRAM__PARAMETER_MAPPINGS,
						(FlowDiagram) container));
			} else if (container instanceof Activity) {
				add(new SimpleAddEObjectCommand<Activity, ParameterMapping>(
						newMapping,
						FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS,
						(Activity) container));
			}
		}
	}

	/**
	 * @param newMapping
	 * @param src
	 * @param diagram
	 */
	public CreateParameterMappingCommand(ParameterMapping newMapping,
			Parameter src, FlowDiagram diagram) {
		this(newMapping, src, null, diagram);
	}

	public CreateParameterMappingCommand(final Parameter src,
			final Parameter target, EObject container) {
		this(FlowControlFactory.eINSTANCE.createParameterMapping(), src,
				target, container);
	}

	/**
	 * @param newMapping
	 * @param src
	 * @param target
	 * @param container
	 */
	public CreateParameterMappingCommand(ParameterMapping newMapping,
			Parameter src, Parameter target, EObject container) {
		super("Create Parameter Mapping");

		this.newMapping = newMapping;
		this.src = src;
		this.target = target;
		this.container = container;

		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		boolean sameProvider = false;
		boolean mappingExisted = false;

		if (srcProvider != null && targetProvider != null) {
			sameProvider = srcProvider == targetProvider;
		}

		Collection<ParameterMapping> currMappings;

		if (container instanceof FlowDiagram) {
			currMappings = ((FlowDiagram) container).getParameterMappings();
		} else {
			currMappings = ((Activity) container).getParameterMappings();
		}

		for (ParameterMapping m : currMappings) {
			if (m.getSrc() == src && m.getTarget() == target) {
				mappingExisted = true;

				break;
			}
		}

		return super.canExecute() && src != target && !sameProvider
				&& !mappingExisted;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(Parameter target) {
		this.target = target;

		init();
	}

	/**
	 * @return the src
	 */
	public Parameter getSrc() {
		return src;
	}
}
