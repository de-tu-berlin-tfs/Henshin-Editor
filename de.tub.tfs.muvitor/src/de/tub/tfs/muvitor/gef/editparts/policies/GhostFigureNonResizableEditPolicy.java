package de.tub.tfs.muvitor.gef.editparts.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

/**
 * @author Tony Modica
 * 
 */
public class GhostFigureNonResizableEditPolicy extends NonResizableEditPolicy {
	
	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		if (getHostFigure() instanceof IGhostFigureProvider) {
			final IGhostFigureProvider hostFigure = (IGhostFigureProvider) getHostFigure();
			final IFigure ghostFigure = hostFigure.getGhostFigure();
			addFeedback(ghostFigure);
			return ghostFigure;
		}
		return null;
	}
}
