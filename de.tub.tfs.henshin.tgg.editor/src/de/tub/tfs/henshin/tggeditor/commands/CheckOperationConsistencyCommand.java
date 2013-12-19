package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class CheckOperationConsistencyCommand extends CompoundCommand {

	protected String consistencyType=null;
	protected String consistencyTypeLowerCase=null;	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	/**
	 * List of the successful RuleApplications.
	 */
	protected ArrayList<RuleApplicationImpl> ruleApplicationList;


	
	
	/**the constructor
	 * @param graph {@link CheckOperationConsistencyCommand#graph}
	 * @param opRuleList {@link CheckOperationConsistencyCommand#opRuleList}
	 */
	public CheckOperationConsistencyCommand(ExecuteOpRulesCommand opRuleCmd) {
		super();
		this.graph = opRuleCmd.graph;
		this.ruleApplicationList = opRuleCmd.getRuleApplicationList();
		consistencyType=opRuleCmd.consistencyType;
		consistencyTypeLowerCase= opRuleCmd.consistencyTypeLowerCase;		
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (graph != null);
	}
	
	/** Executes all the rules on the graph as long as it's possible. The choosing of the sequence 
	 * of RuleApplications is determinated by the order in the {@link CheckOperationConsistencyCommand#opRuleList}. 
	 * So when you execute the command twice without changing the order in the list, the same sequence
	 * of applications is chosen.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		// consistency check
		List<String> errorMessages = checkOperationConsistency();
		openDialog(errorMessages);
	}



	

	// checking source/target/or integrated consistency
	private List<String> checkOperationConsistency() {
		List<String> errorMessages = new ArrayList<String>();
		String errorString = "";
		for (Node n : graph.getNodes()) {
			TNode node = (TNode) n;
			// set marker type to mark the translated nodes
			if (RuleUtil.Not_Translated_Graph.equals(node.getMarkerType())) {
				errorString = "The node [" + node.getName() + ":"
						+ node.getType().getName() + "] was not translated.";
				errorMessages.add(errorString);
			}
			// check contained attributes
			for (Attribute at : node.getAttributes()) {
				// set marker type to mark the translated attributes
				TAttribute a = (TAttribute) at;
				if (RuleUtil.Not_Translated_Graph.equals(a.getMarkerType())) {
					errorString = "The attribute [" + a.getType().getName()
							+ "=" + a.getValue() + "] of node ["
							+ node.getName() + ":" + node.getType().getName()
							+ "] was not translated.";
					errorMessages.add(errorString);
				}
			}
		}
		for (Edge e : graph.getEdges()) {
			TEdge edge = (TEdge) e;
			// set marker type to mark the translated attributes
			if (RuleUtil.Not_Translated_Graph.equals(edge.getMarkerType())) {
				errorString = "The edge [" + edge.getType().getName() + ":"
						+ edge.getSource().getType().getName() + "->"
						+ edge.getTarget().getType().getName()
						+ "] was not translated.";
				errorMessages.add(errorString);
			}
		}
		return errorMessages;
	}
	
	



	/**
	 * opens the dialog with the given error messages, if no error messages given 
	 * opens the dialog with a check message
	 * @param errorMessages
	 */
	protected void openDialog(List<String> errorMessages) {

		String errorString = "";
		if (errorMessages.size() == 0) {
			errorString = consistencyType + " Consistency Check was succsessful.\n";
		} else {
			errorString = consistencyType + " Consistency Check failed!\n";
		}

		if (ruleApplicationList!=null && !ruleApplicationList.isEmpty()) {
			errorString+="\nThe following Rule(s) were applied:\n";
			for (RuleApplicationImpl ra : ruleApplicationList) {
				errorString+="\n"+ra.getRule().getName();
			}
		} else {
			errorString+="\nNo Rules were applied.\n";
		}
		
		errorString += "\n\n===============================================\n\n";
		
		for (String m : errorMessages) {
			
			errorString += m+"\n";
			
		}
		
		String title = consistencyType + " Consistency Check"; 
		Shell shell = new Shell();
		TextDialog dialog = new TextDialog(shell, title, "Results of " + consistencyTypeLowerCase + " consistency check:", errorString);

		dialog.open();
		
		shell.dispose();
		
	}
	


	
	
}
