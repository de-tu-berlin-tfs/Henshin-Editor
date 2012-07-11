package tggeditor.actions.validate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import tgg.TRule;
import tggeditor.commands.CheckConflictCommand;
import tggeditor.editparts.tree.rule.FTRulesTreeEditPart;

public class CheckRuleConflictAction extends SelectionAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.validate.CheckRuleConflictAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Check Rules for Conflicts";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Check Rules for Conflicts";
	
	List<Rule> _rules;
	List<TRule> _tRules;

	public CheckRuleConflictAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	@Override
	protected boolean calculateEnabled() {
		_rules = new ArrayList<Rule>();
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof FTRulesTreeEditPart) {
				FTRulesTreeEditPart ruleFolderEP = (FTRulesTreeEditPart) editpart;
				_tRules = ruleFolderEP.getCastedModel().getTRules();
				for (TRule t : _tRules) {
					_rules.add(t.getRule());
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		for (int i=0; i<_rules.size(); i++) {
			for (int j=0; j<_rules.size(); j++) {
				CheckConflictCommand c = new CheckConflictCommand(_rules.get(i), _rules.get(j));
				c.execute();
			}
		}
	}

}
