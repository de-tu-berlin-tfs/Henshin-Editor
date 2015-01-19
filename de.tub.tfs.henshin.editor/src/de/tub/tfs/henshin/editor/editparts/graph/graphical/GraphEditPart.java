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
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.GraphClipboardEditPolicy;
import de.tub.tfs.henshin.editor.figure.graph.EdgeConnectionRouter;
import de.tub.tfs.henshin.editor.model.properties.graph.GraphPropertySource;
import de.tub.tfs.henshin.editor.util.HenshinCache;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class GraphEditPart.
 */
public class GraphEditPart extends AdapterGraphicalEditPart<Graph> {

	/** The name label. */
	private Label nameLabel = new Label();
	
	/**
	 * Instantiates a new graph edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public GraphEditPart(Graph model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		updateFigure();
		nameLabel = new Label();
		nameLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD)); //$NON-NLS-1$
		nameLabel.setForegroundColor(Display.getCurrent().getSystemColor(
				SWT.COLOR_GRAY));
		nameLabel.setText(getText());
		getFigure().add(nameLabel, new Rectangle(10, 10, -1, -1));
		
		HenshinCache.getInstance().init();
		super.activate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new FreeformLayer();
		layer.setLayoutManager(new FreeformLayout());
		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		cLayer.setAntialias(SWT.ON);
		EdgeConnectionRouter edgeRouter = new EdgeConnectionRouter(layer);
		if (this.getCastedModel().getEdges().size() > 1000) {
			edgeRouter.setNextRouter(ConnectionRouter.NULL);

			MessageDialog
					.open(MessageDialog.INFORMATION,
							Display.getDefault().getActiveShell(),
							"Information",
							"The graph has too many edges. Therefore the edges will not be drawn around nodes.",
							SWT.SHEET);

		}
		cLayer.setConnectionRouter(edgeRouter);

		return layer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GraphXYLayoutEditPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new GraphClipboardEditPolicy() {
					@Override
					protected boolean canCopy() {
						return false;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.addAll(getCastedModel().getNodes());

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		if (featureId == HenshinPackage.GRAPH__NAME) {
			if (getCastedModel().getName() != null) {
				nameLabel.setText(getCastedModel().getName());
			}
		}
		if (featureId ==  HenshinPackage.RULE__MAPPINGS){
			if (notification.getNotifier() instanceof Rule){
				for (Node node : this.getCastedModel().getNodes()) {
					node.eNotify(notification);
				}
			}
			refresh();
		}
		final int type = notification.getEventType();
		switch (type) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			refreshChildren();
			refreshVisuals();
			break;
		case Notification.SET:
			repaintChildren();
			refreshVisuals();
			break;
		}
	}

	/**
	 * Update figure.
	 */
	protected void updateFigure() {
		final FreeformLayer layer = (FreeformLayer) getFigure();
		getFigure().setBackgroundColor(ColorConstants.white);
		if (getSelected() == 0) { // not selected
			layer.setBorder(new LineBorder(1));
			layer.setForegroundColor(SWTResourceManager.getColor(0, 0, 0));
		} else { // selected
			layer.setBorder(new LineBorder(5));
			layer.setForegroundColor(SWTResourceManager.getColor(150, 0, 0));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshVisuals()
	 */
	@Override
	public void refreshVisuals() {
		updateFigure();
		getFigure().repaint();
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#fireSelectionChanged
	 * ()
	 */
	@Override
	protected void fireSelectionChanged() {
		super.fireSelectionChanged();
		refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new GraphPropertySource(getCastedModel());
	}

	/**
	 * @return
	 */
	protected String getText() {
		if (getCastedModel().getName() == null) {
			return new String();
		}
		return getCastedModel().getName();
	}

	/**
	 * Repaint children.
	 */
	public void repaintChildren() {
		for (Object e : getChildren()) {
			if (e instanceof NodeEditPart) {
				((NodeEditPart) e).getFigure().repaint();
			}
		}
	}
	
	@Override
	public void removeChild(EditPart child) {
		super.removeChild(child);
	}
	
	@Override
	public void addChild(EditPart child, int index) {
		super.addChild(child, index);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void refreshChildren() {
		int i;
		EditPart editPart;
		Object model;

		List children = getChildren();
		int size = children.size();
		Map modelToEditPart = Collections.EMPTY_MAP;
		if (size > 0) {
			modelToEditPart = new HashMap(size);
			for (i = 0; i < size; i++) {
				editPart = (EditPart) children.get(i);
				modelToEditPart.put(editPart.getModel(), editPart);
			}
		}

		List modelObjects = getModelChildren();
		for (i = 0; i < modelObjects.size(); i++) {
			model = modelObjects.get(i);
			
			
			if (!HenshinCache.getInstance().getRemovedEditParts().contains(model)) {
				// Do a quick check to see if editPart[i] == model[i]
				if (i < children.size()
					&& ((EditPart) children.get(i)).getModel() == model
				) {
					continue;
				}

				// Look to see if the EditPart is already around but in the
				// wrong location
				editPart = (EditPart) modelToEditPart.get(model);
				if (editPart != null) {
					reorderChild(editPart, children.indexOf(editPart));
				}
				else {
					// An EditPart for this model doesn't exist yet. Create and
					// insert one.
					editPart = createChild(model);
					addChild(editPart, size++);
				}
			}
		}

		// remove the remaining EditParts
		List<NodeEditPart> toDelete = new ArrayList<NodeEditPart>();
		
		for (Object object : children) {
			if (object instanceof NodeEditPart) {
				NodeEditPart nodeEditPart = (NodeEditPart) object;
				if (!modelObjects.contains(nodeEditPart.getCastedModel())) {
					toDelete.add(nodeEditPart);
				}
			}
		}
		
		for (NodeEditPart nodeEditPart : toDelete) {
			removeChild(nodeEditPart);
		}
	}

	@Override
	public void deactivate() {
		HenshinCache.getInstance().init();
		super.deactivate();
	}
	
}
