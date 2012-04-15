/**
 * ValidateFlowDiagramAction.java
 *
 * Created 31.12.2011 - 16:35:36
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramPage;
import de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramView;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowDiagramValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * @author nam
 * 
 */
public class ValidateFlowDiagramAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramAction";

	private FlowDiagram model;

	/**
	 * @param part
	 */
	public ValidateFlowDiagramAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Validate Diagram");
		setDescription("Validate this Flow Diagram");
		setToolTipText("Validate this Flow Diagram");
		setImageDescriptor(ResourceUtil.ICONS.CHECK.descr(18));
	}

	/**
	 * @return the model
	 */
	public FlowDiagram getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(FlowDiagram model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		IWorkbenchPart part = MuvitorTreeEditor.showView(model);

		FlowDiagramPage modelPage = (FlowDiagramPage) ((FlowDiagramView) part)
				.getCurrentPage();

		ResourceSet resource = new ResourceSetImpl();
		Resource rulesInstances = resource
				.createResource(URI
						.createURI("platform:/plugin/de.tub.tfs.henshin.editor/resources/flowRules.henshin"));

		try {
			rulesInstances.load(null);
			List<?> contents = rulesInstances.getContents();

			if (!contents.isEmpty()) {
				for (Object o : contents) {
					if (o instanceof TransformationSystem) {
						FlowDiagramValidator validator = new FlowDiagramValidator(
								((TransformationSystem) o).getRules(), model);

						validator.run();

						GraphicalViewer diagramViewer = modelPage
								.getDiagramViewer();

						Set<FlowElement> elements = new HashSet<FlowElement>(
								model.getElements());

						elements.removeAll(validator.getFailed());

						for (FlowElement e : elements) {
							GraphicalEditPart elementPart = (GraphicalEditPart) diagramViewer
									.getEditPartRegistry().get(e);

							if (elementPart != null) {
								elementPart.getFigure().setBackgroundColor(
										ColorConstants.lightGray);
							}
						}

						for (FlowElement e : validator.getFailed()) {
							GraphicalEditPart elementPart = (GraphicalEditPart) diagramViewer
									.getEditPartRegistry().get(e);

							if (elementPart != null) {
								elementPart.getFigure().setBackgroundColor(
										ColorConstants.red);
							}
						}

						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		model = null;

		if (selection.size() == 1) {
			Object selected = selection.get(0);

			if (selected instanceof EditPart) {
				Object model = ((EditPart) selected).getModel();

				if (model instanceof FlowDiagram) {
					this.model = (FlowDiagram) model;
				}
			}
		}

		return model != null;
	}
}
