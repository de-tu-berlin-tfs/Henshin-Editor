package de.tub.tfs.henshin.tggeditor.editparts.constraint;

import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;

import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.GraphXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.figures.EdgeConnectionRouter;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

public class GraphEditPart extends AdapterGraphicalEditPart<Graph> {

	public GraphEditPart(Graph model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new FreeformLayer() {
			@Override
			public void paint(Graphics graphics) {
				graphics.pushState();
				graphics.setForegroundColor(TGGEditorConstants.FG_STANDARD_COLOR);
				TextLayout textLayout = new TextLayout(null);
				textLayout.setText(getCastedModel().getName());
				textLayout.setFont(TGGEditorConstants.TEXT_TITLE_FONT);		
				graphics.drawTextLayout(textLayout, 5, 5);
				textLayout.dispose();

				graphics.popState();
				super.paint(graphics);
			}
		};

		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		cLayer.setAntialias(SWT.ON);
		EdgeConnectionRouter edgeRouter=new EdgeConnectionRouter(layer);
		if (this.getCastedModel().getEdges().size() > 1000){
			edgeRouter.setNextRouter(ConnectionRouter.NULL);
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					MessageDialog.open(MessageDialog.INFORMATION, Display.getDefault().getActiveShell(), "Information", "The graph has too many edges. Therefore the edges will not be drawn around nodes.", SWT.SHEET);
				}
			});

		}
		cLayer.setConnectionRouter(edgeRouter);
		layer.setLayoutManager(new FreeformLayout());
		return layer;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GraphXYLayoutEditPolicy());
	}

	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new Vector<Object>(getCastedModel().getNodes().size());
		list.addAll(getCastedModel().getNodes());
		return list;
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.GRAPH__NODES:
		case HenshinPackage.GRAPH__EDGES:
			refreshChildren();
			refreshVisuals();		
		}
		super.notifyChanged(notification);
	}
	
}
