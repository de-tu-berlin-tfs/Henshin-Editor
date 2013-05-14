package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

public class GenerateBTRuleCommand extends ProcessRuleCommand {

	public GenerateBTRuleCommand(Rule rule) {
		this(rule,null);
		
	}
	public GenerateBTRuleCommand(Rule rule,IndependentUnit unit) {
		super(rule,unit);
		prefix = "BT_";
		nodeProcessors.put(TripleComponent.TARGET, new NodeProcessor() {
			
			@Override
			public void process(Node oldNodeRHS, Node newNode) {
				Node tNodeRHS = newNode;
				
				Node tNodeLHS = copyNodePure(oldNodeRHS, newNode.getGraph().getRule().getLhs());

				setNodeLayoutAndMarker(tNodeRHS, oldNodeRHS,
						RuleUtil.Translated);
				// set marker also in LHS, for checking the matching constraint during execution 
				setNodeMarker(tNodeLHS, oldNodeRHS,
						RuleUtil.Translated);

				setMapping(tNodeLHS, tNodeRHS);

				// update all markers for the attributes
				TAttribute newAttLHS = null;
				TAttribute newAttRHS = null;
				for (Attribute oldAttribute : oldNodeRHS.getAttributes()) {
					
					newAttRHS = (TAttribute) getCopiedObject(oldAttribute);
					if (newAttRHS.getMarkerType().equals(RuleUtil.NEW)){
						newAttLHS = (TAttribute) copyAtt(oldAttribute, tNodeLHS);
						setAttributeMarker(newAttRHS, oldAttribute,
								RuleUtil.Translated);
						// marker needed for matching constraint
						setAttributeMarker(newAttLHS, oldAttribute,
								RuleUtil.Translated);
					}

				}

				oldRhsNodes2TRhsNodes.put(oldNodeRHS, tNodeRHS);
				oldLhsNodes2TLhsNodes.put(RuleUtil.getLHSNode(oldNodeRHS),
						tNodeLHS);
			}
			
			@Override
			public boolean filter(Node oldNode, Node newNode) {
				return ((TNode)oldNode).getMarkerType() != null && ((TNode)oldNode).getMarkerType().equals(RuleUtil.NEW);
			}
		});
		
		edgeProcessors.add(new EdgeProcessor() {
			
			@Override
			public void process(Edge oldEdge, Edge newEdge) {
			
				Node sourceTNodeRHS = oldRhsNodes2TRhsNodes.get(oldEdge.getSource());
				Node targetTNodeRHS = oldRhsNodes2TRhsNodes.get(oldEdge.getTarget());

				setReferences(sourceTNodeRHS, targetTNodeRHS, newEdge,
						tRuleRhs);

				setEdgeMarker(newEdge,oldEdge,RuleUtil.Translated);
				

				// LHS
				Node sourceTNodeLHS = RuleUtil.getLHSNode(sourceTNodeRHS);
				Node targetTNodeLHS = RuleUtil.getLHSNode(targetTNodeRHS);

				// LHS
				Edge tEdgeLHS = copyEdge(oldEdge, tRuleLhs);
				setReferences(sourceTNodeLHS, targetTNodeLHS, tEdgeLHS,
						tRuleLhs);
				// for matching constraint
				setEdgeMarker(tEdgeLHS,oldEdge,RuleUtil.Translated);

				
			}
			
			@Override
			public boolean filter(Edge oldEdge, Edge newEdge) {
				// TODO Auto-generated method stub
				return NodeUtil.isTargetNode((TNode) oldEdge.getSource())
						&& NodeUtil.isTargetNode((TNode) oldEdge.getTarget()) &&
						RuleUtil.NEW.equals(((TEdge)oldEdge).getMarkerType());
			}
		});
		
	}
	
	public IndependentUnit getContainer(IndependentUnit container){
		Unit ftContainer;
		if (container != null && !container.getName().equals("RuleFolder") ){
			Module m = (Module) EcoreUtil.getRootContainer(oldRule);
			ftContainer = m.getUnit("BT_" + container.getName());
			if (!(ftContainer instanceof IndependentUnit)){
				if (ftContainer != null){
					ftContainer.setName("BTRule_" + ftContainer.getName());
				} 
				ftContainer = HenshinFactory.eINSTANCE.createIndependentUnit();
				ftContainer.setName("BT_" + container.getName());
				ftContainer.setDescription("BTRules.png");
				m.getUnits().add(ftContainer);
				((IndependentUnit)m.getUnit("BTRuleFolder")).getSubUnits().add(ftContainer);
			} 
		} else {
			Module m = (Module) EcoreUtil.getRootContainer(oldRule);
			ftContainer = m.getUnit("BTRuleFolder");
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
				
				DeleteBTRuleCommand deleteCommand = new DeleteBTRuleCommand(
						tr.getRule(),null);
				deleteCommand.execute();
				break;
			}
		}
	}
	
	@Override
	protected String getRuleMarker() {
		// TODO Auto-generated method stub
		return RuleUtil.TGG_BT_RULE;
	}
}
