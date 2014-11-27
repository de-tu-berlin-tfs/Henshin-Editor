/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.transformation_unit;

import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.TransformationUnitEditPart;

/**
 * The Class TransformationUnitFigure.
 */
public class TransformationUnitFigure extends RoundedRectangle {

	/** The Constant Display. */
	protected static final Device Display = null;

	/** The text. */
	private Label text;

	/** The width. */
	private int width;

	/** The sub unit figures. */
	private List<SubUnitFigure> subUnitFigures;

	/** The conditional unit part figures. */
	private List<TransformationUnitPartFigure> transformationUnitPartFigures;

	/** The port parameter figures. */
	private List<ParameterFigure> portParameterFigures;

	/** The line1. */
	private Polyline line1;

	/** The line2. */
	private Polyline line2;

	/** The line3. */
	private Polyline line3;

	/** The line4. */
	private Polyline line4;

	/** The check box container. */
	private Rectangle checkBoxContainer;

	/** The gradient color1. */
	protected Color gradientColor1 = ColorConstants.green;

	/** The gradient color2. */
	protected Color gradientColor2 = ColorConstants.white;

	/**
	 * Instantiates a new transformation unit figure.
	 * 
	 * @param editPart
	 *            the edit part
	 * @param name
	 *            the name
	 * @param width
	 *            the width
	 * @param image
	 *            the image
	 */
	public TransformationUnitFigure(
			final TransformationUnitEditPart<?> editPart, String name,
			int width, Image image) {
		super();
		this.subUnitFigures = new Vector<SubUnitFigure>();
		this.transformationUnitPartFigures = new Vector<TransformationUnitPartFigure>();
		this.portParameterFigures = new Vector<ParameterFigure>();

		text = new Label(name);
		text.setFont(new Font(Display, "Arial", 11, SWT.BOLD | SWT.ITALIC));
		text.setTextAlignment(PositionConstants.LEFT);
		text.setForegroundColor(ColorConstants.black);

		setLayoutManager(new XYLayout());
		add(new ImageFigure(image), new Rectangle(3, 3, 30, 30));
		add(text, new Rectangle(50, 5, width - 170, 25));
		checkBoxContainer = new Rectangle(width - 85, 3, 80, 15);
		line1 = new Polyline();
		line1.setEndpoints(new Point(100, 44), new Point(100, 44));
		line2 = new Polyline();
		line2.setEndpoints(new Point(0, 44), new Point(width, 44));

		line3 = new Polyline();
		line3.setEndpoints(new Point(width - 8, 44), new Point(width - 8, 44));
		line4 = new Polyline();
		line4.setEndpoints(new Point(100, 44), new Point(width - 8, 44));

		add(line1);
		add(line2);
		add(line3);
		add(line4);

		setSize(width, 108);

	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		text.setText(name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		int x = Math.round(getLocation().x + getSize().width / 2.0f);
		graphics.setBackgroundPattern(new Pattern(Display, x, getLocation().y,
				x, getLocation().y + getSize().height, gradientColor1,
				gradientColor2));

		super.paint(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setBackgroundColor(org.eclipse.swt.graphics
	 * .Color)
	 */
	@Override
	public void setBackgroundColor(Color bg) {
		gradientColor1 = bg;
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#repaint()
	 */
	@Override
	public void repaint() {
		int x = getLocation().x;
		int y = getLocation().y;
		int height = 108;
		if (subUnitFigures.size() > 0) {
			height = 52 * subUnitFigures.size() + 56;
		}
		if (portParameterFigures.size() > 0) {
			int count = portParameterFigures.size();
			height = Math.max(height, 42 * count + 56);
		}
		if (transformationUnitPartFigures.size() > 0) {
			int tempHeight = 0;
			for (TransformationUnitPartFigure tFigure : transformationUnitPartFigures) {
				tempHeight += tFigure.calculateHight() + 2;
			}

			if (subUnitFigures.isEmpty()) {
				height = Math.max(height, tempHeight + 56);
			} else { // This differentiation is necessary because amalgamation
				// unit figure
				// contains sub unit figure and transformation unit part
				// figure
				height += tempHeight + 10;
			}
		}

		setSize(width, height);
		line1.setEnd(new Point(100, height - 4));
		line2.setEndpoints(new Point(0, 44), new Point(width, 44));
		line3.setEndpoints(new Point(width - 8, 44), new Point(width - 8,
				height - 4));
		line4.setEndpoints(new Point(100, height - 4), new Point(width - 8,
				height - 4));

		for (int i = 0; i < subUnitFigures.size(); i++) {
			IFigure figure = subUnitFigures.get(i);
			figure.setSize(width - 130, 50);
			figure.setLocation(new Point(x + 115, y + 50 * (i + 1) + 2 * (i)));
		}
		int tempY = 50;
		for (int i = 0; i < transformationUnitPartFigures.size(); i++) {
			TransformationUnitPartFigure figure = transformationUnitPartFigures
					.get(i);
			figure.setSize(width - 130, figure.calculateHight());
			if (subUnitFigures.isEmpty()) {
				figure.setLocation(new Point(x + 115, y + tempY));
			} else { // This differentiation is necessary because amalgamation
				// unit figure
				// contains sub unit figure and transformation unit part
				// figure
				final SubUnitFigure lastSubUnitFigure = subUnitFigures
						.get(subUnitFigures.size() - 1);
				int lastSubUnitY = lastSubUnitFigure.getLocation().y;
				figure.setLocation(new Point(x + 115, y + tempY + lastSubUnitY
						+ 10));
			}
			tempY += figure.calculateHight() + 2;
		}

		for (int i = 0; i < portParameterFigures.size(); i++) {
			IFigure figure = portParameterFigures.get(i);
			figure.setLocation(new Point(x + 5, y + 50 + 40 * i + 2 * i));
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
		this.width = w;
		super.setSize(w, h);
		checkBoxContainer.setLocation(width - 85, 3);
	}

	/**
	 * Gets the name label text bounds.
	 * 
	 * @return the name label text bounds
	 */
	public Rectangle getNameLabelTextBounds() {
		return text.getBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setLocation(org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure,
	 * java.lang.Object, int)
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		if (figure instanceof SubUnitFigure) {
			subUnitFigures.add(index, (SubUnitFigure) figure);
		}
		if (figure instanceof TransformationUnitPartFigure) {
			if (index > transformationUnitPartFigures.size()) {
				transformationUnitPartFigures
						.add((TransformationUnitPartFigure) figure);
			} else {
				transformationUnitPartFigures.add(index,
						(TransformationUnitPartFigure) figure);
			}
		}
		if (figure instanceof ParameterFigure) {
			portParameterFigures.add((ParameterFigure) figure);
		}

		super.add(figure, constraint, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#remove(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public void remove(IFigure figure) {
		if (figure instanceof SubUnitFigure) {
			subUnitFigures.remove(figure);
		}
		if (figure instanceof TransformationUnitPartFigure) {
			transformationUnitPartFigures.remove(figure);
		}
		if (figure instanceof ParameterFigure) {
			portParameterFigures.remove(figure);
		}
		super.remove(figure);
	}
}
