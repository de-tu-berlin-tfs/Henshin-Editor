package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteAttributeCommand extends CompoundCommand {
	/**
	 * Adds an attribute to a DeleteCommand.
	 * 
	 * @param attribute the attribute
	 */
	public DeleteAttributeCommand(Attribute attribute) {
		add(new SimpleDeleteEObjectCommand (attribute));
	}
}
