package tggeditor.actions.validate;

import java.util.List;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
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
import tggeditor.util.ModelUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class CheckRuleConflictAction extends SelectionAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.validate.CheckRuleConflictAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Check Rules for Conflicts";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Check Rules for Conflicts";
	
	List<TRule> _tRules;
	
	Module _trafo;
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
				_trafo = (Module) editpart.getParent().getModel();
				_layoutSystem = NodeUtil.getLayoutSystem(_trafo);
				FTRulesTreeEditPart ruleFolderEP = (FTRulesTreeEditPart) editpart;
				_tRules = ruleFolderEP.getCastedModel().getTRules();
				if (_tRules.isEmpty()) return false;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		
		cleanTrafo(_trafo);
		TggAggInfo aggInfo = new TggAggInfo(_trafo);
		
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
		
		if (firstRuleList != null && secondRuleList != null) {
		for (Object o1 : firstRuleList) {
			if (o1 instanceof TRule) {
				TRule rule1 = (TRule) o1;
				for (Object o2 : secondRuleList) {
					if (o2 instanceof TRule) {
						TRule rule2  =(TRule) o2;
							if (rule1 != null && rule2 != null) {
						CheckForCritPairCommand c = new CheckForCritPairCommand(rule1.getRule(), rule2.getRule(), aggInfo);
						commands.add(c);
							}
						}
					}
		}
			}
		}
		commands.execute();
		aggInfo.save("./", "tgg2agg.ggx");
	}
	
	private void cleanTrafo(Module trafo) {
		CompoundCommand commands = new CompoundCommand();
		for (Rule r : ModelUtil.getRules( trafo)) {
			for (Mapping m : r.getMappings()) {
				if (m.getImage() == null || m.getOrigin() == null) 
					commands.add(new SimpleDeleteEObjectCommand(m));
}
		}
		commands.execute();
	}
}
