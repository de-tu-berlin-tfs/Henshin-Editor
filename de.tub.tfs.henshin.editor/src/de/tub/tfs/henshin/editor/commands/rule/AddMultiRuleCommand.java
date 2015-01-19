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
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;

public class AddMultiRuleCommand extends CompoundCommand {
	private HashMap<EObject, EObject> mappings;
	private Rule multiRule;
	private Rule kernel;

	public AddMultiRuleCommand(Rule kernel,Rule multiRule,HashMap<EObject, EObject> mappings){
		this.kernel = kernel;
		this.multiRule = multiRule;
		this.mappings = mappings;
		
		this.add(new SimpleAddEObjectCommand<EObject, EObject>(multiRule, HenshinPackage.Literals.RULE__MULTI_RULES, kernel));
		LinkedList<Mapping> henshinMappings = new LinkedList<Mapping>();
		for (Entry<EObject, EObject> entry : mappings.entrySet()) {
			if (entry.getKey() instanceof Node){
				henshinMappings.add( HenshinFactory.eINSTANCE.createMapping((Node)entry.getKey(), (Node)entry.getValue()));
			}
		}
		this.add(new SimpleAddEObjectCommand<EObject, EObject>(multiRule, HenshinPackage.Literals.RULE__MULTI_RULES, kernel));
		for (Mapping mapping : henshinMappings) {
			this.add(new SimpleAddEObjectCommand<EObject, EObject>(mapping, HenshinPackage.RULE__MULTI_MAPPINGS, multiRule));
		}
	}
	
	
}
