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
package de.tub.tfs.henshin.tggeditor.views.graphview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class CriticalPairView extends MuvitorPageBookView {

	@Override
	protected String calculatePartName() {
		if(getModel() instanceof CritPair){
			return "CriticalPair: " + ((NamedElement) ((CritPair) getModel()).getOverlapping()).getName();
		}
		return null;
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		CriticalPairPage page = new CriticalPairPage(this);
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		return page;
	}

}
