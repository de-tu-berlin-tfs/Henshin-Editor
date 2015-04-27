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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
public class CreateAttributeConditionDialog extends
		org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label aConditionNameLabel;
	private Button CancelButton;
	private Button okButton;
	private Text conditionTextFeld;
	private Text nameTextFeld;
	private Label errorsLabel;
	private Label conditionTextLabel;

	private String name;
	private String conditionText;
	private IInputValidator validator;
	private boolean cancel = true;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */

	public CreateAttributeConditionDialog(Shell parent, int style, String name,
			IInputValidator validator) {
		super(parent, style);
		this.name = name;
		this.validator = validator;
		this.conditionText = new String();
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(593, 200);
			{
				errorsLabel = new Label(dialogShell, SWT.NONE);
				FormData errorsLabelLData = new FormData();
				errorsLabelLData.left = new FormAttachment(0, 1000, 7);
				errorsLabelLData.top = new FormAttachment(0, 1000, 109);
				errorsLabelLData.width = 557;
				errorsLabelLData.height = 15;
				errorsLabel.setLayoutData(errorsLabelLData);
				errorsLabel.setForeground(ColorConstants.red);
				errorsLabel.setText("");
			}
			{
				CancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData CancelButtonLData = new FormData();
				CancelButtonLData.left = new FormAttachment(0, 1000, 460);
				CancelButtonLData.top = new FormAttachment(0, 1000, 130);
				CancelButtonLData.width = 101;
				CancelButtonLData.height = 25;
				CancelButton.setLayoutData(CancelButtonLData);
				CancelButton.setText("Cancel");
				CancelButton.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						dialogShell.close();
					}
				});
			}
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonLData = new FormData();
				okButtonLData.left = new FormAttachment(0, 1000, 357);
				okButtonLData.top = new FormAttachment(0, 1000, 130);
				okButtonLData.width = 97;
				okButtonLData.height = 25;
				okButton.setLayoutData(okButtonLData);
				okButton.setText("OK");
				okButton.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						cancel = false;
						name = new String(nameTextFeld.getText());
						conditionText = new String(conditionTextFeld.getText());
						dialogShell.close();
					}
				});
				String errorText = validator.isValid(name);
				if (errorText == null) {
					errorsLabel.setText("");
					okButton.setEnabled(true);
				} else {
					errorsLabel.setText(errorText);
					okButton.setEnabled(false);
				}
			}
			{
				conditionTextFeld = new Text(dialogShell, SWT.BORDER);
				FormData conditionTextFeldLData = new FormData();
				conditionTextFeldLData.left = new FormAttachment(0, 1000, 12);
				conditionTextFeldLData.top = new FormAttachment(0, 1000, 83);
				conditionTextFeldLData.width = 547;
				conditionTextFeldLData.height = 20;
				conditionTextFeld.setLayoutData(conditionTextFeldLData);
				conditionTextFeld.setText(conditionText);
			}
			{
				nameTextFeld = new Text(dialogShell, SWT.BORDER);
				FormData nameTextFeldLData = new FormData();
				nameTextFeldLData.left = new FormAttachment(0, 1000, 12);
				nameTextFeldLData.top = new FormAttachment(0, 1000, 33);
				nameTextFeldLData.width = 547;
				nameTextFeldLData.height = 23;
				nameTextFeld.setLayoutData(nameTextFeldLData);
				nameTextFeld.setText(name);
				nameTextFeld.addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e) {
						String errorText = validator.isValid(nameTextFeld
								.getText());
						if (errorText == null) {
							errorsLabel.setText("");
							okButton.setEnabled(true);
						} else {
							errorsLabel.setText(errorText);
							okButton.setEnabled(false);
						}
					}
				});
			}
			{
				conditionTextLabel = new Label(dialogShell, SWT.NONE);
				FormData conditionTextLData = new FormData();
				conditionTextLData.left = new FormAttachment(0, 1000, 12);
				conditionTextLData.top = new FormAttachment(0, 1000, 62);
				conditionTextLData.width = 553;
				conditionTextLData.height = 15;
				conditionTextLabel.setLayoutData(conditionTextLData);
				conditionTextLabel.setText("Enter a condition text:");
			}
			{
				aConditionNameLabel = new Label(dialogShell, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left = new FormAttachment(0, 1000, 12);
				label1LData.top = new FormAttachment(0, 1000, 12);
				label1LData.width = 553;
				label1LData.height = 15;
				aConditionNameLabel.setLayoutData(label1LData);
				aConditionNameLabel
						.setText("Enter a name for the new attribute condition:");
			}
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
	 * @return the name
	 */
	public synchronized String getName() {
		return name;
	}

	/**
	 * @return the conditionText
	 */
	public synchronized String getConditionText() {
		return conditionText;
	}

	/**
	 * @return the cancel
	 */
	public synchronized boolean isCancel() {
		return cancel;
	}

}
