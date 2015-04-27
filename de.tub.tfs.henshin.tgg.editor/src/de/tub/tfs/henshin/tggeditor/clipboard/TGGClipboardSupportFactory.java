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
package de.tub.tfs.henshin.tggeditor.clipboard;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory;

import de.tub.tfs.muvitor.clipboard.MuvitorClipboardSupport;

public class TGGClipboardSupportFactory implements IClipboardSupportFactory {

	private final IClipboardSupport support = new TGGClipboardSupport();

	public TGGClipboardSupportFactory() {
		super();
	}

	public IClipboardSupport newClipboardSupport(EPackage ePackage) {
		return support;
	}
	
}
