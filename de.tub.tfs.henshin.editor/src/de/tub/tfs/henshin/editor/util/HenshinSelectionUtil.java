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
 * HenshinSelectionUtil.java
 * created on 08.02.2012 21:11:16
 */
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.EdgeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.ui.graph.GraphPage;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.muvitor.ui.MuvitorPage;

/**
 * @author huuloi
 * 
 */
public class HenshinSelectionUtil {

	private static HenshinSelectionUtil instance = null;

	private HenshinSelectionUtil() {
	}

	public static synchronized HenshinSelectionUtil getInstance() {
		if (instance == null) {
			instance = new HenshinSelectionUtil();
		}
		return instance;
	}

	/**
	 * 
	 * @return all opened graph pages
	 */
	public List<GraphPage> getOpenedPages() {

		List<GraphPage> openedPages = new ArrayList<GraphPage>();

		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		if (page != null) {
			IViewReference[] viewRefs = page.getViewReferences();

			if (viewRefs != null) {
				for (IViewReference viewRef : viewRefs) {
					IViewPart viewPart = viewRef.getView(false);

					if (viewPart != null) {
						if (viewPart instanceof GraphView) {
							GraphView graphView = (GraphView) viewPart;
							if (graphView != null) {
								GraphPage openedPage = graphView
										.getCurrentGraphPage();
								openedPages.add(openedPage);
							}
						}
					}
				}
			}
		}

		return openedPages;
	}

	/**
	 * 
	 * @return the active page, if this page instance of GraphPage, otherwise
	 *         return null
	 */
	public MuvitorPage getActivePage() {

		MuvitorPage activePage = null;

		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		if (page != null) {
			IWorkbenchPart activePart = page.getActivePart();
			if (activePart instanceof GraphView) {
				GraphView graphView = (GraphView) activePart;
				activePage = (MuvitorPage) graphView.getCurrentPage();
			}
		}

		return activePage;
	}

	public GraphView getActiveGraphView(Graph graph) {

		GraphView graphView = null;

		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		if (page != null) {
			IViewReference[] viewRefs = page.getViewReferences();

			if (viewRefs != null) {
				for (IViewReference viewRef : viewRefs) {
					IViewPart viewPart = viewRef.getView(false);

					if (viewPart != null) {
						if (viewPart instanceof GraphView) {
							GraphView viewer = (GraphView) viewPart;
							GraphPage graphPage = viewer.getCurrentGraphPage();
							if (graphPage.getCastedModel() == graph) {
								graphView = viewer;
							}
						}
					}
				}
			}
		}

		return graphView;
	}

	/**
	 * 
	 * @return all NodeEditPart in opened pages
	 */
	public List<NodeEditPart> getNodeEditParts() {

		List<GraphPage> openedPages = getOpenedPages();
		List<NodeEditPart> nodes = new ArrayList<NodeEditPart>();
		List<GraphEditPart> graphEditParts = new ArrayList<GraphEditPart>();

		for (GraphPage page : openedPages) {
			graphEditParts.add((GraphEditPart) page.getCurrentViewer()
					.getEditPartRegistry().get(page.getCastedModel()));
		}

		for (GraphEditPart graphEditPart : graphEditParts) {
			List<?> children = graphEditPart.getChildren();
			for (Object object : children) {
				if (object instanceof NodeEditPart) {
					NodeEditPart node = (NodeEditPart) object;
					nodes.add(node);
				}
			}
		}

		return nodes;
	}
	
	public List<NodeEditPart> getNodeEditParts(Graph graph) {
		
		List<NodeEditPart> nodeEditParts = new ArrayList<NodeEditPart>();
		
		GraphView graphView = getActiveGraphView(graph);
		
		GraphPage graphPage = graphView.getCurrentGraphPage();
		
		Object object = graphPage.getCurrentViewer().getEditPartRegistry().get(graph);
		
		if (object instanceof GraphEditPart) {
		
			GraphEditPart graphEditPart = (GraphEditPart) object;
			
			List<?> children = graphEditPart.getChildren();
			
			for (Object obj : children) {
				if (obj instanceof NodeEditPart) {
					NodeEditPart nodeEditPart = (NodeEditPart) obj;
					nodeEditParts.add(nodeEditPart);
				}
			}
		}
		
		return nodeEditParts;
	}
	
	public List<EdgeEditPart> getEdgeEditParts(Graph graph) {
		List<EdgeEditPart> edgeEditParts = new ArrayList<EdgeEditPart>();
		
		GraphView graphView = getActiveGraphView(graph);
		
		GraphPage graphPage = graphView.getCurrentGraphPage();
		
		Object object = graphPage.getCurrentViewer().getEditPartRegistry().get(graph);
		
		if (object instanceof GraphEditPart) {
			GraphEditPart graphEditPart = (GraphEditPart) object;
			List<?> children = graphEditPart.getChildren();
			
			for (Object obj : children) {
				if (obj instanceof EdgeEditPart) {
					EdgeEditPart edgeEditPart = (EdgeEditPart) obj;
					edgeEditParts.add(edgeEditPart);
				}
			}
		}
		
		return edgeEditParts;
	}
	
	public List<EClass> getNodeTypes() {

		List<EClass> eClasses = new Vector<EClass>();
		List<Graph> graphs = getOpenedGraphs();

		for (Graph graph : graphs) {
			eClasses.addAll(NodeTypes.getNodeTypes(graph, true));
		}

		return eClasses;
	}

	/**
	 * 
	 * @return all opened graphs
	 */
	public List<Graph> getOpenedGraphs() {

		List<GraphPage> openedPages = getOpenedPages();
		List<Graph> graphs = new ArrayList<Graph>();

		for (GraphPage page : openedPages) {
			graphs.add(page.getCastedModel());
		}

		return graphs;
	}

	/**
	 * 
	 * @return the Module object
	 */
	public Module getTransformationSystem() {

		Module transformationSystem = null;

		List<GraphPage> openedPages = getOpenedPages();
		if (!openedPages.isEmpty()) {
			GraphPage graphPage = openedPages.get(0);
			Graph graph = graphPage.getCastedModel();
			transformationSystem = ModelUtil.getModelRoot(graph,
					Module.class);
		}

		return transformationSystem;
	}

	/**
	 * 
	 * @return all imported EPackages
	 */
	public List<EPackage> getImportedEPackages() {

		List<EPackage> ePackages = new ArrayList<EPackage>();
		Module transformationSystem = getTransformationSystem();
		if (transformationSystem != null) {
			ePackages = transformationSystem.getImports();
		}

		return ePackages;
	}

	/**
	 * 
	 * @param ePackage
	 *            the given EPackage
	 * @return all node types of ePackage
	 */
	public List<EClass> getAllNodeTypeOf(EPackage ePackage) {

		return NodeTypes.getNodeTypesOfEPackage(ePackage, true);
	}
	
	public Map<?, ?> getEditPartRegistry(Graph graph) {
		GraphView graphView = getActiveGraphView(graph);
		
		GraphPage graphPage = graphView.getCurrentGraphPage();
		
		Map<?, ?> editPartRegistry = graphPage.getCurrentViewer().getEditPartRegistry();
		
		return editPartRegistry;
	}

}
