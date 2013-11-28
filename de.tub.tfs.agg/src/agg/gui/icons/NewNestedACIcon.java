package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class NewNestedACIcon implements Icon {

	boolean isEnabled;

	Color color;

	public NewNestedACIcon(Color aColor) {
		this.color = aColor;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (this.isEnabled) {
			g.setColor(Color.white);
			g.fillRect(x, y, getIconWidth(), getIconHeight());
			g.setColor(this.color);
		} else {
			g.setColor(Color.gray.brighter());
			g.fillRect(x, y, getIconWidth(), getIconHeight());
			g.setColor(Color.gray.darker());
		}
		g.drawString("G", x + 0, y + 8);
		g.drawString("A", x + 6, y + 12);
		g.drawString("C", x + 10, y + 15);
	}

	public int getIconWidth() {
		return 16;
	}

	public int getIconHeight() {
		return 16;
	}

	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}
}
