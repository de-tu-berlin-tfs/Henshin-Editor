/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.jface.action.IAction;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;
//NEW GERARD
//REF
/**
 * The class GenerateITRuleAction generates Integration-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateITRuleAction
 * @see ProcessRuleCommand
 */
public class GenerateITRuleToolBarAction extends GenerateITRuleAction implements
		IAction {

	/**
	 * the constructor
	 * @param part
	 * @param rulepage the rule graphical page
	 */
	//REF 2
	public GenerateITRuleToolBarAction(MuvitorPageBookView part, RuleGraphicalPage rulepage) {
		super(part.getEditor());
		this.rule = (TGGRule) rulepage.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.validate.GraphValidAction#calculateEnabled()
	 */
	//REF 133
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

}
