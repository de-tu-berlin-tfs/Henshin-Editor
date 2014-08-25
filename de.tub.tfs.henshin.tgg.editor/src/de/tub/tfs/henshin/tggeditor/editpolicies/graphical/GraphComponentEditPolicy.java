/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editpolicies.graphical;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteGraphInstanceCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteNACCommand;


public class GraphComponentEditPolicy extends ComponentEditPolicy implements
		EditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if(((EObject) getHost().getModel()).eContainer() instanceof NestedCondition){
			return new DeleteNACCommand((Graph)getHost().getModel());
		}
		
		return new DeleteGraphInstanceCommand((Graph) getHost().getModel());
	}

}
