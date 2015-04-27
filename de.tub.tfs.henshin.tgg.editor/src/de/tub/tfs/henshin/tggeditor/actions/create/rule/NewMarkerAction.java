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
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkEdgeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleAttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


public class NewMarkerAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.NewMarkerAction";
	private EObject model;
	
	public NewMarkerAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("De/Mark As New");
		setToolTipText("Mark or demark this Node as a new Node");
	}
	
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if (selecObject instanceof EditPart) {
			EditPart editpart = (EditPart) selecObject;

			if (editpart instanceof RuleAttributeEditPart) {
				model = (Attribute) editpart.getModel();

				if(RuleUtil.graphIsOpRuleRHS(((Attribute)model).getNode().getGraph()))
					return false;
				return true;
			}
			if (editpart instanceof RuleNodeEditPart) { 
				model = (TNode) editpart.getModel();
				if(RuleUtil.graphIsOpRuleRHS(((TNode)model).getGraph()))
					return false;
				return true;
			}
			
			if (editpart instanceof RuleEdgeEditPart) {
				model = (Edge) editpart.getModel();

				if(RuleUtil.graphIsOpRuleRHS(((Edge)model).getGraph()))
					return false;
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		
		if (model instanceof Attribute) {
			Command command = new MarkAttributeCommand((Attribute)model);
			super.execute(command);
		}
		if (model instanceof TNode) {
			Command command = new MarkCommand((TNode)model);
			super.execute(command);
		}
		if (model instanceof Edge) {
			Command command = new MarkEdgeCommand((Edge)model);
			super.execute(command);
		}
	}
}
