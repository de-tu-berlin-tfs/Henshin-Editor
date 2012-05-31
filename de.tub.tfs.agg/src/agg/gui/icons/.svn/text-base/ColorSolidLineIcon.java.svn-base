//$Id: ColorSolidLineIcon.java,v 1.4 2010/08/23 07:33:25 olga Exp $

package agg.gui.icons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

import agg.editor.impl.Line;

public class ColorSolidLineIcon implements Icon {

	Color color;
	boolean filled;
	
	public ColorSolidLineIcon(Color c) {
		this.color = c;
	}

	public ColorSolidLineIcon(Color c, boolean bold) {
		this.color = c;
		this.filled = bold;
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
		
		if (this.filled) {
			((Graphics2D) g).setStroke(new BasicStroke(3.0f));
		}
		
		line.drawColorSolidLine(g);
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 20;
	}

	public int getIconHeight() {
		return 14;
	}
}
