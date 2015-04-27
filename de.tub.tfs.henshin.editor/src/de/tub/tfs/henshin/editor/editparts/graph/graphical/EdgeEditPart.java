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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.SimpleEndpointEditPartPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.EdgeClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.EdgeComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.graph.EdgePropertySource;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * The Class EdgeEditPart.
 */
public class EdgeEditPart extends AdapterConnectionEditPart<Edge> {

	/** The label. */
	private Label label;
	
	private PolylineConnection pLine;
	protected Color FG_COLOR=ColorConstants.buttonDarkest;
	private static final Color GREY = new Color(null,240,240,240);
	protected Color BG_COLOR=GREY;
	
	private boolean collapsing = false;

	/**
	 * Instantiates a new edge edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public EdgeEditPart(Edge model) {
		super(model);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		pLine = new PolylineConnection() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
			 */
			@Override
			public void paint(Graphics graphics) {
				graphics.setAlpha(255);
				super.paint(graphics);
			}
		};
		label = new Label("");
		updateLabel();
		
		
		//label.setFont(TEXT_FONT);
		label.setOpaque(true);
		label.setForegroundColor(FG_COLOR);
		label.setBackgroundColor(GREY);
		pLine.add(label, new MidpointLocator(pLine, 0));
		Color lineColor = FG_COLOR;
		pLine.setForegroundColor(lineColor);
		updateDeco(pLine);

		return pLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#getFigure()
	 */
	@Override
	public IFigure getFigure() {
		IFigure figure = super.getFigure();
		updateLabel();
		updateDeco(figure);
		return figure;
	}

	/**
	 * Update label.
	 */
	private void updateLabel() {
		if (getCastedModel().getType() != null && !collapsing) {
			label.setText(getCastedModel().getType().getName());
		}
	}

	/**
	 * Update deco.
	 * 
	 * @param figure
	 *            the figure
	 */
	private void updateDeco(IFigure figure) {
		if (getCastedModel().getType() != null) {
			if (getCastedModel().getType().isContainment()) {
				PolygonDecoration decoration = new PolygonDecoration();
				decoration.setTemplate(new PointList(new int[] { 0, 0, -1, 1,
						-2, 0, -1, -1 }));
				PolylineConnection Pline = (PolylineConnection) figure;
				Pline.setTargetDecoration(null);
				Pline.setSourceDecoration(decoration);
			} else {
				PolygonDecoration decoration = new PolygonDecoration();
				decoration.setTemplate(PolygonDecoration.TRIANGLE_TIP);
				PolylineConnection Pline = (PolylineConnection) figure;
				Pline.setSourceDecoration(null);
				Pline.setTargetDecoration(decoration);

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractConnectionEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new EdgeComponentEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new SimpleEndpointEditPartPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new EdgeClipboardEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#fireSelectionChanged()
	 */
	@Override
	protected void fireSelectionChanged() {
		if (getSelected() == SELECTED_PRIMARY) {
			getCastedModel().eNotify(
					new NotificationImpl(HenshinNotification.SELECTED, false,
							true));
		}

		super.fireSelectionChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractConnectionEditPart#createPropertySource
	 * ()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new EdgePropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractConnectionEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		getConnectionFigure();
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractConnectionEditPart#notifyChanged(org
	 * .eclipse .emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		if (notification.getEventType() == HenshinNotification.TREE_SELECTED) {
			getViewer().select(this);

			return;
		}

		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case -1:
			refreshSourceAnchor();
			refreshTargetAnchor();
			refreshSourceConnections();
			refreshTargetConnections();
			refreshVisuals();
			break;
		case HenshinPackage.EDGE__SOURCE:
		case HenshinPackage.EDGE__TARGET:
		case HenshinPackage.EDGE__TYPE:
			refreshVisuals();
			break;
		}
	}

	public void collapsing() {
		collapsing = true;
		pLine.remove(label);
		pLine.repaint();
	}
}
