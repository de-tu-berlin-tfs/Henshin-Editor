package agg.attribute.gui.impl;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.net.URL;

import agg.attribute.AttrContext;
import agg.attribute.AttrManager;
import agg.attribute.AttrTuple;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.gui.AttrTopEditor;
import agg.attribute.view.AttrViewSetting;
import agg.gui.saveload.GraphicsExportJPEG;

/**
 * Combination of both a context and a full instance editor of an attribute
 * manager. Delegating, most of the time.
 * 
 * @author $Author: olga $
 * @version $Id: TopEditor.java,v 1.14 2010/08/18 09:24:53 olga Exp $
 */
public class TopEditor extends AbstractEditor implements AttrTopEditor {

	protected JPanel titlePanel;
	protected JLabel titleLabel;
	
	protected JPanel instPanel, contextPanel;

	protected JTabbedPane tabbedPane;

	protected JPanel tabPanel;

	protected ContextEditor contextEditor;

	protected FullInstanceTupleEditor instEditor;

	protected CustomizingEditor customEditor;

	protected GraphicsExportJPEG exportJPEG;

	protected JButton exportJPEGButton;

	public TopEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
	}

	protected void genericCreateAllViews() {
		this.contextEditor = new ContextEditor(getAttrManager(), getEditorManager());
		this.instEditor = new FullInstanceTupleEditor(getAttrManager(),
				getEditorManager());
		this.customEditor = new CustomizingEditor(getAttrManager(),
				getEditorManager());
	}

	protected void genericCustomizeMainLayout() {
		this.tabbedPane = new JTabbedPane(SwingConstants.TOP);
		this.tabbedPane.addTab("Attribute Context", this.contextEditor.getComponent());
		this.tabbedPane.addTab("Current Attribute", this.instEditor.getComponent());
		this.tabbedPane.addTab("Customize", this.customEditor.getComponent());
		int i = this.tabbedPane.indexOfTab("Current Attribute");
		this.tabbedPane.setSelectedIndex(i);

		this.tabPanel = new JPanel(new BorderLayout());
		this.tabPanel.add(this.tabbedPane, BorderLayout.CENTER);

		this.exportJPEGButton = createExportJPEGButton();
		this.titlePanel = new JPanel(new BorderLayout());
		this.titleLabel = new JLabel("     ");
		this.titlePanel.add(this.titleLabel, BorderLayout.CENTER);
		this.titlePanel.add(new JLabel("  "), BorderLayout.WEST);
		if (this.exportJPEGButton != null) {
			this.titlePanel.add(this.exportJPEGButton, BorderLayout.EAST);
		}
		
		this.mainPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.titlePanel, BorderLayout.NORTH);

		// mainPanel.setDebugGraphicsOptions( DebugGraphics.LOG_OPTION );
		// mainPanel.setDebugGraphicsOptions( DebugGraphics.FLASH_OPTION );
		// mainPanel.setDebugGraphicsOptions( DebugGraphics.BUFFERED_OPTION );

		this.mainPanel.add(this.tabPanel, BorderLayout.CENTER);

	}

	private JButton createExportJPEGButton() {
		URL imgsrc = ClassLoader.getSystemClassLoader().getResource("agg/lib/icons/print.gif");
		if (imgsrc != null) {
			ImageIcon image = new ImageIcon(imgsrc);
			// System.out.println(image);
			JButton b = new JButton(image);
			b.setToolTipText("Export JPEG");
			b.setMargin(new Insets(-5, 0, -5, 0));
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (TopEditor.this.exportJPEG != null)
						TopEditor.this.exportJPEG.save(TopEditor.this.tabPanel);
				}
			});
			b.setEnabled(true);
			return b;
		} 
		return null;
				
	}

	/** implements some arrangement stuff */
	protected void arrangeMainPanel() {
		int i = this.tabbedPane.indexOfTab("Current Attribute");
		this.tabbedPane.setSelectedIndex(i);
	}

	public void selectAttributeEditor(boolean b) {
		if (b)
			this.tabbedPane.setSelectedIndex(this.tabbedPane
					.indexOfTab("Current Attribute"));
		else
			this.tabbedPane.setSelectedIndex(0);
	}

	public void selectContextEditor(boolean b) {
		if (b)
			this.tabbedPane.setSelectedIndex(this.tabbedPane
					.indexOfTab("Attribute Context"));
		else
			this.tabbedPane.setSelectedIndex(this.tabbedPane
					.indexOfTab("Current Attribute"));
	}

	public void selectCustomEditor(boolean b) {
		if (b)
			this.tabbedPane.setSelectedIndex(this.tabbedPane.indexOfTab("Customize"));
		else
			this.tabbedPane.setSelectedIndex(this.tabbedPane
					.indexOfTab("Current Attribute"));
	}

	public FullInstanceTupleEditor getAttrInstanceEditor() {
		return this.instEditor;
	}

	public ContextEditor getContextEditor() {
		return this.contextEditor;
	}

	public CustomizingEditor getCustomizingEditor() {
		return this.customEditor;
	}

	public void enableContextEditor(boolean b) {
		arrangeMainPanel();
		int i = this.tabbedPane.indexOfTab("Attribute Context");
		if (i != -1)
			this.tabbedPane.setEnabledAt(i, b);
	}

	// Implementation of the AttrContextEditor interface

	public void setContext(AttrContext anAttrContext) {
		this.contextEditor.setContext(anAttrContext);
	}

	public AttrContext getContext() {
		return this.contextEditor.getContext();
	}

	// Implementation of the AttrTupleEditor interface

	public void setAttrManager(AttrManager m) {
		super.setAttrManager(m);
		this.contextEditor.setAttrManager(m);
		this.instEditor.setAttrManager(m);
		this.customEditor.setAttrManager(m);
	}

	public void setEditorManager(AttrEditorManager m) {
		super.setEditorManager(m);
		this.contextEditor.setEditorManager(m);
		this.instEditor.setEditorManager(m);
		this.customEditor.setEditorManager(m);
	}

	public void setTuple(AttrTuple anAttrTuple) {
		this.instEditor.setTuple(anAttrTuple);
	} // setTuple()

	public AttrTuple getTuple() {
		return this.instEditor.getTuple();
	} // getTuple()

	public void setViewSetting(AttrViewSetting anAttrViewSetting) {
		if (this.instEditor != null) {
			this.instEditor.setViewSetting(anAttrViewSetting);
		} 
	}

	public AttrViewSetting getViewSetting() {
		return this.instEditor.getViewSetting();
	} // getViewSetting()

	public String getTitleOfSelectedEditor() {
		return this.tabbedPane.getTitleAt(this.tabbedPane.getSelectedIndex());
	}

	public void setExportJPEG(GraphicsExportJPEG jpg) {
		this.exportJPEG = jpg;
	}

	public void setTitleText(String str) {
		this.titleLabel.setText(str);
	}
	
}
/*
 * $Log: TopEditor.java,v $
 * Revision 1.14  2010/08/18 09:24:53  olga
 * tuning
 *
 * Revision 1.13  2010/03/08 15:36:20  olga
 * code optimizing
 *
 * Revision 1.12  2008/10/29 09:04:14  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.11  2008/09/04 07:46:40  olga
 * Null pointer fixed
 *
 * Revision 1.10  2008/07/21 10:03:28  olga
 * Code tuning
 *
 * Revision 1.9  2007/11/05 09:18:19  olga
 * code tuning
 *
 * Revision 1.8  2007/09/24 09:42:35  olga
 * AGG transformation engine tuning
 *
 * Revision 1.7  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2006/12/13 13:33:04 enrico
 * reimplemented code
 * 
 * Revision 1.5 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.4 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.3 2005/11/16 09:50:57 olga tests
 * 
 * Revision 1.2 2005/09/08 16:25:02 olga Improved: editing attr. condition,
 * importing graph, sorting node/edge types
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.3 2003/03/05 18:24:11 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:51 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.9 2000/04/05 12:07:59 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.8 1999/12/22 12:36:31 shultzke The user cannot edit the context of
 * graphs. Only in rules it is possible.
 * 
 * Revision 1.7 1999/09/13 10:01:10 shultzke ContextEditor dezent eingefaerbt
 * 
 * Revision 1.6 1999/08/17 07:32:31 shultzke GUI leicht geaendert
 */
