/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.tools;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkUnspecifiedCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkUnspecifiedEdgeCommand;


/**
 * The MarkerUnspecifiedCreationTool can mark or demark nodes in a rule with [tr=?].
 * @see MarkUnspecifiedCommand
 */
public class MarkerUnspecifiedCreationTool extends CreationTool {

	/**
	 * the constructor
	 */
	public MarkerUnspecifiedCreationTool() {
		super();
	}

	/**
	 * the constructor
	 * @param aFactory
	 */
	public MarkerUnspecifiedCreationTool(CreationFactory aFactory) {
		super(aFactory);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
	 */
	@Override
	protected void handleFinished() {
		reactivate();
//		super.handleFinished();
	}

	@Override
	protected void executeCommand(Command command) {
		if (command instanceof MarkUnspecifiedCommand ||
				command instanceof MarkUnspecifiedEdgeCommand) {
			super.executeCommand(command);
		}
	}
	
}
