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
package de.tub.tfs.muvitor.ui.utils.test;

import org.eclipse.core.runtime.jobs.Job;

/**
 * TODO EditorJob: documentation and purpose description
 * 
 * @author Tony Modica
 * 
 */
// OPT Test UIJob for appropriate use
public abstract class EditorJob extends Job {
	
	final private static String FAMILY = "EditorJobFamily";
	
	static public void cancelAll() {
		Job.getJobManager().cancel(FAMILY);
	}
	
	static public void scheduleAll() {
		for (final Job job : Job.getJobManager().find(FAMILY)) {
			job.schedule();
		}
	}
	
	static public void sleepAll() {
		Job.getJobManager().sleep(FAMILY);
	}
	
	static public void wakeUpAll() {
		Job.getJobManager().wakeUp(FAMILY);
	}
	
	public EditorJob(final String name) {
		super(name);
		setPriority(BUILD);
	}
	
	@Override
	public boolean belongsTo(final Object family) {
		return family == FAMILY;
	}
	
}
