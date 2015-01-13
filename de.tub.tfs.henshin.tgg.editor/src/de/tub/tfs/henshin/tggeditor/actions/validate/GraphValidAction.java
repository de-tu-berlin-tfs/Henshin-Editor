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
package de.tub.tfs.henshin.tggeditor.actions.validate;

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

import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tggeditor.dialogs.ValidTestDialog;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphTreeEditPart;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;


/**
 * The class GraphValidAction validates the graph if it is emf consistent.
 */
public class GraphValidAction extends SelectionAction {

	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.GraphValidAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Validate Graph";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Validate Graph";

	/** The graph which is supposed to be vaildated. */
	protected Graph graph;
	
	/**
	 * The constructor receives a part of the class and calls it IWorkbenchPart
	 * Its super constructor.
	 * Setting the tooltip text, and description of the action and the action ID.
	 * In addition given a description.
	 *
	 * @param part the part
	 */
	public GraphValidAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof GraphTreeEditPart) {
				graph = (Graph) editpart.getModel();
				return true;
			}
		}
		return false;
	}

	/**
	 * In the run () - method checks the validity of the graph,
	 * This is to check the root node and containment edges.
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		List<String> fehlerMeldungen = new ArrayList<String>();
		checkGraphValid(fehlerMeldungen);
		openDialog(fehlerMeldungen);

	}

	protected void openDialog(List<String> fehlerMeldungen) {
		if (fehlerMeldungen.size() == 0) {
			fehlerMeldungen.add("Everything Ok!");
		}
		ValidTestDialog vD = new ValidTestDialog(getWorkbenchPart().getSite()
				.getShell(), SWT.NULL, fehlerMeldungen);
		vD.open();
	}

	protected void checkGraphValid(List<String> fehlerMeldungen) {
		Map<EPackage, LinkedList<Node>> ePackage2NodeList = getRoots();
		Set<EPackage> ePackages=ePackage2NodeList.keySet();
		for (EPackage ePackage:ePackages){
			LinkedList<Node> rootNodes=ePackage2NodeList.get(ePackage);
			if (rootNodes.size()!=1){
				if (rootNodes.size() == 0) {
					fehlerMeldungen.add("The "+ePackage.getName()+"-graph has no rootnode!");
					ePackage2NodeList.remove(ePackage);
				}
				if (rootNodes.size()>1){
					TripleComponent type = NodeTypes.getTripleComponent(rootNodes.get(0));
					if (type != TripleComponent.CORRESPONDENCE) {
						fehlerMeldungen.add("The "+ePackage.getName()+"-graph has more than one rootnode!");
					}
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
					fehlerMeldungen.add("The node \""  + node.getName() + ": " + node.getType().getName() + 
							"\" has more than one containment edges!");
				}
			}

		}
		LinkedList<Node> allNodes = new LinkedList<Node>(graph.getNodes());
		while (allNodes.size() > 0) {
			Map<Node, List<List<Node>>> nodeWithPaths = new HashMap<Node, List<List<Node>>>();
			List<Node> pfad = new ArrayList<Node>();
			Node startNode = getNextStartNode(ePackage2NodeList, allNodes);
			addSuccessorNodes(nodeWithPaths, allNodes, startNode, pfad,
					true);
			while (nodeWithPaths.size() > 0) {

				Node node = nodeWithPaths.keySet().iterator().next();
				List<List<Node>> actualPaths = nodeWithPaths.get(node);
				nodeWithPaths.remove(node);
				for (List<Node> visitedNodes : actualPaths) {
					if (visitedNodes.contains(node)) {
						String s="The graph contains a cycle! (";
						int index=visitedNodes.indexOf(node);
						for (int i=index;i<visitedNodes.size();i++){
							Node nn=visitedNodes.get(i);
							String name=" ";
							if (nn.getName()!=null){
								name+=nn.getName();
							}
							name+=":"+nn.getType().getName();
							s+=name+" ->";
						}
						String name=" ";
						if (node.getName()!=null){
							name+=node.getName();
						}
						name+=":"+node.getType().getName();
						s+=name+")";

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
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
//	 */
//	@Override
//	public ImageDescriptor getImageDescriptor() {
//		return IconUtil.getDescriptor("check18.png");
//	}
	
	/**
	 * Gets the next start node.
	 *
	 * @param ePackage2NodeList the e package2 node list
	 * @param allNodes the all nodes
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
	 * @param nodeWithPaths the node with paths
	 * @param allNodes the all nodes
	 * @param actualNode the actual node
	 * @param pfad the pfad
	 * @param nurNichtBesuchte the nur nicht besuchte
	 */
	private void addSuccessorNodes(
			Map<Node, List<List<Node>>> nodeWithPaths,
			LinkedList<Node> allNodes, Node actualNode, List<Node> pfad,
			boolean nurNichtBesuchte) {
		pfad.add(actualNode);
		for (Edge edge : actualNode.getOutgoing()) {
			if (edge.getType().isContainment()) {
				Node target = edge.getTarget();
				if (!nurNichtBesuchte || allNodes.contains(target) || target==actualNode) {
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
	protected Map<EPackage, LinkedList<Node>> getRoots(){
		Map<EPackage, LinkedList<Node>> ePackage2NodeList = new HashMap<EPackage, LinkedList<Node>>();
		for (Node node : graph.getNodes()) {
			EPackage ePackge= node.getType().getEPackage();
			if (!ePackage2NodeList.containsKey(ePackge)){
				ePackage2NodeList.put(ePackge, new LinkedList<Node>());
			}
			if (!NodeTypes.isContainment(node)) {
					ePackage2NodeList.get(
							node.getType().getEPackage()).add(node);
			}
		}
		return ePackage2NodeList;
	}

}
