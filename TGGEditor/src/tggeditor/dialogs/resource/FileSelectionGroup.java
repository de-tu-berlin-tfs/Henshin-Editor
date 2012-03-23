/**
 * 
 */
package tggeditor.dialogs.resource;

import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

/**
 * The Class FileSelectionGroup.
 *
 */
public class FileSelectionGroup extends ContainerSelectionGroup {

	/**
	 * Instantiates a new file selection group.
	 *
	 * @param composite the composite
	 * @param listener the listener
	 * @param allowNew the allow new
	 * @param msg the msg
	 */
	public FileSelectionGroup(Composite composite, Listener listener,
			boolean allowNew, String msg) {
		super(composite, listener, allowNew, msg);

		setValidator(new FileAndContainerValidator());
	}

	/**
	 * Adds the file extensions.
	 *
	 * @param exts the exts
	 */
	public void addFileExtensions(String[] exts) {
		((FileAndContainerValidator) validator).getFileChecker().addExtensions(
				exts);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transeditor.ui.dialog.resources.ResourceSelectionGroup#selectionChanged
	 * (org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

		if (selectedRes instanceof IContainer && allowNewResource) {
			resourceInput.removeModifyListener(this);
			resourceInput.setText(selectedRes.getFullPath().addTrailingSeparator().toString().substring(1));
			resourceInput.addModifyListener(this);
		}
	}

	/**
	 * Gets the file extensions.
	 *
	 * @return the file extensions
	 */
	public Set<String> getFileExtensions() {
		return ((FileAndContainerValidator) validator).getFileChecker().getExtensions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transeditor.ui.dialog.resources.ContainerSelectionGroup#validate()
	 */
	@Override
	protected void validate() {
		super.validate();

		if (isValid) {
			IResource res = ResourcesPlugin.getWorkspace().getRoot().findMember(
					getResourceFullPath());
			if (res instanceof IContainer) {
				isValid = false;
				errorMessage = "No file name is specified.";
			}
			else {
				FileValidator fileChecker = ((FileAndContainerValidator) validator).getFileChecker();

				if (!fileChecker.getExtensions().contains(
						getResourceFullPath().getFileExtension())) {
					isValid = false;
					String extensions = fileChecker.getExtensions().toString();
					errorMessage = "The new file must end with the extension(s): "
							+ extensions.substring(1, extensions.length() - 1);
				}
			}
		}
	}

}
