package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * TreeEditPart for the folder for rules.
 */
public class RuleFolderTreeEditPart extends AdapterTreeEditPart<RuleFolder> {
	/**
	 * A list of rules.
	 */
	private List<Rule> rules = new ArrayList<Rule>();
	
	public RuleFolderTreeEditPart(RuleFolder model) {
		super(model);
		for (Rule r : model.getRules()) {
			if (!r.getName().startsWith("CR_"))
				this.rules.add(r);
		}
	}

	@Override
	protected String getText() {
		return "Rules";
	}

	@Override
	protected Image getImage(){
		try {
			return IconUtil.getIcon("ruleFolder.png");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected List<Rule> getModelChildren() {
		return this.rules;
	}

	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
}
