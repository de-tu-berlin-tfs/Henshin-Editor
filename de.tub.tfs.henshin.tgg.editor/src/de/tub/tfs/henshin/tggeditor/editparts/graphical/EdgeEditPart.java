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
package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeEndpointEditPartPolicy;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * The class EdgeEditPart.
 */
public class EdgeEditPart extends AdapterConnectionEditPart<Edge> {


	/** The marker label */
	protected TextWithMarker labelWithMarker;

	/**
	 * Instantiates a new edge edit part.
	 *
	 * @param model the model
	 */
	public EdgeEditPart(Edge model) {
		super(model);
		createMarker();
	}
	
	protected void createMarker() {
		labelWithMarker=new TextWithMarker(TGGEditorConstants.FG_STANDARD_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		PolylineConnection pLine = new PolylineConnection();
		pLine.setForegroundColor(TGGEditorConstants.LINE_COLOR);
		
		updateLabel();
		labelWithMarker.setLayoutManager(new GridLayout());
		labelWithMarker.setBackgroundColor(TGGEditorConstants.BG_COLOR_GREY);
		pLine.add(labelWithMarker, new MidpointLocator(pLine, 0));
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
		//updateLabel();
		//updateDeco(figure);
		return figure;
	}
	
	/**
	 * Update label.
	 */
	private void updateLabel(){
		Edge edge = getCastedModel();
		if (edge!=null){
			labelWithMarker.setMarker(((TEdge)edge).getMarkerType());
			if (edge.getType() != null) {
			labelWithMarker.setText(edge.getType().getName());
			
			// update color after FT execution
			if((RuleUtil.Translated_Graph.equals(((TEdge) edge).getMarkerType()))
				|| (RuleUtil.Not_Translated_Graph.equals(((TEdge) edge).getMarkerType())))
			{
				labelWithMarker.text.setBorder(new LineBorder());
			}
			else
				labelWithMarker.text.setBorder(null);
		}}
	}
	
	/**
	 * Update deco.
	 *
	 * @param figure the figure
	 */
	private void updateDeco(IFigure figure){
		if (getCastedModel().getType() != null) {
			if (getCastedModel().getType().isContainment()){
				PolygonDecoration decoration = new PolygonDecoration();
				decoration.setTemplate(new PointList(new int[] {0,0,-1,1,-2,0,-1,-1}));
				PolylineConnection Pline =(PolylineConnection)figure;
				Pline.setTargetDecoration(null);
				Pline.setSourceDecoration(decoration);
			}
			else{
				PolygonDecoration decoration = new PolygonDecoration();
				decoration.setTemplate(PolygonDecoration.TRIANGLE_TIP);
				PolylineConnection Pline =(PolylineConnection)figure;
				Pline.setSourceDecoration(null);
				Pline.setTargetDecoration(decoration);

			}
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		getConnectionFigure();
		super.refreshVisuals();
		updateLabel();
		updateDeco(getFigure());
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#notifyChanged(org.eclipse .emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		//long s = System.nanoTime();System.out.println("enter " +this.getClass().getName());
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
		case HenshinPackage.EDGE__GRAPH:	
		case HenshinPackage.EDGE__TYPE:
			refreshVisuals();
			break;
			
		case TggPackage.TEDGE__MARKER_TYPE:
			refreshVisuals();
			break;
		}
		//System.out.println("edge update: " + ((System.nanoTime() - s) / 1000000) + " ms.");
	}
	
	@Override
	protected ConnectionAnchor getSourceConnectionAnchor() {
		return super.getSourceConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#getTargetConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getTargetConnectionAnchor() {
		return super.getTargetConnectionAnchor();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new EdgeComponentEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new EdgeEndpointEditPartPolicy());
		
	}

}
