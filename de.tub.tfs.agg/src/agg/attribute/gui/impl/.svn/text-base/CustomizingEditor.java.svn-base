package agg.attribute.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import agg.attribute.AttrManager;
import agg.attribute.gui.AttrCustomizingEditor;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.gui.HandlerCustomizingEditor;

/**
 * Customizing of an attribute manager as well as his handlers. The
 * implementation uses a tabbed view.
 * 
 * @author $Author: olga $
 * @version $Id: CustomizingEditor.java,v 1.4 2010/08/18 09:24:53 olga Exp $
 */
public class CustomizingEditor extends AbstractEditor implements
		AttrCustomizingEditor {

	protected ManagerCustomizingEditor managerCustomizingEditor = null;

	protected JTabbedPane mainTab, handlerTab;

	protected JPanel handlerPanel;

	public CustomizingEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
	}

	protected void arrangeMainPanel() {
	}

	protected void genericCreateAllViews() {
		this.managerCustomizingEditor = new ManagerCustomizingEditor(
				getAttrManager(), getEditorManager());
		this.handlerTab = new JTabbedPane(SwingConstants.LEFT);

		AttrHandler handlers[] = getAttrManager().getHandlers();
		AttrHandler tmpHandler;
		HandlerCustomizingEditor hce;

		for (int i = 0; i < handlers.length; i++) {
			tmpHandler = handlers[i];
			hce = getHandlerEditorManager().getCustomizingEditor(tmpHandler);
			if (hce != null) {
				this.handlerTab.addTab(tmpHandler.getName(), hce.getComponent());
			}
		}

		this.handlerPanel = new JPanel(new BorderLayout());
		this.handlerPanel.add(this.handlerTab, BorderLayout.CENTER);
	}

	protected void genericCustomizeMainLayout() {
		this.mainTab = new JTabbedPane(SwingConstants.TOP);
		this.mainTab.addTab("Manager", this.managerCustomizingEditor.getComponent());
		this.mainTab.addTab("Handler", this.handlerPanel);
		this.handlerTab.setPreferredSize(new Dimension(200, 100));
		this.handlerPanel.setPreferredSize(new Dimension(200, 100));
		// mainTab.setPreferredSize( new Dimension( 320, 220 ));

		this.mainPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.mainTab, BorderLayout.CENTER);
	}
}

/*
 * $Log: CustomizingEditor.java,v $
 * Revision 1.4  2010/08/18 09:24:53  olga
 * tuning
 *
 * Revision 1.3  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:33:04 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:49 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:07:45 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 1999/12/22 12:36:58 shultzke The user cannot edit the context of
 * graphs. Only in rules it is possible.
 */
