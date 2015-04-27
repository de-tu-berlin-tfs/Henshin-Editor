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
package de.tub.tfs.henshin.editor.util;

/**
 * 
 * Die Klasse wird zum Refresh einzelner Komponenten benötigt.
 * Wird eine der beschriebenen Aktionen ausgeführt, werden durch diese
 * Klasse, die entsprechenden Objekte benachrichtigt.
 */

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * The Class SendNotify.
 */
public class SendNotify {

	/**
	 * Send add mapping notify.
	 * 
	 * @param mapping
	 *            the mapping
	 */
	public static void sendAddMappingNotify(Mapping mapping) {
		mapping.getImage().eNotify(
				new ENotificationImpl((InternalEObject) mapping.getImage(),
						Notification.ADD, HenshinPackage.MAPPING__IMAGE, null,
						mapping));
		mapping.getOrigin().eNotify(
				new ENotificationImpl((InternalEObject) mapping.getOrigin(),
						Notification.ADD, HenshinPackage.MAPPING__ORIGIN, null,
						mapping));
	}

	/**
	 * Send remove mapping notify.
	 * 
	 * @param mapping
	 *            the mapping
	 */
	public static void sendRemoveMappingNotify(Mapping mapping) {
		mapping.getImage().eNotify(
				new ENotificationImpl((InternalEObject) mapping.getImage(),
						Notification.REMOVE, HenshinPackage.MAPPING__IMAGE,
						mapping, null));
		mapping.getOrigin().eNotify(
				new ENotificationImpl((InternalEObject) mapping.getOrigin(),
						Notification.REMOVE, HenshinPackage.MAPPING__ORIGIN,
						mapping, null));
	}

	/**
	 * Send set image mapping notify.
	 * 
	 * @param mapping
	 *            the mapping
	 */
	public static void sendSetImageMappingNotify(Mapping mapping) {
		mapping.getImage().eNotify(
				new ENotificationImpl((InternalEObject) mapping.getImage(),
						Notification.SET, HenshinPackage.MAPPING__IMAGE,
						mapping, null));
	}

	/**
	 * Send update mapping notify.
	 * 
	 * @param node
	 *            the node
	 */
	public static void sendUpdateMappingNotify(Node node) {
		ENotificationImpl notification = new ENotificationImpl(
				(InternalEObject) node, Notification.SET, HenshinPackage.NODE,
				null, null);
		node.eNotify(notification);
	}

	/**
	 * Send add formula notify.
	 * 
	 * @param rule
	 *            the rule
	 * @param formula
	 *            the formula
	 */
	public static void sendAddFormulaNotify(Rule rule, EObject formula) {
		ENotificationImpl notification = new ENotificationImpl(
				(InternalEObject) rule, Notification.ADD,
				HenshinPackage.FORMULA, null, formula);
		rule.eNotify(notification);
	}

	/**
	 * Send add formula notify.
	 * 
	 * @param parent
	 *            the parent
	 * @param formula
	 *            the formula
	 */
	public static void sendAddFormulaNotify(EObject parent, Formula formula) {
		if (parent != null && formula != null) {
			ENotificationImpl notification = new ENotificationImpl(
					(InternalEObject) parent, Notification.ADD,
					HenshinPackage.FORMULA, null, formula);
			parent.eNotify(notification);
		}
	}

	/**
	 * Send add formula notify.
	 * 
	 * @param parent
	 *            the parent
	 * @param formula
	 *            the formula
	 */
	public static void sendSwapFormulaNotify(EObject notifier,
			Formula oldFormula, Formula newFormula) {
		notifier.eNotify(new ENotificationImpl((InternalEObject) notifier,
				HenshinNotification.BINARY_FORMULA_SWAP,
				HenshinPackage.FORMULA, oldFormula, newFormula));
	}

	/**
	 * Send remove formula notify.
	 * 
	 * @param parent
	 *            the parent
	 * @param formula
	 *            the formula
	 */
	public static void sendRemoveFormulaNotify(EObject parent, Formula formula) {
		ENotificationImpl notification = new ENotificationImpl(
				(InternalEObject) parent, Notification.REMOVE,
				HenshinPackage.FORMULA, null, formula);
		parent.eNotify(notification);
	}

	/**
	 * Send remove formula notify.
	 * 
	 * @param rule
	 *            the rule
	 * @param formula
	 *            the formula
	 */
	public static void sendRemoveFormulaNotify(Rule rule, EObject formula) {
		ENotificationImpl notification = new ENotificationImpl(
				(InternalEObject) rule, Notification.REMOVE,
				HenshinPackage.FORMULA, formula, null);
		rule.eNotify(notification);
	}

	/**
	 * Send add port mapping notify.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param tUnit
	 *            the t unit
	 */
	public static void sendAddPortMappingNotify(ParameterMapping mapping,
			Unit tUnit) {
		if (mapping.getSource().getUnit() == tUnit) {
			mapping.getSource().eNotify(
					new ENotificationImpl(
							(InternalEObject) mapping.getSource(),
							Notification.ADD, HenshinPackage.PARAMETER_MAPPING,
							null, mapping));
			mapping.getTarget()
					.getUnit()
					.eNotify(
							new ENotificationImpl((InternalEObject) mapping
									.getTarget().getUnit(), Notification.ADD,
									HenshinPackage.PARAMETER_MAPPING, null,
									mapping));
		} else {
			mapping.getSource()
					.getUnit()
					.eNotify(
							new ENotificationImpl((InternalEObject) mapping
									.getSource().getUnit(), Notification.ADD,
									HenshinPackage.PARAMETER_MAPPING, null,
									mapping));
			mapping.getTarget().eNotify(
					new ENotificationImpl(
							(InternalEObject) mapping.getTarget(),
							Notification.ADD, HenshinPackage.PARAMETER_MAPPING,
							null, mapping));

		}
	}

	/**
	 * Send remove port mapping notify.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param tUnit
	 *            the t unit
	 */
	public static void sendRemovePortMappingNotify(ParameterMapping mapping,
			Unit tUnit) {
		if (mapping.getSource().getUnit() == tUnit) {
			mapping.getSource().eNotify(
					new ENotificationImpl(
							(InternalEObject) mapping.getSource(),
							Notification.REMOVE,
							HenshinPackage.PARAMETER_MAPPING, mapping, null));
			mapping.getTarget()
					.getUnit()
					.eNotify(
							new ENotificationImpl((InternalEObject) mapping
									.getTarget().getUnit(),
									Notification.REMOVE,
									HenshinPackage.PARAMETER_MAPPING, mapping,
									null));
		} else {
			mapping.getSource()
					.getUnit()
					.eNotify(
							new ENotificationImpl((InternalEObject) mapping
									.getSource().getUnit(),
									Notification.REMOVE,
									HenshinPackage.PARAMETER_MAPPING, mapping,
									null));
			mapping.getTarget().eNotify(
					new ENotificationImpl(
							(InternalEObject) mapping.getTarget(),
							Notification.REMOVE,
							HenshinPackage.PARAMETER_MAPPING, mapping, null));

		}
	}

	/**
	 * Send set transformation unit notify.
	 * 
	 * @param tUnit
	 *            the t unit
	 */
	public static void sendSetTransformationUnitNotify(Unit tUnit) {
		Module tSys = HenshinUtil.INSTANCE
				.getTransformationSystem(tUnit);
		if (tSys != null) {
			tSys.eNotify(new ENotificationImpl((InternalEObject) tSys,
					Notification.SET,
					HenshinPackage.MODULE__UNITS,
					tUnit, tUnit));

		}
	}

	/**
	 * Send execute command notify.
	 * 
	 * @param graph
	 *            the graph
	 */
	public static void sendExecuteCommandNotify(Graph graph) {
		graph.eNotify(new ENotificationImpl((InternalEObject) graph,
				HenshinNotification.EXECUTED,
				HenshinPackage.MODULE__INSTANCES, graph, null));
	}

	/**
	 * Send transformation undo notify.
	 * 
	 * @param graph
	 *            the graph
	 */
	public static void sendTransformationUndoNotify(Graph graph) {
		graph.eNotify(new ENotificationImpl((InternalEObject) graph,
				HenshinNotification.TRANSFORMATION_UNDO,
				HenshinPackage.MODULE__INSTANCES, graph, null));
	}

	/**
	 * Send transformation redo notify.
	 * 
	 * @param graph
	 *            the graph
	 */
	public static void sendTransformationRedoNotify(Graph graph) {
		graph.eNotify(new ENotificationImpl((InternalEObject) graph,
				HenshinNotification.TRANSFORMATION_REDO,
				HenshinPackage.MODULE__INSTANCES, graph, null));
	}

}
