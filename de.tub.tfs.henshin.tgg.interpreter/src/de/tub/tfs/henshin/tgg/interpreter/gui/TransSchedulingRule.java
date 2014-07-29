package de.tub.tfs.henshin.tgg.interpreter.gui;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

@SuppressWarnings("restriction")
public class TransSchedulingRule implements ISchedulingRule {

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (this == rule)
			return true;
		if (rule instanceof Folder)
			return true;
		if (rule instanceof File)
			return true;
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof TransSchedulingRule)
			return true;
//		if (rule instanceof LoadSchedulingRule)
//			return true;
//		if (rule instanceof Folder)
//			return true;
//		if (rule instanceof File)
//			return true;
		return false;
	}
}