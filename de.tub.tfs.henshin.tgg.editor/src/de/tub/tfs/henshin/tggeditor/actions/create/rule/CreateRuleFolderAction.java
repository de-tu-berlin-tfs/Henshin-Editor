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

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleFolderCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


public class CreateRuleFolderAction extends SelectionAction {
	
	public static final String ID = "tggeditor.actions.create.CreateRuleFolderAction";
	private Module transSys;
	private IndependentUnit unit = null;
	public CreateRuleFolderAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create a Folder");
		setToolTipText("Creates a Folder to sort Rules.");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if ((selecObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selecObject;
			if ((editpart instanceof RuleFolderTreeEditPart)) {
				unit = (IndependentUnit) editpart.getModel();
				while (editpart != editpart.getRoot() && !(editpart instanceof TransformationSystemTreeEditPart))
					editpart = editpart.getParent();
				transSys = (Module) editpart.getModel();
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		EList<Rule> rules = ModelUtil.getRules( transSys);
		int ruleNr = rules.size()+1;
		if (!rules.isEmpty()) {
			TGG tgg = GraphicalNodeUtil.getLayoutSystem(rules.get(0));
			ruleNr -= tgg.getUnits().size();
		}
		
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(), 
				"Create Folder", 
				"Name for a new Folder", 
				"Folder"+ruleNr, 
				null);
		dialog.open();
		if (dialog.getValue().startsWith("CR_")) {
			Shell shell = new Shell();
			MessageDialog.openInformation(shell, "Please choose another name", 
					"The string 'CR_' is used for automatically generated copies of rules for the critical pair analysis. " +
					"To avoid conflicts and for better overview, please choose another name for the rule not starting with 'CR_'");
			shell.dispose();
		}
		else if(dialog.getReturnCode() != Window.CANCEL){
			
			for (Rule r : rules) {
				if (dialog.getValue().equals(r.getName())) {
					Shell shell = new Shell();
					MessageDialog.openError(shell, "Folder already exists", 
							"A Rule or Folder with the name \""+r.getName()+"\" already exists.\n Please choose a different name.");
					shell.dispose();
					return;
				} 
			}
			System.out.println("Folder " + dialog.getValue() + " created in " + transSys.getName());
			Command command = new CreateRuleFolderCommand(transSys, dialog.getValue(),unit);
			execute(command);
		}
		super.run();
	}
	
	
	
	
	

}
