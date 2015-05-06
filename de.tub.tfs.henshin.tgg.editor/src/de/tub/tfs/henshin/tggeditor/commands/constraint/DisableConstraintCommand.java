package de.tub.tfs.henshin.tggeditor.commands.constraint;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;

public class DisableConstraintCommand extends Command {

	private TGG tgg;
	private Constraint constraint;
	
	public DisableConstraintCommand(TGG tgg, Constraint constraint) {
		this.tgg = tgg;
		this.constraint = constraint;
	}
	
	@Override
	public boolean canExecute() {
		return tgg != null && constraint != null;
	}

	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void execute() {
		constraint.setEnabled(false);
	}
	
}
