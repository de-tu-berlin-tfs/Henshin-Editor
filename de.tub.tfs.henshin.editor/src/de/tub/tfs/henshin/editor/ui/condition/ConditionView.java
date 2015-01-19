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
package de.tub.tfs.henshin.editor.ui.condition;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class ConditionView.
 * 
 * @author Angeline Warning
 */
public class ConditionView extends MuvitorPageBookView {

	/**
	 * An unique id of this view.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.ui.condition.ConditionView";

	/** The page. */
	private ConditionPage page;

	private Formula hiddenFormula;

	private Button zoomOutButton;

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPageBookView#calculatePartName()
	 */
	@Override
	protected String calculatePartName() {
		String viewText = "Condition: ";

		Graph premise = FormulaUtil.getPremise(getCastedModel());
		if (premise != null) {
			viewText += premise.getName() + " -> "
					+ FormulaUtil.getText(getCastedModel());
		}

		return viewText;
	}

	/**
	 * Gets the casted model.
	 * 
	 * @return The casted model hold by condition page.
	 */
	public Formula getCastedModel() {
		if (page != null) {
			return page.getCastedModel();
		}
		return null;
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
		if (page == null) {
			page = new ConditionPage(this);
		}

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
		setPartName(calculatePartName());
	}

	/**
	 * Gets the current condition page.
	 * 
	 * @return the current condition page
	 */
	public ConditionPage getCurrentConditionPage() {
		return page;
	}

	public void setViewContent(final int index, final Formula formula) {
		if (formula != null) {
			if (formula == hiddenFormula) {
				hiddenFormula = null;
				page.setViewersContents(index, formula);
				zoomOutButton.setVisible(false);
			} else if (formula instanceof NestedCondition) {
				final EObject eObject = page.getViewersContents(1);
				if (eObject instanceof Formula) {
					hiddenFormula = (Formula) eObject;
				}

				if (zoomOutButton == null) {
					final FigureCanvas conditionCtrl = (FigureCanvas) page
							.getViewer(getCastedModel()).getControl();
					final int width = 20;
					final int height = 20;
					zoomOutButton = new Button(conditionCtrl, SWT.FLAT);
					zoomOutButton
							.setToolTipText("Zoom out this application condition");
					zoomOutButton.setBackground(ColorConstants.white);
					zoomOutButton.setGrayed(true);
					zoomOutButton.setImage(IconUtil.getIcon("zoomin13.png"));
					zoomOutButton.setSize(width, height);
					zoomOutButton.setLocation(
							conditionCtrl.getClientArea().width - width - 5, 5);
					zoomOutButton.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							setViewContent(index, hiddenFormula);
						}
					});

					conditionCtrl.addPaintListener(new PaintListener() {
						@Override
						public void paintControl(PaintEvent e) {
							zoomOutButton.setBounds(
									conditionCtrl.getClientArea().width - width
											- 5, 5, width, height);
						}
					});
				}
				page.setViewersContents(index,
						((NestedCondition) formula).getConclusion());
				zoomOutButton.setVisible(true);
			}
		}
	}
}
