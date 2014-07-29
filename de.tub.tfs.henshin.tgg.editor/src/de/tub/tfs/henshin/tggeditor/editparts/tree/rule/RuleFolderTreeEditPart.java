package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.IndependentUnitImpl;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tggeditor.editparts.tree.TGGTreeContainerEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;
import de.tub.tfs.muvitor.ui.utils.MuvitorNotifierService;

/**
 * TreeEditPart for the folder for rules.
 */
public class RuleFolderTreeEditPart extends AdapterTreeEditPart<IndependentUnit> implements IDirectEditPart{

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new FolderComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TGGTreeContainerEditPolicy());	
		
	}

	
	public RuleFolderTreeEditPart(IndependentUnit model) {
		super(model);
		
	}
	
	@Override
	protected String getText() {
		if (getCastedModel().getName() != null)
			return getCastedModel().getName();
		return "unnamed Folder";
	}

	@Override
	protected Image getImage(){
		try {
			return IconUtil.getIcon(getCastedModel().getDescription());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected List getModelChildren() {
		HashSet<Unit> ignored = new HashSet<Unit>();
		
		for (Unit unit : getCastedModel().getSubUnits()) {
			if (unit instanceof IndependentUnit){
				ignored.addAll(unit.getSubUnits(true));
			}
		}
		LinkedList<EObject> l = new LinkedList<EObject>(getCastedModel().getSubUnits());
		l.removeAll(ignored);
		return l;
	}

	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		if(notification.getEventType() == 9)
			return ;

		if (notification.getNotifier() != this.getCastedModel()){
			refreshSubUnits();
			return;
		}
			
		switch (featureId){
			
			case HenshinPackage.INDEPENDENT_UNIT__SUB_UNITS:
				//sortRulesIntoCategories(getCastedModel().eContainer());
				
				if (EcoreUtil.getRootContainer(getCastedModel()) != this.getModel()){
					EcoreUtil.getRootContainer(getCastedModel()).eNotify(notification);
					if (this.getCastedModel().eContainer() != null)
						refreshChildren();
				}
				break;
			case HenshinPackage.INDEPENDENT_UNIT__DESCRIPTION:
			case HenshinPackage.INDEPENDENT_UNIT__NAME:
				refreshVisuals();
				break;

			default:
				// check that the TGGLayout of the transformation system is present (e.g. can disappear when another editor is in use)
				if (NodeUtil.getLayoutSystem(getCastedModel()) != null)
				refresh();
				break;
		}
	}

	private void refreshSubUnits() {
		this.getCastedModel().eSetDeliver(false);
		for (Iterator<Unit> iterator = getCastedModel().getSubUnits().iterator(); iterator.hasNext();) {
			 Unit u = iterator.next();
			 if (u.eContainer() == null)
				 iterator.remove();
			
		}
		this.getCastedModel().eSetDeliver(true);
		refresh();
		
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.INDEPENDENT_UNIT__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}
	
	
	
}
