package de.tub.tfs.henshin.tggeditor.editparts.rule;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeEndpointEditPartPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleEdgeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleEdgeXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.AttributeUtil;
import de.tub.tfs.henshin.tggeditor.util.EdgeUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;


/**
 * The EdgeEditPart class of rules.
 */
public class RuleEdgeEditPart extends EdgeEditPart {

//	/** the layout model of edge */
//	private EdgeLayout layoutModel;
	
	/** The edge of the RHS. */
	protected Edge rhsEdge;

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
		
		marker = new Label(RuleUtil.NEW);
		marker.setTextAlignment(SWT.CENTER);
		marker.setOpaque(true);
		marker.setBackgroundColor(markerBG_Color);
		marker.setForegroundColor(ColorConstants.darkGreen);
//		marker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		marker.setFont(new Font(Display, "SansSerif", 8, SWT.BOLD));
		marker.setVisible(true);
		
		translatedMarker = new Label(RuleUtil.Translated);
		translatedMarker.setTextAlignment(SWT.CENTER);
		translatedMarker.setOpaque(true);
		translatedMarker.setBackgroundColor(markerBG_Color);
		translatedMarker.setForegroundColor(ColorConstants.darkGreen);
//		translatedMarker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		translatedMarker.setFont(new Font(Display, "SansSerif", 8, SWT.BOLD));
		translatedMarker.setVisible(true);

		rhsEdge = model;

		
//		layoutModel = EdgeUtil.getEdgeLayout(model);
//		if (layoutModel != null)
//			registerAdapter(layoutModel);
//		if (model.getSource() != null)
//			registerAdapter(NodeUtil.getNodeLayout(model.getSource()));
//		if (model.getTarget() != null)
//			registerAdapter(NodeUtil.getNodeLayout(model.getTarget()));
		
		cleanUpRule();

		
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
//				registerAdapter(NodeUtil.getNodeLayout(getCastedModel().getSource()));
//				registerAdapter(NodeUtil.getNodeLayout(getCastedModel().getTarget()));
				refreshVisuals();
				return;
			case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
				refreshVisuals();
				return;
			}
		}		
//		if (notification.getNotifier() instanceof EdgeLayout) {
//			int featureId = notification.getFeatureID(TggPackage.class);
//			switch (featureId) {
//			case TggPackage.EDGE_LAYOUT__NEW:
//				layoutModel= EdgeUtil.getEdgeLayout(getCastedModel());
//				refreshVisuals();
//				return;
//			}
//		}
//		if (notification.getNotifier() instanceof NodeLayout) {
//			Node n = ((NodeLayout)notification.getNotifier()).getNode();
//			if (n == getCastedModel().getSource() || n == getCastedModel().getTarget()) {
//				int featureId = notification.getFeatureID(TggPackage.class);
//				switch (featureId) {
//				case TggPackage.NODE_LAYOUT__NEW:
//					refreshVisuals();
//					return;
//				}
//			}
//		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		EdgeUtil.refreshIsMarked(rhsEdge);
		updateMarker();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new RuleEdgeComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new RuleEdgeXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new EdgeEndpointEditPartPolicy());
	}

	@Override
	protected void updateMarker() {
		if (rhsEdge.getIsMarked() != null) {
			int lastPos = labelContainer.getChildren().size();
			// if attribute shall be marked, then add marker, if it is not
			// present
			if (rhsEdge.getIsMarked()) {
				if(rhsEdge.getMarkerType() != null)
				{
					if (rhsEdge.getMarkerType().equals(RuleUtil.NEW)) {
						if (!labelContainer.getChildren().contains(marker))
							labelContainer.add(marker, lastPos);
					} else if (rhsEdge.getMarkerType().equals(RuleUtil.Translated)) {
						if (!labelContainer.getChildren()
								.contains(translatedMarker))
							labelContainer.add(translatedMarker, lastPos);
					}
				}
			}

			// if attribute shall be without marker, then remove marker
			else {
				if (labelContainer.getChildren().contains(marker)) 
					labelContainer.remove(marker);
				if (labelContainer.getChildren().contains(translatedMarker)) 
					labelContainer.remove(translatedMarker);
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * calculates if edge is translated or not
	 * @param edgeLayout of edge
	 * @return translated status
	 */
//	protected boolean needTranslateFlag(EdgeLayout edgeLayout) {
//		if(edgeLayout == null) return false;
//		Boolean lhsTranslated = edgeLayout.getLhsTranslated();
//		Boolean rhsTranslated = edgeLayout.getRhsTranslated();
//		if(rhsTranslated != null && lhsTranslated != null && !rhsTranslated.equals(lhsTranslated)){
//			return true;
//		}
//		return false;
//	}
	
	@Override
	protected void performOpen() {
		// TODO Auto-generated method stub
		//super.performOpen();
	}
	
	private void cleanUpRule() {
		// remove edge duplicates in LHS
		
		ArrayList<Edge> lhsEdgesList = RuleUtil
				.getAllLHSEdges(rhsEdge);

		// remove duplicates
		while (lhsEdgesList.size() > 1) {
			Edge lhsEdge = lhsEdgesList.get(0);
			lhsEdgesList.remove(0);

            // If getGraph is null (i.e., no parent reference available), then SimpleDeleteEObjectCommand  
            // can't execute and throws and ugly exception... 
            if(lhsEdge.getGraph()!=null)  
            { 
                   SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(lhsEdge); 
                   cmd.execute(); 
            } 
            else // ...in that case remove the edge directly. 
            { 
                   rhsEdge.getGraph().getRule().getLhs().getEdges().remove(lhsEdge); 
            } 			
			
		}		
		// remove lhs edge, if rule creates the edge
		if(rhsEdge.getIsMarked()!=null && rhsEdge.getIsMarked() 
				&& rhsEdge.getMarkerType()!=null
				&& rhsEdge.getMarkerType().equals(RuleUtil.NEW)){
			if (lhsEdgesList.size()==1) 
			{
				Edge lhsEdge = lhsEdgesList.get(0);
				lhsEdgesList.remove(0);
				if(lhsEdge.getGraph()!=null){
					SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(lhsEdge);
					cmd.execute();										
				}
				else {// parent reference of node is missing, thus remove it directly
					rhsEdge.getGraph().getRule().getLhs().getEdges().remove(lhsEdge);
				}
			}
		}
	}

}
