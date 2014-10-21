package agg.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import agg.attribute.impl.VerboseControl;

/**
 * This class provides a window which switches some debug output on and off. At
 * this time only attribute information are provided.
 * 
 * @author $Author: olga $
 * @version $Id: DebugFrame.java,v 1.3 2007/09/10 13:05:24 olga Exp $
 */
@SuppressWarnings("serial")
public class DebugFrame extends JFrame implements ActionListener {

	// In AGGAppl gibt es das Flag "DEBUGFRAME", mit dem der ganze Frame mit
	// Meunueintrag
	// aus der AGG-GUI genommen werden kann.
	// Siehe agg.attribute.impl.VerboseControl

	private static String FileIO = "File I/O";

	/** Each creation of any subclass of AttrObject */
	private static String Creation = "Creation";

	/** Context of AttrInstance (impl by ValueTuple) */
	private static String ContextOfInstances = "Context of Instances";

	/** Mapping in an AttrContext (impl by ContextView / ContextCore ) */
	private static String Mapping = "Mapping";

	/** Handling of AttrContext (impl by ContextView / ContextCore ) */
	private static String Context = "Context";

	/** Context (rule) conditions AttrCond (impl by CondTuple) */
	private static String Cond = "Condition";

	/** Context (rule) variables AttrVar (impl by VarTuple) */
	private static String Var = "Variable";

	/** Setting of variables. */
	private static String SetValue = "set Value";

	/** Removing of variables */
	private static String RemoveValue = "remove Value";

	/** Events */
	private static String Event = "Events";

	/** Syntax trees of Java expressions. */
	private static String ParseTree = "Parse Tree";

	/** Traces method calls */
	private static String Trace = "Trace";

	public DebugFrame() {
		setTitle("Debug Preferences");
		setSize(200, 300);
		setLocation(200, 200);
		getContentPane().setLayout(new BorderLayout());

		JPanel p = new JPanel(new GridLayout(0, 1), true);
		p.setSize(200, 300);
		getContentPane().add(p);
		p.add(new JLabel("Debugoptionen"));
		JCheckBox jcb = new JCheckBox(FileIO, VerboseControl.logFileIO);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Creation, VerboseControl.logCreation);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(ContextOfInstances,
				VerboseControl.logContextOfInstances);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Mapping, VerboseControl.logMapping);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Context, VerboseControl.logContext);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Cond, VerboseControl.logCond);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Var, VerboseControl.logVar);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(SetValue, VerboseControl.logSetValue);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(RemoveValue, VerboseControl.logRemoveValue);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Event, VerboseControl.logEvent);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(ParseTree, VerboseControl.logParseTree);
		p.add(jcb);
		jcb.addActionListener(this);
		jcb = new JCheckBox(Trace, null, VerboseControl.logTrace);
		p.add(jcb);
		jcb.addActionListener(this);
	}

	public void actionPerformed(ActionEvent evt) {
		String lnfName = evt.getActionCommand();
		if (lnfName == FileIO)
			VerboseControl.logFileIO = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == Creation)
			VerboseControl.logCreation = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == ContextOfInstances)
			VerboseControl.logContextOfInstances = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == Mapping)
			VerboseControl.logMapping = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == Context)
			VerboseControl.logContext = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == Cond)
			VerboseControl.logCond = ((JCheckBox) evt.getSource()).isSelected();
		if (lnfName == Var)
			VerboseControl.logVar = ((JCheckBox) evt.getSource()).isSelected();
		if (lnfName == SetValue)
			VerboseControl.logSetValue = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == RemoveValue)
			VerboseControl.logRemoveValue = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == Event)
			VerboseControl.logEvent = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == ParseTree)
			VerboseControl.logParseTree = ((JCheckBox) evt.getSource())
					.isSelected();
		if (lnfName == Trace)
			VerboseControl.logTrace = ((JCheckBox) evt.getSource())
					.isSelected();
	}

}
// ======================================================================
// $Log: DebugFrame.java,v $
// Revision 1.3  2007/09/10 13:05:24  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.2 2006/12/13 13:32:55 enrico
// reimplemented code
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:03 olga
// Version with Eclipse
//
// Revision 1.2 2003/03/05 18:24:16 komm
// sorted/optimized import statements
//
// Revision 1.1.1.1 2002/07/11 12:17:10 olga
// Imported sources
//
// Revision 1.1 1999/12/06 08:11:58 shultzke
// A little frame provides some switches to turn debugging stuff on and off.
//
