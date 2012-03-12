//$Id: ColorDashLineIcon.java,v 1.4 2010/08/23 07:33:26 olga Exp $

package agg.gui.icons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

import agg.editor.impl.Line;

public class ColorDashLineIcon implements Icon {

	Color color;
	boolean filled;
	
	public ColorDashLineIcon(Color c) {
		this.color = c;
	}

	public ColorDashLineIcon(Color c, boolean bold) {
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
		Line line = new Line(x, y + 7, x + 20, y + 7);
		line.setColor(this.color);
		
		if (this.filled) {
			((Graphics2D) g).setStroke(new BasicStroke(3.0f));
			line.drawColorDotLine(g, 4, 4);
		} else {		
			line.drawColorDotLine(g, 4, 2);
		}
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 20;
	}

	public int getIconHeight() {
		return 14;
	}
}
