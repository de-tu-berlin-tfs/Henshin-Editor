package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
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
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

public class GenerateFTRuleCommand extends ProcessRuleCommand {

	public GenerateFTRuleCommand(Rule rule) {
		this(rule,null);
		
	}
	
	private LinkedList<Parameter> unassignedParameters = new LinkedList<Parameter>();

	public GenerateFTRuleCommand(Rule rule,IndependentUnit unit) {
		super(rule,unit);
		prefix = "FT_";

		
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
		
		// process all nodes in the source component
		nodeProcessors.put(TripleComponent.SOURCE, new NodeProcessor() {
			@Override
			public void process(Node oldNodeRHS, Node newNode) {

				Node ruleTNode = newNode;
				// case: node is marked to be created by the TGG rule, thus it shall be translated by the FT rule
				if (RuleUtil.NEW.equals(((TNode)oldNodeRHS).getMarkerType())){
					Node tNodeLHS = copyNodePure(oldNodeRHS, newNode.getGraph().getRule().getLhs());

					setNodeLayoutAndMarker(ruleTNode, oldNodeRHS,
							RuleUtil.Translated);
					// set marker also in LHS, for checking the matching constraint during execution 
					setNodeMarker(tNodeLHS, RuleUtil.Translated);

					setMapping(tNodeLHS, ruleTNode);

					// update all markers for the attributes
					TAttribute newAttLHS = null;
					TAttribute newAttRHS = null;
					for (Attribute oldAttribute : oldNodeRHS.getAttributes()) {

						newAttRHS = (TAttribute) getCopiedObject(oldAttribute);
						if (newAttRHS.getMarkerType() != null && newAttRHS.getMarkerType().equals(RuleUtil.NEW)){
							newAttLHS = (TAttribute) copyAtt(oldAttribute, tNodeLHS);
							setAttributeMarker(newAttRHS, RuleUtil.Translated);
							// marker needed for matching constraint
							setAttributeMarker(newAttLHS, RuleUtil.Translated);


							if (newNode.getName() != null && !newNode.getName().isEmpty() && newNode.getName().startsWith("ref") && (newNode.getName().charAt(0) < '0' || newNode.getName().charAt(0) > '9')){
								String parameter = newNode.getName() + "_" + newAttLHS.getType().getName();
								newAttLHS.setValue(parameter);
								newAttRHS.setValue(parameter);

								if (newNode.getGraph().getRule().getParameter(parameter) == null){
									Parameter p = HenshinFactory.eINSTANCE.createParameter(parameter);
									//parameter.setType(newAttLHS.getType().eClass());
									newNode.getGraph().getRule().getParameters().add(p);
								}

							} else {

							}	

						}

					}

					oldRhsNodes2TRhsNodes.put(oldNodeRHS, ruleTNode);
					oldLhsNodes2TLhsNodes.put(RuleUtil.getLHSNode(oldNodeRHS),
							tNodeLHS);
				} else {
					// case: node is not created, i.e., in LHS or in NAC
					// set marker that it has to be translated already
					setNodeMarker(ruleTNode, RuleUtil.Translated_Graph);
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
							if (RuleUtil.TR_UNSPECIFIED.equals(tAttributeRHS
									.getMarkerType())){
								setAttributeMarker(tAttributeRHS,
										RuleUtil.TR_UNSPECIFIED);
							}
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
					 setNodeMarker(tNodeLHS, RuleUtil.Translated_Graph);
					
					TAttribute newAttLHS = null;

					for (Attribute attr : oldNodeRHS.getAttributes()) {
						tAttributeRHS = (TAttribute) getCopiedObject(attr);
						// case: attribute is created by the TGG rule
						if (RuleUtil.NEW.equals(((TAttribute)attr).getMarkerType())){
							newAttLHS = (TAttribute) copyAtt(attr, RuleUtil.getLHSNode((Node) tAttributeRHS.eContainer()));
							setAttributeMarker(tAttributeRHS, RuleUtil.Translated);
							// marker needed for matching constraint
							setAttributeMarker(newAttLHS, RuleUtil.Translated);

							final LinkedHashSet<String> usedVars = new LinkedHashSet<String>();
							final LinkedHashSet<String> definedVars = new LinkedHashSet<String>();

							usedVars.removeAll(definedVars);//local definition override global vars

							if (newNode.getName() != null && !newNode.getName().isEmpty() && newNode.getName().startsWith("ref") && (newNode.getName().charAt(0) < '0' || newNode.getName().charAt(0) > '9')){
								String parameter = newNode.getName() + "_" + newAttLHS.getType().getName();
								newAttLHS.setValue(parameter);
								tAttributeRHS.setValue(parameter);

								if (newNode.getGraph().getRule().getParameter(parameter) == null){
									Parameter p = HenshinFactory.eINSTANCE.createParameter(parameter);
									//parameter.setType(newAttLHS.getType().eClass());
									newNode.getGraph().getRule().getParameters().add(p);
								}

							} else {

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
			
			@Override
			public boolean filter(Node oldNode, Node newNode) {
				return true;
			}
		});
		
		edgeProcessors.add(new EdgeProcessor() {
			
			@Override
			public void process(Edge oldEdge, Edge newEdge) {
			
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
					setEdgeMarker(tEdgeLHS, RuleUtil.Translated);
				}
				// case: edge is not created by the TGG rule
				else{
					// mark the edge to be translated already
					TEdge tEdgeLHS = (TEdge) RuleUtil.getLHSEdge(newEdge);
					setEdgeMarker(newEdge, RuleUtil.Translated_Graph);
					setEdgeMarker(tEdgeLHS, RuleUtil.Translated_Graph);
					
				}
				
			}
			
			@Override
			public boolean filter(Edge oldEdge, Edge newEdge) {
				return NodeUtil.isSourceNode((TNode) oldEdge.getSource())
						&& NodeUtil.isSourceNode((TNode) oldEdge.getTarget()) 
						//&& RuleUtil.NEW.equals(((TEdge)oldEdge).getMarkerType()) 
						;
			}
		});
		
	}
	
	public IndependentUnit getContainer(IndependentUnit container){
		Unit ftContainer;
		if (container != null && !container.getName().equals("RuleFolder") ){
			Module m = (Module) EcoreUtil.getRootContainer(oldRule);
			ftContainer = m.getUnit("FT_" + container.getName());
			if (!(ftContainer instanceof IndependentUnit)){
				if (ftContainer != null){
					ftContainer.setName("FTRule_" + ftContainer.getName());
				} 
				ftContainer = HenshinFactory.eINSTANCE.createIndependentUnit();
				ftContainer.setName("FT_" + container.getName());
				ftContainer.setDescription("FTRules.png");
				m.getUnits().add(ftContainer);
				((IndependentUnit)m.getUnit("FTRuleFolder")).getSubUnits().add(ftContainer);
			} 
		} else {
			Module m = (Module) EcoreUtil.getRootContainer(oldRule);
			ftContainer = m.getUnit("FTRuleFolder");
		}
		return (IndependentUnit) ftContainer;
	}

	@Override
	protected void preProcess() {
		for (TRule tr : tgg.getTRules()) {
			Module module = oldRule.getModule();
			this.truleIndex = tgg.getTRules().indexOf(tr);

			if (tr.getRule().getName().equals(prefix + oldRule.getName())) {
				// there is already a TRule for this rule -> delete the old one
				this.update = true;
				this.oldruleIndex = module.getUnits().indexOf(tr.getRule());
				
				DeleteFTRuleCommand deleteCommand = new DeleteFTRuleCommand(
						tr.getRule(),null);
				deleteCommand.execute();
				break;
			}
		}
	}
	
	@Override
	protected String getRuleMarker() {
		return RuleUtil.TGG_FT_RULE;
	}
}
