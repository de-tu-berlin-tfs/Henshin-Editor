package de.tub.tfs.henshin.editor.ui.rule;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.actions.graph.CreateAttributeAction;
import de.tub.tfs.henshin.editor.actions.rule.DeleteMappingAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.editparts.rule.RuleEditPartFactory;
import de.tub.tfs.henshin.editor.util.SendNotify;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class RulePage.
 * 
 * @author Johann
 */
public class RulePage extends MuvitorPage {

	private Graph acGraph;

	/** The rule view. */
	private RuleView ruleView;

	/** The rule palett root. */
	private RulePaletteRoot rulePaletteRoot;

	/**
	 * Konstruktor erh�lt MuvitorPageBookView.
	 * 
	 * @param view
	 *            MuvitorPageBookView
	 */
	public RulePage(MuvitorPageBookView view) {
		super(view);
		this.ruleView = (RuleView) view;

	}

	/**
	 * @return the ncGraph The conclusion's graph of application condition.
	 */
	public Graph getAcGraph() {
		
		return acGraph;
	}

	
	/**
	 * Liefert eine Regel zur�ck.
	 * 
	 * @return Rule
	 */
	public Rule getCastedModel() {
		return (Rule) getModel();
	}

	/**
	 * Refresh the formula shown beside LHS.
	 */
	public void refreshFormula() {
		setCondition(getCastedModel().getLhs().getFormula());
	}

	/**
	 * Refresh palettes.
	 */
	public void refreshPallets() {
		rulePaletteRoot.refreshGraphToolsGroup();
	}

	/**
	 * Sets the conclucion's graph of application condition.
	 * 
	 * @param acGraph
	 *            Conclucion's graph of application condition.
	 */
	public void setCondition(final Formula formula) {
		if (formula instanceof NestedCondition) {
			acGraph = ((NestedCondition) formula).getConclusion();
			refreshFormulaView(0, acGraph);
		} else {
			acGraph = null;
			refreshFormulaView(0, formula);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#getViewerContents()
	 */
	@Override
	protected EObject[] getViewerContents() {
		final Graph lhs = getCastedModel().getLhs();
		return new EObject[] { lhs.getFormula(), lhs, getCastedModel().getRhs() };
	}

	/**
	 * 
	 */
	protected void refreshActionBars() {
		final IContributionItem[] items = ruleView.getViewSite()
				.getActionBars().getToolBarManager().getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].update();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemuvitorkit.ui.MuvitorPage#createContextMenuProvider(org.eclipse.gef.
	 * EditPartViewer)
	 */
	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		return new RuleContextMenuProvider(viewer, getActionRegistry());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createCustomActions()
	 */
	@Override
	protected void createCustomActions() {
		registerSharedActionAsHandler(ActionFactory.COPY.getId());
		registerSharedActionAsHandler(ActionFactory.CUT.getId());
		registerSharedActionAsHandler(ActionFactory.PASTE.getId());
		registerAction(new CreateAttributeAction(getEditor()));
		registerAction(new DeleteMappingAction(getEditor()));
		registerAction(new CreateParameterAction(getEditor()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createEditPartFactory()
	 */
	@Override
	protected EditPartFactory createEditPartFactory() {
		return new RuleEditPartFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createPaletteRoot()
	 */
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		rulePaletteRoot = new RulePaletteRoot(getCastedModel()
				.getTransformationSystem());
		return rulePaletteRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorPage#setupKeyHandler(org.eclipse.gef.KeyHandler)
	 */
	@Override
	protected void setupKeyHandler(KeyHandler kh) {

	}

	/**
	 * Refresh formula view.
	 */
	private void refreshFormulaView(int index, EObject eObject) {
		setViewersContents(index, eObject);
		setViewerVisibility(index, eObject != null);

		refreshActionBars();
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.RULE__MAPPINGS:
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
				if (notification.getNewValue() instanceof Mapping) {
					SendNotify.sendAddMappingNotify((Mapping) notification.getNewValue());
				}
				break;
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				if (notification.getOldValue() instanceof Mapping) {
					SendNotify.sendRemoveMappingNotify((Mapping) notification.getOldValue() );
				}
				break;
			}
			for (EObject eObj : this.getViewerContents()) {
				if (eObj != null)
					eObj.eNotify(notification);
			}
			break;

		}

		super.notifyChanged(notification);
	}
}
