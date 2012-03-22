/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class SequentialUnitTreeEditPart.
 */
public class SequentialUnitTreeEditPart extends
		TransformationUnitTreeEditPart<SequentialUnit> {

	private ArrayList<Integer> counters;

	/**
	 * Instantiates a new sequential unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public SequentialUnitTreeEditPart(SequentialUnit model) {
		super(model);
		
		counters = new ArrayList<Integer>();
	}

	/**
	 * @return the counters
	 */
	public List<Integer> getCounters() {
		return Collections.unmodifiableList(counters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		@SuppressWarnings("unchecked")
		List<Object> children = new LinkedList<Object>(getChildren());

		for (Object o : children) {
			removeChild((EditPart) o);
		}

		List<?> modelChildren = getModelChildren();

		for (Object o : modelChildren) {
			addChild(createChild(o), -1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.
	 * TransformationUnitTreeEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new LinkedList<Object>();
		SequentialUnit model = getCastedModel();

		TransformationUnit subUnit = null;
		int idx = 0;

		counters.clear();

		for (TransformationUnit u : model.getSubUnits()) {
			if (subUnit != u) {
				subUnit = u;
				counters.add(Integer.valueOf(idx));
				children.add(u);
			}

			idx++;
		}

		children.addAll(model.getParameters());

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("seqUnit18.png");
		} catch (Exception e) {
			return null;
		}
	}
}
