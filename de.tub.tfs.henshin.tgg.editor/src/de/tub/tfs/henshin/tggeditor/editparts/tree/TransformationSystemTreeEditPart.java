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
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CheckedRulePairFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolder;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class TransformationSystemTreeEditPart extends AdapterTreeEditPart<TGG> {

	private CheckedRulePairFolder checkedRulePairFolder;
	private ImportFolder importFolder;
	private GraphFolder graphFolder;




	public TransformationSystemTreeEditPart(TGG model) {
		super(model);
	}
	
	@Override
	protected String getText() {
		//return getCastedModel().getName();
		return "Transformation System";
	}
	
	@Override
	protected List<EObject> getModelChildren() {
		if (checkedRulePairFolder == null)
			checkedRulePairFolder = new CheckedRulePairFolder(getCastedModel());
		else
			checkedRulePairFolder.update();
		if (importFolder == null)
			importFolder = new ImportFolder(getCastedModel());
		else 
			importFolder.update();
		if (graphFolder == null)
			graphFolder = new GraphFolder((TGG)getCastedModel());
		else
			graphFolder.update();
		
		List<EObject> list = new ArrayList<EObject>();
		
		list.add(importFolder);
		list.add(graphFolder);
		
		//list.add(new RuleFolder(getCastedModel()));			
		//FTRules ftRules = new FTRules(getCastedModel());

		//list.add(new FTRuleFolder(getCastedModel()));

		/*
		List<Unit> l = new LinkedList<Unit>();
		HashSet<IndependentUnit> folders = new HashSet<IndependentUnit>();
		for (Unit unit : getCastedModel().getUnits()) {
			if (unit instanceof IndependentUnit){
				l.addAll(((IndependentUnit) unit).getSubUnits());
				folders.add((IndependentUnit) unit);
			}		
			
		}
		*/		
		// NEW SUSANN
		List<Unit> l = new LinkedList<Unit>();
		HashSet<MultiUnit> folders = new HashSet<MultiUnit>();
		for (Unit unit : getCastedModel().getUnits()) {
			if (unit instanceof MultiUnit){
				l.addAll(((MultiUnit) unit).getSubUnits());
				folders.add((MultiUnit) unit);
			}		
			
		}		
		list.addAll(getCastedModel().getUnits());
		list.removeAll(l);
		list.add(checkedRulePairFolder);

		return list;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		if(notification.getEventType() == 9)
			return ;
		
		if (notification.getNotifier() != this.getCastedModel()){
			sortRulesIntoCategories(getCastedModel());
			for (Unit folder : getCastedModel().getUnits()) {
				//if (folder instanceof IndependentUnit && notification.getNotifier() != folder){
				// NEW SUSANN
				if (folder instanceof MultiUnit && notification.getNotifier() != folder){
					folder.eNotify(notification);
				}
			}
			refresh();
			return;
		}
		
		switch (featureId){
			
			case HenshinPackage.MODULE__IMPORTS:
				importFolder.update();
			case HenshinPackage.MODULE__INSTANCES:
				refreshChildren();
				break;
			case HenshinPackage.MODULE__UNITS:
				sortRulesIntoCategories(getCastedModel());
				for (Unit folder : getCastedModel().getUnits()) {
					//if (folder instanceof IndependentUnit){
					// NEW SUSANN
					if (folder instanceof MultiUnit){
						folder.eNotify(notification);
					}
				}
				refreshChildren();
				break;
			case HenshinPackage.MODULE__NAME:
				refreshVisuals();
				break;
			case HenshinPackage.MODULE__DESCRIPTION:
				break;

			default:
				// check that the TGGLayout of the transformation system is present (e.g. can disappear when another editor is in use)
				if (GraphicalNodeUtil.getLayoutSystem(getCastedModel()) != null)
				refresh();
				break;
		}
	}
	
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("transformationsystem18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}

	
	
	
	public static void sortRulesIntoCategories(Module module){
		module.eSetDeliver(false);
		
		
		Unit ruleFolder = module.getUnit("unmarked Rules");
		if (ruleFolder instanceof IndependentUnit){
			if (((IndependentUnit) ruleFolder).getSubUnits().isEmpty())
				module.getUnits().remove(ruleFolder);
		}
		ruleFolder = module.getUnit("RuleFolder");
		if (!(ruleFolder instanceof IndependentUnit)){
			if (ruleFolder != null){
				ruleFolder.setName("Rule_" + ruleFolder.getName());
			} 
			ruleFolder = HenshinFactory.eINSTANCE.createIndependentUnit();
			ruleFolder.setName("RuleFolder");
			ruleFolder.setDescription("ruleFolder.png");
			module.getUnits().add(ruleFolder);
		}
		//NEW
		ruleFolder = module.getUnit("ITRuleFolder");
		if (!(ruleFolder instanceof IndependentUnit)){
			if (ruleFolder != null){
				ruleFolder.setName("ITRule_" + ruleFolder.getName());
			} 
			ruleFolder = HenshinFactory.eINSTANCE.createIndependentUnit();
			ruleFolder.setName("ITRuleFolder");
			ruleFolder.setDescription("ITRules.png");
			module.getUnits().add(ruleFolder);
		}//NEW end
		ruleFolder = module.getUnit("FTRuleFolder");
		if (!(ruleFolder instanceof IndependentUnit)){
			if (ruleFolder != null){
				ruleFolder.setName("FTRule_" + ruleFolder.getName());
			} 
			ruleFolder = HenshinFactory.eINSTANCE.createIndependentUnit();
			ruleFolder.setName("FTRuleFolder");
			ruleFolder.setDescription("FTRules.png");
			module.getUnits().add(ruleFolder);
		}
		ruleFolder = module.getUnit("BTRuleFolder");
		if (!(ruleFolder instanceof IndependentUnit)){
			if (ruleFolder != null){
				ruleFolder.setName("BTRule_" + ruleFolder.getName());
			} 
			ruleFolder = HenshinFactory.eINSTANCE.createIndependentUnit();
			ruleFolder.setName("BTRuleFolder");
			ruleFolder.setDescription("BTRules.png");
			module.getUnits().add(ruleFolder);
		}
		ruleFolder = module.getUnit("CCRuleFolder");
		if (!(ruleFolder instanceof IndependentUnit)){
			if (ruleFolder != null){
				ruleFolder.setName("CCRule_" + ruleFolder.getName());
			} 
			ruleFolder = HenshinFactory.eINSTANCE.createIndependentUnit();
			ruleFolder.setName("CCRuleFolder");
			ruleFolder.setDescription("CCRules.png");
			module.getUnits().add(ruleFolder);
		}
		
		HashSet<Unit> ignored = new HashSet<Unit>();
		
		for (Unit unit : module.getUnits()) {
			//if (unit instanceof IndependentUnit){
			// NEW SUSANN
			if (unit instanceof MultiUnit){
				ignored.addAll(unit.getSubUnits(true));
			}
		}
		
		Iterator<Unit> unitIter = module.getUnits().iterator();
		while (unitIter.hasNext()) {
			Unit unit = unitIter.next();
			if (ignored.contains(unit))
				continue;
			//if (!(unit instanceof IndependentUnit)){
			// NEW SUSANN
			if (!(unit instanceof MultiUnit)){
				ruleFolder = null;
				if (unit instanceof Rule){
					if (((TGGRule) unit).getMarkerType() == null){
						ruleFolder = module.getUnit("unmarked Rules");
						//if (!(ruleFolder instanceof IndependentUnit)){
						// NEW SUSANN
						if (!(ruleFolder instanceof MultiUnit)){
							if (ruleFolder != null){
								ruleFolder.setName("Rule_" + ruleFolder.getName());
							} 
							ruleFolder = HenshinFactory.eINSTANCE.createIndependentUnit();
							ruleFolder.setName("unmarked Rules");
							ruleFolder.setDescription("rule.png");
							module.getUnits().add(ruleFolder);
							unitIter = module.getUnits().iterator();
						}
					} else if (((TGGRule) unit).getMarkerType().equals(RuleUtil.TGG_RULE)){
						ruleFolder = module.getUnit("RuleFolder");
						
					} else if (((TGGRule) unit).getMarkerType().equals(RuleUtil.TGG_FT_RULE)){
						ruleFolder = module.getUnit("FTRuleFolder");
						
					} else if (((TGGRule) unit).getMarkerType().equals(RuleUtil.TGG_BT_RULE)){
						ruleFolder = module.getUnit("BTRuleFolder");
					} else if (((TGGRule) unit).getMarkerType().equals(RuleUtil.TGG_CC_RULE)){
						ruleFolder = module.getUnit("CCRuleFolder");
					//NEW
					}else if (((TGGRule) unit).getMarkerType().equals(RuleUtil.TGG_IT_RULE)){
						ruleFolder = module.getUnit("ITRuleFolder");
					}//NEW end
					//if (!((IndependentUnit)ruleFolder).getSubUnits().contains(unit))
					//	((IndependentUnit)ruleFolder).getSubUnits().add(unit);
					// NEW SUSANN
					if (!((MultiUnit)ruleFolder).getSubUnits().contains(unit))
						((MultiUnit)ruleFolder).getSubUnits().add(unit);
					
				}
			}				


		}
		module.eSetDeliver(true);
	}

}
