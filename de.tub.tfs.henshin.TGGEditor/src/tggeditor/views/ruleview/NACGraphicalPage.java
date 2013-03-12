package tggeditor.views.ruleview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;
import tggeditor.editparts.rule.NACGraphicalEditPartFactory;
import tggeditor.views.graphview.GraphicalPage;
import tggeditor.views.graphview.GraphicalPaletteRoot;

public class NACGraphicalPage extends GraphicalPage {

	private GraphicalPaletteRoot nacPaletteRoot;

	public NACGraphicalPage(MuvitorPageBookView view) {
		super(view);
	}

	@Override
	protected EditPartFactory createEditPartFactory() {
		return new NACGraphicalEditPartFactory();
	}

	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof Module)) {
			parent = parent.eContainer();
		}

		if (parent != null && parent instanceof Module) {
			nacPaletteRoot = new NACGraphicalPalletRoot(
					(Module) parent);
		}
		return nacPaletteRoot;
	}
	
	

}
