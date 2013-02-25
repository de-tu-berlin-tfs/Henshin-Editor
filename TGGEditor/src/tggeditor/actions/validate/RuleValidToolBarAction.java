package tggeditor.actions.validate;

import tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The RuleValidAction is shown in the toolbar of the rule editor. Makes same 
 * checks as RuleValidAction.
 * @see tggeditor.actions.validate.RuleValidAction
 */
public class RuleValidToolBarAction extends RuleValidAction {
	
	/**
	 * the Constructor
	 * @see tggeditor.actions.validate.RuleValidAction#RuleValidAction(org.eclipse.ui.IWorkbenchPart)
	 * @param part
	 * @param page
	 */
	public RuleValidToolBarAction(MuvitorPageBookView part, 
			RuleGraphicalPage page) {
		super(part.getEditor());
		this.rule = page.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.validate.GraphValidAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return rule != null;
	}
}
