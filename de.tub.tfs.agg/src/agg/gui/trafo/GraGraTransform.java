package agg.gui.trafo;

import java.util.BitSet;
import java.util.List;
import java.util.Vector;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdRule;
import agg.gui.editor.GraGraEditor;
import agg.gui.event.TransformEvent;
import agg.gui.event.TransformEventListener;
import agg.gui.options.GraTraMatchOptionGUI;
import agg.gui.options.GraTraOptionGUI;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.PriorityGraTraImpl;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.csp.CompletionPropertyBits;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;

/**
 * The class GraGraTransform handles a step by step (debugger) and interpreting
 * (interpreter) transformation of a gragra. It uses the class TransformDebug
 * for the debugger and the class TransformInterpret for the interpreter. It
 * provides the procedures to the Transform menu.
 * 
 * @author $Author: olga $
 * @version $Id: GraGraTransform.java,v 1.12 2010/10/21 11:18:16 olga Exp $
 */
public class GraGraTransform {

	/** Creates a new instance */
	public GraGraTransform(GraGraEditor anEditor) {
		this.editor = anEditor;
		this.threadpriority = 7;

		/* create gratra options GUI */
		this.generalOptionGUI = new GraTraMatchOptionGUI(this);
		this.optionGUI = new GraTraOptionGUI(this.editor.getParentFrame(), this);

		/* set strategy to default strategy */
		this.strategy = this.generalOptionGUI.getMorphCompletionStrategy();

		/* create a new step by step (debugger) transformation object */
		this.debugger = new TransformDebug(this);
		this.debugger.setCompletionStrategy(this.strategy);
		this.editor.addEditEventListener(this.debugger);

		this.transformListeners = new Vector<TransformEventListener>();
	}

	public GraGraEditor getEditor() {
		return this.editor;
	}
	
	public EdGraGra getGraGra() {
		return this.editor.getGraGra();
	}
	
	public Vector<EdRule> getCurrentRuleSet() {
		if (this.editor.getGraGra() != null) {
//			return this.editor.getGraGra().getRules();
			return this.editor.getGraGra().getEnabledRules();
		}
		
		return new Vector<EdRule>(0);	
	}
	
	/**
	 * Returns GraTraOptionGUI instance which allows to set the possible graph
	 * transformation options.
	 */
	public GraTraOptionGUI getOptionGUI() {
		return this.optionGUI;
	}

	/** Returns general options of the gragra transformation. */
	public GraTraMatchOptionGUI getGeneralOptionGUI() {
		return this.generalOptionGUI;
	}

	/** Returns the current completion strategy */
	public MorphCompletionStrategy getStrategy() {
		return this.strategy;
	}

	public void setEachRuleToApplyOfRuleSequence(boolean b) {
		if (this.ruleSequenceTransform != null)
			this.ruleSequenceTransform.setEachRuleToApplyOfRuleSequence(b);
	}
	
	public void setRuleSequences(
			final List<EdRule> rules,
			final List<Pair<List<Pair<String, String>>, String>> sequences) {
		
		this.optionGUI.setRulesOfRuleSequenceGUI(rules);
		this.optionGUI.setRuleSequences(sequences);
		this.optionGUI.enableRuleSequenceGUI(this.optionGUI.ruleSequenceEnabled());
	}
	
	public void resetCurrentRuleSequences() {
		if (this.editor.getGraGra() != null) {
			this.optionGUI.setRulesOfRuleSequenceGUI(this.editor.getGraGra().getEnabledRules());
			this.optionGUI.setRuleSequences(this.editor.getGraGra().getRuleSequenceList());
			this.optionGUI.enableRuleSequenceGUI(this.optionGUI.ruleSequenceEnabled());
		}
	}
	
	public void setRuleSequences(
			List<Pair<List<Pair<String, String>>, String>> sequences) {
		this.optionGUI.setRuleSequences(sequences);
		this.optionGUI.enableRuleSequenceGUI(this.optionGUI.ruleSequenceEnabled());
	}

	public List<Pair<List<Pair<String, String>>, String>> getRuleSequences() {
		if (this.optionGUI.ruleSequenceEnabled())
			return this.optionGUI.getRuleSequences();
		
		return null;
	}

	public String getRuleSequencesAsText() {
		if (this.optionGUI.ruleSequenceEnabled())
			return this.optionGUI.getRuleSequencesAsText();
		
		return null;
	}

	/**
	 * If return value is TRUE, the rule to be applied is choosen
	 * non-deterministically.
	 */
	public boolean nondeterministicallyEnabled() {
		return this.optionGUI.nondeterministicallyEnabled();
	}

	/**
	 * If return value is TRUE, the user defined rule priorities are used for
	 * graph transformation.
	 */
	public boolean priorityEnabled() {
		return this.optionGUI.priorityEnabled();
	}

	/**
	 * If return value is TRUE, the user defined rule layers are used for graph
	 * transformation.
	 */
	public boolean layeredEnabled() {
		return this.optionGUI.layeredEnabled();
	}

	/**
	 * If return value is TRUE, the user defined rule sequences are used for
	 * graph transformation.
	 */
	public boolean ruleSequenceEnabled() {
		return this.optionGUI.ruleSequenceEnabled();
	}

	/**
	 * In case of TRUE, the consistency check will be done after each
	 * transformation step. If the consistency check fails, the next valid match
	 * is taken for the graph transformation.
	 */
	public boolean consistencyEnabled() {
		return this.generalOptionGUI.consistencyEnabled();
	}

	/**
	 * In case of TRUE, the consistency check will be done after the graph
	 * transformation finished. For layered graph transformation the consistency
	 * check will be done at the end of a layer.
	 */
	public boolean consistencyCheckAfterGraphTrafoEnabled() {
		return this.generalOptionGUI.consistencyCheckAfterGraphTrafoEnabled();
	}
	
	/**
	 * If return value is TRUE, the layer's table is displayed before
	 * transformation starts to give the user a possibility to change the
	 * current rule-layer setting.
	 */
	public boolean showLayerEnabled() {
		return this.optionGUI.showLayerEnabled();
	}

	/**
	 * If return value is TRUE, the graph transformation will stop after each
	 * layer and wait for a user action.
	 */
	public boolean stopLayerAndWaitEnabled() {
		return this.optionGUI.stopLayerAndWaitEnabled();
	}

	/**
	 * If return value is TRUE, the only current layer will break when the tool
	 * button "Stop" transformation is pressed.
	 */
	public boolean breakLayerEnabled() {
		return this.optionGUI.breakLayerEnabled();
	}

	/**
	 * If return value is TRUE, the layered transformation will break when the
	 * tool button "Stop" transformation is pressed.
	 */
	public boolean breakAllLayerEnabled() {
		return this.optionGUI.breakAllLayerEnabled();
	}

	/**
	 * If return value is TRUE, the modified host graph will be shown after each
	 * transformation step.
	 */
	public boolean showGraphAfterStepEnabled() {
		return this.generalOptionGUI.showGraphAfterStepEnabled();
	}

	/**
	 * If return value is TRUE, the graph transformation will stop after each
	 * step and wait for a user action.
	 */
	public boolean waitAfterStepEnabled() {
		return this.generalOptionGUI.waitAfterStepEnabled();
	}

	/**
	 * If return value is TRUE, the rule applicability check will be done after
	 * each transformation step.
	 */
	public boolean checkRuleApplicabilityEnabled() {
		return this.generalOptionGUI.checkRuleApplicabilityEnabled();
	}

	/** Returns TRUE if the option -apply parallel- is set. */
	// public boolean applyParallelEnabled() {
	// return this.generalOptionGUI.applyParallelEnabled();
	// }
	
	public boolean selectMatchObjectsEnabled() {
		return this.generalOptionGUI.selectMatchObjectsEnabled();
	}
	
	/**
	 * If return value is TRUE, all nodes and edges created during
	 * transformation step are shown as selected objects.
	 */
	public boolean selectNewAfterStepEnabled() {
		return this.generalOptionGUI.selectNewAfterStepEnabled();
	}

	/**
	 * If return value is TRUE, the layered transformation starts with the first
	 * layer again.
	 */
	public boolean layeredLoopEnabled() {
		return this.optionGUI.layeredLoopEnabled();
	}

	/** Returns TRUE if the option - reset graph - is set 
	 *  in this case the host graph will be reset for each loop over layers.
	 */
	public boolean resetGraphEnabled() {
		return this.optionGUI.resetGraphEnabled();
	}
	
	public int getLayerToStop() {
		return this.optionGUI.getLayerToStop();
	}
	
	/**
	 * Sets the current completion strategy. The completion strategy of the
	 * transform debugger will be set too.
	 */
	public void setCompletionStrategy(MorphCompletionStrategy strat) {
		this.strategy = strat;
		this.debugger.setCompletionStrategy(strat);
		if (this.debugger.getMatch() != null) {
			this.debugger.getMatch().setCompletionStrategy(strat);
		}
		this.editor.getGraGra().getBasisGraGra().setGraTraOptions(strat);
	}

	/**
	 * The current transformation options, backed by a vector of option names.
	 */
	public Vector<String> getGraTraOptionsList() {
		GraTraOptions gratraOptions = getGraTraOptions();
		return gratraOptions.getOptions();
	}

	public GraTraOptions getGraTraOptions() {
		GraTraOptions gratraOptions = new GraTraOptions();
		gratraOptions.addOption(CompletionStrategySelector.getName(this.strategy));
		BitSet activebits = this.strategy.getProperties();
		for (int i = 0; i < CompletionPropertyBits.BITNAME.length; i++) {
			if (activebits.get(i)) {
				String bitName = CompletionPropertyBits.BITNAME[i];
				gratraOptions.addOption(bitName);
			}
		}
		if (!this.strategy.isRandomisedDomain())
			gratraOptions.addOption(GraTraOptions.DETERMINED_CSP_DOMAIN);

		if (this.generalOptionGUI.consistencyEnabled())
			gratraOptions.addOption(GraTraOptions.CONSISTENT_ONLY);

		if (this.generalOptionGUI.consistencyCheckAfterGraphTrafoEnabled())
			gratraOptions.addOption(GraTraOptions.CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO);
		
		if (this.optionGUI.priorityEnabled())
			gratraOptions.addOption(GraTraOptions.PRIORITY);
		else if (this.optionGUI.layeredEnabled())
			gratraOptions.addOption(GraTraOptions.LAYERED);
		else if (this.optionGUI.ruleSequenceEnabled()) {
			gratraOptions.addOption(GraTraOptions.RULE_SEQUENCE);
			if (this.optionGUI.eachRuleToApplyEnabled())
				gratraOptions.addOption(GraTraOptions.EACH_RULE_TO_APPLY);
		}
		
		if (this.optionGUI.stopLayerAndWaitEnabled())
			gratraOptions.addOption(GraTraOptions.STOP_LAYER_AND_WAIT);
		if (this.optionGUI.layeredLoopEnabled())
			gratraOptions.addOption(GraTraOptions.LOOP_OVER_LAYER);
		if (this.optionGUI.resetGraphEnabled())
			gratraOptions.addOption(GraTraOptions.RESET_GRAPH);
		if (this.optionGUI.breakLayerEnabled())
			gratraOptions.addOption(GraTraOptions.BREAK_LAYER);
		if (this.optionGUI.breakAllLayerEnabled())
			gratraOptions.addOption(GraTraOptions.BREAK_ALL_LAYER);

		if (this.generalOptionGUI.checkRuleApplicabilityEnabled())
			gratraOptions.addOption(GraTraOptions.CHECK_RULE_APPLICABILITY);
		if (this.generalOptionGUI.applyParallelEnabled())
			gratraOptions.addOption(GraTraOptions.PARALLEL_MATCHING);
		if (this.generalOptionGUI.showGraphAfterStepEnabled())
			gratraOptions.addOption(GraTraOptions.SHOW_GRAPH_AFTER_STEP);
		if (this.generalOptionGUI.waitAfterStepEnabled())
			gratraOptions.addOption(GraTraOptions.WAIT_AFTER_STEP);
				
		return gratraOptions;
	}

	public void updateGraTraOption(String opt, boolean b) {
		if (this.editor.getGraGra() == null) {
			return;
		}

		if (this.layeredTransform != null && this.layeredTransform.isAlive()) {
			if (opt.equals(GraTraOptions.LOOP_OVER_LAYER) && !b) {
				((LayeredGraTraImpl) this.layeredTransform.getGraTra()).setLayeredLoop(false);
			}
			else if (opt.equals(GraTraOptions.BREAK_ALL_LAYER) && b) {
				((LayeredGraTraImpl) this.layeredTransform.getGraTra()).setBreakAllLayer(true);
				this.layeredTransform.stopping();
			}
		}
		else {
			if (b)
				this.editor.getGraGra().getBasisGraGra().addGraTraOption(opt);
			else
				this.editor.getGraGra().getBasisGraGra().removeGraTraOption(opt);
		}
	}

	public void updateGraTraOptionGUI(Vector<String> optionsList) {
		this.optionGUI.update(optionsList);
		this.optionGUI.updateLayerToStopIfNeeded();		
		this.generalOptionGUI.update(optionsList);
	}

	public void setRulesOfGraphRuleSequenceGUI(Vector<EdRule> rules) {
		this.optionGUI.setRulesOfRuleSequenceGUI(rules);
	}

	/** Sets priority of the interpreter thread. */
	public void setTransformationThreadPriority(int prior) {
		this.threadpriority = prior;
//		if (this.interpreterTransform != null) 
//			interpreterTransform.setPriority(prior);
//		else if (this.layeredTransform != null) 
//			this.layeredTransform.setPriority(prior);
//		else if (this.ruleSequenceTransform != null) 
//			this.ruleSequenceTransform.setPriority(prior);
	}

	/** Returns the transform debugger. */
	public TransformDebug getTransformDebugger() {
		return this.debugger;
	}

	/** Returns a non-deterministically rule transform interpreter */
	public TransformInterpret createInterpreterTransform() {
		this.interpreterTransform = new TransformInterpret(this);
		return this.interpreterTransform;
	}

	/** Returns the non-deterministically rule transform interpreter */
	public TransformInterpret getInterpreterTransform() {
		return this.interpreterTransform;
	}

	/** Returns a rule priority transform interpreter. */
	public TransformInterpret createRulePriorityTransform() {
		this.rulePriorityTransform = new TransformInterpret(this, true);
		return this.rulePriorityTransform;
	}

	/** Returns the rule priority transform interpreter. */
	public TransformInterpret getRulePriorityTransform() {
		return this.rulePriorityTransform;
	}

	/** Returns a rule layer transform interpreter. */
	public TransformLayered createRuleLayerTransform() {
		this.layeredTransform = new TransformLayered(this);
		return this.layeredTransform;
	}

	/** Returns the rule layer transform interpreter. */
	public TransformLayered getRuleLayerTransform() {
		return this.layeredTransform;
	}

	/** Returns a rule sequence transform interpreter. */
	public TransformRuleSequences createRuleSequenceTransform(boolean useApplRuleSequence) {
		this.ruleSequenceTransform = new TransformRuleSequences(this, useApplRuleSequence);		
		return this.ruleSequenceTransform;
	}

	/** Returns the rule sequence transform interpreter. */
	public TransformRuleSequences getRuleSequenceTransform() {
		return this.ruleSequenceTransform;
	}

	/** Starts the rule sequences transformation. */
	public void startTransformByRuleSequence(
			final EdGraGra gragra,
			final RuleSequence ruleSequence) {
		
		this.editor.removeEditEventListener(this.debugger);
		this.editor.addEditEventListener(this.ruleSequenceTransform);
		this.editor.setInterpreting(false);
		this.editor.setLayering(false);
		this.editor.setTransformRuleSequences(true);
		
		if (gragra.getBasisGraGra().trafoByApplicableRuleSequence()) {
			gragra.getBasisGraGra().addGraTraOption(GraTraOptions.WAIT_AFTER_STEP);
			gragra.getBasisGraGra().addGraTraOption(GraTraOptions.SELECT_NEW_AFTER_STEP);
		}
		
		this.ruleSequenceTransform.setGraGra(gragra, ruleSequence);		
		this.ruleSequenceTransform.setCompletionStrategy(this.strategy);
		this.ruleSequenceTransform.setPriority(this.threadpriority);
		this.ruleSequenceTransform
				.setShowGraphAfterStep(showGraphAfterStepEnabled());
		
		((RuleSequencesGraTraImpl) this.ruleSequenceTransform.getGraTra())
				.setGraTraOptions(getGraTraOptions());
		fireTransform(new TransformEvent(this.ruleSequenceTransform,
				TransformEvent.START));

		this.ruleSequenceTransform.start();
	}

	/** Stops the rule sequences transform. */
	public void stopTransformRuleSequences() {
		this.ruleSequenceTransform.stopping();
		if (this.ruleSequenceTransform.getGraTra().getGraGra().trafoByApplicableRuleSequence()) {
			this.ruleSequenceTransform.getGraTra().getGraGra().removeGraTraOption(GraTraOptions.WAIT_AFTER_STEP);
			this.ruleSequenceTransform.getGraTra().getGraGra().removeGraTraOption(GraTraOptions.SELECT_NEW_AFTER_STEP);
		}
	}

	/** Starts the transform interpreter. */
	public void startTransformInterpreter(EdGraGra gragra) {
		this.editor.removeEditEventListener(this.debugger);
		this.editor.addEditEventListener(this.interpreterTransform);
		this.editor.setInterpreting(true);
		this.editor.setLayering(false);
		this.editor.setTransformRuleSequences(false);
		this.interpreterTransform.setGraGra(gragra);
		this.interpreterTransform.setCompletionStrategy(this.strategy);
		this.interpreterTransform.setPriority(this.threadpriority);
		this.interpreterTransform.setShowGraphAfterStep(showGraphAfterStepEnabled());
		((DefaultGraTraImpl) this.interpreterTransform.getGraTra())
				.setGraTraOptions(getGraTraOptions());
		fireTransform(new TransformEvent(this.interpreterTransform,
				TransformEvent.START));

		this.interpreterTransform.start();
	}

	/** Stops the transform interpreter. */
	public void stopTransformInterpreter() {
		this.interpreterTransform.stopping();
	}

	/** Starts the rule priority transformation. */
	public void startRulePriorityTransformInterpreter(EdGraGra gragra) {
		this.editor.removeEditEventListener(this.debugger);
		this.editor.addEditEventListener(this.rulePriorityTransform);
		this.editor.setInterpreting(true);
		this.editor.setLayering(false);
		this.editor.setTransformRuleSequences(false);
		this.rulePriorityTransform.setGraGra(gragra);
		this.rulePriorityTransform.setCompletionStrategy(this.strategy);
		this.rulePriorityTransform.setPriority(this.threadpriority);
		this.rulePriorityTransform
				.setShowGraphAfterStep(showGraphAfterStepEnabled());
		((PriorityGraTraImpl) this.rulePriorityTransform.getGraTra())
				.setGraTraOptions(getGraTraOptions());
		fireTransform(new TransformEvent(this.rulePriorityTransform,
				TransformEvent.START));

		this.rulePriorityTransform.start();
	}

	/** Stops the rule priority transform interpreter. */
	public void stopRulePriorityTransformInterpreter() {
		this.rulePriorityTransform.stopping();
	}

	/** Starts the layered transformation. */
	public void startTransformLayered(EdGraGra gragra) {
		this.editor.removeEditEventListener(this.debugger);
		this.editor.addEditEventListener(this.layeredTransform);
		this.editor.setInterpreting(false);
		this.editor.setLayering(true);
		this.editor.setTransformRuleSequences(false);
		
		this.layeredTransform.setGraGra(gragra);
		this.layeredTransform.setCompletionStrategy(this.strategy);
		this.layeredTransform.setPriority(this.threadpriority);
		this.layeredTransform.setShowGraphAfterStep(showGraphAfterStepEnabled());
		
		((LayeredGraTraImpl) this.layeredTransform.getGraTra())
				.setStopLayerAndWait(stopLayerAndWaitEnabled());
		((LayeredGraTraImpl) this.layeredTransform.getGraTra())
				.setLayeredLoop(layeredLoopEnabled());
		((LayeredGraTraImpl) this.layeredTransform.getGraTra())
				.setResetGraphBeforeLoop(resetGraphEnabled());
		((LayeredGraTraImpl) this.layeredTransform.getGraTra())
				.setBreakLayer(breakLayerEnabled());
		((LayeredGraTraImpl) this.layeredTransform.getGraTra())
				.setBreakAllLayer(breakAllLayerEnabled());

		fireTransform(new TransformEvent(this.layeredTransform, TransformEvent.START));

		this.layeredTransform.start();
	}

	/** Stops the layered transformation. */
	public void stopTransformLayered() {
		this.layeredTransform.stopping();
	}

	public void changeFromTransformLayeredToTransformInterpreter() {
		stopTransformLayered();
		this.editor.startInterpreterTransform();
	}

	public Vector<Rule> getApplicableRules(EdGraGra gragra) {
		return this.debugger.getApplicableRules(gragra);
	}

	public Vector<EdRule> getApplicableRules(EdGraGra gragra, boolean applicable) {
		return this.debugger.getApplicableRules(gragra, applicable);
	}

	/** Defines a new match of the transform debugger. */
	public void matchDef(EdRule rule) {
		this.debugger.setRule(rule);
		this.debugger.matchDef();
	}

	public void destroyMatch() {
		this.debugger.destroyMatch();
	}

	/** Completes the match of the transform debugger. */
	public void nextCompletion(EdRule rule) {
		this.debugger.setRule(rule);
		this.debugger.nextCompletion();
	}

	/** Applies the match of the transform debugger. */
	public void step(EdRule rule) {
		this.debugger.setRule(rule);
		this.debugger.step();
	}

	public void unsetTransformDebug() {
//		this.debugger.setRule(null);
		this.debugger.dispose();
	}
	
	/** Adds a new transform event listener. */
	public synchronized void addTransformEventListener(TransformEventListener l) {
		if (!this.transformListeners.contains(l))
			this.transformListeners.addElement(l);
	}

	/** Removes the transform event listener. */
	public synchronized void removeTransformEventListener(
			TransformEventListener l) {
		if (this.transformListeners.contains(l))
			this.transformListeners.removeElement(l);
	}

	/** Sends a transform event to the all my listeners. */
	public void fireTransform(TransformEvent e) {
		for (int i = 0; i < this.transformListeners.size(); i++)
			this.transformListeners.get(i).transformEventOccurred(e);
	}

	private final GraTraOptionGUI optionGUI;

	private final GraTraMatchOptionGUI generalOptionGUI;

	private MorphCompletionStrategy strategy;

	private final TransformDebug debugger;

	private TransformInterpret interpreterTransform;

	private TransformInterpret rulePriorityTransform;

	private TransformLayered layeredTransform;

	private TransformRuleSequences ruleSequenceTransform;

	private final Vector<TransformEventListener> transformListeners;

	private GraGraEditor editor;

	private int threadpriority;

}
