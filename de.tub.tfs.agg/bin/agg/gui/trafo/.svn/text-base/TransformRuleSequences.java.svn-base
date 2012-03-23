package agg.gui.trafo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdRule;
import agg.gui.event.EditEvent;
import agg.gui.event.EditEventListener;
import agg.gui.event.TransformEvent;
import agg.ruleappl.RuleSequence;
import agg.xt_basis.ApplRuleSequencesGraTraImpl;
import agg.xt_basis.GraTra;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.Match;
import agg.xt_basis.Rule;

/**
 * The class TransformLayered implements an interpreting transformation of a
 * layered gragra. It will be used by the GraGraTransform class.
 * 
 * @author $Author: olga $
 *
 */
public class TransformRuleSequences extends Thread implements
		GraTraEventListener, EditEventListener {

	/** Creates a new instance */
	public TransformRuleSequences(GraGraTransform transform, boolean useApplRuleSequence) {
		this.gragraTransform = transform;
		if (useApplRuleSequence) {
			this.gratra = new ApplRuleSequencesGraTraImpl();			
		} else {
			this.gratra = new RuleSequencesGraTraImpl();
		}
		
		this.gratra.enableWriteLogFile(true);
		this.gratra.addGraTraListener(this);
	}

	public void dispose() {
		this.gratra.removeGraTraListener(this);
		((RuleSequencesGraTraImpl) this.gratra).dispose();
		this.currentMatch = null;
		this.currentRule = null;
		this.event = null;
		this.gragra = null;
//		System.gc();
	}
	
	public GraTra getGraTra() {
		return this.gratra;
	}

	/** Sets a gragra to transform */
	public void setGraGra(
			final EdGraGra gra,
			final RuleSequence ruleSequence) {
		this.gragra = gra;
		this.gratra.setGraGra(this.gragra.getBasisGraGra());
		
		((RuleSequencesGraTraImpl) this.gratra).setRuleSequence(ruleSequence);
		
		this.gratra.setHostGraph(this.gragra.getBasisGraGra().getGraph());
//		((RuleSequencesGraTraImpl) gratra).setEachRuleToApply(
//				gragra.getBasisGraGra().getGraTraOptions().contains(GraTraOptions.EACH_RULE_TO_APPLY));
		this.gratra.addGraTraListener(this);
//		inheritanceWarningSent = false;
	}

	/** Sets the current completion strategy */
	public void setCompletionStrategy(MorphCompletionStrategy strat) {
		this.gratra.setCompletionStrategy(strat);
	}

	public void setParentFrame(JFrame f) {
//		parent = f;
	}

	public void setEachRuleToApplyOfRuleSequence(boolean b) {
		((RuleSequencesGraTraImpl)this.gratra).setEachRuleToApply(b);
	}
	
	/**
	 * If setting show is TRUE, the graph will be updated after each
	 * transformation step and shown newly
	 */
	public void setShowGraphAfterStep(boolean show) {
		this.showGraphAfterStep = show;
	}

	
	/** Implements the Thread.run method */
	public void run() {
		this.steps = 0;
		this.cancelled = false;
		this.stopped = false;
		if (this.gratra.getHostGraph() != this.gragra.getBasisGraGra().getGraph()) {
			this.gratra.setHostGraph(this.gragra.getBasisGraGra().getGraph());
		}
		
		((RuleSequencesGraTraImpl) this.gratra).setEachRuleToApply(
				this.gragra.getBasisGraGra().getGraTraOptions().contains(GraTraOptions.EACH_RULE_TO_APPLY));

		this.gragraAnimated = this.gragra.isAnimated();
		
		this.gratra.transform();
	}

	/** Stops the transformation */
	public void stopping() {
		this.stopped = true;
		this.gratra.stop();
	}

	/** Implements GraTraEventListener.graTraEventOccurred */
	public void graTraEventOccurred(GraTraEvent e) {
		// System.out.println("TransformRuleSequences.graTraEventOccurred
		// "+e.getMessage());
		String ruleName = "";
		this.event = e;
		this.msgGraTra = e.getMessage();
		
		if (this.msgGraTra == GraTraEvent.RULE) {
			this.gragraTransform.fireTransform(
					new TransformEvent(this, TransformEvent.RULE, e.getRule()));
			
		} else if (this.msgGraTra == GraTraEvent.MATCH_VALID) {
			this.currentMatch = this.event.getMatch();
			this.currentRule = this.currentMatch.getRule();
			ruleName = this.currentRule.getName();
			
			if (this.gragraTransform.selectMatchObjectsEnabled()) {
				this.gragra.getGraph().updateAlongMorph(e.getMatch());
			}

			this.gragra.getGraph().unsetNodeNumberChanged();
			
			this.gragraTransform.fireTransform(new TransformEvent(this,
					TransformEvent.MATCH_VALID,
					this.event.getMatch(),
					"  match of  <" + ruleName + ">  is valid"));	
			
		} else if ((this.msgGraTra == GraTraEvent.STEP_COMPLETED)) {
			this.steps++;			
			this.currentMatch = this.event.getMatch();
			this.currentRule = this.currentMatch.getRule();
			ruleName = this.currentRule.getName();
			
			if (this.showGraphAfterStep) {
				
				this.gragra.getGraph().setXYofNewNode(this.gragra.getRule(this.currentRule), this.currentMatch, this.currentMatch.getCoMorphism());
				
				if (this.gragra.isRuleAnimated(this.currentRule)) {
					this.gragraTransform.fireTransform(new TransformEvent(this,
							TransformEvent.ANIMATED_NODE, this.currentMatch));
				} else if (!this.gragraAnimated) {
					this.gragraTransform.getEditor().doStepLayoutProc();
				
					if (this.gragraTransform.selectNewAfterStepEnabled()) {
						this.gragra.getGraph().updateAlongMorph(this.event.getCoMatch(), this.currentRule);
					}
				}				
				disposeMatch();
			}
			this.gragraTransform.fireTransform(new TransformEvent(this,
					TransformEvent.STEP_COMPLETED, "  <" + ruleName
							+ ">  is applied"));
			
		} else if (this.msgGraTra == GraTraEvent.TRANSFORM_FINISHED) {
			this.gratra.stop();
			
			this.gragra.getGraph().clearMarks();
			

			if (!this.showGraphAfterStep) {					
				this.gragraTransform.getEditor().doStandardLayoutProc();
			}
			
			if (((this.steps == 0) && !this.cancelled)
					|| ((RuleSequencesGraTraImpl) this.gratra).isTrafoSequenceBroken()) {
				this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.CANNOT_TRANSFORM, e.getMessageText()));
			} 
			
			this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.STOP, "  finished.  "));
			System.out.println("*** Rule sequence transformation - finished.");
			
		} else if ((this.msgGraTra == GraTraEvent.INPUT_PARAMETER_NOT_SET)) {
			this.inputParameterOK = false;
			String rulename = "";
			if (this.event.getMatch() != null) 
				rulename = this.event.getMatch().getRule().getName();
			else if (this.event.getRule() != null) 
				rulename = this.event.getRule().getName();
			int answer = parameterWarning(rulename);
			if (answer == JOptionPane.YES_OPTION) {
				if (this.event.getMatch() != null) {
					this.currentMatch = this.event.getMatch();
					this.currentRule = this.currentMatch.getRule();
					this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.INPUT_PARAMETER_NOT_SET, this.currentMatch));
				}
				else if (this.event.getRule() != null) {
					this.currentRule = this.event.getRule();
					this.gragraTransform.fireTransform(new TransformEvent(this,
							TransformEvent.INPUT_PARAMETER_NOT_SET, this.currentRule));
				}
				
				while (!this.inputParameterOK) {
					// wait for INPUT_PARAMETER_OK 
					// inside of editEventOccurred(EditEvent e)
					try {
						sleep(1000);
					} catch (InterruptedException ex) {}
				}
			} else if (answer == 1) { // Continue
				this.gratra.stopRule();
			} else if (answer == 2) { // Cancel
				this.gratra.stop();
				this.cancelled = true;
				this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.CANCEL));
			}
			
		} else if (this.msgGraTra == GraTraEvent.NOT_READY_TO_TRANSFORM) {
			ruleName = this.event.getMessageText();
			String s = "Please check variables of the rule:  " + ruleName; // e.getMessageText();
			this.gragraTransform.fireTransform(new TransformEvent(this,
					TransformEvent.NOT_READY_TO_TRANSFORM, s));
			// gragraTransform.fireTransform(new TransformEvent(this,
			// TransformEvent.NOT_READY_TO_TRANSFORM, " <"+ruleName+"> is
			// failed"));
			
		} else if ((this.msgGraTra == GraTraEvent.ATTR_TYPE_FAILED)
				|| (this.msgGraTra == GraTraEvent.RULE_FAILED)
				|| (this.msgGraTra == GraTraEvent.ATOMIC_GC_FAILED)
				|| (this.msgGraTra == GraTraEvent.GRAPH_FAILED)) {
			String s = e.getMessageText();
			this.gragraTransform.fireTransform(new TransformEvent(this,
					TransformEvent.NOT_READY_TO_TRANSFORM, s));
			
		} else if (this.msgGraTra == GraTraEvent.NEW_MATCH) {
			// currentMatch = event.getMatch();
			// currentRule = currentMatch.getRule();
			// ruleName = currentRule.getName();
			// gragraTransform.fireTransform(new TransformEvent(this,
			// TransformEvent.NEW_MATCH, " new match of <"+ruleName+"> is
			// created"));
			
		} else if (this.msgGraTra == GraTraEvent.NO_COMPLETION) {
//			if (showGraphAfterStep) {
//				currentRule = event.getMatch().getRule();
//				disposeMatch();
//			}
		}

		else if (this.msgGraTra == GraTraEvent.INCONSISTENT) {
			// ruleName = currentRule.getName();
			// String msg = "Graph inconsistency after applying rule <
			// "+ruleName+"> !";
			// gragraTransform.fireTransform(new TransformEvent(this,
			// TransformEvent.INCONSISTENT, msg));

			if (this.gragraTransform.consistencyCheckAfterGraphTrafoEnabled()) {
				this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.INCONSISTENT, this.event.getMessageText()));
			}		
		} else if (this.msgGraTra == GraTraEvent.MATCH_FAILED) {
			
		}
	}

	/** Implements EditEventListener.editEventOccurred */
	public void editEventOccurred(EditEvent e) {
		if (e.getMsg() == EditEvent.INPUT_PARAMETER_OK)
			this.inputParameterOK = true;
	}

	/** Returns TRUE if there is at least one step was possible */
	public boolean isSuccessful() {
		if (this.steps == 0)
			return false;
		
		return true;
	}

	/** Returns TRUE if the transformation was stopped */
	public boolean isStopped() {
		return this.stopped;
	}

	private void disposeMatch() {
		EdRule r = this.gragra.getRule(this.currentRule);
		if (r != null) 
			r.updateRule();
	}

	private int parameterWarning(String ruleName) {
		Object[] options = { "Set", "Continue", "Cancel" };
		int answer = JOptionPane.showOptionDialog(null,
				"Input parameter of the rule  \" " + ruleName
						+ " \"  not set!\nDo you want to set parameter?",
				"Warning", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		return answer;
	}

	/*
	private void inheritanceWarning() {
		if (gratra.getGraGra().getConstraints().hasMoreElements()
				&& gratra.getGraGra().getTypeSet().usesInheritance()) {
			if (!inheritanceWarningSent) {
				gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.INHERITANCE));
				inheritanceWarningSent = true;
			}
		} else {
			inheritanceWarningSent = false;
		}
	}
*/
	
//	private JFrame parent;

	private GraGraTransform gragraTransform;

	private GraTra gratra;

//	private List<Pair<List<Pair<String, String>>, String>> ruleSequences;

	private int msgGraTra;

	private GraTraEvent event;

	private EdGraGra gragra;

	private Rule currentRule;

	private Match currentMatch;

	private boolean inputParameterOK = false;

	private int steps;

	private boolean cancelled = false;

	private boolean stopped = false;

	private boolean showGraphAfterStep;

//	private boolean inheritanceWarningSent = false;
	
	private boolean gragraAnimated;

}
