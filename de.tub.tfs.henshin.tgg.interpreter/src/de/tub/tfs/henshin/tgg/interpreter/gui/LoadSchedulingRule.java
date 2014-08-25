/*******************************************************************************
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