/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.internal.TransformationUnitPart;

/**
 * The Class TransformationUnitUtil.
 */
public class TransformationUnitUtil {

	/**
	 * Gets the sub units.
	 * 
	 * @param tUnit
	 *            the t unit
	 * @return the sub units
	 */
	@SuppressWarnings("unchecked")
	public static List<Unit> getSubUnits(Unit tUnit) {

		EStructuralFeature feature = getSubUnitsFeature(tUnit);
		List<Unit> list = new Vector<Unit>();
		if (feature != null) {
			if (feature.isMany()) {
				return (List<Unit>) tUnit.eGet(feature);
			} else {
				Object object = null;
				if (tUnit instanceof TransformationUnitPart<?>) {
					object = ((TransformationUnitPart<?>) tUnit).getModel()
							.eGet(feature);
				} else {
					object = tUnit.eGet(feature);
				}
				if (object != null) {
					list.add((Unit) object);
				}
			}
		}
		if (tUnit instanceof ConditionalUnit) {
			ConditionalUnit cUnit = (ConditionalUnit) tUnit;
			if (cUnit.getIf() != null) {
				list.add(cUnit.getIf());
			}
			if (cUnit.getThen() != null) {
				list.add(cUnit.getThen());
			}
			if (cUnit.getElse() != null) {
				list.add(cUnit.getElse());
			}
		}

		return list;

	}

	/**
	 * Gets the sub units feature.
	 * 
	 * @param tUnit
	 *            the t unit
	 * @return the sub units feature
	 */
	public static EStructuralFeature getSubUnitsFeature(Unit tUnit) {
		if (tUnit instanceof SequentialUnit) {
			return tUnit.eClass().getEStructuralFeature(
					HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS);
		}
		if (tUnit instanceof IndependentUnit) {
			return tUnit.eClass().getEStructuralFeature(
					HenshinPackage.INDEPENDENT_UNIT__SUB_UNITS);
		}
		if (tUnit instanceof PriorityUnit) {
			return tUnit.eClass().getEStructuralFeature(
					HenshinPackage.PRIORITY_UNIT__SUB_UNITS);
		}
		if (tUnit instanceof ConditionalUnitPart) {
			return ((ConditionalUnitPart) tUnit).getFeature();
		}

		if (tUnit instanceof LoopUnit) {
			return HenshinPackage.Literals.UNARY_UNIT__SUB_UNIT;
		}

		return null;
	}

	/**
	 * Creates the conditional unit parts.
	 * 
	 * @param conditionalUnit
	 *            the conditional unit
	 * @return the list
	 */
	public static List<ConditionalUnitPart> createConditionalUnitParts(
			ConditionalUnit conditionalUnit) {
		List<ConditionalUnitPart> list = new ArrayList<ConditionalUnitPart>();
		list.add(new ConditionalUnitPart(conditionalUnit, "IF", conditionalUnit
				.eClass().getEStructuralFeature(
						HenshinPackage.CONDITIONAL_UNIT__IF)));
		list.add(new ConditionalUnitPart(conditionalUnit, "THEN",
				conditionalUnit.eClass().getEStructuralFeature(
						HenshinPackage.CONDITIONAL_UNIT__THEN)));
		list.add(new ConditionalUnitPart(conditionalUnit, "ELSE",
				conditionalUnit.eClass().getEStructuralFeature(
						HenshinPackage.CONDITIONAL_UNIT__ELSE)));
		return list;
	}
}
