/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
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
