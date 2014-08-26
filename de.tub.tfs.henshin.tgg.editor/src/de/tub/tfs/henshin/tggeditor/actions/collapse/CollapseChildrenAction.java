/**
 * CollapseNodeAction.java
 * created on 14.07.2012 12:30:04
 */
package de.tub.tfs.henshin.tggeditor.actions.collapse;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.collapse.CollapseChildrenCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.util.TGGCache;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;

/**
 * @author huuloi
 *
 */
public class CollapseChildrenAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.collapse.CollapseChildrenAction";
	
	private Node node;

	public CollapseChildrenAction(IWorkbenchPart part) {
		super(part);
		
		setId(ID);
		setText("Collapse node");
		setDescription("Make this node and all its children to a collapsing-node");
		setToolTipText("Make this node and all its children to a collapsing-node");
	}

	@Override
	protected boolean calculateEnabled() {

		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		
		Object selectedObject = selectedObjects.get(0);
		if (selectedObject instanceof NodeObjectEditPart) {
			NodeObjectEditPart nodeEditPart = (NodeObjectEditPart) selectedObject;
			node = nodeEditPart.getCastedModel();
			EList<Edge> outgoing = node.getOutgoing();
			for (Edge edge : outgoing) {
				if (edge.getType().isContainment() && 
					!TGGCache.getInstance().getRemovedEditParts().contains(edge)
				) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		Map<?, ?> editPartRegistry = ((GraphicalView)getWorkbenchPart()).getPage().getCurrentViewer().getEditPartRegistry();
		CollapseChildrenCommand command = new CollapseChildrenCommand(node, editPartRegistry);
		execute(command);
	}
}
