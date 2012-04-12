package agg.gui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

public class TextIcon implements Icon {

	String name = "";
	double rotate = 0;
	boolean isEnabled;

	Color col;

	public TextIcon(String name, boolean enabled) {
		this.name = name;
		this.isEnabled = enabled;
	}

	public TextIcon(String name, double rotate, boolean enabled) {
		this.name = name;
		this.rotate = rotate;
		this.isEnabled = enabled;
	}
	
	public void paintIcon(Component comp, Graphics grs, int xp, int yp) {
		if (comp == null || grs == null)
			return;
		
		if (this.isEnabled) {
			if (this.col == null)
				grs.setColor(Color.black);
			else
				grs.setColor(this.col);
		} else
			grs.setColor(Color.gray.darker());
		// g.setFont(new Font("Helvetica-Bold", Font.ITALIC, 14));
		// Font: [family=dialog.bold,name=Dialog,style=bold,size=12]
		
		grs.drawString(this.name, xp, yp + 13);
		
		if (this.rotate != 0) {
			((Graphics2D)grs).rotate(this.rotate);
		}
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

	public void setText(String t) {
		this.name = t;
		// paintIcon(c, g, x, y);
	}

	public String getText() {
		return this.name;
	}

	public void setColor(Color c) {
		this.col = c;
	}
}
