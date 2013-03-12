package tggeditor.commands.setType;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.commands.Command;

import tgg.TGG;

public class SetCorrModellCommand extends Command {
	
	private EPackage corr;
	private TGG tgg;
	private EPackage oldCorr;

	public SetCorrModellCommand(EPackage corr, TGG tgg) {
		super();
		this.corr = corr;
		this.tgg = tgg;
	}

	public boolean canExecute(){
		return this.corr != null && this.tgg != null;
	}

	public void execute(){
		oldCorr = tgg.getCorresp();
		tgg.setCorresp(corr);
	}

	@Override
	public void undo() {
		tgg.setCorresp(oldCorr);
		super.undo();
	}
	
	@Override
	public void redo() {
		oldCorr = tgg.getCorresp();
		tgg.setCorresp(corr);
	}
}
