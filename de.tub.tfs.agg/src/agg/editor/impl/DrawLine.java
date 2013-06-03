// $Id: DrawLine.java,v 1.5 2008/06/26 14:18:47 olga Exp $

package agg.editor.impl;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

public class DrawLine {

	public final static int SOLID = 0;

	public final static int DASHED = 1;

	public final static int DOTTED = 2;

	final static int DOTTEDSPACE = 4; // The space between dots.

	final static int DASHEDSPACE = 10; // The space/size between/of basic

	// lines.

	final static BasicStroke stroke = new BasicStroke(1.0f);

	final static BasicStroke wideStroke = new BasicStroke(2.0f);

	final static float dash1[] = { 8.0f };// {10.0f};

	final static BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	final static float dot1[] = { 2.0f };// {1.0f};

	final static BasicStroke dotted = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dot1, 0.0f);

	/**
	 * Draws a line with given style on a graphic context. Points of line are:
	 * P0(x0,y0) - P1(x1,y1)
	 */
	public DrawLine(Graphics g, int x0, int y0, int x1, int y1) {
		this(g, SOLID, x0, y0, x1, y1);
	}

	public DrawLine(Graphics grs, int style, int x0, int y0, int x1, int y1) {
		if (grs == null)
			return;
		Graphics2D g = (Graphics2D) grs;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (style == DASHED) {
			BasicStroke tmpdashed = new BasicStroke(((BasicStroke)g.getStroke()).getLineWidth(),
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
			g.setStroke(tmpdashed);
		} else if (style == DOTTED) {
			BasicStroke tmpdotted = new BasicStroke(((BasicStroke)g.getStroke()).getLineWidth(),
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dot1, 0.0f);
			g.setStroke(tmpdotted);
		}

		g.draw(new Line2D.Double(x0, y0, x1, y1));
	}
}
