package de.tub.tfs.henshin.tggeditor.commands;

import java.util.Arrays;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UserConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Node;

public class TGGEngineImpl extends EngineImpl {
	/**
	 * 
	 */

	private EGraph graph;
	private ObjectCopier copier;

	/**
	 * @param executeFTRulesCommand
	 */
	public TGGEngineImpl(EGraph graph) {
		this.graph = graph;
		this.copier = new ObjectCopier(graph);
		this.getScriptEngine().put("ObjectCopier",copier );
	}
	
	public void postProcess(){
		copier.postProcess();
	}

}