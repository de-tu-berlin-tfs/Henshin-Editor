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
 * SearchMatchAction.java
 * created on 18.03.2012 17:15:04
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 *
 */
public class SearchMatchAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.SearchMatchAction";

	private Graph graph;
	
	public SearchMatchAction(IWorkbenchPart part, Graph graph) {

		super(part);
		setId(ID);
		setText(Messages.MATCH_SEARCH);
		setToolTipText(Messages.MATCH_SEARCH_DESC);
		this.graph = graph;
	}
	
	@Override
	protected boolean calculateEnabled() {
		
		return graph != null && graph.getNodes().size() > 0;
	}
	
	private EList<Rule> getRules(Module m) {
		EList<Rule> rules = new BasicEList<Rule>();
		for (Unit unit : m.getUnits()) {
			if (unit instanceof Rule) {
				rules.add((Rule) unit);
			}
		}
		return ECollections.unmodifiableEList(rules);
	}
	
	@Override
	public void run() {
		Module transformationSystem = ModelUtil.getModelRoot(graph, Module.class);
		List<Rule> rules = getRules(transformationSystem);
		GraphView graphView = HenshinSelectionUtil.getInstance().getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		// open rule selection dialog
		Rule rule = DialogUtil.runRuleChoiceDialog(shell, rules);
		
		if(rule == null){
			return;
		}
		
		// do search
		HenshinEGraph henshinGraph = new HenshinEGraph(graph);
		
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		
		RuleApplication ruleApplication = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		Iterable<Match> allMatches = engine.findMatches(rule, henshinGraph, ruleApplication.getPartialMatch());
		
		List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance().getNodeEditParts(graph);
		
		Set<EObject> matchedEObjects = new HashSet<EObject>();
		
		for (NodeEditPart nodeEditPart : nodeEditParts) {
		
			Node node = nodeEditPart.getCastedModel();
			
			EObject eObject = henshinGraph.getNode2ObjectMap().get(node);
			
			for (Match match : allMatches) {
			
				for (EObject nodeTarget : match.getNodeTargets()) {
				
					if (nodeTarget == eObject) {
						nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
						matchedEObjects.add(eObject);
					}
					
					else if (!matchedEObjects.contains(eObject) && nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart.getDefaultColor()) {
						nodeEditPart.getFigure().setBackgroundColor(nodeEditPart.getDefaultColor());
					}
				}
			}
		}

		// refresh
		((GraphEditPart)graphView.getCurrentGraphPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
		
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ResourceUtil.ICONS.MATCH_SEARCH_TOOL.descr(Constants.SIZE_16);
	}
}
