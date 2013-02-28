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
