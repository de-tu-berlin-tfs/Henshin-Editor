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
package de.tub.tfs.henshin.tggeditor.editparts.critical;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;


public class CritPairEditPart extends GraphEditPart {

	Rule rule1;
	Rule rule2;
	Graph overlapping;
	CritPair critPair;
	
	public CritPairEditPart(CritPair model) {
		super((TripleGraph) model.getOverlapping());
		registerAdapter(model.getOverlapping());
		registerAdapter(model);
		registerAdapter(model.getRule1());
		registerAdapter(model.getRule2());
		this.critPair = model;
		this.overlapping = model.getOverlapping();
		this.rule1 = model.getRule1();
		this.rule2 = model.getRule2();
		
	}
	
	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new CritPairLayoutEditPolicy());
	}
	
	@Override
	protected void setFigureNameLabel() {
		nameLabel.setText(critPair.getOverlapping().getName());
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof CritPair) {
			final int featureId = notification.getFeatureID(HenshinPackage.class);
			switch (featureId){
				case TggPackage.CRIT_PAIR:
					setFigureNameLabel();
					refreshVisuals();
				default:
					break; 
			}
		}
		super.notifyChanged(notification);
	}

}
