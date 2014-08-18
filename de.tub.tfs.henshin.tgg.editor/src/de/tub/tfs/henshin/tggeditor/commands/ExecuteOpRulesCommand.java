/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.TggTransformation;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformationImpl;
import de.tub.tfs.henshin.tgg.interpreter.impl.TranslationMaps;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.TggUtil;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteOpRulesCommand extends CompoundCommand {

	protected String consistencyType=null;
	protected String consistencyTypeLowerCase=null;	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	/**
	 * The list of the Rules ({@link TRule}).
	 */
	protected List<Rule> opRuleList;

	


	
	/**
	 * List of the successful RuleApplications.
	 */
	protected ArrayList<RuleApplicationImpl> ruleApplicationList= new ArrayList<RuleApplicationImpl>();

	public ArrayList<RuleApplicationImpl> getRuleApplicationList() {
		return ruleApplicationList;
	}





	private TggTransformation tggTrafo = null;
	protected TranslationMaps translationMaps = null;
	protected HashMap<EObject, Boolean> isTranslatedNodeMap = null;
	protected HashMap<EObject, HashMap<EAttribute, Boolean>> isTranslatedAttributeMap = null;
	protected HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap = null;
	
	protected Map<Node,EObject> node2eObject;
	protected Map<EObject, Node> eObject2Node;

	
	
	/**the constructor
	 * @param graph {@link ExecuteOpRulesCommand#graph}
	 * @param opRuleList {@link ExecuteOpRulesCommand#opRuleList}
	 */
	public ExecuteOpRulesCommand(Graph graph, List<Rule> opRuleList) {
		super();
		this.graph = graph;
		this.opRuleList = opRuleList;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (graph != null && !opRuleList.isEmpty());
	}
	
	/** Executes all the rules on the graph as long as it's possible. The choosing of the sequence 
	 * of RuleApplications is determinated by the order in the {@link ExecuteOpRulesCommand#opRuleList}. 
	 * So when you execute the command twice without changing the order in the list, the same sequence
	 * of applications is chosen.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		tggTrafo =  new TggTransformationImpl();

		// create an Egraph from the triple graph 
		final TggHenshinEGraph henshinGraph = new TggHenshinEGraph(graph);

		
//		List<EObject> eObjects = new Vector<EObject>();
//		eObject2Node = henshinGraph.getObject2NodeMap();
		node2eObject = henshinGraph.getNode2ObjectMap();
//		for (Node n: eObject2Node.values()){
//			if(n instanceof TNode && ((TNode)n).getMarkerType()!=null)
//				eObjects.add(node2eObject.get(n));
//		}
		tggTrafo.setInput(henshinGraph);

		Module module = TggUtil.getModuleFromElement(graph);
		tggTrafo.setNullValueMatching(module.isNullValueMatching());
		tggTrafo.setOpRuleList(opRuleList);

		// execute the transformation
		tggTrafo.applyRules();

		// update the node positions of the created nodes
		createNodePositions(henshinGraph);
		
		// set the graph element markers according to translation maps
		setGraphMarkers();
		
	}



	private void createNodePositions(final TggHenshinEGraph henshinGraph) {
		ruleApplicationList= tggTrafo.getRuleApplicationList();
		
		int index=0;
		for (RuleApplication r: ruleApplicationList){
		index++;
			createNodePositions(r, henshinGraph,
					index * 40);
		}
	}

	private void setGraphMarkers() {
		translationMaps = tggTrafo.getTranslationMaps();
		isTranslatedNodeMap = translationMaps.getIsTranslatedNodeMap();
		isTranslatedEdgeMap = translationMaps.getIsTranslatedEdgeMap();
		isTranslatedAttributeMap = translationMaps.getIsTranslatedAttributeMap();

		for (Node n : graph.getNodes()) {
			TNode node = (TNode) n;
			EObject graphNodeEObject = node2eObject.get(node);
			// set node component using the hash map from the transformation
			node.setComponent(tggTrafo.getTripleComponentNodeMap().get(graphNodeEObject));
			if (isTranslatedNodeMap.containsKey(graphNodeEObject)) {
				// set marker type to mark the translated nodes
				node.setMarkerType(RuleUtil.Not_Translated_Graph);

				if (isTranslatedNodeMap.get(graphNodeEObject)) {
					// mark the translated node
					node.setMarkerType(RuleUtil.Translated_Graph);
				}
				// check contained attributes
				for (Attribute at : node.getAttributes()) {
					// set marker type to mark the translated attributes
					TAttribute a = (TAttribute) at;
					a.setMarkerType(RuleUtil.Not_Translated_Graph);
					if(!isTranslatedAttributeMap.get(graphNodeEObject).containsKey(a.getType()))
						System.out.println("Inconsistent marking: attribute" + a.getType() + "=" + a.getValue() 
								+ " is not marked, but its container node is marked.");
					else if (isTranslatedAttributeMap.get(graphNodeEObject).get(a.getType())) {
						// mark the translated attribute
						a.setMarkerType(RuleUtil.Translated_Graph);
					}
				}
			}
			else // node is not in marked component 
				{
				node.setMarkerType(null);
				for (Attribute at : node.getAttributes()) {
					TAttribute a = (TAttribute) at;
					a.setMarkerType(null);
				}
				
				
			}
		}
		for (Edge e : graph.getEdges()) {
			TEdge edge = (TEdge) e;
			EObject sourceNodeEObject = node2eObject.get(e.getSource());
			EObject targetNodeEObject = node2eObject.get(e.getTarget());
			
			if (isTranslatedEdgeMap.get(sourceNodeEObject)!=null &&
			isTranslatedEdgeMap.get(sourceNodeEObject).get(edge.getType())!=null && 
			isTranslatedEdgeMap.get(sourceNodeEObject).get(edge.getType()).containsKey(targetNodeEObject)) 
			{
				// set marker type to mark the translated attributes
				edge.setMarkerType(RuleUtil.Not_Translated_Graph);

				if (isTranslatedEdgeMap.get(sourceNodeEObject).get(edge.getType()).get(targetNodeEObject)) {
					// mark the translated edge
					edge.setMarkerType(RuleUtil.Translated_Graph);
				}
			}
			else // edge is not in marked component - delete markers
			{
				edge.setMarkerType(null);
			}
		}
		return;
	}		
	




	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
//		for (int i = ruleApplicationList.size()-1; i>=0; i--) {
//			ruleApplicationList.get(i).undo(null);
//		}
		// TODO: revise
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
//		for (RuleApplicationImpl rp : ruleApplicationList) {
//			rp.redo(null);
//		}
		// TODO: revise
	}
	
	
	/**
	 * Creates the node layouts for the new nodes in the graph. The coordinates 
	 * are calculated for each new node in the graph. relative to the next node. 
	 * 
	 * @param ruleApplication the rule application with the applied rule
	 * @param henshinGraph henshingraph on which the rule was aplied
	 * @param deltaY adds the value to the y coordinate of all generated layouts
	 */
	protected static void createNodePositions(RuleApplication ruleApplication,
			TggHenshinEGraph henshinGraph, int deltaY) {
		
		Rule rule = ruleApplication.getRule();
		
		EList<Node> ruleNodes = (EList<Node>) rule.getRhs().getNodes();
		// store rule nodes in two lists of preserved and created nodes
		ArrayList<TNode> createdRuleNodes = new ArrayList<TNode>();
		ArrayList<TNode> preservedRuleNodes = new ArrayList<TNode>();
		TNode rNode;
		for (Node rn : ruleNodes) {
			rNode = (TNode) rn;
			if (NodeUtil.isNew(rNode)) {
				createdRuleNodes.add(rNode);
			} else {
				preservedRuleNodes.add(rNode);
			}
		}
		
		Match comatch = ruleApplication.getResultMatch();
		Map<EObject, Node> eObject2graphNode = henshinGraph.getObject2NodeMap();
		for (TNode createdRuleNode : createdRuleNodes) {
			
			//find next preservedRuleNode
			Point createdRnPoint = new Point(createdRuleNode.getX(), createdRuleNode.getY());
			TNode closestRn = createdRuleNode;
			double bestDistance = Double.MAX_VALUE;
			for (TNode preservedRn : preservedRuleNodes) {
				Point preservedRnP = new Point(preservedRn.getX(), preservedRn.getY());
				double curDistance = createdRnPoint.getDistance(preservedRnP);
				if (curDistance < bestDistance) {
					bestDistance = curDistance;
					closestRn = preservedRn;
				}
			}
			
			//get graph node at closest position
			EObject closestGraphEObject = comatch.getNodeTarget(closestRn);
			TNode closestGraphNode = (TNode) eObject2graphNode.get(closestGraphEObject);
						
			//get created graph node
			EObject createdGraphEObject = comatch.getNodeTarget(createdRuleNode);
			TNode createdGraphNode = (TNode) eObject2graphNode.get(createdGraphEObject);	

			//set Point for created graph node as closestGraphNode.Point+distance
			int dX, dY;
			if (closestRn == createdRuleNode) { 
				// there is no preserved rule node, thus use the position of the rule node
				dX = createdRuleNode.getX();
				dY = createdRuleNode.getY();
			} else {
				dX = createdRuleNode.getX() - closestRn.getX();
				dY = createdRuleNode.getY() - closestRn.getY();
			}
			int x = closestGraphNode.getX() + dX;
			int y = closestGraphNode.getY() + dY;
			
			if (NodeUtil.isCorrespondenceNode(createdGraphNode)){
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() > x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() + 20;
				}
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() < x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() + 20;
				}
			} else if (NodeUtil.isTargetNode(createdGraphNode)){
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() > x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() + 20;
				}
			}
			
			createdGraphNode.setY(y+deltaY);
			createdGraphNode.setX(x);
		}
	}

}
