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
package de.tub.tfs.henshin.tggeditor.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextDialog extends TitleAreaDialog {
    private String title;
    private String text;
    private String scrollableText;
	private boolean editale;
	private Text scrollable;
	private String input;
	
	
	public TextDialog(Shell parentShell, String title, String text, String scrollableText) {
		this(parentShell,title,text,scrollableText,false);
	}
	    
    public TextDialog(Shell parentShell, String title, String text, String scrollableText,boolean editable) {
        super(parentShell);
        this.title = title;
        this.text = text;
        this.scrollableText = scrollableText;
        this.editale = editable;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea (parent); // Let the dialog create the parent composite

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true; // Layout vertically, too! 
        gridData.verticalAlignment = GridData.FILL;

        scrollable = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
        scrollable.setLayoutData(gridData);
        scrollable.setText(scrollableText);
        scrollable.setEditable(editale);
        return composite;
    }

    @Override
    public void create() {
        super.create();

        // This is not necessary; the dialog will become bigger as the text grows but at the same time,
        // the user will be able to see all (or at least more) of the error message at once
        //getShell ().setSize (300, 300);
        setTitle(title);
        setMessage(text, IMessageProvider.INFORMATION);

    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        Button okButton = createButton(parent, OK, "OK", true);
        okButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                close();
            }
        });
    }

    @Override
    protected boolean isResizable() {
        return true; // Allow the user to change the dialog size!
    }

    
    public void setText(String text) {
		this.text = text;
	}
    
    public String getInputText() {
    	return input;
    }
    
    @Override
    public boolean close() {
    	if (!scrollable.isDisposed())
    		this.input = scrollable.getText();
    	return super.close();
    }
    
}
