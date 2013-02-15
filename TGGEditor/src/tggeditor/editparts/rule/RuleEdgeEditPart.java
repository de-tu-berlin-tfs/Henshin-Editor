package tggeditor.editparts.rule;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tgg.TGGPackage;
import tggeditor.editparts.graphical.EdgeEditPart;
import tggeditor.editpolicies.graphical.EdgeEndpointEditPartPolicy;
import tggeditor.editpolicies.rule.RuleEdgeComponentEditPolicy;
import tggeditor.editpolicies.rule.RuleEdgeXYLayoutEditPolicy;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;

/**
 * The EdgeEditPart class of rules.
 */
public class RuleEdgeEditPart extends EdgeEditPart {

	/** the layout model of edge */
	private EdgeLayout layoutModel;
	
	/** The marker label. */
	protected Label marker;
	
	/** The marker label. */
	protected Label translatedMarker;
	
	/** The Constant Display. */
	static final Device Display = null;

	
	protected Color markerBG_Color= new Color(null,232,250,238);

	/**
	 * Instantiates a new rule edge edit part.
	 *
	 * @param model the model
	 */
	public RuleEdgeEditPart(Edge model) {
		super(model);
		
		marker = new Label("<++>");
		marker.setTextAlignment(SWT.CENTER);
		marker.setOpaque(true);
		marker.setBackgroundColor(markerBG_Color);
		marker.setForegroundColor(ColorConstants.darkGreen);
//		marker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		marker.setFont(new Font(Display, "SansSerif", 8, SWT.BOLD));
		marker.setVisible(true);
		
		translatedMarker = new Label("<tr>");
		translatedMarker.setTextAlignment(SWT.CENTER);
		translatedMarker.setOpaque(true);
		translatedMarker.setBackgroundColor(markerBG_Color);
		translatedMarker.setForegroundColor(ColorConstants.darkGreen);
//		translatedMarker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		translatedMarker.setFont(new Font(Display, "SansSerif", 8, SWT.BOLD));
		translatedMarker.setVisible(true);

		layoutModel = EdgeUtil.getEdgeLayout(model);
		if (layoutModel != null)
			registerAdapter(layoutModel);
		if (model.getSource() != null)
			registerAdapter(NodeUtil.getNodeLayout(model.getSource()));
		if (model.getTarget() != null)
			registerAdapter(NodeUtil.getNodeLayout(model.getTarget()));
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof Edge) {
			int featureId = notification.getFeatureID(HenshinPackage.class);		
			switch (featureId) {
			case -1:
				refreshSourceAnchor();
				refreshTargetAnchor();
				refreshSourceConnections();
				refreshTargetConnections();
				refreshVisuals();
				return;
			case HenshinPackage.EDGE__TARGET:
				registerAdapter(NodeUtil.getNodeLayout(getCastedModel().getSource()));
				registerAdapter(NodeUtil.getNodeLayout(getCastedModel().getTarget()));
				refreshVisuals();
				return;
			}
		}		
		if (notification.getNotifier() instanceof EdgeLayout) {
			int featureId = notification.getFeatureID(TGGPackage.class);
			switch (featureId) {
			case TGGPackage.EDGE_LAYOUT__NEW:
				layoutModel= EdgeUtil.getEdgeLayout(getCastedModel());
				refreshVisuals();
				return;
			}
		}
		if (notification.getNotifier() instanceof NodeLayout) {
			Node n = ((NodeLayout)notification.getNotifier()).getNode();
			if (n == getCastedModel().getSource() || n == getCastedModel().getTarget()) {
				int featureId = notification.getFeatureID(TGGPackage.class);
				switch (featureId) {
				case TGGPackage.NODE_LAYOUT__NEW:
					refreshVisuals();
					return;
				}
			}
		}
	}

	@Override
	protected void refreshVisuals() {
		updateMarker();
		super.refreshVisuals();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new RuleEdgeComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new RuleEdgeXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new EdgeEndpointEditPartPolicy());
	}
//
//	/**
//	 * finds the edge layout belongs to model, if not available creates a new edge layout
//	 * @param model the model
//	 */
//	private void findAndSetOrCreateLayout(Edge model) {
//		layoutModel = EdgeUtil.getEdgeLayout(model);
//		if (layoutModel == null) {
//			layoutModel = TGGFactory.eINSTANCE.createEdgeLayout();
//			TGG tgg = NodeUtil.getLayoutSystem(getCastedModel().getSource().getGraph());
//			tgg.getEdgelayouts().add(layoutModel);
//		}
//	}

	@Override
	protected void updateMarker() {
		if(layoutModel != null) {
			if(layoutModel.isNew() && !labelContainer.getChildren().contains(marker)) {
				labelContainer.add(marker, 0);
			}
			if(!layoutModel.isNew() && labelContainer.getChildren().contains(marker)) {
				labelContainer.remove(marker);
			}
			boolean translated = needTranslateFlag(layoutModel);
			if(translated && !labelContainer.getChildren().contains(translatedMarker)) {
				labelContainer.add(translatedMarker, 0);
			}
			if (!translated && labelContainer.getChildren().contains(translatedMarker)) {
				labelContainer.remove(translatedMarker);
			}
		}
	}
	
	/**
	 * calculates if edge is translated or not
	 * @param edgeLayout of edge
	 * @return translated status
	 */
	protected boolean needTranslateFlag(EdgeLayout edgeLayout) {
		if(edgeLayout == null) return false;
		Boolean lhsTranslated = edgeLayout.getLhsTranslated();
		Boolean rhsTranslated = edgeLayout.getRhsTranslated();
		if(rhsTranslated != null && lhsTranslated != null && !rhsTranslated.equals(lhsTranslated)){
			return true;
		}
		return false;
	}
	
	@Override
	protected void performOpen() {
		// TODO Auto-generated method stub
		//super.performOpen();
	}

}
