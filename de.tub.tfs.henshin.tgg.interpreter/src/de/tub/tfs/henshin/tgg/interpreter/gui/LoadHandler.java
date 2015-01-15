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
package de.tub.tfs.henshin.tgg.interpreter.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.interpreter.util.TggUtil;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

public class LoadHandler extends AbstractHandler implements IHandler {
	public static final String henshinExt = "henshin";
	protected static List<String> trFileNames = new Vector<String>();
	protected static IFile trFile;
	protected static List<TGG> trSystems = new ArrayList<TGG>();
	protected static Job loadGrammarJob = null;
	protected static Queue<IFile> loadQueue = new LinkedList<IFile>();
	protected static HashMap<String,Long> cacheTimes = new HashMap<String, Long>();
	protected static HashMap<String,EList<EObject>> cacheFiles = new HashMap<String,EList<EObject>>();	
	
	public static List<String> getTrFileNames() {
		return trFileNames;
	}

	public static List<TGG> getTrSystems() {
		return trSystems;
	}


	
	private static void loadCachedQueue() {
		// saves plugin preferences at the workspace level
		try {
			IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("lu.uni.snt.scope2spell.gui"); // does all the above behind the scenes
			
			int entries = prefs.getInt("CachedQueueEntries",0);

			for (int i = 0; i < entries; i++) {
				String locationString = prefs.get("CachedLoadQueue_" + i,null);
				if (locationString != null){
					IPath p = Path.fromOSString(locationString);
					
					IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(p);
					if (!iFile.exists()){
						 iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(p.makeAbsolute());
						 IPath fullPath = iFile.getFullPath();
						 Field field = fullPath.getClass().getDeclaredField("device");
						 field.setAccessible(true);
						 field.set(fullPath, p.getDevice());
					}
					loadQueue.add(iFile);
					trFileNames.add(iFile.getName());
				}
			}

			// prefs are automatically flushed during a plugin's "super.stop()".
			///prefs.flush();
		} catch(Exception e) {
			//TODO write a real exception handler.
			e.printStackTrace();
		}
	}
	
	private static void saveCachedQueue() {
		// saves plugin preferences at the workspace level
		try {
			IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("lu.uni.snt.scope2spell.gui"); // does all the above behind the scenes
			
			prefs.putInt("CachedQueueEntries",loadQueue.size());

			int idx = 0;
			for (IFile iFile : loadQueue) {
				prefs.put("CachedLoadQueue_" + idx,iFile.getFullPath().toString());
				idx++;
			}

			// prefs are automatically flushed during a plugin's "super.stop()".
			prefs.flush();
		} catch(Exception e) {
			//TODO write a real exception handler.
			e.printStackTrace();
		}
	}

	
	public static void updateGrammars(){
		EMFModelManager manager = EMFModelManager.createModelManager(henshinExt);					
		manager.cleanUp();
		TggUtil.initClassConversions();
		
		if (loadQueue.isEmpty()){
			loadCachedQueue();
		}
		
		
		trSystems.clear();
		final ResourceSet resSet = new ResourceSetImpl();

		setLoadGrammarJob(new Job("Loading Triple Graph Grammar") {

			protected IStatus run(IProgressMonitor monitor) {

				try {
					monitor.beginTask("Updating Triple Graph Grammars", loadQueue.size());
					monitor.subTask("Preparing");

					boolean useCache = false;

					Iterator<IFile> loadQueueIt = loadQueue.iterator();
					while (loadQueueIt.hasNext()) {
						trFile = loadQueueIt.next();
						monitor.subTask("Loading " + trFile.getName());
						String trFilePath = trFile.getFullPath().toString();
						URI trURI = URI.createPlatformResourceURI(trFilePath,
								true);
						EList<EObject> modules;
						if (cacheTimes.get(trFilePath) != null && trFile.getModificationStamp() <= (Long)cacheTimes.get(trFilePath)){
							modules = cacheFiles.get(trFilePath);
							useCache = true;
						} else {
							Resource resource = resSet.getResource(trURI, false);
							if (resource != null)
								resource.unload();
							try {
								modules = resSet.getResource(trURI, true).getContents();
							} catch (Exception ex){
								trURI = URI.createFileURI(trFilePath);
								modules = resSet.getResource(trURI, true).getContents();
							}
							
							cacheTimes.put(trFilePath, trFile.getModificationStamp());
							cacheFiles.put(trFilePath, modules);
						}
						
						if (modules.size() > 0)
							trSystems.add((TGG) modules.get(0));

						monitor.worked(1);
						System.out.println("Grammar " + trFile.getName() + " was loaded successfully.");


					}
					if (useCache){
						System.out.println("Only changed modules have been loaded.");
						System.out.println("Hold Shift while clicking the Load button to clear cache.");
					}
				} finally {
					monitor.done();

				}
				return Status.OK_STATUS;
			}

		});
		getLoadGrammarJob().setRule(new LoadSchedulingRule());
		getLoadGrammarJob().schedule();
		getLoadGrammarJob().wakeUp();


	}
	
	public static Job getLoadGrammarJob() {
		return loadGrammarJob;
	}

	public static void setLoadGrammarJob(Job loadGrammarJob) {
		LoadHandler.loadGrammarJob = loadGrammarJob;
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (event != null && event.getTrigger() instanceof Event){
			int shift = SWT.SHIFT & ((Event)event.getTrigger()).stateMask;
			if (shift != 0){// press shift while clicking button to clear cache
				cacheTimes.clear();
				cacheFiles.clear();
			}
		}
		
		// clear all lists from possible previous execution
		loadQueue.clear();
		trSystems.clear();
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
		saveCachedQueue();
		System.out.println("Registered Grammars will be loaded when needed.");
		
		return null;
	}
}