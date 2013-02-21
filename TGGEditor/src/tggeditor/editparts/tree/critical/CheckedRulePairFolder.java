package tggeditor.editparts.tree.critical;

import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;

import tgg.CritPair;
import tgg.TGG;
import tggeditor.util.NodeUtil;

public class CheckedRulePairFolder extends EObjectImpl {

	private Module sys;
	private List<CritPair> _critPairs;
	private TGG tgg;
	
	public CheckedRulePairFolder(Module sys) {
		this.sys = sys;
		
		tgg = NodeUtil.getLayoutSystem(this.sys);
		
		_critPairs = tgg.getCritPairs();
	}

	public boolean contains(CritPair c) {
		for (CritPair cP : _critPairs) {
			if (cP == c)
				return true;
		}
		return false;
	}
	
	public List<CritPair> getCritPairs(){
		return _critPairs;
	}
	
	public TGG getTGGModel(){
		return tgg;
	}
	
}
