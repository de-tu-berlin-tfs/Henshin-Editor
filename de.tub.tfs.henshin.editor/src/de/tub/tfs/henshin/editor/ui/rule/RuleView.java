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
/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.rule;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.editor.actions.rule.ExecuteRuleToolBarRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.LhsToNcCopyAction;
import de.tub.tfs.henshin.editor.actions.rule.LhsToRhsCopyAction;
import de.tub.tfs.henshin.editor.actions.rule.ValidateRuleToolBarAction;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class RuleView.
 * 
 * @author Johann
 */

public class RuleView extends MuvitorPageBookView {

	/**
	 * An unique if of this view
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.ui.rule.RuleView";

	/** The page. */
	private RulePage page;

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPageBookView#calculatePartName()
	 */
	@Override
	protected String calculatePartName() {
		return "Rule: " + ((Rule) getModel()).getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorPageBookView#createPageForModel(org.eclipse.emf.
	 * ecore.EObject)
	 */
	@Override
	protected IPage createPageForModel(EObject forModel) {
		page = new RulePage(this);
		getViewSite().getActionBars().getToolBarManager()
				.add(new ValidateRuleToolBarAction(this, page));
		getViewSite().getActionBars().getToolBarManager()
				.add(new ExecuteRuleToolBarRuleAction(this, page));

		getViewSite().getActionBars().getToolBarManager()
				.add(new LhsToNcCopyAction(this, page));
		getViewSite().getActionBars().getToolBarManager()
				.add(new LhsToRhsCopyAction(this, page));

		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorPageBookView#notifyChanged(org.eclipse.emf.common
	 * .notify.Notification)
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.RULE__NAME:
			setPartName(calculatePartName());
			break;
		default:
			break;
		}
	}

	/**
	 * Gets the current rule page.
	 * 
	 * @return the current rule page
	 */
	public RulePage getCurrentRulePage() {
		return page;
	}

	/**
	 * Returns casted model.
	 */
	public Rule getCastedModel() {
		return page.getCastedModel();
	}
}
