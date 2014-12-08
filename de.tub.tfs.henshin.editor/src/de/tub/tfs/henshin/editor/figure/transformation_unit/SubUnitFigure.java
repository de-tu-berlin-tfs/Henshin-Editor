/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.transformation_unit;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.SubUnitEditPart;

/**
 * The Class SubUnitFigure.
 */
public class SubUnitFigure extends RoundedRectangle {

	/** The Constant Display. */
	protected static final Device Display = null;

	/** The text. */
	private Label text;

	/** The text tectangle. */
	private Rectangle textTectangle;

	/** The aktivated rectangle. */
	private Rectangle aktivatedRectangle;

	/** The gradient color1. */
	protected Color gradientColor1 = ColorConstants.green;

	/** The gradient color2. */
	protected Color gradientColor2 = ColorConstants.white;

	/**
	 * Instantiates a new sub unit figure.
	 * 
	 * @param sEditPart
	 *            the s edit part
	 * @param name
	 *            the name
	 * @param width
	 *            the width
	 * @param image
	 *            the image
	 */
	public SubUnitFigure(final SubUnitEditPart<?> sEditPart, String name,
			int width, Image image) {
		super();
		Color[] shadow = { ColorConstants.black, ColorConstants.black };
		Color[] highlight = { ColorConstants.gray, ColorConstants.gray };
		setBorder(new SchemeBorder(new SchemeBorder.Scheme(highlight, shadow)));

		text = new Label(name);
		text.setFont(new Font(Display, "Arial", 11, SWT.BOLD | SWT.ITALIC));
		text.setForegroundColor(ColorConstants.black);

		setLayoutManager(new XYLayout());
		add(new ImageFigure(image), new Rectangle(3, 3, 30, 30));
		textTectangle = new Rectangle(50, 5, width - 170, 25);
		add(text, textTectangle);
		aktivatedRectangle = new Rectangle(width - 85, 3, 80, 15);
		setSize(width, 50);

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
	 * @see org.eclipse.draw2d.Figure#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		textTectangle.setSize(w - 100, h);
		aktivatedRectangle.setLocation(w - 85, 3);
		super.setSize(w, h);
	}

	/**
	 * Gets the name label text bounds.
	 * 
	 * @return the name label text bounds
	 */
	public Rectangle getNameLabelTextBounds() {
		return text.getBounds();
	}
}
