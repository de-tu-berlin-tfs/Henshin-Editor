/**
 * CompoundActivityHighlightEditPolicy.java
 *
 * Created 09.01.2012 - 15:28:55
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.compound_activity;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;

import de.tub.tfs.henshin.editor.figure.flow_diagram.CompoundActivityFigure;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author nam
 * 
 */
public class CompoundActivityHighlightEditPolicy extends GraphicalEditPolicy {

	private static final int HIGHLIGHT_W_EXPAND = 20;

	private static final int HIGHLIGHT_H_EXPAND = 10;

	private boolean highlighted = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#showTargetFeedback(org
	 * .eclipse.gef.Request)
	 */
	@Override
	public void showTargetFeedback(Request request) {
		if (request.getType().equals(RequestConstants.REQ_ADD) && !highlighted) {
			IFigure fig = getHostFigure();

			if (fig instanceof CompoundActivityFigure) {
				CompoundActivityFigure f = (CompoundActivityFigure) fig;

				f.setSize(f.getSize().getExpanded(HIGHLIGHT_W_EXPAND,
						HIGHLIGHT_H_EXPAND));

				f.setTxtColor(SWTResourceManager.getColor(230, 230, 250));

				highlighted = true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseTargetFeedback(org
	 * .eclipse.gef.Request)
	 */
	@Override
	public void eraseTargetFeedback(Request request) {
		if (highlighted) {
			IFigure fig = getHostFigure();

			if (fig instanceof CompoundActivityFigure) {
				CompoundActivityFigure f = (CompoundActivityFigure) fig;

				f.setSize(f.getSize().getExpanded(-HIGHLIGHT_W_EXPAND,
						-HIGHLIGHT_H_EXPAND));

				f.setTxtColor(ColorConstants.gray);

				highlighted = false;
			}
		}
	}
}
