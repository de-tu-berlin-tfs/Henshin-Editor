/**
 * 
 */
package agg.gui.treeview.nodedata;


import javax.swing.tree.DefaultMutableTreeNode;

import agg.attribute.impl.CondMember;
import agg.editor.impl.EdAtomApplCond;
import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdConstraint;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNAC;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdPAC;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleConstraint;
import agg.editor.impl.EdRuleScheme;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;

/**
 * @author olga
 *
 */
public interface GraGraTreeNodeData {
	
	public boolean isGraGra();
	public boolean isTypeGraph();
	public boolean isGraph();
	public boolean isRule();
	public boolean isRuleScheme();
	public boolean isKernelRule();
	public boolean isMultiRule();
	public boolean isAmalgamatedRule();
	public boolean isNAC();
	public boolean isPAC();
	public boolean isNestedAC();
	public boolean isApplFormula();
	public boolean isAttrCondition();
	public boolean isAtomic();
	public boolean isConclusion();
	public boolean isConstraint();
	public boolean isRuleConstraint();
	public boolean isAtomApplCond();
	public boolean isRuleSequence();
	
	public Object getData();

	/**
	 * Returns the string to display for this object.
	 */
	public String string();
	public String toString();
	public void update();

	public EdGraGra getGraGra();
	public EdGraph getGraph();
	public EdRule getRule();
	public EdRuleScheme getRuleScheme();	
	public EdRule getKernelRule();	
	public EdRule getMultiRule();	
	public EdRule getAmalgamatedRule();	
	public EdAtomic getAtomic();
	public EdAtomic getConclusion();
	public EdConstraint getConstraint();
	public EdNAC getNAC();
	public EdPAC getPAC();
	public EdNestedApplCond getNestedAC();
	public Pair<CondMember, EdRule> getAttrCondition();
	public EdRuleConstraint getRuleConstraint();
	public EdAtomApplCond getAtomApplCond();
	public RuleSequence getRuleSequence();	
	public DefaultMutableTreeNode getTreeNode();
	public String getToolTipText();
	public boolean isTreeTextEditable();
	
	public void setData(Object obj);	
	public void setString(String tag, String tag1, String newString);	
	public void setString(String tag, String newString);	
	public void setString(String str);	
	public void setTreeNode(DefaultMutableTreeNode node);
	
	public void dispose();
	

}