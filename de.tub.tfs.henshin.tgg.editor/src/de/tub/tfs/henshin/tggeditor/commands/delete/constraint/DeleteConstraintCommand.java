package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteConstraintCommand extends CompoundCommand {

	public DeleteConstraintCommand(Constraint c) {
		add(new SimpleDeleteEObjectCommand(c));
	}

	@Override
	public boolean canUndo() {
		return false;
	}
	
}
