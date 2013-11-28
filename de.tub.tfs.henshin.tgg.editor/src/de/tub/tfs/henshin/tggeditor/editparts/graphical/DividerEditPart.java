package de.tub.tfs.henshin.tggeditor.editparts.graphical;


import javax.swing.text.StyleConstants.ColorConstants;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.graphics.Color;

import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class DividerEditPart.
 */
public class DividerEditPart extends AdapterGraphicalEditPart<Divider> {
	
	
	/** The background color **/
	private static Color backgroundColorSrc = new Color(null, 192, 192, 152); 
	private static Color backgroundColorTar = new Color(null, 140, 140, 100); 
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
		if (getCastedModel().isSC()){
			figure.setBackgroundColor(backgroundColorSrc);
			figure.setForegroundColor(backgroundColorSrc);
		} else {
			figure.setBackgroundColor(backgroundColorTar);
			figure.setForegroundColor(backgroundColorTar);
		}
			
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
