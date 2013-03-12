/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.dialogs.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

/**
 * The Class FileValidator.
 *
 */
public class FileValidator implements IResourceValidator {

	/** The extensions. */
	private Set<String> extensions = new HashSet<String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transeditor.ui.dialog.resources.IResourceValidator#isValid(java.lang.
	 * Object )
	 */
	@Override
	public boolean isValid(IResource obj) {
		return (obj instanceof IFile)
				&& (extensions.isEmpty() || extensions.contains(((IFile) obj).getFileExtension()));
	}

	/**
	 * Adds the extensions.
	 *
	 * @param exts the exts
	 */
	public void addExtensions(String[] exts) {
		for (String ext : exts) {
			extensions.add(ext);
		}
	}

	/**
	 * Removes the extension.
	 *
	 * @param ext the ext
	 */
	public void removeExtension(String ext) {
		extensions.remove(ext);
	}

	/**
	 * Returns a read-only set of file extensions.
	 *
	 * @return the extensions
	 */
	public Set<String> getExtensions() {
		return Collections.unmodifiableSet(extensions);
	}

}
