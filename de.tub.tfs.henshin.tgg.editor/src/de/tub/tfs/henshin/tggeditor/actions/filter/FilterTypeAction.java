/**
 * FilterTypeAction.java
 * created on 08.07.2012 15:28:47
 */
package de.tub.tfs.henshin.tggeditor.actions.filter;

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
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.dialogs.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;

/**
 * @author huuloi
 *
 */
public class FilterTypeAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.FilterTypeAction";
	
	private Graph graph;

	public FilterTypeAction(IWorkbenchPart part, Graph graph) {
		
		super(part);
		setId(ID);
		setText("Filter type");
		setDescription("Choose a type to filter:");
		this.graph = graph;
	}


	@Override
	protected boolean calculateEnabled() {
		return graph != null && NodeTypes.getUsedNodeTypes(graph).size() > 1;
	}
	
	@Override
	public void run() {
		// open Type Dialog
		Set<EClass> nodeTypes = NodeTypes.getUsedNodeTypes(graph);
		EClass filterType = new ExtendedElementListSelectionDialog<EClass>(
			getWorkbenchPart().getSite().getShell(), 
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((EClass) element).getName();
				}

			}, 
			nodeTypes.toArray(new EClass[nodeTypes.size()]),
			"Filter type",
			"Choose a type to filter:").runSingle();
		
		// do filter
		if (filterType != null) {
			collapsing(filterType);
		}
	}
	
	private void collapsing(EClass filterType) {
		
		Map<?, ?> editPartRegistry = ((GraphicalView)getWorkbenchPart()).getPage().getCurrentViewer().getEditPartRegistry();
		Object object = editPartRegistry.get(graph);
		
		if (object instanceof GraphEditPart) {
			GraphEditPart graphEditPart = (GraphEditPart) object;
			for (Node node : graph.getNodes()) {
				Object obj = editPartRegistry.get(node);
				
				if (obj != null) {
					if (!EcoreUtil.equals(filterType, node.getType()) && obj instanceof NodeObjectEditPart) {
						NodeObjectEditPart nodeEditPart = (NodeObjectEditPart) obj;
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
						NodeObjectEditPart nodeEditPart = (NodeObjectEditPart) obj;
						
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
		return IconUtil.getDescriptor("typeFilter16.png");
	}
}
