/**
 * FigureDecorator.java
 *
 * Created 28.12.2011 - 00:10:53
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;

/**
 * @author nam
 * 
 */
public abstract class FigureDecorator<T extends Figure> extends Figure {
	/**
     * 
     */
	protected T target;

	/**
     * 
     */
	protected abstract void decorate();

	/**
     * 
     */
	public FigureDecorator(T target) {
		this.target = target;

		setLayoutManager(new XYLayout());
		setBounds(target.getBounds());

		add(target);

		decorate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
	 */
	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
		return target.getPreferredSize(wHint, hHint).getExpanded(2, 2);
	}
}
