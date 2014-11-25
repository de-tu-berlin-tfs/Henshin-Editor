package de.tub.tfs.henshin.tggeditor.clipboard;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportPolicy;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.impl.TggPackageImpl;

public class TGGClipboardSupportPolicy implements IClipboardSupportPolicy {

	public TGGClipboardSupportPolicy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean provides(IAdaptable adaptable) {
		EObject eObj = (EObject) adaptable.getAdapter(Object.class);
		if (eObj == null)
			return false;
		if (eObj.eClass().getEPackage().equals(HenshinPackage.eINSTANCE))
			return true;
		if (eObj.eClass().getEPackage().equals(TggPackage.eINSTANCE))
			return true;
		
		return false;
	}

}
