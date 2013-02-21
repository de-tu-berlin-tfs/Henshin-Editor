package tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;

import tggeditor.util.ModelUtil;

/**
 * A folder for rules in the tree editor.
 */
public class RuleFolder extends EObjectImpl {
	/**
	 * A list of rules.
	 */
	private List<Rule> rules;
	/**
	 * Transformationsystem.
	 */
	private Module sys;
	
	public RuleFolder(Module sys){
		this.sys = sys;
		//Rule sortieren.
		rules = new ArrayList<Rule>();
		List<Rule> allRules = ModelUtil.getRules(this.sys);
		FTRules ftRules = new FTRules(this.sys);
		for(Rule r: allRules){
			if(!ftRules.contains(r)){
				rules.add(r);
			}			
		}
	}
	
	public List<Rule> getRules(){
		return this.rules;
	}
}
