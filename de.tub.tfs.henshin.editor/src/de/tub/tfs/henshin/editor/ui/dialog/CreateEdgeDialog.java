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
package de.tub.tfs.henshin.editor.ui.dialog;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.editor.util.EdgeReferences;

/**
 * The Class CreateEdgeDialog.
 */
public class CreateEdgeDialog extends Dialog {

	/** The graph nodes. */
	private java.util.List<Node> graphNodes;

	/** The source. */
	private Node source;

	/** The target. */
	private Node target;

	/** The edge type. */
	private EReference edgeType;

	/** The title. */
	private String title;

	/** The source list viewer. */
	private ListViewer sourceListViewer;

	/** The target list viewer. */
	private ListViewer targetListViewer;

	/** The type list viewer. */
	private ListViewer typeListViewer;

	/** The source list. */
	private List sourceList;

	/** The target list. */
	private List targetList;

	/** The type list. */
	private List typeList;

	/** The ok button. */
	private Button okButton;

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public Node getSource() {
		return source;
	}

	/**
	 * Gets the target.
	 * 
	 * @return the target
	 */
	public Node getTarget() {
		return target;
	}

	/**
	 * Gets the edge type.
	 * 
	 * @return the edgeType
	 */
	public EReference getEdgeType() {
		return edgeType;
	}

	/**
	 * Instantiates a new creates the edge dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param graph
	 *            the graph
	 * @param title
	 *            the title
	 */
	public CreateEdgeDialog(Shell parentShell, Graph graph, String title) {
		super(parentShell);
		this.title = title;

		if (graph != null) {
			graphNodes = graph.getNodes();
		} else {
			throw new IllegalArgumentException("The given graph is null!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);

		if (title != null) {
			shell.setText(title);
		}

		shell.setSize(600, 400);
		shell.layout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets
	 * .Composite, int, java.lang.String, boolean)
	 */
	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (id == OK) {
			okButton = button;
			okButton.setEnabled(false);
		}
		return button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label sourceLabel = new Label(composite, SWT.NONE);
		sourceLabel.setText("Source: ");

		final Label targetLabel = new Label(composite, SWT.NONE);
		targetLabel.setText("Target: ");

		final Label typeLabel = new Label(composite, SWT.NONE);
		typeLabel.setText("Type: ");

		sourceListViewer = new ListViewer(composite, SWT.READ_ONLY | SWT.BORDER
				| SWT.V_SCROLL);
		sourceList = sourceListViewer.getList();
		sourceList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sourceListViewer.setContentProvider(new ArrayContentProvider());
		sourceListViewer.setLabelProvider(new NodeLabelProvider());
		sourceListViewer.setSorter(new ViewerSorter());
		sourceListViewer.add(graphNodes.toArray());

		targetListViewer = new ListViewer(composite, SWT.READ_ONLY | SWT.BORDER
				| SWT.V_SCROLL);
		targetList = targetListViewer.getList();
		targetList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		targetList.setEnabled(false);
		targetListViewer.setContentProvider(new ArrayContentProvider());
		targetListViewer.setLabelProvider(new NodeLabelProvider());
		targetListViewer.setSorter(new ViewerSorter());

		typeListViewer = new ListViewer(composite, SWT.READ_ONLY | SWT.BORDER
				| SWT.V_SCROLL);
		typeList = typeListViewer.getList();
		typeList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		typeList.setEnabled(false);
		typeListViewer.setContentProvider(new ArrayContentProvider());
		typeListViewer.setSorter(new ViewerSorter());
		typeListViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof EReference) {
					return ((EReference) element).getName();
				}
				return null;
			}
		});

		addListener();

		return composite;
	}

	/**
	 * Adds the listener.
	 */
	private void addListener() {
		sourceListViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						Object selection = getListSelection(sourceList,
								sourceListViewer);
						if (selection instanceof Node) {
							source = (Node) selection;
							refreshTargetListContents();
							refreshTypeListContents();
						}
					}
				});

		targetListViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						Object selection = getListSelection(targetList,
								targetListViewer);
						if (selection instanceof Node) {
							target = (Node) selection;
							refreshTypeListContents();
						}
					}
				});

		typeListViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						Object selection = getListSelection(typeList,
								typeListViewer);
						if (selection instanceof EReference) {
							edgeType = (EReference) selection;
							okButton.setEnabled(true);
						}
					}
				});
	}

	/**
	 * Refresh target list contents.
	 */
	private void refreshTargetListContents() {
		targetList.removeAll();
		if (source != null) {
			for (Node node : graphNodes) {
				if (!EdgeReferences.getSourceToTargetFreeReferences(source,
						node).isEmpty()) {
					targetListViewer.add(node);
				}
			}
		}
		targetList.setEnabled(source != null);
	}

	/**
	 * Refresh type list contents.
	 */
	private void refreshTypeListContents() {
		typeList.removeAll();
		okButton.setEnabled(false);
		if (target != null) {
			java.util.List<EReference> eReferences = EdgeReferences
					.getSourceToTargetFreeReferences(source, target);
			for (EReference ref : eReferences) {
				typeListViewer.add(ref);
			}
		}
		typeList.setEnabled(target != null);
	}

	/**
	 * Gets the list selection.
	 * 
	 * @param list
	 *            the list
	 * @param listViewer
	 *            the list viewer
	 * @return the list selection
	 */
	private Object getListSelection(List list, ListViewer listViewer) {
		Object selection = listViewer.getSelection();
		if (selection != null && selection instanceof StructuredSelection) {
			selection = ((StructuredSelection) selection).getFirstElement();
		}
		return selection;
	}

	/**
	 * The Class NodeLabelProvider.
	 */
	private class NodeLabelProvider extends LabelProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof Node) {
				Node node = (Node) element;
				if (node.getName() != null) {
					return node.getName() + ":" + node.getType().getName();
				}
				return ":" + node.getType().getName();
			} else {
				return null;
			}
		}
	}
}
