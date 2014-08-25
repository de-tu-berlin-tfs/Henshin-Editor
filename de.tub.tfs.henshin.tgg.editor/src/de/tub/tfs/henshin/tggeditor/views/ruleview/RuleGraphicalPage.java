/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.views.ruleview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.actions.AbstractTggActionFactory;
import de.tub.tfs.henshin.tggeditor.actions.DeleteNacMappingsAction;
import de.tub.tfs.henshin.tggeditor.actions.EditAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerUnspecifiedAction;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.Divider;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPartFactory;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MultiDimensionalPage;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class RuleGraphicalPage extends MultiDimensionalPage<Rule> {
	private static final Graph DUMMY = TggFactory.eINSTANCE.createTripleGraph();
	private NestedCondition currentNac;
	private MuvitorPaletteRoot rulePaletteRoot;
	
	static {
		DUMMY.setName("");
	}

	public RuleGraphicalPage(MuvitorPageBookView view) {
		super(view,new int[]{1,1},new int[]{1,1} );
		TreeEditor editor = (TreeEditor) this.getEditor();
		editor.addRulePage((Rule) getModel(), this);
		registerAdapter(((Rule) getModel()).getLhs());
		
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
		registerAction(new NewMarkerUnspecifiedAction(getEditor()));
		registerAction(new CreateAttributeAction(getEditor()));
        registerSharedActionAsHandler(ActionFactory.COPY.getId());
        registerSharedActionAsHandler(ActionFactory.CUT.getId());
        registerSharedActionAsHandler(ActionFactory.PASTE.getId());

        registerAction(new EditAttributeAction(getEditor()));
        IExtensionRegistry reg = Platform.getExtensionRegistry();
        IExtensionPoint ep = reg.getExtensionPoint("de.tub.tfs.henshin.tgg.editor.graph.actions");
        IExtension[] extensions = ep.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
        	IExtension ext = extensions[i];
        	IConfigurationElement[] ce = 
        			ext.getConfigurationElements();
        	for (int j = 0; j < ce.length; j++) {

        		try {
        			AbstractTggActionFactory obj = (AbstractTggActionFactory) ce[j].createExecutableExtension("class");

        			registerAction(obj.createAction(getEditor()));

        		} catch (CoreException e) {
        			
        		}


        	}
        }
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
			
			if(ModelUtil.isOpRule((Rule) getModel())) {
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
	protected void notifyChanged(Notification notification) {
		
		if (notification.getNotifier() instanceof Graph) {
			final int featureId = notification.getFeatureID(HenshinPackage.class);
			switch (featureId){
				case HenshinPackage.GRAPH__FORMULA:
				if (notification.getNewValue() == null){
					this.maximiseViewer(1);
					this.setViewersContents(0, DUMMY);					
					this.setViewerVisibility(0, false);
				} else {
					this.maximiseViewer(-1);
					
					if(this.getCastedModel().getLhs().getFormula() != null){
						Formula f = getCastedModel().getLhs().getFormula();
						TreeIterator<EObject> elems = f.eAllContents();
						while(elems.hasNext()){
							EObject elem = elems.next();
							if(elem instanceof Graph){
								this.setViewersContents(0,elem );
								break;
							}
						}

					}
					
					
					
					this.setViewerVisibility(0, true);
				}
				
				default:
					break; 
			}
		}	
	}

	/*@Override
	protected EObject[] getViewerContents() {
		ArrayList<EObject> l = new ArrayList<EObject>();
		/*if(currentNac !=null)		
			l.add(currentNac.getConclusion());
		else
			l.add(null);
		*/ /*
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
	}*/

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


	@Override
	protected EObject[] getContentsForIndex(int i) {

		ArrayList<EObject> l = new ArrayList<EObject>();

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
		//setViewerVisibility(i, true);

		if (l.size() < 1){
			l.add(0, DUMMY);
		}

		l.add(getModel());

		return l.toArray(new EObject[]{});
		
	}

	@Override
	protected String getName(int index) {
		
		return getCastedModel().getName();
	}

	@Override
	protected int getNumberOfItems() {
		// TODO Auto-generated method stub
		return 1;
	}

}
