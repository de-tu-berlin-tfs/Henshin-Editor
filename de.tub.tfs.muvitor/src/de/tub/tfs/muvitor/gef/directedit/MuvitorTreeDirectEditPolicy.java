package de.tub.tfs.muvitor.gef.directedit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

/**
 * The direct edit policy for {@link IDirectEditPart}s. Creates a
 * {@link SetEObjectFeatureValueCommand}.
 * 
 * @author Tony Modica
 */
public class MuvitorTreeDirectEditPolicy extends DirectEditPolicy {
	
	/*
	 * (non-Javadoc)
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		// In MuvitorDirectEditManager we use the request's direct
		// edit feature to transmit the new name! We do this because the cell
		// editor will already be disposed at this time.
		final IDirectEditPart directEditPart = (IDirectEditPart) getHost();
		final String newValue = ((String) request.getDirectEditFeature()).trim();
		return new SetEObjectFeatureValueCommand((EObject) getHost().getModel(), newValue,
				directEditPart.getDirectEditFeatureID());
	}
	
	/*
	 * (non-Javadoc)
	 * @see DirectEditPolicy#showCurrentEditValue(DirecEditRequest)
	 */
	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
	}
	
}
