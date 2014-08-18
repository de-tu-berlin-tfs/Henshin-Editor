package de.tub.tfs.henshin.tgg.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.impl.TGGEngineImpl;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.TranslationMaps;

public interface TggTransformation {

	public abstract HashMap<EObject, TripleComponent> getTripleComponentNodeMap();

	public abstract ArrayList<RuleApplicationImpl> getRuleApplicationList();

	/**
	 * @return the graph
	 */
	public abstract EGraph getGraph();

	/**
	 * @param graph the graph to set
	 */
	public abstract void setGraph(EGraph graph);

	public abstract List<Rule> getfTRuleList();

	public abstract void setOpRuleList(List<Rule> opRuleList);

	/**
	 * @return the emfEngine
	 */
	public abstract TGGEngineImpl getEmfEngine();

	/**
	 * @param emfEngine the emfEngine to set
	 */
	public abstract void setEmfEngine(TGGEngineImpl emfEngine);

	public abstract TranslationMaps getTranslationMaps();

	public abstract void setTranslationMaps(TranslationMaps translationMaps);

	/**
	 * creates the input graphs from the input elements; 
	 * marks all input elements and registers the matching constraints for the markers
	 * @param inputEObjects the input objects for the TGG transformation 
	 */
	public abstract void setInput(List<EObject> inputRootEObjects);

	/**
	 * sets the input graph - to be used from the graphical editor;
	 * retrieves the marked elements from the triple graph markers and registers the matching constraints for the markers
	 * @param eGraph the input graph for the TGG transformation
	 */
	public abstract void setInput(TggHenshinEGraph eGraph);

	/**
	 * applies the operational rules to the input graph -
	 * without job information, e.g. for GUI editor
	 */
	public abstract void applyRules();

	/**
	 * applies the operational rules to the input graph
	 * @param monitor the progress monitor for the running transformation job
	 * @param msg the message to be displayed in the progress monitor
	 */
	public abstract void applyRules(IProgressMonitor monitor, String msg);

	public abstract void fillTranslatedMaps(List<EObject> inputEObjects);

	public abstract void setNullValueMatching(boolean matchNullValues2);

}