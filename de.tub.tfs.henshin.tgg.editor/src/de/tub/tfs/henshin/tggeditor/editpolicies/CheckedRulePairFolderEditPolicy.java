/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteCritPairCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CheckedRulePairFolder;


public class CheckedRulePairFolderEditPolicy extends ComponentEditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		
		if (getHost().getModel() instanceof CheckedRulePairFolder) {
			
			CompoundCommand command = new CompoundCommand();
			
			for (CritPair crit : ((CheckedRulePairFolder)getHost().getModel()).getCritPairs()) {
				command.add(new DeleteCritPairCommand(crit));
			}
			
			return command;
		}
		
		return null;
	}

}
