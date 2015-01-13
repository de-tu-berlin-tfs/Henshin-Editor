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
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;

import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerUnspecifiedAction;
import de.tub.tfs.henshin.tggeditor.editparts.rule.NACGraphicalEditPartFactory;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPaletteRoot;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class NACGraphicalPage extends GraphicalPage {

	private GraphicalPaletteRoot nacPaletteRoot;

	public NACGraphicalPage(MuvitorPageBookView view) {
		super(view);
	}

	@Override
	protected EditPartFactory createEditPartFactory() {
		return new NACGraphicalEditPartFactory();
	}

	@Override
	protected void createCustomActions() {
		registerAction(new NewMarkerUnspecifiedAction(getEditor()));
		super.createCustomActions();
	}


	
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof Module)) {
			parent = parent.eContainer();
		}

		if (parent != null && parent instanceof Module) {
			nacPaletteRoot = new NACGraphicalPalletRoot(
					(Module) parent);
		}
		return nacPaletteRoot;
	}
	
	

}
