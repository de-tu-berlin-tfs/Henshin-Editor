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
package de.tub.tfs.henshin.tggeditor.views.ruleview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;


public class NACGraphicalView extends GraphicalView {
	@Override
	protected String calculatePartName() {
		return "NAC: " + ((NamedElement) getModel()).getName();
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {
		return new NACGraphicalPage(this);
	}
	
	
}
