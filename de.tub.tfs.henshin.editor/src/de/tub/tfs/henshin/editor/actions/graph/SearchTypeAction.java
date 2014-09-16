/**
 * SearchTypeAction.java
 * created on 18.03.2012 16:50:17
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.NodeTypes;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 * 
 */
public class SearchTypeAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.SearchTypeAction";

	private Graph graph;

	public SearchTypeAction(IWorkbenchPart part, Graph graph) {

		super(part);
		setId(ID);
		setText(Messages.TYPE_SEARCH);
		setToolTipText(Messages.TYPE_SEARCH_DESC);
		this.graph = graph;
	}

	@Override
	protected boolean calculateEnabled() {
		return graph != null && NodeTypes.getUsedNodeTypes(graph).size() > 1;
	}

	@Override
	public void run() {
		// open Type Dialog
		GraphView graphView = HenshinSelectionUtil.getInstance()
				.getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		Set<EClass> nodeTypes = NodeTypes.getUsedNodeTypes(graph);
		EClass searchForType = new ExtendedElementListSelectionDialog<EClass>(
				shell, new LabelProvider() {

					@Override
					public String getText(Object element) {
						return ((EClass) element).getName();
					}

					@Override
					public Image getImage(Object element) {
						return ResourceUtil.ICONS.NODE.img(Constants.SIZE_18);
					}
				}, 
				nodeTypes.toArray(new EClass[nodeTypes.size()]),
				Messages.SEARCH_FOR_NODE_TYPE,
				Messages.SEARCH_FOR_NODE_TYPE_DESC
		).runSingle();

		// do search
		List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance()
				.getNodeEditParts(graph);
		for (NodeEditPart nodeEditPart : nodeEditParts) {
			if (EcoreUtil.equals(nodeEditPart.getCastedModel().getType(), searchForType)) {
				nodeEditPart.getFigure().setBackgroundColor(
						ColorConstants.lightBlue);
			} else if (!EcoreUtil.equals(nodeEditPart.getCastedModel()
					.getType(), searchForType)
					&& nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart
							.getDefaultColor()) {
				nodeEditPart.getFigure().setBackgroundColor(
						nodeEditPart.getDefaultColor());
			}
		}

		// refresh
		((GraphEditPart) graphView.getCurrentGraphPage().getCurrentViewer()
				.getEditPartRegistry().get(graph)).refresh();

	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ResourceUtil.ICONS.TYPE_SEARCH_TOOL.descr(Constants.SIZE_16);
	}
}
