/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.editor.ui.dialog.condition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.FormulaUtil;

/**
 * The Class FormulaTreeNode.
 */
public class FormulaTreeNode {

	/** A list of tree node's children. */
	private List<FormulaTreeNode> children;

	/** Data that represented by this node. */
	protected Formula value;

	/** Node's parent. */
	protected FormulaTreeNode parent;

	/**
	 * This variable is only needed by binary formula to mark whether the node
	 * to add is a first or second child.
	 */
	private boolean isSecondNode = false;

	/**
	 * Instantiates a new formula tree node.
	 */
	public FormulaTreeNode() {
		this(null, null, false);
	}

	/**
	 * Instantiates a new formula tree node.
	 * 
	 * @param parent
	 *            the parent
	 * @param value
	 *            the value
	 * @param addToParent
	 *            the add to parent
	 */
	public FormulaTreeNode(FormulaTreeNode parent, Formula value,
			boolean addToParent) {
		this(parent, value, addToParent, false);
	}

	/**
	 * Instantiates a new formula tree node.
	 * 
	 * @param parent
	 *            the parent
	 * @param value
	 *            the value
	 * @param addToParent
	 *            the add to parent
	 * @param isSecondNode
	 *            the is second node
	 */
	public FormulaTreeNode(FormulaTreeNode parent, Formula value,
			boolean addToParent, boolean isSecondNode) {
		this.parent = parent;
		this.value = value;
		this.isSecondNode = isSecondNode;
		if (addToParent && parent != null) {
			parent.addChild(this);
		}
	}

	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	public Image getImage() {
		// TODO
		return null;
	}

	/**
	 * Returns the data (entity) of the node.
	 * 
	 * @return the value
	 */
	public Formula getValue() {
		return value;
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public List<FormulaTreeNode> getChildren() {
		return children;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		if (value == null) {
			return null;
		}

		if (value instanceof NestedCondition) {
			Graph conclusion = ((NestedCondition) value).getConclusion();
			if (conclusion == null) {
				conclusion = HenshinFactory.eINSTANCE.createGraph();
				conclusion.setName("AC");
				((NestedCondition) value).setConclusion(conclusion);
			}

			return conclusion.getName();
		} else {
			return FormulaUtil.getText(value);
		}
	}

	/**
	 * Checks if is complete.
	 * 
	 * @return true, if is complete
	 */
	public boolean isComplete() {
		if (isRootNode()) {
			FormulaTreeNode childNode = children.get(0);
			return (childNode.value instanceof NestedCondition)
					|| childNode.isComplete();
		} else {
			if (value instanceof NestedCondition) {
				return true;
			} else if (value instanceof UnaryFormula) {
				return children != null && children.size() == 1
						&& children.get(0).isComplete();
			} else if (value instanceof BinaryFormula) {
				return children != null && children.size() == 2
						&& children.get(0).isComplete()
						&& children.get(1).isComplete();
			}
		}

		return false;
	}

	/**
	 * Returns this node's child nodes.
	 * 
	 * @return the children as array
	 */
	public FormulaTreeNode[] getChildrenAsArray() {
		FormulaTreeNode[] childrenArray = null;
		if (children != null) {
			childrenArray = children.toArray(new FormulaTreeNode[children
					.size()]);
		} else {
			childrenArray = new FormulaTreeNode[0];
		}
		return childrenArray;
	}

	/**
	 * Returns true if this node has children, false otherwise.
	 * 
	 * @return true, if successful
	 */
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	/**
	 * Returns the parent node or null if this is the root node.
	 * 
	 * @return the parent
	 */
	public FormulaTreeNode getParent() {
		return parent;
	}

	/**
	 * Adds the child.
	 * 
	 * @param child
	 *            the child
	 */
	private void addChild(FormulaTreeNode child) {
		if (child == null) {
			throw new NullPointerException();
		} else if (children == null) {
			children = new ArrayList<FormulaTreeNode>();
		}

		if ((isRootNode() || this.value instanceof UnaryFormula)) {
			if (!children.isEmpty()) {
				children.clear();
			}
			children.add(child);
		}

		else if (this.value instanceof BinaryFormula) {
			// The default position to add is at the end of the list
			int position = children.size();
			for (int i = 0; i < children.size(); i++) {
				FormulaTreeNode node = children.get(i);
				if (node.isSecondNode == child.isSecondNode) {
					children.remove(node);
					position = i;
					break;
				}
			}
			children.add(position, child);
		}

		addFormula(child);
	}

	/**
	 * Adds the formula.
	 * 
	 * @param child
	 *            A formula tree node whose value has to be added to the current
	 *            formula.
	 */
	private void addFormula(FormulaTreeNode child) {
		Formula formulaToAdd = child.value;
		if (this.value instanceof UnaryFormula) {
			((UnaryFormula) this.value).setChild(formulaToAdd);
		} else if (this.value instanceof BinaryFormula) {
			BinaryFormula binaryFormula = (BinaryFormula) this.value;
			if (child.isSecondNode) {
				binaryFormula.setRight(formulaToAdd);
			} else {
				binaryFormula.setLeft(formulaToAdd);
			}
		}
	}

	/**
	 * Checks if is root node.
	 * 
	 * @return true, if is root node
	 */
	private boolean isRootNode() {
		return this.value == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value == null ? "root" : value.toString();
	}
}
