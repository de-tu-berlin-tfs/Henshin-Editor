//$Id: SolidLineIcon.java,v 1.3 2010/09/23 08:20:21 olga Exp $

package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import agg.editor.impl.Line;

public class SolidLineIcon implements Icon {

	Color color;

	public SolidLineIcon(Color c) {
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
		line.drawSolidLine(g);
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 20;
	}

	public int getIconHeight() {
		return 14;
	}
}
