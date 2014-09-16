/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.TreeEditPart;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.TreeContainerEditPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.FlowDiagramClipBoardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.FlowDiagramComponentEditPolicy;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * A {@link TreeEditPart} for {@link FlowDiagram} model objects.
 * 
 * @see AdapterTreeEditPart
 * 
 * @author nam
 */
public class FlowDiagramTreeEditpart extends AdapterTreeEditPart<FlowDiagram>
		implements IDirectEditPart {

	private EContainerDescriptor parameters;

	/**
	 * Constructor, instantiates a {@link FlowDiagramTreeEditpart} for a given
	 * {@link FlowDiagram} model object.
	 * 
	 * @param model
	 *            a {@link FlowDiagram} model object
	 */
	public FlowDiagramTreeEditpart(FlowDiagram model) {
		super(model);

		Map<EClass, EStructuralFeature> parametersContainmentMap = new HashMap<EClass, EStructuralFeature>();

		parametersContainmentMap.put(FlowControlPackage.Literals.PARAMETER,
				FlowControlPackage.Literals.PARAMETER_PROVIDER__PARAMETERS);

		parameters = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		parameters.setContainer(model);
		parameters.setContainmentMap(parametersContainmentMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.FLOW_DIAGRAM.img(16);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		return getCastedModel().getName();
	}

	@Override
	protected void notifyChanged(Notification notification) {
		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart#getDirectEditFeatureID
	 * ()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return FlowControlPackage.FLOW_DIAGRAM__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<?> getModelChildren() {
		List<Object> children = new LinkedList<Object>();

		// children.addAll(getCastedModel().getElements());
		// children.add(parameters);

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new FlowDiagramClipBoardEditPolicy());

		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new FlowDiagramComponentEditPolicy());

		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TreeContainerEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart#getDirectEditValidator
	 * ()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(getCastedModel().eContainer(),
				FlowControlPackage.FLOW_CONTROL_SYSTEM__UNITS, true,
				getCastedModel(), FlowControlPackage.FLOW_DIAGRAM__NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getAdapter(java.
	 * lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (FlowDiagram.class.equals(key)) {
			return getCastedModel();
		}

		return super.getAdapter(key);
	}
}
