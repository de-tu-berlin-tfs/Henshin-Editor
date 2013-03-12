package tggeditor.commands.setType;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.commands.Command;

import tgg.TGG;


public class SetSourceModellCommand extends Command {
	
	private EPackage source;
	private TGG tgg;
	private EPackage oldSource;

	public SetSourceModellCommand(EPackage source, TGG tgg) {
		super();
		this.source = source;
		this.tgg = tgg;
	}

	public boolean canExecute(){
		return this.source != null && this.tgg!=null;
	}

	public void execute(){
		oldSource = tgg.getSource();
		tgg.setSource(source);
	}

	@Override
	public void undo() {
		tgg.setSource(oldSource);
		super.undo();
	}
	
	@Override
	public void redo() {
		oldSource = tgg.getSource();
		tgg.setSource(source);
	}

}
