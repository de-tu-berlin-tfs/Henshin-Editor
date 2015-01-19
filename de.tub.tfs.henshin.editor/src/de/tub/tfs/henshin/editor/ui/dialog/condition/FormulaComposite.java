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

import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The Class FormulaComposite.
 */
public class FormulaComposite extends Composite {

	/** The selected formula. */
	private Formula selectedFormula;

	/** The list label. */
	private Label listLabel;

	/** The list viewer. */
	private ListViewer listViewer;

	/**
	 * Instantiates a new formula composite.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public FormulaComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, true));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Create list label
		listLabel = new Label(this, SWT.NONE);
		listLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// Create list viewer
		listViewer = new ListViewer(this, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		listViewer.getList().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
		listViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				String label = null;
				if (element instanceof NestedCondition) {
					label = "Application Condition";
				} else if (element instanceof Not) {
					label = "NOT";
				} else if (element instanceof And) {
					label = "AND";
				} else if (element instanceof Or) {
					label = "OR";
				}
				return label;
			}
		});

		updateListView(null);
	}

	/**
	 * Gets the selected formula.
	 * 
	 * @return the selected formula
	 */
	public Formula getSelectedFormula() {
		Object iSelection = listViewer.getSelection();
		if (iSelection != null && iSelection instanceof StructuredSelection) {
			iSelection = ((StructuredSelection) iSelection).getFirstElement();

			if (iSelection instanceof NestedCondition) {
				selectedFormula = (NestedCondition) iSelection;
			} else if (iSelection instanceof Not) {
				selectedFormula = (Not) iSelection;
			} else if (iSelection instanceof And) {
				selectedFormula = (And) iSelection;
			} else if (iSelection instanceof Or) {
				selectedFormula = (Or) iSelection;
			} else {
				selectedFormula = null;
			}
		}

		return selectedFormula;
	}

	/**
	 * Adds the selection changed listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listViewer.addSelectionChangedListener(listener);
	}

	/**
	 * Update list view.
	 * 
	 * @param formula
	 *            the formula
	 */
	protected void updateListView(Formula formula) {
		if (formula == null) {
			listLabel.setText("Select Root");
		} else if (formula instanceof Not) {
			listLabel.setText("Select NOT-Child");
		} else if (formula instanceof And) {
			listLabel.setText("Select AND-Child");
		} else if (formula instanceof Or) {
			listLabel.setText("Select OR-Child");
		}

		setListContents(formula);
	}

	/**
	 * Gets the list viewer.
	 * 
	 * @return the list viewer
	 */
	protected ListViewer getListViewer() {
		return listViewer;
	}

	/**
	 * Sets the list contents.
	 * 
	 * @param formula
	 *            the new list contents
	 */
	private void setListContents(Formula formula) {
		listViewer.getList().removeAll();
		if (formula == null || !(formula instanceof NestedCondition)) {
			listViewer.add(new Formula[] {
					HenshinFactory.eINSTANCE.createNestedCondition(),
					HenshinFactory.eINSTANCE.createNot(),
					HenshinFactory.eINSTANCE.createAnd(),
					HenshinFactory.eINSTANCE.createOr() });
		}
	}

}
