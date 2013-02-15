/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.transSys.MoveEObjectCommand;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.RuleAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * @author Johann
 * 
 */
public class TreeContainerEditPolicy extends
		org.eclipse.gef.editpolicies.TreeContainerEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getAddCommand(org
	 * .eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Command getAddCommand(ChangeBoundsRequest request) {
		EObject host = (EObject) getHost().getModel();
		List<?> editparts = request.getEditParts();
		if (editparts.size() == 1) {
			
			if (editparts.get(0) instanceof RuleAsSubUnitTreeEditPart) {
				EList<EObject> list = null;
				Unit tUnit = (Unit) getHost()
						.getParent().getModel();
				EStructuralFeature feature = TransformationUnitUtil
						.getSubUnitsFeature(tUnit);
				if (feature != null) {
					if (feature.isMany()) {
						list = (EList<EObject>) tUnit.eGet(feature);
						return new MoveEObjectCommand(list,
								list.indexOf(((EditPart) editparts.get(0))
										.getModel()), list.indexOf(getHost()
										.getModel()));
					}
				}
			}
			
			EObject child = (EObject) ((EditPart) editparts.get(0)).getModel();
			if (host.eContainer() == child.eContainer()
					&& host.eContainingFeature() == child.eContainingFeature()) {
				EList<EObject> list = (EList<EObject>) host.eContainer().eGet(
						host.eContainingFeature());
				return new MoveEObjectCommand(list, list.indexOf(child),
						list.indexOf(host));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getCreateCommand
	 * (org.eclipse.gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getMoveChildrenCommand
	 * (org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@Override
	protected Command getMoveChildrenCommand(ChangeBoundsRequest request) {
		return null;
	}

}
