package de.tub.tfs.henshin.tggeditor.views.ruleview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;

import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerUnspecifiedAction;
import de.tub.tfs.henshin.tggeditor.editparts.rule.NACGraphicalEditPartFactory;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPaletteRoot;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

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
	protected void createCustomActions() {
		registerAction(new NewMarkerUnspecifiedAction(getEditor()));
		super.createCustomActions();
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
