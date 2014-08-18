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
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;

public abstract class GenerateOpRuleCommand extends ProcessRuleCommand {

	private static final String RULE_FOLDER = "RuleFolder";
	protected String OP_RULE_CONTAINER_PREFIX = "OPRule_";
	protected String OP_RULE_FOLDER = "OPRuleFolder";
	protected String OP_RULES_PNG = "OPRules.png";

	public GenerateOpRuleCommand(Rule rule) {
		this(rule,null);		
	}
	
	protected abstract boolean filterNode(TNode node);
	
	protected class OpRuleNodeProcessor implements NodeProcessor{
		private static final String REF_PREFIX = "ref";

		@Override
		public void process(Node oldNodeRHS, Node newNode) {

			Node ruleTNode = newNode;
			// case: node is marked to be created by the TGG rule, thus it shall be translated by the FT rule
			if (RuleUtil.NEW.equals(((TNode)oldNodeRHS).getMarkerType())){
				Node tNodeLHS = copyNodePure(oldNodeRHS, newNode.getGraph().getRule().getLhs());

				setNodeLayoutAndMarker(ruleTNode, oldNodeRHS,
						RuleUtil.Translated);
				// set marker [!tr] in LHS, for checking the matching constraint during execution 
				setNodeMarker(tNodeLHS, RuleUtil.Not_Translated_Graph);

				setMapping(tNodeLHS, ruleTNode);

				// update all markers for the attributes
				TAttribute newAttLHS = null;
				TAttribute newAttRHS = null;
				for (Attribute oldAttribute : oldNodeRHS.getAttributes()) {

					newAttRHS = (TAttribute) getCopiedObject(oldAttribute);
					if (RuleUtil.NEW.equals(newAttRHS.getMarkerType())){
						newAttLHS = (TAttribute) copyAtt(oldAttribute, tNodeLHS);
						setAttributeMarker(newAttRHS, RuleUtil.Translated);
						// marker needed for matching constraint
						setAttributeMarker(newAttLHS, RuleUtil.Not_Translated_Graph);

						setValueOfMarkedAttribute(newNode, newAttLHS,
								newAttRHS, oldAttribute);	

						
						
						
					}

				}

				oldRhsNodes2TRhsNodes.put(oldNodeRHS, ruleTNode);
				oldLhsNodes2TLhsNodes.put(RuleUtil.getLHSNode(oldNodeRHS),
						tNodeLHS);
			} else {
				// case: node is not created, i.e., in LHS or in NAC
				// set marker that it has to be translated already
				TAttribute tAttributeRHS = null;
				TAttribute tAttributeLHS = null;
				
				TNode tNodeLHS = (TNode) RuleUtil.getLHSNode(ruleTNode);
				// case: node is in NAC
				if (tNodeLHS == null) {
					if (RuleUtil.TR_UNSPECIFIED.equals(((TNode)oldNodeRHS).getMarkerType())){
							setNodeMarker(ruleTNode, RuleUtil.TR_UNSPECIFIED);				
					}
					else{
						setNodeMarker(ruleTNode, RuleUtil.Translated_Graph);				
					}
					for (Attribute attr : oldNodeRHS.getAttributes()) {
						tAttributeRHS = (TAttribute) getCopiedObject(attr);
						tAttributeLHS = (TAttribute) RuleUtil.getLHSAttribute(tAttributeRHS);
						// case: attribute in NAC has marker "unspecified"
						if (RuleUtil.TR_UNSPECIFIED.equals(tAttributeRHS
								.getMarkerType())){
							setAttributeMarker(tAttributeRHS,
									RuleUtil.TR_UNSPECIFIED);
						}
						// case: attribute in NAC has no marker, i.e. it has to be translated already
						else{
							setAttributeMarker(tAttributeRHS,
									RuleUtil.Translated_Graph);
						}
					}
					
				}

				// case: node is in LHS
				// set marker that it has to be translated already

				if(tNodeLHS!=null){
				// set marker in LHS, if node is in LHS (not in NAC)
				 setNodeMarker(ruleTNode, RuleUtil.Translated_Graph);
				 setNodeMarker(tNodeLHS, RuleUtil.Translated_Graph);
				
				TAttribute newAttLHS = null;

				for (Attribute attr : oldNodeRHS.getAttributes()) {
					tAttributeRHS = (TAttribute) getCopiedObject(attr);
					// case: attribute is created by the TGG rule
					if (RuleUtil.NEW.equals(((TAttribute)attr).getMarkerType())){
						newAttLHS = (TAttribute) copyAtt(attr, RuleUtil.getLHSNode((Node) tAttributeRHS.eContainer()));
						setAttributeMarker(tAttributeRHS, RuleUtil.Translated);
						// marker needed for matching constraint
						setAttributeMarker(newAttLHS, RuleUtil.Not_Translated_Graph);

						setValueOfMarkedAttributeInPreservedNode(newNode, attr,
								tAttributeRHS, newAttLHS);	
					}
					// case: attribute is not created by the TGG rule
					else{
						// set marker that it has to be translated already
						tAttributeLHS = (TAttribute) RuleUtil.getLHSAttribute(tAttributeRHS);
						setAttributeMarker(tAttributeRHS, RuleUtil.Translated_Graph);							
						setAttributeMarker(tAttributeLHS, RuleUtil.Translated_Graph);							
					}

				}
			}
			}
		}

		private void setValueOfMarkedAttributeInPreservedNode(Node newNode,
				Attribute oldAttribute, TAttribute tAttributeRHS, TAttribute newAttLHS) {
			final LinkedHashSet<String> usedVars = new LinkedHashSet<String>();
			final LinkedHashSet<String> definedVars = new LinkedHashSet<String>();

			usedVars.removeAll(definedVars);//local definition override global vars

			// case: node has name identifier - then replace attribute value by parameter
			if (newNode.getName() != null && !newNode.getName().isEmpty() && newNode.getName().startsWith(REF_PREFIX) && (newNode.getName().charAt(0) < '0' || newNode.getName().charAt(0) > '9')){

				replaceAttributeValueByStructuredName(newNode, newAttLHS,
						tAttributeRHS);

			} else {

				// case: attribute value is an expression
				if (newNode.getGraph().getRule().getParameter(oldAttribute.getValue()) == null){
					    convertAttExpressionToAttCondition(newNode, oldAttribute.getValue(),
					    		newAttLHS, tAttributeRHS);
						
				} 

				
				for (Iterator<Parameter> itr = unassignedParameters.iterator(); itr.hasNext();) {
					Parameter p = itr.next();
					if (usedVars.contains(p.getName())){
						newAttLHS.setValue(p.getName());
						tAttributeRHS.setValue(p.getName());
						itr.remove();
					}
				}
			}
		}

		private void setValueOfMarkedAttribute(Node newNode,
				TAttribute newAttLHS, TAttribute newAttRHS,
				Attribute oldAttribute) {
			// case: node has name identifier - then replace attribute value by parameter
			if (newNode.getName() != null && !newNode.getName().isEmpty() && newNode.getName().startsWith(REF_PREFIX) && (newNode.getName().charAt(0) < '0' || newNode.getName().charAt(0) > '9')){
				replaceAttributeValueByStructuredName(newNode, newAttLHS,
						newAttRHS);

			} else {

				// case: attribute value is an expression
				if (newNode.getGraph().getRule().getParameter(oldAttribute.getValue()) == null){
					    convertAttExpressionToAttCondition(newNode,oldAttribute.getValue(),
					    		newAttLHS, newAttRHS);
						
				} 

				
				
				
				
			}
		}

		private void convertAttExpressionToAttCondition(Node newNode,
				String oldAttValue, Attribute newAttLHS, TAttribute newAttRHS) {
			// replace attribute value by new parameter 
			String parameter = getFreshParameterName("in_"+newAttRHS.getType().getName(),newNode.getGraph().getRule()); 
			Parameter p = HenshinFactory.eINSTANCE.createParameter(parameter);
			newNode.getGraph().getRule().getParameters().add(p);
			newAttLHS.setValue(p.getName());
			newAttRHS.setValue(p.getName());
			
			// create attribute condition to check the value
			AttributeCondition attCondition = HenshinFactory.eINSTANCE.createAttributeCondition();
			attCondition.setConditionText(parameter+"=="+oldAttValue);
			newNode.getGraph().getRule().getAttributeConditions().add(attCondition);
		}

		private void replaceAttributeValueByStructuredName(Node newNode,
				TAttribute newAttLHS, TAttribute newAttRHS) {
			String parameter = newNode.getName() + "_" + newAttLHS.getType().getName();
			newAttLHS.setValue(parameter);
			newAttRHS.setValue(parameter);

			if (newNode.getGraph().getRule().getParameter(parameter) == null){
				Parameter p = HenshinFactory.eINSTANCE.createParameter(parameter);
				//parameter.setType(newAttLHS.getType().eClass());
				newNode.getGraph().getRule().getParameters().add(p);
			}
		}
		
		private String getFreshParameterName(String name,
				Rule rule) {
			String new_name=name;
			int i=1;
			
			while(rule.getParameter(new_name)!=null)
			{   
				i++;
				new_name=name+"_"+i;
			}
			return new_name;
		}

		@Override
		public boolean filter(Node oldNode, Node newNode) {
			return true;
		}
		
		
	}
	
	protected class OpRuleEdgeProcessor implements EdgeProcessor {
		
		@Override
		public void process(Edge oldEdge, Edge newEdge) {
			TEdge newTEdge = (TEdge) newEdge;
		
			// case: edge is marked to be created by the TGG rule, thus it
			// shall be translated by the FT rule
			if (RuleUtil.NEW.equals(((TEdge) oldEdge).getMarkerType())) {
				setEdgeMarker(newEdge, RuleUtil.Translated);

				// LHS
				Node sourceTNodeLHS = RuleUtil.getLHSNode(newEdge
						.getSource());
				Node targetTNodeLHS = RuleUtil.getLHSNode(newEdge
						.getTarget());

				// LHS
				Edge tEdgeLHS = copyEdge(oldEdge, tRuleLhs);
				newEdge.getGraph().getRule().getLhs().getEdges()
						.add(tEdgeLHS);
				tEdgeLHS.setSource(sourceTNodeLHS);
				tEdgeLHS.setTarget(targetTNodeLHS);
				// for matching constraint
				setEdgeMarker(tEdgeLHS, RuleUtil.Not_Translated_Graph);
			}
			// case: edge is not created by the TGG rule
			else {
				// case: edge is in NAC and has an unspecified marker
				if (RuleUtil.TR_UNSPECIFIED.equals(newTEdge.getMarkerType()))
					newTEdge.setMarkerType(RuleUtil.TR_UNSPECIFIED);
				else {
					// mark the edge to be translated already
					setEdgeMarker(newEdge, RuleUtil.Translated_Graph);

					// handle LHS edge
					TEdge tEdgeLHS = (TEdge) RuleUtil.getLHSEdge(newEdge);
					if (tEdgeLHS != null)
						// case: edge is in RHS graph
						setEdgeMarker(tEdgeLHS, RuleUtil.Translated_Graph);
				}
			}
			
		}
		
		@Override
		public boolean filter(Edge oldEdge) {
			return filterNode((TNode)oldEdge.getSource()) && filterNode((TNode)oldEdge.getTarget());
		}
		
	}
	
	private LinkedList<Parameter> unassignedParameters = new LinkedList<Parameter>();

	public GenerateOpRuleCommand(Rule rule,IndependentUnit unit) {
		super(rule,unit);
		unassignedParameters.addAll(rule.getParameters());

		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr  : node.getAttributes()) {
				for (Iterator<Parameter> itr = unassignedParameters.iterator(); itr.hasNext();) {
					Parameter p = itr.next();
					if (p.getName().equals(attr.getValue()))
					{
						itr.remove();
					}
				}
			}
		}
		
		addNodeProcessors();
		edgeProcessors.add(new OpRuleEdgeProcessor());
		
	}
	
	protected abstract void addNodeProcessors();
	
	public IndependentUnit getContainer(IndependentUnit container){
		Unit opRuleContainer;
		if (container != null && !container.getName().equals(RULE_FOLDER) ){
			Module m = (Module) EcoreUtil.getRootContainer(oldRule);
			opRuleContainer = m.getUnit(prefix + container.getName());
			if (!(opRuleContainer instanceof IndependentUnit)){
				if (opRuleContainer != null){
					opRuleContainer.setName(OP_RULE_CONTAINER_PREFIX + opRuleContainer.getName());
				} 
				opRuleContainer = HenshinFactory.eINSTANCE.createIndependentUnit();
				opRuleContainer.setName(prefix + container.getName());
				opRuleContainer.setDescription(OP_RULES_PNG);
				m.getUnits().add(opRuleContainer);
				((IndependentUnit)m.getUnit(OP_RULE_FOLDER)).getSubUnits().add(opRuleContainer);
			} 
		} else {
			Module m = (Module) EcoreUtil.getRootContainer(oldRule);
			opRuleContainer = m.getUnit(OP_RULE_FOLDER);
		}
		return (IndependentUnit) opRuleContainer;
	}

	protected abstract void deleteTRule(Rule tr);
	
	@Override
	protected void preProcess() {
		for (Unit tr : tgg.getUnits()) {
			TGGRule rule = null;
			if (tr instanceof TGGRule) {
				rule = (TGGRule) tr;
				if (rule.getName().equals(prefix + oldRule.getName())) {
					// there is already a TRule for this rule -> delete the old
					// one
					this.update = true;
					this.oldruleIndex = tgg.getUnits().indexOf(rule);
					deleteTRule(rule);
					break;
				}
			}
		}
	}
	
}
