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
import de.tub.tfs.henshin.tgg.interpreter.impl.TggEngineImpl;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.TranslationMaps;

/**
 * The Interface for executing a transforamtion via operational TGG rules.
 */
public interface TggTransformation {

	/**
	 * Gets the triple component node map.
	 *
	 * @return the triple component node map
	 */
	public abstract HashMap<EObject, TripleComponent> getTripleComponentNodeMap();

	/**
	 * flag, whether attribute values can be matched to null values
	 */
	public abstract Boolean getNullValueMatching();

	
	/**
	 * Gets the rule application list.
	 *
	 * @return the rule application list
	 */
	public abstract ArrayList<RuleApplicationImpl> getRuleApplicationList();

	/**
	 * Gets the graph.
	 *
	 * @return the graph
	 */
	public abstract EGraph getGraph();

	/**
	 * Sets the graph.
	 *
	 * @param graph the graph to set
	 */
	public abstract void setGraph(EGraph graph);

	/**
	 * Gets the list of operational rules.
	 *
	 * @return the list of operational rules
	 */
	public abstract List<Rule> getOpRuleList();

	/**
	 * Sets the list of operational rules.
	 *
	 * @param opRuleList the new list of operational rules
	 */
	public abstract void setOpRuleList(List<Rule> opRuleList);

	/**
	 * @return the emfEngine
	 */
	public abstract TggEngineImpl getEmfEngine();

	/**
	 * @param emfEngine the emfEngine to set
	 */
	public abstract void setEmfEngine(TggEngineImpl emfEngine);

	/**
	 * Gets the translation maps.
	 *
	 * @return the translation maps
	 */
	public abstract TranslationMaps getTranslationMaps();

	/**
	 * Sets the translation maps.
	 *
	 * @param translationMaps the new translation maps
	 */
	public abstract void setTranslationMaps(TranslationMaps translationMaps);

	/**
	 * creates the input graphs from the input elements; 
	 * marks all input elements and registers the matching constraints for the markers
	 * @param inputEObjects the input objects for the TGG transformation 
	 */
	public abstract void setInput(List<EObject> inputRootEObjects);

	
	/**
	 * creates the input graphs from the root input element; 
	 * marks all contained input elements and registers the matching constraints for the markers
	 * @param inputEObjects the input objects for the TGG transformation 
	 */
	public abstract void setInput(EObject inputRootEObject);
	
	
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
	 * applies the operational rules to the input graph -
	 * debug flag determines whether to display debug information in the console
	 * @return whether any rule was applied
	 */

	boolean applyRules(boolean debug);

	/**
	 * applies the operational rules to the input graph -
	 * debug flag determines whether to display debug information in the console
	 * @return whether any rule was applied
	 */
	boolean applyRules(IProgressMonitor monitor, String msg, boolean debug);

	
	/**
	 * applies the operational rules to the input graph
	 * @param monitor the progress monitor for the running transformation job
	 * @param msg the message to be displayed in the progress monitor
	 * @return whether any rule was applied
	 */
	public abstract boolean applyRules(IProgressMonitor monitor, String msg);

	/**
	 * Fills the maps with Boolean translation markers initially with the value of markerValue for each input e object.
	 *
	 * @param eObjects the e objects to be marked
	 */
	public abstract void fillTranslatedMaps(List<EObject> eObjects, Boolean markerValue);

	public abstract void fillTranslatedMaps(EObject eObject, Boolean markerValue);

	/**
	 * Sets the flag, whether parameters can be matched to null values on attributes.
	 *
	 * @param matchNullValues the Boolean flag whether null value matching is possible
	 */
	public abstract void setNullValueMatching(boolean matchNullValues);

	void addOpRuleList(List<Rule> opRuleList);

	


}
