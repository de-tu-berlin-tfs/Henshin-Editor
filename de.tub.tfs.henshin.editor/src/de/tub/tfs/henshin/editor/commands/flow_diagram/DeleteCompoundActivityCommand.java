/**
 * DeleteCompoundActivityCommand.java
 *
 * Created 28.12.2011 - 03:16:43
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;

/**
 * @author nam
 * 
 */
public class DeleteCompoundActivityCommand extends CompoundCommand {
	/**
     * 
     */
	public DeleteCompoundActivityCommand(CompoundActivity model) {
		super();

		for (Activity child : model.getChildren()) {
			if (child instanceof CompoundActivity) {
				add(new DeleteCompoundActivityCommand((CompoundActivity) child));
			} else {
				add(new DeleteFlowElementCommand(child));
			}
		}

		add(new DeleteFlowElementCommand(model));
	}
}
