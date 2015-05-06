package de.tub.tfs.henshin.tggeditor.commands.constraint;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;

public class EnableConstraintsCommand extends Command {

	private TGG tgg;
	
	public EnableConstraintsCommand(TGG tgg) {
		this.tgg = tgg;
	}
	
	@Override
	public boolean canExecute() {
		return tgg != null;
	}

	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void execute() {
		for (Constraint c : tgg.getConstraints()) {
			c.setEnabled(true);
		}
	}
	
}
