package de.tub.tfs.henshin.editor.actions.transSys;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.NodeUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;

public class ImportInstanceModelAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.ImportInstanceModelAction";

	private TransformationSystem transformationSystem;

	public ImportInstanceModelAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Import EMF Instance Model...");
		setImageDescriptor(IconUtil.getDescriptor("attr16.png"));
		setToolTipText("Imports an instance model.");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof TransformationSystem) {
					transformationSystem = (TransformationSystem) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Graph.class) != null) {
					transformationSystem = (TransformationSystem) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return transformationSystem != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Shell shell = new Shell();
		ResourceDialog dialog = new ResourceDialog(shell,
				"Please select the instance model you want to load.", SWT.OPEN
						+ SWT.MULTI);

		HashMap<EObject, Node> instanceGraphToHenshinGraphMapping = new HashMap<EObject, Node>();

		int p = dialog.open();

		if (p == 1) {
			return;
		}

		List<URI> urIs = dialog.getURIs();
		shell.dispose();
		
		if (urIs.isEmpty()) {
			MessageDialog
					.open(SWT.ERROR,
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getActivePage()
									.getActivePart().getSite().getShell(),
							"No File selected.",
							"Please select a valid emf file to load an instance model.",
							SWT.SHEET);
			return;
		}

		for (URI uri : urIs) {
			ResourceImpl r = (ResourceImpl) transformationSystem.eResource()
					.getResourceSet().getResource(uri, true);
			r.unload();
			try {
				r.load(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Graph graph = HenshinFactory.eINSTANCE.createGraph();

			if (r.getContents().isEmpty())
				continue;
			TreeIterator<EObject> itr = r.getAllContents();
			graph.setName(uri.segment(uri.segmentCount() - 1));

			while (itr.hasNext()) {
				EObject eObj = itr.next();
				Node node = HenshinFactory.eINSTANCE.createNode();
				node.setType(eObj.eClass());
				node.setName("");
				instanceGraphToHenshinGraphMapping.put(eObj, node);
				graph.getNodes().add(node);
			}
			itr = r.getAllContents();
			while (itr.hasNext()) {
				EObject eObj = itr.next();
				Node node = instanceGraphToHenshinGraphMapping.get(eObj);
				for (EStructuralFeature feat : eObj.eClass()
						.getEAllStructuralFeatures()) {
					if (eObj.eIsSet(feat)) {
						if (feat instanceof EReference) {
							if (feat.isMany()) {
								List<EObject> list = (List<EObject>) eObj
										.eGet(feat);
								for (EObject ref : list) {
									Edge edge = HenshinFactory.eINSTANCE
											.createEdge();
									edge.setSource(node);
									if (instanceGraphToHenshinGraphMapping
											.containsKey(ref)) {
										edge.setTarget(instanceGraphToHenshinGraphMapping
												.get(ref));
									} else {
										edge.setTarget(createTargetNode(ref,
												graph));
									}
									edge.setType((EReference) feat);
									graph.getEdges().add(edge);
								}
							} else {
								EObject ref = (EObject) eObj.eGet(feat);
								Edge edge = HenshinFactory.eINSTANCE
										.createEdge();
								edge.setSource(node);
								if (instanceGraphToHenshinGraphMapping
										.containsKey(ref)) {
									edge.setTarget(instanceGraphToHenshinGraphMapping
											.get(ref));
								} else {
									edge.setTarget(createTargetNode(ref, graph));
								}
								edge.setType((EReference) feat);
								graph.getEdges().add(edge);
							}
						} else if (feat instanceof EAttribute) {
							if (feat.isMany()) {
								// cannot handle array attributes right now
							} else {
								Attribute attr = HenshinFactory.eINSTANCE
										.createAttribute();
								attr.setNode(node);
								attr.setType((EAttribute) feat);
								attr.setValue(eObj.eGet(feat).toString());
								if (attr.getType().getName().contains("name")
										&& !(eObj instanceof NamedElement)) {
									node.setName(attr.getValue());
								}
							}
						}
					}
				}
			}

			LinkedList<Layout> layouts = new LinkedList<Layout>();

			for (Node n : graph.getNodes()) {
				NodeLayout l = HenshinLayoutFactory.eINSTANCE
						.createNodeLayout();

				l.setModel(n);

				Point loc = NodeUtil.calculatePosition(
						NodeUtil.getPoints2NodeLyouts(n, layouts), l);

				l.setX(loc.x);
				l.setY(loc.y);

				layouts.add(l);
			}

			CompoundCommand cmd = new CompoundCommand("Import Instance Graph");
			LayoutSystem layoutSystem = HenshinLayoutUtil.INSTANCE
					.getLayoutSystem(transformationSystem);

			for (Layout l : layouts) {
				cmd.add(new SimpleAddEObjectCommand<EObject, EObject>(l,
						HenshinLayoutPackage.LAYOUT_SYSTEM__LAYOUTS,
						layoutSystem));
			}

			cmd.add(new SimpleAddEObjectCommand<EObject, EObject>(graph,
					HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES,
					transformationSystem));

			execute(cmd);
		}
	}

	private HashMap<Graph, HashMap<EObject, Node>> graphToNodeMap = new HashMap<Graph, HashMap<EObject, Node>>();

	private Node createTargetNode(EObject ref, Graph graph) {
		HashMap<EObject, Node> map = graphToNodeMap.get(graph);
		if (map == null)
			graphToNodeMap.put(graph, map = new HashMap<EObject, Node>());
		Node node = map.get(ref);
		if (node == null) {
			node = HenshinFactory.eINSTANCE.createNode();
			node.setType(ref.eClass());
			node.setName(ref.toString());
			graph.getNodes().add(node);
			map.put(ref, node);
		}
		return node;
	}
}
