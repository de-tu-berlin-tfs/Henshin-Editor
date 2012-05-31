package agg.gui.treeview;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import agg.xt_basis.agt.MultiRule;
import agg.gui.IconResource;
import agg.gui.icons.NestedACIcon;
import agg.gui.icons.NewConclusionIcon;
import agg.gui.icons.TextIcon;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;

/**
 * @author $Author: olga $
 * @version $Id: GraGraTreeCellRenderer.java,v 1.10 2010/09/23 08:22:47 olga Exp $
 */
public class GraGraTreeCellRenderer extends JLabel implements TreeCellRenderer {

	/** Whether or not the item that was last configured is selected. */
	protected boolean selected;

	/** Whether or not the item that is a rule is not applicable. */
	protected boolean notApplicable;

	/** Whether or not the item that is a rule is a trigger rule of a layer. */
	protected boolean isTriggerRule = false;

	protected boolean isKernelRule = false;
	
	protected boolean isFormula = false;
	
	/** Color to use for the background when selected. */
	static protected final Color SelectedColor = new Color(153, 153,
			255);

	/** Color to use for the background when a rule is not applicable. */
	static protected final Color NotApplicableColor = Color.LIGHT_GRAY;

	/**
	 * Color to use for the background when a rule is a trigger rule of its
	 * layer.
	 */
	static protected final Color TriggerRuleColor = Color.RED;

	static protected final Color RuleFormulaColor = Color.BLUE;
	
	
	public GraGraTreeCellRenderer() {
	}
		
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		
		String stringValue = tree.convertValueToText(value, sel, expanded,
				leaf, row, hasFocus);

		/* Set the text. */	
		setText(stringValue);
		setIconTextGap(5);
		
		GraGraTreeNodeData userObject = (GraGraTreeNodeData) ((DefaultMutableTreeNode) value)
				.getUserObject();

		this.notApplicable = false;
		this.isTriggerRule = false;
		this.isKernelRule = false;
		this.isFormula = false;
		
		if (userObject.isGraGra()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLGraGra()));
			setToolTipText(userObject.getToolTipText());
		}
		// check first if it is a type graph, because
		// a type graph is also a graph
		else if (userObject.isTypeGraph()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLTypeGraph()));
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isGraph()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLGraph()));
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isRuleScheme()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLRuleScheme()));			
			this.notApplicable = !userObject.getRuleScheme().getKernelRule().isApplicable();			
//			isTrigger = userObject.getRule().getBasisRule().isTriggerOfLayer();
			setToolTipText(userObject.getToolTipText());		
		} 
		else if (userObject.isKernelRule()) {
			this.isKernelRule = true;
			setIcon(IconResource.getIconFromURL(IconResource.getURLRule()));			
			this.notApplicable = !userObject.getKernelRule().isApplicable();
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isMultiRule()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLRule()));			
			this.notApplicable = !((MultiRule)userObject.getMultiRule().getBasisRule()).getRuleScheme().isApplicable();
			setToolTipText(userObject.getToolTipText());
		}
		else if (userObject.isAmalgamatedRule()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLRule()));
			setToolTipText(userObject.getToolTipText());
		}
		else if (userObject.isRule()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLRule()));			
			this.notApplicable = false;
			if (!userObject.getRule().isApplicable())
				this.notApplicable = true;			
			this.isTriggerRule = userObject.getRule().getBasisRule().isTriggerOfLayer();			
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isNestedAC()) {
			final NestedACIcon icon = new NestedACIcon(Color.blue);
			setIcon(icon);
			setToolTipText(userObject.getToolTipText());			
			this.notApplicable = !userObject.getNestedAC().getRule().isApplicable();
		} 
		else if (userObject.isNAC()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLNAC()));
			setToolTipText(userObject.getToolTipText());			
			this.notApplicable = !userObject.getNAC().getRule().isApplicable();
		} 
		else if (userObject.isPAC()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLPAC()));
			setToolTipText(userObject.getToolTipText());			
			this.notApplicable = !userObject.getPAC().getRule().isApplicable();
		} 
		else if (userObject.isAttrCondition()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLAttrCondition()));
			setToolTipText(userObject.getToolTipText());
			this.notApplicable = !userObject.getAttrCondition().second.isApplicable();
		} 
		else if (userObject.isAtomic()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLAtomic()));
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isConclusion()) {
			NewConclusionIcon icon = new NewConclusionIcon(Color.blue);
			icon.setEnabled(true);
			setIcon(icon);
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isConstraint()) {
			// setIcon(IconResource.getIconFromURL(IconResource.getURLFormula()));
			setIcon(IconResource.getIconFromURL(IconResource.getURLConstraint()));
			setToolTipText(userObject.getToolTipText());
		} 
		else if (userObject.isRuleConstraint()) {
			setIcon(IconResource.getIconFromURL(IconResource.getURLPost()));
			setToolTipText(userObject.getToolTipText());
			this.notApplicable = !userObject.getRuleConstraint().getRule().isApplicable();
		} 
		else if (userObject.isAtomApplCond()) {
			setIcon(IconResource
					.getIconFromURL(IconResource.getURLAtomConstr()));
			setToolTipText(userObject.getToolTipText());
			this.notApplicable = !userObject.getAtomApplCond().getRule().isApplicable();
		}  
		else if (userObject.isRuleSequence()) {
			setIcon(null);
			setToolTipText(userObject.getToolTipText());
		}
		else if (userObject.isApplFormula()) {
			TextIcon icon = new TextIcon("F", true);
			icon.setColor(Color.red);
			setIcon(icon);
			setToolTipText(userObject.getToolTipText());
			this.isFormula = true;
		}
		else {
			setIcon(null);
			setToolTipText("");
		}
		
		this.selected = sel;		
		return this;
	}

	public void paintComponent(Graphics g) {		
		Color bColor = Color.WHITE;
		Color fColor = Color.BLACK;
//		Icon currentI = getIcon();

		if (this.selected)
			bColor = SelectedColor;
		else if (this.notApplicable)
			fColor = NotApplicableColor;
		else if (this.isTriggerRule || this.isKernelRule)
			fColor = TriggerRuleColor;
		else if (this.isFormula)
			fColor = RuleFormulaColor;
		else if (getParent() != null) {
			/*
			 * Pick background color up from parent (which will come from the
			 * JTree we're contained in).
			 */
			bColor = getParent().getBackground();
		} else
			bColor = getBackground();

		g.setColor(bColor);
		setForeground(fColor);
		
		g.fillRect(0, 0, getWidth(), getHeight());
		
//		if (currentI != null && getText() != null) {
//			int offset = (currentI.getIconWidth() + getIconTextGap());
//			g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
//		} else
//			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		
		super.paintComponent(g);
	}


}
