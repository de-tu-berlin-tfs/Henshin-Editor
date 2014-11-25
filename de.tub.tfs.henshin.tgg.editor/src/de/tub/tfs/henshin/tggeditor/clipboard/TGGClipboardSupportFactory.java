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
