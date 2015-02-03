/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The class GenerateAllOpRuleAction generates FT, BT, IT and CC-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateAllOpRuleAction
 */
public class GenerateAllOpRulesToolBarAction extends GenerateAllOpRuleAction {

	/**
	 * the constructor
	 * @param part
	 * @param rulepage the rule graphical page
	 */
	public GenerateAllOpRulesToolBarAction(MuvitorPageBookView part, RuleGraphicalPage rulepage) {
		super(part.getEditor());
		this.rule = (TGGRule) rulepage.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.validate.GraphValidAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

}
