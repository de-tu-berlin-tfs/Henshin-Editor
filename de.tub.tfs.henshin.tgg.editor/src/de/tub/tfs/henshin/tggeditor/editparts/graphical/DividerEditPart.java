package de.tub.tfs.henshin.tggeditor.editparts.graphical;


import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.graphics.Color;


import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.TGGPackage;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class DividerEditPart.
 */
public class DividerEditPart extends AdapterGraphicalEditPart<GraphLayout> {
	
	
	/** The background color **/
	private Color backgroundColor = new Color(null, 192, 192, 152); 
	private static final int w = 5;
	
	public DividerEditPart(GraphLayout model, GraphEditPart gep) {
		super(model);

		if (model.isIsSC())
			gep.setDividerSC(this);
		else
			gep.setDividerCT(this);
		
		registerAdapter(model);
		gep.registerAdapter(model);
	}
	
	
	@Override
	protected void createEditPolicies() {
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof GraphLayout){
			final int featureId = notification.getFeatureID(TGGPackage.class);
			switch (featureId) {
			case TGGPackage.GRAPH_LAYOUT__DIVIDER_X:
			case TGGPackage.GRAPH_LAYOUT__MAX_Y:	
				refreshLocation();
				refreshVisuals();
				return;
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
		figure = new RectangleFigure();
		figure.setSize(w, this.getCastedModel().getMaxY());
		figure.setLocation(new Point(this.getCastedModel().getDividerX(), 10));
		figure.setBackgroundColor(backgroundColor);
		figure.setForegroundColor(backgroundColor);
		return figure;
	}
	
	protected RectangleFigure getModelFigure() {
		return (RectangleFigure) getFigure();
	}
	
	
	/**
	 * Refresh location.
	 */
	private void refreshLocation(){
		figure.setSize(w, this.getCastedModel().getMaxY());
		figure.setLocation(new Point(this.getCastedModel().getDividerX(), 10));
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
