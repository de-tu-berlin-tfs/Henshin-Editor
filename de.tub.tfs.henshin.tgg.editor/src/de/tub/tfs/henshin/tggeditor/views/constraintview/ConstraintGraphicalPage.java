package de.tub.tfs.henshin.tggeditor.views.constraintview;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;

import de.tub.tfs.henshin.tggeditor.editparts.constraint.ConstraintGraphicalEditPartFactory;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MultiDimensionalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class ConstraintGraphicalPage extends
		MultiDimensionalPage<NestedConstraint> {

	public ConstraintGraphicalPage(MuvitorPageBookView view) {
		super(view,new int[]{1,1},new int[]{1,1} );
	}

	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		return new ConstraintGraphicalContextMenuProvider(viewer);
	}

	@Override
	protected void createCustomActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected EditPartFactory createEditPartFactory() {
		return new ConstraintGraphicalEditPartFactory();
	}

	@Override
	protected EObject[] getContentsForIndex(int i) {
		ArrayList<EObject> l = new ArrayList<EObject>();
		l.add(getCastedModel().getPremise());
		l.add(((NestedCondition)getCastedModel().getPremise().getFormula()).getConclusion());
		return l.toArray(new EObject[]{});
	}

	@Override
	protected void setupKeyHandler(KeyHandler kh) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void notifyChanged(Notification msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getName(int index) {
		return getCastedModel().getPremise().getName() + " -> " + ((NestedCondition)getCastedModel().getPremise().getFormula()).getConclusion().getName();
	}

	@Override
	protected int getNumberOfItems() {
		return 1;
	}
	
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof Module)) {
			parent = parent.eContainer();
		}
		MuvitorPaletteRoot rulePaletteRoot = null;
		if (parent != null && parent instanceof Module) {
			rulePaletteRoot = new ConstraintGraphicalPaletteRoot((Module) parent);
		}
		return rulePaletteRoot;
	}

}
