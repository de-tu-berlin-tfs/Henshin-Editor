package de.tub.tfs.henshin.editor.ui.graph;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;

import de.tub.tfs.henshin.editor.actions.graph.CollapseChildrenAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateAttributeAction;
import de.tub.tfs.henshin.editor.actions.graph.ExecuteRuleToolBarGraphAction;
import de.tub.tfs.henshin.editor.actions.graph.FilterTypeAction;
import de.tub.tfs.henshin.editor.actions.graph.GraphValidToolBarAction;
import de.tub.tfs.henshin.editor.actions.graph.SearchMatchAction;
import de.tub.tfs.henshin.editor.actions.graph.SearchModelAction;
import de.tub.tfs.henshin.editor.actions.graph.SearchTypeAction;
import de.tub.tfs.henshin.editor.actions.graph.ValidateGraphAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.ExecuteTransformationUnitToolBarAction;
import de.tub.tfs.henshin.editor.commands.transformation_unit.ExecuteTransformationUnitCommand;
import de.tub.tfs.henshin.editor.internal.UnitApplicationEObject;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class GraphPage.
 * 
 * @author Johann
 */
public class GraphPage extends MuvitorPage {

	/** The graph palette root. */
	private GraphPaletteRoot graphPaletteRoot;

	/** The model changed. */
	private boolean modelChanged = false;

	
	/**
	 * Instantiates a new graph page.
	 * 
	 * @param view
	 *            the view
	 */
	public GraphPage(MuvitorPageBookView view) {
		super(view);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#init(org.eclipse.ui.part.IPageSite)
	 */
	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		((CommandStack) getEditor().getAdapter(CommandStack.class))
				.addCommandStackListener(new CommandStackListener2TraceRefresh(
						this));
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
		return new GraphContextMenuProvider(viewer);
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
		registerSharedAction(ValidateGraphAction.ID);

		getToolBarManager().add(new GraphValidToolBarAction(getEditor(), this));
		getToolBarManager().add(
				new ExecuteRuleToolBarGraphAction(getEditor(), this));
		getToolBarManager().add(
				new ExecuteTransformationUnitToolBarAction(getEditor(), this));
		
		SearchTypeAction searchTypeAction = new SearchTypeAction(getEditor(), this.getCastedModel());
		SearchModelAction searchModelAction = new SearchModelAction(getEditor(), this.getCastedModel());
		SearchMatchAction searchMatchAction = new SearchMatchAction(getEditor(), this.getCastedModel());

		registerAction(searchTypeAction);
		registerAction(searchModelAction);
		registerAction(searchMatchAction);
		
		getToolBarManager().add(searchTypeAction);
		getToolBarManager().add(searchModelAction);
		getToolBarManager().add(searchMatchAction);
		
		FilterTypeAction filterTypeAction = new FilterTypeAction(getEditor(), this.getCastedModel());
		
		registerAction(filterTypeAction);
		
		registerSharedAction(CollapseChildrenAction.ID);
		
		getToolBarManager().add(filterTypeAction);
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createEditPartFactory()
	 */
	@Override
	protected EditPartFactory createEditPartFactory() {
		return new GraphEditPartFactory(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createPaletteRoot()
	 */
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		// ==============================================================
		// old version without filter
		// 
		// edited by huuloi
		/*
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof TransformationSystem)) {
			parent = parent.eContainer();
		}

		if (parent != null && parent instanceof TransformationSystem) {
			graphPaletteRoot = new GraphPalletRoot(
					(TransformationSystem) parent);
		}
		return graphPaletteRoot;
		*/
		//
		// ==============================================================
		
		Graph graph = getCastedModel();
		graphPaletteRoot = new GraphPaletteRoot(graph);
		
		return graphPaletteRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#getViewerContents()
	 */
	@Override
	protected EObject[] getViewerContents() {
		return new EObject[] { getModel(), null };
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
	 * Liefert ein Graphobjekt vom Model zur�ck.
	 * 
	 * @return Graph
	 */
	public Graph getCastedModel() {
		return (Graph) getModel();
	}

	/**
	 * Refresh pallets.
	 */
	public void refreshPallets() {
		graphPaletteRoot.refreshGraphToolsGroup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorPage#notifyChanged(org.eclipse.emf.common.notify
	 * .Notification)
	 */
	@Override
	protected void notifyChanged(Notification msg) {
		switch (msg.getEventType()) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			modelChanged = true;
			break;
		case HenshinNotification.TRANSFORMATION_UNDO:
		case HenshinNotification.TRANSFORMATION_REDO:
			modelChanged = false;
			break;
		}
		super.notifyChanged(msg);
	}

	/**
	 * Refresh trace.
	 */
	public void refreshTrace() {
		if (modelChanged) {
			modelChanged = false;
			CommandStack commandStack = (CommandStack) getEditor().getAdapter(
					CommandStack.class);
			if (commandStack.getUndoCommand() instanceof ExecuteTransformationUnitCommand) {
				ExecuteTransformationUnitCommand command = (ExecuteTransformationUnitCommand) commandStack
						.getUndoCommand();
				UnitApplicationEObject object = new UnitApplicationEObject(
						command);
				setViewersContents(1, object);
				setViewerVisibility(1, true);
				final Control control = getViewers().get(1).getControl();
				final Control rulercomposite = control.getParent();
				final SashForm sashForm = (SashForm) rulercomposite.getParent();
				sashForm.setWeights(new int[] { 5, 1 });
				getViewer(object).select(getViewer(object).getContents());
			} else {
//				setViewersContents(1, null);
//				setViewerVisibility(1, false);
			}
		}
	}

}
