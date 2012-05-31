/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdPAC;
import agg.gui.editor.GraphMorphismEditor;
import agg.gui.icons.TextIcon;
import agg.util.Pair;

/*
 * not more used
 */
public class FormulaGUI extends JDialog implements ActionListener,
ListSelectionListener, MouseMotionListener  {
	
	final JList list;
	String t;
	
	final JTextField text;

	final JButton ok, cancel, clear, clearLast;

	final JPanel dialogPanel;

	JFrame frame;

	boolean changed, canceled;
	
	String formula, f;
	final List<Integer> itsVars = new Vector<Integer>(2);
	final List<Object> objs = new Vector<Object>(5,1);
	
	final Vector<Pair<String,String>> edit = new Vector<Pair<String,String>>(5,2);
	int n1,n2;
	boolean forall, p1forall, p2forall, exists, p1exists, p2exists;	
	
	final JButton andB, orB, notB, forallB, existsB, notexistsB, trueB, falseB;	
	final GraphMorphismEditor view;	
	final JDialog viewFrame;
	final JScrollPane viewScroll;
	
	
	public FormulaGUI(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		
		this.viewFrame = new JDialog(this, false);
		this.viewFrame.getContentPane().setLayout(new BorderLayout());
		this.viewScroll = new JScrollPane(this.view);
		this.viewScroll.setPreferredSize(new Dimension(350, 450));
		this.viewFrame.getContentPane().add(this.viewScroll, BorderLayout.CENTER);
		this.viewFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		this.viewFrame.setLocation(this.getX()+this.getWidth()*2, 200);
		this.viewFrame.setPreferredSize(new Dimension(100, 300));	
		
		this.view = new GraphMorphismEditor(null);
		
		this.view.getLeftPanel().setToolTipText("");
		this.view.getRightPanel().setToolTipText("");
		this.view.getLeftPanel().getCanvas().setBackground(Color.LIGHT_GRAY);
		this.view.getRightPanel().getCanvas().setBackground(Color.LIGHT_GRAY);
		
		JPanel p1 = new JPanel(new BorderLayout());
		
		this.list = new JList();
		this.list.getSelectionModel().setSelectionMode(0);
		this.list.addListSelectionListener(this);
		this.list.addMouseMotionListener(this);
		
		JPanel opPanel = new JPanel(new GridBagLayout());
		opPanel.setPreferredSize(new Dimension(100,300));
		
		this.andB = new JButton(new TextIcon("AND ", true));
		this.andB.setActionCommand("AND");
		this.andB.setToolTipText(" AND ");
		this.andB.addActionListener(this);
		
		this.orB = new JButton(new TextIcon("OR", true));
		this.orB.setToolTipText(" OR ");
		this.orB.setActionCommand("OR");
		this.orB.addActionListener(this);
		
		this.notB = new JButton(new TextIcon("NOT ", true));
		this.notB.setToolTipText(" NOT ");
		this.notB.setActionCommand("NOT");
		this.notB.addActionListener(this);
		
		this.existsB = new JButton("EXISTS");
		this.existsB.setToolTipText(" EXISTS ");
		this.existsB.setActionCommand("EXISTS");
		this.existsB.addActionListener(this);
		
		this.notexistsB = new JButton("NOT EXISTS");
		this.notexistsB.setToolTipText(" NOT EXISTS ");
		this.notexistsB.setActionCommand("NOTEXISTS");
		this.notexistsB.addActionListener(this);
		
		this.forallB = new JButton("FOR ALL");
		this.forallB.setToolTipText(" FOR ALL ");
		this.forallB.setActionCommand("FORALL");
		this.forallB.addActionListener(this);
		
		this.trueB = new JButton(new TextIcon(" T ", true));
		this.trueB.setActionCommand("TRUE");
		this.trueB.setToolTipText(" TRUE ");		
		this.trueB.addActionListener(this);
		
		this.falseB = new JButton(new TextIcon(" F ", true));		
		this.falseB.setActionCommand("FALSE");
		this.falseB.setToolTipText(" FALSE ");
		this.falseB.addActionListener(this);
		
		JButton sub1B = new JButton(new TextIcon(" ( ", true));
		sub1B.setActionCommand("(");
		sub1B.addActionListener(this);
		
		JButton sub2B = new JButton(new TextIcon(" ) ", true));
		sub2B.setActionCommand(")");
		sub2B.addActionListener(this);
		
		constrainBuild(opPanel, this.notB, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, this.andB, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, this.orB, 1, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);	
		constrainBuild(opPanel, this.existsB, 0, 2, 2, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, this.forallB, 0, 3, 2, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, this.trueB, 0, 4, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, this.falseB, 1, 4, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, sub1B, 0, 5, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		constrainBuild(opPanel, sub2B, 1, 5, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 0);
		
		JPanel labelP = new JPanel(new GridLayout(3,0));
		labelP.add(new JLabel("     Select  an  application  condition     "));
		labelP.add(new JLabel("  and  an  operator  to  build  a  formula  "));
		labelP.add(new JLabel("                                      "));
		
		p1.add(labelP, BorderLayout.NORTH);
		p1.add(new JScrollPane(this.list), BorderLayout.CENTER);
		p1.add(opPanel, BorderLayout.EAST);

		this.text = new JTextField("true");
		this.text.setEditable(false);
		this.text.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		
		this.clear = new JButton("Clear");
		this.clear.addActionListener(this);
		this.clearLast = new JButton("Del");
		this.clearLast.addActionListener(this);
		this.ok = new JButton("Apply");
		this.ok.addActionListener(this);
		this.cancel = new JButton("Cancel");
		this.cancel.addActionListener(this);
		JPanel buttons = new JPanel(new GridBagLayout());
		constrainBuild(buttons, this.clear, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(buttons, this.clearLast, 1, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(buttons, this.ok, 2, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(buttons, this.cancel, 3, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		
		
		JPanel p2 = new JPanel(new GridBagLayout());
		JLabel exmpl = new JLabel("Example: ( a & ( b | ! c ) )    ");
		constrainBuild(p2, exmpl, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p2, this.text, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
				
		// make common dialog panel
		this.dialogPanel = new JPanel(new GridBagLayout()); 
		this.dialogPanel.setPreferredSize(new Dimension(350, 350));
		
		constrainBuild(this.dialogPanel, p1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 10, 10, 10, 10);
		constrainBuild(this.dialogPanel, p2, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 10, 10, 10, 10);
		constrainBuild(this.dialogPanel, buttons, 0, 2, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 10, 10, 10, 10);
		
		getContentPane().setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(this.dialogPanel);
		scroll.setPreferredSize(new Dimension(400, 400));
		getContentPane().add(scroll, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.formula = "";
		this.f = "";
		this.changed = true;
	}

	public void disableFORALL(boolean b) {
		this.forallB.setEnabled(!b);
	}
	
	public void disableEXIST(boolean b) {
		this.existsB.setEnabled(!b);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Clear")) {
			clear();
		} else if (e.getActionCommand().equals("Del")) {
			if (this.edit.size() > 0) {				
				this.t = null;
				String last = delLastEdit();
				
				if (last.equals(")")) {
					this.n2--;					
				} else if (last.equals("(")) {
					this.n1--;	
				} 
				else if (last.equals(",")) {
					for (int j=0; j<3; j++) {
						last = delLastEdit();
					}	
					this.forall = false;
					this.p1forall = false;
					this.p2forall = false;
					this.exists = false;
					this.p1exists = false;
					this.p2exists = false;
				} 
			}
		} else if (e.getActionCommand().equals("Apply")) {
			if (this.n1 == this.n2) {
				setVisible(false);
				String s = this.text.getText();
				if (!this.formula.equals(s)) {
					this.formula = s;
					if ("".equals(this.formula)) {
						this.f = "";
					}
					this.changed = true;
				} else
					this.changed = true; //false;
			}
		} else if (e.getActionCommand().equals("Cancel")) {
			setVisible(false);
			this.canceled = true;
		} else {
			if (this.text.getText().endsWith(")")) {
				if (this.p2forall) {
					this.forall = false; this.p1forall = false; this.p2forall = false;
				} else if (this.p2exists) {
					this.exists = false; this.p1exists = false; this.p2exists = false;
				}
			}
			
			addToFormula(e);
		}
	}

	public String getFormula() {
		System.out.println(this.f);
		System.out.println(this.formula);
		
		if ("".equals(this.f))
			return "true";
		else
			return this.f;
	}

	public List<Integer> getIndxOfVar() {
		for (int i=0; i<this.edit.size(); i++) {
			try {
				int idx = Integer.valueOf(this.edit.get(i).second).intValue() - 1;
				if (idx > -1)
					this.itsVars.add(Integer.valueOf(idx));
			} catch (Exception ex) {}
		}
//		System.out.println(itsVars);
		return this.itsVars;
	}
	
	public boolean isChanged() {
		return this.changed;
	}
	
	public boolean isCanceled() {
		return this.canceled;
	}
	
	public void setVars(List<String> vars, String formulaStr) {
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < vars.size(); i++) {
			s.add(vars.get(i));
			this.objs.add(vars.get(i));
		}
		
		if (s.isEmpty()) {
			clear();
		} else {
			this.list.setListData(s);
			this.formula = formulaStr;
			fillFromString(formulaStr);
		}
	}
	
	public void setVarsAsObj(List<EdNestedApplCond> v, String formulaStr) {
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < v.size(); i++) {
			EdNestedApplCond obj = v.get(i);
//			if (obj instanceof EdPAC) 
//				s.add(((EdPAC)obj).getMorphism().getName());
			
			s.add(obj.getMorphism().getName());
			
			this.objs.add(obj);
		}
		
		if (s.isEmpty()) {
			clear();
		} else {
			this.list.setListData(s);
			this.formula = formulaStr;
			fillFromString(formulaStr);		
		}
	}

	
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		int indx = this.list.getSelectionModel().getMinSelectionIndex();
		if (indx >= 0 && this.t == null) {
//			this.showInView(indx, this.objs.get(indx));
			
			this.t = (String) this.list.getSelectedValue();
			addEdit(this.t, indx+1);
			
			if (this.forall) {
				if (!this.p2forall) {
					addEdit(")", -1);
					this.n2++;
					this.p2forall = true;
				}
			} else if (this.exists) {
				if (!this.p2exists) {	
					addEdit(")", -1);
					this.n2++;
					this.p2exists = true;
				} 
			}
		}
		this.list.getSelectionModel().clearSelection();
	}
	
	private void addEdit(String s, int i) {
		if (i == -1) {
			// add pair (operator, -1)
			this.edit.add(new Pair<String,String>(s, String.valueOf(i))); 
			this.f = this.f.concat(s);
		}
		else {
			// add pair (name, index)
			this.edit.add(new Pair<String,String>(s, String.valueOf(i))); 
			// add obj index
			this.f = this.f.concat(String.valueOf(i));
		}
		// add space
		this.edit.add(new Pair<String,String>(" ", "-1"));
		// add space to text
		this.text.setText(this.text.getText().concat(s).concat(" "));
	}
	
	private String delLastEdit() {
		int n = this.edit.size()-1;
		String tmp = this.text.getText();
		String last = this.edit.get(n).first; // " "
		n--;
		last = this.edit.get(n).first; // element
		int i = tmp.lastIndexOf(last) - 1 ;
		if (i >= 0)
			this.text.setText(tmp.substring(0, i));	
		else
			this.text.setText("");
		
		this.edit.remove(this.edit.size()-1); // " "
		this.edit.remove(this.edit.size()-1); // element
		
		return last;
	}
	
	void clear() {
		this.text.setText("");
		this.f = "";
		this.formula = "";
		this.t = null;
		this.itsVars.clear();
		this.edit.clear();	
	}
	
	void addToFormula(final ActionEvent e) {		
		if (e.getSource() instanceof JButton) {
			this.t = null;			
			String last = this.edit.isEmpty()? "": this.edit.get(this.edit.size()-1).first;
			if (e.getActionCommand().equals("AND")) {
				if (!last.equals("") && !last.equals("&") && !last.equals(",")
						&& !last.equals("!") && !last.equals("|")) {
					this.t = "&";
					addEdit(this.t, -1);
				}
			} else if (e.getActionCommand().equals("OR")) {
				if (!last.equals("") && !last.equals("&") && !last.equals(",") 
						&& !last.equals("!") && !last.equals("|")) {
					this.t = "|";
					addEdit(this.t, -1);
				}
			} else if (e.getActionCommand().equals("NOT")) {
				if (last.equals("") || last.equals(" ")
						|| last.equals("&") || last.equals("|")
						|| last.equals("(") || last.equals(",")) {
					this.t = "!";
					addEdit(this.t, -1);
				}
			} else if (e.getActionCommand().equals("FORALL")) {
				if (last.equals("") || last.equals(" ")
						|| last.equals("&") || last.equals("|")
						|| last.equals("!")) {
					this.t = "A";	
					addEdit(this.t, -1);
					this.t = "(";	
					addEdit(this.t, -1);
					this.n1++;
					this.t = "$";	
					addEdit(this.t, -1);
					this.t = ",";	
					addEdit(this.t, -1);
					
					this.forall = true;
					this.p1forall = true;
					this.p2forall = false;
					this.exists = false;
				}
			} 
			else if (e.getActionCommand().equals("EXISTS")) {
				if (last.equals("") || last.equals(" ")
						|| last.equals("&") || last.equals("|")
						|| last.equals("!")) {
					this.t = "E";	
					addEdit(this.t, -1);
					this.t = "(";	
					addEdit(this.t, -1);
					this.n1++;
					this.t = "$";	
					addEdit(this.t, -1);
					this.t = ",";	
					addEdit(this.t, -1);
					
					this.exists = true;
					this.p1exists = false;
					this.p2exists = false;
					this.forall = false;
				}
			} 
			else if (e.getActionCommand().equals("TRUE")) {
				if (last.equals("") || last.equals(" ") || last.equals(",")) {
					this.t = "true";	
					addEdit(this.t, -1);
				}
			}
			else if (e.getActionCommand().equals("FALSE")) {
				if (last.equals("") || last.equals(" ") || last.equals(",")) {
					this.t = "false";
					addEdit(this.t, -1);
				}
			}
			else if (e.getActionCommand().equals("(")) {
				if (last.equals("") || last.equals(" ")
						|| last.equals("!") || last.equals(",")
						|| last.equals("&") || last.equals("|")) {
					this.t = "(";
					addEdit(this.t, -1);
					this.n1++;
					
				}
			} else if (e.getActionCommand().equals(")")) {
				if (this.n2 < this.n1 && !last.equals("")
						&& !last.endsWith("(") 
						&& !last.equals("!") && !last.equals("|")
						&& !last.equals("&")) {
					this.t = ")";	
					addEdit(this.t, -1);
					this.n2++;
				}
			}
			this.t = null;
		}
	}
	
	private void fillFromString(String str) {
		this.text.setText("");
		String s = str.replaceAll(" ", "");
		StringCharacterIterator i = new StringCharacterIterator(s);
		char c = i.current();
		while (c != CharacterIterator.DONE) {
			if (c == '&' || c == '|' 
					|| c == '!' || c == '$'
					|| c == 'A' || c == 'E' 
					|| c == ' ' || c == ','
					|| c == '(' || c == ')') {
				addEdit(String.valueOf(c), -1);
				i.next();
			} else if (c >= '0' && c <= '9') {
				String cs = "";
				int v = 0;
				while (c >= '0' && c <= '9') {
					cs = cs.concat(String.valueOf(c));
					v = v * 10 + (c - '0');
					c = i.next();
				}
				v--;
				if (v < 0 /*|| v >= list.getModel().getSize()*/)
					return;
				
				int num = Integer.valueOf(cs).intValue();
				if (this.objs.size() > 0) {
					Object obj = this.objs.get(num-1);
					if (obj instanceof String)
						addEdit((String) obj, num);
					else if (obj instanceof EdPAC)
						addEdit(((EdPAC)obj).getMorphism().getName(), num);
				}
			} else if (c == 'f' || c == 't') {
				String cs = String.valueOf(c);
				char c1 = c;
				while (i.current() >= 'a' && i.current() <= 'z') {
					c1 = i.next();
					if (c1 != CharacterIterator.DONE) {
						cs = cs.concat(String.valueOf(c1));
					}
				}
				addEdit(String.valueOf(cs), -1);
			} else if (c == 'A') {
				String cs = String.valueOf(c);				
				cs = cs.concat(String.valueOf(i.next()));
				addEdit(String.valueOf(cs), -1);
			} else if (c == 'E') {
				String cs = String.valueOf(c);				
				cs = cs.concat(String.valueOf(i.next()));
				addEdit(String.valueOf(cs), -1);
			}
			
			c = i.current();			
		}
	}
	
	private void constrainBuild(Container container, Component component,
			int grid_x, int grid_y, int grid_width, int grid_height, int fill,
			int anchor, double weight_x, double weight_y, int top, int left,
			int bottom, int right) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = grid_x;
		c.gridy = grid_y;
		c.gridwidth = grid_width;
		c.gridheight = grid_height;
		c.fill = fill;
		c.anchor = anchor;
		c.weightx = weight_x;
		c.weighty = weight_y;
		c.insets = new Insets(top, left, bottom, right);
		((GridBagLayout) container.getLayout()).setConstraints(component, c);
		container.add(component);
	}
	
	void showInView(int indx, EdNestedApplCond left) {		
		this.view.setLeftGraph(left);
		if (left.getParent() == null) {
			this.view.setRightGraph(left.getRule().getLeft());
			this.view.updateGraphs();
		} else {
			for (int i=0; i<this.objs.size(); i++) {
				Object obj = this.objs.get(i);
				if (obj instanceof EdNestedApplCond) {
					if (obj == left.getParent()) {
						this.view.setRightGraph(left.getParent());
						this.view.updateGraphs();
					}
				}
			}			
		}
		
		this.viewFrame.setVisible(true);
		this.viewFrame.toFront();		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		if (e.getSource() == this.list) {
//			int indx = ((JList)e.getSource())e.get
//			if (indx >= 0)
//				this.showInView(indx, this.nestedACs.get(indx));
		}
	}
	

}
