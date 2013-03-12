package tggeditor.actions.create.rule;

import org.eclipse.jface.action.IAction;

import tggeditor.commands.create.rule.GenerateFTRuleCommand;
import tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleAction
 * @see GenerateFTRuleCommand
 */
public class GenerateFTRuleToolBarAction extends GenerateFTRuleAction implements
		IAction {

	/**
	 * the constructor
	 * @param part
	 * @param rulepage the rule graphical page
	 */
	public GenerateFTRuleToolBarAction(MuvitorPageBookView part, RuleGraphicalPage rulepage) {
		super(part.getEditor());
		this.rule = rulepage.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.validate.GraphValidAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

}
