package tggeditor.actions.exports;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.henshin.interpreter.HenshinGraph;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import tggeditor.editparts.tree.graphical.GraphTreeEditPart;

//import de.tub.tfs.henshin.editor.editparts.tree.graph.GraphTreeEditPart;
//import de.tub.tfs.henshin.editor.editparts.tree.rule.GraphElementsTreeEditPart;
//import de.tub.tfs.henshin.editor.internal.GraphElementsContainterEObject;
//import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

public class ExportInstanceModelAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.ExportInstanceModelAction";

	/** The graph. */
	private Graph graph;
	
	
	public ExportInstanceModelAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Export an instance model.");
//		setImageDescriptor(IconUtil.getDescriptor("attr16.png"));
		setToolTipText("Export an instance model.");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof GraphTreeEditPart) {
				graph = (Graph) editpart.getModel();
				return true;
			}
//			if (editpart instanceof GraphElementsTreeEditPart) {
//				graph = ((GraphElementsContainterEObject) editpart.getModel()).getModel();
//				return true;
//			}
		}
		return false;
	}

	
	@Override
	public void run() {
		Shell shell = new Shell();
		ResourceDialog dialog = new ResourceDialog(shell, "Please select a file name for the instance graph.", SWT.SAVE + SWT.SINGLE);
		
		//FileDialog dialog = new FileDialog(shell);
		//dialog.setFilterExtensions(new String[]{"*.ecore"});

		//dialog.
		int p = dialog.open();
		if (p == SWT.CANCEL || p == 1){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), "Cannot save instance graph.", "Please select a valid file name.",SWT.SHEET );
			return;
		}
		List<URI> urIs = dialog.getURIs();
		if (urIs.isEmpty()){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), "Cannot save instance graph.", "Please select a valid file name.",SWT.SHEET );
			return;
		}
		//for (URI uri : urIs) {
		try {
		
		EMFModelManager modelManager = new EMFModelManager("");
		Path path = new Path(urIs.get(0).toPlatformString(true));
		List<EObject> obj = modelManager.load(path,new LinkedList<EObject>());
		HenshinGraph gr = new HenshinGraph(graph);
			
		obj.addAll(gr.getRootObjects());
		for (EObject eObject : gr.getRootObjects()) {
			EList<EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
			for (EStructuralFeature feature : features) {
				if (feature.getDefaultValueLiteral() == null){
					((EStructuralFeatureImpl)feature).setDefaultValueLiteral("");
				}
			}
		}
		try {
			
			modelManager.save(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (EObject eObject : gr.getRootObjects()) {
			EList<EStructuralFeature> features = eObject.eClass().getEAllStructuralFeatures();
			for (EStructuralFeature feature : features) {
				if (feature.getDefaultValueLiteral() == null){
					feature.setDefaultValueLiteral(null);
				}
			}
		}
		
		
		//}	
		//resourceSet.getURIConverter().getURIMap().put(uri, URI.createFileURI(p));
		} catch (NullPointerException ex){
			MessageDialog.open(SWT.ERROR, PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActivePart().getSite().getShell(), "Cannot save instance graph.", "Please select a valid file name.",SWT.SHEET );
		
		}
		shell.dispose();
		
		super.run();
	}
}
