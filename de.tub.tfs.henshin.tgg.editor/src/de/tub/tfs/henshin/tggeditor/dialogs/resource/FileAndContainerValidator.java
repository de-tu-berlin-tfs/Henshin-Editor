/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.dialogs.resource;

import org.eclipse.core.resources.IResource;

/**
 * The Class FileAndContainerValidator.
 *
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
