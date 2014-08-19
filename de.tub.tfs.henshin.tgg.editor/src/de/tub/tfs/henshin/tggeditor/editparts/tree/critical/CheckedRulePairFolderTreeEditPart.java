package de.tub.tfs.henshin.tggeditor.editparts.tree.critical;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tggeditor.editpolicies.CheckedRulePairFolderEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class CheckedRulePairFolderTreeEditPart extends AdapterTreeEditPart<CheckedRulePairFolder>{

	/**
	 * List of critical pairs.
	 */
	private List<CritPair> critPairs;
	
	public CheckedRulePairFolderTreeEditPart(CheckedRulePairFolder model) {
		super(model);
		critPairs = model.getCritPairs();
	}
	
	@Override
	protected String getText() {
		if (critPairs.isEmpty())
			return "no critical pairs!";
		return critPairs.get(0).getRule1().getName() + " x " + critPairs.get(0).getRule2().getName();
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("CritPairs.png");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected List<CritPair> getModelChildren() {
		return getCastedModel().getCritPairs();
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new CheckedRulePairFolderEditPolicy());
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		refresh();
		super.notifyChanged(notification);
	}
}
