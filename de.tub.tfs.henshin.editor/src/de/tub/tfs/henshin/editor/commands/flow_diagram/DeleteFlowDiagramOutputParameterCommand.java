/**
 * DeleteFlowDiagramOutputParameterCommand.java
 *
 * Created 25.01.2012 - 17:20:12
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class DeleteFlowDiagramOutputParameterCommand extends CompoundCommand {

	public DeleteFlowDiagramOutputParameterCommand(final Parameter p) {

		FlowDiagram diagram = (FlowDiagram) p.getProvider();

		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getTarget() == p) {
				add(new SimpleDeleteEObjectCommand(m));
			}
		}

		for (ParameterMapping m : ModelUtil.getReferences(p,
				ParameterMapping.class,
				FlowControlUtil.INSTANCE.getFlowControlSystem(diagram),
				FlowControlPackage.Literals.PARAMETER_MAPPING__SRC)) {

			add(new DeleteActivityParameterCommand(m.getTarget(), diagram));
		}

		add(new SimpleDeleteEObjectCommand(p));
	}
}
