package tggeditor.actions.validate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import tgg.TGG;
import tgg.TRule;
import tggeditor.TggAggInfo;
import tggeditor.commands.CheckConflictCommand;
import tggeditor.editparts.tree.rule.FTRulesTreeEditPart;
import tggeditor.util.NodeUtil;

public class CheckRuleConflictAction extends SelectionAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.validate.CheckRuleConflictAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Check Rules for Conflicts";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Check Rules for Conflicts";
	
	List<TRule> _tRules;
	
	TransformationSystem _trafo;
	TGG _layoutSystem;

	public CheckRuleConflictAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof FTRulesTreeEditPart) {
				_trafo = (TransformationSystem) editpart.getParent().getModel();
				_layoutSystem = NodeUtil.getLayoutSystem(_trafo);
				FTRulesTreeEditPart ruleFolderEP = (FTRulesTreeEditPart) editpart;
				_tRules = ruleFolderEP.getCastedModel().getTRules();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		TggAggInfo aggInfo = new TggAggInfo(_trafo);
		aggInfo.extendDueToTGG(_layoutSystem);
		aggInfo.save("./", "tgg2agg.ggx");
		
		//TODO Auswahl ermöglichen
		for (int i=0; i<_tRules.size(); i++) {
			for (int j=0; j<_tRules.size(); j++) {
				System.out.println(i+" und "+j);
				CheckConflictCommand c = new CheckConflictCommand(_tRules.get(i).getRule(), _tRules.get(j).getRule(), aggInfo);
				c.execute();
				
			} 
		}
	}

}
