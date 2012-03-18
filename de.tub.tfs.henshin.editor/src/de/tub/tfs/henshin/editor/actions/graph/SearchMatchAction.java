/**
 * SearchMatchAction.java
 * created on 18.03.2012 17:15:04
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.HenshinGraph;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 *
 */
public class SearchMatchAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.SearchMatchAction";

	private Graph graph;
	
	public SearchMatchAction(IWorkbenchPart part, Graph graph) {

		super(part);
		setId(ID);
		setText(Messages.MATCH_SEARCH);
		setToolTipText(Messages.MATCH_SEARCH_DESC);
		this.graph = graph;
	}
	
	@Override
	protected boolean calculateEnabled() {
		
		return graph != null && graph.getNodes().size() > 0;
	}
	
	@Override
	public void run() {
		TransformationSystem transformationSystem = ModelUtil.getModelRoot(graph, TransformationSystem.class);
		List<Rule> rules = transformationSystem.getRules();
		GraphView graphView = HenshinSelectionUtil.getInstance().getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		// open rule selection dialog
		Rule rule = DialogUtil.runRuleChoiceDialog(shell, rules);
		
		// do search
		HenshinGraph henshinGraph = new HenshinGraph(graph);
		
		EmfEngine engine = new EmfEngine(henshinGraph);
		
		RuleApplication ruleApplication = new RuleApplication(engine, rule);
		ruleApplication.setParameterValues(new HashMap<String, Object>());
		
		List<Match> allMatches = ruleApplication.findAllMatches();
		
		List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance().getNodeEditParts(graph);
		
		Set<EObject> matchedEObjects = new HashSet<EObject>();
		
		for (NodeEditPart nodeEditPart : nodeEditParts) {
		
			Node node = nodeEditPart.getCastedModel();
			
			EObject eObject = henshinGraph.getNode2eObjectMap().get(node);
			
			for (Match match : allMatches) {
			
				for (Entry<Node, EObject> entry : match.getNodeMapping().entrySet()) {
				
					if (entry.getValue() == eObject) {
						nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
						matchedEObjects.add(eObject);
					}
					
					else if (!matchedEObjects.contains(eObject) && nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart.getDefaultColor()) {
						nodeEditPart.getFigure().setBackgroundColor(nodeEditPart.getDefaultColor());
					}
				}
			}
		}

		// refresh
		((GraphEditPart)graphView.getCurrentGraphPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
		
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ResourceUtil.ICONS.MATCH_SEARCH_TOOL.descr(Constants.SIZE_16);
	}
}
