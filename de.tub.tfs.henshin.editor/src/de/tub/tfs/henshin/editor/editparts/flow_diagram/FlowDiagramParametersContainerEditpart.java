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
 * FlowDiagramParametersContainerEditpart.java
 *
 * Created 24.12.2011 - 21:26:37
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.swt.SWT;

import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.ParameterEditPart;
import de.tub.tfs.henshin.editor.figure.flow_diagram.FlowDiagramParameterMappingsConnectionRouter;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * @author nam
 * 
 */
public class FlowDiagramParametersContainerEditpart extends
		AdapterGraphicalEditPart<FlowDiagram> {

	/**
	 * @param model
	 */
	public FlowDiagramParametersContainerEditpart(FlowDiagram model) {
		super(model);

		for (FlowElement e : model.getElements()) {
			if (e instanceof Activity) {
				deepRegisterAdapters((Activity) e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		final FreeformLayer fig = new FreeformLayer();

		GridLayout gridLayout = new GridLayout(1, false);

		gridLayout.verticalSpacing = 20;

		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);

		FanRouter router = new FanRouter();

		router.setNextRouter(new FlowDiagramParameterMappingsConnectionRouter(
				fig));

		cLayer.setAntialias(SWT.ON);
		cLayer.setConnectionRouter(router);

		fig.setLayoutManager(gridLayout);

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Parameter> getModelChildren() {
		List<Parameter> children = new ArrayList<Parameter>();
		FlowDiagram model = getCastedModel();

		for (FlowElement e : model.getElements()) {
			if (e instanceof Activity) {
				deepGetParameters(children, (Activity) e);
			}
		}

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#notifyChanged
	 * (org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		int msgId = notification.getFeatureID(FlowControlPackage.class);
		int msgType = notification.getEventType();

		switch (msgId) {
		case FlowControlPackage.FLOW_DIAGRAM__ELEMENTS:
			if (msgType == Notification.ADD
					&& notification.getNewValue() instanceof Activity) {
				deepRegisterAdapters((Activity) notification.getNewValue());
			}
			break;

		default:
			break;
		}

		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		super.refreshChildren();

		for (Object child : getChildren()) {
			((EditPart) child).refresh();
		}

		sortChildren();
	}

	/**
	 * 
	 */
	private void sortChildren() {
		@SuppressWarnings("unchecked")
		List<Object> children = new ArrayList<Object>(getChildren());

		Collections.sort(children, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				ParameterEditPart p0 = (ParameterEditPart) o1;
				ParameterEditPart p1 = (ParameterEditPart) o2;

				return Integer.valueOf(p0.get2NodeMappingID()).compareTo(
						Integer.valueOf(p1.get2NodeMappingID()));
			}
		});

		for (int i = 0; i < children.size(); i++) {
			reorderChild((EditPart) children.get(i), i);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
	}

	/**
	 * @param parameters
	 * @param a
	 */
	private void deepGetParameters(List<Parameter> parameters, Activity a) {
		parameters.addAll(a.getParameters());

		TreeIterator<EObject> it = a.eAllContents();

		while (it.hasNext()) {
			EObject child = it.next();

			if (child instanceof Activity) {
				parameters.addAll(((Activity) child).getParameters());
			}
		}
	}

	/**
	 * @param a
	 */
	private void deepRegisterAdapters(Activity a) {
		registerAdapter(a);

		TreeIterator<EObject> it = a.eAllContents();

		while (it.hasNext()) {
			EObject child = it.next();

			if (child instanceof Activity) {
				registerAdapter(child);

				if (((Activity) child).getContent() != null) {
					registerAdapter(((Activity) child).getContent());
				}
			}
		}
	}
}
