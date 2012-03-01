/**
 * RunRuleCommand.java
 *
 * Created 02.01.2012 - 12:51:11
 */
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.HenshinGraph;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class RunRuleCommand extends CompoundCommand {

	private boolean executed;

	private RuleApplication rApplication;

	private Rule rule;

	private Graph graph;

	private Map<String, Object> assignments;

	/**
	 * @param rule
	 * @param graph
	 * @param assignments
	 */
	public RunRuleCommand(Rule rule, Graph graph,
			Map<String, Object> assignments) {
		super("Run Rule");

		this.rule = rule;
		this.graph = graph;
		this.assignments = assignments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return rule != null && graph != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		getCommands().clear();

		executed = false;

		Set<Node> oldNodes = new HashSet<Node>(graph.getNodes());
		Set<NodeLayout> oldLayout = new HashSet<NodeLayout>();

		for (Node n : oldNodes) {
			oldLayout.add(HenshinLayoutUtil.INSTANCE.getLayout(n));
		}

		HenshinGraph henshinGraph = new HenshinGraph(graph);
		EmfEngine engine = new EmfEngine(henshinGraph);

		rApplication = new RuleApplication(engine, rule);
		rApplication.setParameterValues(assignments);

		executed = rApplication.apply();

		if (executed) {
			Set<Node> newNodes = new HashSet<Node>(graph.getNodes());
			Set<Node> deletedNodes = new HashSet<Node>(oldNodes);
			deletedNodes.removeAll(newNodes);

			Set<Node> createdNodes = new HashSet<Node>(newNodes);
			createdNodes.removeAll(oldNodes);

			for (NodeLayout l : oldLayout) {
				if (deletedNodes.contains(l.getModel())) {
					add(new SimpleDeleteEObjectCommand(l));
				}
			}

			for (Node n : createdNodes) {
				EObject o = henshinGraph.getNode2eObjectMap().get(n);
				Node rhsNode = null;

				for (Entry<Node, EObject> entry : rApplication.getComatch()
						.getNodeMapping().entrySet()) {
					if (entry.getValue() == o) {
						rhsNode = entry.getKey();
					}
				}

				int r = new Random(System.currentTimeMillis()).nextInt(100) + 1;
				int x = r;
				int y = r;

				if (rhsNode != null) {
					NodeLayout rhsNodeLayout = HenshinLayoutUtil.INSTANCE
							.getLayout(rhsNode);

					x += rhsNodeLayout.getX();
					y += rhsNodeLayout.getY();
				}

				add(new CreateNodeLayoutCommand(n, x, y));
			}

			super.execute();
		} else {
			MessageDialog.openError(null, "Rule Execution Error",
					"Unable to apply rule.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();

		rApplication.undo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return executed;
	}
}
