/**
 * ParameterFigureConnectionAnchor.java
 *
 * Created 26.12.2011 - 16:20:56
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * @author nam
 * 
 */
public class ParameterFigureConnectionAnchor extends AbstractConnectionAnchor {

	/**
	 * @param owner
	 */
	public ParameterFigureConnectionAnchor(IFigure owner) {
		super(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry
	 * .Point)
	 */
	@Override
	public Point getLocation(Point reference) {
		return getOwner().getBounds().getRight();
	}

}
