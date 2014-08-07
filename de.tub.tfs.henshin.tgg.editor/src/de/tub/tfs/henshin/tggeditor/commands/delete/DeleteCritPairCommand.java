package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteCritPairCommand extends CompoundCommand {

	private CritPair _critPair;
	private TGG _tgg;
	
	public DeleteCritPairCommand(CritPair critPair) {
		this._critPair = critPair;
	
//		_trasfo = critPair.getRule1().getTransformationSystem();
		
		add(new SimpleDeleteEObjectCommand(critPair.getRule1()));
		add(new SimpleDeleteEObjectCommand(critPair.getRule2()));
		
		add(new SimpleDeleteEObjectCommand(critPair.getOverlapping()));
		
		add(new SimpleDeleteEObjectCommand(critPair));
	}
	
	public void execute() {
		
		// remove CritPair from tgg
		_tgg.getCritPairs().remove(_critPair);		
		// remove overlapping from instances of transformation system
		_tgg.getInstances().remove(_critPair.getOverlapping());
		
		
		super.execute();
	}
}
