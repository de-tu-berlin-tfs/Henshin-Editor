/**
 * RuleDirectEditPolicy.java
 *
 * Created 24.12.2011 - 18:02:38
 */
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

import de.tub.tfs.muvitor.gef.directedit.MuvitorTreeDirectEditPolicy;

/**
 * @author nam
 * 
 */
public class RuleTreeDirectEditPolicy extends MuvitorTreeDirectEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.directedit.MuvitorDirectEditPolicy#
	 * getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(DirectEditRequest edit) {
		// return super.getDirectEditCommand(edit);

		return null;
	}
}
