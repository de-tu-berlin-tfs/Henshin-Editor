/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import agg.editor.impl.EdType;
import agg.editor.impl.EdTypeSet;
import agg.gui.editor.EditorConstants;



public class SelectChildOfNodeTypeDialog extends JDialog implements ActionListener {

	final JTree typeTree;
	
	final DefaultTreeModel treeModel;
	
	final EdTypeSet typeSet;
	
	final EdType nodeType;
	
	final Hashtable<DefaultMutableTreeNode, EdType> 
	treeNode2NodeType = new Hashtable<DefaultMutableTreeNode, EdType>();
	
	final JButton Ok, cancel;

	final JPanel dialogPanel;

	JFrame f;

	public SelectChildOfNodeTypeDialog(JFrame parent, EdTypeSet typeset, EdType nodetype) {
		super(parent, " Node type: <"+nodetype.getName()+"> ", true);

		this.typeSet = typeset;
		this.nodeType = nodetype;
		this.typeTree = new JTree();
		
		this.typeTree.setCellRenderer(new MyTreeCellRenderer());
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(this.nodeType.getName(), true);
		this.treeNode2NodeType.put(top, this.nodeType);
		this.treeModel = new DefaultTreeModel(top);
		this.typeTree.setModel(this.treeModel);
		this.typeTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		initTypeTree(top, this.nodeType);
		
		JLabel text = new JLabel("Please select a child type to be created.");
		
		this.Ok = new JButton("Ok");
		this.Ok.addActionListener(this);
		this.cancel = new JButton("Cancel");
		this.cancel.addActionListener(this);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(this.Ok);
		buttons.add(this.cancel);
		JPanel below = new JPanel(new BorderLayout());
		below.add(text, BorderLayout.CENTER);
		below.add(buttons, BorderLayout.SOUTH);

		this.dialogPanel = new JPanel(new BorderLayout());
		this.dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.dialogPanel.add(new JScrollPane(this.typeTree), BorderLayout.CENTER);
		this.dialogPanel.add(below, BorderLayout.SOUTH);
		this.dialogPanel.setPreferredSize(new Dimension(300, 200));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.dialogPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.pack();
		
		this.setLocation(100, 100);
		this.setVisible(true);
	}

	private void initTypeTree(DefaultMutableTreeNode treetypenode, EdType nodetype) {		
		Vector<agg.xt_basis.Type> children = nodetype.getBasisType().getChildren();
		for (int i=0; i<children.size(); i++) {
			agg.xt_basis.Type childType = children.get(i);
			EdType edchildType = this.typeSet.getNodeType(childType);
//			System.out.println(edchildType.getName());
			DefaultMutableTreeNode childnode = new DefaultMutableTreeNode(edchildType.getName(), true);
			
			this.treeNode2NodeType.put(childnode, edchildType);
			
			this.treeModel.insertNodeInto(childnode, treetypenode, i);
			if (!this.typeTree.isExpanded(this.typeTree.getPathForRow(0)))
				this.typeTree.expandPath(this.typeTree.getPathForRow(0));
			
			initTypeTree(childnode, edchildType);						
		}
	}
	
	public void actionPerformed(ActionEvent e) {		
		if (e.getActionCommand().equals("Ok")) {
			if (this.typeTree.getSelectionPath() == null) {
				String msg = "Please select a child type or cancel this dialog.";
				JOptionPane.showMessageDialog(this, msg);
				return;
			} 
			setVisible(false);
		} else 
			setVisible(false);
	}

	public EdType getSelectedChildType() {		
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) this.typeTree.getSelectionPath().getLastPathComponent();
		EdType t = this.treeNode2NodeType.get(treeNode);		
		return t;
	}
	
	
	public class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {
		boolean selected;
		final Color SelectedBackgroundColor = new Color(153, 153, 255);
		
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			String stringValue = tree.convertValueToText(value, sel, expanded,
					leaf, row, hasFocus);
			/* Set the text. */
			setText(stringValue);
			
			EdType userObject = SelectChildOfNodeTypeDialog.this.treeNode2NodeType.get(value);
			if (userObject != null) {
				setIconTextGap(5);
				Icon icon = getNodeTypeIcon(userObject.getShape(), userObject.getColor());
				setIcon(icon);
			}
			
			this.selected = sel;
			
			return this;
		}
		
		public void paint(Graphics g) {
			Color bColor = Color.WHITE;
			Color fColor = Color.BLACK;
			Icon currentI = getIcon();

			if (this.selected)
				bColor = this.SelectedBackgroundColor;
			else
				bColor = Color.WHITE; //getBackground();

			g.setColor(bColor);
			setForeground(fColor);
			if (currentI != null && getText() != null) {
				int offset = (currentI.getIconWidth() + getIconTextGap());
				g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
			} else
				g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
			super.paint(g);
		}
		
		private Icon getNodeTypeIcon(int shape, Color color) {
			Icon icon = null;
			switch (shape) {
			case EditorConstants.RECT:
				icon = (new agg.gui.icons.RectShapeIcon(color));
				break;
			case EditorConstants.ROUNDRECT:
				icon = (new agg.gui.icons.RoundRectShapeIcon(color));
				break;
			case EditorConstants.CIRCLE:
				icon = (new agg.gui.icons.CircleShapeIcon(color));
				break;
			case EditorConstants.OVAL:
				icon = (new agg.gui.icons.OvalShapeIcon(color));
				break;
			default:
				break;
			}
			return icon;
		}
		
		
	}
	
}
