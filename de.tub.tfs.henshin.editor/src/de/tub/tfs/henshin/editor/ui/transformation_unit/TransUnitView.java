/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.transformation_unit;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.editor.actions.transformation_unit.ExecuteTransformationUnitToolBarAction;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class TransUnitView.
 */

public class TransUnitView extends MuvitorPageBookView {

	/**
	 * An unique id of this view.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.ui.transformationUnit.TransUnitView";

	/** The page. */
	private TransUnitPage page;

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPageBookView#calculatePartName()
	 */
	@Override
	protected String calculatePartName() {
		return "Transformation unit: "
				+ ((Unit) getModel()).getName();
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
		page = new TransUnitPage(this);
		getViewSite().getActionBars().getToolBarManager()
				.add(new ExecuteTransformationUnitToolBarAction(this.getEditor(), page));
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
		case HenshinPackage.UNIT__NAME:
			setPartName(calculatePartName());
			break;
		default:
			break;
		}
	}

	/**
	 * Gets the current trans unit page.
	 * 
	 * @return the current trans unit page
	 */
	public TransUnitPage getCurrentTransUnitPage() {
		return page;
	}
}
