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
package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateParameterAndRenameNodeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateParameterCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.ParameterUtil;


public class RuleNodeXYLayoutEditPolicy extends NodeLayoutEditPolicy implements
		EditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Attribute) {
			Node node = (Node) getHost().getModel();
			if (node.getType() != null) {
				if ((node.getType().getEAllAttributes().size()
						- node.getAttributes().size() > 0))
					command = new CreateRuleAttributeCommand(node,"New Attribute");
			}
		}
		if (request.getNewObject() instanceof Mapping) {
			Node rhsnode = (Node) getTargetEditPart(request).getModel();
			Mapping newMapping = (Mapping) request.getNewObject();
			command = new MarkCommand(newMapping, rhsnode);
		}
		if (request.getNewObject() instanceof Parameter) {
			Node node = (Node) getHost().getModel();
			Rule rule = (Rule) node.getGraph().eContainer();

			if (node.getName()!=null && !node.getName().isEmpty()){
				if (ParameterUtil.getParameter(node)!=null){
					return null;
				}
				command = new CreateParameterCommand(rule,node.getName());
			}
			else{
				command = new CreateParameterAndRenameNodeCommand(rule,node, "");
			}
		}
		return command;
	}

}
