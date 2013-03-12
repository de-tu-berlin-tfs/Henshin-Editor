package de.tub.tfs.henshin.tggeditor.views.graphview;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.ui.actions.PartEventAction;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tggeditor.MappingConverter;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.editparts.critical.CriticalPairEditPartFactory;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.IDUtil;
import de.tub.tfs.muvitor.ui.MultiDimensionalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class CriticalPairPage extends MultiDimensionalPage<CritPair> {

	private MuvitorPaletteRoot paletteRoot;
	
	public CriticalPairPage(MuvitorPageBookView view) {
		super(view, new int[]{2,1}, new int[]{1,1});
		
		TreeEditor editor = (TreeEditor) this.getEditor();
		CritPair critPair = (CritPair) getModel();
		editor.addCritPairPage(critPair, this);
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
		while (parent != null && !(parent instanceof Module)) {
			parent = parent.eContainer();
		}
		
		if (parent != null && parent instanceof Module){
			paletteRoot = new CriticalPairPaletteRoot((Module) parent);
		}
		return paletteRoot;
	}
	
	@Override
	protected void notifyChanged(Notification msg) {
	}

	@Override
	protected void setupKeyHandler(KeyHandler kh) {
	}
	
	
	public CritPair getCastedModel() {
		return (CritPair) getModel();
	}

	@Override
	protected EObject[] getContentsForIndex(int i) {
		ArrayList<EObject> l = new ArrayList<EObject>();
		CritPair critPair = getCastedModel();
		l.add(critPair.getRule1());
		l.add(critPair.getRule2());
		l.add(critPair.getOverlapping());
		
		HashSet<HashSet<Node>> mappings = MappingConverter.convertMappings(critPair);
		int idx = 0;
		for (HashSet<Node> hashSet : mappings) {
			for (Node node : hashSet) {
				node.setName("["+idx+"]");
			}
			idx++;
		}
		return l.toArray(new EObject[]{});
	}

	@Override
	protected String getName(int index) {
		return "page_"+index;
	}

	@Override
	protected int getNumberOfItems() {
		return 1;
	}
}
