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
package de.tub.tfs.henshin.editor.ui.flow_diagram;

import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.gef.tools.CreationTool;

import de.tub.tfs.henshin.editor.actions.flow_diagram.tools.LinkCreationTool;
import de.tub.tfs.henshin.editor.actions.flow_diagram.tools.SecondTransitionCreationTool;
import de.tub.tfs.henshin.editor.actions.rule.RuleCreationTool;
import de.tub.tfs.henshin.editor.model.FlowControlModelCreationFactory;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;

/**
 * @author nam
 * 
 */
public class FlowDiagramPaletteRoot extends MuvitorPaletteRoot {

	/**
     * 
     */
	public FlowDiagramPaletteRoot() {
		PaletteDrawer flowElementsGroup = new PaletteDrawer("Flow Diagram");

		PaletteStack activityGroup = new PaletteStack(null, null, null);

		PaletteStack conditionalActGroup = new PaletteStack(null, null, null);

		flowElementsGroup.setSmallIcon(ResourceUtil.ICONS.FLOW_DIAGRAM
				.descr(16));

		defaultPaletteGroup.add(flowElementsGroup);

		flowElementsGroup.add(activityGroup);
		flowElementsGroup.add(conditionalActGroup);

		addToolEntry(flowElementsGroup, "Compound Activity",
				"Create a Compound Activity",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.COMPOUND_ACTIVITY),
				ResourceUtil.ICONS.COMPOUND_ACTIVITY.descr(16),
				IconUtil.getDescriptor("act_tool32.png"), CreationTool.class);

		addToolEntry(conditionalActGroup, "Conditional Activity",
				"Create a Conditional Activity",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.CONDITIONAL_ACTIVITY),
				IconUtil.getDescriptor("cond_activity_tool16.png"),
				IconUtil.getDescriptor("conditional_activity32.png"),
				CreationTool.class);

		addToolEntry(conditionalActGroup, "Rule",
				"Create or set an Activity with a contained Rule",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.CONDITIONAL_ACTIVITY),
				ResourceUtil.ICONS.RULE_COND_ACT_TOOL.descr(16),
				ResourceUtil.ICONS.RULE_COND_ACT_TOOL.descr(32),
				RuleCreationTool.class);

		addToolEntry(conditionalActGroup, "Diagram Link",
				"Create or set an Activity linked to another Flow Diagram",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.CONDITIONAL_ACTIVITY),
				IconUtil.getDescriptor("cond_act_tool_link16.png"),
				IconUtil.getDescriptor("cond_act_tool_link32.png"),
				LinkCreationTool.class);

		addToolEntry(flowElementsGroup, "Pamameter Mapping",
				"Create Mapping between Parameters.",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.PARAMETER_MAPPING),
				ResourceUtil.ICONS.PARAM_MAPPING.descr(16),
				IconUtil.getDescriptor("transition_tool32.png"),
				ConnectionCreationTool.class);

		addToolEntry(flowElementsGroup, "Transition",
				"Create a Transition between Actitvities",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.TRANSITION),
				IconUtil.getDescriptor("transition_tool16.png"),
				IconUtil.getDescriptor("transition_tool32.png"),
				ConnectionCreationTool.class);

		addToolEntry(flowElementsGroup, "Second Transition",
				"Create a Alternative Transition from Conditional Activities",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.TRANSITION),
				IconUtil.getDescriptor("second_transition16.png"),
				IconUtil.getDescriptor("transition_tool32.png"),
				SecondTransitionCreationTool.class);

		addToolEntry(activityGroup, "Simple Activity",
				"Create an Simple Activity",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.SIMPLE_ACTIVITY),
				IconUtil.getDescriptor("act_tool16.png"),
				IconUtil.getDescriptor("simple_activity32.png"),
				CreationTool.class);

		addToolEntry(activityGroup, "Rule",
				"Create or set an Activity with a contained Rule",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.SIMPLE_ACTIVITY),
				ResourceUtil.ICONS.RULE_ACT_TOOL.descr(16),
				ResourceUtil.ICONS.RULE_ACT_TOOL.descr(32),
				RuleCreationTool.class);

		addToolEntry(activityGroup, "Diagram Link",
				"Create or set an Activity linked to another Flow Diagram",
				new FlowControlModelCreationFactory(
						FlowControlPackage.Literals.SIMPLE_ACTIVITY),
				IconUtil.getDescriptor("act_tool_link16.png"),
				IconUtil.getDescriptor("act_tool_link32.png"),
				LinkCreationTool.class);
	}
}
