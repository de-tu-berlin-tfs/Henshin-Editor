package de.tub.tfs.muvitor.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.PartPane.Sashes;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import de.tub.tfs.muvitor.actions.ExportViewerImageAction;
import de.tub.tfs.muvitor.actions.MoveNodeAction;
import de.tub.tfs.muvitor.actions.TrimViewerAction;
import de.tub.tfs.muvitor.animation.IGraphicalViewerProvider;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.MuvitorPage.MultiViewerPageViewer;
import de.tub.tfs.muvitor.ui.utils.SelectionProviderIntermediate;
import de.tub.tfs.muvitor.ui.utils.ZoomManagerDelegate;

public abstract class MultiDimensionalPage<T extends EObject> extends Page implements IAdaptable,
CommandStackListener, IGraphicalViewerProvider, ISelectionListener {

	private int[] dimensionSizes = null;//new int[]{5,29,5,29,29};

	private int[] dimensions = null;//new int[]{1,3,1,3,1};

	@SuppressWarnings("rawtypes")
	private MultiDimensionalViewer[][] viewers = null;

	/**
	 * The action registry that holds the actions for this page.
	 */
	private final ActionRegistry actionRegistry = new ActionRegistry();

	/**
	 * The main {@link Control} of this page.
	 */
	protected FlyoutPaletteComposite flyoutPaletteComposite;

	/**
	 * The main {@link MuvitorTreeEditor} this page is a part of.
	 */
	private final MuvitorTreeEditor editor;

	/**
	 * The viewer that is
	 * <ul>
	 * <li>showed in the {@link Thumbnail},
	 * <li>the current {@link ISelectionProvider} for this page's
	 * {@link IPageSite} and
	 * <li>observed for selection changes
	 * <li>providing a {@link ZoomManager} for the zoom actions via a
	 * {@link ZoomManagerDelegate}.
	 * </ul>
	 * 
	 * @see #setCurrentViewer(roneditor.ui.part.createActions.MultiViewerPageViewer)
	 */
	private MultiDimensionalViewer<T> currentViewer;

	/**
	 * A thumbnail showing a miniature of what the viewer contains.
	 */
	private ScrollableThumbnail thumbnail;

	/**
	 * The {@link EditDomain} managing this page like an encapsulated editor.
	 */
	private EditDomain editDomain;

	/**
	 * The model which is shown in the viewer.
	 */
	private final EObject model;

	/**
	 * This subclassed {@link ZoomManager} is set for the zoom actions and the
	 * returned by {@link #getAdapter(Class)} for the
	 * {@link ZoomComboContributionItem} created in the
	 * {@link ActionBarContributor}. It delegates to the {@link ZoomManager} of
	 * {@link #currentViewer}.
	 * 
	 * @see #setCurrentViewer(MultiViewerPageViewer)
	 */
	private final ZoomManagerDelegate zoomManagerDelegate = new ZoomManagerDelegate();

	/**
	 * This enables this page to switch the actual selection-providing viewer.
	 * 
	 * @see #setCurrentViewer(MultiViewerPageViewer)
	 */
	private final SelectionProviderIntermediate selectionProvider = new SelectionProviderIntermediate();

	/**
	 * A shared {@link KeyHandler} for the viewers on this page.
	 */
	private KeyHandler keyHandler;

	/**
	 * The EMF adapter listening to this pages's model EObject.
	 */
	final private Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			MultiDimensionalPage.this.notifyChanged(msg);
		}
	};


	//private MuvitorPageBookView pageview = null;

	EObject[] models = null;
/*	public MultiDimensionalPage(MuvitorPageBookView pageview) {
		this.editor = (MuvitorTreeEditor) pageview.getEditor();
		this.model = pageview.getModel();
		this.pageview = pageview;
	}
*/
	
	/**
	 * @param view
	 *            the MuvitorPageBookView that hosts this page
	 * @param dim
	 * 			  number of viewers for each row e.g. dim = new int[]{3,2,1}  means 3 viewers for first row, two viewers for the second one and 1 viewer in the last raw.
	 * 			   
	 */
	public MultiDimensionalPage(MuvitorPageBookView view,int[] dim,int[] sizes){
		this.editor = (MuvitorTreeEditor) view.getEditor();
		this.model = view.getModel();
		//this.pageview = view;
		
		this.dimensions = dim;
		this.dimensionSizes = sizes;
		
	}
	
	abstract protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) ;
	

	final public EObject getModel() {
		return model;
	}

	@SuppressWarnings("rawtypes")
	final public GraphicalViewer getViewer(final EObject model) {
		for (final MultiDimensionalViewer[] arr : viewers) {
			for (MultiDimensionalViewer<T> viewer : arr){
				if (viewer.getContents() != null
						&& viewer.getContents().getModel() == model) {
					return viewer;
				}
			}
		}
		return null;
	}

	final public void setViewersContents(final int viewerPosition,
			final EObject model) {
		Assert.isTrue(viewerPosition < getNumberOfViewers(),
				"AbstractMultiViewerPage tried to set contents for viewer on position"
				+ viewerPosition + " but has only "
				+ getNumberOfViewers() + "viewers!");
		getViewers()[viewerPosition].setContents(model);
		// hide empty viewers
		if (model == null) {
			setViewerVisibility(viewerPosition, false);
		}
	}

	final public void setViewerVisibility(final int viewerPosition,
			final boolean visible) {
		Assert.isTrue(viewerPosition < getNumberOfViewers(),
				"AbstractMultiViewerPage tried to switch visibility of viewer on position"
				+ viewerPosition + " but has only "
				+ getNumberOfViewers() + "viewers!");
		final Control control = getViewers()[viewerPosition].getControl();
		control.setVisible(visible);
		control.getParent().layout(false);
	}

	final protected SelectionSynchronizer getSelectionSynchronizer() {
		final SelectionSynchronizer synchronizer = (SelectionSynchronizer) getEditor()
		.getAdapter(SelectionSynchronizer.class);
		Assert
		.isNotNull(synchronizer,
				"The editor did not deliver a selection synchronizer instance for the page!");
		return synchronizer;
	}

	@Override
	public void dispose() {
		getModel().eAdapters().remove(adapter);
		getActionRegistry().dispose();
		((CommandStack) getEditor().getAdapter(CommandStack.class))
		.removeCommandStackListener(this);

		for (final GraphicalViewer viewer : getViewers()) {
			getSelectionSynchronizer().removeViewer(viewer);
		}

		getSite().getPage().removeSelectionListener(this);

		if (thumbnail != null) {
			thumbnail.deactivate();
			thumbnail = null;
		}
		super.dispose();
	}


	@Override
	final public Control getControl() {
		return flyoutPaletteComposite;
	}

	/**
	 * Sets focus to a part in the page.
	 */
	@Override
	public void setFocus() {
		if (getControl() != null) {
			getControl().setFocus();
		}
	}


	@Override
	final public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		updateActions();
	}

	@SuppressWarnings("unchecked")
	final protected void updateActions() {
		for (final Iterator<IAction> iter = getActionRegistry().getActions(); iter
		.hasNext();) {
			final IAction action = iter.next();
			if (action instanceof UpdateAction) {
				((UpdateAction) action).update();
			}
		}
	}


	@SuppressWarnings("rawtypes")
	public Object getAdapter(final Class adapter) {
		if (adapter == ZoomManager.class) {
			return zoomManagerDelegate;
		} else if (adapter == IPropertySheetPage.class) {
			return editor.getAdapter(adapter);
		}
		return null;
	}


	final protected ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	final protected MuvitorTreeEditor getEditor() {
		return editor;
	}

	final void setCurrentViewer(final MultiDimensionalViewer<T> viewer) {
		Assert.isNotNull(viewer,
				"AbstractMultiViewerPage can not be set to viewer 'null'!");

		// update current viewer only if the passed is different to it
		if (currentViewer == viewer) {
			// this may happen if we switch from the tree viewer to this viewer,
			// so we have to update the actions
			updateActions();
			return;
		}

		currentViewer = viewer;
		currentViewer.getControl().setFocus();

		// set current viewer as actual selection provider for this page's site
		selectionProvider.setSelectionProviderDelegate(currentViewer);

		updateActions();

		// update current viewer as source of Thumbnail
		final ScalableFreeformRootEditPart rootEditPart = (ScalableFreeformRootEditPart) currentViewer
		.getRootEditPart();

		thumbnail.setViewport((Viewport) rootEditPart.getFigure());
		thumbnail.setSource(rootEditPart
				.getLayer(LayerConstants.PRINTABLE_LAYERS));
		thumbnail.setVisible(true);

		// update current viewer's ZoomManager for ZoomManagerDelegate
		zoomManagerDelegate.setCurrentZoomManager((ZoomManager) currentViewer
				.getProperty(ZoomManager.class.toString()));
	}



	final public void commandStackChanged(final EventObject event) {
		updateActions();
	}


	@Override
	public void init(final IPageSite pageSite) {
		super.init(pageSite);
		getModel().eAdapters().add(adapter);
		((CommandStack) getEditor().getAdapter(CommandStack.class))
		.addCommandStackListener(this);
	}

	final protected IAction registerAction(final IAction action) {
		getActionRegistry().registerAction(action);
		return action;
	}

	final private void createActions() {
		// create standard zoom actions for this page
		registerActionAsHandler(new ZoomInAction(zoomManagerDelegate));
		registerActionAsHandler(new ZoomOutAction(zoomManagerDelegate));

		// register shared standard GEF actions from the editor
		// save is treated specially and does not need this
		registerSharedActionAsHandler(ActionFactory.REVERT.getId());
		registerSharedActionAsHandler(ActionFactory.DELETE.getId());
		registerSharedActionAsHandler(ActionFactory.UNDO.getId());
		registerSharedActionAsHandler(ActionFactory.REDO.getId());

		//registerActionAsHandler(new SelectAllInMultiViewerAction(this));

		// register shared actions from editor to put them in the local context
		// menu
		registerSharedAction(ExportViewerImageAction.ID);
		registerSharedAction(TrimViewerAction.ID);
		registerSharedAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY);
		registerSharedAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY);

		// register shared standard node-moving actions from the main editor for
		// the local key handler
		registerSharedAction(MoveNodeAction.LEFT);
		registerSharedAction(MoveNodeAction.RIGHT);
		registerSharedAction(MoveNodeAction.UP);
		registerSharedAction(MoveNodeAction.DOWN);
		registerSharedAction(MoveNodeAction.PREC_LEFT);
		registerSharedAction(MoveNodeAction.PREC_RIGHT);
		registerSharedAction(MoveNodeAction.PREC_UP);
		registerSharedAction(MoveNodeAction.PREC_DOWN);

		// register shared standard aligning actions from the main editor for
		// the context menu
		registerSharedAction(GEFActionConstants.ALIGN_LEFT);
		registerSharedAction(GEFActionConstants.ALIGN_CENTER);
		registerSharedAction(GEFActionConstants.ALIGN_RIGHT);
		registerSharedAction(GEFActionConstants.ALIGN_TOP);
		registerSharedAction(GEFActionConstants.ALIGN_MIDDLE);
		registerSharedAction(GEFActionConstants.ALIGN_BOTTOM);

		createCustomActions();
	}

	final protected IAction registerActionAsHandler(final IAction action) {
		final String id = action.getId();
		getSite().getActionBars().setGlobalActionHandler(id,
				registerAction(action));
		return action;
	}

	final protected IAction registerSharedAction(final String id) {
		final ActionRegistry editorRegistry = (ActionRegistry) getEditor()
		.getAdapter(ActionRegistry.class);
		Assert
		.isNotNull(editorRegistry,
		"The editor did not deliver an ActionRegistry instance for the page!");

		final IAction action = editorRegistry.getAction(id);
		Assert.isNotNull(action,
				"The editor did not deliver an action with the id '" + id
				+ "' for the page!");
		return registerAction(action);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	final public void createControl(final Composite parent) {
		final Plugin plugin = MuvitorActivator.getDefault();
		flyoutPaletteComposite = new FlyoutPaletteComposite(parent, SWT.NONE,
				getSite().getPage(),
				new PaletteViewerProvider(getEditDomain()),
				FlyoutPaletteComposite.createFlyoutPreferences(plugin
						.getPluginPreferences()));

		// This sets the state of the flyout palette to "pinned open"
		plugin.getPluginPreferences().setValue("org.eclipse.gef.pstate", 4);

		// create control that hosts graphical viewer
		final Composite viewerControl = createGraphicalViewerComposite();
		flyoutPaletteComposite.setGraphicalControl(viewerControl);

		// install thumbnail
		installThumbnailInPalette();

		// set first viewer as default current viewer
		setCurrentViewer(getViewers()[0]);

		// set selection provider intermediate for the page's site
		getSite().setSelectionProvider(selectionProvider);

		// a listener to the global selection service
		getSite().getPage().addSelectionListener(this);

		// create actions, this is done here because zoom actions need a
		// viewer's zoom manager
		createActions();

		// add key strokes defined by concrete subclasses
		setupKeyHandler(getKeyHandler());
	}

	final protected EditDomain getEditDomain() {
		if (null == editDomain) {
			editDomain = new EditDomain() {
				/**
				 * Overridden to set different behavior for the
				 * {@link MarqueeSelectionTool}.
				 * 
				 * @see org.eclipse.gef.EditDomain#setActiveTool(org.eclipse.gef.Tool)
				 */
				@Override
				public void setActiveTool(final Tool tool) {
					if (tool instanceof MarqueeSelectionTool) {
						// this may be changed according individual needs
						((MarqueeSelectionTool) tool)
						.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED);
					}
					super.setActiveTool(tool);
				}
			};
			editDomain.setPaletteRoot(createPaletteRoot());
			final CommandStack commandStack = (CommandStack) getEditor()
			.getAdapter(CommandStack.class);
			Assert
			.isNotNull(commandStack,
			"The editor did not deliver a command stack instance for the page!");
			editDomain.setCommandStack(commandStack);
		}
		return editDomain;
	}


	final private void installThumbnailInPalette() {
		// get the FigureCanvas of the PaletteViewer
		final FigureCanvas figureCanvas = (FigureCanvas) getEditDomain()
		.getPaletteViewer().getControl();

		// install a SashForm on the parent of the FigureCanvas
		final SashForm sashForm = new SashForm(figureCanvas.getParent(),
				SWT.VERTICAL);

		// move the original figureCanvas onto the SashForm
		figureCanvas.setParent(sashForm);

		// create a new FigureCanvas and put a new Thumbnail on it
		final FigureCanvas thumbCanvas = new FigureCanvas(sashForm);

		thumbnail = new ScrollableThumbnail();
		thumbnail.setBorder(new MarginBorder(3));
		thumbCanvas.setContents(thumbnail);

		// adjust the position of the thumbnail on the canvas
		sashForm.setWeights(getThumbSashWeights());
	}


	protected Composite getCustomContents(int i,Composite parent) {
		return null;
	}

	final protected KeyHandler getKeyHandler() {
		if (keyHandler == null) {
			keyHandler = new KeyHandler();
			// this may be null
			keyHandler.setParent((KeyHandler) getEditor().getAdapter(
					KeyHandler.class));
			keyHandler
			.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
					getActionRegistry().getAction(
							ActionFactory.DELETE.getId()));
			keyHandler.put(KeyStroke.getPressed(SWT.F2, 0), getActionRegistry()
					.getAction(GEFActionConstants.DIRECT_EDIT));

			// Arrow keys run the correspondent MoveNodeActions
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, 0),
					getActionRegistry().getAction(MoveNodeAction.LEFT));
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, 0),
					getActionRegistry().getAction(MoveNodeAction.RIGHT));
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, 0),
					getActionRegistry().getAction(MoveNodeAction.UP));
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, 0),
					getActionRegistry().getAction(MoveNodeAction.DOWN));

			// SHIFT + Arrow keys run the correspondent precise MoveNodeActions
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, SWT.SHIFT),
					getActionRegistry().getAction(MoveNodeAction.PREC_LEFT));
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, SWT.SHIFT),
					getActionRegistry().getAction(MoveNodeAction.PREC_RIGHT));
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, SWT.SHIFT),
					getActionRegistry().getAction(MoveNodeAction.PREC_UP));
			keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, SWT.SHIFT),
					getActionRegistry().getAction(MoveNodeAction.PREC_DOWN));
		}
		return keyHandler;
	}

	final public MultiDimensionalViewer<T> getCurrentViewer() {
		return currentViewer;
	}



	final protected IAction registerSharedActionAsHandler(final String id) {
		final IAction editorAction = registerSharedAction(id);
		getSite().getActionBars().setGlobalActionHandler(id, editorAction);
		return editorAction;
	}

	abstract protected void createCustomActions();

	abstract protected EditPartFactory createEditPartFactory();

	protected MuvitorPaletteRoot createPaletteRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumberOfViewers() {
		int numberOfViewers = 0;
		for (int i = 0; i < dimensions.length; i++) {
			numberOfViewers += dimensions[i];
		}
		return numberOfViewers;
	}

	public int getDimension(int dim) {
		// TODO Auto-generated method stub
		return dimensions[dim];
	}

	public int[] getDimensionSize() {

		return dimensionSizes;
	}

	protected int[] getThumbSashWeights() {
		// TODO Auto-generated method stub
		return new int[] { 3, 1 };
	}



	@SuppressWarnings("unchecked")
	public T getCastedModel(){

		return (T)getModel();
	}


	public EObject[] getViewerContents() {

		//((ActiGra)getCastedModel().eContainer()).getObjectGraphs().add(coDomain);
		//((ActiGra)getCastedModel().eContainer()).getObjectGraphs().add(domain);
		return getContentsForIndex(0);
	}
	/**
	 * 
	 *  getContentsForIndex(int i) has to return an array with all Eobjects that should be displayed in the MultidimensionalPage if you created this Page with the dimension {3,2,1} then getContentsForIndex should return an array with the length of 6 the first three elements will be the 3 EObjects for the first row, the fourth and fith element will be put into the second row and the last element will be shown in the last row.
	 * @param i
	 * 			the index for the currently selected tab
	 * @see getNumberOfItems()
	 */
	protected abstract EObject[] getContentsForIndex(int i);
		
	abstract protected void setupKeyHandler(KeyHandler kh) ;
	abstract protected void notifyChanged(Notification msg);


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MultiDimensionalViewer[] getPageViewers(){
		ArrayList<MultiDimensionalViewer<T>> list = new ArrayList<MultiDimensionalViewer<T>>();
		for (int i = 0; i < viewers.length; i++) {
			for (int j = 0; j < viewers[i].length; j++) {
				list.add(viewers[i][j]);
			}
		}
		return list.toArray(new MultiDimensionalViewer[]{});
	}

	protected Composite createGraphicalViewerComposite() {
		viewers = new MultiDimensionalViewer[getNumberOfItems()][getNumberOfViewers()];

		// create composite that hold the graphical viewers
		CTabFolder folder = new CTabFolder(flyoutPaletteComposite,SWT.BOTTOM);
		folder.setSimple(false);
		folder.setMRUVisible(true);
		folder.setLayout(new FillLayout());
		for (int index = 0;index < getNumberOfItems();index++){
			CTabItem item = new CTabItem(folder, SWT.NULL);

			Composite viewerComposite = null;
			if (enableSashComposite(index)){
				viewerComposite = new SashForm(folder, SWT.BORDER | SWT.VERTICAL | SWT.SMOOTH);
	
			} else {
				viewerComposite = new Composite(folder,
						SWT.BORDER );
			}
			GridLayout gridLayout = new GridLayout(1,true);

			viewerComposite.setLayout(gridLayout);


			item.setControl(viewerComposite);
			item.setText(getName(index));


			GridData d = new GridData();
			d.grabExcessHorizontalSpace = true;
			d.grabExcessVerticalSpace = true;
			d.minimumHeight = 40;
			d.widthHint = 2000;
			d.heightHint = 2000;
			viewerComposite.setLayoutData(d);

			// prepare shared edit part factory
			final EditPartFactory editPartFactory = createEditPartFactory();

			// prepare contents to set
			final EObject[] contents = getContentsForIndex(index);

			Composite container = null;

			int counter = 0;
			int dim = 0;

			// create viewers
			for (int i = 0; i < getNumberOfViewers(); i++) {

				if (counter == 0){
					container = new SashForm(viewerComposite,SWT.BORDER);
					container.setLayout(new FillLayout());
				}

				if (counter == getDimension(dim)){
					dim++;
					counter = 0;
					container = new SashForm(viewerComposite,SWT.BORDER);
					container.setLayout(new FillLayout());
				}

				final MultiDimensionalViewer<T> viewer = new MultiDimensionalViewer<T>(this);
				getEditDomain().addViewer(viewer);

				final RulerComposite rulerComp = new RulerComposite(
						container, SWT.NONE);

				// set the new viewer on the ruler composite
				viewer.createControl(rulerComp);

				viewers[index][i] = viewer;
				viewer.setKeyHandler(getKeyHandler());
				getSelectionSynchronizer().addViewer(viewer);
				final ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
				viewer.setRootEditPart(rootEditPart);
				viewer.setEditPartFactory(editPartFactory);

				GridData d1 = new GridData();
				d1.grabExcessHorizontalSpace = true;
				d1.grabExcessVerticalSpace = grabExcessVerticalSpace(dim, counter);
				d1.widthHint = 2000;
				d1.heightHint = getHeight(dim, counter);
				d1.minimumHeight = getMinimumHeight(dim, counter);
				
				container.setLayoutData(d1);		
				
				

				viewer.setContents(contents[i]);

				final ContextMenuProviderWithActionRegistry cmp = createContextMenuProvider(viewer);
				cmp.setActionRegistry(getActionRegistry());
				viewer.setContextMenu(cmp);

				// set antialias on connection layer, this may be turned off if
				// causing trouble
				((ConnectionLayer) rootEditPart
						.getLayer(LayerConstants.CONNECTION_LAYER))
						.setAntialias(SWT.ON);

				// initialize viewer's ZoomManager
				final ZoomManager zoomManager = (ZoomManager) viewer
				.getProperty(ZoomManager.class.toString());
				final List<String> zoomLevels = new ArrayList<String>(3);
				zoomLevels.add(ZoomManager.FIT_ALL);
				zoomLevels.add(ZoomManager.FIT_WIDTH);
				zoomLevels.add(ZoomManager.FIT_HEIGHT);
				zoomManager.setZoomLevelContributions(zoomLevels);
				zoomManager.setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);

				// some optional coloring
				viewer.getControl().setBackground(
						PlatformUI.getWorkbench().getDisplay().getSystemColor(
								SWT.COLOR_LIST_BACKGROUND));
				viewer.getControl().setForeground(
						PlatformUI.getWorkbench().getDisplay().getSystemColor(
								SWT.COLOR_LIST_FOREGROUND));

				// prepare viewer for showing rulers and grid
				rulerComp.setGraphicalViewer(viewer);
				viewer.setProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER,
						new MuvitorRulerProvider());
				viewer.setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,
						new MuvitorRulerProvider());
				viewer.setProperty(RulerProvider.PROPERTY_RULER_VISIBILITY,
						Boolean.FALSE);
				viewer.setProperty(SnapToGrid.PROPERTY_GRID_SPACING, new Dimension(
						20, 20));

				counter++;
				if (container instanceof SashForm){
					((SashForm) container).setWeights(getChildDimensions(dim));
				}
			}
			if (viewerComposite instanceof SashForm){
				((SashForm) viewerComposite).setWeights(getDimensionSize());
			}
		}
		//pageview.updatePageLayout();
		folder.setSelection(0);

		return folder;
	}

	
	protected int[] getChildDimensions(int dim) {
		// TODO Auto-generated method stub
		int[] result = new int[this.dimensions[dim]];
		Arrays.fill(result, 1);
		return result;
	}

	protected boolean enableSashComposite(int index) {
		// TODO Auto-generated method stub
		return true;
	}

	protected abstract String getName(int index);

	/**
	 * 
	 * @return this function should return the number of tabs that should be displayed 
	 */
	abstract protected int getNumberOfItems();
	


	@SuppressWarnings("rawtypes")
	protected MultiDimensionalViewer[] getViewers() {
		// TODO Auto-generated method stub
		return viewers[0];
	}

	final public class MuvitorRulerProvider extends RulerProvider {
		@Override
		public int getUnit() {
			return RulerProvider.UNIT_PIXELS;
		}

		@Override
		public Object getRuler() {
			return MuvitorRulerProvider.this;
		}
	}


	protected int getHeight(int dim,int counter){
		return 2000;
	}
	
	protected boolean grabExcessVerticalSpace(int dim, int counter){
		return true;
	}

	protected int getMinimumHeight(int dim,int counter){
		return getDimensionSize()[dim];
	}
}
