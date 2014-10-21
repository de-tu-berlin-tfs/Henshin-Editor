/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;

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

	private ExecuteOpRulesCommand opRuleCmd;
	private boolean consistencyCheckFailed = false;

	
	
	/**the constructor
	 * @param graph {@link CheckOperationConsistencyCommand#graph}
	 * @param opRuleList {@link CheckOperationConsistencyCommand#opRuleList}
	 */
	public CheckOperationConsistencyCommand(ExecuteOpRulesCommand opRuleCmd) {
		super();
		this.opRuleCmd= opRuleCmd;
		this.graph = opRuleCmd.graph;
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
	 * of RuleApplications is determined by the order in the {@link CheckOperationConsistencyCommand#opRuleList}. 
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
		
		// print only the first 10 elements
		int untranslatedElements=0;
		int untranslatedNodes=0;
		int untranslatedAttributes=0;
		int untranslatedEdges=0;
		int maxPrintedErrors=10;
		for (Node n : graph.getNodes()) {
			TNode node = (TNode) n;
			// set marker type to mark the translated nodes
			if (RuleUtil.Not_Translated_Graph.equals(node.getMarkerType())) {
				errorString = "The node [" + node.getName() + ":"
						+ node.getType().getName() + "] was not translated.";
				if(untranslatedElements<maxPrintedErrors)
					errorMessages.add(errorString);
				untranslatedElements++;
				untranslatedNodes++;
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
					if(untranslatedElements<maxPrintedErrors)
						errorMessages.add(errorString);
					untranslatedElements++;
					untranslatedAttributes++;
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
				if(untranslatedElements<maxPrintedErrors)
					errorMessages.add(errorString);
				untranslatedElements++;
				untranslatedEdges++;
			}
		}
		errorString = "===================================================";
		errorMessages.add(errorString);
		errorString = "Amount of untranslated elements:";
		errorMessages.add(errorString);
		errorString = "" + untranslatedNodes + " nodes";
		errorMessages.add(errorString);
		errorString = "" + untranslatedAttributes + " attributes";
		errorMessages.add(errorString);
		errorString = "" + untranslatedEdges + " edges";
		errorMessages.add(errorString);
		errorString = "===================================================";
		errorMessages.add(errorString);
		
		if(untranslatedElements!=0)
			consistencyCheckFailed=true;
		else consistencyCheckFailed=false;
		
		return errorMessages;
	}
	
	



	/**
	 * opens the dialog with the given error messages, if no error messages given 
	 * opens the dialog with a check message
	 * @param errorMessages
	 */
	protected void openDialog(List<String> errorMessages) {

		// retrieve the list of applied rules from the executed operational rule command
		this.ruleApplicationList = opRuleCmd.getRuleApplicationList();

		
		String messageString = "";
		if (consistencyCheckFailed) {
			messageString = consistencyType + " Consistency Check failed!\n";
		} else {
			messageString = consistencyType + " Consistency Check was successful.\n";
		}

		if (ruleApplicationList!=null && !ruleApplicationList.isEmpty()) {
			messageString+="\nThe following Rule(s) were applied:\n \n";
			String ruleName=null;
			// hashmap of rule names and the amount of their applications
			// linked list preserves the order of entries
			HashMap<String,Integer> ruleAmounts = new LinkedHashMap<String,Integer>(); 
			for (RuleApplicationImpl ra : ruleApplicationList) {
				ruleName=ra.getRule().getName();
				if(ruleName==null) continue;

				if(!ruleAmounts.containsKey(ruleName)){
					// create the fresh entry in the map with initial value 1
					ruleAmounts.put(ruleName,1);
				}
				else{
					// increment the amount
					ruleAmounts.put(ruleName,ruleAmounts.get(ruleName)+1);
				}
			}
			// put the result in the message String
			for(String rule: ruleAmounts.keySet()){
				messageString+=rule + ": " + ruleAmounts.get(rule) + " time(s).\n";
			} 

			
			
		} else {
			messageString+="\nNo Rules were applied.\n \n";
		}
		
		messageString += "\n===============================================\n\n";
		
		for (String m : errorMessages) {
			
			messageString += m+"\n";
			
		}
		
		String title = consistencyType + " Consistency Check"; 
		Shell shell = new Shell();
		TextDialog dialog = new TextDialog(shell, title, "Results of " + consistencyTypeLowerCase + " consistency check:", messageString);

		dialog.open();
		
		shell.dispose();
		
	}
	


	
	
}
