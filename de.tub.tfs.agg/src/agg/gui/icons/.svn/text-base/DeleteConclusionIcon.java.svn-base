package agg.gui.icons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class DeleteConclusionIcon implements Icon {

	boolean isEnabled;

	Color color;

	public DeleteConclusionIcon(Color aColor) {
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
		g.drawString("A", x + 1, y + 10);
		g.drawString("C", x + 7, y + 15);
		if (this.isEnabled)
			g.setColor(Color.red);
		else
			g.setColor(Color.gray.darker());
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1.0f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g2.drawLine(x,y, x+getIconWidth(),y+getIconHeight());
		g2.drawLine(x, y + getIconHeight(), x + getIconWidth(), y);
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
