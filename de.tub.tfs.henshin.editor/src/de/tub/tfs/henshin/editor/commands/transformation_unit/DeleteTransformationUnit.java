/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteTransformationUnit.
 * 
 * @author Johann
 */
public class DeleteTransformationUnit extends CompoundCommand {

	/**
	 * Instantiates a new delete transformation unit.
	 * 
	 * @param toDelete
	 *            the Transformation unit to delete
	 */
	public DeleteTransformationUnit(final Unit toDelete) {
		super();

		final Module transformationSystem = HenshinUtil.INSTANCE
				.getTransformationSystem(toDelete);

		if (transformationSystem != null) {
			for (Unit tUnit : transformationSystem
					.getUnits()) {
				final EStructuralFeature feature = TransformationUnitUtil
						.getSubUnitsFeature(tUnit);
				if (feature != null) {
					deleteUnit(toDelete, tUnit, feature);
				}

				if (tUnit instanceof ConditionalUnit) {
					deleteConditionalUnit(toDelete, (ConditionalUnit) tUnit);
				}
			}

			add(new SimpleDeleteEObjectCommand(toDelete));
		}
	}

	/**
	 * @param unitToDelete
	 *            Transformation unit to delete.
	 * @param currentUnit
	 *            The current conditional unit in the tree which is checked.
	 */
	private void deleteConditionalUnit(final Unit unitToDelete,
			final ConditionalUnit currentUnit) {
		if (currentUnit.getIf() == unitToDelete) {
			add(new RemoveTransformationUnitCommand(new ConditionalUnitPart(
					currentUnit, "", currentUnit.eClass()
							.getEStructuralFeature(
									HenshinPackage.CONDITIONAL_UNIT__IF)),
					unitToDelete));
		}
		if (currentUnit.getThen() == unitToDelete) {
			add(new RemoveTransformationUnitCommand(new ConditionalUnitPart(
					currentUnit, "", currentUnit.eClass()
							.getEStructuralFeature(
									HenshinPackage.CONDITIONAL_UNIT__THEN)),
					unitToDelete));
		}
		if (currentUnit.getElse() == unitToDelete) {
			add(new RemoveTransformationUnitCommand(new ConditionalUnitPart(
					currentUnit, "", currentUnit.eClass()
							.getEStructuralFeature(
									HenshinPackage.CONDITIONAL_UNIT__ELSE)),
					unitToDelete));
		}
	}

	/**
	 * @param unitToDelete
	 *            Transformation unit to delete.
	 * @param currentUnit
	 *            The current transformation unit in the tree which is checked.
	 */
	@SuppressWarnings("unchecked")
	private void deleteUnit(final Unit unitToDelete,
			final Unit currentUnit,
			final EStructuralFeature feature) {
		if (feature.isMany()) {
			final EList<Unit> list = (EList<Unit>) currentUnit
					.eGet(feature);
			if (list.contains(unitToDelete)) {
				add(new RemoveTransformationUnitCommand(currentUnit,
						unitToDelete));
			}
		} else {
			if (currentUnit.eGet(feature) == unitToDelete) {
				add(new RemoveTransformationUnitCommand(currentUnit,
						unitToDelete));
			}
		}
	}
}
