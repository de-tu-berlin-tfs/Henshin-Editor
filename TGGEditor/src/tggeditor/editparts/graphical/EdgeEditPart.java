package tggeditor.editparts.graphical;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;

import tggeditor.editpolicies.graphical.EdgeComponentEditPolicy;
import tggeditor.editpolicies.graphical.EdgeEndpointEditPartPolicy;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * The class EdgeEditPart.
 */
public class EdgeEditPart extends AdapterConnectionEditPart<Edge> {
	/** The label container. */
	protected Figure labelContainer;
	
	/** The label. */
	private Label label;
	
	/**
	 * Instantiates a new edge edit part.
	 *
	 * @param model the model
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
		PolylineConnection pLine = new PolylineConnection();
		
		labelContainer = new Figure();
		labelContainer.setLayoutManager(new GridLayout(1,true));
		
		label = new Label("");
		label.setTextAlignment(SWT.CENTER);
		updateLabel();
		label.setOpaque(true);
		label.setBackgroundColor(ColorConstants.white);
		
		labelContainer.add(label);
		pLine.add(labelContainer, new MidpointLocator(pLine, 0));
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
	private void updateLabel(){
		if (getCastedModel().getType() != null) {
			label.setText(getCastedModel().getType().getName());
		}
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
		updateMarker();
		super.refreshVisuals();
	}
	
	/**
	 * Updates edge marker.
	 */
	protected void updateMarker() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#notifyChanged(org.eclipse .emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(final Notification notification) {
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
		}
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
