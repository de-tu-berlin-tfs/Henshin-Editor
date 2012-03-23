package tggeditor.editpolicies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import tggeditor.editparts.graphical.DividerEditPart;
import tggeditor.editparts.graphical.GraphEditPart;

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
			if (divider.getCastedModel().isIsSC()){
				DividerEditPart otherDivider = graphEditPart.getDividerCTpart();
				if (request.getMoveDelta().x + rect.x < 100){
					request.getMoveDelta().x = 100 - rect.x;
				} if (request.getMoveDelta().x + rect.x > otherDivider.getCastedModel().getDividerX() - 150){
					request.getMoveDelta().x = (otherDivider.getCastedModel().getDividerX() - 150) - rect.x;
				}
			} else {
				DividerEditPart otherDivider = graphEditPart.getDividerSCpart();
				if (request.getMoveDelta().x + rect.x < otherDivider.getCastedModel().getDividerX() + 150){
					request.getMoveDelta().x = otherDivider.getCastedModel().getDividerX() + 150 - rect.x;
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
