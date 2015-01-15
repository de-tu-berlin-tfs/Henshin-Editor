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

import java.io.Console;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TransHandler extends AbstractHandler implements IHandler {

	public static final String TRANSLATION_JOB_FAMILY = "lu.uni.snt.translationJobFamily";
	protected static boolean useOutputFolder;
	private Job waitForSave;
	private TranslationJobCreator[] creator = new TranslationJobCreator[MAX_TRANSFORMATION_THREADS];
	public static int MAX_TRANSFORMATION_THREADS = (Runtime.getRuntime().availableProcessors() <= 1 ? 1 : Runtime.getRuntime().availableProcessors());
	public static int MEMORY_NEEDED_FOR_TRANSLATION_PER_THREAD = 100;
	private static Object[] lockObjects;
	private int idx = 0;
	private ConcurrentLinkedQueue<IFile> inputFiles;
	private HashMap<String,String> options = new HashMap<>();
	private static final String CONFIG_RUNTIME_MODULE_NAME = "de.tub.tfs.henshin.tgg.interpreter.config.TggInterpreterConfigRuntimeModule";
	private static com.google.inject.Module CONFIG_RUNTIME_MODULE = null;
	private static Injector CONFIG_INJECTOR = null;
	private Resource CONFIG_RESOURCE;
	private static boolean inited;
	
	static {
		int maxThreadsInMemory = (int) (Runtime.getRuntime().maxMemory() / (MEMORY_NEEDED_FOR_TRANSLATION_PER_THREAD *1024 * 1024));
		if (maxThreadsInMemory < MAX_TRANSFORMATION_THREADS && maxThreadsInMemory > 0){
			MAX_TRANSFORMATION_THREADS = maxThreadsInMemory;
		}
		 lockObjects = new Object[MAX_TRANSFORMATION_THREADS];
		for (int i = 0; i < lockObjects.length; i++) {
			lockObjects[i] = new Object();
		}
		
		Class<?> module = null;
		try {
			module = Class.forName(CONFIG_RUNTIME_MODULE_NAME);
			
			CONFIG_RUNTIME_MODULE = (com.google.inject.Module) module.newInstance();
			
		} catch (Exception e) {
			module = null;
		}		
		
	}
	public HashMap<String,String> getOptions() {
		return options;
	}

	public void setOptions(HashMap<String,String> options) {
		this.options = options;
	}

	public Job getWaitForSave() {
		return waitForSave;
	}

	public void setWaitForSave(Job waitForSave) {
		this.waitForSave = waitForSave;
	}	
	
	private void loadConfigFile(IPath path) {
		if (CONFIG_RESOURCE == null)
			return;
		try {
			CONFIG_RESOURCE.unload();
		}  catch (Exception e){
			
		}
		CONFIG_RESOURCE.setURI(URI.createPlatformResourceURI(path.toString() + "/config/Grammar.config", true));
		try {
			
			CONFIG_RESOURCE.load(null);
			EObject eObject = CONFIG_RESOURCE.getContents().get(0);
			TreeIterator<EObject> eAllContents = eObject.eAllContents();
			while (eAllContents.hasNext()){
				EObject next = eAllContents.next();
				EStructuralFeature keyFeature = next.eClass().getEStructuralFeature("key");
				EStructuralFeature valueFeature = next.eClass().getEStructuralFeature("value");
				if (keyFeature != null && valueFeature != null){
					String value = (String) next.eGet(valueFeature);
					if (value.contains("%DATE%")){
						Date now = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");	
						value = value.replace("%DATE%", format.format(now));
					}
					getOptions().put((String) next.eGet(keyFeature), value);
				}
			}
		} catch (IOException e) {
			try {
				if (!path.equals(path.removeLastSegments(1))) 
					loadConfigFile(path.removeLastSegments(1));
			} catch (Exception ex){
				
			}
		}
		
	}
	
	private void waitForJob(final Job loadGrammarJob,final Object[] locks) {
		Job waitJob = new Job("Wait for Loading Grammars") {
			private Job joinedJob = loadGrammarJob;
			private Object[] lockedObjects = locks;
			private int idx = 0;
			private void joinJob(){
				if (idx == lockedObjects.length){
					try {
						joinedJob.join();
					} catch (InterruptedException e) {
						
					}
				} else {
					synchronized (lockedObjects[idx]) {
						idx++;
						joinJob();
					}	
				}
				
			}
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				joinJob();
				return  Status.OK_STATUS;
			}
		};
		waitJob.schedule();
	}
	
	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get currently active shell:
		useOutputFolder=false;
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		// Load the transformation units during first run:
		
		if (!inited){
			inited = true;
			if (CONFIG_RUNTIME_MODULE != null){
				CONFIG_INJECTOR = Guice.createInjector(CONFIG_RUNTIME_MODULE);
				try {
					CONFIG_RESOURCE = (Resource) CONFIG_INJECTOR.getInstance(Class.forName("org.eclipse.xtext.resource.XtextResource"));
				} catch (ClassNotFoundException e) {
					
				} 
			}
		}
		
		LoadHandler.updateGrammars();
		Job loadGrammarJob = LoadHandler.getLoadGrammarJob();
		if (loadGrammarJob != null){
			waitForJob(loadGrammarJob,lockObjects);
		}
		
		
		if (LoadHandler.trSystems == null) {
			MessageDialog.openError(shell, "No Translator loaded",
					"Please load the translator.");
		}

		
		Queue<IFile> transQueue = retrieveFilesForTranslation(event);
		
		
		
		
		// Start jobs for all input files:
		long execution_Begin=System.currentTimeMillis();
		
		final Map<String, ExecutionTimes> executionTimesMap = new TreeMap<String, ExecutionTimes>();

		idx = 0;
		inputFiles = new ConcurrentLinkedQueue<IFile>((List<IFile>) transQueue);
		for (int i = 0; i < creator.length; i++) {
			idx = i;
			creator[i]  = new TranslationJobCreator() {

				private int index = idx;

				@Override
				public Job createJob() {

					//TranslationJob job = new TranslationJob(TransHandler.this, inputFiles,lockObjects[i]);
					TranslationJob job = new TranslationJob(inputFiles,useOutputFolder,getOptions(),lockObjects[index]);
					job.setTimesMap(executionTimesMap);
					job.setPriority(Job.DECORATE);

					return job;
				}
			};	
		}
		for (int i = 0; i < creator.length; i++) {
			if (creator[i] != null)
				creator[i].createJob().schedule();
			creator[i] = null;
		}
		
		long execution_End=0;

		IJobManager jobMan = Job.getJobManager();
		// wait for all jobs to complete
		while(jobMan.find(TRANSLATION_JOB_FAMILY).length!=0 || !inputFiles.isEmpty()){
			try {

				if (!Display.getDefault().readAndDispatch())
					Thread.sleep(50);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		execution_End=System.currentTimeMillis();

		System.out.println("\n" + "==================================================================================== \n"
				+ "Summary of execution times in ms (parsing, transformation, serialisation) \n"
				+ "====================================================================================");
		for (String file : executionTimesMap.keySet()) {
			ExecutionTimes times=executionTimesMap.get(file);
			System.out.println(file + " : " + times.overall + " ( " + times.stage1 + ", " + times.stage2 + ", " + times.stage3 + " )");
		}
		System.out
		.println(         "------------------------------------------------------------------------------------ \r\n"
				+ "overall execution time (parallel processing)"
				+ " : " + (execution_End - execution_Begin) +"\n"
				+ "====================================================================================");


		return null;
	}


	protected Queue<IFile> retrieveFilesForTranslation(ExecutionEvent event) {
		// Find files to translate:
		Queue<IFile> transQueue = new LinkedList<IFile>();
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel != null && sel instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) sel;
			for (Iterator<Object> it = structSel.iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					transQueue.add(file);
					IPath path = file.getFullPath().removeLastSegments(1);
					loadConfigFile(path);
				}
				if (obj instanceof IContainer) {
					useOutputFolder=true;
					IResource[] resArr;
					try {
						resArr = ((IContainer) obj).members();
						for (int i=0; i<resArr.length; i++) {
							if (resArr[i] instanceof IFile) {
								IFile file = (IFile) resArr[i];
								transQueue.add(file);

								IPath path = file.getFullPath().removeLastSegments(1);
								loadConfigFile(path);
							}

						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return transQueue;
	}

	
	public static void waitForEnter(String message, Object... args) {
		Console c = System.console();
		if (c != null) {
			// printf-like arguments
			if (message != null)
				c.format(message, args);
			c.format("\nPress ENTER to proceed.\n");
			c.readLine();
		}
	}

}