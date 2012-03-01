package de.tub.tfs.henshin.editor.actions.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

import de.tub.tfs.muvitor.actions.GenericPasteAction.IPasteRule;

public class PasteHenshinRules implements IPasteRule {

	@Override
	public void afterPaste(EObject element, EObject target) {
		if (!(element instanceof Rule && target instanceof TransformationSystem))
			return;
		if (element.eContainer() == null)
			((TransformationSystem) target).getRules().add((Rule) element);
	}

	@Override
	public boolean isValidPaste(EObject element, EObject target) {
		// TODO Auto-generated method stub
		return true;
	}

}
