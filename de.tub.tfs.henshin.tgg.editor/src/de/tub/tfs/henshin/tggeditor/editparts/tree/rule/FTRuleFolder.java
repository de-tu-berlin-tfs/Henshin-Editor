package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

/**
 * A folder for FT rules in the tree editor. 
 */
public class FTRuleFolder extends EObjectImpl {
	/**
	 * A list of translation rules.
	 */
	private List<Rule> tRules;
	/**
	 * transformation system
	 */
	private Module module;

	public FTRuleFolder(Module module){
		this.module = module;
		tRules = new ArrayList<Rule>();
		List<Unit> allUnits = this.module.getUnits();
		for(Unit u: allUnits){
			if(!(u instanceof TGGRule)) continue;
			TGGRule r=(TGGRule) u;
			if(r.getMarkerType()==null || r.getMarkerType().equals(RuleUtil.TGG_FT_RULE))
			{
				tRules.add(r);
			}			
		}

	}
	
	
	public List<Rule> getTRules(){
		return tRules;
	}
	
}
