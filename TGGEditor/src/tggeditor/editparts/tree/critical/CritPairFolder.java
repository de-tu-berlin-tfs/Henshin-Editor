package tggeditor.editparts.tree.critical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.TransformationSystem;

import tgg.CritPair;
import tgg.TGG;
import tggeditor.util.NodeUtil;

public class CritPairFolder extends EObjectImpl{
	
	private TransformationSystem sys;
	private List<CritPair> critPairs;
	private TGG tgg;
	
	/**
	 * List of checked rule pairs
	 */
	private List<CheckedRulePairFolder> checkedRulePairFolders;
	
	private HashMap<String, List<CritPair>> map;
	
	public CritPairFolder(TransformationSystem sys) {
		this.sys = sys;
		tgg = NodeUtil.getLayoutSystem(this.sys);
		critPairs = new ArrayList<CritPair>();
		critPairs.addAll(tgg.getCritPairs());
		
		checkedRulePairFolders = new ArrayList<CheckedRulePairFolder>();
		map = new HashMap<String, List<CritPair>>();
	}

	public boolean contains(CritPair c) {
		for (CritPair cP : critPairs) {
			if (cP == c)
				return true;
		}
		return false;
	}
	
	public List<CheckedRulePairFolder> getCheckedRulePairFolders(){
		
		for (CritPair crit : critPairs) {
			String rulesCombined = crit.getRule1().getName() + " x " + crit.getRule2().getName();
			if (!map.containsKey(rulesCombined)) {
				List<CritPair> critForThisRulePair = new ArrayList<CritPair>();
				critForThisRulePair.add(crit);
				
				map.put(rulesCombined, critForThisRulePair);
			} else {
				map.get(rulesCombined).add(crit);
			}
		}
		
		for (String name : map.keySet()) {
			CheckedRulePairFolder rulePairFolder = new CheckedRulePairFolder(sys, map.get(name));
			checkedRulePairFolders.add(rulePairFolder);
		}
		
		return checkedRulePairFolders;
	}
	
	public TGG getTGGModel(){
		return tgg;
	}
	
}
