//$Id: DotLineIcon.java,v 1.3 2010/08/25 00:34:16 olga Exp $

package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import agg.editor.impl.Line;

public class DotLineIcon implements Icon {

	Color color;

	public DotLineIcon(Color c) {
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
		Line line = new Line(x, y + 7, x + 20, y + 7);
		line.setColor(this.color);
		line.drawDotLine(g, 1, 1);
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 20;
	}

	public int getIconHeight() {
		return 14;
	}
}
