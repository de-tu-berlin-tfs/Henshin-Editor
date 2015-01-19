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
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.UnaryFormula;

/**
 * The Class FormulaTree.
 */
public class FormulaTree {

	/**
	 * Gets the application conditions.
	 * 
	 * @param formula
	 *            the formula
	 * @return the application conditions
	 */
	public static List<NestedCondition> getApplConditions(Formula formula) {
		List<NestedCondition> conditions = new ArrayList<NestedCondition>();
		if (formula != null) {
			addApplConditions(conditions, formula);
		}
		return conditions;
	}

	/**
	 * Adds the application conditions.
	 * 
	 * @param conditions
	 *            the conditions
	 * @param formula
	 *            the formula
	 */
	private static void addApplConditions(List<NestedCondition> conditions,
			Formula formula) {
		if (conditions == null) {
			conditions = new ArrayList<NestedCondition>();
		}

		if (formula instanceof NestedCondition) {
			conditions.add((NestedCondition) formula);
		} else if (formula instanceof UnaryFormula) {
			addApplConditions(conditions, ((UnaryFormula) formula).getChild());
		} else if (formula instanceof BinaryFormula) {
			addApplConditions(conditions, ((BinaryFormula) formula).getLeft());
			addApplConditions(conditions, ((BinaryFormula) formula).getRight());
		}
	}

	/**
	 * Gets the formula graph.
	 * 
	 * @param formula
	 *            the formula
	 * @return the formula graph
	 */
	public static List<Graph> getFormulaGraph(Formula formula) {
		List<Graph> conditions = new ArrayList<Graph>();
		if (formula != null) {
			calculateFormulaGraph(formula, conditions);
		}
		return conditions;
	}

	/**
	 * Calculate formula graph.
	 * 
	 * @param formula
	 *            the formula
	 * @param graphs
	 *            the graphs
	 */
	private static void calculateFormulaGraph(Formula formula,
			List<Graph> graphs) {
		if (formula instanceof BinaryFormula) {
			BinaryFormula binaryFormula = (BinaryFormula) formula;
			Formula left = binaryFormula.getLeft();
			calculateFormulaGraph(left, graphs);
			Formula right = binaryFormula.getRight();
			calculateFormulaGraph(right, graphs);
		} else if (formula instanceof UnaryFormula) {
			UnaryFormula unaryFormula = (UnaryFormula) formula;
			calculateFormulaGraph(unaryFormula.getChild(), graphs);
		} else if (formula instanceof NestedCondition) {
			NestedCondition condition = (NestedCondition) formula;
			graphs.add(condition.getConclusion());
		}
	}

}
