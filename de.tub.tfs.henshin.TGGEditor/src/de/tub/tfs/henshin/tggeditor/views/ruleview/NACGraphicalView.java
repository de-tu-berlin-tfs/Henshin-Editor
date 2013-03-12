package de.tub.tfs.henshin.tggeditor.views.ruleview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;


public class NACGraphicalView extends GraphicalView {
	@Override
	protected String calculatePartName() {
		return "NAC: " + ((NamedElement) getModel()).getName();
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		return new NACGraphicalPage(this);
	}
	
	
}
