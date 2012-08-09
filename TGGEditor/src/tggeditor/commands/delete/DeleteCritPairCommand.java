package tggeditor.commands.delete;

import org.eclipse.gef.commands.Command;

import tgg.CritPair;

public class DeleteCritPairCommand extends Command {

	private CritPair critPair;
	
	public DeleteCritPairCommand(CritPair critPair) {
		this.critPair = critPair;
	}
}
