package de.tub.tfs.henshin.tgg.interpreter.gui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

public class LoadHandler extends AbstractHandler implements IHandler {
	private static final String henshinExt = "henshin";
	private static final String tggExt = "tgg";
	protected static List<String> trFileNames = new Vector<String>();
	protected static IFile trFile;
	protected static List<Module> trSystems = new Vector<Module>();
	protected static List<TGG> layoutModels = new Vector<TGG>();
	protected static Queue<IFile> loadQueue = new LinkedList<IFile>();
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// clear all lists from possible previous execution
		loadQueue.clear();
		trSystems.clear();
		layoutModels.clear();
		trFileNames.clear();
		
		// Find grammar files to load:
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel != null && sel instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) sel;
			@SuppressWarnings("unchecked")
			Iterator<Object> it = structSel.iterator();
			for (;it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					if (file.getFileExtension().equals(henshinExt)) {
						loadQueue.add(file);
					}
				}
				if (obj instanceof IContainer) {
					IResource[] resArr;
					try {
						resArr = ((IContainer) obj).members();
						for (int i=0; i<resArr.length; i++) {
							if (resArr[i] instanceof IFile) {
								IFile file = (IFile) resArr[i];
								if (file.getFileExtension().equals(henshinExt)) {
									loadQueue.add(file);
								}
							}
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				for(IFile f:loadQueue){
					trFileNames.add(f.getName());
				}
			}
		}
		
		Job job = new Job("Loading Triple Graph Grammar") {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Loading Triple Graph Grammars", 3);
					monitor.subTask("Preparing");
					ResourceSet resSet = new ResourceSetImpl();
					monitor.worked(1);
					
					EMFModelManager manager = EMFModelManager.createModelManager(henshinExt);					
					manager.cleanUp();
					de.tub.tfs.henshin.tggeditor.TreeEditor.initClassConversions();
					
					Iterator<IFile> loadQueueIt = loadQueue.iterator();
					while (loadQueueIt.hasNext()) {
						trFile = loadQueueIt.next();
						monitor.subTask("Loading " + trFile.getName());
						String trFilePath = trFile.getFullPath().toString();
						URI trURI = URI.createPlatformResourceURI(trFilePath,
								true);
						
						EList<EObject> modules = resSet
								.getResource(trURI, true).getContents();
						if (modules.size() > 0)
							trSystems.add((Module) modules.get(0));
						monitor.worked(1);

						String tggFilePath = trFilePath.substring(0,
								trFilePath.length() - henshinExt.length() - 1)
								+ "." + tggExt;
						
						URI tggURI = URI.createPlatformResourceURI(tggFilePath,
								true);
						ResourceSet resSet2 = new ResourceSetImpl();
						
						EList<EObject> retrievedLayoutModels = resSet2.getResource(
								tggURI, true).getContents();
						if (retrievedLayoutModels.size() > 0)
							layoutModels.add((TGG) retrievedLayoutModels.get(0));
						monitor.worked(1);
						System.out.println("Grammar " + trFile.getName() + " was loaded successfully.");
					}
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(new LoadSchedulingRule());
		job.schedule();
		return null;
	}
}