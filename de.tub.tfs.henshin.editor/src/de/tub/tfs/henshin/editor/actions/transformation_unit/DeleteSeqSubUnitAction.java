/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.transformation_unit.RemoveTransformationUnitCommand;

/**
 * @author nam
 * 
 */
public class DeleteSeqSubUnitAction extends DeleteAction {

	/**
	 * An unique id for this {@link Action action}.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.transformation_unit.DeleteSeqSubUnitAction";

	private SequentialUnit parent;

	private List<Integer> models;

	/**
	 * @param part
	 */
	public DeleteSeqSubUnitAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Delete Sub Unit(s)");
		setToolTipText("Delete Selected Transformation Unit(s).");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

		models = new ArrayList<Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.DeleteAction#run()
	 */
	@Override
	public void run() {
		CompoundCommand cmd = new CompoundCommand("Delete Sub Units");

		for (Integer idx : models) {
			int i = idx.intValue();

			TransformationUnit unit = parent.getSubUnits().get(i);
			TransformationUnit u;

			do {
				u = parent.getSubUnits().get(i);

				cmd.add(new RemoveTransformationUnitCommand(parent, u));

				i++;
			} while (u == unit && i < parent.getSubUnits().size());
		}

		execute(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.DeleteAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		if (selection.size() > 0) {
			if (selection.get(0) instanceof EditPart) {
				EditPart part = (EditPart) selection.get(0);

				if (part.getParent() != null) {
					if (part.getParent().getModel() instanceof SequentialUnit) {
						parent = (SequentialUnit) part.getParent().getModel();
					}
				}
			}

		}

		models.clear();

		for (Object o : selection) {
			if (o instanceof EditPart) {
				EditPart part = (EditPart) o;

				int idx = -1;

				if (part.getParent() != null) {
					idx = part.getParent().getChildren().indexOf(part);
				}

				if (idx >= 0) {
					models.add(Integer.valueOf(idx));
				}
			}
		}

		return parent != null && !models.isEmpty();
	}
}
