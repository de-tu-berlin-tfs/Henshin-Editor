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
package de.tub.tfs.henshin.editor.actions.transSys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

public class SortRulesAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.SortRuleAction";

	/** The graph. */
	private Module transformationSystem;

	public SortRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Sort rules by name");
		setImageDescriptor(ResourceUtil.ICONS.SORT.descr(16));
		setToolTipText("Sort rules by name.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof Module) {
					transformationSystem = (Module) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Unit.class) != null) {
					final EObject container = ((EContainerDescriptor) model)
							.getContainer();
					if (container instanceof Module)
						transformationSystem = (Module) container;
				}
			}
		}

		return transformationSystem != null;
	}

	@Override
	public void run() {
		EList<Rule> rules = HenshinUtil.getRules(transformationSystem);
		transformationSystem.getUnits().removeAll(rules);
		Rule[] rulesArr = new Rule[0];
		rulesArr = rules.toArray(rulesArr);
		Arrays.sort(rulesArr, new Comparator<Rule>() {
			@Override
			public int compare(Rule o1, Rule o2) {
				// TODO Auto-generated method stub
				if (o1 == o2)
					return 0;
				if (o1 == null || o1.getName() == null)
					return -1;
				if (o2 == null || o2.getName() == null)
					return 1;
				return o1.getName().toLowerCase()
						.compareTo(o2.getName().toLowerCase());
			}
		});
		// transformationSystem.eSetDeliver(false);
		rules.clear();
		transformationSystem.getUnits().addAll(Arrays.asList(rulesArr));
		// transformationSystem.eSetDeliver(true);

	}
}
