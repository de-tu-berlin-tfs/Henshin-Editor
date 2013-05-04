package de.tub.tfs.henshin.tggeditor.views.graphview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.actions.ShowMetaModelAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFTRulesToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.filter.FilterMetaModelAction;
import de.tub.tfs.henshin.tggeditor.actions.filter.FilterTypeAction;
import de.tub.tfs.henshin.tggeditor.actions.search.ModelSearchInGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.search.TypeSearchInGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.GraphValidToolBarAction;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class GraphicalView extends MuvitorPageBookView {
	
	private GraphicalPage page;

	
	@Override
	protected String calculatePartName() {
		if(getModel().eContainer() instanceof NestedCondition){
			return "NAC: " + ((NamedElement) getModel()).getName();
		}else{
			return "Graph: " + ((NamedElement) getModel()).getName();
		}
	}

	
	@Override
	protected IPage createPageForModel(EObject forModel) {				
		page = new GraphicalPage(this);
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(new GraphValidToolBarAction(this, page));
		toolBar.add(new ExecuteFTRulesToolBarAction(this, page));
		toolBar.add(new TypeSearchInGraphAction(this, (TripleGraph) page.getCastedModel()));
		toolBar.add(new ModelSearchInGraphAction(this, (TripleGraph) page.getCastedModel()));
		toolBar.add(new FilterTypeAction(this, (TripleGraph) page.getCastedModel()));
		toolBar.add(new FilterMetaModelAction(this, (TripleGraph) page.getCastedModel()));
		if (getEditor().getEditorSite().getActionBars().getToolBarManager().find(ShowMetaModelAction.ID) == null) {
			getEditor().getEditorSite().getActionBars().getToolBarManager().add(new ShowMetaModelAction(this, (TripleGraph) page.getCastedModel()));
		}
		else {
			ActionContributionItem actionContributionItem = (ActionContributionItem) getEditor().getEditorSite().getActionBars().getToolBarManager().find(ShowMetaModelAction.ID);
			ShowMetaModelAction action = (ShowMetaModelAction) actionContributionItem.getAction();
			action.setGraph((TripleGraph) page.getCastedModel());
		}
		return page;
	}

	
	public GraphicalPage getPage() {
		return page;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class key) {
		if (key == CommandStack.class) {
			return (CommandStack) page.getEditor().getAdapter(CommandStack.class);
		}
		return super.getAdapter(key);
	}
}
