package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import java.util.List;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class ConstraintFolderTreeEditPart extends
		AdapterTreeEditPart<ConstraintFolder> {

	public ConstraintFolderTreeEditPart(ConstraintFolder model) {
		super(model);
	}

	@Override
	protected String getText() {
		return "Graph Constraints";
	}
	
	@Override
	protected Image getImage() {
		return null;
	}
	
	@Override
	protected List<Constraint> getModelChildren() {
		return this.getCastedModel().getConstraints();
	}
	
}
