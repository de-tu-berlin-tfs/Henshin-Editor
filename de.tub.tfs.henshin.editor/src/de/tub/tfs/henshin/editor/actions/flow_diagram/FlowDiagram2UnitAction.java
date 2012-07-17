/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.List;

import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlInterpreter;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;

/**
 * An {@link Action} used to convert {@link FlowDiagram}s to
 * {@link TransformationUnit}s.
 * 
 * @author nam
 * 
 */
public class FlowDiagram2UnitAction extends SelectionAction {

	public static String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.FlowDiagram2UnitAction";

	private static String DESC = "Convert this Flow Diagram into an appropriate transformation unit.";

	/**
	 * The {@link FlowDiagram} to be converted.
	 */
	private FlowDiagram model;

	/**
	 * @param part
	 */
	public FlowDiagram2UnitAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Translate");
		setDescription(DESC);
		setToolTipText(DESC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		model = null;

		if (selectedObjects.size() == 1) {
			Object obj = selectedObjects.get(0);

			if (obj instanceof EditPart) {
				EditPart part = (EditPart) obj;
				Object model = part.getModel();

				if (model instanceof FlowDiagram) {
					FlowDiagram diag = (FlowDiagram) model;

					this.model = diag;
				}
			}
		}

		return model != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		FlowControlInterpreter intpr = new FlowControlInterpreter(model);
		
		TransformationUnit unit = intpr.parse();
		
		TransformationSystem transformationSystem = HenshinUtil.INSTANCE.getTransformationSystem(model);
		
		if(transformationSystem != null){
			transformationSystem.getTransformationUnits().add(unit);
		}
	}

}
