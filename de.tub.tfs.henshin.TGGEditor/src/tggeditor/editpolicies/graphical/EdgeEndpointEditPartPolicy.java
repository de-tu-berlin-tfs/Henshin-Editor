package tggeditor.editpolicies.graphical;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * The Class EdgeEndpointEditPartPolicy.
 */
public class EdgeEndpointEditPartPolicy extends ConnectionEndpointEditPolicy  {
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#addSelectionHandles()
	 */
	@Override
	protected void addSelectionHandles() {
		super.addSelectionHandles();
		((PolylineConnection) getConnection()).setLineWidth(2);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#removeSelectionHandles()
	 */
	@Override
	protected void removeSelectionHandles() {
		super.removeSelectionHandles();
		((PolylineConnection) getConnection()).setLineWidth(1);
	}

}
