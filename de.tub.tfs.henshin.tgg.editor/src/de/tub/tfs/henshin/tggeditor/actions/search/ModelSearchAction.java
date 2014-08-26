/**
 * SearchModelAction.java
 * created on 18.03.2012 17:05:30
 */
package de.tub.tfs.henshin.tggeditor.actions.search;

import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.SelectionUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.FilterMetaModelDialog;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;

/**
 * @author huuloi
 *
 */
public class ModelSearchAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.SearchModelAction";
	
	private Graph graph;
	
	public ModelSearchAction(IWorkbenchPart part, Graph graph) {
		
		super(part);
		setId(ID);
		setText(Messages.ModelSearchAction_Text);
		setToolTipText(Messages.ModelSearchAction_ToolTipText);
		this.graph = graph;
	}
	
	@Override
	protected boolean calculateEnabled() {
		
		return graph != null && graph.getNodes().size() > 1;
	}

	@Override
	public void run() {
		
		Collection<EPackage> usedEPackages = ModelUtil.getEPackagesOfGraph(graph);
		FilterMetaModelDialog dialog = new FilterMetaModelDialog(
			getWorkbenchPart().getSite().getShell(), 
			usedEPackages.toArray(new EPackage[usedEPackages.size()]), 
			"Search for meta model", 
			"Select a meta model to search for:"
		);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK) {
			List<EPackage> metaModels = dialog.getMetaModels();
			if (metaModels.size() == 1) {
				EPackage searchForEpackage = metaModels.get(0);
				List<NodeObjectEditPart> nodeEditParts = SelectionUtil.getNodeEditParts(getWorkbenchPart(), graph);
				for (NodeObjectEditPart nodeEditPart : nodeEditParts) {
					if (EcoreUtil.equals(nodeEditPart.getCastedModel().getType().getEPackage(), searchForEpackage)) {
						nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
					}
					else {
						Color standardColor = ((NodeFigure)nodeEditPart.getFigure()).getStandardColor();
						if (!nodeEditPart.getFigure().getBackgroundColor().equals(standardColor)) {
							nodeEditPart.getFigure().setBackgroundColor(standardColor);
						}
					}
				}
			}
			
			// refresh
			((GraphEditPart)((GraphicalView)getWorkbenchPart()).getPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
		}
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("modelSearch16.png");
	}
}
