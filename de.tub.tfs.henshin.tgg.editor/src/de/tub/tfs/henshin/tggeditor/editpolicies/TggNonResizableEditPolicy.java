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
package de.tub.tfs.henshin.tggeditor.editpolicies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;


public class TggNonResizableEditPolicy extends NonResizableEditPolicy {	
	
	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		if (!request.getEditParts().isEmpty() && (request.getEditParts().get(0) instanceof DividerEditPart)){
			IFigure feedback = getDragSourceFeedbackFigure();

			
			PrecisionRectangle rect = new PrecisionRectangle(
					getInitialFeedbackBounds().getCopy());
			getHostFigure().translateToAbsolute(rect);
			request.getMoveDelta().y = 0;
			DividerEditPart divider = (DividerEditPart) request.getEditParts().get(0);
			GraphEditPart graphEditPart = (GraphEditPart) this.getHost().getParent();
			if (divider.isSC()){
				DividerEditPart dividerCTeditPart = graphEditPart.getDividerCTpart();
				if (request.getMoveDelta().x + rect.x < 5){
					request.getMoveDelta().x = 5 - rect.x;
				} if (request.getMoveDelta().x + rect.x > dividerCTeditPart.getCastedModel().getTripleGraph().getDividerCT_X() - 5){
					request.getMoveDelta().x = (dividerCTeditPart.getCastedModel().getTripleGraph().getDividerCT_X() - 5) - rect.x;
				}
			} else {
				DividerEditPart dividerSCeditPart = graphEditPart.getDividerSCpart();
				if (request.getMoveDelta().x + rect.x < dividerSCeditPart.getCastedModel().getTripleGraph().getDividerSC_X() + 5){
					request.getMoveDelta().x = dividerSCeditPart.getCastedModel().getTripleGraph().getDividerSC_X() + 5 - rect.x;
				}
			}
			rect.translate(request.getMoveDelta());
			rect.resize(request.getSizeDelta());

			feedback.translateToRelative(rect);
			feedback.setBounds(rect);
		}
		super.showChangeBoundsFeedback(request);
	}
}
