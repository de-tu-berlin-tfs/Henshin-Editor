package de.tub.tfs.henshin.tggeditor.views.ruleview;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRuleToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateCCRuleToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRuleToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteRuleToolBarRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidToolBarAction;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * This is a PageBookView for displaying some model on 
 * an IPage. It is merely a wrapper for the contained
 * {@link MuvitorPage} implementing {@link IPage}. This class
 *  offers to show a customized page in a workbench view. 
 */

public class RuleGraphicalView extends MuvitorPageBookView {

	/**
	 * An unique if of this view
	 */
	public static final String ID = TreeEditor.RULE_VIEW_ID;
	
	@Override
	protected String calculatePartName() {
		return "Rule: " + ((NamedElement) getModel()).getName();
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		final RuleGraphicalPage page = new RuleGraphicalPage(this);

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
			toolBarManager.add(new GenerateBTRuleToolBarAction(this, page));
			toolBarManager.add(new GenerateCCRuleToolBarAction(this, page));
			toolBarManager.add(new RuleValidToolBarAction(this, page));
			toolBarManager.add(new ExecuteRuleToolBarRuleAction(this, page));
		}
		
		 Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if (((Rule) getModel()).getLhs().getFormula() == null)
					page.maximiseViewer(1);
				
			}
		});
		
		return page;
	}

}
