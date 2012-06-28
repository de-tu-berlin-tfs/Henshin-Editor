package tggeditor.editparts.graphical;



import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import tgg.GraphLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tgg.TGGPackage;
import tggeditor.editpolicies.graphical.GraphXYLayoutEditPolicy;
import tggeditor.figures.EdgeConnectionRouter;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class GraphEditPart.
 */
public class GraphEditPart extends AdapterGraphicalEditPart<Graph> {
	/** The name label */
	protected Label nameLabel;

	/** The Layout system */
	protected TGG tgg;
	
	/** The graph layouts of dividers */
	protected GraphLayout divSC, divCT;
	
	/** The edit parts of dividers */
	protected DividerEditPart divSCpart, divCTpart;
	
	/** The current height of visual display of model */
	protected int height;
	
	/**
	 * Instantiates a new graph edit part.
	 *
	 * @param model the model
	 */
	public GraphEditPart(Graph model) {
		super(model);
		tgg = NodeUtil.getLayoutSystem(getCastedModel());
		initDividers();
	}

	/**
	 * Generates dividers graph layouts of this graph if not already created and saves them in 
	 * divSC and divCT.
	 */
	private void initDividers() {
		for (GraphLayout gl : tgg.getGraphlayouts()) {
			if (gl.getGraph() == getCastedModel()) {
				if (gl.isIsSC())
					divSC = gl;
				else
					divCT = gl;
			}
		}
		if (divSC == null) {
			divSC = TGGFactory.eINSTANCE.createGraphLayout();
			divSC.setDividerX(GraphUtil.center - GraphUtil.correstpondenceWidth/2);
			divSC.setIsSC(true);
			divSC.setGraph(getCastedModel());
			divCT = TGGFactory.eINSTANCE.createGraphLayout();
			divCT.setDividerX(GraphUtil.center + GraphUtil.correstpondenceWidth/2);
			divCT.setIsSC(false);
			divCT.setGraph(getCastedModel());
			tgg.getGraphlayouts().add(divSC);
			tgg.getGraphlayouts().add(divCT);
		}
	}

	/**
	 * sets a new divider edit part for sc divider
	 * @param divPart is new divider edit part
	 */
	public void setDividerSC(DividerEditPart divPart) {
		divSCpart = divPart;		
	}
	
	/**
	 * sets a new divider edit part for ct divider
	 * @param divPart is new divider edit part
	 */
	public void setDividerCT(DividerEditPart divPart) {
		divCTpart = divPart;		
	}
	
	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new FreeformLayer(){
			@Override
			protected void paintClientArea(Graphics graphics) {
				super.paintClientArea(graphics);
				Rectangle rect = this.getBounds();				
				if (divSC != null) {
					if (divSC.getDividerX() == 0) {
						divSC.setDividerX(rect.width/2 - rect.width/8);
						divSC.setMaxY(rect.height-20 + rect.y);
						divCT.setDividerX(rect.width/2 + rect.width/8);	
						divCT.setMaxY(rect.height-20 + rect.y);
					}
					else if (height != rect.height) {
						height = rect.height;
						divSC.setMaxY(rect.height-20+rect.y);
					
						divCT.setMaxY(rect.height-20+rect.y);
					}
					else if (divSC.getMaxY() > rect.height-20) {
						divSC.setMaxY(rect.height-20 + rect.y);
						divCT.setMaxY(rect.height-20 + rect.y);
					}
					else {
						divSC.setMaxY(rect.height-20 + rect.y);
						divCT.setMaxY(rect.height-20 + rect.y);
					}
				}			}
		};
		layer.setLayoutManager(new FreeformLayout());
		System.out.println("");
		nameLabel =new Label();
		nameLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD));
		nameLabel.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		setFigureNameLabel();
		layer.add(nameLabel, new Rectangle(10,10,-1,-1));
		
		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		cLayer.setAntialias(SWT.ON);
		EdgeConnectionRouter edgeRouter=new EdgeConnectionRouter(layer);
		if (this.getCastedModel().getEdges().size() > 1000){
			edgeRouter.setNextRouter(ConnectionRouter.NULL);
			
			MessageDialog.open(MessageDialog.INFORMATION, Display.getDefault().getActiveShell(), "Information", "The graph has too many edges. Therefore the edges will not be drawn around nodes.", SWT.SHEET);
			
		}
		cLayer.setConnectionRouter(edgeRouter);
		return layer;
	}
	
	/**
	 * sets the name of corresponding model into name label
	 */
	protected void setFigureNameLabel(){
		nameLabel.setText(getCastedModel().getName());
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GraphXYLayoutEditPolicy());
	}

		
	@Override
	protected void performOpen() {
		// TODO Auto-generated method stub
		super.performOpen();
	}

	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new Vector<Object>(getCastedModel().getNodes().size()+2);
		if (this.divSC != null)
			list.add(this.divSC);
		if (this.divCT != null)
			list.add(this.divCT);
		list.addAll(getCastedModel().getNodes());		
		return list;
	}
	
	@Override
	protected void notifyChanged(final Notification msg) {
		final int featureId = msg.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.GRAPH__NAME:
			setFigureNameLabel();
			refreshVisuals();
			break;
		case HenshinPackage.GRAPH__NODES:
		case HenshinPackage.GRAPH__EDGES:
			refreshChildren();
			refreshVisuals();
		}
		
		final int featureId2 = msg.getFeatureID(TGGPackage.class);
		switch (featureId2) {
		case TGGPackage.GRAPH_LAYOUT:
		case TGGPackage.GRAPH_LAYOUT__DIVIDER_X:
			refreshChildren();
			refreshVisuals();
		}
		super.notifyChanged(msg);
	}
	
	/**
	 * refresh the figure of this edit part
	 */
	protected void refreshVisuals() {
		getFigure().setBackgroundColor(ColorConstants.white);
		getFigure().repaint();
	}
	
	/**
	 * gets edit part of sc-divider
	 * @return edit part of sc-divider
	 */
	public DividerEditPart getDividerSCpart() {
		return divSCpart;
	}
	
	/**
	 * gets edit part of ct-divider
	 * @return edit part of ct-divider
	 */
	public DividerEditPart getDividerCTpart() {
		return divCTpart;
	}
}
