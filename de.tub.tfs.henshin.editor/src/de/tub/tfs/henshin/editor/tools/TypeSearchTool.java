/**
 * TypeSearchTool.java
 * created on 08.02.2012 00:00:43
 */
package de.tub.tfs.henshin.editor.tools;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.NodeTypes;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 *
 */
public class TypeSearchTool extends AbstractTool {

	private Graph graph;
	
	public TypeSearchTool() {
	}
	
	@Override
	public void activate() {
		
		// open Type Dialog
		GraphView graphView = HenshinSelectionUtil.getInstance().getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		List<EClass> nodeTypes = NodeTypes.getNodeTypes(graph, true);
		EClass searchForType = new ExtendedElementListSelectionDialog<EClass>(
				shell, 
				new LabelProvider() {
					
					@Override
					public String getText(Object element) {
						return ((EClass)element).getName();
					}
					
					@Override
					public Image getImage(Object element) {
						return ResourceUtil.ICONS.NODE.img(Constants.SIZE_18);
					}
				}, 
				nodeTypes.toArray(new EClass[nodeTypes.size()]), 
				Messages.SEARCH_FOR_NODE_TYPE, 
				Messages.SEARCH_FOR_NODE_TYPE_DESC
		).runSingle();
		
		// do search
		List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance().getNodeEditParts(graph);
		for (NodeEditPart nodeEditPart : nodeEditParts) {
			if (EcoreUtil.equals(nodeEditPart.getCastedModel().getType(), searchForType)) {
				nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
			}
			else if (!EcoreUtil.equals(nodeEditPart.getCastedModel().getType(), searchForType) && nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart.getDefaultColor()) {
				nodeEditPart.getFigure().setBackgroundColor(nodeEditPart.getDefaultColor());
			}
		}
		
		// refresh
		((GraphEditPart)graphView.getCurrentGraphPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
	}
	
	
	@Override
	protected String getCommandName() {
		return Messages.TYPE_SEARCH;
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
}
