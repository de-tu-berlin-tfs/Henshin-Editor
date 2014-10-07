// $Id: PriorityOfTransform.java,v 1.2 2010/09/23 08:20:39 olga Exp $

package agg.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import agg.gui.trafo.GraGraTransform;

@SuppressWarnings("serial")
public class PriorityOfTransform extends JMenu {

	private JCheckBoxMenuItem mi;

	private int prior;

	private GraGraTransform transform;

	public PriorityOfTransform(GraGraTransform trans) {
		this();
		this.transform = trans;
	}

	public PriorityOfTransform() {
		super("Priority");

		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("1"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("2"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("3"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("4"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("5"));
		this.mi.setState(true);
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("6"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("7"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("8"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("9"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
		this.mi = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("10"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onlyOneChoice(e);
			}
		});
	}

	public void setGraGraTransform(GraGraTransform trans) {
		this.transform = trans;
	}

	void onlyOneChoice(ActionEvent e) {
		((JCheckBoxMenuItem) e.getSource()).setSelected(true);
		for (int i = 0; i < 9; i++) {
			if (((JCheckBoxMenuItem) e.getSource()).getState()) {
				if (!(getItem(i)).equals(e.getSource()))
					(getItem(i)).setSelected(!((JCheckBoxMenuItem) e
							.getSource()).getState());
				else
					this.prior = i + 1;
			}
		}
		this.transform.setTransformationThreadPriority(this.prior);
		// System.out.println(">>> Priority "+prior);
	}
}
