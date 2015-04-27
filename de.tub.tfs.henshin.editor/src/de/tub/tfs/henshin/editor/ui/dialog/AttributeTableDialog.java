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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.tub.tfs.henshin.editor.util.validator.TypeEditorValidator;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class AttributeTableDialog extends org.eclipse.swt.widgets.Dialog {

	/** The dialog shell. */
	private Shell dialogShell;

	/** The table1. */
	private Table table1;

	/** The button1. */
	private Button button1;

	/** The table column1. */
	private TableColumn tableColumn1;

	/** The button2. */
	private Button button2;

	/** The table column2. */
	private TableColumn tableColumn2;

	/** The table column3. */
	private TableColumn tableColumn3;

	/** The Constant corect. */
	private static final Boolean corect = new Boolean(true);

	/** The Constant incorect. */
	private static final Boolean incorect = new Boolean(false);

	/** The cancel. */
	private boolean cancel;

	/** The attribute typen. */
	private List<EAttribute> attributeTypen;

	/** The attribute2 value. */
	private Map<EAttribute, String> attribute2Value;

	/** The node. */
	private Node node;

	private Map<TypeEditorValidator, Boolean> vavueCorect = new HashMap<TypeEditorValidator, Boolean>();
	private List<TypeEditorValidator> typeEditorValidators = new ArrayList<TypeEditorValidator>();

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param node
	 *            the node
	 * @param attribute
	 *            the attribute
	 */

	public AttributeTableDialog(Shell parent, int style, Node node,
			List<EAttribute> attribute) {
		super(parent, style);
		this.node = node;
		this.attributeTypen = attribute;
		cancel = true;
	}

	/**
	 * Open.
	 */
	public void open() {
		try {
			attribute2Value = new HashMap<EAttribute, String>();
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			{
				button2 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData button2LData = new FormData();
				button2LData.left = new FormAttachment(0, 1000, 330);
				button2LData.top = new FormAttachment(0, 1000, 310);
				button2LData.width = 99;
				button2LData.height = 25;
				button2.setLayoutData(button2LData);
				button2.setText("Cancel");
				button2.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						cancel = true;
						dialogShell.close();
					}
				});

			}

			{
				button1 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left = new FormAttachment(0, 1000, 229);
				button1LData.top = new FormAttachment(0, 1000, 310);
				button1LData.width = 95;
				button1LData.height = 25;
				button1.setLayoutData(button1LData);
				button1.setText("Ok");
				button1.setEnabled(true);
				button1.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						cancel = false;
						for (int i = 0, n = table1.getItemCount(); i < n; i++) {
							TableItem item = table1.getItem(i);
							if (item.getChecked()) {
								EAttribute attribut = attributeTypen.get(i);
								attribute2Value.put(attribut, item.getText(2));
							}
						}
						dialogShell.close();
					}
				});
			}
			{
				FormData table1LData = new FormData();
				table1LData.left = new FormAttachment(0, 1000, 12);
				table1LData.top = new FormAttachment(0, 1000, 12);
				table1LData.width = 400;
				table1LData.height = 269;
				table1 = new Table(dialogShell, SWT.CHECK | SWT.SINGLE
						| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL
						| SWT.VIRTUAL);
				table1.setLayoutData(table1LData);
				table1.setHeaderVisible(true);
				table1.setLinesVisible(true);
				{
					tableColumn1 = new TableColumn(table1, SWT.CENTER);
					tableColumn1.setText("Create");
					tableColumn1.setWidth(60);
				}
				{
					tableColumn2 = new TableColumn(table1, SWT.LEFT);
					tableColumn2.setText("Attribute");
					tableColumn2.setWidth(200);
				}
				{
					tableColumn3 = new TableColumn(table1, SWT.LEFT);
					tableColumn3.setText("Value");
					tableColumn3.setWidth(140);
				}

				vavueCorect = new HashMap<TypeEditorValidator, Boolean>();
				typeEditorValidators = new ArrayList<TypeEditorValidator>();

				for (EAttribute attr : attributeTypen) {
					TableItem tableItem = new TableItem(table1, SWT.NONE);
					TypeEditorValidator typeEditorValidator = new TypeEditorValidator(
							node, attr);

					tableItem.setText(1, attr.getName());
					tableItem.setText(2, typeEditorValidator.getDefaultValue());
					table1.addListener(SWT.Selection, new Listener() {

						@Override
						public void handleEvent(Event event) {
							if (event.detail == SWT.CHECK) {
								TableItem tItem = (TableItem) event.item;
								corectValueTest(tItem, tItem.getText(2));
								refreshOkButton();
							}

						}
					});
					typeEditorValidators.add(typeEditorValidator);
					/*
					 * if (typeEditorValidator.isValid(typeEditorValidator
					 * .getDefaultValue()) == null) {
					 * vavueCorect.put(typeEditorValidator, corect); } else {
					 * vavueCorect.put(typeEditorValidator, incorect);
					 * button1.setEnabled(false); }
					 */
				}
				final TableEditor editor = new TableEditor(table1);
				editor.horizontalAlignment = SWT.LEFT;
				editor.grabHorizontal = true;

				table1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent event) {
						// Dispose any existing editor
						Control old = editor.getEditor();
						if (old != null)
							old.dispose();

						// Determine where the mouse was clicked
						Point pt = new Point(event.x, event.y);

						// Determine which row was selected
						final TableItem item = table1.getItem(pt);
						if (item != null) {
							// Determine which column was selected
							int column = -1;
							for (int i = 0, n = table1.getColumnCount(); i < n; i++) {
								Rectangle rect = item.getBounds(i);
								if (rect.contains(pt)) {
									// This is the selected column
									column = i;
									break;
								}
							}
							// Column 2 holds dropdowns
							if (column == 2) {
								// Create the Text object for our editor
								final Text text = new Text(table1, SWT.NONE);
								text.setForeground(item.getForeground());

								// Transfer any text from the cell to the Text
								// control,
								// set the color to match this row, select the
								// text,
								// and set focus to the control
								text.setText(item.getText(column));
								text.setForeground(item.getForeground());
								text.selectAll();
								text.setFocus();

								// Recalculate the minimum width for the editor
								editor.minimumWidth = text.getBounds().width;

								// Set the control into the editor
								editor.setEditor(text, item, column);

								// Add a handler to transfer the text back to
								// the cell
								// any time it's modified
								final int col = column;
								text.addModifyListener(new ModifyListener() {

									@Override
									public void modifyText(ModifyEvent e) {
										item.setChecked(true);
										corectValueTest(item, text.getText());
										item.setText(col, text.getText());
									}
								});
							}
						}
					}
				});

			}
			dialogShell.layout();
			dialogShell.setSize(450, 380);
			Rectangle shellBounds = getParent().getBounds();
			Point dialogSize = dialogShell.getSize();
			dialogShell.setLocation(shellBounds.x
					+ (shellBounds.width - dialogSize.x) / 2, shellBounds.y
					+ (shellBounds.height - dialogSize.y) / 2);
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the assigment.
	 * 
	 * @return the assigment
	 */
	public Map<EAttribute, String> getAssigment() {
		return attribute2Value;
	}

	/**
	 * Checks if is cancel.
	 * 
	 * @return the cancel
	 */
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * @param vavueCorect
	 */
	private void refreshOkButton() {
		if (vavueCorect.containsValue(incorect)) {
			button1.setEnabled(false);
		} else {
			button1.setEnabled(true);
		}
	}

	/**
	 * @param item
	 * @param text
	 */
	private void corectValueTest(final TableItem item, String text) {
		TypeEditorValidator tEV = typeEditorValidators
				.get(table1.indexOf(item));
		String s = tEV.isValid(text);
		if (item.getChecked()) {
			if (s == null) {
				item.getParent().setToolTipText("");
				if (vavueCorect.get(tEV) != corect) {
					vavueCorect.put(tEV, corect);
				}
			} else {
				item.getParent().setToolTipText(s);
				if (vavueCorect.get(tEV) != incorect) {
					vavueCorect.put(tEV, incorect);
				}
			}

		} else {
			vavueCorect.remove(tEV);
		}
		refreshOkButton();
	}
}
