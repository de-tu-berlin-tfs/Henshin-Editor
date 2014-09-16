package agg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import agg.gui.event.EditEvent;
import agg.gui.event.EditEventListener;
import agg.gui.event.LoadEvent;
import agg.gui.event.LoadEventListener;
import agg.gui.event.SaveEvent;
import agg.gui.event.SaveEventListener;
import agg.gui.event.TreeViewEvent;
import agg.gui.event.TreeViewEventListener;
import agg.gui.event.TypeEvent;
import agg.gui.event.TypeEventListener;
import agg.gui.icons.MatchIcon;
import agg.gui.icons.TextIcon;
import agg.gui.parser.AGGParser;
import agg.gui.parser.event.StatusMessageEvent;
import agg.gui.parser.event.StatusMessageListener;
import agg.gui.typeeditor.ArcTypePropertyEditor;
import agg.gui.typeeditor.NodeTypePropertyEditor;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;

/**
 * 
 * @author $Author: olga $
 */
public class StatusBar extends JPanel implements SaveEventListener,
		LoadEventListener, TreeViewEventListener, EditEventListener,
		StatusMessageListener, ParserEventListener, TypeEventListener {

	public StatusBar() {
		super(new BorderLayout(), true);
		setBackground(new Color(102, 200, 155));
		
		this.mode = new JLabel("");
		
		final JPanel modePanel = new JPanel(new BorderLayout());
		modePanel.setBackground(new Color(102, 200, 155));
		modePanel.setPreferredSize(new Dimension(40, 40));
		modePanel.add(new JLabel("    "), BorderLayout.WEST);
		modePanel.add(this.mode, BorderLayout.CENTER);
		add(modePanel, BorderLayout.WEST);
		
		this.content = new JPanel(new BorderLayout());
		this.content.setPreferredSize(new Dimension(550, 40));
		this.content.setBackground(new Color(102, 200, 155));
				
		this.statusJSP = new JScrollPane(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.status = new JLabel(this.msg);
		this.status.setForeground(Color.blue);
		this.status.setIcon(new TextIcon(" ", true));
		this.statusJSP.setViewportView(this.status);
		this.content.add(this.statusJSP, BorderLayout.CENTER);
		
		this.typePanel = new JPanel();
		this.typePanel.setBorder(new TitledBorder(""));
		this.typeList = new JList();
		this.typeList.setEnabled(true);
		this.typeList.setModel(new DefaultListModel());
		this.typeList.setCellRenderer(new MyCellRenderer());
		this.typePanel.add(this.typeList);
		this.types = new Vector<JLabel>(2);
		this.types.add(new JLabel("   "));
		this.types.add(new JLabel("   "));
		((DefaultListModel) this.typeList.getModel()).add(0, "");
		((DefaultListModel) this.typeList.getModel()).add(1, "");
		
		this.add(this.content, BorderLayout.CENTER);
		this.add(this.typePanel, BorderLayout.EAST);

		this.modeStr = "";
		this.msg = "";
		this.lastModeStr = this.modeStr;
		this.lastMsg = this.msg;

		final MouseListener ml = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setMsg(StatusBar.this.mode.getToolTipText());
			}
		};
		this.mode.addMouseListener(ml);

		welcome();
	}

	/** Gets my minimum dimension */
	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}

	/** Gets my preferred dimension */
	public Dimension getPreferredSize() {
		return new Dimension(600, 50);
	}

	/** Removes the content of my status panel */
	public void removeContent() {
		this.content.removeAll();
	}

	/** Sets the content of my status panel */
	public void setContent(Component comp) {
		// System.out.println("StatusBar.setContentOfStatus ");
		this.content.removeAll();
		this.content.add(comp, BorderLayout.CENTER);
	}

	/** Resets the content of my main panel */
	public void resetContent() {
		// System.out.println("StatusBar.resetContentOfStatus ");
		this.content.removeAll();
		this.content.add(this.statusJSP, BorderLayout.CENTER);
	}

	/** Returns my status message */
	public String getMsg() {
		return this.msg;
	}

	/** Returns my mode */
	public String getMode() {
		return this.modeStr;
	}

	/*
	private void doResize(String s) {
		// System.out.println("StatusBar.doResize ");
		if (s == null)
			return;
		int l = s.length() * 6;
		if (status.getFontMetrics(status.getFont()) != null)
			l = status.getFontMetrics(status.getFont()).stringWidth(s);
		if (l > content.getWidth()) {
			status.setSize(new Dimension(l, getHeight()));
		}
	}
*/
	
	/** Sets mode */
	public void setMode(String aMode) {
		synchronized (this) {
			this.modeStr = aMode;
		Icon icon = getImageIcon(this.modeStr);
		if (icon != null)
			this.mode.setIcon(icon);
		else
			this.mode.setIcon(new TextIcon(this.modeStr, true));
		this.mode.setToolTipText("");
		}
	}

	/** Sets mode */
	public void setMode(String aMode, String aToolTipText) {
		setMode(aMode);
		this.mode.setToolTipText(aToolTipText);
	}

	/** Sets mode */
	public void setMode(Icon modeIcon, String aToolTipText) {
		this.mode.setIcon(modeIcon);
		this.mode.setToolTipText(aToolTipText);
	}

	/** Sets mode */
	public void setMode(int modekey, String aToolTipText) {
		Icon icon = getImageIcon(modekey);
		if (icon != null)
			setMode(icon, aToolTipText);
	}

	/** Sets mode */
	public void setMode(Object obj) {
		if (obj instanceof String)
			setMode((String) obj, "");
		else if (obj instanceof Icon)
			setMode((Icon) obj, "");
	}

	/** Sets status message */
	public void setMsg(String aMessage) {
		if (this.msg == null || aMessage == null)
			return;
		if (!this.msg.equals(aMessage)) {
			this.msg = aMessage;
			this.status.setText(this.msg);
			
//			doResize(this.msg);
			
//			status.invalidate();
//			status.repaint();
		}
	}

	/** Sets mode */
	public void set(String aMode, String aMessage, String aToolTipText) {
		this.modeStr = aMode;
		setMode(this.modeStr, aToolTipText);
		this.msg = aMessage;
		this.status.setText(this.msg);
//		doResize(this.msg);
	}

	/** Adds String aMessage to the status message */
	public void addMsg(String aMessage) {
		String s = this.msg.concat(" / " + aMessage);
		this.msg = s;
		this.status.setText(this.msg);
//		doResize(this.msg);
	}

	public void reset() {
		if (this.lastModeStr.equals("Hi!")) {
			this.lastModeStr = "";
			this.lastMsg = "";
		}
		setMode(this.lastModeStr);
		setMsg(this.lastMsg);
	}

	public void setFrame(JFrame f) {
//		frame = f;
	}

	/* Implements agg.gui.event.SaveEventListener */
	public void saveEventOccurred(SaveEvent e) {
		// System.out.println("StatusBar.saveEventOccurred " +e.getMsg());
		int msgkey = e.getMsg();
		if (msgkey == SaveEvent.SAVE) {
			if (!this.modeStr.equals("Save")) {
				setMode(new ImageIcon(ClassLoader
						.getSystemResource("agg/lib/icons/save.gif")), "");
				this.lastModeStr = this.modeStr;
				this.lastMsg = this.msg;
				this.modeStr = "Save";
			}
			setMsg(e.getMessage());
			revalidate();
		} else if (msgkey == SaveEvent.PROGRESS_BEGIN) {
			if (e.getUsedComponent() != null) {
				removeContent();
				setContent(e.getUsedComponent());
			}
			return;
		} else if (msgkey == SaveEvent.PROGRESS_FINISHED) {
			if (e.getUsedComponent() != null) {
				removeContent();
				resetContent();
			}
			return;
		}
		setMsg(e.getMessage());
	}

	/* Implements agg.gui.event.LoadEventListener */
	public void loadEventOccurred(LoadEvent e) {
		// System.out.println("StatusBar.loadEventOccurred " +e.getMsg());
		int msgkey = e.getMsg();
		if (msgkey == LoadEvent.LOAD) {
			this.lastModeStr = this.modeStr;
			this.lastMsg =this. msg;
			this.modeStr = "Load";
			setMode(new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/open.gif")), "");
			setMsg(e.getMessage());
		} else if (msgkey == LoadEvent.PROGRESS_BEGIN) {
			if (e.getUsedComponent() != null) {
				removeContent();
				setContent(e.getUsedComponent());
			}
			return;
		} else if (msgkey == LoadEvent.PROGRESS_FINISHED) {
			removeContent();
			resetContent();
			return;
		}
		setMsg(e.getMessage());
	}

	/* Implements agg.gui.event.TreeViewEventListener */
	public void treeViewEventOccurred(TreeViewEvent e) {
		// System.out.println("StatusBar.treeViewEventOccurred "+e.getMsg());
		int msgkey = e.getMsg();
		if (msgkey == TreeViewEvent.CONVERT_STEP) {
			setMsg(e.getMessage());
		}
		if (msgkey == TreeViewEvent.CONVERTED) {
			setMsg(e.getMessage());
		}
		/*
		 * else if(msgkey == TreeViewEvent.SAVE) { if(!modeStr.equals("Save")) {
		 * lastModeStr = modeStr; lastMsg = msg; modeStr = "Save"; setMode(new
		 * ImageIcon(ClassLoader.getSystemResource("agg/lib/icons/save.gif")),
		 * ""); } setMsg(e.getMessage()); revalidate(); } else if(msgkey ==
		 * TreeViewEvent.SAVED) { //setMode(lastModeStr); //setMsg(lastMsg); }
		 * else if(msgkey == TreeViewEvent.LOADED) { //setMode(lastModeStr);
		 * //setMsg(lastMsg); }
		 */
	}

	/* Implements agg.gui.event.EditEventListener */
	public void editEventOccurred(EditEvent e) {		
//		System.out.println("StatusBar.editEventOccurred:  "+e.getSource().getClass().getName()+"  ::  "+e.getMsg());
		int msgkey = e.getMsg();
		if (msgkey == agg.gui.editor.EditorConstants.DRAW
				|| msgkey == agg.gui.editor.EditorConstants.SELECT
				|| msgkey == agg.gui.editor.EditorConstants.MOVE
				|| msgkey == agg.gui.editor.EditorConstants.ATTRIBUTES
				|| msgkey == agg.gui.editor.EditorConstants.MAP
				|| msgkey == agg.gui.editor.EditorConstants.UNMAP
				|| msgkey == agg.gui.editor.EditorConstants.INTERACT_MATCH
				|| msgkey == agg.gui.editor.EditorConstants.VIEW) {
			// setMode(e.getMode(), e.getMessage());
			this.modeStr = e.getMode();
			setMode(msgkey, e.getMessage());
			setMsg(e.getMessage());
		} else if (msgkey == EditEvent.EDIT_PROCEDURE) {
			setMsg(e.getMessage());
			// System.out.println("StatusBar: "+status.getText());
		} else if (msgkey == EditEvent.NO_EDIT_PROCEDURE) {
			this.modeStr = "";
			setMode("", "");
			setMsg(e.getMessage());
		}
	}

	/* Implements agg.gui.parser.event.StatusMessageListener */
	public void newMessage(StatusMessageEvent e) {
		// System.out.println("StatusBar.StatusMessageEvent ");
		// System.out.println("von: "+e.getSource());
		// System.out.println("Msg: "+e.getMessage());
		// System.out.println("AGG mode: "+modeStr);
		if ((e.getSource() instanceof agg.gui.cpa.CriticalPairAnalysisGUI)
				|| (e.getSource() instanceof agg.gui.cpa.CriticalPairAnalysis)) {
			if (!this.modeStr.equals("CriticPairs")) {
				this.lastModeStr = this.modeStr;
				this.lastMsg = this.msg;
			}
			this.modeStr = "CriticPairs";
			setMode(new TextIcon("CP", true), "");

			String s = "";
			if (e.getMessage().indexOf("finished") != -1) {
				if (e.getMessage().indexOf("Critical") != -1) {
					s = " You can select rule pairs on the left to see results.";
					System.out.println(s);
				}
			} else if (e.getMessage().indexOf("back") != -1) {
				this.lastMsg = "";
				reset();
			}
			if (e.getMessage() != "")
				setMsg(e.getMessage() + s);
		} else if (e.getSource() instanceof AGGParser) {
			if (!this.modeStr.equals("Parser")) {
				this.lastModeStr = this.modeStr;
				this.lastMsg = this.msg;
			}
			this.modeStr = "Parser";
			setMode(new TextIcon("P", true), "");

			String s = "";
			if (e.getMessage().indexOf("finished") != -1) {
				if (e.getMessage().indexOf("Critical") != -1) {
					s = " Please choice menu  - Parser / Start -  to start parsing.";
					System.out.println(s);
				}
			} else if (e.getMessage().indexOf("back") != -1) {
				this.lastMsg = "";
				reset();
			}
			if (e.getMessage() != "")
				setMsg(e.getMessage() + s);
		}
		// System.out.println("AGG mode: "+modeStr);
	}

	/* Implements agg.parser.ParserEventListener */
	public void parserEventOccured(ParserEvent e) {
		// System.out.println("StatusBar.parserEventOccured "+e.getMessage());
		// System.out.println("von: "+e.getSource());
		// System.out.println("Msg: "+e.getMessage());
		if (e.getMessage() != null) {
			String s = "";
			if (e.getMessage().indexOf("Starting") != -1) {
				if (!this.modeStr.equals("Parser") && !this.modeStr.equals("CriticPairs")) {
					this.lastModeStr = this.modeStr;
					this.lastMsg = this.msg;
				}
			} else if (e.getMessage().indexOf("Generate") != -1) {
				if (!this.modeStr.equals("Parser") && !this.modeStr.equals("CriticPairs")) {
					this.lastModeStr = this.modeStr;
					this.lastMsg = this.msg;
				}
			} else if (e.getMessage().indexOf("loaded") != -1) {
				if (!this.modeStr.equals("Parser") && !this.modeStr.equals("CriticPairs")) {
					this.lastModeStr = this.modeStr;
					this.lastMsg = this.msg;
				}
			} else if (e.getMessage().indexOf("finished") != -1) {
				if (this.modeStr.equals("Parser")
						&& (e.getMessage().indexOf("Critical") != -1)) {
					s = " Please choice menu  - Parser / Start -  to start parsing.";
				}
				// else if( modeStr.equals("CriticPairs") )
				// s = " You can select rule pairs to see results.";
				else
					s = "";
			}
			if (!s.equals(""))
				setMsg(s);
			else
				setMsg(e.getMessage());
		}
	}

	public void welcome() {
		setMode("Hi !", "");
		setMsg("    Welcome to AGG - The Attributed Graph Grammar System.");
	}

	private Icon getImageIcon(int modekey) {
		Icon image = null;
		if (modekey == agg.gui.editor.EditorConstants.DRAW)
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/draw_mode.gif"));
		else if (modekey == agg.gui.editor.EditorConstants.SELECT)
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/select_mode.gif"));
		else if (modekey == agg.gui.editor.EditorConstants.MOVE)
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/move_mode.gif"));
		else if (modekey == agg.gui.editor.EditorConstants.ATTRIBUTES)
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/attributes_mode.gif"));
		else if (modekey == agg.gui.editor.EditorConstants.MAP)
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/map_mode.gif"));
		else if (modekey == agg.gui.editor.EditorConstants.UNMAP)
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/unmap_mode.gif"));
		else if (modekey == agg.gui.editor.EditorConstants.INTERACT_MATCH) {
			MatchIcon matchIcon = new MatchIcon(Color.black);
			matchIcon.setEnabled(true);
			image = matchIcon;
		}
		// else if(modekey == agg.editor.impl.EditorConstants.VIEW)
		// image = new TextIcon("V", true);
		return image;
	}

	private Icon getImageIcon(String modestr) {
		Icon image = null;
		if (modestr.equals("Draw"))
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/draw_mode.gif"));
		else if (modestr.equals("Select"))
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/select_mode.gif"));
		else if (modestr.equals("Move"))
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/move_mode.gif"));
		else if (modestr.equals("Attributes"))
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/attributes_mode.gif"));
		else if (modestr.equals("Map"))
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/map_mode.gif"));
		else if (modestr.equals("Unmap"))
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/unmap_mode.gif"));
		else if (modestr.equals("Match")) {
			MatchIcon matchIcon = new MatchIcon(Color.black);
			matchIcon.setEnabled(true);
			image = matchIcon;
		} else if (modestr.equals("Load")) {
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/open.gif"));
		} else if (modestr.equals("Save")) {
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/save.gif"));
		} else if (modestr.equals("Print")) {
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/print.gif"));
		} else if (modestr.equals("Parser")) {
			image = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/Baustelle.gif"));
		}
		// else if(modestr.equals("View"))
		// image = new TextIcon("V", true);

		return image;
	}

	
	/** Implements TypeEventListener.typeEventOccurred */
	public void typeEventOccurred(TypeEvent e) {
//		 System.out.println("StatusBar.typeEventOccurred:: "+e.getSource());
		if (e.getMsg() == TypeEvent.SELECTED_NODE_TYPE) 
			updateTypes(0, (JLabel) e.getUsedObject());
		else if (e.getMsg() == TypeEvent.SELECTED_ARC_TYPE) 
			updateTypes(1, (JLabel) e.getUsedObject());	
		else if (e.getMsg() == TypeEvent.SELECTED) {
			updateTypes(e.getIndexOfObject(), (JLabel) e.getUsedObject());
		}
		else if (e.getMsg() == TypeEvent.MODIFIED_CHANGED) {
			if (e.getSource() instanceof NodeTypePropertyEditor) {
				updateTypes(0, ((NodeTypePropertyEditor)e.getSource()).
						getTypeEditor().getTypePalette().getSelectedNodeTypeLabel());
			}
			else if (e.getSource() instanceof ArcTypePropertyEditor) {
				updateTypes(1, ((ArcTypePropertyEditor)e.getSource()).
						getTypeEditor().getTypePalette().getSelectedArcTypeLabel());
			}
		}
	}
	
	public void updateTypes(int index, JLabel typeLabel) {
		this.types.remove(index);
		if (typeLabel == null)
			this.types.add(index, new JLabel(""));
		else
			this.types.add(index, typeLabel);

		String typeName = "";
		if (typeLabel != null)
			typeName = typeLabel.getText();	
		((DefaultListModel) this.typeList.getModel()).remove(index);
		((DefaultListModel) this.typeList.getModel()).add(index, typeName);
	}
	
	
//	 Display an icon and a string for each object in the list.
	class MyCellRenderer extends JLabel implements ListCellRenderer {
		// This is the only method defined by ListCellRenderer.
		// We just reconfigure the JLabel each time we're called.

		public Component getListCellRendererComponent(JList list, 
				Object value, // value to display
				int index, // cell index
				boolean isSelected, // is the cell selected
				boolean cellHasFocus) // the list and the cell have the focus
		{
			String str = "";
			if (value.toString().length()>0)
				str = value.toString()+" ";

			if (list == StatusBar.this.typeList && !StatusBar.this.types.isEmpty()) {
				Icon icon = StatusBar.this.types.get(index).getIcon();
				setIcon(icon);
				setForeground(StatusBar.this.types.get(index).getForeground());
			} 

			setText(str);
			
			setBackground(list.getBackground());

			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}

	}
	
	
//	private JFrame frame;

	private final JPanel typePanel;
	
	final JList typeList;
	
	final Vector<JLabel> types;
	
	final JLabel mode;

	private final JPanel content;

	private final JScrollPane statusJSP;

	private final JLabel status;

	private String msg = "";

	private String modeStr = "";

	private String lastModeStr = "";

	private String lastMsg = "";

}
