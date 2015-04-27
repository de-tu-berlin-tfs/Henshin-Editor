/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.graph.CreateAttributeCommand;
import de.tub.tfs.henshin.editor.commands.graph.CreateEdgeCommand;
import de.tub.tfs.henshin.editor.commands.graph.CreateNodeCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * The Class GraphCopyCommand.
 * 
 * @author Johann
 */
public class GraphCopyCommand extends CompoundCommand {

	/** The source. */
	private Graph sourceGraph;

	/** The target. */
	private Graph targetGraph;

	/** The map between origin and image nodes. */
	private Map<Node, Node> nodes;

	/**
	 * Instantiates a new graph copy command.
	 * 
	 * @param sourceGraph
	 *            the source
	 * @param targetGraph
	 *            the target
	 * @param mappings
	 */
	public GraphCopyCommand(final Graph sourceGraph, final Graph targetGraph,
			final EObject container) {
		this.sourceGraph = sourceGraph;
		this.targetGraph = targetGraph;
		nodes = new HashMap<Node, Node>();

		List<Mapping> mappings = new ArrayList<Mapping>();

		if (container instanceof Rule) {
			mappings = ((Rule) container).getMappings();
		} else if (container instanceof NestedCondition) {
			mappings = ((NestedCondition) container).getMappings();
		}

		for (Node node : sourceGraph.getNodes()) {
			boolean nodeMapped = false;
			for (Mapping mapping : new ArrayList<Mapping>(mappings)) {
				if (mapping.getOrigin() == node
						&& mapping.getImage().getGraph() == targetGraph) {
					nodeMapped = true;
					// add(new DeleteMappingCommand(mapping));
					break;
				}
			}

			if (!nodeMapped) {
				Node newNode = HenshinFactory.eINSTANCE.createNode();

				if (node.getName() != null) {
					newNode.setName(new String(node.getName()));
				}

				newNode.setType(node.getType());

				NodeLayout nodeLayout = HenshinLayoutUtil.INSTANCE
						.getLayout(node);

				int x = nodeLayout.getX();
				int y = nodeLayout.getY();

				NodeLayout targetLayout = null;
				CreateNodeCommand newNodeCmd = null;

				if (targetGraph.eContainer() instanceof NestedCondition) {
					newNodeCmd = new CreateNodeCommand(targetGraph, newNode, x,
							y);

				} else if ((sourceGraph.isLhs() && targetGraph.isRhs())
						|| (!sourceGraph.isLhs() && !targetGraph.isLhs())) {
					newNodeCmd = new CreateNodeCommand(targetGraph, newNode, x,
							y, false, false);

				}

				if (newNodeCmd != null) {
					add(newNodeCmd);

					targetLayout = newNodeCmd.getNodeLayout();
				}

				CreateNodeMappingCommand mapCmd = new CreateNodeMappingCommand(
						node, newNode, container);

				mapCmd.setImageLayout(targetLayout);
				mapCmd.setImgGraph(targetGraph);

				add(mapCmd);

				nodes.put(node, newNode);

				for (Attribute attr : node.getAttributes()) {
					add(new CreateAttributeCommand(newNode, new String(
							attr.getValue()), attr.getType()));
				}
			}
		}

		for (Edge edge : sourceGraph.getEdges()) {
			add(new CreateEdgeCommand(targetGraph, nodes.get(edge.getSource()),
					nodes.get(edge.getTarget()), edge.getType()));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return sourceGraph != null && targetGraph != null;
	}

	public Graph getTargetGraph() {
		return targetGraph;
	}
}
