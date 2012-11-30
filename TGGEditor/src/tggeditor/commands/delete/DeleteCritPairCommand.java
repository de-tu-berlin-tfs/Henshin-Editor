package tggeditor.commands.delete;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.CritPair;
import tgg.TGG;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteCritPairCommand extends CompoundCommand {

	private CritPair _critPair;
	private TGG _tgg;
//	private TransformationSystem _trafo;
	
	public DeleteCritPairCommand(CritPair critPair) {
		this._critPair = critPair;
	
//		_trasfo = critPair.getRule1().getTransformationSystem();
		
		add(new DeleteGraphCommand(critPair.getOverlapping()));
		
		add(new SimpleDeleteEObjectCommand(critPair));
	}
	
	public void execute() {
		
		// CritPair von tgg entfernen
		_tgg.getCritPairs().remove(_critPair);		
		//overlapping aus Instanzen des Trafo entfernen
//		_trafo.getInstances().remove(_critPair.getOverlapping());
		
		
		super.execute();
	}
}
