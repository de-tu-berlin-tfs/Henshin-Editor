package tggeditor.actions.imports;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

//import de.tub.tfs.henshin.editor.editparts.tree.SimpleListTreeEditpart;
import tggeditor.editparts.tree.TransformationSystemTreeEditPart;
//import de.tub.tfs.henshin.editor.util.IconUtil;

public class ImportInstanceModelAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "tggeditor.actions.ImportInstanceModelAction";

	
	private TransformationSystem transformationSystem;

	
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
				transformationSystem = (TransformationSystem) host.getModel();
				return true;
		}

		return false;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Shell shell = new Shell();
		ResourceDialog dialog = new ResourceDialog(shell, "Please select the instance model you want to load.", SWT.OPEN + SWT.MULTI);
		
		//FileDialog dialog = new FileDialog(shell);
		//dialog.setFilterExtensions(new String[]{"*.ecore"});

		//dialog.
		HashMap<EObject,Node> instanceGraphToHenshinGraphMapping = new HashMap<EObject, Node>(); 
		int p = dialog.open();
		if (p == 1 || p == SWT.CANCEL){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), "No File selected.", "Please select a valid emf file to load an instance model.",SWT.SHEET );
			return;
		}
		List<URI> urIs = dialog.getURIs();
		if (urIs.isEmpty()){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), "No File selected.", "Please select a valid emf file to load an instance model.",SWT.SHEET );
			return;
		}
		long startTime = System.currentTimeMillis();
		System.out.println("DEBUG: start instance import ");
		for (URI uri : urIs) {
			ResourceImpl r = (ResourceImpl) transformationSystem.eResource().getResourceSet().getResource(uri, true);
			r.unload();
			try {
				r.load(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Graph graph = HenshinFactory.eINSTANCE.createGraph();
			//HenshinGraph henshinGraph = new HenshinGraph(graph);
			
			if (r.getContents().isEmpty())
				continue;
			TreeIterator<EObject> itr = r.getAllContents();
			//henshinGraph.addRoot(r.getContents().get(0));
			graph.setName(uri.segment(uri.segmentCount()-1));
			/*if (r.getContents().get(0) instanceof NamedElement){
				graph.setName(((NamedElement)r.getContents().get(0)).getName());
			} else {
				graph.setName("_DEBUG:_" + r.getContents().get(0).toString() + "_");
			}*/
			
			while (itr.hasNext()){
				EObject eObj = itr.next();
				//henshinGraph.addEObject(eObj);
				Node node = HenshinFactory.eINSTANCE.createNode();
				node.setType(eObj.eClass());
				/*if (eObj instanceof NamedElement){
					node.setName(((NamedElement)eObj).getName());
				} else {
					node.setName("_DEBUG:_" + eObj.toString() + "_");
				}*/
				node.setName("");
				instanceGraphToHenshinGraphMapping.put(eObj, node);
				graph.getNodes().add(node);
			}
			itr = r.getAllContents();
			while (itr.hasNext()){
				EObject eObj = itr.next();
				Node node = instanceGraphToHenshinGraphMapping.get(eObj);
				for (EStructuralFeature feat : eObj.eClass().getEAllStructuralFeatures()){
					if (eObj.eIsSet(feat)){
						if (feat instanceof EReference){
							if (feat.isMany()){
								List<EObject> list = (List<EObject>) eObj.eGet(feat);
								for (EObject ref : list) {
									Edge edge = HenshinFactory.eINSTANCE.createEdge();
									edge.setSource(node);
									if (instanceGraphToHenshinGraphMapping.containsKey(ref)){
										edge.setTarget(instanceGraphToHenshinGraphMapping.get(ref));
									} else {
										edge.setTarget(createTargetNode(ref, graph));
									}
									edge.setType((EReference) feat);
									graph.getEdges().add(edge);
								}
							} else {
								EObject ref = (EObject) eObj.eGet(feat);
								Edge edge = HenshinFactory.eINSTANCE.createEdge();
								edge.setSource(node);
								if (instanceGraphToHenshinGraphMapping.containsKey(ref)){
									edge.setTarget(instanceGraphToHenshinGraphMapping.get(ref));
								} else {
									edge.setTarget(createTargetNode(ref, graph));
								}
								edge.setType((EReference) feat);
								graph.getEdges().add(edge);
							}
						} else if (feat instanceof EAttribute){
							if (feat.isMany()){
								// cannot handle array attributes right now
							} else {
								Attribute attr = HenshinFactory.eINSTANCE.createAttribute();
								attr.setNode(node);
								attr.setType((EAttribute) feat);
								attr.setValue(eObj.eGet(feat).toString());
								if (attr.getType().getName().contains("name")  && !(eObj instanceof NamedElement)){
									node.setName(attr.getValue());
								}
							}
						}
					}
				}
				
				System.out.println("");
			}
			System.out.println("DEBUG: end instance import " + ((System.currentTimeMillis() - startTime) / 1000d)  + " s");
			startTime = System.currentTimeMillis();
			transformationSystem.getInstances().add(graph);
			System.out.println("DEBUG: graph added " + ((System.currentTimeMillis() - startTime) / 1000d)  + " s");
			
		}	
		//resourceSet.getURIConverter().getURIMap().put(uri, URI.createFileURI(p));
		
		shell.dispose();
		
		super.run();
	}

	private HashMap<Graph, HashMap<EObject,Node>> graphToNodeMap = new HashMap<Graph, HashMap<EObject,Node>>();
	private Node createTargetNode(EObject ref,Graph graph) {
		HashMap<EObject, Node> map = graphToNodeMap.get(graph);
		if (map == null)
			graphToNodeMap.put(graph, map = new HashMap<EObject, Node>());
		Node node = map.get(ref);
		if (node == null){
			node = HenshinFactory.eINSTANCE.createNode();
			node.setType(ref.eClass());
			node.setName(ref.toString());
			graph.getNodes().add(node);
			map.put(ref, node);
		}
		return node;
	}
}
