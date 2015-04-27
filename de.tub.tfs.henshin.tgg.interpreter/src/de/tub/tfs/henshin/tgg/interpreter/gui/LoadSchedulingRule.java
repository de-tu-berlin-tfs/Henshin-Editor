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

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class LoadSchedulingRule implements ISchedulingRule {

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof LoadSchedulingRule)
			return true;
		
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof TransSchedulingRule)
			return true;
		if (rule instanceof LoadSchedulingRule)
			return true;
		return false;
	}
}