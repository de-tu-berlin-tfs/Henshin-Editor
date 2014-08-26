/**
 * TypeSearchInGraphAction.java
 * created on 28.03.2013 15:36:49
 */
package de.tub.tfs.henshin.tggeditor.actions.search;

import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.SelectionUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.ExtendedElementListSelectionDialog;

/**
 * @author huuloi
 */
public class TypeSearchAction extends SelectionAction{
	
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.search.TypeSearchInGraphAction"; //$NON-NLS-1$
	
	private Graph graph; 
	
	public TypeSearchAction(IWorkbenchPart part, Graph graph) {
		super(part);
		setId(ID);
		setText(Messages.TypeSearchAction_Text);
		setToolTipText(Messages.TypeSearchAction_ToolTipText);
		this.graph = graph;
	}


	@Override
	protected boolean calculateEnabled() {
		return graph != null && NodeTypes.getUsedNodeTypes(graph).size() > 1;
	}

	
	@Override
	public void run() {
		// open Type Dialog
		Set<EClass> nodeTypes = NodeTypes.getUsedNodeTypes(graph);
		EClass searchForType = new ExtendedElementListSelectionDialog<EClass>(
			getWorkbenchPart().getSite().getShell(), 
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((EClass) element).getName();
				}
			}, 
			nodeTypes.toArray(new EClass[nodeTypes.size()]), 
			Messages.TypeSearchAction_SearchForNodeTypeTitle, 
			Messages.TypeSearchAction_SearchForNodeTypeMsg
		).runSingle();

		// do search
		List<NodeObjectEditPart> nodeEditParts = SelectionUtil.getNodeEditParts(
			getWorkbenchPart(), 
			graph
		);
		for (NodeObjectEditPart nodeEditPart : nodeEditParts) {
			if (EcoreUtil.equals(nodeEditPart.getCastedModel().getType(), searchForType)) {
				nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
			}
			else {
				Color standardColor = ((NodeFigure)nodeEditPart.getFigure()).getStandardColor();
				if (!nodeEditPart.getFigure().getBackgroundColor().equals(standardColor)) {
					nodeEditPart.getFigure().setBackgroundColor(standardColor);
				}
			}
		}
	}
	

	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("typeSearch16.png");
	}
	
}
