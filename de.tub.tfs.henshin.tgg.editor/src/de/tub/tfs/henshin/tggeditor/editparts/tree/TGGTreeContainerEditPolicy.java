/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tggeditor.commands.move.MoveEObjectCommand;


/**
 * @author Johann
 *
 */
@SuppressWarnings("deprecation")
public class TGGTreeContainerEditPolicy extends TreeContainerEditPolicy {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getAddCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Command getAddCommand(ChangeBoundsRequest request) {
		EObject host=(EObject) getHost().getModel();
		List<?> editparts = request.getEditParts();
		if (editparts.size() == 1) {
			EObject child = (EObject) ((EditPart)editparts.get(0)).getModel();
			if (host.eContainer()==child.eContainer() && host.eContainingFeature()==child.eContainingFeature()){
				EList<EObject> list = (EList<EObject>) host.eContainer().eGet(host.eContainingFeature());
				return new MoveEObjectCommand(list, list.indexOf(child), list.indexOf(host));
			}
		}
		return null;
	}
	
	
	/**
	 * Returns a Command for moving the children within the container.
	 * 
	 * @param request
	 *            the Request to move
	 * @return Command <code>null</code> or a Command to perform the move
	 */
	@Override
	protected Command getMoveChildrenCommand(ChangeBoundsRequest request) {
		EObject host=(EObject) getHost().getModel();
		List<?> editparts = request.getEditParts();
		if (editparts.size() == 1) {
			EObject child = (EObject) ((EditPart)editparts.get(0)).getModel();
			if (host.eContainer()==child.eContainer() && host.eContainingFeature()==child.eContainingFeature()){
				EList<EObject> list = (EList<EObject>) host.eContainer().eGet(host.eContainingFeature());
				return new MoveEObjectCommand(list, list.indexOf(child), list.indexOf(host));
			}
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}



}
