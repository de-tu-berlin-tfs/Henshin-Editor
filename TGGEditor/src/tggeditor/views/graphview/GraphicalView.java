package tggeditor.views.graphview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.part.IPage;

import tggeditor.actions.execution.ExecuteFTRulesToolBarAction;
import tggeditor.actions.validate.GraphValidToolBarAction;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class GraphicalView extends MuvitorPageBookView {

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
		GraphicalPage page = new GraphicalPage(this);
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(new GraphValidToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteFTRulesToolBarAction(this, (GraphicalPage) page));
		return page;
	}

}
