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
 * SubtreeEdgeEditPart.java
 * created on 16.07.2012 01:39:03
 */
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.SimpleEndpointEditPartPolicy;
import de.tub.tfs.henshin.model.subtree.Edge;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * @author huuloi
 *
 */
public class SubtreeEdgeEditPart extends AdapterConnectionEditPart<Edge> {
	
	private PolylineConnection polylineConnection;
	
	public SubtreeEdgeEditPart(Edge model) {
		super(model);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new SimpleEndpointEditPartPolicy());
	}

	@Override
	protected IFigure createFigure() {
		polylineConnection = new PolylineConnection() {
			@Override
			public void paint(Graphics graphics) {
				graphics.setAlpha(255);
				super.paint(graphics);
			}
		};
		
		updateDeco(polylineConnection);
		
		return polylineConnection;
	}
	
	private void updateDeco(IFigure figure) {
		if (getCastedModel().getType() != null) {
			if (getCastedModel().getType().isContainment()) {
				PolygonDecoration decoration = new PolygonDecoration();
				decoration.setTemplate(new PointList(new int[] { 0, 0, -1, 1,
						-2, 0, -1, -1 }));
				PolylineConnection pline = (PolylineConnection) figure;
				pline.setTargetDecoration(null);
				pline.setSourceDecoration(decoration);
			} else {
				PolygonDecoration decoration = new PolygonDecoration();
				decoration.setTemplate(PolygonDecoration.TRIANGLE_TIP);
				PolylineConnection pline = (PolylineConnection) figure;
				pline.setSourceDecoration(null);
				pline.setTargetDecoration(decoration);

			}
		}

	}
	
	@Override
	public IFigure getFigure() {
		if (polylineConnection == null) {
			polylineConnection = (PolylineConnection) createFigure();
		}
		return polylineConnection;
	}
}
