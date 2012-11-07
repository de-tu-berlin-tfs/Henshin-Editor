package tggeditor.views.criticalview;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;

import tgg.CritPair;
import tggeditor.MappingConverter;
import tggeditor.TreeEditor;
import tggeditor.editparts.critical.CriticalPairEditPartFactory;
import tggeditor.views.ruleview.MuvitorVPage;
import tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class CriticalPairPage extends MultiDimensionalPage<CritPair> {

	private Rule rule1;
	private Rule rule2;
	private Graph overlapping;
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
//		notifyChanged(msg);
	}

//	@Override
//	public EObject[] getViewerContents() {
//		ArrayList<EObject> l = new ArrayList<EObject>();
//		CritPair critPair = getCastedModel();
////		l.add(critPair.getRule1());	
////		l.add(critPair.getRule2());
////		l.add(critPair.getOverlapping());
//		
//		HashSet<HashSet<Node>> mappings = MappingConverter.convertMappings(critPair);
////		int idx = 0;
////		for (HashSet<Node> hashSet : mappings) {
////			for (Node node : hashSet) {
////				node.setName("["+idx+"]");
////			}
////		}
////		l.add(getModel());
//		return l.toArray(new EObject[]{});
//	}

	@Override
	protected void setupKeyHandler(KeyHandler kh) {
		// TODO Auto-generated method stub

	}
	
	public CritPair getCastedModel() {
		return (CritPair) getModel();
	}
	
	public void setRule1(Rule model){
		this.rule1 = model;	
		this.setViewersContents(0, model.getRhs());
		this.setViewerVisibility(0, true);
	}
	
	public void setRule2(Rule model){
		this.rule1 = model;	
		this.setViewersContents(1, model.getRhs());
		this.setViewerVisibility(1, true);
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
		return "getNameWithIndex"+index;
	}

	@Override
	protected int getNumberOfItems() {
		return 1;
	}
}
