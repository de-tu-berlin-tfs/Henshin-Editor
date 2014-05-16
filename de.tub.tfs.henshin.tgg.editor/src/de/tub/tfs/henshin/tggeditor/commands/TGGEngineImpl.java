package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class TGGEngineImpl extends EngineImpl {
	/**
	 * 
	 */

	private EGraph graph;
	private ObjectCopier copier;

	public TGGEngineImpl(EGraph graph) {
		this(graph,null,null,null);
	}
	
	/**
	 * @param executeFTRulesCommand
	 */
	public TGGEngineImpl(EGraph graph,HashMap<Node, Boolean> isTranslatedMap, 
			HashMap<Attribute, Boolean> isTranslatedAttributeMap, 
			HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		this.graph = graph;
		this.copier = new ObjectCopier(graph,this,isTranslatedMap,isTranslatedAttributeMap,isTranslatedEdgeMap);
		this.getScriptEngine().put("ObjectCopier",copier );
		this.sortVariables = false;
		
	}
	
	public void postProcess(Match m){
		copier.postProcess(m);
	}
	
	@Override
	protected RuleInfo getRuleInfo(Rule rule) {
		RuleInfo ruleInfo = ruleInfos.get(rule);
		if (ruleInfo == null) {
			// Create the rule info:
			ruleInfo = new RuleInfo(rule, this);
			ruleInfos.put(rule, ruleInfo);
			// Listen to changes:
			//rule.eAdapters().add(ruleListener);
			
	
			// Check for missing factories:			
			for (Node node : ruleInfo.getChangeInfo().getCreatedNodes()) {
				if (node.getType()==null) {
					throw new RuntimeException("Missing type for " + node);
				}
				if (node.getType().getEPackage()==null || 
					node.getType().getEPackage().getEFactoryInstance()==null) {
					throw new RuntimeException("Missing factory for '" + node + 
							"'. Register the corresponding package, e.g. using PackageName.eINSTANCE.getName().");
				}
			}
		}
		return ruleInfo;
	}
}