/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.info;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UserConstraint;
import org.eclipse.emf.henshin.model.Rule;

public class RuleInfo {
	private Rule rule;
	
	private VariableInfo variableInfo;
	private RuleChangeInfo changeInfo;
	private ConditionInfo conditionInfo;

	public RuleInfo(Rule rule, EngineImpl engine,HashMap<Constructor<? extends UserConstraint>, Object[]>  userConstraints) {
		this.rule = rule;
		
		this.conditionInfo = new ConditionInfo(rule);
		this.variableInfo = new VariableInfo(this, engine,userConstraints);
		this.changeInfo = new RuleChangeInfo(rule);
	}

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @return the variableInfo
	 */
	public VariableInfo getVariableInfo() {
		return variableInfo;
	}

	/**
	 * @return the changeInfo
	 */
	public RuleChangeInfo getChangeInfo() {
		return changeInfo;
	}

	/**
	 * @return the conditionInfo
	 */
	public ConditionInfo getConditionInfo() {
		return conditionInfo;
	}
}