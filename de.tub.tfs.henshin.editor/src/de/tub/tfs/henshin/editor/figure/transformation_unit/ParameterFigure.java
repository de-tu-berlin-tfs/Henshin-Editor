/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.transformation_unit;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * The Class ParameterFigure.
 */
public class ParameterFigure extends RectangleFigure {

	/** The text. */
	private final Label text;

	/**
	 * Instantiates a new port figure.
	 * 
	 * @param name
	 *            the name
	 * @param image
	 *            the image
	 */
	public ParameterFigure(String name, Image image) {
		super();
		Color[] shadow = { ColorConstants.black, ColorConstants.black };
		Color[] highlight = { ColorConstants.gray, ColorConstants.gray };
		setBorder(new SchemeBorder(new SchemeBorder.Scheme(highlight, shadow)));
		text = new Label(name);
		text.setTextAlignment(PositionConstants.LEFT);
		text.setForegroundColor(ColorConstants.black);
		setBackgroundColor(ColorConstants.lightGray);
		setLayoutManager(new XYLayout());
		add(new ImageFigure(image), new Rectangle(33, 0, 18, 18));
		add(text, new Rectangle(0, 18, 85, 18));
		setSize(85, 40);

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

	/**
	 * Gets the name label text bounds.
	 * 
	 * @return the name label text bounds
	 */
	public Rectangle getNameLabelTextBounds() {
		return text.getBounds();
	}

}
