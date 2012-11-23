/**
 * FilterTypeAction.java
 * created on 08.07.2012 15:28:47
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.EdgeEditPart;
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
public class FilterTypeAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.FilterTypeAction";
	
	private Graph graph;

	public FilterTypeAction(IWorkbenchPart part, Graph graph) {
		
		super(part);
		setId(ID);
		setText(Messages.FILTER_TYPE);
		setDescription(Messages.FILTER_TYPE_DESC);
		this.graph = graph;
	}


	@Override
	protected boolean calculateEnabled() {
		return graph != null && NodeTypes.getUsedNodeTypes(graph).size() > 1;
	}
	
	@Override
	public void run() {
		// open Type Dialog
		GraphView graphView = HenshinSelectionUtil.getInstance()
				.getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		Set<EClass> nodeTypes = NodeTypes.getUsedNodeTypes(graph);
		EClass filterType = new ExtendedElementListSelectionDialog<EClass>(
				shell, new LabelProvider() {

					@Override
					public String getText(Object element) {
						return ((EClass) element).getName();
					}

					@Override
					public Image getImage(Object element) {
						return ResourceUtil.ICONS.NODE.img(Constants.SIZE_18);
					}
				}, nodeTypes.toArray(new EClass[nodeTypes.size()]),
				Messages.FILTER_NODE_TYPE,
				Messages.FILTER_NODE_TYPE_DESC).runSingle();
		
		// do filter
		collapsing(filterType);
	}
	
	private void collapsing(EClass filterType) {
		Map<?, ?> editPartRegistry = HenshinSelectionUtil.getInstance().getEditPartRegistry(graph);
		Object object = editPartRegistry.get(graph);
		
		if (object instanceof GraphEditPart) {
			GraphEditPart graphEditPart = (GraphEditPart) object;
			for (Node node : graph.getNodes()) {
				Object obj = editPartRegistry.get(node);
				
				if (obj != null) {
					if (!EcoreUtil.equals(filterType, node.getType()) && obj instanceof NodeEditPart) {
						NodeEditPart nodeEditPart = (NodeEditPart) obj;
						nodeEditPart.collapsing();
						
						EList<Edge> outgoing = node.getOutgoing();
						for (Edge edge : outgoing) {
							Object edeObj = editPartRegistry.get(edge);
							if (edeObj instanceof EdgeEditPart) {
								EdgeEditPart edgeEditPart = (EdgeEditPart) edeObj;
								if (!EcoreUtil.equals(filterType, edge.getTarget().getType())) {
									edgeEditPart.collapsing();
								}
								else {
									edgeEditPart.getFigure().repaint();
								}
							}
						}
					}
					else {
						NodeEditPart nodeEditPart = (NodeEditPart) obj;
						
						List<?> sourceConnections = nodeEditPart.getSourceConnections();
						for (Object src : sourceConnections) {
							if (src instanceof EdgeEditPart) {
								EdgeEditPart edgeEditPart = (EdgeEditPart) src;
								edgeEditPart.getFigure().repaint();
							}
						}
					}
				}
				
			}
			
			graphEditPart.refreshVisuals();
		}
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ResourceUtil.ICONS.TYPE_SEARCH_TOOL.descr(Constants.SIZE_16);
	}
}
