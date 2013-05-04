package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CheckedRulePairFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.FTRuleFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolder;
import de.tub.tfs.henshin.tggeditor.model.properties.tree.ModulePropertySource;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class TransformationSystemTreeEditPart extends AdapterTreeEditPart<Module> {

	public TransformationSystemTreeEditPart(Module model) {
		super(model);
	}
	
	@Override
	protected String getText() {
		//return getCastedModel().getName();
		return "Transformation System";
	}
	
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.add(new ImportFolder(getCastedModel()));
		list.add(new GraphFolder(getCastedModel()));
		list.add(new RuleFolder(getCastedModel()));			
		//FTRules ftRules = new FTRules(getCastedModel());
		list.add(new FTRuleFolder(getCastedModel()));
		list.add(new CheckedRulePairFolder(getCastedModel()));
		return list;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		if(notification.getEventType() == 9)
			return ;
		
		switch (featureId){
			case HenshinPackage.MODULE__INSTANCES:
				refreshChildren();
				break;
			default:
				// check that the TGGLayout of the transformation system is present (e.g. can disappear when another editor is in use)
				if (NodeUtil.getLayoutSystem(getCastedModel()) != null)
				refresh();
				break;
		}
	}
	
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("transformationsystem18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
	
	@Override
	protected IPropertySource createPropertySource() {
		return new ModulePropertySource(getCastedModel());
	}
}
