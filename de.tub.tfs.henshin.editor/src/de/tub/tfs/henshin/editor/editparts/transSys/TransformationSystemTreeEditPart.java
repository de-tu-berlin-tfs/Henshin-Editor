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
package de.tub.tfs.henshin.editor.editparts.transSys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.TreeEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.transSys.TransformationSystemPropertySource;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.ui.rule.RuleView;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * A {@link TreeEditPart} of the model root {@link Module}.
 * 
 * @author johann, nam
 */
public class TransformationSystemTreeEditPart extends
		AdapterTreeEditPart<Module> implements IDirectEditPart {

	private EContainerDescriptor imports;

	private EContainerDescriptor instances;

	private EContainerDescriptor rules;

	private EContainerDescriptor units;

	private EContainerDescriptor flowDiagrams;

	/**
	 * Constructs a new {@link TransformationSystemTreeEditPart} for a given
	 * {@link Module}
	 * 
	 * @param model
	 *            the model object.
	 */
	public TransformationSystemTreeEditPart(Module model) {
		super(model);

		Map<EClass, EStructuralFeature> importContainmentMap = new HashMap<EClass, EStructuralFeature>();
		Map<EClass, EStructuralFeature> instancesContainmentMap = new HashMap<EClass, EStructuralFeature>();
//		Map<EClass, EStructuralFeature> rulesContainmentMap = new HashMap<EClass, EStructuralFeature>();
		Map<EClass, EStructuralFeature> unitsContainmentMap = new HashMap<EClass, EStructuralFeature>();
		Map<EClass, EStructuralFeature> diagramsContainmentMap = new HashMap<EClass, EStructuralFeature>();

		importContainmentMap.put(EcorePackage.Literals.EPACKAGE,
				HenshinPackage.Literals.MODULE__IMPORTS);
		instancesContainmentMap.put(HenshinPackage.Literals.GRAPH,
				HenshinPackage.Literals.MODULE__INSTANCES);
//		rulesContainmentMap.put(HenshinPackage.Literals.RULE,
//				HenshinPackage.Literals.MODULE__UNITS);
		unitsContainmentMap
				.put(HenshinPackage.Literals.UNIT,
						HenshinPackage.Literals.MODULE__UNITS);
		diagramsContainmentMap.put(FlowControlPackage.Literals.FLOW_DIAGRAM,
				FlowControlPackage.Literals.FLOW_CONTROL_SYSTEM__UNITS);

		imports = HenshinLayoutFactory.eINSTANCE.createEContainerDescriptor();
		imports.setContainer(model);
		imports.setContainmentMap(importContainmentMap);

		instances = HenshinLayoutFactory.eINSTANCE.createEContainerDescriptor();
		instances.setContainer(model);
		instances.setContainmentMap(instancesContainmentMap);

//		rules = HenshinLayoutFactory.eINSTANCE.createEContainerDescriptor();
//		rules.setContainer(model);
//		rules.setContainmentMap(rulesContainmentMap);

		units = HenshinLayoutFactory.eINSTANCE.createEContainerDescriptor();
		units.setContainer(model);
		units.setContainmentMap(unitsContainmentMap);

		flowDiagrams = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		flowDiagrams.setContainer(FlowControlUtil.INSTANCE
				.getFlowControlSystem(model));
		flowDiagrams.setContainmentMap(diagramsContainmentMap);

		// registers this to be notified by changes in flow control system model
		registerAdapter(FlowControlUtil.INSTANCE.getFlowControlSystem(model));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart#getDirectEditFeatureID
	 * ()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.MODULE__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(getCastedModel(),
				HenshinPackage.MODULE, getCastedModel(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getAdapter(java.
	 * lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (EPackage.class.equals(key) || Graph.class.equals(key)
				|| Rule.class.equals(key)
				|| Unit.class.equals(key)
				|| FlowDiagram.class.equals(key)) {

			return getCastedModel();
		}

		return super.getAdapter(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#canShowView()
	 */
	@Override
	protected boolean canShowView() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new TransformationSystemClipboardEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new TransformationSystemPropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.TRANS_SYS.img(18);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new ArrayList<Object>();

		children.add(imports);
		children.add(instances);
		// children.add(rules);
		children.add(units);
		children.add(flowDiagrams);

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		if (getCastedModel().getName() == null) {
			return "";
		}

		return getCastedModel().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		int msgId = notification.getFeatureID(HenshinPackage.class);

		switch (msgId) {
		case HenshinPackage.MODULE__NAME:
			refreshVisuals();
			break;

		case HenshinPackage.MODULE__IMPORTS:
			// new meta model loaded, so palletes need to be refreshed
			refreshPallets();
			break;

		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#addChild(org.eclipse.gef.EditPart
	 * , int)
	 */
	@Override
	protected void addChild(EditPart child, int index) {
		super.addChild(child, index);

		child.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();

		performOpen();
	}

	/**
	 * Refreshes all graphical views' {@link PaletteRoot}s to adapt imports of
	 * new meta model packages.
	 */
	private void refreshPallets() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		if (page != null) {
			IViewReference[] viewRefs = page.getViewReferences();

			if (viewRefs != null) {
				for (IViewReference viewRef : viewRefs) {
					IViewPart viewPart = viewRef.getView(true);

					if (viewPart != null) {
						if (viewPart instanceof GraphView) {
							((GraphView) viewPart).getCurrentGraphPage()
									.refreshPallets();
						}

						else if (viewPart instanceof RuleView) {
							((RuleView) viewPart).getCurrentRulePage()
									.refreshPallets();
						}

						else if (viewPart instanceof ConditionView) {
							((ConditionView) viewPart)
									.getCurrentConditionPage().refreshPallets();
						}
					}
				}
			}
		}
	}
}
