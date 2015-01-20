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
 * SubtreeEditPart.java
 * created on 15.07.2012 00:06:41
 */
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.henshin.model.subtree.Edge;
import de.tub.tfs.henshin.model.subtree.Subtree;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * @author huuloi
 *
 */
public class SubtreeEditPart extends AdapterGraphicalEditPart<Subtree> implements NodeEditPart {
	
	private NodeLayout layoutModel;
	
	public SubtreeEditPart(Subtree model) {
		super(model);
		
		layoutModel = HenshinLayoutFactory.eINSTANCE.createNodeLayout();
		registerAdapter(layoutModel);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	protected IFigure createFigure() {
		figure = new Ellipse();
		figure.setOpaque(true);
		figure.setBackgroundColor(ColorConstants.green);
		figure.setSize(Constants.SIZE_25, Constants.SIZE_25);
		figure.setLocation(new Point(layoutModel.getX(), layoutModel.getY()));
		return figure;
	}

	public NodeLayout getLayoutModel() {
		return layoutModel;
	}

	public void setLayoutModel(NodeLayout layoutModel) {
		this.layoutModel = layoutModel;
		
		registerAdapter(layoutModel);
	}

	@Override
	protected void createEditPolicies() {
	}
	
	
	@Override
	protected void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof NodeLayout) {
			refreshLocation();
		}
	}
	
	private void refreshLocation() {
		IFigure figure = getFigure();
		figure.setLocation(new Point(getLayoutModel().getX(), getLayoutModel().getY()));
	}
	
	@Override
	protected List<Edge> getModelSourceConnections() {
		return getCastedModel().getOutgoing();
	}
	
	@Override
	protected List<Edge> getModelTargetConnections() {
		return getCastedModel().getIncoming();
	}
	
}
