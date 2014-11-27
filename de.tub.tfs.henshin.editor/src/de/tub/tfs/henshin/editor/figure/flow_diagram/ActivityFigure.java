/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * A {@link Figure} for {@link Activity} flow control elements.
 * 
 * @author nam
 * 
 */
public class ActivityFigure extends FlowElementFigure {
	private static final String EMPTY_TXT = "<empty>";

	/**
	 * The gradient color1.
	 */
	protected Color gradientColorStart = ColorConstants.lightGray;

	/**
	 * The gradient color2.
	 */
	protected Color gradientColorEnd = ColorConstants.white;

	/**
	 * The label containing the mapping number e.g. '[1]'
	 */
	protected Label mappingLabel;

	protected Label nameLabel;

	protected Label contentIcon;

	/**
     * 
     */
	public ActivityFigure() {
		super();

		setLayoutManager(new XYLayout());
		setCornerDimensions(new Dimension(30, 30));
		setLineWidth(2);
		setForegroundColor(ColorConstants.gray);

		mappingLabel = new Label();
		nameLabel = new Label();
		contentIcon = new Label();

		nameLabel.setForegroundColor(ColorConstants.black);

		mappingLabel.setForegroundColor(ColorConstants.black);
		mappingLabel.setFont(SWTResourceManager.getFont("Sans", 9, SWT.BOLD));

		add(mappingLabel);
		add(contentIcon);
		add(nameLabel);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		if (name == null) {
			name = EMPTY_TXT;
			setToolTip("Activity with no content.");

			contentIcon.setIcon(null);
		}

		nameLabel.setText(name);

		setCompact(nameLabel);
	}

	public void setContentIcon(Image icon) {
		contentIcon.setIcon(icon);
	}

	/**
	 * @param mappingId
	 */
	public void setMapping(int mappingId) {
		mappingLabel.setText("[" + mappingId + "]");

		if (mappingId < 0) {
			mappingLabel.setText("");
		}

		setCompact(mappingLabel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.setAntialias(SWT.ON);

		int x = Math.round(getLocation().x + getSize().width / 2.0f);

		graphics.setBackgroundPattern(new Pattern(null, x, getLocation().y, x,
				getLocation().y + getSize().height, gradientColorStart,
				gradientColorEnd));

		graphics.setAlpha(255);

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
		gradientColorStart = bg;

		repaint();
	}

	/**
     * 
     */
	protected void setCompact(Label lbl) {
		int maxLength = 15;

		if (isCompactMode()) {
			maxLength = 5;
		}

		int head = maxLength / 5;
		int tail = maxLength - head - 3;
		int l = lbl.getText().length();

		if (l > maxLength) {
			lbl.setText(lbl.getText().substring(0, head) + "..."
					+ lbl.getText().substring(l - tail, l));
		}

	}
}
