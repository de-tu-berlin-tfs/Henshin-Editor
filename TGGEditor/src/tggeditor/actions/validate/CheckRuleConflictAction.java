package tggeditor.actions.validate;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import tgg.TGG;
import tgg.TRule;
import tggeditor.TggAggInfo;
import tggeditor.commands.CheckForCritPairCommand;
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
//		for (int i=0; i<_tRules.size(); i++) {
//			for (int j=0; j<_tRules.size(); j++) {
//				System.out.println(i+" und "+j);
//				CheckForCritPairCommand c = new CheckForCritPairCommand(_tRules.get(i).getRule(), _tRules.get(j).getRule(), aggInfo);
//				c.execute();
//			} 
//		}
		
//		TRule first = new SingleElementListSelectionDialog<TRule>(null,
//				new LabelProvider() {
//					@Override
//					public String getText(Object element) {
//						return ((TRule) element).getRule().getName();
//					}
//
//				}, _tRules.toArray(new TRule[_tRules.size()]), "Rule Selection",
//				"Select the first Rule for checking conflicts:").run();
//		TRule second = new SingleElementListSelectionDialog<TRule>(null,
//				new LabelProvider() {
//					@Override
//					public String getText(Object element) {
//						return ((TRule) element).getRule().getName();
//					}
//
//				}, _tRules.toArray(new TRule[_tRules.size()]), "Rule Selection",
//				"Select the second Rule for checking conflicts:").run();
		
		ElementListSelectionDialog firstDialog = new ElementListSelectionDialog(null,
				new LabelProvider() {
					@Override
					public String getText(Object element) {
						return ((TRule) element).getRule().getName();
					}
				});
		firstDialog.setElements(_tRules.toArray(new TRule[_tRules.size()]));
		firstDialog.setTitle("Rule Selection");
		firstDialog.setMessage("Select the Rule for the first parameter.");
		firstDialog.setMultipleSelection(true);
		firstDialog.open();
		Object[] firstRuleList = firstDialog.getResult();
		
		ElementListSelectionDialog secondDialog = new ElementListSelectionDialog(null,
										new LabelProvider() {
											@Override
											public String getText(Object element) {
												return ((TRule) element).getRule().getName();
											}
										});
		secondDialog.setElements(_tRules.toArray(new TRule[_tRules.size()]));
		secondDialog.setTitle("Rule Selection");
		secondDialog.setMessage("Select the Rule for the second parameter.");
		secondDialog.setMultipleSelection(true);
		secondDialog.open();
		Object[] secondRuleList = secondDialog.getResult();
	
		CompoundCommand commands = new CompoundCommand();
		
		for (Object o1 : firstRuleList) {
			if (o1 instanceof TRule) {
				TRule rule1 = (TRule) o1;
				for (Object o2 : secondRuleList) {
					if (o2 instanceof TRule) {
						TRule rule2  =(TRule) o2;
						CheckForCritPairCommand c = new CheckForCritPairCommand(rule1.getRule(), rule2.getRule(), aggInfo);
						commands.add(c);
					}
				}
			}
		}
		
		commands.execute();
		
//		CheckForCritPairCommand c = new CheckForCritPairCommand(first.getRule(), second.getRule(), aggInfo);
//		c.execute();
	}

}
