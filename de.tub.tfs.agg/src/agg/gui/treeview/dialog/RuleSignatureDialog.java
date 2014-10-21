package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import agg.gui.AGGAppl;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.gui.impl.ContextEditor;
import agg.attribute.gui.impl.TopEditor;
import agg.attribute.gui.impl.TupleTableModel;
import agg.attribute.gui.impl.TupleTableModelConstants;
import agg.attribute.gui.impl.VariableTupleEditor;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdRule;


@SuppressWarnings("serial")
public class RuleSignatureDialog extends JDialog implements TableModelListener {

	final static String ttt = "The signature includes the rule name and "
								+"\nthe number, types and order of its parameters. "
								+"\nThe last parameter may be an output parameter.";
	
	protected JFrame applFrame;
	
	protected EdGraGra gragra;
	
	protected EdRule rule;
	
	protected ContextEditor contextEditor;
	protected VariableTupleEditor variableEditor;
	protected VarMember var;
	protected int indxOut;
	protected boolean inFailed, outFailed;
	protected String signatureTxt;
	protected String s0, s1, s2, s3, s4, s5;
	private List<Integer> indexesIn;
	private List<Integer> store;// 0:indxOut; 1...nn: indexesIn;
	private JTextField signTxt;
	private static boolean showInfoMsg; // = true;
	
	
	public RuleSignatureDialog(final JFrame frame, final Point location, EdRule r) {
		super();
	
		this.applFrame = frame;
		this.rule = r;
		this.gragra = r.getGraGra();
		
		setModal(true); //false);		
		setTitle("  Signature of the Rule:  "+this.rule.getName());
		
		initData();
		
		((AGGAppl) this.applFrame).getGraGraEditor().getAttrEditor().setContext(this.rule.getBasisRule().getAttrContext());
		this.contextEditor = ((TopEditor)((AGGAppl) this.applFrame).getGraGraEditor()
									.getAttrEditor()).getContextEditor();
		this.variableEditor = this.contextEditor.getVariableEditor();
		this.variableEditor.getTableModel().addTableModelListener(this);	
//		((TopEditor) ((AGGAppl) this.applFrame).getGraGraEditor().getAttrEditor())
//				.getContextEditor().getVariableEditor().getTableModel().addTableModelListener(this);
		
		final JPanel content = initContentPane();
		
		JScrollPane scroll = new JScrollPane(content);
		scroll.setPreferredSize(new Dimension(500, 550));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll, BorderLayout.CENTER);
		validate();

		setLocation(location);
		pack();
		
		this.storeIndexes();
	}
	
	private void initData() {
		indxOut = -1;
		indexesIn = new Vector<Integer>(5);
		if (this.rule.getBasisRule().getSignaturOrder() != null) {
			VarTuple vars = (VarTuple)this.rule.getBasisRule().getAttrContext().getVariables();
			indexesIn.addAll(vars.getSignaturOrder());
			fillSignatureItems(vars);
		}
		fillSignatureItems();
	}
	
	private void fillSignatureItems() {
		s0 = ""; //"boolean ";
		s1 = this.rule.getName();
		s2 = "(";
		s3 = "";
		s4 = "";
		s5 = ")";
	}
	
	private void fillSignatureItems(VarTuple vars) {
		fillSignatureItems();
		
		for (int i = 0; i < indexesIn.size(); i++) {
			VarMember m = (VarMember) vars.getMemberAt(indexesIn.get(i).intValue());
			String nt = m.getName().concat(":").concat(m.getDeclaration().getTypeName());
			s3 = s3.concat(nt);
			if (i < (indexesIn.size()-1))
				s3 = s3.concat(", ");
		}
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember m = (VarMember) vars.getMemberAt(i);
			if (m.isOutputParameter()) {
				indxOut = i;
				if (!s3.isEmpty())
					s4 = s4.concat(", ");				
				s4 = s4.concat("out ");
				String nt = m.getName().concat(":").concat(m.getDeclaration().getTypeName());
				s4 = s4.concat(nt);
				break;
			}
		}
	}
	
	private JPanel initContentPane() {
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = makeRuleContextPanel();
		
		JPanel p2 = new JPanel(new BorderLayout());
		JPanel pAssign = new JPanel(new GridLayout());
		JPanel p3 = makeAssignInParPanel();
		JPanel p4 = makeAssignOutParPanel();
		pAssign.add(p3);
		pAssign.add(p4);
		
		JPanel pSign = makeSignaturePanel();
		
		p2.add(pAssign, BorderLayout.NORTH);
		p2.add(pSign, BorderLayout.CENTER);
		
		JPanel pClose = new JPanel(new GridLayout());
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check()) {
					variableEditor.getTableModel().setColumnEditable(TupleTableModelConstants.EXPR, true);
					setVisible(false);
					System.out.println(signatureTxt);
					contextEditor.resetVariableEditorComponent();
				}
				else if (inFailed) {
					warning("Incorrect Signature", "The rule signature contains an incorrect input parameter.");
				}
				else if (outFailed) {
					warning("Incorrect Signature", "The rule signature contains an incorrect output parameter.");
				}
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				variableEditor.getTableModel().setColumnEditable(TupleTableModelConstants.EXPR, true);
				restoreSignature();
				setVisible(false);
			}
		});
		pClose.add(new JLabel("     "));
		pClose.add(close);
		pClose.add(new JLabel("     "));
		pClose.add(new JLabel("     "));
		pClose.add(cancel);
		pClose.add(new JLabel("     "));
		
		p.add(p1, BorderLayout.NORTH);
		
		p.add(p2, BorderLayout.CENTER);
		p.add(pClose, BorderLayout.SOUTH);
				
		return p;
	}
	
	private void setSignatureText() {
		signatureTxt = s0+s1+s2+s3+s4+s5;
		signTxt.setText("  "+signatureTxt);
	}
	
	protected void clearSignatureText() {
		s3 = ""; s4 = "";
		signatureTxt = s0+s1+s2+s3+s4+s5;
		signTxt.setText("  "+signatureTxt);
	}
	
	protected void clearIndexes() {
		this.indexesIn.clear();
		this.indxOut = -1;
		this.inFailed = false;
		this.outFailed = false;
	}
	
	protected void unsetInOutParams() {		
		VarTuple vars = (VarTuple) this.variableEditor.getTuple();
		for (int i = 0; i < vars.getNumberOfEntries(); i++) {
			VarMember m = (VarMember) vars.getMemberAt(i);
			m.setInputParameter(false);
			m.setOutputParameter(false);
		}
	}
	
	private void storeIndexes() {
		this.store = new Vector<Integer>(this.indexesIn);
		this.store.add(0, Integer.valueOf(this.indxOut));
	}
	
	protected void clearSignature() {
		unsetInOutParams();
		clearSignatureText();
		clearIndexes();	
		this.validate();
	}

	protected void restoreSignature() {
		this.indxOut = this.store.get(0).intValue();
		this.indexesIn.clear();
		for (int i=1; i<this.store.size(); i++) {
			this.indexesIn.add(this.store.get(i));
		}
		VarTuple vars = (VarTuple) this.variableEditor.getTuple();
		if (this.indxOut >= 0)
			((VarMember) vars.getMemberAt(this.indxOut)).setOutputParameter(true);
		for (int i=0; i<this.indexesIn.size(); i++) {
			((VarMember) vars.getMemberAt(this.indexesIn.get(i).intValue())).setInputParameter(true);
		}
		fillSignatureItems(vars);
		setSignatureText();
	}
	
	private JPanel makeLabelPanel() {
		JPanel p = new JPanel(new GridLayout(3,0));
		p.add(new JLabel(" Please define one or more input parameters "));
		p.add(new JLabel(" and one output parameter."));
		p.add(new JLabel("     "));
		return p;
	}

	JPanel pVarEditor;
	private JPanel makeRuleContextPanel() {
		this.variableEditor.getTableModel().setColumnEditable(TupleTableModelConstants.EXPR, false);
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel("     "), BorderLayout.NORTH);
		pVarEditor = new JPanel(new BorderLayout());
		pVarEditor.setBorder(new TitledBorder("  Rule  context  "));
		pVarEditor.add(makeLabelPanel(), BorderLayout.NORTH);	
		pVarEditor.add(this.variableEditor.getComponent(), BorderLayout.CENTER);
		
		p.add(pVarEditor, BorderLayout.CENTER);
		p.add(new JLabel("     "), BorderLayout.SOUTH);
		return p;
	}
	
	private JPanel makeAssignInParPanel() {
		JPanel p = new JPanel(new GridLayout(3,0));
		p.setBorder(new TitledBorder("  Input  "));
		p.add(new JLabel("  Please select an input parameter  "));
		p.add(new JLabel("  and assign it to the rule signature  "));
		JPanel p1 = new JPanel(new GridLayout(0,2));
		
		JButton assign = new JButton("Assign Input");
		assign.setSize(50, 20);
		assign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var = (VarMember) variableEditor.getSelectedMember();
				if (var != null && ((VarMember)var).isInputParameter()) {
					int i = variableEditor.getTuple().getIndexForMember(var);
					if (!s3.isEmpty() && s4.isEmpty())
						s3 = s3+", ";
					String nt = variableEditor.getSelectedMember().getDeclaration().getName()
								+":"+variableEditor.getSelectedMember().getDeclaration().getTypeName();
					if (indexesIn.contains(Integer.valueOf(i))) {
						s3 = s3.replaceFirst(nt, "");
						if (s3.equals(", ")) 
							s3 = "";
						else if (s3.startsWith(", "))
							s3 = s3.replaceFirst(", ", "");
						else 
							s3 = s3.replaceFirst(", , ", ", ");
						indexesIn.remove(Integer.valueOf(i));
					}
					s3 = s3.concat(nt);	
					if (!s4.isEmpty())
						s3 = s3+", ";
					indexesIn.add(Integer.valueOf(i));
					setSignatureText();
				}
			}
		});
		
		p1.add(assign); 
		p1.add(new JLabel("      "));
		
		p.add(p1);
		return p;
	}
	
	private JPanel makeAssignOutParPanel() {
		JPanel p = new JPanel(new GridLayout(3,0));
		p.setBorder(new TitledBorder("  Output  "));
		p.add(new JLabel("  Please select one output parameter  "));
		p.add(new JLabel("  and assign it to the rule signature  "));
		JPanel p1 = new JPanel(new GridLayout(0,2));
		
		JButton assign = new JButton("Assign Output");
		assign.setSize(50, 20);
		assign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var = (VarMember) variableEditor.getSelectedMember();
				if (var != null && ((VarMember)var).isOutputParameter()) {
					indxOut = variableEditor.getTuple().getIndexForMember(var);
					if (!s3.isEmpty() && s4.isEmpty())
						s3 = s3+", ";
					String nt = "out "+variableEditor.getSelectedMember().getDeclaration().getName()
								+":"+variableEditor.getSelectedMember().getDeclaration().getTypeName();
					s4 = nt;						
					setSignatureText();
				}
			}
		});
		
		p1.add(assign);
		p1.add(new JLabel("      "));
		p.add(p1);
		return p;
	}
	
	private JPanel makeSignaturePanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel("     "), BorderLayout.NORTH);	
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(new TitledBorder("  Rule signature  "));
		signTxt = new JTextField();
		signTxt.setEditable(false);
		signTxt.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
		setSignatureText();

		p1.add(signTxt, BorderLayout.CENTER);
		
		JPanel pClear = new JPanel(new GridLayout(0,5));
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearSignature();
			}
		});

		pClear.add(new JLabel("     "));
		pClear.add(new JLabel("     "));
		pClear.add(clear);
		pClear.add(new JLabel("     "));
		pClear.add(new JLabel("     "));

		p1.add(pClear, BorderLayout.SOUTH);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(new JLabel("     "), BorderLayout.SOUTH);

		return p;
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int c = ((TupleTableModel) e.getSource()).getChangedColumn();
		String cname = ((TupleTableModel) e.getSource()).getColumnName(c);
		if (cname.equals("In") || cname.equals("Out") 
				|| cname.equals("Type") || cname.equals("Name")) {
			int key = ((TupleTableModel) e.getSource()).getItemKeyAt(c);
			switch (key) {
			case TupleTableModel.IS_INPUT_PARAMETER:
				if (var != null && !var.isInputParameter()) {
					int i = variableEditor.getTuple().getIndexForMember(var);
					if (indexesIn.contains(Integer.valueOf(i))) {
						clearSignatureText();
					}
				}
			case TupleTableModel.IS_OUTPUT_PARAMETER:
				if (var != null && !var.isOutputParameter()) {
					if (!checkOut()) {
						warning("Incorrect Signature", "The rule signature contains incorrect output parameter.\n\n" +
								"The signature will be unset.");
						clearSignatureText();
					}
				}
			case TupleTableModel.TYPE:
			case TupleTableModel.NAME:
				if (var != null) {
					int i = variableEditor.getTuple().getIndexForMember(var);
					if (indexesIn.contains(Integer.valueOf(i))) {
						warning("Incorrect Signature", "Changing of the type or name of an input|output parameter \nleads to an incorrect signature.\n\n" +
								"The current rule signature will be unset.");
						clearSignatureText();
					}
					if (!checkOut()) {
						warning("Incorrect Signature", "Changing of the type or name of an input|output parameter \nleads to an incorrect signature.\n\n" +
								"The current rule signature will be unset.");
						clearSignatureText();
					}
				}
			default:;
			}
			
		}
	}
	
	protected boolean check() {
		inFailed = !checkIn();
		outFailed = !checkOut();
		if (!inFailed && !outFailed) {
			this.rule.getBasisRule().disposeSignatur();
			if (!this.indexesIn.isEmpty() || this.indxOut >= 0) {
				this.rule.getBasisRule().initSignatur();
				for (int i=0; i<this.indexesIn.size(); i++) {
					this.rule.getBasisRule().addInToSignatur(this.indexesIn.get(i));
				}
				this.rule.getBasisRule().addOutToSignatur(this.indxOut);
				System.out.println(this.rule.getBasisRule().getSignatur());
			}
			return true;
		}
		return false;
	}
	
	protected boolean checkIn() {
		inFailed = false;
		for (int l = 0; l<indexesIn.size(); l++) {
			int i = indexesIn.get(l).intValue();
			VarMember m = ((VarTuple)variableEditor.getTuple()).getVarMemberAt(i);
			if (m == null || !m.isInputParameter()) {
				inFailed = true;
				return false;
			}
		}
		return true;
	}
	
	protected boolean checkOut() {
		outFailed = false;
		if (!s4.isEmpty()) {
			String[] nt = s4.split(":");
			String n = nt[0].replaceFirst("out ", "");
			VarMember m = ((VarTuple)variableEditor.getTuple()).getVarMemberAt(n);
			if (m == null || !m.isOutputParameter() 
					|| !m.getDeclaration().getTypeName().equals(nt[1])) {
				outFailed = true;
				return false;
			}
		}
		return true;
	}
	
	protected void warning(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public final static String getToolTipText() {
		return ttt;
	}
	
	public final static void infoMsg() {
		if (showInfoMsg) {
			Object[] options = { "Ok", "Don't show this info anymore" };
			String msg = "The rule signature includes the rule name and "
					+"\nthe number, types and order of its parameters. "
					+"\nThe last parameter may be an output parameter.\n";
			int answer = JOptionPane.showOptionDialog(null, msg, "Rule Signature", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					options, options[0]);
			if (answer == 1)
				showInfoMsg = false;
		}
	}
}
