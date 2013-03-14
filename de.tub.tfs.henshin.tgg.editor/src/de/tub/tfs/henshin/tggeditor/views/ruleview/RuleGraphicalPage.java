package de.tub.tfs.henshin.tggeditor.views.ruleview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.actions.DeleteNacMappingsAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerAction;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPartFactory;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class RuleGraphicalPage extends MuvitorPage {
	private NestedCondition currentNac;
	private MuvitorPaletteRoot rulePaletteRoot;
	

	public RuleGraphicalPage(MuvitorPageBookView view) {
		super(view);
		TreeEditor editor = (TreeEditor) this.getEditor();
		editor.addRulePage((Rule) getModel(), this);
	}

	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		return new RuleGraphicalContextMenuProvider(viewer);
	}

	@Override
	protected void createCustomActions() {
		registerAction(new DeleteNacMappingsAction(getEditor()));
		registerAction(new NewMarkerAction(getEditor()));
		registerAction(new CreateAttributeAction(getEditor()));
        registerSharedActionAsHandler(ActionFactory.COPY.getId());
        registerSharedActionAsHandler(ActionFactory.CUT.getId());
        registerSharedActionAsHandler(ActionFactory.PASTE.getId());
	}

	@Override
	protected EditPartFactory createEditPartFactory() {
		return new RuleGraphicalEditPartFactory();
	}

	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof Module)) {
			parent = parent.eContainer();
		}

		if (parent != null && parent instanceof Module) {
			
			if(ModelUtil.isFTRule((Rule) getModel())) {
				rulePaletteRoot = new FTRuleGraphicalPaletteRoot(
						(Module) parent);
			} else {	
				rulePaletteRoot = new RuleGraphicalPaletteRoot(
						(Module) parent);
			}
		}
		return rulePaletteRoot;
	}
	
	@Override
	protected void notifyChanged(Notification msg) {
		// TODO Auto-generated method stub
		super.notifyChanged(msg);
	}

	@Override
	protected EObject[] getViewerContents() {
		ArrayList<EObject> l = new ArrayList<EObject>();
		/*if(currentNac !=null)		
			l.add(currentNac.getConclusion());
		else
			l.add(null);
		*/
		Rule rule = (Rule)getModel();
		if(rule.getLhs().getFormula() != null){
			Formula f = getCastedModel().getLhs().getFormula();
			TreeIterator<EObject> elems = f.eAllContents();
			while(elems.hasNext()){
				EObject elem = elems.next();
				if(elem instanceof Graph){
					l.add(elem );
					break;
				}
			}
			
		}
		if (l.size() < 1)
				l.add(null);
		l.add(getModel());
		
		return l.toArray(new EObject[]{});
	}

	public void setCurrentNac(NestedCondition model){
		this.currentNac = model;	
		this.setViewersContents(0, model.getConclusion());
		this.setViewerVisibility(0, true);
	}
	
	public NestedCondition getCurrentNac(){
		return currentNac;
	}
	
	@Override
	protected void setupKeyHandler(KeyHandler kh) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unused")
	private void getNAClist(Formula f, List<EObject> l) {
		if (f instanceof And) {
			if (((And)f).getLeft() instanceof And) 
				getNAClist(((And)f).getLeft(), l);
			else
				l.add(((NestedCondition)((And)f).getLeft()).getConclusion());
			if (((And)f).getRight() instanceof And)
				getNAClist(((And)f).getRight(), l);
			else
				l.add(((NestedCondition)((And)f).getRight()).getConclusion());
		}
		else
			l.add(((NestedCondition)f).getConclusion());
	}
	
	public Rule getCastedModel() {
		return (Rule) getModel();
	}

}
