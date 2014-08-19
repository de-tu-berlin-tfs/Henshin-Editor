package de.tub.tfs.henshin.tggeditor.dialogs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ValidTestDialog extends org.eclipse.swt.widgets.Dialog {

	/** The dialog shell. */
	private Shell dialogShell;
	
	/** The styled text1. */
	private StyledText styledText1;
	
	/** The button ok. */
	private Button buttonOk;
	
	/** The meldngen. */
	private  List<String> messages;

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Dialog inside a new Shell.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param messages the meldngen
	 */

	public ValidTestDialog(Shell parent, int style, List<String> messages) {
		super(parent, style);
		this.messages=messages;
	}

	/**
	 * Open.
	 */
	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			{
				buttonOk = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData buttonOkLData = new FormData();
				buttonOkLData.left =  new FormAttachment(0, 1000, 212);
				buttonOkLData.top =  new FormAttachment(0, 1000, 490);
				buttonOkLData.width = 450;
				buttonOkLData.height = 25;
				buttonOk.setLayoutData(buttonOkLData);
				buttonOk.setText("Ok");
				buttonOk.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						dialogShell.close();
						
					}
				});
			}
			{
				styledText1 = new StyledText(dialogShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				FormData styledText1LData = new FormData();
				styledText1LData.left =  new FormAttachment(0, 1000, 12);
				styledText1LData.top =  new FormAttachment(0, 1000, 12);
				styledText1LData.width = 840;
				styledText1LData.height = 450;
				styledText1.setLayoutData(styledText1LData);
				String text=messages.get(0);
				for (int i=1,n=messages.size();i<n;i++){
					text+="\n"+messages.get(i);
				}
				styledText1.setText(text);
				styledText1.setEditable(false);
			}
			dialogShell.layout();
			dialogShell.setSize(880, 550);
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
	
}
