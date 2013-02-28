package de.tub.tfs.muvitor.clipboard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList.Generic;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.clipboard.core.AbstractClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteAction;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;

import de.tub.tfs.muvitor.actions.GenericPasteAction;
import de.tub.tfs.muvitor.actions.GenericPasteAction.IPasteRule;

public class MuvitorClipboardSupport extends AbstractClipboardSupport {

	public MuvitorClipboardSupport() {
		super();
	}


	protected EAttribute getNameAttribute(EClass eClass) {
		
		return eClass.getEIDAttribute();
	}
	
	public PasteAction getPasteCollisionAction(EClass eClass) {
		return super.getPasteCollisionAction(eClass);
	}
	
	public boolean isCopyAlways(EObject context, EReference eReference,
			Object value) {
			return super.isCopyAlways(context, eReference, value);
	}
	
	@Override
	public OverridePasteChildOperation getOverrideChildPasteOperation(
			final PasteChildOperation overriddenChildPasteOperation) {
		// TODO Auto-generated method stub
		return new OverridePasteChildOperation(overriddenChildPasteOperation) {
			@Override
			public PasteChildOperation getPostPasteOperation() {
				// TODO Auto-generated method stub
				return new PostPasteChildOperation(overriddenChildPasteOperation,Arrays.asList(new PasteChildOperation(getParentPasteProcess(), getChildObjectInfo()){
					
					@Override
					protected EObject doPasteInto(EObject pasteIntoEObject) {

						EObject copy = super.doPasteInto(pasteIntoEObject);
						
						performPostPasteProcessing(copy,pasteIntoEObject);
						
						return copy;
					}					
					
				}) );
			}
		};
	}
	
	private void performPostPasteProcessing(EObject copy,
			EObject pasteIntoEObject) {
		final LinkedList<EClass> copySuperTypes = new LinkedList<EClass>( copy.eClass().getEAllSuperTypes());
		copySuperTypes.add(EcorePackage.Literals.EOBJECT);
		copySuperTypes.add(copy.eClass());
		
		for (final Entry<EClass, IPasteRule> entry : GenericPasteAction.pasteRules.entrySet()) {
			
			if (copySuperTypes.contains(entry.getKey())) {
				entry.getValue().afterPaste(copy, copy.eContainer());
			}
		}	
	}
	@Override
	public void performPostPasteProcessing(Set pastedEObjects) {
		Set<EObject> pasted = pastedEObjects;
		for (final EObject copy : pasted) {
			//performPostPasteProcessing(copy, copy.eContainer());
		}
	}
	
}