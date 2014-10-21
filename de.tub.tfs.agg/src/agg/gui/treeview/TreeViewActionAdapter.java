package agg.gui.treeview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

public class TreeViewActionAdapter implements ActionListener {

	private final GraGraTreeView treeView;
	
	public TreeViewActionAdapter(GraGraTreeView treeview) {
		super();
		this.treeView = treeview;
	}

	public GraGraTreeView getTreeView() {
		return this.treeView;
	}
	
	
	@Override
	/* Implements the actionPerformed method of the ActionListener */
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		if (command.equals("undirectedArcs")) {
			this.treeView.setUndirectedArcsOfGraphs(e);
		} else if (command.equals("nonparallelArcs")) {
			this.treeView.setNoParallelArcsOfGraphs(e);
		} else if (command.equals("checkEmptyAttrs")) {
			this.treeView.setCheckEmptyAttrs(e);
		}		
		else if (command.equals("layered")) {
			this.treeView.setGraTraOption_layered(e);		
		} else if (command.equals("priority")) {
			this.treeView.setGraTraOption_priority(e);			
		} else if (command.equals("ruleSequence")) {
			this.treeView.setGraTraOption_ruleSequence(e);			
		} else if (command.equals("nondeterministically")) {
			this.treeView.setGraTraOption_nondeterministically();			
		} 
		else if (command.equals("disableRuleScheme")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disableRuleScheme");
				else
					this.treeView.executeCommand("enableRuleScheme");
			}
		}
		else if (command.equals("disableRule")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disableRule");
				else
					this.treeView.executeCommand("enableRule");
			}
		} else if (command.equals("disableNAC")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disableNAC");
				else
					this.treeView.executeCommand("enableNAC");
			}
		} else if (command.equals("disablePAC")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disablePAC");
				else
					this.treeView.executeCommand("enablePAC");
			}
		} else if (command.equals("disableNestedAC")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disableNestedAC");
				else
					this.treeView.executeCommand("enableNestedAC");
			}
		} else if (command.equals("disableAttrCondition")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disableAttrCondition");
				else
					this.treeView.executeCommand("enableAttrCondition");
			}
		} else if (command.equals("disableConstraint")) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				if (((JMenuItem) e.getSource()).isSelected())
					this.treeView.executeCommand("disableConstraint");
				else
					this.treeView.executeCommand("enableConstraint");
			}
		} else
			this.treeView.executeCommand(command);
	}

}
