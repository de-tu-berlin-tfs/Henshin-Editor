/**
 * 
 */
package agg.gui.animation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.Node;
import agg.xt_basis.Type;
import agg.xt_basis.TypeGraph;

/**
 * @author olga
 *
 */
public class AnimationParamDialog //extends JPanel 
{

	protected final JDialog dialog;
	protected final JComboBox kindField;	
	protected JTextField stepField, delayField, plusField;
	protected boolean changed;
	protected final Vector<String> kinds;
	
	protected JComboBox targetEdgeTypesField;
	protected final Vector<String> targetEdgeTypeNames;
	
	protected final JLabel text;
	protected AnimationParam param;
	
	public AnimationParamDialog(final AnimationParam parameter) {
		super();
		
		this.dialog = new JDialog(new JFrame(), " Node Animation Parameter ");
		this.dialog.setModal(false);
		this.dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				AnimationParamDialog.this.dialog.setVisible(false);
			}
		});
		
		this.param = parameter;
		
		this.text = new JLabel("   ");
		
		this.kinds = NodeAnimation.getAnimationKindsAsString();		
		this.kindField = new JComboBox(this.kinds);
		
		this.targetEdgeTypeNames = new Vector<String> ();
		
		final JPanel content = initDialog();
		if (this.param.kind == NodeAnimation.JUMP
				|| this.param.kind == NodeAnimation.CROSS
				|| this.param.kind == NodeAnimation.COMBI_CROSS) {
			this.stepField.setEditable(false);
		}
		
		this.dialog.getContentPane().add(content);
		this.dialog.validate();
		
		this.dialog.setSize(300, 200);
		this.dialog.pack();
	}
	
	public AnimationParamDialog(
			final AnimationParam parameter, 
			final Type nodeType,
			final Graph typeGraph) {
		
		super();
		
		this.dialog = new JDialog(new JFrame(), " Node Animation Parameter ");
		this.dialog.setModal(false);
		this.dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				AnimationParamDialog.this.dialog.setVisible(false);
			}
		});
		
		this.param = parameter;
		
		this.text = new JLabel("   ");
		
		this.kinds = NodeAnimation.getAnimationKindsAsString();		
		this.kindField = new JComboBox(this.kinds);
		
		this.targetEdgeTypeNames = new Vector<String> ();
		
		if (typeGraph instanceof TypeGraph) {			
			final List<Node> list = typeGraph.getNodes(nodeType);
			if (list != null && list.size() == 1) {
				final Node n = list.get(0);
				final Iterator<Arc> outarcs = n.getOutgoingArcsSet().iterator();
				while (outarcs.hasNext()) {
					final Arc arc = outarcs.next();
					String tname = arc.getType().getName();
					if ("".equals(tname))
						tname = "[unnamed";
					if (!this.targetEdgeTypeNames.contains(tname))
						this.targetEdgeTypeNames.add(tname);
				}
			}
			
			this.targetEdgeTypesField = new JComboBox(this.targetEdgeTypeNames);
		}
		
		final JPanel content = initDialog();
		
		if (this.param.kind == NodeAnimation.JUMP
				|| this.param.kind == NodeAnimation.CROSS
				|| this.param.kind == NodeAnimation.COMBI_CROSS) {
			this.stepField.setEditable(false);
		}
		
		this.dialog.getContentPane().add(content);
		this.dialog.validate();
		
		this.dialog.setSize(300, 200);
		this.dialog.pack();
		
	}
	
	public boolean isVisible() {
		return this.dialog.isVisible();
	}
	
	public void setVisible(boolean b) {
		this.dialog.setVisible(b);
	}
	
	public void showParameterDialog(int x, int y) {
		this.dialog.setLocation(x, y);
		this.dialog.setVisible(true);
		this.dialog.toFront();
		this.changed = false;
	}
	
	public boolean hasChanged() {
		return this.changed;
	}
	
	public void unsetChanged() {
		this.changed = false;
	}
	
	private JPanel initDialog() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(" Please set values "));
		
//		panel.add(text, BorderLayout.NORTH);
		
		final JPanel p = new JPanel(new GridLayout(0,1));
		final JPanel p1 = makeKindField(this.param.getKind());
		p.add(p1);
		
		final JPanel p2 = makeStepField(this.param.getStep());
		p.add(p2);
		
		final JPanel p3 = makeDelayField(this.param.getDelay());
		p.add(p3);
		
		final JPanel p4 = makeEndPlusField(this.param.getEndPlus());
		p.add(p4);
		
		if (this.targetEdgeTypesField != null) {	
			String paramTargetEdgeTypeName = this.targetEdgeTypeNames.get(0);
			if (this.param.getTargetEdgeTypeName() != null) {				
				paramTargetEdgeTypeName = this.param.getTargetEdgeTypeName();				
			} else {
				this.param.setTargetEdgeTypeName(paramTargetEdgeTypeName);				
			}
			
			final JPanel p5 = makeTargetEdgeTypesField(paramTargetEdgeTypeName);
			p.add(p5);
		}
		
		final JPanel pClose = makeCloseButton();
		
		panel.add(p, BorderLayout.CENTER);
		panel.add(pClose, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel makeKindField(String value) {
		final JPanel p1 = new JPanel(new GridLayout(0, 1));
		p1.setBorder(new TitledBorder(""));
		
		final JLabel l1 = new JLabel("Animation kind");
		this.kindField.setSelectedItem(value);
		this.kindField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String kind = (String) e.getItem();
					if (kind.equals("JUMP")) {
						AnimationParamDialog.this.changed = AnimationParamDialog.this.changed 
								|| (AnimationParamDialog.this.param.kind != NodeAnimation.JUMP);
						AnimationParamDialog.this.param.kind = NodeAnimation.JUMP;
						AnimationParamDialog.this.stepField.setEditable(false);
					} 
					else if (kind.equals("WORM")) {
						AnimationParamDialog.this.changed = AnimationParamDialog.this.changed 
								|| (AnimationParamDialog.this.param.kind != NodeAnimation.WORM);
						AnimationParamDialog.this.param.kind = NodeAnimation.WORM;
						AnimationParamDialog.this.stepField.setEditable(true);
					}
					else if (kind.equals("CROSS")) {
						AnimationParamDialog.this.changed = AnimationParamDialog.this.changed 
								|| (AnimationParamDialog.this.param.kind != NodeAnimation.CROSS);
						AnimationParamDialog.this.param.kind = NodeAnimation.CROSS;
						AnimationParamDialog.this.stepField.setEditable(false);
					}
					else if (kind.equals("COMBI_CROSS")) {
						AnimationParamDialog.this.changed = AnimationParamDialog.this.changed || (AnimationParamDialog.this.param.kind != NodeAnimation.COMBI_CROSS);
						AnimationParamDialog.this.param.kind = NodeAnimation.COMBI_CROSS;
						AnimationParamDialog.this.stepField.setEditable(false);
					}
				}
			}
		});
		p1.add(l1);
		p1.add(this.kindField);
		
		return p1;
	}
	
	private JPanel makeCloseButton() {	
		final JPanel p = new JPanel(new GridLayout(1, 0));
		final JButton close = new JButton("Close");
		p.add(close, BorderLayout.CENTER);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAnimationParameter();
				AnimationParamDialog.this.dialog.setVisible(false);
			}
		});
		
		p.add(new JLabel("   "));
		p.add(close);
		p.add(new JLabel("   "));
		
		final JPanel p1 = new JPanel(new BorderLayout());
		p1.add(p, BorderLayout.SOUTH);
		
		return p1;
	}
	
	public AnimationParam getAnimationParameter() {
		this.param.setKind((String) this.kindField.getSelectedItem());
		this.param.setStep(this.stepField.getText());
		this.param.setDelay(this.delayField.getText());
		this.param.setEndPlus(this.plusField.getText());
		this.param.setTargetEdgeTypeName((String) this.targetEdgeTypesField.getSelectedItem());
		
		return this.param;
	}
	
	private JPanel makeStepField(final String value) {
		final JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(" Animation step size "));
		
		final JLabel l = new JLabel("Points: ");
		this.stepField = new JTextField(value, 5);	
		this.stepField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						if (nb.intValue() >= 0) {	
							AnimationParamDialog.this.changed = AnimationParamDialog.this.changed || (AnimationParamDialog.this.param.step != nb.intValue());
							AnimationParamDialog.this.param.step = nb.intValue();
						}	else {
							AnimationParamDialog.this.stepField.setText(value);
						}					
					} catch (NumberFormatException ex) {
						AnimationParamDialog.this.stepField.setText(value);
					}
				} 				
			}
		});
		
		p.add(l, BorderLayout.CENTER);
		p.add(this.stepField, BorderLayout.EAST);
		
		return p;
	}
	
	private JPanel makeDelayField(final String value) {
		final JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(" Animation delay time "));
		
		final JLabel l = new JLabel(" Milliseconds: ");
		this.delayField = new JTextField(value, 5);	
		this.delayField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						if (nb.intValue() >= 0) {	
							AnimationParamDialog.this.changed = AnimationParamDialog.this.changed || (nb.intValue() != AnimationParamDialog.this.param.delay);
							AnimationParamDialog.this.param.delay = nb.intValue();
						} else {
							AnimationParamDialog.this.delayField.setText(value);
						}
					} catch (NumberFormatException ex) {
						AnimationParamDialog.this.delayField.setText(value);
					}
				} 				
			}
		});
		
		p.add(l, BorderLayout.CENTER);
		p.add(this.delayField, BorderLayout.EAST);
		
		return p;
	}
	
	private JPanel makeEndPlusField(final String value) {
		final JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(" End position offset "));
		
		final JLabel l = new JLabel(" Points: ");
		this.plusField = new JTextField(value, 5);	
		this.plusField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());	
						AnimationParamDialog.this.changed = AnimationParamDialog.this.changed || (nb.intValue() != AnimationParamDialog.this.param.plus);
						AnimationParamDialog.this.param.plus = nb.intValue();						
					} catch (NumberFormatException ex) {
						AnimationParamDialog.this.plusField.setText(value);
					}
				} 				
			}
		});
		
		p.add(l, BorderLayout.CENTER);
		p.add(this.plusField, BorderLayout.EAST);
		
		return p;
	}
	
	private JPanel makeTargetEdgeTypesField(String value) {
		final JPanel p1 = new JPanel(new GridLayout(0, 1));
		p1.setBorder(new TitledBorder(""));
		
		final JLabel l1 = new JLabel("Edge Type");
		this.targetEdgeTypesField.setSelectedItem(value);
		this.targetEdgeTypesField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String tname = (String) e.getItem();

					AnimationParamDialog.this.changed = AnimationParamDialog.this.changed 
							|| !AnimationParamDialog.this.param.targetEdgeTypeName.equals(tname);
					
					AnimationParamDialog.this.param.setTargetEdgeTypeName(tname);				
				}
			}
		});
				
		p1.add(l1);
		p1.add(this.targetEdgeTypesField);
				
		return p1;
	}
}
