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
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterMappingCommand;
import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class AddContentInNewUnitAndReplaceCommand.
 * 
 * @author Johann
 */
public class AddContentInNewUnitAndReplaceCommand extends CompoundCommand {

	/**
	 * Instantiates a new adds the content in new unit and replace command.
	 * 
	 * @param parentObject
	 *            the parent object
	 * @param transformationUnit
	 *            the transformation unit
	 * @param contents
	 *            the contents
	 */
	public AddContentInNewUnitAndReplaceCommand(Object parentObject,
			Unit transformationUnit,
			List<Unit> contents) {
		super();
		if (transformationUnit != null) {
			if (transformationUnit instanceof ConditionalUnit) {
				List<ConditionalUnitPart> cPartUnits = TransformationUnitUtil
						.createConditionalUnitParts((ConditionalUnit) transformationUnit);
				for (int i = 0, n = contents.size(); i < n; i++) {
					add(new AddTransformationUnitCommand(cPartUnits.get(i),
							contents.get(i)));
				}

			} else {
				for (Unit tUnit : contents) {
					add(new AddTransformationUnitCommand(transformationUnit,
							tUnit));
				}
			}
			if (parentObject instanceof Unit) {
				Unit parentUnit = (Unit) parentObject;
				int index = TransformationUnitUtil.getSubUnits(parentUnit)
						.indexOf(contents.get(0));
				Map<Parameter, Parameter> old2newParameter = new HashMap<Parameter, Parameter>();
				for (ParameterMapping mapping : parentUnit
						.getParameterMappings()) {
					if (contents.contains(mapping.getTarget().getUnit())) {
						Parameter parameter = null;
						if (old2newParameter.containsKey(mapping.getSource())) {
							parameter = old2newParameter.get(mapping
									.getSource());
						} else {
							parameter = HenshinFactory.eINSTANCE
									.createParameter();
							parameter.setName(mapping.getSource().getName());
							old2newParameter
									.put(mapping.getSource(), parameter);
							add(new CreateParameterCommand(transformationUnit,
									parameter));
						}
						add(new CreateParameterMappingCommand(parentUnit,
								mapping.getSource(), parameter));
						add(new CreateParameterMappingCommand(
								transformationUnit, parameter,
								mapping.getTarget()));
					}
					if (contents.contains(mapping.getSource().getUnit())) {
						Parameter parameter = null;
						if (old2newParameter.containsKey(mapping.getTarget())) {
							parameter = old2newParameter.get(mapping
									.getTarget());
						} else {
							parameter = HenshinFactory.eINSTANCE
									.createParameter();
							parameter.setName(mapping.getTarget().getName());
							old2newParameter
									.put(mapping.getTarget(), parameter);
							add(new CreateParameterCommand(transformationUnit,
									parameter));
						}
						add(new CreateParameterMappingCommand(
								transformationUnit, mapping.getSource(),
								parameter));
						add(new CreateParameterMappingCommand(parentUnit,
								parameter, mapping.getTarget()));
					}
				}
				for (Unit tUnit : contents) {
					add(new RemoveTransformationUnitCommand(parentUnit, tUnit));
				}
				add(new AddTransformationUnitCommand(parentUnit,
						transformationUnit, index));
			}
		}

	}

}
