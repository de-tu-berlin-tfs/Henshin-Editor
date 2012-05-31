//$Id: ShortDashLineIcon.java,v 1.3 2010/09/23 08:20:21 olga Exp $

package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import agg.editor.impl.Line;

public class ShortDashLineIcon implements Icon {

	Color color;

	public ShortDashLineIcon(Color c) {
		this.color = c;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return this.color;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Color oldColor = g.getColor();
		Line line = new Line(x, y + getIconHeight() / 2, x + getIconWidth(), y
				+ getIconHeight() / 2);
		line.setColor(this.color);
		line.drawDotLine(g, 2, 2);
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 20;
	}

	public int getIconHeight() {
		return 14;
	}
}
