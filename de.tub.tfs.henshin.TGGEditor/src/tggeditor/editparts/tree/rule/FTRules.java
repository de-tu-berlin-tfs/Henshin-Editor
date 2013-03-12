package tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;

import tgg.TGG;
import tgg.TRule;
import tggeditor.util.NodeUtil;

/**
 * A folder for FT rules in the tree editor. 
 */
public class FTRules extends EObjectImpl {
	private Module sys;
	private List<TRule> tRules;
	private TGG tgg;

	public FTRules(Module sys){
		this.sys = sys;
		tRules = new ArrayList<TRule>();
		tgg = NodeUtil.getLayoutSystem(this.sys);		
		tRules.addAll(tgg.getTRules());
//		TreeIterator<EObject> iter = tgg.eAllContents();
//		while(iter.hasNext()){
//			EObject o = iter.next();
//			if(o instanceof TRule){
//				tRules.add((TRule) o);
//			}
//		}		
	}
	
	public boolean contains(Rule r) {
		for (TRule tr : tRules) {
			if (tr.getRule() == r)
				return true;
		}
		return false;
	}
	
	public List<TRule> getTRules(){
		return tRules;
	}
	
	public TGG getTGGModel(){
		return tgg;
	}
}
