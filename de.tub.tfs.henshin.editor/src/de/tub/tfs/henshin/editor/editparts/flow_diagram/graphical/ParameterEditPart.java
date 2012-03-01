/**
 * ParameterEditPart.java
 *
 * Created 23.12.2011 - 17:39:32
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;

import de.tub.tfs.henshin.editor.figure.flow_diagram.ParameterFigure;
import de.tub.tfs.henshin.editor.figure.flow_diagram.ParameterFigure.TYPE;
import de.tub.tfs.henshin.editor.figure.flow_diagram.ParameterFigureConnectionAnchor;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * @author nam
 * 
 */
public class ParameterEditPart extends AdapterGraphicalEditPart<Parameter>
		implements NodeEditPart {

	/**
	 * @param model
	 */
	public ParameterEditPart(final Parameter model) {
		super(model);

		if (model.getProvider() instanceof Activity) {
			Activity container = (Activity) model.getProvider();
			FlowElementLayout containerLayout = HenshinLayoutUtil.INSTANCE
					.getLayout((FlowElement) container);

			registerAdapter(containerLayout);

			if (container.getContent() != null) {
				registerAdapter(container.getContent());
			}
		}

		registerAdapter(model.getHenshinParameter());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#notifyChanged
	 * (org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		int id = notification.getFeatureID(HenshinLayoutPackage.class);

		if (id == HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__MAP_ID
				&& notification.getNotifier() instanceof FlowElementLayout) {
			((ParameterFigure) getFigure()).setMapping(notification
					.getNewIntValue());
		}

		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		ParameterFigure fig = new ParameterFigure();
		Parameter model = getCastedModel();

		fig.setName(model.getHenshinParameter().getName());

		updateMapping(fig);

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new ParameterGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
				new SelectionEditPolicy() {

					@Override
					protected void showSelection() {
						((ParameterFigure) getHostFigure())
								.setHighlighted(true);
					}

					@Override
					protected void hideSelection() {
						((ParameterFigure) getHostFigure())
								.setHighlighted(false);
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	@Override
	protected List<ParameterMapping> getModelSourceConnections() {
		List<ParameterMapping> outMapping = new LinkedList<ParameterMapping>();
		FlowDiagram diagram;

		ParameterProvider provider = getCastedModel().getProvider();
		if (provider instanceof Activity) {
			diagram = ((Activity) provider).getDiagram();
		} else {
			diagram = (FlowDiagram) provider;
		}

		List<EObject> allMappings = ModelUtil.getReferences(getCastedModel(),
				diagram, FlowControlPackage.Literals.PARAMETER_MAPPING__SRC);

		for (EObject m : allMappings) {
			ParameterMapping mapping = (ParameterMapping) m;

			if (!(mapping.getTarget().getProvider() instanceof FlowDiagram)) {
				outMapping.add(mapping);
			}
		}

		return outMapping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	@Override
	protected List<ParameterMapping> getModelTargetConnections() {
		List<ParameterMapping> inMapping = new LinkedList<ParameterMapping>();
		ParameterProvider provider = getCastedModel().getProvider();

		FlowDiagram diagram;
		if (provider instanceof FlowDiagram) {
			diagram = (FlowDiagram) provider;
		} else {
			diagram = ((Activity) provider).getDiagram();
		}

		List<EObject> allMappings = ModelUtil.getReferences(getCastedModel(),
				diagram, FlowControlPackage.Literals.PARAMETER_MAPPING__TARGET);

		for (EObject m : allMappings) {
			ParameterMapping mapping = (ParameterMapping) m;

			if (!(mapping.getSrc().getProvider() instanceof FlowDiagram)) {
				inMapping.add(mapping);
			}
		}

		return inMapping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ParameterFigureConnectionAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ParameterFigureConnectionAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		Parameter model = getCastedModel();
		ParameterFigure fig = (ParameterFigure) getFigure();

		if (model.isInput()) {
			fig.setType(TYPE.IN);
		} else if (model.isOutPut()) {
			fig.setType(TYPE.OUT);
		} else {
			fig.setType(TYPE.NONE);
		}

		fig.setName(model.getHenshinParameter().getName());

		updateMapping(fig);

		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ParameterFigureConnectionAnchor(getFigure());
	}

	/**
	 * @return
	 */
	public int get2NodeMappingID() {
		if (getCastedModel().getProvider() instanceof Activity) {
			Activity container = (Activity) getCastedModel().getProvider();
			FlowElementLayout containerLayout = HenshinLayoutUtil.INSTANCE
					.getLayout((FlowElement) container);

			return containerLayout.getMapId();
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ParameterFigureConnectionAnchor(getFigure());
	}

	/**
	 * @param fig
	 */
	private void updateMapping(ParameterFigure fig) {
		Parameter model = getCastedModel();

		if (model.getProvider() instanceof Activity) {
			Activity container = (Activity) model.getProvider();
			EObject content = container.getContent();
			FlowElementLayout containerLayout = HenshinLayoutUtil.INSTANCE
					.getLayout((FlowElement) container);

			fig.setMapping(containerLayout.getMapId());

			if (content instanceof FlowDiagram) {
				for (ParameterMapping m : container.getParameterMappings()) {
					if (m.getSrc() == model || m.getTarget() == model) {
						Parameter target = m.getTarget() == model ? m.getSrc()
								: m.getTarget();
						FlowDiagram d = (FlowDiagram) content;

						if (target.isInput()) {
							for (ParameterMapping m0 : d.getParameterMappings()) {
								if (m0.getSrc() == target) {
									Activity a = (Activity) m0.getTarget()
											.getProvider();

									int idExt = HenshinLayoutUtil.INSTANCE
											.getLayout(a).getMapId();

									fig.setMapExt(idExt, true);
								}
							}
						} else if (target.isOutPut()) {
							for (ParameterMapping m0 : d.getParameterMappings()) {
								if (m0.getTarget() == target) {
									Activity a = (Activity) m0.getSrc()
											.getProvider();

									int idExt = HenshinLayoutUtil.INSTANCE
											.getLayout(a).getMapId();

									fig.setMapExt(idExt, false);
								}
							}
						}
					}
				}
			}
		}
	}

}
