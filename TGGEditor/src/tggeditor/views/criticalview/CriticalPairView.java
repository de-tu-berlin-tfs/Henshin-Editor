package tggeditor.views.criticalview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.part.IPage;

import tgg.CritPair;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class CriticalPairView extends MuvitorPageBookView {

	@Override
	protected String calculatePartName() {
		if(getModel() instanceof CritPair){
			return "CriticalPair: " + ((NamedElement) ((CritPair) getModel()).getOverlapping()).getName();
		}
		return null;
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		CriticalPairPage page = new CriticalPairPage(this);
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		return page;
	}
}
