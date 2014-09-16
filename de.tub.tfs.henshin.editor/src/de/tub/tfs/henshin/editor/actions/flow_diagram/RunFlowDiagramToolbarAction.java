/**
 * RunFlowDiagramToolbarAction.java
 *
 * Created 31.12.2011 - 15:04:56
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;

/**
 * @author nam
 * 
 */
public class RunFlowDiagramToolbarAction extends ExecuteFlowDiagramAction {

	/**
	 * @param part
	 */
	public RunFlowDiagramToolbarAction(FlowDiagram model, IWorkbenchPart part) {
		super(part);

		setModel(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.actions.flow_diagram.RunFlowDiagramAction#
	 * calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return getModel() != null;
	}
}
