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
package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TGGTreeContainerEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * TreeEditPart for rules.
 */
public class RuleTreeEditPart extends AdapterTreeEditPart<Rule> implements
		IDirectEditPart {

	public RuleTreeEditPart(Rule model) {
		super(model);
		registerAdapter(model.getLhs());
	}

	@Override
	protected String getText() {
		if (getCastedModel().getName() == null)
			return "null";
		return getCastedModel().getName();
	}

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.add(getCastedModel().getLhs());
		list.add(getCastedModel().getRhs());
		
		if(getCastedModel().getLhs().getFormula() != null)
		//list.add(((NestedCondition)getCastedModel().getLhs().getFormula()).getConclusion());
		list.addAll(getAllNACs());
		list.addAll(getCastedModel().getParameters());
		list.addAll(getCastedModel().getAttributeConditions());
		return list;
	}

	
	private List<EObject> getAllNACs() {
		Rule rule = (Rule)getModel();
		List<EObject> l = new ArrayList<EObject>();
		TreeIterator<EObject> iter = rule.getLhs().getFormula().eAllContents();
		while (iter.hasNext()) {
			EObject elem = iter.next();
			
			if (elem instanceof Graph) {
				l.add(elem);
			}
		}
		return l;
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.RULE__NAME:
				refreshVisuals();
			case HenshinPackage.RULE__RHS:
			case HenshinPackage.RULE__LHS:
			case HenshinPackage.RULE__MAPPINGS:
			case HenshinPackage.FORMULA:
			case HenshinPackage.RULE__PARAMETERS:
				refreshChildren();
			default:
				break; 
		}
		refreshVisuals();
		super.notifyChanged(notification);
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.RULE__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RuleComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TGGTreeContainerEditPolicy());
	}

	/**
	 * The methode openRuleView is called by the performOpen method of NACTreeEditPart.
	 * It shows the NAC above and the rule below in the graphical editor.
	 * @param nac
	 */
	public void openRuleView(Graph nac){
		this.performOpen();
		TreeEditor editor = (TreeEditor) IDUtil.getHostEditor((Rule) getModel());
		if (editor.getRulePage((Rule) getModel())  != null)
			editor.getRulePage((Rule) getModel()).setCurrentNac((NestedCondition) nac.eContainer());
	}
	
	@Override
	protected Image getImage() {
		try {	
			return IconUtil.getIcon("rule.png");
		} catch (Exception e) {
			return null;
		}		
	}	
	
	@Override
	public void performOpen() {
		super.performOpen();
	}
}
