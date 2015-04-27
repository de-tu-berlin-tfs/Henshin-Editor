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
package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TGGRule;

/**
 * The ConRuleNamesComparator class provides a comparison method that, based on
 * the predefined order of the priorityOrder attribute, checks whether a given
 * rule is prior to another given rule according to their rule names.
 * 
 * @author Gérard Kirpach
 */

public class ConcurrentRuleComparator {
	
	public List<String> priorityOrder;
	/**
	 * 
	 * @param order this list contains the reference rules in priority order.
	 */
	
	public ConcurrentRuleComparator(final List<TGGRule> order) {
		if (order == null) {
			throw new IllegalArgumentException();
		}
		this.priorityOrder = new LinkedList<String>();
		for (Rule cRule : order) {
			this.priorityOrder.add(cRule.getName());
		}
	}
	
	/**
	 * 
	 * @param aName1
	 * @param aName2
	 * @return
	 */
	
	private Boolean prior(String aName1, String aName2) {
		if (aName1.equals(aName2)) {
			return null;
		};
		for (String name : priorityOrder) {
			if (name.equals(aName1)) {
				return true;
			}
			if (name.equals(aName2)) {
				return false;
			}
		}
		return null;
	}
	
	
	/**
	 * This method checks whether rule1 is strictly greater than rule2 based on their names.
	 * If both rules have equal (or uncomparable) subrule names with same order, they are considered to be equal
	 * and the method return null.
	 * @param rule1 first rule that is prior if result is true
	 * @param rule2 second rule that is prior if result is false
	 * @return null if rule1 and rule2 are equal or incomparable
	 */
	public Boolean prior(Rule rule1, Rule rule2) {
		List<String> aNames1 = ConcurrentRuleUtil.getAtomicRuleNames(rule1.getName());
		List<String> aNames2 = ConcurrentRuleUtil.getAtomicRuleNames(rule2.getName());
		Boolean prior = null;
		for (int i = 0; (i < aNames1.size()) && (i < aNames2.size()); i++) {
			prior =  prior(aNames1.get(i), aNames2.get(i));
			if (prior != null) {
				return prior;
			}
		}
		return (aNames1.size() == aNames2.size() ? null : aNames1.size() > aNames2.size() ); 
	}
	
	
	/**
	 * This class represents the tree structure of a concurrent rule name and 
	 * can be used to compare rules based on their names when consideration of
	 * braces is needed.
	 * @author Jerry
	 *
	 */
	public class AtomicRuleNamesTree {
		
		private AtomicRuleNamesTree subRuleLTree;
		private AtomicRuleNamesTree subRuleRTree;
		private String ruleName;
		
		public AtomicRuleNamesTree(String ruleName) {
			this.ruleName = ruleName;
			if (!(ruleName.startsWith("(") 
					&& ruleName.endsWith(")")
					&& ruleName.contains("|"))) {
				return;
			}
			String ruleNameL;
			String ruleNameR;
			String purgedName = ruleName.substring(1, ruleName.length() - 1);
			int openBraces = 0;
			int tmpIdx = 0;
			int idxL = 0;
			int idxR = 0;
			char tmp;
			boolean found = false;
			while(!found && (tmpIdx < purgedName.length())) {
				tmp = purgedName.charAt(tmpIdx);
				switch (tmp) {
				case '(': 
					openBraces++;
					break;
				case ')':
					openBraces--;
					break;
				case '|': 
					if (openBraces == 0) {
						idxL = tmpIdx;
						do {
							tmpIdx++;
							tmp = purgedName.charAt(tmpIdx);
						} while(('0' <= tmp) && (tmp <= '9'));
						if (tmp != '|') {
							throw new IllegalArgumentException();
						}
						idxR = tmpIdx+1;
						found = true;
					};
					break;
				default : break;
				}
				tmpIdx++;
			}
			ruleNameL = purgedName.substring(0, idxL);
			ruleNameR = purgedName.substring(idxR);
			System.out.println(ruleNameL);
			System.out.println(ruleNameR);
			subRuleLTree = new AtomicRuleNamesTree(ruleNameL);
			subRuleRTree = new AtomicRuleNamesTree(ruleNameR);
		}
		
		public boolean isConcurrent() {
			return ((subRuleLTree != null) && (subRuleRTree != null));
		}
	}

}
