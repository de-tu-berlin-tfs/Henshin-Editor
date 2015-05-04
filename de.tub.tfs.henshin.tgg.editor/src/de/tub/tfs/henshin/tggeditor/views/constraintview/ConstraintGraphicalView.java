package de.tub.tfs.henshin.tggeditor.views.constraintview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class ConstraintGraphicalView extends MuvitorPageBookView {

	@Override
	protected String calculatePartName() {
		return ((NestedConstraint)getModel()).getPremise().getName() + " -> " + ((NestedCondition)((NestedConstraint)getModel()).getPremise().getFormula()).getConclusion().getName();
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		ConstraintGraphicalPage page = new ConstraintGraphicalPage(this);
		//IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		//toolBar.add(new GraphValidToolBarAction(this, (GraphicalPage) page));
		return page;
	}

}
