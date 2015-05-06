package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		// lexicographically sort constraints by name & SOURCE and TARGET component
		List<Constraint> sortedConstraints = new ArrayList<Constraint>(this.getCastedModel().getConstraints());
		Collections.sort(sortedConstraints, new Comparator<Constraint>() {
			public int compare(Constraint o1, Constraint o2) {
				if (o1.getComponent().equals(o2.getComponent())) {
					return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
				} else {
					return o1.getComponent().compareTo(o2.getComponent());
				}
			}
		});
		return sortedConstraints;
	}
	
}
