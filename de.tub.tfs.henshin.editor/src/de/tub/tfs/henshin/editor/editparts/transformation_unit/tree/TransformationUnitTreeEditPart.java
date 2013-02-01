/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.TreeContainerEditPolicy;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.transformation_unit.TransformationUnitPropertySource;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * The Class TransformationUnitTreeEditPart.
 * 
 * @param <T>
 *            the type of an specific {@link Unit}.
 */
public class TransformationUnitTreeEditPart<T extends Unit>
		extends AdapterTreeEditPart<T> implements IDirectEditPart {

	protected EContainerDescriptor parameters;

	/**
	 * Constructs a new {@link TransformationUnitTreeEditPart} with a given
	 * model.
	 * 
	 * @param model
	 *            the model
	 */
	public TransformationUnitTreeEditPart(T model) {
		super(model);

		Map<EClass, EStructuralFeature> parametersContainmentMap = new HashMap<EClass, EStructuralFeature>();

		parametersContainmentMap.put(HenshinPackage.Literals.PARAMETER,
				HenshinPackage.Literals.UNIT__PARAMETERS);

		parameters = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		parameters.setContainer(model);
		parameters.setContainmentMap(parametersContainmentMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new TransformationUnitClipboardEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new TransformationUnitComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TreeContainerEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		return getCastedModel().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new ArrayList<Object>(
				TransformationUnitUtil.getSubUnits(getCastedModel()));

		list.add(parameters);

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int type = notification.getEventType();

		switch (type) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
		case Notification.MOVE:
			refreshChildren();
			refreshVisuals();
			break;

		case Notification.SET:
			refreshVisuals();
			break;
		}
		if (this.isActive())
			refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.UNIT__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(
				HenshinUtil.INSTANCE.getTransformationSystem(getCastedModel()),
				HenshinPackage.MODULE__UNITS,
				getCastedModel(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new TransformationUnitPropertySource(getCastedModel());
	}
}
