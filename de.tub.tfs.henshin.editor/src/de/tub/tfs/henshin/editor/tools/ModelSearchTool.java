/**
 * ModelSearchTool.java
 * created on 01.03.2012 09:46:31
 */
package de.tub.tfs.henshin.editor.tools;

import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.dialog.FilterMetaModelDialog;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * @author huuloi
 *
 */
public class ModelSearchTool extends AbstractTool {
	
	private Graph graph;
	
	public ModelSearchTool() {
	}
	
	@Override
	public void activate() {
		
		GraphView graphView = HenshinSelectionUtil.getInstance().getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		Collection<EPackage> usedEPackages = ModelUtil.getEPackagesOfGraph(graph);
		FilterMetaModelDialog dialog = new FilterMetaModelDialog(
				shell, 
				usedEPackages.toArray(new EPackage[usedEPackages.size()]), 
				Messages.SEARCH_FOR_META_MODEL, 
				Messages.SEARCH_FOR_META_MODEL_DESC
		);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK) {
			List<EPackage> metaModels = dialog.getMetaModels();
			if (metaModels.size() == 1) {
				EPackage searchForEpackage = metaModels.get(0);
				List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance().getNodeEditParts(graph);
				for (NodeEditPart nodeEditPart : nodeEditParts) {
					if (EcoreUtil.equals(nodeEditPart.getCastedModel().getType().getEPackage(), searchForEpackage)) {
						nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
					}
					else if (!EcoreUtil.equals(nodeEditPart.getCastedModel().getType().getEPackage(), searchForEpackage) && nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart.getDefaultColor()) {
						nodeEditPart.getFigure().setBackgroundColor(nodeEditPart.getDefaultColor());
					}
				}
			}
			
			// refresh
			((GraphEditPart)graphView.getCurrentGraphPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
		}
		
		setState(STATE_TERMINAL);
		handleFinished();
	}
	
	@Override
	protected void handleFinished() {
		getDomain().loadDefaultTool();
	}

	@Override
	protected String getCommandName() {
		return Messages.MODEL_SEARCH;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
