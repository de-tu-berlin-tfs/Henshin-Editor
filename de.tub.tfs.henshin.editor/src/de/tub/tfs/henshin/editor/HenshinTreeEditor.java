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
package de.tub.tfs.henshin.editor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.actions.EditPartRetargetAction;
import de.tub.tfs.henshin.editor.actions.HenshinCopyAction;
import de.tub.tfs.henshin.editor.actions.HenshinCutAction;
import de.tub.tfs.henshin.editor.actions.HenshinPasteAction;
import de.tub.tfs.henshin.editor.actions.IHandlersRegistry;
import de.tub.tfs.henshin.editor.actions.condition.CreateAndAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateApplicationConditionAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateConditionAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateNotAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateOrAction;
import de.tub.tfs.henshin.editor.actions.condition.SetNegatedAction;
import de.tub.tfs.henshin.editor.actions.condition.SwapBinaryFormulaAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ClearActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.CreateFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ExecuteFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.FlowDiagram2UnitAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SortFlowDiagramsAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.UnNestActivityAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateParameterMappingsAction;
import de.tub.tfs.henshin.editor.actions.graph.CollapseChildrenAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateAttributeAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateEdgeAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateGraphAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateNodeAction;
import de.tub.tfs.henshin.editor.actions.graph.FilterMetaModelAction;
import de.tub.tfs.henshin.editor.actions.graph.ValidateGraphAction;
import de.tub.tfs.henshin.editor.actions.rule.AddMultiRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.CreateAttributeConditionAction;
import de.tub.tfs.henshin.editor.actions.rule.CreateLoopWithRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.CreateRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.DeleteMappingAction;
import de.tub.tfs.henshin.editor.actions.rule.ExecuteRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.ValidateRuleAction;
import de.tub.tfs.henshin.editor.actions.transSys.DeleteEPackageAction;
import de.tub.tfs.henshin.editor.actions.transSys.ExportInstanceModelAction;
import de.tub.tfs.henshin.editor.actions.transSys.HenshinTreeContextMenuProvider;
import de.tub.tfs.henshin.editor.actions.transSys.ImportEcoreModelAction;
import de.tub.tfs.henshin.editor.actions.transSys.ImportInstanceModelAction;
import de.tub.tfs.henshin.editor.actions.transSys.SortGraphsAction;
import de.tub.tfs.henshin.editor.actions.transSys.SortRulesAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.AddTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateConditionalUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateIndependentUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateLoopUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreatePriorityUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateSequentialUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.DeleteSeqSubUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.ExecuteTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveDownTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveUpTransformationUnitAction;
import de.tub.tfs.henshin.editor.editparts.HenshinTreeEditPartFactory;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramView;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.ui.rule.RuleView;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitView;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.IDUtil;
import de.tub.tfs.muvitor.ui.MuvitorActivator;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

/**
 * The Class HenshinTreeEditor.
 */
public class HenshinTreeEditor extends MuvitorTreeEditor implements
		IHandlersRegistry {

	private static final String LAYOUT_EXTENSION = "henshinlayout";
	private static final String FLOWCRTL_EXTENSION = "flowcontrol";

	private IPath layoutFilePath;
	private IPath flowCtrlFilePath;

	private final EMFModelManager layoutModelManager = EMFModelManager.createModelManager(
			LAYOUT_EXTENSION);
	private final EMFModelManager flowCtrlModelManager = EMFModelManager.createModelManager(
			FLOWCRTL_EXTENSION);

	private LayoutSystem layoutSystem;
	private FlowControlSystem flowControlSystem;

	
	/* (non-Javadoc)
	 * @see de.tub.tfs.muvitor.ui.MuvitorTreeEditor#registerViewIDs()
	 */
	@Override
	protected void registerViewIDs() {
		super.registerViewIDs();
		registerViewID(HenshinPackage.Literals.GRAPH, GraphView.ID);
		registerViewID(HenshinPackage.Literals.RULE, RuleView.ID);

		registerViewID(HenshinPackage.Literals.NESTED_CONDITION,
				ConditionView.ID);
		registerViewID(HenshinPackage.Literals.NOT, ConditionView.ID);
		registerViewID(HenshinPackage.Literals.AND, ConditionView.ID);
		registerViewID(HenshinPackage.Literals.OR, ConditionView.ID);

		registerViewID(HenshinPackage.Literals.SEQUENTIAL_UNIT,
				TransUnitView.ID);
		registerViewID(HenshinPackage.Literals.INDEPENDENT_UNIT,
				TransUnitView.ID);
		registerViewID(HenshinPackage.Literals.PRIORITY_UNIT, TransUnitView.ID);
		registerViewID(HenshinPackage.Literals.CONDITIONAL_UNIT,
				TransUnitView.ID);
		registerViewID(HenshinPackage.Literals.LOOP_UNIT, TransUnitView.ID);

		registerViewID(FlowControlPackage.Literals.FLOW_DIAGRAM,
				FlowDiagramView.ID);
	}
	
//	// statically registers views
//	static {
//		registerViewID(HenshinPackage.Literals.GRAPH, GraphView.ID);
//		registerViewID(HenshinPackage.Literals.RULE, RuleView.ID);
//
//		registerViewID(HenshinPackage.Literals.NESTED_CONDITION,
//				ConditionView.ID);
//		registerViewID(HenshinPackage.Literals.NOT, ConditionView.ID);
//		registerViewID(HenshinPackage.Literals.AND, ConditionView.ID);
//		registerViewID(HenshinPackage.Literals.OR, ConditionView.ID);
//
//		registerViewID(HenshinPackage.Literals.SEQUENTIAL_UNIT,
//				TransUnitView.ID);
//		registerViewID(HenshinPackage.Literals.INDEPENDENT_UNIT,
//				TransUnitView.ID);
//		registerViewID(HenshinPackage.Literals.PRIORITY_UNIT, TransUnitView.ID);
//		registerViewID(HenshinPackage.Literals.CONDITIONAL_UNIT,
//				TransUnitView.ID);
//		registerViewID(HenshinPackage.Literals.LOOP_UNIT, TransUnitView.ID);
//
//		registerViewID(FlowControlPackage.Literals.FLOW_DIAGRAM,
//				FlowDiagramView.ID);
//
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorTreeEditor#createContextMenuProvider(org.eclipse
	 * .gef.ui.parts.TreeViewer)
	 */
	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			TreeViewer viewer) {
		return new HenshinTreeContextMenuProvider(viewer, getActionRegistry());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorTreeEditor#createCustomActions()
	 */
	@Override
	protected void createCustomActions() {
		registerAction(new CreateGraphAction(this));
		registerAction(new CreateRuleAction(this));
		registerAction(new CreateNodeAction(this));
		registerAction(new ExportInstanceModelAction(this));
		registerAction(new SortRulesAction(this));
		registerAction(new SortGraphsAction(this));
		registerAction(new CreateEdgeAction(this));
		registerAction(new DeleteMappingAction(this));

		registerAction(new CreateConditionAction(this));
		registerAction(new CreateApplicationConditionAction(this));
		registerAction(new CreateNotAction(this));
		registerAction(new CreateAndAction(this));
		registerAction(new CreateOrAction(this));
		registerAction(new SwapBinaryFormulaAction(this));
		registerAction(new SetNegatedAction(this));

		registerAction(new HenshinCopyAction(this));
		registerAction(new HenshinPasteAction(this));
		registerAction(new HenshinCutAction(this));

		registerAction(new ImportEcoreModelAction(this));
		registerAction(new ImportInstanceModelAction(this));
		registerAction(new ExecuteRuleAction(this));
		registerAction(new CreateLoopWithRuleAction(this));
		registerAction(new AddMultiRuleAction(this));

		registerAction(new ExecuteTransformationUnitAction(this));
		registerAction(new ValidateRuleAction(this));
		registerAction(new ValidateGraphAction(this));
		registerAction(new CreateAttributeAction(this));
		registerAction(new CreateSequentialUnitAction(this));
		registerAction(new CreateIndependentUnitAction(this));
		registerAction(new CreatePriorityUnitAction(this));
		registerAction(new CreateConditionalUnitAction(this));
		registerAction(new CreateLoopUnitAction(this));

		registerAction(new AddTransformationUnitAction(this));
		registerAction(new MoveUpTransformationUnitAction(this));
		registerAction(new MoveDownTransformationUnitAction(this));
		registerAction(new CreateParameterAction(this));
		registerAction(new CreateAttributeConditionAction(this));

		registerAction(new CreateFlowDiagramAction(this));
		registerAction(new SetActivityContentAction(this));

		EditPartRetargetAction deleteAction = new EditPartRetargetAction(this,
				ActionFactory.DELETE.getId());

		deleteAction.setDefaultHandler(new DeleteAction(getWorkbenchPart()));
		deleteAction.registerHandler(DeleteEPackageAction.ID);
		deleteAction.registerHandler(DeleteSeqSubUnitAction.ID);
		
		registerAction(deleteAction);

		registerAction(new DeleteEPackageAction(this));
		registerAction(new DeleteSeqSubUnitAction(this));

		registerAction(new UnNestActivityAction(this));
		registerAction(new ExecuteFlowDiagramAction(this));
		registerAction(new ValidateFlowDiagramAction(this));
		registerAction(new ClearActivityContentAction(this));
		registerAction(new SortFlowDiagramsAction(this));
		registerAction(new ValidateParameterMappingsAction(this));

		registerAction(new FilterMetaModelAction(this));
		
		registerAction(new FlowDiagram2UnitAction(this));
	
		registerAction(new CollapseChildrenAction(this));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorTreeEditor#createDefaultModel()
	 */
	@Override
	protected EObject createDefaultModel() {
		Module trafoSystem = HenshinFactory.eINSTANCE
				.createModule();

		trafoSystem.setName("Transformation System");

		return trafoSystem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorTreeEditor#createTreeEditPartFactory()
	 */
	@Override
	protected EditPartFactory createTreeEditPartFactory() {
		return new HenshinTreeEditPartFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorTreeEditor#setupKeyHandler(org.eclipse.gef.KeyHandler
	 * )
	 */
	@Override
	protected void setupKeyHandler(KeyHandler kh) {

	}

	/**
	 * Convenient method to get a correctly typed model root.
	 * 
	 * @param type
	 *            the type of the returned model root
	 * @return the correctly typed model root or <code>null</code>, if not
	 *         found.
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T extends EObject> T getModelRoot(Class<T> type) {
		for (EObject modelRoot : getModelRoots()) {
			if (type.isInstance(modelRoot)) {
				return (T) modelRoot;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorTreeEditor#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if (type == SelectionSynchronizer.class) {
			return null;
		}

		return super.getAdapter(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.actions.IHandlersRegistry#getWorkbenchPart()
	 */
	@Override
	public IWorkbenchPart getWorkbenchPart() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorTreeEditor#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);

		final IFile file = ((IFileEditorInput) input).getFile();

		// loads layout model
		layoutFilePath = file.getFullPath().removeFileExtension()
				.addFileExtension(LAYOUT_EXTENSION);

		List<EObject> layoutModelRoots = new ArrayList<EObject>();

		layoutModelRoots.add(HenshinLayoutFactory.eINSTANCE
				.createLayoutSystem());

		layoutSystem = (LayoutSystem) layoutModelManager.load(layoutFilePath,
				layoutModelRoots).get(0);

		// loads flow control system
		flowCtrlFilePath = file.getFullPath().removeFileExtension()
				.addFileExtension(FLOWCRTL_EXTENSION);

		List<EObject> flowControlModelRoots = new ArrayList<EObject>();

		flowControlModelRoots.add(FlowControlFactory.eINSTANCE
				.createFlowControlSystem());

		flowControlSystem = (FlowControlSystem) flowCtrlModelManager.load(
				flowCtrlFilePath, flowControlModelRoots).get(0);

		this.getModelRoots().add(1, layoutSystem);
		this.getModelRoots().add(2, flowControlSystem);

		// re-registers this editors now with all model roots loaded
		IDUtil.registerEditor(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorTreeEditor#save(org.eclipse.core.resources.IFile,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void save(IFile file, IProgressMonitor monitor)
			throws CoreException {
		Iterator<Layout> iter = layoutSystem.getLayouts().iterator();
		while (iter.hasNext()) {
			Layout tmp = iter.next();
			if (!(tmp instanceof NodeLayout))
				continue;
			NodeLayout layout = (NodeLayout) tmp;
			if (layout.getModel() == null) {
				iter.remove();
				continue;
			}
			if (((Node) layout.getModel()).getGraph() == null) {
				iter.remove();
				continue;
			}
		}
		super.save(file, monitor);

		monitor.beginTask("Saving " + file, 2);
		// save model to file
		try {
			layoutFilePath = file.getFullPath().removeFileExtension()
					.addFileExtension(LAYOUT_EXTENSION);
			layoutModelManager.save(layoutFilePath);
			monitor.worked(1);
			file.refreshLocal(IResource.DEPTH_ZERO, new SubProgressMonitor(
					monitor, 1));
			monitor.done();
		} catch (final FileNotFoundException e) {
			MuvitorActivator.logError("Error writing file.", e);
		} catch (final IOException e) {
			MuvitorActivator.logError("Error writing file.", e);
		}

		monitor.beginTask("Saving " + file, 2);
		// save model to file
		try {
			flowCtrlFilePath = file.getFullPath().removeFileExtension()
					.addFileExtension(FLOWCRTL_EXTENSION);
			flowCtrlModelManager.save(flowCtrlFilePath);
			monitor.worked(1);
			file.refreshLocal(IResource.DEPTH_ZERO, new SubProgressMonitor(
					monitor, 1));
			monitor.done();
		} catch (final FileNotFoundException e) {
			MuvitorActivator.logError("Error writing file.", e);
		} catch (final IOException e) {
			MuvitorActivator.logError("Error writing file.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.actions.IHandlersRegistry#registerHandler(org
	 * .eclipse.jface.action.IAction, java.lang.String)
	 */
	@Override
	public void registerHandler(IAction handler, String id) {
		handler.setId(id);
		
		registerAction(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.actions.IHandlersRegistry#getHandler(java.lang
	 * .String)
	 */
	@Override
	public IAction getHandler(String id) {
		return getActionRegistry().getAction(id);
	}

}
