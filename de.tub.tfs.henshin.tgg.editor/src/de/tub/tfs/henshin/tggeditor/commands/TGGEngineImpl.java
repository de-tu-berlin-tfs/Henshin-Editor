package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

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
	}
	
	public void postProcess(Match m){
		copier.postProcess(m);
	}

}