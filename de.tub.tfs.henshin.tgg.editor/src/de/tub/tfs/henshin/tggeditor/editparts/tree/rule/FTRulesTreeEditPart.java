package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;


import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * TreeEditPart for the folder for FT rules.
 */
public class FTRulesTreeEditPart extends AdapterTreeEditPart<FTRules> {
	/**
	 * List of FT rules.
	 */
	private List<TRule> tRules;
	/**
	 * List of rules relative to the FT rules.
	 */
	private List<Rule> rules;
	
	public FTRulesTreeEditPart(FTRules model) {
		super(model);			
		tRules = model.getTRules();	
		rules = new ArrayList<Rule>();
		for(TRule tr: tRules){
			rules.add(tr.getRule());
		}
	}

	@Override
	protected String getText() {
		return "FTRules";
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("FTRules.png");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected List<Rule> getModelChildren() {
		return rules;
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
}
