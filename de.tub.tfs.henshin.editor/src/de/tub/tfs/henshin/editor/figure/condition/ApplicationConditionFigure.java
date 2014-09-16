/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.condition;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author Angeline Warning
 * 
 */
public class ApplicationConditionFigure extends FormulaFigure<NestedCondition> {

	/**
	 * @param ac
	 *            The application condition whose figure is built.
	 */
	public ApplicationConditionFigure(final NestedCondition ac) {
		super(ac);

		if (ac != null) {
			final Image image = getLabelImage();
			label.setIcon(image);
			label.setBounds(new Rectangle(getLocation().x, getLocation().y,
					width, height));
		}
	}

	/**
	 * Returns a not-exist-image if the given application condition is negated,
	 * otherwise returns an exist-image.
	 * 
	 * @param nc
	 *            The application condition.
	 * @return An image of the given application condition.
	 */
	private Image getLabelImage() {
		return ResourceUtil.ICONS.EXIST.img(16);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.condition.FormulaFigure#refreshLabel()
	 */
	@Override
	protected void refreshLabel() {
		final String name = getFormula().getConclusion().getName();
		label.setText(name);
		label.setIcon(getLabelImage());
		label.setBounds(new Rectangle(getLocation().x, getLocation().y, width,
				height));

		setToolTip(new Label(name));
	}

}
