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
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteActivityParameterCommand;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteParameterCommand.
 * 
 * @author Johann
 */
public class DeleteParameterCommand extends CompoundCommand {

	/**
	 * Constructs a {@link DeleteParameterCommand} for a given {@link Parameter
	 * parameter}.
	 * 
	 * @param parameter
	 *            the {@link Parameter parameter} to be deleted.
	 */
	public DeleteParameterCommand(Parameter parameter) {
		super("Delete Parameter '" + parameter.getName() + "'");

		Unit transformationUnit = parameter.getUnit();

		FlowControlSystem flowSystem = FlowControlUtil.INSTANCE
				.getFlowControlSystem(transformationUnit);

		TreeIterator<EObject> it = flowSystem.eAllContents();

		/*
		 * Deletes all flow control parameters with same name.
		 */
		while (it.hasNext()) {
			EObject eObject = (EObject) it.next();

			if (eObject instanceof Activity) {
				Activity a = (Activity) eObject;

				if (a.getContent() == transformationUnit) {
					for (de.tub.tfs.henshin.model.flowcontrol.Parameter p : a
							.getParameters()) {
						if (p.getHenshinParameter() == parameter) {
							add(new DeleteActivityParameterCommand(p));
						}
					}
				}
			}
		}

		/*
		 * Deletes references in LHS and RHS (attribute values, node names
		 * etc...), if the containing Trafo-Unit is a rule.
		 */
		if (transformationUnit instanceof Rule) {
			Rule rule = (Rule) transformationUnit;

			deleteParameterInGraph(parameter, rule.getLhs());
			deleteParameterInGraph(parameter, rule.getRhs());
		}

		for (Unit tUnit : HenshinUtil.INSTANCE
				.getTransformationSystem(parameter).getUnits()) {
			for (ParameterMapping parameterMapping : tUnit
					.getParameterMappings()) {
				if (parameterMapping.getSource() == parameter
						|| parameterMapping.getTarget() == parameter) {
					add(new DeletePortMappingCommand(parameterMapping));
				}
			}
		}

		add(new SimpleDeleteEObjectCommand(parameter));
	}

	/**
	 * @param p
	 * @param g
	 */
	private void deleteParameterInGraph(Parameter p, Graph g) {
		String parameterName = p.getName();

		for (Node n : g.getNodes()) {
			for (Attribute a : n.getAttributes()) {
				if (parameterName.equals(a.getValue())) {
					add(new SetEObjectFeatureValueCommand(a, "",
							HenshinPackage.ATTRIBUTE__VALUE));
				}
			}

			if (parameterName.equals(n.getName())) {
				add(new SetEObjectFeatureValueCommand(n, "",
						HenshinPackage.NAMED_ELEMENT__NAME));
			}
		}
	}
}
