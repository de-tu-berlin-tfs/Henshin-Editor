//$Id: CircleShapeIcon.java,v 1.4 2010/08/23 07:33:26 olga Exp $

package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class CircleShapeIcon implements Icon {

	Color color;
	boolean filled;
	
	public CircleShapeIcon(Color c) {
		this.color = c;
	}

	public CircleShapeIcon(Color c, boolean filledshape) {
		this.color = c;
		this.filled = filledshape;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return this.color;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Color oldColor = g.getColor();
		g.setColor(this.color);
		if (this.filled) {
			g.fillOval(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2);
		} else {
			g.drawOval(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2);
		}
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 14;
	}

	public int getIconHeight() {
		return 14;
	}
}
