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
package de.tub.tfs.henshin.editor.actions.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.muvitor.actions.GenericPasteAction.IPasteRule;

public class PasteHenshinRules implements IPasteRule {

	@Override
	public void afterPaste(EObject element, EObject target) {
		if (!(element instanceof Rule && target instanceof Module))
			return;
		if (element.eContainer() == null)
			((Module) target).getUnits().add((Rule) element);
	}

	@Override
	public boolean isValidPaste(EObject element, EObject target) {
		// TODO Auto-generated method stub
		return true;
	}

}
