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
/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.dialog.ValidTestDialog;
import de.tub.tfs.henshin.editor.util.NodeTypes;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * The Class GraphValidAction.
 * 
 * @author Johann
 */
public class ValidateGraphAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.ValidateGraphAction"; //$NON-NLS-1$

	/** The Constant DESC. */
	static private final String DESC = "Validate the selected Graph.";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Validate this Graph.";

	static private final String LABEL = "Validate";

	/** The graph. */
	protected Graph graph;

	/**
	 * 
	 * @param part
	 *            the part
	 */
	public ValidateGraphAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(LABEL);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
		setImageDescriptor(ResourceUtil.ICONS.CHECK.descr(18));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		graph = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if (selectedObject instanceof EditPart) {
				Object model = ((EditPart) selectedObject).getModel();

				if (model instanceof Graph) {
					graph = (Graph) model;
				}
			}
		}

		return graph != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		List<String> fehlerMeldungen = new ArrayList<String>();
		Map<EPackage, LinkedList<Node>> ePackage2NodeList = getRoots();
		Set<EPackage> ePackages = ePackage2NodeList.keySet();
		for (EPackage ePackage : ePackages) {
			LinkedList<Node> wurzeln = ePackage2NodeList.get(ePackage);
			if (wurzeln.size() != 1) {
				if (wurzeln.size() == 0) {
					fehlerMeldungen
							.add("Der Graph hat keinen Wurzelknoten von EMF_Model "
									+ ePackage.getName() + "!");
					ePackage2NodeList.remove(ePackage);
				}
				if (wurzeln.size() > 1) {
					fehlerMeldungen
							.add("Der Graph enth�lt mehrere Wurzelknoten eines EMF-Modells ("
									+ ePackage.getName() + ")!");
				}
			}
		}

		for (Node node : graph.getNodes()) {
			if (NodeTypes.isContainment(node)) {
				int count = 0;
				for (Edge edge : node.getIncoming()) {
					if (edge.getType().isContainment()) {
						count++;
					}
				}
				if (count > 1) {
					fehlerMeldungen.add("Der Knoten " + node.getName() + ": "
							+ node.getType().getName()
							+ " hat mehrere containment Kanten. ");
				}
			}

		}
		LinkedList<Node> allNodes = new LinkedList<Node>(graph.getNodes());
		while (allNodes.size() > 0) {
			Map<Node, List<List<Node>>> nodeWithPaths = new HashMap<Node, List<List<Node>>>();
			List<Node> pfad = new ArrayList<Node>();
			Node startNode = getNextStartNode(ePackage2NodeList, allNodes);
			addSuccessorNodes(nodeWithPaths, allNodes, startNode, pfad, true);
			while (nodeWithPaths.size() > 0) {

				Node node = nodeWithPaths.keySet().iterator().next();
				List<List<Node>> actualPaths = nodeWithPaths.get(node);
				nodeWithPaths.remove(node);
				for (List<Node> visitedNodes : actualPaths) {
					if (visitedNodes.contains(node)) {
						String s = "Der Graph enth�lt Zyklen. (";
						int index = visitedNodes.indexOf(node);
						for (int i = index; i < visitedNodes.size(); i++) {
							Node nn = visitedNodes.get(i);
							String name = " ";
							if (nn.getName() != null) {
								name += nn.getName();
							}
							name += ":" + nn.getType().getName();
							s += name + " ->";
						}
						String name = " ";
						if (node.getName() != null) {
							name += node.getName();
						}
						name += ":" + node.getType().getName();
						s += name + ")";

						fehlerMeldungen.add(s);
						continue;
					}
					List<Node> newVisitedNode = new ArrayList<Node>(
							visitedNodes);
					addSuccessorNodes(nodeWithPaths, allNodes, node,
							newVisitedNode, false);
				}
			}
		}
		if (fehlerMeldungen.size() == 0) {
			fehlerMeldungen.add("Alles Ok!");
		}
		ValidTestDialog vD = new ValidTestDialog(getWorkbenchPart().getSite()
				.getShell(), SWT.NULL, fehlerMeldungen);
		vD.open();

	}

	/**
	 * Gets the next start node.
	 * 
	 * @param ePackage2NodeList
	 *            the e package2 node list
	 * @param allNodes
	 *            the all nodes
	 * @return the next start node
	 */
	private Node getNextStartNode(
			Map<EPackage, LinkedList<Node>> ePackage2NodeList,
			LinkedList<Node> allNodes) {
		Node startNode;
		if (ePackage2NodeList.size() > 0) {
			EPackage eP = ePackage2NodeList.keySet().iterator().next();
			LinkedList<Node> wurzelnNodes = ePackage2NodeList.get(eP);
			startNode = wurzelnNodes.poll();
			if (wurzelnNodes.isEmpty()) {
				ePackage2NodeList.remove(eP);
			}
			allNodes.remove(startNode);
		} else {
			startNode = allNodes.poll();
		}
		return startNode;
	}

	/**
	 * Adds the successor nodes.
	 * 
	 * @param nodeWithPaths
	 *            the node with paths
	 * @param allNodes
	 *            the all nodes
	 * @param actualNode
	 *            the actual node
	 * @param pfad
	 *            the pfad
	 * @param nurNichtBesuchte
	 *            the nur nicht besuchte
	 */
	private void addSuccessorNodes(Map<Node, List<List<Node>>> nodeWithPaths,
			LinkedList<Node> allNodes, Node actualNode, List<Node> pfad,
			boolean nurNichtBesuchte) {
		pfad.add(actualNode);
		for (Edge edge : actualNode.getOutgoing()) {
			if (edge.getType().isContainment()) {
				Node target = edge.getTarget();
				if (!nurNichtBesuchte || allNodes.contains(target)
						|| target == actualNode) {
					allNodes.remove(target);
					if (nodeWithPaths.containsKey(target)) {
						nodeWithPaths.get(target).add(pfad);
					} else {
						List<List<Node>> newPaths = new ArrayList<List<Node>>();
						newPaths.add(pfad);
						nodeWithPaths.put(target, newPaths);

					}
				}
			}
		}
	}

	/**
	 * Gets the roots.
	 * 
	 * @return the roots
	 */
	private Map<EPackage, LinkedList<Node>> getRoots() {
		Map<EPackage, LinkedList<Node>> ePackage2NodeList = new HashMap<EPackage, LinkedList<Node>>();
		for (Node node : graph.getNodes()) {
			EPackage ePackge = node.getType().getEPackage();
			if (!ePackage2NodeList.containsKey(ePackge)) {
				ePackage2NodeList.put(ePackge, new LinkedList<Node>());
			}
			if (!NodeTypes.isContainment(node)) {
				ePackage2NodeList.get(node.getType().getEPackage()).add(node);
			}
		}
		return ePackage2NodeList;
	}

}
