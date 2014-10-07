//$Id: RoundRectFillShapeIcon.java,v 1.3 2010/09/23 08:20:23 olga Exp $

package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class RoundRectFillShapeIcon implements Icon {

	Color color;

	public RoundRectFillShapeIcon(Color c) {
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
		g.setColor(this.color);
		g.fillRoundRect(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2,
				5, 5);
		g.setColor(Color.black);
		g.drawRoundRect(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2,
				5, 5);
		g.setColor(oldColor);
	}

	public int getIconWidth() {
		return 14;
	}

	public int getIconHeight() {
		return 12;
	}
}
