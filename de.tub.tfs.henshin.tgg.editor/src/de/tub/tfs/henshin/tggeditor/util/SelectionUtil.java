/**
 * SelectionUtil.java
 * created on 28.03.2013 16:09:57
 */
package de.tub.tfs.henshin.tggeditor.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;
import de.tub.tfs.henshin.tggeditor.views.ruleview.RuleGraphicalView;

/**
 * @author huuloi
 */
public final class SelectionUtil {

	private SelectionUtil() {
		
	}
	
	
	public static List<NodeObjectEditPart> getNodeEditParts(IWorkbenchPart part, Graph graph) {
		List<NodeObjectEditPart> nodeEditParts = new ArrayList<NodeObjectEditPart>();
		if (part instanceof GraphicalView) {
			GraphicalView view = (GraphicalView) part;
			Object object = view.getPage().getCurrentViewer().getEditPartRegistry().get(graph);
			if (object instanceof GraphEditPart) {
				GraphEditPart graphEditPart = (GraphEditPart) object;
				List<?> children = graphEditPart.getChildren();
				for (Object obj : children) {
					if (obj instanceof NodeObjectEditPart) {
						NodeObjectEditPart nodeEditPart = (NodeObjectEditPart) obj;
						nodeEditParts.add(nodeEditPart);
					}
				}
			}
		}
		
		return nodeEditParts;
	}
	
	
	public static List<RuleNodeEditPart> getNodeEditParts(IWorkbenchPart part, Rule rule) {
		List<RuleNodeEditPart> nodeEditParts = new ArrayList<RuleNodeEditPart>();
		if (part instanceof RuleGraphicalView) {
			RuleGraphicalView view = (RuleGraphicalView) part;
			Object object = view.getPage().getCurrentViewer().getEditPartRegistry().get(rule);
			if (object instanceof RuleGraphicalEditPart) {
				RuleGraphicalEditPart graphEditPart = (RuleGraphicalEditPart) object;
				List<?> children = graphEditPart.getChildren();
				for (Object obj : children) {
					if (obj instanceof RuleNodeEditPart) {
						RuleNodeEditPart nodeEditPart = (RuleNodeEditPart) obj;
						nodeEditParts.add(nodeEditPart);
					}
				}
			}
		}
		
		return nodeEditParts;
	}
}