package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Mapping;
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
		
		nodeProcessors.put(TripleComponent.SOURCE, new NodeProcessor() {
			private HashMap<String,Node> parameterToNodeMap = new HashMap<String, Node>();
			@Override
			public void process(Node oldNodeRHS, Node newNode) {
				if (RuleUtil.NEW.equals(((TNode)oldNodeRHS).getMarkerType())){

					Node tNodeRHS = newNode;

					Node tNodeLHS = copyNodePure(oldNodeRHS, newNode.getGraph().getRule().getLhs());

					setNodeLayoutAndMarker(tNodeRHS, oldNodeRHS,
							RuleUtil.Translated);
					// set marker also in LHS, for checking the matching constraint during execution 
					setNodeMarker(tNodeLHS, oldNodeRHS);

					setMapping(tNodeLHS, tNodeRHS);

					// update all markers for the attributes
					TAttribute newAttLHS = null;
					TAttribute newAttRHS = null;
					for (Attribute oldAttribute : oldNodeRHS.getAttributes()) {

						newAttRHS = (TAttribute) getCopiedObject(oldAttribute);
						if (newAttRHS.getMarkerType() != null && newAttRHS.getMarkerType().equals(RuleUtil.NEW)){
							newAttLHS = (TAttribute) copyAtt(oldAttribute, tNodeLHS);
							setAttributeMarker(newAttRHS, oldAttribute);
							// marker needed for matching constraint
							setAttributeMarker(newAttLHS, oldAttribute);

							final LinkedHashSet<String> usedVars = new LinkedHashSet<String>();
							final LinkedHashSet<String> definedVars = new LinkedHashSet<String>();

							if (newNode.getName() != null && !newNode.getName().isEmpty()){
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

					oldRhsNodes2TRhsNodes.put(oldNodeRHS, tNodeRHS);
					oldLhsNodes2TLhsNodes.put(RuleUtil.getLHSNode(oldNodeRHS),
							tNodeLHS);
					
					
					//if (newNode.getName() != null && !newNode.getName().isEmpty()){
						//newRule.setInjectiveMatching(false);
					//	parameterToNodeMap.put(newNode.getName(), tNodeLHS);
				//	}
				} else {
					
					//if (newNode.getName() != null && !newNode.getName().isEmpty()){
						//newRule.setInjectiveMatching(false);
					//	Node lhsNode = parameterToNodeMap.get(newNode.getName());
					//	if (lhsNode != null){
					//		Mapping m = HenshinFactory.eINSTANCE.createMapping(lhsNode, newNode);
					//		newRule.getMappings().add(m);
					//	}
					//}
					TAttribute newAttLHS = null;
					TAttribute newAttRHS = null;

					for (Attribute attr : oldNodeRHS.getAttributes()) {
						if (RuleUtil.NEW.equals(((TAttribute)attr).getMarkerType())){
							newAttRHS = (TAttribute) getCopiedObject(attr);
							newAttLHS = (TAttribute) copyAtt(attr, RuleUtil.getLHSNode((Node) newAttRHS.eContainer()));
							setAttributeMarker(newAttRHS, attr);
							// marker needed for matching constraint
							setAttributeMarker(newAttLHS, attr);

							final LinkedHashSet<String> usedVars = new LinkedHashSet<String>();
							final LinkedHashSet<String> definedVars = new LinkedHashSet<String>();

							usedVars.removeAll(definedVars);//local definition override global vars

							if (newNode.getName() != null && !newNode.getName().isEmpty()){
								String parameter = newNode.getName() + "_" + newAttLHS.getType().getName();
								newAttLHS.setValue(parameter);
								newAttRHS.setValue(parameter);

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
										newAttRHS.setValue(p.getName());
										itr.remove();
									}
								}
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
			
				Node sourceTNodeRHS = oldRhsNodes2TRhsNodes.get(oldEdge.getSource());
				Node targetTNodeRHS = oldRhsNodes2TRhsNodes.get(oldEdge.getTarget());

				setEdgeMarker(newEdge,oldEdge,RuleUtil.Translated);
				

				// LHS
				Node sourceTNodeLHS = RuleUtil.getLHSNode(newEdge.getSource());
				Node targetTNodeLHS = RuleUtil.getLHSNode(newEdge.getTarget());

				// LHS
				Edge tEdgeLHS = copyEdge(oldEdge, tRuleLhs);
				newEdge.getGraph().getRule().getLhs().getEdges().add(tEdgeLHS);
				tEdgeLHS.setSource(sourceTNodeLHS);
				tEdgeLHS.setTarget(targetTNodeLHS);
				// for matching constraint
				setEdgeMarker(tEdgeLHS,oldEdge,RuleUtil.Translated);

				
			}
			
			@Override
			public boolean filter(Edge oldEdge, Edge newEdge) {
				// TODO Auto-generated method stub
				return NodeUtil.isSourceNode((TNode) oldEdge.getSource())
						&& NodeUtil.isSourceNode((TNode) oldEdge.getTarget()) &&
						RuleUtil.NEW.equals(((TEdge)oldEdge).getMarkerType()) ;
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
		// TODO Auto-generated method stub
		return RuleUtil.TGG_FT_RULE;
	}
}
