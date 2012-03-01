/**
 * 
 */
package de.tub.tfs.henshin.editor.tools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.HenshinGraph;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * @author huuloi
 *
 */
public class MatchSearchTool extends AbstractTool {
	
	private Graph graph;
	
	public MatchSearchTool() {
	}
	
	@Override
	public void activate() {

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
//		ruleApplication.apply();
		List<Match> matches = ruleApplication.findAllMatches();
		Set<Node> nodeMapping = new HashSet<Node>();
		for (Match match : matches) {
			nodeMapping.addAll(match.getNodeMapping().keySet());
		}
		
		List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance().getNodeEditParts(graph);
		for (NodeEditPart nodeEditPart : nodeEditParts) {
			Node node = nodeEditPart.getCastedModel();
			Iterator<Node> iterator = nodeMapping.iterator();
			while (iterator.hasNext()) {
				Node mappedNode = iterator.next();
				if (EcoreUtil.equals(node, mappedNode)) {
					nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
				}
				else if (!EcoreUtil.equals(node, mappedNode) && nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart.getDefaultColor()) {
					nodeEditPart.getFigure().setBackgroundColor(nodeEditPart.getDefaultColor());
				}
			}
		}
		
		// refresh
		((GraphEditPart)graphView.getCurrentGraphPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
	}

	@Override
	protected String getCommandName() {
		return Messages.MATCH_SEARCH;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
}
