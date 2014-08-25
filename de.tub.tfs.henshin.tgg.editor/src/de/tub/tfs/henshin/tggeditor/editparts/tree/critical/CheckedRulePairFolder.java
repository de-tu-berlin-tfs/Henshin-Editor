package de.tub.tfs.henshin.tggeditor.editparts.tree.critical;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


public class CheckedRulePairFolder extends EObjectImpl {

	private Module sys;
	private List<CritPair> _critPairs;
	private TGG tgg;
	
	public CheckedRulePairFolder(Module sys) {
		this.sys = sys;
		
		tgg = GraphicalNodeUtil.getLayoutSystem(this.sys);
		if (tgg != null){
			_critPairs = tgg.getCritPairs();
		}
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

	public void update() {
		_critPairs = tgg.getCritPairs();	
		eNotify(new ENotificationImpl(this, Notification.ADD, 0, null, null));
	}
	
}
