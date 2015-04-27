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
/**
 * 
 */
package de.tub.tfs.henshin.editor.util.flowcontrol;

import java.util.LinkedList;

import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class FlowControlParameterMappingValidator {

	/**
	 * @param diagram
	 */
	public FlowControlParameterMappingValidator() {
		super();
	}

	public boolean validate(final ParameterMapping m) {
		LinkedList<FlowElement> l = new LinkedList<FlowElement>();
		FlowElement target = (FlowElement) m.getTarget().getProvider();

		l.add((FlowElement) m.getSrc().getProvider());

		int i = 0;

		while (i < l.size()) {
			FlowElement e = l.get(i);

			if (e == target) {
				return true;
			}

			for (Transition t : e.getOutGoings()) {
				if (!l.contains(t.getNext())) {
					l.add(t.getNext());
				}
			}

			i++;
		}

		return false;
	}
}
