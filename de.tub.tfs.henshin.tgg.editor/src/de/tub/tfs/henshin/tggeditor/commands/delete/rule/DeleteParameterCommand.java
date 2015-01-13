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
package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteParameterCommand extends CompoundCommand {
	/**
	 * Instantiates a new delete parameter command.
	 *
	 * @param parameter the parameter
	 */
	public DeleteParameterCommand(Parameter parameter) {
		super();
		/** TODO Franky*/
		/*for (Unit tUnit:ModelUtil.getTransSystem(parameter).getTransformationUnits()){
			for (ParameterMapping parameterMapping:tUnit.getParameterMappings()){
				if (parameterMapping.getSource()==parameter || parameterMapping.getTarget()==parameter){
					add(new DeleteParameterMappingCommand(parameterMapping));
				}
			}
		}*/
		add(new SimpleDeleteEObjectCommand(parameter));
	}
}
