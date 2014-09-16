/**
 * ActivityRuleFigure.java
 *
 * Created 27.12.2011 - 15:41:00
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

import de.tub.tfs.henshin.editor.figure.BoxModel;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.muvitor.gef.editparts.policies.IGhostFigureProvider;

/**
 * @author nam
 * 
 */
public class ActivityRuleFigure extends FlowElementFigure {
	/**
     * 
     */
	private static final int DEFAULT_MAX_NAME_LENGTH = 10;

	/**
	 * The name label
	 */
	private Label nameLabel;

	/**
     * 
     */
	private String name;

	/**
	 * An icon showing the content type of this activity
	 */
	private Label contentIconLabel;

	/**
     * 
     */
	public ActivityRuleFigure() {
		super();

		setOutline(false);

		getNameLabel().setForegroundColor(ColorConstants.black);

		add(getNameLabel());
		add(getContentIconLabel());
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;

		setToolTip(name);

		invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#invalidate()
	 */
	@Override
	public void invalidate() {
		if (isCompactMode()) {
			getNameLabel().setText(getName().charAt(0) + "...");
		} else {
			if (getName().length() > DEFAULT_MAX_NAME_LENGTH) {
				getNameLabel().setText(
						getName().substring(0, DEFAULT_MAX_NAME_LENGTH - 3)
								+ "...");
			} else {
				getNameLabel().setText(getName());
			}
		}

		super.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.FlowElementFigure#setUpBoxModel
	 * (de.tub.tfs.henshin.editor.figure.BoxModel)
	 */
	@Override
	protected void setUpBoxModel(BoxModel boxModel) {
		boxModel.sethPadding(1);
		boxModel.setvPadding(1);
		boxModel.sethSpacing(2);
	}

	/**
	 * @return the nameLabel
	 */
	private Label getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new Label();
		}

		return nameLabel;
	}

	/**
	 * @return the contentIconLabel
	 */
	private Label getContentIconLabel() {
		if (contentIconLabel == null) {
			contentIconLabel = new Label(
					ResourceUtil.ICONS.RULE.img(25));
		}

		return contentIconLabel;
	}

	/**
	 * @return
	 */
	private String getName() {
		if (name == null) {
			name = "<name>";
		}

		return name;
	}

	@Override
	public IFigure getGhostFigure() {
		return ((IGhostFigureProvider) getParent()).getGhostFigure();
	}
}
