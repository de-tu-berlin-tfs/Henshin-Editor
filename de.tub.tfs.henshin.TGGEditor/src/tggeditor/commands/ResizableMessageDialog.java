package tggeditor.commands;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ResizableMessageDialog extends MessageDialog {

	
	
	public ResizableMessageDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
	}
	
	
	public ResizableMessageDialog(String title, String errorString) {
			super(Display.getDefault().getActiveShell(),
					title, null, errorString,
					MessageDialog.WARNING, new String[] { "OK" }, 0);
	}


	@Override
	  protected boolean isResizable() {
	    return true;
	  }

}
