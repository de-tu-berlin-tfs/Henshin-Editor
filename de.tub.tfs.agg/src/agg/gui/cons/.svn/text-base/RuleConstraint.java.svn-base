package agg.gui.cons;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import agg.cons.AtomApplCond;
import agg.cons.AtomConstraint;
import agg.cons.EvalSet;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdMorphism;
import agg.gui.editor.GraphEditor;
import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

public class RuleConstraint {

	private JButton but_back, but_forw;

	private JLabel label;

	private JPanel mainPane;

	private GraphEditor graphs[];

	private EdGraGra layout;

	private Rule rule;

	private int atom_index;

	private int shown_cond;

	public RuleConstraint(EdGraGra eGra) {
		this.graphs = new GraphEditor[6];
		JSplitPane smallsplit[] = new JSplitPane[3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				this.graphs[i * 2 + j] = new GraphEditor();
				this.graphs[i * 2 + j].setSize(new Dimension(145, 120));
				this.graphs[i * 2 + j].setPreferredSize(new Dimension(145, 120));
				this.graphs[i * 2 + j]
						.setEditMode(agg.gui.editor.EditorConstants.VIEW);
				this.graphs[i * 2 + j].setGraph(null);
			}
			smallsplit[i] = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					this.graphs[i * 2], this.graphs[i * 2 + 1]);
			smallsplit[i].setOneTouchExpandable(true);
			smallsplit[i].setContinuousLayout(true);
			smallsplit[i].setPreferredSize(new Dimension(300, 130));
		}
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setOneTouchExpandable(true);
		split.setContinuousLayout(true);
		split.setTopComponent(smallsplit[0]);
		split.setBottomComponent(smallsplit[1]);
		JSplitPane main = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		main.setOneTouchExpandable(true);
		main.setContinuousLayout(true);
		main.setTopComponent(split);
		main.setBottomComponent(smallsplit[2]);

		this.but_back = new JButton("<");
		this.but_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addShown(-1);
			}
		});
		this.but_forw = new JButton(">");
		this.but_forw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addShown(+1);
			}
		});
		this.label = new JLabel("0 / 0");
		JPanel butpan = new JPanel(new FlowLayout());
		butpan.add(this.but_back);
		butpan.add(this.but_forw);
		butpan.add(this.label);

		this.mainPane = new JPanel(new BorderLayout());
		this.mainPane.add(butpan, BorderLayout.NORTH);
		this.mainPane.add(main, BorderLayout.CENTER);
		setLayout(eGra);
		setRule(null);
		setAtomic(null, 0);
	}

	public Component getComponent() {
		return this.mainPane;
	}

	public void reset() {
	}

	public void setRule(Rule r) {
		this.rule = r;
		this.shown_cond = 0;
		paint();
	}

	public void setAtomic(AtomConstraint a, int index) {
		this.shown_cond = 0;
		if (a == null) {
			this.atom_index = -1;
		} else {
			this.atom_index = index;
		}
		paint();
	}

	public void setLayout(EdGraGra e) {
		this.layout = e;
	}

	private EvalSet getEvalSet() {
		if (this.atom_index < 0 || this.rule == null)
			return null;
		EvalSet ret = null;
		Vector<EvalSet> v = this.rule.getAtomApplConds();
		if (this.atom_index < v.size())
			ret = v.get(this.atom_index);
		if (ret != null && ret.getSet().isEmpty())
			ret = null;
		return ret;
	}

	protected void addShown(int add) {
		EvalSet set = getEvalSet();
		if (set != null) {
			this.shown_cond += add;
			if (this.shown_cond < 0)
				this.shown_cond = 0;
			else if (this.shown_cond >= set.getSet().size())
				this.shown_cond = set.getSet().size() - 1;
		} else
			this.shown_cond = 0;
		paint();
	}

	private void paint() {
		EvalSet set = getEvalSet();
		AtomApplCond c = null;
		if (set != null) {
			if (this.shown_cond >= 0 && this.shown_cond < set.getSet().size())		
				c = (AtomApplCond) set.getSet().get(this.shown_cond);
		if (c == null) {
			setMorphism(null, 0);
			setMorphism(null, 1);
			setMorphism(null, 2);
			this.but_forw.setEnabled(false);
			this.but_back.setEnabled(false);
			this.label.setText("0 / 0");
		} else {
			setMorphism(c.getPreCondition(), 0);
			setMorphism(c.getT(), 1);
			setMorphism(c.getQ(), 2);
			this.but_forw.setEnabled(this.shown_cond + 1 < set.getSet().size());
			this.but_back.setEnabled(this.shown_cond > 0);
			int i1 = this.shown_cond + 1;
			this.label.setText(i1 + " / " + set.getSet().size());
		}
	}
	}

	private String getGraphName(int i) {
		switch (i) {
		case 0:
			return "R = right rule side";
		case 1:
		case 2:
			return "S = overlap R + premise";
		case 3:
		case 5:
			return "T = pushout";
		case 4:
			return "conclusion of atom";
		}
		return "PANIK-GRAPH";
	}

	private void setGraph(Graph g, int i) {
		GraphEditor ge = this.graphs[i];
		if (g == null) {
			ge.setGraph(null);
			ge.updateGraphics();
		} else {
			if (this.layout == null) {
				System.err.println("RuleConstraint.setGraph: have no EdGraGra");
				return;
			}
			EdGraph eg = ge.getGraph();
			if (eg == null || eg.getBasisGraph() != g) {
				eg = new EdGraph(g);
				eg.setTypeSet(this.layout.getTypeSet());
				eg.updateGraph();
				ge.setGraph(eg);
				// geLeft.setEditMode(agg.editor.impl.EditorConstants.VIEW);
				ge.setTitle(getGraphName(i));
			}
		}
	}

	private void setMorphism(OrdinaryMorphism m, int i) {
		if (m == null) {
			setGraph(null, i * 2);
			setGraph(null, i * 2 + 1);
			return;
		}
		setGraph(m.getOriginal(), i * 2);
		setGraph(m.getImage(), i * 2 + 1);
		EdMorphism numbers = new EdMorphism(m);
		EdGraph eg;
		eg = this.graphs[i * 2].getGraph();
		eg.setMorphismMarks(numbers.getSourceOfMorphism(), true);
		eg = this.graphs[i * 2 + 1].getGraph();
		eg.setMorphismMarks(numbers.getTargetOfMorphism(), true);
		this.graphs[i * 2].updateGraphics();
		this.graphs[i * 2 + 1].updateGraphics();
	}
}
