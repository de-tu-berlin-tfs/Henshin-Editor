package de.tub.tfs.henshin.tgg.interpreter.gui;

import java.io.Console;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransHandler extends AbstractHandler implements IHandler {
//	private static final String sourceExt = "xml";
	private static boolean useOutputFolder;

	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get currently active shell:
		useOutputFolder=false;
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		// Load the transformation units during first run:
		if (LoadHandler.trSystems == null) {
			MessageDialog.openError(shell, "No Translator loaded",
					"Please load the translator.");
		}
		// Find files to translate:
		Queue<IFile> transQueue = new LinkedList<IFile>();
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel != null && sel instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) sel;
			for (Iterator<Object> it = structSel.iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					transQueue.add(file);
				}
				if (obj instanceof IContainer) {
					useOutputFolder=true;
					IResource[] resArr;
					try {
						resArr = ((IContainer) obj).members();
						for (int i=0; i<resArr.length; i++) {
							if (resArr[i] instanceof IFile) {
								IFile file = (IFile) resArr[i];
//								if (file.getFileExtension().equals(sourceExt)) {
									transQueue.add(file);
//								}
							}
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// Start jobs for all input files:
		for (IFile inputFile: transQueue) {
			TranslationJob job = new TranslationJob(inputFile,useOutputFolder);
			job.setRule(new TransSchedulingRule());
			job.schedule();
		}
		return null;
	}

	
	public static void waitForEnter(String message, Object... args) {
		Console c = System.console();
		if (c != null) {
			// printf-like arguments
			if (message != null)
				c.format(message, args);
			c.format("\nPress ENTER to proceed.\n");
			c.readLine();
		}
	}

}