/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.condition;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;

/**
 * This command changed the negated value of the given application condition.
 * 
 * @author angel
 */
public class SetNegatedCommand extends CompoundCommand {

	/**
	 * @param ac
	 */
	public SetNegatedCommand(final NestedCondition ac) {
		final EObject container = ac.eContainer();

		if (container instanceof Not) {
			add(new SimpleSetEFeatureCommand<EObject, EObject>(container, ac,
					ac.eContainingFeature()));
			add(new SimpleSetEFeatureCommand<EObject, EObject>(
					container.eContainer(), ac, container.eContainingFeature()));
		} else {

			Not not = HenshinFactory.eINSTANCE.createNot();

			final EStructuralFeature acFeature = ac.eContainingFeature();

			add(new SimpleSetEFeatureCommand<Not, Formula>(not, ac,
					HenshinPackage.NOT__CHILD));

			if (acFeature != null) {
				add(new SimpleSetEFeatureCommand<EObject, Formula>(
						ac.eContainer(), not, acFeature));
			}
		}
	}
}
