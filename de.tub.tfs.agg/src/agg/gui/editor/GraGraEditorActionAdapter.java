/**
 * 
 */
package agg.gui.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

import agg.attribute.impl.AttrTupleManager;
import agg.gui.event.EditEvent;
import agg.gui.icons.TextIcon;
import agg.layout.GraphLayouts;
import agg.xt_basis.GraTraOptions;

/**
 * @author olga
 *
 */
public class GraGraEditorActionAdapter implements ActionListener {

	private GraGraEditor editor;
	
	/**
	 * 
	 */
	public GraGraEditorActionAdapter(final GraGraEditor gragraeditor) {
		this.editor = gragraeditor;
	}

	/** Implements ActionListener.actionPerformed */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (!((JButton) e.getSource()).isEnabled()) {
				return;
			}
						
			final JButton b = (JButton) e.getSource();
			if (b.equals(this.editor.getTransformationKindButton())) {
				if (((TextIcon) b.getIcon()).getText().equals("NT")) {
					((TextIcon) b.getIcon()).setText("LT");
					this.editor.getGraGraTransform().getOptionGUI().doClick(GraTraOptions.LAYERED);
				} else if (((TextIcon) b.getIcon()).getText().equals("LT")) {
					((TextIcon) b.getIcon()).setText("PT");
					this.editor.getGraGraTransform().getOptionGUI().doClick(
								GraTraOptions.PRIORITY);
				} else if (((TextIcon) b.getIcon()).getText().equals("PT")) {
					((TextIcon) b.getIcon()).setText("ST");
					this.editor.getGraGraTransform().getOptionGUI().doClick(
								GraTraOptions.RULE_SEQUENCE);
					this.editor.fireEditEvent(new EditEvent(this, EditEvent.SHOW_RULE_SEQUENCE, ""));
				} else if (((TextIcon) b.getIcon()).getText().equals("ST")) {					
					((TextIcon) b.getIcon()).setText("NT");
					this.editor.fireEditEvent(new EditEvent(this, EditEvent.HIDE_RULE_SEQUENCE, ""));
					this.editor.getGraGraTransform().getOptionGUI().doClick(
								GraTraOptions.NONDETERMINISTICALLY);
				}
			} else if (b.equals(this.editor.getUndoButton()) 
					&& this.editor.getUndoManager() != null 
					&& this.editor.getUndoManager().isEnabled()) {
				if (!this.editor.getUndoManager().canUndo()) {
					this.editor.getUndoButton().setEnabled(false);
					this.editor.setUndoStepButtonEnabled(false);
				}
				if (this.editor.getUndoManager().canRedo())
					this.editor.getRedoButton().setEnabled(true);
			} else if (b.equals(this.editor.getUndoStepButton()) 
					&& this.editor.getUndoManager() != null 
						&& this.editor.getUndoManager().isEnabled()) {
				if (!this.editor.getUndoManager().canUndo()) {
					this.editor.getUndoButton().setEnabled(false);
					this.editor.setUndoStepButtonEnabled(false);
				}
			} else if (b.equals(this.editor.getRedoButton()) 
					&& this.editor.getUndoManager() != null 
					&& this.editor.getUndoManager().isEnabled()) {
				if (!this.editor.getUndoManager().canRedo()) {
					this.editor.getRedoButton().setEnabled(false);
				}
			}
			
		} else if (e.getSource() instanceof JCheckBoxMenuItem) {
			String actionCommand = ((JCheckBoxMenuItem)e.getSource()).getActionCommand();
//			if (actionCommand.equals("undirectedArcs")) {
//				if (this.editor.getGraGra() != null)
//					this.editor.getGraGra().getBasisGraGra().getTypeSet()
//					.setArcDirected(!((JCheckBoxMenuItem)e.getSource()).isSelected());
//			} else 
			if (actionCommand.equals("nonparallelArcs")) {
				if (this.editor.getGraGra() != null)
					this.editor.getGraGra().getBasisGraGra().getTypeSet()
					.setArcParallel(!((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
			if (actionCommand.equals("typesOnTop")) 
				this.editor.setTypesAlwaysOn(((JCheckBoxMenuItem)e.getSource()).isSelected());				
			else if (actionCommand.equals("showAttributesOfRule")) {
				this.editor.getRuleEditor().setAttributeVisible(((JCheckBoxMenuItem)e.getSource()).isSelected());				
			} else if (actionCommand.equals("showAttributesOfGraph")) {
				this.editor.getGraphEditor().setGraphAttributeVisible(((JCheckBoxMenuItem)e.getSource()).isSelected());			
			} else if (actionCommand.equals("showAttributesOfTypeGraph")) {				
				this.editor.getGraphEditor().setTypeGraphAttributeVisible(((JCheckBoxMenuItem)e.getSource()).isSelected());			
			}
				
		} else if (e.getSource() instanceof JMenuItem) {
			if (!((JMenuItem) e.getSource()).isEnabled())
				return;
		} else if (e.getSource() instanceof JRadioButton) {
			if (((JRadioButton) e.getSource()).getActionCommand().equals(
					GraTraOptions.NONDETERMINISTICALLY)) {
				this.editor.resetTransformationKindIcon(this.editor.getGraGraTransform()
						.nondeterministicallyEnabled(), "NT");
			} else if (((JRadioButton) e.getSource()).getActionCommand()
					.equals(GraTraOptions.LAYERED)) {
				this.editor.resetTransformationKindIcon(this.editor.getGraGraTransform().layeredEnabled(),
						"LT");
			} else if (((JRadioButton) e.getSource()).getActionCommand()
					.equals(GraTraOptions.PRIORITY)) {
				this.editor.resetTransformationKindIcon(this.editor.getGraGraTransform().priorityEnabled(),
						"PT");
			} else if (((JRadioButton) e.getSource()).getActionCommand()
					.equals(GraTraOptions.RULE_SEQUENCE)) {
				this.editor.resetTransformationKindIcon(this.editor.getGraGraTransform()
						.ruleSequenceEnabled(), "ST");
			}
		} else if (e.getSource() instanceof JCheckBox) {
			if (((JCheckBox) e.getSource()).getActionCommand().equals(
					GraTraOptions.WAIT_AFTER_STEP)) {
				this.editor.setSleep(this.editor.getGraGraTransform().waitAfterStepEnabled());
			}
			return;
		}

		String command = e.getActionCommand();
		
		/* edit commands */
		if (command.equals("attributes"))
			this.editor.attrsProc();
		else if (command.equals("delete"))
			this.editor.deleteProc();
		else if (command.equals("copy"))
			this.editor.copyProc();
		else if (command.equals("paste"))
			this.editor.pasteProc();
		else if (command.equals("selectAll"))
			this.editor.selectAllProc();
		else if (command.equals("selectNodeType"))
			this.editor.selectNodeTypeProc();
		else if (command.equals("selectArcType"))
			this.editor.selectArcTypeProc();
		else if (command.equals("deselectAll"))
			this.editor.deselectAllProc();
		else if (command.equals("straighten"))
			this.editor.doStraightenArcsProc();
		else if (command.equals("identicRule"))
			this.editor.doIdenticRule();
		else if (command.equals("identicNAC"))
			this.editor.doIdenticNAC();
		else if (command.equals("identicPAC")) 
			this.editor.doIdenticPAC();
		else if (command.equals("identicAC")) 
			this.editor.doIdenticGAC();
		else if (command.equals("makeNACFromRHS"))
			this.editor.doNACDuetoRHS();
		else if (command.equals("makeGACFromRHS")) 
			this.editor.doGACDuetoRHS();
		
		else if (command.equals("bold") 
				|| command.equals("italic")
				|| command.equals("plain") 
				|| command.equals("LARGE")
				|| command.equals("large") 
				|| command.equals("small")
				|| command.equals("tiny") 
				|| command.equals("0.2")
				|| command.equals("0.3")
				|| command.equals("0.5")
				|| command.equals("0.7") 
				|| command.equals("1.0")
				|| command.equals("1.5")
				|| command.equals("2.0")
				|| command.equals("typesON")) {
			this.editor.doPreferencesProc(command);
		} 
		else if (command.equals("scaleGraphOnly")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				this.editor.setScalingGraphOnly(((JCheckBoxMenuItem) e.getSource()).isSelected());
			}
		}		
		/* mode commands */
		else if (command.equals("drawMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Draw");
			else if (e.getSource() instanceof JCheckBoxMenuItem)
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
			this.editor.setEditMode(EditorConstants.DRAW);// 11
		} else if (command.equals("selectMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Select");
			else if (e.getSource() instanceof JCheckBoxMenuItem)
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
			this.editor.setEditMode(EditorConstants.SELECT);// 12
		} else if (command.equals("moveMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Move");
			else if (e.getSource() instanceof JCheckBoxMenuItem)
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
			this.editor.setEditMode(EditorConstants.MOVE);// 13
		} else if (command.equals("attributesMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Attributes");
			else if (e.getSource() instanceof JCheckBoxMenuItem)
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
			this.editor.setEditMode(EditorConstants.ATTRIBUTES);// 114
		} else if (command.equals("mapMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Map");
			else if (e.getSource() instanceof JCheckBoxMenuItem)
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
			this.editor.setEditMode(EditorConstants.MAP);// 115
		} else if (command.equals("unmapMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Unmap");
			else if (e.getSource() instanceof JCheckBoxMenuItem)
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
			this.editor.setEditMode(EditorConstants.UNMAP);// 116
		} else if (command.equals("imageMode")) {
			if (e.getSource() instanceof JButton)
				this.editor.forwardModeCommand("Image_view");
			else if (e.getSource() instanceof JCheckBoxMenuItem) {
				this.editor.forwardModeCommand((JCheckBoxMenuItem) e.getSource());
				this.editor.setNodeIconable(((JCheckBoxMenuItem) e.getSource())
						.isSelected());
			}
		}
		/* transform commands */
		else if (command.equals("start")) {
			if (this.editor.getGraGra() == null || !this.editor.isEditable()
					|| !this.editor.isGraGraReadyToTransform(this.editor.getGraGra())
					|| this.editor.isTransformationRunning()) {
				return;
			}
			
			this.editor.resetStepCounter();
			if (this.editor.getEditMode() == EditorConstants.ATTRIBUTES) {
				this.editor.setEditMode(EditorConstants.MOVE);
				this.editor.forwardModeCommand("Move");
			}
			this.editor.getTypeEditor().setEnabled(false);
			this.editor.splitPane1.setDividerLocation(this.editor.splitPane1.getWidth());

			// update tool bar and menus
			this.editor.selectToolBarTransformItem("start");
			if (e.getSource() instanceof JButton) {
				this.editor.resetTransformMenu("Start");
			} else if (e.getSource() instanceof JMenuItem) {
				this.editor.resetTransformMenu((JMenuItem) e.getSource());
			}
			this.editor.resetEditModeAfterMapping(this.editor.getLastEditMode());

			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(false);

			this.editor.enableStopButton(true);
			
			this.editor.getGraGra().destroyAllMatches();
			this.editor.getGraphEditor().getGraph().adjustTypeObjectsMap();
			
			// prepare layouter: path of jpg images and metrics
			if (this.editor.getGraphLayouter().isEnabled()) {
				this.editor.doPrepareLayouterProc();
			}
			else {
				this.editor.doPrepareDefaultGraphLayout();
			}

			this.editor.getGraGra().getGraph().setTransformChangeEnabled(true);

//			if (this.editor.getGraGra().isAnimated()
//					&& this.editor.isImageViewModeSelected()) {	
//				if (this.editor.getUndoManager() != null) 
//					this.editor.getUndoManager().setEnabled(false);
//			}
			
			if (this.editor.getUndoManager() != null 
					&& this.editor.getUndoManager().isEnabled()) {				
				this.editor.getUndoManager().setUndoEndOfTransformStep();
			}
			
			if (this.editor.getGraGraTransform().layeredEnabled()) {
				if (!this.editor.isLayeredTransformationRunning()) {
					this.editor.doPrepareTransformProc();
					this.editor.startLayeredTransform();
				} else if (!this.editor.isSleeping())
					this.editor.fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							" Please wait. Transformation is running ... "));
			} else if (this.editor.getGraGraTransform().ruleSequenceEnabled()) {
				if (!this.editor.isRuleSequencesTransformationRunning()) {
					this.editor.doPrepareTransformProc();
					this.editor.startRuleSequenceTransform();
				} else if (!this.editor.isSleeping())
					this.editor.fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							" Please wait. Transformation is running ... "));
			} else {
				if (!this.editor.isDefaultTransformationRunning()) {
					this.editor.doPrepareTransformProc();
					this.editor.startInterpreterTransform();
				} else if (!this.editor.isSleeping())
					this.editor.fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							" Please wait. Transformation is running ... "));
			}
		} else if (command.equals("stop")) {
			if (this.editor.getGraGra() != null
					&& this.editor.getGraGra().getGraph() != null) {
				
				if (!this.editor.getGraGra().getGraph().isEditable()) {
					return;
				}
								
				if (this.editor.isAnimationRunning()) {
						this.editor.getNodeAnimation().setStop();	
				}
				
				if (this.editor.isLayeredTransformationRunning()) {
					this.editor.selectToolBarTransformItem("stop");

					if (this.editor.getGraGraTransform().breakAllLayerEnabled()) {
						if (e.getSource() instanceof JButton)
							this.editor.resetTransformMenu("Stop");
						else if (e.getSource() instanceof JMenuItem)
							this.editor.resetTransformMenu((JMenuItem) e.getSource());						
					}
					this.editor.doStopTransformLayeredProc();
				} else if (this.editor.isDefaultTransformationRunning()) {
					this.editor.selectToolBarTransformItem("stop");
					if (e.getSource() instanceof JButton)
						this.editor.resetTransformMenu("Stop");
					else if (e.getSource() instanceof JMenuItem)
						this.editor.resetTransformMenu((JMenuItem) e.getSource());
					this.editor.doStopTransformInterpreterProc();
				} else if (this.editor.isRuleSequencesTransformationRunning()) {
					this.editor.selectToolBarTransformItem("stop");
					if (e.getSource() instanceof JButton)
						this.editor.resetTransformMenu("Stop");
					else if (e.getSource() instanceof JMenuItem)
						this.editor.resetTransformMenu((JMenuItem) e.getSource());
					this.editor.doStopTransformRuleSequencesProc();
				}
			}
		} else if (command.equals("match")) {
			this.editor.doPrepareInteractiveMatchProc();
		} else if (command.equals("completion")) {
			this.editor.doPrepareCompletionMatchProc();
		} else if (command.equals("step")) {
			if (this.editor.getEditMode() == EditorConstants.ATTRIBUTES) {
				this.editor.setEditMode(EditorConstants.MOVE);
				this.editor.forwardModeCommand("Move");
			}
			this.editor.doExecuteStepProc();
		} else if (command.equals("options")) {
			this.editor.showOptionGUI();
		}		
		else if (command.equals("undoStep")) {
			this.editor.undoTransformStep();
		} else if (command.equals("undo")) {
			this.editor.undoEdit();
		} else if (command.equals("redo")) {
			this.editor.redoEdit();
		} else if (command.equals("discardAllEdits")) {
			this.editor.discardAllEdits();			
		} 
		else if (command.equals("graphlayout")) {
			this.editor.doGraphLayout();
		} 
		// graph layout menu item
		else if (command.equals(GraphLayouts.DEFAULT_LAYOUT)
//				|| command.equals(GraphLayouts.DIRECTED_LAYOUT)
				|| command.equals(GraphLayouts.SPRING_LAYOUT)
				|| command.equals(GraphLayouts.TREE_VERTICAL_LAYOUT)
				|| command.equals(GraphLayouts.TREE_HORIZONTAL_LAYOUT)
				|| command.equals(GraphLayouts.RADIAL_LAYOUT)
				|| command.equals(GraphLayouts.GRID_LAYOUT)
				|| command.equals(GraphLayouts.VERTICAL_LAYOUT)
				|| command.equals(GraphLayouts.HORIZONTAL_LAYOUT)
//				|| command.equals(GraphLayouts.DIRECTED_DRAW2D_LAYOUT)
				) {
			this.editor.setGraphLayoutAlgorithmName(((JMenuItem)e.getSource()).getText());
		}
		
	}
	
}
