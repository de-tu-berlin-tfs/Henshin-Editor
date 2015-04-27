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
package de.tub.tfs.muvitor.clipboard;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory;

public class MuvitorClipboardSupportFactory implements IClipboardSupportFactory {

	private final IClipboardSupport support = new MuvitorClipboardSupport();

	public MuvitorClipboardSupportFactory() {
		super();
	}

	public IClipboardSupport newClipboardSupport(EPackage ePackage) {
		return support;
	}
	
}
