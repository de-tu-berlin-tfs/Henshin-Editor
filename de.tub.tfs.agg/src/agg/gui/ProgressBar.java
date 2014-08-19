package agg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import agg.gui.saveload.LoadSaveStatus;

/**
 * This progress bar shows a little window which displays the load / save
 * process.
 * 
 * @author $Author: olga $
 * @version $Id: ProgressBar.java,v 1.4 2010/09/23 08:16:05 olga Exp $
 */
public class ProgressBar extends Thread implements Observer {

	/** default tool tip text */
	private final static String TOOLTIPTEXT = "Load / Save Status";

	/** the frame which shows the progress bar */
	private JFrame statusFrame;

	/** the content pane of the main frame */
	private JPanel contentPanel;

	/** the label over the progress bar */
	private JLabel label;

	/** the little bar which grows */
	private JProgressBar statusbar;

	/** displayed in the title and in the label */
	private String statusname;

	/** should the at the end of the process appended at the title or not */
	private boolean finishAppend;

	/** the text which is shown at the end. By default it is empty */
	private String finishText;

	private double oldPercentage = 0.0;

	/** let's build a sweet little frame */
	public ProgressBar(String name) {
		super(name);

		this.contentPanel = new JPanel();
		this.contentPanel.setName("JFrameContentPane");
		this.contentPanel.setToolTipText(TOOLTIPTEXT);
		this.contentPanel.setPreferredSize(new Dimension(600, 50));
		this.contentPanel.setLayout(new BorderLayout());

		this.statusbar = new JProgressBar();
		this.statusbar.setName("ProgressBar");
		this.statusbar.setToolTipText(TOOLTIPTEXT);
		this.statusbar.setDoubleBuffered(true);
		this.statusbar.setBounds(1, 1, 600, 14);
		this.statusbar.setForeground(new Color(0, 255, 127));
		this.statusbar.setBorderPainted(true);

		this.label = new JLabel();
		this.label.setName("Statusname");
		this.label.setToolTipText(TOOLTIPTEXT);
		this.label.setText(this.statusname);
		this.label.setBounds(160, 10, 100, 15);
		this.label.setForeground(new Color(0, 0, 0));

		this.contentPanel.add(new JLabel("Please wait ...   "), BorderLayout.NORTH);
		this.contentPanel.add(this.statusbar, BorderLayout.CENTER);
		this.contentPanel.add(new JLabel("   "), BorderLayout.SOUTH);

		this.finishAppend = true;
		this.finishText = "";

		this.oldPercentage = 0.0;

		LoadSaveStatus.addObserver(this);
	}

	/** pops up a frame and resets the value of the progress bar */
	public void start() {
		LoadSaveStatus.reset();
		Thread t = new Thread(this);
		t.start();
	}

	/** closes the window */
	public void quit() {
		try {
			Thread.sleep(1);
		} catch (Exception e) {
		}
	}

	/** sets the window to the finish value */
	public void finish() {
		LoadSaveStatus.setValue(LoadSaveStatus.getMaximum());
		if (this.finishAppend)
			this.statusname += this.finishText;
		setLabel(this.statusname);
	}

	/** sets the label and title of the status window */
	public void setLabel(String name) {
		this.statusname = name;
		this.label.setText(this.statusname);
	}

	/** sets the finish text */
	public void setFinishText(String text) {
		this.finishText = text;
	}

	/** sets the tool tip text */
	public void setToolTipText(String tooltip) {
		this.label.setToolTipText(tooltip);
		this.statusbar.setToolTipText(tooltip);
		this.contentPanel.setToolTipText(tooltip);
	}

	/** true if the finish text will be appended */
	public void setFinishAppend(boolean b) {
		this.finishAppend = b;
	}

	/** if any data has changed then we need a update */
	public void update(Observable o, Object arg) {
		this.statusbar.setValue(LoadSaveStatus.getValue());
		if (Math.abs(this.oldPercentage - this.statusbar.getPercentComplete()) > 0.1) {
			this.oldPercentage = this.statusbar.getPercentComplete();
			java.awt.Graphics g = this.statusbar.getGraphics();
			if (g != null)
				this.statusbar.update(g);
			// java.awt.Graphics g = statusFrame.getGraphics();
			// if(g != null) statusFrame.update(g);
		}
	}

	public void run() {
		this.statusbar.setMaximum(LoadSaveStatus.getMaximum());
		this.statusbar.setMinimum(LoadSaveStatus.getMinimum());
		this.statusbar.setValue(LoadSaveStatus.getValue());
		// java.awt.Graphics g = this.contentPanel.getGraphics();
		// if(g != null) this.contentPanel.update(g);
		java.awt.Graphics g = this.statusFrame.getGraphics();
		if (g != null)
			this.statusFrame.update(g);
	}

	public Component getContentPanel() {
		return this.contentPanel;
	}

	public void setFrame(JFrame f) {
		this.statusFrame = f;
	}

}
// ======================================================================
// $Log: ProgressBar.java,v $
// Revision 1.4  2010/09/23 08:16:05  olga
// tuning
//
// Revision 1.3  2008/10/29 09:04:10  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.2  2007/09/10 13:05:29  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:03 olga
// Version with Eclipse
//
// Revision 1.3 2003/03/05 18:24:17 komm
// sorted/optimized import statements
//
// Revision 1.2 2002/11/07 16:03:56 olga
// Fehlerbehandlung in TypeEditor
//
// Revision 1.1.1.1 2002/07/11 12:17:11 olga
// Imported sources
//
// Revision 1.3 2000/12/21 12:43:41 olga
// *** empty log message ***
//
// Revision 1.2 2000/12/21 09:49:12 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.1.8.1 2000/11/06 09:32:48 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.1 2000/01/04 13:52:59 shultzke
// Progressbalken fuer das Laden und Speichern
// integriert.
//
