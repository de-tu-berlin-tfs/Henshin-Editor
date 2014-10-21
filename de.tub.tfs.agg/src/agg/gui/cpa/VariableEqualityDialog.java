/**
 * 
 */
package agg.gui.cpa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import agg.editor.impl.EdGraph;

/**
 * @author olga
 *
 */
@SuppressWarnings("serial")
public class VariableEqualityDialog extends JDialog {

	final protected EdGraph graph;
	
	public VariableEqualityDialog(final EdGraph graph, final Point location) {
		super(new JFrame(), "Variable Equalities", false);
		
		this.graph = graph;
		String text = graph.getBasisGraph().getHelpInfoAboutVariableEquality();
		
//		text = text.substring(1,text.length()-1).replaceAll(",", "\n").replaceAll(" ", "");
		text = text.substring(1,text.length()-1).replaceAll(" ", "");
		String [] array = text.split(",");
				
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				setVisible(false);
			}
		});
		
		final JPanel content = new JPanel(new BorderLayout());
		content.add(new JLabel("                    "), BorderLayout.NORTH);
		
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("  "+graph.getName()+"  "), BorderLayout.NORTH);
		
		final JPanel p = new JPanel(new GridLayout(array.length+3, 1));	
		p.add(new JLabel("        "));
		for (int i=0; i<array.length; i++) {
			String s = array[i];
			final JLabel l = new JLabel("    "+s+"    ");
			l.setForeground(Color.RED);
			p.add(l);
		}
		p.add(new JLabel("        "));		
		panel.add(p, BorderLayout.CENTER);
		
		final JButton close = new JButton("OK");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});		
		final JPanel pb = new JPanel();
		pb.add(new JLabel("  "));
		pb.add(close);
		pb.add(new JLabel("  "));		
		panel.add(pb, BorderLayout.SOUTH);
		
		content.add(panel, BorderLayout.CENTER);
		content.add(new JLabel("                    "), BorderLayout.SOUTH);
		
		setContentPane(content);
		setLocation(location.x, location.y);
		pack();
		
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	public EdGraph getGraph() {
		return this.graph;
	}
	
	public static Hashtable<String,String> getVarNameEquality(String varsEquality) {
		String [] array = varsEquality.split(",");	
		Hashtable<String,String> map = new Hashtable<String,String>(array.length);	
		for (int i=0; i<array.length; i++) {
			String s = array[i];
			String [] vars = s.split("=");
			if (vars.length == 2) {
				map.put(vars[0], vars[1]);
			}
		}
		return map;
	}
}
