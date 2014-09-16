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
