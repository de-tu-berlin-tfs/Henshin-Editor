/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.graph;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class GraphView.
 * 
 * @author Johann
 */
public class GraphView extends MuvitorPageBookView {

	/**
	 * An unique id of this view.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.ui.graphs.GraphView";

	/** The page. */
	private GraphPage page;

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPageBookView#calculatePartName()
	 */
	@Override
	protected String calculatePartName() {
		return "Graph: " + ((NamedElement) getModel()).getName();

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
		page = new GraphPage(this);

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
		case HenshinPackage.MODULE__IMPORTS:
			getCurrentGraphPage().refreshPallets();
			break;
		case HenshinPackage.GRAPH__NAME:
			setPartName(calculatePartName());
			break;
		default:
			break;
		}
	}

	/**
	 * Liefert ein IPage Objekt der Instanz eines GraphPage zur�ck.
	 * 
	 * @return IPage
	 */
	public GraphPage getCurrentGraphPage() {
		return page;
	}

}
