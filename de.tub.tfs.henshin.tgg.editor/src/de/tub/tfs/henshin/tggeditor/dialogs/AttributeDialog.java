package de.tub.tfs.henshin.tggeditor.dialogs;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AttributeDialog extends Dialog {
	
	/** the attribute types */
	private List<EAttribute> attributeTypes;
	
	/** the chosen attribute type and its value */
	private SimpleEntry<EAttribute, String> result;
	
	/** the cancelButton */
	private Button okButton;
	
	/** the okButton */
	private Button cancelButton;
	
	/** the dialog shell */
	private Shell dialogShell;
	
	private Text valueTextField;
	private Combo attributeTypeSelection;
	
	/**
	 * constructor to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param attribute the attribute
	 */

	public AttributeDialog(Shell parent, int style, List<EAttribute> attribute, 
			SimpleEntry<EAttribute, String> result) {
		super(parent, style);
		this.attributeTypes = attribute;
		this.result = result;
	}
	
	public SimpleEntry<EAttribute, String> getResult(){
		return result;
	}
	
	public void open() {
		try {
			if(result == null) result = new SimpleEntry<EAttribute, String>(null,"");
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData cancelButtonData = new FormData();
				cancelButtonData.left =  new FormAttachment(0, 1000, 130);
				cancelButtonData.top =  new FormAttachment(0, 1000, 110);
				cancelButtonData.width = 99;
				cancelButtonData.height = 25;
				cancelButton.setLayoutData(cancelButtonData);
				cancelButton.setText("Cancel");
				cancelButton.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						dialogShell.close();
					}
				});

			}
			
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonData = new FormData();
				okButtonData.left =  new FormAttachment(0, 1000, 29);
				okButtonData.top =  new FormAttachment(0, 1000, 110);
				okButtonData.width = 95;
				okButtonData.height = 25;
				okButton.setLayoutData(okButtonData);
				okButton.setText("Ok");
				okButton.setEnabled(false);
				okButton.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						if(valueTextField.getText().length()>0 && 
								attributeTypeSelection.getSelectionIndex()>-1){
							result = new SimpleEntry<EAttribute, String>(
									attributeTypes.get(attributeTypeSelection.getSelectionIndex()),
									valueTextField.getText());
						}
						else result = new SimpleEntry<EAttribute, String>(null,"");
						
						dialogShell.close();
					}
				});
			}
			{	
				FormData attributeLabelData = new FormData();
				attributeLabelData.left =  new FormAttachment(5);
				attributeLabelData.top =  new FormAttachment(5);
				attributeLabelData.width = 40;
				attributeLabelData.height = 20;
				Label attributeLabel = new Label(dialogShell, SWT.PUSH | SWT.RIGHT);
				attributeLabel.setText("Type");
				attributeLabel.setLayoutData(attributeLabelData);
			}
			{	
				FormData attributeData = new FormData();
				attributeData.left =  new FormAttachment(20);
				attributeData.top =  new FormAttachment(5);
				attributeData.width = 200;
				attributeData.height = 20;
				attributeTypeSelection = new Combo(dialogShell, SWT.PUSH | SWT.LEFT);
				attributeTypeSelection.setLayoutData(attributeData);
				
				int i = 0;
				for(EAttribute type : attributeTypes) {
					attributeTypeSelection.add(type.getName());
					if(result.getKey() == type)attributeTypeSelection.select(i);
					++i;
				}
				
				attributeTypeSelection.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						activationOkButton();
					}
				});
			}
			{	
				FormData valueLabelData = new FormData();
				valueLabelData.left =  new FormAttachment(5);
				valueLabelData.top =  new FormAttachment(20);
				valueLabelData.width = 40;
				valueLabelData.height = 20;
				Label valueLabel = new Label(dialogShell, SWT.PUSH | SWT.RIGHT);
				valueLabel.setText("Value");
				valueLabel.setLayoutData(valueLabelData);
			}
			{
				
				FormData valueData = new FormData();
				valueData.left =  new FormAttachment(20);
				valueData.top =  new FormAttachment(20);
				valueData.width = 200;
				valueData.height = 20;
				valueTextField = new Text(dialogShell,SWT.PUSH | SWT.LEFT);
				valueTextField.setText("''");
				valueTextField.setLayoutData(valueData);
				valueTextField.setEditable(true);
				if(result.getValue() != null && !result.getValue().equals("")) {
					valueTextField.setText(result.getValue());
					if(attributeTypeSelection.getSelectionIndex()>-1) okButton.setEnabled(true);
				}
				
				valueTextField.addModifyListener(new ModifyListener() {
					
					@Override
					public void modifyText(ModifyEvent e) {
						activationOkButton();
					}
				});
			}
			dialogShell.layout();
			dialogShell.setSize(300, 200);
			Rectangle shellBounds = getParent().getBounds();
			Point dialogSize = dialogShell.getSize();
			dialogShell.setLocation(shellBounds.x
					+ (shellBounds.width - dialogSize.x) / 2, shellBounds.y
					+ (shellBounds.height - dialogSize.y) / 2);
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			activationOkButton();
			attributeTypeSelection.setFocus();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void activationOkButton() {
		if(valueTextField.getText().length()>0 && attributeTypeSelection.getSelectionIndex()>-1) {
			okButton.setEnabled(true);
		} else {
			okButton.setEnabled(false);
		}
	}
}
