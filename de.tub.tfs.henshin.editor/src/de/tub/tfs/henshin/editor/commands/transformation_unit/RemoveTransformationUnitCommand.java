/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.SendNotify;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class RemoveTransformationUnitCommand.
 * 
 * @author Johann
 */
public class RemoveTransformationUnitCommand extends Command {

	/** The parent. */
	final private TransformationUnit parent;

	/** The delete unit. */
	final private TransformationUnit deleteUnit;

	/** The feature. */
	private final EStructuralFeature feature;

	/** The index. */
	private int index;

	/** The parameter mappings. */
	private List<ParameterMapping> parameterMappings;

	/**
	 * Instantiates a new removes the transformation unit command.
	 * 
	 * @param parent
	 *            the parent
	 * @param deletedUnit
	 *            the deleted unit
	 */
	public RemoveTransformationUnitCommand(TransformationUnit parent,
			TransformationUnit deletedUnit) {
		super();
		this.deleteUnit = deletedUnit;
		this.feature = TransformationUnitUtil.getSubUnitsFeature(parent);
		this.parameterMappings = new Vector<ParameterMapping>();
		if (parent instanceof ConditionalUnitPart) {
			this.parent = ((ConditionalUnitPart) parent).getModel();
		} else {
			this.parent = parent;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return feature != null && deleteUnit != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Iterator<ParameterMapping> iter = parent.getParameterMappings()
				.iterator();
		while (iter.hasNext()) {
			ParameterMapping parameterMapping = iter.next();
			if (parameterMapping.getSource().getUnit() == deleteUnit
					|| parameterMapping.getTarget().getUnit() == deleteUnit) {
				parameterMappings.add(parameterMapping);
				iter.remove();
			}
		}

		if (feature.isMany()) {
			EList<TransformationUnit> list = (EList<TransformationUnit>) parent
					.eGet(feature);
			index = list.indexOf(deleteUnit);
			list.remove(deleteUnit);
		} else {
			parent.eSet(feature, null);
		}
		SendNotify.sendSetTransformationUnitNotify(deleteUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		if (feature.isMany()) {
			EList<TransformationUnit> list = (EList<TransformationUnit>) parent
					.eGet(feature);
			list.add(index, deleteUnit);
		} else {
			parent.eSet(feature, deleteUnit);
		}
		parent.getParameterMappings().addAll(parameterMappings);
		SendNotify.sendSetTransformationUnitNotify(deleteUnit);

	}

}
