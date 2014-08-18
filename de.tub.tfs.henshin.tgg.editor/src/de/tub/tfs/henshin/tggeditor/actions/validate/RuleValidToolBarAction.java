/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.validate;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The RuleValidAction is shown in the toolbar of the rule editor. Makes same 
 * checks as RuleValidAction.
 * @see de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidAction
 */
public class RuleValidToolBarAction extends RuleValidAction {
	
	/**
	 * the Constructor
	 * @see de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidAction#RuleValidAction(org.eclipse.ui.IWorkbenchPart)
	 * @param part
	 * @param page
	 */
	public RuleValidToolBarAction(MuvitorPageBookView part, 
			RuleGraphicalPage page) {
		super(part.getEditor());
		this.rule = (TGGRule) page.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.validate.GraphValidAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return rule != null;
	}
}
