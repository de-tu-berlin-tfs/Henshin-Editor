/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.figures;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
//import org.eclipse.draw2d.ShortestPathConnectionRouter;

/**
 * The Class EdgeConnectionRouter.
 */
public class EdgeConnectionRouter extends AbstractRouter {
	

	
	/** The next router. */
	private ConnectionRouter nextRouter;
	
	/**
	 * Instantiates a new edge connection router.
	 *
	 * @param figure the figure
	 */
	public EdgeConnectionRouter(IFigure figure) {
		FanRouter fRouter = new FanRouter();
		TGGShortestPathConnectionRouter router = new TGGShortestPathConnectionRouter(figure);
		fRouter.setSeparation(20);
		router.setSpacing(20);
		fRouter.setNextRouter(router);
		setNextRouter(fRouter);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.ConnectionRouter#route(org.eclipse.draw2d.Connection)
	 */
	@Override
	public void route(Connection connection) {
		if (connection.getSourceAnchor().getOwner()!=connection.getTargetAnchor().getOwner()){
			nextRouter.route(connection);
		}
		else{
			PointList points = connection.getPoints();
			points.removeAllPoints();
			Rectangle rectangle=connection.getSourceAnchor().getOwner().getBounds();
			int x=rectangle.x+rectangle.width;
			int y= rectangle.y;
			points.addPoint(x,y);
			points.addPoint(x, y-20);
			points.addPoint(x+30, y-20);
			points.addPoint(x+30, y+10);
			points.addPoint((new ChopboxAnchor(connection.getSourceAnchor().getOwner())).getLocation(new Point(x+30,y+10)));
			connection.setPoints(points);
		}
	}
	
	/**
	 * Sets the next router.
	 *
	 * @param nextRouter the new next router
	 */
	public synchronized void setNextRouter(ConnectionRouter nextRouter) {
		this.nextRouter = nextRouter;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.AbstractRouter#getConstraint(org.eclipse.draw2d.Connection)
	 */
	@Override
	public Object getConstraint(Connection connection) {
		return nextRouter.getConstraint(connection);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.AbstractRouter#invalidate(org.eclipse.draw2d.Connection)
	 */
	@Override
	public void invalidate(Connection connection) {
		nextRouter.invalidate(connection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.AbstractRouter#remove(org.eclipse.draw2d.Connection)
	 */
	@Override
	public void remove(Connection connection) {
		nextRouter.remove(connection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.AbstractRouter#setConstraint(org.eclipse.draw2d.Connection, java.lang.Object)
	 */
	@Override
	public void setConstraint(Connection connection, Object constraint) {
		nextRouter.setConstraint(connection, constraint);
	}
}
