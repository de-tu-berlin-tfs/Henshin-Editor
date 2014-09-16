/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.rule;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.rule.RulePage;

/**
 * The Class RuleValidToolBarAction.
 */
public class ValidateRuleToolBarAction extends ValidateRuleAction {

	/**
	 * Instantiates a new rule valid tool bar action.
	 * 
	 * @param part
	 *            the part
	 * @param rulePage
	 *            the rule page
	 */
	public ValidateRuleToolBarAction(IWorkbenchPart part, RulePage rulePage) {
		super(part);
		this.rule = rulePage.getCastedModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.CreateNACAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return rule != null;
	}

}
