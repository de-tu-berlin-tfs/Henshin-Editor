/**
 * LinkCreationTool.java
 *
 * Created 10.01.2012 - 12:36:40
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowElementCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.editor.ui.dialog.NamedElementLabelProvider;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;

/**
 * @author nam
 * 
 */
public class LinkCreationTool extends CreationTool {

	private Activity model;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands
	 * .Command)
	 */
	@Override
	protected void executeCommand(Command command) {
		EditPart part = getTargetEditPart();

		if (part != null) {
			if (model == null) {
				model = (Activity) ((CreateFlowElementCommand<?>) command)
						.getModel();
			}

			TransformationSystem root = HenshinUtil.INSTANCE
					.getTransformationSystem((EObject) getTargetEditPart()
							.getModel());
			FlowControlSystem flowRoot = FlowControlUtil.INSTANCE
					.getFlowControlSystem(root);

			List<NamedElement> possibleContents = new ArrayList<NamedElement>(
					flowRoot.getUnits());

			if (model.getContent() != null) {
				possibleContents.remove(model.getContent());
			}

			possibleContents.remove(model.getDiagram());
			possibleContents.remove(model);

			NamedElement content = new ExtendedElementListSelectionDialog<NamedElement>(
					null,
					new NamedElementLabelProvider(null) {
						@Override
						public Image getImage(Object element) {
							return ResourceUtil.ICONS.FLOW_DIAGRAM
									.img(16);
						}
					},
					possibleContents.toArray(new NamedElement[possibleContents
							.size()]), "Content Selection",
					"Please choose a content object for the selected Activity:")
					.runSingle();

			if (content != null) {
				Command cmd = null;

				if (command instanceof CreateFlowElementCommand<?>) {
					cmd = command.chain(new SetActivityContentCommand(
							(Activity) ((CreateFlowElementCommand<?>) command)
									.getModel(),
							((CreateFlowElementCommand<?>) command)
									.getLayoutModel(), content));
				} else {
					cmd = new SetActivityContentCommand(model, content);
				}

				super.executeCommand(cmd);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.TargetingTool#getCommand()
	 */
	@Override
	protected Command getCommand() {
		EditPart target = getTargetEditPart();

		model = null;

		if (target != null) {
			Object model = target.getModel();

			if (model instanceof Activity
					&& !(model instanceof CompoundActivity)) {
				this.model = (Activity) model;

				return new SetActivityContentCommand(this.model, null);
			}
		}

		return super.getCommand();
	}
}
