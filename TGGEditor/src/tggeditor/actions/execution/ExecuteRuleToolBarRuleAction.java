package tggeditor.actions.execution;

import tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;



/**
 * The Class ExecuteRuleToolBarRuleAction is shown in the toolbar of the rule editor.
 * Gives a selection when more than one graph is available. Executes the 
 * ExecuteRuleCommand
 * @see tggeditor.commands.ExecuteRuleCommand
 */
public class ExecuteRuleToolBarRuleAction extends ExecuteRuleAction {
	
	/**
	 * Instantiates a new execute rule tool bar rule action.
	 *
	 * @param part the part
	 * @param rulePage the rule page
	 */
	public ExecuteRuleToolBarRuleAction(MuvitorPageBookView part, RuleGraphicalPage rulePage) {
		super(part.getEditor());
		rule=rulePage.getCastedModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.CreateNACAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see henshineditor.actions.rule.ExecuteRuleAction#run()
	 */
	@Override
	public void run() {
		graph=null;
		super.run();
	}
	
	
}
