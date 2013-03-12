package tggeditor.views.ruleview;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.part.IPage;

import tgg.TGG;
import tgg.TRule;
import tggeditor.actions.create.rule.GenerateFTRuleToolBarAction;
import tggeditor.actions.execution.ExecuteRuleToolBarRuleAction;
import tggeditor.actions.validate.RuleValidToolBarAction;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * This is a PageBookView for displaying some model on 
 * an IPage. It is merely a wrapper for the contained
 * {@link MuvitorPage} implementing {@link IPage}. This class
 *  offers to show a customized page in a workbench view. 
 */

public class RuleGraphicalView extends MuvitorPageBookView {

	@Override
	protected String calculatePartName() {
		return "Rule: " + ((NamedElement) getModel()).getName();
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		RuleGraphicalPage page = new RuleGraphicalPage(this);

		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		
		boolean addFTRulesActions = true;
		TGG layoutSystem = NodeUtil.getLayoutSystem(forModel);
		if(layoutSystem != null) {
			EList<TRule> tRules = layoutSystem.getTRules();
			for(TRule temp: tRules) {
				if(temp.getRule().equals(forModel)) {
					addFTRulesActions = false;
				}
			}
		}
		if(addFTRulesActions) {
			toolBarManager.add(new GenerateFTRuleToolBarAction(this, page));
			toolBarManager.add(new RuleValidToolBarAction(this, page));
			toolBarManager.add(new ExecuteRuleToolBarRuleAction(this, page));
		}
		return new RuleGraphicalPage(this);
	}

}
