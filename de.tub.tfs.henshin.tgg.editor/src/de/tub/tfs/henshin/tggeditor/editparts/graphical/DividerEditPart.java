/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.graphical;



import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;


import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class DividerEditPart.
 */
public class DividerEditPart extends AdapterGraphicalEditPart<Divider> {
	
	
	private static final int w = 5;
	private boolean isSC;
	
	public boolean isSC() {
		return isSC;
	}


	public void setSC(boolean isSC) {
		this.isSC = isSC;
	}


	public DividerEditPart(Divider model, GraphEditPart gep) {
		super(model);
		this.isSC=model.isSC();
		if (isSC)
			gep.setDividerSCpart(this);
		else
			gep.setDividerCTpart(this);
		registerAdapter(model);
		gep.registerAdapter(model);
	}
	
	
	@Override
	protected void createEditPolicies() {
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		//long s = System.nanoTime();System.out.println("enter " +this.getClass().getName());
		if (notification.getNotifier() instanceof Divider){
			final int featureId = notification.getFeatureID(TggPackage.class);
			switch (featureId) {
			case TggPackage.TRIPLE_GRAPH__DIVIDER_CT_X:
			case TggPackage.TRIPLE_GRAPH__DIVIDER_SC_X:
			case TggPackage.TRIPLE_GRAPH__DIVIDER_MAX_Y:
				refreshLocation();
				refreshVisuals();
				return;
			}
		}
		//System.out.println("divider update: " + ((System.nanoTime() - s) / 1000000) + " ms.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		figure = new RectangleFigure();
		figure.setSize(w, this.getCastedModel().getTripleGraph().getDividerMaxY());
		setX();
			figure.setBackgroundColor(TGGEditorConstants.BG_COLOR_GREY);
			figure.setForegroundColor(TGGEditorConstants.FG_STANDARD_COLOR);
		return figure;
	}


	/**
	 * sets the x coordinate of the divider
	 */
	private void setX() {
		if(isSC)
			figure.setLocation(new Point(this.getCastedModel().getTripleGraph().getDividerSC_X(), this.getCastedModel().getTripleGraph().getDividerYOffset() + 10));
		else
			figure.setLocation(new Point(this.getCastedModel().getTripleGraph().getDividerCT_X(), this.getCastedModel().getTripleGraph().getDividerYOffset() + 10));
	}
	
	protected RectangleFigure getModelFigure() {
		return (RectangleFigure) getFigure();
	}
	
	
	/**
	 * Refresh location.
	 */
	public void refreshLocation(){
		figure.setSize(w, this.getCastedModel().getTripleGraph().getDividerMaxY());
		setX();
	}
		
	
	@Override
	protected void refreshVisuals() {		
		getFigure().repaint();
	}

	@Override
	protected void performDirectEdit() {
	}
	
	@Override
	protected void performOpen() {		
	}
		
	
}
