/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.graph;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.swt.graphics.Color;

/**
 * @author angel
 * 
 */
public class MultiNodeFigure extends NodeFigure {

	private final SimpleNodeFigure frontFigure;

	private final RectangleFigure backFigure;

	/**
	 * 
	 */
	public MultiNodeFigure(Node node, int width, MouseListener mouseListener) {
		super();
		this.width = width;

		frontFigure = new SimpleNodeFigure(node, width, mouseListener);
		backFigure = new RectangleFigure();

		add(backFigure);
		add(frontFigure);

		setSize(width + 5, height + 5);

		setLayoutManager(new XYLayout());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure,
	 * java.lang.Object, int)
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		if (figure == frontFigure || figure == backFigure) {
			super.add(figure, constraint, index);
		} else {
			frontFigure.add(figure, constraint, index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#remove(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public void remove(IFigure figure) {
		if (figure == frontFigure || figure == backFigure) {
			super.remove(figure);
		} else {
			frontFigure.remove(figure);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.graph.NodeFigure#isHide()
	 */
	@Override
	public boolean isHide() {
		return frontFigure.isHide();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.graph.NodeFigure#setHide(boolean)
	 */
	@Override
	public void setHide(boolean hide) {
		frontFigure.setHide(hide);
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.graph.NodeFigure#getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return frontFigure.getValueLabelTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#repaint()
	 */
	@Override
	public void repaint() {
		if (frontFigure != null) {
			if (frontFigure.isHide()) {
				frontFigure.setSize(width, 25);
			} else {
				frontFigure.setSize(width, height);
			}

			frontFigure.setLocation(new Point(this.getLocation().x, this
					.getLocation().y + 5));
			frontFigure.repaint();
		}

		if (backFigure != null) {
			backFigure.setSize(frontFigure.getSize().width,
					frontFigure.getSize().height);
			backFigure.setLocation(new Point(this.getLocation().x + 5, this
					.getLocation().y));
		}

		super.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		if (isHide()) {
			super.setSize(w + 5, 30);
		} else {
			super.setSize(w + 5, h + 5);
		}

		this.width = w;
		this.height = h;
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.graph.NodeFigure#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		frontFigure.setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.figure.graph.NodeFigure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		if (frontFigure != null) {
			frontFigure.paint(graphics);
		}

		super.setAlpha(0);
		super.paint(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.figure.graph.NodeFigure#setBackgroundColor(org.eclipse.
	 * swt.graphics.Color)
	 */
	@Override
	public void setBackgroundColor(Color bg) {
		if (frontFigure != null && backFigure != null) {
			frontFigure.setBackgroundColor(bg);
			backFigure.setBackgroundColor(bg);
		}
	}
}
