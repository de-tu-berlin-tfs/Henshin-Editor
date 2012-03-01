/**
 * CreateFlowDiagramParameterCommand.java
 *
 * Created 25.01.2012 - 19:14:36
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;

/**
 * @author nam
 * 
 */
public class CreateFlowDiagramParameterCommand extends CompoundCommand {
	/**
     * 
     */
	public CreateFlowDiagramParameterCommand(final FlowDiagram diagram,
			final Parameter activityParameter, boolean isIn) {
		super();

		CreateFlowControlParameterCommand c = new CreateFlowControlParameterCommand(
				diagram, activityParameter.getHenshinParameter());

		Parameter newParam = c.getNewParameter();

		add(c);

		if (isIn) {
			add(new CreateParameterMappingCommand(newParam, activityParameter,
					diagram));
		} else {
			add(new CreateParameterMappingCommand(activityParameter, newParam,
					diagram));
		}

		for (Activity a : ModelUtil.getReferences(diagram, Activity.class,
				FlowControlUtil.INSTANCE.getFlowControlSystem(diagram),
				FlowControlPackage.Literals.ACTIVITY__CONTENT)) {

			c = new CreateFlowControlParameterCommand(a,
					activityParameter.getHenshinParameter());

			add(c);

			if (isIn) {
				add(new CreateParameterMappingCommand(c.getNewParameter(),
						newParam, a));
			} else {
				add(new CreateParameterMappingCommand(newParam,
						c.getNewParameter(), a));
			}
		}
	}
}
