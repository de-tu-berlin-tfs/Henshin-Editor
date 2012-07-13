package de.tub.tfs.henshin.editor.figure.graph;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Pattern;

import de.tub.tfs.henshin.editor.interfaces.Constants;

/**
 * The Class NodeFigure.
 */
public class SimpleNodeFigure extends NodeFigure {

	/** The text. */
	protected Label text;

	/** The hide symbol. */
	protected Label hideSymbol;

	/** The hide button. */
	protected RectangleFigure hideButton;

	/** The hide. */
	protected boolean hide = true;

	/** The entered color. */
	protected Color entredClor = ColorConstants.darkBlue;

	/** The exit color. */
	protected Color exitClor = ColorConstants.lightBlue;

	/** The rectangle2. */
	private RectangleFigure textFigure;

	private Node node;

	private boolean collapsing = false;

	/**
	 * Instantiates a new node figure.
	 * 
	 * @param name
	 *            the name
	 * @param weight
	 *            the weight
	 */
	public SimpleNodeFigure(Node node, int weight, MouseListener mouseListener) {
		super();

		this.node = node;

		Color[] shadow = { ColorConstants.black, ColorConstants.black };
		Color[] highlight = { ColorConstants.gray, ColorConstants.gray };

		setBorder(new SchemeBorder(new SchemeBorder.Scheme(highlight, shadow)));

		textFigure = new RectangleFigure();
		text = new Label(getNodeName());

		setSize(weight, 25);

		textFigure.add(text);

		hideButton = new RectangleFigure();

		hideSymbol = new Label(getSymbol());
		hideSymbol.setSize(10, 10);
		hideSymbol.setTextAlignment(PositionConstants.CENTER);

		Font font = new Font(Display, "Arial", 12, SWT.BOLD | SWT.ITALIC);

		hideSymbol.setFont(font);
		hideButton.add(hideSymbol);
		hideButton.setBackgroundColor(exitClor);
		hideButton.addMouseListener(mouseListener);
		hideButton.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent me) {
			}

			@Override
			public void mouseHover(MouseEvent me) {
			}

			@Override
			public void mouseExited(MouseEvent me) {
				hideButton.setBackgroundColor(exitClor);
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				hideButton.setBackgroundColor(entredClor);

			}

			@Override
			public void mouseDragged(MouseEvent me) {

			}
		});
		hideButton.setSize(10, 10);

		setLayoutManager(new XYLayout());

		add(textFigure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#repaint()
	 */
	@Override
	public void repaint() {
		int x = getLocation().x;
		int y = getLocation().y;
		
		if (collapsing) {
			width = Constants.SIZE_16;
			height = Constants.SIZE_16;
			getBounds().height = height;
			getBounds().width = width;
		}
		else {
			
			if (hideButton != null) {
				
				if (hide) {
					super.setSize(width, 25);
				} else {
					super.setSize(width, height);
				}
				textFigure.setSize(width, 25);
				text.setSize(width, 25);
				text.setLocation(new Point(x, y));
				
				if (getChildren().size() > 1) {
					if (!textFigure.getChildren().contains(hideButton)) {
						textFigure.add(hideButton);
					}
					hideButton.setLocation(new Point(x + width - 11, y + 1));
				} else {
					if (textFigure.getChildren().contains(hideButton)) {
						textFigure.remove(hideButton);
					}
				}
			}
			
			for (int i = 0; i < getChildren().size(); i++) {
				IFigure figure = (IFigure) getChildren().get(i);
				
				if (figure == textFigure || hide) {
					figure.setSize(width, 25);
					figure.setLocation(new Point(x, y));
				} else {
					figure.setSize(width - 5, 15);
					figure.setLocation(new Point(x + 5, y + 25 + 15 * i));
				}
			}
		}



		super.repaint();
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	@Override
	public void setName(String name) {
		text.setText(name);
		textFigure.setSize(width, 25);
		text.setSize(width, 25);
		text.setLocation(getLocation());
		textFigure.setLocation(getLocation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setLocation(org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
		textFigure.setLocation(p);
		text.setLocation(p);

	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	private String getNodeName() {
		String name = new String();
		if (node != null) {
			if (node.getName() != null) {
				name = new String(node.getName());
			}
			name += ":";
			if (node.getType() != null) {
				name += node.getType().getName();
			}
		}
		return name;
	}

	/**
	 * @return the hide
	 */
	@Override
	public synchronized boolean isHide() {
		return hide;
	}

	/**
	 * Sets the hide.
	 * 
	 * @param hide
	 *            the new hide
	 */
	@Override
	public void setHide(boolean hide) {
		this.hide = hide;
		hideSymbol.setText(getSymbol());
		repaint();
	}

	/**
	 * Gets the value label text bounds.
	 * 
	 * @return the value label text bounds
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return text.getBounds();
	}

	/**
	 * Gets the symbol.
	 * 
	 * @return the symbol
	 */
	protected String getSymbol() {
		if (hide) {
			return "+";
		} else {
			return "-";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		repaint();
	}

	@Override
	public void paint(Graphics graphics) {
		int x = Math.round(getLocation().x + getSize().width / 2);
		graphics.setBackgroundPattern(new Pattern(Display, x, getLocation().y,
				x, getLocation().y + getSize().height, gradientColor1,
				gradientColor2));

		if (node != null) {
			graphics.setAlpha(255);
		}

		super.paint(graphics);
	}

	public void collapsing(boolean collapsing) {
		this.collapsing = collapsing;
		List<?> children = getChildren();
		for (int i = 0; i < children.size(); i++) {
			remove((IFigure) children.get(i));
		}
		repaint();
	}
}
