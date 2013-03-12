package tggeditor.commands.setType;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.commands.Command;

import tgg.TGG;

public class SetTargetModellCommand extends Command {
	EPackage target;
	TGG tgg;
	EPackage oldTarget;
	

	public SetTargetModellCommand(EPackage target, TGG tgg) {
		this.target = target;
		this.tgg = tgg;
	}

	@Override
	public boolean canExecute() {
		return this.target != null && this.tgg != null;
	}

	@Override
	public void execute() {
		oldTarget = tgg.getTarget();
		tgg.setTarget(target);
	}

	@Override
	public void undo() {
		tgg.setTarget(oldTarget);
		super.undo();
	}
	
	@Override
	public void redo() {
		oldTarget = tgg.getTarget();
		tgg.setTarget(target);
	}


	
	
	
	

}
