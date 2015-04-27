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
package de.tub.tfs.henshin.tggeditor.actions.validate;

import java.util.List;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tggeditor.TggAggInfo;
import de.tub.tfs.henshin.tggeditor.commands.CheckForCritPairCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class CheckRuleConflictAction extends SelectionAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.validate.CheckRuleConflictAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Check Rules for Conflicts";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Check Rules for Conflicts";
	
	List<Unit> _tRules;
	
	Module _trafo;
	TGG _layoutSystem;

	public CheckRuleConflictAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof RuleFolderTreeEditPart) {
				//_trafo = (Module) editpart.getParent().getModel();
				EditPart editpart2 = editpart;
				
				while (editpart2 != editpart2.getRoot() && !(editpart2 instanceof TransformationSystemTreeEditPart))
					editpart2 = editpart2.getParent();
				_trafo = (Module) editpart2.getModel();
								
				_layoutSystem = GraphicalNodeUtil.getLayoutSystem(_trafo);
				_tRules = ((IndependentUnit)editpart.getModel()).getSubUnits(true);
				if (_tRules.isEmpty()) return false;
				return true;
			}
		}
		return false;
	}
	
	private class UnitLabelProvider extends LabelProvider{
		
		@Override
		public String getText(Object element) {
			if(element instanceof TGGRule)
				return ((TGGRule) element).getName();
			if(element instanceof IndependentUnit)
				return ((IndependentUnit) element).getName();
			return "";
		}
	}
	
	@Override
	public void run() {
		
		cleanTrafo(_trafo);
		TggAggInfo aggInfo = new TggAggInfo(_trafo);
		
		ElementListSelectionDialog firstDialog = new ElementListSelectionDialog(null, new UnitLabelProvider() );
		firstDialog.setElements(_tRules.toArray());
		firstDialog.setTitle("Rule Selection");
		firstDialog.setMessage("Select the Rule for the first parameter.");
		firstDialog.setMultipleSelection(true);
		firstDialog.open();
		Object[] firstRuleList = firstDialog.getResult();
				
		ElementListSelectionDialog secondDialog = new ElementListSelectionDialog(null, new UnitLabelProvider() );
		secondDialog.setElements(_tRules.toArray());
		secondDialog.setTitle("Rule Selection");
		secondDialog.setMessage("Select the Rule for the second parameter.");
		secondDialog.setMultipleSelection(true);
		secondDialog.open();
		Object[] secondRuleList = secondDialog.getResult();
	
		CompoundCommand commands = new CompoundCommand();
		
		if (firstRuleList != null && secondRuleList != null) {
			for (Object o1 : firstRuleList) {
				if (o1 instanceof TGGRule) {
					TGGRule rule1 = (TGGRule) o1;
					for (Object o2 : secondRuleList) {
						if (o2 instanceof TGGRule) {
							TGGRule rule2  =(TGGRule) o2;
							if (rule1 != null && rule2 != null) {
								CheckForCritPairCommand c = new CheckForCritPairCommand(rule1, rule2, aggInfo);
								commands.add(c);
							}
						}
					}
				}
			}
		}
		commands.execute();
		aggInfo.save("./", "tgg2agg.ggx");
	}
	
	private void cleanTrafo(Module trafo) {
		CompoundCommand commands = new CompoundCommand();
		for (Rule r : ModelUtil.getRules( trafo)) {
			for (Mapping m : r.getMappings()) {
				if (m.getImage() == null || m.getOrigin() == null) 
					commands.add(new SimpleDeleteEObjectCommand(m));
}
		}
		commands.execute();
	}
}
