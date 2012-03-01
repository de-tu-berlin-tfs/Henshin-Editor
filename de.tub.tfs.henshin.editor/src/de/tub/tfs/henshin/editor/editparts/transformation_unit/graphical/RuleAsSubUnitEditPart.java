
/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.model.properties.rule.RulePropertySource;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * The Class RuleAsSubUnitEditPart.
 */
public class RuleAsSubUnitEditPart extends SubUnitEditPart<Rule> {

	/**
	 * Instantiates a new rule as sub unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param transformationUnit
	 *            the transformation unit
	 * @param model
	 *            the model
	 */
	public RuleAsSubUnitEditPart(TransUnitPage transUnitPage,
			TransformationUnit transformationUnit, Rule model) {
		super(transUnitPage, transformationUnit, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.getColor(2));
		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("Rule25.png");
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		if (getParent() instanceof RuleUnitEditPart) {
			MuvitorTreeEditor.showView(getCastedModel());
		} else {
			super.performOpen();
		}
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
				HenshinUtil.INSTANCE.getTransformationSystem(getCastedModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__RULES, getCastedModel(),
				true);
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
