package tggeditor.views.graphview;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.ui.actions.PartEventAction;

import tgg.CritPair;
import tggeditor.TreeEditor;
import tggeditor.editparts.graphical.CriticalPairEditPartFactory;
import tggeditor.util.ModelUtil;
import tggeditor.views.ruleview.MuvitorVPage;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.IDUtil;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class CriticalPairPage extends MuvitorVPage {

	private Rule rule1;
	private Rule rule2;
	private Graph overlapping;
	private MuvitorPaletteRoot paletteRoot;
	
	public CriticalPairPage(MuvitorPageBookView view) {
		super(view);
		TreeEditor editor = (TreeEditor) IDUtil.getHostEditor(getModel());
		editor.addCritPairPage(getCastedModel(), this);
	}

	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		// TODO Auto-generated method stub
		return new CriticalPairContextMenuProvider(viewer);
	}

	@Override
	protected void createCustomActions() {
		//später evtl. generateNAC
	}

	@Override
	protected EditPartFactory createEditPartFactory() {
		return new CriticalPairEditPartFactory();
	}

	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof TransformationSystem)) {
			parent = parent.eContainer();
		}
		
		if (parent != null && parent instanceof TransformationSystem){
			paletteRoot = new CriticalPairPaletteRoot((TransformationSystem) parent);
		}
		return paletteRoot;
	}
	
	@Override
	protected void notifyChanged(Notification msg) {
		// TODO Auto-generated method stub
		super.notifyChanged(msg);
	}

	@Override
	protected EObject[] getViewerContents() {
		ArrayList<EObject> l = new ArrayList<EObject>();
		CritPair critPair = getCastedModel();
		l.add(critPair.getOverlapping());
		l.add(critPair.getRule1());
		l.add(critPair.getRule2());
//		l.add(getModel());
		return l.toArray(new EObject[]{});
	}

	@Override
	protected void setupKeyHandler(KeyHandler kh) {
		// TODO Auto-generated method stub

	}
	
	public CritPair getCastedModel() {
		return (CritPair) getModel();
	}

}
