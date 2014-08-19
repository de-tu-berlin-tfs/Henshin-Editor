package de.tub.tfs.henshin.tggeditor.editparts.rule;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.RuleObjectTextWithMarker;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeEndpointEditPartPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleEdgeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleEdgeXYLayoutEditPolicy;
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

		
		EdgeUtil.refreshIsMarked(model);
		
		rhsEdge = model;

		cleanUpRule();

		
	}

	@Override
	protected void createMarker() {
		labelWithMarker=new RuleObjectTextWithMarker(ColorConstants.buttonDarkest);
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (!this.isActive())
			return;
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
			case HenshinPackage.EDGE__SOURCE:
			case HenshinPackage.EDGE__TYPE:
			case HenshinPackage.EDGE__GRAPH:
				refreshVisuals();
				return;
			case TggPackage.TEDGE__MARKER_TYPE: // is always triggered by above case
				refreshVisuals();
				return;
			}
			if (featureId == 7)
				refreshVisuals();
		}		
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new RuleEdgeComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new RuleEdgeXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new EdgeEndpointEditPartPolicy());
	}
		
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
		if( ((TEdge) rhsEdge).getMarkerType()!=null
				&& ((TEdge) rhsEdge).getMarkerType().equals(RuleUtil.NEW)){
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
