package tggeditor.editparts.tree.rule;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import tggeditor.editpolicies.rule.ParameterComponentEditPolicy;
import tggeditor.util.IconUtil;
import tggeditor.util.validator.NameEditorValidator;

import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class ParameterTreeEditPart extends AdapterTreeEditPart<Parameter> implements IDirectEditPart {
	/**
	 * Instantiates a new parameter tree edit part.
	 *
	 * @param model the model
	 */
	public ParameterTreeEditPart(Parameter model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		return getCastedModel().getName();
	}

	/* (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.PARAMETER__NAME;
	}

	/* (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditorValidator(getCastedModel().getUnit(), HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS, getCastedModel(), true);
	}


	
	/* (non-Javadoc)
	 * @see henshineditor.editparts.tree.rule.RuleTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ParameterComponentEditPolicy());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("parameter14.png");
		} catch (Exception e) {
			return null;
		}
	}

	
	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 
	@Override
	protected IPropertySource createPropertySource() {
		return new ParameterPropertySource(getCastedModel());
	}*/
}
