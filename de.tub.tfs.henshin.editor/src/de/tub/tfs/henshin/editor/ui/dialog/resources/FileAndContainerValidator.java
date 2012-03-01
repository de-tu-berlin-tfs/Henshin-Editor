/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.dialog.resources;

import org.eclipse.core.resources.IResource;

/**
 * The Class FileAndContainerValidator.
 * 
 * @author nam
 */
public class FileAndContainerValidator implements IResourceValidator {

	/** The folder validator. */
	private ContainterValidator folderValidator = new ContainterValidator();

	/** The file validator. */
	private FileValidator fileValidator = new FileValidator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transeditor.ui.dialog.resources.FileValidator#isValid(org.eclipse.core
	 * .resources.IResource)
	 */
	@Override
	public boolean isValid(IResource obj) {
		return fileValidator.isValid(obj) || folderValidator.isValid(obj);
	}

	/**
	 * Gets the file checker.
	 * 
	 * @return the fileValidator
	 */
	public FileValidator getFileChecker() {
		return fileValidator;
	}

	/**
	 * Gets the folder checker.
	 * 
	 * @return the folderValidator
	 */
	public ContainterValidator getFolderChecker() {
		return folderValidator;
	}
}
