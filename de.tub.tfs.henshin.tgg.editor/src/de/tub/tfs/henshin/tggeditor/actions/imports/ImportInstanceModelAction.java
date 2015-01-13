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
package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


public class ImportInstanceModelAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "tggeditor.actions.ImportInstanceModelAction";

	/** the shell of the dialog */
	protected Shell shell;
	
	/** the transformation system, in which the instance shall be included */
	protected TGG module;
	
	/** the URIs for the files to import */
	protected List<URI> urIs;
	
	/** the mapping between henshinGraph and instanceGraph  */
	protected HashMap<EObject,TNode> instanceGraphToHenshinGraphMapping = new HashMap<EObject, TNode>();;
	
	/** the current object of the instance graph */
	protected EObject eObj;

	/** the current node of the henshin graph */
	protected TNode node;

	/** the graph to be created in henshin*/
	protected TripleGraph tripleGraph;
	
	public ImportInstanceModelAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Import an instance model.");
//		setImageDescriptor(IconUtil.getDescriptor("attr16.png"));
		setToolTipText("Import an instance model.");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjs = getSelectedObjects();
		if (selectedObjs.size() != 1) {
			return false;
		}

		Object selected = selectedObjs.get(0);
		if (selected instanceof TransformationSystemTreeEditPart) {
			TransformationSystemTreeEditPart host = (TransformationSystemTreeEditPart) selected;
				module = (TGG) host.getModel();
				return true;
		}
		else if (selected instanceof GraphFolderTreeEditPart) {
			GraphFolderTreeEditPart graphFolder = (GraphFolderTreeEditPart) selected;
			TransformationSystemTreeEditPart host = (TransformationSystemTreeEditPart) graphFolder.getParent();
				module = (TGG) host.getModel();
				return true;
		}

		return false;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		instanceGraphToHenshinGraphMapping = new HashMap<EObject, TNode>();

		boolean dialogSuccess = openImportDialog();
		if (!dialogSuccess)
			return;

		long startTime = System.currentTimeMillis();
		System.out.println("DEBUG: start instance import ");
		for (URI uri : urIs){
			ResourceImpl r = (ResourceImpl) module.eResource()
					.getResourceSet().createResource(uri);
			r.unload();
			try {
				r.load(null);
			} catch (IOException e) {
				//e.printStackTrace();
				try {
					r.unload();
				} catch (Exception ex){
					
				}

				HashMap<String,Object> options = new HashMap<String,Object>();
				options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
				// options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE); // duplicate line

				options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

				options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
				// options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE); // duplicate line

				options.put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);		
				
				try {
					r.load(options);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			createAndAddGraph(r,uri);
			
			System.out
					.println("DEBUG: graph added "
							+ ((System.currentTimeMillis() - startTime) / 1000d)
							+ " s");

		}
		// reset triple graph for next call of action
		tripleGraph=null;
		shell.dispose();
		super.run();
	}

	public void createAndAddGraph(ResourceImpl r,URI uri) {
		{
			if(tripleGraph==null)
				tripleGraph = TggFactory.eINSTANCE.createTripleGraph();

			if (r.getContents().isEmpty())
				return;
			Iterator<EObject> itr = r.getAllContents();
			if(tripleGraph.getName()==null)
				tripleGraph.setName(uri.segment(uri.segmentCount() - 1));
			else
				tripleGraph.setName(tripleGraph.getName() + "_" + uri.segment(uri.segmentCount() - 1));
			HashSet<EObject> allNodes = new HashSet<EObject>();
			
			// import all nodes without features first (otherwise, references
			// could be dangling)
			HashSet<EObject> missingNodes = new HashSet<EObject>();
			long amountNodes = 0;
			while (itr.hasNext()) {
				eObj = itr.next();
				createNode();
				amountNodes++;
				allNodes.add(eObj);
				if (eObj instanceof DynamicEObjectImpl){
					for (EReference ref : eObj.eClass().getEAllContainments()) {
						if (ref.isMany()){
							missingNodes.addAll((Collection<? extends EObject>) eObj.eGet(ref));
						} else {
							missingNodes.add((EObject) eObj.eGet(ref));
						}
					}
				}
			}
			Iterator<EObject> iterator = missingNodes.iterator();
			while (iterator.hasNext()) {
				eObj = iterator.next();
				iterator.remove();
				if (eObj == null)
					continue;
				// If this node was already created, then don't create duplicative nodes.
				// This case occurs for input files in xmi format.
				if (allNodes.contains(eObj))
					continue;
				createNode();
				amountNodes++;
				allNodes.add(eObj);
				if (eObj instanceof DynamicEObjectImpl){
					for (EReference ref : eObj.eClass().getEAllContainments()) {
						if (ref.isMany()){
							missingNodes.addAll((Collection<? extends EObject>) eObj.eGet(ref));
							iterator = missingNodes.iterator();
						} else {
							missingNodes.add((EObject) eObj.eGet(ref));
							iterator = missingNodes.iterator();
						}
					}
				}
			}

			TGG tgg = GraphicalNodeUtil.getLayoutSystem(module);
			Iterator<ImportedPackage> importedPkgsItr = tgg.getImportedPkgs().iterator();
			ImportedPackage impPkg;
			List<EObject> typesWithLoadDefaultValues = new Vector<EObject>();
			while(importedPkgsItr.hasNext()){
				impPkg = importedPkgsItr.next();
				if(impPkg.isLoadWithDefaultValues())
				typesWithLoadDefaultValues.addAll(impPkg.getPackage().eContents());
			}
			
			itr = allNodes.iterator();
			boolean loadAttributesWithDefaultValues;
			while (itr.hasNext()) {
				eObj = itr.next();
				// get node that was created in the loop before
				node = instanceGraphToHenshinGraphMapping.get(eObj);
				loadAttributesWithDefaultValues = typesWithLoadDefaultValues.contains(node.getType());

				// iterate over all features of that node
				for (EStructuralFeature feat : eObj.eClass()
						.getEAllStructuralFeatures()) {
					// import references
					if (feat instanceof EReference) {
						createEdge((EReference) feat);
					}
					// import attribute values
					else if (feat instanceof EAttribute) {
						createAttribute((EAttribute) feat, loadAttributesWithDefaultValues);
					}
				}
			}
			//System.out
			//		.println("DEBUG: end instance import "
			//				+ ((System.currentTimeMillis() - startTime) / 1000d)
			//				+ " s");
			//startTime = System.currentTimeMillis();
			module.getInstances().add(tripleGraph);
			
			// extend source component to rectangle with edge length sqrt(n) times 40 pixels per horizontal node, n is amount of nodes
//			GraphLayout dividerSC = GraphUtil.getGraphLayout(graph, true);
//			GraphLayout dividerCT = GraphUtil.getGraphLayout(graph, false);
//			if(dividerSC!= null && dividerCT != null){
				double width = 200; //350 + 200 * Math.sqrt((double)amountNodes);
				tripleGraph.setDividerCT_X((int) (width+200) );
				tripleGraph.setDividerSC_X((int) width );
//			}
			
			//System.out
			//		.println("DEBUG: graph added "
			//				+ ((System.currentTimeMillis() - startTime) / 1000d)
			//				+ " s");

		}
	}

	/**
	 * @return
	 */
	protected boolean openImportDialog() {
		shell = new Shell();
		ResourceDialog dialog = new ResourceDialog(shell, "Please select the instance model you want to load.", SWT.OPEN + SWT.MULTI);
		
		int p = dialog.open();
		if (p == 1 || p == SWT.CANCEL){
			if(p == 1){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), "No File selected.", "Please select a valid emf file to load an instance model.",SWT.SHEET );
			}
			return false;
		}
		urIs = dialog.getURIs();
		if (urIs.isEmpty()){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), "No File selected.", "Please select a valid emf file to load an instance model.",SWT.SHEET );
			return false;
		}
		return true;
	}

	/**
	 * @param feat
	 */
	protected void createAttribute(EAttribute feat,  boolean loadAttributesWithDefaultValues) {
		Attribute attr;
		if (feat.getEType().getName().equals("EFeatureMapEntry"))
			// do nothing, because this map summarizes all features
			return;
		if (!eObj.eIsSet(feat) && (!loadAttributesWithDefaultValues || feat.getDefaultValue() == null)) 
			// no value available, thus do not create an attribute
			return;

		// value is available
		
		// process attribute
		attr = TggFactory.eINSTANCE.createTAttribute();
		attr.setNode(node);
		attr.setType((EAttribute) feat);

		if (attr.getType().getName().contains("name")
				&& !(eObj instanceof NamedElement)) {
			node.setName(attr.getValue());
		}

		if (feat.isMany()) {
			if (eObj.eIsSet(feat)) {
				// value of feature is set
				String valueString = eObj.eGet(feat).toString();
				// remove the square brackets of the array
				// TODO: remove only if the list contains one element
				if (valueString.length() > 1) {
					valueString = valueString.substring(1,
							valueString.length() - 1);
				}
				attr.setValue(valueString);
			} else {
				if (feat.getDefaultValue() != null) {
					attr.setValue(feat.getDefaultValue().toString());
			} 
			}
		} else {
			// feature is single valued
			if (eObj.eIsSet(feat)) {
			attr.setValue(eObj.eGet(feat).toString());
			} else {
				if (feat.getDefaultValue() != null) {
					attr.setValue(feat.getDefaultValue().toString());
		}
	}
		}
	}
	/**
	 * @param feat
	 */
	protected void createEdge(EReference feat) {
		if (eObj.eIsSet(feat)) {
			if (feat.isMany()) {
				List<EObject> list = (List<EObject>) eObj.eGet(feat);
				for (EObject ref : list) {
					Edge edge =  TggFactory.eINSTANCE.createTEdge();
					edge.setSource(node);
					if (instanceGraphToHenshinGraphMapping.containsKey(ref)) {
						edge.setTarget(instanceGraphToHenshinGraphMapping
								.get(ref));
					} else {
						edge.setTarget(createTargetNode(ref, tripleGraph));
					}
					edge.setType((EReference) feat);
					tripleGraph.getEdges().add(edge);
				}
			} else {
				EObject ref = (EObject) eObj.eGet(feat);
				Edge edge = TggFactory.eINSTANCE.createTEdge();
				edge.setSource(node);
				if (instanceGraphToHenshinGraphMapping.containsKey(ref)) {
					edge.setTarget(instanceGraphToHenshinGraphMapping.get(ref));
				} else {
					edge.setTarget(createTargetNode(ref, tripleGraph));
				}
				edge.setType((EReference) feat);
				tripleGraph.getEdges().add(edge);
			}
		}
	}

	/**
	 */
	protected void createNode() {
		//henshinGraph.addEObject(eObj);
		TNode node = TggFactory.eINSTANCE.createTNode();
		node.setType(eObj.eClass());
		node.setX(0);
		node.setY(0);
		/*if (eObj instanceof NamedElement){
			node.setName(((NamedElement)eObj).getName());
		} else {
			node.setName("_DEBUG:_" + eObj.toString() + "_");
		}*/
		node.setName("");
		instanceGraphToHenshinGraphMapping.put(eObj, node);
		tripleGraph.getNodes().add(node);
	}

	protected HashMap<Graph, HashMap<EObject,TNode>> graphToNodeMap = new HashMap<Graph, HashMap<EObject,TNode>>();
	protected TNode createTargetNode(EObject ref,Graph graph) {
		HashMap<EObject, TNode> map = graphToNodeMap.get(graph);
		if (map == null)
			graphToNodeMap.put(graph, map = new HashMap<EObject, TNode>());
		TNode node = map.get(ref);
		if (node == null){
			node = TggFactory.eINSTANCE.createTNode();
			node.setType(ref.eClass());
			node.setName(ref.toString());
			graph.getNodes().add(node);
			map.put(ref, node);
		}
		return node;
	}

	public void setModule(TGG transSys) {
		this.module = transSys;
		
	}
}
