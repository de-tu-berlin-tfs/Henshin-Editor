package agg.gui.trafo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdRule;
import agg.gui.event.EditEvent;
import agg.gui.event.EditEventListener;
import agg.gui.event.TransformEvent;
import agg.gui.treeview.dialog.GraGraLayerDialog;
import agg.xt_basis.GraTra;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;

/**
 * The class TransformLayered implements an interpreting transformation of a
 * layered gragra. It will be used by the GraGraTransform class.
 * 
 * @author $Author: olga $
 * @version $ID: TransformLayered.java,v 1.31 2000/07/31 09:46:16 shultzke Exp $
 */
public class TransformLayered extends Thread 
								implements GraTraEventListener,
											EditEventListener {

	public static int MAX_LAYERED_RUNS = 5;
	
	/** Creates a new instance */
	public TransformLayered(GraGraTransform transform) {
		this.gragraTransform = transform;
		this.gratra = new LayeredGraTraImpl();
		this.gratra.enableWriteLogFile(true);
		this.gratra.addGraTraListener(this);
	}

	public void dispose() {
		this.gratra.removeGraTraListener(this);
		this.gratra.dispose();
		this.rl.dispose();
		this.currentMatch = null;
		this.currentRule = null;
		this.gragra = null;
		this.event = null;	
	}

	public GraTra getGraTra() {
		return this.gratra;
	}

	/** Sets a gragra to transform */
	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
		this.gratra.setGraGra(this.gragra.getBasisGraGra());
		this.gratra.setHostGraph(this.gragra.getBasisGraGra().getGraph());
		this.gratra.addGraTraListener(this);
//		this.inheritanceWarningSent = false;
	}

	/** Sets the current completion strategy */
	public void setCompletionStrategy(MorphCompletionStrategy strat) {
		this.gratra.setCompletionStrategy(strat);
	}

	public void setParentFrame(JFrame f) {
		this.parent = f;
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
		this.gragraAnimated = false;
		this.steps = 0;
		this.cancelled = false;
		this.stopped = false;
		if (this.gratra.getHostGraph() != this.gragra.getBasisGraGra().getGraph()) {
			this.gratra.setHostGraph(this.gragra.getBasisGraGra().getGraph());
		}
		this.gratra.setLayerToStop(this.gragraTransform.getLayerToStop());
		this.consistencyCheckNotNeeded = !this.gragra.hasEnabledConstraints();
		this.consistencyWhenCheckAfterGraphTrafo = this.gragraTransform.consistencyCheckAfterGraphTrafoEnabled();
		this.gragraAnimated = this.gragra.isAnimated();
		
		layeredTransform();		
	}
	
	/** Stops the transformation */
	public void stopping() {
		if (this.gragraTransform.breakAllLayerEnabled()) {
			this.stopped = true;
		}
		this.gratra.stop();
	}

	public void nextLayer() {
		this.gratra.nextLayer();
	}

	private void layeredTransform() {
		this.gratra.setGraTraOptions(this.gragraTransform.getGraTraOptions());
		GraTraOptions gratraOptions = this.gratra.getGraTraOptions();
		gratraOptions.addOption("layered");
		this.gragra.getBasisGraGra().setGraTraOptions(
				this.gragraTransform.getGraTraOptionsList());

		this.rl = new RuleLayer(this.gragra.getBasisGraGra().getEnabledRules()); //getListOfRules());
		if (this.gragraTransform.showLayerEnabled()) {
			GraGraLayerDialog lg = new GraGraLayerDialog(this.parent, this.rl);
//			lg.setGraGra(gragra);
			lg.showGUI();
			if (lg.isCancelled()) {
//				rl = new RuleLayer(gragra.getBasisGraGra().getEnabledRules()); //getListOfRules());
			} else {
				this.gragra.getBasisGraGra().setRuleLayer(this.rl);
			}
		}
	
		this.gratra.transform();
	}
	
	/** Implements GraTraEventListener.graTraEventOccurred 
	 */
	public void graTraEventOccurred(GraTraEvent e) {
		String ruleName = "";
		this.event = e;
		this.msgGraTra = e.getMessage();
		
		if (this.msgGraTra == GraTraEvent.MATCH_VALID) {
//			if (e.getMatch().getRule() != null)
				ruleName = e.getMatch().getRule().getName();
			if (this.gragraTransform.selectMatchObjectsEnabled()) {
				this.gragra.getGraph().updateAlongMorph(e.getMatch());
			}
			
			this.gragra.getGraph().unsetNodeNumberChanged();
			
			this.gragraTransform.fireTransform(new TransformEvent(this,
					TransformEvent.MATCH_VALID, 
					this.event.getMatch(),
					"  match of  <" + ruleName + ">  is valid"));
			
		} else if (this.msgGraTra == GraTraEvent.STEP_COMPLETED) {
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
			
		} else if (this.msgGraTra == GraTraEvent.LAYER_FINISHED) {
			if (this.gratra.getCurrentLayer() >= 0) {
				
				if (!this.showGraphAfterStep) {					
					this.gragraTransform.getEditor().doStandardLayoutProc();
				}
				
				this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.LAYER_FINISHED, " Layer  "
								+ e.getMessageText() + "  done.  "));
				
			} else if (this.gragraTransform.layeredLoopEnabled()) {
				this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.LAYER_FINISHED, " Loop over layer. First layer will start. "));
			}
			
		} else if (this.msgGraTra == GraTraEvent.TRANSFORM_FINISHED) {
//			System.out.println("GraTraEvent.TRANSFORM_FINISHED");
			this.gratra.stop();
			
			this.gragra.getGraph().clearMarks();
			
			if (!this.showGraphAfterStep) {					
				this.gragraTransform.getEditor().doStandardLayoutProc();
			}
			
			if (!this.cancelled) {
				if (this.steps == 0) {
					this.gragraTransform.fireTransform(new TransformEvent(this,
							TransformEvent.CANNOT_TRANSFORM, e.getMessageText()));
				} 
			}

			this.gragraTransform.fireTransform(new TransformEvent(this,
							TransformEvent.STOP, "  finished.  "));
			System.out.println("*** Layered transformation - finished.  ");
			
			if (this.gragraTransform.layeredLoopEnabled()
					&& this.gragraTransform.resetGraphEnabled()) {
				
				if (!this.consistencyWhenCheckAfterGraphTrafo
						|| this.consistencyCheckNotNeeded) 
					this.gragraTransform.fireTransform(new TransformEvent(this,
						TransformEvent.RESET_GRAPH));
				
			}
		}	
		
		else if ((this.msgGraTra == GraTraEvent.INPUT_PARAMETER_NOT_SET)) {			
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
						Thread.sleep(1000);
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
//			if (this.showGraphAfterStep) {			
//				this.currentRule = this.event.getMatch().getRule();
//				disposeMatch();
//			}
			
		} else if (this.msgGraTra == GraTraEvent.INCONSISTENT) {
			// ruleName = currentRule.getName();
			// String msg = "Graph inconsistency after applying rule <
			// "+ruleName+"> !";

			if (this.gragraTransform.consistencyCheckAfterGraphTrafoEnabled()) {
				this.consistencyWhenCheckAfterGraphTrafo = false;
				this.gragraTransform.fireTransform(new TransformEvent(this,
							TransformEvent.INCONSISTENT, this.event.getMessageText()));
			}
		} else if (this.msgGraTra == GraTraEvent.MATCH_FAILED) {
			
		}
	}

	/** Implements EditEventListener.editEventOccurred */
	public void editEventOccurred(EditEvent e) {
		if (e.getMsg() == EditEvent.INPUT_PARAMETER_OK) {
			this.inputParameterOK = true;
		}
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
		} else
			inheritanceWarningSent = false;
	}
*/
	
	private JFrame parent;

	private GraGraTransform gragraTransform;

	private LayeredGraTraImpl gratra;
	
	private boolean consistencyWhenCheckAfterGraphTrafo = true;
	
	private int msgGraTra;

	private GraTraEvent event;

	private EdGraGra gragra;

	private Rule currentRule;

	private Match currentMatch;

	private boolean inputParameterOK = false;

	private int steps;

	private boolean cancelled = false;

	private boolean stopped = false;

	private RuleLayer rl;

//	private int extraRuns = 10;

	private boolean showGraphAfterStep;
	
//	private boolean inheritanceWarningSent = false;
	
	private boolean gragraAnimated;
	
	private boolean consistencyCheckNotNeeded = false;

}
