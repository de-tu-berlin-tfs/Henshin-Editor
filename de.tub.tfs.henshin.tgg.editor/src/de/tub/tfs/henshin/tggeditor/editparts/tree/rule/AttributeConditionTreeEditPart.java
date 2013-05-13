package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import sun.org.mozilla.javascript.internal.JavaScriptException;

import de.tub.tfs.henshin.tggeditor.editparts.tree.TGGTreeContainerEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.AttributeConditionComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.ParameterComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.validator.NameEditorValidator;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class AttributeConditionTreeEditPart extends AdapterTreeEditPart<AttributeCondition> implements IDirectEditPart {
	/**
	 * Instantiates a new parameter tree edit part.
	 *
	 * @param model the model
	 */
	public AttributeConditionTreeEditPart(AttributeCondition model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		return "Condition: " + getCastedModel().getConditionText();
	}

	/* (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.ATTRIBUTE_CONDITION__CONDITION_TEXT;
	}

	/* (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}


	
	/* (non-Javadoc)
	 * @see henshineditor.editparts.tree.rule.RuleTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new AttributeConditionComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TGGTreeContainerEditPolicy());	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		//try {
		//	return IconUtil.getIcon("parameter14.png");
		//} catch (Exception e) {
			return null;
		//}
	}

	
	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 
	@Override
	protected IPropertySource createPropertySource() {
		return new ParameterPropertySource(getCastedModel());
	}*/
}