/**
 * ValidateFlowDiagramToolbarAction.java
 *
 * Created 31.12.2011 - 16:43:46
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramPage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;

/**
 * @author nam
 * 
 */
public class ValidateFlowDiagramToolbarAction extends ValidateFlowDiagramAction {

	/**
	 * @param model
	 * @param part
	 */
	public ValidateFlowDiagramToolbarAction(FlowDiagram model,
			IWorkbenchPart part, final FlowDiagramPage modelPage) {
		super(part);

		setModel(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramAction
	 * #calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return getModel() != null;
	}

}
