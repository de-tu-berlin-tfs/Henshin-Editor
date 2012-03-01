/**
 * CreateMappingColorCommand.java
 *
 * Created 30.12.2011 - 12:05:49
 */
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * @author nam
 * 
 */
public class CreateMappingColorCommand extends Command {

	private NodeLayout orgLayout;

	private NodeLayout imgLayout;

	private Rule container;

	private int oldOrgColor;

	private int oldImgColor;

	/**
	 * @param orgLayout
	 * @param imgLayout
	 * @param container
	 *            the container of the {@link Mapping} and must be either a
	 *            {@link Rule} or a {@link NestedCondition}.
	 */
	public CreateMappingColorCommand(NodeLayout orgLayout,
			NodeLayout imgLayout, EObject container) {
		super();

		this.orgLayout = orgLayout;
		this.imgLayout = imgLayout;

		if (container instanceof NestedCondition) {
			NestedCondition cond = (NestedCondition) container;

			this.container = cond.getConclusion().getContainerRule();
		} else if (container instanceof Rule) {
			this.container = (Rule) container;
		}

		oldImgColor = imgLayout.getColor();
		oldOrgColor = orgLayout.getColor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return orgLayout != null && imgLayout != null && container != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		int mappingColor = orgLayout.getColor();

		if (mappingColor <= 0) {
			mappingColor = imgLayout.getColor();

			if (mappingColor <= 0) {
				Set<Integer> usedColors = new HashSet<Integer>();

				for (Mapping m : container.getMappings()) {
					usedColors.add(Integer.valueOf(HenshinLayoutUtil.INSTANCE
							.getLayout(m.getOrigin()).getColor()));
				}

				mappingColor = ColorUtil.color2Int(ColorUtil
						.getDistinctColor(usedColors));
			}
		}

		orgLayout.setColor(mappingColor);
		imgLayout.setColor(mappingColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		orgLayout.setColor(oldOrgColor);
		imgLayout.setColor(oldImgColor);
	}
}
