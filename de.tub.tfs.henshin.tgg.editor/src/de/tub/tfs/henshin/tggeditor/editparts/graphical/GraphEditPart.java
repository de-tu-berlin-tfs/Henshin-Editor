package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.tree.ImportFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolder;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.figures.EdgeConnectionRouter;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class GraphEditPart.
 */
public class GraphEditPart extends AdapterGraphicalEditPart<TripleGraph> {
	private static Font f = new Font(null, java.awt.Font.MONOSPACED, 20, SWT.BOLD);
	

	/** The name label */
	protected Label nameLabel;

	/** The Layout system */
	protected TGG tgg;
	
	/** The triple graph with dividers */
	protected TripleGraph tripleGraph;
	
	public TripleGraph getTripleGraph() {
		return tripleGraph;
	}

	public void setTripleGraph(TripleGraph tripleGraph) {
		this.tripleGraph = tripleGraph;
	}

	/** The edit parts of dividers */
	protected Divider divSC, divCT;
	
	
	public DividerEditPart dividerSCpart, dividerCTpart;
	
	public DividerEditPart getDividerCTpart() {
		return dividerCTpart;
	}

	public void setDividerCTpart(DividerEditPart dividerCTpart) {
		this.dividerCTpart = dividerCTpart;
	}

	public DividerEditPart getDividerSCpart() {
		return dividerSCpart;
	}

	public void setDividerSCpart(DividerEditPart dividerSCpart) {
		this.dividerSCpart = dividerSCpart;
	}

	/** The current height of visual display of model */
	protected int height;
	
	/**
	 * Instantiates a new graph edit part.
	 *
	 * @param model the model
	 */
	public GraphEditPart(TripleGraph model) {
		super(model);
		tripleGraph=model;
		tgg = NodeUtil.getLayoutSystem(getCastedModel());
		divSC = new Divider(tripleGraph,true);
		divCT = new Divider(tripleGraph,false);
		initDividers();
	}

	/**
	 * Generates dividers graph layouts of this graph if not already created and saves them in 
	 * divSC and divCT.
	 */
	private void initDividers() {
		if(tripleGraph.getDividerSC_X()==0)
			tripleGraph.setDividerSC_X(GraphUtil.center - GraphUtil.correstpondenceWidth/2);
		if(tripleGraph.getDividerCT_X()==0)
			tripleGraph.setDividerCT_X(GraphUtil.center + GraphUtil.correstpondenceWidth/2);		
	}

	public Rectangle getCorrectedBounds(Rectangle rect,List<Figure> figures,HashSet<Class<? extends Figure>> skip){
		rect = new Rectangle(rect);
		int maxY = 0;
		int maxX = 0;
		//System.out.println(rect);
		for (Figure figure : figures) {
			if (skip.contains(figure.getClass()))
				continue;
			Rectangle r = figure.getBounds();
			
			if (r.x + r.width > maxX)
				maxX = r.x + r.width;
			if (r.y + r.height > maxY)
				maxY = r.y + r.height;
			
		}
		
		rect.width = maxX;
		rect.height = maxY;
		return rect;
		
	}
	
	
	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new FreeformLayer(){
			
			@Override
			protected void paintClientArea(Graphics graphics) {
				super.paintClientArea(graphics);
				Rectangle rect = getCorrectedBounds(this.getBounds(),this.getChildren(),new HashSet<Class<? extends Figure>>(Arrays.asList(RectangleFigure.class)));				
				
				if (tripleGraph.getDividerSC_X() == 0) {
					tripleGraph.setDividerSC_X(rect.width/2 - rect.width/8);
					tripleGraph.setDividerMaxY(rect.height - rect.y);
					tripleGraph.setDividerCT_X(rect.width/2 + rect.width/8);	
					tripleGraph.setDividerMaxY(rect.height - rect.y);
				}
				else if (height != rect.height) {
					height = rect.height;
					tripleGraph.setDividerMaxY(rect.height+20-rect.y);
				}
				else if (tripleGraph.getDividerMaxY() + rect.y > rect.height - 20) {
					tripleGraph.setDividerMaxY(rect.height + 20 - rect.y);
				}
				else {
					tripleGraph.setDividerMaxY(rect.height+20 - rect.y);
				}
				tripleGraph.setDividerYOffset(rect.y);
			}
			
			
			
			
			@Override
			public void paint(Graphics graphics) {
				graphics.pushState();
				String text = nameLabel.getText();
				graphics.setForegroundColor(ColorConstants.black);
				TextLayout textLayout = new TextLayout(null);
				textLayout.setText(text);
				textLayout.setFont(f);		
				graphics.drawTextLayout(textLayout, 5, 5);
				textLayout.dispose();

				graphics.popState();
				super.paint(graphics);
				
			}
		};
		layer.setLayoutManager(new TGGLayoutManager());
		System.out.println("");
		nameLabel =new Label();
		nameLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD));
		nameLabel.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		setFigureNameLabel();
		
		//layer.add(nameLabel, new Rectangle(10,10,-1,-1));

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
		String name = getCastedModel().getName()+"\n";
		nameLabel.setText(name);
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
		list.add(this.divSC);
		list.add(this.divCT); // TGG shortest path connection router has problems, if dividers are not at the beginning
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
			//long s = System.nanoTime();
			System.out.println("enter " +this.getClass().getName());
			refreshChildren();
			refreshVisuals();
			//System.out.println("graph graphical update time: " + ((System.nanoTime() - s)/1000000) + " ms");
			
		}
		
		final int featureId2 = msg.getFeatureID(TggPackage.class);
		switch (featureId2) {
		case TggPackage.TRIPLE_GRAPH__DIVIDER_SC_X:
			if(dividerSCpart!=null)
			dividerSCpart.refreshLocation();
		case TggPackage.TRIPLE_GRAPH__DIVIDER_CT_X:
			if(dividerCTpart!=null)
			dividerCTpart.refreshLocation();
		case TggPackage.TRIPLE_GRAPH__DIVIDER_MAX_Y:
//			refreshChildren();
			if (this.isActive()){
				refreshVisuals();
				getFigure().invalidate();
			}
			// TODO - this should be handled with refreshChildren, but notification is currently not working
			if(dividerSCpart!=null && dividerCTpart!=null)
			{
				dividerSCpart.refreshLocation();
				dividerCTpart.refreshLocation();
			}
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
	
}
