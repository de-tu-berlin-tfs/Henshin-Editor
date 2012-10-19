package tggeditor.editparts.tree.graphical;

import java.util.ArrayList;
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
	
	public CritPairFolder(TransformationSystem sys) {
		this.sys = sys;
		tgg = NodeUtil.getLayoutSystem(this.sys);
		critPairs = new ArrayList<CritPair>();
		critPairs.addAll(tgg.getCritPairs());
	}

	public boolean contains(CritPair c) {
		for (CritPair cP : critPairs) {
			if (cP == c)
				return true;
		}
		return false;
	}
	
	public List<CritPair> getCritPairs(){
		return critPairs;
	}
	
	public TGG getTGGModel(){
		return tgg;
	}
	
}
