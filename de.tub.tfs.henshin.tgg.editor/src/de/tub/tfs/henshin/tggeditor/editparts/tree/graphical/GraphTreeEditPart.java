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
package de.tub.tfs.henshin.tggeditor.editparts.tree.graphical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TGGTreeContainerEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;


public class GraphTreeEditPart extends AdapterTreeEditPart<TripleGraph> implements IDirectEditPart {
	
	public GraphTreeEditPart(TripleGraph model) {
		super(model);
		if (this.getCastedModel().getRule() != null){
			hideTree = true;
		}
	}
	
	protected boolean hideTree = false;
	
	@Override
	protected String getText() {
		if (getCastedModel() == null)
			return "";
		return getCastedModel().getName();
	}

	@Override
	protected List<EObject> getModelChildren() {
		if (hideTree)
			return Collections.EMPTY_LIST;
		List<EObject> list = new ArrayList<EObject>();
		list.addAll(getCastedModel().getNodes());
		list.addAll(getCastedModel().getEdges());
		if (list.size() > 5000)
			return Collections.EMPTY_LIST;
		return list;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.GRAPH__NAME:
				refreshVisuals();
			case HenshinPackage.GRAPH__NODES:
			case HenshinPackage.GRAPH__EDGES:
			case HenshinPackage.GRAPH__FORMULA:
				//long s = System.nanoTime();
				refreshChildren();
				//System.out.println("graph update time: " + ((System.nanoTime() - s)/1000000) + " ms");
			default:
				break; 
		}
		refreshVisuals();
		//super.notifyChanged(notification);
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.GRAPH__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GraphComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TGGTreeContainerEditPolicy());	
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("graph18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void performOpen() {
		hideTree = !hideTree;
		refresh();
		if (getCastedModel().getRule() == null)
			super.performOpen();
		else {
			if (this.widget instanceof TreeItem) {
				TreeItem item = (TreeItem) this.widget;
				item.setExpanded(!item.getExpanded());	
			}
		}
	}
	
}
