/**
 * ShowMetaModelAction.java
 * created on 03.05.2013 22:37:13
 */
package de.tub.tfs.henshin.tggeditor.actions;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.TGGEditorActivator;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;
import de.tub.tfs.henshin.tggeditor.util.SelectionUtil;

/**
 * @author huuloi
 */
public class ShowMetaModelAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.ShowMetaModelAction"; //$NON-NLS-1$
	
	private Graph graph;

	public ShowMetaModelAction(IWorkbenchPart part, Graph graph) {
		super(part, AS_CHECK_BOX);
		
		setId(ID);
		setToolTipText("Show nodes with their meta model");
		setText("Show meta model");
		setImageDescriptor(TGGEditorActivator.getImageDescriptor(TGGEditorConstants.ICON_SHOW_META_MODEL_18));
		
		this.graph = graph;
	}


	@Override
	protected boolean calculateEnabled() {
		return true;
	}
	
	
	@Override
	public void run() {
		List<NodeObjectEditPart> nodeEditParts = SelectionUtil.getNodeEditParts(getWorkbenchPart(), graph);
		for (NodeObjectEditPart nodeEditPart : nodeEditParts) {
			nodeEditPart.setShowMetaModel(isChecked());
			nodeEditPart.refreshVisuals();
		}
	}
	
	
	public void setGraph(TripleGraph graph) {
		this.graph = graph;
	}

}
