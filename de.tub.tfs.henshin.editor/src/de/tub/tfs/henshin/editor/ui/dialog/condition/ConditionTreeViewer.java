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

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

/**
 * The Class ConditionTreeViewer.
 */
public class ConditionTreeViewer extends TreeViewer implements
		ISelectionChangedListener {

	/** The selected tree node. */
	private FormulaTreeNode selectedTreeNode;

	/** The root node. */
	private FormulaTreeNode rootNode = new FormulaTreeNode();

	private TreeEditor editor;

	private Button okButton;

	/**
	 * Instantiates a new condition tree viewer.
	 * 
	 * @param parent
	 *            the parent
	 */
	public ConditionTreeViewer(Composite parent) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		editor = new TreeEditor(getTree());
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		getTree().addListener(SWT.KeyUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				renameAcTreeNode();
			}
		});

		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				String label = null;
				if (element instanceof FormulaTreeNode) {
					FormulaTreeNode treeNode = (FormulaTreeNode) element;
					label = treeNode.getText();
				}
				return label;
			}
		});

		setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			@Override
			public boolean hasChildren(Object element) {
				boolean hasChildren = false;
				if (element instanceof FormulaTreeNode) {
					FormulaTreeNode treeNode = (FormulaTreeNode) element;
					hasChildren = treeNode.hasChildren();
				}
				return hasChildren;
			}

			@Override
			public Object getParent(Object element) {
				Object parent = null;
				if (element instanceof FormulaTreeNode) {
					FormulaTreeNode treeNode = (FormulaTreeNode) element;
					parent = treeNode.getParent();
				}
				return parent;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				Object[] children = null;
				if (parentElement instanceof FormulaTreeNode) {
					FormulaTreeNode treeNode = (FormulaTreeNode) parentElement;
					children = treeNode.getChildrenAsArray();
				}
				return children;
			}
		});

		addSelectionChangedListener(this);

		setInput(rootNode);
	}

	/**
	 * Gets the selected tree node.
	 * 
	 * @return the selected tree node
	 */
	public FormulaTreeNode getSelectedTreeNode() {
		return selectedTreeNode;
	}

	/**
	 * Gets the root node.
	 * 
	 * @return the root node
	 */
	protected FormulaTreeNode getRootNode() {
		return rootNode;
	}

	/**
	 * Gets the first formula.
	 * 
	 * @return the first formula
	 */
	protected Formula getFirstFormula() {
		FormulaTreeNode firstChildNode = rootNode.getChildren().get(0);
		if (firstChildNode != null) {
			return firstChildNode.getValue();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		Object iSelection = event.getSelection();
		if (iSelection != null && iSelection instanceof StructuredSelection) {
			iSelection = ((StructuredSelection) iSelection).getFirstElement();

			if (iSelection instanceof FormulaTreeNode) {
				selectedTreeNode = (FormulaTreeNode) iSelection;
			} else {
				selectedTreeNode = null;
			}
		}
	}

	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}

	private void renameAcTreeNode() {
		final TreeItem[] selection = getTree().getSelection();
		if (selection.length != 1) {
			return;
		}

		final TreeItem item = selection[0];
		final Object data = item.getData();
		if (data instanceof FormulaTreeNode
				&& ((FormulaTreeNode) data).getValue() instanceof NestedCondition) {
			final boolean okButtonEnabled = okButton.isEnabled();
			okButton.setEnabled(false);

			final NestedCondition ac = (NestedCondition) ((FormulaTreeNode) data)
					.getValue();
			final Graph conclusion = ac.getConclusion();

			final Text text = new Text(getTree(), SWT.NONE);
			text.setText(ac.getConclusion().getName());
			text.selectAll();
			text.setFocus();

			text.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					String editedName = text.getText();
					if (editedName != null && editedName.trim().length() > 0) {
						conclusion.setName(editedName);
						item.setText(editedName);
					}
					text.dispose();
				}
			});

			text.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch (e.keyCode) {
					case SWT.CR:
						String editedName = text.getText();
						if (editedName != null
								&& editedName.trim().length() > 0) {
							conclusion.setName(editedName);
							item.setText(editedName);
							text.dispose();
							okButton.setEnabled(okButtonEnabled);
						}
						break;
					case SWT.ESC:
						text.dispose();
						break;
					default:
						break;
					}
				}
			});

			editor.setEditor(text, item);
		}
	}
}
