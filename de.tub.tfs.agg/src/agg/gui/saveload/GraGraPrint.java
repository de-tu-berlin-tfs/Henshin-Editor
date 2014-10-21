package agg.gui.saveload;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
//import java.awt.PrintJob;
import java.awt.Rectangle;
import java.awt.RenderingHints;
//import java.awt.Toolkit;
//import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
//import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import agg.editor.impl.Arrow;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNAC;
import agg.editor.impl.EdRule;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraphCanvas;
import agg.gui.event.EditEvent;
import agg.gui.event.EditEventListener;

/**
 * The GraGraPrint prints a gragra. A print dialog allows to choose what do you
 * want to print.
 * 
 * @deprecated
 * @author $Author: olga $
 * @version $ID
 */
@SuppressWarnings("serial")
class GraGraPrint extends JPanel implements ActionListener, EditEventListener {

	/**
	 * Creates a new instance of the GraGraPrint with a print dialog. The gragra
	 * object is not defined.
	 */
	public GraGraPrint(JFrame applFrame) {
		this(applFrame, null);
	}

	/**
	 * Creates a new instance of the GraGraPrint with a print dialog.
	 */
	public GraGraPrint(JFrame applFrame, EdGraGra gra) {
		super(new BorderLayout(), true);
		setBackground(Color.white);
		this.applFrame = applFrame;
		this.gragra = gra;
		initialize(this.gragra);
	}

	private void initialize(EdGraGra gra) {
		this.gragraRuleChecks = new Vector<JCheckBox>(); // Elem is JCheckBox
		this.gragraRuleInfos = new Vector<JLabel>(); // Elem is String
		this.gragraOptionalChecks = new Vector<JCheckBox>(); // Elem is JCheckBox
		this.optionalChecks = new Vector<Vector<JCheckBox>>(); // Elem is Vector of
		// JCheckBox

		/* create a preview frame */
		this.previewFrame = new JFrame("Preview");

		/* add this to preview frame */
		this.setSize(150, 150);
		this.setPreferredSize(new Dimension(150, 150));
		JScrollPane jsp = new JScrollPane(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setViewportView(this);
		this.previewFrame.getContentPane().add(BorderLayout.CENTER, jsp);

		/* add button panel to preview frame */
		JPanel buttonPanel = new JPanel();
		this.previewPrint = new JButton("Print");
		this.previewCancel = new JButton("Cancel");
		buttonPanel.add(this.previewPrint);
		buttonPanel.add(this.previewCancel);
		this.previewPrint.addActionListener(this);
		this.previewCancel.addActionListener(this);
		this.previewFrame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);

		this.previewFrame.setSize(150, 150);
		this.previewFrame.setLocation(10, 10);
		this.previewFrame.pack();

		/* create a print dialog */
		this.dialog = new JDialog(new JFrame(), "Print", true);

		/* create main panel of the print dialog */
		JPanel mainPanel = new JPanel(new GridBagLayout(), true);
		mainPanel.setPreferredSize(new Dimension(300, 300));
		this.dialog.getContentPane().add(mainPanel);

		/* create gragra panel for the print dialog */
		this.gragraPanel = new JPanel(new BorderLayout());
		this.gragraPanel.add(createGraGraCheckBox(gra), BorderLayout.CENTER);

		/* create scale panel for the print dialog */
		JPanel scalePanel = new JPanel(new BorderLayout());
		scalePanel.add(createScaleBox(), BorderLayout.CENTER);

		/* create confirm panel for the print dialog */
		JPanel confirmPanel = new JPanel(new BorderLayout());
		confirmPanel.add(createConfirmButtons(), BorderLayout.CENTER);
		this.print.addActionListener(this);

		/* fill the main panel of the print dialog */
		constrainBuild(mainPanel, this.gragraPanel, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 0.0, 0.0,
				5, 5, 5, 5);
		// constrainBuild(mainPanel, scalePanel, 0, 1, 2, 1,
		// GridBagConstraints.BOTH, GridBagConstraints.CENTER, 0.0, 0.0, 5, 5,
		// 5, 5);
		constrainBuild(mainPanel, confirmPanel, 0, 2, 2, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 0.0, 0.0,
				5, 5, 5, 5);

		this.dialog.pack();
		this.dialog.setLocation(50, 50);
		/*
		 * if (applFrame != null)
		 * this.dialog.setLocation(applFrame.getLocation().x+applFrame.getWidth()/4,
		 * applFrame.getLocation().y+applFrame.getHeight()/4);
		 */
	}

	/**
	 * Calls the print() Methode of my printJob if the <Print> button of my
	 * previewer was pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getSource() == this.previewPrint || e.getSource() == this.print) {
				if (isEmpty())
					return;
				/* Get a printer job */
				PrinterJob printJob = PrinterJob.getPrinterJob();
				if (printJob != null) {
					printJob.setJobName("GraGra");
					// printJob.setPrintable(this);

					/* Create a page format */
					this.pageFormat = printJob.defaultPage();
					this.pageFormat.setOrientation(PageFormat.PORTRAIT);

					/* Set up a book */
					Book book = new Book();

					/* Pass the book to the PrinterJob */
					printJob.setPageable(book);

					// PageFormat pf =
					// printJob.pageDialog(printJob.defaultPage());
					if (printJob.printDialog()) {
						/*
						 * System.out.println("\n printJob \n");
						 * System.out.println("PageFormat : 1/72 nds of an inch :
						 * "); System.out.println("Height:
						 * "+this.pageFormat.getHeight()+" /
						 * "+this.pageFormat.getImageableHeight());
						 * System.out.println("Width : "+this.pageFormat.getWidth()+" /
						 * "+this.pageFormat.getImageableWidth());
						 * System.out.println("Imageable X:
						 * "+this.pageFormat.getImageableX());
						 * System.out.println("Imageable Y:
						 * "+this.pageFormat.getImageableY());
						 * System.out.println("Orientation:
						 * "+this.pageFormat.getOrientation()); System.out.println();
						 */
						this.pageable = new Dimension((int) this.pageFormat.getWidth(),
								(int) this.pageFormat.getHeight());
						this.imageable = new Dimension(this.pageable.width - 2
								* ((int) this.pageFormat.getImageableX()),
								this.pageable.height - 2
										* ((int) this.pageFormat.getImageableY()));
						this.W = this.imageable.width;
						this.H = this.imageable.height;
						// System.out.println("this.imageable W x this.H : "+W+" x "+this.H);
						Vector<Vector<Image>> pages = getPagesToPrint(this.pageFormat);
						if (pages != null) {
							// System.out.println("To print pages: "+
							// pages.size());
							for (int i = 0; i < pages.size(); i++) {
								PaintContent paintContent = new PaintContent();
								paintContent.setPageImages(pages.elementAt(i));
								book.append(paintContent, this.pageFormat);
							}
						}
						try {
							printJob.print();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
		this.previewFrame.dispose();
	}

	/** Emplements the EditEventListaner interface */
	public void editEventOccurred(EditEvent e) {
		// System.out.println("GraGraPrint.editEventOccurred "+e.getMsg());
		int msgkey = e.getMsg();
		if (msgkey == EditEvent.EDIT_FONT_STYLE) {
			this.fontStyle = e.getIntValue();
		} else if (msgkey == EditEvent.EDIT_FONT_SIZE) {
			this.fontSize = e.getIntValue();
		} else if (msgkey == EditEvent.EDIT_SCALE) {
			this.scale = e.getDoubleValue();
		}
		// System.out.println(this.scale+" "+this.fontStyle+" / "+this.fontSize);
	}

	/** Shows my print dialog */
	public void showDialog() {
		this.dialog.setVisible(true);
	}

	/** Paints an image for printing in my previewer panel */
	public void paintComponent(Graphics grs) {
		super.paintComponent(grs);
		Graphics2D g2D = (Graphics2D) grs;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setPaint(Color.white);
		g2D.drawRect(0, 0, getWidth(), getHeight());

		// g2D.this.scale(this.scale, this.scale);
		// paintImages(g2D);

		printImages(g2D);
	}

	/** Sets a gragra for printing */
	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
		this.gragraRuleChecks = new Vector<JCheckBox>(); // Elem is JCheckBox
		this.gragraRuleInfos = new Vector<JLabel>(); // Elem is String
		this.gragraOptionalChecks = new Vector<JCheckBox>(); // Elem is JCheckBox
		this.optionalChecks = new Vector<Vector<JCheckBox>>(); // Elem is Vector of
		// JCheckBox
		this.gragraPanel.removeAll();
		this.gragraPanel.add(createGraGraCheckBox(this.gragra), BorderLayout.CENTER);
		this.preview.setEnabled(false);
		this.print.setEnabled(false);
	}

	/** Sets an application frame */
	public void setFrame(JFrame f) {
		this.applFrame = f;
		if ((this.dialog != null) && (this.applFrame != null))
			this.dialog.setLocation(this.applFrame.getLocation().x + this.applFrame.getWidth()
					/ 4, this.applFrame.getLocation().y + this.applFrame.getHeight() / 4);
	}

	/**
	 * Returns true if the print dialog would be cancelled or nothing would be
	 * chosen for printing.
	 */
	public boolean isEmpty() {
		if (this.cancelled)
			return true;
		if (this.all.isSelected())
			return false;
		else if (this.g.isSelected())
			return false;
		else {
			for (int i = 0; i < this.gragraRuleChecks.size(); i++) {
				JCheckBox cb = this.gragraRuleChecks.elementAt(i);
				if (cb.isSelected())
					return false;
			}
			return true;
		}
	}

	/** Gets all images of the chosen gragra elements */
	private Vector<Image> getAllImages() {
		Vector<Image> images = new Vector<Image>();
		for (int i = 0; i < this.gragraRuleChecks.size(); i++) {
			JCheckBox cb = this.gragraRuleChecks.elementAt(i);
			EdRule r = this.gragra.getRules().elementAt(i);
			if (this.all.isSelected() || cb.isSelected()) {
				Vector<Image> imgs = getImage(i, r);
				if (imgs != null) {
					for (int j = 0; j < imgs.size(); j++) {
						Image image = imgs.elementAt(j);
						if (image != null)
							images.addElement(image);
					}
				}
			}
		}
		if (this.all.isSelected() || this.g.isSelected()) {
			Image image = null;
			if (this.gragra.getGraph().getGraphDimension(this.scale).width == 0)
				image = getImage(this.gragra.getGraph(), new Dimension(100, 50));
			else
				image = getImage(this.gragra.getGraph(), null);
			if (image != null)
				images.addElement(image);
		}
		return images;
	}

	/* Paints images into the specified Graphics2D grs */
/*
	private void paintImages(Graphics2D grs) {
		// System.out.println("GraGraPrint.paintImages BEGIN\n");
		Vector<Image> images = getAllImages();
		if (images.isEmpty())
			return;

		int x = 72;
		int y = 72;
		for (int i = 0; i < images.size(); i++) {
			Image image = images.elementAt(i);
			grs.drawImage(image, x, y, null);
			y = y + image.getHeight(null) + offset;
		}

		int sizeX = this.W + 72;
		int sizeY = y + 10;
		this.setSize(sizeX, sizeY);
		// markiere Seitenende
		for (int i = (this.H + 72); i < sizeY; i = i + (this.H + 72)) {
			grs.setPaint(Color.black);
			grs.drawLine(0, i - 2, sizeX - 1, i - 2);
		}
		grs.setPaint(Color.white);
		// System.out.println("GraGraPrint.paintImages END");
	}
*/
	
	/** Paints images into the specified Graphics2D grs */
	private void printImages(Graphics2D grs) {
		// System.out.println("GraGraPrint.printImages BEGIN");
		if (this.pageFormat == null)
			this.pageFormat = new PageFormat();
		Vector<Vector<Image>> pages = getPagesToPrint(this.pageFormat);
		if (pages.isEmpty())
			return;

		int x = 0;
		int y = 0;
		for (int j = 0; j < pages.size(); j++) {
			Vector<Image> pageImages = pages.elementAt(j);
			if (pageImages != null) {
				x = (int) this.pageFormat.getImageableX();
				y = y + (int) this.pageFormat.getImageableY();
				for (int i = 0; i < pageImages.size(); i++) {
					Image image = pageImages.elementAt(i);
					grs.drawImage(image, x, y, null);
					y = y + image.getHeight(null);
				}

				// markiere Seitenende
				y = y + (int) this.pageFormat.getImageableY();
				grs.setPaint(Color.black);
				grs.drawLine(0, y, this.W, y);
				grs.setPaint(Color.white);
			}
		}
		this.setSize(this.W + (int) this.pageFormat.getImageableX() + 10, y + 10);
		// System.out.println("GraGraPrint.printImages END");
	}

	private Vector<Vector<Image>> getPagesToPrint(PageFormat pf) {
		// System.out.println("\nGraGraPrint.getPagesToPrint BEGIN");
		Vector<Vector<Image>> pgs = new Vector<Vector<Image>>();
		Vector<Image> images = getAllImages();
		if (images.isEmpty())
			return pgs;

//		int sizeX = 0;
//		int sizeY = 0;
//		int x = (int) pf.getImageableX();
		int y = (int) pf.getImageableY();

		Vector<Image> page = new Vector<Image>();
		for (int i = 0; i < images.size(); i++) {
			Image image = images.elementAt(i);
			if (image != null) {
				if ((y + image.getHeight(null)) <= this.imageable.height) {
					page.addElement(image);
					y = y + image.getHeight(null);
					if ((y + offset) <= this.imageable.height)
						y = y + image.getHeight(null) + offset;
				} else {
					if (!page.isEmpty())
						pgs.addElement(page);
					y = (int) this.pageFormat.getImageableY();
					page = new Vector<Image>();
					page.addElement(image);
					y = y + image.getHeight(null);
					if ((y + offset) <= this.imageable.height)
						y = y + image.getHeight(null) + offset;
				}
			}
		}
		if (!page.isEmpty())
			pgs.addElement(page);
		// System.out.println("Pages to print: "+pgs.size());
		// System.out.println("GraGraPrint.getPagesToPrint END");
		return pgs;
	}

	private JPanel createGraGraCheckBox(EdGraGra gra) {
		JPanel p = new JPanel(new GridBagLayout());
		JLabel info = new JLabel("GraGra: EMPTY");
		if (gra == null)
			return p;

		info = new JLabel("GraGra:  " + gra.getName());
		int y = 0;
		constrainBuild(p, info, 0, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

		this.all = new JCheckBox("All");
		this.all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((e.getSource() instanceof JCheckBox)
						&& (e.getSource() == GraGraPrint.this.all)) {
					if (GraGraPrint.this.all.isSelected()) {
						deselectRules();
						GraGraPrint.this.g.setSelected(false);
						GraGraPrint.this.preview.setEnabled(true);
						GraGraPrint.this.print.setEnabled(true);
					} else if (isEmpty()) {
						GraGraPrint.this.preview.setEnabled(false);
						GraGraPrint.this.print.setEnabled(false);
					}
				}
			}
		});

		y++;
		constrainBuild(p, this.all, 0, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

		JLabel opt = new JLabel("optional");
		constrainBuild(p, opt, 1, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

		for (int i = 0; i < gra.getRules().size(); i++) {
			final EdRule r = gra.getRules().elementAt(i);
			final int indx = i;
			if (r != null) {
				JCheckBox cb = new JCheckBox(r.getBasisRule().getName());
				cb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JCheckBox item = (JCheckBox) e.getSource();
						if (item.isSelected()) {
							GraGraPrint.this.all.setSelected(false);
							GraGraPrint.this.gragraOptionalChecks.elementAt(indx).setEnabled(
									true);
							GraGraPrint.this.preview.setEnabled(true);
							GraGraPrint.this.print.setEnabled(true);
						} else {
							GraGraPrint.this.gragraOptionalChecks.elementAt(indx).setEnabled(
									false);
							if (isEmpty()) {
								GraGraPrint.this.preview.setEnabled(false);
								GraGraPrint.this.print.setEnabled(false);
							}
						}
					}
				});

				this.gragraRuleChecks.add(cb);
				y++;
				constrainBuild(p, cb, 0, y, 1, 1, GridBagConstraints.BOTH,
						GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);
				cb = new JCheckBox();
				cb.setEnabled(false);
				this.gragraOptionalChecks.addElement(cb);

				final Vector<JCheckBox> checks = new Vector<JCheckBox>();
				this.optionalChecks.add(checks);
				cb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JCheckBox item = (JCheckBox) e.getSource();
						if (item.isSelected())
							GraGraPrint.this.ruleCheck = getRuleCheckBox(r, indx, checks);
					}
				});

				constrainBuild(p, cb, 1, y, 1, 1, GridBagConstraints.BOTH,
						GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

				info = new JLabel("");
				this.gragraRuleInfos.addElement(info);
				y++;
				constrainBuild(p, info, 0, y, 1, 1, GridBagConstraints.BOTH,
						GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);
			}
		}

		this.g = new JCheckBox(this.gragra.getGraph().getBasisGraph().getName());
		this.g.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((e.getSource() instanceof JCheckBox)
						&& (e.getSource() == GraGraPrint.this.g)) {
					if (GraGraPrint.this.g.isSelected()) {
						GraGraPrint.this.all.setSelected(false);
						GraGraPrint.this.preview.setEnabled(true);
						GraGraPrint.this.print.setEnabled(true);
					} else if (isEmpty()) {
						GraGraPrint.this.preview.setEnabled(false);
						GraGraPrint.this.print.setEnabled(false);
					}
				}
			}
		});
		y++;
		constrainBuild(p, this.g, 0, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

		return p;
	}

	JDialog getRuleCheckBox(EdRule r, int n, Vector<JCheckBox> v) {
		final JDialog dial = new JDialog(new JFrame(), "optional", true);
		JPanel p = new JPanel(new GridBagLayout());
		dial.getContentPane().add(p);

		int y = 0;
		JCheckBox cb = new JCheckBox("LHS");
		constrainBuild(p, cb, 0, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);
		v.addElement(cb);
		y++;
		cb = new JCheckBox("RHS");
		constrainBuild(p, cb, 0, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);
		v.addElement(cb);

		if (r.getNACs().size() != 0) {
			y++;
			JLabel l = new JLabel("NAC:");
			constrainBuild(p, l, 0, y, 1, 1, GridBagConstraints.BOTH,
					GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

			for (int i = 0; i < r.getNACs().size(); i++) {
				EdNAC nac = r.getNACs().elementAt(i);
				y++;
				cb = new JCheckBox(nac.getName());
				v.addElement(cb);
				constrainBuild(p, cb, 0, y, 1, 1, GridBagConstraints.BOTH,
						GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);
			}
		}

		y++;
		JButton ok = new JButton("OK");
		ok.setBorderPainted(true);

		final Vector<JCheckBox> checks = v;
		final int indx = n;
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean check = false;
				for (int i = 0; i < checks.size(); i++) {
					JCheckBox c = checks.elementAt(i);
					if (c.isSelected())
						check = true;
				}
				if (!check)
					deselectRuleOptional(indx);
				dial.setVisible(false);
			}
		});

		constrainBuild(p, ok, 0, y, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 5);

		dial.pack();
		dial.setLocation(this.dialog.getX() + 100, this.dialog.getY() + 50);
		dial.setVisible(true);
		return dial;
	}

	private JPanel createScaleBox() {
		JPanel p = new JPanel();
		JLabel l = new JLabel("Scale:  ");
		p.add(l);
		final JTextField s = new JTextField("1.0", 3);
		p.add(s);
		this.scale = 1.0;
		s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JTextField) {
					try {
						Double d = new Double(((JTextField) e.getSource())
								.getText().trim());
						GraGraPrint.this.scale = d.doubleValue();
					} catch (NumberFormatException ex) {
						s.setText("1.0");
					}
					// System.out.println("this.scale: "+this.scale);
				}
			}
		});
		return p;
	}

	private JPanel createConfirmButtons() {
		JPanel p = new JPanel(new GridBagLayout());
		// Preview button
		this.preview = new JButton("Preview");
		this.preview.setEnabled(false);
		this.preview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraGraPrint.this.dialog.setVisible(false);
				// paintGraphics();
				GraGraPrint.this.previewFrame.setVisible(true);
			}
		});
		constrainBuild(p, this.preview, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 10, 5, 5);

		// Print button
		this.print = new JButton("Print");
		this.print.setEnabled(false);
		this.print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraGraPrint.this.dialog.setVisible(false);
			}
		});
		constrainBuild(p, this.print, 1, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 10, 5, 5);

		// Cancel button
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraGraPrint.this.cancelled = true;
				GraGraPrint.this.dialog.setVisible(false);
			}
		});
		constrainBuild(p, b, 2, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 0.0, 0.0, 5, 5, 5, 10);

		return p;
	}

	void deselectRules() {
		for (int i = 0; i < this.gragraRuleChecks.size(); i++) {
			this.gragraRuleChecks.elementAt(i).setSelected(false);
			this.gragraOptionalChecks.elementAt(i).setEnabled(false);
		}
	}

	void deselectRuleOptional(int n) {
		JCheckBox cb = this.gragraOptionalChecks.elementAt(n);
		cb.setSelected(false);
	}

	private void constrainBuild(Container container, Component component,
			int grid_x, int grid_y, int grid_width, int grid_height, int fill,
			int anchor, double weight_x, double weight_y, int top, int left,
			int bottom, int right) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = grid_x;
		c.gridy = grid_y;
		c.gridwidth = grid_width;
		c.gridheight = grid_height;
		c.fill = fill;
		c.anchor = anchor;
		c.weightx = weight_x;
		c.weighty = weight_y;
		c.insets = new Insets(top, left, bottom, right);
		((GridBagLayout) container.getLayout()).setConstraints(component, c);
		container.add(component);
	}

	/*
	 * Get an image of an EdGraph g. The EdGraph g can be a start graph, a
	 * left/right graph of a rule or NAC graph.
	 */
	private Image getImage(EdGraph graph, Dimension imageDim) {
		if (graph == null)
			return null;
		// System.out.println("GraGraPrint.getImage of GRAPH BEGIN\n");
		Dimension graphDim = graph.getGraphDimension(this.scale);
		if (graphDim == null)
			return null;
		graphDim.width = graphDim.width;// + offset;
		graphDim.height = graphDim.height;// + offset;

		Dimension d = new Dimension(graphDim.width, 2 * offset
				+ graphDim.height);
		if (imageDim != null) {
			if (imageDim.width > d.width)
				d.width = imageDim.width;
			if (imageDim.height > d.height)
				d.height = imageDim.height;
		}

		int w = d.width;
		int h = d.height;
		Image image = createImage(w, h);
		if (image == null)
			return null;

		Graphics2D grs = (Graphics2D) image.getGraphics();
		grs.setPaint(Color.white);
		grs.fill(new Rectangle(0, 0, w, h));

		GraphCanvas canvas = new GraphCanvas();
		canvas.setSize(d);
		canvas.setGraph(graph);

		Image im = createImage(graphDim.width, graphDim.height);
		canvas.setScale(this.scale);
		canvas.setFontStyle(this.fontStyle);
		canvas.setFontSize(this.fontSize);
		canvas.paint(im.getGraphics());

		grs.setPaint(Color.black);
		grs
				.drawString(graph.getBasisGraph().getName(), offset, offset
						+ offset / 2);
		grs.drawImage(im, 1, 2 * offset, null);
		grs.drawRect(1, 2 * offset, w - 2, h - 2 * offset - 2);
		// System.out.println("GraGraPrint.getImage of GRAPH END\n");
		return image;
	}

	/* Get an image of all NAC graphs. */
	private Image getImage(Vector<EdNAC> nacGraphs) {
		if ((nacGraphs == null) || nacGraphs.isEmpty())
			return null;
		// System.out.println("GraGraPrint.getImage(Vector graphs)
		// "+graphs.size()+" BEGIN");
		int iw = 0;
		int ih = 0;
		Vector<EdGraph> imageGraphs = new Vector<EdGraph>();
		for (int i = 0; i < nacGraphs.size(); i++) {
			EdGraph eg = nacGraphs.elementAt(i);
			if (this.optional != null) {
				if (this.optional.isEmpty()
						|| this.optional.elementAt(i + 2).booleanValue()) {
					Dimension graphDim = eg.getGraphDimension(this.scale);
					if (graphDim.width > iw)
						iw = graphDim.width;
					if (graphDim.height > ih)
						ih = graphDim.height;
					if ((iw > 0) && (ih > 0))
						imageGraphs.addElement(eg);
				}
			}
		}

		iw = iw + offset; // groesste breite
		ih = 2 * offset + ih + offset; // groesste hoehe
		Vector<Image> images = new Vector<Image>();
		for (int i = 0; i < imageGraphs.size(); i++) {
			EdGraph eg = imageGraphs.elementAt(i);
			Image image = getImage(eg, new Dimension(iw, ih));
			images.addElement(image);
		}

		// space between single images
		Dimension space = new Dimension(offset, ih);

		int w = 0;
		int h = 0;
		int n = 0;
		for (int i = 0; i < images.size(); i++) {
//			Image image = images.elementAt(i);
			if (i == 0) {
				w = iw;
				n = 1;
			} else if ((w + space.width) > this.W) {
				n = i;
				i = images.size();
			} else if ((w + space.width + iw) > this.W) {
				w = w + space.width;
				n = i;
				i = images.size();
			} else {
				n++;
				w = w + space.width + iw;
			}
		}
		int nn = Math.round(((float) images.size()) / ((float) n));

		if (nn > 0) {
			h = nn * ih + 2 * offset;
			while (h > this.H) {
				// eine NAC Reihe wird abgeschnitten
				nn--;
				h = nn * ih + 2 * offset;
			}
			Image bigImage = createImage(w, h);
			Graphics2D grs = (Graphics2D) bigImage.getGraphics();
			grs.setPaint(Color.white);
			grs.fill(new Rectangle(0, 0, w, h));
			int x = 0;
			int y = 0;
			for (int i = 0; i < images.size(); i++) {
				Image image = images.elementAt(i);
				if (((x + space.width) > this.W) || ((x + space.width + iw) > this.W)) {
					x = 0;
					y = y + ih;
				}
				grs.drawImage(image, x, y, null);
				x = x + iw + space.width;
			}
			return bigImage;
		} 
		return null;
	}

	private Image getRuleArrow(Dimension d, boolean horizontal) {
		if (d == null)
			return null;
		Image image = createImage(d.width, d.height);
		Graphics2D grs = (Graphics2D) image.getGraphics();
		grs.setPaint(Color.white);
		grs.fill(new Rectangle(0, 0, d.width, d.height));
		grs.setPaint(Color.black);
		Arrow arrow = null;
		if (horizontal) {
			grs.drawLine(0, d.height / 2, d.width, d.height / 2);
			arrow = new Arrow(1.0, 0, d.height / 2, d.width, d.height / 2);
		} else {
			grs.drawLine(d.width / 2, 0, d.width / 2, d.height);
			arrow = new Arrow(1.0, d.width / 2, 0, d.width / 2, d.height);
		}
		arrow.draw(grs);
		return image;
	}

	/* Get an image of an EdRule r with its NAC image. */
	private Vector<Image> getImage(int ruleIndx, EdRule r) {
		if (r == null)
			return null;
		// System.out.println("GraGraPrint.getImage(int ruleIndx, EdRule r)
		// BEGIN");
		this.optional = new Vector<Boolean>();
		if (this.gragraOptionalChecks.elementAt(ruleIndx).isSelected()) {
			Vector<JCheckBox> v = this.optionalChecks.elementAt(ruleIndx);
			for (int i = 0; i < v.size(); i++) {
				JCheckBox cb = v.elementAt(i);
				if (cb.isSelected()) {
					Boolean b = new Boolean(true);
					this.optional.addElement(b);
				} else {
					Boolean b = new Boolean(false);
					this.optional.addElement(b);
				}
			}
		} else {
			Boolean b = new Boolean(true);
			this.optional.addElement(b); // LHS
			b = new Boolean(true);
			this.optional.addElement(b); // RHS
			for (int i = 0; i < r.getNACs().size(); i++) { // NACs
				b = new Boolean(true);
				this.optional.addElement(b);
			}
		}
		// System.out.println("this.optional: [left, right, nac0, nac1, ...]
		// "+this.optional);
		int w = 0;
		int h = 0;
		Dimension leftDim = r.getLeft().getGraphDimension(this.scale);
		Dimension rightDim = r.getRight().getGraphDimension(this.scale);
		if (leftDim.width > rightDim.width)
			w = leftDim.width;
		else
			w = rightDim.width;
		if (leftDim.height > rightDim.height)
			h = leftDim.height;
		else
			h = rightDim.height;

		w = w + offset; // groesste Breite von beiden Rule Seiten
		h = 2 * offset + h + offset; // groesste Hoehe von beiden Rule Seiten

		int lw = 0;
		int lh = 0;
		Image leftImage = null;
		if (this.optional.elementAt(0).booleanValue()) {
			leftImage = getImage(r.getLeft(), new Dimension(w, h));
			if (leftImage != null) {
				lw = w;
				lh = h;
			}
		}

		int rw = 0;
		int rh = 0;
		Image rightImage = null;
		if (this.optional.elementAt(1).booleanValue()) {
			rightImage = getImage(r.getRight(), new Dimension(w, h));
			if (rightImage != null) {
				rw = w;
				rh = h;
			}
		}

		Image ruleArrow = null;
		boolean horizontal = true;
		Dimension arrowDim = new Dimension(0, 0);
		if ((leftImage != null) && (rightImage != null)) {
			if ((lw + 3 * offset) >= this.W || (lw + 3 * offset + rw) >= this.W) {
				arrowDim = new Dimension(lw, 3 * offset);
				horizontal = false;
				ruleArrow = getRuleArrow(arrowDim, horizontal);
			} else {
				arrowDim = new Dimension(3 * offset, h + 2 * offset);
				horizontal = true;
				ruleArrow = getRuleArrow(arrowDim, horizontal);
			}
		}

		int ruleW = 0;
		int ruleH = 0;
		Image ruleImage = null;
		Graphics2D grs = null;
		int x = 0;
		int y = 0;
		if ((leftImage != null) && (rightImage != null) && horizontal) {
			ruleW = lw + arrowDim.width + rw;
			ruleH = lh;
			ruleImage = createImage(ruleW, ruleH);
			grs = (Graphics2D) ruleImage.getGraphics();
			grs.setColor(Color.white);
			grs.fillRect(0, 0, ruleW, ruleH);
			grs.drawImage(leftImage, x, y, null);
			x = x + lw;
			grs.drawImage(ruleArrow, x, y, null);
			if (ruleArrow != null)
				x = x + ruleArrow.getWidth(null);
			grs.drawImage(rightImage, x, y, null);
		} else if ((leftImage != null) && (rightImage != null) && !horizontal) {
			ruleW = lw;
			if (ruleArrow != null)
				ruleH = lh + ruleArrow.getHeight(null) + lh;
			ruleImage = createImage(ruleW, ruleH);
			grs = (Graphics2D) ruleImage.getGraphics();
			grs.setColor(Color.white);
			grs.fillRect(0, 0, ruleW, ruleH);
			grs.drawImage(leftImage, x, y, null);
			y = y + lh;
			grs.drawImage(ruleArrow, x, y, null);
			if (ruleArrow != null)
				y = y + ruleArrow.getHeight(null);
			grs.drawImage(rightImage, x, y, null);
		} else if (leftImage != null) {
			ruleW = lw;
			ruleH = lh;
			ruleImage = createImage(ruleW, ruleH);
			grs = (Graphics2D) ruleImage.getGraphics();
			grs.setColor(Color.white);
			grs.fillRect(0, 0, ruleW, ruleH);
			grs.drawImage(leftImage, x, y, null);
		} else if (rightImage != null) {
			ruleW = rw;
			ruleH = rh;
			ruleImage = createImage(ruleW, ruleH);
			grs = (Graphics2D) ruleImage.getGraphics();
			grs.setColor(Color.white);
			grs.fillRect(0, 0, ruleW, ruleH);
			grs.drawImage(rightImage, x, y, null);
		} // rule image ready

		Vector<Image> images = new Vector<Image>();
		int bigImageW = 0;
		int bigImageH = 0;
		if (ruleImage != null) {
			bigImageW = ruleW;
			bigImageH = ruleH;
		}
		Image bigImage = null;
		Image nacImage = null;

		Vector<EdNAC> nacs = r.getNACs();
		nacImage = getImage(nacs); // NACs image ready

		if (nacImage != null) {
			if (ruleImage != null) {
				// System.out.println("Rule + NACs ( this.W x this.H) : "+ruleW+" x
				// "+(ruleH + nacImage.getHeight(null))+" ( "+this.W+" x "+this.H+ ")");
				if ((ruleH + nacImage.getHeight(null)) > this.H) {
					// rule ohne NACs passt auf eine Seite
					bigImageW = this.W;
					bigImageH = this.H;
					bigImage = createImage(bigImageW, bigImageH);
					grs = (Graphics2D) bigImage.getGraphics();
					grs.setPaint(Color.white);
					grs.fillRect(0, 0, bigImageW, bigImageH);
					grs.setColor(Color.black);
					grs.drawString("Rule: " + r.getBasisRule().getName(),
							offset, offset + offset / 2);
					x = 5;
					y = 2 * offset;
					grs.drawImage(ruleImage, x, y, null);
					grs.drawRect(1, 2 * offset, ruleW + offset - 2, ruleH
							+ offset / 2);

					images.addElement(bigImage);
					// System.out.println("Rule ohne NACs auf eine Seite ( this.W x
					// this.H) : "+bigImageW+" x "+bigImageH);
				} else { // < this.H
					bigImageH = bigImageH + nacImage.getHeight(null);
				}
			} else {
				bigImageH = nacImage.getHeight(null);
			} // if (ruleImage != null)
			if (nacImage.getWidth(null) > bigImageW) // ruleW)
			{
				bigImageW = nacImage.getWidth(null);
			} else {
				bigImageW = ruleW;
			}
		} // if (nacImage != null)
		// System.out.println("Rule + NACs ( this.W x this.H) : "+bigImageW+" x
		// "+bigImageH);

		if (images.isEmpty()) {
			// rule und NACs passen auf eine Seite
			bigImageW = bigImageW + offset;
			bigImageH = 2 * offset + bigImageH + offset / 2;
			bigImage = createImage(bigImageW, bigImageH);
			grs = (Graphics2D) bigImage.getGraphics();
			grs.setPaint(Color.white);
			grs.fillRect(0, 0, bigImageW, bigImageH);
			grs.setColor(Color.black);
			grs.drawString("Rule: " + r.getBasisRule().getName(), offset,
					offset + offset / 2);
			x = 5;
			y = 2 * offset;
			grs.drawImage(ruleImage, x, y, null);
			if (nacImage != null) {
				if (ruleImage != null)
					y = y + ruleImage.getHeight(null);
				grs.drawImage(nacImage, x, y, null);
			}
			grs.drawRect(1, 2 * offset, bigImageW - 2, bigImageH - 2 * offset
					- 1);

			images.addElement(bigImage);
			// System.out.println("Rule + NACs auf eine Seite ( this.W x this.H) :
			// "+bigImageW+" x "+bigImageH);
		} else if (nacImage != null) {
			// NACs passen auf eine Seite
			bigImageW = this.W;
			bigImageH = this.H;
			bigImage = createImage(bigImageW, bigImageH);
			grs = (Graphics2D) bigImage.getGraphics();
			grs.setPaint(Color.white);
			grs.fillRect(0, 0, bigImageW, bigImageH);
			grs.setColor(Color.black);
			grs.drawString("NAC(s) of Rule: " + r.getBasisRule().getName(),
					offset, offset + offset / 2);
			x = 5;
			y = 2 * offset;
			grs.drawImage(nacImage, x, y, null);
			grs.drawRect(1, 2 * offset, nacImage.getWidth(null) + offset - 2,
					nacImage.getHeight(null) - 1);

			images.addElement(bigImage);
			// System.out.println("NACs auf eine Seite( this.W x this.H) : "+bigImageW+" x
			// "+bigImageH);
		}
		// System.out.println("GraGraPrint.getImage(int ruleIndx, EdRule r)
		// END");
		return images;
	}

//	private PrintJob getPrintJob() {
//		if (this.applFrame == null)
//			return null;
//		Frame f = new Frame("");
//		f.pack();
//		Toolkit tk = ((Window) f).getToolkit();
//		PrintJob pj = tk.getPrintJob(this.applFrame, "Print Job", new Properties());
//		return pj;
//	}

	EdGraGra gragra;

	JPanel gragraPanel;

	JFrame applFrame;

	JDialog dialog;

	JButton preview;

	JButton print;

	JFrame previewFrame;

	JButton previewPrint;

	JButton previewCancel;

	JCheckBox all;

	JCheckBox g;

	Vector<JCheckBox> gragraRuleChecks; // Elem is JCheckBox

	Vector<JLabel> gragraRuleInfos; // Elem is String

	Vector<JCheckBox> gragraOptionalChecks; // Elem is JCheckBox

	Vector<Vector<JCheckBox>> optionalChecks; // Elem is Vector of JCheckBox

	JDialog ruleCheck; // optional dialog

	Vector<Boolean> optional; // Elem is Boolean

	boolean cancelled = false;

	int W = 468; // Page size

	int H = 648; // Page size

	final static int offset = 10; // space between single images

	Dimension pageable = new Dimension(612, 792);

	Dimension imageable = new Dimension(468, 648);

	double scale = 1.0;

	PageFormat pageFormat;

	Font font = new Font(EditorConstants.FONT_NAME, EditorConstants.FONT_STYLE,
			EditorConstants.FONT_SIZE);

	String fontName = EditorConstants.FONT_NAME;

	int fontStyle = EditorConstants.FONT_STYLE;

	int fontSize = EditorConstants.FONT_SIZE;

	// local class

	class PaintCover implements Printable {
		public int print(Graphics grs, PageFormat pf, int pageIndex)
				throws PrinterException {
//			Font fnt = new Font("Helvetica-Bold", Font.BOLD, 48);
			grs.setFont(GraGraPrint.this.font);
			grs.setColor(Color.black);
			grs.drawString("Graph Grammar", 100, 200);
			return Printable.PAGE_EXISTS;
		}
	}

	class PaintContent implements Printable {

		public int print(Graphics grs, PageFormat pf, int pageIndex)
				throws PrinterException {
			if (this.myPageImages != null) {
				Graphics2D g2D = (Graphics2D) grs;
				// g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				// RenderingHints.VALUE_ANTIALIAS_ON);
				g2D.setFont(GraGraPrint.this.font);
				int x = (int) pf.getImageableX();
				int y = (int) pf.getImageableY();
				for (int i = 0; i < this.myPageImages.size(); i++) {
					Image image = this.myPageImages.elementAt(i);
					g2D.drawImage(image, x, y, null);
					y = y + image.getHeight(null);
				}
				return Printable.PAGE_EXISTS;
			} 
			return Printable.NO_SUCH_PAGE;
		}

		public void setPageImages(Vector<Image> imgs) {
			this.myPageImages = imgs;
		}

		private Vector<Image> myPageImages;

	} // class PaintContent

}
