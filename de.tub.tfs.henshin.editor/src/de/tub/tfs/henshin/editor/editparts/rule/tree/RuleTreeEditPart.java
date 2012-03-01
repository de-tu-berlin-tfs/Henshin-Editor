package de.tub.tfs.henshin.editor.editparts.rule.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.TreeContainerEditPolicy;
import de.tub.tfs.henshin.editor.editparts.rule.RuleClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.rule.RuleComponentEditPolicy;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.TransformationUnitTreeEditPart;
import de.tub.tfs.henshin.editor.model.properties.rule.RulePropertySource;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;

/**
 * The Class RuleTreeEditPart.
 */
public class RuleTreeEditPart extends TransformationUnitTreeEditPart<Rule>
		implements IDirectEditPart {

	private EContainerDescriptor attributeConditions;
	private EContainerDescriptor multiRules;

	/**
	 * Instantiates a new rule tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public RuleTreeEditPart(Rule model) {
		super(model);

		Map<EClass, EStructuralFeature> attributeConditionsMap = new HashMap<EClass, EStructuralFeature>();

		attributeConditionsMap.put(HenshinPackage.Literals.ATTRIBUTE_CONDITION,
				HenshinPackage.Literals.RULE__ATTRIBUTE_CONDITIONS);

		attributeConditions = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		attributeConditions.setContainer(model);
		attributeConditions.setContainmentMap(attributeConditionsMap);

	

		Map<EClass, EStructuralFeature> multiRulesMap = new HashMap<EClass, EStructuralFeature>();

		multiRulesMap.put(HenshinPackage.Literals.RULE,
				HenshinPackage.Literals.RULE__MULTI_RULES);

		multiRules = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		multiRules.setContainer(model);
		multiRules.setContainmentMap(multiRulesMap);
		
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.RULE__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(
				getCastedModel().getTransformationSystem(),
				HenshinPackage.TRANSFORMATION_SYSTEM__RULES, getCastedModel(),
				true);

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
		super.notifyChanged(notification);
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.NODE:
		case HenshinPackage.EDGE:
		case HenshinPackage.ATTRIBUTE:
		case HenshinPackage.MAPPING:
		case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
		case HenshinPackage.FORMULA:
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
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.RULE.img(16);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new RuleClipboardEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RuleComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TreeContainerEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new ArrayList<Object>();

		list.add(getCastedModel().getLhs());
		list.add(getCastedModel().getRhs());
		list.add(attributeConditions);
		
		
		list.addAll(super.getModelChildren());
		if (!this.getCastedModel().getMultiRules().isEmpty())
			list.add(multiRules);

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new RulePropertySource(getCastedModel());
	}
}
