/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;


/**
 * A folder for rules in the tree editor.
 */
public class RuleFolder extends EObjectImpl {
	/**
	 * A list of rules.
	 */
	private List<Rule> rules;
	/**
	 * Transformationsystem.
	 */
	private Module sys;
	
	public RuleFolder(Module sys){
		this.sys = sys;
		//Rule sortieren.
		rules = new ArrayList<Rule>();
		List<TGGRule> allRules = (List) ModelUtil.getRules(this.sys);
		for(TGGRule r: allRules){
			if(r.getMarkerType()==null || r.getMarkerType().equals(RuleUtil.TGG_RULE))
			{
				rules.add(r);
			}			
		}
	}
	
	public List<Rule> getRules(){
		return this.rules;
	}
}
