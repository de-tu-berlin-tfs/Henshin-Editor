package de.tub.tfs.henshin.tggeditor.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.TGGEditorActivator;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteManyNodesCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


/**
 * This action removes all nodes of the selected TGG components
 * 
 * @author "Frank Hermann"
 */
public class RestrictGraphAction extends SelectionAction {
	
	public static final String ID = "RestrictGraphAction";
	
	private static final String DESC = "Removes components of the graph";
	
	private static final String LABEL = "Restric graph";
	
	static final ArrayList<String> restrictionTypeNames = new ArrayList<String>();
	/**
	 * the restriction types for the subsets of components of a triple graph 
	 */
	static final ArrayList<ArrayList<NodeGraphType>> restrictionTypes = new ArrayList<ArrayList<NodeGraphType>>();
	
	/** the command that deletes all nodes of the chosen components */
	private CompoundCommand compCommand;
	
	static {
		ArrayList<NodeGraphType> removeSource = new ArrayList<NodeGraphType>();		
		ArrayList<NodeGraphType> removeCorrespondence = new ArrayList<NodeGraphType>();
		ArrayList<NodeGraphType> removeTarget = new ArrayList<NodeGraphType>();
		ArrayList<NodeGraphType> removeSourceCorrespondence = new ArrayList<NodeGraphType>();
		ArrayList<NodeGraphType> removeCorrespondenceTarget = new ArrayList<NodeGraphType>();
		removeSource.add(NodeGraphType.SOURCE);
		removeCorrespondence.add(NodeGraphType.CORRESPONDENCE);
		removeTarget.add(NodeGraphType.TARGET);
		removeSourceCorrespondence.add(NodeGraphType.SOURCE);
		removeSourceCorrespondence.add(NodeGraphType.CORRESPONDENCE);
		removeCorrespondenceTarget.add(NodeGraphType.CORRESPONDENCE);
		removeCorrespondenceTarget.add(NodeGraphType.TARGET);
		
		restrictionTypes.add(removeSource);
		restrictionTypes.add(removeCorrespondence);
		restrictionTypes.add(removeTarget);
		restrictionTypes.add(removeSourceCorrespondence);
		restrictionTypes.add(removeCorrespondenceTarget);

		restrictionTypeNames.add("Remove source component");
		restrictionTypeNames.add("Remove correspondence component");
		restrictionTypeNames.add("Remove target component");
		restrictionTypeNames.add("Remove source and correspondence components");
		restrictionTypeNames.add("Remove correspondence and target components");
	}
	
	/**
	 * the viewer containing the currently selected GraphicalEditPart
	 */
	private EditPartViewer viewer;
	
	/**
	 * the currently selected layout algorithm
	 */
	ArrayList<NodeGraphType> currentRestrictionType;
	
	/**
	 * The constructor prepares the menu to selected the layout algorithm from
	 * 
	 * @param part
	 *            the workbench part
	 */
	public RestrictGraphAction(final IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(LABEL);
		setDescription(DESC);
		setToolTipText(DESC);
		setImageDescriptor(TGGEditorActivator.getImageDescriptor(TGGEditorConstants.ICON_DELETE_18));
		currentRestrictionType = restrictionTypes.get(0);
		setMenuCreator(new IMenuCreator() {
			
			Menu menu;
			
			@Override
			public void dispose() {
				if (menu != null) {
					menu.dispose();
					menu = null;
				}
			}
			
			@Override
			public Menu getMenu(final Control parent) {
				if (menu == null) {
					menu = new Menu(parent);
					fillMenu();
				}
				return menu;
			}
			
			@Override
			public Menu getMenu(final Menu parent) {
				// not intended as submenu
				return null;
			}
			
			private void fillMenu() {
				for (final String restrictionTypeName : restrictionTypeNames) {
					final MenuItem item = new MenuItem(menu, SWT.RADIO);
					item.setText(restrictionTypeName);
					item.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							final int index = menu.indexOf((MenuItem) e.widget);
							currentRestrictionType = restrictionTypes.get(index);
						}
					});
				}
				// select first item by default
				menu.getItem(0).setSelection(true);
			}
		});
		
	}
	
	/**
	 * Gets the Graph Layout Command and executes it.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// when invoking directly, a viewer must be set manually!
		if (viewer == null) {
			return;
		}
		
		// 
		
		Graph graph=null;
		TGG tgg=null;
		compCommand = new CompoundCommand();
		if (viewer.getContents().getModel() instanceof Graph)
			graph = (Graph) viewer.getContents().getModel();
		if (graph != null)
			tgg = NodeUtil.getLayoutSystem(graph);
		if (tgg!=null)
			removeNodes(tgg,currentRestrictionType);
	}
	
	private void removeNodes(TGG tgg,
			ArrayList<NodeGraphType> restrictionType) {
		
		List<Node> nodesToDelete = new Vector<Node>();
		for (final EditPart editPart : (Collection<EditPart>) viewer.getContents().getChildren()) {
			if (editPart instanceof NodeEditPart) {
				Node node = (Node) editPart.getModel();
				if(	(NodeUtil.isSourceNode(node) && (restrictionType.contains(NodeGraphType.SOURCE)) )
					|| (NodeUtil.isCorrespondenceNode(node) && (restrictionType.contains(NodeGraphType.CORRESPONDENCE)) )
					|| (NodeUtil.isTargetNode(node) && (restrictionType.contains(NodeGraphType.TARGET)) )						
				){
					nodesToDelete.add((Node) editPart.getModel());
						}
			}
		}
		
		Command cmd = new DeleteManyNodesCommand(nodesToDelete);
		execute(cmd);
	}

	/**
	 * This setter allows universal usage of this action. Just call the
	 * constructor with <code>null</code> and set the algorithm for layout
	 * manually.
	 * 
	 * @param name
	 *            the name of the algorithm to use. Must be one of
	 *            {@value #restrictionTypes}.
	 * @see #setViewer(EditPartViewer)
	 */
	public void setRestrictionType(final String name) {
		currentRestrictionType = restrictionTypes.get(restrictionTypeNames.indexOf(name));
	}
	
	/**
	 * This setter allows universal usage of this action. Just call the
	 * constructor with <code>null</code> and set the viewer for layout
	 * manually.
	 * 
	 * @param viewer
	 * @see #setRestrictionType(String)
	 */
	public void setViewer(final EditPartViewer viewer) {
		this.viewer = viewer;
	}
	
	/**
	 * This action is enabled if some graphical edit part is currently selected
	 * from which a viewer can be determined to be trimmed.
	 */
	@Override
	protected boolean calculateEnabled() {
		viewer = null;
		if (getSelection() == null) {
			return false;
		}
		if (getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) getSelection();
			for (final Object selectedObject : selection.toList()) {
				if (selectedObject instanceof GraphicalEditPart) {
					viewer = ((GraphicalEditPart) selectedObject).getViewer();
					return viewer != null;
				}
			}
		}
		return false;
	}
	
}
