package agg.gui.cons;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import agg.cons.AtomConstraint;
import agg.editor.impl.EdGraGra;
import agg.gui.cpa.RuleModel;
import agg.gui.cpa.RuleTree;
import agg.gui.parser.event.ParserGUIEvent;
import agg.gui.parser.event.ParserGUIListener;
import agg.xt_basis.GraGra;
import agg.xt_basis.Rule;

public class ConstraintsGUI implements ParserGUIListener {

	JSplitPane mainPane;

	/**
	 * the top tree view
	 */
	RuleTree rtTop;

	/**
	 * the bottom tree view
	 */
	RuleTree rtBottom;

	RuleConstraint right;

	EdGraGra eGra;

	public ConstraintsGUI() {
		this.rtTop = new RuleTree(null, false, false); // the Rules
		this.rtTop.addParserGUIListener(this);
		this.rtBottom = new RuleTree(null, true, false); // the Atomics
		this.rtBottom.addParserGUIListener(this);

		JSplitPane treePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		treePane.setOneTouchExpandable(true);
		treePane.setContinuousLayout(true);
		JScrollPane tmpPane = new JScrollPane(this.rtTop.getTree());
		tmpPane.setSize(170, 210);
		tmpPane.setPreferredSize(new Dimension(170, 210));
		treePane.setTopComponent(tmpPane);
		tmpPane = new JScrollPane(this.rtBottom.getTree());
		tmpPane.setSize(170, 210);
		tmpPane.setPreferredSize(new Dimension(170, 210));
		treePane.setBottomComponent(tmpPane);
		treePane.getTopComponent().setSize(185, 300);
		treePane.getBottomComponent().setSize(185, 300);
		treePane.resetToPreferredSizes();

		this.right = new RuleConstraint(null);

		this.mainPane = new JSplitPane();
		this.mainPane.setOneTouchExpandable(true);
		this.mainPane.setContinuousLayout(true);
		this.mainPane.setRightComponent(this.right.getComponent());
		this.mainPane.setLeftComponent(treePane);

		setGrammar(null);
		setLayout(null);
	}

	private void setGrammar(GraGra gragra) {
		if (gragra != null) {
			this.rtTop.setGrammar(gragra);
			this.rtBottom.setGrammar(gragra);
		}
	}

	private void setLayout(EdGraGra edgragra) {
		this.eGra = edgragra;
		this.right.setLayout(edgragra);
	}

	/**
	 * get the container which contains the gui
	 * 
	 * @return the gui
	 */
	public Container getContainer() {
		return this.mainPane;
	}

	/**
	 * set the grammar with layout
	 * 
	 * @param edgragra
	 *            the grammar
	 */
	public void setGraGra(EdGraGra edgragra) {
		this.eGra = edgragra;
		revalidate();
	}

	public void revalidate() {
		setLayout(this.eGra);
		setGrammar(this.eGra != null ? this.eGra.getBasisGraGra() : null);
		this.right.reset();
		this.mainPane.repaint();
		this.mainPane.validate();
	}

	/**
	 * this gui listens for <CODE>ParserGUIEvents</CODE>. So it must
	 * implement the listener
	 * 
	 * @param pguie
	 *            the event
	 */
	public void occured(ParserGUIEvent pguie) {
		if (pguie.getSource() == this.rtTop) {
			RuleModel.TreeData td = (RuleModel.TreeData) pguie.getData();
			if (td.isRule()) {
				this.right.setRule((Rule) td.getData());
			}
		} else if (pguie.getSource() == this.rtBottom) {
			RuleModel.TreeData td = (RuleModel.TreeData) pguie.getData();
			if (td.isAtomic()) {
				AtomConstraint a = (AtomConstraint) td.getData();
				if (this.eGra != null) {
					int index = this.eGra.getBasisGraGra().getListOfAtomics()
							.indexOf(a);
					this.right.setAtomic(a, index);
				} else {
					this.right.setAtomic(null, 0);
				}
			}
		}
		this.mainPane.repaint();
	}
}
