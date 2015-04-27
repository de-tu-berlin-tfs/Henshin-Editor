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
package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class RemoveMarkersToolBarAction extends RemoveMarkersAction {
	

//	protected String name_OP_RULE_FOLDER = "CCRuleFolder";

	/**
	 * Instantiates a new remove marker tool bar rule action.
	 *
	 * @param part the part
	 * @param page the graph page
	 */
	public RemoveMarkersToolBarAction(MuvitorPageBookView part, GraphicalPage page) {
		super(part.getEditor());
		graph=page.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#run()
	 */
	@Override
	public void run() {
		EObject o =  EcoreUtil.getRootContainer( (EObject) graph);
		if (!(o instanceof Module))
			return;
		Module m = (Module) o;
		//model = (IndependentUnit) m.getUnit(name_OP_RULE_FOLDER);
		//retrieveOPRules();
//		if (tRules.isEmpty())
//			return;
		super.run();
	}

}
