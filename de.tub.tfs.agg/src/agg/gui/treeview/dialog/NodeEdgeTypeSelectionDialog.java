/**
 * 
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdType;
import agg.gui.editor.EditorConstants;
import agg.gui.icons.CircleShapeIcon;
import agg.gui.icons.ColorDashLineIcon;
import agg.gui.icons.ColorDotLineIcon;
import agg.gui.icons.ColorSolidLineIcon;
import agg.gui.icons.OvalShapeIcon;
import agg.gui.icons.RectShapeIcon;
import agg.gui.icons.RoundRectShapeIcon;


/**
 * The first button is predefined for YES_OPTION,
 * the second button - otherwise.
 * 
 * @author olga
 *
 */
public class NodeEdgeTypeSelectionDialog {

	public final static int YES_OPTION = 0;
	
	protected final JDialog dialog;
	
	protected final JButton button, button2;
	
	protected boolean cancelled;
	
	protected EdGraGra gragra;
	
	protected EdType nodeType, edgeType;
	
	protected Vector<EdType> edgeTypes, nodeTypes;
	
	protected JComboBox edgeTypeCB, nodeTypeCB;
	
	final Object[] options = {"Continue", "Cancel"};

	
	public NodeEdgeTypeSelectionDialog(final JFrame parent) {
		
		this.dialog = new JDialog(parent, "Select Type");
		this.dialog.setModal(true);
//		this.dialog.setLocationRelativeTo(parent);	
		this.dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				NodeEdgeTypeSelectionDialog.this.dialog.setVisible(false);
			}
		});
		
		this.button = new JButton("Option");
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NodeEdgeTypeSelectionDialog.this.cancelled = false;
				NodeEdgeTypeSelectionDialog.this.dialog.setVisible(false);
			}
		});
		
		this.button2 = new JButton("Option2");
		this.button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NodeEdgeTypeSelectionDialog.this.cancelled = true;
				NodeEdgeTypeSelectionDialog.this.dialog.setVisible(false);
			}
		});
		
		if (this.options.length == 2) {			
			if (this.options[0] instanceof String)
				this.button.setText((String) this.options[0]);
			if (this.options[1] instanceof String)
				this.button2.setText((String) this.options[1]);
		}		
		
		final JPanel content = new JPanel(new BorderLayout());
		initContent(content);
		
		this.dialog.getContentPane().add(content);
		this.dialog.validate();
		
		this.dialog.setPreferredSize(new Dimension(300, 350));
		this.dialog.pack();
	}
	
	public void setLocation(int x, int y) {
		this.dialog.setLocation(x, y);
	}
	
	
	private void initContent(final JPanel content) {
		final JPanel p = new JPanel(new GridLayout(11, 1));
		p.add(new JLabel("                                           "));	
		p.add(new JLabel("                                           "));	
		p.add(new JLabel("        Nodes of selected Node Type        "));	
		p.add(new JLabel("                   and                     "));
		p.add(new JLabel("        edges of selected Edge Type        "));
		p.add(new JLabel("            between these nodes            "));
		p.add(new JLabel("        will be exported to ( .col )       "));
		p.add(new JLabel("        resp. imported from ( .col.res )   "));
		p.add(new JLabel("        ColorGraph format.                 "));
		p.add(new JLabel("                                           "));
		p.add(new JLabel("                                           "));
		
		content.add(p, BorderLayout.NORTH);		
		
		final JPanel p11 = new JPanel(new BorderLayout());
		p11.setBorder(new TitledBorder("  Node  Type  "));
		p11.add(createNodeTypeComboBox(), BorderLayout.CENTER);
		p11.add(new JLabel("           "), BorderLayout.SOUTH);
		
		final JPanel p21 = new JPanel(new BorderLayout());
		p21.setBorder(new TitledBorder("  Edge  Type  "));		
		p21.add(createEdgeTypeComboBox(), BorderLayout.CENTER);
		p21.add(new JLabel("           "), BorderLayout.SOUTH);
		
		final JPanel p3 = new JPanel();	
		p3.add(p11);
		p3.add(p21);
		
		content.add(p3, BorderLayout.CENTER);
		
		final JPanel p5 = new JPanel();
		p5.add(this.button);
		p5.add(new JLabel("          "));
		p5.add(this.button2);
		
		final JPanel p6 = new JPanel(new BorderLayout());
		p6.add(new JLabel("          "), BorderLayout.NORTH);
		p6.add(p5, BorderLayout.CENTER);
		p6.add(new JLabel("          "), BorderLayout.SOUTH);
		
		content.add(p6, BorderLayout.SOUTH);
	}
	
	public boolean isVisible() {
		return this.dialog.isVisible();
	}
	
	public void setVisible(boolean b) {
		this.dialog.setVisible(b);
	}
		
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
	
		updateNodeTypeComboBox(null);
		updateEdgeTypeComboBox(null);
		
		if (this.gragra != null) {
			updateNodeTypeComboBox(this.gragra.getTypeSet().getNodeTypes());
			updateEdgeTypeComboBox(this.gragra.getTypeSet().getArcTypes());
			
//			int indx1 = this.nodeTypeCB.getSelectedIndex();
//			int indx2 = this.edgeTypeCB.getSelectedIndex();
		}
	}
	
	public void setNodeType(final EdType type) {
		this.nodeType = type;
		if (this.nodeType != null) {
			int indx = this.nodeTypes.indexOf(this.nodeType);
//			System.out.println(indx+"   "+ this.nodeType);
			if (indx != -1) {
				final JLabel l = (JLabel) this.nodeTypeCB.getModel().getElementAt(indx+1);
				this.nodeTypeCB.getModel().setSelectedItem(l);
				this.nodeTypeCB.setSelectedIndex(indx+1);
//				System.out.println(indx+"   "+ this.nodeType);
			}
		} else {
			final Object l = this.nodeTypeCB.getModel().getElementAt(0);
			this.nodeTypeCB.getModel().setSelectedItem(l);
			this.nodeTypeCB.setSelectedIndex(0);			
		}
	}
	
	public void setEdgeType(final EdType type) {		
		this.edgeType = type;
		if (this.edgeType != null) {
			int indx = this.edgeTypes.indexOf(this.edgeType);
			if (indx != -1) {
				final JLabel l = (JLabel) this.edgeTypeCB.getModel().getElementAt(indx+1);
				this.edgeTypeCB.getModel().setSelectedItem(l);	
				this.edgeTypeCB.setSelectedIndex(indx+1);
			}
		}
		else {
			final Object l = this.edgeTypeCB.getModel().getElementAt(0);
			this.edgeTypeCB.getModel().setSelectedItem(l);
			this.edgeTypeCB.setSelectedIndex(0);
		}
	}
	
	public EdType getNodeType() {
		return this.nodeType;
	}
	
	public EdType getEdgeType() {
		return this.edgeType;
	}
	
	private JComboBox createNodeTypeComboBox() {
		if (this.nodeTypeCB == null) {
			this.nodeTypeCB = new JComboBox();
			this.nodeTypeCB.setRenderer(new MyCellRenderer(true));
			this.nodeTypeCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (((JComboBox) e.getSource()).getSelectedItem() instanceof JLabel) {
						int indx = ((JComboBox) e.getSource())
								.getSelectedIndex();
						if (indx >= 0) {
							JLabel l = (JLabel) ((JComboBox) e.getSource())
									.getSelectedItem();							
							if (l != null) {
								if (l.getText().equals("ALL")) 
									NodeEdgeTypeSelectionDialog.this.nodeType = null;
								else {
									NodeEdgeTypeSelectionDialog.this.nodeType = NodeEdgeTypeSelectionDialog.this.nodeTypes.get(indx-1);
								}
							}
						}
					}
				}
			});
		}
		return this.nodeTypeCB;
	}
	
	private JComboBox createEdgeTypeComboBox() {
		if (this.edgeTypeCB == null) {
			this.edgeTypeCB = new JComboBox();
			this.edgeTypeCB.setRenderer(new MyCellRenderer(true));
			this.edgeTypeCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (((JComboBox) e.getSource()).getSelectedItem() instanceof JLabel) {
						int indx = ((JComboBox) e.getSource())
								.getSelectedIndex();
						if (indx >= 0) {
							JLabel l = (JLabel) ((JComboBox) e.getSource())
									.getSelectedItem();
							if (l != null) {
								if (l.getText().equals("ALL")) 
									NodeEdgeTypeSelectionDialog.this.edgeType = null;
								else {
									NodeEdgeTypeSelectionDialog.this.edgeType = NodeEdgeTypeSelectionDialog.this.edgeTypes.get(indx-1);
								}
							}
						}
					}
				}
			});
		}
		return this.edgeTypeCB;
	}
	
	private JComboBox updateNodeTypeComboBox(Vector<EdType> nodetypes) {
		this.nodeTypes = nodetypes;
		if (this.nodeTypeCB != null)
			this.nodeTypeCB.removeAllItems();
		
		if (this.nodeTypes == null) {
			return this.nodeTypeCB;
		}
			
		this.nodeTypeCB.addItem(new JLabel("ALL"));		
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.get(i);
			JLabel l = new JLabel(t.getName());
			l.setIcon(getNodeTypeIcon(t));
			l.setForeground(t.getColor());
			this.nodeTypeCB.addItem(l);
		} 
		
		return this.nodeTypeCB;
	}

	
	private JComboBox updateEdgeTypeComboBox(Vector<EdType> arcTypes) {
		this.edgeTypes = arcTypes;
		if (this.edgeTypeCB != null)
			this.edgeTypeCB.removeAllItems();
		
		if (this.edgeTypes == null) {
			return this.edgeTypeCB;
		}

		this.edgeTypeCB.addItem(new JLabel("ALL"));
		for (int i = 0; i < this.edgeTypes.size(); i++) {
			EdType t = this.edgeTypes.get(i);
			JLabel l = new JLabel(t.getName());
			l.setIcon(getArcTypeIcon(t));
			l.setForeground(t.getColor());
			this.edgeTypeCB.addItem(l);
		}

		return this.edgeTypeCB;
	}
	
	private Icon getArcTypeIcon(EdType et) {
		Icon icon = null;
		switch (et.shape) {
		case EditorConstants.SOLID:
			icon = new ColorSolidLineIcon(et.color);
			break;
		case EditorConstants.DASH:
			icon = new ColorDashLineIcon(et.color);
			break;
		case EditorConstants.DOT:
			icon = new ColorDotLineIcon(et.color);
			break;
		default:
			break;
		}
		return icon;
	}

	public Icon getNodeTypeIcon(EdType et) {
		Icon icon = null;
		switch (et.shape) {
		case EditorConstants.RECT:
			icon = new RectShapeIcon(et.color);
			break;
		case EditorConstants.ROUNDRECT:
			icon = new RoundRectShapeIcon(et.color);
			break;
		case EditorConstants.CIRCLE:
			icon = new CircleShapeIcon(et.color);
			break;
		case EditorConstants.OVAL:
			icon = new OvalShapeIcon(et.color);
			break;
		default:
			break;
		}
		return icon;
	}
	
	
	class MyCellRenderer extends JLabel implements ListCellRenderer {

		boolean allowSelect;

		public MyCellRenderer(boolean allowSelect) {
			this.allowSelect = allowSelect;
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList listbox,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (value instanceof JLabel) {
				JLabel l = (JLabel) value;
				// System.out.println(l.getForeground());
				setForeground(l.getForeground());
				setIcon(l.getIcon());
				setText(l.getText());
			} else if (value instanceof String) {
				setText((String) value);
			} else {
				setText("");
			}
			if (this.allowSelect && isSelected)
				setBackground(Color.lightGray);
			else
				setBackground(Color.white);
			setOpaque(true);
			return this;
		}
	}
}
