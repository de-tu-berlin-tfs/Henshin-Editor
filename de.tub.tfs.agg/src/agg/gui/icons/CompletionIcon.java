package agg.gui.icons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class CompletionIcon implements Icon {

	boolean isEnabled;

	Color color;

	public CompletionIcon(Color aColor) {
		this.color = aColor;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (this.isEnabled)
			g.setColor(this.color);
		else
			g.setColor(Color.gray.darker());
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1.5f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawRect(x + 4, y + 2, 3, 3);
		g2.drawRect(x + getIconWidth() - 5, y + 2, 3, 3);
		g2.drawRect(x + 4, y + getIconHeight() - 4, 3, 3);
		g2.drawRect(x + getIconWidth() - 5, y + getIconHeight() - 4, 3, 3);
		if (this.isEnabled)
			g.setColor(Color.red);
		g2.setStroke(new BasicStroke(1.0f));
		g2.drawLine(x + 6, y + 3, x + 2, y + getIconHeight() / 2);
		g2.drawLine(x + 2, y + getIconHeight() / 2, x + 6, y + getIconHeight()
				- 3);
		g2.drawLine(x + getIconWidth() - 4, y + 3, x + getIconWidth() / 2, y
				+ getIconHeight() / 2);
		g2.drawLine(x + getIconWidth() / 2, y + getIconHeight() / 2, x
				+ getIconWidth() - 4, y + getIconHeight() - 3);
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
